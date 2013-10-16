<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="competenceNode" type="learningresourcefinder.util.CompetenceNode"%>
<%@ attribute name="mustlistchildren" required="true" type="java.lang.Boolean"%>


<p>${competenceNode.competence.description}</p>

<c:if test="${mustlistchildren}">

  <ul>
	  <c:forEach items="${competenceNode.competence.children}" var="child">
			<li>${child.competence.code} - <a href='searchresource?competenceid=${child.competence.id}'>${child.competence.name}</a></li>
	 </c:forEach>
  </ul>

</c:if>