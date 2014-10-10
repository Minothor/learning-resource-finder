<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"    prefix="fn" %>
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
	<section>
	<h2>Ressources:</h2>

	<c:choose>
		<c:when test="${numberResource==0}">
		     Aucune ressource ne correspond à ce critère de recherche.
		</c:when>
		<c:otherwise>
			<c:forEach items="${resourceList}" var="resource">
					<lrftag:resource resource="${resource}"/>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${numberResource>5}">
		   <c:set var="moreResourceText" value="voir les ${numberResource-5} autres ressources"/>
		</c:when>
		<c:otherwise>
		   <c:set var="moreResourceText" value="Recherche avancée"/>
		</c:otherwise>
	</c:choose>
	<div>
		<a href="/searchresource?searchphrase=${searchPhrase}">${moreResourceText}</a>
	</div>
	</section>
							<!-- Resource end -->




							<!-- Playlist start -->
    <section>	
	<h2>Séquences:</h2>
							
	<c:choose>
		<c:when test="${fn:length(playlistList)==0}">
		     Aucune séquence ne correspond à ce critère de recherche.
		</c:when>
		<c:otherwise>
			<c:forEach items="${playlistList}" var="playlist">
					<lrftag:playlist playlist="${playlist}"></lrftag:playlist>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	</section>
							<!-- Playlist end -->
							
							
							<!-- catégorie start -->
    <section>
	<h2>Catégories:</h2>

	<c:choose>
		<c:when test="${fn:length(competenceList)==0}">
		     Aucune catégorie ne correspond à ce critère de recherche.
		</c:when>
		<c:otherwise>
			<c:forEach items="${competenceList}" var="competence">
				<div style="float: left; position: relative; padding-left: 5px; margin-top: 10px;">
					<a href="/searchresource?searchphrase=${searchPhrase}&competenceid=${competence.getId()}"><lrf:competencepath competence="${competence}"/></a>	
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
							<!-- catégorie end -->
    </section>
</div>
</body>
</html>