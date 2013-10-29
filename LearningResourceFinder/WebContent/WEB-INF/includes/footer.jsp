<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer>
	<section id="footer-main">
		<div class="container">
			<div class="row">
				<div class="col-md-3">
				    <br/>
				    <br/>
				    <br/>
				    <br/>
					<a href="/"><img src="/images/ToujoursPlus-logo-footer.png"></a>
				    <br/>
				    <br/>
					<p>ToujoursPlus.be permet aux élèves et professeurs de trouver
						facilement les meilleures cours, jeux éducatifs et vidéos sur
						internet. Il facilite la pédagogie différenciée et le rattrapage
						scolaire.</p>
				</div>
				<div class="col-md-3 col-md-offset-1">
					<h4>Contribuer</h4>

					<p><a class="addResourceLink">Ajouter une ressource </a></p>

					<p><a href="<c:url value='/rights'/>" class="">Droits</a></p>
					
				    <c:if test="${current.user != null}">
				        <p><a href="<c:url value='/ressourcelist/${current.user.userName}'/>" >Mes ressources</a></p>
					</c:if>
					   
				    <p><a href="<c:url value='/contribution'/>" >Améliorer des ressources</a></p>
		

				</div>
				<div class="col-md-3">
					<h4>Compétences</h4>

                     <p><a href="<c:url value='/cycle?id=300'/>">1<sup>ère</sup> - 2<sup>ème</sup> primaire</a></p>
                     <p><a href="<c:url value='/cycle?id=300'/>">3<sup>ème</sup> - 4<sup>ème</sup> primaire</a></p>
                     <p><a href="<c:url value='/cycle?id=300'/>">5<sup>ème</sup> - 6<sup>ème</sup> primaire</a></p>
                     <br/>
                     <p><a href="<c:url value='/cycle?id=300'/>">1<sup>ère</sup> - 2<sup>ème</sup> secondaire</a></p>
                     <p><a href="<c:url value='/cycle?id=300'/>">3<sup>ère</sup> - 6<sup>ème</sup> secondaire</a></p>
                     <br/>
<%-- We hardcode links to have nicer names and sorting					    
						  <c:forEach items="${applicationScope.cache.cycles}" var="cycle">
                             <li><a href="<c:url value='/cycle?id=${cycle.id}'/>">${cycle.name}</a> </li>
                          </c:forEach>
 --%>
                           
                      
                      <lrf:conditionDisplay privilege="MANAGE_COMPETENCE">
                    	  <p><a href="<c:url value='/cyclelist'/>">Gestion des Cycles</a> </p>
                    	  <br/> 
                      </lrf:conditionDisplay>
                      <p><a href="<c:url value='/competencetree?rootCode=socle'/>">Socles (primaire & 1-2 secondaire)</a> </p>
                      <p><a href="<c:url value='/competencetree?rootCode=term'/>">Terminales (3-6 secondaire)</a> </p>
				</div>
				<div class="col-md-2">
					<h4>Séquences</h4>
					<a href="/playlist/all">Toutes les séquences</a>
				</div>
      		</div>
      	</div>
	</section>
</footer>