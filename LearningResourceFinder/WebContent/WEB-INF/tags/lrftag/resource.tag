<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>
<%@ attribute name="res" type="learningresourcefinder.model.Resource"%>

<table>
	<div class="name">
		${res.name}
	</div>
	<div class="description">
		<i>${res.description}</i>
	</div>
</table>