<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Playlist editing</title>
</head>
<body>
	<h1>Edit PlayList</h1>
	<form:form modelAttribute="playlist" method="post" action='<%=response.encodeUrl("/playlist/editsubmit")%>'>
		<form:hidden path="id" />
		<label>Description</label> <form:input path="description" />  <form:errors path="description"/><br>
	    <label>Name</label>        <form:input path="name" />         <form:errors path="name"/><br>
		
		<input type="submit" value="Save" />
	</form:form>
	
	
	
</body>
</html>