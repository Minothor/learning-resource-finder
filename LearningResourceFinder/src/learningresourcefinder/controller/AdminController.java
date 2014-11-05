package learningresourcefinder.controller;

import java.io.IOException;

import learningresourcefinder.batch.ImportCompetencesFromVraiForumBatch;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.BaseRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ImportLabSetService;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.ReSlugifyService;
import learningresourcefinder.service.crawler.CrawlerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController extends BaseRepository<User> {
	
	@Autowired IndexManagerService indexManagerService;
	@Autowired ImportCompetencesFromVraiForumBatch importCompetencesFromVraiForumBatch;
	@Autowired ImportLabSetService importLabSetService;
	@Autowired ReSlugifyService reslugService;
	@Autowired	CrawlerService crawlerService;
	
	@RequestMapping("/admin")
	public String admin() {
	    SecurityContext.assertUserHasRole(Role.ADMIN);
		return "admin";
    }
    
    @RequestMapping("/createindex")
    public String createIndex(){
        SecurityContext.assertUserHasRole(Role.ADMIN);
    	indexManagerService.removeIndexes();
    	indexManagerService.createIndexes();
		NotificationUtil.addNotificationMessage("Indexes supprimés puis recréés avec succcès", Status.SUCCESS);
		return "admin";  // JSP
    	
    }
    
//    @RequestMapping("/importfromvraisforum")
//    public String importFromVraisForum() {
//    	SecurityContext.assertUserHasRole(Role.ADMIN);
//        importCompetencesFromVraiForumBatch.run();
//        NotificationUtil.addNotificationMessage("Import réussi");
//        return "admin";
//    }
    
//	@RequestMapping("/importMathLabset")
//	public String executeBatchMathLabsetImport() {
//		SecurityContext.assertUserHasRole(Role.ADMIN);
//		importLabSetService.importMaths(); //adds resources and adds the id and url of the image to the image list for processing
//		importLabSetService.processImages(); // runs through the map, then empties it after completion.
//      NotificationUtil.addNotificationMessage("Import réussi");
//		return "admin";
//	}
//	
//	@RequestMapping("/importFrenchLabset")
//	public String executeBatchFrenchLabsetImport() {
//		SecurityContext.assertUserHasRole(Role.ADMIN);
//		importLabSetService.importFrancais(); 
//	//	importLabSetService.processImages();
//      NotificationUtil.addNotificationMessage("Import réussi");
//		return "admin";
//	}
	
    @RequestMapping("/reslugify")
    public String reslugify(){
    	SecurityContext.assertUserHasRole(Role.ADMIN);
    	reslugService.reslugResources();
    	reslugService.reslugCycles();
    	reslugService.reslugPlaylists();
    	NotificationUtil.addNotificationMessage("Regeneration des Slugs réussi");
    	return "admin";
    }

    @RequestMapping("/crawler/{pageName}")
    public String crawler(@PathVariable("pageName") String pageName) throws ParseException 
    {
        SecurityContext.assertUserHasRole(Role.ADMIN);      
        try {
            crawlerService.crawlerPage(pageName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NotificationUtil.addNotificationMessage("Crawler "+pageName+" réussi");
        return createIndex();
    }
    
    @RequestMapping("/crawler67/{num}")
    public String crawler67(@PathVariable("num") int num) throws ParseException 
    {
        SecurityContext.assertUserHasRole(Role.ADMIN);      
        try
        {
            crawlerService.crawler67(num);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException(e);
        }
        NotificationUtil.addNotificationMessage("Crawler Soutien 67("+num+") réussi");
        return createIndex();
    }
	
	@RequestMapping("/crawlerkhanacademy/{num}")
    public String crawlerKhanAcademy(@PathVariable("num") int num) throws ParseException    {
        SecurityContext.assertUserHasRole(Role.ADMIN);      
        try
        {
            crawlerService.crawlerPageKhanAcademy(num);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException(e);
        }
        NotificationUtil.addNotificationMessage("Crawler Khan Academy("+num+") réussi");
        return createIndex();
    }
    
}