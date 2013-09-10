<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/js/int/addResource.js"></script>
<link rel="stylesheet" type="text/css" href="/css/int/resource-image-gallery.css" />
<title>Catalog</title>
</head>
<body>
	<a data-toggle="modal" href="#myModal" class="btn btn-primary btn-lg">Créer une ressource</a>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une ressource</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="url">Url</label> <input type="text" class="form-control" id="url"placeholder="Taper l'URL">
						</div>
						<div class="form-group id="titleDiv" style="display: none;">
						    <label for="name">Titre</label> <input type="text" class="form-control" id="title"placeholder="Taper le titre">
						</div>
						<button type="submit"  class="pull-right">Sauver</button>
					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
					<button type="button" class="btn btn-primary">Ajouter</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 	<a href="#" data-width="500" data-rel="popupJquery" class="poplight">Créer une ressource</a> -->
	<!-- 	<div id="popupJquery" class="popupJquery"> -->
	<!-- 		<div class="popup-close"> -->
	<!-- 			<a class="close" title="close this popup">X</a> -->
	<!-- 		</div> -->
	<!-- 		<h2>Créer une ressource</h2> -->
	<!-- 		<form:form method="post" action="#" class="formUrlResource"> -->

	<!-- 			<label for="url">Url : </label> -->
	<!-- 			<input type="text" name="url" id="url" /> -->
	<!-- 			<br /> -->
	<!-- 			<div id="titleDiv" style="display: none;"> -->
	<!-- 				<label for="name">Titre :</label> <input type="text" id="name" name="name" />  -->
	<!-- 				<br /> -->
	<!-- 			</div> -->

	<%-- 			<input type="hidden" name="idresource" id="idresource" value="${resource.id}" /> --%>
	<!-- 			<input type="button" class="btnSubmit" value="Ajouter "onclick="showTilte()" /> -->
	<!-- 		</form:form> -->
	<%-- 		<p id="response">${response}</p> --%>
	<!-- 	</div> -->
	<br />

	<c:forEach items="${resourceList}" var="resource">
		<div
			style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<restag:resource resource="${resource}"></restag:resource>
		</div>
	</c:forEach>
</body>
</html>