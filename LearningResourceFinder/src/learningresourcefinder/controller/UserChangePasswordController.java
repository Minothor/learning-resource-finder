package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping ("/user")
public class UserChangePasswordController extends BaseController<User> {

	// Value object matching the form fields (ModelAttribute)
	static class PasswordData {
		String oldPassword;
		String newPassword;
		String confirmPassword;
		public String getOldPassword() {
			return oldPassword;
		}
		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	}
	
	
	@Autowired UserRepository userRepository;

	@RequestMapping("/changepassword")
	public ModelAndView userChangePassword(@RequestParam("id") long id) {
		User user = getRequiredEntity(id);
		SecurityContext.assertCurrentUserMayEditThisUser(user);

		ModelAndView formMv = new ModelAndView("userchangepassword");
		formMv.addObject("user",user);
		formMv.addObject("verifyOldPassword", needToVerifyOldPassword(user));
		formMv.addObject("passwordData", new PasswordData());
		return formMv;
	}

	
	@RequestMapping("/changepasswordsubmit")
	public ModelAndView userChangePasswordSubmit(@Valid @ModelAttribute PasswordData passwordData, BindingResult bindingResult, @RequestParam("id") long id) {
		User user = getRequiredEntity(id);
		SecurityContext.assertCurrentUserMayEditThisUser(user);

		if (bindingResult.hasErrors()) {
			return new ModelAndView("redirect:user","id",user.getId());
			
		} else {

			if(needToVerifyOldPassword(user)){
				if (!(SecurityUtils.md5Encode(passwordData.oldPassword)).equals(user.getPassword())) {
				    bindingResult.rejectValue("oldPassword", "", "Le mot de passe que vous avez introduit ne correspond pas au mot de passe que nous connaissons.");
				}
			}

			if (passwordData.newPassword == null || passwordData.newPassword.equals("")) {
				bindingResult.rejectValue("newPassword", "", "Veuillez introduire un nouveau mot de passe");
			}
			if ( passwordData.confirmPassword == null || passwordData.confirmPassword.equals("")) {
				bindingResult.rejectValue("confirmPassword", "", "Veuillez introduire la confirmation du nouveau mot de passe");
			}
			if (passwordData.confirmPassword != null && passwordData.newPassword != null && !passwordData.confirmPassword.equals(passwordData.newPassword)) {
				String msg = "Le nouveau mot de passe et sa confirmation ne correspondent pas.";
				bindingResult.rejectValue("newPassword", "", msg);
				bindingResult.rejectValue("confirmPassword", "", msg);
			}


			if (bindingResult.hasErrors()) {
				ModelAndView formMv = new ModelAndView("userchangepassword");
				formMv.addObject("user", user);
				formMv.addObject("verifyOldPassword", needToVerifyOldPassword(user));
				formMv.addObject("passwordData", passwordData);
				return formMv;
			} else {
				user.setPassword(SecurityUtils.md5Encode(passwordData.confirmPassword));
				userRepository.merge(user);
				NotificationUtil.addNotificationMessage("Votre nouveau mot de passe est bien enregistré");
				return new ModelAndView("redirect:/user/"+user.getUserName());
			}				

		}

	}

	
	private boolean needToVerifyOldPassword(User user) {
		// If admin and edits somebody else => we don't ask for the old password.
		if(SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS) && !user.equals(SecurityContext.getUser())){
			return false;
		} else {
			return true;
		}
	}
	

//	@ModelAttribute
//	public User findUser(@RequestParam("id") Long id){
//		return  userRepository.find(id);
//	}


}
