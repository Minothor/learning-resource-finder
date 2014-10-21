<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
<title>Edition d'un cycle</title>
</head>
<body>
   <c:choose>
       <c:when test="${cycle.id == null}">
			<lrftag:breadcrumb linkactive="création">
		   		<lrftag:breadcrumbelement label= "home" link="/home"/>
		   		<lrftag:breadcrumbelement label="cycles" link="/cyclelist"/>
	   		</lrftag:breadcrumb>
	  </c:when>

	  <c:otherwise>
         <lrftag:breadcrumb linkactive="édition">
			<lrftag:breadcrumbelement label= "home" link="/home" />
			<lrftag:breadcrumbelement label="cycles" link=" /cyclelist "/>
			<lrftag:breadcrumbelement label="${cycle.name}" link="cycle?id=${cycle.id}" />
	     </lrftag:breadcrumb>
	  </c:otherwise>    
    
    </c:choose>
    
     
<div class="container">
	<c:choose>
		<c:when test="${cycle.id==null}">
			<lrftag:pageheadertitle title="Création d'un cycle"/>
		</c:when>
		<c:otherwise>
			<lrftag:pageheadertitle title="Edition : ${cycle.name}"/>
		</c:otherwise>
	</c:choose>

	<div class="panel panel-default">
		<div class="panel-body">
			<br />
			<form:form modelAttribute="cycle" method="post" action='<%=response.encodeURL("cycleeditsubmit")%>'>
				<div class="col-md-12">
					<input type="hidden" name="id" value="${cycle.id}"/>
					<span class="input-control">
				    <form:input class="form-control" label="Nom" placeholder="Nom" style="width:150px" maxlength="15" path="name"/>
				    <label style="color: red"><form:errors path="name"/></label>
				    </span>
				    <br/>
				    <form:input class="form-control" label="Description" placeholder="Description" style="width:500px" maxlength="50" path="description"/>
				    <label style="color: red"><form:errors path="description"/></label>
				    <br/>
				    
				</div>
				<div class="form-group">
					<label class="col-lg-0"></label>
				   	<ul class="col-lg-0" >
				    <li style="display:inline;">
					    <input type="submit" class="btn btn-primary" value="Sauver"/><br>
					</li>
					</ul>
				</div>
			 </form:form>
		</div>
	</div>	
</div>
</body>
</html>