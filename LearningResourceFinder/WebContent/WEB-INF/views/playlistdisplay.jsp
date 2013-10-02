<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<!-- Jquery for change input popup addImageUser -->
<script type="text/javascript" src="/js/int/addImageUrlPlaylist.js"></script>
<title>PlayList</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="${playlist.name}">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="${current.user.userName}" link="/user/${current.user.userName}" />
		<lrftag:breadcrumbelement label="Mes playlists" link="/playlist/user/${current.user.userName}" />
	</lrftag:breadcrumb>
	
	<div class="container">
		<lrftag:pageheadertitle title="${playlist.name}"/>
		<div class="btn-group">
			<c:if test="${canEdit}">
			<a class="btn btn-default" href=<c:url value='/playlist/edit?id=${playlist.id}'/>>Editer</a>
			</c:if>
			<a class="btn btn-default"  href="/playlist/user/${playlist.createdBy.userName}">Mes PlayLists</a> 
			<a class="btn btn-default" href=<c:url value='/ressourcelist'/>>Vers l'arborescence des ressources</a>
		</div>
		<br />
		<br />
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-2">
					<lrftag:playlistimage canEdit="${canEdit}" playlist="${playlist}" random="${random}" />
				</div>
				<div class="col-md-4">
					<dl class="dl-horizontal">
						<dt>Nom :</dt>
					  	<dd>${playlist.name}</dd>
					  	<dt>Description :</dt>
					  	<dd>${playlist.description}</dd>
					  	<dt>Auteur :</dt>
					  	<dd><a href="/user/${playlist.createdBy.userName}">${playlist.createdBy.fullName}</a></dd>
					</dl>
				</div>			
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-12">
					<h2>Ressources incluses</h2>
					<c:forEach items="${playlist.resourceList}" var="resource">
					<div style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
						<lrftag:resource resource="${resource}"></lrftag:resource>
			
						<div style="padding: 10px; width: 180px; height: 10px; background-color: #F6CEF5">
							<a href=<c:url value='/playlist/remove?idplaylist=${playlist.id}&idresource=${resource.id}'/>>Remove</a>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>


		
		<!-- Modal -->
		<div class="modal fade" id="modalPlaylist" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="formUrlPlaylist close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
		          		<h4 class="modal-title">Ajouter un avatar</h4>
		        	</div>		        	
		     		<form method="post" action="/playlist/imageadd" enctype="multipart/form-data" class="form-horizontal formUrlResource">	
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
							      	<input type="hidden" name="strUrl" value="http://..." class="form-control inputSource" id="inputUrl" />
								</div>
							</div>
				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="idPlayList" value="${playlist.id}" />
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<button type="submit" class="btn btn-primary closeModal">Sauver l'image</button>
			        	</div>
				    </form>
		      	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</body>
</html>