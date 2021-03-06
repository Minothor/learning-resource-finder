<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<!DOCTYPE html>
<html lang="fr" itemscope itemtype="http://schema.org/WebPage" >
<head>
  <meta name="locale" itemprop="inLanguage" content="fr" /><%-- defines page language for searching --%>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />  <%-- force IE to doesn't use is compataibility mode--%>
  <meta name="viewport" content="width=device-width" />  <%--Define the base-width as the screen width --%>
  <meta name="keywords" itemprop="keywords" content="soutien scolaire gratuit, cours particuliers, cours à domicile, vidéos, exercices en ligne, aide aux devoirs, leçons interactives, éveil, vidéos" />
  
  <%@ include file="/WEB-INF/includes/import.jsp"%>
  
  <decorator:head />
  
  <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico" />  <%-- for IE --%>
  <link rel="icon" type="image/png" href="/favicon.png">  <%-- for Chrome, Safari and Firefox --%>

  <title itemprop="name"><decorator:title /></title>
</head>
<body>

  <%@ include file="/WEB-INF/includes/badbrowserwarning.jsp"%>
    
  <div id="wrap">

	<%@ include file="/WEB-INF/includes/header.jsp"%>

	<decorator:body />

  </div>

  <%@ include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>