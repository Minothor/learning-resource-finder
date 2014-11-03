package learningresourcefinder.service.crawler;

import java.io.IOException;
import learningresourcefinder.search.SearchOptions.Format;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Elements elements = doc.select("#imPage #imContent .text-inner tr");
            //on commence a la 4eme div de #imContent. Dans ces div il y a 5 div compliquées: 
            //  la 3eme div : la catégorie (nom de l’image)
            //  la 4eme div : le titre
            //  la 5eme div : le lien

            for (Element element : elements) 
            {
                    String name = element.select("td:nth-child(3)").text();
                    String topic = element.select("td:nth-child(2) img").attr("src");
                    String url = element.select("td:nth-child(4) a").attr("href");
                    /*int startTrim = topic.indexOf("/")+1;
                    int endTrim = (topic.indexOf("_")<topic.indexOf(".") && topic.indexOf("_")>0 )? topic.indexOf("_") : topic.indexOf("."); 
                    topic = topic.substring(startTrim,endTrim);*/

                    if(cs!=null && url!=null && !url.trim().equals(""))
                    {
                        cs.persistRessource(name,url,topic,"",0,"P5-6","P5-6", 0, "G. Demeulenaere",Format.INTERACTIVE);
                    }
                    System.out.println(name);
                    System.out.println(topic);
                    System.out.println(url);
                    System.out.println("=====================================");
            }
        }
    }
    
    public static void main(String[] args) throws IOException {

        CrawlerClassePrimaire cr = new CrawlerClassePrimaire();
        cr.crawler();
    }
}
