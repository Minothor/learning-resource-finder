$(document).ready(function() {	
	    $(".nonaddfavorite").popoverWithAutoHide("Pour avoir des favoris, il faut être connecté.");
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