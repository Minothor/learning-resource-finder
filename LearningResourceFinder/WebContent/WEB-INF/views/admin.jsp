<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<html>
<body>
<div class="container">

    <a href="/createindex" class="btn btn-primary btn-lg">re-créer index</a>
    <hr>
    <a href="/importfromvraisforum" class="btn btn-primary btn-lg">importer les compétences de http://findecycle.vraiforum.com/</a>
    <hr>
    <div>
    	<a href="/importMathLabset" class="btn btn-primary btn-lg">execute batch import Math Labset</a>
    	<a href="/importFrenchLabset" class="btn btn-primary btn-lg">execute batch import French Labset</a>
    </div>
    <hr>
    <div>    	
    	<a href="/crawler/brainPop" class="btn btn-primary btn-lg">execute Crawler brainpop.fr</a>
    	<a href="/crawler/championMath" class="btn btn-primary btn-lg">execute Crawler championmath.free.fr</a>
    	<a href="/crawler/classePrimaire" class="btn btn-primary btn-lg">execute Crawler classeprimaire.be</a>
    	<br><br>    	
    	<a href="/crawler/exoFiche" class="btn btn-primary btn-lg">execute Crawler exofiches.net</a>
    	<a href="/crawler/fondationlamap" class="btn btn-primary btn-lg">execute Crawler fondation-lamap.org</a>
    	<a href="/crawler/khanAcademy" class="btn btn-primary btn-lg">execute Crawler fr.khanacademy.org</a>   	
    	<br><br>
    	<a href="/crawler/crawlerLaRecre" class="btn btn-primary btn-lg">execute Crawler LaRecre</a>    	
    	<a href="/crawler/professeurPhiFix" class="btn btn-primary btn-lg">execute Crawler profeseurphifix</a>
    	<a href="/crawler/soutien67" class="btn btn-primary btn-lg">execute Crawler soutien67.free.fr</a>
    	<a href="/crawler/toutSavoir" class="btn btn-primary btn-lg">execute Crawler toutsavoir-hatier.com</a>
    </div>
</div>    
</body>
</html>