<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<body>
<div class="container" style="padding-top:20px; padding-bottome:20px;">

    <%-- jsp:include does not work with Sitemesh !! John 2014-10-31 --%>
    
    <c:choose>
	     <c:when test="${urlPath == '/corps-humain'}">
	        <head><title>Le corps humain</title></head>
	        <h1>Le corps humain</h1>
            <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur le corps humain. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: coeur, système circulatoire, système digestif, poumons, muscles, système musculaires, bras, jambes, appareil reproducteur, bouche, yeux, cerveau, etc.</p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/exercices-de-math'}">
	       <head><title>Exercices de math</title></head>
	       <h1>Exercices de math</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur les maths. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: les fractions, trigonométrie, les angles, la multiplication, la règle de 3, calcul mental...</p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/nederlands'}">
	        <head><title>Nederlands</title></head>
	       <h1>Nederlands</h1>
		   <p>ToujoursPlus.be référence des exercices, jeux éducatifs ou vidéos de néerlandais. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: le verbe “nemen”, animaux-dieren... </p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/neerlandais'}">
	        <head><title>Néerlandais</title></head>
	       <h1>Néerlandais</h1>
		   <p>ToujoursPlus.be référence des exercices, jeux éducatifs ou vidéos de néerlandais. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: le verbe “nemen”, animaux-dieren... </p>
	     </c:when>
	     
	      <c:when test="${urlPath == '/anglais-facile'}">
	        <head><title>Anglais facile</title></head>
	       <h1>Anglais Facile</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur la langue anglaise. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: the numbers, the months, the alphabet, London...</p>
	     </c:when>
	     
	      <c:when test="${urlPath == '/conjugaison'}">
	        <head><title>Conjugaison</title></head>
	       <h1>Conjugaison</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur la conjugaison. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: imparfait du subjonctif, verbes irréguliers, accord du participe passé, l’impératif, indicatif présent, participe présent, passé composé, passé simple...</p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/orthographe'}">
	        <head><title>Orthographe</title></head>
	       <h1>Orthographe</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur l’orthographe. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: accents, pluriel, verbes irréguliers, homophones, homonymes, féminin des noms, pluriel des noms…</p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/ecole-virtuelle'}">
	        <head><title>Ecole virtuelle</title></head>
	       <h1>Ecole virtuelle</h1>
           <p>ToujoursPlus.be permet aux élèves et professeurs de trouver facilement les meilleures cours, jeux éducatifs et vidéos sur internet. Il facilite la pédagogie différenciée et le rattrapage scolaire.  Essayez une recherche précise dans la bande bleue de recherche et trouvez votre bonheur parmi les milliers de liens que nous référençons.</p>
	     </c:when>
	     
	     
	     <c:when test="${urlPath == '/subjonctif'}">
	        <head><title>Subjonctif</title></head>
	       <h1>Subjonctif</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur le subjonctif et la conjugaison. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: imparfait du subjonctif, verbes irréguliers, accord du participe passé, l’impératif, indicatif présent, participe présent, passé composé, passé simple...</p>
	     </c:when>
	  
	     <c:when test="${urlPath == '/botanique'}">
	        <head><title>Botanique</title></head>
	       <h1>Botanique</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur la botanique. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: identification des plantes, arbres, feuilles, reproduction des végétaux, fruits et légumes…</p>
	     </c:when>
	     
	     <c:when test="${urlPath == '/ligne-du-temps'}">
	        <head><title>Ligne du temps</title></head>
	       <h1>Ligne du temps</h1>
           <p>ToujoursPlus.be référence de très nombreux exercices, jeux éducatifs ou vidéos sur la ligne du temps. Essayez une recherche précise dans la bande bleue de recherche, comme par exemple: la période gallo-romaine, les croisades, la Grèce Antique, Waterloo, le paléolithique... </p>
	     </c:when>
	     
	     
	     
	    <c:otherwise>
	        
	    </c:otherwise>
    </c:choose>
</div>



<%@ include file = "/WEB-INF/includes/search.jsp" %>
    

<div class="container" style="padding-top:20px; padding-bottome:20px;">

 	<section id="resourcelist">
		<c:forEach items="${resourceList}" var="resource">
			<lrftag:resource resource="${resource}" />
		</c:forEach>
	</section>

</div>


</body>
</html>