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
</head>
<body>
	<lrftag:breadcrumb linkactive="${user.firstName} ${user.lastName}">
		<lrftag:breadcrumbelement label="Home" link="/home" />
	</lrftag:breadcrumb>
	<div class="container">
		<lrftag:pageheadertitle title="${user.fullName}"/>
		<div class="btn-group">
			<lrf:conditionDisplay privilege="MANAGE_USERS">
				<a class="btn btn-default" href="/user/privilegeedit?id=${user.id}">Privilèges</a>	
				<a class="btn btn-default" href="/user/usertypeedit?id=${user.id}">Editer le type d'un user</a>	
				<c:if test="${not(current.user  eq user)}">
				<a class="btn btn-default" href="/user/delete?id=${user.id}">Supprimer le compte</a>
				</c:if>
			</lrf:conditionDisplay>
			<c:if test="${canEdit}">
				<a class="btn btn-default" href="/user/edit?id=${user.id}">Editer le Profil</a>
				<a class="btn btn-default" href="/user/changepassword?id=${user.id}">Modifier le mot de passe</a>
			</c:if>
			<a class="btn btn-default" href="/playlist/user/${user.userName}">Play-lists</a>
		</div>
		
		<br />
		<br />

		<%-- IMAGE   IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  IMAGE  --%>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-3">
					<div>
						<c:if test="${canEdit}">
				    	<a data-toggle="modal" href="#modalAvatar">
				    	</c:if> 		
				        	<figure>
				        		<c:choose>
									<c:when test="${user.picture}">
										<%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
				            			<img src="/gen/user/resized/large/${user.id}.jpg<c:if test="${random!=null}">?${random}</c:if>" alt=""  />
				            		</c:when>
					            	<c:otherwise>
					            		<c:choose>
					            			<c:when test="${user.isFemale()}">
												<img src="/images/Femme_anonyme.jpg" class="avatar" />
											</c:when>
											<c:otherwise>
												<img src="/images/Homme_anonyme.jpg"  class="avatar" />
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						<c:if test="${canEdit}">	 
				                	<figcaption lang="la"><span><strong>Charger une image</strong></span></figcaption>
				        </c:if>         	
				      		</figure>
						<c:if test="${canEdit}">     
				      	</a>
				       	</c:if> 		      	
				    </div>
				</div>
			</div>
		</div>
<%-- 		<c:if test="${canEdit}"> --%>
		<div class="panel panel-default">
		  	<div class="panel-body">
				<ul>
					<li><label>Prénom :</label> <c:choose><c:when test="${user.firstName ne null}">${user.firstName}</c:when><c:otherwise>?</c:otherwise></c:choose></li>
					<li><label>Nom :</label> <c:choose><c:when test="${user.lastName ne null}">${user.lastName}</c:when><c:otherwise>?</c:otherwise></c:choose></li>						
					<li>
						<label>Âge :</label>
						<% 
							if (((User) pageContext.getRequest().getAttribute("user")).getBirthDate() != null){
								DateUtil.SlicedTimeInterval sti = DateUtil.sliceDuration(((User) pageContext.getRequest().getAttribute("user")).getBirthDate(), new Date());
					  			out.println(sti.years + " ans");
							}
					 	%>
					</li>
					<li><label>Genre :</label> <c:choose><c:when test="${user.gender ne null}">${user.gender}</c:when><c:otherwise>?</c:otherwise></c:choose></li>
					<li><label>Né le :</label> <c:choose><c:when test="${user.birthDate ne null}">${user.birthDate}</c:when><c:otherwise>?</c:otherwise></c:choose></li>
					<li><label>Pseudo :</label> ${user.userName}</li>
					<li><label>Email :</label> <c:choose><c:when test="${user.mail ne null}">${user.mail}</c:when><c:otherwise>?</c:otherwise></c:choose></li>
					<li><label>Titre :</label> <c:choose><c:when test="${user.title ne null}">${user.title}</c:when><c:otherwise>?</c:otherwise></c:choose></li>
					<li>
						<label>Rôle :</label>
						<c:if test="${user.role != USER}">
							${user.role.name}
						</c:if>
					</li>
					<li><label>Date d'enregistrement :</label> <lrf:datedisplay date="${user.createdOn}" /></li>
					<li><label>Dernier accès :</label> <lrf:datedisplay date="${user.lastAccess}" duration="true"/></li>
					<li><label>Depuis l'adresse :</label> ${user.lastLoginIp}</li>
					<li>
						<label>Status du compte :</label> ${user.accountStatus}
						<c:if test="${user.lockReason}!= ACTIVE ">
							Raison blocage compte : ${user.lockReason}<br /> 
						</c:if>
					</li>
				</ul>
		  	</div>
		</div>
<%-- 		</c:if> --%>

		<!-- Modal -->
		<div class="modal fade" id="modalAvatar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="close closeModal" data-dismiss="modal" aria-hidden="true">&times;</button>
		          		<h4 class="modal-title">Ajouter un avatar</h4>
		        	</div>		        	
		     		<form method="post" action="/user/imageadd" enctype="multipart/form-data" class="form-horizontal formUrlResource">	
		     			<div class="modal-body">
		     			
		     				<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio" name="rdFrom" value="computer" class="radioComputer" id="inputComputer" checked="checked" /> 
							    	Depuis l'ordinateur
							    </label>
							    <div class="col-lg-8">
							      	<input type="file" name="file" value="Parcourir..." class="inputSource"  id="inputFile" style="width:345px;" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="url" class="radioUrl" /> 
							    	Depuis un lien
							    </label>
							    <div class="col-lg-8">
							      	<input type="hidden" name="strUrl" value="http://..." class="form-control inputSource" id="inputUrl" />
								</div>
							</div>
							
							<div class="form-group">
							    <label class="col-lg-4 control-label" style="text-align:left;">
							    	<input type="radio"  name="rdFrom" value="defaut" class="radioDefaut" /> 
							    	Image par défaut
							    </label>
							    <div class="col-lg-8">
							    	<c:choose>
							    		<c:when test="${user.isFemale()}">
												<img class="avatarDefault" style="visibility:hidden;margin:-95px 211px;position:absolute;" src="/images/Femme_anonyme.jpg" alt="Femme" />
										</c:when>
										<c:otherwise>
												<img class="avatarDefault" style="visibility:hidden;margin:-95px 211px;position:absolute;" src="/images/Homme_anonyme.jpg" alt="Homme" />
										</c:otherwise>
									</c:choose>
							    </div>
							</div>

				    	</div>
				    	
				    	<div class="modal-footer">
				    		<input type="hidden" name="id" value="${user.id}" />
			          		<button type="button" class="btn btn-default closeModal" data-dismiss="modal">Fermer</button>
			          		<button type="submit" class="btn btn-primary closeModal">Sauver l'avatar</button>
			        	</div>
				    </form>
		      	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	</div>
</body>
</html>