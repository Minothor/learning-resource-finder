<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PlayList</title>
</head>
<body>
	<h1>PlayList</h1>
	<br>

	<table cellspacing="10">
		<tr>
			<td>Nom</td>
			<td>Description</td>
			<td>Utilisateur</td>
			<td></td>
		</tr>
		
		<tr>
			<td>${playlist.name}</td>
			<td>${playlist.description}</td>
			<td></td>

		</tr>
		
		<tr>
			<td><i>Ressources incluses</i></td>
		</tr>
		

		<c:forEach items="${playlist.resourceList}" var="resource">
			<tr>
				<td>
					<restag:resource res="${resource}"></restag:resource>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>