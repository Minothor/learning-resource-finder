<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<html>
<body>
<div class="container">

    <a href="/createindex" class="btn btn-primary btn-lg">re-créer index</a>
    <hr>
<!--     <a href="/importfromvraisforum" class="btn btn-primary btn-lg">importer les compétences de http://findecycle.vraiforum.com/</a>
    <hr> -->
    <div>
    	<a href="/importMathLabset" class="btn btn-primary btn-lg">execute batch import Math Labset</a>
    	<a href="/importFrenchLabset" class="btn btn-primary btn-lg">execute batch import French Labset</a>
    </div>
    <hr>
    <div>    	
    	<h3>Execute Crawlers</h3> <br>
    	<a href="/crawler/brainPop" class="btn btn-primary btn-lg">Crawler brainpop.fr</a>
    	<a href="/crawler/championMath" class="btn btn-primary btn-lg">Crawler championmath.free.fr</a>
    	<a href="/crawler/classePrimaire" class="btn btn-primary btn-lg">Crawler classeprimaire.be</a>   	
    	<a href="/crawler/exoFiche" class="btn btn-primary btn-lg">Crawler exofiches.net</a><br><br> 
    	<a href="/crawler/fondationlamap" class="btn btn-primary btn-lg">Crawler fondation-lamap.org</a>   	
    	<a href="/crawler/crawlerLaRecre" class="btn btn-primary btn-lg">Crawler LaRecre</a>   	
    	<a href="/crawler/professeurPhiFix" class="btn btn-primary btn-lg">Crawler profeseurphifix</a>
    	<a href="/crawler/soutien67" class="btn btn-primary btn-lg">Crawler soutien67.free.fr</a><br><br>
    	<a href="/crawler/toutSavoir" class="btn btn-primary btn-lg">Crawler toutsavoir-hatier.com</a>
    	<br><br>
    	<h3>Execute Crawler khanacademy</h3> <br>
    	<p><b>Math</b></p>
    	<a href="/crawlerkhanacademy/1" class="btn btn-primary btn-lg">#1 Arithmetic</a>
    	<a href="/crawlerkhanacademy/2" class="btn btn-primary btn-lg">#2 Pre Algebra</a>   	
    	<a href="/crawlerkhanacademy/3" class="btn btn-primary btn-lg">#3 Algebra</a>   	
    	<a href="/crawlerkhanacademy/4" class="btn btn-primary btn-lg">#4 Probability</a>   	
    	<a href="/crawlerkhanacademy/5" class="btn btn-primary btn-lg">#5 Geometry</a>   	
    	<a href="/crawlerkhanacademy/6" class="btn btn-primary btn-lg">#6 Precalculus</a>   	
    	<a href="/crawlerkhanacademy/7" class="btn btn-primary btn-lg">#7 Trigonometry</a><br><br>   	
    	<a href="/crawlerkhanacademy/8" class="btn btn-primary btn-lg">#8 Integral Calculus</a>
    	<br>
    	<p><b>Science</b></p>
    	<a href="/crawlerkhanacademy/9" class="btn btn-primary btn-lg">#9 Biology</a>   	
    	<a href="/crawlerkhanacademy/10" class="btn btn-primary btn-lg">#10 Chemistry</a>   	
    	<a href="/crawlerkhanacademy/11" class="btn btn-primary btn-lg">#11 Cosmology and Astronomy</a>   	
    	<a href="/crawlerkhanacademy/12" class="btn btn-primary btn-lg">#12 Physics</a>   	
    	<a href="/crawlerkhanacademy/13" class="btn btn-primary btn-lg">#13 Organic Chemistry</a><br><br>  	
    	<a href="/crawlerkhanacademy/14" class="btn btn-primary btn-lg">#14 Health and Medicine</a>  	
    </div>
</div>    
</body>
</html>