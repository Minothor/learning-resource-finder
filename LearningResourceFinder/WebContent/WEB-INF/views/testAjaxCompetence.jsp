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
	<a href="javascript:ajaxCompetenceRemoveSubmit(21)">Supprimer id 21</a>
  <!-- Button trigger modal -->
  <a data-toggle="modal" href="#myModal" class="btn btn-primary btn-lg">Launch demo modal</a>

  <!-- Modal -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
  
  

	

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://code.jquery.com/jquery.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	function ajaxCompetenceAddSubmit(idparent) {
		var name = $('#name').val();
		var code = $('#code').val();
		if (name != '' && code != '' && idparent != '' ) {
			$.ajax({
				type : "POST",
			    url : 'ajax/competenceAddSubmit',
			    data: "name=" + name + "&code=" +code + "&idparent=" +idparent,
			    success : function(data) {
			    	alert("Merci pour votre intérêt, nous vous tiendrons informé des prochaines grandes étapes.");
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ai eu une petite erreur sur notre serveur lorsque vous avez introduit \n " +
	    			"votre e-mail.Cela montre bien que nous ne sommes pas prêts ;-) Toutes nos excuses.");
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
			    	alert("Merci pour votre intérêt, nous vous tiendrons informé des prochaines grandes étapes.");
			    	return false;
			    },
			    error : function(data) {
			    	alert("Il semble qu'il y ai eu une petite erreur sur notre serveur lorsque vous avez essayé \n " +
	    			"de supprimer une compétence.Cela montre bien que nous ne sommes pas prêts ;-) Toutes nos excuses." + id);
	        return false;
			    },
			}); 
		}else {
			alert("Pas d'id reçu pour suppression !");
		} 
	}
	</script>

</body>
</html>