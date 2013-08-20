jQuery(function($) {
	
	  // According to the radio button on "from my computer" or "from url", it changes the right part of the dialog.
	  $('input.radioUrl').on('change', function() {
	    document.getElementById('inputFile').type='hidden';
	    document.getElementById('inputUrl').type='text';
	  });
	  $('input.radioComputer').on('change', function() {
	    document.getElementById('inputUrl').type='hidden';
	    document.getElementById('inputFile').type='file';
	  });
	  
	  // Clean input string URL
	  $('#inputUrl').on('click', function() {
	    $('#inputUrl').val(''); 
	  });	  
	  
	  // Change radio button if leave by a.close. (to prevent a bug that if we close the dialog and we come back, the radios and the right part are still in sync).
	  $('a.close').on('click', function() {
		 document.getElementById('inputComputer').checked = true; 
	  });
});

