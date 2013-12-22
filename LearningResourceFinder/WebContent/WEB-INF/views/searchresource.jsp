<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ page import="learningresourcefinder.search.SearchOptions"%>

<html>
<head>

<!-- To delete competence field -->
<script type="text/javascript"> 
$(document).ready(function reinitialize(){
  $("button").click(function(){
    $("#competenceIdHiddenField").val("");
  });
});
</script>

<link rel="stylesheet" href="css/ext/bootstrap.css" type="text/css"	media="screen" />
<link rel="stylesheet" href="css/ext/bootstrap-theme.css"type="text/css" media="screen" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
label {
	font-weight: normal;
}

.form-platform {
	position: absolute;
	top: 0px;
	left: 13em
}

.form-nature {
	position: absolute;
	top: 0px;
	left: 26em
}

.form-lang {
	position: absolute;
	top: 0px;
	left: 42em
}

.form-pub {
	position: absolute;
	top: 0px;
	left: 55em
}

.form-time {
	position: absolute;
	top: 0px;
	left: 68em
}


.filterSubBlock {
     display:inline-block;
     vertical-align:top;
     padding-right:30px;
}

</style>

<script>
window.onload = addLang;
	function addLang() {
		if ($("#monMenu input:checkbox:checked").length > 0) {
			var i = 0;
			$("input[name=language]")
					.each(
							function() {
								i++;
								if (this.checked) {
									document.getElementById('lang' + i).style.display = 'inline';
								} else {
									document.getElementById('lang' + i).style.display = 'none';
								}
								$('#monMenu').hide();
							});
		} else {
			alert('Vous devez sélectionner au moins une langue');
		}
	}
</script>
</head>


<body>
	<div class="container">
	
	<lrftag:pageheadertitle title="Recherche ${searchOptions.searchPhrase}"/>
	
	<form:form modelAttribute="searchOptions" action="searchresourceform" method="POST">
			  <div class="filterSubBlock">
 				  	 <c:if test="${searchOptions.competence != null}">
								<lrf:competencepath competence="${searchOptions.competence}"/>
								<input id="competenceIdHiddenField" name="competenceId" value="${searchOptions.competence.id }" type="hidden"/>
				            	<button type="button" class="close" style="margin-top:-18px; margin-left:10px;" data-dismiss="alert" onclick="reinitialize()" rel="tooltip" title="cliquer pour fermer">&times;</button> 
				      </c:if>
				</div>
			
			<br>
		<div class="filterSubBlock">
			<fieldset>
				<c:forEach var="formats" items="${formatEnumAllValues}">
					<label class="checkbox" style="margin-bottom:3px;"> <form:checkbox path="format" value="${formats}" />${formats.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>


		<div class="filterSubBlock">
			<fieldset>
				<c:forEach var="platform" items="${platformsEnumAllValues}">
					<label class="checkbox" style="margin-bottom:3px;"> <form:checkbox path="platform"	value="${platform}" />${platform.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>

		<div class="filterSubBlock">
			<fieldset>
				<c:forEach var="nature" items="${natureEnumAllValues}">
					<label class="checkbox" style="margin-bottom:3px;"> <form:checkbox path="nature" value="${nature}" />${nature.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>
		<div class="filterSubBlock">
			<fieldset>
				<form:input type="text" path="searchPhrase" style="width:150px; margin-top:10px" rel="tooltip" title="terme de votre recherche" />
			<fieldset> 
				<div class="dropdown" style="margin-right:33px">
				<span id="lang1" class="label label-primary" style="display: inline">Français</span> 
				<span id="lang2" class="label label-primary" style="display: none">Néerlandais</span>
				<span id="lang3" class="label label-primary" style="display: none">Allemand</span>
				<span id="lang4" class="label label-primary" style="display: none">Anglais</span>
				<span id="lang5" class="label label-primary" style="display: none">Russe</span>
					<a data-toggle="dropdown" href="#"
						onclick="$('#monMenu').show();">Changer</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
						id="monMenu">
					
						<c:forEach var="language" items="${languagesEnumAllValues}">
							<label class="checkbox" style="margin-left: 1em">
							   <form:checkbox path="language" value="${language}" />${language.description}
							</label>
						</c:forEach>

						<input type="button" id="btnclose" class="btn btn-default"	data-dismiss="modal" style="margin-left: 1em" onclick="addLang()" value="Fermer">
					</ul>
				</div>
			</fieldset>
			</fieldset>
			<div class="filterSubBlock">
				<fieldset>
					<div class="checkbox">
 				      <label><input type="checkbox" id="optionsCheck" name="noadvertizing" value="true" <c:if test="${searchOptions.wantsNoAd}">checked</c:if> />sans pub</label>
				    </div>
					<div>
						<form:input type="text" path="maxDuration" style=" width:30px" rel="tooltip" title="durée maximale" /> minutes max.
					</div>
					<br>
					<button type="submit" class="btn btn-default">Filtrer</button>
				</fieldset>
			</div>
		
		</div>
		
		<input type="hidden" id="so" name="so" value="${timeStamp}" />
		
		
	</form:form>
	</div>
	
	<c:forEach items="${resourcelist}" var="resource">
			<lrftag:resource resource="${resource}"/>
	</c:forEach>

	<c:choose>
		<c:when test="${empty param.page}">
			<c:set var="page" value="1"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="page" value="${param.page}">
			</c:set>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${numberResource>0}">
			<ul class="pagination">
				<c:if test="${page>1}">
					<li><a href="searchresource?page=${page-1}&so=${timeStamp}">&laquo;</a></li>
				</c:if>
				<c:forEach begin="1" end="${numberResource / 100}" varStatus="loop">
					<li><a href="searchresource?page=${loop.index}&so=${timeStamp}">${loop.index}</a></li>
				</c:forEach>
				<c:if test="${page*100 < numberResource}">
				    <li><a href="searchresource?page=${page+1}&so=${timeStamp}">&raquo;</a></li>
				</c:if>
			</ul>
		</c:when>
	</c:choose>
</body>
</html>