    $(function () {
    	var austDay = new Date();
    	austDay = new Date(austDay.getFullYear() + 1, 1 - 1, 26);
    	$('#defaultCountdown').countdown({until: austDay});
    	$('#year').text(austDay.getFullYear());
    });
    
    $(document).ready(function() {
        $('nav a').click(function(){
    		$('nav a').removeClass("cbp-fbcurrent")
    		$(this).addClass("cbp-fbcurrent");
    	});
    });  
    
    $(document).ready(function() {
        $(".countdown").circularCountdown (
                {
                    startDate:"2013/09/14 12:30:00",  //Year/Month/Day Hours:Minutes:Seconds
                    endDate:"2014/01/15 14:30:00",
                    timeZone:+2,
                    past:false,

                    //Show-hide day, hour, minute, second
                    showDay:true,
                    showHour:true,
                    showMinute:true,
                    showSecond:true,

                    //Margin between circles
                    margin:12.5,

                    //Diameters
                    dayDiameter:180,
                    hourDiameter:180,
                    minuteDiameter:180,
                    secondDiameter:180,

                    //Circle BG width
                    dayBgWidth:18,
                    hourBgWidth:18,
                    minuteBgWidth:18,
                    secondBgWidth:18,

                    //Circle width
                    dayCircleWidth:18,
                    hourCircleWidth:18,
                    minuteCircleWidth:18,
                    secondCircleWidth:18,

                    //Circle BG color
                    dayBgColor:"#ccc",
                    hourBgColor:"#ccc",
                    minuteBgColor:"#ccc",
                    secondBgColor:"#ccc",

                    //Circle color
                    dayCircleColor:"#e8065c",
                    hourCircleColor:"#00A5D7",
                    minuteCircleColor:"#80BB27",
                    secondCircleColor:"#F18D00",

                    //Counter font size
                    dayCounterFontSize:32,
                    hourCounterFontSize:32,
                    minuteCounterFontSize:32,
                    secondCounterFontSize:32,

                    //Text font size
                    dayTextFontSize:18,
                    hourTextFontSize:18,
                    minuteTextFontSize:18,
                    secondTextFontSize:18,

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
                    dayText:"jours",
                    hourText:"heures",
                    minuteText:"minutes",
                    secondText:"secondes",

                    //Texts top margin
                    dayTextMarginTop:0,
                    hourTextMarginTop:0,
                    minuteTextMarginTop:0,
                    secondTextMarginTop:0,

                    //Timer on finish function
                    onFinish:function(){}
                });

           
    });