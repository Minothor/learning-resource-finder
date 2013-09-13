<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="/js/ext/jquery-1.9.0.min.js"></script>
<script src="/js/ext/bootstrap.js"></script>
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
function addLang(){
	var a=0;
  
	if ($('#languages1').is(':checked')){
	document.getElementById('lang-fr').style.display = 'inline';
	}
	else {
		document.getElementById('lang-fr').style.display = 'none';
		a++;
	}
	if ($('#languages2').is(':checked')){
		document.getElementById('lang-nl').style.display = 'inline';
		}
	else {
		document.getElementById('lang-nl').style.display = 'none';
		a++;
	}
	if ($('#languages3').is(':checked')){
		document.getElementById('lang-de').style.display = 'inline';
		}
	else {
		a++;
		document.getElementById('lang-de').style.display = 'none';
	}
	if ($('#languages4').is(':checked')){
		document.getElementById('lang-en').style.display = 'inline';
		}
	else {
		document.getElementById('lang-en').style.display = 'none';
		a++;
	}
	if ($('#languages5').is(':checked')){
		document.getElementById('lang-ru').style.display = 'inline';
		}
	else {
		document.getElementById('lang-ru').style.display = 'none';
		a++;
	}
	if (a==5){
		alert('Vous devez sélectionner au moins une langue');
	}
	else {
		$('#monMenu').hide(); 
	}
	  
}

</script>
<title>Insert title here</title>
</head>
<body>

	<form:form modelAttribute="searchoptions" action="searchresourcesubmit">
		<div class="filterSubBlock">
			<fieldset>
				<legend>Format</legend>
				<c:forEach var="formats" items="${formatEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="formats"
							value="${formats}" />${formats.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>


		<div class="filterSubBlock">
			<fieldset>
				<legend>Plateforme</legend>
				<c:forEach var="platform" items="${platformsEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="languages"
							value="${platform}" />${platform.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>

		<div class="filterSubBlock">
			<fieldset>
				<legend>Nature pédagogique</legend>
				<c:forEach var="nature" items="${natureEnumAllValues}">
					<label class="checkbox"> <form:checkbox path="nature"
							value="${nature}" />${nature.description}
					</label>
				</c:forEach>
			</fieldset>
		</div>

		<div class="filterSubBlock" style="max-width: 200px;">
			<fieldset>
				<legend>Langues</legend>
				<br> <span id="lang-fr" class="label label-primary"
					style="display: inline">Français</span> <span id="lang-nl"
					class="label label-primary" style="display: none">Néerlandais</span>
				<span id="lang-de" class="label label-primary" style="display: none">Allemand</span>
				<span id="lang-en" class="label label-primary" style="display: none">Anglais</span>
				<span id="lang-ru" class="label label-primary" style="display: none">Russe</span>
				<div class="dropdown">
					<br> <a data-toggle="dropdown" href="#"
						onclick="$('#monMenu').show();">Changer</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel"
						id="monMenu">
						<c:forEach var="language" items="${languagesEnumAllValues}">
							<label class="checkbox" style="margin-left: 1em"> <form:checkbox
									path="languages" value="${language}" />${language.description}
							</label>
						</c:forEach>

						<input type="button" id="btnclose" class="btn btn-default"
							data-dismiss="modal" style="margin-left: 1em" onclick="addLang()"
							value="Fermer">
					</ul>
				</div>
			</fieldset>
		</div>
		<div class="filterSubBlock">
			<fieldset>
				<legend>Publicité</legend>
				<div class="radio">
					<label> <input type="radio" name="pub" id="optionsRadio1"
						value="1" checked> Oui
					</label>
				</div>
				<div class="radio">
					<label> <input type="radio" name="pub" id="optionsRadio2"
						value="0"> Non
					</label>
				</div>
			</fieldset>
		</div>

		<div class="filterSubBlock">
			<fieldset>
				<legend>Durée</legend>
				Moins de <input type="text" name="maxDuration">
			</fieldset>
		</div>
		<input type="hidden" name="search" value="${search}">
		<button type="submit" class="btn btn-default">Filtrer</button>
	</form:form>
	
		<c:forEach items="${resourceList}" var="resource">
		<div style="float: left; position: relative; padding: 10px; margin-top: 10px; width: 210px;">
			<restag:resource resource="${resource}"></restag:resource>
		</div>
	</c:forEach>
</body>
</html>