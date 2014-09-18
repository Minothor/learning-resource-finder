<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<script type="text/javascript" src="/js/int/urlGeneric.js"></script>

<!-- Modal for adding a generic url (invisible until button clicked) -->
<div class="modal fade" id="addUrlModal1" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">

		<form id="addUrlForm1" role="form" method="post"<%-- action bound with javascript --%>>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="resetForm()">&times;</button>
					<h4 class="modal-title">Ajouter une url générique</h4>
				</div>

				<div class="modal-body">
					<div class="form-horizontal container">
						<div class="form-group">
							<label for="url">site</label> <input type="text"
								class="form-control" id="urlGenericAddField" name="url"
								placeholder="http://..." pattern="https?://.+"
								title="Votre URL doit commencer par http:// ou https://">
							<span class="help-block">Ajouter une URL d'un site trop général.</span>
							<div class="pull-right">
								<button type="button" class="btn btn-mini btn-primary"
									id="urlGenericCheckButton" onclick="ajaxVerifyUrlGeneric()">Vérifier</button>
							</div>
							<span id="urlGenericErrorMessage" class="text-warning"></span>
							<%-- Place holder filled by JavaScript after ajax response --%>
						</div>

					</div>
				</div>
				<div class="modal-footer" style="display: none;" id="bottomButtons">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick='resetForm()'>Annuler</button>
					<input class="btn btn-primary" type="submit" value="Ajouter">
				</div>
			</div>
			<!-- /.modal-content -->
		</form>
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->








