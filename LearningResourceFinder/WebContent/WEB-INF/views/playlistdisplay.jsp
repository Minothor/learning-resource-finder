<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag" %>
<html>
<head>
<title>PlayList</title>
</head>
<body>
	<h1>PlayList</h1>
	<ul>
		<li>Nom: ${playlist.name}</li>
		<li>Description : ${playlist.description}</li>
		<li>Auteur : ${playlist.createdBy.fullName}</li>
	</ul>
	
    <h2>Ressources incluses</h2>
	
	<c:forEach items="${playlist.resourceList}" var="resource">
			<restag:resource resource="${resource}"></restag:resource>
	</c:forEach>


</body>
</html>