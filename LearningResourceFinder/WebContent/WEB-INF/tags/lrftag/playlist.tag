<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="playlist" type="learningresourcefinder.model.PlayList"%>


<div style = "left:50px; 
background-color: #D3D3D3;
width: 200px;
height: 200px;
 ">
<a href="/playlist/${playlist.shortId}/${playlist.slug}">
	${playlist.name}<br />
   <p style="font-size: 10px;"><i>${playlist.description}</i></p><br />
</a>
</div>