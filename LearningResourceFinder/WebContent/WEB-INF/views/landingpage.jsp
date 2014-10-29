<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<body>

<jsp:include page="/WEB-INF/includes/${introtextjsp}" />


<%@include file = "/WEB-INF/includes/search.jsp" %>

<%-- 	<section id="resourcelist">
		<c:forEach items="${resourceList}" var="resource">
			<lrftag:resource resource="${resource}" />
		</c:forEach>
	</section>
 --%>



</body>
</html>