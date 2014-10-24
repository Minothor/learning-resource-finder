<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
 <%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%> 



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
	
	
	<lrftag:pageheadertitle title="Comment pouvez-vous améliorer Toujours Plus?"/>
	
	<div class="col-xs-12 col-md-7">
	
	<br/>
	<h4> Contributions et niveaux</h4> 
	<p>En tant qu'utilisateur/contributeur de Toujours plus, vous avez: <br/>
		- un niveau  (entre 1 et 5) <br/>
		- et pour chaque niveau, des points de contribution, gagnés grâce à vos actions. <br/><br/>

Plus votre niveau est élevé, plus vous avez de possibilités d'action. Vous trouverez ci-dessous le détail de vos droits actuels (en vert, et uniquement si vous êtes connectés!), ainsi que la liste de toutes les contributions possibles et des points qu'elles vous font gagner.</p> <br/>
    
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


    <c:if test="${user != null}">
      Points de contribution pour ${user.fullName}: <strong>${user.userProgressPoints}</strong> (il en faut ${user.accountLevel.levelProgressPoints} pour arriver au niveau ${user.accountLevel.levelIndex + 1}). <br/>
	  <div class="progress" style="background-color: rgb(213, 213, 213)" >
			<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${100 * user.userProgressPoints / user.accountLevel.levelProgressPoints}%;">
				<span class="sr-only"> ${100 * user.userProgressPoints / user.accountLevel.levelProgressPoints}%</span> <br/>
			</div>
	  </div>
    </c:if>

	<p>Pour <strong>vérifier votre score actuel</strong>, voyez également <strong> votre barre de progression personnelle</strong>, visible tout en haut de chaque page de Toujours Plus (connexion requise). </p> <br/>
	
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
	
	<div class="col-xs-6 col-md-5">
	   <c:if test="${user != null}">
	         <jsp:include page="contributiondisplay.jsp"></jsp:include>
	   </c:if> 
	</div>
</div>
</body>
</html>