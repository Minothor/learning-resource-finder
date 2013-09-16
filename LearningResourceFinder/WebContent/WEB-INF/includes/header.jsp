
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="/js/int/resourceEdit.js"></script>


<script>
	$(document).ready(function() {
		<c:if test="${!empty sessionScope.notifications}">
		   showNotificationArea();
		</c:if>
		
	});
	
 	function showNotificationArea() {	
		$("#notificationArea").slideDown(); // Was hidden at the beginning.
	}
	
	function showNotificationText(message) {
		$("#notificationText").html(message);
		showNotificationArea();
	}
	
	
	$(document).ready(function() {
	 	$("#registerLink").click(function(e) {
// 	 	    	
	 	    e.preventDefault();// prevent the default anchor functionality
	 	    // Hide dropdown connect
	 	    $('#loginDropDown').dropdown('toggle');
	 	    $('#registerDropDown').dropdown('toggle');
	 	    $('#email').focus();
		});
	});
</script>


<%-- ********** NOTIFICATIONS ************** --%>
<%-- <c:if test="${!empty sessionScope.notifications}"> --%>
	<div id="notificationArea"
		style="padding:3px; <%-- padding in style to override header-area class padding  --%> 
                     display:none;  <%-- display none because will be shown through animation by the docuiment ready javascript --%>
                     ">
		<a id="notificationCloser"
			style="float: right; padding: 0px 0px 0px 10px; cursor: pointer;"
			onclick="$('#notificationArea').slideUp();">×</a>
		<span id="notificationText">
			<c:forEach items="${sessionScope.notifications}" var="notif">
			           ${notif.text}<br />
			</c:forEach>
		</span>
		<%
		    session.removeAttribute("notifications");
		%>
	</div>
<%-- </c:if> --%>

<!-- Main Menu / Start
================================================== -->
<header>

	<nav class="navbar navbar-default" role="navigation">

		<div class="container">

			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="http://toujoursplus.be/">ToujoursPlus</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="active">
					<!-- <a href="#">Lien 1</a></li> -->
					<li class="dropdown">
					   <a href="#" class="dropdown-toggle"data-toggle="dropdown">Contribuer <b class="caret"></b></a>
					   <ul class="dropdown-menu">
							<li><a data-toggle="modal" href="#addResourceModal">Créer une ressource</a></li>
					   </ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"data-toggle="dropdown">Cycles <b class="caret"></b></a>
					   <ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"data-toggle="dropdown">Dropdown <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul>
				    </li>
				</ul>

				<c:choose>
					<c:when test="${current.user!=null}">
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">${current.user.userName} <b
									class="caret"></b></a>
								<ul class="dropdown-menu">
									<li><a href="/user/${current.user.userName}">Profil</a></li>
									<li><a href="playlist/user/${current.user.userName}">Playlist</a></li>
								</ul></li>
							<li><a href="logout" class="dropdown-toggle">Déconnexion</a>
							</li>
						</ul>
					</c:when>

					<c:otherwise>
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a id="loginDropDown" href="#" class="dropdown-toggle" data-toggle="dropdown">Connexion <b class="caret"></b></a>
								<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
									<button data-icon="&#xe005;" type="button" class="btn btn-google btn-block connectionGoogle">
										Connexion avec Google
									</button>
									<button data-icon="&#xe000;" type="button" class="btn btn-facebook btn-block connectionFacebook">
										Connexion avec Facebook
									</button>
									<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>	
									<form action="/loginsubmit" method="post" action="login" accept-charset="UTF-8" style="width: 200px;">	
										<input class="form-control" style="margin-bottom: 15px;" type="text" placeholder="Username" id="userNameOrMail" name="userNameOrMail"> 
										<input class="form-control" style="margin-bottom: 15px;" type="password" placeholder="Password" id="password" name="password">
										<label class="string optional" for="remember_me"> 
											<input style="float: left; margin-right: 10px;" type="checkbox" name="remember-me" id="remember-me" value="1"> 
											Se souvenir de moi
										</label> 
										<input class="btn btn-bleu btn-block" type="button" id="sign-in" value="Connexion" onclick="ajaxlogin()">
										<div>
											<label > Pas de compte ?</label>	
											<a href="#" id="registerLink">S'inscrire</a>
										</div>
									</form>
									<br />
								</div></li>
							<li class="dropdown"><a id="registerDropDown" href="#" class="dropdown-toggle" data-toggle="dropdown">Inscription <b class="caret"></b></a>
								<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
									<button data-icon="&#xe005;" type="button" class="btn btn-google btn-block connectionGoogle">
										Inscription avec Google
									</button>
									<button data-icon="&#xe000;" type="button" class="btn btn-facebook btn-block connectionFacebook">
										Inscription avec Facebook
									</button>
									<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>		
									<form method="post" action="#" accept-charset="UTF-8" style="width: 200px;">		
										<input class="form-control" style="margin-bottom: 15px;" type="email" placeholder="Email" id="emailRegister" name="emailRegister">
										<input class="form-control" style="margin-bottom: 15px;" type="text" placeholder="Username" id="usernameRegister" name="usernameRegister"> 
										<input class="form-control" style="margin-bottom: 15px;" type="password" placeholder="Password" id="passwordRegister" name="passwordRegister">
										<input class="btn btn-bleu btn-block" type="button" id="sign-in" value="Inscription" onclick="ajaxRegister()">
									</form>
									<br />
								</div>
							</li>
						</ul>
					</c:otherwise>

				</c:choose>
				<form class="navbar-form navbar-right" role="search" method="post" action="search">
				   <div class="form-group">
					<div class="input-group" style="width:150px;">
						<input name="search" id="search" style="min-width:150px; width:150px" type="text" class="form-control" placeholder="Recherche" required>
						<span class="input-group-btn">
							<button class="btn btn-default" type="submit">Go!</button>
						</span>
					</div>
				  </div>
				</form>
				<!-- /.col-lg-6 -->
			</div>
		</div>
	</nav>

</header>



<!-- Modal for adding a resource (invisible until button clicked) -->
<div class="modal fade" id="addResourceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
		<form id="addResourceForm" role="form" method="post" action="resourceaddsubmit">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une ressource</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
	                        <label for="url">site</label> 
	                        <input type="text" class="form-control" id="url" name="url" placeholder="http://...">
	                        <div class="pull-right"> 
	                           <button type="button" class="btn btn-mini btn-primary" id="urlCheckButton" onclick="ajaxVerifyUrl()">Vérifier</button>
							</div>
							<span class="help-block">URL vers le site que vous désirez ajouter.</span>
							
						</div>

						<div id="addResourceFormPart2" style="display:none;">  <%-- will not be displayed until the url is valid --%>
							
						</div>
					</div>
                  </div>
				  <div class="modal-footer" style="display: none;" id="bottomButtons">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
					<button type="button" class="btn btn-primary" onclick="ajaxResourceAddSubmit()" >Ajouter</button>
				  </div>
			</div>
			<!-- /.modal-content -->
		</form>
	    </div>
		<!-- /.modal-dialog -->
</div>
<!-- /.modal --> 






