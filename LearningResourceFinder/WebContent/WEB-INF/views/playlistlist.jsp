<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
   <h1>PlayListList</h1>
   <table cellspacing="10">
		<tr>
		    <td>Name</td>
			<td>Username</td>
			<td>Description</td>
			<td>Action</td>
		</tr>
		<c:forEach items="${playlistlist}" var="playlist">
			<tr>
				<td>${playlist.name}</td>
				<td>${playlist.description}</td>
		   </tr>
		</c:forEach>
		
				
</table>
</body>
</html>