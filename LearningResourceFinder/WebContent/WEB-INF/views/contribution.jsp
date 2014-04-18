<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="Contribution">
		<lrftag:breadcrumbelement label="Home" link="home" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="Am�liorer des ressources" />

		<c:forEach var="topic" items="${mapProblem}" varStatus="status">
			<div class="panel panel-default" style="display:inline-block; vertical-align:middle; width:330px; margin-right:20px; margin-bottom:20px;">
			
  				<div class="panel-heading"><h4>${topic.key.description}</h4></div>

		       		<ul class="list-group" >
					  	<li class="list-group-item">
			    			<a href="/problemresourcelist/${topic.key}">${topic.value[0]} probl�me${topic.value[0] > 1 ? "s" : ""} � corriger.</a>
					  	</li>
					  	<li class="list-group-item">
			    			<a href="/fieldsnullresourcelist/${topic.key}">${topic.value[1]} ressource${topic.value[1] > 1 ? "s" : ""} incompl�te${topic.value[1] > 1 ? "s" : ""}.</a>
					  	</li>
					  	<li class="list-group-item">
			    			<a href="/notcompetencesresourcelist/${topic.key}">${topic.value[2]} ressource${topic.value[2] > 1 ? "s" : ""} li�e${topic.value[2] > 1 ? "s" : ""} � aucune comp�tence.</a>
					  	</li>
                   </ul>					  	

			</div>
		</c:forEach>
	</div>
</body>
</html>