<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home page</title>
</head>
<body>




 <ul>
    <c:forEach items= "${cyclelist}" var="cycle">
       <li><a href="<c:url value='cycle?id=${cycle.id}'/>">${cycle.name}</a> </li>
</c:forEach>
  </ul>
  <br/>
  
  
</body>
</html>