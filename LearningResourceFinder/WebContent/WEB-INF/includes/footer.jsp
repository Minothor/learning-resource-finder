<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<footer>
	<section id="footer-main">
		<div class="container">
		
			<div class="one-fourth column">
				<h4>About Us</h4>
				<hr class="smalline">
				<p>Per modo inermis disputando cu, facilis scaevola vix ei. Sit ad omnium volutpat prodesset, 
				eidicta expetenda sensibus definitiones his, vide omittantur vixen velum meliore moderatius te.</p>
				<div class="clear"></div>
				<a style="padding-top: 15px; display: block;" href="about.html"><i class="icon-info-sign"></i> Read More </a>
			</div>
			
			<div class="one-fourth column">
				<h4>Recent News</h4>
				<hr class="smalline">
				<ul class="footer-recent-news">
					<li><a href="" alt="" title=""><i class="icon-circle-arrow-right"></i> Ex tantas urbanitas repudiandae</a></li>
					<li><a href="" alt="" title=""><i class="icon-circle-arrow-right"></i> Tantas urbanitas repudiandae</a></li>
					<li><a href="" alt="" title=""><i class="icon-circle-arrow-right"></i> Facer partiendo scriptorem exiv</a></li>
					<li><a href="" alt="" title=""><i class="icon-circle-arrow-right"></i> Soluta sensibus usuet adan</a></li>
					<li><a href="" alt="" title=""><i class="icon-circle-arrow-right"></i> Scripta abhorreant consectetuer</a></li>
				</ul>
			</div>				
			
			<div class="one-fourth column">
				<h4>Latest Tweets</h4>
				<hr class="smalline">
				<ul id="twitter-feed"></ul>
				<script type="text/javascript">
					jQuery(document).ready(function($){
					$.getJSON('get-tweets.php?url='+encodeURIComponent(), function(tweets){
						$("#twitter-feed").html(tz_format_twitter(tweets));
					}); });
				</script>
			</div>	
			
			<div class="one-fourth column">
				<h4>Contact</h4>
				<hr class="smalline">
				<ul class="getintouch">
					<li><i class="icon-map-marker"></i><strong>Adress: </strong>85 Green Street, TheCity</li>
					<li><i class="icon-phone"></i><strong>Phone: </strong>(+48) 799 456 158</li>
					<li><i class="icon-envelope"></i><strong>Mail: </strong>contact@domain.com</li>
					<li><a href="contact.html"><i class="icon-pencil"></i>Go to contact form</a></li>
				</ul>
			</div>				

		</div>
	</section>
	
	<section class="footer-bar">
		<div class="container">
			<div class="eight columns">
				<div class="footer-logo"><img src="images/logo.png" alt="" title=""><span>2013 © Copyright Vena.</span></div>
			</div>
			
			<div class="eight columns">
				<ul class="footer-links">
					<li><a href="#"><i class="icon-twitter"></i></a></li>
					<li><a href="#"><i class="icon-facebook"></i></a></li>
					<li><a href="#"><i class="icon-linkedin"></i></a></li>
					<li><a href="#"><i class="icon-pinterest"></i></a></li>
					<li><a href="#"><i class="icon-google-plus"></i></a></li>
				</ul>
			</div>
		</div>
	</section>
</footer>