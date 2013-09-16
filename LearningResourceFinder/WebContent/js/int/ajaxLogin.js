function ajaxlogin() {
	var userNameOrMail = $('#userNameOrMail').val();
	var password  = $('#password').val();
			
	$.ajax({
		type : "POST",
	    url : 'ajax/loginsubmit',
	    data: "userNameOrMail="+userNameOrMail+"&password="+password,
	    success : function(data) {
	    	
	    		 location.reload();
	    	
	    },
	    error : function(data) {
	    	alert(data);
	    }
	}); 
}

function ajaxRegister() {
	var emailRegister = $('#emailRegister').val();
	var usernameRegister  = $('#usernameRegister').val();
	var passwordRegister  = $('#passwordRegister').val();
			
	$.ajax({
		type : "POST",
	    url : 'ajax/registersubmit',
	    data: "emailRegister="+emailRegister+"&usernameRegister="+usernameRegister+"&passwordRegister="+passwordRegister,
	    success : function(data) {	    	
	    		location.reload();		    	
	    },
	    error : function(data) {
	    	alert(data);
	    }
	}); 
}