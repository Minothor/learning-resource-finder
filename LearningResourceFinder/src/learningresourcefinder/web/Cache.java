package learningresourcefinder.web;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CycleRepository;


// Cache accessible from the ServletContext and initialized when the web container starts.
public class Cache implements ServletContextListener {

    List <Cycle> cycles;  // We need to display cycles in the header (menu bar) on each page.
    
    
   

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
     //empty
        
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        fillCacheFromDB(); 
        arg0.getServletContext().setAttribute("cache", this); 
        
        
    }
    
    public void fillCacheFromDB(){
       CycleRepository cyclerepository = ContextUtil.getSpringBean(CycleRepository.class);
       cycles = cyclerepository.findAllCycles(); 
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
