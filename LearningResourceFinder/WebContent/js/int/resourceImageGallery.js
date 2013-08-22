$(document).ready( function(){ // quand la page a fini de se charger
	$("#list-photos").sortable({ // initialisation de Sortable sur #list-photos
		placeholder: 'highlight', // classe à ajouter à l'élément fantome
    	update: function() {  // callback quand l'ordre de la liste est changé
    		var order = $('#list-photos').sortable('serialize'); // récupération des données à envoyer
    		$.post('ajax.php',order); // appel ajax au fichier ajax.php avec l'ordre des photos
    	}
	});
	$("#list-photos").disableSelection(); // on désactive la possibilité au navigateur de faire des sélections
}); 