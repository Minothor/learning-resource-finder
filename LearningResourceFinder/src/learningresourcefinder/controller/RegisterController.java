package learningresourcefinder.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import learningresourcefinder.exception.UserAlreadyExistsException;
import learningresourcefinder.exception.UserAlreadyExistsException.IdentifierType;
import learningresourcefinder.model.User;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;

@Controller
public class RegisterController extends BaseController<User> {

	@Autowired UserService userService;

	@RequestMapping("/register")
	public String register(@ModelAttribute User user) {

		return "register"; 
	}

	@RequestMapping("/ajax/registersubmit")  
	public @ResponseBody String registerSubmit(@RequestParam(value="emailRegister") String email, 
											   @RequestParam(value="usernameRegister") String username,
											   @RequestParam(value="passwordRegister") String password) {
	

		/////// Errors detection
		if (StringUtils.isNotEmpty(username) && !HTMLUtil.isHtmlSecure(username)) {
			NotificationUtil.addNotificationMessage("Vous avez introduit du HTML/Javascript dans vos informations d'enregistrement", Status.ERROR);
		}
		
		if (StringUtils.isNotEmpty(email) && (email == null || email.isEmpty())) {
			NotificationUtil.addNotificationMessage("Veuillez introduire une adresse e-mail (pour recevoir votre code de vérification).", Status.WARNING);			
		} 
		
		if (StringUtils.isNotEmpty(password) && (password == null || password.isEmpty())) {
			NotificationUtil.addNotificationMessage("Veuillez introduire un mot de passe.");
		} 
		
		User user = null;

		//////// Try to register
		try {

			user = userService.registerUser(false, username, password, email);
			NotificationUtil.addNotificationMessage("Un message de confirmation de votre inscription vous a été envoyé sur votre email: "
					+ email
					+ ". Merci d'activer votre compte (en cliquant sur le lien de confirmation contenu dans l'e-mail) afin de pouvoir l'utiliser.");
			return "success";
		} catch (UserAlreadyExistsException uaee) {

			if (uaee.getType() == IdentifierType.MAIL) {
				NotificationUtil.addNotificationMessage("Un autre utilisateur a déjà choisi cet e-mail. Cela veut soit dire que vous avez déjà un compte chez nous"
						+ " (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>), "
						+ "ou bien cela peut vouloir dire que l'e-mail que vous avez introduit n'est pas correct.");
				return "error";

			} else if (uaee.getType() == IdentifierType.USERNAME) {
				NotificationUtil.addNotificationMessage("Un autre utilisateur a déjà choisi ce pseudonyme. Merci d'en spécifier un autre. "
						+ "A moins que cela veuille dire que vous avez déjà un compte chez nous (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>.");
				return "error";
			} else { 
				throw new RuntimeException("Bug - Unsupported type: " + uaee.getType());
			}
		}
		
		
	}
}
