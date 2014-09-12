<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/int/resource-image-gallery.css" />
<link rel="stylesheet" type="text/css" href="/css/ext/jquery.Jcrop.css" />
<link rel="stylesheet" type="text/css" href="/css/ext/demos.css" />
<script type="text/javascript" src="/js/int/imageGallery-sortable.js"></script>
<script type="text/javascript" src="/js/int/problemReport.js"></script>
<script type="text/javascript" src="/js/int/addResourceFavorite.js"></script>
<script type="text/javascript" src="/js/ext/jquery.Jcrop.js"></script>
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
<script src="/js/ext/jquery.nouislider.min.js"></script>
<script src="/js/ext/jquery.nouislider.full.min.js"></script>
<link href="/css/ext/jquery.nouislider.css" rel="stylesheet">

<script type="text/javascript" src="/js/int/addResourceFavorite.js"></script>
<script type="text/javascript">


	function ajaxeditcycle() {
		
		
		var resourceID=$("#resourceHiddenField").val();
		var mincycle = $("#valuemin").val();
		var maxcycle = $("#valuemax").val();	
		
		$.ajax({	
			type : "POST", 
			dataType: "text",
			url : "/ajax/cycleeditinresourcesubmit",
			data : "pk="+resourceID+"&mincycle="+mincycle+"&maxcycle="+maxcycle,
			success : function(data) {
					location.reload();			
			},
			
			error : function(data) {
				alert("Problème en contactant le serveur" );
			}
		});	
	}

 	$(document).ready(function() {
 		
 		//Show cycle editing  pop-ap 
 		$("#cycle").click(function(e) {
 		
	 	 	    e.preventDefault();// prevent the default anchor functionality
	 	 	    e.stopPropagation();
	 	 	    $("#adjustBalance").css('width','324px');
	 	 	    $("#editecycle").modal("show");
		});
 		
 		$.fn.editable.defaults.mode = 'inline';

 		$('.editableField').editable({   
 	    	  emptytext: '? ?',
 	    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
 	    	  mode: 'popup',
 	    	  type: 'text',
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
 	 		  success: function(response) {
 				location.reload();
 		      }
		});

 		 $('.editableFieldInline').editable({   
 	    	  emptytext: '? ?',
 	    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
 	    	  mode: 'inline',
 	    	  type: 'text',
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
 	 		  success: function(response) {
 				location.reload();
 		      }
 		});

 		$('.editableFieldArray').editable({   
 		    	  emptytext: '? ?',
 		    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
 		    	  mode: 'popup',
 		    	  type: 'text',
 		    	  url: '/ajax/resourceeditfieldarraysubmit',
 		    	  pk: '${resource.id}',
 		 		  success: function(response) {
 					location.reload();
 			      }
		});

 	 		
 		//Show cycle editing  pop-ap 


 	 		
 	    $('.nonurleditpop').popoverWithAutoHideForPrivilege("Pour modifier une url, il faut être connecté et avoir un niveau 4 de contribution.");

 	    $('.nonCompetenceLinkPop').popoverWithAutoHideForPrivilege("Pour lier (et délier) une compétence et une ressource, il faut être connecté et avoir un niveau 4 de contribution." );

 	    $('.nonimageeditpop').popoverWithAutoHideForPrivilege("Pour ajouter/retirer/modifier une image, il faut être connecté et avoir un niveau 3 de contribution." );

 	    $(".noAddProblemPop").popoverWithAutoHideForPrivilege("Pour signaler un problème, il faut être connecté.");
 	 	    
 	    $(".noneditresource").popoverWithAutoHideForPrivilege("Pour modifier ce champ, il faut être connecté et avoir un niveau 3 de contribution." );

 	    
 	   	$("#closeButton1, #closeButton2").click( function()
           {
	    var filename = $('#imageFileName').val(); 
	    var resourceid = $('#resourceid').val();
        $.ajax({
   		     url: "/resource/ajax/deleteTempImage",
   		     data : "imageFileName=" + filename + "&resourceid=" + resourceid,
   		 });

           }
        );

 		$("#idPrintScreen").click(function(){
            var idresource = $("#resourceHiddenField").val();
            
            $.ajax({
 		    	url:'/resource/ajax/checkClipBoard',
 		    	data: 'id='+idresource,
 		    		success: function(response){
 		    		url=response;
 		    		$("#imageFileName").val(response);
 		    		if(url != null && url != ''){
 	 		    		url = '/gen/resource/original/'+response;
 		    			$("#modalPrintScreen").modal("show");
 		    			$("#imageFromPrintScreenAndCrop").attr("src", url);
 		    			}
 		    		else{
		                location.reload();
 		    		};
 		   			},
            });
 		});
 		
 	$('#imageFromLink').click(function() { 
			$("#modalImageGalerieResourceFromUrl").modal("show");
	});
 	
 	
	$("#imageFromFile").on('click', function(e){
        e.preventDefault();
        $("#inputFile").trigger('click');
	});
	
	
	$('#inputFile[type=file]').change(function() {
		$('#formToSubmit').submit();
	});
	
	
 	$('#tutorialPrintScreen').click(function() { 
 		var resourceid = $('#resourceid').val();
 		window.location.replace("/resource/displayPrintScreenHelpText?resourceid=" + resourceid);
 	 });


	});   // End of Document Ready
 		
	
	
 	///////  Start JCrop functions
 	$(document).ready(function() {

		$('#modalPrintScreen').on('shown.bs.modal', function() { 
				jQuery('#imageFromPrintScreenAndCrop').Jcrop({
	 				onChange : updateCoords,
	 				onSelect : updateCoords,
					boxWidth:800,
					boxHeight:600
	 			});
		});
		
		function updateCoords(c) {
 			$('#x').val(c.x);
 			$('#y').val(c.y);
 			$('#w').val(c.w);
 			$('#h').val(c.h);
 	    };

 	    function checkCoords() {
 			if (parseInt($('#w').val())) return true;
 			return false;
 	    };
	

    });

//////  End's of Jcrop functions


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
/* Styles needed to have a larger text-area for the descrioption (placed by X-editable JavaScript).*/
#descriptionDiv textarea { /* description text area width */
	width: 250% !important;
}

#descriptionDiv .editable-buttons {
	margin-left: 70%;
	/* else, the 2 X-editable buttons (validate and cancel) are in front of the description field */
}
</STYLE>
<title>${resource.name}</title>
</head>
<body>

	<lrftag:breadcrumb linkactive="${resource.name}">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<c:if test="${user != null}">
			<lrftag:breadcrumbelement label="ressource"
				link="/ressourcelist/${user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	<div class="container">
		<div class="modal fade" id="editecycle" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" id="adjustBalance">
		
				<form id="cycles" role="form" method="post"<%-- action bound with javascript --%>>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="resetForm()">&times;</button>
							<h4 class="modal-title">Ajuster la balance selon les cycles appropriés</h4>
						</div>			
						<div class="modal-body">
							<div class="form-horizontal container">					
								<div class="form-group">
								
								
									<lrftag:cycleslider idSlider="resourcedisplayslider" minCycle="${mincycle}" maxCycle="${maxcycle}"/>
																	
								<div class="pull-right">
									<button type="button" class="btn btn-mini btn-primary" onclick="ajaxeditcycle()">Enregistrer</button>
								</div>
								</div>
		
							</div>
						</div>
					</div>
					<!-- /.modal-content -->
				</form>
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
								
		<div class="row">
			<div class="col-md-10">
				<a id="title"
					${canEdit==true ? " href='#' class='editableFieldInline'" : " class='noneditresource'"}><h1
						style="display: inline-block;">${resource.name}</h1></a> <a
					href="<c:url value='${resource.urlResources[0].url}'/>"> <span
					class="addToolTip glyphicon glyphicon-log-in"
					style="font-size: 16px; top: -11px;" data-toggle="tooltip"
					title="lien direct vers ce site"></span>
				</a>
			</div>
			<div class="col-md-2 text-right">
				<lrftag:rating id="${resource.id}" title="${resource.name}"
					scoreResource="${resource.avgRatingScore}"
					scoreUser="${mapRating[resource].score}"
					countRating="${resource.countRating}" canvote="${current.canVote}" />
				<div class="addthis_sharing_toolbox" style="display: inline-block; margin-right: 26px; vertical-align: super;"></div>
				<lrftag:favorite isFavorite="${isFavorite}"
					idResource="${resource.id}" />
			</div>
		</div>

		<div class="row">
			<div id="descriptionDiv" class="col-md-6">
				<a id="description"
					${canEdit==true ? " href='#' class='editableFieldInline'" : " class='noneditresource'"}
					data-type="textarea" data-inputclass="largerTextArea">${resource.description}</a>
			</div>
			<div class="col-md-6">
				<c:forEach items="${resource.urlResources}" var="urlResource">
					<div class="row">
						<c:choose>
							<c:when test="${oneUrlHasAName}">
								<%-- One extra column for the URL's name --%>
								<div class="col-md-3 text-right">
									<c:if test="${urlResource.name != null}">${urlResource.name}</c:if>
								</div>
								<div class="col-md-9">
							</c:when>
							<c:otherwise>
								<%-- Full width for the URLs --%>
								<div class="col-md-12">
							</c:otherwise>
						</c:choose>
						<a href="${urlResource.url}" target="_blank" id="urlresource"
							data-type="text">${urlResource.url}</a> <span
							style="float: none; font-size: 15px" title="Modifier cette URL"
							class="glyphicon glyphicon-pencil close addToolTip
							  <c:choose>
							        <c:when test="${canEditUrl}">
							          "
							onclick="onUrlEditClick(${urlResource.id},'${urlResource.url}','${urlResource.name}')">
							</c:when> <c:otherwise>
							          nonurleditpop">
							        </c:otherwise> </c:choose>
						</span>

						<button type="button" style="float: none;"
							title="Retirer cette URL"
							class="close addToolTip
						    <c:choose>
							        <c:when test="${canEditUrl}">
							            "
							style="float:none;" onclick="onUrlRemoveClick(${urlResource.id})">
							</c:when>
							<c:otherwise>
							            nonurleditpop"  
							        </c:otherwise>
							</c:choose>
							>&times;
						</button>
					</div>
			</div>
			<%-- end row --%>
			</c:forEach>
			<div class="row">
				<c:choose>
					<c:when test="${oneUrlHasAName}">
						<%-- One extra column for the URL's name --%>
						<div class="col-md-3"></div>
						<div class="col-md-9">
					</c:when>
					<c:otherwise>
						<%-- Full width for the URLs --%>
						<div class="col-md-12">
					</c:otherwise>
				</c:choose>
				<span
					class="glyphicon glyphicon-plus close addToolTip ${canEditUrl==false ? "
					nonurleditpop":""}"	 ${canEditUrl==true
					? "onclick='onUrlAddClick()'
					":""}  style="float: none; font-size: 15px"
					title="ajouter une url (certaines ressources ont plusieurs liens, par exemple l'un pour l'énoncé et l'autre pour la solution s'ils sont dans des documents différents; ou bien un lien principal vers la ressource et un lien vers une vidéo montrant l'utilisation de la ressource en classe)"></span>
			</div>
		</div>

		<br />
		<br />

		<c:if test="${listMyPlayListsWithThisResource != null}">
					Mes séquences contenant cette ressource:<br />
			<c:forEach items="${listMyPlayListsWithThisResource}" var="playlist">
				<lrftag:playlist playlist="${playlist}" />
			</c:forEach>
			<br />
		</c:if>
		<c:if test="${listMyPlayListWithoutThisResource != null}">
			<a id="addToPlayList" href='#' class='editableField'
				data-type='select'
				data-source="${listMyPlayListWithoutThisResource}">Ajouter à une
				de mes séquences</a>
			<br />
		</c:if>

		<lrf:conditionDisplay privilege="MANAGE_PLAYLIST">
			<a id="addToOtherPlayList" href='#' class='editableField'
				data-type='text' data-title="Entrez l'id court de la séquence"
				data-value="">Ajouter à une séquence d'un autre utilisateur</a>
			<br />
		</lrf:conditionDisplay>

		<c:if test="${listOtherPeoplePlayListsWithThisResource != null}">
					Séquences d'autres utilisateurs contenant cette ressource:<br />
			<c:forEach items="${listOtherPeoplePlayListsWithThisResource}"
				var="playlist">
				<lrftag:playlist playlist="${playlist}" />
			</c:forEach>
		</c:if>
		<br /> <br />

	</div>
	</div>
	<%-- end row --%>


	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-md-3">
							<a id="format"
								${canEdit==true? "href='#' class='editableField' data-type='select' data-emptytext='?format?'":" class='noneditresource'"}
								data-source="${dataEnumFormat}">${resource.format.description}</a>
						</div>
						<div class="col-md-3">
							<a id="nature"
								${canEdit==true? "href='#' class='editableField' data-type='select' data-emptytext='?nature?'": " class='noneditresource'"}
								data-source="${dataEnumNature}">${resource.nature.description}</a>
						</div>
						<div class="col-md-3">
							<a id="topic"
								${canEdit==true ? " href='#' class='editableField' data-type='select' data-emptytext='?mati�re?'": " class='noneditresource'"}
								data-source="${dataEnumTopic}">${resource.topic.description}</a>
						</div>
						<div class="col-md-3">

							<a id="platform"
								${canEdit==true? "href='#' class='editableFieldArray'  data-type='checklist'  data-emptytext='?plate-forme?'":" class='noneditresource'"}
								data-source="${dataEnumPlatform}"
								data-value="${platformsForJson}"> <c:forEach
									items="${resource.platforms}" var="platform">
								 	${platform.name}
								 </c:forEach>
							</a>

						</div>
					</div>
					<%-- end row --%>
					<div class="row">
						<div class="col-md-3">
							<a id="duration"
								${canEdit==true? "href='#' class='editableField' data-type='text'":" class='noneditresource'"}>
								${resource.duration}</a> minutes
						</div>
						<div class="col-md-3">
							<a id="language"
								${canEdit==true ? "href='#' class='editableField' data-type='select' data-emptytext='?langue?'":" class='noneditresource'"}
								data-source="${dataEnumLanguage}">
								${resource.language.description}</a>
						</div>
						<div class="col-md-3">
						    
   
			   <a  ${canEdit==true? "href='#' id='cycle'":" class='noneditresource'"} >
			   
			   	 <c:choose>
   					 <c:when test="${resource.minCycle == null}">? &#8594; ?</c:when>
    				<c:otherwise>${resource.minCycle.name} &#8594; ${resource.maxCycle.name}</c:otherwise>
				</c:choose>
			   
			   </a>
						
				
						</div>
						<div class="col-md-3">
							<a id="advertising"
								${canEdit==true ? "href='#' class='editableField' data-type='select' data-emptytext='?publicité?'":" class='noneditresource'"}
								data-source="[{value:'false',text:'Non'},{value:'true',text:'Oui'}]">
								<c:if test="${resource.advertising == true}">
		    							pub
									</c:if> <c:if test="${resource.advertising == false}">
		    							sans pub
									</c:if>
							</a>
						</div>
					</div>
					<%-- end row --%>
					<div class="row">
						<div class="col-md-3">
							<a href="/user/${resource.createdBy.userName}" class="addToolTip"
								title="contributeur">${resource.createdBy.fullName}</a>
						</div>
						<div class="col-md-3 col-md-offset-9">
							<a id="author"
								${canEdit==true ? "href='#' class='editableField' data-emptytext='?auteur?'":" class='noneditresource'"}>
								${resource.author}</a>
						</div>
					</div>
					<%-- end row --%>

					<c:if test="${canValidate==true}">
						<div class="row">
							<div class="col-md-3">

								<a id="validate"
									title="
									<c:choose>
									<c:when test="${resource.validationStatus!=null}">
										<c:if test="${resource.validator!=null}">
					   	        		  par ${resource.validator.userName}
								        </c:if>
										<c:if test="${resource.validationDate!=null}">
											<lrf:datedisplay date="${resource.validationDate}"	duration="true" withspan="false" />
										</c:if>
									</c:when>
									<c:otherwise>
										Cette ressource peut-elle être montrée à des enfants?
									</c:otherwise>
								    </c:choose>"
									${canValidate==true ? 
								"
									href='#' 
									class='editableField addToolTip'
									data-type='select' 
									data-emptytext='?non validée?' 
								": " 
									class='noneditresource addToolTip'
							"}
									data-source="${dataEnumValidationStatus}">
									${resource.validationStatus.description} </a>


							</div>

						</div>
					</c:if>
					<%-- end row --%>


				</div>
				<%-- end panel body --%>
			</div>
			<%-- end panel --%>
		</div>
	</div>
	<%-- end row --%>


	<c:forEach items="${resource.competences}" var="competence">
		<lrf:competencepath competence="${competence}" />
		<button type="button" style="float: none;"
			class="close
	           <c:choose>
			        <c:when test="${canLinkToCompetence}">
			          "
			onclick="onCompetenceRemoveClick(${competence.id})">
			</c:when>
			<c:otherwise>
			          nonCompetenceLinkPop">
			        </c:otherwise>
			</c:choose>
			&times;
		</button>
		<br />
	</c:forEach>

	<c:if test="${empty resource.competences}">aucune compétence liée</c:if>
	<span
		class='glyphicon glyphicon-plus close addToolTip  ${canLinkToCompetence==false ? "nonCompetenceLinkPop'" : "' onclick='onAddCompetenceClick()'"} 
		style="float: none; font-size: 15px" title="Ajouter une compétence"></span>

	<c:if test="${not empty youtubeVideoId}">
		<%-- This resource's first URL has been detected as being a youtube url => we embed the video in the page (it's better for SEO to not have people systematically leave our site) --%>
		<style type="text/css">
<%--
to have a responsive layout - See more at: http: //avexdesigns.com
	/responsive-youtube-embed/#sthash.fkIODW9M.dpuf   --%> .video-container
	{
	position: relative;
	padding-bottom: 56.25%;
	padding-top: 30px;
	height: 0;
	overflow: hidden;
}

.video-container iframe,.video-container object,.video-container embed {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}
</style>
		<div class="video-container">
			<iframe width="420" height="315"
				src="//www.youtube.com/embed/${youtubeVideoId}?rel=0"
				frameborder="0" allowfullscreen></iframe>
		</div>
	</c:if>
	<br />
	<br />

	<div title="Ajouter une image" class="dropdown">
		<h4 style="display: inline-block">Images &nbsp; &nbsp; &nbsp;</h4>
		
		<span class='glyphicon glyphicon-plus close addToolTip ${canEdit==true ? "' href='#' data-toggle='dropdown'": "nonimageeditpop'"} data-original-title="" title="Ajouter une image" style="font-size: 15px; display: inline-block; float: none;"></span>
			<ul class="dropdown-menu">
				<li><a href="#" id="imageFromFile">Depuis un fichier sur votre ordinateur</a></li>
				<li><a href="#" id="imageFromLink">Depuis un lien (url) vers une image</a></li>
				<li><a href="#" id="idPrintScreen">Depuis une image du presse papier</a></li>
				<li><a href="#" id="tutorialPrintScreen">Comment faire une capture d'écran ?</a></li>
			</ul>
			
			<form method="post" action="/resource/imageadd"	enctype="multipart/form-data" id="formToSubmit">
				<input type="file" id="inputFile" name="file" style="display: none;"/>
				<input type="hidden" name="idResource" value="${resource.id}" />
			</form>
	</div>

	<%@ include file="resourceimagegallery.jsp"%>

	<br />
	<br />


	<h4>
		Problèmes &nbsp; &nbsp; &nbsp; <a
			class='glyphicon glyphicon-exclamation-sign addToolTip ${canAddProblem ? "'
			href='#modalProblemReport' data-toggle='modal'
			" : " noAddProblemPop'"} 
			  style="cursor: pointer; text-decoration: initial; line-height: 20px; font-size: 25px; color: #CCC"
			title="Signaler un problème pour cette ressource..."></a>
	</h4>
	<lrftag:problemreport title="${resource.name}"
		resourceid="${resource.id}" />
	<%@ include file="problemlist.jsp"%>

	<br />
	<br />




	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>
	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>
	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>
	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>
	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>
	<%-- MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS   MODALS  --%>

	<!-- Modal : ADD URL -->
	<div class="modal fade" id="modalUrlResource" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close closeModal" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une URL</h4>
				</div>
				<form method="post" action="/urlresourceeditsubmit"
					enctype="multipart/form-data"
					class="form-horizontal formUrlResource">
					<div class="modal-body">

						<div class="form-group">
							<label class="col-lg-2 control-label">Intitulé
								(optionnel): </label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="nameField"
									name="name" /> <span class="help-block">indication de
									la nature de cette url supplémentaire.<br /> ex: "solutions",
									"vidéo de cette activité en classe", "version éditable", etc.
								</span>
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-2 control-label">Url : </label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="urlField" name="url"
									placeholder="http://..." required />
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<input type="hidden" name="resourceid" value="${resource.id}" />
						<%-- used for create --%>
						<input id="urlResourceIdField" type="hidden" name="urlresourceid" />
						<%-- Value set via JavaScript -- used for edit --%>
						<button type="button" class="btn btn-default closeModal"
							data-dismiss="modal">Annuler</button>
						<input class="btn btn-primary" type="submit" value="Enregistrer" />
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- Modal : ADD IMAGE FROM URL -->
	<div class="modal fade" id="modalImageGalerieResourceFromUrl" tabindex="-1"
		role="dialog" aria-labelledby="Ajouter une image" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close closeModal" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une image à la galerie</h4>
				</div>
				
				
				<form method="post" action="/resource/imageaddUrl"	enctype="multipart/form-data" class="form-horizontal formUrlGallery">
					<div class="modal-body">
						<div class="form-group">
							<label class="col-lg-4 control-label" style="text-align: left;">Depuis un lien</label>
							<div class="col-lg-8">
								<input type="text" name="strUrl" placeholder="http://..."	class="inputSource form-control" id="inputUrl" />
							</div>
						</div>
						<p id="response">${response}</p>
					</div>
					<div class="modal-footer">
						<input type="hidden" name="idResource" id="idResource" value="${resource.id}" />
						<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
						<button type="submit" name="btnPicture"	class="btn btn-primary closeModal btnSubmit">Sauver	l'image</button>
					</div>
				</form>
				
				
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- Modal : URL REMOVE CONFIRM  -->
	<div class="modal fade" id="modalConfirmDeleteResource">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Confirmation</h4>
				</div>
				<div class="modal-body">
					<p>Voulez-vous supprimer cette URL?</p>
				</div>
				<div class="modal-footer">
					<form action="/removeurlresource">
						<input id="urlResourceHiddenField" type="hidden"
							name="urlresourceid" value="" />
						<%-- Filled by JavaScript --%>
						<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
						<button type="submit" class="btn btn-primary">Supprimer</button>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- Modal : ADD COMPETENCE -->
	<div class="modal fade" id="modalCompetence" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close closeModal" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Placer la ressource dans une
						compétence</h4>
				</div>
				<form method="post" action="/competenceaddtoresourcesubmit"
					role="form">
					<div class="modal-body">
						<div class="form-group">
							<row>
							<div class="col-lg-4">
								<label for="codeField">Code:</label> <input type="text"
									class="form-control" id="codeField" name="code" />
							</div>
							</row>
							<br />
							<br />
							<br />
							<div class="help-block">
								Code de la compétence dans laquelle vous désirez placer la
								ressource.<br /> Astuce: affichez la liste des compétences dans
								un autre onglet de votre navigateur.
							</div>
						</div>
					</div>

					<div class="modal-footer">
						<input type="hidden" name="resourceid" value="${resource.id}" />
						<button type="button" class="btn btn-default closeModal"
							data-dismiss="modal">Annuler</button>
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
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Confirmation</h4>
				</div>
				<div class="modal-body">
					<p>Voulez-vous retirer cette compétence de la ressource ?</p>
				</div>
				<div class="modal-footer">
					<form action="/removecompetencefromresource">
						<input id="competenceHiddenField" type="hidden"
							name="competenceid" />
						<%-- Filled by JavaScript --%>
						<input id="resourceHiddenField" type="hidden" name="resourceid"
							value="${resource.id}" />
						<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
						<button type="submit" class="btn btn-primary">Retirer</button>
					</form>
				</div>
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
		<!-- Modal : ADD URL -->
		<div class="modal fade" id="modalPrintScreen" >
			<div class="modal-dialog" style="width: 845px; height: 550px">
				<div class="modal-content">
					<div class="modal-header" style="padding-bottom: 0px; padding-top: 10px;">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="closeButton1">&times;</button>
						<h4 class="modal-title">Votre image</h4>
					</div>
					<div class="modal-body" style="padding: 10px">
					
					<p>Vous pouvez sélectionner une zone dans l'image avec la souris.</p>
					<div >
						<img id="imageFromPrintScreenAndCrop" name="img"/>
					
					</div>
					
					<div class="modal-footer" style="padding-top: 0px; padding-bottom: 0px">
					
					<form action="/resource/ajax/imageaddPrintScreen" method="get" id="submitCoordsToCrop" onsubmit="return checkCoords();">
							<input type="hidden" id="x" name="xCoord" />
							<input type="hidden" id="y"	name="yCoord" />
							<input type="hidden" id="w" name="wCoord" />
							<input type="hidden" id="h" name="hCoord" />
							<input type="hidden" id="imageFileName" value="" name="imageFileName" />
							<input type="hidden" id="resourceid" name="resourceid" value="${resource.id}" />
							<button type="button" class="btn btn-default" data-dismiss="modal" id="closeButton2">Fermer</button>
							<button type="submit" class="btn btn-primary" >Sauver l'image</button>
						</form>
					</div>
				</div>	
				</div>
			</div>
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