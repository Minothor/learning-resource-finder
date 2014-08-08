<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="competenceNode" type="learningresourcefinder.util.CompetenceNode"%>
<%@ attribute name="mustlistchildren" required="true" type="java.lang.Boolean"%>


<p>${competenceNode.competence.name}</p>

<c:if test="${mustlistchildren}">
<div style='min-width:250px;'>
 <small>
  <p>${competenceNode.competence.description}</p>
  <ul style='padding-left:0px'>
	  <c:forEach items="${competenceNode.children}" var="child">  		
			<li style='padding-bottom:10px;'>
				${child.competence.code} - <a href='searchresource?competenceid=${child.competence.id}'>${child.competence.name}</a>
			</li>
	 </c:forEach>
  </ul>
 </small>
</div>

</c:if>