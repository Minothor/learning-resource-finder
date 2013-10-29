<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
	<title>ToujoursPlus.be - Coming Soon...</title>
</head>
<body>

<%---------------------------------- LOGO ----------------------------------%>
<div class="container" style="padding-top:20px; padding-bottom:20px;">
   <div class="text-center"><img src="/images/ToujoursPlus-logo-500px.png" alt="ToujoursPlus.be logo" /></div>
</div>


<%------------------------------------ BENEFITS -------------------------------------%>
<div style="background-color:#17A7D6;">
<div class="container" style="padding-top:20px; padding-bottom:20px; color:white;">
	<%@include file="/WEB-INF/includes/benefits.jsp" %>
</div>
</div>

<%------------------------------------ PURPOSES -------------------------------------%>
<div style="background-color:white;">
<div class="container" style="padding-top:20px; padding-bottom:20px;">
	<%@include file="/WEB-INF/includes/purposes.jsp" %>
</div>
</div>

<%---------------------------------- COUNTER ----------------------------------%>
<div class="container" style="padding-top:40px; padding-bottom:40px;">
  <%@include file="/WEB-INF/includes/counter.jsp" %>
</div>


<%------------------------------------ TESTIMONIALS ---------------------------------%>
<div style="background-color:#84BB04;">
<div class="container" style="padding-top:20px; padding-bottom:20px; color:white;">
	<%@include file="/WEB-INF/includes/testimonials.jsp" %>
</div>
</div>

<%--------------------------------- PEOPLE + SUBSCRIBE MAIL ------------------------%>
<div class="container" style="padding-top:0px; padding-bottom:20px;">
  <%@include file="/WEB-INF/includes/peopleAndSubscribe.jsp" %>
</div>

<%------------------------------------ SUPPORT --------------------------------------%>
<div style="background-color:#F28F10;"> <%-- Orange --%>
<div class="container" style="padding-top:20px; padding-bottom:20px; color:white;">
	<%@include file="/WEB-INF/includes/support.jsp" %>
</div>
</div>

</body>
</html>