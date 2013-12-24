<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>

<c:if test="${resource.numberImage > 0}">
    <script type="text/javascript">
       $(document).ready(function(){
    	  $("#resource-image-gallery").yoxview({
    		  lang:"fr"
    	  }) 
       });
    </script>

	<div id="resource-image-gallery" style="height:300px;">
		<ul id="list-photos">
		<c:forEach var="i" begin="1" end="${resource.numberImage}" step="1">
			<li>    
			    <a href="/gen/resource/original/${resource.id}-${i}.jpg">  <%-- Link for yoxview --%>
		            <img id="${resource.id}-${i}" src="/gen/resource/resized/small/${resource.id}-${i}.jpg" alt="image ${resource.name}"  />
		        </a>
		        <br/>
		        
		        <c:choose>
		          <c:when test="${canEdit}">
		            <a href='/resource/delete?id=${resource.id}&img=${i}'>
		          </c:when>
		          <c:otherwise>  
		            <a class='addPopover' data-content="Pour ajouter/supprimer une image de la ressource, il faut être connecté et avoir un niveau 3 de contribution.">
		          </c:otherwise>
		        </c:choose>
    	        Supprimer</a>
		    </li>
		</c:forEach>
		</ul>
	</div>
</c:if>