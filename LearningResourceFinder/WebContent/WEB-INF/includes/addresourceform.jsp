
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
	                        <input type="text" class="form-control" id="url" name="url" placeholder="http://...">
	                        <br />
	                        <div class="pull-right"> 
	                           <button type="button" class="btn btn-mini btn-primary" id="urlCheckButton" onclick="ajaxVerifyUrl()">Vérifier</button>
							</div>
							<span class="help-block">URL vers le site que vous désirez ajouter.</span>
						</div>

						<div id="addResourceFormPart2" style="display:none;">  <%-- will not be displayed until the url is valid --%>

							<div class="form-group">
		                        <label for="titre">Intitulé</label> 
		                        <input type="text" class="form-control" id="title" name="title" placeholder="titre" required="required">
		                   		<div class="pull-right"></div>
							</div>
							
							<br />
	                            
                            <div class="row">
                            	
                            	<div class="form-group col-md-4">                            		 
                            		<label for="Format">Format</label>
				                     <c:forEach var="format" items="${applicationScope.cache.format}">
					                  	<div class="radio"> 
					                   	<label for="${format.description}">
					                   		<input type="radio" name="format" value="${format}" id="${format.description}" required="required">
					                   		${format.description}
					                   	</label>
				                       </div> 
								     </c:forEach>
                            	</div>

								<div class="form-group col-md-4">
									<label for="platform">Platforme</label>
									<c:forEach var="platform" items="${applicationScope.cache.platform}">
									<div class="radio">
										<label for="${platform.description}">	
						                   	<input type="radio" name="platform" value="${platform}" id="${platform.description}" required="required"> 
						                    ${platform.description}
						                </label>  
									</div>
									</c:forEach>	                      
                            	</div>

								<div class="form-group col-md-4">
									<label for="topic">Topic</label>
									<c:forEach var="topic" items="${applicationScope.cache.topic}">
									<div class="radio">
										<label for="${topic.description}">
											<input type="radio" name="topic" value="${topic}" id="${topic.description}" required="required"> 
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
						<textarea class="form-control" id="description" name="description"
							placeholder="Description" rows="3"></textarea>
						<div class="pull-right"></div>
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" value="true"
							name="advertising"> Présence de publicité
						</label>
					</div>
					<label for="language">Langue de la resource</label>

						<select class="form-control" name="language">
							<c:forEach var="language"
								items="${applicationScope.cache.language}">
								<option value="${language}">${language.description}</option>
							</c:forEach>
						</select>

					<br>
					<div class="form-group">
						<label for="duration">Durée</label> Moins de <input type="text" class="form-control"
							name="maxDuration">
					</div>

					<div class="form-group">
						<label for="nature">Nature</label>

						<c:forEach var="nature" items="${applicationScope.cache.nature}">
							<div class="radio">
								<label for="${nature.description}">
									<input type="radio" name="nature" id="${nature.description}" value="${nature}"> 
									${nature.description}
								</label>
							</div>
						</c:forEach>

					</div>
				</div>

				<div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <input class="btn btn-primary" type="submit" value="Save changes" >
        </div>
      </div><!-- /.modal-content -->
   </form>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


                    
 
 
 
 
