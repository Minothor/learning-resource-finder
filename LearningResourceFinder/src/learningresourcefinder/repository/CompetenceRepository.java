package learningresourcefinder.repository;

import java.util.List;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class CompetenceRepository extends BaseRepository<Competence> {

	public List<Resource> findResourceByCompetenceAndSubs(Competence startCompetence) {
		// 1. We recursively collect all the sub-program points
		List<Competence> competences = startCompetence.getChildrenAndSubChildren();
		competences.add(startCompetence);

		// 2. We query
		List<Resource> result = em
				.createQuery("SELECT distinct r FROM Resource r join r.competences c WHERE c in (:competences)")
				.setParameter("competences", competences)
				.getResultList();

		return result;
	}


	public Competence findByCode(String code){
		return getSingleOrNullResult(
				em.createQuery("select c from Competence c where lower(c.code) = :code")
	                .setHint("org.hibernate.cacheable", true) 
	                .setParameter("code",code.toLowerCase())
				);
	}

	public Competence findRoot(){
		return    (Competence) em.createQuery("select c from Competence c where c.parent is null ")
                  .setHint("org.hibernate.cacheable", true) 
		          .getSingleResult();  // Will throw an excpetion if no root found.
	}


	public boolean isThereAnotherCompetenceWithThatCode(Long editedCompetenceId, String newCode) {
		
		List<Competence> result = em
				.createQuery("select c from Competence c where lower(c.code) = :code")
				.setParameter("code", newCode.toLowerCase())
				.getResultList();
		
		switch (result.size()){
			case 0: return true;
			case 1: {
			    Competence competence = result.get(0);
			    if (competence.getId().equals(editedCompetenceId) ) {  // It's the same competence as the one we are editing.
			        return true;
			    } else {  // Another competence has that code already.
			        return false;
			    }
			}
			default : return false;  // Should never happen: the code is an unique DB column.
		}
	}


	public boolean getIfParentExist(Long parentIdCompetence) {
		
		return (em
				.createQuery("select c from Competence c where c.id = :id")
				.setParameter("id", parentIdCompetence)
				.getResultList()).size() > 0;
	}


	public boolean getIfCodeExist(String codeCompetence) {
	
		return (em
				.createQuery("select c from Competence c where lower(c.code) = :code")
				.setParameter("code", codeCompetence.toLowerCase())
				.getResultList()).size() != 0;
	}
	
    public List<Competence> getCompetencesByCycle(Long cycleId) {  
        // As link between cycle and competence has been broken, we must now retrieve resources by cycle and then categories attached to these resources  
        List<Competence> listCompetence = em.createNativeQuery(
                        "select * from Competence where id in (select distinct competences_id from resource_competence where resource_id in " +
                        "(select id from resource where mincycle_id <= ? and maxcycle_id >= ?))", Competence.class)
                        .setParameter(1, cycleId)
                        .setParameter(2, cycleId)
                        .getResultList();
        return listCompetence;
   }

   public List<String> getAllcategoryName() {
	   		List<String> allCategoryName = em.createQuery("select c.name from Competence c").getResultList();
	   return allCategoryName;
   } 

}
