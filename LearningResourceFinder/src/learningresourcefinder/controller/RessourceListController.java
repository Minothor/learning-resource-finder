package learningresourcefinder.controller;

import java.util.List;

import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.ResourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RessourceListController extends BaseController<Resource>{
	@Autowired ResourceRepository resourcerepository; 

	@RequestMapping("/ressourcelist")
	public ModelAndView showResourceList () {
		List<Resource> list=resourcerepository.findAllRessourceOrderByTitle();
		return new ModelAndView("resourcelist", "resourceList", list);
	}
}
