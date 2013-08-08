<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<img src="http://mrsthiessenswiki.wikispaces.com/file/view/math_clipart.jpg" style="width:500px;height:100px;" />
		<h2>${resource.name}</h2>
		<p>${resource.description}</p>
		<p>${resource.createdBy.firstName} ${resource.createdBy.lastName}</p>
		<p></p>
		<a href="<c:url value='resourceedit?id=${resource.id}'/>">Edit resource</a>  <br>
	</div>
</body>
</html>