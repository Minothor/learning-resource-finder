<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource" %>
<%@ attribute name="closeUrl" type="java.lang.String" required="false"%>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>


<div style="display:inline-block; position:relative; vertical-align:top; width: 200px; margin-right:20px; margin-bottom:20px;"
     class="panel panel-default">
  <div class="panel-heading"><span class="lead"><c:if test='${! empty prefix}'>${prefix}. </c:if>
       <a href="/resource/${resource.shortId}/${resource.slug}" >${resource.name}</a></span>

	   <c:if test='${! empty closeUrl}'>
		    <a href="<c:url value='${closeUrl}'/>">
			   <button type="button" class="addToolTip close" style="position:absolute; top:2px; right:14px;"
			           data-toggle="tooltip" title="retirer cette ressource de la séquence">&times;</button>
		    </a>
	   </c:if>

       <a href="<c:url value='${resource.urlResources[0].url}'/>">
	       <span class="addToolTip glyphicon glyphicon-log-in" style="position:absolute; top:32px; right:4px;"
		           data-toggle="tooltip" title="lien direct vers ce site"></span>
	   </a>
  </div>
  
  <c:if test="${resource.numberImage >= 1}">
    <div id="yoxview-resource-${resource.id}">
        <a href="/gen/resource/original/${resource.id}-1.jpg">
            <img src="/gen/resource/resized/small/${resource.id}-1.jpg" alt="${resource.name}" />
        </a>
    </div>        
    <script type="text/javascript">
       $(document).ready(function(){
    	  $("#yoxview-resource-${resource.id}").yoxview({
    		  lang:"fr",
    		  <%-- Additional images if any, will be displayed by yoxview if the user clicks arrows to view next --%>
    	      images: [
    		  <c:forEach var="i" begin="2" end="${resource.numberImage}" step="1">
    		      { media: { src: '/gen/resource/original/${resource.id}-${i}.jpg' }}
    		      <c:if test="${i < resource.numberImage}">,</c:if>
    		  </c:forEach>
    		  ]
    	  }) 
       });
    </script>
  </c:if>

  <div class="panel-body" style="margin-bottom:20px;">
     <p><small>${resource.descriptionCut}</small></p>
  </div>
  
  <div style="position:absolute; bottom:0px; left:15px;">
	    <lrftag:rating id="${resource.id}" title="${resource.name}" scoreResource="${resource.avgRatingScore}" scoreUser="${mapRating[resource].score}" countRating="${resource.countRating}" canvote="${current.canVote}" />
  </div>
</div>
