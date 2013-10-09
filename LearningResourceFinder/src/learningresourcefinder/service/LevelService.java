package learningresourcefinder.service;

import learningresourcefinder.model.User;
import learningresourcefinder.model.User.LEVEL_STATE;
import learningresourcefinder.util.Actions.ACTIONS_STATE;


import org.springframework.stereotype.Service;

@Service
public class LevelService {

	public boolean canDoAction(User user, ACTIONS_STATE action) {
		
		int userlevelIndex = user.getAccountLevel().getLevelIndex();
		int actionlevelIndex = action.getLevel().getLevelIndex();
		
		if (userlevelIndex >= actionlevelIndex) {
			return true;
		}
		
		return false;
	}

	public void addActionPoints(User user, ACTIONS_STATE action) {
		int lastLevel = LEVEL_STATE.getHighestLevelIndex();
		
		user.setUserProgressPoints(user.getUserProgressPoints()
				+ action.getActionPoints());
		if (user.getUserProgressPoints() > user.getAccountLevel().getLevelProgressPoints() && user.getAccountLevel().getLevelIndex() != lastLevel) {
		      user.setAccountLevel(user.getAccountLevel().getLevelIndex());
		      user.setUserProgressPoints(0); // Initialize user Points.
		      // Notify User .
		}
	}

}
