package learningresourcefinder.service;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.util.CompetenceNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetenceNodeService {
    
	@Autowired CompetenceRepository competenceRepository;
	
	/** Returns the root of a CompetenceNode tree, which is a copy of the Competence tree, filtered to include only the elements of the list */
	public CompetenceNode buildCompetenceNodeTree(Cycle cycle){
		
		List<Competence> competencesFromCycle = cycle.getComputedCompetences();   // We only want to include these competences.
		Competence root = competenceRepository.findRoot();

		CompetenceNode rootNode = new CompetenceNode(root, null);
		for (Competence child : root.getChildren()) {
			processNode(child, rootNode, 1, competencesFromCycle);
		}

		return null;
	}

	// Recursive method to run through the Competence tree 
	private void processNode(Competence currentCompetence, CompetenceNode parentCompetenceNode, int level, List<Competence> competencesFromCycle) {
		System.out.println("level = " + level);
		if(competencesFromCycle.contains(currentCompetence)) {  // Ok, it's in the list of competences we need to get.
			// We create the corresponding CompetenceNode
			CompetenceNode competenceNode = new CompetenceNode(currentCompetence, parentCompetenceNode);
			System.out.println("Competence processed: " + currentCompetence.getFullName());
			
			// We recursively process the childs.
			for(Competence childCompetence : currentCompetence.getChildren()){
				processNode(childCompetence, competenceNode, level+1, competencesFromCycle);
			}
		}
	}		

	
	public List<CompetenceNode> splitCompetenceNodesInColumns(CompetenceNode root) {
		// TODO
		return null;
	}

}
