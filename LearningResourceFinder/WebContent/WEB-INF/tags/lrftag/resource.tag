<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource" %>
<%@ attribute name="closeUrl" type="java.lang.String" required="false"%>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>

<div
	style="display: inline-block; position: relative; vertical-align: top; height:325px; width: 200px; margin-right: 20px; margin-bottom: 20px;"
	class="panel panel-default">

	<span id="${playlist.id}-${resource.id}"></span> <%-- Used by sorting JavaScript code to know the id of this resource (for drag-drop sorting) --%>
	
	<c:choose>
		<c:when
			test="${resource.validationStatus=='ACCEPT' || current.canSeeNotValidatedResource}">

			<div class="panel-heading">
				<span class="lead"><c:if test='${! empty prefix}'>${prefix}. </c:if>
					<a href="/resource/${resource.shortId}/${resource.slug}">${resource.name}</a></span>

				<c:if test='${! empty closeUrl}'>
					<a href="<c:url value='${closeUrl}'/>">
						<button type="button" class="addToolTip close"
							style="position: absolute; top: 2px; right: 14px;"
							data-toggle="tooltip"
							title="retirer cette ressource de la séquence">&times;</button>
					</a>
				</c:if>

				<a href="<c:url value='${resource.urlResources[0].url}'/>"> <span
					class="addToolTip glyphicon glyphicon-log-in"
					style="position: absolute; top: 32px; right: 4px;"
					data-toggle="tooltip" title="lien direct vers ce site"></span>
				</a>
			</div>

			<c:if test="${resource.numberImage >= 1}">
				<div id="yoxview-resource-${resource.id}" style="margin-left: 5px">
					<a href="/gen/resource/original/${resource.id}-1.jpg"> <img
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

			<div class="panel-body" style="margin-bottom: 20px;">
				<p>
					<small>${resource.descriptionCut}</small>
				</p>
			</div>

			<div style="position: absolute; bottom: 0px; left: 15px;">
				<lrftag:rating id="${resource.id}" title="${resource.name}"
					scoreResource="${resource.avgRatingScore}"
					scoreUser="${mapRating[resource].score}"
					countRating="${resource.countRating}" canvote="${current.canVote}" />
			</div>


		</c:when>
		<c:otherwise>
			<div class="panel-heading">

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


