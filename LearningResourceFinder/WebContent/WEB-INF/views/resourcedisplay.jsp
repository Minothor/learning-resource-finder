<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<!-- Script for Add a URL in a Resource-->
	<script type="text/javascript" src="/js/int/ajaxAddUrlResource.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/int/resource-image-gallery.css"  />
	<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
 	<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
</head>
<body>
	<lrftag:breadcrumb linkactive="${resource.name}">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="Ressource" link="/ressourcelist" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="${resource.name}"/>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-12">
				<h4>Informations :</h4>
					<dl class="dl-horizontal">
						<dt>Nom :</dt>
					  	<dd>${resource.name}</dd>
					  	<dt>Description :</dt>
					  	<dd>${resource.description}</dd>
					  	<dt>Auteur :</dt>
					  	<dd><a href="/user/${resource.createdBy.userName}">${resource.createdBy.fullName}</a></dd>
					</dl>
					<br />
					<lrftag:rating id="${resource.id}" title="${resource.name}" scoreResource="${resource.avgRatingScore}" scoreUser="${mapRating[resource].score}" countRating="${resource.countRating}" />
					<br />
					<c:if test="${canEdit}">
					<h4>Options :</h4>	
					<a class="btn btn-primary" href="<c:url value='/resourceedit?id=${resource.id}'/>">Edit resource</a>
					</c:if>
					<br />
					<br />
					<h4>Les liens</h4>
					<a data-toggle="modal" href="#modalUrlResource" class="btn btn-primary">Ajouter une URL</a>
					<br />
					<br />
					<div class="table-responsive">
						<table class="table table-bordered ">
							<tr>
								<th>Nom</th>
								<th>Url</th>
								<th>Action</th>
							</tr>
						<c:forEach items="${resource.urlResources}" var="url">
							<tr>
								<td>${url.name}</td>
								<td><a href="${url.url}">${url.url}</a></td>
								<td><a href="<c:url value='/removeurlresource?id=${url.id}'/>">Supprimer</a></td>
							</tr>	
						</c:forEach>
						</table>
					</div>
					<br />
					<h4>Galerie</h4>
					<c:if test="${canEdit}">
					<a data-toggle="modal" href="#modalImageGalerieResource" class="btn btn-primary">Ajouter une Image</a>
					</c:if>
					<br />
					<br />
					<%@ include file="resourceimagegallery.jsp" %>
				</div>
			</div>
		</div>

		<!-- Modal : AJOUTER UNE URL -->
		<div class="modal fade" id="modalUrlResource" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
		          		<h4 class="modal-title">Ajouter une URL</h4>
		        	</div>		        	
		     		<form method="post" action="#" enctype="multipart/form-data" class="form-horizontal formUrlResource">	
		     			<div class="modal-body">
		     			
		     				<div class="form-group">
							    <label class="col-lg-2 control-label" style="text-align:left;">
							    	Nom :
							    </label>
							    <div class="col-lg-10">
							      	<input type="text" class="form-control" id="name" name="name" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-2 control-label" style="text-align:left;">
							    	Url :
							    </label>
							    <div class="col-lg-10">
							      	<input type="text" class="form-control" id="urlResource" name="urlResource" />
								</div>
							</div>
							
							<p id="response">${response}</p>
							
				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="idResource" id="idResource" value="${resource.id}" />
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<button type="button" class="btn btn-primary closeModal btnSubmit" onclick="ajaxPostUrlResource();">Sauver le lien</button>
			        	</div>
				    </form>
		      	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
		
		<!-- Modal : AJOUTER UNE IMAGE -->
		<div class="modal fade" id="modalImageGalerieResource" tabindex="-1" role="dialog" aria-labelledby="Ajouter une image" aria-hidden="true">
			<div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
		          		<h4 class="modal-title">Ajouter une image à la galerie</h4>
		        	</div>		        	
		     		<form method="post" action="/resource/imageadd" enctype="multipart/form-data" class="form-horizontal formUrlGallery">	
		     			<div class="modal-body">
		     			
		     				<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> 
					          		Depuis l'ordinateur
					          	</label>
							    <div class="col-lg-8">
							      	<input type="file"name="file" value="Parcourir..." class="inputSource" id="inputFile" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="url" class="radioUrl" /> 
					          		Depuis un lien
					          	</label>
							    <div class="col-lg-8">
							    	<input type="hidden" name="strUrl" value="http://..." class="inputSource form-control" id="inputUrl" /> 
								</div>
							</div>
							
							<p id="response">${response}</p>
							
				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="idResource" id="idResource" value="${resource.id}" />
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<button type="submit" name="btnPicture" class="btn btn-primary closeModal btnSubmit">Sauver l'image</button>
			        	</div>
				    </form>
		      	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</body>
</html>