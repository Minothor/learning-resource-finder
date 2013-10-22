package learningresourcefinder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learningresourcefinder.exception.InvalidUrlException;
import learningresourcefinder.model.Competence;
import learningresourcefinder.model.Problem;
import learningresourcefinder.model.Resource;
import learningresourcefinder.model.Resource.Topic;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.model.User;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.FavoriteRepository;
import learningresourcefinder.repository.ProblemRepository;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.search.SearchOptions;
import learningresourcefinder.search.SearchOptions.Format;
import learningresourcefinder.search.SearchOptions.Language;
import learningresourcefinder.search.SearchOptions.Nature;
import learningresourcefinder.search.SearchOptions.Platform;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.service.IndexManagerService;
import learningresourcefinder.service.LevelService;
import learningresourcefinder.util.Action;
import learningresourcefinder.util.DateUtil;
import learningresourcefinder.util.EnumUtil;
import learningresourcefinder.util.NotificationUtil;
import learningresourcefinder.util.NotificationUtil.Status;
import learningresourcefinder.web.Slugify;
import learningresourcefinder.web.UrlUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ResourceDisplayController extends BaseController<Resource> {
    @Autowired ResourceRepository resourceRepository;
    @Autowired UrlResourceRepository urlResourceRepository;
    @Autowired IndexManagerService indexManager;
    @Autowired LevelService levelService;
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
        boolean canEditUrl=levelService.canDoAction(user, Action.EDIT_RESOURCE_URL);
        mv.addObject("canEditUrl", canEditUrl);
        boolean canvote  = levelService.canDoAction(user, Action.VOTE);
		mv.addObject("usercanvote",canvote);
		mv.addObject("user",user);
    	mv.addObject("canEdit", levelService.canDoAction(user, Action.EDIT_RESOURCE));
    	mv.addObject("canAddProblem", levelService.canDoAction(user, Action.ADD_PROBLEM));
    	
    	List<Problem> problemList = problemRepository.findProblemOfResourceNoResolved(resource);
    	for(Problem problem: problemList){
    	    problemDate.put(problem.getId(), DateUtil.formatIntervalFromToNowFR(problem.getCreatedOn()));
    	}
    	
    	mv.addObject("problemList", problemList);
    	mv.addObject("problemDate", problemDate);
    	
    	addDataEnumToModelAndView(mv, Platform.class);
    	addDataEnumToModelAndView(mv, Format.class);
    	addDataEnumToModelAndView(mv, Nature.class);
    	addDataEnumToModelAndView(mv, Language.class);
    	addDataEnumToModelAndView(mv, Topic.class);
    	
    	
    	mv.addObject("isFavorite", favoriteRepository.isFavorite(resource, user));
    	
    	return mv;
	}

	@RequestMapping("/ajax/resourceeditfieldsubmit")
	public @ResponseBody String resourceAddSubmit(@RequestParam("pk") Long id, @RequestParam("value") String value, @RequestParam ("name") String fieldName) {
		
		
		Resource resource = getRequiredEntity(id);
		SecurityContext.assertCurrentUserMayEditThisResource(resource);

		if (fieldName.equals("title")){
			resource.setName(value);
			String slug = Slugify.slugify(resource.getName());
			resource.setSlug(slug);
		}
		else if(fieldName.equals("description")){
			resource.setDescription(value);
		}
		else if(fieldName.equals("platform")){
			resource.setPlatform(Platform.values()[Integer.parseInt(value)-1]);
		
		}
		else if(fieldName.equals("format")){
			resource.setFormat(Format.values()[Integer.parseInt(value)-1]);
		}
		else if(fieldName.equals("nature")){
			resource.setNature(Nature.values()[Integer.parseInt(value)-1]);
		}
		else if(fieldName.equals("language")){
			resource.setLanguage(Language.values()[Integer.parseInt(value)-1]);
		}
		else if(fieldName.equals("advertising")){
			resource.setAdvertising(Boolean.valueOf(value));
		}
		else if(fieldName.equals("duration")){
			resource.setDuration(Integer.parseInt(value));
		}
		else if(fieldName.equals("topic")){
			resource.setTopic(Topic.values()[Integer.parseInt(value)-1]);
		}
		
		resourceRepository.merge(resource);

		indexManager.update(resource);

		return "success";
	}



	private void addDataEnumToModelAndView(ModelAndView mv, Class enumClass) {
		int i = 1, sizeplatform = EnumUtil.getValues(enumClass).length;
		String dataEnum = "[";

		for (Object enumValue : EnumUtil.getValues(enumClass)) {
			if (i == sizeplatform) {
				dataEnum = dataEnum + "{value:" + i + "," + "text:" + "'" + EnumUtil.getDescription(enumValue) + "'}]";
				break;
			}
			dataEnum = dataEnum + "{value:" + i + "," + "text:" + "'" + EnumUtil.getDescription(enumValue) + "'},";
			i++;
		}
		
		if(enumClass.getSimpleName().equals("Platform"))
			mv.addObject("dataEnumPlatform", dataEnum);
		else if(enumClass.getSimpleName().equals("Format"))
			mv.addObject("dataEnumFormat", dataEnum);
		else if(enumClass.getSimpleName().equals("Nature"))
			mv.addObject("dataEnumNature", dataEnum);
		else if(enumClass.getSimpleName().equals("Language"))
			mv.addObject("dataEnumLanguage", dataEnum);
		else if(enumClass.getSimpleName().equals("Topic"))
			mv.addObject("dataEnumTopic", dataEnum);
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
        if(true) {   // No errors
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
            competence.addResource(resource);
            NotificationUtil.addNotificationMessage("Competence liée avec succès", Status.SUCCESS);         
        }
        
        return ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }

    @RequestMapping("/removecompetencefromresource")
    public ModelAndView removeCompetenceFromResource(@RequestParam("competenceid") long competenceId, @RequestParam("resourceid") long resourceId) {
        Resource resource = getRequiredEntity(resourceId);
        Competence competence = (Competence)getRequiredEntity(competenceId, Competence.class);

        resource.getCompetences().remove(competence);
        competence.getResources().remove(resource);
        resourceRepository.merge(resource);
        competenceRepository.merge(competence);
        
        NotificationUtil.addNotificationMessage("Compétence déliée de la ressource avec succès.", Status.SUCCESS);
        return new ModelAndView ("redirect:"+UrlUtil.getRelativeUrlToResourceDisplay(resource));
    }
    

} 
