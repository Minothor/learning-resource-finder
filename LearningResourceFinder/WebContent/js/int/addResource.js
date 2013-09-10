// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxPostAddResource() {
	var url  = $('#url').val();
	if(url != '') {
		
		$.ajax({
			type : "POST",
		    url : 'ajax/checkUrl',
		    data: "url="+url,
		    success : function(data) {
	    		
		    	if(data == "") {  // No other resource with the same url found in DB.
		    		 $("#titleDiv").show();
		    	} else{
		    		alert("une resource avec une url similaire existe déjà sur le site");
		        }
		    	
		    },
		    error : function(data) {
		    	alert("Problème en contactant le serveur - "+ data);
		    }
		}); 
	}
	else {
		alert('Veuillez entrer une URL valide !');
	}
	
}


function showTilte() {
	$("#titleDiv").show();
}

 


