var previousParent = null;  // parent of the clicked div, but for the previous click.
var temp2;
$(document).ready(function() {
		$(".resource-content .panel-heading").click(function() {
			if (previousParent != null) {  // restore previousParent to small version
				previousParent.css("z-index", "1");
				parent.switchClass("resource-content-big", "resource-content", 0);  // show small version
				previousParent = temp2;

			} else {  // show big version for parent
				parent = $(this).parent();
				if (!parent.hasClass("resource-content-big")) {
					parent.css("z-index", "10");
					parent.switchClass("resource-content", "resource-content-big", 0);  // show big version
				}
				previousParent = parent;	
			}
		});
});

//$(document).ready(function() {
//	$(".resource-content .panel-heading").click(function() {
//			
//		
//		
//		
//	});
//});