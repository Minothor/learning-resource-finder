<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<body>
	<div class="container">
		<lrftag:pageheadertitle title="Comment faire une capture d’écran et l’utiliser comme image associée à une ressource ?" />
		<br/>
		
		<p><h4>(1) Capturer l’écran dans le presse-papier</h4>
        Affichez d’abord le site de la ressource (jeu, exercice, vidéo, etc.) concernée. Ensuite appuiez sur la touche « Imp Ecran » 
        (ou sur certains claviers « PrtScr » ou « Print Scrn ») de votre clavier. Cette touche est en général en haut à droite au-dessus
        de la touche « Insert » et juste à droite de la touche F12. C'est tout à fait normal qu'il ne se passe rien !
        Mais votre écran a été copié dans votre presse papier (c'est à dire au même endroit que là où les données sont stockées lorsque 
        vous copiez-collez un texte).</p><br/> 

		<p><h4>(2) Envoyer l’image sur ToujoursPlus.be</h4>
		Ensuite, revenez au site « ToujoursPlus.be », à la page qui affiche les informations de la ressource concernée. Près du titre « Image », 
		cliquez sur le signe « + » et choisissez l’option « Depuis le presse papier ». Votre capture d’écran s’affiche à l’écran. Si vous le désirez,
		en appuyant sur le bouton « Recadrer », vous avez alors la possibilité de sélectionner une zone bien précise de cette image à l’aide de 
		votre souris. Faites glisser le pointeur sur la partie de l’image à conserver afin de créer le rectangle de sélection. Pour déplacer le 
		rectangle de sélection, placez le pointeur à l’intérieur du cadre de sélection et faites-le glisser. Sélectionnez une zone pour n’avoir que 
		l’essentiel, de quoi permettre à un élève ou un professeur de « se faire une idée » en voyant une miniature de cette image.</p><br/>

		<p><h4>(3) Sauver</h4>
		Lorsque le résultat vous convient, il ne vous reste ensuite plus qu’à appuyer sur le bouton « Sauver l’image » et votre image sera alors 
		correctement associée à la ressource courante. Répétez le processus si vous désirez ajouter plusieurs images pour cette ressource.</p>

		<p>Pour information, de nombreux programmes utilitaires gratuits existent sur internet pour prendre des captures d’écran et vous permettent de directement capturer l’écran en entier, la fenêtre active 
		ou même directement une zone précise. <a href="http://www.techsmith.com/jing.html" target="_blank">Jing</a> est l’un d’entre eux.</p>

	
		<p><a href="/resource/${resource.shortId}/${resource.slug}">Retour vers la ressource : ${resource.name}</a></p>
	</div>
</body>
</html>