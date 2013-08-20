package learningresourcefinder.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import learningresourcefinder.exception.UserAlreadyExistsException;
import learningresourcefinder.exception.UserAlreadyExistsException.IdentifierType;
import learningresourcefinder.model.User;
import learningresourcefinder.service.UserService;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.util.NotificationUtil;

@Controller
public class RegisterController extends BaseController<User> {

	@Autowired UserService userService;

	@RequestMapping("/register")
	public String register(@ModelAttribute User user) {

		return "register"; 
	}

	@RequestMapping("/registersubmit")
	public ModelAndView registerSubmit(@Valid @ModelAttribute User user, BindingResult result) {

		/////// Errors detection
		if (StringUtils.isNotEmpty(user.getUserName()) && !HTMLUtil.isHtmlSecure(user.getUserName())) {
			result.rejectValue( "userName","","Vous avez introduit du HTML/Javascript dans vos informations d'enregistrement");
		}
		
		if (StringUtils.isNotEmpty(user.getUserName()) && (user.getMail() == null || user.getMail().isEmpty())) {
    		result.rejectValue("mail", "", "Veuillez introduire une adresse e-mail (pour recevoir votre code de vérification).");			
		} 
		
		if (StringUtils.isNotEmpty(user.getUserName()) && (user.getPassword() == null || user.getPassword().isEmpty())) {
    		result.rejectValue("password", "", "Veuillez introduire un mot de passe.");			
		} 	
		
		if (result.hasErrors()) {
			return new ModelAndView("register");
		}

		
		//////// Try to register
		try {

			user = userService.registerUser(false, user.getUserName(),	user.getPassword(), user.getMail());
			NotificationUtil
			.addNotificationMessage("Un message de confirmation de votre inscription vous a été envoyé sur votre email :"
					+ user.getMail()
					+ ". Merci d'activer votre compte (en cliquant sur le lien de confirmation)afin de pouvoir l'utiliser.");

		} catch (UserAlreadyExistsException uaee) {

			if (uaee.getType() == IdentifierType.MAIL) {
				result.rejectValue("mail", "", "Un autre utilisateur a déjà choisi cet e-mail. Cela veut soit dire que vous avez déjà un compte chez nous"
						+ " (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>), "
						+ "ou bien cela peut vouloir dire que l'e-mail que vous avez introduit n'est pas correct.");

			} else if (uaee.getType() == IdentifierType.USERNAME) {
				result.rejectValue("userName", "", "Un autre utilisateur a déjà choisi ce pseudonyme. Merci d'en spécifier un autre. "
						+ "A moins que cela veuille dire que vous avez déjà un compte chez nous (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>.");				
			} else { 
				throw new RuntimeException("Bug - Unsupported type: "
						+ uaee.getType());
			}

			return new ModelAndView("register");
		}


		return new ModelAndView("redirect:home");
	}

}
