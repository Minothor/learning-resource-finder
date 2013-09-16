package learningresourcefinder.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class SearchOptions {

	List<Language> languages = new ArrayList();
	List<Format> formats = new ArrayList();
	List<Platform> platforms = new ArrayList();
	List<Nature> nature = new ArrayList();
	boolean advertising;
	
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
	
	public List<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	public List<Format> getFormats() {
		return formats;
	}
	public void setFormats(List<Format> formats) {
		this.formats = formats;
	}
	public List<Platform> getPlatforms() {
		return platforms;
	}
	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}
	public List<Nature> getNature() {
		return nature;
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
	Integer maxDuration;
	
	
}
