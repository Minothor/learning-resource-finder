<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
</head>
<body>
<div class="container">
	<lrftag:breadcrumb linkactive="droits et niveaux">
		<lrftag:breadcrumbelement label="home" link="/home" />
        <c:if test="${user != null}">
		   <lrftag:breadcrumbelement label="${user.fullName}" link="/user/${user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	
	<lrftag:pageheadertitle title="Droits et niveaux"/>
	
	<p>Les actions listées ci-dessous nécessitent des droits. Plus votre niveau d'utilisateur est élevé, plus vous avez de droits.</p>
    
    <c:if test="${user != null}">
      <p>Niveau de ${user.fullName}: <strong>${user.accountLevel.levelIndex}</strong></p>
    </c:if>

	<c:forEach items="${actions}" var="action">
		<div class="row">
					<c:if test="${user != null && user.accountLevel.levelIndex >= action.level.levelIndex}">
					    <c:set var="greenClass" value="text-success"/>
					    <c:set var="strongStart" value="<strong>"/>			    
					    <c:set var="strongEnd" value="</strong>"/>			    
					</c:if>
						
					${strongStart}	
					  <div class="col-xs-2 ${greenClass} text-right">niveau ${action.level.levelIndex}</div>
					  <div class="col-xs-10 ${greenClass}">${action.describe}</div>
					${strongEnd}
                    <c:set var="greenClass" value=""/>
				    <c:set var="strongStart" value=""/>			    
				    <c:set var="strongEnd" value=""/>			    
		</div>
	</c:forEach>
	
	<br/>
	<br/>
	<p>Votre niveau d'utilisateur augmente automatiquement: votre barre de progression est visible tout en haut de chaque page (connexion requise!).</p>

    <c:if test="${user != null}">
      Points de contribution pour ${user.fullName}: <strong>${user.userProgressPoints}</strong> (il en faut ${user.accountLevel.levelProgressPoints} pour arriver au niveau ${user.accountLevel.levelIndex + 1}).
	  <div class="progress" >
			<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${100 * user.userProgressPoints / user.accountLevel.levelProgressPoints}%">
				<span class="sr-only"> ${100 * user.userProgressPoints / user.accountLevel.levelProgressPoints}%</span>
			</div>
	  </div>
    </c:if>

	<p>Cette barre se remplit avec des points de contribution que vous gagnez grâce aux actions suivantes:</p>
	
	<c:forEach items="${actions}" var="action">
	  <c:if test="${action.actionPoints > 0}"> <%-- it's not worth displaying actions that get the user no point here --%>
		<div class="row">
					<div class="col-xs-2 ${style} text-right">${action.actionPoints} point${action.actionPoints >1 ? "s" : "" }</div>
					<div class="col-xs-10 ${style}">${action.describe}</div>
                    <c:set var="style" value=""/>
		</div>
	  </c:if>
	</c:forEach>
	
	
</div>
</body>
</html>