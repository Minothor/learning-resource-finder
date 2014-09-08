package learningresourcefinder.service;


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
public class CrawlerService
{
	@Autowired  ResourceRepository resourceRepository ;
	@Autowired  UrlResourceRepository urlResourceRepository ;

	public void crawlerPage(String pageName) throws IOException {
		switch (pageName) {
		case "championMath" :
			crawlerChampionMath();			
			break;
		case "classePrimaire" :
			crawlerClassePrimaire();
			break;

		default:
		}
	}

	public void crawlerChampionMath() throws IOException
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

				Resource r = new Resource(getSubString(titre, 50),"",SecurityContext.getUser());
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


	public static void main(String[] args) throws IOException 
	{
		CrawlerService s = new CrawlerService();
		s.crawlerClassePrimaire();
	}

	public  void crawlerClassePrimaire() throws IOException
	{
		for(int i=10;i<=70;i=i+10)
		{
			Document doc = Jsoup.connect("http://classeprimaire.be/exercices"+i+".html").timeout(10000).get();
			Elements elements = doc.select("#imContent > div");
			//on commence a la 4eme div de #imContent. Dans ces div il y a 5 div compliquées: 
			//	la 3eme div : la catégorie (nom de l’image)
			//	la 4eme div : le titre
			//	la 5eme div : le lien

			int y =0;
			for (Element element : elements) 
			{
				if(y>=3 && y<=12)
				{
					/*
					 * Math :
					 * 		mathematique.png
					 * Histoire :
					 * 		histoire.png
					 * Francais :
					 * 		grammaire.png
					 * 		lecture.png
					 * 		conugaison.png
					 * 		orthographe.png
					 * 		savoir-ecouter.png
					 * 		vocabulaire.png
					 * Geographie :
					 * 		geographie_gux7xau1.png
					 * 
					 * anciens-ceb.png
					 * coach-memorisation.png 
					 */
					String categorie = element.select("div:nth-child(3) img").attr("src");
					String titre = element.select("div:nth-child(4)").text();
					String lien = element.select("div:nth-child(5) a").attr("href");
					
					Resource r = new Resource(getSubString(titre,50),"Exercice classeprimaire.be",SecurityContext.getUser());
					r.setLanguage(Language.FR);
					
					Topic topic = getTopicFromString(categorie);
					topic = (topic!=null)?topic:getTopicFromString(titre);					
					r.setTopic( topic);
					
					r.setFormat(Format.INTERACTIVE);
					Set<Platform> tempSet = new HashSet<>();
					tempSet.add(Platform.BROWSER);
					r.setPlatforms(tempSet);
					UrlResource url = new UrlResource(getSubString(titre,50),lien,r);

					resourceRepository.persist(r);
					urlResourceRepository.persist(url);
					r.getShortId();	
					
					
					System.out.println(titre);
					System.out.println(categorie);
					System.out.println(lien);
					System.out.println("=====================================");
				}
				y++;
			}
		}
	}

	private String getSubString(String s,int i)
	{
		return (s.length()<=i)?s:s.substring(0, i);
	}
	private Topic getTopicFromString(String s)
	{
		s = s.toLowerCase();
		if(s.contains("grammaire") ||
				s.contains("lecture") ||
				s.contains("conugaison") ||
				s.contains("conjugaison") ||
				s.contains("orthographe") ||
				s.contains("savoir-ecouter") ||
				s.contains("vocabulaire"))
		{
			return Topic.FRENCH;
		}
		else if(s.contains("geographie") || s.contains("géographie") ||
				s.contains("geographique") || s.contains("géographique"))
		{
			return Topic.GEO;
			
		}
		else if(s.contains("math")||
				s.contains("nombres")||
				s.contains("opération") )
		{
			return Topic.MATH;
			
		}
		else if(s.contains("histoire") || s.contains("historique"))
		{
			return Topic.HISTORY;
			
		}
		else if(s.contains("scientifique") || s.contains("science"))
		{
			return Topic.SCIENCE;
			
		}
		return null;
	}

	/*


	public static void fondationLamapCrawler() throws IOException
	{
		String[] urls = {"http://www.fondation-lamap.org/fr/search-activite-classe?filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-l4jUhWDDLbValkrkGFsiJUfXfsDLVqVYTQu1h23PRvc&form_id=project_search_recherche_activite_class_form",
		"http://www.fondation-lamap.org/fr/search-activite-classe?page=1&filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-l4jUhWDDLbValkrkGFsiJUfXfsDLVqVYTQu1h23PRvc&form_id=project_search_recherche_activite_class_form"};

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
	 */
}



