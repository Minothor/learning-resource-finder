<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %> 
<%@ attribute name="title" required="true" type="java.lang.String"%> 
<%@ attribute name="resourceid" required="true" type="java.lang.Long"%>


<!-- Modal -->
  <div class="modal fade" id="modalProblemReport" tabindex="-1" role="dialog" aria-labelledby="modalProblemReport" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
      <form class="form-horizontal" role="form" id="formProblemReport" method="post">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Reporter un problème pour la ressource : ${title}</h4>
        </div>
        <div class="modal-body">
        	
			  <div class="form-group">
			    <label for="titreProblemReport" class="col-lg-2 control-label">Titre</label>
			    <div class="col-lg-10">
			      <input type="text" class="form-control" id="titreProblemReport" placeholder="Titre" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="descriptionProblemReport" class="col-lg-2 control-label">Description</label>
			    <div class="col-lg-10">
			      <textarea rows="5" cols="10" class="form-control" id="descriptionProblemReport" placeholder="Description" required></textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-lg-offset-2 col-lg-10">
			      <input type="hidden" name="idResourceProblemReport" id="idResourceProblemReport" value="${resourceid}" />
			    </div>
			  </div>
			 
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
          <button type="submit" class="btn btn-primary">Envoyer</button>
        </div>
        </form>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->