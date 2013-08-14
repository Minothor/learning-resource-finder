<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>
<html>
<head>
<title>PlayList</title>
</head>
<body>
	<h1>PlayList</h1>

	<lrftag:pageheadertitle title="${user.fullName}" />
	<div class=" " style="font-size: 14px">
		&nbsp&nbsp<a href=<c:url value='/playlist/edit?id=${playlist.id}'/>>Editer</a>
		&nbsp-&nbsp<a href="playlist/user/${playlist.createdBy.userName}">Mes PlayLists</a>
		&nbsp-&nbsp<a href=<c:url value='/ressourcelist'/>>Vers l'arborescence des ressources</a>
		</li>
	</div>
	<ul>
		<li>Nom: ${playlist.name}</li>
		<li>Description : ${playlist.description}</li>
		<li>Auteur : <a href="/user/${playlist.createdBy.userName}">${playlist.createdBy.fullName}</a></li>
	</ul>

	<h2>Ressources incluses</h2>

	<c:forEach items="${playlist.resourceList}" var="resource">
		<div
			style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<restag:resource resource="${resource}"></restag:resource>

			<div
				style="padding: 10px; width: 180px; height: 10px; background-color: #F6CEF5">
				<a
					href=<c:url value='/playlist/remove?idplaylist=${playlist.id}&idresource=${resource.id}'/>>Remove</a>
			</div>
		</div>
	</c:forEach>
</body>
</html>