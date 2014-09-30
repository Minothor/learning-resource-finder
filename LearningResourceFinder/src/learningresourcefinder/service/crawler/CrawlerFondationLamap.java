package learningresourcefinder.service.crawler;

import java.io.IOException;

import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CrawlerFondationLamap 
{

    @Autowired CrawlerService cs;
    public void crawler () throws IOException
    {
        String[] urls = {"http://www.fondation-lamap.org/fr/search-activite-classe?filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-BHdeSlYAz_chhuj_gUZ_AAbAW4XrDCay5MXcOGHSt-M&form_id=project_search_recherche_activite_class_form",
                 "http://www.fondation-lamap.org/fr/search-activite-classe?page=1&filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-BHdeSlYAz_chhuj_gUZ_AAbAW4XrDCay5MXcOGHSt-M&form_id=project_search_recherche_activite_class_form"};


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
    public static void main(String[] args) throws IOException {
        CrawlerFondationLamap cr = new CrawlerFondationLamap();
        cr.crawler();
    }
}