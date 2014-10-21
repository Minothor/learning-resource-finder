package learningresourcefinder.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import learningresourcefinder.model.Resource;
import learningresourcefinder.util.Logger;
import learningresourcefinder.web.UrlUtil;

import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class ImportService {

	@Logger Log logger;
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getYoutubeInfos(String youTubeVideoUrl) {
    	
    	// We return a JSON result for our Javascript that needs to fill the form with the infos found from YouTube.
    	// {title:"Pitbull - Rain Over Me ft. Marc Anthony", description:"Putbull - Rain..... Featuring...", duration:5, type:"video"} 
    	JSONObject resultJSonObject = new JSONObject(); 
    	
		String youTubeServiceUrl = "https://gdata.youtube.com/feeds/api/videos/"+UrlUtil.getYoutubeVideoId(youTubeVideoUrl)+"?v=2&alt=json"; 

    	try {         

    		// Ask YouTube
    		URL myURL = new URL(youTubeServiceUrl);
    		URLConnection urlConnection = myURL.openConnection();
    		BufferedReader youTubeAnswerReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));                             

    		// User jsonParser to analyze the JSON String from YouTube
    		JSONParser jsonParser = new JSONParser();           
    		String inputLine  = youTubeAnswerReader.readLine();
    		JSONObject rootJsonObject = (JSONObject) jsonParser.parse(inputLine);   // {version:..., entry:..., ...}

    		JSONObject entryJSonObject = (JSONObject) rootJsonObject.get("entry");    	//   entry: {"xmlns$app":..., ..., title:...}
    		
    		JSONObject titleJSonObject = (JSONObject) entryJSonObject.get("title");         //      title:{"$t":"Pitbull - Rain Over Me ft. Marc Anthony"} 
    		String title = (String) titleJSonObject.get("$t");
    			
    		resultJSonObject.put("title", ( title.length()>Resource.MAX_TITLE_LENGTH)?title.substring(0, Resource.MAX_TITLE_LENGTH-1):title);	// output = {title: "Pitbull - Rain Over Me ft. Marc Anthony", ...}

    		
    		JSONObject mediaGroupJSonObject = (JSONObject) entryJSonObject.get("media$group");     // "media$group":{"media$category":[{"$t":"Music","label":"Musique", ....
    		JSONObject mediaDescrJSonObject = (JSONObject) mediaGroupJSonObject.get("media$description");   // "media$description":{"$t":"Music video by Pitbull Featuring ...    
    		resultJSonObject.put("description",(String) mediaDescrJSonObject.get("$t"));
    		
    		mediaDescrJSonObject = (JSONObject) mediaGroupJSonObject.get("yt$duration");
    		String s = (String) mediaDescrJSonObject.get("seconds");															//calculus of numbers of minutes
    		int nbSec= Integer.parseInt(s);
    		resultJSonObject.put("duration", Math.ceil(nbSec/60.));
    		
    		resultJSonObject.put("type", "video");

    		youTubeAnswerReader.close();             
    		return resultJSonObject;

    	} catch (Exception e) {
    		// YouTube is down? It may happen. Ok, we don't return anything interesting.
    		logger.info("Youtube seems down ? - error when collecting data from video - " + youTubeServiceUrl , e);
    		return resultJSonObject;
    	}                
    }
	
}
