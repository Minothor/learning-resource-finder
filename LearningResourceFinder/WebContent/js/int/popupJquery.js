jQuery(function($){			   		   

	// Opens the pop-up dialog when clicking on the link having the poplight class.
	// This function gives the dialog some standard style attributes (margin, fadeIn, etc.)
	// Reusable for any dialog of this site.
	$('a.poplight').on('click', function() {
		var popID = $(this).data('rel'); // Find pop-up
		var popWidth = $(this).data('width'); // Find width
    
		// Show popup and add close button
		$('#' + popID).fadeIn().css({'width': popWidth});
		
		// Recover margin for center on window 
		var popMargTop = ($('#' + popID).height() + 80) / 2;
		var popMargLeft = ($('#' + popID).width() + 80) / 2;
		
		//Apply Margin to popup
		$('#' + popID).css({ 
			'margin-top' : -popMargTop,
			'margin-left' : -popMargLeft
		});
		
		// Show background - .css({'filter' : 'alpha(opacity=80)'}) for correct bug with old version of IE
		$('body').append('<div id="fade"></div>');
		$('#fade').css({'filter' : 'alpha(opacity=80)'}).fadeIn();
		
		return false;
	});
	
	// Close popup and Fade Layer
	$('body').on('click', 'a.close, #fade', function() { //Au clic sur le body...
		$('#fade , .popupJquery').fadeOut(function() {
			$('#fade').remove();   
	    });
		
		location.reload();
	    return false;
	});
});


