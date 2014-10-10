$(document).ready(function() {
	$(".resource-content > .panel-heading").click(function() {
      $(this).parent().switchClass("resource-content", "resource-content-big", 100);
      $(this).parent().css("z-index", "2000");
      	if ($(this).parent().hasClass("resource-content-big")) {
      		$(this).parent().switchClass("resource-content-big", "resource-content", 10);
      	}
	});
});