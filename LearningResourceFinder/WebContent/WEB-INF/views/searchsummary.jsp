<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>

<html>
<head>
<title>Search</title>

<style type="text/css">
.ecart {
	margin: 0 80px 0px 100px;
	float: left;
}
</style>

</head>
<body>
<div class="container">

	<h1>Recherche: ${searchPhrase}</h1>
	
							<!-- Resource start -->
	<div class="ecart">
		<h4>Resource:</h4>
	</div>

	<c:forEach items="${resourceList}" var="resource">
		<div style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<lrftag:resource resource="${resource}"></lrftag:resource>
		</div>
	</c:forEach>

							<!-- Resource end -->
	
	<br />
	<br />
	<br />
	<br />
	<br />			
	<br />
	<br />		<!--  les <br/> sont là just pour testé il faudra les modifier avec du css -->
	<br />
	<br />
	<br />
	<br />
	<br />
	<c:choose>
	<c:when test="${numberResource>5}">
	<div>
	<a href="/searchresource?searchphrase=${searchPhrase}">${numberResource-5} more</a>
	</div>
	</c:when>
	<c:otherwise>
	<div>
	<a href="/searchresource?searchphrase=${searchPhrase}">Recherche avancée</a>
	</div>	
	</c:otherwise>
	</c:choose>
	<br />
	<br />
	<br />

							<!-- Playlist start -->
	<div class="ecart">
		<h4>Playlist:</h4>
	</div>							
							
	<c:forEach items="${playlistList}" var="playlist">
		<div style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<lrftag:playlist playlist="${playlist}"></lrftag:playlist>
		</div>
	</c:forEach>

							<!-- Playlist end -->
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />		<!--  les <br/> sont là just pour avoir une bonne position il faudra les modifier avec du css -->
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	
							<!-- compétence start -->
	<div class="ecart">
		<h4>Compétence:</h4>
	</div>							
							
	<c:forEach items="${competenceList}" var="competence">
		<div style="float: left; position: relative; padding-left: 5px; margin-top: 10px;">
			<a href="/searchresource?searchphrase=${searchPhrase}&competenceid=${competence.getId()}"><lrf:competencepath competence="${competence}"/></a>	
		</div>
	</c:forEach>

							<!-- compétence end -->
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />		<!--  les <br/> sont là just pour avoir une bonne position il faudra les modifier avec du css -->
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />

</div>
</body>
</html>