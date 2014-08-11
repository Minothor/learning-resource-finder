package learningresourcefinder.service;

import java.util.List;

import learningresourcefinder.model.Contribution;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.ContributionRepository;
import learningresourcefinder.util.Action;

import org.springframework.beans.factory.annotation.Autowired;

public class ContributionService
	{
@Autowired ContributionRepository contributionRepository;
		
		public Contribution contribute(User user, Resource resource, Action action, String oldValue, String NewValue) 
		{
			  List<Contribution> userContributions = contributionRepository.findByUserAndRessource(user, resource, action);
			  
			  
			  
			  if(userContributions.size()>0)
				  return null;
		
			  
			  
			  
			  
			     
			
			
			return null;
		}
		
	}
