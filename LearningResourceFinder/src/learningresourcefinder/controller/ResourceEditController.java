package learningresourcefinder.controller;

import learningresourcefinder.model.Resource;
import learningresourcefinder.model.UrlResource;
import learningresourcefinder.repository.ResourceRepository;
import learningresourcefinder.repository.UrlResourceRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.web.Slugify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceEditController extends BaseController<Resource> {

    @Autowired
    ResourceRepository resourceRepository;
    @Autowired
    UrlResourceRepository urlResourceRepository;

    @RequestMapping("/ajax/resourceeditsubmit")
    public @ResponseBody
    String resourceAddSubmit(@RequestParam("url") String url,
            @RequestParam("title") String title,
            @RequestParam("description") String description) {

        SecurityContext.assertUserIsLoggedIn();

        Resource resource = new Resource();
        resource.setDescription(description);
        resource.setName(title);

        UrlResource urlResource = new UrlResource();
        urlResource.setName(title);
        urlResource.setUrl(url);
        urlResource.setResource(resource);

        resource.getUrlResources().add(urlResource);

        String slug = Slugify.slugify(resource.getName());
        resource.setSlug(slug);

        resourceRepository.persist(resource);

        // Url to eventually view the resource
        return "/resource/" + resource.getShortId() + "/" + resource.getSlug();
    }

    @RequestMapping(value = "ajax/checkUrl", method = RequestMethod.POST)
    public @ResponseBody
    boolean urlSubmit(@RequestParam("url") String url) {
        Boolean checkUrlOk = false;
        if (urlResourceRepository.getResourceByUrl(url) != null)
            checkUrlOk = true;
        else
            checkUrlOk = false;

        return checkUrlOk;
    }

}
