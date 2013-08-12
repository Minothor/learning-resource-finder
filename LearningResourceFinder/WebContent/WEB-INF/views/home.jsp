<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<body>
   <table visible=0>
   <tr><i>Add a line per feature</i></tr>
   	<!-- Add a line per feature--> 
   	<tr><td>PlayList list</td><td><a href="/playlist/user/${user.userName}" > My list of PlayLists</a></td><td>JU/NOR/EM</td><td></td></tr> 
   	<tr><td>Resources list</td><td><a href="/ressourcelist" >List of Resources</a></td><td>THD/SEB</td><td></td></tr>
   </table>
</body>
</html>

ressourcelist