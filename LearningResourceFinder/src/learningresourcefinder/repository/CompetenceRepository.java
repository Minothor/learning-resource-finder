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
				.setParameter("code",code.toLowerCase())
				);
	}

	public Competence findRoot(){
		return    (Competence) em.createQuery("select c from Competence c where c.parent is null ").getSingleResult();
	}


	public boolean getIfCompetenceCodeExistOrIsCurrentlyBeingEdited(Long id,String codeCompetence) {
		
		List<Competence> result = em
				.createQuery("select c from Competence c where lower(c.code) = :code")
				.setParameter("code", codeCompetence.toLowerCase())
				.getResultList();
		
		switch (result.size()){
			case 0: return true;
			case 1: if (result.get(0).getId() == id ) {return true;}else{return false;}
			default : return false;
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


}
