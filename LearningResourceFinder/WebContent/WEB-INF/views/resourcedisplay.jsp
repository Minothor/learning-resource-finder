
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
</body>
</html>