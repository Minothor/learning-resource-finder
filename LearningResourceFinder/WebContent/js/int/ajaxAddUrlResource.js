
// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxPostUrlResource() {
	var name = $('#name').val();
	var url  = $('#urlResource').val();
	var idresource = $('#idResource').val();
	$.ajax({
		type : "POST",
	    url : '/ajax/addurl',
	    data: "idresource="+idresource+"&name="+name+"&url="+url,
	    success : function(data) {
	    	$('#response').html(data);
	        $("input[type='text']").val('');
	        location.reload();
	    },
	    error : function(data) {
	    	$('#response').html(data);
	    }
	}); 
}

