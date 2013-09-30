package learningresourcefinder.search;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.Competence;


public class SearchOptions {

	List<Language> language = new ArrayList<>();
	List<Format> format = new ArrayList<>();
	List<Platform> platform = new ArrayList<>();
	List<Nature> nature = new ArrayList<>();
	boolean advertising = true;
	Integer maxDuration;
	String searchPhrase;
	Competence competence; // Search restricted within this competence.
	
	public SearchOptions () {
		getLanguage().add(Language.FR);
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
		BROWSER("Navigateur"),
		ANDROID("Android"), 
		IPAD("iPad/iPhone"), 
	    WIN("Windows"), 
		MAC("Mac"),
		LINUX("Linux");
		private Platform(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}
	
	public enum Nature {
		FORMATIVE("Formatif sans correction"),
		EVALUATIVE("Evaluatif sans correction"), 
		FORMATIVECORR("Formatif avec correction"), 
		EVALUATIVECORR("Evaluatif avec correction");
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
	public boolean isAdvertising() {
		return advertising;
	}
	public void setAdvertising(boolean advertising) {
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
