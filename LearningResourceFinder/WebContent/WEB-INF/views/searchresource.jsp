<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="/js/ext/jquery-1.9.0.min.js"></script>
<script src="/js/ext/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function reinitialize(){
  $("button").click(function(){
    $("#competenceIdHiddenField").val("");
  });
});
</script>
<link rel="stylesheet" href="css/ext/bootstrap.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/ext/bootstrap-theme.css"
	type="text/css" media="screen" />

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
<title>Insert title here</title>
</head>
<body>

	<form:form modelAttribute="searchOptions" action="searchresourcesubmit">
		<div class="filterSubBlock">
			<fieldset>
				<legend>Format</legend>
				<c:forEach var="formats" items="${formatEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="format" value="${formats}" />${formats.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>


		<div class="filterSubBlock">
			<fieldset>
				<legend>Plateforme</legend>
				<c:forEach var="platform" items="${platformsEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="platform"	value="${platform}" />${platform.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>

		<div class="filterSubBlock">
			<fieldset>
				<legend>Nature pédagogique</legend>
				<c:forEach var="nature" items="${natureEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="nature" value="${nature}" />${nature.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>

		<div class="filterSubBlock" style="max-width: 200px;">
			<fieldset>
				<legend>Langues</legend>
				<br> 
				<span id="lang1" class="label label-primary" style="display: inline">Français</span> 
				<span id="lang2" class="label label-primary" style="display: none">Néerlandais</span>
				<span id="lang3" class="label label-primary" style="display: none">Allemand</span>
				<span id="lang4" class="label label-primary" style="display: none">Anglais</span>
				<span id="lang5" class="label label-primary" style="display: none">Russe</span>
				<div class="dropdown">
					<br> <a data-toggle="dropdown" href="#"
						onclick="$('#monMenu').show();">Changer</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
						id="monMenu">
					
						<c:forEach var="language" items="${languagesEnumAllValues}">
				
							<label class="checkbox" style="margin-left: 1em"> <form:checkbox path="language" value="${language}" />${language.description}
							</label>
						</c:forEach>

						<input type="button" id="btnclose" class="btn btn-default"	data-dismiss="modal" style="margin-left: 1em" onclick="addLang()" value="Fermer">
					</ul>
				</div>
			</fieldset>
		</div>
		<div class="filterSubBlock">
			<fieldset>
				<legend>Publicité</legend>
				<div class="radio">
					<label> <input type="radio" name="advertising" id="optionsRadio1" value="1" checked> Oui
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="advertising" id="optionsRadio2" value="0"> Non
					</label>
				</div>
			</fieldset>
		</div>

		<div class="filterSubBlock">
			<fieldset>
				<legend>Durée</legend>
				<input type="text" name="maxDuration" style=" width:30px"> minutes max.
				
			</fieldset>
		</div>
		<div class="filterSubBlock">
			<fieldset>
				<legend>Terme de recherche</legend>
				<input type="text" name="maxDuration" style=" width:150px" value="${searchOptions.searchPhrase}"> 
				
			</fieldset>
		</div>
		<!-- 	if searchOptions.competence différent de null.... -->
		
			<c:if test="${searchOptions.competence != null}">
				<div class="filterSubBlock">
					<fieldset>
						<legend>Competence</legend>
						<lrf:competencepath competence="${searchOptions.competence}"/>
						<input id="competenceIdHiddenField" name="competenceId" value="${searchOptions.competence.id }" type="hidden"/>
					</fieldset>
		            	<button type="button" class="close" data-dismiss="alert" onclick="reinitialize()" rel="tooltip" title="cliquer pour fermer">X</button>
				</div>
			</c:if>
		<form:input type="hidden" path="searchPhrase"/>
		<button type="submit" class="btn btn-default">Filtrer</button>
		
	</form:form>
	
	
	
	
	<c:forEach items="${resourcelist}" var="resource">
		<div style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<lrftag:resource resource="${resource}"></lrftag:resource>
		</div>
	</c:forEach>
	
	<ul class="pagination">
			
  <li><a href="searchresource?searchphrase=${searchOptions.searchPhrase}&page=${param.page-1}">&laquo;</a></li>
  <c:forEach begin="1" end="${numberResource}" varStatus="loop">
    <li><a href="searchresource?searchphrase=${searchOptions.searchPhrase}&page=${loop.index}">${loop.index}</a></li>
</c:forEach>
  <li><a href="searchresource?searchphrase=${searchOptions.searchPhrase}&page=${param.page+1}">&raquo;</a></li>
</ul>
	
</body>
</html>