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

							<c:choose>
								<c:when test='${resource.validationStatus=="REJECT"}'>
										<p>Un contributeur a estimé que cette ressource est inappropriée pour les enfants.</p>
										<p>Seuls les membres connectés en tant qu'adulte y ont donc accès.</p>
								</c:when>
								<c:otherwise>
										<p>Cette ressource n'est pas encore validée.</p>
										<p>Entretemps, par précaution, seuls les membres connectés
											en tant qu'adulte y ont accès.</p>
								</c:otherwise>
							</c:choose>
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