package learningresourcefinder.service;

import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Level;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    @Autowired UserRepository userRepository;


	public boolean canDoAction(User user, Action action) {
		if(user == null || !SecurityContext.isUserLoggedIn()){
	        return false;
		}
		
		if (SecurityContext.isUserHasRole(Role.ADMIN)) {
		    return true;
		}

		int userlevelIndex = user.getAccountLevel().getLevelIndex();
		int actionlevelIndex = action.getLevel().getLevelIndex();

		return (userlevelIndex >= actionlevelIndex);
	}
	
	
	public void addActionPoints(User user, Action action) {
		int lastLevel = Level.getHighestLevelIndex();

		user.setUserProgressPoints(user.getUserProgressPoints()	+ action.getActionPoints());
		if (user.getUserProgressPoints() > user.getAccountLevel().getLevelProgressPoints() && user.getAccountLevel().getLevelIndex() != lastLevel) {
			user.setAccountLevel(user.getAccountLevel().getLevelIndex()+1);
			user.setUserProgressPoints(0); // Initialize user Points.
			userRepository.merge(user);
			NotificationUtil.addNotificationMessage("Vous êtes à présent un contributeur de <a href='/rights'>niveau " + user.getAccountLevel().getLevelIndex()+"</a>");
		}
	}
}
