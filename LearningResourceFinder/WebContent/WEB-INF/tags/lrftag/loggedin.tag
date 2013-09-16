<%@tag import="learningresourcefinder.security.SecurityContext"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ attribute name="yes" type="java.lang.String" %>
<%@ attribute name="no"  type="java.lang.String" %>
<% if(SecurityContext.isUserLoggedIn()){ %>${yes}<% } else { %>${no}<% } %>