package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import learningresourcefinder.model.Cycle;
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

@Service
public class CrawlerClassePrimaire 
{

    @Autowired CrawlerService cs;
    //Done 70 ressources
    public void crawler() throws IOException
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
                    String name = element.select("div:nth-child(4)").text();
                    String topic = element.select("div:nth-child(3) img").attr("src");
                    String url = element.select("div:nth-child(5) a").attr("href");
                    int startTrim = topic.indexOf("/")+1;
                    int endTrim = (topic.indexOf("_")<topic.indexOf(".") && topic.indexOf("_")>0 )? topic.indexOf("_") : topic.indexOf(".");
                    topic = topic.substring(startTrim,endTrim);

                    if(cs!=null)
                        cs.persistRessource(name,url,topic,"",0,"P5-6","P5-6", 0, "G. Demeulenaere");
                    
                    System.out.println(name);
                    System.out.println(topic);
                    System.out.println(url);
                    System.out.println("=====================================");
                }
                y++;
            }
        }
    }
    

    public static void main(String[] args) throws IOException {

        CrawlerClassePrimaire cr = new CrawlerClassePrimaire();
        cr.crawler();
    }

}
