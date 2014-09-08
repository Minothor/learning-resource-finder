package learningresourcefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import learningresourcefinder.batch.ImportCompetencesFromVraiForumBatch;
import learningresourcefinder.model.User;
import learningresourcefinder.model.User.Role;
import learningresourcefinder.repository.BaseRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ImportLabSetService;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;

@Controller
public class AdminController extends BaseRepository<User> {
	
	@Autowired IndexManagerService indexManagerService;
	@Autowired ImportCompetencesFromVraiForumBatch importCompetencesFromVraiForumBatch;
	@Autowired ImportLabSetService importLabSetService;
	
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
    
    @RequestMapping("/importfromvraisforum")
    public String importFromVraisForum() {
    	SecurityContext.assertUserHasRole(Role.ADMIN);
        importCompetencesFromVraiForumBatch.run();
        NotificationUtil.addNotificationMessage("Import réussi");
        return "admin";
    }
    
	@RequestMapping("/importMathLabset")
	public String executeBatchMathLabsetImport() {
		SecurityContext.assertUserHasRole(Role.ADMIN);
		importLabSetService.importMaths(); //adds resources and adds the id and url of the image to the image list for processing
		importLabSetService.processImages(); // runs through the map, then empties it after completion.
        NotificationUtil.addNotificationMessage("Import réussi");
		return "admin";
	}
	
	@RequestMapping("/importFrenchLabset")
	public String executeBatchFrenchLabsetImport() {
		SecurityContext.assertUserHasRole(Role.ADMIN);
		importLabSetService.importFrancais(); 
	//	importLabSetService.processImages();
        NotificationUtil.addNotificationMessage("Import réussi");
		return "admin";
	}
    
}