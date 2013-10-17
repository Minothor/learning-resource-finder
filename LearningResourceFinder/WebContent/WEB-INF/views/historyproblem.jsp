<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
					<th class="col-md-2">Titre</th>
					<th class="col-md-6">Description</th>
					<th class="col-md-2">Auteur</th>
					<th class="col-md-2">Date</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${problemList}" var="problem">
				<c:choose>
		    		<c:when test="${problem.resolved}">
				<tr>
					<td><a class="linethrough" href="/problem?id=${problem.id}">${problem.name}</a></td>
					<td  class="linethrough">${problem.description}</td>
					<td><a href="/user/${problem.createdBy.userName}">${problem.createdBy.userName}</a></td>
					<td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"  value="${problem.createdOn}" /></td>
				</tr>
					</c:when>
					<c:otherwise>
				<tr>
					<td><a href="/problem?id=${problem.id}">${problem.name}</a></td>
					<td>${problem.description}</td>
					<td><a href="/user/${problem.createdBy.userName}">${problem.createdBy.userName}</a></td>
					<td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"  value="${problem.createdOn}" /></td>
				</tr>
					</c:otherwise>
		    	</c:choose>		
			</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>