package learningresourcefinder.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchResult;

import org.apache.lucene.search.ScoreDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SearchService {

	@Autowired
	private IndexManagerService indexManagerService;
	@Autowired ResourceRepository resourceRepository;
	
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
	
	public List<Resource> getFirstResources(List<SearchResult> searchResults, int maxResult) {
		List<Long> resourceIds = new ArrayList<>();
		
		for(SearchResult searchResult : searchResults){
			if (resourceIds.size() == maxResult) {
				break;
			}
			resourceIds.add(searchResult.getId());
		}
		
		return resourceRepository.findResourcesByIdList(resourceIds);
	}
	
}
