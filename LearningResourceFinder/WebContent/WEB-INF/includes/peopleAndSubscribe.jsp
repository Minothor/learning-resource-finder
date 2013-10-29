<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!-- start mail -->
<script type="text/javascript">
$(document).ready(function() {
    $("#formMail").submit(ajaxPostUrlResource);
});
function ajaxPostUrlResource(e) {
		e.preventDefault();
		if (mail != '') {
			$.ajax({
				type : "POST",
			    url : '/ajax/addMailOnTable',
			    data: $("#formMail").serialize(),
			    success : function(data) {
			    	alert("Merci pour votre intérêt, nous vous tiendrons informés des prochaines grandes étapes.");
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ai eu une petite erreur sur notre serveur lorsque vous avez introduit \n " +
	    			"votre e-mail. Cela montre bien que nous ne sommes pas encore prêts :-( Toutes nos excuses.");
	        return false;
			    },
			}); 
		}else {
			alert("Vous n'avez pas entreé de mail.");
		} 
	}
</script>
<!-- end mail -->

<!-- Script facebook -->
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/fr_FR/all.js#xfbml=1&appId=1414750432070920";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<!-- /Script facebook -->


<div class="row">
    <div class="col-md-2"  style="width:275px;">
       <div id="peopleSlides">
	      <img src="/images/handsup/handsup-1.jpg" alt="">
	      <img src="/images/handsup/handsup-2.jpg" alt="">
	      <img src="/images/handsup/handsup-3.jpg" alt="">
	      <img src="/images/handsup/handsup-4.jpg" alt="">
	      <img src="/images/handsup/handsup-5.jpg" alt="">
	      <img src="/images/handsup/handsup-6.jpg" alt="">
	      <img src="/images/handsup/handsup-7.jpg" alt="">
	    </div>
        <p style="text-align:center" class="text-muted">Pour nos enfants (6-18)</br>
            et nos enseignants,<br/>
        <small>gratuit, sans but lucratif.</small></p>
    </div>
    
    <div class="col-md-6 col-md-offset-2" style="padding-top:115px;">
             	<h3>Soyez informés du lancement</h3>
                <div class="contact-us" style="text-align:left;">
                	<form id="formMail" class="form-inline" role="form">
                	    <input type="email" name="mail" id="mail" class="form-control input-lg" placeholder="Entrez votre adresse e-mail" style="width:300px;" required/>
                	    <button type="submit" class="btn btn-primary btn-lg">Envoyer</button>
                	</form>
                </div>
                <br/>
                <br/>
                <p><small>Suivez-nous sur facebook pour être informé du lancement et/ou discuter du développement en cours.</small></p>
				<div class="fb-like" style="margin-top:0px;" 
					data-href="https://www.facebook.com/toujoursplus.be"
					data-width="450" data-layout="button_count" data-show-faces="true">
				</div>
     </div>
            
</div>

<script type="text/javascript" src="/js/ext/jquery.slides.min.js"></script>
<script>
$('#peopleSlides').slidesjs({
        height: 1400,
        navigation: false,
        pagination: false,
        generatePagination: false,
        start: 3,
        play: {auto: true, effect:"fade", interval: 2500, },
});
</script>