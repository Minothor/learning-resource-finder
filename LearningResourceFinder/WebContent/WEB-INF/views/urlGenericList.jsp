<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@include  file="/WEB-INF/includes/addurlgenericform.jsp" %>
<script type="text/javascript" src="/js/int/urlGeneric.js"></script>

<div class="container">

	<lrftag:pageheadertitle title="Liste des urls génériques"/>

   	<p>Une url générique est une url qu'on ne désire pas référencer car elle pointe vers un site avec plusieurs ressources, plutôt que sur une ressource précise de ce site.</p>
   	         
   	<a class="btn btn-primary pull-right addUrlGenericLink" id="addUrlButton">Ajouter une url générique</a>
			
	<br/><br/><br/>
	<div class="table-responsive">
	  	<table class="table table-bordered">
			<tr>
			    <td><strong>URL</strong></td>
				<td><strong>Auteur</strong></td>
				<td><strong>Action</strong></td>
			</tr>
			<c:forEach items="${urlList}" var="urlGeneric">
				<tr>
					<td>${urlGeneric.url}</td>
					<td>${urlGeneric.createdBy.fullName}</td>
					<td><a class="deleteUrlLink" href="/deleteUrlGeneric?id=${urlGeneric.id}">supprimer</a></td>
			   </tr>
			</c:forEach>
		</table>
	</div>
      
</div>    
