<%@ tag body-content="scriptless" isELIgnored="false"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ attribute name="competence" type="learningresourcefinder.model.Competence"%>


<% 

	Competence comp = (Competence) pageContext.getAttribute("competence");
	HTMLUtil.getCompetencePath(comp.getParent());

%>
