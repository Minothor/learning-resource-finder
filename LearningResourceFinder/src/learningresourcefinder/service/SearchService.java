package learningresourcefinder.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchResult;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SearchService {

	@Autowired private IndexManagerService indexManagerService;
	@Autowired private ResourceRepository resourceRepository;
	
	@PersistenceContext
	EntityManager em;

	private List<SearchResult> searchResultList;

	public List<SearchResult> search(String keyWord) {
		searchResultList = new ArrayList<SearchResult>();
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
	
}
