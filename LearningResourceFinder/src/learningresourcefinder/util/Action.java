package learningresourcefinder.util;

import learningresourcefinder.model.User.Level;

public enum Action {
	
		VOTE(10,Level.LEVEL_2, "Voter sur les ressources"),
		ADD_RESOURCES(20,Level.LEVEL_2,"Ajouter des ressources"),
		EDIT_RESOURCES(20,Level.LEVEL_3,"Editer des ressources"),
		REMOVE_RESOURCES(30,Level.LEVEL_3,"Supprimer des ressources");
		
		int actionPoints;
	    Level level;
		String describe;
	    
		Action(int actionPoints,Level level,String describe){
			this.actionPoints = actionPoints;
			this.level = level;
			this.describe = describe;
		}

		public int getActionPoints() {
			return actionPoints;
		}

		public Level getLevel() {
			return level;
		}

		public String getDescribe() {
			return describe;
		}
		
}
