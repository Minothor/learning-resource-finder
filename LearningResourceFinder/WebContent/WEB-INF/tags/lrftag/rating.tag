<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@ attribute name="id"  required="true" type="java.lang.Integer" %>
<%@ attribute name="scoreResource"  required="true" type="java.lang.Double" %>
<%@ attribute name="scoreUser"  required="true" type="java.lang.Double" %>
<%@ attribute name="countRating"  required="true" type="java.lang.Integer" %>
<%@ attribute name="title"  required="true" type="java.lang.String" %>
<%@ attribute name="canvote"  required="true" type="java.lang.Boolean" %>
<div data-id="${id}">
    <%-- Always visible stars --%>	
	<a href="#" id="${id}" class="btn rating-link ${canvote == true ? 'ratingpop' : 'noratingpop'}" rel="popover" data-original-title="Voter pour : ${title}" style="outline:none;box-shadow:none;padding:0;">
		<ul class="score" style='color:${countRating > 0 ? " #F28F10" : "#DBDBDB"};'>	
			<c:forEach var="i" begin="0" end="4" step="1" varStatus ="status">
				<c:choose>
					<c:when test="${i < scoreResource}">
					   <li><span class="glyphicon glyphicon-star" style="left: 14px;"></span></li>		
					</c:when> 
					<c:otherwise>
					   <li><span class="glyphicon glyphicon-star-empty" style="left: 14px;"></span></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	    </ul>
	</a>
	
    <%-- Clickable stars, only visible within the popover --%>	
	<div class="hidden_pop_content_container" data-container="${id}"  style="display:none;">
	    <%-- Content moved by Javascript between here and the popover --%>
	    <div class="pop_content" data-container="${id}"  style="min-width:276px;">
	        <form action="#" method="post">
	            <input class="rating" data-max="4" data-min="0" value="<fmt:formatNumber value='${scoreUser - 1}'/>" name="rating" type="number" />
	        </form>
	        <p class="ratingText">${scoreResource} / ${countRating} votes</p>
	    </div>
	</div>
</div>