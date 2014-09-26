
$(document).ready(function() {
		// Handler for .ready() called.
		
		// Add initialization
		$("span[id^='A-']").attr('data-toggle', 'modal'); 
		$("span[id^='A-']").attr('href', '#competenceEditModal');
		$("span[id^='A-']").click(click_in_tree_addCompetence);
		
		// Modify initialization
		$("span[id^='E-']").attr('data-toggle', 'modal'); 
		$("span[id^='E-']").attr('href', '#competenceEditModal');
		$("span[id^='E-']").click(click_in_tree_editCompetence);
		
		// Move initialization
		$("span[id^='D-']").attr('data-toggle', 'modal'); 
		$("span[id^='D-']").attr('href', '#competenceMoveModal');
		$("span[id^='D-']").click(click_in_tree_moveCompetence); 
		
		// Remove-Delete initialization
		$("span[id^='R-']").attr('data-toggle', 'modal'); 
		$("span[id^='R-']").attr('href', '#competenceRemoveModal');
		$("span[id^='R-']").click(click_in_tree_removeCompetence);

// TODO Remove me after completion of TP-36 - No nonsense categories
//		// setCycle Init
//		$("a[id^='CY-']").click(click_in_tree_setCycle);
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
	    $("#competenceModalTitle").text("Editer une catégorie");
		var compId = this.id.substring(2);
        $("#hiddenField").attr("name", "id");
        $("#hiddenField").attr("value", compId);
		$("#competenceForm").submit(ajaxCompetenceEditSubmit);
		ajaxCompetenceEditLoad(compId);
	}
	
// TODO Remove me after completion of TP-36 - No nonsense categories	
//	function click_in_tree_removeCompetence(){
//		var compId = this.id.substring(2);
//		$("button[id='removesubmit']").attr("onclick","ajaxCompetenceRemoveSubmit(" + compId +")");
//	}
//	
//	function click_in_tree_setCycle(){
//		var temp = this.id.split('CP-');
//		var compId =  temp[1];
//		var cycleId = temp[0].substring(3);
//		var newvalue = $(this).text();
//		$.ajax({
//			type : "GET",
//		    url : 'ajax/setcycle',
//		    data: "idcomp=" +compId + "&idcycle=" +cycleId,
//		    success : function(data) {
//		    	if (data == "success") {
//		    		showNotificationText("Assignation du cycle réussie.", "success");
//		    		$("span[id='CP-" + compId + "']").html(newvalue + "<span class='caret'/>"); //Refresh dropdown text value with new cycle value without refresh all the page
//		    		$("span[id='CP-" + compId + "']").dropdown('toggle'); //hide dropdown list
//		    	} else {
//		    		showNotificationText(data);
//		    	}
//		    	return false;
//		    },
//		    error : function(data) {
//		    	showNotificationText("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'assigner un cycle." + data, "danger");
//		    	
//        		return false;
//		    },
//		}); 
//        return false;  // Prevents the link to be followed
//	}

	
	function ajaxCompetenceAddSubmit(event) {
		ajaxCompetenceSubmit(this, event, "ajax/competenceaddsubmit");
	}

	function ajaxCompetenceEditSubmit(event) {
		ajaxCompetenceSubmit(this, event, "ajax/competenceeditsubmit");
	}

	function ajaxCompetenceSubmit(form, event, url) {
		event.preventDefault();  // Don't refresh the whole page.
		var data = $(form).serialize();
		$.post(url, data).done( function(data) {
		    	if (data == "success") {
//		    		$(':input','#competenceForm')  // Clear the form field content (probably useless since we reload the page anyway).
//		 		   .not(':button, :submit')
//		 		   .val('');
		    		$("#codeedit").parent().removeClass("has-error");  // Clear Bootstrap class to draw the field in red
		    		$("#codeedithelp").text("");
		    		window.location.reload();
		    	} else {
		    		showNotificationText("Souci lors de la soumission du formulaire : " + data, "danger");  // Probably not visible because of scrolling on that very long page
		    		$("#codeedit").parent().addClass("has-error");  // Bootstrap class to draw the field in red
		    		$("#codeedithelp").text(data);
		    	}
		    	return false;
	    });

	}
	
	function ajaxCompetenceRemoveSubmit(id) {
		if (id != '') {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceremovesubmit',
			    data: "id=" + id,
			    success : function(data) {
			    	if (data == "success") {
			    		window.location.reload();
			    	} else {
			    		showNotificationText("Souci lors de la soumission : " + data, "danger");
			    	}
			    	return false;
			    },
			    error : function(data) {
			    	showNotificationText("Il semble qu'il y ait eu une erreur sur notre serveur lorsque vous avez essayé \n " +
	    			"de supprimer une catégorie.", "danger");
	        return false;
			    },
			}); 
		}else {
			showNotificationText("Pas d'id reçu pour suppression !", "danger");
		} 
	}
	
	function ajaxCompetenceEditLoad(id) {
		if (id != '') {
			$.getJSON("ajax/competenceeditfillfields?id="+id, function(competence) {
				$('#nameedit').val(competence.name);
				$('#codeedit').val(competence.code);
				$('#descriptionedit').val(competence.description);
			});
		} else {
			showNotificationText("Bug (defensive coding): no id received!");
		} 
	}
	
	