package learningresourcefinder.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class UrlUtils {
	public static String toPrettyURL(String string) {
	    String clean= Normalizer.normalize(string.toLowerCase(), Form.NFD)
	    	.replaceAll("les", "")
	    	.replaceAll("de", "")
	    	.replaceAll("la", "")
	    	.replaceAll("un", "")
	    	.replaceAll("une", "")
	        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	        .replaceAll("[^\\p{Alnum}]+", "-")
	        .replaceAll("^-", "")
	        .replaceAll("-$", "");
	    return clean;
	}
}
