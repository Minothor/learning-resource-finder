function ajaxlogin() {
	var userNameOrMail = $('#userNameOrMail').val();
	var password  = $('#password').val();
			
	$.ajax({
		type : "POST",
	    url : 'ajax/loginsubmit',
	    data: "userNameOrMail="+userNameOrMail+"&password="+password,
	    success : function(data) {
	    	if(data == "success") {
	    		 location.reload();
	    	}
	    	else {
	    		alert('Erreur lors de la connexion.');
	    	}
	    },
	    error : function(data) {
	    	alert(data);
	    }
	}); 
}