package learningresourcefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.BaseRepository;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;

@Controller
public class AdminController extends BaseRepository<User> {
	
	@ Autowired IndexManagerService indexManagerService;
   
	@RequestMapping("/admin")
	public String admin() {
		return "admin";
    }
    
    @RequestMapping("/createindex")
    public String createIndex(){
    	indexManagerService.removeIndexes();
    	indexManagerService.createIndexes();
		NotificationUtil.addNotificationMessage("Inexes supprimés puis recréés avec succcès", Status.SUCCESS);
		return "admin";  // JSP
    	
    }
}