<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css" media="screen">
	label
	{
	    display:block;
	    width:100px; 
	    float:left;
	}
	textarea
	{
		width: 400px;
		height: 25Opx;
	}
</style>
</head>
<body>
	<h1>Resource Edit :</h1>
	<form:form modelAttribute="resource" method="post" action='<%=response.encodeUrl("resourceeditsubmit")%>'>
		<p>
			<label>Name :</label>        <form:input path="name" /> <form:errors path="name" />
		</p>
		<p>
			<label>Description :</label> <form:textarea path="description" /> <form:errors path="description" />
		</p>
		<p>
			<label>Author :</label>      <form:input path="createdBy" disabled="true" />
		</p>
		<p>
			<form:hidden path="id" />
			<input type="submit" value="Save" />
		</p>
	</form:form>
	
	<br />
	<a href="resource?id=">home page</a>
</body>
</html>