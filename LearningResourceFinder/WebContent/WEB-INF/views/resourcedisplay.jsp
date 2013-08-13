<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<title>Insert title here</title>
	<script type="text/javascript">
		function ajaxPostUrlResource() 
		{
			var name = $('#name').val();
			var url  = $('#url').val();
			var idresource = $('#idresource').val();
			
			$.ajax({
				type : "POST",
	            url : 'ajax/addurl',
	            data: "idresource="+idresource+"&name="+name+"&url="+url,
	            success : function(data) {
	                $('#response').html(data);
	                $("input[type='text']").val('');
	            },
	            error : function(data) {
	            	$('#response').html(data);
	            }
	        }); 
		}
	</script>
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
		<table>
			<tr>
				<th>Nom</th>
				<th>Url</th>
				<th>Action</th>
			</tr>
		<c:forEach items="${resource.urlResource}" var="url">
			<tr>
				<td>${url.name}</td>
				<td>${url.url}</td>
				<td><a href="<c:url value='removeurlresource?id=${url.id}'/>">Supprimer</a></td>
			</tr>	
		</c:forEach>
		</table>		
	</div>
	<br />
	<div>
		<a href="#" data-width="500" data-rel="popup_addURL" class="poplight">Ajouter une URL</a>
		<div id="popup_addURL" class="popup_addURL">
		    <h2>Ajouter une URL</h2>	
		    <form:form method="post" action="#" class="formUrlResource">	
			    <label for="name">Name :</label> <input type="text" id="name" name="name" /> <br /> 
			    <label for="url">Url :  </label> <input type="text" name="url" id="url" />   <br />  
			    <input type="hidden" name="idresource" id="idresource" value="${resource.id}" />
			    <input type="button" value="Add" onclick="ajaxPostUrlResource()" />
			</form:form>  
			<p id="response">${response}</p>
		</div>
	</div>
	<br />
	<a href="ressourcelist">home page</a>
</body>
</html>