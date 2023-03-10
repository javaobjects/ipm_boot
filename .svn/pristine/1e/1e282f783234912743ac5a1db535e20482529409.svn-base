$(document).ready(function(){
    /* --------------------------------------------------------
	Components组件
    -----------------------------------------------------------*/
    (function(){
        /* Textarea */
        if($('.auto-size')[0]) {
            $('.auto-size').autosize();
        }
        //Select
        if($('.select')[0]) {
            $('.select').selectpicker();
        }
        //Tag Select
        if($('.tag-select')[0]) {
            $('.tag-select').chosen();
        }
        /* Tab */
        if($('.tab')[0]) {
            $('.tab a').click(function(e) {
                e.preventDefault();
                $(this).tab('show');
            });
        }
        /* Collapse */
        if($('.collapse')[0]) {
            $('.collapse').collapse();
        }
        /* Accordion */
        $('.panel-collapse').on('shown.bs.collapse', function () {
            $(this).prev().find('.panel-title a').removeClass('active');
        });
        $('.panel-collapse').on('hidden.bs.collapse', function () {
            $(this).prev().find('.panel-title a').addClass('active');
        });
        //Popover
    	if($('.pover')[0]) {
    	    $('.pover').popover();
    	} 
    })();
    /* --------------------------------------------------------
	Sidebar + Menu
    -----------------------------------------------------------*/
    (function(){
        /* Menu Toggle */
        $('body').on('click touchstart', '#menu-toggle', function(e){
            e.preventDefault();
            $('html').toggleClass('menu-active');
            $('#sidebar').toggleClass('toggled');
            //$('#content').toggleClass('m-0');
        });
        /* Active Menu */
        $('#sidebar .menu-item').hover(function(){
            $(this).closest('.dropdown').addClass('hovered');
        }, function(){
            $(this).closest('.dropdown').removeClass('hovered');
        });
        /* Prevent */
        $('.side-menu .dropdown > a').click(function(e){
            e.preventDefault();
        });
    })();
    /* --------------------------------------------------------
	Chart Info
    -----------------------------------------------------------*/
    (function(){
        $('body').on('click touchstart', '.tile .tile-info-toggle', function(e){
            e.preventDefault();
            $(this).closest('.tile').find('.chart-info').toggle();
        });
    })();
    /* --------------------------------------------------------
	Todo List事項清單
    -----------------------------------------------------------*/
    (function(){
        setTimeout(function(){
            //Add line-through for alreadt checked items
            $('.todo-list .media .checked').each(function(){
                $(this).closest('.media').find('.checkbox label').css('text-decoration', 'line-through')
            });
            //Add line-through when checking
            $('.todo-list .media input').on('ifChecked', function(){
                $(this).closest('.media').find('.checkbox label').css('text-decoration', 'line-through');
            });
            $('.todo-list .media input').on('ifUnchecked', function(){
                $(this).closest('.media').find('.checkbox label').removeAttr('style');
            });    
        })
    })();
    (function(){
        $('body').on('click touchstart', '.drawer-toggle', function(e){
            e.preventDefault();
            var drawer = $(this).attr('data-drawer');
            $('.drawer:not("#'+drawer+'")').removeClass('toggled');
            if ($('#'+drawer).hasClass('toggled')) {
                $('#'+drawer).removeClass('toggled');
            }
            else{
                $('#'+drawer).addClass('toggled');
            }
        });
        //Close
        $('body').on('click touchstart', '.drawer-close', function(){
            $(this).closest('.drawer').removeClass('toggled');
            $('.drawer-toggle').removeClass('open');
        });
    })();
    /* --------------------------------------------------------
	Calendar
    -----------------------------------------------------------*/
    (function(){
        //Sidebar
        if ($('#sidebar-calendar')[0]) {
            var date = new Date();
            var d = date.getDate();
            var m = date.getMonth();
            var y = date.getFullYear();
            $('#sidebar-calendar').fullCalendar({
                editable: false,
                events: [],
                header: {
                    left: 'title'
                }
            });
        }
        //Content widget
        if ($('#calendar-widget')[0]) {
            $('#calendar-widget').fullCalendar({
                header: {
                    left: 'title',
                    right: 'prev, next'
                },
                editable: true,
                events: [
                    {
                        title: 'All Day Event',
                        start: new Date(y, m, 1)
                    },
                    {
                        title: 'Long Event',
                        start: new Date(y, m, d-5),
                        end: new Date(y, m, d-2)
                    },
                    {
                        title: 'Repeat Event',
                        start: new Date(y, m, 3),
                        allDay: false
                    },
                    {
                        title: 'Repeat Event',
                        start: new Date(y, m, 4),
                        allDay: false
                    }
                ]
            });
        }
    })();
    /* --------------------------------------------------------
	RSS Feed widget
    -----------------------------------------------------------*/
    (function(){
	if($('#news-feed')[0]){
	    $('#news-feed').FeedEk({
		FeedUrl: 'http://rss.cnn.com/rss/edition.rss',
		MaxCount: 5,
		ShowDesc: false,
		ShowPubDate: true,
		DescCharacterLimit: 0
	    });
    }
    })();
    /* --------------------------------------------------------
	Chat
    -----------------------------------------------------------*/
    $(function() {
        $('body').on('click touchstart', '.chat-list-toggle', function(){
            $(this).closest('.chat').find('.chat-list').toggleClass('toggled');
        });
        $('body').on('click touchstart', '.chat .chat-header .btn', function(e){
            e.preventDefault();
            $('.chat .chat-list').removeClass('toggled');
            $(this).closest('.chat').toggleClass('toggled');
        });
        $(document).on('mouseup touchstart', function (e) {
            var container = $('.chat, .chat .chat-list');
            if (container.has(e.target).length === 0) {
                container.removeClass('toggled');
            }
        });
    });
    /* --------------------------------------------------------
	Form Validation表单验证
    -----------------------------------------------------------*/
    (function(){
	if($("[class*='form-validation']")[0]) {
	    $("[class*='form-validation']").validationEngine();
	    //Clear Prompt
	    $('body').on('click', '.validation-clear', function(e){
		e.preventDefault();
		$(this).closest('form').validationEngine('hide');
	    });
	}
    })();
    /* --------------------------------------------------------
     `Color Picker
    -----------------------------------------------------------*/
    (function(){
        //Default - hex
        if($('.color-picker')[0]) {
            $('.color-picker').colorpicker();
        }
        //RGB
        if($('.color-picker-rgb')[0]) {
            $('.color-picker-rgb').colorpicker({
                format: 'rgb'
            });
        }
        //RGBA
        if($('.color-picker-rgba')[0]) {
            $('.color-picker-rgba').colorpicker({
                format: 'rgba'
            });
        }
        //Output Color
        if($('[class*="color-picker"]')[0]) {
            $('[class*="color-picker"]').colorpicker().on('changeColor', function(e){
                var colorThis = $(this).val();
                $(this).closest('.color-pick').find('.color-preview').css('background',e.color.toHex());
            });
        }
    })();
    /* --------------------------------------------------------
     Input Slider
     -----------------------------------------------------------*/
    (function(){
        if($('.input-slider')[0]) {
            $('.input-slider').slider().on('slide', function(ev){
                $(this).closest('.slider-container').find('.slider-value').val(ev.value);
            });
        }
    })();
    /* --------------------------------------------------------
     WYSIWYE Editor + Markedown
     -----------------------------------------------------------*/
    (function(){
        //Markedown
        if($('.markdown-editor')[0]) {
            $('.markdown-editor').markdown({
                autofocus:false,
                savable:false
            });
        }
        //WYSIWYE Editor
        if($('.wysiwye-editor')[0]) {
            $('.wysiwye-editor').summernote({
                height: 200
            });
        }
    })();
    /* --------------------------------------------------------
     Media Player
     -----------------------------------------------------------*/
    (function(){
        if($('audio, video')[0]) {
            $('audio,video').mediaelementplayer({
                success: function(player, node) {
                    $('#' + node.id + '-mode').html('mode: ' + player.pluginType);
                }
            });
        }
    })();

    /* ---------------------------
	Image Popup [Pirobox]
    --------------------------- */
    (function() {
        if($('.pirobox_gall')[0]) {
            //Fix IE
            jQuery.browser = {};
            (function () {
                jQuery.browser.msie = false;
                jQuery.browser.version = 0;
                if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
                    jQuery.browser.msie = true;
                    jQuery.browser.version = RegExp.$1;
                }
            })();
            //Lightbox
            $().piroBox_ext({
                piro_speed : 700,
                bg_alpha : 0.5,
                piro_scroll : true // pirobox always positioned at the center of the page
            });
        }
    })();
    /* ---------------------------
     Vertical tab
     --------------------------- */
    (function(){
        $('.tab-vertical').each(function(){
            var tabHeight = $(this).outerHeight();
            var tabContentHeight = $(this).closest('.tab-container').find('.tab-content').outerHeight();
            if ((tabContentHeight) > (tabHeight)) {
                $(this).height(tabContentHeight);
            }
        });
        $('body').on('click touchstart', '.tab-vertical li', function(){
            var tabVertical = $(this).closest('.tab-vertical');
            tabVertical.height('auto');

            var tabHeight = tabVertical.outerHeight();
            var tabContentHeight = $(this).closest('.tab-container').find('.tab-content').outerHeight();

            if ((tabContentHeight) > (tabHeight)) {
                tabVertical.height(tabContentHeight);
            }
        });
    })();
    /* --------------------------------------------------------
     Login + Sign up
    -----------------------------------------------------------*/
    (function(){
        $('body').on('click touchstart', '.box-switcher', function(e){
            e.preventDefault();
            var box = $(this).attr('data-switch');
            $(this).closest('.box').toggleClass('active');
            $('#'+box).closest('.box').addClass('active');
        });
    })();
    /* --------------------------------------------------------
        MAC Hack 
    -----------------------------------------------------------*/
    (function(){
	//Mac only
        if(navigator.userAgent.indexOf('Mac') > 0) {
            $('body').addClass('mac-os');
        }
    })();
});
$(window).load(function(){
    /* --------------------------------------------------------
     Tooltips
     -----------------------------------------------------------*/
    (function(){
        if($('.tooltips')[0]) {
            $('.tooltips').tooltip();
        }
    })();
    /* --------------------------------------------------------
     Animate numbers
     -----------------------------------------------------------*/
    $('.quick-stats').each(function(){
        var target = $(this).find('h2');
        var toAnimate = $(this).find('h2').attr('data-value');
        // Animate the element's value from x to y:
        $({someValue: 0}).animate({someValue: toAnimate}, {
            duration: 1000,
            easing:'swing', // can be anything
            step: function() { // called on every step
                // Update the element's text with rounded-up value:
                target.text(commaSeparateNumber(Math.round(this.someValue)));
            }
        });
        function commaSeparateNumber(val){
            while (/(\d+)(\d{3})/.test(val.toString())){
                val = val.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
            }
            return val;
        }
    });
});

/* --------------------------------------------------------
Date Time Widget日期时间控件
-----------------------------------------------------------*/
(function(){
    // 当前系统时间
    $.ajax({
        url:"/systemSet/readDateTimeSet.do",
        type:"post",
        data:"",
        dataType:"json",
        success:function(data){
            var serverTime = data.nowTime.split(" ")[1],
                hours = serverTime.split(":")[0]-0,
                minutes = serverTime.split(":")[1]-0,
                seconds = serverTime.split(":")[2]-0;
            $("#hours").html(( hours < 10 ? "0":"" )+hours);
            $("#min").html(( minutes < 10 ? "0":"" )+minutes);
            $("#sec").html(( seconds < 10 ? "0":"" )+seconds);
            setInterval( function(){
                seconds += 1;
                if(seconds>59){
                    seconds = 0;
                    minutes +=1;
                    if(minutes>59){
                        minutes = 0;
                        hours +=1;
                        if(hours>23){
                            hours = 0;
                        }
                    }
                }
                $("#hours").html(( hours < 10 ? "0":"" )+hours);
                $("#min").html(( minutes < 10 ? "0":"" )+minutes);
                $("#sec").html(( seconds < 10 ? "0":"" )+seconds);
            },1000);
        }
    });
})();

// logo-------------------------------------------------------------
(function() {
	var jquery_div = $(".footer-width");
	var div_width = function() {
		var width = $(window).width() / 2;
		jquery_div.each(function() {
			$(this).css("width", width - 210);
		});
	};
	window.onresize = div_width;
	div_width();
})();
//----------解决表格thead与tbody不对齐的bug-------------------
$("#menu-toggle").click(function(){
    $('.table').bootstrapTable('resetView');
});
//-------------操作表格伸缩------------------
$(".blockHead").click(function(){
    if($(this).children("i").hasClass("ti-minus")){
        $(this).children("i").removeClass("ti-minus").addClass("ti-plus");
    }else {
        $(this).children("i").removeClass("ti-plus").addClass("ti-minus");
    }
    $(this).siblings().toggle("600");
});
/*系统设置及存储所有页面收缩 此代码下边代码不一样切勿删除*/
$(".block-header").click(function(){
    if($(this).find("i").hasClass("ta-minus")){
        $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
        $(this).parent().addClass("m-f-15");
    }else {
        $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
        $(this).parent().removeClass("m-f-15");
    }
    $(this).siblings().toggle("600");
});
/*系统所有页面收缩*/
$(".block-headers").click(function(){
    if($(this).find("i").hasClass("ta-minus")){
        $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
        $(this).parent().addClass("m-f-15");
    }else {
        $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
        $(this).parent().removeClass("m-f-15");
    }
    $(this).siblings().toggle("600");
});
