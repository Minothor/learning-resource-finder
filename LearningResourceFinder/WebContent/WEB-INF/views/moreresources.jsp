<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<c:forEach items="${moreResourceList}" var="resource">
		<lrftag:resource resource="${resource}"/>
</c:forEach>

<c:if test="${tokenMoreResources=='0'}">
	<input type="hidden" value="${tokenMoreResources}" id="tokenMoreResources"/>
</c:if>