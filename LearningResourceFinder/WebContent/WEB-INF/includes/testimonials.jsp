<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="carousel-example-generic" class="carousel slide">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
	  </ol>
	
	  <!-- Wrapper for slides -->
	  <div class="carousel-inner">
	    <div class="item active">
	      Hello I'm a content
	      <div class="carousel-caption">
	        Hello I'm a caption
	      </div>
	    </div>
	    <div class="item">
	      Content 2
	      <div class="carousel-caption">
	        Hello I'm a caption 2
	      </div>
	    </div>
	    <div class="item">
	      Content 3
	      <div class="carousel-caption">
	        Hello I'm a caption 3
	      </div>
	    </div>
	  </div>
	
	  <!-- Controls -->
 	  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev" style="background-image:none">
	    <span class="glyphicon glyphicon-chevron-left"></span>
	  </a> 
	  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next" style="background-image:none">
	    <span class="glyphicon glyphicon-chevron-right"></span>
	  </a>
</div>
<script src="http://getbootstrap.com/assets/js/holder.js"></script>