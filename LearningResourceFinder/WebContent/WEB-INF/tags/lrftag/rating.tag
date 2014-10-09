<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@ attribute name="id"  required="true" type="java.lang.Integer" %>
<%@ attribute name="scoreResource"  required="true" type="java.lang.Double" %>
<%@ attribute name="scoreUser"  required="true" type="java.lang.Double" %>
<%@ attribute name="countRating"  required="true" type="java.lang.Integer" %>
<%@ attribute name="title"  required="true" type="java.lang.String" %>
<%@ attribute name="canvote"  required="true" type="java.lang.Boolean" %>
<%@ attribute name="enablevote"  required="false" type="java.lang.Boolean" %>

<c:set var="enablevote" value="${(empty enablevote) ? true:enablevote}" />
<div data-id="${id}">	
	<a href="#" id="${id}" class="btn ${enablevote==true?'popover-link ':''} ${enablevote==true?(canvote == true ? 'pop' : 'novotepop'):'' }" rel="popover" data-original-title="Voter pour : ${title}" style="outline:none;box-shadow:none;padding:0;">
		<ul class="score" style='color:${countRating > 0 ? " #F28F10" : "#DBDBDB"};'>	
			<c:forEach var="i" begin="0" end="4" step="1" varStatus ="status">
				<c:choose>
					<c:when test="${i < scoreResource}">
					   <li><span class="glyphicon glyphicon-star"></span></li>		
					</c:when> 
					<c:otherwise>
					   <li><span class="glyphicon glyphicon-star-empty"></span></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	    </ul>
	</a>
	
	<div class="pop_display" data-container="${id}" style="display:none;">
	    <div class="pop_content" data-container="${id}" style="min-width:276px;">
	        <form action="#" method="post">
	            <input class="rating" data-max="4" data-min="0" value="<fmt:formatNumber value='${scoreUser - 1}'/>" name="rating" type="number" />
	        </form>
	        <p>${scoreResource} / ${countRating} votes</p>
	    </div>
	</div>
</div>