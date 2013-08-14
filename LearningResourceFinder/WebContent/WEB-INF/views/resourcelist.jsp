<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="restag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Catalog</title>
</head>
<body>
	<a href="resourcecreate">Créer une ressource</a>
	<br />
	<c:forEach items="${resourceList}" var="resource">
		<div style="float:left;position:relative;padding:10px;margin-top:10px;width:210px;">
			<restag:resource resource="${resource}"></restag:resource>
		</div>
	</c:forEach>
</body>
</html>