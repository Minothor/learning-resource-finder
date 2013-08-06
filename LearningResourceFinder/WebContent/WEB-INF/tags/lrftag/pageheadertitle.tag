<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/ryctag/" prefix="ryctag" %>
<%@ attribute name="title" required="true" type="java.lang.String"%>

<head>
	<title>${title}</title>
</head>

<h1>${title}</h1>