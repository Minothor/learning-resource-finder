<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/tags/lrf.tld' prefix='lrf'%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag"%>
<html>
<head>
 <script type="text/javascript">

 $(document).ready(function() {
 	$('#testForm').submit(test);
 });
function test(e){
	e.preventDefault();
	alert("test message");
	location.reload();
};
 
 </script>
</head> 
<body>
<div class="container">

   <table visible=0>
	   <tr><i>Add a line per feature</i></tr>
	   	<!-- Add a line per feature--> 
	   	<tr><td>PlayList list</td><td><a href="/playlist/user/${user.userName}" > My list of PlayLists</a></td><td>JU/NOR/EM</td><td></td></tr> 
	   	<tr><td>Resources list</td><td><a href="/ressourcelist" >List of Resources</a></td><td>THD/SEB</td><td></td></tr>
   </table>
   <form  id="testForm" action="" methode="post">
      <div>
        <label>FirstName</label><input type="text"  name="firstname" size="30" required/>
        <label>LastName</label><input type="text"   name="lastname" size="30" required/>
        <input type="submit"  name="bouton" value="tester"> 
      </div>
   </form>   
   <a href="/test/addMsg">Générer un message (serveur)</a><br/>
   <span onclick='showNotificationText("Hello", "danger");'>Générer un message (javascript)</span>
   
</div>   
</body>
</html>
