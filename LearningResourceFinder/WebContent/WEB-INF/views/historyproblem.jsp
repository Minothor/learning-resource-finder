<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<html>
<head>	
</head>
<body>
	<lrftag:breadcrumb linkactive="Historique des problèmes">
		<lrftag:breadcrumbelement label="Home" link="/home" />
	</lrftag:breadcrumb>
	<div class="container">
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>Titre</th>
					<th>Description</th>
					<th>Auteur</th>
					<th>Date</th>
					<th>Statut</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${problemList}" var="problem">
				<tr>
					<td>${problem.name}</td>
					<td>${problem.description}</td>
					<td>${problem.createdBy.userName}</td>
					<td>${problem.createdOn}</td>
					<td></td>
				</tr>		
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>