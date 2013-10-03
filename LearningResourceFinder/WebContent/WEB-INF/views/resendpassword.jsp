<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
	<body>
		<div class="container">
		
			<p>Nous allons vous renvoyer un mot de passe sur votre
				adresse e-mail. Vous devez avoir été enregistré au préalable sur notre site avec cette adresse e-mail, merci de la renseigner ci-dessous. Alternativement, vous pouvez indiquer votre nom d'utilisateur.</p>
			
			<form class="form-horizontal" role="form" method="post" action="resendpasswordsubmit">
			  	<div class="form-group">
			    	<label for="inputEmail1" class="col-lg-2 control-label">Email ou nom d'utilisateur</label>
			    	<div class="col-lg-4">
			      		<input type="email" class="form-control" id="inputEmail1" placeholder="Email" name="identifier">
						<div style="color: red;">
							<c:if test='${mailerrormessage != null}'>${mailerrormessage}</c:if>
							<c:if test='${param.mailerrormessage != null}'>${param.mailerrormessage}</c:if>
						</div>
			    	</div>
			  	</div>
			  
			  	<div class="form-group">
			    	<div class="col-lg-offset-2 col-lg-10">
			      		<button type="submit" class="btn btn-primary">Envoyez moi un nouveau mot de passe</button>
			    	</div>
			  	</div>
			  	
			</form>
		</div>
	</body>
</html>