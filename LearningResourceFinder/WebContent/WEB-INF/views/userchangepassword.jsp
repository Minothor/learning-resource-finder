<%@ page import="learningresourcefinder.security.SecurityContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="learningresourcefinder.security.Privilege"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>


<html>
<body>
	<lrftag:breadcrumb linkactive="changement de mot de passe">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<lrftag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrf:pageheadertitle title="Changement de mot de passe"/>
		<br />
		<form:form modelAttribute="passwordData" action="/user/changepasswordsubmit" class="form-horizontal">
			<c:if test="${verifyOldPassword}">
				<lrftag:input label="Mot de passe actuel" path="oldPassword" type="password" />
			</c:if>
			<lrftag:input label="Nouveau mot de passe" path="newPassword" type="password" />
			<lrftag:input label="Confirmer le nouveau  mot de passe" path="confirmPassword" type="password" />
			<input type="hidden" name="id" value="${user.id}" />
			
			<div class="form-group">
		   		<label class="col-lg-2"></label>
		   		<ul class="col-lg-2" >
		    		<li style="display:inline;"><input type="submit" value="changer" class="btn btn-primary"/></li>
		    		<li style="display:inline;"><button onclick="location.href='/user/${user.userName}';" class="btn" type="reset">Annuler</button></li>
		    	</ul>
		    </div>
		</form:form>
	</div>
</body>
</html>