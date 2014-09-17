package learningresourcefinder.service.crawler;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CrawlerService
{	@Autowired
    CycleRepository cycleRepository;
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UrlResourceRepository urlResourceRepository;    
    
    public void crawlerPage(String pageName) throws IOException 
    {
      /*  switch (pageName) 
        {
            case "championMath" :
                CrawlerChampionMath.crawler();			
                break;
            case "classePrimaire" :
                CrawlerClassePrimaire.crawler();
                break;
            case "crawlersoutien67" :
                CrawlerSoutien67.crawlerFr1();
                break;
    
            default:
        }*/
    }

    public static String getSubString(String s,int i)
    {
        return (s.length()<=i)?s:s.substring(0, i);
    }
    public static Topic getTopicFromString(String s)
    {
        s = s.toLowerCase();
        if(s.contains("grammaire") ||
                s.contains("lecture") ||
                s.contains("conugaison") ||
                s.contains("conjugaison") ||
                s.contains("orthographe") ||
                s.contains("savoir-ecouter") ||
                s.contains("vocabulaire") ||
                s.contains("coach-memorisation") )
        {
            return Topic.FRENCH;
        }
        else if(s.contains("geographie") || s.contains("géographie") ||
                s.contains("geographique") || s.contains("géographique"))
        {
            return Topic.GEO;

        }
        else if(s.contains("math")||
                s.contains("nombres")||
                s.contains("opération") )
        {
            return Topic.MATH;

        }
        else if(s.contains("histoire") || s.contains("historique"))
        {
            return Topic.HISTORY;

        }
        else if(s.contains("scientifique") || s.contains("science"))
        {
            return Topic.SCIENCE;

        }
        return null;
    }

    public void persistRessource(String name, String url, String topic, String description,int duration,String minCycle,String maxCycle ) 
    {
        Resource r = new Resource(CrawlerService.getSubString(name, 50),description,SecurityContext.getUser());
        r.setLanguage(Language.FR);
        r.setTopic(getTopicFromString(topic));
        //r.setFormat(Format.INTERACTIVE);
        Set<Platform> tempSet = new HashSet<>();
        tempSet.add(Platform.BROWSER);
        //r.setPlatforms(getformatByUrl(url));
        UrlResource urlResource = new UrlResource(name,url,r);
        if(duration!=0)
        {
            
        }
        if(maxCycle!="" && maxCycle!=null)
        {
            r.setMaxCycle(cycleRepository.findByName(maxCycle));          
        }
        if(minCycle!="" && minCycle!=null)
        {
            r.setMinCycle(cycleRepository.findByName(minCycle));        
        }
        
        resourceRepository.persist(r);
        urlResourceRepository.persist(urlResource);
        r.getShortId();
    }
    
   


    /*
	public static void fondationLamapCrawler() throws IOException
	{
		String[] urls = {"http://www.fondation-lamap.org/fr/search-activite-classe?filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-BHdeSlYAz_chhuj_gUZ_AAbAW4XrDCay5MXcOGHSt-M&form_id=project_search_recherche_activite_class_form"};
		Boolean cont = true;

		while(cont)
		{
			for (String url : urls) 
			{
				Document doc = Jsoup.connect(url).timeout(10000).get();
				Elements elements = doc.select(".results .result-item");
				for (Element element : elements) 
				{
					Element elem = element.select("div > .right").first();

					String titre = elem.select(".title > a").text();
					String lien = "http://www.fondation-lamap.org"+elem.select(".title > a").attr("href");
					String description = elem.select(".field-doc-resume").text();
					String categorie = "science";

					System.out.println(titre+" ("+categorie+")");
					System.out.println("lien : "+lien);
					System.out.println("description : "+description);
					System.out.println("=====================================");
				}
			}
		}
	}
     */ 
}



