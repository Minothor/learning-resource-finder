/**************************************************************************
 * Circular Countdown jQuery Plugin
 * @info: http://www.codegrape.com/item/circular-countdown-jquery-plugin/2038
 * @version: 1.0 (11.07.2013)
 * @requires: jQuery v1.7 or later (tested on 1.10.1)
 * @author: flashblue - http://www.codegrape.com/user/flashblue
**************************************************************************/

;(function($) {
	$.fn.circularCountdown = function(options) {		
		
		//Default variables
		var defaults = {
			startDate:"",
			endDate:"",
			timeZone:0,
			past:false,
			
			//Show-hide day, hour, minute, second
			showDay:true,
			showHour:true,
			showMinute:true,
			showSecond:true,
			
			//Margin between circles
			margin:10,
			
			//Diameters
			dayDiameter:160,
			hourDiameter:160,
			minuteDiameter:160,
			secondDiameter:160,
			
			//Circle BG width
			dayBgWidth:23,
			hourBgWidth:23,
			minuteBgWidth:23,
			secondBgWidth:23,
			
			//Circle width
			dayCircleWidth:23,
			hourCircleWidth:23,
			minuteCircleWidth:23,
			secondCircleWidth:23,
			
			//Circle BG color
			dayBgColor:"#ccc",
			hourBgColor:"#ccc",
			minuteBgColor:"#ccc",
			secondBgColor:"#ccc",
			
			//Circle color
			dayCircleColor:"#e8065c",
			hourCircleColor:"#e8065c",
			minuteCircleColor:"#e8065c",
			secondCircleColor:"#e8065c",
			
			//Counter font size
			dayCounterFontSize:32,
			hourCounterFontSize:32,
			minuteCounterFontSize:32,
			secondCounterFontSize:32,
			
			//Text font size
			dayTextFontSize:11,
			hourTextFontSize:11,
			minuteTextFontSize:11,
			secondTextFontSize:11,
			
			//Counter font color
			dayCounterFontColor:"#333",
			hourCounterFontColor:"#333",
			minuteCounterFontColor:"#333",
			secondCounterFontColor:"#333",
			
			//Text font color
			dayTextFontColor:"#666",
			hourTextFontColor:"#666",
			minuteTextFontColor:"#666",
			secondTextFontColor:"#666",
			
			//Texts
			dayText:"DAYS",
			hourText:"HOURS",
			minuteText:"MINUTES",
			secondText:"SECONDS",
			
			//Texts top margin
			dayTextMarginTop:0,
			hourTextMarginTop:0,
			minuteTextMarginTop:0,
			secondTextMarginTop:0,
			
			//Timer on finish function
			onFinish:function(){}
		};
		
		//Options
		var options = $.extend({}, defaults, options);
		
		//Create timer
		return this.each(function() {
			var container = $(this);
			container.addClass("circular-countdown");
			var timer = new CircularCountDown($(this), options);
			timer.init();
		});
		
		//Circular Countdown Class
		function CircularCountDown($obj, options) {
			
			//Variables
			var $timer;
			var $days, $hours, $minutes, $seconds;				
			var currentTime, time, startTimeDiff, endTimeDiff;
			var dTotal, d, h, i, s;
			var maxDiameter;
			
			//Canvas support
			var canvas_support = document.createElement("canvas").getContext;
			
			//Timer on finish function
			var onFinish = typeof(options.onFinish)=="function" ? options.onFinish : function(){};
			
			//Init
			this.init = function() {
				var that = this;
				$timer = $obj;
				maxDiameter = Math.max(options.dayDiameter, options.hourDiameter, options.minuteDiameter, options.secondDiameter);
				
				//Convert date string to time
				options.startDate = this.convertToTime(options.startDate);
				options.endDate = this.convertToTime(options.endDate);				
				
				//Day
				$days = this.addItem(options.showDay, "day", options.dayDiameter, options.dayBgWidth, options.dayCircleWidth, 
										options.dayBgColor, options.dayCircleColor, options.dayCounterFontSize, options.dayTextFontSize, 
										options.dayCounterFontColor, options.dayTextFontColor, options.dayTextMarginTop, options.dayText);
				
				//Hour
				$hours = this.addItem(options.showHour, "hour", options.hourDiameter, options.hourBgWidth, options.hourCircleWidth, 
										options.hourBgColor, options.hourCircleColor, options.hourCounterFontSize, options.hourTextFontSize, 
										options.hourCounterFontColor, options.hourTextFontColor, options.hourTextMarginTop, options.hourText);
										
				//Minute
				$minutes = this.addItem(options.showMinute, "minute", options.minuteDiameter, options.minuteBgWidth, options.minuteCircleWidth, 
										options.minuteBgColor, options.minuteCircleColor, options.minuteCounterFontSize, options.minuteTextFontSize, 
										options.minuteCounterFontColor, options.minuteTextFontColor, options.minuteTextMarginTop, options.minuteText);
				
				//Second
				$seconds = this.addItem(options.showSecond, "second", options.secondDiameter, options.secondBgWidth, options.secondCircleWidth, 
										options.secondBgColor, options.secondCircleColor, options.secondCounterFontSize, options.secondTextFontSize, 
										options.secondCounterFontColor, options.secondTextFontColor, options.secondTextMarginTop, options.secondText);
				
				//On finish event
				$timer.onFinish = onFinish;
				
				//Start timer			
				this.intervalId = setInterval(function(){that.checkTime();}, 1000);
				that.checkTime();
			};	
			
			//Convert string to time
			this.convertToTime = function(date){
				var time = date.split("/").join(" ").split(":").join(" ").split(" ");
				var y = parseInt(time[0], 10);
				var m = parseInt(time[1], 10)-1;
				var d = parseInt(time[2], 10);
				var h = parseInt(time[3], 10);
				var i = parseInt(time[4], 10)-options.timeZone*60;
				var s = parseInt(time[5], 10);
				date = new Date(y, m, d, h, i, s, 0).getTime();
				return date;
			};
			
			//Add item
			this.addItem = function(show, className, diameter, bgWidth, circleWidth, bgColor, circleColor, counterFontSize, 
									textFontSize, counterFontColor, textFontColor, textMarginTop, text) {
				if (show) {
					var w, h, lh, marginTop;
					var x, y, r;
					var $count, $text;
					
					//Create item
					var $item = $('<div class="'+className+' time">\
									<canvas class="bg"></canvas>\
									<canvas class="circle"></canvas>\
									<div class="count">0</div>\
									<div class="text"></div>\
							   </div>');
					
					w = diameter;
					h = !textMarginTop ? diameter : (diameter+textMarginTop+counterFontSize);
					
					$item.css({width:w, height:h, "margin":options.margin+"px"});
					$item.data({"diameter":diameter, "bgWidth":bgWidth, "circleWidth":circleWidth, "circleColor":circleColor});
					$timer.append($item);
					
					//Top margin for different diameter sizes
					if (diameter!=maxDiameter) {
						marginTop = Math.round((maxDiameter-diameter)/2);
						$item.css("margin-top", marginTop+"px");
					}
					
					//Count
					$count = $item.find(".count");
					lh = !textMarginTop ? (diameter-textFontSize) : diameter;
					$count.css({width:diameter, height:diameter, "line-height":lh+"px", "font-size":counterFontSize+"px", "color":counterFontColor});
					
					//Text
					$text = $item.find(".text");
					lh = !textMarginTop ? (diameter+counterFontSize) : counterFontSize;
					marginTop = !textMarginTop ? 0 : (diameter+textMarginTop);
					$text.text(text).css({width:diameter, height:diameter, "line-height":lh+"px", "font-size":textFontSize+"px", "color":textFontColor, "margin-top":marginTop+"px"});
					
					//BG circle
					if (canvas_support) {
						x = y = diameter/2;
						r = x-bgWidth/2;
						
						var bg = $item.find("canvas.bg")[0].getContext("2d");
						
						bg.canvas.width = diameter;
						bg.canvas.height = diameter;
						bg.lineWidth = bgWidth;
						bg.strokeStyle = bgColor;
						bg.clearRect(0, 0, bgWidth, bgWidth);
						bg.beginPath();
						bg.arc(x, y, r, 0, Math.PI*2, true);
						bg.stroke();
						bg.closePath();
					}
				} else {
					$item.hide();	
				}
				
				return $item;
			};
			
			//Counter circle
			this.circle = function($item, now, total) {
				var diameter = $item.data("diameter");
				var circleWidth = $item.data("circleWidth");
				var circleColor = $item.data("circleColor");
				var x, y, r;		
					
				x = y = diameter/2;
				r = x-circleWidth/2;
				
				//Count
				var $count = $item.find(".count");
				$count.text(now);
				
				//Circle
				if (canvas_support) {
					var circle = $item.find("canvas.circle")[0].getContext("2d");
					
					circle.canvas.width = diameter;
					circle.canvas.height = diameter;
					circle.lineWidth = circleWidth;
					circle.strokeStyle = circleColor;
					circle.clearRect(0, 0, circleWidth, circleWidth);
					circle.beginPath();
					circle.arc(x, y, r, (Math.PI*2*(now/total))-(Math.PI/2), -Math.PI/2, true);
					circle.stroke();
					circle.closePath();
				}
			};
			
			//Check current time
			this.checkTime = function() {
				time = new Date();
				currentTime = time.getTime()+time.getTimezoneOffset()*60*1000;
				
				//End date
				endTimeDiff = !options.past ? options.endDate-currentTime : currentTime-options.startDate;
				if (endTimeDiff<0) {
					clearInterval(this.intervalId);
					endTimeDiff = 0;
					$timer.onFinish.call(this);
				}
				
				//Start date
				startTimeDiff = options.endDate-options.startDate;
				
				//Total days	
				dTotal = Math.floor(startTimeDiff/(86400000));
				
				//Format time
				this.timeFormat(endTimeDiff);	
				
				//Day
				if (options.showDay) {
					this.circle($days, d, dTotal);
				}
				
				//Hour
				if (options.showHour) {
					this.circle($hours, h, 24);
				}
				
				//Minute
				if (options.showMinute) {
					this.circle($minutes, i, 60);
				}
				
				//Second
				if (options.showSecond) {
					this.circle($seconds, s, 60);
				}
			};
			
			//Time format
			this.timeFormat = function(msec){				
				var endTime = Math.floor(msec/1000);
				s = endTime%60;
				i = Math.floor(endTime%(3600)/60);
				h = Math.floor(endTime%(86400)/3600);
				d = Math.floor(endTime/86400);				
			};
			
		}
	};		
})(jQuery);