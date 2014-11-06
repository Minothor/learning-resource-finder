$(document).ready(function()  {	
	// Instance the tour
	var tour = new Tour({
	  steps: [
	  {
	    element: "#tourStepProgressBar",
	    title: "Jauge de niveau",
	    content: "Lorsque vous contribuez, vous gagnez des points, puis des nouveaux droits."
	  }
	],
	template: "<div class='popover tour'> <div class='arrow'></div> <h3 class='popover-title'></h3><div class='popover-content'></div> 	<nav class='popover-navigation'> <div class='btn-group'>	</div> 	<button class='btn btn-default' data-role='end'>fermer</button> </nav> </div>"
	});

	// Initialize the tour
	tour.init();

	// Start the tour
	tour.start();			
	
});
