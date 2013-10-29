<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="imageName" type="java.lang.String"%>
<%@ attribute name="mainText" type="java.lang.String"%>
<%@ attribute name="name" type="java.lang.String"%>
<%@ attribute name="function" type="java.lang.String"%>

<div class="row">
	<div class="col-sm-2"></div><%-- blank space for the previous arrow --%>
  		<div class="col-sm-3 testimonialimg">
  			<img alt="" src="/images/testimonials/${imageName}">
  		</div>
  		<div class="col-sm-5 testimonialtext">
      <p>${mainText}</p>
      <label><span>${name}</span>
         <br/>
	     ${function}
      </label>
   </div>
   <div class="col-sm-2"></div><%-- blank space for the next arrow --%>
</div>
