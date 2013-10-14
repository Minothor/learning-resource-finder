<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<title>Catalog</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="Liste des ressources">
		<lrftag:breadcrumbelement label="Home" link="home" />
	</lrftag:breadcrumb>
	<div class="container">
	
	<section id="resourcelist">
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="sixteen columns">
						<a data-toggle="modal" href="#addResourceModal" class="btn btn-primary btn-lg">Créer une ressource</a> <br />
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
		</div>
	</section>
	</div>
</body>
</html>