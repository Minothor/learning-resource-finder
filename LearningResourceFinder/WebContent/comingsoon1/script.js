$(function () {
	var austDay = new Date();
	austDay = new Date(austDay.getFullYear() + 1, 1 - 1, 26);
	$('#defaultCountdown').countdown({until: austDay});
	$('#year').text(austDay.getFullYear());
});

$(document).ready(function() {
    $('nav a').click(function(){
		$('nav a').removeClass("cbp-fbcurrent")
		$(this).addClass("cbp-fbcurrent");
	});
});