<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="learningresourcefinder.web.UrlUtil" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<!DOCTYPE html>
<html lang="fr">
<head>
	<!-- <base href="<%= UrlUtil.getAbsoluteUrl("") %>"/> -->
	<meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /><%-- force IE to doesn't use is compataibility mode--%>
	<meta name="viewport" content="width=device-width"/><%--Define the base-width as the screen width --%>	
	<%@ include file="/WEB-INF/includes/import.jsp"%>
	<decorator:head />
	<link rel="shortcut icon" href="#"/>
	<title><decorator:title /></title>
	<!-- Script Connection With Facebook -->
	<script type="text/javascript">
		function mPopupLogin(provid) {			
			var w = 780;
			var h = 410;
			var left = (screen.width/2)-(w/2);
		    var   top = (screen.height/2)-(h/2);
		    var urlLogin = "<%= UrlUtil.getAbsoluteUrl("/loginsocial?provider=") %>" + provid;
		    var signin= window.open(urlLogin, "Login", "nom_popup,menubar=no, status=no, scrollbars=no, menubar=no, width="+w+", height="+h+",left=" + left + ",top=" + top);
			return false; 			
		};
				
		$(document).ready(function(event) {		
			$(".connectionGoogle").click(function() {
				mPopupLogin("googleplus");
			});
			$(".connectionFacebook").click(function() {
				mPopupLogin("facebook");
			});	
		});
	</script>
</head>
<body>
	<div id="wrap">
	
		<%@ include file="/WEB-INF/includes/header.jsp"%>
	
		<decorator:body />
		
	</div>
	
	<%@ include file="/WEB-INF/includes/footer.jsp"%>

  	 
</body>
</html>