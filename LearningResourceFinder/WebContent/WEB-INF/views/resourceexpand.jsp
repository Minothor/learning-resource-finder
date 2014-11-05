<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<%-- Fragment returned by an AJAX call to expend resource info --%>

<div class="resource-content-exp panel panel-default" >

	<div class="panel-heading" >
		<span class="lead" style="font-size: 16px">
			<a><img src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-close-round-128.png" style="width:20px;float:right;"/></a>
		    <a itemprop="name"><h3>${resource.name}</h3> </a>
		  
		</span>
		
	</div>

		<div class="row" >
				<c:choose>  
  				  <c:when test="${resource.numberImage >= 1}"> 
  				  	<div class="imgDiv col-xs-6" id="yoxview-resource-${resource.id}" >
						<a href="/gen/resource/original/${resource.id}-1.jpg"> <img 
						itemprop="image"
							src="/gen/resource/resized/small/${resource.id}-1.jpg"
							alt="${resource.name}" style="padding:7px;"/>
						</a>
					</div>
					<script type="text/javascript">
				       //$(document).ready(function(){
				       function yoxviewOnLatestResource(){	   
				    	  $("#yoxview-resource-${resource.id}").yoxview({
				    		  lang:"fr",
				    		  <%-- Additional images if any, will be displayed by yoxview if the user clicks arrows to view next --%>
				    	      images: [
				    		  <c:forEach var="i" begin="2" end="${resource.numberImage}" step="1">
				    		      { media: { src: '/gen/resource/original/${resource.id}-${i}.jpg' }}
				    		      <c:if test="${i < resource.numberImage}">,</c:if>
				    		  </c:forEach>
				    		  ]
				    	  }) ;
				       }
	    			</script>
  				 </c:when>  
        		 <c:otherwise>  
        			 
                   	<div class="imgDiv col-xs-6 resource-content-hidden">
                   		
						 <img itemprop="image" style="width:214px;height:130px;padding: 7px;"
							src="images/defaultimage.png"
							alt="${resource.name}" />
						
					</div>
        		 </c:otherwise>  
				</c:choose> 
					
				<div  class="descriptionDiv col-xs-6 ${resource.numberImage>=1?"resource-content-hidden":""}" >
					
					<p class="resourceDescription" style="margin: 10px;">
						<small itemprop="description">${resource.descriptionCut}</small>
					</p>
				</div>
			</div>
			


	<div class="row"style="margin-left: 0px; margin-right: 0px; padding-top: 5px;">
					<div class="col-md-6" >				
					<div style="background:#FCFCFD;margin-right: -33px;margin-left: -7px;padding: 11px;margin-bottom: 7px;font-size:13px;font-weight: bold;">
						<span class="addToolTip glyphicon " style=" padding: 0px;"
							data-toggle="tooltip" title="Nombre de vues"><span id="viewCounter${resource.id}"> <b> ${resource.viewCount}</b></span> </span>&nbsp;vues<br>
						  <span><c:forEach items="${resource.platforms}" var="platform">${platform.name} </c:forEach></span><br>
						  <c:choose>
						  		<c:when test="${resource.duration != null}"><span>${resource.duration} minutes</span><br> </c:when>
						  </c:choose>
						  <c:choose>
						  	<c:when test="${resource.minCycle != null}"><span>${resource.cycleRange}</span></c:when>
						  </c:choose>
					</div>	
				</div>
				<div class="col-md-6" style="margin-left: 0px; margin-right: 0px;padding-top: 5px;">
					<div class="col-md-12">
						<a itemprop="url" href="/resource/${resource.shortId}/${resource.slug}"> <span
							class="addToolTip"
							style="font-size: 14px; padding: 0px"
							data-toggle="tooltip" title="lien vers la ressource">Détails</span>
						</a>
						<div class="resource-content-hidden" style="float:right; padding:0px; margin-top: 5px">	
							<a href="<c:url value='${resource.urlResources[0].url}'/>" target="_blank" onclick="updateViewcountAndPpularity(${resource.id});" > <span
							class="addToolTip glyphicon glyphicon-circle-arrow-right"
							style="font-size: 35px; padding: 0px"
							data-toggle="tooltip" title="lien direct vers ce site"></span>
							</a>
						</div>
					</div>
					<div class="col-md-12" style="padding-left: 3px; padding-right: 0px;">
						<div class="col-md-8">
							<lrftag:rating id="${resource.id}" title="${resource.name}"
								scoreResource="${resource.avgRatingScore}"
								scoreUser="${mapRating[resource].score}"
								countRating="${resource.countRating}" canvote="${current.canVote}"/>
						</div>
					
						<div class="col-md-4">	
							<lrftag:favorite isFavorite="${isFavorite}" idResource="${resource.id}"/>
						</div>					
						<div class=" addthis_sharing_toolbox" style="display: inline-block;">	
											
						</div>
				
	
					</div>
				</div>

	</div>

</div>