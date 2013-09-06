<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<!-- Jquery for change input popup addImageUser -->
<script type="text/javascript" src="/js/int/addImageUrlPlaylist.js"></script>
<title>PlayList</title>
</head>
<body>
	<h1>PlayList</h1>
	<lrftag:pageheadertitle title="${user.fullName}" />
	<div class="" style="font-size: 14px">
		<c:if test="${canEdit}">
		&nbsp&nbsp<a href=<c:url value='/playlist/edit?id=${playlist.id}'/>>Editer</a>
		&nbsp-&nbsp
	</c:if>
		<a href="playlist/user/${playlist.createdBy.userName}">Mes
			PlayLists</a> &nbsp-&nbsp<a href=<c:url value='/ressourcelist'/>>Vers
			l'arborescence des ressources</a>
		</li>
	</div>


	<lrftag:playlistimage canEdit="${canEdit}" playlist="${playlist}"
		random="${random}" />



	<br />
	<br />
	<ul>
		<li>Nom: ${playlist.name}</li>
		<li>Description : ${playlist.description}</li>
		<li>Auteur : <a href="/user/${playlist.createdBy.userName}">${playlist.createdBy.fullName}</a></li>
	</ul>

	<h2>Ressources incluses</h2>

	<c:forEach items="${playlist.resourceList}" var="resource">
		<div
			style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<lrftag:resource resource="${resource}"></lrftag:resource>

			<div
				style="padding: 10px; width: 180px; height: 10px; background-color: #F6CEF5">
				<a
					href=<c:url value='/playlist/remove?idplaylist=${playlist.id}&idresource=${resource.id}'/>>Remove</a>
			</div>
		</div>
	</c:forEach>
	<div id="popupJquery" class="popupJquery">
		<div class="popup-close">
			<a class="close" title="close this popup">X</a>
		</div>
		<form method="post" action="/playlist/imageadd"
			class="formUrlPlaylist" enctype="multipart/form-data">
			<h2>Ajouter une image</h2>
			<br /> <label><input type="radio" name="rdFrom"
				value="computer" class="radioComputer" id="inputComputer"
				checked="checked" /> Depuis l'ordinateur</label> <input type="file"
				name="file" value="Parcourir..." class="inputSource" id="inputFile" />
			<input type="hidden" name="strUrl" value="http://..."
				class="inputSource" id="inputUrl" /> <br /> <label><input
				type="radio" name="rdFrom" value="url" class="radioUrl" /> Depuis
				un lien</label> <input type="hidden" name="idPlayList"
				value="${playlist.id}" /> <br /> <br /> <input class="btnSubmit"
				type="submit" value="Ajouter" name="btnPicture" />
		</form>
		<br />
	</div>
</body>
</html>