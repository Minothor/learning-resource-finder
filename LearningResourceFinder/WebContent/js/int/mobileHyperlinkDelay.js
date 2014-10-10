//Used to compensate for the touch handing of Android and Windows Phone, allows first click to activate tooltip and second to confirm navigation/
//initialise variables with empty, non-null types.
var delayRequired=false;
var lastLink = "";

$(document).ready(function () {
	//checks for the two user agents known to be problematic as of 10/10/2014
    delayRequired = ((navigator.userAgent.search("Android") > -1) || (navigator.userAgent.search("Windows Phone") > -1));
    
    
    $('.poper a, .addToolTip').click(function (element) {
        if (delayRequired) {
        	var currentLink = $(this).attr("href");
        	var followLink = false;
        	//compares active link to the one last clicked and allows navigation if they match
            followLink = (currentLink.localeCompare(lastLink) == 0);
            lastLink = currentLink;
            if (followLink) {
                window.location.href = currentLink;
            }
            return false;
        }
        //default behaviour for all other user agents
        return true;
    });
});