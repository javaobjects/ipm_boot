 /**
    * 背景皮肤更换及保存
*/
$(document).ready(function(){
    var settings = '<a id="settings" href="#changeSkin" data-toggle="modal">' +
        '<i class="glyphicon glyphicon-cog"></i> 皮肤设置' +
        '</a>' +
        '<div class="modal fade" id="changeSkin" tabindex="-1" role="dialog" aria-hidden="true">' +
        '<div class="modal-dialog modal-lg">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
        '<h4 class="modal-title">更改皮肤</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<div class="row template-skins logoutChange">' +
        '<a data-skin="skin-blur-night" data-value="1" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-night.jpg" alt="">' +
        '</a>' +
        '<a data-skin="skin-blur-violate" data-value="2" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-violate.jpg" alt="">' +
        '</a>' +
        '<a data-skin="skin-blur-lights" data-value="3" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-lights.jpg" alt="">' +
        '</a>' +
        '<a data-skin="skin-blur-greenish" data-value="4" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-greenish.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-kiwi" data-value="5" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-kiwi.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-road" data-value="6" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-road.jpg" alt="">' +
        '</a>' +  
        '<a  data-skin="skin-blur-spotlight" data-value="7" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-spotlight.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-glow" data-value="8" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-glow.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-tree" data-value="9" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-tree.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-bamboo" data-value="10" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-bamboo.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-blackspot" data-value="11" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-blackspot.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-cloud" data-value="12" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-cloud.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-cobweb" data-value="13" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-cobweb.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-deepBlue" data-value="14" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-deepBlue.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-line" data-value="15" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-line.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-starrySky" data-value="16" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-starrySky.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-wind" data-value="17" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-wind.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-water" data-value="18" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-water.jpg" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-anse" data-value="19" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-anse.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-duose" data-value="20" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-duose.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-green" data-value="21" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-green.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-guang" data-value="22" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-guang.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-ji" data-value="23" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-ji.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-lightred" data-value="24" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-lightred.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-orange" data-value="25" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-orange.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-riluo" data-value="26" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-riluo.png" alt="">' +
        '</a>' +
        '<a  data-skin="skin-blur-jianbian" data-value="27" class="col-sm-2 col-xs-4 _skins" href="">' +
        '<img src="images/skin-jianbian.png" alt="">' +
        '</a>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    $("body").eq(0).prepend(settings);
    
    //把皮肤模态框居中页面
    function centerModals(){
        $('.modal').each(function(i){
            if($(this).attr("id")!="alarmList"){
                var $clone = $(this).clone().css('display', 'block').appendTo('body');
                var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
                top = top > 0 ? top : 0;
                $clone.remove();
                $(this).find('.modal-content').css("margin-top", top);
            }
        });
    }  
    $('.modal').on('show.bs.modal', centerModals);
    $(window).on('resize', centerModals);
    $('body').on('click', '.template-skins > a', function(e){
        e.preventDefault();
        var skin = $(this).data('skin');
        $('body').attr('id', skin);
        $('#changeSkin').modal('hide');
    });
    
    // 背景皮肤保存--------------------------------------------------------------
    $("._skins").click(function(){
        $.ajax({
            url:"/watchpointController/updateUserConfigureByKey.do",
            type:"post",
            data:{
                key:"name",
                value:$(this).attr("data-value")
            },
            dataType:"text",
            success:function(data){
            }
        })
    });
    /*----------进入页面加载用户上次所保存的背景图--------------*/
    $.ajax({
        url:"/watchpointController/getUserConfigureBeanByKey.do",
        method: "POST",
        data: {userId:0},
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success: function (data,textStatus,XMLHttpRequest) {
            switch(data){
                case 1:
                    $("body").attr("id","skin-blur-night");
                    break;
                case 2:
                    $("body").attr("id","skin-blur-violate");
                    break;
                case 3:
                    $("body").attr("id","skin-blur-lights");
                    break;
                case 4:
                    $("body").attr("id","skin-blur-greenish");
                    break;
                case 5:
                    $("body").attr("id","skin-blur-kiwi");
                    break;
                case 6:
                    $("body").attr("id","skin-blur-road");
                    break;
                case 7:
                    $("body").attr("id","skin-blur-spotlight");
                    break;
                case 8:
                    $("body").attr("id","skin-blur-glow");
                    break;
                case 9:
                    $("body").attr("id","skin-blur-tree");
                    break;
                case 10:
                    $("body").attr("id","skin-blur-bamboo");
                    break;
                case 11:
                    $("body").attr("id","skin-blur-blackspot");
                    break;
                case 12:
                    $("body").attr("id","skin-blur-cloud");
                    break;
                case 13:
                    $("body").attr("id","skin-blur-cobweb");
                    break;
                case 14:
                    $("body").attr("id","skin-blur-deepBlue");
                    break;
                case 15:
                    $("body").attr("id","skin-blur-line");
                    break;
                case 16:
                    $("body").attr("id","skin-blur-starrySky");
                    break;
                case 17:
                    $("body").attr("id","skin-blur-wind");
                    break;
                case 18:
                    $("body").attr("id","skin-blur-water");
                    break;
                case 19:
                    $("body").attr("id","skin-blur-anse");
                    break;
                case 20:
                    $("body").attr("id","skin-blur-duose");
                    break;
                case 21:
                    $("body").attr("id","skin-blur-green");
                    break;
                case 22:
                    $("body").attr("id","skin-blur-guang");
                    break;
                case 23:
                    $("body").attr("id","skin-blur-ji");
                    break;
                case 24:
                    $("body").attr("id","skin-blur-lightred");
                    break;
                case 25:
                    $("body").attr("id","skin-blur-orange");
                    break;
                case 26:
                    $("body").attr("id","skin-blur-riluo");
                    break;
                case 27:
                    $("body").attr("id","skin-blur-jianbian");
                    break;
                default:
                    $("body").attr("id","skin-blur-night");
                    break;
            }
        },
        error: function(XMLHttpRequest,textStatus,errorThorwn) {
            window.location.href = 'login.html';
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    
    window.onload = function(){
    	var scrollHeight=document.getElementById("content").scrollHeight;
    	var height=$("#content").height();
    	if(scrollHeight > height){
    	    $(".arrNext a").css("right","28px");
    	}else{
    	  	$(".arrNext a").css("right","12px");
    	}
    }
    
    //左右箭头隐藏出现
    $(".arrNext").hover(function() {
	    $(this).children("a").fadeIn();
	}, function() {
	    $(this).children("a").fadeOut();
	});
});


//客服系统代码
(function(m, ei, q, i, a, j, s) {
        m[i] = m[i] || function() {
            (m[i].a = m[i].a || []).push(arguments)
        };
        j = ei.createElement(q),
            s = ei.getElementsByTagName(q)[0];
        j.async = true;
        j.charset = 'UTF-8';
        j.src = 'https://static.meiqia.com/dist/meiqia.js?_=t';
        s.parentNode.insertBefore(j, s);
    })(window, document, 'script', '_MEIQIA');
    _MEIQIA('entId', 116583);





