<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>

<html>
<head>
<title>Insert title here</title>

<script>

   $(document).ready(function() {
   	  $("#addPlayListLink").click(function(e) {
	    e.preventDefault();// prevent the default anchor functionality
	    e.stopPropagation();
	    $("#loginDropDown").dropdown("toggle");
   	  });
   	  
   	  
      $('.noaddplaylistpop').popover({
    	   	html :true,
    	   	content: "Pour créer une séquence, il faut être connecté et avoir un niveau 3 de contribution."
      });
   	  
   });
   

</script>

</head>
<body>
<c:choose>
 <c:when test="${(user!=null && displaytype!='all')}">
	<lrftag:breadcrumb linkactive="PlayLists">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<lrftag:breadcrumbelement label="${user.firstName}" link="/user/${user.userName}" />
	</lrftag:breadcrumb>
 </c:when>
 <c:otherwise>
 	<lrftag:breadcrumb linkactive="Toutes les séquences">
		<lrftag:breadcrumbelement label="home" link="/home" />
	</lrftag:breadcrumb>
 </c:otherwise>
</c:choose>
	
<%-- 	${current.user.fullName} --%>
	<div class="container">
		 <c:choose>
    		<c:when test="${displaytype!='all'}">
				<lrftag:pageheadertitle title="Séquences de ${user.fullName}"/>
			</c:when>
			<c:otherwise>
				<lrftag:pageheadertitle title="Toutes les séquences"/>
			</c:otherwise>
    	</c:choose>
   	
		<a class="btn btn-primary pull-right ${canaddplaylist == true ? '\" href=\"/playlist/create\"' : 'noaddplaylistpop' }">
			Créer une PlayList
		</a>
	
		<br />
		<div class="table-responsive">
		  	<table class="table table-bordered">
				<tr>
				    <td><strong>Titre</strong></td>
					<td><strong>Description</strong></td>
					<td><strong>Auteur</strong></td>
				</tr>
				<c:forEach items="${playlistlist}" var="playlist">
					<tr>
						<td><a href="<c:url value='/playlist/${playlist.shortId}/${playlist.slug}'/>">${playlist.name}</a></td>
						<td>${playlist.description}</td>
						<td>${playlist.createdBy.fullName}</td>
				   </tr>
				</c:forEach>
			</table>
		</div>
	</div>
	
	
</body>
</html>
