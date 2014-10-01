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
   
 
function ajaxVerifyUrl() {
	var url = $('#urlAddField').val();
	
	$('#urlErrorMessage').html('');
	
	if (url != '') {

		$.ajax({
			type : "POST", 
			dataType: "json",
			url : '/ajax/checkUrl',
			data : "url=" + url,
			success : function(response) {
				if (response.type == "video" || response.type == "ok") { // No other resource with the same url found in DB.					
					$('#urlAddField').attr('readonly', true);
					switchToForm2();
					if (response.type == "video") {  // Pre-select the "vidéo" radio button and pre-fills fields from YouTube
						$('#addResourceForm1 input:radio[name="format"]').filter('[value="VIDEOS"]').attr('checked', true);
						$('#addResourceForm1 input:text[name="title"]').val(response.title);
						$('#addResourceForm2 textarea[name="description"]').val(response.description);
						$('#addResourceForm2MaxDuration').val(response.duration);
					}
				} else {
					if(response.type == "urlGeneric")
						$("#urlErrorMessage").html("L'Url introduite est trop générique");
					else if(response.type == "duplicateUrl")		
						$("#urlErrorMessage").html("<a href='"+response.value+"'>Une ressource avec une url similaire</a> existe déjà sur le site");
// 					We decided not to check whether the url is valid or not because the method is not reliable. 
//					see isUrlValid method in web.UrlUtil for full explanation. 
//					also the server hosting the resource can be temporarily down when we check, so we prefer not to do it.
//					else if(response.type == "invalidUrl")
//						$("#urlErrorMessage").html("Aucun site web ne semble répondre pour l'instant avec cette URL. Est-elle valide?");
					
				}

			},
			error : function(data) {
				alert("Problème en contactant le serveur - " + response);
			}
		});
	} else {
		$("#urlErrorMessage").html("Veuillez entrer une URL valide");
	}

}


function resetForm() {
   $('#urlAddField').attr('readonly', false);
   $('#urlAddField').val('');
   $(':input')
	 .not(':button, :submit, :reset, :hidden')
	 .val('')
	 .removeAttr('checked')
	 .removeAttr('selected');
   $('#urlErrorMessage').html('');	
   $('#addResourceFormPart2').hide();    
   $('#bottomButtons').hide();  		
   $('#urlCheckButton').show();
   $('#addResourceModal1').modal('hide');
   $('#addResourceModal2').modal('hide');	
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
	    	
	    } else {	    	
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
