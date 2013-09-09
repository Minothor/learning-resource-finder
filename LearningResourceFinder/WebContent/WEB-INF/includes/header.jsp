
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(document).ready(function(){
	$("#notificationArea").slideDown();  // Was hidden at the beginning.
});
</script>


<%-- ********** NOTIFICATIONS ************** --%>
<c:if test="${!empty sessionScope.notifications}">
 		    <div id="notificationArea"  style="padding:3px; <%-- padding in style to override header-area class padding  --%> 
                     display:none;  <%-- display none because will be shown through animation by the docuiment ready javascript --%>
                     "> 
		        <a id="notificationCloser" style="float:right; padding:0px 0px 0px 10px; cursor: pointer;" onclick="$('#notificationArea').slideUp();">×</a>
		        <c:forEach items="${sessionScope.notifications}"   var="notif">
		           ${notif.text}<br/>
   	            </c:forEach>
   	            <% session.removeAttribute("notifications"); %>
		   </div>
</c:if>

<!-- Main Menu / Start
================================================== -->
<header class="menu">
	
	<div class="container">
	
		<!-- Mobile Only Menu Button  -->
		<a href="#menu" class="menu-trigger"><i class="icon-reorder"></i></a>
		
		<!-- Logo  -->	
		<a class="logo" href="index.html"><img src="http://iodos.eu/ftp/logo.png" alt="LOGO ICI" title="Logo"></a>
<!-- 		<div class="logodiv" > -->
<%-- 			${p_website_logo_tag} --%>
<!-- 		</div> -->

		<!-- Connection Zone -->
		<div style="float:right">
			<nav>
				<ul class="navigation">	
			<c:choose>
				<c:when test="${current.user!=null}">
					<li><a href="/user/${current.user.userName}">${current.user.userName}</a></li>
					<li><a href="logout">Déconnexion</a></li>	
				</c:when>
				
				<c:otherwise>
					<li><a href="login">LOG IN</a></li>
					<li><a href="register">SIGN UP </a></li>
				</c:otherwise>
			
			</c:choose>
				</ul>
			</nav>
		</div>
		
		<!-- Search -->
		<div style="float:right">
			<form class="form-recherche cf" action="search" method="get">      
	        	<input type="text" placeholder="Rechercher" required>         
	          	<button type="submit"></button>    
	        </form>
        </div>

		<!-- Navigation -->		
		<nav>
			<ul class="navigation">
				<li class="search"><input class="searchform" type="search" value="" placeholder="Search"></li>
				<li class="current"><a href="/">Home</a></li>
				<li><a href="#" class="arrow">Point 1</a>
					<!-- Drop-Down / Start -->
					<ul>
						<li><a href="pricing-tables.html">Pricing Tables</a></li>
						<li><a href="elements.html">Elements</a></li>
						<li><a href="typography.html">Typography</a></li>
						<li><a href="columns.html">Columns</a></li>
						<li><a href="icons.html">Icons</a></li>
					</ul>
					<!-- Drop-Down / End -->
				</li>

				<li><a href="#" class="arrow">Point 2</a>
					<!-- Drop-Down / Start -->
					<ul>
						<li><a href="about.html">About</a></li>
						<li><a href="faq.html">FAQ</a></li>
						<li><a href="404.html">404 Error</a></li>
					</ul>
					<!-- Drop-Down / End -->
				</li>
				
				<li><a href="#" class="arrow">Point 3</a>
					<!-- Drop-Down / Start -->
					<ul>
						<li><a href="portfolio-three-columns.html">3 columns</a></li>
						<li><a href="portfolio-four-columns.html">4 columns</a></li>
						<li><a href="#" class="arrow">Single Portfolio Item</a>
							<ul>
								<li><a href="project-vena-style-1.html">Style 1</a></li>
								<li><a href="project-vena-style-2.html">Style 2</a></li>
							</ul>
						</li>
					</ul>
					<!-- Drop-Down / End -->
				</li>		
				
			</ul>

		</nav>
		
	</div><!-- End of container -->
	
</header>



	
			
		
					


 