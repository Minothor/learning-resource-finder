package learningresourcefinder.service.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ImportService;
import learningresourcefinder.web.UrlUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CrawlerKhanAcademy
	{
		@Autowired
		ResourceRepository resourceRepository;
		@Autowired
		UrlResourceRepository urlResourceRepository;

		Elements elements = new Elements();
		List<String> urlsection = new ArrayList<>();
		List<String> urlSecondSection = new ArrayList<>();
		List<String> urlResources = new ArrayList<>();
		String khanAcademy = "https://fr.khanacademy.org";

		String section;

		public void crawler() throws IOException, ParseException {


			/* MATHEMATIQUES */
			 section = "math";
			
			 getUrlSection();
			 geturlSecondSections(urlsection);
			 getRessourcesUrl();
			 getResources(urlResources);
			 System.out.println(section + "FINISH !!!!!!!!!!!");
			
			 urlsection.clear();
			 urlSecondSection.clear();

			/* SCIENCES */
			 section = "science";
			
			 urlsection.add(khanAcademy + "/science/biology");
			 urlsection.add(khanAcademy + "/science/chemistry");
			 urlsection.add(khanAcademy + "/science/cosmology-and-astronomy");
			 urlsection.add(khanAcademy + "/science/physics");
			 urlsection.add(khanAcademy + "/science/organic-chemistry");
			 urlsection.add(khanAcademy + "/science/health-and-medicine");
			 geturlSecondSections(urlsection);
			 getRessourcesUrl();
			 getResources(urlResources);
			 urlsection.clear();
			 urlSecondSection.clear();
			 System.out.println(section + "FINISH !!!!!!!!!!!");

		}

		private List<String> getUrlSection() throws IOException {
			Document doc = Jsoup.connect(khanAcademy + "/" + section).timeout(0).get();

			elements = doc.select("a");
			int j = 0;

			for (int i = 0; i < elements.size(); i++)
				{
					String href = khanAcademy + elements.get(i).attr("href");

					if ((href.contains("/" + section + "/")) && (!urlsection.contains(href)) && (!href.equals(khanAcademy + "/" + section + "/early-math")))
						{
							j++;
							urlsection.add(href);
						}
				}
			return urlsection;
		}

		private List<String> geturlSecondSections(List<String> urls) throws IOException {

			System.out.println("\n");
			for (String url : urls)
				{
					String matiere = url.replace(khanAcademy + "/" + section + "/", "");
					// System.out.println(matiere);

					Document doc = Jsoup.connect(khanAcademy + "/" + section + "/" + matiere).timeout(0).get();

					elements = doc.select("a");

					for (int i = 0; i < elements.size(); i++)
						{
							String href = elements.get(i).attr("href");

							if ((href.contains(section + "/" + matiere)) && (!href.trim().equals("/" + section + "/" + matiere)) && (!href.trim().equals("/" + section + "/" + matiere + "/d")))
								{
									urlSecondSection.add(elements.get(i).attr("href"));
									// System.out.println(elements.get(i).attr("href"));

								}
						}

				}
			return urlSecondSection;
		}

		private void getRessourcesUrl() throws IOException {

			for (String url : urlSecondSection)
				{
					String matiere = url.replace("/" + section + "/" + url, "");

					System.out.println("****************************************************");
					System.out.println("URL :" + khanAcademy + "/" + section + "/" + url);
					System.out.println("****************************************************");
					Document doc = Jsoup.connect(khanAcademy + "/" + section + "/" + url).timeout(0).get();

					elements = doc.select("a");

					for (int i = 0; i < elements.size(); i++)
						{
							String href = elements.get(i).attr("href");

							if ((href.contains(url)) && (!href.equals(url)))
								{
									String urlresource = khanAcademy + href;
									System.out.println(urlresource);
									urlResources.add(urlresource);

								}
						}
				}

		}

		private void getResources(List<String> resources) throws IOException, ParseException {

			int cpt = 0;
			String temporyTitle = "";

			for (String url : resources)
				{
					Document doc = Jsoup.connect(url).timeout(0).get();
					boolean youtube = false;

					String title = CrawlerService.getSubString(doc.title().replace("| Khan Academy", ""), 50);

					if (!title.equals(temporyTitle))
						{
							System.out.println("Create resouce for " + url);
							cpt++;

							Elements elements = doc.select("meta").select("[content*=youtube]");

							if (elements.size() > 0) // LIEN YOUTUBE
								createYoutubeResource(doc.select("meta").select("[property*=og:video]").attr("content"));
							else
								{
									Resource r = new Resource(title, title, SecurityContext.getUser());
									r.setFormat(Format.INTERACTIVE);
									r.setLanguage(Language.FR);
									Topic topic = CrawlerService.getTopicFromString(section);
									r.setTopic(topic);
									
									Set<Platform> tempSet = new HashSet<>();
									tempSet.add(Platform.BROWSER);
									r.setPlatforms(tempSet);
									UrlResource urlResource = new UrlResource(CrawlerService.getSubString(title, 50), url, r);

									resourceRepository.persist(r);
									urlResourceRepository.persist(urlResource);
									r.getShortId();

								}
						}
					temporyTitle = title;
				}
		}

		private void createYoutubeResource(String url) throws IOException, ParseException {

			System.out.println("Youtube " + url);

			if (UrlUtil.getYoutubeVideoId(url) != null)
				{
					String youTubeServiceUrl = "https://gdata.youtube.com/feeds/api/videos/" + UrlUtil.getYoutubeVideoId(url) + "?v=2&alt=json";
					URL myURL = new URL(youTubeServiceUrl);
					URLConnection urlConnection = myURL.openConnection();
					BufferedReader youTubeAnswerReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

					JSONParser jsonParser = new JSONParser();
					String inputLine = youTubeAnswerReader.readLine();
					JSONObject rootJsonObject = (JSONObject) jsonParser.parse(inputLine);

					JSONObject entryJSonObject = (JSONObject) rootJsonObject.get("entry");
					JSONObject titleJSonObject = (JSONObject) entryJSonObject.get("title");

					String title = (String) titleJSonObject.get("$t");

					JSONObject mediaGroupJSonObject = (JSONObject) entryJSonObject.get("media$group");

					JSONObject mediaDescrJSonObject = (JSONObject) mediaGroupJSonObject.get("media$description");
					String description = (String) mediaDescrJSonObject.get("$t");

					User user = SecurityContext.getUser();
					title = CrawlerService.getSubString(title, 50);
					Resource r = new Resource(title, description, user);
					r.setFormat(Format.VIDEOS);
					r.setLanguage(Language.FR);
					Topic topic = CrawlerService.getTopicFromString(section);
					r.setTopic(topic);
					Set<Platform> tempSet = new HashSet<>();
					tempSet.add(Platform.BROWSER);
					r.setPlatforms(tempSet);
					UrlResource urlResource = new UrlResource(title, url, r);

					resourceRepository.persist(r);
					urlResourceRepository.persist(urlResource);
					r.getShortId();

				}

		}
	}
