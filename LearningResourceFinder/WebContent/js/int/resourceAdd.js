 $(document).ready(function() {
	 $('#addResourceForm1').submit(ajaxResourceAddSubmitModal1);
	 
	 // The error message may appear to tell the user to select at least one platform.
	 // As soon as the user checks a platform, that error message should hide.
	 $(".alert-danger").hide(); // 
	  $('input[name="platform"]').on('click', function(){
		    if ( $(this).is(':checked') ) {
		    	 $(".alert-danger").hide();
		    } 		    
		});
	
 });
   
// Sends an ajax request for the user who wants to upload an image from an URL (text field).
function ajaxVerifyUrl() {
	var url = $('#urlAddField').val();
	if (isValidURL(url) == false) {
		alert('Veuillez entrer une URL valide, commençant par "http://"');
		return;
	}
	
	
	if (url != '') {

		$.ajax({
			type : "POST", 
			dataType: "json",
			url : '/ajax/checkUrl',
			data : "url=" + url,
			success : function(response) {
				if (response.type == "video" || response.type == "ok") { // No other resource with the same url found in DB.
					$("#urlErrorMessage").html("");  // empty the error message
					switchToForm2();
					if (response.type == "video") {  // Pre-select the "vidéo" radio button and pre-fills fields from YouTube
						$('#addResourceForm1 input:radio[name="format"]').filter('[value="VIDEOS"]').attr('checked', true);
						$('#addResourceForm1 input:text[name="title"]').val(response.title);
						$('#addResourceForm2 textarea[name="description"]').val(response.description);
						$('#addResourceForm2MaxDuration').val(response.duration);
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
	// I've tried many clever regexp from the web, but all make a valid url fail soon or later. The one below does not accept this: http://www.asblentraide.be/docs%5CFICHE%205.2%20-%20SUITES%20ARITHMETIQUES%20ET%20SUITES%20GEOMETRIQUES.pdf
	// var result = url.match(/^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/);
	url = url.toLowerCase();
	if (url.indexOf("http://") == 0 || url.indexOf("https://")==0 ) { // url starts with "http://" or "https://"
		return true;
	} else {
		return false;
	}
	
}

function resetForm() {  
   $(':input','#addResourceForm1')
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
   $("#addResourceFormPart2").toggle('hide');
   $("#bottomButtons").hide();
   $("#urlCheckButton").show();
}



function switchToForm2() {  
	$("#bottomButtons").toggle();
	$("#addResourceFormPart2").toggle();
	$("#urlCheckButton").toggle();
}

function ajaxResourceAddSubmitModal1(e) {
	    e.preventDefault();

	    // Form validation (is there at least one check box checked?
	    var validForm = true; // default value is that form is correct, you can chcange it as you wish	    
	    var n =  $("input[type=checkbox]:checked").size(); // count of checked inputs detected by jQuery
	    if (n == 0) { validForm=false; } // only if you checked 1 checkbox, form is evaluated as valid	    
	  
	    if (!validForm) { // if result of validation is: invalid
	    	e.preventDefault(); // stop processing form and disable submitting  

	    	$(".alert-danger").css({"margin-right":"25px"});
	    	$(".alert-danger").css({"padding": "5px"}); 
	    	$(".alert-danger").show("scale", 500); 
	    //	$("label[for='platform']").css({"color": "#e81578"});
	    	
	    }
	    else {	    	
	    	// ok, submit form
	    	$(".alert-danger").hide();   

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
}


//////// The 2nd modal form is not submitted via ajax anymore because we show the added resource page now -- John 2013/12
//function ajaxResourceAddSubmitModal2(e){
//	e.preventDefault();
//	$.ajax({
//		type : "POST",
//		url : '/ajax/resourceaddsubmit2',
//		data : $("#addResourceForm2").serialize(),
//		success : function(messageAndId) {  // 
//			showNotificationText(messageAndId.message, "success");
//			$('#addResourceModal2').modal('hide');
//
//		},
//		error : function(data) {
//			alert("Suite à un problème du côté serveur, les informations de la seconde boite de dialogue n'ont probablement pas été sauvegardées. - "
//					+ data);
//		}
//	});
//}
