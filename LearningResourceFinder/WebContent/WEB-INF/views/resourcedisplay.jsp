
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<div>
		<img src="http://mrsthiessenswiki.wikispaces.com/file/view/math_clipart.jpg" style="width:500px;height:100px;" />
		<h1>${resource.name}</h1>
		<p>${resource.description}</p>
		<p>${resource.createdBy.firstName} ${resource.createdBy.lastName}</p>		
		<a href="<c:url value='resourceedit?id=${resource.id}'/>">Edit resource</a> 
	</div>
	<br />
	<div>
		<h2>Les liens :</h2>
		<c:forEach items="${resource.urlResource}" var="url">
			<a href="<c:url value='removeurlresource?id=${url.id}'/>">${url.name}</a>
		</c:forEach>		
	</div>
	<br />
	<div>
		<a href="#" data-width="500" data-rel="popup_addURL" class="poplight">Voir la pop-up - Width = 500px</a>
		<div id="popup_addURL" class="popup_addURL">
		    <h2>Ajouter une URL</h2>
		    <form:form modelAttribute="" action="" method="">
			    <label for="name">Name :</label> <form:input path="name" id="name"> <form:errors path="name" /><br /> 
			    <label for="url">Url :</label>   <form:input path="url" id="url"> <form:errors path="url" /><br />  
			    <input type="submit" value="Add" />
			</form:form>  
		</div>
	</div>
</body>
</html>