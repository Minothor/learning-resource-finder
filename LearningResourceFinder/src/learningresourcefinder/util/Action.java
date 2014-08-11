package learningresourcefinder.util;

import learningresourcefinder.model.User.Level;

public enum Action {
        ADD_RESOURCE(2, Level.LEVEL_1, "Ajouter une ressource"),  // Maybe later should be level 2.
        COMPLETE_RESOURCE(1, Level.LEVEL_1,"Completer les champs manquants d'une ressouce introduite par un autre utilisateur"),
        ADD_PROBLEM(1, Level.LEVEL_1, "Signaler un problème"),
        VALIDATE_RESOURCE(1, Level.LEVEL_1, "Valider une ressource comme visible par des enfants"),
        DISCUSS_PROBLEM(0, Level.LEVEL_1, "Discuter sur un problème"),
        VOTE(1,Level.LEVEL_2, "Voter sur les ressources"),
		EDIT_RESOURCE(1,Level.LEVEL_3,"Editer une ressource introduite par un autre utilisateur"),
        ADD_PLAYLIST(2,Level.LEVEL_3,"Ajouter une séquence"),
		REMOVE_RESOURCE(0,Level.LEVEL_3,"Supprimer une ressource introduite par un autre utilisateur"),
		
		EDIT_RESOURCE_URL(1, Level.LEVEL_4,"Modifier l'URL d'une ressource introduite par un autre utilisateur"),		
		EDIT_RESOURCE_DESCRIPTION(1, Level.LEVEL_4,"Modifier la description d'une ressource introduite par un autre utilisateur"),
		EDIT_RESOURCE_LANGUAGE(1, Level.LEVEL_4,"Modifier le language d'une ressource introduite par un autre utilisateur"),
		EDIT_RESOURCE_ADVERTISING(1, Level.LEVEL_4,"Modifier l'advertising d'une ressource introduite par un autre utilisateur"),
		EDIT_RESOURCE_DURATION(1, Level.LEVEL_4,"Modifier la durée d'une ressource introduite par un autre utilisateur"),
		EDIT_RESOURCE_NATURE(1, Level.LEVEL_4,"Modifier la nature d'une ressource introduite par un autre utilisateur"),
		
		
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
