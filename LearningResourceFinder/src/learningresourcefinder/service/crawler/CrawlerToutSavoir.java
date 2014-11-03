package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.net.URL;
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

import org.apache.poi.hssf.record.chart.CatLabRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrawlerToutSavoir 
{
    @Autowired  CrawlerService cs;
    //DONE 428
    public void crawler() throws IOException    
    {  
        String charsetName = "ISO-8859-1";//"UTF-8";
        String minCycle = "";
        String maxCycle = "";
        String mainUrl = "http://www.toutsavoir-hatier.com";
        Document frontPage = Jsoup.parse(new URL(mainUrl).openStream(), charsetName, mainUrl);
        //Document frontPage = Jsoup.connect("http://www.toutsavoir-hatier.com").timeout(10000).get();
        Elements elements = frontPage.select(".lienblancns").select("a");    //Selectionne CP CM... liens de milieu de page
       
        for (Element element : elements) 
        {
            if (element.text().equals("CP") || element.text().equals("CE1")) {
                
                minCycle = "P1-2";
                maxCycle = "P1-2";
                }
            
            else if (element.text().equals("CE2") || element.text().equals("CM1")) {
                
                minCycle = "P3-4";
                maxCycle = "P3-4";
                }
            
            else if (element.text().equals("CM2")) {
                
               minCycle = "P5-6";
               maxCycle = "P5-6";
                }
                    
            System.out.println("=======================================================================");
            System.out.println(element.text());
            System.out.println("=======================================================================");
            String mainLink = "http://www.toutsavoir-hatier.com/" + element.attr("href");

           // Document categoryDoc = Jsoup.connect(mainLink).timeout(10000).get();
            Document categoryDoc = Jsoup.parse(new URL(mainLink).openStream(), charsetName, mainLink);
            Elements elementsByCategory = categoryDoc.select(".lienblancns").select("a");  // Selectionne Francais Maths... + CP CM1 en bas de page

            for (Element elementByCategory : elementsByCategory)  
            {

                String category = "------------------";
                String title = "-----------------------";
                String link = null;
                String goToCategory = "http://www.toutsavoir-hatier.com/" + elementByCategory.attr("href");
               

                if (!goToCategory.contains("ecole")) // Pour éviter les liens CP CM1... de bas de page - garde les liens année/matière
                {

                    Document sommaireExosByCategory ; 
                   // Document sommaireExosByCategory = Jsoup.connect(goToCategory).timeout(10000).get(); 
                    if(goToCategory.equals("http://www.toutsavoir-hatier.com/liste_podcasts.php?matiere=anglais&id_classe=cm2"))
                    {
                        sommaireExosByCategory = Jsoup.parse(new URL(goToCategory).openStream(), "UTF-8", goToCategory); 
                    }
                    else
                    {
                        sommaireExosByCategory = Jsoup.parse(new URL(goToCategory).openStream(), charsetName, goToCategory);                         
                    }
                    Elements exosList= sommaireExosByCategory.select("a");

                    for (Element linkFinal : exosList) 
                    {
                        if (linkFinal.attr("href").contains("exo_entrainement")) {  // exo_entrainement: nomenclature commune math francais

                            String goToFinalLinkMathFrench = "http://www.toutsavoir-hatier.com/" + linkFinal.attr("href");
                            //Document mathFrenchDoc = Jsoup.connect(goToFinalLinkMathFrench).timeout(10000).get();
                            Document mathFrenchDoc = Jsoup.parse(new URL(goToFinalLinkMathFrench).openStream(), charsetName, goToFinalLinkMathFrench); 
                            Elements mathFrenchExos = mathFrenchDoc.select(".enonce");
                            for ( Element mathFrenchExo : mathFrenchExos) {


                                title = mathFrenchExo.select(".enonce").text();
                                link = goToFinalLinkMathFrench; 
                                if (goToCategory.contains("id_mat=2")) {
                                    category = "math";
                                }
                                else if (goToCategory.contains("id_mat=1")) {
                                    category = "français";
                                }
                            }

                        }

                        else if (linkFinal.attr("href").contains("fiche_dictee")) {  

                            String goToFinalLinkDictees = "http://www.toutsavoir-hatier.com/" + linkFinal.attr("href");
                            //Document dicteesDoc = Jsoup.connect(goToFinalLinkDictees).timeout(10000).get();
                            Document dicteesDoc = Jsoup.parse(new URL(goToFinalLinkDictees).openStream(), "ISO-8859-1", goToFinalLinkDictees); 
                            Elements dicteesExos = dicteesDoc.select(".notion");
                            for ( Element dicteesExo : dicteesExos) {


                                title = dicteesExo.select(".notion").text();
                                link = goToFinalLinkDictees;
                                category = "français";
                            }

                        }
                        

                        else if (linkFinal.attr("href").contains("liste_podcasts")) {  

                            String goToFinalLinkEnglishEasy = "http://www.toutsavoir-hatier.com/" + linkFinal.attr("href");
                          


                                title = linkFinal.text();
                                link = goToFinalLinkEnglishEasy;
                                category = "anglais";
                           

                        }
                        else if (linkFinal.attr("href").contains("workshop")) {  
                            String goToFinalLinkEnglish = "http://www.toutsavoir-hatier.com/" + linkFinal.attr("href");

                                title = linkFinal.text();
                                link = goToFinalLinkEnglish;
                                category = "anglais";
                        }
                        if(link!=null)
                        {
                            System.out.println(title);
                            System.out.println(link);
                            System.out.println(category);
                            System.out.println(minCycle);

                            cs.persistRessource(title,link,category,"",0,minCycle,maxCycle, 0, "Editions Hatier",null);
                                                         

                        }
                        category = "------------------";
                        title = "-----------------------";
                        link = null;

                    }

                }
            }

        }
    }

    public static void main(String[] args) throws IOException 
    {
        CrawlerToutSavoir cr = new CrawlerToutSavoir();
        cr.crawler();

    }

}
