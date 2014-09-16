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

import org.json.simple.parser.ParseException;
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
		@Autowired
		CrawlerChampionMath crawlerChampionMath;
		@Autowired
		CrawlerClassePrimaire crawlerClassePrimaire;
		@Autowired
		CrawlerExoFiche crawlerExoFiches;
		@Autowired
		CrawlerKhanAcademy crawlerKanAcademy;

		public void crawlerPage(String pageName) throws IOException, ParseException {
			switch (pageName)
				{
					case "championMath":
						crawlerChampionMath.crawler();
						break;
					case "classePrimaire":
						crawlerClassePrimaire.crawler();
						break;
					case "classePrimaire2":
						crawlerClassePrimaire.crawler();
						break;
					case "exoFiches":
						crawlerExoFiches.crawler();
						break;
					case "KhanAcademy":
						crawlerKanAcademy.crawler();
						break;

					default:
				}
		}

		public static String getSubString(String s, int i) {
			return (s.length() <= i) ? s : s.substring(0, i);
		}

		public static Topic getTopicFromString(String s) {
			s = s.toLowerCase();
			if (s.contains("grammaire") || s.contains("lecture") || s.contains("conugaison") || s.contains("conjugaison") || s.contains("orthographe") || s.contains("savoir-ecouter") || s.contains("vocabulaire"))
				{
					return Topic.FRENCH;
				}
			else if (s.contains("geographie") || s.contains("géographie") || s.contains("geographique") || s.contains("géographique"))
				{
					return Topic.GEO;

				}
			else if (s.contains("math") || s.contains("nombres") || s.contains("opération"))
				{
					return Topic.MATH;

				}
			else if (s.contains("histoire") || s.contains("historique"))
				{
					return Topic.HISTORY;

				}
			else if (s.contains("scientifique") || s.contains("science"))
				{
					return Topic.SCIENCE;

				}
			return null;
		}

		/*
		 * public static void fondationLamapCrawler() throws IOException {
		 * String[] urls = {
		 * "http://www.fondation-lamap.org/fr/search-activite-classe?filter[keyword]=&filter[num_per_page]=200&filter[sort]=ds_created&filter[order]=asc&op=Rechercher&form_build_id=form-BHdeSlYAz_chhuj_gUZ_AAbAW4XrDCay5MXcOGHSt-M&form_id=project_search_recherche_activite_class_form"
		 * }; Boolean cont = true;
		 * 
		 * while(cont) { for (String url : urls) { Document doc =
		 * Jsoup.connect(url).timeout(10000).get(); Elements elements =
		 * doc.select(".results .result-item"); for (Element element : elements)
		 * { Element elem = element.select("div > .right").first();
		 * 
		 * String titre = elem.select(".title > a").text(); String lien =
		 * "http://www.fondation-lamap.org"
		 * +elem.select(".title > a").attr("href"); String description =
		 * elem.select(".field-doc-resume").text(); String categorie =
		 * "science";
		 * 
		 * System.out.println(titre+" ("+categorie+")");
		 * System.out.println("lien : "+lien);
		 * System.out.println("description : "+description);
		 * System.out.println("====================================="); } } } }
		 */
	}
