<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<title>Catalogue de ressources</title>
<script type="text/javascript" src="/js/int/moreResources.js"></script>
</head>
<body>
    <input type="hidden" value="${tokenListOfResources}" id="tokenListOfResources"/>
    
	<c:choose>
		<c:when test="${topic != null}">
			<lrftag:breadcrumb linkactive="${topic.description}">
			<lrftag:breadcrumbelement label="home" link="/home" />
			<lrftag:breadcrumbelement label="contributions" link="/contribution" />
			</lrftag:breadcrumb>
		</c:when>
		<c:otherwise>
			<lrftag:breadcrumb linkactive="${titleFragment}">
			<lrftag:breadcrumbelement label="home" link="/home" />
			<lrftag:breadcrumbelement label="${user.fullName}" link="/user/${user.userName}" />
			</lrftag:breadcrumb>
		</c:otherwise>
	</c:choose>
	
	<div class="container">
		<c:choose>
			 <c:when test="${topic != null}">
				<h1>${topic.description}: ${problemTitle}</h1>
			 </c:when>
			 <c:otherwise>
			 	<h1>${titleFragment} : <a href="/user/${user.userName}">${user.fullName}</a></h1>
			 </c:otherwise>
		</c:choose>
	    <section id="resourcelist">
			<c:forEach items="${resourceList}" var="resource">
				<lrftag:resource resource="${resource}"/>
			</c:forEach>
	    </section>
	    
		<div id="spinner">
	     	<img style="margin-left: 35%;" src="/images/spinner.gif" />
	  	</div>
	</div>
</body>
</html>