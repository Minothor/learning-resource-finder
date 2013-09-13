jQuery(function($) {	  
	  // Clean input string URL
	  $('#inputUrl').on('click', function() {
	    $('#inputUrl').val(''); 
	  });	  
	  
	// Change radio button if leave by a.close. (to prevent a bug that if we close the dialog and we come back, the radios and the right part are still in sync).
	  $('.closeModal').on('click', function() {
		 document.getElementById('inputComputer').checked = true; 
		 document.getElementById('inputUrl').type='hidden';
		 document.getElementById('inputFile').type='file';
	  });
	  
	  // PAGE : playlistdisplay.jsp
	  $('.radioUrl').on('change', function() {
		  document.getElementById('inputFile').type='hidden';
		  document.getElementById('inputUrl').type='text';
		  $('form.formUrlPlaylist').attr('action', '/playlist/imageaddUrl');
	  });
	  
	  $('.radioComputer').on('change', function() {
		  document.getElementById('inputUrl').type='hidden';
		  document.getElementById('inputFile').type='file';
		  $('form.formUrlPlaylist').attr('action', '/playlist/imageadd');
	  });
});
