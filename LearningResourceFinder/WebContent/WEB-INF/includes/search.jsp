<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/lrftag/" prefix="lrftag" %>

<%------------------------------------ SEARCH ---------------------------------%>
<div style="background-color:#17A7D6;">
<div class="container" style="padding:20px 20px;">
<div class="row">
  <div class="col-md-8 col-md-offset-2">
  		<form role="search" method="get" action="/search">
  				   <div class="form-group">
					<div class="input-group" >
						<input name="searchphrase"  type="text" class="form-control input-lg searchinput" placeholder="mot(s) clef(s)  ex: multiplication, corps humain, subjonctif..." required>
						<span class="input-group-btn">
							<button class="btn btn-default btn-lg" type="submit">Rechercher</button>
						</span>
					</div>
				  </div>
		</form>
  </div>
</div>  
</div>
</div>
