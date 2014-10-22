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
public class ResourceListPager {

    @Autowired ResourceRepository resourceRepository; 
    
    public static final int NUMBER_OF_ROWS_TO_RETURN = 150;
    
    public String addListOfResources(List<Resource> resourceList) {
        HttpSession httpSession = ContextUtil.getHttpSession();
        
        @SuppressWarnings("unchecked")
        Map<String, ResourceListAndPointer> listOfResources = (Map<String, ResourceListAndPointer>) httpSession.getAttribute("listOfResources");

        if (listOfResources == null) {
            listOfResources = new HashMap<>();
        }
        
        // Generate an unique code which will be used as key field of the map
        String tokenListOfResources = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Timestamp(System.currentTimeMillis()));

        // Add to our map a list of resources Id with the number of rows already returned at first access
        listOfResources.put(tokenListOfResources, new ResourceListAndPointer(NUMBER_OF_ROWS_TO_RETURN, resourceList));
        
        httpSession.setAttribute("listOfResources", listOfResources);
        
        return tokenListOfResources;
    }

    public List<Resource> getMoreResources(String tokenListOfResources) {
        HttpSession httpSession = ContextUtil.getHttpSession();
        
        @SuppressWarnings("unchecked")
        Map<String, ResourceListAndPointer> mapOfResources = (Map<String, ResourceListAndPointer>) httpSession.getAttribute("listOfResources");

        // Session probably expired ...
        if (mapOfResources == null) return null;
        
        ResourceListAndPointer resourceListAndPointer = mapOfResources.get(tokenListOfResources);
        
        int startIndex = resourceListAndPointer.getNumberofRowsAlreadyReturned();
        int endIndex = startIndex + NUMBER_OF_ROWS_TO_RETURN;
        
        List<Long> listOfResourcesId = resourceListAndPointer.getListOfResourceIds();
        
        // Extract next range of resources Id
        if (endIndex > listOfResourcesId.size()) endIndex = listOfResourcesId.size(); 
        final List<Long> finalListOfResourcesId = listOfResourcesId.subList(startIndex, endIndex);
        
        // Update number of rows currently returned
        resourceListAndPointer.setNumberofRowsAlreadyReturned(endIndex);
        
        // Get Resources from list of resource Ids
        List<Resource> resourceList = resourceRepository.findResourcebyIdList(listOfResourcesId);
        
        // TODO extract this part of coding in a new method into ResourceService (same for coding into SearchService (2 different places) !!!)
        // We need to sort the list of resources to match the order of the Id list (the first is supposed to be more relevant) instead of the random order from the DB.
        Collections.sort(resourceList, new Comparator<BaseEntity>() {
            @Override   public int compare(BaseEntity arg0, BaseEntity arg1) {
                return (new Integer((finalListOfResourcesId.indexOf(arg0.getId())))).compareTo( new Integer(finalListOfResourcesId.indexOf(arg1.getId())));
            }
        });
        
        return resourceList;
    }
    
    // Inner class
    public class ResourceListAndPointer {
        List<Long> listOfResourceIds;
        int numberofRowsAlreadyReturned;
        
        public ResourceListAndPointer(int numberofRowsReturned, List<Resource> listOfResources) {
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