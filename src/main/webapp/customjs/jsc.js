/**
 * 首页驾驶舱展示及跳转
 */
$(function() {
    var htmlArr = new Array(),
        htmlTop = new Array(),
        datas,
        currPage,
        countPage,
        sz = [],
        jsc = [];
    //加载驾驶舱
    $(document).ready(function() {
        $.ajax({
            url: "/userAuthorize/getJurisModuleList.do",
            type: "post",
            async: false,
            data: {
                requestType:"get"
            },
            dataType: "json",
            success: function(data) {
                for (var i = 0; i < data.length; i++) {
                    //id小于100是默认驾驶舱大于100的是业务模块，107是自主驾驶舱
                    if (data[i].id <= 100 && data[i].checked == 1) {
                        jsc.push(data[i]);
                    } else if (data[i].id == 107) {
                        jsc.push(data[i]);
                    } else {
                        sz.push(data[i]);
                    }
                }
                datas = jsc;
                currPage = 1;
                countPage = Math.ceil(jsc.length / 3);
                showdata(datas, currPage, countPage);
            }
        });
    });
    //下一页
    function funright() {
        if (currPage < countPage) {
            $("#tab").empty();
            $("#topTab").empty();
            htmlArr = [];
            htmlTop = [];
            currPage = currPage + 1;
            showdata(datas, currPage, countPage);
        } else {
            return;
        }
    }
    //上一页
    function funleft() {
        if (currPage == 1) {
            return;
        } else {
            $("#tab").empty();
            $("#topTab").empty();
            htmlTop = [];
            htmlArr = [];
            currPage = currPage - 1;
            showdata(datas, currPage, countPage);
        }
    }
    //鼠标滑过事件
    function overs(id) {
        if (id == "left") {
            $(".more1").find("a").css("background-image", "url(../images/leftf.png) ");
        } else if (id == "right") {
            $(".more2").find("a").css("background-image", "url(../images/rightf.png) ");
        } else {
            $("#" + id).css({
                "background": "#2b4350"
            });
            $("#" + id).find("span").css({
                "color": "#7ec4dd"
            });
        }
    }
    //鼠标滑过之后事件
    function outs(id) {
        if (id == "left") {
            $(".more1").find("a").css("background-image", "url(../images/left.png) ");
        } else if (id == "right") {
            $(".more2").find("a").css("background-image", "url(../images/right.png)");
        } else {
            $("#" + id).css({
                "background": "rgba(0,0,0,0.2)"
            });
            $("#" + id).find("span").css({
                "color": "#ffffff"
            });
        }
    }

    /**
     * 驾驶舱展示
     * @param {array} datas 数据 <br>
     *        {string} currPage 驾驶舱最小长度为1 <br>
     *        {array}  countPage 驾驶舱以3组为1的长度 <br>
     *
     */
    function showdata(datas, currPage, countPage) {
        if (countPage < 1) {countPage = 1};
        var topTab = $("#topTab");
        var tab = $("#tab");
        if (currPage < countPage) {
            var i = 3 * (currPage - 1);
            //上层驾驶舱只显示三个
            htmlTop.push('<td  class="jscWidthUp" id="' + datas[i].id + '">');
            htmlTop.push('<div class="jsctd _jsc cursor" data-busiId="' + datas[i].monitorId + '"  data-nameZh="' + datas[i].namezh + '"><a>');
            htmlTop.push('<div class="cio" id="Transaction" ><img id="' + datas[i].id + '" name="' + datas[i].checked + '" src="../images/view.png" ></div>');
            htmlTop.push('<span class="jscfont">' + datas[i].namezh + '</span></a></div></td>');

            htmlTop.push('<td  class="jscWidthUp" id="' + datas[++i].id + '">');
            htmlTop.push('<div class="jsctd _jsc cursor" data-busiId="' + datas[i].monitorId + '"  data-nameZh="' + datas[i].namezh + '"><a>');
            htmlTop.push('<div class="cio" id="Transaction" ><img id="' + datas[i].id + '" name="' + datas[i].checked + '" src="../images/view.png" ></div>');
            htmlTop.push('<span class="jscfont">' + datas[i].namezh + '</span></a></div></td>');

            htmlTop.push('<td  class="jscWidthUp" id="' + datas[++i].id + '">');
            htmlTop.push('<div class="jsctd _jsc cursor" data-busiId="' + datas[i].monitorId + '" data-nameZh="' + datas[i].namezh + '"><a>');
            htmlTop.push('<div class="cio" id="Transaction" ><img id="' + datas[i].id + '" name="' + datas[i].checked + '" src="../images/view.png" ></div>');
            htmlTop.push('<span class="jscfont">' + datas[i].namezh + '</span></a></div></td>');

            topTab.append($(htmlTop.join("")));

            //下层业务模块展示6个
            for (var j = 0; j < sz.length; j++) {
                if (sz[j].nameen == "systemSet") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="settingindex.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/system.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "reportManager") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="fromPlan.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/report.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "alarmSet") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="alarmSetting.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/warning.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "flowStore") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="flowmanageSetting.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/traffic.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "businessManager") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="cockpitmanage.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/business.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "topology") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="jnode.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/topo.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                }
            }
            tab.append($(htmlArr.join("")));
            if (currPage == 1) {
                $("#rightdiv").show();
                $("#leftdiv").hide();
            } else {
                $("#leftdiv").show();
                $("#rightdiv").show();
            }
        } else {
            var width = "";
            if (datas.length - 3 * (currPage - 1) == 1) {
                width = 97;
            } else if (datas.length - 3 * (currPage - 1) == 2) {
                width = 48.2;
            } else {
                width = 32;
            }
            for (var i = 3 * (currPage - 1); i < datas.length; i++) {
                if (i % 3 == 0) {
                    htmlTop.push('<td class="jscWidthUp" id="' + datas[i].id + '">');
                } else if (i % 2 == 0) {
                    htmlTop.push('<td class="jscWidthUp" id="' + datas[i].id + '">');
                } else {
                    htmlTop.push('<td class="jscWidthUp" id="' + datas[i].id + '">');
                }
                htmlTop.push('<div class="jsctd _jsc cursor" data-busiId="' + datas[i].monitorId + '" data-nameZh="' + datas[i].namezh + '"><a>');
                htmlTop.push('<div class="cio" id="Transaction" ><img id="' + datas[i].id + '" name="' + datas[i].checked + '" src="../images/view.png" ></div>');
                htmlTop.push('<span class="jscfont">' + datas[i].namezh + '</span></a></div></td>');
            }
            topTab.append($(htmlTop.join("")));
            for (var j = 0; j < sz.length; j++) {
                if (sz[j].nameen == "systemSet") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="settingindex.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/system.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "reportManager") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="fromPlan.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/report.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "alarmSet") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="alarmSetting.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/warning.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "flowStore") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="flowmanageSetting.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="aaa" name="' + sz[j].checked + '" src="../images/traffic.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "businessManager") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="cockpitmanage.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/business.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                } else if (sz[j].nameen == "topology") {
                    htmlArr.push('<td class="jscWidth" id="a' + countPage + '1"><a href="jnode.html">');
                    htmlArr.push('<div class="jsctd"><div class="cio" id="business"><img class="jscimg" id="index-power" name="' + sz[j].checked + '" src="../images/topo.png" ></div>');
                    htmlArr.push('<span class="jscfonts">' + sz[j].namezh + '</span></div></a></td>');
                }
            }
            tab.append($(htmlArr.join("")));
            if (datas.length <= 3) {
                $("#rightdiv").hide();
                $("#leftdiv").hide();
            } else {
                $("#rightdiv").hide();
                $("#leftdiv").show();
            }
        }
    }

    //右箭头点击事件
    $("#rightdiv").click(function() {
        funright();
    });
    //右箭头鼠标滑过
    $("#rightdiv").hover(function() {
        overs("right")
    }, function() {
        outs("right")
    });
    //左箭头点击事件
    $("#leftdiv").click(function() {
        funleft()
    });
    //左箭头鼠标滑过
    $("#leftdiv").hover(function() {
        overs("left")
    }, function() {
        outs("left")
    });

    //点击驾驶舱跳转，增加传参
    $(document).on("click", "._jsc", function() {
        var busiId = $(this).attr("data-busiId"),
            nameZh = $(this).attr("data-nameZh");
        // 3000003 3000004  3000005 3000006
        function toPage(url, page, nameZh, busiId) {
            var _endTime = $.myTime.CurTime()-20,
                _strTime = _endTime - 600 ;
            $.ajax({
                url:"/center/getRemoteWatchpointKpiList.do",
                method:"POST",
                data:{
                    "starttime": _strTime,
                    "endtime": _endTime
                },
                dataType:"json",
                beforeSend:function (XMLHttpRequest) {},
                success:function (data,textStatus,XMLHttpRequest) {
                    if(data.length){
                        $.ajax({
                            url:url,
                            method:"POST",
                            data:{
                                ipmCenterId:data[0].ipmCenterId
                            },
                            dataType:"json",
                            success:function (data_bsId,textStatus,XMLHttpRequest) {
                                if(data_bsId.length){
                                    location.href = page+".html?databsId=" + data_bsId[0].id+
                                        "&nameZh="+encodeURI(nameZh)+
                                        "&busiId="+busiId+
                                        "&watchpointId="+data[0].id+
                                        "&ipmCenterId="+data[0].ipmCenterId;
                                }else {
                                    //无业务id
                                    location.href = page+".html?databsId=0"+
                                        "&nameZh="+encodeURI(nameZh)+
                                        "&busiId="+busiId+
                                        "&watchpointId="+data[0].id+
                                        "&ipmCenterId="+data[0].ipmCenterId;
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }else {
                        //无 ipmCenterId watchpointId
                        location.href = page+".html?databsId=0"+
                            "&nameZh="+encodeURI(nameZh)+
                            "&busiId="+busiId+
                            "&watchpointId=0"+
                            "&ipmCenterId=0";
                    }
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
        }
        switch (busiId) {
            case "2":
                toPage("/watchpointController/getFindAll.do", "observationPointkpi", nameZh, busiId);
                break;
            case "3":
                toPage("/client/getClient.do?moduleId=11", "userSidekpi", nameZh, busiId);
                break;
            case "4":
                toPage("/serverManagement/getAllServerSide.do", "serverSidekpi", nameZh, busiId);
                break;
            case "5":
                toPage("/appController/getAllAppByModuleId.do?moduleId=4", "httpSerkpi", nameZh, busiId);
                break;
            case "6":
                toPage("/appController/getAllAppByModuleId.do?moduleId=5", "oracleSerkpi", nameZh, busiId);
                break;
            case "7":
                toPage("/appController/getAllAppByModuleId.do?moduleId=6", "mysqlSerkpi", nameZh, busiId);
                break;
            case "8":
                toPage("/appController/getAllAppByModuleId.do?moduleId=7", "sqlSerkpi", nameZh, busiId);
                break;
            case "9":
                toPage("/appController/getAllAppByModuleId.do?moduleId=8", "urlkpi", nameZh, busiId);
                break;
            case "10":
                toPage("/appController/getAllAppByModuleId.do?moduleId=9", "baowenJy", nameZh, busiId);
                break;
            case "1":
                toPage("/watchpointController/getFindAll.do", "netCockpit", nameZh, busiId);
                break;
            default:
                location.href = "cockpit.html?busiId=" + busiId + "&nameZh=" + encodeURI(nameZh);
        }
    });

    //驾驶舱是否有权限
    $(document).on('click', '.jsctd', function() {
        var _this = $(this).find("div");
        var name = _this.find("img")[0].name;
        if ("Transaction" == _this[0].id) {
            var id = _this.find("img")[0].id;
            for (var j = 0; j < datas.length; j++) {
                if (id == datas[j].id) {
                    if (datas[j].checked != "1") {
                        jeBox.alert("您没有开通此功能权限");
                        return false;
                    }
                }
            }
        } else if (name != "1") {
            jeBox.alert("您没有开通此功能权限");
            return false;
        }
    });
    /* $(document).on( 'click','.cio', function() {
         var name = $(this).find("img")[0].name;
         if("Transaction" == this.id){
             var id = $(this).find("img")[0].id;
             for (var j = 0; j < datas.length; j++) {
                 if(id ==datas[j].id){
                     if(datas[j].checked !="1"){
                         jeBox.alert("您没有开通此功能权限");
                         return false;
                     }
                 }
             }
         }else if(name!="1"){
             jeBox.alert("您没有开通此功能权限");
             return false;
         }
     });*/
    // logo边线计算-------------------------------------------------------------
    (function() {
        var jquery_div = $(".footer-width");
        var div_width = function() {
            var width = $(window).width() / 2;
            jquery_div.each(function() {
                $(this).css("width", width - 275);
            });
        };
        window.onresize = div_width;
        div_width();
    })();

    /*logo点击弹出网站二维码*/
    $(".logoindex").click(function(){
        jeBox({
            cell:"jbx",
            title:"网站二维码",
            boxSize: ["150px", "170px"],
            padding: "8px 14px 0",
            content:'<img class="img-responsive" src="../images/erweima.png" />',
            maskLock : true,
            success: function(element, index){
                $(element).children(".jeBox-content").css("border-bottom","none");
            }
        });
    });
    /**
     * 如果服务器时间和本地时间相差30秒以上，弹出提示
     */
    $.ajax({
        url:"/systemSet/readDateTimeSet.do",
        method:"POST",
        data:{},
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function(data,textStatus,XMLHttpRequest){
         	var difVal = Math.abs($.myTime.DateToUnix(data.nowTime)-$.myTime.DateToUnix($.myTime.nowTime()));
            if(difVal > 30){
                jeBox.alert("本地时间跟服务器的时间相差较大,建议您进行时间同步");
            }
         },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
     });
});