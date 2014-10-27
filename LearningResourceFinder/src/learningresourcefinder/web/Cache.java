package learningresourcefinder.web;

import java.util.ArrayList;
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
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.service.CompetenceService;
import learningresourcefinder.service.SiteMapService;
import learningresourcefinder.service.SiteMapService.SiteMap;


// Cache stored in the ServletContext and initialized when the web container starts.
// If you put entities from the DB here, only put thoses that rarely change.
public class Cache implements ServletContextListener {

	List <Cycle> cycles;  // We need to display cycles in the header (menu bar) on each page.

	List<Long> idCyclesInDb = new ArrayList<>(); // We use this for the slider

	Map<Long, List<Competence>> computedCategoriesByCycle = new HashMap<>();

	Map<Long, List <Resource>> computedTopResourcesByCycle = new HashMap<>();

	List <String> searchAutocompleteMasterStrings = new ArrayList<String>();

	Map<String,String> siteMapXmlStrings = new HashMap<>();  // Key = name of the sitemap enum (.../sitemap/category, ...)  Value = XML data

	Date computedCacheDate;
	boolean cacheShouldBeUpdated = true;  // Is the cache valid ?

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
		// fillCacheFromDB(); We don't do that because filling the DB may require the application (Spring) to be fully started. => fillCacheFromDB will be called lazily. 

		sce.getServletContext().setAttribute("cache", this); 
	}

	public static Cache getInstance(){
		Cache instance = (Cache) ContextUtil.getServletContext().getAttribute("cache");
		if (instance == null) {  // defensive coding
			throw new RuntimeException("Cache not initialized. Has it been added in the web.xml as ServletContextListener?");
		}
		return instance;
	}
	public List<Cycle> getCycles() {
	    updateCacheIfTooOld();
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

	/**
	 * Checks if Cached Sitemap is not Null, setting it to a freshly generated sitemap if it is.
	 * @return the Cached sitemap xml data.
	 */
	public String getSiteMapXml(SiteMap targetMap){ return getSiteMapXml(targetMap,0);}
	public String getSiteMapXml(SiteMap targetMap, int mapNum){
		updateCacheIfTooOld();
		if(siteMapXmlStrings.isEmpty() || !siteMapXmlStrings.containsKey(targetMap.name()+mapNum)) {
			SiteMapService siteMapService = ContextUtil.getSpringBean(SiteMapService.class);
			siteMapXmlStrings.put(targetMap.name()+mapNum, siteMapService.generateSiteMap(targetMap,mapNum));

		}
		return siteMapXmlStrings.get(targetMap.name()+mapNum);
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
		updateCacheIfTooOld();
		return computedCategoriesByCycle.get(id);
	}

	public synchronized void updateCacheIfTooOld() {
		// Calculate time difference since last call to this method.
		int timeLaps = (int) ((computedCacheDate != null) ? (new Date().getTime() - computedCacheDate.getTime()) / (60*60*1000) : 0);

		// If map does not contain competences for this cycle Id or Date is NULL or last access done more than 1 hour ago
		if (cycles == null || cacheShouldBeUpdated || timeLaps >= 1) {
			fillCacheFromDB();
		}
	}



	public synchronized void fillCacheFromDB() {
		CycleRepository cycleRepository = ContextUtil.getSpringBean(CycleRepository.class);
		cycles = cycleRepository.findAllCycles();
		ResourceRepository resourceRepository = ContextUtil.getSpringBean(ResourceRepository.class);
		CompetenceRepository competenceRepository = ContextUtil.getSpringBean(CompetenceRepository.class);

		idCyclesInDb.clear();

		for(Cycle cycle : cycles) {

			idCyclesInDb.add(cycle.getId());

			/////// 1. computedCategoriesByCycle
			// Get all categories attached to resources part of current cycle
			List<Competence> finalCompetences = competenceRepository.getCompetencesByCycle(cycle.getId());

			// Add parents to this list of categories
			CompetenceService competenceService = ContextUtil.getSpringBean(CompetenceService.class);
			List<Competence>computedCategories = competenceService.computeAllCompetencesRelatedToCycle(cycle.getId(), finalCompetences);

			// Save the cycle Id and the list of categories into our "cache" variable
			computedCategoriesByCycle.put(cycle.getId(), computedCategories);

			////// 2. topResourcesByCycle
			//get top resource by cycle

			List <Resource> computedTopResources = resourceRepository.findTop5ResourcesByCycleAndPopularity(cycle);   
			// Save the cycle Id and the list of top rated resources for each cycle into our "cache" variable
			computedTopResourcesByCycle.put(cycle.getId(), computedTopResources);   

		}

		/////// 3. Autocomplete
		for (Topic topic : Topic.values()){
			searchAutocompleteMasterStrings.add(topic.getDescription());
		}
		searchAutocompleteMasterStrings.addAll(competenceRepository.getAllcategoryName());
		searchAutocompleteMasterStrings.addAll(resourceRepository.findAllResourceName());


		////////// call method on service class to fill siteMapXmlString
		siteMapXmlStrings.clear();
		SiteMapService siteMapService = ContextUtil.getSpringBean(SiteMapService.class);
		for (SiteMap map : SiteMap.values()) {

			//splits resources
			if(map.equals(SiteMap.RESOURCES)){
				int numMaps = siteMapService.getNumResourceMaps();
				for (int i=0; i<=numMaps;i++){
					siteMapXmlStrings.put(map.name()+i, siteMapService.generateSiteMap(map,i));
				}
			} else {
				siteMapXmlStrings.put(map.name()+0, siteMapService.generateSiteMap(map));
			}
		}



		// (Re)initialize with current date/time
		computedCacheDate = new Date(); 
		cacheShouldBeUpdated = false;
	}

	public List<Long> getIdCyclesInDb() {
		return idCyclesInDb;
	}

	public synchronized List<Resource> getComputedTopResourcesByCycle(Long id) {
		updateCacheIfTooOld();

		return computedTopResourcesByCycle.get(id);
	}


	public synchronized void resourceChanged(Resource r) {
		if (cycles == null) {   // Cache never filled from DB yet.
			return;
		}
		// If that resource is in the cache, then update the whole cache.
		for (Cycle cycle : cycles){ 
			List <Resource> computedTopResources = computedTopResourcesByCycle.get(cycle.getId());
			if (computedTopResources != null && computedTopResources.contains(r)) {
				cacheShouldBeUpdated = true;
				return;
			}

		}
	}

	public synchronized List<String> getAutocompleteSuggestions(String prefix) {
		updateCacheIfTooOld();
		List <String> autocompleteSuggestionsList = new ArrayList<>();
		for (String searchAutocompleteMasterString : searchAutocompleteMasterStrings) {
			if (searchAutocompleteMasterString.toLowerCase().startsWith(prefix.toLowerCase())){
				if(!autocompleteSuggestionsList.contains(searchAutocompleteMasterString)){
					autocompleteSuggestionsList.add(searchAutocompleteMasterString);
				}
			}
			if ( autocompleteSuggestionsList.size() >= 5){
				break;
			}
		}

		return autocompleteSuggestionsList;
	}
}
