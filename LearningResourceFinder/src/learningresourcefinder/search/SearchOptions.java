package learningresourcefinder.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class SearchOptions {

	List<Language> language = new ArrayList<>();
	List<Format> format = new ArrayList<>();
	List<Platform> platform = new ArrayList<>();
	List<Nature> nature = new ArrayList<>();
	boolean advertising;
	Integer maxDuration;
	
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

	public enum Platform{
		IPAD("iPad"), 
		ANDROID("Android"), 
		PC("PC"), 
		MAC("Mac"), 
		BROWSER("Navigateur");
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

	
	
}
