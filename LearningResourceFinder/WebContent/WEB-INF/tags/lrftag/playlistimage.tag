<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>

<%@ attribute name="playlist"
	type="learningresourcefinder.model.PlayList"%>
<%@ attribute name="random"%>
<%@ attribute name="canEdit"%>

<div id="divPhoto">
	<c:if test="${canEdit}">
		<a data-toggle="modal" href="#modalPlaylist">
	</c:if>

	<figure>
		<c:choose>
			<c:when test="${playlist.picture}">
				<%-- Random, to force the reload of the image in case it changes (but its name does not change) --%>
				<img
					src="/gen/playlist/resized/large/${playlist.id}.jpg<c:if test="${random!=null}">?${random}</c:if>"
					alt="" />
			</c:when>
			<c:otherwise>
				<div class="playlist-no-image">
					I M A G E<br/>S E Q U E N C E
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${canEdit}">
			<figcaption lang="la">
				<span class="lib-change-image"><b>Charger une image</b></span>
			</figcaption>
		</c:if>
	</figure>

	<c:if test="${canEdit}">
		</a>
	</c:if>
</div>
