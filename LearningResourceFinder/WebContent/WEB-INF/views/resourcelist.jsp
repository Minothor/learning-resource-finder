
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Catalog</title>
</head>
<body>


	${toto} xxxxxxx
	<table cellspacing="10">
		<tr>
			<th>name</th>
			<th>description</th>
			<th>author</th>
		<tr>

			<c:forEach items="${resourceList}" var="resource">
				<tr>
					<td>
						<a href="<c:url value='resource?id=${resource.id}'/>">${resource.name}</a>
					</td>
					<td>${resource.description}</td>
					<td>${resource.createdBy.firstName }</td>				
				</tr>
			</c:forEach>
	</table>

</body>
</html>