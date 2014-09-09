<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="idSlider" type="java.lang.String" %>
<%@ attribute name="minCycle" type="java.lang.String" %>
<%@ attribute name="maxCycle" type="java.lang.String" %>



<head>
<script>
$(document).ready(function() {
// On document ready, initialize Slider (to select min and max cycle).
	$(function() {
	//Slider Documentation can be find here http://refreshless.com/nouislider/ 

		sliderDiv = $('#${idSlider} .slider');
		
		sliderDiv.noUiSlider({
	
			start : [${minCycle},${maxCycle}],		
			connect : true,
			mode: 'values',	
			orientation : "vertical",
			step : 1,
			behaviour : 'tap-drag',
			range : {
				'min' : 0,
				'max' : 4
			}
		});
		
	    $('.noUi-connect').css('background', '#84bb04');
		// Put Slider values in hidden imput
		sliderDiv.Link('lower').to($('#valuemin'));
		sliderDiv.Link('upper').to($('#valuemax'));

	});
});

</script>
</head>

<div id="${idSlider}">
	<label for="cycleslider">Cycles (année)</label> 
	<div class="slider" id="slider"></div>   <%--    slider injected here by JavaScript --%>		
	<div id="numberslider">
		<ul>								
			<li><b>-</b> P 1-2</li>
			<li><b>-</b> P 3-4</li>
			<li><b>-</b> P 5-6</li>
			<li><b>-</b> S 1-2</li>
			<li><b>-</b> S 3-6</li>
		</ul>
	</div>		
										
	<%-- Input filled by the slider automatically. These values will be sent to the controller. --%>
	<input id="valuemin" type="hidden" name="value-min"  />
	<input id="valuemax" type="hidden" name="value-max" />
</div>



