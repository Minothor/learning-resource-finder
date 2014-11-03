$(document).ready(function() {
	
	// Expand small to big (what happens when we click on small
	$('.resource-content-small .panel-heading').click(function(){
		var resourcecontainer = $(this).parents(".resourcecontainer");
		var resourcecontentexp =  resourcecontainer.find(".resource-content-exp");
		if (resourcecontentexp.length == 0) { // Not yet loaded through ajax (first time we click on it)
			var resourceinput = $(this).find('input');
			resourceid = resourceinput.val();
			$.ajax({
				url : "/ajax/expendresourceinfo",
				dataType: "html",
				type : 'POST',
				data : "resourceid="+resourceid,
				success : function(data) {
//					alert (resourceid);
					$(data).appendTo(resourcecontainer);
					showResourceContainerExp(resourcecontainer);
					// Reduce big to small (what happens when we click on small
					resourcecontainer.find('.resource-content-exp .panel-heading').click(function(){
					    showResourceContainerSmall(resourcecontainer);
					});
				}
			});		
		} else {
			showResourceContainerExp(resourcecontainer);
		}
	});
});


var toFrontZIndex = 2;

function showResourceContainerExp(resourcecontainer){

    // Hide small
    resourcecontainer.find(".resource-content-small").hide();
        
    // Show expanded
    resourcecontainer.find(".resource-content-exp").show();
    
    resourcecontainer.css("z-index", toFrontZIndex++);  // to front (we make it more and more to front in case user opens multiple boxes that overlap

    }
function showResourceContainerSmall(resourcecontainer){
    resourcecontainer.css("z-index", "1");
    // Hide expanded
    resourcecontainer.find(".resource-content-exp").hide();
    // Show small
    resourcecontainer.find(".resource-content-small").show();
}
