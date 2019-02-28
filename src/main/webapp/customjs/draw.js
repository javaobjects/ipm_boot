/**
 * Created by yanb on 2017/10/19
 *
 * （此代码是我在其它同事写一半的基础上进行的重新封装 固代码逻辑稍乱 ）
 *
 *
 *
 * 画出图形 弹出框选图形模态框
 *
 * 初始化js条件
 * <script src="js/jquery.min.js"></script>
 * <script src="js/bootstrap.min.js"></script>
 * <script src="js/highcharts/code/highcharts.js"></script>
 * <script src="customjs/highchartUtil/highchart.util.js"></script>
 *  @param type||types  10||11||12
 *  @param  creatCharParams obj {type:"",selectDataId:"",starttime:"","endtime":""}
 *  @param selectDataId selectDataId
 *  @param urlArry ["/watchpointController/getFindAll.do"]
 *         ||["/watchpointController/getFindAll.do","/serverManagement/getAllServerSide.do"]
 *         ||["/watchpointController/getFindAll.do","/client/getClient.do?moduleId=11"]
 *  @param dataChecked arry 数据列选中的复选框id
 *  @param wcsIds arry [watchpointId]||
 *                     [watchpointId,clientId]||
 *                     [watchpointId,serverId]
 * @param   obj hChartParams {}
 *
 *
 */
var _chart = {
    _pageName:location.pathname.split(".")[0].replace(/\//,""),
    refreNum:0,
    refrebar:false,
    kpiName: [],
    kpiNames: [],
    drawId: [],
    drawIds: [],
    netCockpitHuiSu:undefined,//时间回溯标识符
    drageBox: undefined,
    /**
     * 保存主驾驶舱的四个ID
     * 顺序自定
     * @param cockpitConfig
     * @private
     */
    _savaNetCockpitIds:function(cockpitConfig){
        $.ajax({
            url:"/watchpointController/updateUserConfigureByKey.do",
            method:"POST",
            data:{
                key: "cockpitConfig",
                value:cockpitConfig
            },
            dataType:"json",
            beforeSend:function (XMLHttpRequest) {},
            success:function (data,textStatus,XMLHttpRequest) {
                if(data){
                    console.log(data);
                }else {
                    jeBox.alert("保存失败");
                }
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn)
            },
            complete:function (XMLHttpRequest,textStatus) {}
        })
    },
    /**
     * 删除所有图形除咯“+”号图形
     */
    removelinedraw:function(){
        for (var i = 0, $linedraw = $(".linedraw"); i < $linedraw.length; i++) {
            if ($linedraw.length - 1) {
                if (i != $linedraw.length - 1) {
                    $linedraw.eq(i).parents(".draw").remove();
                }
            }
        }
    },
    /**
     * plotIdsArray,params
     * @param params
     * @private
     */
    _creatFSChart:function(fsparams){
        var html = '\t\t<div class="col-md-6 draw drawPading">\n' +
            '\t\t\t\t\t\t<div class="row">\n' +
            '\t\t\t\t\t\t\t<div class="col-md-6 netChartBox netChartBoxS0">\n' +
            '\t\t\t\t\t\t\t\t<div class="title">\n' +
            '\t\t\t\t\t\t\t\t\t<h2 class="tile-title fsTitle"></h2>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t<div class="linedraw smChBox smChBoxbg0 text-right cur_text no_removeChart">\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCVlue"></span>\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCUnit"></span>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t<div class="col-md-6 netChartBox netChartBoxS1">\n' +
            '\t\t\t\t\t\t\t\t<div class="title">\n' +
            '\t\t\t\t\t\t\t\t\t<h2 class="tile-title fsTitle"></h2>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t<div class="linedraw smChBox smChBoxbg1 text-right cur_text no_removeChart">\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCVlue"></span>\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCUnit"></span>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t<div class="row netRow1S">\n' +
            '\t\t\t\t\t\t\t<div class="col-md-6 netChartBox netChartBoxS2">\n' +
            '\t\t\t\t\t\t\t\t<div class="title">\n' +
            '\t\t\t\t\t\t\t\t\t<h2 class="tile-title fsTitle"></h2>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t<div class="linedraw smChBox smChBoxbg2 text-right cur_text no_removeChart">\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCVlue"></span>\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCUnit"></span>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t<div class="col-md-6 netChartBox netChartBoxS3">\n' +
            '\t\t\t\t\t\t\t\t<div class="title">\n' +
            '\t\t\t\t\t\t\t\t\t<h2 class="tile-title fsTitle"></h2>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t<div class="linedraw smChBox smChBoxbg3 text-right cur_text no_removeChart">\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCVlue"></span>\n' +
            '\t\t\t\t\t\t\t\t\t<span class="netCUnit"></span>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t</div>';
        $(".pointline").prepend(html);
        $.ajax({
            url:fsparams.url?fsparams.url:fsparams.urlPath,
            method:"POST",
            data:fsparams.ajaxParam?fsparams.ajaxParam:fsparams,
            dataType:"json",
            beforeSend:function (XMLHttpRequest) {},
            success:function (data,textStatus,XMLHttpRequest) {
                data.data.forEach(function (item,index) {
                    var numObj = numForUtil(item.value,item.unit);
                    $(".fsTitle").eq(index).text(item.plotName);
                    $(".netCVlue").eq(index).text(numObj.value);
                    $(".netCUnit").eq(index).text(numObj.unit);
                    $(".netCVlue").eq(index).attr({
                        "data-url":fsparams.url,
                        "data-isNet":"netCockpit",
                        "data-plotId":fsparams.plotIdsArray[index],
                        "data-starttime":data.starttime,
                        "data-endtime":data.endtime
                    });
                    for(var key in fsparams.ajaxParam){
                        if(key != "plotIds"){
                            $(".netCVlue").eq(index).attr("data-"+key,fsparams.ajaxParam[key])
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
        $(".smChBox").dblclick(function () {
            if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                var _this = $(this).children(".netCVlue");
                if( _this.attr("data-moduleId")!=10 &&
                    _this.attr("data-moduleId")!=11 &&
                    _this.attr("data-moduleId")!=12){
                    if(!_chart.netCockpitHuiSu){
                        location.href = 'bssSession.html?' +
                            'plotId=' + _this.attr("data-plotId") + '&' +
                            'plotTypeId=1&' +
                            'moduleId=' + _this.attr("data-moduleId") + '&' +
                            'ipmCenterId=' + _this.attr("data-ipmCenterId") + '&' +
                            'watchpointId=' + _this.attr("data-watchpointId") + '&' +
                            'busiId=' + _this.attr("data-busiId") + '&' +
                            'starttime=' + $(".timeBackText").attr("data-strTime") + '&' +
                            'endtime=' + $(".timeBackText").attr("data-endTime");
                    }else {
                        location.href = 'bssSession.html?' +
                            'plotId=' + _this.attr("data-plotId") + '&' +
                            'plotTypeId=1&' +
                            'moduleId=' + _this.attr("data-moduleId") + '&' +
                            'ipmCenterId=' + _this.attr("data-ipmCenterId") + '&' +
                            'watchpointId=' + _this.attr("data-watchpointId") + '&' +
                            'busiId=' + _this.attr("data-busiId") + '&' +
                            'starttime=' + _this.attr("data-starttime") + '&' +
                            'endtime=' + _this.attr("data-endtime");
                    }
                }else {
                    if(!_chart.netCockpitHuiSu){
                        location.href = 'commun_queue.html?' +
                            'chartUrl='+_this.attr("data-dblclickUrl")+'&' +
                            'plotId=' + _this.attr("data-plotId") + '&' +
                            'plotTypeId=1&' +
                            'moduleId=' + _this.attr("data-moduleId") + '&' +
                            'ipmCenterId=' + _this.attr("data-ipmCenterId") + '&' +
                            'watchpointId=' + _this.attr("data-watchpointId") + '&' +
                            'starttime=' + $(".timeBackText").attr("data-strTime") + '&' +
                            'endtime=' + $(".timeBackText").attr("data-endTime");
                    }else {
                        location.href = 'commun_queue.html?' +
                            'chartUrl='+_this.attr("data-dblclickUrl")+'&' +
                            'plotId=' + _this.attr("data-plotId") + '&' +
                            'plotTypeId=1&' +
                            'moduleId=' + _this.attr("data-moduleId") + '&' +
                            'ipmCenterId=' + _this.attr("data-ipmCenterId") + '&' +
                            'watchpointId=' + _this.attr("data-watchpointId") + '&' +
                            'starttime=' + _this.attr("data-starttime") + '&' +
                            'endtime=' + _this.attr("data-endtime");
                    }
                }
            }else {

            }
        })
    },
    /**
     *
     * @param params.titleId
     * @param params.itemId
     * @param params.url
     * @param params.ipmCenterId
     * @param params.watchpointId
     * @param params.moduleId
     * @param params.columnNum
     * @param params.handledflag
     * @param params.businessId
     * @param params.starttime
     * @param params.endtime
     * @private
     */
    _netAlarmChart:function(params){
        var html = '<div class="col-md-6 draw drawPading">' +
            '<div class="title">' +
            '<h2 class="tile-title" id ='+params.titleId+'>图形</h2>' +
            '</div>' +
            '<div class="linedraw cur_text no_removeChart">' +
            '<div id='+ params.itemId +
            ' style="width:100%;height:221px;"></div>' +
            '</div>' +
            '</div>',
            itemAttrObj = new Object();
        for(var key in params){
            itemAttrObj["data-"+key] = params[key]
        }
        $(".pointline").prepend(html);
        $("#"+params.itemId).attr(itemAttrObj);
        $.drawHChart(
            params.itemId,
            params.titleId,
            params.url,
            {
                starttime:params.starttime,
                endtime:params.endtime,
                // watchpointId:params.watchpointId,
                ipmCenterId:params.ipmCenterId,
                moduleId:params.moduleId,
                columnNum:params.columnNum,
                handledflag:params.handledflag,
                businessId:params.businessId
            }
        );
        $("#"+params.itemId).dblclick(function () {
            // serverId clientId busiId
            var _t = $(this);
            switch (_t.attr("data-moduleId")){
                case "10"://WATCHPOINT
                    location.href = "alarmSetting.html?watchpointId="+_t.attr("data-watchpointId")+
                        "&ipmCenterId="+_t.attr("data-ipmCenterId")+"&moduleId="+_t.attr("data-moduleId")+
                        "&starttime=" + _t.attr("data-starttime") + "&endtime=" + _t.attr("data-endtime") + "&state=N";
                    break;
                case "11"://CLIENT
                    location.href = "alarmSetting.html?watchpointId="+_t.attr("data-watchpointId")+
                        "&ipmCenterId="+_t.attr("data-ipmCenterId")+"&moduleId="+_t.attr("data-moduleId")+
                        "&clientId="+_t.attr("data-businessid")+
                        "&starttime=" + _t.attr("data-starttime") + "&endtime=" + _t.attr("data-endtime") + "&state=N";
                    break;
                case "12"://SERVER
                    location.href = "alarmSetting.html?watchpointId="+_t.attr("data-watchpointId")+
                        "&ipmCenterId="+_t.attr("data-ipmCenterId")+"&moduleId="+_t.attr("data-moduleId")+
                        "&serverId="+_t.attr("data-businessid")+
                        "&starttime=" + _t.attr("data-starttime") + "&endtime=" + _t.attr("data-endtime") + "&state=N";
                    break;
                default:
                    location.href = "alarmSetting.html?watchpointId="+_t.attr("data-watchpointId")+
                        "&ipmCenterId="+_t.attr("data-ipmCenterId")+"&moduleId="+_t.attr("data-moduleId")+
                        "&busiId="+_t.attr("data-businessid")+
                        "&starttime=" + _t.attr("data-starttime") + "&endtime=" + _t.attr("data-endtime") + "&state=N";
            }
        })
    },
    /**
     *
     * @returns {Object}
     * @private
     */
    _saveChartCont:function(){
        var _JsonChart = new Object();
        _JsonChart.graph = [];
        for(var i = 0,_lineDraw = $(".linedraw");i<_lineDraw.length;i++){
            if(!_lineDraw.eq(i).children().hasClass("center-block") &&
                !_lineDraw.eq(i).parent().hasClass("dads-children-clone") &&
                !_lineDraw.eq(i).parents().hasClass("dads-children-clone")
            ){
                var _linedraChild = _lineDraw.eq(i).children(),
                    _linedraChildAttr = new Object();
                _linedraChild.each(function () {
                    $.each(this.attributes,function () {
                        if(this.specified){
                            if(this.name != "style"){
                                var _thisName = this.name.replace("data-","") == "url"?
                                    "urlPath":this.name.replace("data-","").
                                    replace("id","Id").
                                    replace("type","Type").
                                    replace("center","Center"),
                                    _thisVal = this.value;
                                if(_thisVal != "undefined"){
                                    _linedraChildAttr[_thisName] = _thisVal;
                                }
                            }
                        }
                    })
                });
                _linedraChildAttr.drage = "dadDrage";
                if(_linedraChild.hasClass("netCVlue")){
                    //四个小图形的保存
                    if(_JsonChart.graph[0] && _JsonChart.graph[0]["class"] == "netCUnit"){
                        if(_JsonChart.graph[0].plotId.indexOf(_linedraChildAttr.plotId) == -1){
                            _JsonChart.graph[0].plotId = _JsonChart.graph[0].plotId + ","+_linedraChildAttr.plotId;
                        }
                    }else {
                        _JsonChart.graph.unshift(_linedraChildAttr);
                    }
                }else {
                    _JsonChart.graph.unshift(_linedraChildAttr);
                }
            }
        }
        return _JsonChart;
    },
    /**
     * 保存图形拖拽后的位置排列
     * @param content
     * @private
     */
    _dadDrageSave:function(content){
        var _busiId = content.graph[0].busiId?
            content.graph[0].busiId:
            content.graph[0].clientId?
                content.graph[0].clientId:
                content.graph[0].serverId?
                    content.graph[0].serverId:
                    $("#watchPoint .custom-row-style").attr("data-id")?
                        $("#watchPoint .custom-row-style").attr("data-id"):1;
        if(content){
            $.ajax({
                url: "/viewConfig/updModuleConfig.do",
                method: "POST",
                data: {
                    ipmCenterId:content.graph[0].ipmCenterId,
                    moduleId: content.graph[0].moduleId,
                    busiId: _busiId,
                    content: JSON.stringify(content)
                },
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success: function (data,textStatus,XMLHttpRequest) {
                    console.log(data);
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error("保存失败");
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
        }
    },
    /**
     *
     * @param creatCharParams
     * @param hChartParams
     * @private
     */
    _createChart: function (creatCharParams, hChartParams) {
        $.ajax({
            url: "/viewConfig/getModuleConfig.do",
            method: "POST",
            async: false,
            data: {
                ipmCenterId:creatCharParams.ipmCenterId,
                moduleId: creatCharParams.type,//此处的命名需要更改
                busiId: creatCharParams.selectDataId//此处的命名需要更改
            },
            dataType: "json",
            beforeSend:function (XMLHttpRequest) {},
            success: function (data,textStatus,XMLHttpRequest) {
                if(data){
                    $(".draw").remove();
                    var plotId,
                        plotTypeId,
                        url,
                        watchpointId,
                        html = '',
                        clientId,
                        serverId,
                        busiId,
                        chartemId,
                        drawCharparem,
                        dataWatchpointId,
                        dataClientId,
                        dataBusiId,
                        dataServerId,
                        domId,
                        cur_text;//双击鼠标样式
                    switch (+creatCharParams.type) {
                        case 10:
                            chartemId = "draw_ober_plot_plotype";
                            break;
                        case 11:
                            chartemId = "draw_ober_user_plot_plotype";
                            break;
                        case 12:
                            chartemId = "draw_ober_server_plot_plotype";
                            break;
                        case 4:
                            chartemId = "draw_ober_web_plot_plotype";
                            break;
                        case 5:
                            chartemId = "draw_ober_oracle_plot_plotype";
                            break;
                        case 6:
                            chartemId = "draw_ober_mysql_plot_plotype";
                            break;
                        case 7:
                            chartemId = "draw_ober_sqlserver_plot_plotype";
                        case 8:
                            chartemId = "draw_ober_url_plot_plotype";
                            break;
                        case 9:
                            chartemId = "draw_ober_baowenJy_plot_plotype";
                            break;
                        case 0:
                            chartemId = "draw_ober_systemCapital_plot_plotype";
                            break;
                        default:
                            chartemId = "draw_ober_default_plot_plotype";
                            console.log("未书写此部分代码");
                    }
                    switch (_chart._pageName){
                        case "observationPointkpi":
                        case "userSidekpi":
                        case "serverSidekpi":
                        case "httpSerkpi":
                        case "oracleSerkpi":
                        case "mysqlSerkpi":
                        case "sqlSerkpi":
                        case "urlkpi":
                        case "baowenJy":
                            cur_text = "cur_text";
                            break;
                        case "systemCapital":
                            cur_text = "";
                            break;
                        default:
                            cur_text = "cur_text";
                    }
                    for (var i = 0; i < data.graph.length; i++) {
                        _chart.kpiName.push(data.graph[i].plotId);
                        dataWatchpointId = hChartParams ? hChartParams.watchpointId :
                            creatCharParams.watchpointId?creatCharParams.watchpointId:
                            data.graph[i].watchpointId;
                        dataClientId = hChartParams ? hChartParams.clientId :
                            data.graph[i].clientId;
                        dataServerId = hChartParams ? hChartParams.serverId :
                            data.graph[i].serverId;
                        dataBusiId = hChartParams ? hChartParams.busiId :
                            data.graph[i].busiId;
                        if(data.graph[i].class != "netCUnit" && (data.graph[i].Id?data.graph[i].Id.indexOf("alarm_chartemId") == -1:true)){
                            plotId = data.graph[i].plotId;
                            plotTypeId = data.graph[i].plotTypeId;
                            url = data.graph[i].urlPath;
                            watchpointId = creatCharParams.type == 10 ? data.graph[i].watchpointId :
                                creatCharParams.watchpointId ? creatCharParams.watchpointId  : 1;
                            clientId = creatCharParams.type == 11 ? data.graph[i].clientId : undefined;
                            serverId = creatCharParams.type == 12 ? data.graph[i].serverId : undefined;
                            busiId = data.graph[i].busiId ? data.graph[i].busiId : undefined;
                            domId = chartemId + i + creatCharParams.ipmCenterId + watchpointId + plotId + plotTypeId;
                            if(clientId){
                                domId += clientId;
                            }
                            if(serverId){
                                domId += serverId;
                            }
                            if(busiId){
                                domId += busiId;
                            }
                            if (hChartParams) {
                                drawCharparem = {
                                    "ipmCenterId": creatCharParams.ipmCenterId,
                                    "watchpointId": hChartParams.watchpointId,
                                    "moduleId": creatCharParams.type,
                                    "plotId": plotId,
                                    "plotTypeId": plotTypeId,
                                    "starttime": creatCharParams.starttime,
                                    "endtime": creatCharParams.endtime
                                };
                                if (creatCharParams.type == 12) {
                                    hChartParams.serverId ? drawCharparem.serverId = hChartParams.serverId :
                                        drawCharparem.serverId = serverId;
                                } else if (creatCharParams.type == 11) {
                                    hChartParams.clientId ? drawCharparem.clientId = hChartParams.clientId :
                                        drawCharparem.clientId = clientId;
                                } else {
                                    if (creatCharParams.type != 10) {
                                        hChartParams.busiId ? drawCharparem.busiId = hChartParams.busiId :
                                            drawCharparem.busiId = busiId;
                                    }
                                }
                            } else {
                                drawCharparem = {
                                    "ipmCenterId": creatCharParams.ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "plotId": plotId,
                                    "plotTypeId": plotTypeId,
                                    "moduleId": creatCharParams.type,
                                    "starttime": creatCharParams.starttime,
                                    "endtime": creatCharParams.endtime
                                };
                                if (creatCharParams.type == 12) {
                                    drawCharparem.serverId = serverId
                                } else if (creatCharParams.type == 11) {
                                    drawCharparem.clientId = clientId
                                } else {
                                    if (creatCharParams.type != 10) {
                                        drawCharparem.busiId = busiId
                                    }
                                }
                            }
                            _chart.drawId.push(domId);
                            html = '<div class="col-md-6 draw drawPading">' +
                                '<div class="title">' +
                                '<h2 class="tile-title" id =draw_title_' + i + '>图形</h2>' +
                                '</div>' +
                                '<div class="linedraw '+cur_text+'">' +
                                '<div id=' + domId + ' ' +
                                'data-ipmCenterId = "' + creatCharParams.ipmCenterId + '" ' +
                                'data-moduleId = "' + creatCharParams.type + '" ' +
                                'data-serverId=' + dataServerId +
                                ' data-clientId=' + dataClientId +
                                ' data-busiId=' + dataBusiId +
                                ' data-starttime=' + creatCharParams.starttime +
                                ' data-endtime=' + creatCharParams.endtime +
                                ' data-watchpointId=' + dataWatchpointId +
                                ' data-plotId=' + data.graph[i].plotId +
                                ' data-plotTypeId=' + data.graph[i].plotTypeId +
                                ' data-url=' + data.graph[i].urlPath +
                                ' style="width:100%;height:221px;"></div>' +
                                '</div>' +
                                '</div>';
                            $(".pointline").prepend(html);
                            $.drawHChart(domId,
                                "draw_title_" + i,
                                url,
                                drawCharparem
                            );
                            $("#content .block-area").on("dblclick", "#" +domId, function () {
                                if(!creatCharParams.dblclick){
                                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                        var chartUrl = $(this).attr("data-url"),
                                            ipmCenterId = $(this).attr("data-ipmCenterId"),
                                            watchpointId = $(this).attr("data-watchpointId"),
                                            clientId = $(this).attr("data-clientId"),
                                            serverId = $(this).attr("data-serverId"),
                                            busiId = $(this).attr("data-busiId"),
                                            moduleId = $(this).attr("data-moduleId"),
                                            plotId = $(this).attr("data-plotId"),
                                            starttime = $(this).attr("data-starttime") && $(this).attr("data-starttime") != "undefined" ?
                                                $(this).attr("data-starttime") : "",
                                            endtime = $(this).attr("data-endtime") && $(this).attr("data-endtime") != "undefined" ?
                                                $(this).attr("data-endtime") : "",
                                            plotTypeId = $(this).attr("data-plotTypeId"),
                                            csParam = clientId && clientId != "undefined" ? '&clientId=' + clientId :
                                                serverId && serverId != "undefined" ? '&serverId=' + serverId : "";
                                        if (chartUrl == "/appController/getPlotData.do"
                                            || chartUrl == "/url/getPlotData.do"
                                            || chartUrl == "/depthanaly/depthanalyGraphical.do") {
                                            location.href = 'bssSession.html?' +
                                                'moduleId=' + moduleId + '&' +
                                                'ipmCenterId=' + ipmCenterId + "&" +
                                                'busiId=' + busiId + '&' +
                                                'plotId=' + plotId + '&' +
                                                'plotTypeId=' + plotTypeId + '&' +
                                                'starttime=' + starttime + '&' +
                                                'endtime=' + endtime + '&' +
                                                'watchpointId=' + watchpointId;
                                        } else {
                                            location.href = 'commun_queue.html?' +
                                                'moduleId=' + moduleId + '&' +
                                                'ipmCenterId=' + ipmCenterId + "&" +
                                                'watchpointId=' + watchpointId + "&" +
                                                'chartUrl=' + chartUrl + '&' +
                                                'plotId=' + plotId + '&' +
                                                'plotTypeId=' + plotTypeId + '&' +
                                                'starttime=' + starttime + '&' +
                                                'endtime=' + endtime + csParam;
                                        }
                                    }
                                }
                            });
                        }else {
                            if(_chart._pageName == "netCockpit"){
                                if((data.graph[i].Id?data.graph[i].Id.indexOf("alarm_chartemId") != -1:false)){
                                    _chart._netAlarmChart(creatCharParams.netAlarmChart);
                                }
                                if(data.graph[i].class == "netCUnit"){
                                    _chart._creatFSChart(creatCharParams.fsParams);
                                }
                            }
                        }
                    }
                    if(_chart._pageName == "netCockpit"){
                        if(!data.graph.length || !data.graph[0].drage || !$(".netChartBox").length){
                            _chart._netAlarmChart(creatCharParams.netAlarmChart);
                            _chart._creatFSChart(creatCharParams.fsParams);
                        }
                    }
                    $('.pointline').dad({
                        draggable: '.title',
                        target:".draw",
                        placeholder: '',
                        callback:function () {
                            _chart._dadDrageSave(_chart._saveChartCont());
                        }
                    });
                }else {
                    _chart.removelinedraw();
                }
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                _chart.removelinedraw();
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn);
            },
            complete:function (XMLHttpRequest,textStatus) {}
        });
    },
    /**
     *
     * @param types
     * @param urlArry
     * @param dataChecked
     * @param wcsIds
     * @private
     */
    _kpiSelectM: function (types, urlArry, dataChecked, wcsIds) {
        $(".list-content").empty();
        if (urlArry && urlArry.length) {
            var formHorizontal = '<div class="form-horizontal" style="margin-right: 20px;margin-left: 20px;">' +
                '<div class="form-group">';
            $.ajax({
                url: "/center/getCenterIpInfo.do",
                method: "POST",
                async: false,
                data: {},
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success: function (data,textStatus,XMLHttpRequest) {
                    var select = '<div class="col-md-4">' +
                        '<select class="form-control col-md-2 selectParam centerSelect "' +
                        'style="background: rgba(0,0,0,0.0);height: 30px;padding-top: 4px;margin-top: 11px;" ' +
                        'data-class="centerSelect" data-charUrl="" data-url="/center/getCenterIpInfo.do">' +
                        '<option>XPM服务器</option>';
                    data.forEach(function (item, index) {
                        if (index) {
                            select += '<option data-id="' + item.id + '" data-index="' + index + '">' + item.name + '</option>';
                        } else {
                            select += '<option data-id="' + item.id + '" data-index="' + index + '" selected="selected">' + item.name + '</option>';
                        }
                    });
                    select += '</select></div>';
                    formHorizontal += select;
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
            for (var i = 0; i < urlArry.length; i++) { //此处为写下拉框的功能
                var selectedName,
                    selectClassName,
                    chartUrl,
                    moduleId,
                    id;
                switch (urlArry[i]) {
                    case "/watchpointController/getFindAll.do":
                        selectedName = "观察点";
                        selectClassName = "watchPoinSelect";
                        chartUrl = "/watchpointController/getWatchpointGraphical.do";
                        moduleId = 10;
                        break;
                    case "/serverManagement/getAllServerSide.do":
                        selectedName = "服务端";
                        selectClassName = "serverSideSelect";
                        chartUrl = "/serverManagement/getServerSideGraphical.do";
                        moduleId = 12;
                        break;
                    case "/client/getClient.do?moduleId=11":
                        selectedName = "客户端";
                        selectClassName = "userSideSelect";
                        chartUrl = "/client/getClientGraphical.do";
                        moduleId = 11;
                        break;
                    case "/appController/getAllAppByModuleId.do?moduleId=4":
                        selectedName = "HTTP服务";
                        selectClassName = "webSelect";
                        chartUrl = "/appController/getPlotData.do?moduleId=4";
                        moduleId = 4;
                        break;
                    case "/appController/getAllAppByModuleId.do?moduleId=5":
                        selectedName = "ORACLE服务";
                        selectClassName = "oracleSelect";
                        chartUrl = "/appController/getPlotData.do?moduleId=5";
                        moduleId = 5;
                        break;
                    case "/appController/getAllAppByModuleId.do?moduleId=6":
                        selectedName = "MYSQL服务";
                        selectClassName = "mysqlSelect";
                        chartUrl = "/appController/getPlotData.do?moduleId=6";
                        moduleId = 6;
                        break;
                    case "/appController/getAllAppByModuleId.do?moduleId=7":
                        selectedName = "SQLSERVER服务";
                        selectClassName = "sqlserverSelect";
                        chartUrl = "/appController/getPlotData.do?moduleId=7";
                        moduleId = 7;
                        break;
                    case "/url/get.do":
                        selectedName = "URL服务";
                        selectClassName = "urlSelect";
                        chartUrl = "/url/getPlotData.do";
                        moduleId = 8;
                        break;
                    case "/depthanaly/selectAll.do":
                        // selectedName = "报文交易";
                        selectedName = "报文服务";
                        selectClassName = "baowenJySelect";
                        chartUrl = "/depthanaly/depthanalyGraphical.do";
                        moduleId = 9;
                        break;
                }
                var select = '<div class="col-md-4">' +
                    '<select class="form-control col-md-2 selectParam ' + selectClassName + '"  ' +
                    'style="background: rgba(0,0,0,0.0);height: 30px;padding-top: 4px;margin-top: 11px;" ' +
                    'data-class="' + selectClassName + '" data-charUrl="' + chartUrl + '" data-url="' + urlArry[i] + '" data-moduleId="'+moduleId+'">' +
                    '<option>' + selectedName + '</option>';
                $.ajax({
                    url: urlArry[i],
                    method: "POST",
                    async: false,
                    data: {},
                    dataType: "json",
                    beforeSend:function(XMLHttpRequest){},
                    success: function (data,textStatus,errorThorwn) {
                        data.forEach(function (item, index) {
                            id = item.id;
                            if (wcsIds && wcsIds.length) {
                                if (wcsIds[i]) {
                                    if (id == wcsIds[i]) {
                                        select += '<option data-id="' + id + '" data-index="' + index + '" selected="selected">' + item.name + '</option>';
                                    } else {
                                        select += '<option data-id="' + id + '" data-index="' + index + '">' + item.name + '</option>';
                                    }
                                }
                            } else {
                                //未传参wcsIds
                                if (index) {
                                    select += '<option data-id="' + id + '" data-index="' + index + '">' + item.name + '</option>';
                                } else {
                                    select += '<option data-id="' + id + '" data-index="' + index + '" selected="selected">' + item.name + '</option>';
                                }
                            }
                        });
                        select += '</select></div>';
                        formHorizontal += select;
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        consle.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                })
            }
            formHorizontal += '</div></div>';
        }
        var dataTogglCla = urlArry && urlArry.length ? "" : "none",
            formHorizontal = formHorizontal ? formHorizontal : "";
        var tables = formHorizontal +
            '<table class="table table-hover" style="margin: 12px;width: 96%;border: 1px solid rgba(0, 0, 0, 0.3);">' +
            '<thead style="display:block;border-bottom:1px solid rgba(0, 0, 0, 0.3);"><tr>' +
            '<th style="background: #404040;color: #fff;">Kpi名称</th>' +
            '<th style="background: #404040;color: #fff;">图形</th>' +
            '<th class="' + dataTogglCla + '" style="background: #404040;color: #fff;">数据</th>' +
            '</tr></thead>' +
            '<tbody id="table-list"  style="display:block;max-height:260px;overflow-y: scroll;"></tbody>' +
            '</table>';
        $(".list-content").append(tables);
        /**
         * 此处写XPM服务器下拉框联动的功能
         */
        $(".centerSelect").change(function () {
            var ipmCenterId = $(this).children("option:selected").attr("data-id"),
                select2Ele = $(this).parent().next().children(".selectParam"),
                select3Ele = $(this).parent().next().next().children(".selectParam");
            if (ipmCenterId) {
                var url1 = select2Ele.attr("data-url"),
                    moduleId1 = select2Ele.attr("data-moduleId"),
                    moduleId2 = select3Ele.attr("data-moduleId"),
                    url2 = select3Ele.attr("data-url");
                if (url1) {
                    $.ajax({
                        url: url1,
                        method: "POST",
                        data: {
                            ipmCenterId: ipmCenterId,
                            moduleId:moduleId1
                        },
                        dataType: "json",
                        beforeSend:function(XMLHttpRequest){},
                        success: function (data,textStatus,errorThorwn) {
                            select2Ele.html("");
                            var select;
                            data.forEach(function (item, index) {
                                if (index) {
                                    select += '<option data-id="' + item.id + '" data-index="' + index + '">' + item.name + '</option>';
                                } else {
                                    select += '<option data-id="' + item.id + '" data-index="' + index + '" selected="selected">' + item.name + '</option>';
                                }
                            });
                            select2Ele.append(select)
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    })
                }
                if (url2) {
                    $.ajax({
                        url: url2,
                        method: "POST",
                        data: {
                            ipmCenterId: ipmCenterId,
                            moduleId:moduleId2
                        },
                        dataType: "json",
                        beforeSend:function (XMLHttpRequest) {},
                        success: function (data,textStatus,errorThorwn) {
                            select3Ele.html("");
                            var select;
                            data.forEach(function (item, index) {
                                if (index) {
                                    select += '<option data-id="' + item.id + '" data-index="' + index + '">' + item.name + '</option>';
                                } else {
                                    select += '<option data-id="' + item.id + '" data-index="' + index + '" selected="selected">' + item.name + '</option>';
                                }
                            });
                            select3Ele.append(select);
                        },
                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThorwn);
                        },
                        complete:function (XMLhttpRequest,textStatus) {}
                    })
                }
            }
        });
        var _width = $('#listDraw').width();
        urlArry && urlArry.length ? $('.list-content th:first-child').width(_width * 0.6) :
            $('.list-content th:first-child').width(_width * 0.6);
        $('.list-content th:nth-child(2)').width(_width * 0.36);
        $('.list-content th:nth-child(3)').width(_width * 0.385);
        $.ajax({
            url: "/plot/getPlotByModuleId.do",
            method: "POST",
            async: false,
            data: {
                moduleId: types
            },
            dataType: "json",
            beforeSend:function(XMLHttpRequest){},
            success: function (data,textStatus,XMLHttpRequest) {
                var str,
                    str1,
                    groupName = [], //标题数组  ["告警类","时间类","流量类","会话类","数据包类","其他"]
                    classId,
                    trs;
                $("#table-list").empty();
                data.forEach(function (item, index) {
                    if (groupName.indexOf(item.groupName) == -1) {
                        groupName.push(item.groupName);
                        switch (item.groupName) {
                            case "告警类":
                                classId = "alarmId";
                                break;
                            case "流量类":
                                classId = "flowId";
                                break;
                            case "会话类":
                                classId = "huihuaId";
                                break;
                            case "数据包类":
                                classId = "dataId";
                                break;
                            case "时间类":
                                classId = "timeId";
                                break;
                            case "其他":
                                classId = "otherId";
                                break;
                            case "硬件类":
                                classId = "hardwareId";
                                break;
                            default:
                                console.log("后端新增类，请前端开发人员书写此新增类的前端代码");
                                classId = "unknownId";
                        }
                        trs = $('<tr class="text-center tr-class" id="' + classId + '"  style="background: rgba(0,0,0,.55);"><td colspan="3" style="font-size: 14px;color: #fff;">' + item.groupName + '</td></tr>');
                        $("#table-list").append(trs);
                    }
                });
                for (var i = 0; i < data.length; i++) {
                    if (data[i].graph == true) {
                        if (data[i].types.length) {
                            str = '<td><input  name="litlisChart" ' +
                                'data-id="' + data[i].id + '" ' +
                                'data-moduleId="' + data[i].moduleId + '" ' +
                                'data-plotId="' + data[i].id + '" ' +
                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                ' value="' + data[i].id + ':' + data[i].types[0].id + '" ' +
                                'type="checkbox" style="opacity: 1;"></td>';
                            $('.list-content td:nth-child(2)').width(_width * 0.35);
                        }
                    } else {
                        str = '<td></td>';
                    }
                    //小列表专用     10 11 12   4 5 6 78
                    if (data[i].number == true) {
                        switch (types - 0) {
                            case 10:
                                //未响应告警数量
                                //网络流量
                                //会话数量
                                //网络丢包率
                                //负载传输时延
                                //链路时延RTT
                                //流控宽带占用率
                                if (data[i].id != "86" &&
                                    data[i].id != "1" &&
                                    data[i].id != "2" &&
                                    data[i].id != "9" &&
                                    data[i].id != "23"
                                //data[i].id!="6"
                                //&&data[i].id!="276"
                                ) {
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        /*
                                         * 此处应该判断默认勾选的id是否有传过来
                                         * 若未传则将其默认勾选去掉
                                         *
                                         */
                                        if (
                                            dataChecked.indexOf("86") == -1 ||
                                            dataChecked.indexOf("1") == -1 ||
                                            dataChecked.indexOf("2") == -1 ||
                                            dataChecked.indexOf("9") == -1 ||
                                            dataChecked.indexOf("23") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 11:
                                //未响应告警数量
                                //网络流量
                                //会话数量
                                //网络丢包率
                                //负载传输入时延
                                //链路时延RTT
                                //流控宽带占用率
                                if (data[i].id != "87" &&
                                    data[i].id != "32" &&
                                    data[i].id != "33" &&
                                    data[i].id != "40" &&
                                    data[i].id != "54"
                                //data[i].id!="37"
                                //&&data[i].id!="58"
                                ) {
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("87") == -1 ||
                                            dataChecked.indexOf("32") == -1 ||
                                            dataChecked.indexOf("33") == -1 ||
                                            dataChecked.indexOf("40") == -1 ||
                                            dataChecked.indexOf("54") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 12:
                                /*============================================
                                 * 未响应告警数量
                                 * 网络流量
                                 * 会话数量
                                 * 网络丢包率
                                 * 负载传输时延
                                 * 链路时延RTT
                                 * 流控宽带占用率
                                 ==============================================*/
                                if (data[i].id != "88" &&
                                    data[i].id != "60" &&
                                    data[i].id != "61" &&
                                    data[i].id != "68" &&
                                    data[i].id != "82"
                                //data[i].id!="65"
                                //&&data[i].id!="97"
                                ) {
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("88") == -1 ||
                                            dataChecked.indexOf("60") == -1 ||
                                            dataChecked.indexOf("61") == -1 ||
                                            dataChecked.indexOf("68") == -1 ||
                                            dataChecked.indexOf("82") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 4:
                                if (data[i].id != "101" && //未响应告警数量
                                    //data[i].id!="102"&&   //网络流量
                                    data[i].id != "131" && //HTTP应用会话数量
                                    data[i].id != "133" && //URL加载时延
                                    data[i].id != "275" && //HTTP未响应比率
                                    data[i].id != "273") { //HTTP错误返回码比率
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("101") == -1 ||
                                            dataChecked.indexOf("131") == -1 ||
                                            dataChecked.indexOf("133") == -1 ||
                                            dataChecked.indexOf("275") == -1 ||
                                            dataChecked.indexOf("273") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 5:
                                if (data[i].id != "135" && //未响应告警数量
                                    data[i].id != "158" && //SQL处理时延
                                    data[i].id != "165" && //SQL应用会话数量
                                    data[i].id != "277" && //错误返回码比率
                                    data[i].id != "279") { //未响应比率
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("135") == -1 ||
                                            dataChecked.indexOf("158") == -1 ||
                                            dataChecked.indexOf("165") == -1 ||
                                            dataChecked.indexOf("277") == -1 ||
                                            dataChecked.indexOf("279") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 6:
                                if (data[i].id != "169" && //未响应告警数量
                                    data[i].id != "192" && //SQL处理时延
                                    data[i].id != "199" && //SQL应用会话数量
                                    data[i].id != "280" && //错误返回码比率
                                    data[i].id != "282") { //未响应比率
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("169") == -1 ||
                                            dataChecked.indexOf("192") == -1 ||
                                            dataChecked.indexOf("199") == -1 ||
                                            dataChecked.indexOf("280") == -1 ||
                                            dataChecked.indexOf("282") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 7:
                                if (data[i].id != "203" && //未响应告警数量
                                    data[i].id != "226" && //SQL处理时延
                                    data[i].id != "233" && //SQL应用会话数量
                                    data[i].id != "283" && //错误返回码比率
                                    data[i].id != "285") { //未响应比率
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("203") == -1 ||
                                            dataChecked.indexOf("226") == -1 ||
                                            dataChecked.indexOf("233") == -1 ||
                                            dataChecked.indexOf("283") == -1 ||
                                            dataChecked.indexOf("285") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 8:
                                if (data[i].id!="286"&&   //未响应告警数量
                                    data[i].id != "289" && //URL加载时延
                                    data[i].id != "291" && //HTTP应用会话数量
                                    data[i].id != "294") {
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("289") == -1 ||
                                            dataChecked.indexOf("291") == -1 ||
                                            dataChecked.indexOf("294") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            case 9:
                                if (data[i].id!="303"&&   //交易量
                                    data[i].id != "304" && //交易时延
                                    data[i].id != "305" && //交易成功率
                                    data[i].id != "306") {//交易响应率
                                    str1 = '<td  class="' + dataTogglCla + '">' +
                                        '<input type="checkbox" name="litlisDate" ' +
                                        'data-id="' + data[i].id + '" ' +
                                        ' data-moduleId="' + data[i].moduleId + '"' +
                                        ' data-plotId="' + data[i].id + '" ' +
                                        ' data-plotTypeId="' + data[i].types[0].id + '"' +
                                        ' style="opacity: 1;"></td>';
                                } else {
                                    if (dataChecked && dataChecked.length) {
                                        if (
                                            dataChecked.indexOf("303") == -1 ||
                                            dataChecked.indexOf("304") == -1 ||
                                            dataChecked.indexOf("305") == -1 ||
                                            dataChecked.indexOf("306") == -1
                                        ) {
                                            str1 = '<td  class="' + dataTogglCla + '">' +
                                                '<input type="checkbox" name="litlisDate" ' +
                                                'data-id="' + data[i].id + '"' +
                                                ' data-moduleId="' + data[i].moduleId + '"  ' +
                                                ' data-plotId="' + data[i].id + '" ' +
                                                'data-plotTypeId="' + data[i].types[0].id + '"' +
                                                ' style="opacity: 1;"></td>';
                                        }
                                    } else {
                                        str1 = '<td  class="' + dataTogglCla + '">' +
                                            '<input type="checkbox" name="litlisDate" ' +
                                            'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"' +
                                            ' data-plotId="' + data[i].id + '" ' +
                                            '  data-plotTypeId="' + data[i].types[0].id + '"' +
                                            ' style="opacity: 1;" checked=checked ></td>';
                                    }
                                }
                                break;
                            default:
                                jeBox.alert("当前模块的代码未书写");
                        }
                        $('.list-content td:nth-child(3)').width(_width * 0.35);
                    } else {
                        str1 = '<td class="' + dataTogglCla + '"></td>';
                    }
                    if (dataChecked && dataChecked.length) {
                        for (var k = 0; k < dataChecked.length; k++) {
                            if (data[i].id == dataChecked[k]) {
                                str1 = '<td  class="' + dataTogglCla + '">' +
                                    '<input type="checkbox" name="litlisDate" ' +
                                    'data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '"  ' +
                                    'data-plotId="' + data[i].id + '" ' +
                                    'data-plotTypeId="' + data[i].types[0].id + '"' +
                                    ' style="opacity: 1;" checked=checked ></td>';
                                $('.list-content td:nth-child(3)').width(_width * 0.35);
                            }
                        }
                    }
                    //小列表结束
                    if (_chart.kpiNames == "") {
                        for (var j = 0; j < _chart.kpiName.length; j++) {
                            if (data[i].id == _chart.kpiName[j]) {
                                str = '<td><input  name="litlisChart"  ' +
                                    'data-id="' + data[i].id + '" ' +
                                    'data-moduleId="' + data[i].moduleId + '" ' +
                                    'data-plotId="' + data[i].id + '" ' +
                                    'data-plotTypeId="' + data[i].types[0].id + '"' +
                                    'value="' + data[i].id + ':' + data[i].types[0].id + '" ' +
                                    'type="checkbox" checked=checked style="opacity: 1;"></td>';
                                $('.list-content td:nth-child(2)').width(_width * 0.35);
                            }
                        }
                    } else {
                        for (var k = 0; k < _chart.kpiNames.length; k++) {
                            if (data[i].id == _chart.kpiNames[k]) {
                                str = '<td><input  name="litlisChart"  ' +
                                    'data-id="' + data[i].id + '" ' +
                                    'data-moduleId="' + data[i].moduleId + '" ' +
                                    'data-plotId="' + data[i].id + '" ' +
                                    'data-plotTypeId="' + data[i].types[0].id + '"' +
                                    'value="' + data[i].id + ':' + data[i].types[0].id + '" ' +
                                    'type="checkbox" checked=checked style="opacity: 1;"></td>';
                                $('.list-content td:nth-child(2)').width(_width * 0.35);
                            }
                        }
                    }
                    /**
                     * 此处暂时的对323、324、325做特殊处理
                     * @type {*|jQuery|HTMLElement}
                     */
                    if(urlArry && urlArry.length){
                        //之前未做处理走这里
                        trs = $('<tr>' +
                            '<td>' + data[i].name + '</td>' + str + str1 +
                            '</tr>');
                    }else {
                        if(
                            data[i].id == 323 ||
                            data[i].id == 324 ||
                            data[i].id == 325
                        ){
                            trs = $('<tr class="none">' +
                                '<td>' + data[i].name + '</td>' + str + str1 +
                                '</tr>');
                        }else {
                            trs = $('<tr>' +
                                '<td>' + data[i].name + '</td>' + str + str1 +
                                '</tr>');
                        }
                    }
                    $('.list-content td:first-child').width(_width * 0.6);
                    switch (data[i].groupName) {
                        /**
                         * 此处代码得到的结果是严格按照袁总要求进行数据分类排序 不可更改
                         */
                        case "告警类":
                            //urlArry && urlArry.length 为true小列表勾选出现
                            // data[i].graph 为true 图形勾选出现
                            if(Boolean(urlArry && urlArry.length) || data[i].graph){
                                if ($(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).length) {
                                    //表示此类的下一类是存在的
                                    $(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).before(trs);
                                } else {
                                    $("#table-list").append(trs);
                                }
                            }else {
                                $(trs).addClass("none");
                                if ($(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).length) {
                                    //表示此类的下一类是存在的
                                    $(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).before(trs);
                                } else {
                                    $("#table-list").append(trs);
                                }
                                $("#alarmId").addClass("none");
                            }
                            // if ($(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).length) {
                            // 	//表示此类的下一类是存在的
                            // 	$(".tr-class").eq($(".tr-class").index($("#alarmId")) + 1).before(trs);
                            // } else {
                            // 	$("#table-list").append(trs);
                            // }
                            break;
                        case "时间类":
                            if ($(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).length) {
                                //表示此类的下一类是存在的
                                $(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).before(trs);
                            } else {
                                $("#table-list").append(trs);
                            }
                            break;
                        case "流量类":
                            if ($(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).length) {
                                //表示此类的下一类是存在的
                                $(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).before(trs);
                            } else {
                                $("#table-list").append(trs);
                            }
                            break;
                        case "会话类":
                            if ($(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).length) {
                                //表示此类的下一类是存在的
                                $(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).before(trs);
                            } else {
                                $("#table-list").append(trs);
                            }
                            break;
                        case "数据包类":
                            if ($(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).length) {
                                //表示此类的下一类是存在的
                                $(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).before(trs);
                            } else {
                                $("#table-list").append(trs);
                            }
                            break;
                        case "其他":
                            if ($(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).length) {
                                //表示此类的下一类是存在的
                                $(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).before(trs);
                            } else {
                                $("#table-list").append(trs);
                            }
                            break;
                        case "硬件类":
                            $("#table-list").append(trs);
                            break;
                        default:
                            $("#table-list").append(trs);
                            break;
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
        $('#listDraw').modal('show');
        /**
         * 监听复选框，只能选择10个
         */
        $('input[name="litlisChart"]').click(function (e) {
            var len = $('input[name="litlisChart"]:checked').length;
            if(_chart._pageName == "cockpit"){
                len = len + $(".linedraw").length
            }
            if(len>10){
                var alam_span = '<span class="red alam_span_litlisChart">' +
                    '驾驶舱图形展示至多10个，目前已经超出上限！</span>';
                if(_chart._pageName == "cockpit"){
                    if(!$("#list-save_cockpit").prev().hasClass("alam_span_litlisChart")){
                        $("#list-save_cockpit").before(alam_span);
                    }
                }else {
                    if(!$("#list-save").prev().hasClass("alam_span_litlisChart")){
                        $("#list-save").before(alam_span);
                    }
                }
                setTimeout(function () {
                    $(".alam_span_litlisChart").remove()
                },3000);
                e.preventDefault();//另刚才勾选的取消
            }
        });
    },
    /**
     *
     * @param moduleId
     * @param selectDataId
     * @param hChartParams
     * @private
     */
    _saveJson: function (moduleId, selectDataId, hChartParams) {
        $(".draw").remove();
        var listvalue = [],
            listgragh = [],
            plotId,
            plotTypeId,
            urls,
            html,
            _starttime,
            _endtime,
            ipmCenterId,
            watchpointId,
            chartemId,
            drawCharparem,
            csKey,
            csVal,
            cur_text;//双击样式
        if (hChartParams) {
            _starttime = hChartParams.starttime ? hChartParams.starttime : "";
            _endtime = hChartParams.endtime ? hChartParams.endtime : "";
        } else {
            _starttime = "";
            _endtime = "";
        }
        switch (moduleId - 0) {
            case 10:
                urls = "/watchpointController/getWatchpointGraphical.do";
                chartemId = "draw_ober_plot_plotype";
                csKey = "";
                break;
            case 11:
                urls = "/client/getClientGraphical.do";
                chartemId = "draw_ober_user_plot_plotype";
                csKey = "clientId";
                break;
            case 12:
                urls = "/serverManagement/getServerSideGraphical.do";
                chartemId = "draw_ober_server_plot_plotype";
                csKey = "serverId";
                break;
            case 4:
                urls = "/appController/getPlotData.do";
                chartemId = "draw_ober_web_plot_plotype";
                csKey = "busiId";
                break;
            case 5:
                urls = "/appController/getPlotData.do";
                chartemId = "draw_ober_oracle_plot_plotype";
                csKey = "busiId";
                break;
            case 6:
                urls = "/appController/getPlotData.do";
                chartemId = "draw_ober_mysql_plot_plotype";
                csKey = "busiId";
                break;
            case 7:
                urls = "/appController/getPlotData.do";
                chartemId = "draw_ober_sqlserver_plot_plotype";
                csKey = "busiId";
                break;
            case 8:
                urls = "/url/getPlotData.do";
                chartemId = "draw_ober_url_plot_plotype";
                csKey = "busiId";
                break;
            case 9:
                urls = "/depthanaly/depthanalyGraphical.do";
                chartemId = "draw_ober_baowenJy_plot_plotype";
                csKey = "busiId";
                break;
            case 0:
                urls = "/plot/getPlotData.do";
                chartemId = "draw_ober_systemCapital_plot_plotype";
                csKey = "busiId";
                break;
            default:
                console.info("未书写此模块代码");
        }
        switch (_chart._pageName){
            case "observationPointkpi":
            case "userSidekpi":
            case "serverSidekpi":
            case "httpSerkpi":
            case "oracleSerkpi":
            case "mysqlSerkpi":
            case "sqlSerkpi":
            case "urlkpi":
            case "baowenJy":
                cur_text = "cur_text";
                break;
            case "systemCapital":
                cur_text = "";
                break;
            default:
                cur_text = "cur_text";
        }
        $('input[name="litlisChart"]:checked').each(function () {
            listgragh.push($(this).val());
        });
        _chart.kpiNames = []; //为解决bug新增代码
        watchpointId = moduleId == 10 ? selectDataId : 1;
        if (hChartParams) {
            watchpointId = hChartParams.watchpointId;
            ipmCenterId = hChartParams.ipmCenterId;
        }
        csVal = moduleId == 10 ? undefined : selectDataId;
        var datas = "{\"graph\":[";
        for (var j = 0; j < listgragh.length; j++) {
            listvalue = listgragh[j].split(":");
            plotId = listvalue[0];
            plotTypeId = listvalue[1];
            datas += '{';
            datas += '"ipmCenterId":"' + ipmCenterId + '",';
            datas += '"urlPath":"' + urls + '",';
            if (moduleId != 12) {
                datas += '"watchpointId":"' + watchpointId + '",';
            }
            datas += '"' + csKey + '":"' + csVal + '",';
            datas += '"plotId":"' + plotId + '",';
            datas += '"plotTypeId":"' + plotTypeId + '"';
            datas += '}';
            datas += ',';
            /*----出图形---------**/
            html = '<div class="col-md-6 draw drawPading">' +
                '<div class="title">' +
                '<h2 class="tile-title" id =draw_title_' + j + '>图形</h2>' +
                '</div>' +
                '<div class="linedraw '+cur_text+'">' +
                '<div id=' + chartemId + j +
                ' data-moduleId = "' + moduleId + '" ' +
                ' data-' + csKey + '=' + csVal +
                ' data-ipmCenterId=' + ipmCenterId +
                ' data-watchpointId=' + watchpointId +
                ' data-plotId=' + plotId +
                ' data-plotTypeId=' + plotTypeId +
                ' data-starttime=' + _starttime +
                ' data-endtime=' + _endtime +
                ' data-url=' + urls +
                ' style="width:100%;height:221px;"></div>' +
                '</div>' +
                '</div>';
            $(".pointline").prepend(html);
            _chart.drawId.push(chartemId + j);
            drawCharparem = {
                "ipmCenterId": ipmCenterId,
                "watchpointId": watchpointId,
                "plotId": plotId,
                "moduleId": moduleId,
                "plotTypeId": plotTypeId,
                "starttime": _starttime?_starttime:undefined,
                "endtime": _endtime?_endtime:undefined
            };
            if (moduleId == 12) {
                drawCharparem.serverId = selectDataId;
            } else if (moduleId == 11) {
                drawCharparem.clientId = selectDataId;
            } else {
                if (moduleId != 10) {
                    drawCharparem.busiId = selectDataId;
                }
            }
            $.drawHChart(chartemId + j,
                'draw_title_' + j,
                urls,
                drawCharparem
            );
            $("#content .block-area").on("dblclick", "#" + chartemId + j, function () {
                if(!hChartParams.dblclick){
                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                        var chartUrl = $(this).attr("data-url"),
                            ipmCenterId = $(this).attr("data-ipmCenterId"),
                            watchpointId = $(this).attr("data-watchpointId"),
                            clientId = $(this).attr("data-clientId"),
                            serverId = $(this).attr("data-serverId"),
                            busiId = $(this).attr("data-busiId"),
                            moduleId = $(this).attr("data-moduleId"),
                            plotId = $(this).attr("data-plotId"),
                            plotTypeId = $(this).attr("data-plotTypeId"),
                            starttime = $(this).attr("data-starttime") && $(this).attr("data-starttime") != "undefined" ?
                                $(this).attr("data-starttime") : "",
                            endtime = $(this).attr("data-endtime") && $(this).attr("data-endtime") != "undefined" ?
                                $(this).attr("data-endtime") : "",
                            csParam = clientId && clientId != "undefined" ? '&clientId=' + clientId :
                                serverId && serverId != "undefined" ? '&serverId=' + serverId : "";
                        if (chartUrl == "/appController/getPlotData.do"
                            || chartUrl == "/url/getPlotData.do"
                            ||chartUrl == "/depthanaly/depthanalyGraphical.do") {
                            location.href = 'bssSession.html?' +
                                'moduleId=' + moduleId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'busiId=' + busiId + '&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'starttime=' + starttime + '&' +
                                'endtime=' + endtime + '&' +
                                'watchpointId=' + watchpointId;
                        } else {
                            location.href = 'commun_queue.html?' +
                                'moduleId=' + moduleId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'watchpointId=' + watchpointId + '&' +
                                'chartUrl=' + chartUrl + '&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'starttime=' + starttime + '&' +
                                'endtime=' + endtime + csParam;
                        }
                    }
                }
            });
        }
        datas += "]}";
        var json = datas.substring(0, listgragh.length?datas.length - 3:datas.length-2) + "]}";
        if(_chart._pageName == "netCockpit"){
            _chart._netAlarmChart(hChartParams.netAlarmChart);
            _chart._creatFSChart(hChartParams.fsParams);
        }
        $('.pointline').dad({
            draggable: '.title',
            target:".draw",
            placeholder: '',
            callback:function () {
                _chart._dadDrageSave(_chart._saveChartCont());
            }
        });
        //此处观察点ID可不传
        $.ajax({
            url: "/viewConfig/updModuleConfig.do",
            method: "POST",
            data: {
                ipmCenterId:ipmCenterId,
                moduleId: moduleId,
                busiId: selectDataId,
                content: json
            },
            dataType: "json",
            beforeSend:function(XMLHttpRequest){},
            success: function (data,textStatus,errorThorwn) {
                console.log(data);
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                console.error("保存失败");
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn);
            },
            complete:function (XMLHttpRequest,textStatus) {}
        });
        $("#listDraw").modal("hide");
    },
    /**
     *
     * @param _starttime
     * @param _endtime
     * @param isRefreClum
     */
    refreshChart: function (_starttime, _endtime, isRefreClum) {
        if ($(".linedraw").length) {
            var $lChar = $(".linedraw");
            for (var i = 0; i < $lChar.length - 1; i++) {
                var domId = $lChar.eq(i).children().attr("id"),
                    isDrawColumn = $("#" + domId).attr("data-chartType"),
                    titleId = $lChar.eq(i).prev("div.title").children("h2.tile-title").attr("id"),
                    url = $lChar.eq(i).children().attr("data-url"),
                    watchpointId = $lChar.eq(i).children().attr("data-watchpointId"),
                    plotId = $lChar.eq(i).children().attr("data-plotId"),
                    plotTypeId = $lChar.eq(i).children().attr("data-plotTypeId"),
                    serverId = $lChar.eq(i).children().attr("data-serverId"),
                    moduleId = $lChar.eq(i).children().attr("data-moduleId"),
                    busiId = $lChar.eq(i).children().attr("data-busiId"),
                    clientId = $lChar.eq(i).children().attr("data-clientId"),
                    //starttime = $.myTime.DateToUnix($.myTime.nowTime())-600,
                    //endtime = $.myTime.DateToUnix($.myTime.nowTime()),
                    params = {
                        "watchpointId": watchpointId,
                        "plotId": plotId,
                        "moduleId": moduleId,
                        "plotTypeId": plotTypeId
                    };
                if (_starttime && _endtime) {
                    params.starttime = _starttime;
                    params.endtime = _endtime;
                    $lChar.eq(i).children().attr({
                        "data-starttime": _starttime,
                        "data-endtime": _endtime
                    });
                } else {
                    $lChar.eq(i).children().attr({
                        "data-starttime": "",
                        "data-endtime": ""
                    });
                }
                serverId && serverId != "undefined" ? params.serverId = serverId :
                    clientId && clientId != "undefined" ? params.clientId = clientId : "";
                if (busiId && busiId != "undefined") {
                    params.busiId = busiId
                }
                if (!isDrawColumn && isDrawColumn != "drawColumn") {
                    $.drawHChart(domId, titleId, url, params);
                } else {
                    if (isRefreClum) {
                        $.drawHChart(domId, titleId, url, params);
                    }
                }
            }
        }
    },
    /**
     * 此处方法有待优化 可以直接获取元素所有的属性
     * 而不用很low的一个个去取
     * @param _watchpointId
     * @param _busiIdObj
     * @param _starttime
     * @param _endtime
     * @param _ipmCenterId
     * @param ctrolParms
     */
    reloadChart: function (_watchpointId, _busiIdObj, _starttime, _endtime, _ipmCenterId,ctrolParms) {
        if ($(".linedraw").length) {
            var $lChar = $(".linedraw"),
                watchpointId = _watchpointId,
                ipmCenterId = _ipmCenterId,
                ipmCenterName;
            $.ajax({
                url: "/center/getCenterIpInfo.do",
                method: "POST",
                async: false,
                data: {},
                dataType: "json",
                beforeSend:function(XMLHttpRequest){},
                success: function (data,textStatus,XMLHttpRequest) {
                    data.forEach(function (item, index) {
                        if (item.id == ipmCenterId) {
                            ipmCenterName = item.name;
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
            //应该在此处写得到XPM服务器的接口代码
            function refreshChart(_starttime, _endtime, $lCharEqi, isRefreClum, ipmCenterName,ctrolParms) {
                var domId = $lCharEqi.children().attr("id"),
                    isDrawColumn = $("#" + domId).attr("data-chartType"),
                    titleId = $lCharEqi.prev("div.title").children("h2.tile-title").attr("id"),
                    url = $lCharEqi.children().attr("data-url"),
                    ipmCenterId = $lCharEqi.children().attr("data-ipmCenterId"),
                    watchpointId = $lCharEqi.children().attr("data-watchpointId"),
                    plotId = $lCharEqi.children().attr("data-plotId"),
                    plotTypeId = $lCharEqi.children().attr("data-plotTypeId"),
                    serverId = $lCharEqi.children().attr("data-serverId"),
                    moduleId = $lCharEqi.children().attr("data-moduleId"),
                    busiId = $lCharEqi.children().attr("data-busiId"),
                    clientId = $lCharEqi.children().attr("data-clientId"),
                    columnNum = $lCharEqi.children().attr("data-columnNum"),//告警图形刷新
                    handledflag =  $lCharEqi.children().attr("data-handledflag"),//告警图形刷新
                    businessId =  $lCharEqi.children().attr("data-businessId"),//告警图形刷新
                    netCockpit = $lCharEqi.children(".netCVlue").attr("data-isNet"),//用于区分是否为四个小图形
                    params = {
                        "ipmCenterId": ipmCenterId,
                        "ipmCenterName": ipmCenterName,
                        "watchpointId": watchpointId,
                        "plotId": plotId,
                        "moduleId": moduleId,
                        "plotTypeId": plotTypeId,
                        "columnNum":columnNum,
                        "handledflag":handledflag,
                        "businessId":businessId
                    };
                if (_starttime && _endtime) {
                    params.starttime = _starttime;
                    params.endtime = _endtime;
                    $lCharEqi.attr({
                        "data-starttime": _starttime,
                        "data-endtime": _endtime
                    });
                    $lCharEqi.children().attr({
                        "data-starttime": _starttime,
                        "data-endtime": _endtime
                    });
                } else {
                    $lCharEqi.children().attr({
                        "data-starttime": "",
                        "data-endtime": ""
                    });
                }
                serverId && serverId != "undefined" ? params.serverId = serverId :
                    clientId && clientId != "undefined" ? params.clientId = clientId : "";
                if (busiId && busiId != "undefined") {
                    params.busiId = busiId
                }
                if(domId){
                    // if (!isDrawColumn && isDrawColumn != "column" && isDrawColumn != "bar") {
                    if (!isDrawColumn && isDrawColumn != "bar") {
                        $.drawHChart(domId, titleId, url, params);
                    } else {
                        /**
                         * bar 不刷新 column一分钟
                         * 此处涉及到以下几个逻辑
                         *  1、回溯 或 刷新 两种柱图都刷新
                         *  2、10秒累加数值 column刷新
                         *  3、清除定时器时将 控制刷新数值给归零
                         */
                        if(ctrolParms == "back"){
                            $.drawHChart(domId, titleId, url, params);
                        }else {
                            if(ctrolParms == "clearTime"){
                                _chart.refreNum = 0;
                            }else {
                                if(_chart.refrebar){
                                    _chart.refrebar = false;
                                    $.drawHChart(domId, titleId, url, params);
                                }else {
                                    if(isDrawColumn == "column"){
                                        // _chart.refreNum += 10;
                                        // if(_chart.refreNum == 60){
                                        //     /**
                                        //      * 此处的bug在于如果有很多柱图则谁先循环到此谁刷新
                                        //      * 后续的图永远不可刷新
                                        //      * @type {number}
                                        //      */
                                        //     $.drawHChart(domId, titleId, url, params);
                                        //     _chart.refreNum = 0;
                                        // }

                                        $.drawHChart(domId, titleId, url, params);
                                    }
                                }
                            }
                        }
                    }
                }else {
                    if(netCockpit == "netCockpit"){
                        var netCparmas = {
                            ipmCenterId:ipmCenterId,
                            watchpointId:watchpointId,
                            moduleId:moduleId,
                            plotIds:plotId,
                            busiId: busiId,
                            clientId: clientId,
                            serverId: serverId
                        };
                        if(_busiIdObj.timeback == "netCockpit"){
                            netCparmas.starttime = _starttime;
                            netCparmas.endtime = _endtime;
                        }
                        $.ajax({
                            url:url,
                            method:"POST",
                            async: false,
                            data:netCparmas,
                            dataType:"json",
                            beforeSend:function (XMLHttpRequest,textStatus,XMLHttpRequest) {},
                            success:function (data) {
                                var numObj = numForUtil(data.data[0].value,data.data[0].unit);
                                for(var j =0;j<$(".netCVlue").length;j++){
                                    if($(".netCVlue").eq(j).attr("data-plotid") == plotId){
                                        $(".netCVlue").eq(j).text(numObj.value);
                                        $(".netCUnit").eq(j).text(numObj.unit);
                                    }
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                }
            }
            for (var i = 0; i < $lChar.length - 1; i++) {
                if (ipmCenterId) {
                    $lChar.eq(i).children().attr("data-ipmCenterId", ipmCenterId);
                }
                if (watchpointId) {
                    $lChar.eq(i).children().attr("data-watchpointId", watchpointId);
                }
                if (_busiIdObj.clientId) {
                    $lChar.eq(i).children().attr("data-clientId", _busiIdObj.clientId);
                }
                if (_busiIdObj.serverId) {
                    $lChar.eq(i).children().attr("data-serverId", _busiIdObj.serverId);
                }
                if (_busiIdObj.busiId) {
                    $lChar.eq(i).children().attr("data-busiId", _busiIdObj.busiId);
                }
                if (_starttime && _endtime) {
                    $lChar.attr({
                        "data-starttime": _starttime,
                        "data-endtime": _endtime
                    });
                    refreshChart(_starttime, _endtime, $lChar.eq(i), true, ipmCenterName,ctrolParms)
                } else {
                    refreshChart(null, null, $lChar.eq(i), false, ipmCenterName,ctrolParms)
                }
            }
        }
    }
};