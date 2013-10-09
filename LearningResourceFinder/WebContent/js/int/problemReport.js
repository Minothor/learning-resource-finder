$(document).ready(function() {
	$('#formProblemReport').submit(problemReport);
});

function problemReport(e) {	
	e.preventDefault();
	var title = $('#titreProblemReport').val();
	var description = $('#descriptionProblemReport').val();
	var idResource = $('#idResourceProblemReport').val();
	$.ajax({
		type: "POST",
		url: "/ajax/problemreport",
		data: "title="+title+"&description="+description+"&idresource="+idResource,
		success: function(data) {
			location.reload();
		},
		error: function(data) {
			location.reload();
		}
	});
}