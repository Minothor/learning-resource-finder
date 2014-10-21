package learningresourcefinder.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import learningresourcefinder.util.Logger;

import org.apache.commons.logging.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class TestJsonController {
    @Logger Log log;
    
    @RequestMapping(value="/testyoutube")
    public ModelAndView testyoutubejson() {
        
        JSONObject resultJSonObject = new JSONObject();
        ModelAndView testjson = new ModelAndView("testYoutubeJson");
        try {         
            // Ask YouTube
            URL myURL = new URL("https://gdata.youtube.com/feeds/api/videos/5zwMcizMB7w?v=2&alt=json");
            URLConnection urlConnection = myURL.openConnection();
            BufferedReader youTubeAnswerReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            
            JSONParser jsonParser = new JSONParser();           
            String inputLine  = youTubeAnswerReader.readLine();
            
            JSONObject rootJsonObject = (JSONObject) jsonParser.parse(inputLine);
            JSONObject entryJSonObject = (JSONObject) rootJsonObject.get("entry");            
            JSONObject titleJSonObject = (JSONObject) entryJSonObject.get("title");
            String title = (String) titleJSonObject.get("$t");
            
            testjson.addObject("title", title);
            return testjson;
            
        }catch (Exception e) {
            // YouTube is down? It may happen. Ok, we don't return anything interesting.
            log.info("Youtube seems down ? - error when collecting data from video - " , e);
            return testjson;
        }
    }
}
