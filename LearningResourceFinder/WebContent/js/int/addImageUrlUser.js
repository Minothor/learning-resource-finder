jQuery(function($) {
	  // According to the radio button on "from my computer" or "from url", it changes the right part of the dialog.
	  $('.radioUrl').on('change', function() {
	    document.getElementById('inputFile').type='hidden';
	    document.getElementById('inputUrl').type='text';
	    $('form.formUrlResource').attr('action', '/user/imageaddUrl');
	    $('.avatarDefault').css({'visibility':'hidden'});
	  });
	  
	  $('.radioComputer').on('change', function() {
	    document.getElementById('inputUrl').type='hidden';
	    document.getElementById('inputFile').type='file';
	    $('form.formUrlResource').attr('action', '/user/imageadd');
	    $('.avatarDefault').css({'visibility':'hidden'});
	  });
	  
	  $('.radioDefaut').on('change', function() {
		  $('form.formUrlResource').attr('action', '/user/imagedelete');
		  document.getElementById('inputUrl').type='hidden';
		  document.getElementById('inputFile').type='hidden';
		  $('.avatarDefault').css({'visibility':'visible'});
	  });
	  
	  // Clean input string URL
	  $('#inputUrl').on('click', function() {
	    $('#inputUrl').val(''); 
	  });	  
	  
	  // Change radio button if leave by a.close. (to prevent a bug that if we close the dialog and we come back, the radios and the right part are still in sync).
	  $('.closeModal').on('click', function() {
		 document.getElementById('inputComputer').checked = true; 
		 document.getElementById('inputUrl').type='hidden';
		 document.getElementById('inputFile').type='file';
		 $('.avatarDefault').css({'visibility':'hidden'});
	  });
});

