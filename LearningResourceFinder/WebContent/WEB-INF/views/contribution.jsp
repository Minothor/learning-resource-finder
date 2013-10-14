<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="Contribution">
		<lrftag:breadcrumbelement label="Home" link="home" />
	</lrftag:breadcrumb>
	<div class="container">
		<h2>Contribution</h2>
		<br />
		<c:forEach var="topic" items="${mapProblem}" varStatus="status">
		<ul class="list-group">
			<a href="#" class="list-group-item active">
		    	${topic.key.description}
		  	</a>
			<li class="list-group-item">
		    	<span class="badge">${topic.value[0]}</span>
		    	<a href="/problemresourcelist/${topic.key.description}">Problèmes à corriger.</a>
		  	</li>
		  	<li class="list-group-item">
		    	<span class="badge">${topic.value[1]}</span>
		    	<a href="/incompleteresourcelist/${topic.key.description}">Ressoures incomplètes.</a>
		  	</li>
		  	<li class="list-group-item">
		    	<span class="badge">${topic.value[2]}</span>
		    	<a href="/unboundresourcelist/${topic.key.description}">Ressources liées à aucunes compétences.</a>
		  	</li>
		</ul>	
		</c:forEach>

		
	</div>
</body>
</html>