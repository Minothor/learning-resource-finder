// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxVerifyUrl() {
	var url = $('#url').val();
	if (url != '') {

		$.ajax({
					type : "POST",
					url : 'ajax/checkUrl',
					data : "url=" + url,
					success : function(data) {
						if (data == "") { // No other resource with the same url found in DB.
							if (isValidURL(url) == false) {
								alert("url non valide ");   // TODO: display error in the form;
							} else {
                                toggleForm();
                                // Initiate an ajax to get the additional form inputs.
                                $.get('ajax/addresourceform', function(htmlInputs) {
	                                  $('#addResourceFormPart2').html(htmlInputs);
	                                  alert('Load was performed.');  // TODO remove me.
                                });
							}
						} else {
							alert("une resource avec une url similaire existe déjà sur le site");  // TODO: display error in the form;   
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
	var urlRegxp = /^(http:\/\/www.|https:\/\/www.|http:\/\/){1}([\w]+)(.[\w]+){1,2}$/;
	if (urlRegxp.test(url) != true) {
		return false;
	} else {
		return true;
	}
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

function ajaxResourceAddSubmit() {
	$.ajax({
				type : "POST",
				url : 'ajax/resourceaddsubmit',
				data : $("#addResourceForm").serialize(),
				success : function(msg) {
					showNotificationText(msg);  // function defined in header.jsp
					$('#addResourceModal').modal('hide');
					clearForm();
					toggleForm();
				},
				error : function(data) {
					alert("Suite à un problème du côté serveur, la ressource n'a probablement pas pu être ajoutée. - "
							+ data);
				}
			});

}
