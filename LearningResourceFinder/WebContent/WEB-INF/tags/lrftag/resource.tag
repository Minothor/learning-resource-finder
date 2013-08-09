<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="resource" type="learningresourcefinder.model.Resource"%>


<div style = "position:relative; left:50px; 
background-color: #D3D3D3;
position: relative;
padding: 10px;
margin-top: 10px;
width: 200px;
height: 200px;
float: left;
margin-left: 10px;
 ">
<a href="/resource?id=${resource.id}">
	${resource.name}<br />
   <p style="font-size: 10px;"><i>${resource.description}</i></p><br />
</a>
</div>
