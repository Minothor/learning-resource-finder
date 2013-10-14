<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<div class="container">
	<table class="table table-bordered ">
		<tr>
			<th>Nom</th>
			<th>Description</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${problemList}" var="problem">
			<tr>
				<td>${problem.name}</td>
				<td>${problem.description}</td>
				<td><a href="<c:url value='/?id=${problem.id}'/>">Clôturer</a></td>
			</tr>
		</c:forEach>
	</table>
</div>