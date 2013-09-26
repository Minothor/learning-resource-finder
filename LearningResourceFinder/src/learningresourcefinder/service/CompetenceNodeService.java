package learningresourcefinder.service;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.util.CompetenceNode;

import org.springframework.stereotype.Service;

@Service
public class CompetenceNodeService {
       
       public CompetenceNode buildCompetenceNodeArbre(Cycle cycle){
          List<Competence> listOfCompetenceFromCycle = cycle.getComputedCompetences(); 
          return null;
       }
       
       public List<CompetenceNode> splitCompetenceNodesInColumns(CompetenceNode root) {
           // TODO
           return null;
       }
       
}
