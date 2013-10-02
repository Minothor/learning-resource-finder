$(document).ready( function() {
	
	var ids = [];
	
	$("#list-photos").sortable({ // initialisation de Sortable sur #list-photos
		placeholder: 'highlight', // classe à ajouter à l'élément fantome
    	update: function() {  // callback quand l'ordre de la liste est changé
    		orderImageRefresh();
    		$.ajax({
    			type:"POST",
    			headers:{
    				'Accept': 'application/json',
    				'Content-type': 'application/json'
    			},
    			'url': '/resource/change',
    			'data': JSON.stringify(ids),
    			'success': function(e) {
    				location.reload();
    			} 
    		});
    	}
	});
	
	$("#list-photos").disableSelection(); // on désactive la possibilité au navigateur de faire des sélections

	function orderImageRefresh() {
		ids = [];
		$("#list-photos img").each(function() {
			ids.push(this.id);
		});	
	}

});

