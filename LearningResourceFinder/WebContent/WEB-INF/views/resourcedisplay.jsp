<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>	
	<script type="text/javascript" src="/js/int/ajaxAddUrlResource.js"></script>
	<script type="text/javascript" src="/js/int/resourceImageGallery.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/int/resource-image-gallery.css"  />
	<!-- Jquery for change input popup addImageUser -->
	<script type="text/javascript" src="/js/int/addImageUrlGallery.js"></script>
	<title>Insert title here</title>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<lrf:breadcrumb-title title="${resource.name}">
		<lrf:breadcrumbelement title="${resource.name}" label="Ressource" link="ressourcelist" />
	</lrf:breadcrumb-title>
	<div>
		<img src="http://mrsthiessenswiki.wikispaces.com/file/view/math_clipart.jpg" style="width:500px;height:100px;" />
		<h1>${resource.name}</h1>
		<p>${resource.description}</p>
		<p>${resource.createdBy.firstName} ${resource.createdBy.lastName}</p>
		<c:if test="${canEdit}">		
			<a href="<c:url value='resourceedit?id=${resource.id}'/>">Edit resource</a> 
		</c:if>
	</div>
	<br />
	<div>
		<h2>Les liens :</h2>
		<table>
			<tr>
				<th>Nom</th>
				<th>Url</th>
				<th>Action</th>
			</tr>
		<c:forEach items="${resource.urlResources}" var="url">
			<tr>
				<td>${url.name}</td>
				<td><a href="${url.url}">${url.url}</a></td>
				<td><a href="<c:url value='removeurlresource?id=${url.id}'/>">Supprimer</a></td>
			</tr>	
		</c:forEach>
		</table>		
	</div>
	<br />
	<div>
		<a href="#" data-width="500" data-rel="popupJquery" class="poplight">Ajouter une URL</a>
		<div id="popupJquery" class="popupJquery">
			<div class="popup-close">
		    	<a class="close" title="close this popup">X</a>
		    </div>
		    <h2>Ajouter une URL</h2>	
		    <form:form method="post" action="#" class="formUrlResource">	
			    <label for="name">Name :</label> <input type="text" id="name" name="name" /> <br /> 
			    <label for="url">Url :  </label> <input type="text" name="url" id="url" />   <br />  
			    <input type="hidden" name="idresource" id="idresource" value="${resource.id}" />
			    <input type="button" class="btnSubmit" value="Add" onclick="ajaxPostUrlResource()" />
			</form:form>  
			<p id="response">${response}</p>
		</div>
	</div>
	<br />
	<%@ include file="resourceimagegallery.jsp" %>
	<c:if test="${canEdit}">
	<div>
		<a href="#" data-width="500" data-rel="popupJqueryaddImageGallery" class="poplight">Ajouter une image à la ressource</a>
		<div id="popupJqueryaddImageGallery" class="popupJquery">
			<div class="popup-close">
		    	<a class="close" title="close this popup">X</a>
		    </div>
		    <form method="post" action="/resource/imageadd" class="formUrlGallery" enctype="multipart/form-data">	
	          	<h2>Ajouter une image à la galerie</h2>
	          	<label><input type="radio"  name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> Depuis l'ordinateur</label>
	          	<input type="file"   name="file"   value="Parcourir..."    class="inputSource"   id="inputFile" /> 
	          	<input type="hidden" name="strUrl" value="http://..."      class="inputSource"   id="inputUrl"  />             
	          	<br /> 
	          	<label><input type="radio"  name="rdFrom" value="url"      class="radioUrl" /> Depuis un lien</label>
	          	<input type="hidden" name="idResource" value="${resource.id}" />
	          	<br />
	          	<br />
	          	<input class="btnSubmit" type="submit" value="Ajouter" name="btnPicture" /> 
	      	</form> 
			<p id="response">${response}</p>
		</div>
	</div>
	</c:if>
	<br />
	<a href="ressourcelist">home page</a>
</body>
</html>