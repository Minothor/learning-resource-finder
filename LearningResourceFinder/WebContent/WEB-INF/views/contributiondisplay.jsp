<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<html>
<body>
	<div class="container">
		<lrftag:pageheadertitle title="Contributions de ${user.getFullName()}" />


		<br />

		<div class="table-responsive">
			<table>
				<tr>
					<td>Total général des points de contributions :
						${contributionsPoints}</br> Points pour le niveau en cours :
						${levelProgress}
					</td>
					<td><a class="btn btn-primary addToolTip"
						href="/recomputeContributionPoints"
						title="Si vous avez un doute sur votre total, ce bouton parcours la base de données afin de recalculer vos points à partir de chaque contribution">recalculer
							les points</a></td>
				</tr>	
			</table>

			<div></div>

			<table class="table table-bordered">
				<tr>
					<td><strong>Resource</strong></td>
					<td><strong>Action</strong></td>
					<td><strong>Date</strong></td>
					<td><strong>Points</strong></td>
				</tr>
				<c:forEach items="${contributions}" var="contribution">
					<tr>
						<td><a
							href="<c:url value='resource/${contribution.ressource.shortId}/${contribution.ressource.name}'/>">${contribution.ressource.name}</a>
						</td>
						<td>${contribution.action.describe}</td>
						<td>
						<span class="addToolTip" title="${contribution.createdOn}">
						<lrf:datedisplay  date="${contribution.createdOn}"
								duration="true" withspan="false" />
								</span>
								
								</td>
						<td>${contribution.points}</td>
					</tr>
				</c:forEach>
			</table>
			<div>
				Total général des points de contributions : ${contributionsPoints}</br>
				Points pour le niveau en cours : ${levelProgress}
			</div>
		</div>
	</div>
</body>
</html>