
// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxPostUrlResource() {
	var name = $('#name').val();
	var url  = $('#url').val();
	var idresource = $('#idresource').val();
	alert(url);		
	$.ajax({
		type : "POST",
	    url : '/ajax/addurl',
	    data: "idresource="+idresource+"&name="+name+"&url="+url,
	    success : function(data) {
	    	$('#response').html(data);
	        $("input[type='text']").val('');
	    },
	    error : function(data) {
	    	$('#response').html(data);
	    }
	}); 
}

