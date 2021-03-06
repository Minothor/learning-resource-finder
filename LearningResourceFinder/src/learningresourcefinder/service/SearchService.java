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

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SearchService {
	
	@Autowired private IndexManagerService indexManagerService;
	@Autowired private ResourceRepository resourceRepository;
	@Autowired private ResourceService resourceService;	
	@PersistenceContext
	EntityManager em;

	public List<SearchResult> search(String keyWord) {
		List<SearchResult> searchResultList = new ArrayList<SearchResult>();
		if (StringUtils.isNotBlank(keyWord)) {  // if we pass a blank "   " string to Lucine it throws a nullpointerexception.
			keyWord = QueryParser.escape(keyWord); // Else, lucene throws an exception in case the user uses special chars. http://stackoverflow.com/questions/17798300/lucene-queryparser-with-in-query-criteria
			ScoreDoc[] scoreDocs = indexManagerService.search(keyWord);
			for (ScoreDoc sd : scoreDocs) {
				searchResultList.add(new SearchResult(keyWord, indexManagerService
						.findDocument(sd), indexManagerService, em));
			}
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

		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random order from the DB.
		entities = (List<BaseEntity>) resourceService.keepCorrectListOrder(entities, entityIds);
		
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
	
	public List<Resource> getFilteredResources1FromSearchResults(List<SearchResult> searchResults, SearchOptions searchOptions) {
	    // 1. We transform the SearchResults into resource ids
		final List<Long> resourceIds = new ArrayList<>();
		for(SearchResult resource: searchResults){
		    resourceIds.add(resource.getId());
		}

		// 2. We call the method that can handle resource ids.
		return getFilteredResources2FromResourceIds(resourceIds, searchOptions);
	}
	
    public List<Resource> getFilteredResources2FromResourceIds(final List<Long> resourceIds, SearchOptions searchOptions) {
        List<Resource> entities = resourceRepository.findFilteredResourcesByIdList(resourceIds, searchOptions);

		// entities = removeResourcesNotInCompetence(searchOptions, entities); // John 2014-10-27 Why calling this?
		
		// We need to sort the entities to match the order of the searchResults (the first is supposed to be more relevant) instead of the random order from the DB.		
		entities = (List<Resource>) resourceService.keepCorrectListOrder(entities, resourceIds);
	
		return entities;
	}
	

    public List<Resource> getFilteredResources3FromNothing(SearchOptions searchOptions) {
        List<Resource> entities = resourceRepository.findFilteredResourcesByIdList(null, searchOptions);

        //entities = removeResourcesNotInCompetence(searchOptions, entities);  // John 2014-10-27 Why calling this?
        
        return entities;
    }
    
    // FIXME Why does this method exist ?   -- John 2014-10-27
    private List<Resource> removeResourcesNotInCompetence(SearchOptions searchOptions, List<Resource> entities) {
        // We remove the resources not within the specified competence.
        if (searchOptions.getCompetence() != null) {
            List<Resource> result = new ArrayList<>();  // We put the resources that we keep in a new list.
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
        return entities;
    }


}
