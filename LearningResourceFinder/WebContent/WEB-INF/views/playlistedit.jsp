<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- Jquery for change input popup addImageUser -->
	<script type="text/javascript" src="/js/int/addImageUtil.js"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Playlist editing</title>
	<script type="text/javascript">
		function verifForm(form) {
			if (document.form.name.value == "") {
				alert("Veuillez remplir le champ nom !");
				document.form.name.focus();
				return false;
			}
		}
	</script>
</head>
<body>
	<lrftag:breadcrumb linkactive="Edition">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="${current.user.userName}" link="/user/${current.user.userName}" />
		<lrftag:breadcrumbelement label="${playlist.name}" link="playlist/${playlist.shortId}/${playlist.slug}" />
	</lrftag:breadcrumb>
	
	<div class="container">
	
		<c:choose>
			<c:when test="${playlist.id==null}">
				<h1>Create PlayList</h1>
			</c:when>
			<c:otherwise>
				<h1>Edit PlayList</h1>
			</c:otherwise>
		</c:choose>
	
		<lrftag:playlistimage canEdit="${canEdit}" playlist="${playlist}" random="${random}" />

		<div id="popupJquery" class="popupJquery">
			<div class="popup-close">
				<a class="close" title="close this popup">X</a>
			</div>
			<form method="post" action="/playlist/imageadd" class="formUrlResource" enctype="multipart/form-data">
				<h2>Ajouter une image</h2>
				<br /> 
				<label>
					<input type="radio" name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> 
					Depuis l'ordinateur
				</label> 
				<input type="file" name="file" value="Parcourir..." class="inputSource" id="inputFile" />
				<input type="hidden" name="strUrl" value="http://..." class="inputSource" id="inputUrl" /> 
				<br /> 
				<label>
					<input type="radio" name="rdFrom" value="url" class="radioUrl" /> 
					Depuis un lien
				</label> 
				<input type="hidden" name="id" value="${user.id}" /> 
				<input type="hidden" name="idPlayList" value="${playlist.id}" /> <br /> <br />
				<input class="btnSubmit" type="submit" value="Ajouter" name="btnPicture" />
			</form>
			<br />
		</div>

		<form modelAttribute="playlist" method="post" name="form"
			action='<%=response.encodeURL("/playlist/editsubmit")%>'>
			<form:hidden path="id" />
	
			<label>Titre</label>
			<form:input path="name" />
			<form:errors path="name" />
			<br />
			<label>Description</label>
			<form:input path="description" />
			<form:errors path="description" />
			<br />
			<input type="submit"
				value="<c:choose><c:when test="${playlist.id==null}">Créer la play-list</c:when><c:otherwise>Sauver</c:otherwise></c:choose>"
				onclick="javascript: return verifForm(this);" />
	
		</form>
	
	</div>
</body>
</html>