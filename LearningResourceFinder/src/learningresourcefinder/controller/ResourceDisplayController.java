package learningresourcefinder.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Cycle;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.Resource.ValidationStatus;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.FavoriteRepository;
import learningresourcefinder.repository.PlayListRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.Privilege;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.ContributionService;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.service.PlayListService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.Cache;
import learningresourcefinder.web.ModelAndViewUtil;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.util.StringUtils;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
    @Autowired ResourceRepository resourceRepository;
    @Autowired UrlResourceRepository urlResourceRepository;
    @Autowired IndexManagerService indexManager; 
    @Autowired LevelService levelService;
    @Autowired PlayListRepository playListRepository;
    @Autowired PlayListService playListService;
    @Autowired CompetenceRepository competenceRepository;
    @Autowired ProblemRepository problemRepository;
    @Autowired FavoriteRepository favoriteRepository;
    @Autowired ContributionService contributionService; 
    
    //public final static  int[] ID_CYCLES_IN_DB = new int[]{300,301,302,303,304};
    
    @RequestMapping({"/resource/{shortId}/{slug}",
        "/resource/{shortId}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/resource/{shortId}" // SpringMVC needs us to explicitely specify that the "/" is optional.    
    })  
    public ModelAndView displayResourceByShortId(@PathVariable String shortId) {   
        Resource resource = getRequiredEntityByShortId(shortId);
        Map<Long, String> problemDate = new HashMap<Long, String>();

        if(!SecurityContext.canCurrentUserSeeResource(resource)) {
            return new ModelAndView("resourcedisplayhidden", "resource", resource);
        }        
        User user = SecurityContext.getUser();
        ModelAndView mv = new ModelAndView("resourcedisplay", "resource", resource);
        ////// Find min and max Cycles from slider input
        // cycles ids in ascending order of cycles names

        int cycleListLength = Cache.getInstance().getIdCyclesInDb().size(); 
        long minCycleId = (resource.getMinCycle() != null)?resource.getMinCycle().getId() : Cache.getInstance().getIdCyclesInDb().get(0); //id:300 -> 0  default   min value for slider
        long maxCycleId = (resource.getMaxCycle() != null)?resource.getMaxCycle().getId() : Cache.getInstance().getIdCyclesInDb().get(cycleListLength-1);//id:304 -> 4  default   max value for slider
        
        if((resource.getMinCycle() != null) && (resource.getMaxCycle()!=null)){
            
            mv.addObject("cyclevalue","null"); // get in jsp " ? -> ? "  to  cycle value
        }else{
            mv.addObject("cyclevalue","notnull"); // Get in jsp a real cycle value 
        }

         int minCycle=0;
         int maxCycle=4;

         int numberOfCycles = Cache.getInstance().getIdCyclesInDb().size();
         for (int i = 0; i < numberOfCycles; i++) {
            if(Cache.getInstance().getIdCyclesInDb().get(i) == minCycleId){
                minCycle=i;               
            }
            if(Cache.getInstance().getIdCyclesInDb().get(i) == maxCycleId){                
                maxCycle=i;               
            }
        } 
        
        mv.addObject("mincycle",minCycle);
        mv.addObject("maxcycle",maxCycle);
        

        
        mv.addObject("user",user);
        mv.addObject("canEditUrl", levelService.canDoAction(user, Action.EDIT_RESOURCE_URL, resource));
        mv.addObject("canEdit", levelService.canDoAction(user, Action.EDIT_RESOURCE, resource));
        mv.addObject("canValidate", levelService.canDoAction(user, Action.VALIDATE_RESOURCE, resource));
        mv.addObject("canaddplaylist",levelService.canDoAction(user,Action.ADD_PLAYLIST));
    	mv.addObject("canAddProblem", levelService.canDoAction(user, Action.ADD_PROBLEM));
        mv.addObject("canLinkToCompetence", levelService.canDoAction(user, Action.LINK_RESOURCE_TO_COMPETENCE));
    	    	
    	
        // If none of the Urls has a String name, then the JSP will opt for a simplified layout.
        boolean oneUrlHasAName = false;
        for (UrlResource urlResource: resource.getUrlResources()) {
            if (!StringUtils.isBlank(urlResource.getName())) {
                oneUrlHasAName = true;
            }
        }
        mv.addObject("oneUrlHasAName", oneUrlHasAName);
        
        
    	List<Problem> problemList = problemRepository.findProblemOfResourceNoResolved(resource);
    	for(Problem problem: problemList){
    	    problemDate.put(problem.getId(), DateUtil.formatIntervalFromToNowFR(problem.getCreatedOn()));
    	}
    	
    	if(user!=null){
    	    //for add Resource in PlayList
    	    Set<PlayList> tpl = playListService.getAllUserPlayListsDontContainAResource(user,resource);
    	    if(tpl.size() != 0){
    	        mv.addObject("listMyPlayListWithoutThisResource", playListService.setPlayListToJSONWithIdAndName(tpl));
    	    }
    	    
    	    //for watch user's playlists with this resource
    	    Set<PlayList> tpl2 = playListService.getUserPlayListsWithResource(resource, user);
    	    if(tpl2.size() != 0){
    	        mv.addObject("listMyPlayListsWithThisResource",tpl2 );
    	    }
    	    
    	    //for watch other playlists contain this resource
    	    Set<PlayList> tpl3 = playListService.getOtherPeoplePlayListsWithResource(resource, user);
    	    if(tpl3.size() != 0){
    	        mv.addObject("listOtherPeoplePlayListsWithThisResource", tpl3);
    	    }
    	}
    	
    	
    	mv.addObject("problemList", problemList);
    	mv.addObject("problemDate", problemDate);
    	mv.addObject("platformsForJson", selectedPlatformsToJSON(resource));

    	
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Platform.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Format.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Nature.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Language.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Topic.class);
        ModelAndViewUtil.addDataEnumToModelAndView(mv, ValidationStatus.class);
        
    	
    	ModelAndViewUtil.addRatingMapToModelAndView(mv, resource);
    	
    	mv.addObject("isFavorite", favoriteRepository.isFavorite(resource, user));
    	
    	if (resource.getUrlResources().size() > 0) {
    	    mv.addObject("youtubeVideoId", UrlUtil.getYoutubeVideoId(resource.getUrlResources().get(0).getUrl()));
    	}
    	
    	return mv;
	}
    
    public String selectedPlatformsToJSON(Resource resource) {
        int i = 1, sizeplatform = resource.getPlatforms().size();
        String dataEnum = "";

        for (Platform platform : resource.getPlatforms()) {
            dataEnum += platform.ordinal()+1;
            if (i == sizeplatform) {
                break;
            }
            dataEnum += ",";
            i++;
        }
        return dataEnum;
    }
 
                           
    @RequestMapping("/ajax/resourceeditfieldarraysubmit")  // For a list of checkboxes, value is an array (=> resourceDditFieldSubmit cannot get it in its String).
    public @ResponseBody ResponseEntity<String> resourceeditfieldsubmitarray(@RequestParam("pk") Long id, @RequestParam(value="value[]", required=false) int[] valueArray) {
        if (valueArray == null) {
            return new ResponseEntity<String>("Veuillez indiquer au moins une plate-forme.", HttpStatus.BAD_REQUEST);
        }
        
        Resource resource = getRequiredEntity(id);
        
        // valueArray contains something like = [2,3] (if third and forth options have been checked)
        Set<Platform> platforms=new HashSet<Platform>();
        for(int platformNumber : valueArray) {        
            platforms.add(Platform.values()[platformNumber-1]);      
        }
        
        resource.setPlatforms(platforms);       
        resourceRepository.merge(resource);
        indexManager.update(resource);
        return new ResponseEntity<String>("success",HttpStatus.OK);
    }


    // FIXME: what happens if the input value is wrong (decimal number when int expected, for example) ?  - John 2014/04
	@RequestMapping("/ajax/resourceeditfieldsubmit")
	public @ResponseBody ResponseEntity<String> resourceEditFieldSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam("name") String fieldName) {
		
	    Resource resource = getRequiredEntity(id);
	    value = (value == null ? null : value.trim());
	    User user = SecurityContext.getUser();

        switch (fieldName){
            case "title":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
                if (StringUtils.isBlank(value)) {
                    return new ResponseEntity<String>("Vous n'avez pas introduit de nom.", HttpStatus.BAD_REQUEST);
                }
                Resource resourceHavingTheSameName = resourceRepository.getResourceByName(value);
                if (resourceHavingTheSameName != null) {
                    return new ResponseEntity<String>("Cet intitulé existe déjà pour une resource, veuillez en choisir un autre",HttpStatus.BAD_REQUEST);
                }
                if (value.length() >50) {
                    return new ResponseEntity<String>("Le titre du resource ne peut pas dépasser 50 caractères.", HttpStatus.BAD_REQUEST);
                }
                resource.setName(value);
                String slug = Slugify.slugify(resource.getName());
                resource.setSlug(slug);
                break;
                
            case "description":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
            	contributionService.contribute(user, resource, Action.EDIT_RESOURCE_DESCRIPTION, resource.getDescription(), value);
            	resource.setDescription(value);
                break;
   
            case "format":            	
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
            	String format = (resource.getFormat() == null) ? "" : resource.getFormat().getDescription();
				contributionService.contribute(user, resource, Action.EDIT_RESOURCE_FORMAT, format, Format.values()[Integer.parseInt(value) - 1].getDescription());
				resource.setFormat(Format.values()[Integer.parseInt(value) - 1]);
				break;
				
            case "nature":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
				String nature = (resource.getNature() == null) ? "" : resource.getNature().getDescription();
				contributionService.contribute(user, resource, Action.EDIT_RESOURCE_NATURE, nature, value);
                resource.setNature(Nature.values()[Integer.parseInt(value)-1]);
                break;
                
            case "language":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
            	String language = (resource.getLanguage() == null) ? "" : resource.getLanguage().getDescription();
				contributionService.contribute(user, resource, Action.EDIT_RESOURCE_LANGUAGE, language, Language.values()[Integer.parseInt(value) - 1].getDescription());
                resource.setLanguage(Language.values()[Integer.parseInt(value)-1]); //
                break;

            case "advertising":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
                resource.setAdvertising(Boolean.valueOf(value));
                break;
                
            case "duration":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
                Integer number=null;
                try {
                    number = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    if(value!=null) {
                        return new ResponseEntity<String>("Nombre invalide.", HttpStatus.BAD_REQUEST);
                    }
                }
                if (number!=null && number < 0 ) {
                    return new ResponseEntity<String>("La durée doit être un nombre entier et positif de minutes.", HttpStatus.BAD_REQUEST);
                }
                String duration=(resource.getDuration()==null)?"":resource.getDuration().toString();
                contributionService.contribute(user, resource, Action.EDIT_RESOURCE_DURATION, duration, value);
                resource.setDuration(((number==null||number==0) ? null : number));    
                break;
                
            case "author":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
                resource.setAuthor(value);
                break;
                
            case "topic":
        	    SecurityContext.assertCurrentUserMayEditThisResource(resource);
                resource.setTopic(Topic.values()[Integer.parseInt(value)-1]);
                break;
                
            case "addToPlayList":
            	SecurityContext.assertCanCurrentDoAction(Action.ADD_PLAYLIST);
                PlayList pl = playListRepository.find(Long.parseLong(value));
                pl.getResources().add(resource);
                NotificationUtil.addNotificationMessage("Resource "+resource.getName() + " ajoutée à la séquence "+ pl.getName(), Status.SUCCESS);
                break;

            case "addToOtherPlayList":
                SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_PLAYLIST);

                if (value==null || value.trim().isEmpty()) 
                    return new ResponseEntity<String>("Merci d'introduire l'id court d'une séquence à modifier.", HttpStatus.BAD_REQUEST);

                PlayList pl2 = playListRepository.getPlayListByShortId(value);
                if (pl2==null) { 
                    return new ResponseEntity<String>("L'id court introduit ne correspond pas à une séquence existante.", HttpStatus.BAD_REQUEST); 
                }

                // Is the resource in the playList arleady ?
                if (pl2.getResources().contains(resource)) {
                    return new ResponseEntity<String>("Cette ressource fait déjà partie de la séquence choisie.", HttpStatus.BAD_REQUEST);    
                }

                // all checks done. We change the data.
                pl2.getResources().add(resource);
                NotificationUtil.addNotificationMessage("Ressource "+ resource.getName() + " ajoutée à la séquence "+ pl2.getName(), Status.SUCCESS);
                break;
                
            case "validate":
            	SecurityContext.assertCanCurrentDoAction(Action.VALIDATE_RESOURCE);
                resource.setValidationStatus(ValidationStatus.values()[Integer.parseInt(value)-1]);
                resource.setValidationDate(new Date());
                resource.setValidator(SecurityContext.getUser());
                break;
        }
		
		
		resourceRepository.merge(resource);

		indexManager.update(resource);
		
//		if (! resource.getCreatedBy().equals(SecurityContext.getUser())) {
//            levelService.addActionPoints(SecurityContext.getUser(), Action.EDIT_RESOURCE);
//		}
        


	    return new ResponseEntity<String>("success",HttpStatus.OK);
	}
    
	
    @RequestMapping("/removeurlresource")
    public ModelAndView removeUrlResource(@RequestParam("urlresourceid") long urlresourceid) {
        
        
        UrlResource urlResource = (UrlResource)getRequiredEntity(urlresourceid, UrlResource.class); 
        Resource resource = urlResource.getResource();
	    SecurityContext.assertCurrentUserMayEditThisResource(resource);

        resource.getUrlResources().remove(urlResource);
        urlResource.setResource(null);
        urlResourceRepository.remove(urlResource);
        
        NotificationUtil.addNotificationMessage("Url supprimée avec succès.<br/>"+urlResource.getUrl(),Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    
    
    @RequestMapping("/urlresourceeditsubmit")
    public String urlSubmit(@RequestParam(value="urlresourceid", required=false) Long urlResourceId, @RequestParam("resourceid") long resourceId, @RequestParam("name") String name, @RequestParam("url") String url) 
    {
        Resource resource = getRequiredEntity(resourceId);
        SecurityContext.assertCurrentUserMayEditThisResource(resource);

        if (url == null || url.length() == 0) {
            NotificationUtil.addNotificationMessage("Veuillez entrer une URL valide.", Status.ERROR);
        }
        else {

            UrlResource urlResource;
            if (urlResourceId != null) {
                urlResource = urlResourceRepository.find(urlResourceId);
            } else {
                urlResource = new UrlResource();
                urlResource.setResource(resource);
            }

            urlResource.setName(name);
            
            url=url.trim();
            if(!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://" + url;
            }
            
            urlResource.setUrl(url);

            if (urlResource.getId() == null) {  // Create
                urlResourceRepository.persist(urlResource);
                NotificationUtil.addNotificationMessage("Url ajoutée avec succès", Status.SUCCESS);         
            } else {  // Edit
                urlResourceRepository.merge(urlResource);
                NotificationUtil.addNotificationMessage("Url modifiée avec succès", Status.SUCCESS);         
            }

            if (! resource.getCreatedBy().equals(SecurityContext.getUser())) {
                levelService.addActionPoints(SecurityContext.getUser(), Action.EDIT_RESOURCE_URL);
            }
            
        }
        
        return ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

    @RequestMapping("/competenceaddtoresourcesubmit")
    public String competenceAddToResourceSubmit(@RequestParam(value="resourceid",required=false) long resourceId, @RequestParam("code") String code) {
        Resource resource = getRequiredEntity(resourceId);
        if (code == null) {
            NotificationUtil.addNotificationMessage("Veuillez introduire un code correspondant à une catégorie.", Status.ERROR);
        }
        else {
            Competence competence = competenceRepository.findByCode(code) ;
            
            if (competence == null) { 
                NotificationUtil.addNotificationMessage("Le code '"+code+"' ne correspond à aucune catégorie connue.", Status.ERROR);         
            } else {  // Edit
                resource.getCompetences().add(competence);
                levelService.addActionPoints(SecurityContext.getUser(), Action.LINK_RESOURCE_TO_COMPETENCE);
                NotificationUtil.addNotificationMessage("Catégorie liée avec succès", Status.SUCCESS);         
            }
        }
        
        return ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

    @RequestMapping("/removecompetencefromresource")
    public ModelAndView removeCompetenceFromResource(@RequestParam("competenceid") long competenceId, @RequestParam("resourceid") long resourceId) {
        Resource resource = getRequiredEntity(resourceId);
        Competence competence = (Competence)getRequiredEntity(competenceId, Competence.class);

        resource.getCompetences().remove(competence);
        resourceRepository.merge(resource);
        competenceRepository.merge(competence);
        
        NotificationUtil.addNotificationMessage("Catégorie déliée de la ressource avec succès.", Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    
    @RequestMapping("/ajax/cycleeditinresourcesubmit")
    public ModelAndView cycleEditInResourceSubmit(@RequestParam("pk") Long id, @RequestParam(value="mincycle",required=true) String minCycle,@RequestParam(value="maxcycle",required=true) String maxCycle) {
        

        
        Resource resource = getRequiredEntity(id);
        SecurityContext.assertCurrentUserMayEditThisResource(resource);
        
        
        //convert String to int, for Ex: "1.00" to 1 (slider component sends decimal strings...)
        int tempIntMin=(int)Double.parseDouble(minCycle);
        int tempIntMax=(int)Double.parseDouble(maxCycle);
       
        // Get cycle id from slider position (1 -> 301)
        long idMinCycle= Cache.getInstance().getIdCyclesInDb().get(tempIntMin);
        long idMaxCycle= Cache.getInstance().getIdCyclesInDb().get(tempIntMax);
             
        Cycle cycleMin = (Cycle) getRequiredEntity(idMinCycle, Cycle.class);
        Cycle cycleMax = (Cycle) getRequiredEntity(idMaxCycle, Cycle.class);

        
        resource.setMinCycle(cycleMin);
        resource.setMaxCycle(cycleMax);        
        resourceRepository.merge(resource);

        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    } 

	@RequestMapping("/resource/delete")
	public String resourceDelete(
			@RequestParam(value = "idresource", required = false) Long id) {
		if (id == null) {
			throw new InvalidUrlException("Missing resource id.");
		}
		Resource resource = getRequiredEntity(id);
		SecurityContext.assertCurrentUserMayEditThisResource(resource);
		// We first need to unbind with the competences (else we have a stacktrace).  --Ramzi 2014-10-30
		resource.getCompetences().clear();
		resourceRepository.merge(resource);
		resourceRepository.remove(resource);
		NotificationUtil.addNotificationMessage("Ressource supprimée", Status.SUCCESS);
		return "redirect:/home";
	}
} 
