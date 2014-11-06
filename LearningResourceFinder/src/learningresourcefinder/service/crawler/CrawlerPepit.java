package learningresourcefinder.service.crawler;

import java.awt.image.BufferedImage;
import java.io.IOException;

import learningresourcefinder.model.Resource;
import learningresourcefinder.util.CurrentEnvironment;
import learningresourcefinder.util.ImageUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CrawlerPepit 
{

    @Autowired  CrawlerService cs;
    @Autowired  CurrentEnvironment currentEnvironment;
    
    static long count = 0;

    public void crawler(int i) throws IOException
    {
        switch(i)
        {
        case(1) : crawlerLevels("P1-2", "P1-2", "http://www.pepit.be/niveaux/niveau1.html"); break;
        case(2) : crawlerLevels("P1-2", "P1-2", "http://www.pepit.be/niveaux/niveau2.html"); break;
        case(3) : crawlerLevels("P3-4", "P3-4", "http://www.pepit.be/niveaux/niveau3.html"); break;
        case(4) : crawlerLevels("P3-4", "P3-4", "http://www.pepit.be/niveaux/niveau4.html"); break;
        case(5) : crawlerLevels("P5-6", "P5-6", "http://www.pepit.be/niveaux/niveau5.html"); break;
        case(6) : crawlerLevels("P5-6", "P5-6", "http://www.pepit.be/niveaux/niveau6.html"); break;
        case(7) : crawlerLevels("P1-2", "P5-6", "http://www.pepit.be/niveaux/conjugaison.html"); break;
        case(8) : crawlerLevels("P1-2", "P5-6", "http://www.pepit.be/niveaux/tablesmultiplication.html"); break;
        case(9) : crawlerLevels(null, null, "http://www.pepit.be/niveaux/special.html"); break;
        case(10) : crawlerLevels(null, null, "http://www.pepit.be/niveaux/pourtous.html"); break;
        }
    }

    public void crawlerLevels(String minCcycle, String maxCcycle, String url) throws IOException {
        Document doc1 = Jsoup.connect(url).timeout(10000).get();
        Elements ligne1 = doc1.select("table h2, table a");
        String topic = null;
        for (Element ligne : ligne1) {
            if(ligne.tagName().equals("h2") && !ligne.text().isEmpty()) {
                topic = ligne.text();
            }
            if(ligne.tagName().equals("a"))
            {     
                String lien = ligne.attr("href");
                lien = lien.substring(2, lien.length());
                String link = "http://www.pepit.be"+lien;
                String titre = ligne.text();

                crawler2ndPage(topic, link, titre, minCcycle, maxCcycle);
            }
        }
    }

  
    public void crawler2ndPage(String topic, String urlLink, String title, String minCycle, String maxCycle) throws IOException {
        String imageUrl = null;
        
        Document doc1 = Jsoup.connect(urlLink).timeout(10000).get();

        int position = urlLink.indexOf("page.html");
        if (position != -1) {
            imageUrl = urlLink.substring(0, position) + "images/Image%201.png";
        }
        
        String url;
        Elements ligne1 = doc1.select("table a");

        for (Element ligne : ligne1) {
            url = null;
            
            if(ligne.tagName().equals("a") && ligne.attr("href").endsWith(".html"))
            {   
                if (position != -1) {
                    String lien = ligne.attr("href");
                    url = urlLink.substring(0, position) + lien;
                }
                else {
                    url = urlLink;
                }
                
                Resource resource = cs.persistRessource(title, url, topic,"",0, minCycle, maxCycle, 0, "Pepit",null);
                count++;
                //System.out.println(minCycle + " " + maxCycle + " : " + title + " : " + url);
                
                if (imageUrl != null) {
                    BufferedImage image = null;
                    
                    try {
                        image = ImageUtil.readImage(imageUrl);
                    } catch (RuntimeException e) {
                        try {
                            imageUrl = urlLink.substring(0, position) + "image/Image%201.png";
                            image = ImageUtil.readImage(imageUrl);
                        }
                        catch  (RuntimeException ex1)  {
                            try {
                                imageUrl = urlLink.substring(0, position) + "IMAGES/Image%201.png";
                                image = ImageUtil.readImage(imageUrl);
                            }
                            catch  (RuntimeException ex2)  {
                                System.out.println("Image import problem for " + url);    
                            }
    
                        }
                    }
                    if (image != null){
                        ImageUtil.createOriginalAndScalesImageFileForResource(resource, image, currentEnvironment); 
                    }
                }
            }
        }
    }

//    public void crawler2ndPage(String topic, String urlLink, String titre, String minCycle, String maxCycle) throws IOException {
//        Document doc1 = Jsoup.connect(urlLink).timeout(10000).get();
//        Elements ligne1 = doc1.select("table a");
//
//        int position = urlLink.indexOf("page.html");
//        if (position != -1) {
//            for (Element ligne : ligne1) {
//                if(ligne.tagName().equals("a") && ligne.attr("href").endsWith(".html"))
//                {     
//                    String lien = ligne.attr("href");
//                    urlLink = urlLink.substring(0, position) + lien;
//                    //cs.persistRessource(titre, urlLink, topic,"",0, minCycle, maxCycle, 0, "Ludovic Mercier",null);
//                }
//            }
//        }
//        else {
//            System.out.println("!!!!!!!!!!!!!! Problem with " + urlLink);
//        }
//
//        System.out.println(minCycle + " " + maxCycle + " : " + titre + " : " + urlLink);
//    }

    public static void main(String[] args) throws IOException {
        CrawlerPepit cr = new CrawlerPepit();
        System.out.println("**************** Niveau 1 ********************");
        cr.crawlerLevels("P1-2", "P1-2", "http://www.pepit.be/niveaux/niveau1.html");
        System.out.println("**************** Niveau 2 ********************");
        cr.crawlerLevels("P1-2", "P1-2", "http://www.pepit.be/niveaux/niveau2.html");
        System.out.println("**************** Niveau 3 ********************");
        cr.crawlerLevels("P3-4", "P3-4", "http://www.pepit.be/niveaux/niveau3.html");
        System.out.println("**************** Niveau 4 ********************");
        cr.crawlerLevels("P3-4", "P3-4", "http://www.pepit.be/niveaux/niveau4.html");
        System.out.println("**************** Niveau 5 ********************");
        cr.crawlerLevels("P5-6", "P5-6", "http://www.pepit.be/niveaux/niveau5.html");
        System.out.println("**************** Niveau 6 ********************");
        cr.crawlerLevels("P5-6", "P5-6", "http://www.pepit.be/niveaux/niveau6.html");
        
        System.out.println("**************** Conjugaison ********************");
        cr.crawlerLevels("P1-2", "P5-6", "http://www.pepit.be/niveaux/conjugaison.html");
        
        System.out.println("**************** Tables de multiplication ********************");
        cr.crawlerLevels("P1-2", "P5-6", "http://www.pepit.be/niveaux/tablesmultiplication.html");

        System.out.println("**************** Enseignement spécial ********************");
        cr.crawlerLevels(null, null, "http://www.pepit.be/niveaux/special.html");

        System.out.println("**************** Pour tous ********************");
        cr.crawlerLevels(null, null, "http://www.pepit.be/niveaux/pourtous.html");
        
        System.out.println("Number of created resources : " + count);

    }
}
