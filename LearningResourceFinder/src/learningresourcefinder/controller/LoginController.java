package learningresourcefinder.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import learningresourcefinder.exception.InvalidPasswordException;
import learningresourcefinder.exception.UserAlreadyExistsException;
import learningresourcefinder.exception.UserLockedException;
import learningresourcefinder.exception.UserNotFoundException;
import learningresourcefinder.exception.UserNotValidatedException;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.AccountConnectedType;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.LoginService;
import learningresourcefinder.service.LoginService.WaitDelayNotReachedException;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.lang3.StringUtils;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController<User> {

	public static final String AUTOLOGIN_KEY = "autologin";
	public static final String PROVIDERSIGNEDIN_KEY = "providersignedin";

	@Autowired LoginService loginService;
	@Autowired UserDisplayController userDisplayController;
	@Autowired UserRepository userRepository;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String signin(HttpServletRequest request) {
		return "login";
	}
	
	@RequestMapping(value = "/loginsocialPage", method = RequestMethod.GET)
    public String socialSigninPage(HttpServletRequest request,WebRequest req) {
	       return getLoginSocialPage(request);
	   }
	
   public String getLoginSocialPage(HttpServletRequest request){
	        
	      String urlSocial = "";
	      HttpSession mySession = request.getSession();
	      String providerId = (String) request.getParameter("providerid");
	      
	      SocialAuthConfig config = SocialAuthConfig.getDefault();
	      SocialAuthManager SocialManager = new SocialAuthManager();
	
	      try {
	          
            config.load();
            SocialManager.setSocialAuthConfig(config);
            urlSocial = SocialManager.getAuthenticationUrl(providerId,"http://localhost:8080/loginsocial"); //  We can use this method to add permissions later : getAuthenticationUrl(id, successUrl, permission) 
     
            
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
	      
	     mySession.setAttribute("socialmanager", SocialManager); 
	     
	     return "redirect:"+urlSocial;
    }
  
	@RequestMapping(value = "/loginsocial", method = RequestMethod.GET)
	public ModelAndView socialSignin(HttpServletRequest request,WebRequest req) throws IOException, UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException, UserAlreadyExistsException {

        AuthProvider provider = null;
        User u = null;
     
        SocialAuthManager socialManager = (SocialAuthManager) request.getSession()
                .getAttribute("socialmanager");
        Map<String, String> paramsMap = SocialAuthUtil
                .getRequestParametersMap(request);
        
         try {
             
            provider= socialManager.connect(paramsMap);
            u = loginService.loginSocial(provider);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
 
        }
       
         if(u!= null){
             u = loginService.login(u.getMail(), u.getPassword(), false,u.getId(), AccountConnectedType.FACEBOOK);
             NotificationUtil.addNotificationMessage("Vous êtes à present connecté sur "   + UrlUtil.getWebSiteName());             
             return new ModelAndView("redirect:user/" + u.getUserName());
         }
           // Maybe add notification
         return new ModelAndView("redirect:login");
	}
	
	
	
	
	/**
	 * 
	 * @param password
	 *            required=false because we don't use pswd in DEV
	 * @param keepLoggedIn
	 *            required=false because when user don't check the checkbox we
	 *            get a 400 error
	 * @return
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
			String nextPage = loginService.getPageAfterLogin(user);
			if (nextPage != null) {
				return new ModelAndView("redirect:" + nextPage);
			} else {
				return new ModelAndView("redirect:user/" + user.getUserName());
			}
		}
	}



}
