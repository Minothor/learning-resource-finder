<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
$(document).ready(function() {
    $("#formContact").submit(sendDonation);
});
function sendDonation(e){
	e.preventDefault();
	var sender = $('#email').val();
	var subject = $('#subject').val();
	var content = $('#body').val();
			
	$.ajax({
		type : "POST",
	    url : '/ajax/sendmail',
	    data: "sender="+sender+"&subject="+subject+"&content="+content,
	    dataType: 'text',
	    success : function(data) {
	    	alert('Merci. Votre message a été envoyé avec succès. Nous vous répondons par e-mail dès que possible.');
	    },
	    error : function(data) {
	    	alert("Il y a un petit problème technique lors de l'envoi du message. Avez-vous remplis tous les champs du formulaire?");
	    }
	}); 
}

</script>

<section id="fbsection5" class="wrapper contact-us">
<section class="container" style="text-align:center; width:820px;">
<h1>Comment pouvez-vous nous soutenir?</h1>
<p>Nous avons besoin de vous pour témoigner, diffuser le concept dans votre école/association, tester le site en version beta, participer à des réunions d’analyse et de feedback, donner des millions de dollars.</p>
<br>
<form id="formContact" role="form" style="margin-left:15%; width:70%;">
  <div class="form-group">
    <input type="email" class="form-control" id="email" placeholder="Votre e-mail">
  </div>
  <div class="form-group">
    <input type="text" class="form-control" id="subject" placeholder="Sujet">
  </div>
  <div class="form-group">
    <textarea id="body" placeholder="Votre message" name="body" class="form-control" rows="5"></textarea>
  </div>
  <button type="submit" class="btn btn-default">Envoyer</button>
</form>
</section>
</section>