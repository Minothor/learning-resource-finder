$(document).ready(function() {	
	// Declaration tooltip favorite. 
	$('.addResourceFavorite').tooltip();
});

function favoriteToggle(id) {
    $.ajax({
        type: 'POST',
        url:'/ajax/addfavorite',
        data: 'idResource='+id,
        success: function(data) {
            $('#favorite'+id).replaceWith(data);
        },
        error: function(data) {
        	alert(data);
        }
    });
}