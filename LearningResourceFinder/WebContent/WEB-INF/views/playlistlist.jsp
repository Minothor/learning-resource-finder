<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>

<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<lrf:breadcrumb linkactive="Mes PlayLists">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrf:breadcrumbelement label="${current.user.firstName}" link="/user/${current.user.userName}" />
	</lrf:breadcrumb>
	
	<div class="container">
		<lrftag:pageheadertitle title="${current.user.firstName} ${user.lastName} : Mes PlayLists"/>
		<div class=" " style="font-size:14px">
		   &nbsp&nbsp<a href="<c:url value='/playlist/create'/>"> Créer une PlayList</a>
		</div>
	   	<table cellspacing="10">
			<tr>
			    <td>Titre</td>
				<td>Description</td>
				<td>Auteur</td>
			</tr>
			<c:forEach items="${playlistlist}" var="playlist">
				<tr>
					<td><a href="<c:url value='playlist/${playlist.shortId}/${playlist.slug}'/>">${playlist.name}</a></td>
					<td>${playlist.description}</td>
					<td>${playlist.createdBy.fullName}</td>
			   </tr>
			</c:forEach>		
		</table>
	</div>
</body>
</html>