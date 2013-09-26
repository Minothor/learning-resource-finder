package learningresourcefinder.controller;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class TestController extends BaseController<BaseEntity>{
    @Autowired CycleRepository cycleRepository;
    @Autowired CompetenceRepository competenceRepository;
    
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
