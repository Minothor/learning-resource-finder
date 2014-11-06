$(document).ready(function() {	
	// Instance the tour
	var tour1 = new Tour({
	  steps: [
	  {
	    element: "#title",
	    title: "Bienvenue sur ToujoursPlus",
	    content: "La ressource est disponible sur un autre site (en cliquant là)."
	  },
	  {
		element: "#search",
		title: "Autres vidéos, exercices ou jeux éducatifs?",
		content: 'ToujoursPlus.be est un moteur de recherche. Essayez des mots tel que "multiplication", "corps humain" ou "subjonctif."'
	  },
	  {
		element: "#tourStepVote",
		title: "Contributions",
		content: "Lorsque vous êtes connectés, vous pouvez aider les autres internautes en améliorant la description des ressources et en donnant votre avis."
	  }
	  
	],
	template: "<div class='popover tour'> <div class='arrow'></div> <h3 class='popover-title'></h3><div class='popover-content'></div> 	<nav class='popover-navigation'> <div class='btn-group'> <button class='btn btn-default' data-role='prev'>pr\351c\351dent</button> <button class='btn btn-default' data-role='next'>suivant</button> </div> <button class='btn btn-default' data-role='end'>fermer</button> </nav> 	</div>"
	});

	// Initialize the tour
	tour1.init();

	// Start the tour
	tour1.start();			
	
});