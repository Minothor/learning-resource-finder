<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="playlist" type="learningresourcefinder.model.PlayList"%>

<div style="display:inline-block; position:relative; vertical-align:top; width: 200px; margin-right:20px; margin-bottom:20px;"
     class="panel panel-default">
  <div class="panel-heading">
       <a href="/playlist/${playlist.shortId}/${playlist.slug}" class="lead">${playlist.name}</a>
  </div>
  
  <c:if test="${playlist.picture}">
    <img src="/gen/playlist/resized/small/${playlist.id}.jpg" alt="${playlist.name}" />
  </c:if>

  <div class="panel-body" style="margin-bottom:20px;">
     <p><small>${playlist.descriptionCut}</small></p>
  </div>

</div>