<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<html>
<body>
<div class="container">

    <a href="/createindex" class="btn btn-primary btn-lg">re-créer index</a>
    <hr>
<!--    <a href="/importfromvraisforum" class="btn btn-primary btn-lg">importer les compétences de http://findecycle.vraiforum.com/</a>
    <hr> 
    <div>
    	<a href="/importMathLabset" class="btn btn-primary btn-lg">execute batch import Math Labset</a>
    	<a href="/importFrenchLabset" class="btn btn-primary btn-lg">execute batch import French Labset</a>
    </div>-->
        <a href="/reslugify" class="btn btn-primary btn-lg">Reslugify URLs</a>
    <hr>
    <div>    	
    	<!--  <h3>Execute Crawlers</h3> <br> -->
		<!--  <a href="/crawler/brainPop" class="btn btn-primary btn-lg">Crawler brainpop.fr</a>  -->
    	<!--  executed without error  <a href="/crawler/championMath" class="btn btn-primary btn-lg">Crawler championmath.free.fr ></a> -->
    	<!--  executed without error  <a href="/crawler/classePrimaire" class="btn btn-primary btn-lg">Crawler classeprimaire.be</a>   --> 	
    	<!--  executed without error  <a href="/crawler/exoFiche" class="btn btn-primary btn-lg">Crawler exofiches.net</a><br><br>  -->
    	<!--  no persist  <a href="/crawler/fondationlamap" class="btn btn-primary btn-lg">Crawler fondation-lamap.org</a>     -->	  	
    	<!--  executed without error  <a href="/crawler/crawlerLaRecre" class="btn btn-primary btn-lg">Crawler LaRecre</a>    -->	 
    	<!--  executed without error  <a href="/crawler/professeurPhiFix" class="btn btn-primary btn-lg">Crawler profeseurphifix</a> -->
    	<!--  split into different categories <a href="/crawler/soutien67" class="btn btn-primary btn-lg">Crawler soutien67.free.fr</a>  -->
    	<!--  executed without error  <a href="/crawler/toutSavoir" class="btn btn-primary btn-lg">Crawler toutsavoir-hatier.com</a> -->
    	<!--  <br><br><hr> --> 
    	
<!--     	<h3>Execute Crawler Soutien67</h3> <br> -->
<!--     	<a href="/crawler67/1" class="btn btn-primary btn-lg">#1 Français 1</a> -->
<!--     	<a href="/crawler67/2" class="btn btn-primary btn-lg">#2 Français 2</a> -->
<!--     	<a href="/crawler67/3" class="btn btn-primary btn-lg">#3 Français 3</a> -->
<!--     	<a href="/crawler67/4" class="btn btn-primary btn-lg">#4 Français 4</a> -->
<!--     	<a href="/crawler67/5" class="btn btn-primary btn-lg">#5 Mathématique 1</a> -->
<!--     	<a href="/crawler67/6" class="btn btn-primary btn-lg">#6 Mathématique 2</a> -->
<!--     	<a href="/crawler67/7" class="btn btn-primary btn-lg">#7 Mathématique 3</a> -->
<!--     	<a href="/crawler67/8" class="btn btn-primary btn-lg">#8 Mathématique 4</a> -->
<!--     	<a href="/crawler67/9" class="btn btn-primary btn-lg">#9 Géographie</a> -->
<!--     	<a href="/crawler67/10" class="btn btn-primary btn-lg">#10 Histoire</a> -->
<!--     	<a href="/crawler67/11" class="btn btn-primary btn-lg">#11 Science</a> -->
    	
<!--     	<h3>Execute Crawler khanacademy</h3> <br> -->
<!--     	<p><b>Math</b></p> -->
<!--     	<a href="/crawlerkhanacademy/1" class="btn btn-primary btn-lg">#1 Arithmetic</a> -->
<!--     	<a href="/crawlerkhanacademy/2" class="btn btn-primary btn-lg">#2 Pre Algebra</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/3" class="btn btn-primary btn-lg">#3 Algebra</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/4" class="btn btn-primary btn-lg">#4 Probability</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/5" class="btn btn-primary btn-lg">#5 Geometry</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/6" class="btn btn-primary btn-lg">#6 Precalculus</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/7" class="btn btn-primary btn-lg">#7 Trigonometry</a><br><br>   	 -->
<!--     	<a href="/crawlerkhanacademy/8" class="btn btn-primary btn-lg">#8 Integral Calculus</a> -->
<!--     	<br> -->
<!--     	<p><b>Science</b></p> -->
<!--     	<a href="/crawlerkhanacademy/9" class="btn btn-primary btn-lg">#9 Biology</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/10" class="btn btn-primary btn-lg">#10 Chemistry</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/11" class="btn btn-primary btn-lg">#11 Cosmology and Astronomy</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/12" class="btn btn-primary btn-lg">#12 Physics</a>   	 -->
<!--     	<a href="/crawlerkhanacademy/13" class="btn btn-primary btn-lg">#13 Organic Chemistry</a><br><br>  	 -->
<!--     	<a href="/crawlerkhanacademy/14" class="btn btn-primary btn-lg">#14 Health and Medicine</a> -->
    	
<!--     	<h3>Execute Crawler Pepit</h3> <br> -->
<!--     	<a href="/crawlerPepit/1" class="btn btn-primary btn-lg">#1 Niveau 1</a> -->
<!--     	<a href="/crawlerPepit/2" class="btn btn-primary btn-lg">#2 Niveau 2</a> -->
<!--     	<a href="/crawlerPepit/3" class="btn btn-primary btn-lg">#3 Niveau 3</a> -->
<!--     	<a href="/crawlerPepit/4" class="btn btn-primary btn-lg">#4 Niveau 4</a> -->
<!--     	<a href="/crawlerPepit/5" class="btn btn-primary btn-lg">#5 Niveau 5</a> -->
<!--     	<a href="/crawlerPepit/6" class="btn btn-primary btn-lg">#6 Niveau 6</a> -->
<!--     	<a href="/crawlerPepit/7" class="btn btn-primary btn-lg">#7 Conjugaison</a> -->
<!--     	<a href="/crawlerPepit/8" class="btn btn-primary btn-lg">#8 Tables Multiplication</a> -->
<!--     	<a href="/crawlerPepit/9" class="btn btn-primary btn-lg">#9 Ens. Spécial</a> -->
<!--     	<a href="/crawlerPepit/10" class="btn btn-primary btn-lg">#10 Pour tous</a>  	 -->
    </div>
</div>    
</body>
</html>