<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

<script type="text/javascript">
$(function ()  
 { $("#mycyclePopover").popover({title: 'Aide',trigger:' hover',placement:'right'});  
});  

</script>
</head> 
<body>



<div class="container">
<a  id="mycyclePopover" data-content="hello teste " href="#"> un message</a><br/>

   <a href="/test/addMsg">Générer un message (serveur)</a><br/>
   <span onclick='showNotificationText("Hello", "danger");'>Générer un message (javascript)</span>
   <button onclick="progressBarAjax();">Progress bar ajax</button>
</div>
</body>
</html>
