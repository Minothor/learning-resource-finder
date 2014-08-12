<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/css/int/resource-image-gallery.css" />
<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
<script type="text/javascript" src="/js/int/problemReport.js"></script>
<script type="text/javascript" src="/js/int/addResourceFavorite.js"></script>

</head>
<body>




	<lrftag:breadcrumb linkactive="${resource.shortId}">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<c:if test="${user != null}">
			<lrftag:breadcrumbelement label="ressource"
				link="/ressourcelist/${user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<lrftag:pageheadertitle title="${resource.shortId}" />
			</div>

		</div>



		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-body">

						<div class="panel-body">
							<p>Cette ressource n'est pas encore validée.</p>
							<p>Entretemps, par précaution, seul les membres connectés en
								tant qu'adulte ont accès à cette ressource.</p>
						</div>

					</div>
					<%-- end panel body --%>
				</div>
				<%-- end panel --%>
			</div>
		</div>
		<%-- end row --%>
</body>
</html>