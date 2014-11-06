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

<script type="text/javascript" src="/js/int/addResourceFavorite.js"></script>
<script type="text/javascript" src="/js/ext/jquery.popconfirm.js"></script>
<script src="/js/int/bootstrapTourCustom.js"></script>
<script type="text/javascript">

	 // create youtube player
	 var player;
	 function onYouTubePlayerAPIReady() {
	     player = new YT.Player('videoyoutube', {
	       height: '315',
	       width: '420',
	       videoId: '${youtubeVideoId}',
	       events: {
	
	         'onStateChange': onPlayerStateChange
	       }
	     });
	 }

	 var countHasBeenInc=true;
	 // when video ends
	 function onPlayerStateChange(event) {    
	
	     if(YT.PlayerState.PLAYING) {  
	         if(countHasBeenInc==true){
	     		var idresource = $("#resourceHiddenField").val();
	        	 //increment value in db whith ajax
	             updateViewcountAndPpularity(idresource);
	             countHasBeenInc=false;
	         }                     
	       
	     }
	 }

	function ajaxeditcycle() {
		
		
		var resourceID=$("#resourceHiddenField").val();
		var mincycle = $("#resourcedisplayslidervaluemin").val();
		var maxcycle = $("#resourcedisplayslidervaluemax").val();	
		
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

	// Clipboard paste image - START 
	// http://mobiarch.wordpress.com/2013/09/25/upload-image-by-copy-and-paste/
	document.addEventListener("DOMContentLoaded", function(){
		var pasteTarget = document.getElementById("pasteTarget");
		pasteTarget.addEventListener("paste", handlePaste);
	});
	
	function handlePaste(e) {
		var idresource = $("#resourceHiddenField").val();
	    for (var i = 0 ; i < e.clipboardData.items.length ; i++) {
	        var item = e.clipboardData.items[i];
	        console.log("Item type: " + item.type);
	        if (item.type.indexOf("image") != -1) {
	        	uploadFile(item.getAsFile());
	        } else {
				$('#printScreenNotFound').html("Veuillez d'abord effectuer une capture d'écran (il n'y a pas d'image dans le presse-papier)."); 
	        }
	    }
	}
	function uploadFile(file) {
	    var xhr = new XMLHttpRequest();
	    xhr.open("POST", "/resource/ajax/resourceImageClipBoardBeforeCrop?resourceid= " + $('#resourceid').val(), false);
	    xhr.onreadystatechange=function() {
	        url=xhr.responseText;
	 		$("#imageFileName").val(xhr.responseText);
	   		if(url != null && url != ''){
	    		url = '/gen/resource/original/' + xhr.responseText;  // Url of the temp image (to display for cropping)
	   			$("#imageFromPrintScreenAndCrop").attr("src", url);
	   			$("#waitingModalForPrintScreen").modal("hide");
	   			$("#modalPrintScreen").modal("show");
   			} else {
              location.reload();
	   		};

	    }
	    xhr.setRequestHeader("Content-Type", file.type);
	    xhr.send(file);
	}
	function resetFormPrintScreenNotFound() {
		$('#printScreenNotFound').html('');
	};
	// Clipboard paste image - END
	
 	$(document).ready(function() {
 		
 		//Why do you erase thes lines?
 		//Show cycle editing  pop-ap 
 		$('.editableField').editable({   
 	    	  emptytext: '? ?',
 	    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
 	    	  mode: 'popup',
 	    	  type: 'text',
 	    	  url: '/ajax/resourceeditfieldsubmit',
 	    	  pk: '${resource.id}',
 	 		  success: function(response) {
 				// Not worth reloading all the page. location.reload();
 		      }
		});
 		//Why do you erase thes lines? :: end
 		
 		// "Turn on/off" editable fields
			
		$('#turnInfoPlusEditableField').click(function(){
			$('#turnInfoPlusEditableField').hide();
			$('#editingSpan').show();
			$('#closeInfoPlusEditableField').show();
			$('#infoPlusDiv .noteditableField').each(function(){
				$(this).wrap("<a href='#'></a>");
				$(this).removeClass('noteditableField').addClass('editableField');
				$(this).editable({  
			    	  emptytext: '? ?',
			    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
			    	  mode: 'popup',
			    	  type: 'text',
			    	  url: '/ajax/resourceeditfieldsubmit',
			    	  pk: '${resource.id}',
			 		  success: function(response) {
				      }
				});
	 			$(this).effect("highlight", 2000);
			});
			$('#infoPlusDiv .noteditableFieldArray').each(function(){
				$(this).wrap("<a href='#'></a>");
				$(this).removeClass('noteditableFieldArray').addClass('editableFieldArray');
				$(this).editable({   
	 		    	  emptytext: '? ?',
	 		    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
	 		    	  mode: 'popup',
	 		    	  type: 'text',
	 		    	  url: '/ajax/resourceeditfieldarraysubmit',
	 		    	  pk: '${resource.id}',
	 		 		  success: function(response) {
	 			      }
				});
	 			$(this).effect("highlight", 2000);
			});
			
			// To make the text green as the other elements
			$("#cycle").wrap("<a href='#'></a>"); 
			$("#idContributor").wrap("<a href='/user/${resource.createdBy.userName}' target='_blank'></a>");
			

			
			//Show cycle editing  pop-ap 
	 		$("#cycle").click(function(e) {
		 	 	    e.preventDefault();// prevent the default anchor functionality
		 	 	    e.stopPropagation();
		 	 	    $("#adjustBalance").css('width','324px');
		 	 	    $("#editecycle").modal("show");
			});
		});
		

		$('#turnTitleAndDescriptionEditableField').click(function(){
			$('#closeTitleAndDescriptionEditableField').show();
			$('#turnTitleAndDescriptionEditableField').hide();
			$('.noteditableFieldInline').each(function(){
				$(this).wrap("<a href='#'></a>");
				$(this).removeClass('noteditableFieldInline').addClass('editableFieldInline');
				$(this).editable({   
		 	    	  emptytext: '? ?',
		 	    	  send: 'always',  // http://stackoverflow.com/a/20661423/174831
		 	    	  mode: 'inline',
		 	    	  type: 'text',
		 	    	  url: '/ajax/resourceeditfieldsubmit',
		 	    	  pk: '${resource.id}',
		 	 		  success: function(response) {
		 		      }
		 		});
				$(this).effect("highlight", 2000);
			});
		});
		
 		
 		$.fn.editable.defaults.mode = 'inline';
 		
 		
		// end "turn on/off" editable fields


 	 		
 		//Show cycle editing  pop-ap 


 	 		
 	    $('.nonurleditpop').popoverWithAutoHideForPrivilege("Pour modifier une url, il faut être connecté et avoir un niveau 4 de contribution.");

 	    $('.nonCompetenceLinkPop').popoverWithAutoHideForPrivilege("Pour lier (et délier) une catégorie et une ressource, il faut être connecté et avoir un niveau 4 de contribution." );

 	    $('.nonimageeditpop').popoverWithAutoHideForPrivilege("Pour ajouter/retirer/modifier une image, il faut être connecté et avoir un niveau 3 de contribution." );

 	    $(".noAddProblemPop").popoverWithAutoHideForPrivilege("Pour signaler un problème, il faut être connecté.");
 	 	    
 	    $(".noneditresource").popoverWithAutoHideForPrivilege("Pour modifier ce champ, il faut être connecté et avoir un niveau 3 de contribution." );

 	    $(".noneditresources").popoverWithAutoHideForPrivilege("Pour modifier ces champs, il faut être connecté et avoir un niveau 3 de contribution." );
 	    
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
 	   	
 	$('#idPrintScreen').click(function() { 
		$("#waitingModalForPrintScreen").modal("show");
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
 		
		$("[data-toggle='confirmation']").popConfirm({
			 title : "Confirmation",
			 content : "Voulez-vous vraiment supprimer cette ressource ?",
			 placement : "bottom",
			 yesBtn : "oui",
			 noBtn : "non"
		 });
		
 		$('#modalPrintScreen').on('shown.bs.modal', function() { 
			jQuery('#imageFromPrintScreenAndCrop').Jcrop({
 				onChange : updateCoords,
 				onSelect : updateCoords,
 				boxWidth:800,
				boxHeight:600,
 		        setSelect: [($('#imageFromPrintScreenAndCrop').width()/20),
 		                    ($('#imageFromPrintScreenAndCrop').height()/20),
 		                    ($('#imageFromPrintScreenAndCrop').width() - $('#imageFromPrintScreenAndCrop').width()/20),
 		                    ($('#imageFromPrintScreenAndCrop').height() - $('#imageFromPrintScreenAndCrop').height()/20)]
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
 	
	
 	
</script>
<script src="http://www.youtube.com/player_api"></script>

<STYLE type="text/css">
/* Styles needed to have a larger text-area for the descrioption (placed by X-editable JavaScript).*/
#descriptionDiv span.editable-inline,
#descriptionDiv span.editable-inline form .form-group,
#descriptionDiv span.editable-inline form .form-group .editable-input,
#descriptionDiv span.editable-inline form .form-group .editable-input textarea
{ /* description text area width */
	width: 100% !important;
}


#descriptionDiv span.editable-inline form .form-group div
{
	display: block !important;	
}
</STYLE>
<title>${resource.name}</title>
<meta name="title" content="${resource.name}" />
<meta name="description" content="<c:choose><c:when test="${empty resource.description}"><c:out value="${resource.name}"/></c:when><c:otherwise><c:out value="${resource.description}"/></c:otherwise></c:choose>"/>
<meta name="keywords" content="${resource.name}, ressource, éducatif, vidéo, exercice en ligne, aide aux devoirs, leçon éducative" />
<c:if test="${resource.numberImage >= 1}">
<meta name="thumbnail" content="/gen/resource/resized/small/${resource.id}-1.jpg" />
</c:if>
</head>
<body>

	<lrftag:breadcrumb linkactive="${resource.name}">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<c:if test="${user != null}">
			<lrftag:breadcrumbelement label="ressource"
				link="/ressourcelist/${user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	<div class="container" id="pasteTarget" itemscope itemtype="http://schema.org/CreativeWork">
	<meta itemprop="isFamilyFriendly" content="true" />
	<span itemprop="audience" itemscope itemtype="http://schema.org/EducationalAudience">
		<meta itemprop="educationalRole" content="student" />
		<meta itemprop="educationalRole" content="parent" />
		<meta itemprop="educationalRole" content="teacher" />
	</span>
		<div class="modal fade" id="editecycle" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" id="adjustBalance">
				<form id="cycles" role="form" method="post"<%-- action bound with javascript --%>>
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="resetForm()">&times;</button>
							<h4 class="modal-title">Cycles pertinents pour la ressource</h4>
						</div>			
						<div class="modal-body">
							<div class="form-horizontal">					
								<div class="form-group">
									<lrftag:cycleslider idSlider="resourcedisplayslider"
										minCycle="${mincycle}" maxCycle="${maxcycle}" />
									<div class="pull-right">
										<button type="button" class="btn btn-mini btn-primary"
											onclick="ajaxeditcycle()">Enregistrer</button>
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
			<div id="titleDivForMakeEditable" class="col-md-7" style="padding-left: 0px">
				<span id="title" class='noteditableFieldInline'}><h1
				itemprop="name"	style="display: inline-block;">${resource.name}</h1></a> <a
					href="<c:url value='${resource.urlResources[0].url}'/>" target="_blank"> <span
					id="tourStepLink"
					class="addToolTip glyphicon glyphicon-log-in"
					style="font-size: 16px; top: -11px;" data-toggle="tooltip"
					title="lien direct vers ce site"></span>
				</span>
			    <lrf:conditionDisplay privilege="MANAGE_RESOURCE">
          		    <a href="<c:url value='/resource/delete?idresource=${resource.id}'/>" class="btn" data-toggle='confirmation'>
						<button type="button" class="btn addToolTip close" title="supprimer cette ressource">&times;</button>
				    </a>
				    <span id="turnTitleAndDescriptionEditableField" ${canEdit==true? "class='glyphicon glyphicon-pencil addToolTip close' ":" class='noneditresource'"} style="float: none; font-size: 15px" title="Modifier le titre ou la description"></span>
				    <span id="closeTitleAndDescriptionEditableField" class="glyphicon glyphicon-remove addToolTip close" style="display:none; float: none; font-size: 15px" title="Accepter les changements du titre et de la description" onclick="location.reload()"></span>
      		    </lrf:conditionDisplay>
			</div>
			<div class="col-md-5">
		</div>
		<div class="row">
			<div class="col-md-8">
				<div id="rateAndShareDiv" class="row">
					<div class="col-md-6" itemProp="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">
						<div class="col-md-4" id="tourStepVote">
						<lrftag:rating id="${resource.id}" title="${resource.name}"
							scoreResource="${resource.avgRatingScore}"
							scoreUser="${mapRating[resource].score}"
							countRating="${resource.countRating}" canvote="${current.canVote}" />
						</div>
							<meta itemprop="ratingValue" content="${resource.avgRatingScore}" />
                            <meta itemprop="ratingCount" content="${resource.countRating}" />
                            <meta itemprop="bestRating" content="5" />
                            <meta itemprop="worstRating" content="1" />
					</div>
					<div class="col-md-6">
						<div class="addthis_sharing_toolbox"
							style="display: inline-block; margin-right: 26px; vertical-align: super;"></div>
						<lrftag:favorite isFavorite="${isFavorite}"	idResource="${resource.id}" />
					</div>
				</div>
				<div id="urlsDiv">					
					<c:forEach items="${resource.urlResources}" var="urlResource">
						<div class="row">
							<div class="col-md-12">
							<c:if test="${oneUrlHasAName && urlResource.name != null}">${urlResource.name} : </c:if>
							<a itemprop="url" href="${urlResource.url}" onclick="updateViewcountAndPpularity(${resource.id});"
								target="_blank" id="urlresource" data-type="text">${urlResource.url}</a>
							<span style="float: none; font-size: 15px"
								title="Modifier cette URL"
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
								            nonurleditpop"> 
								        </c:otherwise>
								</c:choose>
								&times;
							</button>
							<span id="viewCounter${resource.id}" class="addToolTip glyphicon glyphicon-eye-open" itemprop="interactionCount" style="font-size: 12px; padding: 0px; margin-left: 5px" data-toggle="tooltip" title="" data-original-title="Nombre de vues"> ${resource.viewCount}</span>
							</div>
						</div>
						<%-- end row --%>
					</c:forEach>				
					<div class="row">
						<div class="col-md-12">
							<span
								class="glyphicon glyphicon-plus close addToolTip ${canEditUrl==false ? "
								nonurleditpop":""}"	 ${canEditUrl==true
								? "onclick='onUrlAddClick()'
								":""}  style="float: none; font-size: 15px"
								title="ajouter une url (certaines ressources ont plusieurs liens, par exemple l'un pour l'énoncé et l'autre pour la solution s'ils sont dans des documents différents; ou bien un lien principal vers la ressource et un lien vers une vidéo montrant l'utilisation de la ressource en classe)"></span>
						</div>
					</div>
				</div>					
				<div class="row">
					<div itemprop="description" id="descriptionDiv" class="col-md-6">
						<span id="description" class='noteditableFieldInline'}
							data-type="textarea" data-inputclass="largerTextArea">${resource.description}</span>
					</div>			
					
					<div id="infoPlusDiv" class="col-md-6">
						
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12">
										<c:choose>
										    <c:when test="${canEdit}">
												<span id="editingSpan" class="close" style="display:none; float: left; font-size: 15px"><i>...MODIFICATION...</i></span>
												<span id="turnInfoPlusEditableField" class='glyphicon glyphicon-pencil addToolTip close' style="float: right; font-size: 15px" title="Modifier l'info"></span>
												<span id="closeInfoPlusEditableField" class="glyphicon glyphicon-remove close" style="display:none; float: right; font-size: 15px" title="Fermer l'édition" onclick="location.reload()"></span>
											</c:when> 
											<c:otherwise>
												<span class='glyphicon glyphicon-pencil addToolTip close noneditresources' style="float: right; font-size: 15px" title="Modifier l'info"></span>
								    	    </c:otherwise> 
										</c:choose>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<span class="text-muted">format :</span>
<!-- 										<a id="format" -->
<%-- 											${canEdit==true? "href='#' class='editableField' data-type='select' data-emptytext='?format?'":" class='noneditresource'"} --%>
<%-- 											data-source="${dataEnumFormat}">${resource.format.description}</a> --%>
										<span id="format" class='noteditableField' data-type='select' data-emptytext='?format?'	data-source="${dataEnumFormat}">${resource.format.description}</span>
											<!-- Begin Micro-data -->
											<c:choose>
											    <c:when test="${resource.format.description == 'Interactif'}"><c:set var="formatCode" value="active"/><c:set var="educationalUse1" value="Interactive"/></c:when>
											    <c:when test="${resource.format.description == 'Vidéo'}"><c:set var="formatCode" value="expositive"/><c:set var="learningResourceType1" value="Handout"/>
											    <c:set var="genreCode" value="Video"/></c:when>
											    <c:when test="${resource.format.description == 'Audio'}"><c:set var="formatCode" value="expositive"/><c:set var="learningResourceType1" value="Handout"/>
											    <c:set var="genreCode" value="Audio"/></c:when>
											    <c:when test="${resource.format.description == 'Document'}"><c:set var="formatCode" value="mixed"/>
											    <c:set var="genreCode" value="Text"/></c:when>
											    <c:otherwise><c:set var="formatCode" value="mixed"/></c:otherwise>
											</c:choose>
											<meta itemprop="interactivityType" content="${formatCode}" />	
											<c:if test="${not empty educationalUse1}"><meta itemprop="educationalUse" content="${educationalUse1}" /></c:if>
											<c:if test="${not empty learningResourceType1}"><meta itemprop="learningResourceType" content="${learningResourceType1}" /></c:if>
											<c:if test="${not empty genreCode}"><meta itemprop="genre" content="${genreCode}" /></c:if>
											<!--  End Micro-data -->
									</div>
									<div class="col-md-6">
										<span class="text-muted">nature :</span>
										<span id="nature" class='noteditableField' data-type='select' data-emptytext='?nature?'	data-source="${dataEnumNature}">${resource.nature.description}</span>
										<!-- Begin Micro-data -->	
										<c:choose>
										    <c:when test="${resource.nature.description == 'Formatif (jeu/exploration)'}"><c:set var="educationalUse2" value="Interactive"/><c:set var="learningResourceType2" value="Game"/></c:when>
										    <c:when test="${resource.nature.description == 'Formatif (théorie)'}"><c:set var="educationalUse2" value="Theory"/><c:set var="learningResourceType2" value="Handout"/></c:when>
										    <c:when test="${resource.nature.description == 'Evaluatif sans correction'}"><c:set var="educationalUse2" value="Test"/><c:set var="educationalUse3" value="Homework"/>
										    <c:set var="learningResourceType2" value="Test"/></c:when>
										    <c:when test="${resource.nature.description == 'Evaluatif avec correction'}"><c:set var="educationalUse2" value="Assessment"/><c:set var="learningResourceType2" value="Test"/></c:when>
										    <c:when test="${resource.nature.description == 'Formatif et évaluatif sans correction'}"><c:set var="educationalUse2" value="Interactive"/><c:set var="educationalUse3" value="Test"/>
										    <c:set var="educationalUse4" value="Homework"/><c:set var="learningResourceType2" value="Test"/></c:when>
										    <c:when test="${resource.nature.description == 'Formatif et évaluatif avec correction'}"><c:set var="educationalUse2" value="Interactive"/>
										    <c:set var="educationalUse3" value="Assessment"/><c:set var="learningResourceType2" value="Test"/></c:when>
										    <c:otherwise><c:set var="educationalUse2" value="Interactive"/><c:set var="learningResourceType2" value="Test"/><c:set var="learningResourceType3" value="Handout"/></c:otherwise>
										</c:choose>
										<c:if test="${not empty educationalUse2 && educationalUse2 != educationalUse1}"><meta itemprop="educationalUse" content="${educationalUse2}" /></c:if>
										<c:if test="${not empty educationalUse3}"><meta itemprop="educationalUse" content="${educationalUse3}" /></c:if>
										<c:if test="${not empty educationalUse4}"><meta itemprop="educationalUse" content="${educationalUse4}" /></c:if>
										<c:if test="${not empty learningResourceType1}"><meta itemprop="learningResourceType" content="${learningResourceType1}" /></c:if>
										<c:if test="${not empty learningResourceType2}"><meta itemprop="learningResourceType" content="${learningResourceType2}" /></c:if>
										<c:if test="${not empty learningResourceType3}"><meta itemprop="learningResourceType" content="${learningResourceType3}" /></c:if>
									    <!--  End Micro-data -->
									</div>
								</div>	
								<div class="row">
									<div class="col-md-6">										
										<span class="text-muted">matière : </span>
										<span id="topic" class='noteditableField' data-type='select' data-emptytext='?matière?' data-source="${dataEnumTopic}">${resource.topic.description}</span>
									</div>
									<div class="col-md-6">									
										<span class="text-muted">plateforme : </span> 
										<span id="platform" class='noteditableFieldArray'  data-type='checklist'  data-emptytext='?plate-forme?' data-source="${dataEnumPlatform}"
											data-value="${platformsForJson}"> <c:forEach
 												items="${resource.platforms}" var="platform">
											 	${platform.name}
 											 </c:forEach>
										</span>			
									</div>
								</div>
								<%-- end row --%>
								<div class="row">
									<div class="col-md-6">							
										<span class="text-muted">durée : </span>
										<span id="duration" class='noteditableField' data-type='text'>
											${resource.duration}</span> minutes
											<meta itemprop="timeRequired" content="PT${resource.duration}M" />
									</div>
									<div class="col-md-6">							
										<span class="text-muted">langue : </span> 
										<span id="language" class='noteditableField' data-type='select' data-emptytext='?langue?' data-source="${dataEnumLanguage}">
											${resource.language.description}</span>
											<!--  Begin Micro-data -->
											<c:choose>
											    <c:when test="${resource.language.description == 'Français'}"><c:set var="languageCode" value="fr"/></c:when>
											    <c:when test="${resource.language.description == 'Néerlandais'}"><c:set var="languageCode" value="nl"/></c:when>
											    <c:when test="${resource.language.description == 'Allemand'}"><c:set var="languageCode" value="de"/></c:when>
											    <c:when test="${resource.language.description == 'Anglais'}"><c:set var="languageCode" value="en"/></c:when>
											    <c:when test="${resource.language.description == 'Russe'}"><c:set var="languageCode" value="ru"/></c:when>
											    <c:otherwise><c:set var="languageCode" value="fr"/></c:otherwise>
											</c:choose>
											<meta itemprop="inLanguage" content="${languageCode}" />
											<!--  End Micro-data -->
									</div>
									
								</div>	
								<div class="row">
									<div class="col-md-6">							
										<span class="text-muted">cycle : </span> 
										<span id='cycle'>			
											<c:choose>
												<c:when test="${resource.minCycle == null}">? &#8594; ?</c:when>
												<c:otherwise>${resource.minCycle.name} &#8594; ${resource.maxCycle.name}</c:otherwise>
											</c:choose>			
										</span>
										<!--  Begin Micro-data -->
										<c:choose>
										    <c:when test="${resource.minCycle.name == 'P1-2'}"><c:set var="ageMin" value="6"/></c:when>
										    <c:when test="${resource.minCycle.name == 'P3-4'}"><c:set var="ageMin" value="8"/></c:when>
										    <c:when test="${resource.minCycle.name == 'P5-6'}"><c:set var="ageMin" value="10"/></c:when>
										    <c:when test="${resource.minCycle.name == 'S1-2'}"><c:set var="ageMin" value="12"/></c:when>
										    <c:when test="${resource.minCycle.name == 'S3-6'}"><c:set var="ageMin" value="15"/></c:when>
										    <c:otherwise><c:set var="ageMin" value="6"/></c:otherwise>
										</c:choose>
										<c:choose>
										    <c:when test="${resource.maxCycle.name == 'P1-2'}"><c:set var="ageMax" value="9"/></c:when>
										    <c:when test="${resource.maxCycle.name == 'P3-4'}"><c:set var="ageMax" value="11"/></c:when>
										    <c:when test="${resource.maxCycle.name == 'P5-6'}"><c:set var="ageMax" value="13"/></c:when>
										    <c:when test="${resource.maxCycle.name == 'S1-2'}"><c:set var="ageMax" value="16"/></c:when>
										    <c:when test="${resource.maxCycle.name == 'S3-6'}"><c:set var="ageMax" value="19"/></c:when>
										    <c:otherwise><c:set var="ageMax" value="18"/></c:otherwise>
										</c:choose>
										<meta itemprop="typicalAgeRange" content="${ageMin}-${ageMax}" />
										<!--  End Micro-data -->
									</div>
									<div class="col-md-6">						
										<span class="text-muted">publicité : </span> 
										<span id="advertising" class='noteditableField' data-type='select' data-emptytext='?publicité?'	data-source="[{value:'false',text:'Non'},{value:'true',text:'Oui'}]">
											<c:if test="${resource.advertising == true}">
					    							pub
												</c:if> <c:if test="${resource.advertising == false}">
					    							sans pub
												</c:if>
										</span>
									</div>
								</div>
								<%-- end row --%>
								<div class="row">
									<div class="col-md-6">					
										<span class="text-muted">contributeur: </span> 
										<span id="idContributor" class="addToolTip"
											title="contributeur">${resource.createdBy.fullName}</span>
										<meta itemprop="contributor" content="${resource.createdBy.fullName}"/>	
									</div>
									<div class="col-md-6">					
										<span class="text-muted">éditeur: </span> 
										<span id="author" class='noteditableField' data-emptytext='?auteur?'>
											${resource.author}</span>
										<meta itemprop="author" content="${resource.author}"/>		
									</div>
								</div>
								<%-- end row --%>
			
							</div>
								
								<%-- end row --%>
							<%-- end panel body --%>
							<div>
					
						
						</div>
						<%-- end panel --%>
						
						</div>	
						
					</div>
					<div>  <%-- Additional div for validation  --%>
						
										<span  class="text-muted">Validée: </span> 
										<span id="validate"	title="
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
											  </c:choose>"${canValidate==true ? "href='#' class='editableField addToolTip'	data-type='select' data-emptytext='?non validée?' 
											": "class='noneditresource addToolTip'"} data-source="${dataEnumValidationStatus}">
											  <c:choose>
											    <c:when test="${resource.validator==null}">
								   	        		 non validée 
											    </c:when>
											    <c:otherwise>
											       ${resource.validationStatus.description} 
											    </c:otherwise>
											  </c:choose>
										   </span>
						
    				</div>
				
				</div>	
						
				<c:if test="${not empty youtubeVideoId}">
					<%-- This resource's first URL has been detected as being a youtube url => we embed the video in the page (it's better for SEO to not have people systematically leave our site) --%>
					<%-- injected video youtube--%>
					<style type="text/css">
						<%--to have a responsive layout - See more at: http: //avexdesigns.com/responsive-youtube-embed/#sthash.fkIODW9M.dpuf   --%> 
							#video-container
							{
							position: relative;
							padding-bottom: 56.25%;
							padding-top: 30px;
							height: 0;
							overflow: hidden;
						}
						
						#video-container iframe,#video-container object,#video-container embed {
							position: absolute;
							top: 0;
							left: 0;
							width: 100%;
							height: 100%;
						}
					</style>	
					<div id="video-container" itemprop="video" itemscope="" itemtype="http://schema.org/VideoObject"><%-- container div for responsive layout  --%>
						<meta itemprop="name" content="${resource.name}"/>
						<meta itemprop="duration" content="PT${resource.duration}M"/>
						<div id="videoyoutube""> <%-- div transformed to iframe with javascript --%> 							
						</div>
					</div>	
				</c:if>
				
								
				<div id="competencesDiv" itemprop="educationalAlignment" itemscope itemtype="http://schema.org/AlignmentObject">	
						<meta itemprop="alignmentType" content="teaches"/>
						<meta itemprop="alignmentType" content="assesses"/>		
						<meta itemprop="alignmentType" content="educationalSubject"/>	
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
						<c:if test="${empty resource.competences}">aucune catégorie liée</c:if>
							<span
								class='glyphicon glyphicon-plus close addToolTip  ${canLinkToCompetence==false ? "nonCompetenceLinkPop'
								" : "' onclick='onAddCompetenceClick()'
								"} 
								style="float: none; font-size: 15px"
								title="Ajouter une catégorie"></span>
						
							<c:if test="${not empty youtubeVideoId}">
								<%-- This resource's first URL has been detected as being a youtube url => we embed the video in the page (it's better for SEO to not have people systematically leave our site) --%>
								<%-- injected video youtube--%>
						
								<style type="text/css">
						<%--to have a responsive layout - See more at: http: //avexdesigns.com
							/responsive-youtube-embed/#sthash.fkIODW9M.dpuf   --%> #video-container
							{
							position: relative;
							padding-bottom: 56.25%;
							padding-top: 30px;
							height: 0;
							overflow: hidden;
						}
						
						#video-container iframe,#video-container object,#video-container embed {
							position: absolute;
							top: 0;
							left: 0;
							width: 100%;
							height: 100%;
						}
						</style>			
						</c:if>		
				</div>				
				<div id="problemeDiv">
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
				</div>
			</div>
			<div class="col-md-4">
				<div id="imageDiv" class="row">			
					<div title="Ajouter une image" class="dropdown">
						<h4 style="display: inline-block">Images &nbsp; &nbsp; &nbsp;</h4>
				
						<span
							class='glyphicon glyphicon-plus close addToolTip ${canEdit==true ? "'
							href='#' data-toggle='dropdown'
							": "nonimageeditpop'"} data-original-title=""
							title="Ajouter une image"
							style="font-size: 15px; display: inline-block; float: none;"></span>
						<ul class="dropdown-menu">
							<li><a href="#" id="imageFromFile">Depuis un fichier sur
									votre ordinateur</a></li>
							<li><a href="#" id="imageFromLink">Depuis un lien (url) vers
									une image</a></li>
							<li><a href="#" id="idPrintScreen">Depuis une image du
									presse papier</a></li>
							<li><a href="#" id="tutorialPrintScreen">Comment faire une
									capture d'écran ?</a></li>
						</ul>
				
						<form method="post" action="/resource/imageadd"
							enctype="multipart/form-data" id="formToSubmit">
							<input type="file" id="inputFile" name="file" style="display: none;" />
							<input type="hidden" name="idResource" value="${resource.id}" />
						</form>
					</div>				
					<%@ include file="resourceimagegallery.jsp"%>				
				</div>
		
				<div id="sequenceDiv" class="row">
					<c:if test="${listMyPlayListsWithThisResource != null}">
								Mes séquences contenant cette ressource:<br />
						<c:forEach items="${listMyPlayListsWithThisResource}" var="playlist">
							<lrftag:playlist playlist="${playlist}" />
						</c:forEach>
						<br />
					</c:if>
					<c:if test="${canaddplaylist}">
						<c:if test="${listMyPlayListWithoutThisResource != null}">
							<a id="addToPlayList" href='#' class='editableField'
								data-type='select'
								data-source="${listMyPlayListWithoutThisResource}">Ajouter à une
								de mes séquences</a>
							<br />
						</c:if>
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
				</div>	
				
			</div>	
		</div>			
	</div>
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
	<div class="modal fade" id="modalImageGalerieResourceFromUrl"
		tabindex="-1" role="dialog" aria-labelledby="Ajouter une image"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close closeModal" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une image à la galerie</h4>
				</div>


				<form method="post" action="/resource/imageaddUrl"
					enctype="multipart/form-data"
					class="form-horizontal formUrlGallery">
					<div class="modal-body">
						<div class="form-group">
							<label class="col-lg-4 control-label" style="text-align: left;">Depuis
								un lien</label>
							<div class="col-lg-8">
								<input type="text" name="strUrl" placeholder="http://..."
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
					<h4 class="modal-title">Placer la ressource dans une catégorie</h4>
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
							<br /> <br /> <br />
							<div class="help-block">
								Code de la catégorie dans laquelle vous désirez placer la ressource.<br /> Astuce: affichez la <a href="http://toujoursplus.be/competencetree"  TARGET="_blank"> liste des catégories</a> dans
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
					<p>Voulez-vous retirer cette catégorie de la ressource ?</p>
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
	<div class="modal fade" id="modalPrintScreen">
		<div class="modal-dialog" style="width: 845px; height: 550px">
			<div class="modal-content">
				<div class="modal-header"
					style="padding-bottom: 0px; padding-top: 10px;">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" id="closeButton1">&times;</button>
					<h4 class="modal-title">Votre image</h4>
				</div>
				<div class="modal-body" style="padding: 10px">

					<p>Vous pouvez sélectionner une zone dans l'image avec la
						souris.</p>
					<div>
						<!-- The attribute src will be populated later when the clipboard image will be inserted 
						      as a (temporary) file on disk. See JS function uploadFile(), after call of the 
						      ResourceImageController. See line "$("#imageFromPrintScreenAndCrop").attr("src", url);" -->
						<img id="imageFromPrintScreenAndCrop" name="img" />

					</div>

					<div class="modal-footer"
						style="padding-top: 0px; padding-bottom: 0px">

						<form action="/resource/ajax/resourceImageClipBoardAfterCrop"
							method="get" id="submitCoordsToCrop"
							onsubmit="return checkCoords();">
							<input type="hidden" id="x" name="xCoord" /> <input
								type="hidden" id="y" name="yCoord" /> <input type="hidden"
								id="w" name="wCoord" /> <input type="hidden" id="h"
								name="hCoord" /> <input type="hidden" id="imageFileName"
								value="" name="imageFileName" /> <input type="hidden"
								id="resourceid" name="resourceid" value="${resource.id}" />
							<button type="button" class="btn btn-default"
								data-dismiss="modal" id="closeButton2">Fermer</button>
							<button type="submit" class="btn btn-primary">Sauver
								l'image</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.modal -->

	<!-- Modal : WAITING MODAL FROM PRINTSCREEN  -->
	<div class="modal fade" id="waitingModalForPrintScreen">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" onclick="resetFormPrintScreenNotFound()">&times;</button>
					<h4 class="modal-title">Information</h4>
				</div>
				<div class="modal-body">
					<p>Veuillez appuyer sur les touches CTRL + V pour récupérer
						l'image du presse-papier.</p>
					<p>(Pour l'instant, cela ne fonctionne qu'avec le navigateur
						"Chrome")</p>
					<span id="printScreenNotFound" class="text-warning"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default closeModal"
						data-dismiss="modal" onclick="resetFormPrintScreenNotFound()">Fermer</button>
				</div>
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>

</body>
</html>