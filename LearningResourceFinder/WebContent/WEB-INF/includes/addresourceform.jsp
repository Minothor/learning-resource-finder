<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
                              <fieldset>
                                  <Legend>format</Legend>
                                  <input type="radio" name="format" value="VIDEOS">
<%--                                     <form:form modelAttribute="searchOptions">      --%>
<%-- 				                        <c:forEach var="format" items="${applicationScope.cache.formatEnumAllValues}"> --%>
<%-- 					                      <div class="radio"> <form:radiobutton path="format"  --%>
<%-- 					                       value="${format}" />${format.description} </div> --%>
<%-- 									    </c:forEach> --%>
<%--                                    </form:form>  --%>
                              </fieldset>
                            </div>  
          
                            <div class="form-group">  
                                  <Legend>Platforme</Legend>
                                   <input type="radio" name="platform" value="PC">
<%--                                       <form:form modelAttribute="searchOptions">      --%>
<%-- 					                     <c:forEach var="platform" items="${platformsEnumAllValues}"> --%>
<%-- 					                        <div class="radio"> <form:radiobutton path="platform"  --%>
<%-- 					                         value="${platform}" />${platform.description} --%>
<!-- 					                        </div> -->
<%-- 				                       </c:forEach> --%>
<%--                                      </form:form>  --%>
                            </div>  
                            
						</div>
					</div>
                  </div>
				  <div class="modal-footer" style="display: none;" id="bottomButtons">
					
					<button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
				    <button type="button" class="btn btn-primary" onclick="ajaxResourceAddSubmitModal1()">Ajouter</button>
				 
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
	 <input type="hidden"  name="idresource" id="idresource"  />
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Ajout d'une ressource (partie 2)</h4>
        </div>
        <div class="modal-body">
     
		  
		  
	          <div class="form-group">
		       	   <label for="titre">Description</label> 
				   <input type="text" class="form-control" id="description" name="description" placeholder="description">
			       <div class="pull-right"> </div>
			  </div>
	          <div class="checkbox">
	            <label>
	              <input type="checkbox"> Pub
	            </label>
	          </div>
	     
	          <select class="form-control">
	            <option></option>
	          </select>
        </div>
    
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
   </form>
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

--------------------------------------------------


                    
 
 
 
 
