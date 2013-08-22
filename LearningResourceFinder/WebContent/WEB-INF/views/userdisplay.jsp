<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ page import="learningresourcefinder.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="learningresourcefinder.model.User" %>
<html>
<head>
<!-- Jquery for change input popup addImageUser -->
	<script type="text/javascript" src="/js/int/addImageUrlUser.js"></script>

<script type="text/javascript" >
$(function() {
    $( "#tabs" ).tabs();
});
</script>
<!-- you can set variables starting with "p_" in the file named website_content.properties -->
<link rel="canonical" href="${p_website_address}/user/${user.userName}"/>
<meta name="description" content="${user.firstName} ${user.lastName}">
</head>   

<body>

<lrftag:pageheadertitle title="${user.fullName}"/>
<div class="user-options" style="font-size:12px">
             <lrf:conditionDisplay privilege="MANAGE_USERS">
				 <a href="user/privilegeedit?id=${user.id}">Privilèges</a>&nbsp-&nbsp
				 <a href="user/usertypeedit?id=${user.id}">Editer le type d'un user</a>	
				 <c:if test="${not(current.user  eq user)}">
				  	&nbsp-&nbsp<a href="user/delete?id=${user.id}">Supprimer le compte</a>
				 </c:if>
			</lrf:conditionDisplay>
			 <c:if test="${canEdit}">
				&nbsp-&nbsp<a href="user/edit?id=${user.id}">Editer le Profil</a>
				&nbsp-&nbsp<a href="user/changepassword?id=${user.id}">Modifier le mot de passe</a>
			 </c:if>
			 &nbsp-&nbsp<a href="playlist/user/${user.userName}">Play-lists</a>
</div>

<br/>

<%-- IMAGE   IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  --%>
<div style="display:inline-block; vertical-align:top;">
	<div class="user-options" style="font-size:12px">
	
		<div id="divPhoto">
			<c:if test="${canEdit}">
	    		<a href="#" data-width="500" data-rel="popupJquery" class="poplight">
	    	</c:if> 		
	        	<figure>
	        		<c:choose>
						<c:when test="${user.picture}">
							<%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
	            			<img src="gen/user/resized/large/${user.id}.jpg<c:if test="${random!=null}">?${random}</c:if>" alt=""  />
	            		</c:when>
		            	<c:otherwise>
		            		<c:choose>
		            			<c:when test="${user.isFemale()}">
									<img src="images/Femme_anonyme.jpg" />
								</c:when>
								<c:otherwise>
									<img src="images/Homme_anonyme.jpg" />
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
			<c:if test="${canEdit}">	 
	                	<figcaption lang="la"><span class="lib-change-image"><b>Charger une image</b></span></figcaption>
	        </c:if>         	
	      		</figure>
			<c:if test="${canEdit}">     
	      	</a>
	       	</c:if> 		      	
	    </div>
		
		<div id="popupJquery" class="popupJquery">	
	    	<div class="popup-close">
	        	<a class="close" title="close this popup">X</a>
	      	</div>
	  	  	<form method="post" action="/user/imageadd" class="formUrlResource" enctype="multipart/form-data">	
	    		<h2>Ajouter une image</h2> 
	          	<br />
	          	<label><input type="radio"  name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> Depuis l'ordinateur</label>
	          	<input type="file"   name="file"   value="Parcourir..."    class="inputSource"   id="inputFile" /> 
	          	<input type="hidden" name="strUrl" value="http://..."      class="inputSource"   id="inputUrl"  />             
	          	<br /> 
	          	<label><input type="radio"  name="rdFrom" value="url"      class="radioUrl" /> Depuis un lien</label>
	          	<input type="hidden" name="id" value="${user.id}" />
	          	<br />
	          	<br />
	          	<input class="btnSubmit" type="submit" value="Ajouter" name="btnPicture" /> 
	      	</form>
	      	<br />  <%-- Add/edit links --%>
			<c:if test="${canEdit}">
				<c:choose>
						<c:when test="${user.picture}">
							<a href= "/user/imagedelete?id=${user.id}">Supprimer image</a><br/>
						</c:when>
				</c:choose>			
				<c:if test="${sessionScope.providersignedin != 'LOCAL'}">	
				   <a href ="user/updateusersocialimage?provider=${sessionScope.providersignedin}&id=${user.id}">Mettre à jour mon image depuis ${sessionScope.providersignedin} </a><br/>
				</c:if>
			</c:if>
		</div>
<%-- 		<a href= "/user/image?id=${user.id}">ajouter image</a><br/> --%>
		<br />
		<br />
	</div>
</div>

<div style="display:inline-block; vertical-align:top;">
		<c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose>
		<c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
		<c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
							
		<% 
			 if (((User) pageContext.getRequest().getAttribute("user")).getBirthDate() != null){
			 DateUtil.SlicedTimeInterval sti = DateUtil.sliceDuration(((User) pageContext.getRequest().getAttribute("user")).getBirthDate(), new Date());
  			 out.println(sti.years + " ans");
			}
 		%>	
		<c:if test="${user.role != USER}">
			 ${user.role.name}<br/><br/>
		</c:if>	
		<br />
		<br />
</div>			
<br/>
<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Signalétique</a></li>			
		</ul>
		<div id="tabs-1">		<!--  **************************Signalétique********************* -->
			<div>
				Prénom: <c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Nom de famille: <c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Pseudo : ${user.userName}<br/>
				Titre: <c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				Genre : <c:choose><c:when test="${user.gender ne null}">${user.gender}</c:when><c:otherwise>?</c:otherwise></c:choose> <br/>
				<c:if test="${canEdit}">
					Né le : <c:choose><c:when test="${user.birthDate ne null}">${user.birthDate}</c:when><c:otherwise>?</c:otherwise></c:choose><br />
					mail : <c:choose><c:when test="${user.mail ne null}">${user.mail}</c:when><c:otherwise>?</c:otherwise></c:choose><br />
					
					Date d'enregistrement : <lrf:datedisplay date="${user.createdOn}" /><br />
					Rôle : ${user.role}<br/>
					Dernier accès : <lrf:datedisplay date="${user.lastAccess}" duration="true"/> <br/>
					Depuis l'adresse ${user.lastLoginIp}<br/>
					Status du compte : ${user.accountStatus}<br/>
					<c:if test="${user.lockReason}!= ACTIVE ">
						Raison blocage compte : ${user.lockReason}<br /> 
					</c:if>
				</c:if>
			</div>
		</div>
		
									
</div>
	

</body>
</html>