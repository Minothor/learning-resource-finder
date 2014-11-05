package learningresourcefinder.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.web.ContextUtil;
import learningresourcefinder.web.UrlUtil;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to dynamically generate the ToujoursPlus sitemap.xml file.
 * @author NJ Boyd
 * @version 1.0
 * @approved John Rizzo.
 * Sorry about the mess. Had to borrow some code from the Cache functions since avoiding sync conflicts proved.. tricky at best.
 */
@Service
@Transactional
public class SiteMapService {
	static DocumentFactory docFactory = DocumentFactory.getInstance();
	@Autowired CompetenceRepository competenceRepository;
	@Autowired CycleRepository cycleRepository;
	@Autowired ResourceRepository resourceRepository;

	@PersistenceContext
	EntityManager em;
	private int resNumMaps=-1;

	public enum SiteMap{
		INDEX(.0F),
		MAIN(.0F),
		CYCLES(.1F),
		CATEGORIES(.2F),
		RESOURCES(.0F);

		private float depth;
		SiteMap(float depth){ this.depth = depth;}

		public float getDepth() {return depth;}
	}

	/**
	 * Refreshes the site map from the database
	 * @param targetMap Indentifying string for required Sitemap.
	 * @param depth Optional, used to divide maps into sub maps of 500,000 urls
	 * @return Returns the newly generated site map for immediate display.
	 */
	public String generateSiteMap(SiteMap targetMap){return generateSiteMap(targetMap, 0);}
	public String generateSiteMap(SiteMap targetMap, int mapNum){
		Document doc = docFactory.createDocument();

		switch(targetMap){
		case CYCLES:
			generateCycleMap(doc);
			break;
		case CATEGORIES:
			generateCategoryMap(doc);
			break;
		case RESOURCES:
			generateResourceMap(doc, mapNum);
			break;
		case MAIN:
			generateMainMap(doc);
			break;
		default: generateIndex(doc);
		}

		return doc.asXML();
	}

	/**
	 * Generates the primary map from a string of URLS, index[0] is the root url,
	 * the subsequent ones follow after cycles have been added in order to mimic the layout of the menu bar.
	 * @param doc the xml document to write to
	 */
	private void generateMainMap(Document doc) {
		String[] urls =  {"/","/playlist/all","/resourcelist","/competencetree"};
		String[] landingPageurls = {"/corps-humain", "/exercices-de-math", "/neerlandais", "/anglais-facile", "/conjugaison", "/orthographe", "/ecole-virtuelle", "/nederlands", "/subjonctif", "/ligne-du-temps", "/botanique"}; 

		Element rootElement = generateRootElement(doc);

		Element tempElem;
		Float urlDepth = .0F;
		tempElem = rootElement.addElement("url");
		tempElem.addElement("loc").setText("http://"+getUrlRoot()+urls[0]);
		populateBasicAttributes(tempElem, urlDepth);
		

		urlDepth=.1F;

		for(int i=1;i<urls.length;i++){
			tempElem = rootElement.addElement("url");
			tempElem.addElement("loc").setText("http://"+getUrlRoot()+urls[i]);
			populateBasicAttributes(tempElem, urlDepth);
			
			if(urls[i].equalsIgnoreCase("/resourcelist")){
				//fill out landing pages under resources
				urlDepth = .2F;
				for (String landingURL : landingPageurls) {
					tempElem = rootElement.addElement("url");
					tempElem.addElement("loc").setText("http://"+getUrlRoot()+landingURL);
					populateBasicAttributes(tempElem, urlDepth);
				}
				urlDepth=.1F;
			}
			
			if(urls[i].equalsIgnoreCase("/competencetree")){
				//fill out cycles under categories
				urlDepth = .2F;

				List<Cycle> cycles =  cycleRepository.findAllCycles();

				for (Cycle child_node : cycles) {
					tempElem = rootElement.addElement("url");
					tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/cycle/"+child_node.getId()+"/"+child_node.getSlug());
					populateBasicAttributes(tempElem, urlDepth);
				}
				urlDepth = .1F;
			}
		}

	}

	/**
	 * Generates a map of all validated resources in the database, the query results are subdivided into groups of 500k in order to fit sitemap size restrictions
	 * @param doc the xml document to write to
	 * @param mapNum index for group of 500k urls (i.e. 0 => resources 0->499999; 1 => resources 500000->999999; etc)
	 */
	private void generateResourceMap(Document doc, int mapNum) {
		Element rootElement = generateRootElement(doc);

		Element tempElem;
		Float urlDepth = .0F;
		int startID = (499999*mapNum);
		String queryString = "SELECT r FROM Resource r WHERE r.validationStatus = 'ACCEPT' ORDER BY r.id ASC";


		Query query = em.createQuery(queryString);

		@SuppressWarnings("unchecked")
		List<Resource> result = query
		.setFirstResult(startID)
		.setMaxResults(500000)
		.getResultList();

		for (Resource resource : result) {
			tempElem = rootElement.addElement("url");
			tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/resource/"+resource.getShortId()+"/"+resource.getSlug());
			populateBasicAttributes(tempElem, urlDepth);
		}

	}
	/**
	 * Generates the Sitemap index from the ENUM of types contained within this class,
	 * Resources are automatically subdivided into groups of 500k
	 * @param doc the xml document to write to
	 */
	private void generateIndex(Document doc) {

		Element rootElement = doc.addElement("sitemapindex", "http://www.sitemaps.org/schemas/sitemap/0.9");
		rootElement.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xsi:schemaLocation","http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd");
		doc.setRootElement(rootElement);

		Element tempElem;
		for (SiteMap map : SiteMap.values()) {
			if(map!=SiteMap.INDEX && map!=SiteMap.RESOURCES){
				tempElem = rootElement.addElement("sitemap");
				tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/sitemap/"+map.name().toLowerCase(Locale.US));
				tempElem.addElement("lastmod").setText(DateUtil.formatyyyyMMdd(new Date()));
			}
			if(map==SiteMap.RESOURCES){
				for (int i=0; i<=getNumResourceMaps();i++){
					tempElem = rootElement.addElement("sitemap");
					tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/sitemap/"+map.name().toLowerCase(Locale.US)+"/"+i);
					tempElem.addElement("lastmod").setText(DateUtil.formatyyyyMMdd(new Date()));
				}
			}
		}


	}

	/**
	 * Generates the Cycles map and populates the category tree for each cycle.
	 * borrows code from Cache due to "context not initialised" errors
	 * @param doc the xml document to write to
	 */
	private void generateCycleMap(Document doc){
		Element rootElement = generateRootElement(doc);

		Element tempElem;
		Float urlDepth = .0F;

		List<Cycle> cycles =  cycleRepository.findAllCycles();

		for (Cycle child_node : cycles) {
			tempElem = rootElement.addElement("url");
			tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/cycle/"+child_node.getId()+"/"+child_node.getSlug());
			populateBasicAttributes(tempElem, urlDepth);

			//*****Start of code borrowed from Cache.java*****//
			/////// 1. computedCategoriesByCycle
			// Get all categories attached to resources part of current cycle
			List<Competence> finalCompetences = competenceRepository.getCompetencesByCycle(child_node.getId());

			// Add parents to this list of categories
			CompetenceService competenceService = ContextUtil.getSpringBean(CompetenceService.class);
			List<Competence>computedCategories = competenceService.computeAllCompetencesRelatedToCycle(child_node.getId(), finalCompetences);
			//******End of code borrowed from Cache.java******//

			for (Competence sub_child : computedCategories) {
				populateChildren(sub_child, rootElement, urlDepth+.1F);
			}
		}

	}

	/**
	 * Generates the Category map, recursively parsing the tree in order to replicate the structure visible on the "/competencetree" page
	 * @param doc the xml document to write to
	 */
	private void generateCategoryMap(Document doc){
		Element rootElement = generateRootElement(doc);

		Element tempElem;
		Float urlDepth = .0F;

		tempElem = rootElement.addElement("url");
		tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/competencetree");
		populateBasicAttributes(tempElem, urlDepth);

		urlDepth = .1F;
		Competence rootCompetence = competenceRepository.findRoot();
		for (Competence child_node : rootCompetence.getChildren()) {
			populateChildren(child_node, rootElement, urlDepth);
		}

	}

	/**
	 * Boilerplate function to create the root element and apply it's attributes.
	 * Used by all map generator functions with the exception of generateIndex which uses "sitemapindex" instead of "urlset"
	 * @param doc the document to create the element on.
	 * @return the root element of the document
	 */
	private Element generateRootElement(Document doc) {
		Element rootElement = doc.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
		rootElement.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute("xsi:schemaLocation","http://www.google.com/schemas/sitemap/0.84 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
		doc.setRootElement(rootElement);
		return rootElement;
	}

	/**
	 * Boilerplate function that recursively populates the root element with the children of a given node
	 * @param child_node the node to parse for children
	 * @param rootElement the root element of the document, sitemaps are effectively "flat" with all nodes as children of the root
	 * @param urlDepth used to lower the priority deeper into the tree
	 */
	private void populateChildren(Competence child_node, Element rootElement, Float urlDepth) {
		Element tempElem = rootElement.addElement("url");
		tempElem.addElement("loc").setText("http://"+getUrlRoot()+"/searchresource?competenceid="+child_node.getId());
		populateBasicAttributes(tempElem, urlDepth);
		if(!child_node.getChildren().isEmpty())
		{
			for (Competence sub_child : child_node.getChildren()) {
				populateChildren(sub_child, rootElement, urlDepth+.1F);
			}
		}
	}

	/**
	 * Boilerplate function used to add the common elements such as date modified, change frequency and priority to all url elements
	 * @param elem element to add common details to
	 * @param depth greater depth decreases the priority relayed to the crawler, valid range is 0F -> 1F. invalid input reverts to 0.5F for security
	 */
	private static void populateBasicAttributes(Element elem, float depth){
		if(depth<0||depth>1)
			depth=.5F;

		elem.addElement("lastmod").setText(DateUtil.formatyyyyMMdd(new Date()));
		elem.addElement("changefreq").setText("monthly");
		elem.addElement("priority").setText(String.valueOf(1F-depth));
	}

	/**
	 * Boilerplate function to pull the current production URL from UrlUtil
	 * @return the production url string
	 */
	public String getUrlRoot() {
		return UrlUtil.getProdAbsoluteDomainName();
	}

	/**
	 * Boilerplate function that queries the database for the number of validated resources and returns that number divided by 500k
	 * used by SiteMapController.java, Cache.java and SiteMapService.java for generating the correct number of resource maps.
	 * Thanks go to John Rizzo for debugging query so that the bugged result from query.getMaxResults() was no longer required.
	 * @return the number of validated resources divided by 500k
	 */
	public int getNumResourceMaps() {
		if(resNumMaps<0){
			String queryString = "SELECT count(r) FROM Resource r WHERE r.validationStatus = 'ACCEPT'";
			Query query = em.createQuery(queryString);
			resNumMaps = (int)(((long)query.getSingleResult())/500000);
		}
		return resNumMaps;
	}



}
