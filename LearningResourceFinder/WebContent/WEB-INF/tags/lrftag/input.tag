<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="path" required="true"  type="java.lang.String" %>
<%@ attribute name="label"  type="java.lang.String" %>
<%@ attribute name="required"  type="java.lang.Boolean" %>
<%@ attribute name="help" type="java.lang.String" %>   <%-- Additional text to write below the input such as "we'll keep your e-mail private" --%>
<%@ attribute name="cssClass" required="false" type="java.lang.String"%>
<%@ attribute name="type"  type="java.lang.String" %>
<%-- This is in a .tag file and not in a Java file because in JSP 2.0, only .tag file can be used to make custom tags producing custom tags
see http://stackoverflow.com/questions/439861/spring-mvc-tag-interaction-with-custom-tag
 --%>

<%-- the bootstrap like version of this SpringMVC tag is inspired from this:
     http://duckranger.com/2012/07/spring-mvc-and-twitter-bootstrap-customizing-the-input-fields/ --%>
     
<c:if test="${empty label}"> <%-- If there is no label, we take (compute) one form the path. Example: field for Competence.descritpion has computed label description --%>
    <c:set var="label" value="${fn:toUpperCase(fn:substring(path, 0, 1))}${fn:toLowerCase(fn:substring(path, 1,fn:length(path)))}" />
</c:if>     

<spring:bind path="${path}">  <%-- gives us access to the status and error JSP variables --%>   
    <div class="control-group ${status.error ? 'has-error' : '' }">  <%-- div from Bootstrap --%>
        <label class="control-label" for="${path}">${label}<c:if test="${required}"><span class="required">*</span></c:if></label>
        <div class="controls">
            <form:input path="${path}" required="${required}" cssClass="${empty cssClass ? 'input-xlarge' : cssClass}"/>
            <c:if test="${status.error || ! empty help}">
                <span class="help-inline">
                     <c:if test="${status.error}">${status.errorMessage}</c:if> <%-- ${status.errorMessage} ouptuts the same error text as the <form:error..> tag --%>
                     <c:if test="${! empty help}">${help}</c:if>
                </span>   
            </c:if>
        </div>
    </div>
</spring:bind>