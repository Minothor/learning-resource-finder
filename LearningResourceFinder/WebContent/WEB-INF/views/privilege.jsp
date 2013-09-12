<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@page import="learningresourcefinder.model.User" %>
<html>

<body>
	<lrftag:breadcrumb linkactive="Permission">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="user/${user.userName}" />
	</lrftag:breadcrumb>	
	<div class="container">
		<lrftag:pageheadertitle title="Privileges de ${user.fullName }"/>
		<br />
		<form action="/user/roleeditsubmit" method="post">
			<input type="hidden" name="id" value="${user.id}">
			<div class="form-group col-lg-12">
		   		<form:label path ="role" class="col-lg-1 control-label">Rôles :</form:label>
		    	<ul class="col-lg-11">
		    	<c:forEach var="role" items="<%=User.Role.values()%>">
					<li>
						<label class="font-ligther">
							<input type="radio" name="role" value="${role}" <c:if test="${user.role == role}">checked="checked"</c:if>> ${role.name}
						</label>
					</li>
				</c:forEach>
		    	</ul>	
		    	<input type="submit" class="btn btn-primary " value="Changer rôle">    			
		    </div>
		</form>
		<br />
		<br />
		<br />
		<form action="/user/privilegeeditsubmit" method="post">
			<input type="hidden" name ="id" value="${user.id}">
			
			<div class="table-responsive col-lg-12">
				<table class="table table-bordered">
				<c:forEach items="${privilegetriplets}" var="triplets">
				<tr>
					<c:if test="${user.role.isLowerOrEquivalent(triplets.getRole()) && user.role != triplets.role}">
					<td>
						<input type="checkbox" name="${triplets.privilege.name()}" value="${triplets.privilege.name}"<c:if test="${triplets.permitted}">checked="checked"</c:if>>
					</td>
					</c:if>
					<td><c:if test="${user.role.isHigherOrEquivalent(triplets.getRole())}">Dérivé du rôle</c:if></td>
					<td>${triplets.privilege.name}<br></td>
				</tr>
				</c:forEach>
			  	</table>
			</div>
			
			<div class="form-group">
		   		<ul class="col-lg-4" >
		    		<li style="display:inline;"><input type="submit" class="btn btn-primary" value="Enregistrer"></li>		    	
		    		<li style="display:inline;"><button onclick="location.href='/user/${user.userName}';" class="btn" type="reset">Cancel</button></li>
		    	</ul>
		    </div>
		</form>
	</div>
</body>
</html>