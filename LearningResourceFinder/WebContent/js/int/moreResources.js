$(document).ready(function() {

	$('#spinner').hide();  // Hourglass at the end of the page
	
	$(window).scroll(function() {
		if ($(window).scrollTop() == $(document).height()- $(window).height()) {  // We are near the bottom

			// Retrieve value from hidden field which indicates if we are (=true) or not (=false) in the search resource screen. 
			// We use it to know if the number of rows to return should be either a multiplier of 5 (resourcelist.jsp) or 8 (searchresource.jsp). 
			var searchresourcescreen=$("#searchresourcescreen").val();

			// tokenListOfResources (part of resourcelist.jsp) is loaded at first request. 
			// If it's "0", it means there are less than xxx resources to display, no reload necessary.
			// If it contains a value (timestamp converted to String), this value will be used to retrieve 
			// batch of resources from session when scrolling.
			// tokenMoreResources (part of moreresources.jsp) is set to "0" by resourcemorecontroller 
			// when last batch of resources to display provided to jsp to inform no more reload necessary.
			var tokenListOfResources=$("#tokenListOfResources").val();
			var tokenMoreResources=$("#tokenMoreResources").val();  // Did the server told is that there are more resources to be loaded?
			if (tokenListOfResources != "0" && tokenMoreResources != "0") {  // Let's load more resources

				$('#spinner').show();

				$.ajax({
					url : "/ajax/getmoreresources",
					dataType: "html",
					type : 'POST',
					data : "tokenlistofresources="+tokenListOfResources+"&searchscreen="+searchresourcescreen,
					success : function(data) {
						if(data.length!=0){
							$("#resourcelist").append(data);
							$('#spinner').hide();
						} else { // Session has expired. Reload the page.
							$('#spinner').hide();
							location.reload();
						}
					},
					error : function(data) {
						alert("Problème en contactant le serveur" );
					}
				});
			}			
		}
	});
});