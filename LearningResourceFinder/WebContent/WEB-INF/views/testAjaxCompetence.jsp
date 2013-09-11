<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
<title>Insert title here</title>
</head>
<body>
    <h1>Hello, world!</h1>
	<a href="javascript:ajaxCompetenceRemoveSubmit(23)">Supprimer id 23</a>
  <!-- Button trigger modal -->
  
  <button type="button" data-toggle="modal" href="#myAdd" class="btn btn-primary btn-lg">Ajouter</button>
  <button type="button" data-toggle="modal" href="#myEdit" class="btn btn-primary btn-lg">Editer</button>
  
  <ul class="options">
  <li><a href="#" id="E-12"> Edit </a></li> 
  <li><a href="#" id="E-13"> Edit2 </a></li> 
  <li><a href="#" id="R-12"> Remove </a></li>
  </ul>
  
 
  <!-- Modal -->
  <div class="modal fade" id="myAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Ajouter une compétence</h4>
        </div>
        <div class="modal-body">
			<div class="input-group">
			  <input type="text" class="form-control" id="name" placeholder="Nom">
			</div>
			<div class="input-group">
			  <input type="text" class="form-control" id="code" placeholder="Code">
			</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">Fermer</button>
          <button type="button" class="btn btn-primary" onclick="ajaxCompetenceAddSubmit(23)">Ajouter Compétence</button>
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
  
		<div class="input-group">
		  <input type="text" class="form-control" id="nameedit" placeholder="Nom" value="">
		</div>
		
		<div class="input-group">
		  <input type="text" class="form-control" id="codeedit" placeholder="Code" value="">
		</div>
		
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">Fermer</button>
          <button type="button" class="btn btn-primary" onclick="ajaxCompetenceEditSubmit(18)">Editer Compétence</button>
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  </div>
  

  

	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://code.jquery.com/jquery.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	
	
	$(document).ready(function() {
		// Handler for .ready() called.
		
		// Modify initialization
		$("a[id^='E-']").attr('data-toggle', 'modal'); // fixme $('#myModal').modal(options)
		$("a[id^='E-']").attr('href', '#myEdit');
		$("a[id^='E-']").click(click_editCompetence);
	});
	
	function click_editCompetence(){
		alert(this.id);
		var compId = this.id.substring(2);
		//$(this.id).modal('show');

		ajaxCompetenceEdit(compId);
		
// 		$("input[id='nameedit']").val("Name :-)"); 
// 		$("input[id='codeedit']").val("Code :-)"); 
	}
	
	
		
	function ajaxCompetenceAddSubmit(idparent) {
		var name = $('#name').val();
		var code = $('#code').val();
		if (name != '' && code != '' && idparent != '' ) {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceAddSubmit',
			    data: "name=" + name + "&code=" +code + "&idparent=" +idparent,
			    success : function(data) {
			    	alert("Compétence ajoutée");
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
			    	alert("Cométence supprimée");
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
	function ajaxCompetenceEdit(id) {
		alert("j'y suis!");
		if (id != '') {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceEditFillFields',
			    data: "id=" +id,
			    success : function(response) { 			    	
			    	if(response.status == "SUCCESS"){
			    		alert("SUCCESS");
			    		$('#nameedit').val(response.result.name);
			    	return false;
			    	} else {alert("ERROR");}
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ait eu une erreur sur notre serveur lorsque vous avez tenté d'éditer une compétence.");
	        return false;
			    },
			}); 
		}else {
			alert("Pas d'id reçu!");
		} 
	}
	function ajaxCompetenceEditSubmit(id) {
		var name = $('#nameedit').val();
		var code = $('#codeedit').val();
		if (name != '' && code != '' && id != '' ) {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceEditSubmit',
			    data: "name=" + name + "&code=" +code + "&id=" +id,
			    success : function(data) {
			    	alert("Compétence editée");
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ait eu une petite erreur sur notre serveur lorsque vous avez tenté d'éditer une compétence.");
	        return false;
			    },
			}); 
		}else {
			alert("Vous n'avez pas entré de nom ou/et de code");
		} 
	}
	
	</script>

</body>
</html>