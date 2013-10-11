<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>	
	<!-- Script for Add a URL in a Resource-->
	<script type="text/javascript" src="/js/int/ajaxAddUrlResource.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/int/resource-image-gallery.css"  />
	<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
 	<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
 	<script type="text/javascript" src="/js/int/problemReport.js"></script>
 	
 	<script type="text/javascript">
 	$(document).ready(function() {
 		$.fn.editable.defaults.mode = 'inline';
 	    $('#title,#description,#platform').editable({      
 	    	  type: 'text',
 	    	  inputclass: 'largerTextArea',
 	    	 
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
		});
	});
 	
 	function onEditClick(id, url, name) {
 		$("#idField").attr("value", id);
 		$("#urlField").attr("value", url);
 		$("#nameField").attr("value", name);
 		$("#modalUrlResource").modal("show");
 	}
	</script>
	
	<STYLE type="text/css">
	   .largerTextArea {
	       width:500px;
	   }
    </STYLE>
	   
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
						<dt>Intitulé :</dt>
						<dd><a href="#" id="title" > ${resource.name}</a></dd>
					  	
						<dt>Url :</dt>
						
					  	<dd>
					  	   <c:forEach items="${resource.urlResources}" var="urlResource">
					  	      <a href="${urlResource.url}" target="_blank"  id="urlresource" data-type="text">${urlResource.url}</a>
					  	      <c:if test="${urlResource.name != null}"> (${urlResource.name})</c:if>
					  	      <span class="glyphicon glyphicon-pencil" onclick="onEditClick(${urlResource.id}, '${urlResource.url}', '${urlResource.name}')" ></span>
					  	      <br/>
					  	   </c:forEach>
					    </dd>
					  	
					  	<dt>Description :</dt>
					  	<dd><a href="#" id="description" data-type="textarea">${resource.description}</a></dd>
					  	
					  	<dt>Plate-forme :</dt>
						<dd><a href="#" id="platform" data-type="select" data-source="${dataEnumPlatform}"> ${resource.platform}</a></dd>
						
					  	<dt>Auteur :</dt>
					  	<dd><a href="/user/${resource.createdBy.userName}">${resource.createdBy.fullName}</a></dd>
					  	
					  	
					  	
					</dl>
					<br />
					<lrftag:rating id="${resource.id}" title="${resource.name}" scoreResource="${resource.avgRatingScore}" scoreUser="${mapRating[resource].score}" countRating="${resource.countRating}" />
					<br />	
					<c:if test="${canEdit}">				
						<lrftag:problemreport title="${resource.name}" resourceid="${resource.id}" />
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
		     		<form method="post" action="/urlresourceeditsubmit" enctype="multipart/form-data" class="form-horizontal formUrlResource">	
		     			<div class="modal-body">
		     			
		     				<div class="form-group">
							    <label class="col-lg-2 control-label" style="text-align:left;">
							    	Intitulé (optionnel) :
							    </label>
							    <div class="col-lg-10">
							      	<input type="text" class="form-control" id="nameField" name="name" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-2 control-label" style="text-align:left;">
							    	Url :
							    </label>
							    <div class="col-lg-10">
							      	<input type="text" class="form-control" id="urlField" name="url" required />
								</div>
							</div>
							
							
				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="resourceid" value="${resource.id}" />  <%-- used for create --%>
							<input type="hidden" name="urlresourceid" />  <%-- Value set via JavaScript -- used for edit --%>
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<input class="btn btn-primary" type="submit" value="Enregistrer" />
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