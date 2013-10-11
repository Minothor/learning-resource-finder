<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<lrftag:breadcrumb linkactive="Contribution">
		<lrftag:breadcrumbelement label="Contribution" link="contribution" />
		<lrftag:breadcrumbelement label="Home" link="home" />
	</lrftag:breadcrumb>
	<div class="container">
		Hello ProblemList
	</div>
</body>
</html>