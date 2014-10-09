<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<script type="text/javascript" src="/js/int/resourceIncreaseDiv.js"></script>
<title>Catalog</title>
<style>
.resource-content-big {
	display: inline-block;
 	width: 380px; 
 	min-height: 250px; 
	background: white;
	z-index: 10;
	position: absolute;
}

.resource-content {
	z-index: 10;
	height:100%;
}

.resource-content img {
	max-width: 100%;
}

.resource-content-hidden {
	display: none;
}

.resource-content-big .resource-content-hidden {
	display: block;
}

.resource-content .imgDiv ,
.resource-content .descriptionDiv {
	width:100% !important;
}
</style>

</head>
<body>
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
	</div>
</body>
</html>