
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<script type="text/javascript" src="/js/int/resourceAdd.js"></script>

<!-- Modal for adding a resource (invisible until button clicked) -->
<div class="modal fade" id="addResourceModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
	
 		<form id="addResourceForm1" role="form" method="post" action="resourceaddsubmit">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"aria-hidden="true">&times;</button>
					<h4 class="modal-title">Ajouter une ressource</h4>
				</div>
				
				<div class="modal-body">
					<div class="form-horizontal container">
						<div class="form-group">
	                        <label for="url">site</label> 
	                        <input type="text" class="form-control" id="urlAddField" name="url" placeholder="http://...">
	                        <br />
	                        <div class="pull-right"> 
	                           <button type="button" class="btn btn-mini btn-primary" id="urlCheckButton" onclick="ajaxVerifyUrl()">Vérifier</button>
							</div>
							<span class="help-block">URL vers le site que vous désirez ajouter.</span>
						</div>

						<div id="addResourceFormPart2" style="display:none;">  <%-- will not be displayed until the url is valid --%>

							<div class="form-group">
		                        <label for="titre">Intitulé</label> 
		                        <input type="text" class="form-control" name="title" placeholder="intitulé" required="required">
		                   		<div class="pull-right"></div>
							</div>
							
							<br />
	                            
                            <div class="row">
                            	
                            	<div class="form-group col-sm-4">                            		 
                            		<label for="Format">Format</label>
				                     <c:forEach var="format" items="${applicationScope.cache.format}">
					                  	<div class="radio"> 
					                   	<label for="${format.description}">
					                   		<input type="radio" name="format" value="${format}" required="required">
					                   		${format.description}
					                   	</label>
				                       </div> 
								     </c:forEach>
                            	</div>

								<div class="form-group col-sm-4">
									<label for="platform">Plate-forme</label>
									<c:set var="firstIteration" scope="page" value="true"/>
									<c:forEach var="platform" items="${applicationScope.cache.platform}">
										<div class="radio">
											<label for="${platform.description}">	
							                   	<input type="radio" name="platform" value="${platform}" required="required" <c:if test="${firstIteration==true}">checked</c:if> > 
							                    ${platform.description}
							                </label>  
										</div>
										<c:set var="firstIteration" scope="page" value="false"/>
									</c:forEach>	                      
                            	</div>

								<div class="form-group col-sm-4">
									<label for="topic">Matière</label>
									<c:forEach var="topic" items="${applicationScope.cache.topic}">
									<div class="radio">
										<label for="${topic.description}">
											<input type="radio" name="topic" value="${topic}" required="required"> 
											${topic.description}
										</label>
									</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
                  </div>
                  	
				  <div class="modal-footer" style="display: none;" id="bottomButtons">
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
				    <%--<button type="submit" class="btn" data-toggle="modal" href="#addResourceModal2">Ajouter</button>--%>
				    <input class="btn btn-primary" type="submit" value="Ajouter" >
				 </div>
			</div>
			<!-- /.modal-content -->
		</form>
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal --> 

<div class="modal fade" id="addResourceModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
	<form id="addResourceForm2" role="form" method="post" action="/ajax/resourceaddsubmit">
	 <input type="hidden"  name="idresource" id="idresource" />
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Ajout d'une ressource (partie 2)</h4>
        </div>
				<div class="modal-body">



					<div class="form-group">
						<label for="description">Description</label>
						<textarea class="form-control"  name="description"
							placeholder="Description" rows="3"></textarea>
						<div class="pull-right"></div>
					</div>

					<div class="form-inline">
						Présence de publicité:
						<div class="radio">
						    <label>
							   <input type="radio" value="true" name="advertising"/> Oui 
							</label>
						</div>
						<div class="radio">
						    <label>
						     	<input type="radio" value="false" name="advertising"/> Non
							</label>
						</div>
					</div>

                    <div class="form-group col-lg-3">
	   				    <label for="language">Langue de la resource</label>
						<select class="form-control" name="language">
							<c:forEach var="language" items="${applicationScope.cache.language}">
								<option value="${language}">${language.description}</option>
							</c:forEach>
						</select>
                    </div>
					<br>
					
					<div class="form-inline col-lg-2">
						<label for="duration">Durée</label>
						<input type="text" class="form-control" name="maxDuration"> minutes
						<span class="help-block">Temps approximatif que met un élève pour lire le texte, ou exécuter l'exercice ou visionner la vidéo proposée.</span>
					</div>

					<div class="form-group">
						<label for="nature">Nature</label>

						<c:forEach var="nature" items="${applicationScope.cache.nature}">
							<div class="radio">
								<label for="${nature.description}">
									<input type="radio" name="nature" value="${nature}"> 
									${nature.description}
								</label>
							</div>
						</c:forEach>

					</div>
				</div>

				<div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <input class="btn btn-primary" type="submit" value="Enregistrer" >
        </div>
      </div><!-- /.modal-content -->
   </form>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


                    
 
 
 
 
