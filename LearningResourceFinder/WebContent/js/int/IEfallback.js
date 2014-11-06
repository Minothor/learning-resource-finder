$(document).ready(function(){
	if (document.all) {
	    var logo = document.getElementById("mainLogo");
	    logo.setAttribute("class", "mainLogoFallback");
	    logo.setAttribute("src", "/images/ToujoursPlus-logo-500px.png");
	}
});