package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.Resource.Topic;
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
public class CrawlerClassePrimaire 
{
    @Autowired  ResourceRepository resourceRepository ;
    @Autowired  UrlResourceRepository urlResourceRepository ;

    //Done
    public  void crawler() throws IOException
    {
        for(int i=10;i<=70;i=i+10)
        {
            Document doc = Jsoup.connect("http://classeprimaire.be/exercices"+i+".html").timeout(10000).get();
            Elements elements = doc.select("#imContent > div");
            //on commence a la 4eme div de #imContent. Dans ces div il y a 5 div compliquées: 
            //  la 3eme div : la catégorie (nom de l’image)
            //  la 4eme div : le titre
            //  la 5eme div : le lien

            int y =0;
            for (Element element : elements) 
            {
                if(y>=3 && y<=12)
                {
                    /*
                     * Math :
                     *      mathematique.png
                     * Histoire :
                     *      histoire.png
                     * Francais :
                     *      grammaire.png
                     *      lecture.png
                     *      conugaison.png
                     *      orthographe.png
                     *      savoir-ecouter.png
                     *      vocabulaire.png
                     * Geographie :
                     *      geographie_gux7xau1.png
                     * 
                     * anciens-ceb.png
                     * coach-memorisation.png 
                     */
                    String categorie = element.select("div:nth-child(3) img").attr("src");
                    String titre = element.select("div:nth-child(4)").text();
                    String lien = element.select("div:nth-child(5) a").attr("href");

                    Resource r = new Resource(CrawlerService.getSubString(titre,50),"Exercice classeprimaire.be",SecurityContext.getUser());
                    r.setLanguage(Language.FR);

                    Topic topic = CrawlerService.getTopicFromString(categorie);
                    topic = (topic!=null)?topic:CrawlerService.getTopicFromString(titre);                  
                    r.setTopic( topic);

                    r.setFormat(Format.INTERACTIVE);
                    Set<Platform> tempSet = new HashSet<>();
                    tempSet.add(Platform.BROWSER);
                    r.setPlatforms(tempSet);
                    UrlResource url = new UrlResource(CrawlerService.getSubString(titre,50),lien,r);

                    resourceRepository.persist(r);
                    urlResourceRepository.persist(url);
                    r.getShortId(); 


                    System.out.println(titre);
                    System.out.println(categorie);
                    System.out.println(lien);
                    System.out.println("=====================================");
                }
                y++;
            }
        }
    }

}
