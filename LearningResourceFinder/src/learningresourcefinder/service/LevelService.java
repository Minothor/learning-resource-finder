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
		user.setUserProgressPoints(user.getUserProgressPoints()
				+ action.getActionPoints());
		if (user.getUserProgressPoints() > user.getAccountLevel().getLevelProgressPoints()) {

		//	user.setAccountLevel(LEVEL_STATE.LEVEL_1);
			
			// point = modulo de ce qui reste.
			// leve ++
		}
	}

}
