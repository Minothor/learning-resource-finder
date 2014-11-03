<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<%-- Fragment returned by an AJAX call to expend resource info --%>

<div class="resource-content-exp">

	<div class="panel-heading">
		<span class="lead" style="font-size: 16px">
		    <a itemprop="name">${resource.name}</a>
		</span>
	</div>

	
		<div class="resource-content-exp"
			style="float: right; padding: 0px; margin-top: 5px">
			<a href="<c:url value='${resource.urlResources[0].url}'/>"
				target="_blank"
				onclick="updateViewcountAndPpularity(${resource.id});"> <span
				class="addToolTip glyphicon glyphicon-circle-arrow-right"
				style="font-size: 35px; padding: 0px" data-toggle="tooltip"
				title="lien direct vers ce site"></span>
			</a>
		</div>
		<p class="resourceDescription">
			<small itemprop="description">${resource.descriptionCut}</small>
		</p>


	<div class="resource-content-exp row"
		style="margin-left: 0px; margin-right: 0px; padding-top: 5px;">
		<div class="col-xs-6" style="padding-left: 3px; padding-right: 0px;">
			<lrftag:rating id="${resource.id}" title="${resource.name}"
				scoreResource="${resource.avgRatingScore}"
				scoreUser="${mapRating[resource].score}"
				countRating="${resource.countRating}" canvote="${current.canVote}" />
		</div>
		<div class="resource-content-exp col-xs-6"
			style="margin-left: 0px; margin-right: 0px;">
			<div class="row">
				<div class="col-xs-6 addthis_sharing_toolbox"
					style="display: inline-block;"></div>
				<div class="col-xs-6" style="display: inline-block; font-size: 20px">
					<lrftag:favorite isFavorite="${isFavorite}"
						idResource="${resource.id}" />
				</div>
			</div>
			<a itemprop="url"
				href="/resource/${resource.shortId}/${resource.slug}"> <span
				class="addToolTip" style="font-size: 12px; padding: 0px"
				data-toggle="tooltip" title="lien vers la ressource">Détails</span>
			</a> <span class="addToolTip glyphicon glyphicon-eye-open"
				style="font-size: 12px; padding: 0px; margin-left: 5px"
				data-toggle="tooltip" title="Nombre de vues"><span
				id="viewCounter${resource.id}"> ${resource.viewCount}</span></span><br>
			<span><b>Platform:</b> <c:forEach
					items="${resource.platforms}" var="platform">${platform.name} </c:forEach></span><br>
			<c:choose>
				<c:when test="${resource.duration != null}">
					<span><b>Durée:</b> ${resource.duration}m</span>
					<br>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${resource.minCycle != null}">
					<span><b>Cycle: </b>${resource.minCycle.name} &#8594;
						${resource.maxCycle.name}</span>
				</c:when>
			</c:choose>
		</div>
	</div>

</div>