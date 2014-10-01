package learningresourcefinder.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.service.CompetenceService;


// Cache stored in the ServletContext and initialized when the web container starts.
// If you put entities from the DB here, only put thoses that rarely change.
public class Cache implements ServletContextListener {

    List <Cycle> cycles;  // We need to display cycles in the header (menu bar) on each page.
    
    Map<Long, List<Competence>> computedCategoriesByCycles = new HashMap<>();
    
    Date computedCompetencesDate;

    Format[] format = SearchOptions.Format.values();  // We need them in the addResourceform includes in the header (via an included jsp) => we have no controller.
    Platform[] platform= SearchOptions.Platform.values();
    Nature[] nature = SearchOptions.Nature.values();
    Language[] language=SearchOptions.Language.values(); 

    public Nature[] getNature() {
        return nature;
    }

    public void setNature(Nature[] nature) {
        this.nature = nature;
    }

    Topic[] topic = Resource.Topic.values();


    public Topic[] getTopic() {
        return topic;
    }

    public void setTopic(Topic[] topic) {
        this.topic = topic;
    }

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

    public Format[] getFormat() {
        return format;
    }

    public void setFormat(Format[] format) {
        this.format = format;
    }

    public Platform[] getPlatform() {
        return platform;
    }

    public void setPlatform(Platform[] platform) {
        this.platform = platform;
    }

    public Language[] getLanguage() {
        return language;
    }

    public void setLanguage(Language[] language) {
        this.language = language;
    }

    public synchronized List<Competence> getComputedCategoriesByCycles(Long id) {
        List<Competence> result = null;
        
        // Calculate time difference since last call to this method.
        int timeLaps = (int) ((computedCompetencesDate != null) ? (new Date().getTime() - computedCompetencesDate.getTime()) / (60*60*1000) : 0);
        
        result = computedCategoriesByCycles.get(id);

        // If map does not contain competences for this cycle Id or Date is NULL or last access done more than 1 hour ago
        if ((result == null || (computedCompetencesDate == null) || (timeLaps >= 1))) {
            // (Re)initialize with current date/time
            computedCompetencesDate = new Date(); 
            
            // Get all categories attached to resources part of current cycle
            CompetenceRepository competenceRepository = ContextUtil.getSpringBean(CompetenceRepository.class);
            List<Competence> finalCompetences = competenceRepository.getCompetencesByCycle(id);
            
            // Add parents to this list of categories
            CompetenceService competenceService = ContextUtil.getSpringBean(CompetenceService.class);
            result = competenceService.computeAllCompetencesRelatedToCycle(id, finalCompetences);
            
            // Save the cycle Id and the list of categories into our "cache" variable
            computedCategoriesByCycles.put(id, result);
        }
        return result;
    }

}
