<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title></title>
</head>
<body>

<lrf:breadcrumb linkactive="${cycle.name}">
		<lrf:breadcrumbelement label="Home" link="home" />
		<lrf:breadcrumbelement label="Liste des cycles" link="cyclelist" />
	</lrf:breadcrumb>

<h1>${cycle.name}</h1>

 <a href="<c:url value='cycleedit?id=${cycle.id}'/>">Edit Cycle</a>  <br>
</body>


</html>