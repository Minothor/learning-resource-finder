package learningresourcefinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchResult;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SearchService {

	final int RESOURCES_PAR_SEARCH_PAGE = 5*20;

	
	@Autowired private IndexManagerService indexManagerService;
	@Autowired private ResourceRepository resourceRepository;
	
	@PersistenceContext
	EntityManager em;

	

	public List<SearchResult> search(String keyWord) {
		List<SearchResult> searchResultList = new ArrayList<SearchResult>();
		ScoreDoc[] scoreDocs = indexManagerService.search(keyWord);
		for (ScoreDoc sd : scoreDocs) {
			searchResultList.add(new SearchResult(keyWord, indexManagerService
					.findDocument(sd), indexManagerService, em));
		}
		return searchResultList;
	}
	
	public List<BaseEntity> getFirstEntities(List<SearchResult> searchResults, int maxResult, Class<? extends BaseEntity> clazz) {
		final List<Long> entityIds = new ArrayList<>();
		
		for(SearchResult searchResult : searchResults) {
			if (searchResult.isForClass(clazz)) {
				entityIds.add(searchResult.getId());
			}
			if (entityIds.size() >= maxResult) {
				break;
			}	
		}

		List<BaseEntity> entities = findEntitiesByIdList(entityIds, clazz);
		
		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random ordrer from the DB.
		Collections.sort(entities, new Comparator<BaseEntity>() {
			@Override	public int compare(BaseEntity arg0, BaseEntity arg1) {
				return (new Integer((entityIds.indexOf(arg0.getId())))).compareTo( new Integer(entityIds.indexOf(arg1.getId())));
			}
		});
		
		return entities;
	}
	

	//*********************Ressource pagination
	public List<BaseEntity> getTotalEntities(List<SearchResult> searchResults, Class<? extends BaseEntity> clazz) {
		final List<Long> entityIds = new ArrayList<>();
		
		
		//List<Resource> resources =(List) searchService.getFirstEntities(searchService.search(searchResource), 5, Resource.class);
		
		for(SearchResult searchResult : searchResults) {
			if (searchResult.isForClass(clazz)) {
				entityIds.add(searchResult.getId());
			}
//			if (entityIds.size() >= maxResult) {
//				break;
//			}	
		}

		List<BaseEntity> entities = findEntitiesByIdList(entityIds, clazz);

		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random ordrer from the DB.
		Collections.sort(entities, new Comparator<BaseEntity>() {
			@Override	public int compare(BaseEntity arg0, BaseEntity arg1) {
				return (new Integer((entityIds.indexOf(arg0.getId())))).compareTo( new Integer(entityIds.indexOf(arg1.getId())));
			}
		});

		return entities;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BaseEntity> findEntitiesByIdList(List<Long> idList, Class clazz){
		if(idList.size() == 0){  // If we give an empty list, postGreSQL does not like the query... (Exception).
			return new ArrayList<BaseEntity>();
		}
		String jpql = "SELECT e FROM "+clazz.getSimpleName()+" e WHERE e.id in (:idList)";
		List<BaseEntity> result = em.createQuery(jpql)
				.setParameter("idList", idList)
				.getResultList();
		return result;
	}
	
	
	public List<Resource> getFilteredResources(List<SearchResult> searchResults, int page, SearchOptions searchOptions) {
		final List<Long> resourceIds = new ArrayList<>();
		
		// Pages : foireux (il faut faire cela après le filtre sur les otpions....) --- John 2013-09-26
//		int begin = Math.min(searchResults.size()-1, (page-1) * RESOURCES_PAR_SEARCH_PAGE);
//		int end = Math.min(searchResults.size()-1,    page * RESOURCES_PAR_SEARCH_PAGE);
//		
//		for (int i = begin; i<end; i++){
//			resourceIds.add(searchResults.get(i).getId());
//		}

		List<Resource> entities = resourceRepository.findFilteredResourcesByIdList(resourceIds, searchOptions);

		// We remove the resources not within the specified competence.
		if (searchOptions.getCompetence() != null) {
			List<Resource> result = new ArrayList<>();
			for (Resource resource : entities) {
				for (Competence compOfResource :  resource.getCompetences()) {
					if (compOfResource.isOrIsChildOrSubChildOf(searchOptions.getCompetence())) {
						result.add(resource);
						break;  // get out inner loop and continue with the next resource.
					}
				}
			}
			entities = result;
		}
		
		
		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random ordrer from the DB.
		Collections.sort(entities, new Comparator<BaseEntity>() {
			@Override	public int compare(BaseEntity arg0, BaseEntity arg1) {
				return (new Integer((resourceIds.indexOf(arg0.getId())))).compareTo( new Integer(resourceIds.indexOf(arg1.getId())));
			}
		});
		
		return entities;
	}
	



}
