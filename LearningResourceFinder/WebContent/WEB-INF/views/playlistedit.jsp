<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
   <c:choose>
	<c:when test="${playlist.id==null}">
	  <lrftag:breadcrumb linkactive="créer une playlist">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="${current.user.userName}" link="/user/${current.user.userName}" />
	  </lrftag:breadcrumb>
	</c:when>
	<c:otherwise>
	  <lrftag:breadcrumb linkactive="édition">
		<lrftag:breadcrumbelement label="Home" link="/home" />
		<lrftag:breadcrumbelement label="${current.user.userName}" link="/user/${current.user.userName}" />
		<lrftag:breadcrumbelement label="${playlist.name}" link="/playlist/${playlist.shortId}/${playlist.slug}" />
	  </lrftag:breadcrumb>
	</c:otherwise>
</c:choose>
	 
	
	<div class="container">
		
		
		<c:choose>
			<c:when test="${playlist.id==null}">
				<lrftag:pageheadertitle title="Création d'une PlayList"/>
			</c:when>
			<c:otherwise>
				<lrftag:pageheadertitle title="Edition : ${playlist.name}"/>
			</c:otherwise>
		</c:choose>
	
		<div class="panel panel-default">
			<div class="panel-body">
				<br />
				<form:form action='<%=response.encodeURL("/playlist/editsubmit")%>' modelAttribute="playlist" class="form-horizontal" role="form" method="post">
					<div class="col-md-12">
						<form:hidden path="id" />
						<lrftag:input path="name" label="Titre" />
						<lrftag:input path="description" label="Description" />
				
							
					</div>
					
					<div class="form-group">
				   		<label class="col-lg-2"></label>
				   		<ul class="col-lg-2" >
				    		<li style="display:inline;">
				    			<input type="submit" class="btn btn-primary" value="<c:choose><c:when test="${playlist.id==null}">Créer la play-list</c:when><c:otherwise>Sauver</c:otherwise></c:choose>" onclick="javascript: return verifForm(this);" />
				    		</li>
				    		<c:if test="${playlist.id != null}">
				    		    <li style="display:inline;"><button onclick="location.href='/playlist/${playlist.shortId}/${playlist.slug}';" class="btn" type="reset">Annuler</button></li>
				    		</c:if>    
				    	</ul>
				    </div>
				</form:form>
			</div>
		</div>
		
		


	
	</div>
</body>
</html>