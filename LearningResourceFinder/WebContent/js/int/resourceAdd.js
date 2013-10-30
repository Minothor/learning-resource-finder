 $(document).ready(function() {
	  $('#addResourceForm1').submit(ajaxResourceAddSubmitModal1);
	  $('#addResourceForm2').submit(ajaxResourceAddSubmitModal2);
 });
   
   
// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxVerifyUrl() {
	var url = $('#urlAddField').val();
	if (isValidURL(url) == false) {
		alert("url non valide ");
		return;
	}
	
	if (url != '') {

		$.ajax({
					type : "POST",
					url : '/ajax/checkUrl',
					data : "url=" + url,
					success : function(response) {
						if (response == "video" || response == "ok") { // No other resource with the same url found in DB.
							$("#urlErrorMessage").html("");  // empty the error message
                            toggleForm();
                            if (response == "video") {  // Pre-select the "vidéo" radio button
                            	$('input:radio[name="format"]').filter('[value="VIDEOS"]').attr('checked', true);
                            }
						} else {
                        	$("#urlErrorMessage").html("<a href='"+response+"'>Une resource avec une url similaire</a> existe déjà sur le site"); 
						}

					},
					error : function(data) {
						alert("Problème en contactant le serveur - " + response);
					}
				});
	} else {
		alert('Veuillez entrer une URL valide, commençant par "http://"');
	}

}

function isValidURL(url) {
	var result = url.match(/^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/);
	return result == null ? false : true;
}

function clearForm() {
   $(':input','#addResourceForm')
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
}



function toggleForm() {
	$("#bottomButtons").toggle();
	$("#addResourceFormPart2").toggle();
	$("#urlCheckButton").toggle();
}

function ajaxResourceAddSubmitModal1(e) {
	e.preventDefault();
	$.ajax({
				type : "POST",
				url : '/ajax/resourceaddsubmit1',
				data : $("#addResourceForm1").serialize(),
				success : function(messageAndId) {
					$('#addResourceModal1').modal('hide');			
					$('#addResourceModal2').modal('show');

					showNotificationText(messageAndId.message, "success");
					// Fill and show modal 2
					$('#idresource').val(messageAndId.id);
				},
				error : function(data) {
					alert("Suite à un problème du côté serveur, la ressource n'a probablement pas pu être ajoutée. - "
							+ data);
				}
			});
}


function ajaxResourceAddSubmitModal2(e){
	e.preventDefault();
	$.ajax({
		type : "POST",
		url : '/ajax/resourceaddsubmit2',
		data : $("#addResourceForm2").serialize(),
		success : function(messageAndId) {
			showNotificationText(messageAndId.message, "success");
			$('#addResourceModal2').modal('hide');
		},
		error : function(data) {
			alert("Suite à un problème du côté serveur, la ressource n'a probablement pas pu être ajoutée. - "
					+ data);
		}
	});
	
	
	
	
}
