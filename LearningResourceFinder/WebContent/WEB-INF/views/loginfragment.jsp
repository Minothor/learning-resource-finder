<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ page import="learningresourcefinder.web.ContextUtil"%>


<!-- This fragment will be displayed in a jQuery dialog box. -->
<label id="errorMsg" style="color: red;"></label>

<form action="/loginsubmit" method="post">
	<label for="userNameOrMail">pseudo / adresse e-mail</label><br /> <input
		type="text" name="userNameOrMail" required="required"
		value="${userNameOrMail}" /><br /> <label for="password">mot
		de passe</label><br />
	<c:choose>
		<c:when test="<%=!ContextUtil.devMode%>">
			<input type="password" name="password" required="required" />
		</c:when>
		<c:otherwise>
			<input type="password" name="password" value=""	required="required" />
			<br />
			    Your are in dev mode: password is not verified (type any).<br />
		</c:otherwise>
	</c:choose>


    <input type="checkbox" name="autoLogin"  <c:if test='${autoLogin}'>checked="checked"</c:if> />
	
	<label for="keepLoggedIn"><span
		title="Si vous cochez cette case, lors de votre prochaine visite vous serez connectés automatiquement">Je
			souhaite rester connecté</span> <br />


	</label> J'ai <a href="/resendpassword">oublié mon mot de passe</a> <br /> 
	<input id="ryc" class="image-login" type="submit" value="me connecter" />
</form>
