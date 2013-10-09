<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrs'%>
    <%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<lrf:breadcrumb linkactive="${cycle.name}">
		<lrf:breadcrumbelement label="home" link="home" />
		<lrf:breadcrumbelement label="cycles" link="cyclelist" />
</lrf:breadcrumb>

<div class="container">
      <lrs:conditionDisplay privilege="MANAGE_COMPETENCE">
          <li><a href="<c:url value='/cycleedit?id=${cycle.id}'/>">Edit Cycle</a></li> 
         
      </lrs:conditionDisplay>

<h1>${ cycle.name }</h1>
	<div class="row">
		<c:forEach items="${listColumns}" var="cycleColumn">
				<div class="col-xs-6 col-md-4"> 
					<c:forEach items="${cycleColumn}" var="cycleitems">
							<h2>${cycleitems.competence.name}</h2>
							<c:forEach items="${cycleitems.children}" var="subitems">
								<span style="font-size:120%">${subitems.competence.code }
								<a href="searchresource?competenceid=${subitems.competence.getId()}">${subitems.competence.name }</span> 
								</a></span></br>
								<ul>
									<c:forEach items="${subitems.children}" var="subsubitems">
										<li>
											${subsubitems.competence.code }
											<a href="searchresource?competenceid=${subsubitems.competence.getId()}">${subsubitems.competence.name}</a></br>
										</li>
									</c:forEach>
								</ul>
							</c:forEach>
					</c:forEach>
				</div>
		</c:forEach>
	</div>
		
</div>
</html>