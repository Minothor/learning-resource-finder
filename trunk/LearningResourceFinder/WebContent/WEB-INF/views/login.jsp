﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<head>

<style type="text/css">
.simple-button.provider-signup.facebook {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #185996;
    background-image: linear-gradient(to bottom, #1A60A2, #154E83);
    background-repeat: repeat-x;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    border-image: none;
    border-style: solid;
    border-width: 1px;
    color: #FFFFFF !important;
    text-shadow: none;
}
.simple-button.provider-signup {
    display: block;
    font-family: "MuseoSans300",sans-serif;
    height: 40px;
}
</style>

</head>

<body>

<lrftag:pageheadertitle title="Connexion"/> 

Pour participer (voter, argumenter, etc.), vous devez vous connecter avec votre utilisateur.

<div style="padding-top:20px;">    
	<!-- SOCIAL SIGNIN - RIGHT COLUMN -->
	<div style="border-left-style: solid; border-width: 1px; float:right; padding-left:20px; width:300px;">

		<h1>Connexion via un réseau social</h1>
		<p>Vous pouvez facilement utiliser votre compte facebook, google ou autre pour vous connecter à ${p_website_name}</p>
		<!-- FACEBOOK SIGNIN -->
	
		<div>

			<a id="login-facebook" class="simple-button provider-signup facebook" href="/loginsocialPage?providerid=facebook">
				<img class="provider-signup-img" width="24" height="24" src="https://khan-academy.appspot.com/images/facebook-24px.png">
				<span class="provider-separator"></span>
				<span class="provider-signup-text"> Sign in with Facebook </span>
			</a>
		</div>
		<br/>
		
		
		

		<!-- TWITTER SIGNIN -->
		
		<div>
		<form id="tw_signin" action="<c:url value="/signin/twitter"/>"
			method="POST">
			<div class="container">se connecter avec<br> 
				<input style="padding-top:5px;" type="image" alt="logo twitter" src="images/social_logo/twitter.jpg" />
			</div>
		</form>
		</div>
		<br/>
		
		<!-- LINKEDIN SIGNIN
		<div>
		<form name="li_signin" id="li_signin"
			action="<c:url value="/signin/linkedin"/>" method="POST">
			<input type ="hidden" name ="scope" value="r_basicprofile+r_emailaddress"/>
				<div class="container">se connecter avec<br>
			     	<input style="padding-top:5px;" type="image" alt="logo linkedin" src="images/social_logo/linkedin.jpg" />
				</div>
		</form>
		</div>
		<br/> -->

		<!-- GOOGLE SIGNIN -->
		<div>
		<form name="go_signin" id="go_signin"
			action="<c:url value="/signin/google"/>" method="POST">
			<input type ="hidden" name ="scope" value="https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email"/>
			<div class="container">se connecter avec<br /> 
				<input class="image-login" type="image" alt="logo google"
				src="images/social_logo/google.jpg" />
			</div>
		</form>
		</div>
	</div>
	
	<!-- LOCAL SIGNIN - LEFT COLUMN -->
	<div style="padding-right: 20px;">
			<h1>Connexion avec votre compte ${p_website_name}</h1>
		<p>Si vous n'avez pas de compte facebook, google ou autre <br>(ou ne désirez simplement pas les utiliser ici), <br>vous pouvez vous connecter avec un pseudonyme <br>${p_website_name} (qui nécessite que vous <a href="<c:url value="register"/>">créiez un utilisateur</a> <br>au préalable)</p> 
		<%@ include file="loginfragment.jsp"%>
	</div>
	
</div>	
<!-- this checkbox is not in a form tag because we pass its value by an ajax request -->

</body>