<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<%-- Will be injected in a page having already a head, body and html element. The form element is even already there. We just need the inputs. --%>


 <div class="form-group">
    <label for="title">Titre</label> 
	<input type="text" class="form-control" name="title" id="title" placeholder="titre...">
	  <span class="help-block"> 
	       utilisé dans les listes, le titre doit donner un un coup d'oeil, une idée claire du contenu de cette ressource.
      </span>
 </div>
 
 <div class="form-group">
   <label for="language">Langue</label> 
   <input type="checkbox" class="form-control" name="language" id="language" placeholder="langue...">
</div>
 
 <div class="form-group">  
            <label  for="format">Format</label>  
            <div class="controls">  
              <label class="checkbox">  
                <input type="checkbox" id="optionsCheckbox" value="option1">  
                Option one is this and that—be sure to include why it's great  
              </label>  
            </div>  
          </div>  
 
 
 <div class="form-group">
   <label for="title">Déscription</label> 
   <textarea class="form-control" rows="3" id="description" name="description" placeholder="déscription..."></textarea>
	 <span class="help-block"> 
	       la déscription doit donner assez d'information au visiteur pour qu'il puisse décider, sans qu'il aille voir
           la ressource, si elle correspond à ce qu'il recherche.
     </span>
 </div>

