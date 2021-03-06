<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<head>
<!-- Jquery for change input popup addImageUser -->
<script type="text/javascript" src="/js/int/addImageUrlPlaylist.js"></script>
<script type="text/javascript" src="/js/ext/jquery.popconfirm.js"></script>
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-509a829c59a66215"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
 		$.fn.editable.defaults.mode = 'inline';
 	    $('.editableField').editable({   
 	    	  emptytext: '? ?',
 	    	  type: 'text',
 	    	  url: '/ajax/playlisteditfieldsubmit',
 	    	  pk: '${playlist.id}',
		});
 	    
 	    $(".noneditplaylist").popoverWithAutoHideForPrivilege("Pour modifier ce champ, il faut être connecté et avoir un niveau 3 de contribution.");
 	    
 	    $("[data-toggle='confirmation']").popConfirm({
			 title : "Confirmation",
			 content : "Voulez-vous vraiment supprimer cette séquence ?",
			 placement : "bottom",
			 yesBtn : "oui",
			 noBtn : "non"
		 });
 	   
 	     $('#sortable').sortable({
 	        opacity : 0.6,
 	        update: function() {
        	   var order = [];
 			   $("#sortable .sortableItem").each(function() {
 			 	  order.push(this.id);
 			   });
		   	   $.ajax({
  					type:"POST",
  	    			headers:{
  	    				'Accept': 'application/json',
  	    				'Content-type': 'application/json'
  	    			},
		  			url: "/sortresources",
					data : JSON.stringify(order),
	    			success: function() {
	    				location.reload();
	    			} 
				});
             }
           });
 	    $( "#sortable" ).disableSelection();
 	    
  	    $('#submitButton').click(function(){
  		   $('#modalPlaylist').hide();
  	    });	     
 	});
</script>

<style type="text/css">
#sortable, #notSortable {
	margin: 0;
	padding: 0;
	list-style-type: none;
}

#sortable li, #notSortable li {
	margin: 3px 3px 3px 0;
	padding: 1px;
	float: left;
}

#sortable li .panel-heading{
	cursor: move;
}

#sortable li a {
	cursor: default;
}


dd {
	word-wrap: break-word;
	overflow-wrap: break-word;
}

#descriptionDiv textarea {
	width: 250%;
}

#descriptionDiv .editable-buttons {
	float: left;
}

.border {
	border: 2px solid black;
	background-color: lightgray;
	box-shadow: 0 0 5px black;
	color: white;
}
</style>

</head>

<body>
	<lrftag:breadcrumb linkactive="${playlist.name}">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<c:if test="${playlist.createdBy eq current.user}">
			<lrftag:breadcrumbelement label="mes séquences"	link="/playlist/user/${current.user.userName}" />
		</c:if>
	</lrftag:breadcrumb>
	<div class="container" itemscope itemtype="http://schema.org/WebPage">
		<div class="row">
			<div class="col-md-10">
				<lrftag:pageheadertitle title="${playlist.name}" />
			</div>
			<div class="col-md-2 text-right" style="margin-top: 20px;">
				<div class="addthis_sharing_toolbox"></div>
				<span style="padding-right: 0px">
				<c:if test="${canEdit}">
					<a href="<c:url value='/playlist/delete?id=${playlist.id}'/>" class="btn" data-toggle='confirmation'>
						<button type="button" class="btn addToolTip close"
							title="supprimer cette séquence">&times;</button>
					</a>
				</c:if>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-4">
					<lrftag:playlistimage canEdit="${canEdit}" playlist="${playlist}" random="${random}" />
				</div>
				<div class="col-md-8">
					<dl class="dl-horizontal">
						<dt>Nom :</dt>
						<dd itemprop="name"><a id="name"  href="#" ${canEdit==true ? " href='#' class='editableField'" : " class='noneditplaylist'"}>${playlist.name}</a></dd>
					  	<dt itemprop="description">Description :</dt>
					  	<dd id="descriptionDiv"><a id="description"  data-type="textarea" href="#" ${canEdit==true ? " href='#' class='editableField'" : " class='noneditplaylist'"}>${playlist.description}</a></dd>
					  	<dt>Auteur :</dt>
					  	<dd itemprop="author"><a href="/user/${playlist.createdBy.userName}">${playlist.createdBy.fullName}</a></dd>
					  	<lrf:conditionDisplay privilege="MANAGE_PLAYLIST">
					  		<dt>Id court :</dt>
					  		<dd>${playlist.shortId}</dd>
					  	</lrf:conditionDisplay>
					</dl>
				</div>
			</div>
		</div>


		<h3>Ressources incluses</h3>
		
		<c:choose>
    	<c:when test="${canEdit}">
    		<ul id="sortable">
	    </c:when>
	    <c:otherwise>
	    	<ul id="notSortable">
    	</c:otherwise>
		</c:choose>
		
		<c:set var="i" value='1' />
		<c:forEach items="${playlist.resources}" var="resource">
			<c:if test="${canEdit}">
				<c:set var="closeUrl"
					value='/playlist/remove?idplaylist=${playlist.id}&idresource=${resource.id}' />
			</c:if>
			<c:set var="prefix" value="${i}" />
			<li style="display: inline-block;"><lrftag:resource prefix="${prefix}" resource="${resource}" closeUrl="${closeUrl}"></lrftag:resource></li>
			<c:set var="i" value="${i+1}" />
		</c:forEach>

		</ul>
	
		<div style="clear: left;">
		Pour ajouter une ressource à cette séquence, passez par la page détaillant la ressource à ajouter (en étant connecté avec votre compte).
		</div>

		<!-- Modal -->
		<div class="modal fade" id="modalPlaylist" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="formUrlPlaylist close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
		          		<h4 class="modal-title">Placer une image</h4>
		        	</div>		        	
		     		<form method="post" action="/playlist/imageadd" enctype="multipart/form-data" class="form-horizontal formUrlPlaylist">	
		     			<div class="modal-body">
		     			
		     				<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio" name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> 
							    	Depuis l'ordinateur
							    </label>
							    <div class="col-lg-8">
							      	<input type="file" name="file" value="Parcourir..." class="inputSource"  id="inputFile" style="width:345px;" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="url" class="radioUrl" /> 
							    	Depuis un lien
							    </label>
							    <div class="col-lg-8">
							      	<input type="hidden" name="strUrl" placeholder="http://..." class="form-control inputSource" id="inputUrl" />
								</div>
							</div>
							<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="defaut" class="radioDefaut" /> 
							    	Image par défaut
							    </label>
							</div>
				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="idPlayList" value="${playlist.id}" />
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<button type="submit" class="btn btn-primary closeModal" id="submitButton">Sauver l'image</button>
			        	</div>
				    </form>
		      	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</body>
</html>