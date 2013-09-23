
$(document).ready(function() {
		// Handler for .ready() called.
		
		// Add initialization
		$("a[id^='A-']").attr('data-toggle', 'modal'); 
		$("a[id^='A-']").attr('href', '#myAdd');
		$("a[id^='A-']").click(click_in_tree_addCompetence);
		
		// Modify initialization
		$("a[id^='E-']").attr('data-toggle', 'modal'); 
		$("a[id^='E-']").attr('href', '#myEdit');
		$("a[id^='E-']").click(click_in_tree_editCompetence);
		
		// Deplace initialization
		$("a[id^='D-']").attr('data-toggle', 'modal'); 
		$("a[id^='D-']").attr('href', '#myModal');
		$("a[id^='D-']").click(click_in_tree_moveCompetence); 
		
		// Delete initialization
		$("a[id^='R-']").attr('data-toggle', 'modal'); 
		$("a[id^='R-']").attr('href', '#myRemove');
		$("a[id^='R-']").click(click_in_tree_removeCompetence);
		
		// setCycle Init
		$("a[id^='CY-']").click(click_in_tree_setCycle);
	});
  
  function click_in_tree_moveCompetence (){
	 var compId=this.id.substring(2); 
	 $("#hiddenFieldMoveCompetency").attr("value", compId);
  }
  
  function click_in_tree_addCompetence(){
	    $("#competenceModalTitle").text("Ajouter une compétence");
		var parentId = this.id.substring(2);
        $("#hiddenField").attr("name", "idparent");
        $("#hiddenField").attr("value", parentId);
		$("#competenceForm").submit(ajaxCompetenceAddSubmit);
	}
	
	function click_in_tree_editCompetence(){
	    $("#competenceModalTitle").text("Editer une compétence");
		var compId = this.id.substring(2);
        $("#hiddenField").attr("name", "id");
        $("#hiddenField").attr("value", compId);
		$("#competenceForm").submit(ajaxCompetenceEditSubmit);
		ajaxCompetenceEditLoad(compId);
	}

	
	function click_in_tree_removeCompetence(){
		var compId = this.id.substring(2);
		$("button[id='removesubmit']").attr("onclick","ajaxCompetenceRemoveSubmit(" + compId +")");
	}
	
	function click_in_tree_setCycle(){
		var temp = this.id.split('CP-');
		var compId =  temp[1];
		var cycleId = temp[0].substring(3);
		var newvalue = $(this).text();
		$.ajax({
			type : "GET",
		    url : 'ajax/setCycle',
		    data: "idcomp=" +compId + "&idcycle=" +cycleId,
		    success : function(data) {
		    	if (data == "success") {
		    		showNotificationText("Assignation du cycle réussie.");
		    		$("a[id='CP-" +compId+ "']").text(newvalue); //Refresh dropdown text value with new cycle value without refresh all the page
		    		$("a[id='CP-" +compId+ "']").dropdown('toggle'); //hide dropdown list
		    	} else {
		    		showNotificationText(data);
		    	}
		    	return false;
		    },
		    error : function(data) {
		    	alert("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'assigner un cycle." + data);
		    	
        		return false;
		    },
		}); 
        return false;  // Prevents the link to be followed
	}
	
	function ajaxCompetenceAddSubmit(event) {
		ajaxCompetenceSubmit(event, "ajax/competenceAddSubmit");
	}

	function ajaxCompetenceEditSubmit(event) {
		ajaxCompetenceSubmit(event, "ajax/competenceEditSubmit");
	}

	function ajaxCompetenceSubmit(event, url) {
		event.preventDefault();  // Don't refresh the whole page.
		$.post(url, $( this ).serialize()).done( function(data) {
		    	if (data == "success") {
		    		window.location.reload();
		    	} else {
		    		showNotificationText("Souci lors de la soumission du formulaire : " + data);
		    	}
		    	return false;
	    });
	}
	
	function ajaxCompetenceRemoveSubmit(id) {
		if (id != '') {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceRemoveSubmit',
			    data: "id=" + id,
			    success : function(data) {
			    	if (data == "success") {
			    		window.location.reload();
			    	} else {
			    		alert("Souci lors de la soumission : " + data);
			    	}
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ait eu une erreur sur notre serveur lorsque vous avez essayé \n " +
	    			"de supprimer une compétence.");
	        return false;
			    },
			}); 
		}else {
			alert("Pas d'id reçu pour suppression !");
		} 
	}
	
	function ajaxCompetenceEditLoad(id) {
		if (id != '') {
			$.getJSON("ajax/competenceEditFillFields?id="+id, function(competence) {
				$('#nameedit').val(competence.name);
				$('#codeedit').val(competence.code);
				$('#descriptionedit').val(competence.description);
			});
		}else {
			alert("Pas d'id reçu!");
		} 
	}
	
	function ajaxCompetenceEditSubmit(id) {
		var name = $('#nameedit').val();
		var code = $('#codeedit').val();
		var description = $('#descriptionedit').val();
		if (name != '' && code != '' && id != '') {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceEditSubmit',
			    data: "name=" + name + "&code=" +code + "&id=" +id + "&description=" +description,
			    success : function(data) {
			    	if (data == "success") {
			    		window.location.reload();
			    	} else {
			    		alert("Souci lors de la soumission du formulaire : " + data);
			    	}
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'éditer une compétence.");
	        return false;
			    },
			}); 
		}else {
			alert("Vous n'avez pas entré de nom ou/et de code ou le code est déjà utilisé");
		} 
	}
	