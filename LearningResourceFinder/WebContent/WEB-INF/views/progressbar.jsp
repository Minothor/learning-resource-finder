<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<div id="progressbar">
	<c:if test="${current.user != null}">
	  <div data-toggle="tooltip" title="${current.user.userProgressPoints} points de contribution (sur ${current.user.accountLevel.levelProgressPoints} pour arriver au niveau suivant).">
		<div style="font-size:10px; position:absolute; top:37px; right:16px" >
		        ${current.user.userProgressPoints} / ${current.user.accountLevel.levelProgressPoints}
		</div>
		<div class="progress" style="height: 5px; margin-bottom: 0px; position:relative;">
			<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: ${100 * current.user.userProgressPoints / current.user.accountLevel.levelProgressPoints}%" style="height:5px;">
				<span class="sr-only"> ${100 * current.user.userProgressPoints / current.user.accountLevel.levelProgressPoints}%</span>
			</div>
		</div>
	  </div>
	</c:if>
</div>
