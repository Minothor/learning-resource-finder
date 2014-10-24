 <%@ page language="java" contentType="text/html; charset=UTF-8" 	pageEncoding="UTF-8"%> 
 <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%> 
 <%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%> 
 <%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%> 
<!--  <html> -->
<!-- <body>  -->
	<div >
<%-- 	<lrftag:pageheadertitle title="Contributions de ${user.getFullName()}" />  --%>


		<br />

	<!--  <div class="table-responsive"> -->
	<div>	
			
					<h4>Votre historique:</h4>	
							
					Total général des points de contribution:
						${contributionsPoints}<br/> Points pour le niveau en cours :
						${levelProgress}  <br/><br/>
						
						<a class="btn btn-primary addToolTip"
						href="/recomputeContributionPoints"
						title="Si vous avez un doute sur votre total, ce bouton parcourt la base de données afin de recalculer vos points à partir de chaque contribution">
						recalculer les points </a><br/><br/>
						
					

   
   
</div>
			

	<div class="list-group">
					<c:forEach items="${contributions}" var="contribution">
					
					<div class="list-group-item" >
						 <div class="pull-right" style="text-align:right;">
							<p class="list-group-item-text"><span style="font-size:150%; color:#17A7D6;"> ${contribution.points}</span> <span style="color:#B1B1B1;"><small>point${contribution.points > 1 ? "s" : ""}</small></span> </p>
							<p class="list-group-item-text"> <small>${contribution.createdOnSince}   </small></p>
						</div>
						<div>
							 <h4 class="list-group-item-heading"> <a href="<c:url value='resource/${contribution.ressource.shortId}/${contribution.ressource.name}'/>">  ${contribution.ressource.name}</a> </h4>
							 <p class="list-group-item-text"> <small>${contribution.action.describe}  </small></p>
						 </div>
 					</div> 
				</c:forEach>
			
			
		</div>
		
	</div> 
<!--  </body> -->
<!--  </html>  -->