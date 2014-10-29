package learningresourcefinder.util;

import learningresourcefinder.model.User.Level;

public enum Action {
        ADD_RESOURCE(2, Level.LEVEL_1, "Ajouter une ressource", "Ressource ajoutée"),  // Maybe later should be level 2.
        COMPLETE_RESOURCE(1, Level.LEVEL_1,"Completer les champs manquants d'une ressouce introduite par un autre utilisateur", "Champs manquants complétés"),
        ADD_PROBLEM(1, Level.LEVEL_1, "Signaler un problème", "Un problème signalé"),
        DISCUSS_PROBLEM(0, Level.LEVEL_1, "Discuter sur un problème", "Discussion d'un problème"),

        VALIDATE_RESOURCE(1, Level.LEVEL_2, "Valider une ressource comme visible par des enfants", "Validation d'une ressource comme visible par des enfants"),
        VOTE(1,Level.LEVEL_2, "Voter sur les ressources", "Vote sur une ressource"),
		EDIT_RESOURCE(1,Level.LEVEL_3,"Editer une ressource introduite par un autre utilisateur", "Ressource éditée"),
        ADD_PLAYLIST(2,Level.LEVEL_3,"Ajouter une séquence", "Ajout d'une ressource"),
		REMOVE_RESOURCE(0,Level.LEVEL_3,"Supprimer une ressource introduite par un autre utilisateur", "Suppression d'une ressource"),
		
		EDIT_RESOURCE_URL(1, Level.LEVEL_4,"Modifier l'URL d'une ressource introduite par un autre utilisateur", "Modification de l'URL d'une ressource"),		
		EDIT_RESOURCE_DESCRIPTION(1, Level.LEVEL_4,"Modifier la description d'une ressource introduite par un autre utilisateur", "Modification de la description d'une ressource"),
		EDIT_RESOURCE_LANGUAGE(1, Level.LEVEL_4,"Modifier la langue d'une ressource introduite par un autre utilisateur", "Modification de la langue d'une ressource"),
		EDIT_RESOURCE_ADVERTISING(1, Level.LEVEL_4,"Modifier l'advertising d'une ressource introduite par un autre utilisateur", "Modification de l'advertising d'une ressource"),
		EDIT_RESOURCE_DURATION(1, Level.LEVEL_4,"Modifier la durée d'une ressource introduite par un autre utilisateur", "Modification de la durée d'une ressource"),
		EDIT_RESOURCE_NATURE(1, Level.LEVEL_4,"Modifier la nature d'une ressource introduite par un autre utilisateur", "Modification de la nature d'une ressource"),
		EDIT_RESOURCE_FORMAT(1, Level.LEVEL_4,"Modifier le format d'une ressource introduite par un autre utilisateur", "Modification du format d'une ressource"),
		
		
		
		LINK_RESOURCE_TO_COMPETENCE(1, Level.LEVEL_4,"Lier une ressouce à une catégorie", "Ressource liée à une catégorie"),
		CLOSE_PROBLEM(3, Level.LEVEL_5, "Clôturer un problème", "Problème clôtuté");
		
		int actionPoints;
	    Level level;
		String describeDoable; // Description for the list of doable actions.
		String describeDone;   // Description for contributions already done (should not include "par un autre utilisateur".
	    
		Action(int actionPoints,Level level,String describeDoable, String describeDone){
			this.actionPoints = actionPoints;
			this.level = level;
			this.describeDoable = describeDoable;
			this.describeDone = describeDone;
		}

		public int getActionPoints() {
			return actionPoints;
		}

		public Level getLevel() {
			return level;
		}

		public String getDescribeDoable() {
			return describeDoable;
		}
		public String getDescribeDone() {
			return describeDone;
		}
		
		
}
