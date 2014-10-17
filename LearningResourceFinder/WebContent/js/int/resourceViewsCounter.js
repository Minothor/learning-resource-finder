function updateViewcountAndPpularity(id){
	$.ajax({	
		type : "GET", 
		dataType: "text",
		url : "/ajax/increment",
		data : "idResource="+id,
		success : function(data) {
			$('#viewCounter'+id).text(" "+data);
		},			
		error : function(data) {
			alert("Problème en contactant le serveur" );
		}
	});
}