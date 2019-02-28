/**
 * Created by yanbo on 2017/12/19.
 */
$(function(){
    /*****************头部内容************************/
    /**
     * 封装头部内容 实现在各个页面可配的功能
     * 1 menu-toggle
     * 2 logo
     * 3 media-body
     * 4 top-menu
     *   logOut
     * 5 timeback
     * 6 help
     * 7 refresh
     * 8 serverTime or shwowTimeEle
     * 9 search
     *
     */
    var header = {
        /**
         * 获取URL参数
         */
        getUrlParams : function() {
            var url = location.search,
                params = new Object();
            if (url.indexOf("?") != -1) {
                var arr = url.substr(1).split("&"), tmp = null;
                for (var i = 0, len = arr.length; i < len; i ++) {
                    tmp = arr[i].split("=");
                    params[tmp[0]] = tmp[1];
                }
            }
            return params;
        },
        /**
         * 每个页面的基本元素
         */
        standard:function(){
            var html = '<a href="" id="menu-toggle"></a>'+
                '<a class="logo pull-left" href="netCockpit.html">'+
                '<img src="images/logo.png" alt="logo">'+
                '</a>';
            return html;
        },
        strMediaBody:function(){
            var html = '<div class="media-body"><div class="media" id="top-menu">';
            return html;
        },
        endMediaBody:function(){
            return '</div></div>';
        },
        logOut:function(){
            var html = '<div class="pull-right tm-icon" style="border-left:1px solid rgba(255,255,255,0.15);">'+
                '<a data-drawer="logOut" class="drawer-toggle logout-header" href="#logoutMheader">'+
                '<i class="lnr lnr-power-switch" style="font-size: 27px;margin-top: -3px;"></i>'+
                '<span class="logOutSpan">注销</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        logOutModal:function(){
            var html = '<div class="modal fade" id="logoutMheader" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">'+
                '<div class="modal-dialog" style="width: 20%;">'+
                '<div class="modal-content">'+
                '<div class="modal-header logout-header">'+
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'+
               ' &times;'+
            '</button>'+
            '<h4 class="modal-title" id="myModalLabel">'+
                '温馨提示'+
                '</h4>'+
                '</div>'+
                '<div class="modal-body text-center">'+
                '您确定要退出系统吗？'+
            '</div>'+
            '<div class="modal-footer logout-footer" style="text-align: center;">'+
                '<button type="button" class="btn btn-primary logout-btn">'+
                '确定'+
                '</button>'+
                '<button type="button" class="btn btn-default logout-false" data-dismiss="modal" style="right: 0%">'+
               ' 取消'+
                '</button>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</div>';
            return html;
        },
        timeToback:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="times" class="drawer-toggle" href="">'+
                '<i class="sa-top-time" style="font-size: 21px;padding-top: 1px;"></i>'+
                '<span>时间回溯</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        helpEle:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="help" class="drawer-toggle" href="">'+
                '<i class="lnr lnr-question-circle"></i>'+
                '<span>系统帮助</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        refreshEle:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="refresh" id="refreChTab" class="drawer-toggle" href="">'+
                '<i class="lnr lnr-redo"></i>'+
                '<span>刷新</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        serverTimeEle:function(){
            var html = '<div id="time" class="pull-right">'+
                '<span id="hours"></span>:'+
                '<span id="min"></span>:'+
                '<span id="sec"></span>'+
                '</div>';
            return html;
        },
        /**
         * 每个页面的名字
         * @returns {string}
         */
        cockNameZh:function(){
            var titleText = header.getUrlParams().nameZh?decodeURI(header.getUrlParams().nameZh):$("title").text(),
                html = '<div class="pull-right" id="cockNameZh" style="font-size: 16px;margin-top: 13px;letter-spacing: 2px;">' +
                    '<span style="padding-bottom: 3px;border-bottom: 1px solid #fff;">'
                    +titleText+'</span></div>';
            return html;
        },
        showTimeEle:function(){
          var html = '<div class="pull-right timeBackText" style="font-size: 16px;padding-top: 15px; margin-right: 11px;">'+
              '</div>';
            return html;
        },
        /**
         * 此元素放最后
         */
        searchEle:function(){
        	var html = '<div class="pull-left tm-icon">'+
            '<a data-drawer="search" class="drawer-toggle" href="">'+
            '<i class="lnr lnr-magnifier" style="font-size: 21px;padding-top: 1px;"></i>'+
            '<span>搜索查询</span>'+
            '</a>'+
            '</div>';
            return html;
        },
        topoSearch:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="topoSearch" class="drawer-toggle" href="">'+
                '<i class="sa-top-topo"></i>'+
                '<span>拓朴查询</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        lockStatus:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="lockStatus" class="drawer-toggle" href="">'+
                '<i class="sa-top-lock"></i>'+
                '<span>锁定</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        fullScreen:function(){
            var html = '<div class="pull-left tm-icon">'+
                '<a data-drawer="fullScreen" class="drawer-toggle" href="">'+
                '<i class="sa-top-fScreen"></i>'+
                '<span>全屏</span>'+
                '</a>'+
                '</div>';
            return html;
        },
        headerEle:function(){
            var _t = this,
                html = "";
            html += _t.standard();
            html += _t.strMediaBody();
            html += _t.logOut();
            html += _t.timeToback();
            html += _t.refreshEle();
            html += _t.topoSearch();
            return html;
        },
        /**
         * 时间回溯弹出元素
         * @returns {string}
         */
        timesBoxEle:function(){
            var html = '<div id="times" class="tile drawer animated">\n' +
                '            <div class="listview narrow">\n' +
                '                <div class="media">\n' +
                '                    <a>时间回溯</a> <span class="drawer-close">&times;</span>\n' +
                '                </div>\n' +
                '                <div class="overflow text-center">\n' +
                '                    <div class="col-md-12">\n' +
                '                        <div class="datep">\n' +
                '                            <label class="col-md-1 navtime">开始时间</label>\n' +
                '                            <input class="col-md-2 datainp inptime" id="inpstart" type="text" size="50">\n' +
                '                            <label class="col-md-1 navtime">结束时间</label>\n' +
                '                            <input class=" col-md-2 datainp inptime" id="inpend" type="text" size="50">\n' +
                '                            <button class="col-md-1 btn btn-sm timesure">确定</button>\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>';
            return html;
        },
        /**
         * 刷新粒度弹出元素
         * @returns {string}
         */
        refreshBoxEle:function(){
            var html ='\t\t<div id="refresh" class="tile drawer animated">\n' +
                '\t\t\t<div class="listview narrow">\n' +
                '\t\t\t\t<div class="media">\n' +
                '\t\t\t\t\t<a>刷新粒度</a> <span class="drawer-close">&times;</span>\n' +
                '\t\t\t\t</div>\n' +
                '\t\t\t\t<div class="overflow text-center">\n' +
                '\t\t\t\t\t<div class="col-md-12 text-left">\n' +
                // '\t\t\t\t\t\t<input class="cursor" type="radio" name="timelidu" value="0">立即刷新&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t<input class="cursor none" type="radio" name="timelidu" value="0"><a href="" class="a_refreshData">立即刷新</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t<input class="cursor" type="radio" name="timelidu" value="10">10秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t<input class="cursor" type="radio" name="timelidu" value="30">30秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t<input class="cursor" type="radio" name="timelidu" value="60">60秒&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t</div>\n' +
                '\t\t\t\t</div>\n' +
                '\t\t\t</div>\n' +
                '\t\t</div>';
            return html;
        },
        /**
         * 搜索查询弹出元素
         * @returns {string}
         */
        searchBoxEle:function(){
            var html = '     <div id="search" class="tile drawer animated" style="height: 310px;">\n' +
                '            <div class="listview narrow">\n' +
                '                <div class="media">\n' +
                '                    <a>搜索查询</a>\n' +
                '                    <span class="drawer-close">&times;</span>\n' +
                '                </div>\n' +
                '                <div class="overflow text-right">\n' +
                '                    <div class="col-md-12" style="margin-top: 10px;">\n' +
                '                        <label class="col-md-1 navtime" for="xpmid">XPM服务器</label>\n' +
                '                            <select id="xpmid" class="col-md-2 inptime searchInput">\n' +
                '                                <option value="0">请选择...</option>\n' +
                '                            </select>\n' +
                '                    </div>\n' +
                '                    <div class="col-md-12" style="margin-top: 10px;">\n' +
                '                        <div class="datep">\n' +
                '                            <label class="col-md-1 navtime">开始时间</label>\n' +
                '                            <input class="col-md-2 datainp inptime searchInput" id="searchstart" type="text">\n' +
                '                            <label class="col-md-1 navtime">结束时间</label>\n' +
                '                            <input class=" col-md-2 datainp inptime searchInput" id="searchend" type="text">\n' +
                '                        </div>\n' +
                '                    </div>\n' +
                '                    <div class="col-md-12" style="margin-top: 10px;">\n' +
                '                            <label class="col-md-1 navtime" for="watchpointSearch">观察点</label>\n' +
                '                            <select id="watchpointSearch" class="col-md-2 inptime searchInput">\n' +
                '                                <option value="0">请选择...</option>\n' +
                '                            </select>\n' +
                '                            \n' +
                '                            <label class="col-md-1 navtime" for="serveripSearch">服务端IP</label>\n' +
                '                            <input class="col-md-2 inptime searchInput" type="text" id="serveripSearch">\n' +
                '\n' +
                '                            <label class="col-md-1 navtime" for="clientipSearch">客户端IP</label>\n' +
                '                            <input class="col-md-2 inptime searchInput" type="text" id="clientipSearch">\n' +
                '                    </div>\n' +
                '                    <div class="col-md-12" style="margin-top: 12px;">\n' +
                '                            <label class="col-md-1 navtime" for="urlSearch">URL</label>\n' +
                '                            <input class="col-md-2 inptime searchInput" type="text" id="urlSearch">\n' +
                '\n' +
                '                            <label class="col-md-1 navtime" for="messagessSearch">报文</label>\n' +
                '                            <input class="col-md-2 inptime searchInput" type="text" id="messagessSearch">\n' +
                '                    </div>\n' +
                '                    <div class="col-md-12" style="margin-top: 12px;">\n' +
                '                            <label class="col-md-1 navtime" for="sqlSearch">SQL</label>\n' +
                '                            <input class="col-md-2 inptime searchInput" type="text" id="sqlSearch">\n' +
                '                            <div class="col-md-3 text-left" style="margin-top: 6px; margin-left: 33px;">\n' +
                '\t\t\t\t\t\t\t\t<input class="searchInput" type="radio" name="radio" value="1" checked>ORACLE&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t\t\t<input class="searchInput" type="radio" name="radio" value="2">MYSQL&nbsp;&nbsp;&nbsp;&nbsp;\n' +
                '\t\t\t\t\t\t\t\t<input class="searchInput" type="radio" name="radio" value="3">SQLSERVER\n' +
                '                            </div>\n' +
                '                    </div>\n' +
                '                    <div class="col-md-9 text-right" style="margin-top: 12px;">\n' +
                '                        <button class="btn btn-sm" id="searchTimesure">确定</button>\n' +
                '                </div>\n' +
                '                </div>\n' +
                '            </div>\n' +
                '        </div>';
            return html;
        },
        page:location.pathname.split(".")[0].replace(/\//,""),
        html:"",
        /**
         * 简单跳转页面的html
         */
        nextPage:function () {
            var html = '\t\t<div class="arrNext" id="nextPage">\n' +
                '\t\t\t<a></a>\n' +
                '\t\t</div>';
            $("#content").append(html);
        },
        /**
         * 授权许可
         * @returns {string}
         * @constructor
         */
        license:function(){
            var html = '<div class="pull-right cursor" id="licenseBox" style="font-size: 16px;margin-top: 13px;letter-spacing: 2px;color:red;">' +
                    '</div>';
            return html;
        }
    };
    /**
     *  是否可以跳转到通信队  trafficpair: true | false 若为false将跳转功能隐藏并去掉头部搜索功能
     *  是否可以选择多个观察点 manywatchpoint true | false
     *  是否展示地图 map true | false
     *  是否展拓朴图 topo true | false
     */
    $.ajax({
        url:"/authorizeModuleController/getSelectIsOpen.do",
        method:"POST",
        data:{},
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function (data,textStatus,XMLHttpRequest) {
            $("#header").attr({
                "data-trafficpair":Number(data.trafficpair),   // 是否可以跳转到通信队
                "data-manywatchpoint":Number(data.manywatchpoint),// 是否可以选择多个观察点
                "data-map":Number(data.map),// sidebar是否展示地图
                "data-topo":Number(data.topo) //头部是否展示拓朴图
            })
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {
            if($("#header").attr("data-trafficpair") != undefined && !+$("#header").attr("data-trafficpair")){
                $('a[data-drawer="search"]').parent().remove();
                $("#search").remove();
            }
            if($("#header").attr("data-topo") != undefined && !+$("#header").attr("data-topo")){
                $('a[data-drawer="topoSearch"]').parent().remove();
            }
            if($("#header").attr("data-map") != undefined && !+$("#header").attr("data-map")){
                $('a.sa-side-map').parent().remove();
            }
        }
    });
    switch (header.page){
        case "observationPointkpi":
        case "userSidekpi":
        case "serverSidekpi":
        case "httpSerkpi":
        case "oracleSerkpi":
        case "mysqlSerkpi":
        case "sqlSerkpi":
        case "urlkpi":
        // case "heatMap":
        case "baowenJy":
        case "netCockpit":
            header.html += header.headerEle();
            header.html += header.lockStatus();
            header.html += header.fullScreen();
            header.html += header.showTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.timesBoxEle()));
            $("#content").append($(header.refreshBoxEle())); 
            $("#content").append($(header.searchBoxEle()));
            header.nextPage();
            break;
        case "systemCapital":
            header.html += header.headerEle();
            header.html += header.fullScreen();
            header.html += header.showTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.timesBoxEle()));
            $("#content").append($(header.refreshBoxEle()));
            $("#content").append($(header.searchBoxEle()));
            break;
        case "commun_queue":
        case "alarmSetting":
        case "bssSession":
            //无锁定功能
            header.html += header.headerEle();
            header.html += header.fullScreen();
            header.html += header.showTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.timesBoxEle()));
            $("#content").append($(header.searchBoxEle()));
            break;
        case "fromHistory":
            //无锁定功能
            header.html += header.headerEle();
            header.html += header.fullScreen();
            header.html += header.serverTimeEle();
            header.html += header.showTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.timesBoxEle()));
            $("#content").append($(header.searchBoxEle()));
            break;   
        case "cockpit":
            //时间配置的html不一样
            header.html += header.headerEle();
            header.html += header.lockStatus();
            header.html += header.fullScreen();
            header.html += header.serverTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.timesBoxEle()));
            $("#content").append($(header.refreshBoxEle()));
            $("#content").append($(header.searchBoxEle()));
            break;
        default:
            header.html += header.standard();
            header.html += header.strMediaBody();
            header.html += header.logOut();
            header.html += header.refreshEle();
            header.html += header.topoSearch();
            header.html += header.fullScreen();
            header.html += header.serverTimeEle();
            header.html += header.cockNameZh();

            header.html += header.license();

            header.html += header.searchEle();
            header.html += header.helpEle();
            header.html += header.endMediaBody();
            $("#content").append($(header.searchBoxEle()));
    }
    $("#header").append($(header.html));
    $("body").eq(0).append(header.logOutModal());
    switch (header.page){
        case "observationPointkpi":
        case "userSidekpi":
        case "serverSidekpi":
        case "httpSerkpi":
        case "oracleSerkpi":
        case "mysqlSerkpi":
        case "sqlSerkpi":
        case "heatMap":
        case "cockpit":
        case "urlkpi":
        case "netCockpit":
            //为头部的锁图标赋值
            $.post("/monitor/getViewById.do",{"id":header.page == "netCockpit"?1:header.getUrlParams().busiId},function(data){
                if(data.lockStatus){
                    //锁定
                    $('a[data-drawer="lockStatus"]').children("i").attr("class","sa-top-lock");
                    $('a[data-drawer="lockStatus"]').children("span").text("锁定");
                    if(header.page!="cockpit"){
                        $("#list-draw").parent().hide();
                    }
                }else {
                    //未锁定
                    $('a[data-drawer="lockStatus"]').children("i").attr("class","sa-top-unlock");
                    $('a[data-drawer="lockStatus"]').children("span").text("解锁");
                }
            },"json");
            break;
    }
    $(".logout-header").click(function(){
        $("#logoutMheader").modal("show");
    });
    /**
     * 控制模块的权限
     * 此处应该将所有模块都默认写上
     * 由此接口控制是否隐藏
     */
    $.ajax({
        url:"/userAuthorize/getJurisModuleList.do",
        method:"POST",
        data:{
            requestType:"get"
        },
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function(data,textStatus,XMLHttpRequest){
            data.forEach(function(item,index){
                if(item.namezh == "拓扑图"){
                    if(!item.checked){
                        $("a[data-drawer='topoSearch']").parent().addClass("none");
                    }
                }
            })
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    $("a[data-drawer='topoSearch']").click(function () {
        location.href = 'jnode.html?true';
    });
    $("a[data-drawer='lockStatus']").click(function(){
        var _t = $("a[data-drawer='lockStatus']"),
            _status;
        $.post("/user/getUserRole.do",null,function(data){
            if(data==1){
                // 1 为管理员
                if(_t.children("span").text()!="锁定"){
                    _t.children("i").attr("class","sa-top-lock");
                    _t.children("span").text("锁定");
                    _status = 1;
                }else {
                    _t.children("i").attr("class","sa-top-unlock");
                    _t.children("span").text("解锁");
                    _status = 0;
                }
                $.ajax({
                    url:"/monitor/updateViewStatus.do",
                    async:false,
                    method:"POST",
                    data:{
                        id:header.page == "netCockpit" ? 1 : header.getUrlParams().busiId,
                        status:_status
                    },
                    dataType:"json",
                    beforeSend:function (XMLHttpRequest) {},
                    success:function(data,textStatus,XMLHttpRequest){
                        if(data.status){
                            jeBox.alert("修改状态成功");
                            if(header.page!="cockpit" || header.page!="heatMap"){
                                if(_t.children("span").text()!="锁定"){
                                    $("#list-draw").parent().show();
                                }else {
                                    $("#list-draw").parent().hide();
                                }
                            }
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
            }else {
                //||data==2 普通用户
                //此处应该加一层逻辑 判断当前模块是否是非admin 若是则不进下面这个ajax
                var titleText = header.getUrlParams().nameZh?decodeURI(header.getUrlParams().nameZh):$("title").text(),
                    isAdmin = true;
                $.ajax({
                    url:"/userAuthorize/getJurisModuleList.do",
                    method:"POST",
                    async:false,
                    data:{
                        requestType:"get"
                    },
                    dataType:"json",
                    beforeSend:function (XMLHttpRequest) {},
                    success:function(datait,textStatus,XMLHttpRequest){
                        datait.forEach(function(item,index){
                            if(item.namezh == titleText){
                                if(item.userName != "admin" && item.userName != null){
                                    isAdmin = false;
                                }
                            }
                        })
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
                if(!isAdmin){
                    if(_t.children("span").text()!="锁定"){
                        _t.children("i").attr("class","sa-top-lock");
                        _t.children("span").text("锁定");
                        _status = 1;
                    }else {
                        _t.children("i").attr("class","sa-top-unlock");
                        _t.children("span").text("解锁");
                        _status = 0;
                    }
                    $.ajax({
                        url:"/monitor/updateViewStatus.do",
                        async:false,
                        method:"POST",
                        data:{
                            id:header.page == "netCockpit" ? 1 : header.getUrlParams().busiId,
                            status:_status
                        },
                        dataType:"json",
                        beforeSend:function (XMLHttpRequest) {},
                        success:function(data,textStatus,XMLHttpRequest){
                            if(data.status){
                                jeBox.alert("修改状态成功");
                                if(header.page!="cockpit" || header.page!="heatMap"){
                                    if(_t.children("span").text()!="锁定"){
                                        $("#list-draw").parent().show();
                                    }else {
                                        $("#list-draw").parent().hide();
                                    }
                                }
                            }
                        },
                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThorwn);
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    });
                }else {
                    jeBox.alert("您无权限进行此操作");
                }
            }
        },"json");
    });
    $("a[data-drawer='fullScreen']").click(function(){
        var docElm = document.getElementsByTagName("html")[0];
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        else if (docElm.msRequestFullscreen) {
            jeBox.alert("浏览器不支持全屏API或已被禁用");
        }
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }else{
            jeBox.alert("浏览器不支持全屏API或已被禁用");
        }
    });
    //系统帮助
    $('a[data-drawer="help"]').click(function () {
        window.open("/help/help.html");
    });
    $(".logout-btn").click(function () {
        $.ajax({
            url: "/user/logout.do",
            method: "POST",
            data: {},
            dataType:"json",
            beforeSend:function(XMLHttpRequest){},
            success:function(data,textStatus,XMLHttpRequest){
                if(data.success = "1"){
                    window.location = "/login.html";
                }else if(data.success = "0"){
                    jeBox.alert("注销不成功");
                }
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn);
            },
            complete:function (XMLHttpRequest,textStatus) {}
        })
    });
    /*----------刷新页面功能------------*/
    $(document).on("click","#refreChTab",function(){
        if(
            header.page != "observationPointkpi" &&
            header.page != "userSidekpi" &&
            header.page != "serverSidekpi" &&
            header.page != "httpSerkpi" &&
            header.page != "oracleSerkpi" &&
            header.page != "mysqlSerkpi" &&
            header.page != "sqlSerkpi" &&
            header.page != "urlkpi" &&
            header.page != "heatMap" &&
            header.page != "baowenJy" &&
            header.page != "netCockpit" &&
            header.page != "systemCapital" &&
            header.page != "cockpit"
        ){
            location.reload();
        }
    });
    /**
     * 将刷新粒度赋值
     */
    $.ajax({
        url:"/watchpointController/getUserConfigureBeanByKey.do",
        method:"POST",
        async:false,
        data:{
            key:"dataRefreshTime"
        },
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function (data,textStatus,XMLHttpRequest) {
            var timelidu = $('input[name="timelidu"]');
            switch (data){
                case 10:
                    timelidu.eq(1).attr("checked","checked");
                    break;
                case 30:
                    timelidu.eq(2).attr("checked","checked");
                    break;
                case 60:
                    timelidu.eq(3).attr("checked","checked");
                    break;
                default:
                    timelidu.eq(1).attr("checked","checked");
            }
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            $('input[name="timelidu"]').eq(1).attr("checked","checked");
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    /**
     * 展示授权是否过期
     * 604800 小于一周给提示
     */
    $.ajax({
        url:"/systemSet/readProductLicensSet.do",
        method:"POST",
        data:{},
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function (data,textStatus,XMLHttpRequest) {
            if($.myTime.DateToUnix(data[0].validterm+' 00:00:00') - $.myTime.CurTime() <= 604800){
                $("#licenseBox").text("有授权许可即将到期").click(function () {
                    location.href = '/settingindex.html';
                })
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