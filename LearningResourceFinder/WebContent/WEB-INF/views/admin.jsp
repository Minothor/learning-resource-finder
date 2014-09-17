<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<html>
<body>
<div class="container">
    <a href="/createindex" class="btn btn-primary btn-lg">re-créer index</a>
    <a href="/importfromvraisforum" class="btn btn-primary btn-lg">importer les compétences de http://findecycle.vraiforum.com/</a>
    <div>
    	<a href="/importMathLabset" class="btn btn-primary btn-lg">execute batch import Math Labset</a>
    	<a href="/importFrenchLabset" class="btn btn-primary btn-lg">execute batch import French Labset</a>
    </div>
    <div>    	
    	<a href="/crawler/championMath" class="btn btn-primary btn-lg">execute Crawler championmath.free.fr</a>
    	<a href="/crawler/classePrimaire" class="btn btn-primary btn-lg">execute Crawler classeprimaire.be</a>
    	<a href="/crawler/profeseurphifix" class="btn btn-primary btn-lg">execute batch Crawler profeseurphifix</a>
    	<a href="/crawler/crawlerLaRecre" class="btn btn-primary btn-lg">execute batch Crawler LaRecre</a>
    </div>
</div>    
</body>
</html>