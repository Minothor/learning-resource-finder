$(document).ready(function(){
    var currentID = null;

    // Close all popover except current.
    $('.popover-link').click(function(){
        $('.popover-link').not(this).popover('hide');
    });

    
    // Prevents scrolling up when we click on the stars. 
    $('.popover-link').click(function(e){
    	e.preventDefault();
    });
    
    
    
    //
    $('.pop').popover({
        html : true, // Active format HTML.
        content: function() {
            currentID = $(this).attr('id'); // Take the ID of current link.
            $('.rating-input > .glyphicon').css('color','#17A7D6'); // Add color CSS.
            return $('.pop_content[data-container='+currentID+']'); // Return the current content.
        }
    });
    
    $('.novotepop').popover({
    	html :true,
    	content: "Pour voter, il faut être connecté et avoir un niveau 2 de contribution."
    });

    

    // On close : replace the content to div ".pop_display".
    $('.pop').on('hidden.bs.popover', function () {
        $('.pop_content[data-container='+currentID+']').appendTo('.pop_display[data-container='+currentID+']');
    });

    // Close popover active if click somewhere else.

    $(':not(#anything)').on('click', function (e) {
        $('.popover-link').each(function () {
            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.pop').has(e.target).length === 0) {
                $(this).popover('hide');
                return;
            }
        });
    });
    // Take rating since star on click.
    // Ajax request
    $('i.glyphicon[data-value]').on('click', function() {
    	var score = $(this).attr('data-value');   	
    	$.ajax({
    		type: "POST",
    		url: "/ajax/rateresource",
    		data: "idresource="+currentID+"&score="+score,
    		success: function(data) {
    			location.reload();
    		},
    		error: function(data) {
    			alert('Vote Erreur');
    		}
    	});
    	
    });
});
