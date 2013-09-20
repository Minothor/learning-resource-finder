package learningresourcefinder.web;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;


// Cache stored in the ServletContext and initialized when the web container starts.
// If you put entities from the DB here, only put thoses who rarely change.
public class Cache implements ServletContextListener {

    List <Cycle> cycles;  // We need to display cycles in the header (menu bar) on each page.
   

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
     //empty
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        fillCacheFromDB(); 
        sce.getServletContext().setAttribute("cache", this); 
    }
    
    public void fillCacheFromDB(){
       CycleRepository cycleRepository = ContextUtil.getSpringBean(CycleRepository.class);
       cycles = cycleRepository.findAllCycles();
       
    }
    
    public static Cache getInstance(){
        Cache instance = (Cache) ContextUtil.getServletContext().getAttribute("cache");
        if (instance == null) {  // defensive coding
            throw new RuntimeException("Cache not initialized. Has it been added in the web.xml as ServletContextListener?");
        }
        return instance;
    }
    public List<Cycle> getCycles() {
        return cycles;
    }

    
}
