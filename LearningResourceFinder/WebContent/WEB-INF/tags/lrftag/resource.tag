<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource" %>
<%@ attribute name="closeUrl" type="java.lang.String" required="false"%>


<div style="display:inline-block; position:relative; vertical-align:top; width: 200px; margin-right:20px; margin-bottom:20px;"
     class="panel panel-default">
  <div class="panel-heading">
       <a href="/resource/${resource.shortId}/${resource.slug}" class="lead">${resource.name}</a>

	   <c:if test='${! empty closeUrl}'>
		    <a href="<c:url value='${closeUrl}'/>">
			   <button type="button" class="addToolTip close" style="position:absolute; top:2px; right:14px;"
			           data-toggle="tooltip" title="retirer cette ressource de la séquence">&times;</button>
		    </a>
	   </c:if>
  </div>
  
  <c:if test="${resource.numberImage >= 1}">
    <img src="/gen/resource/resized/small/${resource.id}-1.jpg" alt="${resource.name}" />
  </c:if>

  <div class="panel-body" style="margin-bottom:20px;">
     <p><small>${resource.descriptionCut}</small></p>
  </div>
  
  <div style="position:absolute; bottom:0px; left:15px;">
	    <lrftag:rating id="${resource.id}" title="${resource.name}" scoreResource="${resource.avgRatingScore}" scoreUser="${mapRating[resource].score}" countRating="${resource.countRating}" canvote="${current.canVote}" />
  </div>
</div>
