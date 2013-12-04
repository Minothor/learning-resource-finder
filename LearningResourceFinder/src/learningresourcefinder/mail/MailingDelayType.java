package learningresourcefinder.mail;

public enum MailingDelayType {
	
    IMMEDIATELY("Envoi immédiat", "Un e-mail est envoyé dès qu'un événement se produit."),
	DAILY("Envoi journalier", "Les notifications sont groupées et envoyées dans, au plus, un mail par jour, sauf pour les messages urgents."),
	WEEKLY("Envoi hebdomadaire", "Les notifications sont groupées et envoyées dans, au plus, un mail par semaine, sauf pour les messages urgents.");

    
	private MailingDelayType(String name, String description){
	    this.name = name;
	    this.description = description;
	}

	
	private String name;
    private String description;
    
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
	
}
