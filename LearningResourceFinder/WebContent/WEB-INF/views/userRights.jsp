<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Rights</title>
</head>
<body>
	<h1>Help > Privileges</h1>
	<p>Description de la page ... ??</p>

	<c:forEach items="${actions}" var="action">
		<div class="row">
					<c:if test="${user != null && user.accountLevel.levelIndex >= action.level.levelIndex}">
					    <c:set var="style" value="text-success"/>
						</c:if>
						
						<div class="col-md-9 col-md-push-3 ${style}">${action} --> ${action.describe}</div>
						<div class="col-md-3 col-md-pull-9  ${style}">Level : ${action.level.levelIndex }</div>
                        <c:set var="style" value=""/>
		</div>
	</c:forEach>

</body>
</html>