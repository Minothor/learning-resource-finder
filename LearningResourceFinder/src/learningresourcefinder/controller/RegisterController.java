package learningresourcefinder.controller;

import javax.validation.Valid;

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

		if (result.hasErrors())
			return new ModelAndView("register");

		else if (!HTMLUtil.isHtmlSecure(user.getUserName())) {

			NotificationUtil
					.addNotificationMessage("Vous avez introduit du HTML/Javascript dans vos informations d'enregistrement"
							+ user.getUserName());

			return new ModelAndView("register");

		} else {
			try {

				user = userService.registerUser(false, user.getUserName(),
						user.getPassword(), user.getMail(), false);
				NotificationUtil
						.addNotificationMessage("Un message de confirmation de votre inscription vous a été envoyé sur votre email :"
								+ user.getMail()
								+ ". Merci d'activer votre compte (en cliquant sur le lien de confirmation)afin de pouvoir l'utiliser.");

			} catch (UserAlreadyExistsException uaee) {

				if (uaee.getType() == IdentifierType.MAIL) {
					NotificationUtil
							.addNotificationMessage("Un autre utilisateur a déjà choisi cet e-mail. Cela veut soit dire que vous avez déjà un compte chez nous"
									+ " (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>), "
									+ "ou bien cela peut vouloir dire que l'e-mail que vous avez introduit n'est pas correct.");

				} else if (uaee.getType() == IdentifierType.USERNAME) {
					NotificationUtil
							.addNotificationMessage("Un autre utilisateur a déjà choisi ce pseudonyme. Merci d'en spécifier un autre. "
									+ "A moins que cela veuille dire que vous avez déjà un compte chez nous (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>.");
				} else { 
					throw new RuntimeException("Bug - Unsupported type: "
							+ uaee.getType());
				}

				return new ModelAndView("register");
			}

		}

		return new ModelAndView("redirect:home");
	}

}
