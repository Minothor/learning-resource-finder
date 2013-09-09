<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="title" required="true" type="java.lang.String"%>

<!-- <span class="breadcrumb"> -->
<%-- 	<jsp:doBody/> --%>
<!-- </span> -->


<span class="breadcrumb">
	<div class="page-title">
		<div class="container">
			<div class="twelve columns">
				<h1>${title}</h1>
			</div>
			<nav class="four columns">
				<ul class="breadcrumbs">
					<jsp:doBody/>
				</ul>
			</nav>	
		</div>
	</div>	
</span>
