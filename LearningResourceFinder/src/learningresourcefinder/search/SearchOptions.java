package learningresourcefinder.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import learningresourcefinder.model.Competence;


public class SearchOptions {

	List<Language> language = new ArrayList<>();
	List<Format> format = new ArrayList<>();
	List<Platform> platform = new ArrayList<>();
	List<Nature> nature = new ArrayList<>();
	Boolean advertising = null;
	Integer maxDuration;
	String searchPhrase = "";
	Competence competence; // Search restricted within this competence.
	
	public SearchOptions () {
		getLanguage().add(Language.FR);
		getFormat().addAll(Arrays.asList(Format.values()));
		getPlatform().addAll(Arrays.asList(Platform.values()));
		getNature().addAll(Arrays.asList(Nature.values()));
	}
	
	public String toString() {
	    return getSearchPhrase() + " " +
	           isAdvertising() + " " + getMaxDuration() + " " +
	           getLanguage().toString() + " " + getFormat().toString() + " " + getPlatform().toString() + " " + getNature().toString()
	           + " " + competence;
	}
	
	
	public enum Language{
		FR("Français"), 
		NL("Néerlandais"), 
		DE("Allemand"), 
		EN("Anglais"), 
		RU("Russe");
		private Language(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}
	
	public enum Format{
		VIDEOS("Vidéo"),
		DOC("Document"), 
		INTERACTIVE("Interactif"), 
		AUDIO("Audio");
		private Format(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}

	public enum Platform {
		BROWSER("Navigateur", "Fonctionne dans la pluspart des navigateurs tel que chrome, firefox ou internet exploreur. N'est pas lié à une plate-forme particulière (Android, Linux, Windows, etc.)"),
		ANDROID("Android", "Application nécessitant une tablette ou un smartphone avec Android"), 
		IPAD("iPad/iPhone", "Application nécessitant une tablette ou un smartphone avec iOs"), 
	    WIN("Windows", "Application à installer sous Windows"), 
		MAC("Mac", "Application à installer sous Mac OS"),
		LINUX("Linux", "Application à installer sous Linux");
		private Platform(String name, String description){
		    this.name = name; this.description = description;}
		private final String name;
        private final String description;
		public String getName(){return name;}
        public String getDescription(){return description;}
	}
	
	public enum Nature {
		FORMATIVE_EXPL("Formatif (jeu/exploration)"),
        FORMATIVE_THEO("Formatif (théorie)"),
		EVALUATIVE_NOANSWER("Evaluatif sans correction"), 
        EVALUATIVE_WITHANSWER("Evaluatif avec correction"), 
		FORMATIVE_EVAL_NOANSWER("Formatif et évaluatif sans correction"), 
        FORMATIVE_EVAL_WITHANSWER("Formatif et évaluatif avec correction"); 
		private Nature(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}


	public List<Language> getLanguage() {
		// Spring nullifies the lists if they are empty (which is not nice from Spring...)
		return language == null ? language = new ArrayList<>() : language;
	}
	public void setLanguage(List<Language> language) {
		this.language = language;
	}
	public List<Format> getFormat() {
		// Spring nullifies the lists if they are empty (which is not nice from Spring...)
		return format == null ? format = new ArrayList<>() : format;
	}
	public void setFormat(List<Format> format) {
		this.format = format;
	}
	public List<Platform> getPlatform() {
		// Spring nullifies the lists if they are empty (which is not nice from Spring...)
		return platform == null ? platform = new ArrayList<>() : platform;
	}
	public void setPlatform(List<Platform> platform) {
		this.platform = platform;
	}
	public List<Nature> getNature() {
		// Spring nullifies the lists if they are empty (which is not nice from Spring...)
		return nature == null ? nature = new ArrayList<>() : nature;
	}
	public void setNature(List<Nature> nature) {
		this.nature = nature;
	}
	public Boolean isAdvertising() {
		return advertising;
	}
	public boolean isWantsNoAd() {  // Useful for EL in JSPs (who does not like Boolean objects)
	    return Boolean.FALSE.equals(advertising);
	}
	public void setAdvertising(Boolean advertising) {
		this.advertising = advertising;
	}
	public Integer getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}
	public Competence getCompetence() {
		return competence;
	}
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	public String getSearchPhrase() {
		return searchPhrase;
	}
	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	
	
}
