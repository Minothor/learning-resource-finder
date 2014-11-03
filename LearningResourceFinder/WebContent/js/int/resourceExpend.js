$(document).ready(function() {
	$(this).click(function(){
		$.ajax({
			url : "/ajax/expendresourceinfo",
			dataType: "html",
			type : 'POST',
			data : "resourceid="+resourceId,
			success : function(data) {
			
			}
		});
	});
});