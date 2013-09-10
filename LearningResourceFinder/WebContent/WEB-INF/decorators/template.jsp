<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="learningresourcefinder.web.UrlUtil" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='ryc'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<!DOCTYPE html>
<html lang="fr">
<head>
	<base href="<%= UrlUtil.getAbsoluteUrl("") %>"/>
	<meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" /><%-- force IE to doesn't use is compataibility mode--%>
	<meta name="viewport" content="width=device-width"/><%--Define the base-width as the screen width --%>	
	<%@ include file="/WEB-INF/includes/import.jsp"%>
	<decorator:head />
	<link rel="shortcut icon" href="#"/>
	<title><decorator:title /></title>
</head>
<body>
<!-- 	<div id="wrap"> -->
	
		<%@ include file="/WEB-INF/includes/header.jsp"%>
	
		<decorator:body />
	
		<%@ include file="/WEB-INF/includes/footer.jsp"%>

  	<!--  </div>-->
</body>
</html>