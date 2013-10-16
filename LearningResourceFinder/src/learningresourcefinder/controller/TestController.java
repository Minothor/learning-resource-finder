package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.service.CompetenceNodeService;
import learningresourcefinder.util.CompetenceNode;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class TestController extends BaseController<BaseEntity>{
    @Autowired CycleRepository cycleRepository;
    @Autowired CompetenceRepository competenceRepository;
    @Autowired CompetenceNodeService competenceNodeService;
    
    @RequestMapping("/test")
    public ModelAndView test() {
        Competence competence = competenceRepository.find(12L);
        return new ModelAndView("test", "competence", competence);
    }
    

    @RequestMapping("/test/addMsg")
    public ModelAndView addMsg() {
        NotificationUtil.addNotificationMessage("Ceci est un message.");
        return new ModelAndView("test");
    }
 
}
