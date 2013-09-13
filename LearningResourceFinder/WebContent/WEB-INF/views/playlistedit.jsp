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
		<lrftag:pageheadertitle title="Edition : ${playlist.name}"/>
		
		<c:choose>
			<c:when test="${playlist.id==null}">
				<h1>Create PlayList</h1>
			</c:when>
			<c:otherwise>
				<h1>Edit PlayList</h1>
			</c:otherwise>
		</c:choose>
	
		<lrftag:playlistimage canEdit="${canEdit}" playlist="${playlist}" random="${random}" />
		
		<form:form action='<%=response.encodeURL("/playlist/editsubmit")%>' modelAttribute="playlist" class="form-horizontal" role="form" method="post">
			<form:hidden path="id" />
			<lrftag:input path="name" label="Titre" />
			<lrftag:input path="description" label="Description" />

			<input type="submit"
				value="<c:choose><c:when test="${playlist.id==null}">Créer la play-list</c:when><c:otherwise>Sauver</c:otherwise></c:choose>"
				onclick="javascript: return verifForm(this);" />
		</form:form>


	
	</div>
</body>
</html>