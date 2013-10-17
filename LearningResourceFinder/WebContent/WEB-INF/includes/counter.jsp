<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<head>
	<!-- css sheets -->
	<link rel="stylesheet" type="text/css" href="/css/ext/circular-countdown.css"/>
	<!--  ************************** COUNTER ****************************** -->	
	<script type="text/javascript" src="/js/ext/jquery.countdown.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/ext/jquery.circular-countdown.js"></script>
	<script type="text/javascript" src="/js/ext/counter-home.js"></script>
</head>

<div class="container">
	<div class="col-lg-9 centered">
		<div class="countdown"></div><%-- counter.js and circular-countdown.css will do the job --%>
	</div>
</div>