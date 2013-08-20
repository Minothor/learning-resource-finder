jQuery(function($) {
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
	  
	  // Change radio button if leave by a.close
	  $('a.close').on('click', function() {
		 document.getElementById('inputComputer').checked = true; 
	  });
});


// Code present on RYC
$(function() {
    $( "#tabs" ).tabs();
});