<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<head>
<link rel="stylesheet" type="text/css"	href="/css/int/resource-image-gallery.css" />
<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
<script type="text/javascript" src="/js/int/problemReport.js"></script>
<script type="text/javascript" src="/js/int/addResourceFavorite.js"></script>
<script type="text/javascript">
 	$(document).ready(function() {
 		$.fn.editable.defaults.mode = 'inline';
 	    $('.editableField').editable({   
 	    	  emptytext: '? ?',
 	    	  type: 'text',
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
 	 		  success: function(response) {
 				location.reload();
 		      }
		});
 	    
 	    $('.nonurleditpop').popoverWithAutoHide("Pour modifier une url, il faut être connecté et avoir un niveau 4 de contribution.");
 	    
 	    $(".noAddProblemPop").popoverWithAutoHide("Pour signaler un problème, il faut être connecté.");
 	    
 	    $(".noneditresource").popoverWithAutoHide("Pour modifier ce champ, il faut être connecté et avoir un niveau 3 de contribution.");

	});
 	
 	function pop(){
 		 $('#nonpopoveredit').popoverWithAutoHide("Pour modifier une url, il faut étre connecté et avoir un niveau 4 de contribution.");
 	}
 	
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
		<c:if test="${user != null}">
			<lrftag:breadcrumbelement label="Ressource" link="/ressourcelist/${user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="${resource.name}" />
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-12">
					<dl class="dl-horizontal">
						<dt>Intitulé:</dt>
						<dd>
							<a id="title"  ${canEdit==true ? " href='#' class='editableField'" : " class='noneditresource'"}> ${resource.name}</a>
						</dd>

						<dt>Url:</dt>

						<dd>
							<c:forEach items="${resource.urlResources}" var="urlResource">
								<a href="${urlResource.url}" target="_blank" id="urlresource"data-type="text">${urlResource.url}</a>
								<c:if test="${urlResource.name != null}"> (${urlResource.name})</c:if>
								<span style="float:none; font-size:15px"  class="glyphicon glyphicon-pencil close 
								  <c:choose>
								        <c:when test="${(canEditUrl == true)}">
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
		                 <span  class="glyphicon glyphicon-plus close ${canEditUrl==false ? "nonurleditpop":""}"	 ${canEditUrl==true ? "onclick='onUrlAddClick()'":""}  style="float:none; font-size:15px" ></span> 
		                		                
		             
		                
		                
		                </dd>          
						<dt>Description:</dt>
						<dd>
							<a id="description" ${canEdit==true ? " href='#' class='editableField'" : " class='noneditresource'"}  data-type="textarea" data-inputclass="largerTextArea">${resource.description}</a>
						</dd>
						<dt>Matière:</dt>
						<dd>
							<a id="topic" ${canEdit==true ? " href='#' class='editableField' data-type='select'": " class='noneditresource'"} data-source="${dataEnumTopic}"> ${resource.topic.description}</a>
						</dd>

						<dt>Plate-forme:</dt>
						<dd>
							<a id="platform" ${canEdit==true? "href='#' class='editableField' data-type='select'":" class='noneditresource'"} data-source="${dataEnumPlatform}"> ${resource.platform.description}</a>
						</dd>
						<dt>Format:</dt>
						<dd>
							<a id="format"  ${canEdit==true? "href='#' class='editableField' data-type='select'":" class='noneditresource'"} data-source="${dataEnumFormat}"> ${resource.format.description}</a>
						</dd>
						<dt>Nature:</dt>
						<dd>
							<a id="nature" ${canEdit==true? "href='#' class='editableField' data-type='select'": " class='noneditresource'"} data-source="${dataEnumNature}"> ${resource.nature.description}</a>
						</dd>
						<dt>Langue:</dt>
						<dd>
							<a id="language" ${canEdit==true ? "href='#' class='editableField' data-type='select'":" class='noneditresource'"} data-source="${dataEnumLanguage}"> ${resource.language.description}</a>
						</dd>
						<dt>Publicité:</dt>
						<dd>
							<a id="advertising" ${canEdit==true ? "href='#' class='editableField' data-type='select'":" class='noneditresource'"} data-source="[{value:'false',text:'Non'},{value:'true',text:'Oui'}]">
							<c:if test="${resource.advertising == true}">
    							Oui
							</c:if>
							<c:if test="${resource.advertising == false}">
    							Non
							</c:if>
							 </a>
						</dd>
						<dt>Durée:</dt>
						<dd>
							<a id="duration" ${canEdit==true? "href='#' class='editableField' data-type='text'":" class='noneditresource'"}> ${resource.duration}</a> minutes
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
							<a ${canEdit==true ? "href='#' class='editableField'":" class='noneditresource'"}> ${resource.author}</a>
						</dd>
						<c:if test="${listMyPlayListsWithThisResource != null}">
							<dt>Mes séquences:</dt>
							<c:forEach items="${listMyPlayListsWithThisResource}" var="playlist">
								<dd>
									<a href="<c:url value='/playlist/${playlist.shortId}/${playlist.slug}'/>">${playlist.name}</a>
								</dd>
                          	</c:forEach>
                          	<br/> 
						</c:if>
						<c:if test="${listPlayList != null}">
							<dt>Ajouter à la séquence:</dt>
							<dd>
								<a id="addToPlayList" ${canEdit==true? "href='#' class='editableField' data-type='select'": " class='noneditresource'"} data-source="${listPlayList}">Sélectionner</a>
							</dd>
							<br/> 
						</c:if>
						<c:if test="${listOtherPeoplePlayListsWithThisResource != null}">
							<dt>Autres séquences:</dt>
							<c:forEach items="${listOtherPeoplePlayListsWithThisResource}" var="playlist">
								<dd>
									<a href="<c:url value='/playlist/${playlist.shortId}/${playlist.slug}'/>">${playlist.name}</a>
								</dd>
                          	</c:forEach>
						</c:if>
					</dl>
					<br />
					<lrftag:rating id="${resource.id}" title="${resource.name}"
						scoreResource="${resource.avgRatingScore}"
						scoreUser="${mapRating[resource].score}"
						countRating="${resource.countRating}" canvote="${Current.canVote}" />
					<br />
					
					<lrftag:favorite isFavorite="${isFavorite}" idResource="${resource.id}" />

					<br />
					
					
					<a class='glyphicon glyphicon-exclamation-sign addToolTip ${canAddProblem ? "' href='#modalProblemReport' data-toggle='modal'" : " noAddProblemPop'"} 
					   style="cursor:pointer; text-decoration:initial; line-height:20px; font-size:30px"
					   title="Signaler un problème pour cette ressource..."></a>
					<lrftag:problemreport title="${resource.name}"	resourceid="${resource.id}" />
 	    
					<br /> <br />

                    <c:if test="${not empty youtubeVideoId}">  <%-- This resource's first URL has been detected as being a youtube url => we embed the video in the page (it's better for SEO to not have people systematically leave our site) --%>
	                      <style type="text/css">  <%-- to have a responsive layout - See more at: http://avexdesigns.com/responsive-youtube-embed/#sthash.fkIODW9M.dpuf   --%>
	                        .video-container {
							    position: relative;
							    padding-bottom: 56.25%;
							    padding-top: 30px; height: 0; overflow: hidden;
							}
							 
							.video-container iframe,
							.video-container object,
							.video-container embed {
							    position: absolute;
							    top: 0;
							    left: 0;
							    width: 100%;
							    height: 100%;
							}
						  </style>	
	
	                      <div class="video-container"> 
	                         <iframe width="420" height="315" src="//www.youtube.com/embed/${youtubeVideoId}?rel=0" frameborder="0" allowfullscreen></iframe>
	                      </div> 
                    </c:if>	
					
					<br /> <br />

					<h4>Galerie</h4>
					<a data-toggle="modal" id="nonpopoveredit" ${canEdit == true ? "href='#modalImageGalerieResource'":"onclick='pop()'"}" class="btn btn-primary">Ajouter une Image</a>
					<br /> <br />
					<%@ include file="resourceimagegallery.jsp"%>
					
					<h4>Problèmes</h4>
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
								<label class="col-lg-2 control-label">Intitulé (optionnel): </label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="nameField"	name="name" />
									<span class="help-block">indication de la nature de cette url supplémentaire.<br/>
									   ex: "solutions", "vidéo de cette activité en classe", "version éditable", etc.</span>
								</div>
							</div>

							<div class="form-group">
								<label class="col-lg-2 control-label" >Url : </label>
								<div class="col-lg-10">
									<input type="text" class="form-control" id="urlField"	name="url" placeholder="http://..." required />
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
								<div class="help-block">Code de la compétence dans laquelle vous désirez placer la ressource.<br/>
								   Astuce: affichez la liste des compétences dans un autre onglet de votre navigateur.</div>
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