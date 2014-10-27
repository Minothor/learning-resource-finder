package learningresourcefinder.service;

import java.util.List;

import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.util.Logger;
import learningresourcefinder.web.Slugify;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReSlugifyService {
	@Logger Log log;
	@Autowired CycleRepository cycleRepository;
	@Autowired PlayListRepository playlistRepository;
	@Autowired ResourceRepository resourceRepository;

	public void reslugResources(){
		List<Resource> resourceList = resourceRepository.findAllRessourceOrderByTitle();
		long resourceCount = 0;
		for (Resource resource : resourceList) {
			resource.setSlug(Slugify.slugify(resource.getName()));
			resourceCount++;
		}
		log.info(resourceCount+" Resources ReSlugified");
	}

	public void reslugCycles(){
		//cycles
		int cycleCount = 0;
		List<Cycle> cycleList = cycleRepository.findAllCycles();
		for (Cycle cycle : cycleList) {
			cycle.setSlug(Slugify.slugify(cycle.getDescription()));
			cycleCount++;
		}

		log.info(cycleCount+" Cycles ReSlugified");
	}


	public void reslugPlaylists(){
		//playlists
		long playlistCount = 0;
		List<PlayList> playlistList = playlistRepository.getAllPlayLists();
		for (PlayList playList : playlistList) {
			playList.setSlug(Slugify.slugify(playList.getName()));
			playlistCount++;
		}
		log.info(playlistCount+" Playlists ReSlugified");
	}
}

