<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>



<script src="js/ext/jquery.nouislider.min.js"></script>
<script src="js/ext/optional.pips.js"></script>

<link href="css/ext/jquery.nouislider.css" rel="stylesheet" />
<link href="css/ext/jquery.nouislider.pips.css" rel="stylesheet" />




<script>
	// On document ready, initialize noUiSlider.
	$(function() {

		var range_all_sliders = {
			'min' : [ 0 ],
			'1-2' : [ 0, 1 ],
			'2-3' : [ 2, 3 ],
			'3-4' : [ 3, 4 ],
			'4-5' : [ 4, 5 ],
			'5-6' : [ 5, 6 ],
			'max' : [ 6 ]
		};
		

		$('#range').noUiSlider({
			range : range_all_sliders,
			start : [ 1, 6 ],
			margin : 1,
			connect : true,
			//values:["1-2","2-3", "3-4", "4-5", "5-5"],

			orientation : "vertical",
			step : 1,

			behaviour : 'tap-drag',

			range : {
				'min' : 1,
				'max' : 6
			}
		});

		$('#range').noUiSlider_pips({
			mode : 'steps',
			density : 0
		});
		

	});
</script>




</head>
<body>

	<div id="range"></div>


</body>
</html>