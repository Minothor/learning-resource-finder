var toFrontZIndex = 2;

$(document).ready(function() {
	$(".resource-content > .panel-heading").click(function() {
      	if ($(this).parent().hasClass("resource-content-big")) { // If is small
      		$(this).parent().switchClass("resource-content-big", "resource-content", 0);  // Big will be small
      		$(this).parent().css("z-index", "1");
      	} else {
            $(this).parent().switchClass("resource-content", "resource-content-big", 0); // Small will be big
            $(this).parent().css("z-index", toFrontZIndex++);  // to front (we make it more and more to front in case user opens multiple boxes that overlap
      	}
	});
});