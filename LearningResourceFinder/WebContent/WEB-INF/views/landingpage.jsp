<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<body>

<div class="container">

      <%-- jsp:include does not work with Sitemesh !! John 2014-10-31 --%>
    
    <c:choose>
	      <c:when test="${urlPath == '/corps-humain'}">
	       PAGE 1 ${urlPath}
	     </c:when>
	     <c:when test="${urlPath == '/exercices-de-math'}">
	       PAGE 2 ${urlPath}
	     </c:when>
	     <c:when test="${urlPath == '/neerlandais'}">
	       PAGE 3 ${urlPath}
	     </c:when>
	     
	      <c:when test="${urlPath == '/anglais-facile'}">
	       PAGE 4 ${urlPath}
	     </c:when>
	     
	      <c:when test="${urlPath == '/conjugaison'}">
	       PAGE 5 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/orthographe'}">
	       PAGE 6 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/ecole-virtuelle'}">
	       PAGE 7 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/nederlands'}">
	       PAGE 8 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/chien-et-chat'}">
	       PAGE 9 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/subjonctif'}">
	       PAGE 10 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/ligne-du-temps'}">
	       PAGE 11 ${urlPath}
	     </c:when>
	     
	     <c:when test="${urlPath == '/botanique'}">
	       PAGE 12 ${urlPath}
	     </c:when>
	    <c:otherwise>
	        
	    </c:otherwise>
    </c:choose>
</div>



    <%@ include file = "/WEB-INF/includes/search.jsp" %>
    

<div class="container">

 	<section id="resourcelist">
		<c:forEach items="${resourceList}" var="resource">
			<lrftag:resource resource="${resource}" />
		</c:forEach>
	</section>

</div>


</body>
</html>