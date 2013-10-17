<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div id="carousel-testimonial" data-interval="10000" class="carousel slide">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-testimonial" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="1"></li>
	    		
	    <li data-target="#carousel-testimonial" data-slide-to="2"></li>
	    <li data-target="#carousel-testimonial" data-slide-to="3"></li>
	    
	  </ol>
	
	  <!-- Wrapper for slides -->
	  <div class="carousel-inner">
	    <div class="item active">
	    	<div class="row">
	    		<div class="col-sm-2"></div><%-- blank space for the previous arrow --%>
	    		<div class="col-sm-3 testimonialimg">
	    			<img alt="" src="/comingsoon1/testimonial-4.jpg">
	    		</div>
	    		<div class="col-sm-5 testimonialtext">
	        <p>Les outils au service des enseignants sont nombreux. ToujousPlus n’est pas juste un outil de plus, c’est aussi un outil différent. 
	        Il libère l’enseignant de certains contenus pour lui permettre de se centrer sur le suivi des élèves, 
	        la partie la plus complexe de son métier. ToujoursPlus est aux côtés des enseignants pour aider les élèves à piloter 
	        leur apprentissage. Un programme ambitieux!</p>
	        <label>
			<span>Marianne Poumay, Liège</span>
			<br>
				Directrice du Laboratoire de Soutien à l'Enseignement par Télématique, ULg.
			</label>
	    		</div>
	    		<div class="col-sm-2"></div><%-- blank space for the next arrow --%>
	    	</div>		        
	     	 <div class="carousel-caption">
	     	 </div>
	    </div>
	    
	    <div class="item">
	    	<div class="row">
	    		<div class="col-sm-2"></div><%-- blank space for the previous arrow --%>
	    		<div class="col-sm-3 testimonialimg">
	    			<img src="/comingsoon1/testimonial-1.jpg" alt="" />
	    		</div>
	    		<div class="col-sm-5 testimonialtext">
					<p>Super initiative qui si elle tient ses promesses nous permettra comme enseignants d’avoir des outils pratiques et pour 
	       			 nous élèves de progresser à leur rythme. Initiative qui me semble win-win.</p>
					<label>
						<span>Pierre Pirard, Bruxelles</span>
						<br>
						Managing Director de Teach for Belgium
					</label>	
	    		</div>
	    		<div class="col-sm-2"></div><%-- blank space for the next arrow --%>
	    	</div>	    
	    </div>
	    
	    <div class="item">
	    	<div class="row">
	    		<div class="col-sm-2"></div><%-- blank space for the previous arrow --%>
	    		
	    		<div class="col-sm-3 testimonialimg">
	    			<img src="/comingsoon1/testimonial-2.jpg" alt="" />
	    		</div>
	    		<div class="col-sm-5 testimonialtext">
	    		     <p>C'est gai d'apprendre, voilà le message fort envoyé par ToujoursPlus.</p>
	    			<label>
						<span>Philippe le Hardy - Bruxelles</span>
						<br>
						Formateur en méthode de travail pour les mathématiques et sciences économiques.
					</label> 	
	    		</div>
	    		<div class="col-sm-2"></div><%-- blank space for the next arrow --%>
	    		
	    	</div>
	    
	    
	      <div class="carousel-caption">
	      </div>
	    </div>
	    <div class="item">
	    	<div class="row">
	    		<div class="col-sm-2"></div><%-- blank space for the previous arrow --%>
	    		
	    		<div class="col-sm-3 testimonialimg">
	    			<img src="/comingsoon1/testimonial-3.jpg" alt="" />
	    		</div>
	    		<div class="col-sm-5 testimonialtext">
	    		    <p>ToujoursPlus.be facilitera la tâche des enseignants dans leur recherche d'adaptation de la matière à enseigner aux 
	        		méthodes modernes. Beaucoup de parents apprécieront de pouvoir évaluer l'évolution de leurs enfants et de pouvoir les aider à 
	        		se mettre à niveau dans les matières où leurs compétences sont plus faibles.</p>
	    			<label>
						<span>Nicole Streitz, Nivelles</span>
						<br>
						Jeune assistante sociale PMS retraitée
					</label> 	
	    		</div>
	    		<div class="col-sm-2"></div><%-- blank space for the next arrow --%>
	    		
	    	</div>
	      <div class="carousel-caption">

	      </div>
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
<script src="http://getbootstrap.com/assets/js/holder.js"></script>