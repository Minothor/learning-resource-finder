package learningresourcefinder.service;


import java.io.IOException;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
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
			crawlerchampionMath();			
			break;
		case "classePrimaire" :
			crawlerchampionMath();
			break;

		default:
		}
	}
	
	public void crawlerchampionMath() throws IOException
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

				Resource r = new Resource(titre,"",SecurityContext.getUser());
				// TODO replace 
				UrlResource url = new UrlResource(titre,lien,r);

				resourceRepository.persist(r);
				urlResourceRepository.persist(url);
				r.getShortId();
			}
			System.out.println("=================================================");
		}
	}

	
/*
	public  void classeprimaireCrawler() throws IOException
	{
		Document doc = Jsoup.connect("").timeout(10000).get();
		Elements elements = doc.select("#imContent div");


		//on commence a la 4eme div de #imContent. Dans ces div il y a 5 div compliquées: 
		//	la 3eme div : la catégorie (nom de l’image)
		//	la 4eme div : le titre
		//	la 5eme div : le lien

		
		int i =0;
		for (Element element : elements) 
		{
			if(i>=3)
			{
				element.select("div:nth-child(3)");
				element.select("div:nth-child(4)");
				element.select("div:nth-child(5)");
				System.out.println(element.text());
			}
			i++;
		}
	}
	
	
	
	
	
	
	
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



