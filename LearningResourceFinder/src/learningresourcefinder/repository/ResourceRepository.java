package learningresourcefinder.repository;

import java.util.ArrayList;
import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ResourceRepository extends BaseRepository<Resource>
{
	public Resource getResourceByName(String name) {
		return getSingleOrNullResult(em.createQuery("SELECT r FROM Resource r WHERE r.name =:name")
                .setParameter("name", name));
	}
	
	public List<Resource> findAllResourceByUser(User createdBy) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.createdBy = :createdBy")
				.setParameter("createdBy", createdBy)
				.getResultList();
		
		return results;
	}
	
	public List<Resource> findAllRessourceOrderByTitle () {
		
		List<Resource> result = em.createQuery("SELECT r FROM Resource r ORDER BY r.name ASC")
				.getResultList();
		return result;
	}
	public Long counterResource(Resource resource){
		return (Long)em.createQuery("SELECT  count(r.name) from Resource r WHERE r.name=:resource").getSingleResult();
		
	}
	
	public List<Resource> findFilteredResourcesByIdList(List<Long> idList, SearchOptions searchOptions, int posOfFirstElementPaging, int amountOfElementsPaging){
		if (idList.isEmpty()) { // Defensive coding. An empty list would break the query
			return new ArrayList<>();
		}
		
		List<String> whereConditions = new ArrayList<>();
		
		//// Add Conditions and parameters
		// Language
        if (searchOptions.getLanguage().size() < Language.values().length) {  // else we don't filter on that criteria, we take any value
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
        if (searchOptions.getFormat().size() < Format.values().length) {  // else we don't filter on that criteria, we take any value
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
        if (searchOptions.getPlatform().size() < Platform.values().length) {  // else we don't filter on that criteria, we take any value
            // filter for each selected value
            String whereCondition = "";
            for (Platform platform : searchOptions.getPlatform()) {
                if (!whereCondition.equals("")) {
                    whereCondition += " OR ";
                }
                whereCondition = whereCondition + " r.platform='" + platform.name() + "' ";
            }
            whereConditions.add(whereCondition);
        }

		// Nature
        if (searchOptions.getNature().size() < Nature.values().length) {  // else we don't filter on that criteria, we take any value
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
		
		//// Render the JP-QL statement
		String whereClause = "";
		for (String condition : whereConditions) {
			whereClause += " AND ";
			whereClause += "(" + condition + ")";
		}
		String queryString = "SELECT r FROM Resource r WHERE r.id in (:idList) " + whereClause;  // separate String, to debug.
		

		List<Resource> result = em.createQuery(queryString)
				.setParameter("idList", idList)
				.setFirstResult(posOfFirstElementPaging)
				.setMaxResults(amountOfElementsPaging)
				.getResultList();
		return result;
	}
	
	public List<Resource> findAllResourceWhereProblemByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.topic = :topic AND SIZE(r.problems) > 0")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	public List<Resource> findAllResourceWhereFieldsNullByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE(r.name = null OR r.description = null OR r.language = null OR r.format = null"
				+ " OR r.platform = null OR r.nature = null OR r.numberImage = null) AND r.topic = :topic")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	public List<Resource> findAllResourceWhoNotCompetencesByTopic(Topic topic) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE SIZE(r.competences) = 0 AND r.topic = :topic")
				.setParameter("topic", topic)
				.getResultList();
		return results;
	}
	
	
}