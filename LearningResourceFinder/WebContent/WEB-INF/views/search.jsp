<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>

<style type="text/css">
.ecart {
	margin: 0 80px 0px 100px;
	float: left;
}
</style>

</head>
<body>

	<center>
		<h1>
			Hello you are in the search page! <br> WELCOM
		</h1>
	</center>
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
	<a href="searchresource?search=${searchValue}">${fn:length(searchResultsMore)}</a>
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
<%-- 			<lrftag:competencepath competence="${competence.getName()} "></lrftag:competencepath> --%>
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


</body>
</html>