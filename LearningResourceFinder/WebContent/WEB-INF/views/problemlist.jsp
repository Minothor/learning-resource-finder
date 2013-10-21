<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

	<table class="table table-hover">
	<colgroup>
		<col class="col-md-3" />
		<col class="col-md-6" />
		<col class="col-md-3" />
	</colgroup>
<!-- 		
		<tr>
			<th>Nom</th>
			<th>Description</th>
			<th>Création</th>
		</tr>
 -->	<c:forEach items="${problemList}" var="problem">
		
			<c:choose>
				<c:when test="${problem.resolved!=true}">
				<tr class="warning" onclick="document.location='../../problem?id=${problem.id}'" style=cursor:pointer>
					<td>${problem.name}</td>
				</c:when>
				<c:otherwise>
				<tr onclick="document.location='../../problem?id=${problem.id}'" style=cursor:pointer>
					<td><s>${problem.name}</s></td>
				</c:otherwise>
			</c:choose>
			<td>${problem.description}</td>
			<td><c:out value="${problemDate[problem.id]}"></c:out></td>
		</tr>
	</c:forEach>
</table>