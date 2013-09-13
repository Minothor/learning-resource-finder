package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
public class CycleListController extends BaseController<BaseEntity> {
@Autowired CycleRepository cyclerepository;

@RequestMapping("/cyclelist")
public ModelAndView showCycleList() {
    ModelAndView mv = new ModelAndView("cyclelist"); 
    List<Cycle> list=cyclerepository.findAllCycles();
    mv.addObject("cyclelist", list);
    return mv;
        }


}



