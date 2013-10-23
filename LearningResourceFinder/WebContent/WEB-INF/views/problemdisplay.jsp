<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>

<title>${problem.name}</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="${problem.name}">
		<lrftag:breadcrumbelement label="Home" link="/home" />
        <c:if test="${problem.createdBy eq user}">
		   <lrftag:breadcrumbelement label="Mes problèmes" link="/problem/history" />
		</c:if>
<%-- 		<lrftag:breadcrumbelement label="${problem.name}"  /> --%>
	</lrftag:breadcrumb>
	
	<div class="container">
		<lrftag:pageheadertitle title="${problem.name}"/>
		<div class="btn-group">
<%-- 			<c:if test="${canEdit}"> --%>
<%-- 			   <a class="btn btn-default" href=<c:url value='/playlist/edit?id=${playlist.id}'/>>Editer</a> --%>
<%-- 			</c:if> --%>
		</div>
		<br />
		<br />

		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-3">
						<lrftag:resource resource="${resource}"/>
				</div>
				<div class="col-md-9" style="position:relative;" >
					<dl class="dl-horizontal">
						<dt>Nom :</dt>
					  	<dd>${problem.name}</dd>
					  	<dt>Auteur :</dt>
					  	<dd><a href="/user/${problem.createdBy.userName}">${problem.createdBy.fullName}</a></dd>
					  	<dt>Description :</dt>
					  	<dd>${problem.description}</dd>
					  	<dt>Etat :</dt>
					  	<c:choose>
						  	<c:when test="${problem.resolved!=true}">
						  		<dd style="color:red;"><b>Ouvert</b>
						  			<c:if test="${problem.resolved!=true}">
										<a class="btn btn-primary btn-sm" href=<c:url value='/closeproblem?id=${problem.id}'/>>Clôturer</a> <!-- FIXME ajouter gestion des droits : i.e seuls les contributeurs peuvent clôturer -->
									</c:if>
						  		</dd>					  	
						  	</c:when>
						  	<c:otherwise>
						  		<dd style="color:green;"><b>Résolu</b></dd>	
						  	</c:otherwise>
					  	</c:choose>
						<dt>&nbsp;</dt>
					</dl>
				</div>
			</div>
		</div>
		<!-- discussion thread -->	
		<c:forEach items="${problem.problemDiscussions}" var="discussion">
			<div class="panel panel-default">
					<div class="panel-heading"><a href="/user/${discussion.createdBy.userName}">${discussion.createdBy.fullName}</a> <span style="font-size:70%;position:relative;float:right;" >
					<c:out value="${discussionDate[discussion.id]}"></c:out></span></div>
					<div class="panel-body">
						<span>${discussion.message}</span>
					</div>
			</div>	
		</c:forEach>
			<div class="panel panel-info">
			  <div class="panel-heading">
			    <h3 class="panel-title">Message</h3>
			  </div>
			  <div class="panel-body">
			  	<div class="col-md-12">
						<form action="/addDiscussion" method="post"  id="anchorResponse">
<!-- 						  <input type="text" class="form-control" name="textDiscussion" placeholder="Votre message"> -->
							<textarea class="form-control" rows="5" name="textDiscussion" placeholder="Votre message" required></textarea>
						  <br />
						  <button type="submit" class="btn btn-primary">Répondre</button>
						  <input type="hidden" name="idProblem" value="${problem.id}" />
						</form>  
				</div>
			  </div>
			</div>
	</div>
	
</body>
</html>