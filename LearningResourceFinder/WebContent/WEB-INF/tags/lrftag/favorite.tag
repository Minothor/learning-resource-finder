<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="isFavorite" required="true" type="java.lang.Boolean"%>
<%@ attribute name="idResource" required="true" type="java.lang.Long"%>

<%-- This tag is used within the display of a resource, or directly as an ajax call result --%>
<c:choose>
	<c:when test="${isFavorite}">
		<c:set var="title" value="Retirer de mes favoris" />
		<c:set var="iconClass" value="glyphicon-heart iconFavorite" />
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Ajouter à mes favoris" />
		<c:set var="iconClass" value="glyphicon-heart-empty iconNotFavorite" />		
	</c:otherwise>
</c:choose>
<a  id="favorite${idResource}" class="glyphicon ${iconClass} addToolTip
   <c:if test="${current.user != null}">" onclick="favoriteToggle(${idResource})"</c:if>
   <c:if test="${current.user == null}">nonaddfavorite"</c:if>
    style="text-decoration:initial;" 
    data-placement="top" title='${title}'></a>
