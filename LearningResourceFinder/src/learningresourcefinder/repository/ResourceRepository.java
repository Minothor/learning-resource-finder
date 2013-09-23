package learningresourcefinder.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.search.SearchResult;

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
	
	public List<BaseEntity> getFilteredEntities(List<SearchResult> searchResults, int page, SearchOptions searchOptions) {
		final List<Long> entityIds = new ArrayList<>();
		final int resourcePerPage = 1;
		int begin = (page-1) * resourcePerPage;
		int end = page * resourcePerPage;
		
		for (int i = begin; i<end; i++){
				entityIds.add(searchResults.get(i).getId());
		}

		List<BaseEntity> entities = findFilteredEntitiesByIdList(entityIds, searchOptions);
		
		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random ordrer from the DB.
		Collections.sort(entities, new Comparator<BaseEntity>() {
			@Override	public int compare(BaseEntity arg0, BaseEntity arg1) {
				return (new Integer((entityIds.indexOf(arg0.getId())))).compareTo( new Integer(entityIds.indexOf(arg1.getId())));
			}
		});
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public List<BaseEntity> findFilteredEntitiesByIdList(List<Long> idList, SearchOptions searchOptions){
		Map<String, Object> parameters = new HashMap<>();
		List<String> whereConditions = new ArrayList<>();
		
		//// Add Conditions and parameters
		
		// Language
		boolean first = true;
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
		for (Nature nature : searchOptions.getNature()) {
			if (!whereCondition.equals("")) {
				whereCondition += " OR ";
			}
			whereCondition = whereCondition + " r.nature='" + nature.name() + "' ";
		}
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);				
	
		// Advertising
		whereCondition = "";
		whereCondition += " r.advertising='" + searchOptions.isAdvertising() + "' ";
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);
		
		
		// Max Duration
		whereCondition = "";
		if (!(searchOptions.getMaxDuration() == null)) whereCondition += " r.duration<='" + searchOptions.getMaxDuration().intValue() + "' ";
		if (!whereCondition.equals("")) whereConditions.add(whereCondition);
		
		
		
		//// Render the JP-QL statement
		String whereClause = "";
		for (String condition : whereConditions) {
			whereClause += " AND ";
			whereClause += "(" + condition + ")";
		}
		

		
		List<BaseEntity> result = em.createQuery("SELECT r FROM Resource r WHERE r.id in(:idList) " + whereClause)
				.setParameter("idList", idList)
				.getResultList();
		return result;
	}
}