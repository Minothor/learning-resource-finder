package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import learningresourcefinder.exception.InvalidPasswordException;
import learningresourcefinder.exception.UserLockedException;
import learningresourcefinder.exception.UserNotFoundException;
import learningresourcefinder.exception.UserNotValidatedException;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountConnectedType;
import learningresourcefinder.model.User.AccountStatus;
import learningresourcefinder.model.User.Gender;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.service.LoginService.WaitDelayNotReachedException;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.ImageUtil;
import learningresourcefinder.util.Logger;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class LoginController extends BaseController<User> {

	public static final String AUTOLOGIN_KEY = "autologin";
	public static final String PROVIDERSIGNEDIN_KEY = "providersignedin";

	@Autowired LoginService loginService;
	@Autowired UserDisplayController userDisplayController;
	@Autowired UserRepository userRepository;
	@Autowired SocialAuthConfig socialAuthConfig;
	@Autowired UserService userService;
	
    @Logger Log log;
    
	// We will show the login form and social buttons to the user.
	@RequestMapping(value = "/login")
	public String signin(HttpServletRequest request) {
		return "login";
	}

	// User clicked the facebook or google login button on our web site.
	@RequestMapping(value = "/loginsocial")
	public String loginSocial(@RequestParam("provider") String providerId, HttpSession session) throws Exception {
	    String urlToFacebookOrGoogle = "";

	    SocialAuthManager socialManager = new SocialAuthManager();
	    socialManager.setSocialAuthConfig(socialAuthConfig);
	    String successUrl = UrlUtil.getAbsoluteUrl("loginsocialcallback");  // !!! for local tests, google does not work (it requires an url that is not "localhost" ...).
	    
	    try {
	        urlToFacebookOrGoogle = socialManager.getAuthenticationUrl(providerId, successUrl)+"&display=popup"; //  We can use this method to add permissions later : getAuthenticationUrl(id, successUrl, permission) 
	    } catch (Exception e) {
            log.error("Exception during social login (while getting the URL to " + providerId + "for user " + SecurityContext.getUser(), e);
	        NotificationUtil.addNotificationMessage("Nous ne parvenons pas à contacter "+providerId+". Veuillez vous connecter d'une autre manière ou réessayer plus tard.",Status.WARNING);
	        return "redirect:login";
	    }
	    session.setAttribute("providerId", providerId);
	    session.setAttribute("socialmanager", socialManager); 

	    return "redirect:"+urlToFacebookOrGoogle;
	}

  
	// In loginSocial, we redirect to facebook or google. Then FB or google tells the browser to redirect here.
	@RequestMapping(value = "/loginsocialcallback")
	public String loginSocialCallback(HttpSession session, HttpServletRequest request) {
	  
		log.debug("loginSocialCallback called.");
        SocialAuthManager socialAuthManager = (SocialAuthManager) session.getAttribute("socialmanager");
        session.removeAttribute("socialmanager");
        if (socialAuthManager == null) {
        	throw new RuntimeException("socialAuthManager is not supposed to be null in the callback");  // It may happen if locainSocialCallbeck is called twice in a row for the same user by google or facebook (bug under investigation)
        }
        
        // The following line does not work. We'd need to access SocialAuthManager.providerId which is private. 
        // String providerId = socialAuthManager.getCurrentAuthProvider().getProviderId();  // "facebook" or "google"
        // ==> We have been forced to store the providerId separately in the session.
        String providerId = (String) session.getAttribute("providerId");
        session.removeAttribute("providerId");
        
        // Contacting Facebook or Google to get the user's e-mail 
        Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request);
        Profile profile = null;
        try {
            profile = socialAuthManager.connect(paramsMap).getUserProfile();
        } catch (Exception e) {
            log.error("Exception during social login callback (while contacting "+providerId+" to get the e-mail address)", e);
            NotificationUtil.addNotificationMessage("Nous ne parvenons pas à contacter "+providerId+" pour obtenir votre adress e-mail afin de vous connecter sur notre site. Veuillez vous connecter d'une autre manière ou réessayer plus tard.",Status.ERROR);
            return "redirect:/";
        }

        ///// Get the e-mail & user from e-mail
        String email = profile.getEmail();
        User user = userRepository.getUserByEmail(email);
        if (user == null) {  // First time login with facebook/google => no user in our DB yet.
            user = userService.registerSocialUser(email);
        }
        
        completeUserFromSocialProfile(providerId, profile, user);        

        ////// Login
        if (user.getAccountStatus() == AccountStatus.NOTVALIDATED) {  // it could happen if the user tried to register  manually (without finishing), then tries through facebook/google.
            user.setAccountStatus(AccountStatus.ACTIVE);
        }
        
        String errorMsg = null;
        try {
            loginService.login(user.getMail(), user.getPassword(), false, user.getId(), AccountConnectedType.getProviderType(providerId));
        } catch (UserLockedException e) {
            errorMsg = "L'utilisateur avec l'id '" + user.getId() +"' et le mail '" + user.getMail() + "' "
                    + " est verrouillé. Contacter un administrateur pour le déverrouiller.";

        } catch (WaitDelayNotReachedException e) {
            errorMsg = "Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité."
                    + " Actuellement, il reste "
                    + DateUtil.formatIntervalFromToNow(e.getNextPossibleTry())
                    + " à attendre.";
        } catch (Exception e) {  // All other exceptions should not happen here (or are bugs).
            throw new RuntimeException(e);
        }

        if (errorMsg != null) {
            NotificationUtil.addNotificationMessage(errorMsg,Status.ERROR);
        } else {
            NotificationUtil.addNotificationMessage("Vous êtes à present connecté sur " + UrlUtil.getWebSiteName(),Status.SUCCESS);             
        }
        return "loginsocialcallback"; // This contains a JavaScript to close the popup.
	}

    private void completeUserFromSocialProfile(String providerId,  Profile profile, User user) {
        ///// Get general info
        if (user.getFirstName() != null) {
            user.setFirstName(profile.getFirstName());
        }
        if (user.getLastName() != null) {
            user.setLastName(profile.getLastName());
        }
        if (user.getBirthDate() != null && profile.getDob() != null) {  // I've not seen this worked (as if FB & Google don't send the birthdate) - JOHN 2013-09-06
            Calendar cal = new GregorianCalendar(profile.getDob().getYear(), profile.getDob().getMonth()+1, profile.getDob().getDay());
            user.setBirthDate(cal.getTime());
        }
        if (user.getGender() != null && profile.getGender() != null) {
            user.setGender(profile.getGender().toLowerCase().startsWith("m") ? Gender.MALE : Gender.FEMALE);
        }        
        
        ///// Get the user picture
        if (! user.isPicture()) {
            String urlPicture = null;
            if (providerId.equals("facebook")) { // Picture provided by default is too small. We've added the paramater type to get it large.
                urlPicture = profile.getProfileImageURL() + "?type=large"; 
            } else {
                urlPicture = profile.getProfileImageURL();
            }
    
           
            try {
                BufferedImage image = ImageUtil.readImage(urlPicture);
                userService.addOrUpdateUserImage(user, image, true);
            } catch (RuntimeException e) {
                NotificationUtil.addNotificationMessage("Erreur lors de la récupération de votre photo chez "+providerId,Status.ERROR);
                log.error(e);
                // We just continue, this is a non-blocking error.
            }
        }
    }
	
	
	/**
	 * @param password required=false because we don't use pswd in DEV
	 * @param keepLoggedIn required=false else when user doesn't check the checkbox we get a 400 error
	 */
	@RequestMapping("/ajax/loginsubmit")
	public @ResponseBody void loginSubmit(
			@RequestParam(value= "userNameOrMail", required = false) String userNameOrMail,
			@RequestParam(value= "password", required = false) String password,
			@RequestParam(value= "autoLogin", required = false) boolean autologin) {
		
		
		User user = null;
		try {
			user = loginService.login(userNameOrMail, password, autologin,	null, AccountConnectedType.LOCAL);
			NotificationUtil.addNotificationMessage("Vous êtes à present connecté sur "	+ UrlUtil.getWebSiteName(),Status.SUCCESS);

		} catch (UserNotFoundException e) {
			NotificationUtil.addNotificationMessage("L'utilisateur '" + userNameOrMail + "' n'existe pas", Status.ERROR);

		} catch (InvalidPasswordException e) {
			NotificationUtil.addNotificationMessage("Ce mot de passe n'est pas valide pour l'utilisateur '" 	+ userNameOrMail + "'", Status.ERROR);

		} catch (UserNotValidatedException e) {
			NotificationUtil.addNotificationMessage("L'utilisateur '"
					+ userNameOrMail
					+ "' n'a pas été valide. Vérifiez vos mails reçus et cliquez sur le lien du mail qui vous a été envoyé à l'enregistrement.",Status.ERROR);

		} catch (UserLockedException e) {
			NotificationUtil.addNotificationMessage("L'utilisateur  '"
					+ userNameOrMail
					+ "' est verrouillé. Contacter un administrateur pour le déverrouiller.",Status.ERROR);

		} catch (WaitDelayNotReachedException e) {
			NotificationUtil.addNotificationMessage("Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité."
					+ " Actuellement, il reste "+ DateUtil.formatIntervalFromToNow(e.getNextPossibleTry())	+ " à attendre.", Status.ERROR);
		}
	}



}
