package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrawlerExoFiche
{
    @Autowired  ResourceRepository resourceRepository ;
    @Autowired  UrlResourceRepository urlResourceRepository ;

    @Autowired CrawlerService cs;
    // DONE 226 ressources
    public void crawler() throws IOException 
    {        
        String[] sites = {"http://www.exofiches.net/maternelle-ps-ms-gs.php",
        "http://www.exofiches.net/elementaire-cycle2-cp-ce1.php",
        "http://www.exofiches.net/elementaire-cycle3-ce2-cm1-cm2.php"};
        
        String[][] cycles = {{"P1-2","P1-2"},
                                {"P1-2","P1-2"},
                                {"P3-4","P5-6"}};
        
        
        for (int i=0;i<sites.length;i++)
        {
            String mainLink = sites[i];
            //System.out.println("**********************\n");
          //  System.out.println(mainLink + " min cycle :" + cycles[i][0] + " max cycle :" + cycles[i][1]);
          //  System.out.println("**********************\n");

            Document doc = Jsoup.connect(mainLink).timeout(10000).get();

            Elements elements = doc.select("td td");
            String catName ="";
            for(Element element : elements)
            {
                if(element.select(".ProduitConfig").size()>0)
                {
                    if(!element.select(".ProduitConfig").text().equals(""))
                    catName = element.select(".ProduitConfig").text();
                    //System.out.println(catName);
                }
                if(element.select("a").size()>0)
                {

                    String url = element.select("a").attr("href");
                    if ((!url.contains("http")) && (!url.contains("javascript")))
                    {
                        //System.out.println("url : "+element.select("a").attr("href"));
                        doc = Jsoup.connect("http://www.exofiches.net/"+url).timeout(10000).get();
                        Elements elemRessources = doc.select("td td td");
                        //System.out.println(doc.select("tr").size());
                        List<String> titres = new ArrayList<String>();
                        List<String> urls = new ArrayList<String>();
                        for(Element ressource : elemRessources)
                        {
                            if(ressource.select("a").size()>0)
                            {
                                urls.add("http://www.exofiches.net/" +ressource.select("a").attr("href"));
                            }
                            if(ressource.select(".ResumePrix").size()>0)
                            {
                                titres.add(ressource.text());
                            }
                        }
                        
                        for(int j=0;j<titres.size();j++)
                        {
                            //System.out.println("\t"+titres.get(j)+" "+urls.get(j));
                            System.out.println(titres.get(j)+"("+urls.get(j)+")\n\t"+catName+" "+cycles[i][0]+"-"+cycles[i][1]);

                            cs.persistRessource(titres.get(j),urls.get(j),catName,"",0,cycles[i][0] ,cycles[i][1], 0, "Génération 5",Format.DOC);
                            
                        }
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        CrawlerExoFiche cr = new CrawlerExoFiche();
        cr.crawler();
    }
}
