<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="linkactive" type="java.lang.String"%>

<div class="container">
	<ol class="breadcrumb" style="text-align:right;">
  		<jsp:doBody/>
 		 <li class="active">${linkactive}</li>
	</ol>
</div>

