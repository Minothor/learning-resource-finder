<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head itemscope itemtype="http://schema.org/WPHeader">
	<title>ToujoursPlus.be - Vidéos, jeux et exercices pour l'école</title>
	<meta itemprop="keywords" name="keywords" content="soutien scolaire gratuit, cours particuliers, cours à domicile, vidéos, exercices en ligne, aide aux devoirs, leçons interactives, éveil, vidéos, jeu,education, apprentissage, eveille, français, mathematique, maths, scientifique, leçons, exercices, interactif, apprendre, découvrir, savoir" />
	<meta property="og:url" content="http://toujoursplus.be" />
	<meta itemprop="title" property="og:title" content="ToujoursPlus" /> 
	<meta itemprop="description" property="og:description" content="Moteur de recherche collaboratif pour qu'élèves et enseignants trouvent facilement des vidéos, exercices et jeux éducatifs gratuits." />  
	<meta itemprop="image" property="og:image" content="http://toujoursplus.be/images/ToujoursPlus-montage-Facebook.png" /> 
	
	<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
	
</head>
<body>


<%---------------------------------- LOGO ----------------------------------%>
<div class="container" style="padding-top:20px; padding-bottom:20px;">
   <div class="text-center">
       <img src="/images/ToujoursPlus-logo-500px.png" alt="logo ToujoursPlus créateur de curiosité" class="img-responsive" style="margin: 0 auto;" alt="ToujoursPlus.be logo" /><br/>
       <br/>
   </div>
   <div class="row">
     <div class="col-md-2"></div>
     <div class="col-md-5">
     		Moteur de recherche collaboratif pour élèves, parents et enseignants.<br/> 
     		Trouvez facilement des vidéos, exercices et jeux éducatifs gratuits.<br/>
	       <br/>	      			
     </div>
	<div class="fb-like" data-href="http://www.toujoursplus.be" data-width="400" data-layout="button" data-action="like" data-show-faces="true" data-share="true"  style="margin-top:0.6em; position: relative; float: left;"></div>
		<div class="col-md-3 text-right">
	      	 <a href="/resourcelist"><span style="font-size:20px; margin-top: 0.2em; position: relative; float:left; " class="label label-info addToolTip" title="${nbResouces} ressources ajoutées depuis le 21 avril 2014">${nbResouces}</span></a>
    	 <div class="col-md-2"></div>     
   </div>
</div>


<%------------------------------------ SEARCH ---------------------------------%>
<%@include file = "/WEB-INF/includes/search.jsp" %>



<%------------------------------------ YEARS ---------------------------------%>
<div class="container" style="padding-top:20px; padding-bottom:40px;">
	<div class="row">
	  <div class="col-sm-6" style="text-align: center;">
	      <h3>primaire</h3><br/>
	      <a style="width:170px;" class="btn btn-primary btn-lg" href="<c:url value='/cycle/${applicationScope.cache.cycles[0].id}/${applicationScope.cache.cycles[0].slug}'/>">1<sup>ère</sup> &nbsp;&nbsp;-&nbsp;&nbsp; 2<sup>ème</sup></a><br/>
	      <br/>
	      <a style="width:170px;" class="btn btn-primary btn-lg" href="<c:url value='/cycle/${applicationScope.cache.cycles[1].id}/${applicationScope.cache.cycles[1].slug}'/>">3<sup>ème</sup> &nbsp;&nbsp;-&nbsp;&nbsp; 4<sup>ème</sup></a><br/>
	      <br/>
	      <a style="width:170px;" class="btn btn-primary btn-lg" href="<c:url value='/cycle/${applicationScope.cache.cycles[2].id}/${applicationScope.cache.cycles[2].slug}'/>">5<sup>ème</sup> &nbsp;&nbsp;-&nbsp;&nbsp; 6<sup>ème</sup></a><br/>
	  </div>
	  <div class="col-sm-6 " style="text-align: center;">
	      <h3>secondaire</h3><br/>
	      <a style="width:170px;" class="btn btn-primary btn-lg" href="<c:url value='/cycle/${applicationScope.cache.cycles[3].id}/${applicationScope.cache.cycles[3].slug}'/>">1<sup>ère</sup> &nbsp;&nbsp;-&nbsp;&nbsp; 2<sup>ème</sup></a><br/>
	      <br/>
	      <a style="width:170px;" class="btn btn-primary btn-lg" href="<c:url value='/cycle/${applicationScope.cache.cycles[4].id}/${applicationScope.cache.cycles[4].slug}'/>">3<sup>ème</sup> &nbsp;&nbsp;-&nbsp;&nbsp; 6<sup>ème</sup></a><br/>
	  </div>
	</div>
</div>  

<%------------------------------------ TESTIMONIALS ---------------------------------%>
<div style="background-color:#84BB04;">
<div class="container" style="padding:20px 20px; color:white;">
	<%@include file="/WEB-INF/includes/testimonials.jsp" %>
</div>
</div>

<%---------------------------------- COUNTER ----------------------------------
<div class="container" style="padding-top:40px; padding-bottom:40px;">
  <%@include file="/WEB-INF/includes/counter.jsp" %>
</div>
--%>

<%------------------------------------ PURPOSES -------------------------------------%>
<div style="background-color:white;">
<div class="container" style="padding-top:20px; padding-bottom:20px;">
	<%@include file="/WEB-INF/includes/purposes.jsp" %>
</div>
</div>

<%------------------------------------ BENEFITS -------------------------------------%>
<div style="background-color:#17A7D6;">
<div class="container" style="padding-top:20px; padding-bottom:20px; color:white;">
	<%@include file="/WEB-INF/includes/benefits.jsp" %>
</div>
</div>

<%------------------------------------ SUPPORT --------------------------------------%>
<div style="background-color:white;">
<div class="container" style="padding:20px 20px;">
	<%@include file="/WEB-INF/includes/support.jsp" %>
</div>
</div>

</body>
</html>