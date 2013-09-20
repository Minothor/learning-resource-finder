<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<body>

 <lrf:competencetree/> 
 
  <!-- Button trigger modal -->

 
  <!-- Modal -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
     <form action="competencemovesubmit" method="post">
       <div class="modal-content">
      
         <div class="modal-header">
    		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
         	<h4 class="modal-title">Déplacer une compétence</h4>
         </div>
         
        <div class="modal-body">
	        <div class="form-group">
	          <label for="codeField">Code parent</label>
			  <input type="text" class="form-control" name="codeField"id="codeField" placeholder="Code"  maxlength="8">
			  <span class="help-block">Code de la compétence qui sera parent après le déplacement.</span>
			</div>
			<input type="hidden" class="form-control" name="hiddenFieldMoveCompetency" id="hiddenFieldMoveCompetency" value=""> <%-- Value us set by javascript --%>
		</div>
		
		<div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button  type="submit" class="btn btn-primary" value="Deplace" >Déplacer la compétence</button>
        </div>
       </div><!-- /.modal-content -->
    </form>
  </div><!-- /.modal-dialog -->
 </div><!-- /.modal -->
 
 <!-- Modal -->
  <div class="modal fade" id="myAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Ajouter une compétence</h4>
        </div>
        <div class="modal-body">
			<div class="form-group">
			  <input type="text" class="form-control" id="name" placeholder="Nom" maxlength="40">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" id="code" placeholder="Code"  maxlength="8">
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" id="description" placeholder="Description" value="">
			</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button type="button" class="btn btn-primary" id="addsubmit">Ajouter Compétence</button>
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
    </div>
  
  
    <!-- Modal -->
  <div class="modal fade" id="myEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Editer une compétence</h4>
        </div>
        <div class="modal-body">
  
		<div class="form-group">
		  <input type="text" class="form-control" id="nameedit" placeholder="Nom" value=""  maxlength="40">
		</div>
		
		<div class="form-group">
		  <input type="text" class="form-control" id="codeedit" placeholder="Code" value=""  maxlength="8">
		</div>
		
		<div class="form-group">
		  <input type="text" class="form-control" id="descriptionedit" placeholder="Description" value="">
		</div>
		
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button type="button" class="btn btn-primary" id="editsubmit">Editer Compétence</button>
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  </div>
  
  <!-- Modal -->
  <div class="modal fade" id="myRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Êtes vous sûr?</h4>
        </div>
		<div class="alert alert-danger">Attention : Cela supprimera définitivement la Compétence !</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button type="button" class="btn btn-primary" id="removesubmit">Supprimer Compétence</button>
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  </div>
  
  
  <script type="text/javascript">
  
	$(document).ready(function() {
		// Handler for .ready() called.
		
		// Add initialization
		$("a[id^='A-']").attr('data-toggle', 'modal'); 
		$("a[id^='A-']").attr('href', '#myAdd');
		$("a[id^='A-']").click(click_addCompetence);
		
		// Modify initialization
		$("a[id^='E-']").attr('data-toggle', 'modal'); 
		$("a[id^='E-']").attr('href', '#myEdit');
		$("a[id^='E-']").click(click_editCompetence);
		
		// Deplace initialization
		$("a[id^='D-']").attr('data-toggle', 'modal'); 
		$("a[id^='D-']").attr('href', '#myModal');
		$("a[id^='D-']").click(click_me); 
		
		// Delete initialization
		$("a[id^='R-']").attr('data-toggle', 'modal'); 
		$("a[id^='R-']").attr('href', '#myRemove');
		$("a[id^='R-']").click(click_removeCompetence);
		
		// setCycle Init
		$("a[id^='CY-']").click(click_setCycle);
	});
  
  function click_me (){
	 var compId=this.id.substring(2); 
	 $("#hiddenFieldMoveCompetency").attr("value", compId);
  }
  
  function click_addCompetence(){
		var parentId = this.id.substring(2);
		$("button[id='addsubmit']").attr("onclick","ajaxCompetenceAddSubmit(" + parentId +")");
	}
	
	function click_editCompetence(){
		var compId = this.id.substring(2);
		$("button[id='editsubmit']").attr("onclick","ajaxCompetenceEditSubmit(" + compId +")");
		ajaxCompetenceEditLoad(compId);
	}
	
	function click_removeCompetence(){
		var compId = this.id.substring(2);
		$("button[id='removesubmit']").attr("onclick","ajaxCompetenceRemoveSubmit(" + compId +")");
	}
	
	function click_setCycle(){
		var temp = this.id.split('CP-');
		var compId =  temp[1];
		var cycleId = temp[0].substring(3);
		$.ajax({
			type : "GET",
		    url : 'ajax/setCycle',
		    data: "idcomp=" +compId + "&idcycle=" +cycleId,
		    success : function(data) {
		    	if (data == "success") {
		    		showNotificationText("Assignation du cycle réussie.");
		    	} else {
		    		showNotificationText(data);
		    	}
		    	return false;
		    },
		    error : function(data) {
		    	alert("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'assigner un cycle." + data);
		    	//window.location.reload();
        		return false;
		    },
		}); 
        return false;  // Prevents the link to be followed
	}
	
	function ajaxCompetenceAddSubmit(idparent) {
		var name = $('#name').val();
		var code = $('#code').val();
		var description = $('#description').val();
		if (name != '' && code != '' && idparent != '' ) {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceAddSubmit',
			    data: "name=" + name + "&code=" +code + "&idparent=" +idparent + "&description=" +description,
			    success : function(data) {
			    	if (data == "success") {
			    		alert("Compétence ajoutée");
			    		window.location.reload();
			    	} else {
			    		alert("souci lors de la soumission du formulaire : " + data);
			    	}
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'ajouter une compétence.");
	        		return false;
			    },
			}); 
		}else {
			alert("Vous n'avez pas entré de nom ou/et de code");
		} 
	}
	
	function ajaxCompetenceRemoveSubmit(id) {
		if (id != '') {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceRemoveSubmit',
			    data: "id=" + id,
			    success : function(data) {
			    	if (data == "success") {
			    		alert("Compétence supprimée");
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
			    		alert("Compétence editée");
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
	
 </script> 
 </body> 
 </html> 