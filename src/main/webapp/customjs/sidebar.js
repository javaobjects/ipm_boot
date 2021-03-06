/**
 * Created by yanbo on 2017/10/17.
 */
$(function(){
    /*****************左侧图标工具栏************************/
    /*
     * 封装左侧边栏 实现在各个页面可配的功能
     * 1、将所有的图标均收集到此
     * 2、是否需要href属性以及其属性值
     * 3、是否需要在a标签下再添加ul以及其ul li里的内容 以跳转或不跳转的功能
     * 4、是否需要请求后台的展示的侧边栏内容
     * 图标有：首页 保存 创建自主驾驶仓 后端配置
     * 系统管理 用户管理 产品配置与管理
     * 标准存储方案 高级存储方案 历史数据提取 数据提取列表
     * 驾驶仓管理 地图
     *
     */
    var sidebar = {
        pathName:location.pathname.split(".")[0].replace(/\//,""),
        sideMenu:"",
        showLiTem:function(){
            for(var i=0,litem = $("#sidebar>.list-unstyled").children("li");i<litem.length;i++){
                if(litem.eq(i).hasClass("none")){
                    litem.eq(i).removeClass("none");
                }
            }
        },
        toPage:function(url,page,nameZh,busiId){
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
        },
        html:function(arr){
            var html = '';
            for(var i=0;i<arr.length;i++){
                var liClassName = arr[i].liClassName?arr[i].liClassName:"",
                    liChidAClassName = arr[i].liChidAClassName?arr[i].liChidAClassName:"",
                    liChidAattr = arr[i].liChidAattr?arr[i].liChidAattr:"",
                    liChidAChildSpanText = arr[i].liChidAChildSpanText?arr[i].liChidAChildSpanText:"",
                    liChidAbrotherUl = arr[i].liChidAbrotherUl?arr[i].liChidAbrotherUl:"";
                if(liChidAbrotherUl){
                    html += '<li class="'+liClassName+'">'+
                        '<a class="'+liChidAClassName+'" '+liChidAattr+'>'+
                        '<span class="menu-item cursor">'+liChidAChildSpanText+'</span>'+
                        '</a>'+liChidAbrotherUl+
                        '</li>';
                }else {
                    html += '<li class="'+liClassName+'">'+
                        '<a class="'+liChidAClassName+'" '+liChidAattr+'>'+
                        '<span class="menu-item cursor">'+liChidAChildSpanText+'</span>'+
                        '</a>'+
                        '</li>';
                }
            }
            return html;
        },
        indexToCockpit:function(){
            var indexToCP = '<ul class="list-unstyled menu-item">';
            //判断用户是否存在
            var userData = null;
            $.ajax({
                url:"/user/getSessionUserInfo.do",
                method:"POST",
                async:false,
                data:{},
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success:function(data,textStatus,XMLHttpRequest){
                	userData = data;
                }});
            $.ajax({
                url:"/userAuthorize/getJurisModuleList.do",
                method:"POST",
                async:false,
                data:{
                    requestType:"get"
                },
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success:function(data,textStatus,XMLHttpRequest){
                	if(userData == null){
                		location.href = "/login.html";
                	}
                    for(var i =0; i<data.length;i++){
                        if(data[i].id==107 ){
                           indexToCP += '<li class="skipjsc cursor" data-busiId="'+data[i].monitorId+'"  data-nameZh="'+data[i].namezh+'" style="font-weight: 600;">'+data[i].namezh+'</li>';
                        }else if(data[i].id < 100 && data[i].checked == 1){
                            if(data[i].monitorId != 1 ){
                                indexToCP += '<li class="skipjsc cursor" data-busiId="'+data[i].monitorId+'"  data-nameZh="'+data[i].namezh+'" style="font-weight: 600;">'+data[i].namezh+'</li>';
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
            indexToCP += '</ul>';
            $(document).on("click",".skipjsc",function(){
                var busiId = $(this).attr("data-busiId"),
                    nameZh = $(this).attr("data-nameZh");
                switch (busiId){
                    case "2":
                        sidebar.toPage("/watchpointController/getFindAll.do","observationPointkpi",nameZh,busiId);
                        break;
                    case "3":
                        sidebar.toPage("/client/getClient.do?moduleId=11","userSidekpi",nameZh,busiId);
                        break;
                    case "4":
                        sidebar.toPage("/serverManagement/getAllServerSide.do","serverSidekpi",nameZh,busiId);
                        break;
                    case "5":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=4","httpSerkpi",nameZh,busiId);
                        break;
                    case "6":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=5","oracleSerkpi",nameZh,busiId);
                        break;
                    case "7":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=6","mysqlSerkpi",nameZh,busiId);
                        break;
                    case "8":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=7","sqlSerkpi",nameZh,busiId);
                        break;
                    case "9":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=8","urlkpi",nameZh,busiId);
                        break;
                    case "10":
                        sidebar.toPage("/appController/getAllAppByModuleId.do?moduleId=9","baowenJy",nameZh,busiId);
                        break;
                    case "1":
                        // sidebar.toPage("/watchpointController/getFindAll.do","heatMap",nameZh,busiId);
                    	sidebar.toPage("/watchpointController/getFindAll.do","netCockpit",nameZh,busiId);
                        break;
                    default:
                        location.href = "cockpit.html?busiId="+busiId+"&nameZh="+encodeURI(nameZh);
                }
            });
            return indexToCP;
        },
        cockSidebar:function(cockpit){
            var html = "";
            $.ajax({
                url:"/authorizeModuleController/getFindAll.do",
                async:false,
                method:"POST",
                data:{},
                dataType:"json",
                beforeSend:function (XMLHttpRequest) {},
                success:function(data,textStatus,XMLHttpRequest){
                    if(cockpit){
                        html += '<li><a class="sa-side-wordRoundBox cursor" title="文字圆角框"></a><ul class="menu-item-custom">';
                        html += '<li><a class="sa-side-words cursor" title="文字"></a></li>';
                        html += '<li><a class="sa-side-roundedBox cursor" title="圆角框"></a></li>';
                        html += '</ul></li>';
                        html += '<li><a class="sa-side-tuli cursor" title="绘图"></a>'+
                            '<ul class="menu-item-custom" style="top:0px;">';
                        html += '<li><a class="sa-side-aDedicatedLine cursor _creatImg _huitu" title="专线" data-classname="sa-side-aDedicatedLine"></a></li>';
                        html += '<li><a class="sa-side-branch cursor _creatImg _huitu" title="分支机构" data-classname="sa-side-branch"></a></li>';
                        html += '<li><a class="sa-side-databaseService cursor _creatImg _huitu" title="数据库服务" data-classname="sa-side-databaseService"></a></li>';
                        html += '<li><a class="sa-side-firewall cursor _creatImg _huitu" title="防火墙" data-classname="sa-side-firewall"></a></li>';
                        html += '<li><a class="sa-side-loadBalancing cursor _creatImg _huitu" title="负载均衡" data-classname="sa-side-loadBalancing"></a></li>';
                        html += '<li><a class="sa-side-middleware cursor _creatImg _huitu" title="中间件" data-classname="sa-side-middleware"></a></li>';
                        html += '<li><a class="sa-side-router cursor _creatImg _huitu" title="路由器" data-classname="sa-side-router"></a></li>';
                        html += '<li><a class="sa-side-switchboard cursor _creatImg _huitu" title="交换机" data-classname="sa-side-switchboard"></a></li>';
                        html += '<li><a class="sa-side-thirdPartyBusiness cursor _creatImg _huitu" title="第三方业务" data-classname="sa-side-thirdPartyBusiness"></a></li>';
                        html += '<li><a class="sa-side-webServer cursor _creatImg _huitu" title="web服务" data-classname="sa-side-webServer"></a></li>';
                        html += '</ul></li>'+
                            '<li style="border-top: 1px solid rgba(255,255,255,.8);width: 30px; margin-left: 8px;height: 2px;">'+
                            '</li>';
                    }
                    data.forEach(function(item,index){
                        var className,
                            nameZh = item.namezh;
                        switch (nameZh){
                            case "网络":
                                className = "sa-side-network";
                                break;
                            case "主机":
                                className = "sa-side-ip";
                                break;
                            case "自定义应用":
                                className = "sa-side-custom";
                                break;
                            case "HTTP服务":
                                className = "sa-side-web";
                                break;
                            case "ORACLE服务":
                                className = "sa-side-oracle";
                                break;
                            case "MYSQL服务":
                                className = "sa-side-mysql";
                                break;
                            case "SQLSERVER服务":
                                className = "sa-side-sqlserver";
                                break;
                            case "URL交易":
                            case "URL服务":
                                className = "sa-side-url";
                                break;
                            case "报文交易":
                            case "报文服务":
                                className = "sa-side-baowenJy";
                                break;
                            case "观察点":
                                className = "sa-side-observationPoint";
                                break;
                            case "客户端":
                                className = "sa-side-userSide";
                                break;
                            case "服务端":
                                className = "sa-side-serverSide";
                                break;
                            default:
                                className = "sa-side-home";
                                break;
                        }
                        if(nameZh != "应用可用性"){
                            switch (nameZh){
                                case "观察点":
                                case "服务端":
                                case "客户端":
                                case "HTTP服务":
                                case "ORACLE服务":
                                case "MYSQL服务":
                                case "SQLSERVER服务":
                                case "URL交易":
                                case "URL服务":
                                case "报文交易":
                                case "报文服务":
                                    html += '<li class="dropdown">' +
                                        '<a class="'+className+' cursor _creatImg" data-className="'+className+'">'+
                                        '<span class="menu-item cursor">'+nameZh+ '</span>'+
                                        '</a>' +
                                        '</li>';
                                    break;
                                // default:
                                //     html += '<li>' +
                                //         '<a class="'+className+' cursor">'+
                                //         '<span class="menu-item cursor">'+nameZh+'</span>'+
                                //         '</a>'+
                                //         '</li>';
                                //     $("#sidebar>ul").on("click","."+className+">span.menu-item",function(){
                                //         return false;
                                //     });
                            }
                        }
                    });
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
            return html;
        },
        toDownSpace:function(){
            var html = '<li><a></a></li>';
            return html;
        },
        toDownPic:function(){
            var html = '<div class="sa-sidebar-toBottom" id="sidebarToScroll"></div>';
            return html;
        }
    };
    switch (sidebar.pathName){
        case "settingindex":
        case "settingindex2":
        case "settingindex3":
        case "systemLogs":
        case "systemTool":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-typography",
                    liChidAattr:'href="settingindex.html"',
                    liChidAChildSpanText:"系统设置与管理"
                },
                {
                    liChidAClassName:"sa-side-table",
                    liChidAattr:'href="settingindex3.html"',
                    liChidAChildSpanText:"产品更新与授权"
                },
                {
                    liChidAClassName:"sa-side-widget",
                    liChidAattr:'href="settingindex2.html"',
                    liChidAChildSpanText:"账号管理"
                },
                {
                    liChidAClassName:"sa-side-log",
                    liChidAattr:'href="systemLogs.html"',
                    liChidAChildSpanText:"系统日志"
                },
                {
                    liChidAClassName:"sa-side-sysTool",
                    liChidAattr:'href="systemTool.html"',
                    liChidAChildSpanText:"系统工具"
                }/*,
                {
                    liChidAClassName:"sa-side-sysData",
                    liChidAattr:'href="sysDataExport.html"',
                    liChidAChildSpanText:"数据导出"
                }*/
            ];
            switch (sidebar.pathName){
                case "settingindex":
                    sidebarHtmlArry[1].liClassName = "active";
                    break;
                case "settingindex3":
                    sidebarHtmlArry[2].liClassName = "active";
                    break;
                case "settingindex2":
                    sidebarHtmlArry[3].liClassName = "active";
                    break;
                case "systemLogs":
                    sidebarHtmlArry[4].liClassName = "active";
                    break;
                case "systemTool":
                    sidebarHtmlArry[5].liClassName = "active";
                    break;
               /* case "sysDataExport":
                    sidebarHtmlArry[6].liClassName = "active";
                    break;*/
            }
            sidebar.sideMenu = sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        case "flowmanageSetting":
        case "flowmanageSetting2":
        case "flowmanageSetting3":
        case "flowmanageSetting4":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-sts",
                    liChidAattr:'href="flowmanageSetting.html"',
                    liChidAChildSpanText:"标准存储方案"
                },
                {
                    liChidAClassName:"sa-side-ads",
                    liChidAattr:'href="flowmanageSetting2.html"',
                    liChidAChildSpanText:"高级存储方案"
                },
                {
                    liChidAClassName:"sa-side-his",
                    liChidAattr:'href="flowmanageSetting3.html"',
                    liChidAChildSpanText:"历史数据提取"
                },
                {
                    liChidAClassName:"sa-side-data",
                    liChidAattr:'href="flowmanageSetting4.html"',
                    liChidAChildSpanText:"数据提取列表"
                }
            ];
            switch (sidebar.pathName){
                case "flowmanageSetting":
                    sidebarHtmlArry[1].liClassName = "active";
                    break;
                case "flowmanageSetting2":
                    sidebarHtmlArry[2].liClassName = "active";
                    break;
                case "flowmanageSetting3":
                    sidebarHtmlArry[3].liClassName = "active";
                    break;
                case "flowmanageSetting4":
                    sidebarHtmlArry[4].liClassName = "active";
                    break;
            }
            sidebar.sideMenu = sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        case "cockpitmanage":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-view",
                    liChidAattr:'href="cockpitmanage.html"',
                    liChidAChildSpanText:"管理与设置"
                },
                {
                    liChidAClassName:"sa-side-map",
                    liChidAattr:'href="map.html"',
                    liChidAChildSpanText:"地图"
                }
            ];
            if(sidebar.pathName == "map"){
                sidebarHtmlArry[2].liClassName = "active";
            }else {
                sidebarHtmlArry[1].liClassName = "active";
            }
            sidebar.sideMenu = sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        case "cockpit":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-save cursor",
                    liChidAChildSpanText:"保存"
                }
            ];
            sidebar.sideMenu += sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.cockSidebar("cockpit");
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        case "fromPlan":
        case "fromSetting":
        case "fromHistory":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-formPlan",
                    liChidAattr:'href="fromPlan.html"',
                    liChidAChildSpanText:"报表计划"
                },
                {
                    liChidAClassName:"sa-side-formset",
                    liChidAattr:'href="fromSetting.html"',
                    liChidAChildSpanText:"模板功能"
                },
                {
                    liChidAClassName:"sa-side-formhistory",
                    liChidAattr:'href="fromHistory.html"',
                    liChidAChildSpanText:"历史记录查询"
                }
            ];
            if(sidebar.pathName == "fromHistory"){
                sidebarHtmlArry[3].liClassName = "active";
            }else if(sidebar.pathName == "fromSetting"){
                sidebarHtmlArry[2].liClassName = "active";
            }else{
            	sidebarHtmlArry[1].liClassName = "active";
            }
            sidebar.sideMenu = sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        case "netCockpit":

        case "observationPointkpi":
        case "serverSidekpi":
        case "userSidekpi":
        case "httpSerkpi":
        case "oracleSerkpi":
        case "mysqlSerkpi":
        case "sqlSerkpi":
        case "urlkpi":
        case "baowenJy":
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                },
                {
                    liChidAClassName:"sa-side-bpm",
                    liChidAattr:'href="cockpitmanage.html"',
                    liChidAChildSpanText:"监控对象设置"
                },
                {
                    liChidAClassName:"sa-side-topo",
                    liChidAattr:'href="jnode.html"',
                    liChidAChildSpanText:"拓朴图"
                },
                {
                    liChidAClassName:"sa-side-tsotage",
                    liChidAattr:'href="flowmanageSetting.html"',
                    liChidAChildSpanText:"流量存储"
                },
                {
                    liChidAClassName:"sa-side-alarm",
                    liChidAattr:'href="alarmSetting.html"',
                    liChidAChildSpanText:"告警统计"
                },
                {
                    liChidAClassName:"sa-side-form",
                    liChidAattr:'href="fromPlan.html"',
                    liChidAChildSpanText:"报表管理"
                },
                {
                    liChidAClassName:"sa-side-sets",
                    liChidAattr:'href="settingindex.html"',
                    liChidAChildSpanText:"系统设置"
                }
            ];
            sidebar.sideMenu += sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
            break;
        default:
            var sidebarHtmlArry = [
                {
                    liChidAClassName:"sa-side-home",
                    liChidAattr:'href="netCockpit.html"',
                    liChidAChildSpanText:"首页",
                    liChidAbrotherUl:sidebar.indexToCockpit()
                }
            ];
            sidebar.sideMenu += sidebar.html(sidebarHtmlArry);
            sidebar.sideMenu += sidebar.toDownSpace();
    }
    $("#sidebar .side-menu").append($(sidebar.sideMenu));
    //将小箭头添加进侧边栏并默认不出现 是否出现视屏幕大小而定
    $("#sidebar").append($(sidebar.toDownPic()));
    $("#sidebarToScroll").hide();
    /*为大小屏写左侧边栏兼容样式*/
    if($("html").outerHeight()-$("#header").outerHeight()-$("#sidebar>.side-menu").outerHeight()>0){
        $("#sidebar>.side-menu").css("height","100%");
        // $("#content").css("height","100%");
    }else {
        if($("#sidebar>.side-menu").outerHeight()-$("#content").outerHeight()>0){
            // $("#content").css("height",$("#sidebar>.side-menu").outerHeight());
        }
        $("#sidebarToScroll").show();
    }
    $(window).resize(function(){
        if(sidebar.pathName=="cockpit"){
            if($(document).outerHeight()-$("#header").outerHeight()-$("#sidebar>.side-menu").outerHeight()>=-1){
                if($("#sidebar>.side-menu").children("li").outerHeight()*$("#sidebar>.side-menu").children("li").length-$("#sidebar>.side-menu").outerHeight()>0){
                    //此处能解决由大变小 不能解决浏览器由小变大
                    $("#sidebar>.side-menu").css("height",$("#sidebar>.side-menu").children("li").outerHeight()*($("#sidebar>.side-menu").children("li").length-1)+2);
                    // $("#content").css("height",$("#sidebar>.side-menu").outerHeight());
                    if(sidebar.pathName=="cockpit"){
                        //此处还应该判断箭头的方向
                        if($("#sidebar>.side-menu").outerHeight()-$("body").outerHeight()>($(window).scrollTop()-$("#header").height()+0.5)){
                            $("#sidebarToScroll").removeClass("sa-sidebar-toTop").addClass("sa-sidebar-toBottom");
                        }else {
                            $("#sidebarToScroll").removeClass("sa-sidebar-toBottom").addClass("sa-sidebar-toTop");
                        }
                        $("#sidebarToScroll").show();
                    }
                    //解决浏览器由小变大所带来的bug
                    setTimeout(function(){
                        if($("html").outerHeight()-$("#header").outerHeight()-$("#sidebar>.side-menu").outerHeight()>0){
                            $("#sidebar>.side-menu").css("height","100%");
                            // $("#content").css("height","100%");
                            $("#sidebarToScroll").hide();
                            sidebar.showLiTem();
                        }
                    },1);
                }else {
                    $("#sidebar>.side-menu").css("height","100%");
                    // $("#content").css("height","100%");
                    $("#sidebarToScroll").hide();
                    sidebar.showLiTem();
                }
            }else {
                $("#sidebar>.side-menu").css("height",$("#sidebar>.side-menu").outerHeight());
                // $("#content").css("height",$("#sidebar>.side-menu").outerHeight());
                if(sidebar.pathName=="cockpit"){
                    $("#sidebarToScroll").show();
                }
            }
        }
    });
    $("#content").scroll(function(){
        if($("#content").scrollTop()){
            $("#header").css("background","rgba(0,0,0,0.8)")
        }else {
            $("#header").css("background","")
        }
    });
    $(document).on("click","#sidebarToScroll",function(e){
        /**
         * 此处有更好的方法
         * 每次点击时将最顶的一个给display：none掉
         * 直到最后一个的出现
         *
         * resize的时候将所有的li给判断一遍 显示匹配窗口大小的图标
         *  window.innerHeight - heerder 的高度 差值 除以 li 高度的值
         * Math.ceil()
         *
         */
        if($(this).hasClass("sa-sidebar-toBottom")){
            var _indexNone = Math.ceil((window.innerHeight-$("#header").outerHeight())/40),
                _liItem = $("#sidebar>.list-unstyled").children("li");
            if(_liItem.eq(0).hasClass("none")){
                var _liIndex,
                    _liItemHe = 0;
                for(var i=0;i<_liItem.length;i++){
                    if(!_liItem.eq(i).hasClass("none")){
                        _liIndex = i;
                        break;
                    }
                }
                for(var j=0;j<_liItem.length;j++){
                    if(!_liItem.eq(j).hasClass("none")){
                        _liItemHe += _liItem.eq(j).outerHeight();
                    }
                }
                //此处还有些不友好
                /**
                 * _liItemHe-window.innerHeight+$("#header").outerHeight()
                 * window.innerHeight-$("#header").outerHeight()-_liItemHe>40
                 *
                 */

                // if(_liItemHe>(window.innerHeight-$("#header").outerHeight()+40)){
                if(window.innerHeight-$("#header").outerHeight()-_liItemHe<0){
                    _liItem.eq(_liIndex).addClass("none");
                }else {
                    $("#sidebarToScroll").removeClass("sa-sidebar-toBottom").addClass("sa-sidebar-toTop")
                }
            }else {
                _liItem.eq(0).addClass("none");
            }
        }else {
            var _liIndex,
                _liItem = $("#sidebar>.list-unstyled").children("li");
            for(var i=0;i<_liItem.length;i++){
                if(!_liItem.eq(i).hasClass("none")){
                    _liIndex = i;
                    break;
                }

            }
            if(_liIndex){
                _liItem.eq(_liIndex-1).removeClass("none");
                if(!(_liIndex-1)){
                    $("#sidebarToScroll").removeClass("sa-sidebar-toTop").addClass("sa-sidebar-toBottom")
                }
            }else {
                $("#sidebarToScroll").removeClass("sa-sidebar-toTop").addClass("sa-sidebar-toBottom")
            }
        }



        // if($(this).hasClass("sa-sidebar-toBottom")){
        //     //此处滚动的距离应该是side-menu的高度减去（浏览器可视高度-header的高度）
        //     $("html,body").animate({scrollTop : $("#sidebar>.side-menu").outerHeight()-$(window).height() + $("#header").outerHeight()},800);
        //     $("#header").css("background","rgba(0,0,0,0.8)");
        // }else {
        //     $("html,body").animate({scrollTop : 0}, 800);
        //     $("#header").css("background","");
        // }
        e.stopPropagation();
    });
    $(window).scroll(function(){
        if($("#sidebar>.side-menu").outerHeight()-$("body").outerHeight()>($(window).scrollTop()-$("#header").height()+0.5)){
            $("#sidebarToScroll").removeClass("sa-sidebar-toTop").addClass("sa-sidebar-toBottom");
        }else {
            $("#sidebarToScroll").removeClass("sa-sidebar-toBottom").addClass("sa-sidebar-toTop");
        }
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
            /**
             * sa-side-bpm 101
             * sa-side-topo 102
             * sa-side-tsotage 103
             * sa-side-alarm 104
             * sa-side-form 105
             * sa-side-sets 106
             */
            var sidebarItemIds = [];
            data.forEach(function (item,index) {
                if(+item.checked){
                    sidebarItemIds.push(+item.id);
                }
            });
            if(sidebarItemIds.indexOf(101) == -1){
                $(".sa-side-bpm").parent("li").remove();
            }
            if(sidebarItemIds.indexOf(102) == -1){
                $(".sa-side-topo").parent("li").remove();
            }
            if(sidebarItemIds.indexOf(103) == -1){
                $(".sa-side-tsotage").parent("li").remove();
            }
            if(sidebarItemIds.indexOf(104) == -1){
                $(".sa-side-alarm").parent("li").remove();
            }
            if(sidebarItemIds.indexOf(105) == -1){
                $(".sa-side-form").parent("li").remove();
            }
            if(sidebarItemIds.indexOf(106) == -1){
                $(".sa-side-sets").parent("li").remove();
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


