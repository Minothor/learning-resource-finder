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

public class CrawlerChampionMath 
{
  //DONE
    public static void crawler(CrawlerService cs) throws IOException
    {
        Document doc = Jsoup.connect("http://championmath.free.fr/").timeout(10000).get();
        Elements elements = doc.select("table:nth-child(4)").select("a");
        for (Element element : elements) 
        {
            String lienAnnee = "http://championmath.free.fr/"+element.attr("href");
            String niveau = element.text(); 
            String cycle = null;
            Document doc2 = Jsoup.connect(lienAnnee).timeout(10000).get();
            Elements elements2 = doc2.select("div#menu li a");
            System.out.println(niveau);
            String[] partsOfTitle = niveau.split(" ");
            if (partsOfTitle[0].equals("Maths")) {
                cycle = partsOfTitle[1]; 
            }
            System.out.println(cycle);

            for (Element element2 : elements2) 
            {
                String titre = "Exercice "+element2.text().toLowerCase();
                String lien = "http://championmath.free.fr/"+element2.attr("href");
                System.out.println("\t"+titre+" ("+lien+")");

                cs = new CrawlerService();
                cs.persistRessource(titre,lien,"Math","",0,cycle,cycle);
            }
            System.out.println("=================================================");
        }
    }
    
/*
    public static void main(String[] args) throws IOException {
        CrawlerChampionMath cr = new CrawlerChampionMath();
        cr.crawler();
    }*/
}
