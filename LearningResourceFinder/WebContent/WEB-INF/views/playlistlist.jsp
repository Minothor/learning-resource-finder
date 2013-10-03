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
   });

</script>

</head>
<body>

 <c:if test="${user!=null}">
	<lrftag:breadcrumb linkactive="playLists">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<lrftag:breadcrumbelement label="${user.firstName}" link="/user/${user.userName}" />
	</lrftag:breadcrumb>
</c:if>

	
<%-- 	${current.user.fullName} --%>
	<div class="container">
		 <c:choose>
    		<c:when test="${user!=null}">
				<lrftag:pageheadertitle title="Play-lists de ${user.fullName}"/>
			</c:when>
			<c:otherwise>
				<lrftag:pageheadertitle title="Toutes les PlayLists"/>
			</c:otherwise>
    	</c:choose>
		<div class=" " style="font-size:14px">
		   &nbsp&nbsp<a class="btn btn-primary pull-right" 
		      <lrftag:loggedin yes='href="/playlist/create"' no='id="addPlayListLink" href="#"'/>
		   > Créer une PlayList</a> 
		</div>
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
