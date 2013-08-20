jQuery(function($) {
	  // Choix de la provenance de l'image :
	  $('input.radioUrl').on('change', function() {
	    document.getElementById('inputFile').type='hidden';
	    document.getElementById('inputUrl').type='text';
	  });
	  
	  $('input.radioComputer').on('change', function() {
	    document.getElementById('inputUrl').type='hidden';
	    document.getElementById('inputFile').type='file';
	  });
	  
	  //Si on clique sur le input URL, on le vide
	  $('#inputUrl').on('click', function() {
	    $('#inputUrl').val(''); 
	  });	  
	  
	  $('a.close').on('click', function() {
		 document.getElementById('inputComputer').checked = true; 
	  });
});


// Code present on RYC
$(function() {
    $( "#tabs" ).tabs();
});