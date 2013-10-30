<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>

<div id="resource-image-gallery" style="height:300px;">
	<ul id="list-photos">
	<c:forEach var="i" begin="1" end="${resource.numberImage}" step="1">
		<li>
	        <img id="${resource.id}-${i}" src="/gen/resource/resized/small/${resource.id}-${i}.jpg" alt="image ${resource.name}"  /><br/>
	        <c:choose>
	          <c:when test="${canEdit}">
	            <a href='/resource/delete?id=${resource.id}&img=${i}'>Supprimer</a>
	          </c:when>
	          <c:otherwise>  
	            <a class='addPopover' data-content="Pour ajouter/supprimer une image de la ressource, il faut être connecté et avoir un niveau 3 de contribution.">Supprimer</a>
	          </c:otherwise>
	        </c:choose>
	    </li>
	</c:forEach>
	</ul>
</div>
