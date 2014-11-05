package learningresourcefinder.service.crawler;


import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService
{	
    @Autowired CycleRepository cycleRepository;
    @Autowired ResourceRepository resourceRepository;
    @Autowired UrlResourceRepository urlResourceRepository;    
    
    @Autowired CrawlerBrainPOP crawlerBrainPOP;
    @Autowired CrawlerChampionMath crawlerChampionMath;
    @Autowired CrawlerClassePrimaire crawlerClassePrimaire;
    @Autowired CrawlerExoFiche crawlerExoFiche;
    @Autowired CrawlerLaRecre crawlerLaRecre;
    @Autowired CrawlerProfesseurPhifix crawlerProfesseurPhifix;
    @Autowired CrawlerSoutien67 crawlerSoutien67;
    @Autowired CrawlerToutSavoir crawlerToutSavoir;
    @Autowired CrawlerKhanAcademy crawlerKhanAcademy;

    @PersistenceContext EntityManager em;

    int cont1 = 0,cont2 = 0;

    public void crawlerPage(String pageName) throws IOException, ParseException    {
        pageName = pageName.toLowerCase();
        switch (pageName) 
        {
        case "brainpop" :
            crawlerBrainPOP.crawler();  break;      
        case "championmath" :
            crawlerChampionMath.crawler(); break; 
        case "classeprimaire" :
            crawlerClassePrimaire.crawler(); break;
        case "exofiche" :
            crawlerExoFiche.crawler(); break;
        /*case "fondationlamap" :
            CrawlerFondationLamap.crawler(this); break;*/
        case "crawlerlarecre" :
            crawlerLaRecre.crawler(); break;
        case "professeurphifix" :
            crawlerProfesseurPhifix.crawler(); break;
        case "soutien67" :
            crawlerSoutien67.crawler(); break;
        case "toutsavoir" :
            crawlerToutSavoir.crawler(); break;
        }
        System.out.println("ajout de "+cont1+" ressources et "+cont2+" 2éme passage");
    }
    
    public void crawlerPageKhanAcademy(int num)throws IOException, ParseException {
        crawlerKhanAcademy.crawler(num);
        //crawlerKhanAcademy.superCrawler();
    }
    
    public void persistRessource(String name, String url, String topic, String description, int duration, String minCycle, String maxCycle, int adv, String author,Format format) 
    {
        Resource r = urlResourceRepository.getFirstResourceWithSimilarUrl(url);
        if (r == null)
        {
            //System.out.println("1er passage "+url);
            r = new Resource(getSubString(name, 50),description,SecurityContext.getUser());
            r.setLanguage(Language.FR);
            r.setTopic(getTopicFromString(topic));
            if(format!= null)
            {
                r.setFormat(format);
            }
            else
            {
                r.setFormat(Format.INTERACTIVE);
            }
            Set<Platform> tempSet = new HashSet<>();
            tempSet.add(Platform.BROWSER);
            r.setPlatforms(tempSet);
            UrlResource urlResource = new UrlResource(getSubString(name, 50),url,r);
            if(duration!=0)
            {
                r.setDuration(duration);
            }

            Cycle cycle =  getCycle(maxCycle);
            if(cycle!=null)
            {
                r.setMaxCycle(cycleRepository.findByName(maxCycle));          
            }
            
            if(author!= null)
            {
                r.setAuthor(author);          
            }
            if(adv>=0)
            {
            	/*
            	 * -1 = unknown
            	 * 0 = false
            	 * 1 = true
            	 */
                r.setAdvertising(adv==1);          
            }
            cycle =  getCycle(minCycle);
            if(cycle!=null)
            {            
                r.setMinCycle(cycleRepository.findByName(minCycle));        
            }
            
            r.setValidationDate(new Date());
            r.setValidator(SecurityContext.getUser());
            r.setValidationStatus(ValidationStatus.ACCEPT);
            
            resourceRepository.persist(r);
            urlResourceRepository.persist(urlResource);
            r.getShortId(); 
            em.flush();
            cont1++;
        } 
        else 
        {
            //TODO regroup 2 code in a method
            r.setTopic(getTopicFromString(topic));
            
            if(format!= null)
            {
                r.setFormat(format);
            }
            
            if(duration!=0)
            {
                r.setDuration(duration);
            }
            
            //System.out.println("2eme passage "+url);
            Cycle cycle =  getCycle(maxCycle);
            if(cycle!=null)
            {
                if(r.getMaxCycle()==null ||
                  (getCycleNumber(cycle)>=0 && getCycleNumber(cycle)>getCycleNumber(r.getMaxCycle())))
                    r.setMaxCycle(cycle);          
            }
            cycle =  getCycle(minCycle);
            if(cycle!=null)
            {   
                if(r.getMinCycle()==null ||     
                (getCycleNumber(cycle)>=0 && getCycleNumber(cycle)<getCycleNumber(r.getMinCycle())))                     
                    r.setMinCycle(cycle);        
            }  
            
            if(author!= null)
            {
                r.setAuthor(author);          
            }
            if(adv>=0)
            {
                r.setAdvertising(adv==1);          
            }   
            
            cont2++;
        }
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
                s.contains("coach-memorisation") ||
                s.contains("français"))
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
        else if(s.contains("scientifique") || s.contains("science") ||
                s.contains("ingénierie") )
        {
            return Topic.SCIENCE;
        }
        return Topic.OTHER;
    }

    private Cycle getCycle(String cycleName) 
    {
        cycleName = cycleName.toUpperCase();
        switch(cycleName)
        {
        case("CP") : case("CE1") :case("P1-2") : 
            return cycleRepository.findByName("P1-2"); 
        case("CE2") : case("CM1") :case("P3-4") : 
            return cycleRepository.findByName("P3-4"); 
        case("CM2") : case("6") :case("P5-6") : 
            return cycleRepository.findByName("P5-6"); 
        default : return null;        
        }
    }
    
    private int getCycleNumber(Cycle cycle) 
    {
        System.out.println(cycle);
        System.out.println(cycle.getName());
        String cycleName = cycle.getName().toUpperCase();
        switch(cycleName)
        {
        case("P1-2") : 
            return 0; 
        case("P3-4") : 
            return 1; 
        case("P5-6") : 
            return 2; 
        default : return -1;        
        }
    }

    public void crawler67(int num) throws IOException {
       crawlerSoutien67.crawler(num);
        
    }
}