<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title></title>
</head>
<body>

<lrf:breadcrumb linkactive="${cycle.name}">
		<lrf:breadcrumbelement label="Home" link="home" />
		<lrf:breadcrumbelement label="Liste des cycles" link="cyclelist" />
	</lrf:breadcrumb>



 <a href="<c:url value='cycleedit?id=${cycle.id}'/>">Edit Cycle</a>  <br>

	<h2 class="">${ Cycle.name }</h2>
	<div class="row">
		<c:forEach items="${listColumns}" var="cycleColumn">
			<div class="col-xs-6 col-md-4"> COLUMN
				<c:forEach items="${cycleColumn}" var="cycleitems">
						<h2>${cycleitems.competence.name}</h2>
						<ol>
							<c:forEach items="${cycleitems.children}" var="subitems">
								<li class=""><a class="" href=""> <span class="">${
											subitems.competence.name }</span> <span class="">${
											subitems.competence.description }</span>
								</a></li>
							</c:forEach>
						</ol>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
		
		

</html>