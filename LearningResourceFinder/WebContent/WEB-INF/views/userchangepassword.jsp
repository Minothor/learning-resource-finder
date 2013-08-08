<%@ page import="learningresourcefinder.security.SecurityContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="learningresourcefinder.security.Privilege"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>


<html>
<body>
<!-- <h1>Formulaire de changement de password</h1> -->

<lrf:breadcrumb>
	<lrf:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	<lrf:breadcrumbelement label="changement de mot de passe" />
</lrf:breadcrumb>
<lrf:pageheadertitle title="changement de mot de passe"/>


	<lrf:form modelAttribute="passwordData" action="/user/changepasswordsubmit">
			<c:if test="${verifyOldPassword}">
		 		 <lrf:input label="Mot de passe actuel" path="oldPassword" type="password" />
		    </c:if>
		    <lrf:input label="Nouveau mot de passe" path="newPassword" type="password" />
		    <lrf:input label="Confirmer le nouveau  mot de passe" path="confirmPassword" type="password" />
		<input type="hidden" name="id" value="${user.id}" />
		<tr><td><input type="submit" value="changer"/></td><td><a href="/user/${user.userName}">Annuler</a></td></tr>
	</lrf:form>
</body>
</html>