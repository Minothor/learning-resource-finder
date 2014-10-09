<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ page import="learningresourcefinder.util.NotificationUtil.Status" %>

<script type="text/javascript" src="/js/int/ajaxLogin.js"></script>

<script>
  $(document).ready(function() {
		<c:if test="${!empty sessionScope.notifications}">
		   showNotificationArea();
		</c:if>
		$().UItoTop({ easingType: 'easeOutQuart' });
	});
	
 	function showNotificationArea() {	
		$("#notificationArea").slideDown(); // Was hidden at the beginning.
	}
	
 	// Call this method if you want to display a notification text from JavaScript code.
	function showNotificationText(message, type) {
		$("#notificationText").html(message);
		$("#notificationArea").attr("class", "alert alert-"+type); 
		showNotificationArea();
	}
	
	
	$(document).ready(function() {
	 	$("#registerLink").click(function(e) {
	 	    e.preventDefault();// prevent the default anchor functionality
	 	    $('#loginDropDown').dropdown('toggle');
	 	    $('#registerDropDown').dropdown('toggle');
	 	    $('#emailRegister').focus();
		});
	 	
	 	$(".addResourceLink").click(function(e) {
	 	    e.preventDefault();// prevent the default anchor functionality
	 	    e.stopPropagation();
	 	    <lrftag:loggedin yes='$("#addResourceModal1").modal("show");'
		 	    no='$("#loginDropDown").dropdown("toggle");'/>
		});
	 	
	});
	
	// Refreshes the ajax progress bar from the server
	function progressBarAjax(){
		$.ajax({
			type : "GET",
		    url : '/ajax/progressbar',
		    dataType: 'text',
		    success : function(data) {
		    	$('#progressbar').html(data);
		    }
		}); 
	}

</script>

<%-- ********** NOTIFICATIONS ************** --%>
	<div id="notificationArea" 
		 class="${sessionScope.notifications[0].status.name}"
		 style="text-align:center; display:none; margin-bottom:0px">
        <%-- close cross button (bootstrap) --%>            
		<button id="notificationCloser" type="button" class="close" data-dismiss="alert" aria-hidden="true" onclick="$('#notificationArea').slideUp();">&times;</button>
		<span id="notificationText">
			<c:forEach items="${sessionScope.notifications}" var="notif">
			           ${notif.text}<br />
			</c:forEach>
		</span>
		<% session.removeAttribute("notifications"); %>  <%-- once displayed, we take it out from the session. --%>
	</div>





<!-- Main Menu / Start ================================================== -->
<header>  <%-- Bootstrap style --%>
	<nav class="navbar navbar-default" role="navigation">

		<div class="container" style="position:relative;">

			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header" style="padding-right:10px">
				<button type="button" class="navbar-toggle" data-toggle="collapse"	data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div>
				   <a href="/"><img src="/images/ToujoursPlus-logo-header.png" style="margin-top:3px;"></a> &nbsp;&nbsp;&nbsp;
				   <span style="font-size:9px;" class="label label-info">beta</span>
				</div>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					
					<li class="dropdown">
					   <a href="#" class="dropdown-toggle"data-toggle="dropdown">Contribuer <b class="caret"></b></a>
					   <ul class="dropdown-menu">
							<li><a class="addResourceLink">Ajouter une ressource </a></li>
							<li><a href="<c:url value='/rights'/>" class="">Droits</a></li>
							<c:if test="${current.user != null}">
							   <li><a href="<c:url value='/ressourcelist/${current.user.userName}'/>" >Mes ressources</a></li>
							</c:if>   
						    <li><a href="<c:url value='/contribution'/>" >Améliorer des ressources</a></li>
					   </ul>
					</li>
					
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Catégories <b class="caret"></b></a> 
					    <ul class="dropdown-menu"> 

                          <li><a href="<c:url value='/cycle?id=300'/>">1<sup>ère</sup> - 2<sup>ème</sup> primaire</a></li>
                          <li><a href="<c:url value='/cycle?id=301'/>">3<sup>ème</sup> - 4<sup>ème</sup> primaire</a></li>
                          <li><a href="<c:url value='/cycle?id=302'/>">5<sup>ème</sup> - 6<sup>ème</sup> primaire</a></li>
                          <li role="presentation" class="divider"></li>
                          <li><a href="<c:url value='/cycle?id=303'/>">1<sup>ère</sup> - 2<sup>ème</sup> secondaire</a></li>
                          <li><a href="<c:url value='/cycle?id=304'/>">3<sup>ère</sup> - 6<sup>ème</sup> secondaire</a></li>

<%-- We hardcode links to have nicer names and sorting					    
						  <c:forEach items="${applicationScope.cache.cycles}" var="cycle">
                             <li><a href="<c:url value='/cycle?id=${cycle.id}'/>">${cycle.name}</a> </li>
                          </c:forEach>
 --%>
                           
                          <li role="presentation" class="divider"></li>
                          <lrf:conditionDisplay privilege="MANAGE_COMPETENCE">
                        	  <li><a href="<c:url value='/cyclelist'/>">Gestion des Cycles</a> </li> 
                          </lrf:conditionDisplay>
                          <li><a href="<c:url value='/competencetree'/>">Liste des catégories</a> </li>
					    </ul>
					 </li>
					 
					 <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Séquences <b class="caret"></b></a> 
					    <ul class="dropdown-menu"> 
                          <li><a href="/playlist/all">Toutes les séquences</a> </li>
                          <c:choose>
                          	<c:when test="${current.user!=null}">
                          	<li><a href="/playlist/user/${current.user.userName}">Mes séquences</a></li>
                    	  </c:when>	
                    	</c:choose>			
					    </ul>
					 </li>
					 
					 <lrf:conditionDisplay role="ADMIN">
					 <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin <b class="caret"></b></a>
					   <ul class="dropdown-menu">	
					 	  <li><a href="/admin">Admin</a></li>	
					 	  <li><a href="/urlGeneric">URLs géneriques</a></li>
					   </ul>
					 </li>	
	 				 </lrf:conditionDisplay>					
				</ul>


	            <%-- Login / Register / Profile display --%>
				<c:choose>
					<c:when test="${current.user!=null}">
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">${current.user.userName} <b
									class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="/history/problem">Historique des problèmes</a></li>
									<li><a href="/favoris/${current.user.userName}">Favoris</a></li>
									<li><a href="/user/${current.user.userName}">Profil</a></li>
									<li><a href="/playlist/user/${current.user.userName}">Séquences</a></li>
        						   <li><a href="<c:url value='/ressourcelist/${current.user.userName}'/>" >Ressources</a></li>
								</ul></li>
							<li><a href="/logout" class="dropdown-toggle">Déconnexion</a>
							</li>
						</ul>
					</c:when>

					<c:otherwise>
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a id="loginDropDown" href="#" class="dropdown-toggle"data-toggle="dropdown">Connexion <b class="caret"></b></a>
							  <div class="dropdown-menu"style="padding: 15px; padding-bottom: 0px;">
							    <p><small>ToujoursPlus.be est libre d'accès. Cependant, pour contribuer ou enregistrer vos progrès, vous devez vous connecter.</small></p>
								<button data-icon="&#xe005;" type="button" class="btn btn-google btn-block connectionGoogle">
									Connexion avec Google
								</button>
								<button data-icon="&#xe000;" type="button" class="btn btn-facebook btn-block connectionFacebook">
									Connexion avec Facebook
								</button>
								<form action="javascript:ajaxlogin()" method="post"  style="width: 200px;">
									<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>
									<input class="form-control" style="margin-bottom: 15px;"type="text" onKeyPress="submitenter(this,event)" placeholder="Username" id="userNameOrMail"name="userNameOrMail"> 
									<input class="form-control"style="margin-bottom: 15px;" type="password" onKeyPress="submitenter(this,event)" placeholder="Password" id="password" name="password">
									<label class="string optional" for="remember_me"> 
									   <input style="float: left; margin-right: 10px;" type="checkbox"name="remember-me" id="remember-me" value="1"> Se souvenir de moi
									</label> 
									<input class="btn btn-primary btn-block" type="button" id="sign-in" value="Connexion" onclick="ajaxlogin()">
								    <div>
									 <label > Pas de compte ?</label>	
										<a href="#" id="registerLink">S'inscrire</a>
									</div>
								    <div>
									 Mot de passe <a href="/resendpassword">oublié</a> ?
									</div>									
								</form><br/>
								</div>
							 </li>
							<li class="dropdown"><a id="registerDropDown" href="#" class="dropdown-toggle" data-toggle="dropdown">Inscription <b class="caret"></b></a>
								<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
									<button data-icon="&#xe005;" type="button" class="btn btn-google btn-block connectionGoogle">
										Inscription avec Google
									</button>
									<button data-icon="&#xe000;" type="button" class="btn btn-facebook btn-block connectionFacebook">
										Inscription avec Facebook
									</button>
									<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>		
									<form method="post" action="#"  id="registerForm" style="width: 200px;" >		
										<input class="form-control" style="margin-bottom: 15px;" type="email" placeholder="Email" id="emailRegister" name="emailRegister" required>
										<input class="form-control" style="margin-bottom: 15px;" type="text" placeholder="Username" id="usernameRegister" name="usernameRegister" required> 
										<input class="form-control" style="margin-bottom: 15px;" type="password" placeholder="Password" id="passwordRegister" name="passwordRegister" required>
										<div style="width: 100%; text-align: center; margin: 15px 0;"><input type="checkbox" style="margin-bottom: 5px;" id="userTypeRegister" name="userTypeRegister" value="true"/> Je suis un enfant (accès limité aux ressources approuvées)  </div>
										<input class="btn btn-primary btn-block" type="submit" value="Inscription" >
									
										

									</form>
									<br />
								</div>
							</li>
						</ul>
					</c:otherwise>
				</c:choose>  <%-- Login / Register / Profile --%>
				
				<%-- Search --%>
				<form class="navbar-form navbar-right" role="search" method="get" action="/search" id="searchForm" >
				   <div class="form-group">
					<div class="input-group" style="width:150px;">
						<input name="searchphrase" id="search" style="min-width:150px; width:150px; max-width:220px;" type="text" class="form-control searchinput" placeholder="Recherche" required="required">
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit">Go!</button>
						</span>
					</div>
				  </div>
				</form>
			</div>
			<%@include  file="/WEB-INF/views/progressbar.jsp" %>  <%-- Cannot be dans WEB-INF/includes/ because we also need the SpringMVC dispatcher servlet to find it as a controller result. --%>
		</div> 
	</nav>

</header>

<%@include  file="/WEB-INF/includes/addresourceform.jsp" %>




