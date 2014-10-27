package learningresourcefinder.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.Favorite;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.model.User;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ResourceRepository extends BaseRepository<Resource> {
    
	public Resource getResourceByName(String name) {
		return getSingleOrNullResult(em.createQuery("SELECT r FROM Resource r WHERE r.name =:name")
                .setParameter("name", name));
	}
	
	public List<Resource> findAllResourceByUser(User createdBy) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.createdBy = :createdBy ORDER BY r.createdOn DESC")
				.setParameter("createdBy", createdBy)
				.getResultList();
		
		return results;
	}
	
	public List<Resource> findAllRessourceOrderByTitle () {
		
		List<Resource> result = em.createQuery("SELECT r FROM Resource r ORDER BY r.name ASC")
				.getResultList();
		return result;
	}
	
	
	/** @param idList can be null if we don't want to limit the query to some resources */
	public List<Resource> findFilteredResourcesByIdList(List<Long> idList, SearchOptions searchOptions){
	    if (idList != null && idList.isEmpty()) { // Defensive coding. An empty list would break the query
			return new ArrayList<>();
		}
	    
		List<String> whereConditions = new ArrayList<>();
		Map<String, Object> parameterMap = new HashMap<>();
		
		//// Add Conditions and parameters
		// Language
        if (searchOptions.getLanguage().size() < Language.values().length
                && searchOptions.getLanguage().size() !=0) {  // else we don't filter on that criteria, we take any value
            // filter for each selected value
            String whereCondition = "";
            for (Language language : searchOptions.getLanguage()) {
                if (!whereCondition.equals("")) {
                    whereCondition += " OR ";
                }
                whereCondition += " r.language='" + language.name() + "' ";
            }
            if (searchOptions.getLanguage().size() == 1 && searchOptions.getLanguage().get(0).equals(Language.FR)) {  // If FR is the only selected language (usual case)
                whereCondition += " OR r.language is null ";  // We include all resources having an undefined language. 
            }
            whereConditions.add(whereCondition);
        }

		// Format
        if (searchOptions.getFormat().size() < Format.values().length
                && searchOptions.getFormat().size() !=0) {  // else we don't filter on that criteria, we take any value
            // filter for each selected value
            String whereCondition = "";
            for (Format format : searchOptions.getFormat()) {
                if (!whereCondition.equals("")) {
                    whereCondition += " OR ";
                }
                whereCondition = whereCondition + " r.format='" + format.name() + "' ";
            }
            whereConditions.add(whereCondition);
        }

		// Platform
        if (searchOptions.getPlatform().size() < Platform.values().length
                && searchOptions.getPlatform().size() !=0) {  // else we don't filter on that criteria, we take any value
            // filter for each selected value
            String whereCondition = "";
            for (Platform platform : searchOptions.getPlatform()) {
                if (!whereCondition.equals("")) {
                    whereCondition += " OR ";
                }
                whereCondition = whereCondition + " r.platformsCollOnString like '%" + platform.name() + "%' ";
            }
            whereConditions.add(whereCondition);
        }

		// Nature
        if (searchOptions.getNature().size() < Nature.values().length
                && searchOptions.getNature().size() !=0) {  // else we don't filter on that criteria, we take any value
            // filter for each selected value
            String whereCondition = "";
            for (Nature nature : searchOptions.getNature()) {
                if (!whereCondition.equals("")) {
                    whereCondition += " OR ";
                }
                whereCondition = whereCondition + " r.nature='" + nature.name() + "' ";
            }
            whereConditions.add(whereCondition);
        }
	
		// Advertising
		if (Boolean.FALSE.equals(searchOptions.isAdvertising())) { // We do not accept advertising (other case is Resource.advertising = true or null)
			whereConditions.add( " r.advertising = FALSE ");
		}
		
		// Max Duration
		if (searchOptions.getMaxDuration() != null) {
			whereConditions.add(" r.duration<='" + searchOptions.getMaxDuration().intValue() + "' ");
		}
		
		
		if (idList != null) {
		    whereConditions.add("r.id in (:idList)");
		    parameterMap.put("idList", idList);
		}
		
		//// Render the JP-QL statement
		String whereClause = "";
		boolean firstLoop = true;
		for (String condition : whereConditions) {
			if (firstLoop) {
			    firstLoop = false;
			} else {			    
			    whereClause += " AND ";
			}
			whereClause += "(" + condition + ")";
		}
		
		
		
		
		String queryString = "SELECT r FROM Resource r WHERE " + whereClause + " ORDER BY r.popularity DESC NULLS LAST";
		
	
		
        Query query = em.createQuery(queryString);
        for (String paramName : parameterMap.keySet()) {
            query.setParameter(paramName, parameterMap.get(paramName));
        }
        
		List<Resource> result = query
				.getResultList();
		return result;
	}
	
	
	
	public List<Resource> findAllResourceWhereProblemByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.topic = :topic AND SIZE(r.problems) > 0 ORDER BY r.popularity DESC")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	public List<Resource> findAllResourceWhereFieldsNullByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE(r.name = null OR r.description = null OR r.language = null OR r.format = null"
				+ " OR r.platformsCollOnString = null OR r.nature = null OR r.numberImage = null) AND r.topic = :topic ORDER BY r.popularity DESC")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	public List<Resource> findAllResourceWhoNotCompetencesByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE SIZE(r.competences) = 0 AND r.topic = :topic ORDER BY r.popularity DESC")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	public List<Resource> findAllResourceWhoNoChildrenValidationByTopic(Topic topic) {
        List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE (r.validationStatus=null OR  r.validationStatus!=:validationStatus) AND r.topic = :topic ORDER BY r.popularity DESC")
                .setParameter("topic", topic)
                .setParameter("validationStatus", ValidationStatus.ACCEPT)
                .getResultList();
        return results;
    }

    public List<Long> findIdsByCompetence(Competence competence) {
        List<Competence> allCompetences = competence.getChildrenAndSubChildren();
        allCompetences.add(competence);
        
        List<Long> results = em.createQuery("SELECT r.id FROM Resource r join r.competences c WHERE c in (:allCompetences) ORDER BY r.popularity DESC")
                .setParameter("allCompetences", allCompetences)
                .getResultList();
        return results;
    }
    

    public List<Resource> findLastResources(int maxAmount) {
        List<Resource> results = em.createQuery("SELECT r FROM Resource r  ORDER BY r.createdOn DESC")
                .setMaxResults(maxAmount)              
                .getResultList();
        return results;
    }

    public long countResources() {
            return (Long)em.createQuery("SELECT  count(r) from Resource r ").getSingleResult();
    }
   
    public long countResourcesByCompetenceAndCycle(Long competenceId, Long cycleId) {
        return (Long)em.createQuery("SELECT count(r) from Resource r join r.competences c WHERE c.id = :competenceId AND r.minCycle.id <= :cycleId and :cycleId <=  r.maxCycle.id")
                .setParameter("competenceId", competenceId)
                .setParameter("cycleId", cycleId)
                .getSingleResult();
    }
  
    public List<Resource> findTop5ResourcesByCycleAndPopularity(Cycle cycle){    
        List<Resource> topResources = new ArrayList<>() ;
        
        Topic[] topics={Topic.FRENCH,Topic.MATH,Topic.HISTORY,Topic.GEO,Topic.SCIENCE};
        
        for (Topic topic : topics) {

            Resource topResource = findTopResourceByTopPopularity(cycle,topic);
            
            if(topResource!= null){                
                topResources.add(topResource);   
            }
              
        }
        
        return topResources;
    }        
   
    
   public Resource findTopResourceByTopPopularity(Cycle cycle,Topic topic){  
       String whereClause = "r.minCycle.id <= :cycle and :cycle <=  r.maxCycle.id and r.topic = :topic";
       
       List<Resource> resourceList = (List<Resource>)em.createQuery("select r from Resource r where " + whereClause +
                " and r.popularity = (select max(r.popularity) from Resource r where " + whereClause + ")")      
               .setParameter("topic",topic)
               .setParameter("cycle", cycle.getId())
               .setMaxResults(1)
               .getResultList();
       
       if (resourceList != null && resourceList.size() > 0) {
           return resourceList.get(0);
       } else {
           return null;
       }
       
   }
   
   public List<Resource> findResourceByCycleAndPopularity (Long id , String sortCriteria) {
       List<Resource> resourceListByCycle = (List<Resource>)em.createQuery("select r from Resource r where r.minCycle.id <= :id and :id <= r.maxCycle.id "
               + "order by r." + ("popularity".equals(sortCriteria) ? "popularity" : "createdOn"))
               .setParameter("id", id)
               .getResultList();
       return resourceListByCycle;
   }

   public List<Resource> findLastResourceByCycle (Long id , int maxAmount){
       List<Resource> lastResourceListByCycle = (List<Resource>)em.createQuery("select r from Resource r where r.minCycle.id <= :id and :id <= r.maxCycle.id ORDER BY r.createdOn DESC")
               .setParameter("id", id)
               .setMaxResults(maxAmount)              
               .getResultList();
       return lastResourceListByCycle;
   }
   public List<String> findAllResourceName() {
	   return em.createQuery("select r.name from Resource r order by r.popularity desc").getResultList();
   }
   
   public List<Resource> findResourcebyIdList (List<Long> idList) {
       List<Resource> resourceList = (List<Resource>) em.createQuery("SELECT r FROM Resource r WHERE r.id in :idList")
               .setParameter("idList", idList)
               .getResultList();
       return resourceList;
   }
  
}