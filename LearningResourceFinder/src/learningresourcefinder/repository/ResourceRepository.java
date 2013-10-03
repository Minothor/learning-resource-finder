package learningresourcefinder.repository;

import java.util.ArrayList;
import java.util.List;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
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
	
	public List<Resource> findAllResourceByUser(User author) {
		List<Resource> results = em.createQuery("SELECT r FROM Resource r WHERE r.author = :author")
				.setParameter("author", author)
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
		String whereCondition = "";
		for (Language language : searchOptions.getLanguage()) {
			if (!whereCondition.equals("")) {
				whereCondition += " OR ";
			}
			whereCondition = whereCondition + " r.language='" + language.name() + "' ";
		}
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);

		// Format
		whereCondition = "";
		for (Format format : searchOptions.getFormat()) {
			if (!whereCondition.equals("")) {
				whereCondition += " OR ";
			}
			whereCondition = whereCondition + " r.format='" + format.name() + "' ";
		}
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);
		
		
		// Platform
		whereCondition = "";
		for (Platform platform : searchOptions.getPlatform()) {
			if (!whereCondition.equals("")) {
				whereCondition += " OR ";
			}
			whereCondition = whereCondition + " r.platform='" + platform.name() + "' ";
		}
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);		

		// Nature
		whereCondition = "";
        if(searchOptions.getNature().size() == 4){
            whereCondition = whereCondition + " r.nature is NULL ";        
                }
		for (Nature nature : searchOptions.getNature()) {
			if (!whereCondition.equals("")) {
				whereCondition += " OR ";
			}
			whereCondition = whereCondition + " r.nature='" + nature.name() + "' ";

		}
		

		
        if (!whereCondition.equals("")) whereConditions.add(whereCondition);				
	
		// Advertising
		if (searchOptions.isAdvertising() == false) { // We do not accept advertising (other case is Resource.advertising = true or null)
			whereConditions.add( " r.advertising = FALSE ");
		}
		else{
			whereConditions.add( " r.advertising = FALSE OR r.advertising = TRUE OR r.advertising is NULL ");
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
	
	
}