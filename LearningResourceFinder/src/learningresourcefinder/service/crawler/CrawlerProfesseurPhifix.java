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

import org.apache.log4j.chainsaw.Main;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class CrawlerProfesseurPhifix 
{
    @Autowired  ResourceRepository resourceRepository ;
    @Autowired  UrlResourceRepository urlResourceRepository ;
    
    //DONE
    public static void crawler(CrawlerService cs) throws IOException 
    {
        Element tmp = null;
        Document doc = Jsoup.connect("http://www.professeurphifix.net/").timeout(10000).get();
        Elements elemCategories = doc.select("nav.primary-navigation > ul > li > a");
        for (Element elemCategory : elemCategories) 
        {
            String categorie = elemCategory.text();
           // System.out.println(categorie);
            String lienCategorie = "http://www.professeurphifix.net/" + elemCategory.attr("href");
            Document doc1 = Jsoup.connect(lienCategorie).timeout(10000).get();
            Elements subCategories = doc1.select("div.sommaire-fiche > table > tbody > tr > td > p > a");

            for (Element subCategory : subCategories) 
            {
                String lienSubCat = subCategory.attr("href");
                if(lienSubCat=="")
                    lienSubCat = lienCategorie;
                else
                    lienSubCat = ("http://www.professeurphifix.net/"+lienSubCat).replace("../", "");
               // System.out.println("\t"+lienSubCat);
                Document doc2 = Jsoup.connect(lienSubCat).timeout(10000).get();                
                Elements ressources = doc2.select("div.fiche > table.table-fiches > tbody > tr > td  a");
                int cpt =-1 ;
                for (Element ressource : ressources) 
                {
                    
                    String lienRessource = ressource.attr("href");  
                    String titre = ressource.text();
                    if (titre.matches("1")|| titre.matches("2")|| titre.matches("3")|| titre.matches("4")|| titre.matches("5")|| titre.matches("6")|| titre.matches("7")|| titre.matches("8")|| titre.matches("9") )
                    {   
                        if (titre.matches("1")&& !lienRessource.matches("choisir_la_question.htm") && !lienRessource.matches("geographie_relief.pdf")){
                            cpt ++;
                             tmp = doc2.select("div.fiche > table.table-fiches > tbody > tr > td > p  span").get(cpt);
                            
                        }
                        if ( lienRessource.matches("choisir_la_question.htm"))
                        {
                             tmp = doc2.select("div.fiche > table.table-fiches > tbody > tr > td > p >font > a").first();
                            titre = tmp.text()+titre;
                        }
                        if (lienRessource.matches("geographie_relief.pdf"))
                        {
                             break;
                        }
                        // titre = lienRessource.replace("%20", " ").replace(".pdf", "").replace(".htm", "").replace(".zip", "").replace(".PDF", "");
                        titre = tmp.text()+titre;
                      
                    }
                    
                    String finalLink  =(lienSubCat.substring(0,lienSubCat.lastIndexOf("/")));
                    finalLink = (finalLink+"/"+lienRessource);
                    if (titre.equals("")){
                        titre = "titre a remplacer";
                    }
                    if (titre.startsWith("Fiche")){
                        titre = titre.substring(9, titre.length());
                    }
                    titre = CrawlerService.getSubString(titre, 50);
                   // System.out.println("\t\t"+titre+" ("+finalLink+" )"+"  cat:"+categorie);
                    cs.persistRessource(titre,finalLink,categorie,"",0,"","");
                }    
            }
           // System.out.println("============================");
        }
        Document docgeo = Jsoup.connect("http://www.professeurphifix.net/eveil/sommaire_geographie.htm").timeout(10000).get();
        Elements titregeo = docgeo.select("div.fiche > table.table-fiches > tbody > tr > td > p > a");
        for (Element element : titregeo) {
            String titre = element.text();
            if (titre.matches("1")|| titre.matches("2")|| titre.matches("3")|| titre.matches("4")){
                titre = element.attr("href").replace("_", " ").replace(".pdf", "").replace(".htm", "").replace(".PDF", "");
            }
            String finalLink = ("http://www.professeurphifix.net/eveil/"+element.attr("href"));
            cs.persistRessource(titre,finalLink,"Geographie","",0,"","");
           // System.out.println(titre+" (http://www.professeurphifix.net/eveil/"+element.attr("href")+" )");
        }
        
        
        
    }
    public static void main(String[] args) throws IOException {
        crawler(null);
    }
}
