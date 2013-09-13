package learningresourcefinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
@Controller
public class CycleDisplayController extends BaseController<Cycle> {
    @Autowired CycleRepository cycleRepositoryy;

    @RequestMapping("/cycle")
    public ModelAndView displayCycle(@RequestParam("id") long id) {
        
        Cycle cycle=(Cycle)getRequiredEntity(id, Cycle.class);
        return new ModelAndView("cycledisplay",
                "cycle", cycle);
    }
}
