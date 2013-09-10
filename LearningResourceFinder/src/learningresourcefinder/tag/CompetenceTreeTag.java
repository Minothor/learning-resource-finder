package learningresourcefinder.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import learningresourcefinder.model.Competence;
import learningresourcefinder.repository.CompetenceRepository;
import learningresourcefinder.util.CompetencesTreeVisitorImpl;
import learningresourcefinder.util.CompetencesTreeWalker;
import learningresourcefinder.web.ContextUtil;

public class CompetenceTreeTag extends SimpleTagSupport {
    Competence competence; 
    CompetenceRepository competencerepository; 
    
        
    @Override 
    public void doTag()  {
        
       
        try {
            JspWriter out = this.getJspContext().getOut();
            competencerepository= ContextUtil.getSpringBean(CompetenceRepository.class);
            CompetencesTreeVisitorImpl ctv= new CompetencesTreeVisitorImpl();  
            CompetencesTreeWalker ctw = new CompetencesTreeWalker(competencerepository,ctv);
            ctw.walk();
            out.write(ctv.getHtmlResult());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } 
    }
    

}
