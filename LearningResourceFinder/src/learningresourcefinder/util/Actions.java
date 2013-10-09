package learningresourcefinder.util;

import learningresourcefinder.model.User.LEVEL_STATE;

public class Actions{
	
	public enum ACTIONS_STATE{
		
		VOTE(10,LEVEL_STATE.LEVEL_1),
		ADD_RESOURCES(20,LEVEL_STATE.LEVEL_2),
		EDIT_RESOURCES(20,LEVEL_STATE.LEVEL_3),
		REMOVE_RESOURCES(30,LEVEL_STATE.LEVEL_3);
		
		int actionPoints;
	    LEVEL_STATE level;
		
		ACTIONS_STATE(int actionPoints,LEVEL_STATE level){
			this.actionPoints = actionPoints;
			this.level = level;
		}

		public int getActionPoints() {
			return actionPoints;
		}

		public LEVEL_STATE getLevel() {
			return level;
		}
	}
	
	
}
