package learningresourcefinder.tag;


import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import learningresourcefinder.model.Competence;

public class CompetencePathTag extends SimpleTagSupport {

	private Competence competence;
	String result;

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

	@Override
	public void doTag() throws IOException {
		while (competence != null) { 
			if (result == null) {  // First competence
				result =  "<strong>" + competence.getFullName() + "</strong>";
			} else {
			    if (competence.getParent() != null) { // we don't display the root (space consuming)
				    result =  competence.getFullName() + " / " + result;
			    }
			}
			competence = competence.getParent();
		}
		if (result == null) {
			throw new RuntimeException("Bug - defensive coding: result should not be null here (competence was null from the start?)");
		}
		
		JspWriter out = this.getJspContext().getOut();
		out.write(result);
	}

}
