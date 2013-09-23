package learningresourcefinder.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import learningresourcefinder.model.Competence;

public class CompetencePathTag extends SimpleTagSupport{
	
	private Competence competence;

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	
	@Override
	public void doTag() {
		String result;
	
	}

}
