<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource"%>


<div style = "left:50px; 
background-color: #D3D3D3;
width: 200px;
height: 200px;
 ">
<a href="/resource/${resource.shortId}/${resource.slug}">
	${resource.name}<br />
   <p style="font-size: 10px;"><i>${resource.description}</i></p><br />
</a>
</div>
