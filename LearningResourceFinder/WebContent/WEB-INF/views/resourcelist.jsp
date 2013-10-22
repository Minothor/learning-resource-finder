<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<title>Catalog</title>
</head>
<body>
	<c:choose>
		<c:when test="${topic != null}">
			<lrftag:breadcrumb linkactive="${topic.description}">
			<lrftag:breadcrumbelement label="Home" link="/home" />
			<lrftag:breadcrumbelement label="Contributions" link="/contribution" />
			</lrftag:breadcrumb>
		</c:when>
		<c:otherwise>
			<lrftag:breadcrumb linkactive="Ressources">
			<lrftag:breadcrumbelement label="Home" link="/home" />
			<lrftag:breadcrumbelement label="${user.fullName}" link="/user/${user.userName}" />
			</lrftag:breadcrumb>
		</c:otherwise>
	</c:choose>
	<div class="container">
	<c:choose>
		 <c:when test="${topic != null}">
			<h1>${topic} : ${problemTitle}</h1>
		 </c:when>
		 <c:otherwise>
		 	<h1>Ressources : <a href="/user/${user.userName}">${user.fullName}</a></h1>
		 </c:otherwise>
	</c:choose>
	<section id="resourcelist">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="sixteen columns">
						<c:forEach items="${resourceList}" var="resource">
							<div style="float: left; position: relative; margin-top: 10px; width: 210px;">
								<lrftag:resource resource="${resource}"/>
								<br />
								<lrftag:rating id="${resource.id}" title="${resource.name}" scoreResource="${resource.avgRatingScore}" scoreUser="${mapRating[resource].score}" countRating="${resource.countRating}" canvote="${usercanvote}" />
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
	</section>
	</div>
</body>
</html>