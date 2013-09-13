package learningresourcefinder.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class SearchOptions {

	List<String> languages = new ArrayList();
	List<String> formats = new ArrayList();
	List<String> platforms = new ArrayList();
	List<String> nature = new ArrayList();
	boolean advertising;
	
	public enum Languages{
		FR("Français"), 
		NL("Néerlandais"), 
		DE("Allemand"), 
		EN("Anglais"), 
		RU("Russe");
		private Languages(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}
	
	public enum Formats{
		VIDEOS("Vidéo"),
		DOC("Document"), 
		INTERACTIVE("Interactif"), 
		AUDIO("Audio");
		private Formats(String description){this.description = description;}
		private final String description;
		public String getDescription(){return description;}
	}

	public enum Platforms{
		IPAD("iPad"), 
		ANDROID("Android"), 
		PC("PC"), 
		MAC("Mac"), 
		BROWSER("Navigateur");
		private Platforms(String description){this.description = description;}
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
	
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public List<String> getFormats() {
		return formats;
	}
	public void setFormats(List<String> formats) {
		this.formats = formats;
	}
	public List<String> getPlatforms() {
		return platforms;
	}
	public void setPlatforms(List<String> platforms) {
		this.platforms = platforms;
	}
	public List<String> getNature() {
		return nature;
	}
	public void setNature(List<String> nature) {
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
