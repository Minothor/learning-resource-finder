package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.service.ImportService;
import learningresourcefinder.web.UrlUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerKhanAcademy
{
    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UrlResourceRepository urlResourceRepository;

    //static Elements elements = new Elements();
    /* static List<String> urlsection = new ArrayList<>();
    static List<String> urlSecondSection = new ArrayList<>();
    static List<String> urlResources = new ArrayList<>();*/
    static String khanAcademy = "https://fr.khanacademy.org";

    static String section;

    public static void crawler(CrawlerService cs,int index) throws IOException, ParseException 
    {        

        index-=1;
        if(index<0 || index>13)
            throw new IllegalArgumentException();

        if(index<8)
        {
            section = "math";
        }
        else
        {
            section = "science";            
        }            

        String[] urlsection = {
               //khanAcademy + "/math/early-math",
                khanAcademy + "/math/arithmetic",
                khanAcademy + "/math/pre-algebra" ,
                khanAcademy + "/math/algebra",
                khanAcademy + "/math/probability",
                khanAcademy + "/math/geometry",
                khanAcademy + "/math/precalculus",
                khanAcademy + "/math/trigonometry",
                khanAcademy + "/math/integral-calculus",
                khanAcademy + "/science/biology",
                khanAcademy + "/science/chemistry",
                khanAcademy + "/science/cosmology-and-astronomy",
                khanAcademy + "/science/physics",
                khanAcademy + "/science/organic-chemistry",
                khanAcademy + "/science/health-and-medicine"};       

        //urlsection.addAll(Arrays.asList(a));

        //getUrlSection();   

        geturlSecondSections(urlsection[index],cs);
        System.out.println(section + "FINISH !!!!!!!!!!!");
    }

    private static void geturlSecondSections(String url, CrawlerService cs) throws IOException 
    {
        String matiere = url.replace(khanAcademy + "/" + section + "/", "");
        Document doc = Jsoup.connect(khanAcademy + "/" + section + "/" + matiere).timeout(0).get();

        System.out.println("*****************************************");
        System.out.println(doc.select("h1").first().text());
        System.out.println("*****************************************\n");
        
        Elements elements = doc.select(".topic-list a");
        System.out.println(elements.size());
        for (Element element : elements)
        {            
            System.out.println(element.select("h4").text());
            System.out.println("==================================");
            String href = element.attr("href");
            if ((href.contains(section + "/" + matiere)) && (!href.trim().equals("/" + section + "/" + matiere)) && (!href.trim().equals("/" + section + "/" + matiere + "/d")))
            {
                getRessourcesUrl(element.attr("href"),cs);
            }
        }
    }

    private static void getRessourcesUrl(String url, CrawlerService cs) throws IOException 
    {

        Document doc = Jsoup.connect(khanAcademy + "/" + section + "/" + url).timeout(0).get();

       Elements elements = doc.select(".tutorial-overview-block .progress-item a");
       for (Element element : elements)
        {
         
            String href = element.attr("href");
            System.out.println("\t"+element.select("span").text());
            if ((href.contains(url)) && (!href.equals(url)))
            {
                String urlresource = khanAcademy + href;
                //System.out.println(urlresource);
                getResources(urlresource,cs);
            }
        }
    }

    private static void getResources(String url,CrawlerService cs) 
    {
        String temporyTitle = "";

        Document doc;
        try {
            doc = Jsoup.connect(url).timeout(0).get();
            String title = CrawlerService.getSubString(doc.title().replace("| Khan Academy", ""), 50);

            if (!title.equals(temporyTitle))
            {
                Elements elements = doc.select("meta").select("[content*=youtube]");

                if (elements.size() > 0) // LIEN YOUTUBE
                {
                   createYoutubeResource(doc.select("meta").select("[property*=og:video]").attr("content"),cs);
                }
                else
                {
                    cs.persistRessource(title,url,section,"",0,"" ,"");
                    System.out.println("\t\tressource créée");
                }
            }
            temporyTitle = title;
        } catch (IOException e) {
            System.out.println("IOException "+url);
        } catch (ParseException e) {
            System.out.println("ParseException "+url);
        }
    }

    private static void createYoutubeResource(String url,CrawlerService cs) throws IOException, ParseException 
    {
        if (UrlUtil.getYoutubeVideoId(url) != null)
        {
            ImportService is = new ImportService();
            JSONObject jsonObj = is.getYoutubeInfos(url);
            int duration = (int) ((double) jsonObj.get("duration"));
            String title = (String) jsonObj.get("title");
            String description =  (String) jsonObj.get("description");
            cs.persistRessource(title,url,section, description,duration,"" ,"");
            System.out.println("\t\tressource youtube créée");
        }
    }

    public static void main(String[] args) throws IOException, ParseException 
    {
        crawler(null,1);
    }
}
