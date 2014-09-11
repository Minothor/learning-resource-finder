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
public class CrawlerChampionMath 
{
    @Autowired  ResourceRepository resourceRepository ;
    @Autowired  UrlResourceRepository urlResourceRepository ;
  //DONE
    public void crawler() throws IOException
    {
        Document doc = Jsoup.connect("http://championmath.free.fr/").timeout(10000).get();
        Elements elements = doc.select("table:nth-child(4)").select("a");
        for (Element element : elements) 
        {
            String lienAnnee = "http://championmath.free.fr/"+element.attr("href");
            String niveau = element.text();         
            Document doc2 = Jsoup.connect(lienAnnee).timeout(10000).get();
            Elements elements2 = doc2.select("div#menu li a");
            System.out.println(niveau);
            for (Element element2 : elements2) 
            {
                String titre = "Exercice "+element2.text().toLowerCase();
                String lien = "http://championmath.free.fr/"+element2.attr("href");
                System.out.println("\t"+titre+" ("+lien+")");

                Resource r = new Resource(CrawlerService.getSubString(titre, 50),"",SecurityContext.getUser());
                r.setLanguage(Language.FR);
                r.setTopic(Topic.MATH);
                r.setFormat(Format.INTERACTIVE);
                Set<Platform> tempSet = new HashSet<>();
                tempSet.add(Platform.BROWSER);
                r.setPlatforms(tempSet);
                UrlResource url = new UrlResource(titre,lien,r);

                resourceRepository.persist(r);
                urlResourceRepository.persist(url);
                r.getShortId();
            }
            System.out.println("=================================================");
        }
    }
}
