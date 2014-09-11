<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>


<%@ attribute name="user" type="learningresourcefinder.model.User"%>
<%@tag import="learningresourcefinder.util.FileUtil" %>

<div class="user" id="${user.id}">

	<div class="avatar">
	<c:if test="${user.picture}"> 
       <img src="gen<%=FileUtil.USER_SUB_FOLDER%><%=FileUtil.USER_RESIZED_SUB_FOLDER%><%=FileUtil.USER_RESIZED_SMALL_SUB_FOLDER %>/${user.id}.jpg" /> 
	</c:if> 
	</div>
	<a href="/user/${user.userName}" class="a-name">
      <div class="lien">
		<span>${user.getFullName()}</span>
      </div> 
      <div class="usertitle"> ${user.title}</div>
	</a>
</div>