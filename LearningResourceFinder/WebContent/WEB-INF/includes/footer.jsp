<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer>
	<section id="footer-main">
		<div class="container">
			<div class="row">
        		<div class="col-md-3">
        			<h4><a href="/"><img src="/images/ToujoursPlus-logo-footer.png"></a></h4>
					<p>ToujoursPlus.be permet aux élèves et professeurs de trouver facilement les meilleures cours, jeux éducatifs et vidéos sur internet. 
					Il facilite la pédagogie différenciée et le rattrapage scolaire.
					</p> 
				</div>
        		<div class="col-md-3">
          			<h4>Contribuer</h4>
          			
          			<a class="addResourceLink">Ajouter une ressource </a>
          			
        		</div>
        		<div class="col-md-3">
          			<h4>Compétence</h4>
          			
          			<c:forEach items="${applicationScope.cache.cycles}" var="cycle">
                            	<a href="<c:url value='/cycle?id=${cycle.id}'/>">${cycle.name}<br></a>
                          </c:forEach>
                           <a href="<c:url value='/competencetree?rootCode=socle'/>">Socles (primaire & 1-2 secondaire)</a><br>
                          <a href="<c:url value='/competencetree?rootCode=term'/>">Terminales (3-6 secondaire)</a>
          			
        		</div>
        		<div class="col-md-3">
          			<h4>Playlist</h4>
          			<a href="/playlist/all">Toutes les PlayLists</a>
        		</div>
      		</div>
      	</div>
	</section>
	
	<section id="footer">
		<div class="container">
<!-- 			<p>©2013 ToujoursPlus.be - Create By XXX</p> -->
		</div>
	</section>
</footer>