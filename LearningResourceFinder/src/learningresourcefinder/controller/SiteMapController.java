package learningresourcefinder.controller;

import javax.servlet.http.HttpServletResponse;

import learningresourcefinder.model.Resource;
import learningresourcefinder.service.SiteMapService;
import learningresourcefinder.service.SiteMapService.SiteMap;
import learningresourcefinder.web.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller to handle requests for the ToujoursPlus sitemap.xml file.
 * @author NJ Boyd
 * @version 1.0
 */
@Controller
public class SiteMapController extends BaseController<Resource> {
	@Autowired SiteMapService siteMapService;

	/**
	 * Handles the http request for the site map index,
	 *     
	 * @param response used for setting XML MIMETYPE header
	 * @return the site map index
	 */
	@RequestMapping("/sitemap")
	@ResponseBody
	public String displaySiteMap(HttpServletResponse response){
		prepareResponseHeader(response);
		return Cache.getInstance().getSiteMapXml(SiteMap.INDEX);
	}

	/**
	 * Handles the http request for the Main site map,
	 *     
	 * @param response used for setting XML MIMETYPE header
	 * @return the site map requested
	 */
	@RequestMapping("/sitemap/main")
	@ResponseBody
	public String displayMainMap(HttpServletResponse response){
		prepareResponseHeader(response);
		return Cache.getInstance().getSiteMapXml(SiteMap.MAIN);
	}

	/**
	 * Handles the http request for the resources map, divides into groups of 500k results to fit sitemap limit.
	 * @param response used for setting XML MIMETYPE header
	 * @param mapNum the group index (i.e. 0 => resources 0->499999; 1 => resources 500000->999999; etc)
	 * @return the site map requested
	 */
	@RequestMapping("/sitemap/resources/{mapNum}")
	@ResponseBody
	public String displayresourceMap(HttpServletResponse response, @PathVariable("mapNum")int mapNum){
		prepareResponseHeader(response);
		int maxMaps = siteMapService.getNumResourceMaps();
		if(mapNum<0)
			mapNum=0;
		if(mapNum>maxMaps)
			mapNum=maxMaps;
		return Cache.getInstance().getSiteMapXml(SiteMap.RESOURCES,mapNum);
	}

	/**
	 * Handles the http request for the category tree map,
	 * @param response used for setting XML MIMETYPE header
	 * @return the site map requested
	 */
	@RequestMapping("/sitemap/categories")
	@ResponseBody
	public String displayCategoryMap(HttpServletResponse response){
		prepareResponseHeader(response);
		return Cache.getInstance().getSiteMapXml(SiteMap.CATEGORIES);
	}

	/**
	 * Handles the http request for the cycles map,
	 * @param response used for setting XML MIMETYPE header
	 * @return the site map requested
	 */
	@RequestMapping("/sitemap/cycles")
	@ResponseBody
	public String displayCycleMap(HttpServletResponse response){
		prepareResponseHeader(response);
		return Cache.getInstance().getSiteMapXml(SiteMap.CYCLES);
	}

	/**
	 *     Boilerplate to set the response header to "application/xml" and "UTF-8" encoding.
	 * @param response used for setting XML MIMETYPE header
	 */
	private void prepareResponseHeader(HttpServletResponse response) {
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
	}

}
