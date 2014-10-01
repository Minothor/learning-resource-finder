<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resourceList" type="java.util.List" %>


<div>
	<c:forEach items="${resourceList}" var="resource">
		<lrftag:resource resource="${resource}" />
	</c:forEach>
</div>