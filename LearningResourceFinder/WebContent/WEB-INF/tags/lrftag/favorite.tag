<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="isFavorite" required="true" type="java.lang.Boolean"%>
<%@ attribute name="idResource" required="true" type="java.lang.Long"%>

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
<a href="#" onclick="favoriteToggle(${idResource})" id="favorite${idResource}" class="addResourceFavorite" data-placement="top" data-toggle="tooltip" data-original-title='${title}'><span class="glyphicon ${iconClass}"></span></a>