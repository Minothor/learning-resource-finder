<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Playlist editing</title>
<script type="text/javascript">

function verifForm(form)
{
   if(document.form.name.value == ""){
      alert("Veuillez remplir le champ nom !");
      document.form.name.focus();
      return false;
   }
}


</script>

</head>
<body>
	<c:choose>
	  <c:when test="${playlist.id==null}"><h1>Create PlayList</h1></c:when>
	      <c:otherwise><h1>Edit PlayList</h1></c:otherwise>
	</c:choose>
	<form:form modelAttribute="playlist" method="post" name="form" action='<%=response.encodeURL("/playlist/editsubmit")%>'>
		<form:hidden path="id" />  
		
		<label>Titre</label>        <form:input path="name"/>         <form:errors path="name"/><br />
		<label>Description</label> <form:input path="description" />  <form:errors path="description"/><br />
	    <input type="submit" value="<c:choose><c:when test="${playlist.id==null}">Créer la play-list</c:when><c:otherwise>Sauver</c:otherwise></c:choose>"
	  		onclick="javascript: return verifForm(this);"  />
	    
	</form:form>
	
	
	
</body>
</html>