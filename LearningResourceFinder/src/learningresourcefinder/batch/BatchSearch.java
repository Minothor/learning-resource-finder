package learningresourcefinder.batch;

import java.util.List;

import learningresourcefinder.model.User;
import learningresourcefinder.repository.UserRepository;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.search.SearchResult;
import reformyourcountry.search.Searchable;

@Service
public class BatchSearch implements Runnable {

	@Autowired
	SearchService searchService;
	@Autowired
	IndexManagerService indexer;
	@Autowired
	UserRepository uRep;

	public static void main(String[] args) {
		BatchUtil.startSpringBatch(BatchSearch.class);

	}

	@Override
	public void run() {

		// Simple Test

		List<SearchResult> searchList = searchService.search("Mehdi");

		for (SearchResult rs : searchList) {
			if (rs.getEntityClassName().equals("User")) {

				User us = uRep.find(Long.valueOf(rs.getId()));
				System.out.println("FirstName : " + us.getFirstName()
						+ "\nLastName : " + us.getLastName()
						+ "\nMail Adress : " + us.getMail());

			}

		}

	}

}
