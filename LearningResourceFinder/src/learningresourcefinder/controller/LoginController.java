package learningresourcefinder.controller;

import java.awt.image.BufferedImage;
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
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.service.LoginService.WaitDelayNotReachedException;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.Logger;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.ImageUtil;
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
import org.springframework.web.servlet.ModelAndView;



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

	    try {
	        urlToFacebookOrGoogle = socialManager.getAuthenticationUrl(providerId, UrlUtil.getAbsoluteUrl("loginsocialcallback")); //  We can use this method to add permissions later : getAuthenticationUrl(id, successUrl, permission) 
	    } catch (Exception e) {
            log.error("Exception during social login (while getting the URL to " + providerId + "for user " + SecurityContext.getUser(), e);
	        NotificationUtil.addNotificationMessage("Nous ne parvenons pas à contacter "+providerId+". Veuillez vous connecter d'une autre manière ou réessayer plus tard.");
	        return "redirect:login";
	    }
	    session.setAttribute("providerId", providerId);
	    session.setAttribute("socialmanager", socialManager); 

	    return "redirect:"+urlToFacebookOrGoogle;
	}

  
	// In loginSocial, we redirect to facebook or google. Then FB or google tells the browser to redirect here.
	@RequestMapping(value = "/loginsocialcallback")
	public String loginSocialCallback(HttpSession session, HttpServletRequest request) {

        SocialAuthManager socialAuthManager = (SocialAuthManager) session.getAttribute("socialmanager");
        
        // The following line does not work. We'd need to access SocialAuthManager.providerId which is private. 
        // String providerId = socialAuthManager.getCurrentAuthProvider().getProviderId();  // "facebook" or "google"
        // ==> We have been forced to store the providerId separately in the session.

        String providerId = (String) session.getAttribute("providerId");
        // Contacting Facebook or Google to get the user's e-mail 
        Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request);
        Profile profile = null;
        try {
            profile = socialAuthManager.connect(paramsMap).getUserProfile();
        } catch (Exception e) {
            log.error("Exception during social login callback (while contacting "+providerId+" to get the e-mail address)", e);
            NotificationUtil.addNotificationMessage("Nous ne parvenons pas à contacter "+providerId+" pour obtenir votre adress e-mail afin de vous connecter sur notre site. Veuillez vous connecter d'une autre manière ou réessayer plus tard.");
            return "redirect:login";
        }

        ///// Get the e-mail & user from e-mail
        String email = profile.getEmail();
        User user = userRepository.getUserByEmail(email);
        if (user == null) {  // First time login with facebook/google => no user in our DB yet.
            user = userService.registerSocialUser(email);
        }
        
        
        ///// Get the user picture
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
            NotificationUtil.addNotificationMessage("Erreur lors de la récupération de votre photo chez "+providerId);
            log.error(e);
            // We just continue, this is a non-blocking error.
        }
        

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
            NotificationUtil.addNotificationMessage(errorMsg);

            return "redirect:/login";

        } else {
            NotificationUtil.addNotificationMessage("Vous êtes à present connecté sur " + UrlUtil.getWebSiteName());             
            return "redirect:/user/" + user.getUserName();
        }
	}
	
	
	/**
	 * @param password required=false because we don't use pswd in DEV
	 * @param keepLoggedIn required=false else when user doesn't check the checkbox we get a 400 error
	 */
	@RequestMapping("/loginsubmit")
	public ModelAndView loginSubmit(
			@RequestParam(value= "userNameOrMail", required = false) String userNameOrMail,
			@RequestParam(value= "password", required = false) String password,
			@RequestParam(value= "autoLogin", required = false) boolean autologin) {
		
		
		String errorMsg = null;
		User user = null;
		try {
			user = loginService.login(userNameOrMail, password, autologin,	null, AccountConnectedType.LOCAL);
			NotificationUtil.addNotificationMessage("Vous êtes à present connecté sur "	+ UrlUtil.getWebSiteName());

		} catch (UserNotFoundException e) {
			errorMsg = "L'utilisateur '" + userNameOrMail + "' n'existe pas";

		} catch (InvalidPasswordException e) {

			try {
				User retrieveuser = loginService.identifyUserByEMailOrName(userNameOrMail);

				// TOOD: restore (for social login)
				// errorMsg = loginService.getRemainderLoginMessage(retrieveuser);

			} catch (UserNotFoundException e1) {
				errorMsg = "L'utilisateur '" + userNameOrMail
						+ "' n'existe pas";
			}

			if (StringUtils.isBlank(errorMsg)) {
				errorMsg = "Ce mot de passe n'est pas valide pour l'utilisateur '"
						+ userNameOrMail + "'";
			}

		} catch (UserNotValidatedException e) {
			errorMsg = "L'utilisateur '"
					+ userNameOrMail
					+ "' n'a pas été valide. Vérifiez vos mails reçus et cliquez sur le lien du mail qui vous a été envoyé à l'enregistrement.";

		} catch (UserLockedException e) {
			errorMsg = "L'utilisateur  '"
					+ userNameOrMail
					+ "' est verrouillé. Contacter un administrateur pour le déverrouiller.";

		} catch (WaitDelayNotReachedException e) {
			errorMsg = "Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité."
					+ " Actuellement, il reste "
					+ DateUtil.formatIntervalFromToNow(e.getNextPossibleTry())
					+ " à attendre.";
		}

		if (errorMsg != null) {

		    ModelAndView mv = new ModelAndView("login");
		    mv.addObject("autoLogin",  autologin);
		    mv.addObject("userNameOrMail", userNameOrMail);

		    NotificationUtil.addNotificationMessage(errorMsg);

		    return mv;

		} else {
		    return new ModelAndView("redirect:user/" + user.getUserName());
		}
	}



}
