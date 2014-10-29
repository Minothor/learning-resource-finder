<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
	<title>Landing page</title>
	<meta name="title" content="ToujoursPlus.be - Vidéos, jeux et exercices pour l'école" />
	<meta name="description" content="Moteur de recherche collaboratif pour qu'élèves et enseignants trouvent facilement des vidéos, exercices et jeux éducatifs gratuits." />
	<meta name="keywords" content="soutien scolaire gratuit, cours particuliers, cours à domicile, vidéos, exercices en ligne, aide aux devoirs, leçons interactives, éveil, vidéos" />
	<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
</head>
<body>

<jsp:include page="/WEB-INF/includes/${introtextjsp}" />


<%-- <jsp:include page="/WEB-INF/includes" />
 --%>


<div style="background-color:#17A7D6;">
<div class="container" style="padding:20px 20px;">
<div class="row">
  <div class="col-md-8 col-md-offset-2">
  		<form role="search" method="get" action="/search">
  				   <div class="form-group">
					<div class="input-group" >
						<input name="searchphrase"  type="text" class="form-control input-lg searchinput" placeholder="mot(s) clef(s)  ex: fraction, Bonaparte, abeille,..." required>
						<span class="input-group-btn">
							<button class="btn btn-default btn-lg" type="submit">Rechercher</button>
						</span>
					</div>
				  </div>
		</form>
  </div>
</div>  
</div>
</div>

  




</body>
</html>