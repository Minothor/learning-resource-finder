package learningresourcefinder.batch;

import java.io.IOException;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.util.NotificationUtil;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class ImportCompetencesFromVraiForumBatch implements Runnable {

    @Autowired  CompetenceRepository competenceRepository;
    @Autowired  CycleRepository cycleRepository;
    
    int ident;
    Cycle p2;
    Cycle p4; 
    Cycle p6;

    public static void main(String[] args) {

        BatchUtil.startSpringBatch(ImportCompetencesFromVraiForumBatch.class);

    }

    @Override
    public void run() {
//        Document indexDocument = connectAndGetDocument("index.php");
//        Elements level3Elements = indexDocument.select(".postlink");
//        for (Element element : level3Elements) {
//            processPage(element.attr("href"));
//        }
        
        p2 = cycleRepository.findByName("P1-2");
        p4 = cycleRepository.findByName("P3-4");
        p6 = cycleRepository.findByName("P5-6");

        
        Competence competence;
        competence = competenceRepository.findByCode("FF-SL");
        competence.setVraisForumPage("f8-Savoir-Lire.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FF-SE");
        competence.setVraisForumPage("f9-Savoir-Ecrire.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FF-SP");
        competence.setVraisForumPage("f10-Savoir-Parler.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FF-SEC");
        competence.setVraisForumPage("f12-Savoir-Ecouter.htm");
        processLevel2Page(competence);
        

        competence = competenceRepository.findByCode("FM-N");
        competence.setVraisForumPage("f13-Nombres.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FM-SF");
        competence.setVraisForumPage("f14-Solides-et-figures.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FM-G");
        competence.setVraisForumPage("f15-Grandeurs.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FM-TD");
        competence.setVraisForumPage("f16-Traitement-des-donnees.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-1");
        competence.setVraisForumPage("f18-Histoire.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-2");
        competence.setVraisForumPage("f19-Geographie.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-3");
        competence.setVraisForumPage("f248-HG-IV-Rechercher-utiliser-des-reperes-et-des-representations-du-temps-br.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-4");
        competence.setVraisForumPage("f186-Savoir-faire-communs-histoire-geo.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-5");
        competence.setVraisForumPage("f249-HG-V-Rechercher-utiliser-des-reperes-et-des-representations-de-l-espace-br.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-6");
        competence.setVraisForumPage("f250-HG-VI-Rechercher-lire-et-exploiter-un-paysage-ou-une-image-geographique-br.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FHG-7");
        competence.setVraisForumPage("f251-HG-VII-Structurer-les-resultats-les-valider-et-les-exploiter-br.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FES-1");
        competence.setVraisForumPage("f21-sciences.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FES-2");
        competence.setVraisForumPage("f191-Les-savoirs.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FES-3");
        competence.setVraisForumPage("f246-C-III-Rechercher-recolter-des-infos-par-la-recherche-documentaire-et-la-consultation-de-personnes-ressources-br.htm");
        processLevel2Page(competence);

        competence = competenceRepository.findByCode("FES-4");
        competence.setVraisForumPage("f247-C-IV-Structurer-les-resultats-les-communiquer-les-valider-les-synthetiser-br.htm");
        processLevel2Page(competence);
        

}

    public void processLevel2Page(Competence competence) {
        ident++;
        
        
        Document document = connectAndGetDocument(competence.getVraisForumPage());
        printlnWithIdent(competence);

        Elements linksToSubTopics = document.select("a.topictitle");
        for (Element linkToSubTopic : linksToSubTopics) {
            String pageName = linkToSubTopic.attr("href");
            if (StringUtils.isBlank(pageName)) {
                break;  // Special case (the rest of the links do not lead to interesting pages)
            }
            processPage(competence, pageName);
        }
        
        ident--;
    }

    
    
    public void processPage(Competence parentCompetence, String pageName) {
        ident++;

        Document document = connectAndGetDocument(pageName);
        
        // Extract name and code
        Element titleElement = document.select("a.maintitle").first();
        if (titleElement == null)   {
            printlnWithIdent("problemo: " + pageName);
        }
            
        String titleWithCode = titleElement.text();
        String code = null;
        String title = null;
        Cycle cycle = null;
        if (titleWithCode.toUpperCase().endsWith("P2")) {
            code = titleWithCode;
            cycle = p2;
            title = parentCompetence.getCode() +  " pour la 1e et 2e primaire";
        } else if (titleWithCode.toUpperCase().endsWith("P4")) {
            code = titleWithCode;
            cycle = p4;
            title = parentCompetence.getCode() +  " pour la 3e et 4e primaire";
        } else if (titleWithCode.toUpperCase().endsWith("P6")) {
            code = titleWithCode;
            cycle = p6;
            title = parentCompetence.getCode() +  " pour la 5e et 6e primaire";
        } else {  // Normal case, i.e.: L7-Percevoir les interactions entre les éléments verbaux et non verbaux 
            int posMinus = titleWithCode.indexOf("-");
            if (posMinus > 0) {  
                code = titleWithCode.substring(0, posMinus).trim();
                title = titleWithCode.substring(posMinus+1).trim();
            } else {
                throw new RuntimeException("Problem posMinus = " + posMinus + " for titleWithCode = " + titleWithCode);
            }
        }
        
               
        
        // Find or create competence from/in DB
        Competence competence = competenceRepository.findByCode(code);
        if (competence == null) {
            competence = new Competence();
        }
        competence.setCode(code);
        competence.setName(title);
        if (cycle != null) {
            competence.setCycle(cycle);
        }
        competence.setVraisForumPage(pageName);
        if (competence.getId() == null) {
            competenceRepository.persist(competence);
        }
        if (competence.getParent() == null) {
            parentCompetence.addChild(competence);
        }
        printlnWithIdent(competence);

        // Children
        Elements linksToSubTopics = document.select("a.topictitle");
        for (Element linkToSubTopic : linksToSubTopics) {
            processPage(competence, linkToSubTopic.attr("href"));
        }
        
        ident--;
    }

    public Document connectAndGetDocument(String pageName) {
        while (true) {
            try {
                return Jsoup.connect("http://findecycle.vraiforum.com/" + pageName).get();
            } catch (IOException e) {
                // Wait up to 60 seconds (to not trigger forum's security)
                try {
                    long ms = Math.round(60000 * Math.random());
                    printlnWithIdent("Waiting "+ms+"ms...");
                    Thread.sleep(ms);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                } 
            }
        }
    }

    private void printlnWithIdent(Competence competence) {
        printlnWithIdent(competence.getCode() + " - " + competence.getName());
    }

    private void printlnWithIdent(String s) {
        for (int i=0; i<ident; i++) {
            System.out.print("   ");
        }
        System.out.println(s);

    }

}
