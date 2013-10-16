<%@ page language="java" contentType="text/html;"%>
    <%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    <%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
    <%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
$(function ()  
 { $(".mycompetencePopover").popover({html:true, trigger:'hover'});  
});  

</script>

</head>
<body>
<lrftag:breadcrumb linkactive="${cycle.name}">
		<lrftag:breadcrumbelement label="home" link="home" />
		<lrftag:breadcrumbelement label="cycles" link="cyclelist" />
</lrftag:breadcrumb>

<div class="container">
      <lrf:conditionDisplay privilege="MANAGE_COMPETENCE">
          <li><a href="<c:url value='/cycleedit?id=${cycle.id}'/>">Edit Cycle</a></li> 
         
      </lrf:conditionDisplay>

<h1>${ cycle.name }</h1>
	<div class="row">
		<c:forEach items="${listColumns}" var="cycleColumn">
				<div class="col-xs-6 col-md-4"> 
					<c:forEach items="${cycleColumn}" var="cycleitems">
							<h2>${cycleitems.competence.name}</h2>
							<c:forEach items="${cycleitems.children}" var="subitem">
								<span  class="mycompetencePopover" data-content="<lrftag:competencedescription competence="${subitem}" mustlistchildren="false"/>" style="font-size:120%">${subitem.competence.code }
								  <a href="searchresource?competenceid=${subitem.competence.getId()}">${subitem.competence.name }</span> 
								  </a>
								</span>
								</br>
								<ul>
									<c:forEach items="${subitem.children}" var="subsubitem">
										<li class ="mycompetencePopover" data-content="<lrftag:competencedescription competence="${subitem}" mustlistchildren="true"/>">
											${subsubitem.competence.code }
											<a href="searchresource?competenceid=${subsubitem.competence.getId()}">${subsubitem.competence.name}</a></br>
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