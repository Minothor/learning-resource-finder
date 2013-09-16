
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	$(document).ready(function() {
		$("#notificationArea").slideDown(); // Was hidden at the beginning.
	});
	
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
<c:if test="${!empty sessionScope.notifications}">
	<div id="notificationArea"
		style="padding:3px; <%-- padding in style to override header-area class padding  --%> 
                     display:none;  <%-- display none because will be shown through animation by the docuiment ready javascript --%>
                     ">
		<a id="notificationCloser"
			style="float: right; padding: 0px 0px 0px 10px; cursor: pointer;"
			onclick="$('#notificationArea').slideUp();">×</a>
		<c:forEach items="${sessionScope.notifications}" var="notif">
		           ${notif.text}<br />
		</c:forEach>
		<%
		    session.removeAttribute("notifications");
		%>
	</div>
</c:if>

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
					<li class="active"><a href="#">Lien 1</a></li>
					<li><a href="#">Lien 2</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Dropdown <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
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
							<li class="dropdown"><a id="loginDropDown" href="#" class="dropdown-toggle"
								data-toggle="dropdown">Connexion <b class="caret"></b></a>
								<div class="dropdown-menu"
									style="padding: 15px; padding-bottom: 0px;">
									<form action="/loginsubmit" method="post" action="login"
										accept-charset="UTF-8" style="width: 200px;">
										<button data-icon="&#xe005;" type="submit"
											class="btn btn-google btn-block" id="login-googleplus">
											Connexion avec Google</button>
										<button data-icon="&#xe000;" type="submit"
											class="btn btn-facebook btn-block" id="login-facebook">
											Connexion avec Facebook</button>
										<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>
										<input class="form-control" style="margin-bottom: 15px;"
											type="text" placeholder="Username" id="userNameOrMail"
											name="userNameOrMail"> <input class="form-control"
											style="margin-bottom: 15px;" type="password"
											placeholder="Password" id="password" name="password">
										<label class="string optional" for="remember_me"> <input
											style="float: left; margin-right: 10px;" type="checkbox"
											name="remember-me" id="remember-me" value="1"> Se
											souvenir de moi
										</label> <input class="btn btn-bleu btn-block" type="button"
											id="sign-in" value="Connexion" onclick="ajaxlogin()">
											<div>
										<label > Pas de compte ?</label>	
										<a href="#" id="registerLink">S'inscrire</a>
										</div>
									</form>
									<br />
								</div></li>
							<li class="dropdown"><a id="registerDropDown" href="#" class="dropdown-toggle"
								data-toggle="dropdown">Inscription <b class="caret"></b></a>
								<div class="dropdown-menu"
									style="padding: 15px; padding-bottom: 0px;">
									<form method="post" action="login" accept-charset="UTF-8"
										style="width: 200px;">
										<button data-icon="&#xe005;" type="submit"
											class="btn btn-google btn-block" id="sign-in-google">
											Inscription avec Google</button>
										<button data-icon="&#xe000;" type="submit"
											class="btn btn-facebook btn-block" id="sign-in-facebook">
											Inscription avec Facebook</button>
										<div style="width: 100%; text-align: center; margin: 15px 0;">OU</div>
										<input class="form-control"
											style="margin-bottom: 15px;" type="email"
											placeholder="Email" id="email" name="email">
										<input class="form-control" style="margin-bottom: 15px;"
											type="text" placeholder="Username" id="usernameRegister"
											name="username"> <input class="form-control"
											style="margin-bottom: 15px;" type="password"
											placeholder="Password" id="password2" name="password">
										<input
											class="btn btn-bleu btn-block" type="submit" id="sign-in"
											value="Inscription">
									</form>
									<br />
								</div></li>
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









