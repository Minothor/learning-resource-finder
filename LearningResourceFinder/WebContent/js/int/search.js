$(document).ready(function() {
	$('#search').on('focus',function(){
        var $w=$(this).width();
        $(this).animate({width:$w+60+'px'},300);
})
.on('blur',function(){
    var $w=$(this).width();
    if($(this).val() == '') {
        $(this).animate({width:$w-60+'px'},300);
    }
});
});
