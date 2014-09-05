   
// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxVerifyUrlGeneric() {
	
	var url = $('#urlGenericAddField').val();	
	if (isValidURL(url) == false) {
		alert('Veuillez entrer une URL valide, commençant par "http://"');
		return;
	}	
	if (url != '') {
		$.ajax({	
			type : "POST", 
			dataType: "text",
			url : "/checkUrlGeneric",
			data : "url="+url,
			success : function(data) {
				if (data=="ok") { 	// No other resource with the same url found in DB.
					location.reload();
				} else {
					$('#urlGenericErrorMessage').html("Cette url existe déjà dans la liste des génériques"); 
				}
			},
			error : function(data) {
				alert("Problème en contactant le serveur - " + data);
			}
		});
	} else {
		alert('Veuillez entrer une URL valide, commençant par "http://"');
	}
}

function isValidURL(url) {
	url = url.toLowerCase();
	if (url.indexOf("http://") == 0 || url.indexOf("https://")==0 ) {
		return true;
	} else {
		return false;
	}	
}


function resetForm() {  
   $(':input','#addUrlForm1')
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
   $("#addResourceFormPart2").toggle('hide');
   $("#bottomButtons").hide();
   $("#urlCheckButton").show();
}

