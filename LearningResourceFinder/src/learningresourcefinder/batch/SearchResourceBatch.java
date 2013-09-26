package learningresourcefinder.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.search.SearchResult;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SearchResourceBatch implements Runnable{

	@Autowired SearchService searchService;
	@Autowired IndexManagerService indexManagerService;
	@Autowired ResourceRepository resourceRepository;

	public static void main(String[] args){

		BatchUtil.startSpringBatch(SearchResourceBatch.class);


	}

	//	@Override
	//	public void run() {	
	//		indexService.createIndexes();
	//		List<SearchResult> liste = searchService.search("Matématique");
	//		for(SearchResult result : liste){
	//			if(result.getEntityClassName().equals("Resource")){
	//				Resource resource = resourceRepository.find(result.getId());
	//				System.out.println(resource);
	//			} else {
	//				System.out.println(result.getEntityClassName() + " NOK");
	//			}
	//			
	//		}
	//	}

	@Override
	public void run() {	
		indexManagerService.removeIndexes();
		indexManagerService.createIndexes();
		
		List<SearchResult> list = searchService.search("math");
		
		for (SearchResult sr : list) {
			System.out.println(sr);
		}
		
		
		
		
		
		

}
}
