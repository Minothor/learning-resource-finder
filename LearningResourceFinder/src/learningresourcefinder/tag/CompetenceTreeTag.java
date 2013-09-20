package learningresourcefinder.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.repository.CycleRepository;
import learningresourcefinder.util.CompetencesTreeVisitorImpl;
import learningresourcefinder.util.CompetencesTreeWalker;
import learningresourcefinder.web.ContextUtil;

public class CompetenceTreeTag extends SimpleTagSupport {
	
	Competence root;
	
    @Override 
    public void doTag()  {
        try {
            JspWriter out = this.getJspContext().getOut();
            CompetenceRepository competenceRepository= ContextUtil.getSpringBean(CompetenceRepository.class);
            CycleRepository cycleRepository = ContextUtil.getSpringBean(CycleRepository.class);
            CompetencesTreeVisitorImpl ctv= new CompetencesTreeVisitorImpl();  
            CompetencesTreeWalker ctw = new CompetencesTreeWalker(ctv, root, competenceRepository, cycleRepository);
            ctw.walk();
            out.write(ctv.getHtmlResult());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } 
    }

	public Competence getRoot() {
		return root;
	}

	public void setRoot(Competence root) {
		this.root = root;
	}
}
