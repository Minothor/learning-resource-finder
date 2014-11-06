//$(document).ready(function()


function ratingVote (){
    var currentID = null;
    var popoverContent = null;
    // Close all popover except current.
    $('.rating-link').click(function(){
        $('.rating-link').not(this).popover('hide');
    });

    
    // Prevents scrolling up when we click on the stars: <a href="#" ...
    $('.rating-link').click(function(e){
    	e.preventDefault();
    });
    
    
    
    // This is a rating-link that should trigger a rating popover.
    $('.ratingpop').popover({
        html : true, // Active format HTML.
        content: function() {
            currentID = $(this).attr('id'); // Take the ID of current link.
            $('.rating-input > .glyphicon').css('color','#17A7D6'); // Add color CSS.
            popoverContent = $('.pop_content[data-container='+currentID+'] ');
           return $('.pop_content[data-container='+currentID+'] '); // Return the current content.
            
            
        }
    });
    
    // This is a rating-link that should show "sorry, not allowed".
    $('.noratingpop').popover({
    	html :true,
    	content: "Pour voter, il faut être connecté et avoir un niveau 2 de contribution."
    });

    

    // On close : replace the content to div ".hidden_pop_content_container".
    $('.ratingpop').on('hidden.bs.popover', function () {
       //$('.pop_content[data-container='+currentID+']').appendTo('.hidden_pop_content_container[data-container='+currentID+']');
       popoverContent.appendTo('.hidden_pop_content_container[data-container='+currentID+']');
       popoverContent = null;
    });

    // Close popover active if click somewhere else. !!!! COMMENTED (else, on large pages, it causes performance problems when expanding a resource within a list)
//    $('body').on('click', ':not(#anything)', function (e) {
//        $('.rating-link').each(function () {
//            if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.ratingpop').has(e.target).length === 0) {
//                $(this).popover('hide');
//                return;
//            }
//        });
//    });
    
    
    // Use the rating bootstrap plugin to transform the inputfield into stars
    //https://github.com/javiertoledo/bootstrap-rating-input
    $('input.rating').rating();
    
    
    // Take rating since star on click.
    // Ajax request
    $('i.glyphicon[data-value]').on('click', function() {
    	var score = $(this).attr('data-value'); 
    	
    	$.ajax({
    		type: "POST",
    		url: "/ajax/rateresource",
    		data: "idresource="+currentID+"&score="+score,
    		success: function(data) {
    			popoverContent.find(".ratingText").html(data) ;
    		},
    		error: function(data) {
    			alert('Vote Erreur');
    		}
    	});
    });
    
    

    
};
//});
