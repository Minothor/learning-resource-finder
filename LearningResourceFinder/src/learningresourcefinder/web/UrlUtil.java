package learningresourcefinder.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import learningresourcefinder.model.Resource;
import learningresourcefinder.util.CurrentEnvironment;

public class UrlUtil {

    private static String PROD_ABSOLUTE_DOMAIN_NAME = null;
    public final static String DEV_ABSOLUTE_DOMAIN_NAME = "localhost:8080";
    private static String WebSiteName = null;;
    private static String version = null;
    
    
    //Send the Software Version (Value is in the config.properties)
    public static String getVersion(){
        if (version==null){
            version= ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getVersion();
        }
        return version;
    }
    //Send the Domain Name (x.y.com) (Value is in the config.properties)
    public static String  getProdAbsoluteDomainName(){
        if (PROD_ABSOLUTE_DOMAIN_NAME==null) {
            PROD_ABSOLUTE_DOMAIN_NAME = ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getSiteAddress();
        }
        return PROD_ABSOLUTE_DOMAIN_NAME;
    }
    //Send the Project Name (Value is in the config.properties)
    public static String  getWebSiteName(){
        if(WebSiteName==null){
            WebSiteName =   ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getSiteName();
        }
        return WebSiteName;
    }

    // Usually for images.
    public static String getAbsoluteUrl(String path){
        return getAbsoluteUrl(path, null);
    }

   
  
	/** Returns the domain name for use in the cookies.
	 */
	public static String getCookieDomainName() {
		switch(ContextUtil.getEnvironment()) {
			case DEV  : return ".localhost.local" + ContextUtil.getRealContextPath()+"/"; //Domain name need two dots to be valid so we have to write 127.0.0.1 or .localhost.local (just "localhost" will not work).
		//	case TEST : return TEST_ABSOLUTE_DOMAIN_NAME;
			case PROD : return getProdAbsoluteDomainName();
		}
		throw new RuntimeException("Unknown Environement");
	}
	
	public static enum  Mode {DEV, PROD}; 
	
	
    public static String getAbsoluteUrl(String path, Mode forceMode){
        if(path.startsWith("/")){ //We need to normalize the incoming string. we remove the first / in the path if there is one.
            path = path.substring(1); 
        }
        String result;
        Mode resultType = forceMode;
        if (resultType == null) {
            if (ContextUtil.devMode && !ContextUtil.isInBatchNonWebMode()) {
                resultType = Mode.DEV;
            } else {
                resultType = Mode.PROD;
            }
        }
        if (resultType == Mode.PROD) {
            // In dev, it should be "http://ryc.be/"  (because images are not loaded on developers machines).
        	
            result = "http://" + getProdAbsoluteDomainName()        + "/"; // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://  + toujoursPlus.be                    +  /
            
        } else {  // We are in dev and we don't force prod urls.
            result = "http://"
                + DEV_ABSOLUTE_DOMAIN_NAME    // localhost:8080
                + "/";  // The "/" is crucial at the end of the project name. Without it, you loose the session.
        }
        result += path;    //   mypage?params
        return result;
    }

    public static String getRelativeUrl(String path){
        return  ContextUtil.getRealContextPath() + path;
    }
    
    //return a valid url, ex  http://test for image.png    to    http://test%20for%20image.png
    public static String getUrlWithNoBlank(String url){
    	try {
			String validUrl = new URI(null, null, url).toString();
			return validUrl.substring(1);// remove the '#' before the string (that is automatically added by URI
		} catch (URISyntaxException e) {
			throw new RuntimeException("invalid URL : "+e);
		}
    }

    
    // The method InetAddress.getByName().isReachable() doesnt't always return true when the website is reachable,
    // for example when the http response is a redirect. see post on StackOverflow :
    // http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
    public static boolean isUrlValid(String outUrl) {
		if (outUrl.startsWith("http://") || outUrl.startsWith("https://") ) {
			try {
				return (InetAddress.getByName(new URL(outUrl).getHost())
						.isReachable(2000));
			} catch (IOException e) {
				return false;
			}

		} else {
			try {
				return (InetAddress.getByName(outUrl).isReachable(2000));
			} catch (IOException e) {
				return false;
			}

		}
	}
    
    /** nameParam is something like "Java & OO - Fundamentals". The urlFragment is set to "Java_OO_Fundamentals" */ 
    public static String computeUrlFragmentFromName(String nameParam) {
    	nameParam.trim();
    	nameParam = nameParam.replaceAll("é", "e");
    	nameParam = nameParam.replaceAll("è", "e");
    	nameParam = nameParam.replaceAll("ê", "e");
    	nameParam = nameParam.replaceAll("ë", "e");
    	nameParam = nameParam.replaceAll("ï", "i");
    	nameParam = nameParam.replaceAll("î", "i");
    	nameParam = nameParam.replaceAll("à", "a");
    	nameParam = nameParam.replaceAll("ç", "c");
    	nameParam = nameParam.replaceAll("ù", "u");
        nameParam = nameParam.replaceAll("[^A-Za-z0-9]", "_"); // remove all non alphanumeric values (blanks, spaces,...).
    	nameParam = nameParam.replaceAll("___", "_"); //Sometimes, there is a '&' in the title. The name is compute like : Java___OO_etc. Whith this method, the name will be Java_OO_etc.
        return nameParam;
    }
    
    
	public static String getRelativeUrlToResourceDisplay(Resource resource) {
		return "/resource/" + resource.getShortId() + "/" + resource.getSlug();
	}
	
    
    // From there: http://androidsnippets.wordpress.com/2012/10/11/how-to-get-extract-video-id-from-an-youtube-url-in-android-java/
    // Return null if no vido id found (probably not a youtube url)
    public static String getYoutubeVideoId(String youtubeUrl)  {
        String video_id=null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.startsWith("http")
                && (youtubeUrl.contains("youtube") || youtubeUrl.contains("youtu.be")))  {

            String expression = "^.*((youtu.be"+ "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if(groupIndex1!=null && groupIndex1.length()==11)
                    video_id = groupIndex1;
            }
        }
        return video_id;
    }
}
