<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@page import="learningresourcefinder.model.User" %>
<html>

<body>

<lrftag:breadcrumb>
	<lrftag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	<lrftag:breadcrumbelement label="Permission" />
</lrftag:breadcrumb>
<lrftag:pageheadertitle title="Privileges de ${user.userName }"/>

<h2>Roles :</h2><br>

	<form action="/user/roleeditsubmit" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<c:forEach var="role" items="<%=User.Role.values()%>">
			<input type="radio" name="role" value="${role}" <c:if test="${user.role == role}">checked="checked"</c:if>>${role.name}<br>
		</c:forEach>
		<input type="submit" value="Changer rôle"><br />
	</form>
	<br/>
	<br/>
	<br/>
	<form action="/user/privilegeeditsubmit" method="post">
		<input type="hidden" name ="id" value="${user.id}">
		<table border="1">
		<c:forEach items="${privilegetriplets}" var="triplets">
		<tr>
			<td><c:if test="${user.role.isLowerOrEquivalent(triplets.getRole()) && user.role != triplets.role}">
				<input type="checkbox" name="${triplets.privilege.name()}" value="${triplets.privilege.name}"<c:if test="${triplets.permitted}">checked="checked"</c:if>>
			</c:if></td>
			<td><c:if test="${user.role.isHigherOrEquivalent(triplets.getRole())}">Dérivé du rôle</c:if></td>
			<td>${triplets.privilege.name}<br></td>
		</tr>
		</c:forEach>
		</table><br/>
		<input type="submit" value="Enregistrer"><a href="/user/${user.userName}">  Annuler</a>
	</form>

</body>
</html>