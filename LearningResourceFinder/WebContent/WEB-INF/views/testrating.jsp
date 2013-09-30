<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Insert title here</title>
<script type="text/javascript">

function ajaxRatingResource() {
	var resource = $('#resource').val();
	var score    = $('#score').val();
	 $.ajax({
		type : "POST",
	    url : 'ajax/rateresourcesubmit',
		data: "resource="+resource+"&score="+score,
		success : function(data) {
			alert("erreur vote : " + data);
		 if (data == "success") {
		   location.reload();
			} else {
			 alert("erreur vote : " + data);
			}
		},
	}); 
}
	
</script>
</head>
<body>
  <form id"ratingResourceFrom" action="rateresourcesubmit" method="post">
    <label>Resource</label><input type="text" name="resource" size="20"/>
    <label>Score</label><input type="text" name="score" size="20"/>
     <input type="submit" name="bouton" value="envoyer" onclick="ajaxRatingResource()"/>
  </form>
</body>
</html>