<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			<td>${playlist.author.userName}</td>
			<td></td>

		</tr>
		
		<tr>
			<td>Ressources</td>
		</tr>
		

		<c:forEach items="${playlist.resourceList}" var="resource">
			<tr>
				<td>${resource.name}</td>
				<td>${resource.description}</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>