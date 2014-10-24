package learningresourcefinder.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import learningresourcefinder.model.BaseEntity;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.web.ContextUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Service(value="resourceListPager")
@Scope("singleton")
public class ResourceListPagerService {

    @Autowired ResourceRepository resourceRepository; 
    
    public static final int NUMBER_OF_ROWS_TO_RETURN = 150;
    
    public String addListOfResources(List<Resource> resourceList) {
        HttpSession httpSession = ContextUtil.getHttpSession();
        
        @SuppressWarnings("unchecked")
        Map<String, ResourceListAndToken> listOfResources = (Map<String, ResourceListAndToken>) httpSession.getAttribute("listOfResources");

        if (listOfResources == null) {
            listOfResources = new HashMap<>();
        }
        
        // Generate an unique code which will be used as key field of the map
        String tokenListOfResources = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));

        // Add to our map a list of resources Id with the number of rows already returned at first access
        listOfResources.put(tokenListOfResources, new ResourceListAndToken(NUMBER_OF_ROWS_TO_RETURN, resourceList));
        
        httpSession.setAttribute("listOfResources", listOfResources);
        
        return tokenListOfResources;
    }

    public boolean checkIfMoreResources(String tokenListOfResources) {
        // Get the full list of Ids + number of rows already returned from map using token
        ResourceListAndToken resourceListAndToken = getResourceListAndToken(tokenListOfResources);
        
        // Session probably expired ...
        if (resourceListAndToken == null) return false;
        
        // If number of the rows already returned equals the size of the list of resources id, it means 'no more resources to display'
        if (resourceListAndToken.getNumberofRowsAlreadyReturned() == resourceListAndToken.getListOfResourceIds().size()) {
            return false;
        }
        
        return true;
    }
    
    public List<Resource> getMoreResources(String tokenListOfResources) {
        // Get the full list of Ids + number of rows already returned from map using token
        ResourceListAndToken resourceListAndToken = getResourceListAndToken(tokenListOfResources);
        
        // Session probably expired ...
        if (resourceListAndToken == null) return null;
        
        int startIndex = resourceListAndToken.getNumberofRowsAlreadyReturned();
        int endIndex = startIndex + NUMBER_OF_ROWS_TO_RETURN;
        
        List<Long> listOfResourcesId = resourceListAndToken.getListOfResourceIds();
        
        // Extract next range of resources Id
        if (endIndex > listOfResourcesId.size()) endIndex = listOfResourcesId.size(); 
        final List<Long> finalListOfResourcesId = listOfResourcesId.subList(startIndex, endIndex);
        
        // Update number of rows currently returned
        resourceListAndToken.setNumberofRowsAlreadyReturned(endIndex);
        
        // This is the last bucket of resources that we return
        if (endIndex == listOfResourcesId.size()) {
            HttpSession httpSession = ContextUtil.getHttpSession();
            
            @SuppressWarnings("unchecked")
            Map<String, ResourceListAndToken> mapOfResources = (Map<String, ResourceListAndToken>) httpSession.getAttribute("listOfResources");

            // Remove the token from the map
            if (mapOfResources != null) mapOfResources.remove(tokenListOfResources);
        };
        
        // Get Resources from list of resource Ids
        List<Resource> resourceList = resourceRepository.findResourcebyIdList(finalListOfResourcesId);
        
        // TODO extract this part of coding in a new method into ResourceService (same for coding into SearchService (2 different places) !!!)
        // We need to sort the list of resources to match the order of the Id list (the first is supposed to be more relevant) instead of the random order from the DB.
        Collections.sort(resourceList, new Comparator<BaseEntity>() {
            @Override   public int compare(BaseEntity arg0, BaseEntity arg1) {
                return (new Integer((finalListOfResourcesId.indexOf(arg0.getId())))).compareTo( new Integer(finalListOfResourcesId.indexOf(arg1.getId())));
            }
        });
        
        return resourceList;
    }

    private ResourceListAndToken getResourceListAndToken(String tokenListOfResources) {
        HttpSession httpSession = ContextUtil.getHttpSession();
        
        @SuppressWarnings("unchecked")
        Map<String, ResourceListAndToken> mapOfResources = (Map<String, ResourceListAndToken>) httpSession.getAttribute("listOfResources");

        // If no more map retrieved into session, it means session probably expired ...
        if (mapOfResources == null) return null;
        
        ResourceListAndToken resourceListAndPointer = mapOfResources.get(tokenListOfResources);
        
        // If no more record with specific token retrieved into session's map, it means session probably expired ...
        if (resourceListAndPointer == null) return null;
        
        return resourceListAndPointer;
    }
    
    // Inner class
    public class ResourceListAndToken {
        List<Long> listOfResourceIds;
        int numberofRowsAlreadyReturned;
        
        public ResourceListAndToken(int numberofRowsReturned, List<Resource> listOfResources) {
            listOfResourceIds = new ArrayList<>();
            for (Resource resource : listOfResources) {
                this.listOfResourceIds.add(resource.getId());    
            }
            this.numberofRowsAlreadyReturned = numberofRowsReturned;
        }

        public int getNumberofRowsAlreadyReturned() {
            return numberofRowsAlreadyReturned;
        }
        
        public List<Long> getListOfResourceIds() {
            return listOfResourceIds;
        }

        public void setNumberofRowsAlreadyReturned(int numberofRowsAlreadyReturned) {
            this.numberofRowsAlreadyReturned = numberofRowsAlreadyReturned;
        }
    }
}