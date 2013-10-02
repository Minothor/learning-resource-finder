<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
	<title>Home page</title>
</head>
<body>

<ul>
    <c:forEach items= "${applicationScope.cache.cycles}" var="cycle">
       <li><a href="<c:url value='/cycle?id=${cycle.id}'/>">${cycle.name}</a></li>
    </c:forEach>
</ul>
  
  
</body>
</html>