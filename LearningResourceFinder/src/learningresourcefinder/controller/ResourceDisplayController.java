package learningresourcefinder.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.PlayList;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
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
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.service.PlayListService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.ModelAndViewUtil;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.protocol.ResponseContent;
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
    
    @RequestMapping({"/resource/{shortId}/{slug}",
        "/resource/{shortId}/", // SpringMVC needs us to explicitely specify that the {slug} is optional.   
        "/resource/{shortId}" // SpringMVC needs us to explicitely specify that the "/" is optional.    
    })  
    public ModelAndView displayResourceByShortId(@PathVariable String shortId) {   
        Resource resource = getRequiredEntityByShortId(shortId);
        Map<Long, String> problemDate = new HashMap<Long, String>();
       
        ModelAndView mv = new ModelAndView("resourcedisplay", "resource", resource);
 
        User user = SecurityContext.getUser();
        mv.addObject("user",user);
        mv.addObject("canEditUrl", levelService.canDoAction(user, Action.EDIT_RESOURCE_URL, resource));
    	mv.addObject("canEdit", levelService.canDoAction(user, Action.EDIT_RESOURCE, resource));
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
    	
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Platform.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Format.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Nature.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Language.class);
    	ModelAndViewUtil.addDataEnumToModelAndView(mv, Topic.class);
    	
    	ModelAndViewUtil.addRatingMapToModelAndView(mv, resource);
    	
    	mv.addObject("isFavorite", favoriteRepository.isFavorite(resource, user));
    	
    	if (resource.getUrlResources().size() > 0) {
    	    mv.addObject("youtubeVideoId", UrlUtil.getYoutubeVideoId(resource.getUrlResources().get(0).getUrl()));
    	}
    	
    	return mv;
	}

    // FIXME: what happens if the input value is wrong (decimal number when int expected, for example) ?  - John 2014/04
	@RequestMapping("/ajax/resourceeditfieldsubmit")
	public @ResponseBody ResponseEntity<String> resourceEditFieldSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
		
		Resource resource = getRequiredEntity(id);
		SecurityContext.assertCurrentUserMayEditThisResource(resource);
		List<String> responseErrors = new LinkedList<String>();
		
        value = (value == null ? null: value.trim());
        
        switch (fieldName){
            case "title":
                resource.setName(value);
                String slug = Slugify.slugify(resource.getName());
                resource.setSlug(slug);
                break;
                
            case "description":
                resource.setDescription(value);
                break;
                
            case "platform":
                resource.setPlatform(Platform.values()[Integer.parseInt(value)-1]);
                break;
                
            case "format":
                resource.setFormat(Format.values()[Integer.parseInt(value)-1]);
                break;
                
            case "nature":
                resource.setNature(Nature.values()[Integer.parseInt(value)-1]);
                break;
                
            case "language":
                resource.setLanguage(Language.values()[Integer.parseInt(value)-1]);
                break;

            case "advertising":
                resource.setAdvertising(Boolean.valueOf(value));
                break;
                
            case "duration":
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
                resource.setDuration(((number==null||number==0) ? null : number));
                break;
                
            case "author":
                resource.setAuthor(value);
                break;
                
            case "topic":
                resource.setTopic(Topic.values()[Integer.parseInt(value)-1]);
                break;
                
            case "addToPlayList":
                PlayList pl = playListRepository.find(Long.parseLong(value));
                pl.getResources().add(resource);
                NotificationUtil.addNotificationMessage("Resource "+resource.getName() + " bien ajoutée à la séquence "+ pl.getName(), Status.SUCCESS);
                break;
        }

        resourceRepository.merge(resource);

        indexManager.update(resource);

        if (! resource.getCreatedBy().equals(SecurityContext.getUser())) {
            levelService.addActionPoints(SecurityContext.getUser(), Action.EDIT_RESOURCE);
        }


        return new ResponseEntity<String>("success",HttpStatus.OK);
	}
    

    @RequestMapping("/removeurlresource")
    public ModelAndView removeResource(@RequestParam("urlresourceid") long urlresourceid) {
        UrlResource urlResource = (UrlResource)getRequiredEntity(urlresourceid, UrlResource.class); 
        Resource resource = urlResource.getResource();

        resource.getUrlResources().remove(urlResource);
        urlResource.setResource(null);
        urlResourceRepository.remove(urlResource);
        
        NotificationUtil.addNotificationMessage("Url supprimée avec succès.<br/>"+urlResource.getUrl(),Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    
    
    @RequestMapping("/urlresourceeditsubmit")
    public String urlSubmit(@RequestParam(value="urlresourceid", required=false) Long urlResourceId, @RequestParam("resourceid") long resourceId, @RequestParam("name") String name, @RequestParam("url") String url) 
    {
        // TODO: voir si url est valide (sinon ajouter une erreur sur result)
        
        Resource resource = resourceRepository.find(resourceId);
        if (true) {   // No errors
            UrlResource urlResource;
            if (urlResourceId != null) {
                urlResource = urlResourceRepository.find(urlResourceId);
            } else {
                urlResource = new UrlResource();
                if (resource == null) {
                    throw new InvalidUrlException("Invalid resource id " + resourceId);
                }
                urlResource.setResource(resource);
            }
            
            urlResource.setName(name);
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
            
        } else {
            
            NotificationUtil.addNotificationMessage("Erreur sur un des champs. Url non sauvée.", Status.ERROR);
        }
        
        return ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

    @RequestMapping("/competenceaddtoresourcesubmit")
    public String competenceAddToResourceSubmit(@RequestParam(value="resourceid",required=false) long resourceId, @RequestParam("code") String code) {
        Resource resource = getRequiredEntity(resourceId);
        Competence competence = competenceRepository.findByCode(code) ;
        
        if (competence == null) { 
            NotificationUtil.addNotificationMessage("Le code '"+code+"' ne correspond à aucune compétence connue.", Status.ERROR);         
        } else {  // Edit
            resource.getCompetences().add(competence);
            levelService.addActionPoints(SecurityContext.getUser(), Action.LINK_RESOURCE_TO_COMPETENCE);
            NotificationUtil.addNotificationMessage("Competence liée avec succès", Status.SUCCESS);         
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
        
        NotificationUtil.addNotificationMessage("Compétence déliée de la ressource avec succès.", Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    



    
} 
