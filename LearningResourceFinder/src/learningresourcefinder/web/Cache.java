package learningresourcefinder.web;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;


// Cache stored in the ServletContext and initialized when the web container starts.
// If you put entities from the DB here, only put thoses that rarely change.
public class Cache implements ServletContextListener {

    List <Cycle> cycles;  // We need to display cycles in the header (menu bar) on each page.

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
}
