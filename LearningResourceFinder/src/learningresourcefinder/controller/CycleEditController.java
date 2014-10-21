package learningresourcefinder.controller;

import javax.validation.Valid;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.HTMLUtil;
import learningresourcefinder.web.Cache;
import learningresourcefinder.web.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CycleEditController extends BaseController<Cycle> {
    @Autowired CycleRepository cycleRepository;

    @RequestMapping("/cyclecreate")
    public ModelAndView cycleCreate() {
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_COMPETENCE);
        return prepareModelAndView(new Cycle());
    }
    @RequestMapping("/cycleedit")
    public ModelAndView cycleEdit(@RequestParam("id") long id){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_COMPETENCE);
        Cycle cycle=(Cycle)getRequiredEntity(id,Cycle.class);
        return prepareModelAndView(cycle);
    }

    private ModelAndView prepareModelAndView(Cycle cycle) {
        ModelAndView mv = new ModelAndView("cycleedit");  // JSP
        mv.addObject("cycle", cycle); 
        return mv;
    }
 
    @RequestMapping("/cycleeditsubmit")
    public ModelAndView cycleEditSubmit(@Valid @ModelAttribute Cycle cycle, BindingResult bindingResult){
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_COMPETENCE);

        if (bindingResult.hasErrors()) { // If spring has detected validation errors, we redisplay the form.
            return new ModelAndView ("cycleedit", "cycle", cycle);

        }

        Cycle cycleHavingThatName=(Cycle) cycleRepository.findByName(cycle.getName());
        
        // Set the slug based on the (maybe changed) title
        String slug = Slugify.slugify(HTMLUtil.removeHtmlTags(cycle.getDescription()));
        cycle.setSlug(slug);

        if(cycle.getId()==null){
            if(cycleHavingThatName!=null){
                return otherCycleError(cycle, cycleHavingThatName, bindingResult);

            }

            cycleRepository.persist(cycle);
        }

        else { // Edited cycle instance.
            if(cycleHavingThatName!=null && !cycle.equals(cycleHavingThatName)){
                return otherCycleError(cycle, cycleHavingThatName, bindingResult);
            }

            cycleRepository.merge(cycle);

        }
        Cache.getInstance().fillCacheFromDB();
        return new ModelAndView("redirect:/cycle/"+cycle.getId()+"/"+cycle.getSlug());
    }

    private ModelAndView otherCycleError(Cycle cycle, Cycle otherCycle, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView ("cycleedit", "cycle", cycle);
        bindingResult.rejectValue("name", "", "Un autre cycle a déjà ce nom '" + cycle.getName() + "'");
        System.out.println("Un autre cycle a déjà ce nom '" + cycle.getName() + "'");
        return mv;
    }

    @ModelAttribute
    public Cycle findCycle(@RequestParam(value="id", required=false)Long id){
        if (id==null){
            return new Cycle();
        } else{
            return (Cycle)getRequiredDetachedEntity(id);
        }
    }

}