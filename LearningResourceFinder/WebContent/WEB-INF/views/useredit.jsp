<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %> 
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>


<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<html>
<head>
	<script src="/js/int/birthday_picker.js" type = "text/javascript"></script>	
</head>
<body>
	<lrftag:breadcrumb linkactive="edition">
		<lrftag:breadcrumbelement label="home" link="/home" />
		<lrftag:breadcrumbelement label="${user.firstName} ${user.lastName}" link="/user/${user.userName}" />
	</lrftag:breadcrumb>
	
	<div class="container">
		<lrftag:pageheadertitle title="${user.firstName} ${user.lastName}"/>
		<br />
		<div >
	    <form:form action="/user/editsubmit" modelAttribute="user" class="form-horizontal" role="form">
	    
	    <c:choose>
	    	<c:when test="${canChangeUserName}"><%-- Only an admin can modify name of a certified user --%>
	    		<lrftag:input path="firstName" label="Prénom"/>
	    		<lrftag:input path="lastName" label="Nom" />
		        <lrftag:input path="title" label="Titre" help="Indiquez en peu de mots la nature de votre 
		        fonction en rapport avec l'objet de ce site. Votre titre sera affiché sous votre nom.  
 		        Exemples de titres: 'Directeur d'une PME.', ou 'Ministre de la Bière', 
 		        ou 'Président du comité des gilles de Binche', ou 'Ouvrier dans l'industrie sidérurgique'."/> 
	    	</c:when>
	    	<c:otherwise>
		
	    		Prénom: <c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br /> 
				Nom de famille: <c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br /> 
				Titre: <c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br />  
				<br />
				Vous ne pouvez plus directement éditer votre nom, votre prénom et votre titre depuis que votre identité a été certifiée. <a href= "/contact">Contactez-nous</a> si vous devez corriger ces champs.
	    	</c:otherwise>
	     </c:choose>

	     <lrftag:input path="userName" label="Pseudonyme" required="required"/>
	     <%-- We do not use a date picker here, because for old dates, it's not practical --%>
		<div class="form-group">
			<label class="col-lg-2 control-label">Date de naissance</label>
		    <div class="col-lg-5 form-inline">
			     <jsp:useBean id="now" class="java.util.Date" /><%-- Used by the birthday picker, "year" variable --%>
			     <fmt:formatDate var="year" value="${now}" pattern="yyyy" /><%-- Used by the birthday picker, "year" variable --%>	
			     <select name="birthDay" id="birthDay" class="form-control" style="width:80px;margin-right:5px;">
			     	<option value=null <c:if test="${birthDay==null}">selected="selected"</c:if>>Jour</option>
			    <c:forEach var="i" begin="1" end="31" step="1" varStatus ="status">
			    	<option  value="${i}" <c:if test="${birthDay==i}">selected="selected"</c:if>>${i}</option>
			    </c:forEach> 
			    </select>
			    <select name="birthMonth" id="birthMonth" class="form-control" style="width:140px;margin-right:5px;">
			    	<option value="null" <c:if test="${birthMonth==null}">selected="selected"</c:if>>Mois</option>
			        <option value="1" <c:if test="${birthMonth==0}">selected="selected"</c:if>>Janvier</option>
			        <option value="2" <c:if test="${birthMonth==1}">selected="selected"</c:if>>Fevrier</option>
			        <option value="3" <c:if test="${birthMonth==2}">selected="selected"</c:if>>Mars</option>
			        <option value="4" <c:if test="${birthMonth==3}">selected="selected"</c:if>>Avril</option>
			        <option value="5" <c:if test="${birthMonth==4}">selected="selected"</c:if>>Mai</option>
			        <option value="6" <c:if test="${birthMonth==5}">selected="selected"</c:if>>Juin</option>
			        <option value="7" <c:if test="${birthMonth==6}">selected="selected"</c:if>>Juillet</option>
			        <option value="8" <c:if test="${birthMonth==7}">selected="selected"</c:if>>Aout</option>
			        <option value="9" <c:if test="${birthMonth==8}">selected="selected"</c:if>>Septembre</option>
			        <option value="10" <c:if test="${birthMonth==9}">selected="selected"</c:if>>Octobre</option>
			        <option value="11" <c:if test="${birthMonth==10}">selected="selected"</c:if>>Novembre</option>
			        <option value="12" <c:if test="${birthMonth==11}">selected="selected"</c:if>>Décembre</option>
				</select>
				<select name="birthYear" id="birthYear" class="form-control" style="width:100px;">
					<option value=null <c:if test="${birthYear==null}">selected="selected"</c:if>>Année</option>
				<c:forEach var="i" begin="0" end="100" step="1" varStatus ="status">
					<option value="${year-i}" <c:if test="${birthYear==(year-i)}">selected="selected"</c:if>>${year-i}</option>
			    </c:forEach>
				</select>
			</div>
		 </div>
		
		${errorBirthDate}
		
		<div class="form-group">
			<label class="col-lg-2 control-label">Genre</label>
		    <div class="col-lg-5">
		    	<label class="font-ligther" style="margin-right:10px;"><form:radiobutton  path="gender" value="MALE" /> MALE</label>
		    	<label class="font-ligther"><form:radiobutton   path="gender" value="FEMALE" /> FEMALE</label>
		    </div>
		</div>
		
		<div class="form-group">
			<label class="col-lg-2 control-label">Type</label>
		    <div class="col-lg-5">		    
		    	<c:forEach items="${dataEnumUserType}" var="userType">
		    		<label class="font-ligther" style="margin-right:10px;"><form:radiobutton  path="userType" value="${userType}" /> ${userType.description}</label>
                 </c:forEach>
		    </div>
		</div>		
		
	     
	        
	    <lrftag:input path="mail" label="Mail" type="email" help="Le site vous envoie un e-mail de notification, par exemple lorsqu'un utilisateur commente un de vos arguments, ou lorsque vous recevez une gommette. Ces mails peuvent être groupés en un mail quotidien ou hebdomadaire."/>
	   	
	   	<div class="form-group">
	   		<label class="col-lg-2"></label>
	   		<div class="col-lg-5">
	    		<lrftag:checkbox path="nlSubscriber" label="Newsletters"/>
	    	</div>
	    </div>
	    
	    <div class="form-group">
	   		 <form:label path ="mailingDelayType" class="col-lg-2 control-label">Intervalle de reception des email</form:label>
	    	<ul class="col-lg-2">
	    		<li><label class="font-ligther"><form:radiobutton  path="mailingDelayType" value="IMMEDIATELY" /> Immédiat</label></li>
	    		<li><label class="font-ligther"><form:radiobutton  path="mailingDelayType" value="DAILY" /> Quotidien</label></li>
	    		<li><label class="font-ligther"><form:radiobutton  path="mailingDelayType" value="WEEKLY" /> Hebdomadaire</label></li>
	    	</ul>	    			
	    </div>

	    <c:choose>
			<c:when test="${canChangeAccountStatus}">
		    	Statut du compte:
		        <form:select path="accountStatus" >
		         	<form:options items="${statusList}" />
		         </form:select>
			</c:when>
	        <c:otherwise><!-- because accountstatus cannot be null due to sql constraint -->
	        	<input type="hidden" name="accountStatus" value="${user.accountStatus}"/>
	        </c:otherwise>
		</c:choose>
		
		<div class="form-group">
	   		<label class="col-lg-2"></label>
	   		<ul class="col-lg-2" >
	    		<li style="display:inline;"><input type="submit" value="Sauver" class="btn btn-primary" /></li>
	    		<li style="display:inline;"><button onclick="location.href='/user/${user.userName}';" class="btn" type="reset">Cancel</button></li>
	    	</ul>
	    </div>

		<input type="hidden" name="id" value="${id}"/> <%-- We do not use form:hidden because user.id is sometimes null (fake user)--%>
	    
	    </form:form> 
	  </div>
	</div>
</body>
</html>