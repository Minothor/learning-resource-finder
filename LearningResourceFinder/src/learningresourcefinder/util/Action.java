package learningresourcefinder.util;

import learningresourcefinder.model.User.Level;

public enum Action {
        ADD_RESOURCE(1, Level.LEVEL_1, "Ajouter une ressource"),  // Maybe later should be level 2.
        COMPLETE_RESOURCE(1, Level.LEVEL_1,"Completer les champs manquants d'une ressouce introduite par un autre utilisateur"),
        ADD_PROBLEM(1, Level.LEVEL_1, "Signaler un problème"),
        DISCUSS_PROBLEM(0, Level.LEVEL_1, "Discuter sur un problème"),
        VOTE(1,Level.LEVEL_2, "Voter sur les ressources"),
		EDIT_RESOURCE(1,Level.LEVEL_3,"Editer une ressource introduite par un autre utilisateur"),
        ADD_PLAYLIST(2,Level.LEVEL_3,"Ajouter une séquence"),
		REMOVE_RESOURCE(0,Level.LEVEL_3,"Supprimer une ressource introduite par un autre utilisateur"),
		EDIT_RESOURCE_URL(1, Level.LEVEL_4,"Modifier l'URL d'une ressource introduite par un autre utilisateur"),
		LINK_RESOURCE_TO_COMPETENCE(1, Level.LEVEL_4,"Lier une ressouce à une compétence"),
		CLOSE_PROBLEM(3, Level.LEVEL_5, "Clôturer un problème");
		
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
