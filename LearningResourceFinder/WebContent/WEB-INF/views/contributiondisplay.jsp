<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="container">
		<lrftag:pageheadertitle title="Contributions de ${user.getFullName()}" />
		<a class="btn btn-primary pull-right"> recompute points </a> <br />
		<br />
		<div class="table-responsive">
			<table class="table table-bordered">
				<tr>
					<td><strong>Resource</strong></td>
					<td><strong>Action</strong></td>
					<td><strong>Date</strong></td>
					<td><strong>Points</strong></td>
				</tr>
				<c:forEach items="${contributions}" var="contribution">
					<tr>
						<td><a href="<c:url value='resource/${contribution.ressource.shortId}/${contribution.ressource.name}'/>">${contribution.ressource.name}</a>	</td>
						<td>${contribution.action.describe}</td>
						<td>${contribution.getCreatedOn()}</td>
						<td>${contribution.getPoints()}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>