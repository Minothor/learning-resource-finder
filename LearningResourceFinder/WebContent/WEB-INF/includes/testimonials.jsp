<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>

<style>
.testimonialimg {
	margin-top: 40px;
	margin-bottom: 40px;
}

.testimonialtext {
	margin-top: 40px;
}

.testimonialtext label{
	width: 100%;
	display: table;
	text-align: right;
	font-weight: normal; 
}

.testimonialtext span{
	font-size: 21px;
}

a:focus { 
    outline: none; 
}
</style>

<script>
$(document).ready(function() {
    $("#carousel-testimonial").carousel('cycle');
});
</script>


<h1 class="text-center">Témoignages</h1>
<div id="carousel-testimonial" data-interval="5000" class="carousel slide">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-testimonial" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="1"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="2"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="3"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="4"></li>
	  </ol>
	
	  <!-- Wrapper for slides -->
	  <div class="carousel-inner">
	    <div class="item active">
	       <lrftag:testimonial 
	          imageName = "Marianne-Poumay.jpg"
	          name="Marianne Poumay, Liège"
	          function="Directrice du Laboratoire de Soutien à l'Enseignement par Télématique, ULg"
	          mainText = "Les outils au service des enseignants sont nombreux. ToujousPlus n’est pas juste un outil de plus, c’est aussi un outil différent. 
	            Il libère l’enseignant de certains contenus pour lui permettre de se centrer sur le suivi des élèves, 
	            la partie la plus complexe de son métier. ToujoursPlus est aux côtés des enseignants pour aider les élèves à piloter 
	            leur apprentissage. Un programme ambitieux!"
	        />
	    </div>
	  
	    <div class="item">
	       <lrftag:testimonial 
	          imageName = "Pierre-Pirard.jpg"
	          name="Pierre Pirard, Bruxelles"
	          function="Managing Director de Teach for Belgium"
	          mainText = "Super initiative qui si elle tient ses promesses nous permettra comme enseignants d’avoir des outils pratiques et pour 
	       			 nous élèves de progresser à leur rythme. Initiative qui me semble win-win."
	        />
	    </div>


	    <div class="item">
	       <lrftag:testimonial 
	          imageName = "Philippe-le-Hardy.jpg"
	          name="Philippe le Hardy - Bruxelles"
	          function="Formateur en méthode de travail pour les mathématiques et sciences économiques."
	          mainText = "C'est gai d'apprendre, voilà le message fort envoyé par ToujoursPlus." 
	        />
	    </div>


	    <div class="item">
	       <lrftag:testimonial 
	          imageName = "Nicole-Streitz.jpg"
	          name="Nicole Streitz, Nivelles"
	          function="Jeune assistante sociale PMS retraitée"
	          mainText = "ToujoursPlus.be facilitera la tâche des enseignants dans leur recherche d'adaptation de la matière à enseigner aux 
	        		méthodes modernes. Beaucoup de parents apprécieront de pouvoir évaluer l'évolution de leurs enfants et de pouvoir les aider à 
	        		se mettre à niveau dans les matières où leurs compétences sont plus faibles." 
	        />
	    </div>

	    <div class="item">
	       <lrftag:testimonial 
	          imageName = "Francois-Georges.jpg"
	          name="François Georges, Liège"
	          function="Directeur et chargé de cours adjoint au Laboratoire de Soutien à l'Enseignement par Télématique, ULg."
	          mainText = "« Toujours plus… » d’activités, de choix, d’échanges, d’implication, de plaisir pour « Toujours mieux… » apprendre." 
	        />
	    </div>


	  </div>
	
	  <!-- Controls -->
 	  <a class="left carousel-control" href="#carousel-testimonial" data-slide="prev" style="background-image:none">
	    <span class="glyphicon glyphicon-chevron-left"></span>
	  </a> 
	  <a class="right carousel-control" href="#carousel-testimonial" data-slide="next" style="background-image:none">
	    <span class="glyphicon glyphicon-chevron-right"></span>
	  </a>
</div>
<%-- <script src="http://getbootstrap.com/assets/js/holder.js"></script>  <%-- for carrousel --%>