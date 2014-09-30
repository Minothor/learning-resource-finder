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
    	<br><br>
    	<a href="/crawler/crawlerLaRecre" class="btn btn-primary btn-lg">execute Crawler LaRecre</a>    	
    	<a href="/crawler/professeurPhiFix" class="btn btn-primary btn-lg">execute Crawler profeseurphifix</a>
    	<a href="/crawler/soutien67" class="btn btn-primary btn-lg">execute Crawler soutien67.free.fr</a>
    	<a href="/crawler/toutSavoir" class="btn btn-primary btn-lg">execute Crawler toutsavoir-hatier.com</a>
    	<br><br>
    	<h3>Execute Crawler fr.khanacademy.org</h3> <br>
    	<p> Math</p>
    	<a href="/crawler/khanAcademy/1" class="btn btn-primary btn-lg">#1 Arithmetic</a>
    	<a href="/crawler/khanAcademy/2" class="btn btn-primary btn-lg">#2 Pre Algebra</a>   	
    	<a href="/crawler/khanAcademy/3" class="btn btn-primary btn-lg">#3 Algebra</a>   	
    	<a href="/crawler/khanAcademy/4" class="btn btn-primary btn-lg">#4 Probability</a>   	
    	<a href="/crawler/khanAcademy/5" class="btn btn-primary btn-lg">#5 Geometry</a>   	
    	<a href="/crawler/khanAcademy/6" class="btn btn-primary btn-lg">#6 Precalculus</a>   	
    	<a href="/crawler/khanAcademy/7" class="btn btn-primary btn-lg">#7 Trigonometry</a><br><br>   	
    	<a href="/crawler/khanAcademy/8" class="btn btn-primary btn-lg">#8 Integral Calculus</a>
    	<br>
    	<p>Science</p>
    	<a href="/crawler/khanAcademy/9" class="btn btn-primary btn-lg">#9 Biology</a>   	
    	<a href="/crawler/khanAcademy/10" class="btn btn-primary btn-lg">#10 Chemistry</a>   	
    	<a href="/crawler/khanAcademy/11" class="btn btn-primary btn-lg">#11 Cosmology and Astronomy</a>   	
    	<a href="/crawler/khanAcademy/12" class="btn btn-primary btn-lg">#12 Physics</a>   	
    	<a href="/crawler/khanAcademy/13" class="btn btn-primary btn-lg">#13 Organic Chemistry</a><br><br>  	
    	<a href="/crawler/khanAcademy/14" class="btn btn-primary btn-lg">#14 Health and Medicine</a>  	
    </div>
</div>    
</body>
</html>