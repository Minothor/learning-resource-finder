$(document).ready(function() {
	$('#addResourceFavorite').on('click', function() {
		var idResource = $('#idResource').val();
		$.ajax({
			type: 'POST',
			url:'/ajax/addfavorite',
			data: 'idResource='+idResource,
			success: function(data) {
				alert('ok '+data);
				location.reload();
			},
			error: function(data) {
				alert('error '+data);
				location.reload();
			}
		});
	});
});