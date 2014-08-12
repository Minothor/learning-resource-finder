<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<html>
<body>
<div class="container">

<c:choose>
    <c:when test="${result eq 'VALID_AND_LOGGED' }">
        <p>Merci pour votre inscription. Votre compte utilisateur a été validé avec succès. Vous pouvez à présent l'utiliser.</p>
        <p>Vous venez d'être connecté automatiquement.</p>
    </c:when>
    <c:when test="${result eq 'VALID_BUT_NOT_LOGGED' }">
        <p>Merci pour votre inscription. Votre compte utilisateur a été validé avec succès. Vous pouvez à présent l'utiliser.</p>
        <p>Cependant, il ne nous a pas été possible de vous connecter automatiquement et nous vous invitons à le faire manuellement via le menu "Connexion" dans le coin supérieur droit de la page</p>
    </c:when>
    <c:when test="${result eq 'ALREADY_VALIDATED' }">
        Votre compte utilisateur avait déjà été validé dans le passé. Vous pouvez vous connecter manuellement via le menu "Connexion" dans le coin supérieur droit de la page.</a>
    </c:when>
    <c:when test="${result eq 'INVALID_CODE' }">
        <p>Votre compte n'a <strong>PAS</strong> été validé. Vous avez transmis le code suivant via l'URL: '${code}'</p>
        <p>Veuillez utiliser le lien fourni dans votre e-mail d'inscription (en cliquant sur le lien ou en le copiant en entier dans la barre d'adresse de votre navigateur).</p>
    </c:when>
</c:choose>
</div>
</body>
</html>