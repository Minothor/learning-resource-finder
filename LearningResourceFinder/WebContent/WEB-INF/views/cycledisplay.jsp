<%@ page language="java" contentType="text/html;"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:set var="cycleDescription" value="${cycle.description}"/>
<c:set var="cycleDescriptionPartialEscape" value="${fn:replace(cycleDescription,'<sup>', '')}" />
<c:set var="cycleDescriptionEscaped" value="${fn:replace(cycleDescriptionPartialEscape,'</sup>', '')}" />
<title>${cycleDescriptionEscaped}</title>
<meta name="title" content="${cycle.name} - ${cycleDescriptionEscaped}" />
<meta name="description" content="Liste des ressources, séquences et compétences pour le cycle de ${cycleDescriptionEscaped}" />
<meta name="keywords" content="cylce, classe, primaire, secondaire, ressources, séquences, compétences, catégories, savoir-faire" />

<script type="text/javascript">	
	$(document).ready(function() {
		$('.poper').each(function() {
			var $elem = $(this);
			$elem.popover({
				placement : 'auto',
				trigger : 'hover',
				html : true,
				container : $elem,
				animation : false,
				delay: {hide: 100}
			});
		});
	});

</script>

</head>
<body>
<lrftag:breadcrumb linkactive="${cycle.name}">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<lrftag:breadcrumbelement label="cycles" link="/cyclelist" />
</lrftag:breadcrumb>

<div class="container">
      <lrf:conditionDisplay privilege="MANAGE_COMPETENCE">
          <li><a href="<c:url value='/cycleedit?id=${cycle.id}'/>">Editer cycle</a></li> 
         
      </lrf:conditionDisplay>

<h1>${ cycle.name }</h1>
	<lrftag:resourceband resourceList="${cache.getComputedTopResourcesByCycle(cycle.id)}"/>
	<a href="<c:url value='/resourcelist?cycleid=${cycle.id}&amp;sort=popularity'/>" >Afficher toutes les ressources par popularité pour le cycle ${cycle.name}</a></br>
	<a href="<c:url value='/resourcelist?cycleid=${cycle.id}&amp;sort=createdOn'/>" >Afficher les 100 dernières ressources pour le cycle ${cycle.name}</a>
	<div class="row">
		<c:set var="valeur" value="0" />
		<c:forEach items="${listColumns}" var="cycleColumn">
				<c:set var="valeur" value="${valeur+1}" />
				<div class="col-xs-6 col-md-4"> 
					<c:forEach items="${cycleColumn}" var="cycleitems">
							
							<a href="/searchresource?competenceid=${cycleitems.competence.getId()}"><h2>${cycleitems.competence.name}</h2></a>
							<c:forEach items="${cycleitems.children}" var="subitem">
								<span style="font-size: 80% ; color:#808080"> ${subitem.competence.code } </span>
									<span  <%-- class="mycompetencePopover${valeur<3?'Right':'Left'}" data-content="<lrftag:competencedescription competenceNode='${subitem}' mustlistchildren='true'/>"--%> style="font-size: 120%">
									  <a href="/searchresource?competenceid=${subitem.competence.getId()}">${subitem.competence.name }
									  </a>
									  </span> 
								</br>
								<ul>
								
								<c:forEach items="${subitem.children}" var="subsubitem">

									<div <c:if test='${subsubitem.children.size() > 0}'> class="poper" data-content="<lrftag:competencedescription competenceNode='${subsubitem}' mustlistchildren='true' />"</c:if>>
										<li>
											<span style="font-size: 80% ; color:#808080">${subsubitem.competence.code } </span>
											<a	href="/searchresource?competenceid=${subsubitem.competence.getId()}">${subsubitem.competence.name}</a></br>
										</li>
									</div>
								
								</c:forEach>

							</ul>
							</c:forEach>
					</c:forEach>
				</div>
		</c:forEach>
	</div>
		
</div>
</html>