package learningresourcefinder.service.crawler;

import java.io.IOException;
import java.util.HashSet;
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
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CrawlerExoFiche
	{
		   @Autowired  ResourceRepository resourceRepository ;
		   @Autowired  UrlResourceRepository urlResourceRepository ;

		// DONE
		public void crawler() throws IOException {

			Set<String> liensPagePrincipale = new HashSet<>();
			Set<String> liensPagesCycles = new HashSet<>();
			Set<Resource> ressources = new HashSet<>();

			Elements elements = new Elements();

			// TRAITEMENT PAGE PRINCIPALE
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%

			Document doc = Jsoup.connect("http://www.exofiches.net/").timeout(10000).get();

			elements = doc.select("table table:gt(3)").select("div");

			for (int i = 1; i < 4; i++)
				liensPagePrincipale.add(elements.get(i).select("a").attr("href"));

			// TRAITEMENT PAGES cycles
			// %%%%%%%%%%%%%%%%%%%%%%%%%%%

			int cpt = 0;
			int minCycle = 0;
			int maxCycle = 1;

			for (String mainLink : liensPagePrincipale)
				{

					System.out.println("**********************\n");
					System.out.println(mainLink + " min cycle :" + minCycle + " max cycle :" + maxCycle);
					System.out.println("**********************\n");

					doc = Jsoup.connect(mainLink).timeout(10000).get();

					elements = doc.select("a");

					for (int i = 0; i < elements.size(); i++)
						{
							String href = elements.get(i).attr("href");

							if ((!href.contains("http")) && (!href.contains("javascript")))
								liensPagesCycles.add("http://www.exofiches.net/" + href);
						}
					// TRAITEMENT PAGES RESSOURCES
					// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%
					System.out.println("\n");
					for (String url : liensPagesCycles)
						{
							doc = Jsoup.connect(url).timeout(10000).get();
							System.out.println("connecté a " + url);
							elements = doc.select("a");

							for (int i = 0; i < elements.size(); i++)
								{

									String href = elements.get(i).attr("href");

									if ((!href.contains("http")) && (!href.contains("javascript")) && (!href.contains("sommaire")))
										{

											String titreRessource = href.replace('_', ' ').replace('-', ' ').replace("PDF/", "").replace(".pdf", "");
											href = "http://www.exofiches.net/" + href;
											System.out.println(href);
											

											Resource r = new Resource(CrawlerService.getSubString(titreRessource, 50), titreRessource, SecurityContext.getUser());
											r.setLanguage(Language.FR);
											r.setFormat(Format.DOC);

											Set<Platform> tempSet = new HashSet<>();
											tempSet.add(Platform.BROWSER);
											r.setPlatforms(tempSet);
											UrlResource urlResource = new UrlResource(CrawlerService.getSubString(titreRessource, 50), href, r);

											ressources.add(r);
											
											resourceRepository.persist(r);
											urlResourceRepository.persist(urlResource);
											r.getShortId();
											
											
										}
								}
						}

					// %%%%%%%%%%%%%% Passage aux Cycles suivants
					liensPagesCycles.clear();
					cpt = cpt + 1;
					minCycle = minCycle + 1;
					maxCycle = maxCycle + 1;
				}
		}
	}
