
var temp;
$(document).ready(function() {

	$(".resource-content .panel-heading").click(function() 
	{		
		if(temp!=null)
		{
			temp.css("z-index","1");
			parent.switchClass("resource-content-big", "resource-content", 0);
		}
		
		parent = $(this).parent();
		if (!parent.hasClass("resource-content-big"))
		{
			parent.css("z-index","10");
			parent.switchClass("resource-content", "resource-content-big", 0);
		}
		temp = parent;
	});
});
