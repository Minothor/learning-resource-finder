<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<body>
	<lrftag:breadcrumb linkactive="contribution">
		<lrftag:breadcrumbelement label="home" link="home" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="Améliorer des ressources" />

		<c:forEach var="topic" items="${mapProblem}" varStatus="status">
			<div class="panel panel-default" style="display:inline-block; vertical-align:middle; width:330px; margin-right:20px; margin-bottom:20px;">
			
  				<div class="panel-heading"><h4>${topic.key.description}</h4></div>

		       		<ul class="list-group" >
					  	<li class="list-group-item">
			    			<a href="/problemresourcelist/${topic.key}">${topic.value[0]} problème${topic.value[0] > 1 ? "s" : ""} à corriger.</a>
					  	</li>
					  	<li class="list-group-item">
			    			<a href="/fieldsnullresourcelist/${topic.key}">${topic.value[1]} ressource${topic.value[1] > 1 ? "s" : ""} incomplète${topic.value[1] > 1 ? "s" : ""}.</a>
					  	</li>
					  	<li class="list-group-item">
			    			<a href="/notcompetencesresourcelist/${topic.key}">${topic.value[2]} ressource${topic.value[2] > 1 ? "s" : ""} liée${topic.value[2] > 1 ? "s" : ""} à aucune catégorie.</a>
					  	</li>
					  	<li class="list-group-item">
			    			<a href="/nochildrenvalidationresourcelist/${topic.key}">${topic.value[3]} ressource${topic.value[3] > 1 ? "s" : ""} en attente de validation.</a>
					  	</li>
                   </ul>					  	

			</div>
		</c:forEach>
	</div>
</body>
</html>