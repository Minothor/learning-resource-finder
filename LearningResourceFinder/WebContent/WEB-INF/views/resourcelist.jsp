<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Catalog</title>
</head>
<body>
	<lrf:breadcrumb linkactive="Liste des ressources">
		<lrf:breadcrumbelement label="Home" link="home" />
	</lrf:breadcrumb>
	<section id="resourcelist">
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="sixteen columns">
						<a data-toggle="modal" href="#addResourceModal" class="btn btn-primary btn-lg">Créer une ressource</a> <br />
						<c:forEach items="${resourceList}" var="resource">
							<div style="float: left; position: relative; margin-top: 10px; width: 210px;">
								<restag:resource resource="${resource}"></restag:resource>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>