<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource" %>
<%@ attribute name="closeUrl" type="java.lang.String" required="false"%>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>
<head>
<!--  "Remove" script for social network's buttons -->
<!-- <script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-509a829c59a66215"></script> -->

</head>
<style>
.resourceDescription {
	word-wrap: break-word;
}
</style>

<div class="resourcecontainer" style="display: inline-block; position: relative; vertical-align: top; height:208px; width: 200px; margin-right: 20px; margin-bottom: 20px;">
 <div itemscope itemtype="http://schema.org/CreativeWork" class="resource-content-small panel panel-default">

    <c:if test="${playlist != null}">
	   <span id="${playlist.id}-${resource.id}" class="sortableItem"></span> <%-- Used by sorting JavaScript code to know the id of this resource (for drag-drop sorting) --%>
	</c:if>
	
	<c:choose>
		<c:when test="${resource.validationStatus=='ACCEPT' || current.canSeeNotValidatedResource}">

				<div class="panel-heading">
					<span class="lead" style="font-size: 16px"><c:if
							test='${! empty prefix}'>${prefix}. </c:if> <a itemprop="name">${resource.name}</a>
						<%-- 						<a href="/resource/${resource.shortId}/${resource.slug}" >${resource.name}</a> --%>
						<input type="hidden" value="${resource.id}">
					 </span>


					<c:if test='${! empty closeUrl}'>
						<a href="<c:url value='${closeUrl}'/>">
							<button type="button" class="addToolTip close"
								style="position: absolute; top: 2px; right: 14px;"
								data-toggle="tooltip"
								title="retirer cette ressource de la séquence">&times;</button>
						</a>
					</c:if>
				</div>
				<div class="row" style="margin-left: 0px; margin-right: 0px;">
				<c:if test="${resource.numberImage >= 1}">
					<div class="imgDiv col-xs-6" id="yoxview-resource-${resource.id}" style="padding:0px;">
						<a href="/gen/resource/original/${resource.id}-1.jpg"> <img 
						itemprop="image"
							src="/gen/resource/resized/small/${resource.id}-1.jpg"
							alt="${resource.name}" />
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
			</div>
		</c:when>
		<c:otherwise>
			<div class="panel-heading">
				<input type="hidden" value="${resource.id}">
			
				<a href="/resource/${resource.shortId}" class="lead">${resource.shortId}</a>

			</div>
			<div class="panel-body"
				style="font-size: 10px !important; margin-bottom: 20px;">
				<c:choose>
								<c:when test='${resource.validationStatus=="REJECT"}'>
										<p>Un contributeur a estimé que cette ressource est inappropriée pour les enfants.</p>
										<p>Seuls les membres connectés en tant qu'adulte y ont donc accès.</p>
								</c:when>
								<c:otherwise>
										<p>Cette ressource n'est pas encore validée.</p>
										<p>Entretemps, par précaution, seuls les membres connectés
											en tant qu'adulte y ont accès.</p>
								</c:otherwise>
							</c:choose>
			</div>
		</c:otherwise>
	</c:choose>
  </div>
  
  <%-- div resource-content-exp will be injected here by Ajax. --%>
  
</div>  <%-- end of container --%>


