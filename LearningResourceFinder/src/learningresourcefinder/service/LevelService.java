package learningresourcefinder.service;

import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Level;
import learningresourcefinder.util.Action;



import org.springframework.stereotype.Service;

@Service
public class LevelService {

	public boolean canDoAction(User user, Action action) {
		
		int userlevelIndex = user.getAccountLevel().getLevelIndex();
		int actionlevelIndex = action.getLevel().getLevelIndex();
		
		if (userlevelIndex >= actionlevelIndex) {
			return true;
		}
		
		return false;
	}

	public void addActionPoints(User user, Action action) {
		int lastLevel = Level.getHighestLevelIndex();
		
		user.setUserProgressPoints(user.getUserProgressPoints()
				+ action.getActionPoints());
		if (user.getUserProgressPoints() > user.getAccountLevel().getLevelProgressPoints() && user.getAccountLevel().getLevelIndex() != lastLevel) {
		      user.setAccountLevel(user.getAccountLevel().getLevelIndex());
		      user.setUserProgressPoints(0); // Initialize user Points.
		      // Notify User . And Refresh Progress Bar
		}
		
	}

}
