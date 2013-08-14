<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>
<link type="text/css" rel="stylesheet" href="form.css" />
</head>
<body>
	<form method="post" action="<c:url value="/connexion" />">
		<fieldset>
			<legend>Connexion</legend>
			
			<label for="nom">Adresse email </label> <input
				type="text" id="email" value="" name="email"/> <label for="motdepasse">Mot de passe </label>
			<input type="text" id="motdepasse" name="motdepasse"
				 /> <input type="submit" value="connexion"
				 /> <br />

			<p>${requestScope.result}</p>

			<c:if test="${!empty sessionScope.user }">

				<p class='succes'>Vous êtes connecté(e) avec l'adresse :
					${sessionScope.user.email}</p>

			</c:if>

		</fieldset>
	</form>
</body>
</html>