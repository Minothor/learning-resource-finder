<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Script for Add a URL in a Resource-->
<script type="text/javascript" src="/js/int/ajaxAddUrlResource.js"></script>
<link rel="stylesheet" type="text/css"
	href="/css/int/resource-image-gallery.css" />
<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
<script type="text/javascript" src="/js/int/problemReport.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
 		$.fn.editable.defaults.mode = 'inline';
 	    $('.editableField').editable({      
 	    	  type: 'text',
 	    	  inputclass: 'largerTextArea',
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
		});
 	    
 	    $('.nonurleditpop').popover({
 	    	html :true,
 	        content :"Pour modifier une url, il faut �tre connect� et avoir un niveau 4 de contribution. "
 	    });
	});
 	
 	function onUrlAddClick(){
 		$("#modalUrlResource").modal("show");
 	}
 	function onUrlEditClick(id, url, name) {
 		$("#urlResourceIdField").attr("value", id);
 		$("#urlField").attr("value", url);
 		$("#nameField").attr("value", name);
 		$("#modalUrlResource").modal("show");
 	}
 	
 	
 	function onCompetenceRemoveClick(competenceid, resourceid){
 		$("#competenceHiddenField").attr("value", competenceid);
 		$("#modalConfirmDeleteCompetence").modal("show");	
 	}
 	function onUrlRemoveClick(id) {
 		$("#urlResourceHiddenField").attr("value", id);
 	    $("#modalConfirmDeleteResource").modal("show");	
 	}
 	
 	

 	function onAddCompetenceClick(){
 		$("#modalCompetence").modal("show");
 	}
 	function onCompetenceRemoveClick(competenceid, resourceid){
 		$("#competenceHiddenField").attr("value", competenceid);
 		$("#modalConfirmDeleteCompetence").modal("show");	
 	}
	</script>

<STYLE type="text/css">
.largerTextArea {
	width: 500px;
}
</STYLE>

</head>
<body>
	<lrftag:breadcrumb linkactive="${resource.name}">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="Ressource" link="/ressourcelist" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="${resource.name}" />
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-12">
					<h4>Informations :</h4>
					<dl class="dl-horizontal">
						<dt>Intitulé:</dt>
						<dd>
							<a href="#" class="editableField"> ${resource.name}</a>
						</dd>

						<dt>Url:</dt>

						<dd>
							<c:forEach items="${resource.urlResources}" var="urlResource">
								<a href="${urlResource.url}" target="_blank" id="urlresource"data-type="text">${urlResource.url}</a>
								<c:if test="${urlResource.name != null}"> (${urlResource.name})</c:if>
								<span style="float:none; font-size:15px"  class="glyphicon glyphicon-pencil close 
								  <c:choose>
								        <c:when test="${(canEditUrl== true)}">
								          " onclick="onUrlEditClick(${urlResource.id},'${urlResource.url}','${urlResource.name}')"> 
								        </c:when>
								        <c:otherwise>
								          nonurleditpop">
								        </c:otherwise>
								  </c:choose>
								</span>
								<button type="button" class="close" style="float:none;" onclick="onUrlRemoveClick(${urlResource.id})">&times;</button>
		                        <br />
		                    </c:forEach>
		                    <span class="glyphicon glyphicon-plus close" style="float:none; font-size:15px" onclick="onUrlAddClick()"></span>
		                </dd>          
						<dt>Description:</dt>
						<dd>
							<a ${canEdit==true ? " href='#' class='editableField'" : " class='noneditresource'"}  data-type="textarea">${resource.description}</a>
						</dd>

						<dt>Plate-forme:</dt>
						<dd>
							<a href="#" class="editableField" data-type="select" data-source="${dataEnumPlatform}"> ${resource.platform}</a>
						</dd>

						<dt>Contributeur:</dt>
						<dd>
							<a href="/user/${resource.createdBy.userName}">${resource.createdBy.fullName}</a>
						</dd>
						<dt>Compétence:</dt>
                        <dd>
                          <c:forEach items="${resource.competences}" var="competence">
                            <lrf:competencepath competence="${competence}"/>
                              <button type="button" class="close" style="float:none;" onclick="onCompetenceRemoveClick(${competence.id})">&times;</button>
                            <br/>                           
                          </c:forEach>
                          <span class="glyphicon glyphicon-plus close ${canEdit==false ? "noneditresource\"" :  " \" onclick= 'onAddCompetenceClick()'"} style="float:none; font-size:15px" ></span> 
                        </dd>
                        <dt>Auteur:</dt>
						<dd>
							<a href="#" class="editableField"> ${resource.author}</a>
						</dd>
					</dl>
					<br />
					<lrftag:rating id="${resource.id}" title="${resource.name}"
						scoreResource="${resource.avgRatingScore}"
						scoreUser="${mapRating[resource].score}"
						countRating="${resource.countRating}" canvote="${usercanvote}" />
					<br />
					
					<lrftag:favorite isFavorite="${isFavorite}" idResource="${resource.id}" />

					<br />
					<c:if test="${canEdit}">
						<lrftag:problemreport title="${resource.name}"
							resourceid="${resource.id}" />
					</c:if>
					<br /> <br />
					<h4>Les liens</h4>
					<a data-toggle="modal" href="#modalUrlResource" class="btn btn-primary">Ajouter une URL</a> <br /> <br />
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
									<td><a
										href="<c:url value='/removeurlresource?id=${url.id}'/>">Supprimer</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<br />
					<h4>Galerie</h4>
					<c:if test="${canEdit}">
						<a data-toggle="modal" href="#modalImageGalerieResource"
							class="btn btn-primary">Ajouter une Image</a>
					</c:if>
					<br /> <br />
					<%@ include file="resourceimagegallery.jsp"%>
					
					<h4>Problème</h4>
					<%@ include file="problemlist.jsp" %>
				</div>
			</div>
		</div>

		<!-- Modal : ADD URL -->
		<div class="modal fade" id="modalUrlResource" tabindex="-1"role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeModal"
							data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">Ajouter une URL</h4>
					</div>
					<form method="post" action="/urlresourceeditsubmit"
						enctype="multipart/form-data"
						class="form-horizontal formUrlResource">
						<div class="modal-body">

							<div class="form-group">
								<label class="col-lg-2 control-label" style="text-align: left;">Intitul� (optionnel): </label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="nameField"	name="name" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label" style="text-align: left;">Url : </label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="urlField"	name="url" required />
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<input type="hidden" name="resourceid" value="${resource.id}" /> <%-- used for create --%>
							<input id="urlResourceIdField" type="hidden" name="urlresourceid" />	<%-- Value set via JavaScript -- used for edit --%>
							<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Annuler</button>
							<input class="btn btn-primary" type="submit" value="Enregistrer" />
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->

		<!-- Modal : ADD IMAGE -->
		<div class="modal fade" id="modalImageGalerieResource" tabindex="-1"role="dialog" aria-labelledby="Ajouter une image" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeModal"
							data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">Ajouter une image à la galerie</h4>
					</div>
					<form method="post" action="/resource/imageadd"
						enctype="multipart/form-data"
						class="form-horizontal formUrlGallery">
						<div class="modal-body">

							<div class="form-group">
								<label class="col-lg-4 control-label" style="text-align: left;">
									<input type="radio" name="rdFrom" value="computer"
									class="radioComputer" id="inputComputer" checked="checked" />
									Depuis l'ordinateur
								</label>
								<div class="col-lg-8">
									<input type="file" name="file" value="Parcourir..."
										class="inputSource" id="inputFile" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-4 control-label" style="text-align: left;">
									<input type="radio" name="rdFrom" value="url" class="radioUrl" />
									Depuis un lien
								</label>
								<div class="col-lg-8">
									<input type="hidden" name="strUrl" value="http://..."
										class="inputSource form-control" id="inputUrl" />
								</div>
							</div>

							<p id="response">${response}</p>

						</div>

						<div class="modal-footer">
							<input type="hidden" name="idResource" id="idResource"
								value="${resource.id}" />
							<button type="button" class="btn btn-default closeModal"
								data-dismiss="modal">Fermer</button>
							<button type="submit" name="btnPicture"
								class="btn btn-primary closeModal btnSubmit">Sauver
								l'image</button>
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
	
	<!-- Modal : URL REMOVE CONFIRM  -->
   <div class="modal fade" id="modalConfirmDeleteResource">
	   <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title">Confirmation </h4>
	      </div>
	      <div class="modal-body">
	        <p>Voulez-vous supprimer cette URL?</p>
	      </div>
	      <div class="modal-footer">
	       <form action="/removeurlresource">
	        <input id="urlResourceHiddenField" type="hidden" name="id" value="" />  <%-- Filled by JavaScript --%>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
	        <button type="submit" class="btn btn-primary">Supprimer</button>
	       </form>
	      </div>
	    </div><!-- /.modal-content -->
	   </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- Modal : ADD COMPETENCE -->
		<div class="modal fade" id="modalCompetence" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeModal"
							data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">Placer la ressource dans une compétence</h4>
					</div>
					<form method="post" action="/competenceaddtoresourcesubmit"  role="form">
						<div class="modal-body">
						    <div class="form-group">
								<row>
								  <div class="col-lg-4">
								    <label for="codeField">Code:</label>
								
									<input type="text" class="form-control" id="codeField"	name="code" />
								  </div>
								</row>
								<br/><br/><br/>
								<div class="help-block">Code de la comp�tence dans laquelle vous d�sirez placer la ressource.<br/>
								   Astuce: affichez la liste des comp�tences dans un autre onglet de votre navigateur.</div>
							</div>
						</div>

						<div class="modal-footer">
							<input type="hidden" name="resourceid" value="${resource.id}" />
							<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Annuler</button>
							<input class="btn btn-primary" type="submit" value="Enregistrer" />
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
		
		
		<!-- Modal : COMPETENCE REMOVE CONFIRM  -->
   <div class="modal fade" id="modalConfirmDeleteCompetence">
	   <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title">Confirmation</h4>
	      </div>
	      <div class="modal-body">
	        <p>Voulez-vous retirer cette compétence de la ressource ?</p>
	      </div>
	      <div class="modal-footer">
	       <form action="/removecompetencefromresource">
	        <input id="competenceHiddenField" type="hidden" name="competenceid" />  <%-- Filled by JavaScript --%>
	        <input id="resourceHiddenField" type="hidden" name="resourceid" value="${resource.id}" />
	        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
	        <button type="submit" class="btn btn-primary">Retirer</button>
	       </form>
	      </div>
	   </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
	
</body>
</html>