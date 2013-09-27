<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@ attribute name="id"  required="true" type="java.lang.Integer" %>
<%@ attribute name="scoreResource"  required="true" type="java.lang.Double" %>
<%@ attribute name="scoreUser"  required="true" type="java.lang.Double" %>
<%@ attribute name="title"  required="true" type="java.lang.String" %>

<a href="#" id="${id}" class="btn popover-link pop" rel="popover" data-original-title="Voter pour : ${title}" style="outline:none;box-shadow:none">
	<ul class="score">
	
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
    <div class="pop_content" data-container="${id}">
        <form action="#" method="post">
            <input class="rating" data-max="4" data-min="0" value="<fmt:formatNumber value='${scoreUser}'/>" name="rating" type="number" />
        </form>
        <p>${scoreResource} / 22 votes</p>
    </div>
</div>