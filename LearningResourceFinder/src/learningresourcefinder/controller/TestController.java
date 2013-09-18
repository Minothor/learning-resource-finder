package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.util.NotificationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class TestController extends BaseController<BaseEntity>{
    @Autowired CycleRepository cycleRepository;
    
    @RequestMapping("/test")
    private ModelAndView test() {
         
        return new ModelAndView("test");
    }
    

    @RequestMapping("/test/addMsg")
    private ModelAndView addMsg() {
        NotificationUtil.addNotificationMessage("Ceci est un message.");
        return new ModelAndView("test");
    }
    
}
