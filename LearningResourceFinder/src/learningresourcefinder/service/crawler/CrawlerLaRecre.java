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
public class CrawlerLaRecre {

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UrlResourceRepository urlResourceRepository;
    
    
    public static void crawler(CrawlerService cs) throws IOException{ 
        CrawlerLaRecre cr = new CrawlerLaRecre();
        cr.crawlerFrancais4_7(cs);
        cr.crawlerFrancais8_10(cs);
        cr.crawlerFrancais11_13(cs);
        cr.crawlerGeographie4_7(cs);
        cr.crawlerGeographie8_10(cs);
        cr.crawlerGeographie11_13(cs);
        cr.crawlerHistoire4_7(cs);
        cr.crawlerHistoire8_10(cs);
        cr.crawlerHistoire11_13(cs);
        cr.crawlerLangues8_10(cs);
        cr.crawlerLangues11_13(cs);
        cr.crawlerMath4_7(cs);
        cr.crawlerMath8_10(cs);
        cr.crawlerMath11_13(cs);
        cr.crawlerSciences4_7(cs);
        cr.crawlerSciences8_10(cs);
        cr.crawlerSciences11_13(cs);
        cr.crawlerSecuriteRoutiere4_13(cs);
        cr.crawlerVocabulaire4_7(cs);
        cr.crawlerVocabulaire8_10(cs);
        cr.crawlerVocabulaire11_13(cs);
    }

    public void crawlerMath4_7(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup.connect("http://www.larecre.net/exercices_reload.php?_=1410524431380&min=4&max=7&exo=1&lessons=1&cat=2")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }
    }

    public void crawlerMath8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410524431380&min=8&max=10&exo=1&lessons=1&cat=2")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerMath11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410524431380&min=11&max=13&exo=1&lessons=1&cat=2")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerFrancais4_7(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=7&exo=1&lessons=1&cat=4")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }

    }

    public void crawlerFrancais8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=4")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerFrancais11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=4")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerHistoire4_7(CrawlerService cs) throws IOException {
        
        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=7&exo=1&lessons=1&cat=3")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }

    }

    public void crawlerHistoire8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=3")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerHistoire11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=3")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerVocabulaire4_7(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=7&exo=1&lessons=1&cat=5")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }

    }

    public void crawlerVocabulaire8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=5")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerVocabulaire11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=5")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerSciences4_7(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=7&exo=1&lessons=1&cat=6")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }

    }

    public void crawlerSciences8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=6")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerSciences11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=6")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerGeographie4_7(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=7&exo=1&lessons=1&cat=7")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P1-2");
            }
        }

    }

    public void crawlerGeographie8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=7")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerGeographie11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=7")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

    public void crawlerSecuriteRoutiere4_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=4&max=13&exo=1&lessons=1&cat=8")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P1-2","P5-6");
            }
        }

    }

    public void crawlerLangues8_10(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=8&max=10&exo=1&lessons=1&cat=36")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P3-4","P3-4");
            }
        }

    }

    public void crawlerLangues11_13(CrawlerService cs) throws IOException {

        Document doc1 = Jsoup
                .connect(
                        "http://www.larecre.net/exercices_reload.php?_=1410527270223&min=11&max=13&exo=1&lessons=1&cat=36")
                .timeout(10000).get();
        Element category = doc1.select("h1").first();
        String categorie = category.text();
        System.out.println(categorie);
        Elements lignes = doc1.select("ul > li > a.titre_exo , h2");
        for (Element ligne : lignes) {
            if (ligne.attr("href").equals("")) {
                String subcategori = ligne.text();
                System.out.println(subcategori);
            } else {
                String titre = ligne.text();
                String lien = ligne.attr("href");
                System.out.println(titre + " : " + lien);
                cs.persistRessource(titre,lien,categorie,"",0,"P5-6","P5-6");
            }
        }

    }

//    public static void main(String[] args) throws IOException {
//        CrawlerLaRecre cs = new CrawlerLaRecre();
//        cs.crawlerLangues11_13(null);
//    }
}
