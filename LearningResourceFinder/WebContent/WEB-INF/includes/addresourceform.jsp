
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
					<div class="form-horizontal">
						<div class="form-group">
	                        <label for="url">site</label> 
	                        <input type="text" class="form-control" id="url" name="url" placeholder="http://...">
	                        <div class="pull-right"> 
	                           <button type="button" class="btn btn-mini btn-primary" id="urlCheckButton" onclick="ajaxVerifyUrl()">Vérifier</button>
							</div>
							<span class="help-block">URL vers le site que vous désirez ajouter.</span>
						</div>

						<div id="addResourceFormPart2" style="display:none;">  <%-- will not be displayed until the url is valid --%>

						    <div class="form-group">
	                           <label for="titre">Intitulé</label> 
	                           <input type="text" class="form-control" id="title" name="title" placeholder="titre">
	                            <div class="pull-right"></div>
                            </div>
                            <div class="form-group ">  
                             
                                  <Legend>format</Legend>
                                      
				                     <c:forEach var="format" items="${applicationScope.cache.format}">
					                   <div class="radio"> 
				                      <input type="radio" name="format" value="${format}">
				                        ${format} 
				                           
				                       </div> 
								     </c:forEach>
                                 
                            </div>  
          
                            <div class="form-group">  
                                  <Legend>Platforme</Legend>
                                                                
					             <c:forEach var="platform" items="${applicationScope.cache.platform}">
					                <div class "radio">
					                   <input type="radio" name="platform" value="${platform}"> 
					                      ${platform}
					                      
					                </div>
					             </c:forEach>
					                      
				                       
                                    
                            </div>  
                            
						</div>
					</div>
                  </div>
				  <div class="modal-footer" style="display: none;" id="bottomButtons">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
				    <button type="button" class="btn" data-toggle="modal" href="#addResourceModal2" onclick="ajaxResourceAddSubmitModal1()">Ajouter</button>
				     
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
	<form id="addResourceForm2" role="form" method="post" action="resourceaddsubmit">
	 <input type="hidden"  name="idresource" id="idresource" />
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Ajout d'une ressource (partie 2)</h4>
        </div>
        <div class="modal-body">
     
		  
		  
	          <div class="form-group">
		       	   <label for="description">Description</label> 
				   <input type="text" class="form-control" id="description" name="description" placeholder="description">
			       <div class="pull-right"> </div>
			  </div>
	          <div class="checkbox">
	            <label>
	              <input type="checkbox" value="true" name="advertising"> Pub 	
	            </label>
	          </div>
	     
	         <select class="form-control"name="language">
	          <c:forEach var="language" items="${applicationScope.cache.language}">
	            <option value="${language}">${language.description}</option>
	          </c:forEach>
	          </select>
	          
	           <div class="form-group">
				<label for="duration">Durée</label>
				Moins de <input type="text" name="maxDuration">
			</fieldset>
		</div>
        </div>
    
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="ajaxResourceAddSubmitModal2()">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
   </form>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


                    
 
 
 
 
