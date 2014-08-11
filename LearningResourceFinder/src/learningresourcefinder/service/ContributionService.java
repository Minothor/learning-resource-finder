package learningresourcefinder.service;

import java.util.List;

import learningresourcefinder.model.Contribution;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ContributionRepository;
import learningresourcefinder.util.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {
	
	@Autowired ContributionRepository contributionRepository;
	@Autowired LevelService levelService;

	public Contribution contribute(User user, Resource resource, Action action, String oldValue, String NewValue) 
	{
		Contribution contribution = null;
		List<Contribution> userContributions = contributionRepository.findByUserAndRessource(user, resource, action);

		if (userContributions.size() == 0)
			{
				if (oldValue != NewValue)
					{
						contribution = new Contribution(user, resource, action.getActionPoints(), action);
						contributionRepository.persist(contribution);
						levelService.addActionPoints(user, action);
					}
			}
		return contribution;
	}		
}
