<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AHMED HAHA</title>
</head>
<body>
	<article class=""> <header class="">
	<h1 class="">${ Cycle.name }</h1>
	</header>
	<div class="">
		<c:forEach items="${cycleColumn}" var="listColumns">

			<div>
				<c:forEach items="${cycleitems}" var="cycleColumn">
					<div>
						<h2>${cycleitems.competence.name}</h2>
						<ol>
							<c:forEach items="${subitems}" var="cycleitems">
								<li class=""><a class="" href=""> <span class="">${
											subitems.competence.name }</span> <span class="">${
											subitems.competence.description }</span>
								</a></li>
							</c:forEach>
						</ol>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	</article>
</body>
</html>