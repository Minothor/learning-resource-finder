<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Resource Edit :</h1>
	<form:form modelAttribute="book" method="post" action='<%=response.encodeUrl("")%>'>
		<label>Name</label> <form:input path="name" /> <form:errors path="name" /><br />
		<label>Description</label> <form:input path="description" /> <form:errors path="description" /><br />
		<label>Author</label> <form:input path="createdBy" disabled="true" />
		<form:hidden path="id" />
		<input type="submit" value="Save" />
	</form:form>
</body>
</html>