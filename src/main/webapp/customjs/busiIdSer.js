/**
 * Created by yanb on 2018/1/16.
 */
;
$(function () {
    /* =============================================================
     * 此几个模块可以整合成一个function
     * 不同点为 表格名称不同 表格ID不同 模块ID不同 以及
     * 表格数量不同 是否为收缩状态不同 待有时间将其写为一个函数
     * 可惜一直没有时间。。。。。。。
     *
     ===============================================================*/
    var defaultCockpit = {
        /**
         * 获取页面名
         */
        pageName:location.pathname.split(".")[0].replace(/\//,""),
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
         *
         * @param tableId
         * @param moduleId
         * @param ipm_title
         * @param refreTime
         */
        busiIdSer: function (tableId, moduleId, ipm_title,refreTime) {
            var busiId = parseInt($.getUrlParams().dataId),
                ipmCenterId,
                watchpointId = parseInt($.getUrlParams().watpointId),
                watIndex,
                httpSeIndex,
                onOff,
                _starttime,
                _endtime,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            $.ptcsBSTable("centerTable", "/center/getCenterIpInfo.do", null, {
                pageSize: 10,
                columns: [
                    {
                        field: "name",
                        title: "名称",
                        sortable: true
                    },
                    {
                        field: "ip",
                        title: "IP",
                        sortable: true
                    },
                    {
                        field: "port",
                        title: "端口",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                ipm_title: "XPM服务器",
                ipm_shrink: true,
                ipm_show: false,
                ipm_column_save: true,
                rowStyle: function (row, index) {
                    var cla = {};
                    if (index) {
                        cla.classes = "cursor";
                    } else {
                        ipmCenterId = row.id;
                        cla.classes = "custom-row-style cursor";
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                    $(tr).addClass("custom-row-style");
                    ipmCenterId = row.id;
                    _chart.refreNum = 0;
                    $.ajax({
                        url:"/commonController/getNpmListRrdData.do",
                        type:"post",
                        data:{
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        },
                        dataType:"json",
                        success:function (data) {
                            if(data && data.length){
                                watchpointId = data[0].id;
                                $.ptcsBSTabRefresh("watchPoint", {
                                    "ipmCenterId": ipmCenterId,
                                    "moduleId": 10,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ptcsBSTabRefresh(tableId, {
                                    "moduleId": moduleId,
                                    "ipmCenterId": ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                            }
                        }
                    })
                    // $.ptcsBSTabRefresh("watchPoint", {
                    //     "ipmCenterId": ipmCenterId,
                    //     "moduleId": 10,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh(tableId, {
                    //     "moduleId": moduleId,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                },
                onLoadSuccess: function () {
                    $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("watchPoint", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_show: false,
                            ipm_title: "观察点KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!watIndex) {
                                    watIndex = 0;
                                }
                                if (i == watIndex) {
                                    cla.classes = "custom-row-style cursor";
                                    watchpointId = row.id;
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                watchpointId = row.id;
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (data) {
                                        if(data && data.length){
                                            busiId = data[0].id;
                                            switch (field) {
                                                case "id":
                                                case "name":
                                                    $("#watchPoint > tbody > .custom-row-style").removeClass("custom-row-style");
                                                    $($element).parent().addClass("custom-row-style");
                                                    watIndex = $($element).parent().attr("data-index");
                                                    $.ptcsBSTabRefresh(tableId, {
                                                        "moduleId": moduleId,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    _chart._createChart({
                                                        "type": moduleId,
                                                        "ipmCenterId": ipmCenterId,
                                                        "selectDataId": busiId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    }, {
                                                        "watchpointId": watchpointId,
                                                        "busiId": busiId
                                                    });
                                                    break;
                                                case "alarmLevel":
                                                    if (_starttime && _endtime) {
                                                        location.href = 'alarmSetting.html?' +
                                                            'ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=10'+
                                                            '&busiId='+busiId+
                                                            '&watchpointId=' + watchpointId +
                                                            '&starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&state=N';
                                                    } else {
                                                        var sT = $.getDefaultEndtime() - 600;
                                                        if ($.getDefaultEndtime() && sT) {
                                                            if (value == 0) {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&state=N';
                                                            } else {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&starttime=' + sT +
                                                                    '&endtime=' + $.getDefaultEndtime() +
                                                                    '&state=N';
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    $.ajax({
                                                        url: "/plot/getPlotByModuleKpiName.do",
                                                        type: "post",
                                                        data: {
                                                            moduleId: 10,
                                                            kpiName: field
                                                        },
                                                        dataType: "json",
                                                        success: function (data) {
                                                            var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                                plotId = data.id,
                                                                plotTypeId = data.types[0].id,
                                                                _watchpointId = row.id;
                                                            if (_starttime && _endtime) {
                                                                location.href = 'commun_queue.html?' +
                                                                    'starttime=' + _starttime +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&endtime=' + _endtime +
                                                                    '&chartUrl=' + chartUrl + '&' +
                                                                    'plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            } else {
                                                                location.href = 'commun_queue.html?' +
                                                                    'chartUrl=' + chartUrl + '&' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                });
                            },
                            onLoadSuccess: function () {
                                if(onOff){
                                    if($(".linedraw").length-1){
                                        if ($("#watchPoint").bootstrapTable("getData").length) {
                                            _chart.reloadChart(watchpointId, {
                                                "busiId": busiId
                                            }, _starttime, _endtime, ipmCenterId);
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }else {
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    }
                                }else {
                                    if ($("#watchPoint").bootstrapTable("getData").length ||
                                        $("#"+tableId).bootstrapTable("getData").length) {
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/appController/getAppListColumn.do", {
                        "moduleId": moduleId,
                        "typeId": moduleId
                    }, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable(tableId, "/commonController/getNpmListRrdData.do", {
                            "moduleId": moduleId,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: ipm_title + "服务KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!httpSeIndex) {
                                    httpSeIndex = 0;
                                }
                                if (i == httpSeIndex) {
                                    cla.classes = "custom-row-style cursor";
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                busiId = row.id;
                                switch (field) {
                                    case "id":
                                    case "name":
                                        $("#" + tableId + " > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        httpSeIndex = $($element).parent().attr("data-index");
                                        _chart.refreNum = 0;
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if (_starttime && _endtime) {
                                            location.href = 'alarmSetting.html?' +
                                                'ipmCenterId=' + ipmCenterId +
                                                '&watchpointId=' + watchpointId +
                                                '&moduleId='+ moduleId +
                                                '&busiId=' + busiId +
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&state=N';
                                        } else {
                                            var sT = $.getDefaultEndtime() - 600;
                                            if ($.getDefaultEndtime() && sT) {
                                                if (value == 0) {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId='+ moduleId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&busiId=' + busiId + '&state=N';
                                                } else {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId='+ moduleId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&starttime=' + sT +
                                                        '&endtime=' + $.getDefaultEndtime() +
                                                        '&busiId=' + busiId +
                                                        '&state=N';
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            type: "post",
                                            data: {
                                                moduleId: moduleId,
                                                kpiName: field
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                var chartUrl = "/appController/getPlotData.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    _watchpointId = watchpointId,
                                                    busiId = row.id;
                                                if (_starttime && _endtime) {
                                                    location.href = 'bssSession.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&plotId=' + plotId + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=' + moduleId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&busiId=' + busiId;
                                                } else {
                                                    location.href = 'bssSession.html?' +
                                                        'plotId=' + plotId + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=' + moduleId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&busiId=' + busiId;
                                                }
                                            }
                                        })
                                }
                            }
                        });
                    }, "json");
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if($("#"+tableId).bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(moduleId);
                }else {
                    jeBox.alert("请先添加"+ipm_title+"业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(moduleId, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer3);
                    clearInterval(timer2);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("watchPoint", {
                    "moduleId": 10,
                    "ipmCenterId": ipmCenterId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh(tableId, {
                    "moduleId": moduleId,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            timeBackText();
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         */
        url: function (refreTime) {
            var busiId = parseInt($.getUrlParams().dataId),
                ipmCenterId,
                watchpointId = parseInt($.getUrlParams().watpointId),
                _starttime,
                _endtime,
                onOff,
                watIndex,
                httpSeIndex,
                timer3 = setInterval(refreshTableData, refreTime),
                timer2 = setInterval(timeBackText, refreTime);
            $.ptcsBSTable("centerTable", "/center/getCenterIpInfo.do", null, {
                pageSize: 10,
                columns: [{
                        field: "name",
                        title: "名称",
                        sortable: true
                    },
                    {
                        field: "ip",
                        title: "IP",
                        sortable: true
                    },
                    {
                        field: "port",
                        title: "端口",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                ipm_title: "XPM服务器",
                ipm_shrink: true,
                ipm_show: false,
                ipm_column_save: true,
                rowStyle: function (row, index) {
                    var cla = {};
                    if (index) {
                        cla.classes = "cursor";
                    } else {
                        ipmCenterId = row.id;
                        cla.classes = "custom-row-style cursor";
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                    $(tr).addClass("custom-row-style");
                    ipmCenterId = row.id;
                    _chart.refreNum = 0;

                    // $.ptcsBSTabRefresh("watchPoint", {
                    //     "ipmCenterId": ipmCenterId,
                    //     "moduleId": 10,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("urlkpiState", {
                    //     "moduleId": 8,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("urlkpiProp", {
                    //     "moduleId": 8,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "appBusinessId": busiId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime,
                    //     "busiId": busiId
                    // });
                    $.ajax({
                        url:"/commonController/getNpmListRrdData.do",
                        type:"post",
                        data:{
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        },
                        dataType:"json",
                        success:function (data) {
                            if(data && data.length){
                                watchpointId = data[0].id;
                                $.ptcsBSTabRefresh("watchPoint", {
                                    "ipmCenterId": ipmCenterId,
                                    "moduleId": 10,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ptcsBSTabRefresh("urlkpiState", {
                                    "moduleId": 8,
                                    "ipmCenterId": ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ajax({
                                    url:"/url/getUrlStateList.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    },
                                    dataType:"json",
                                    success:function (urlData) {
                                        if(urlData && urlData.length){
                                            busiId = urlData[0].id;
                                            $.ptcsBSTabRefresh("urlkpiProp", {
                                                "moduleId": 8,
                                                "ipmCenterId": ipmCenterId,
                                                "watchpointId": watchpointId,
                                                "appBusinessId": busiId,
                                                "starttime": _starttime,
                                                "endtime": _endtime,
                                                "busiId": busiId
                                            });
                                        }else {
                                            $('#urlkpiProp').bootstrapTable('load', []);
                                        }
                                    },
                                    error:function () {
                                        $('#urlkpiProp').bootstrapTable('load', []);
                                    }
                                });
                            }else {
                                $('#watchPoint').bootstrapTable('load', []);
                                $('#urlkpiState').bootstrapTable('load', []);
                            }
                        },
                        error:function () {
                            $('#watchPoint').bootstrapTable('load', []);
                            $('#urlkpiState').bootstrapTable('load', []);
                            $('#urlkpiProp').bootstrapTable('load', []);
                        }
                    });
                    // $.post("/commonController/getNpmListRrdData.do",
                    //     { "moduleId": 10,
                    //     "ipmCenterId": ipmCenterId
                    //     },
                    //     function(data){
                    //     watchpointId = data[0].id;
                    //         $.ptcsBSTabRefresh("watchPoint", {
                    //             "ipmCenterId": ipmCenterId,
                    //             "moduleId": 10,
                    //             "watchpointId": watchpointId,
                    //             "starttime": _starttime,
                    //             "endtime": _endtime
                    //         });
                    //         $.ptcsBSTabRefresh("urlkpiState", {
                    //             "moduleId": 8,
                    //             "ipmCenterId": ipmCenterId,
                    //             "watchpointId": watchpointId,
                    //             "starttime": _starttime,
                    //             "endtime": _endtime
                    //         });
                    //         $.ptcsBSTabRefresh("urlkpiProp", {
                    //             "moduleId": 8,
                    //             "ipmCenterId": ipmCenterId,
                    //             "watchpointId": watchpointId,
                    //             "appBusinessId": busiId,
                    //             "starttime": _starttime,
                    //             "endtime": _endtime,
                    //             "busiId": busiId
                    //         });
                    // });


                },
                onLoadSuccess: function () {
                    $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("watchPoint", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_show: false,
                            ipm_title: "观察点KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!watIndex) {
                                    watIndex = 0;
                                }
                                if (i == watIndex) {
                                    cla.classes = "custom-row-style cursor";
                                    watchpointId = row.id;
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                watchpointId = row.id;
                                $.ajax({
                                    url:"/url/getUrlStateList.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    },
                                    dataType:"json",
                                    success:function (urlData) {
                                        if(urlData && urlData.length){
                                            busiId = urlData[0].id;
                                            switch (field) {
                                                case "id":
                                                case "name":
                                                    $("#watchPoint > tbody > .custom-row-style").removeClass("custom-row-style");
                                                    $($element).parent().addClass("custom-row-style");
                                                    watIndex = $($element).parent().attr("data-index");
                                                    _chart.refreNum = 0;
                                                    $.ptcsBSTabRefresh("urlkpiState", {
                                                        "moduleId": 8,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    $.ptcsBSTabRefresh("urlkpiProp", {
                                                        "moduleId": 8,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "appBusinessId": busiId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime,
                                                        "busiId": busiId
                                                    });
                                                    _chart._createChart({
                                                        "type": 8,
                                                        "ipmCenterId": ipmCenterId,
                                                        "selectDataId": busiId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    }, {
                                                        "watchpointId": watchpointId,
                                                        "busiId": busiId
                                                    });
                                                    break;
                                                case "alarmLevel":
                                                    if (_starttime && _endtime) {
                                                        location.href = 'alarmSetting.html?' +
                                                            'watchpointId=' + watchpointId +
                                                            '&moduleId=10'+
                                                            '&busiId='+ busiId +
                                                            '&ipmCenterId=' + ipmCenterId +
                                                            '&starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&state=N';
                                                    } else {
                                                        var sT = $.getDefaultEndtime() - 600;
                                                        if ($.getDefaultEndtime() && sT) {
                                                            if (value == 0) {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+ busiId +
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&state=N';
                                                            } else {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+ busiId +
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&starttime=' + sT +
                                                                    '&endtime=' + $.getDefaultEndtime() +
                                                                    '&state=N';
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    $.ajax({
                                                        url: "/plot/getPlotByModuleKpiName.do",
                                                        type: "post",
                                                        data: {
                                                            moduleId: 10,
                                                            kpiName: field
                                                        },
                                                        dataType: "json",
                                                        success: function (data) {
                                                            var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                                plotId = data.id,
                                                                plotTypeId = data.types[0].id,
                                                                _watchpointId = row.id;
                                                            if (_starttime && _endtime) {
                                                                location.href = 'commun_queue.html?' +
                                                                    'starttime=' + _starttime +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+ busiId +
                                                                    '&endtime=' + _endtime +
                                                                    '&chartUrl=' + chartUrl + '&' +
                                                                    'plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            } else {
                                                                location.href = 'commun_queue.html?' +
                                                                    'chartUrl=' + chartUrl + '&' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+ busiId +
                                                                    '&plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                });
                            },
                            onLoadSuccess:function () {
                                if(onOff){
                                    if($(".linedraw").length-1){
                                        if ($("#watchPoint").bootstrapTable("getData").length) {
                                            _chart.reloadChart(watchpointId, {
                                                "busiId": busiId
                                            }, _starttime, _endtime, ipmCenterId);
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }else {
                                        _chart._createChart({
                                            "type": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    }
                                }else {
                                    if ($("#watchPoint").bootstrapTable("getData").length ||
                                        $("#urlkpiProp").bootstrapTable("getData").length) {
                                        _chart._createChart({
                                            "type": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/appController/getAppListColumn.do", {
                        moduleId: 8,
                        ipmCenterId: ipmCenterId,
                        typeId: 8
                    }, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("urlkpiState", "/url/getUrlStateList.do", {
                            "moduleId": 8,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "URL状态列表",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!httpSeIndex) {
                                    httpSeIndex = 0;
                                }
                                if (i == httpSeIndex) {
                                    cla.classes = "custom-row-style cursor";
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                busiId = row.id;
                                switch (field) {
                                    case "id":
                                    case "name":
                                        $("#urlkpiState > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        httpSeIndex = $($element).parent().attr("data-index");
                                        _chart.refreNum = 0;
                                        $.ptcsBSTabRefresh("urlkpiState", {
                                            "moduleId": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "watchpointId": watchpointId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        });
                                        $.ptcsBSTabRefresh("urlkpiProp", {
                                            "moduleId": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "watchpointId": watchpointId,
                                            "appBusinessId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime,
                                            "busiId": busiId
                                        });
                                        _chart._createChart({
                                            "type": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if (_starttime && _endtime) {
                                            location.href = 'alarmSetting.html?' +
                                                'ipmCenterId=' + ipmCenterId +
                                                '&moduleId=8'+
                                                '&busiId='+ busiId +
                                                '&watchpointId=' + watchpointId +
                                                '&starttime=' + _endtime +
                                                '&endtime=' + _endtime +
                                                '&state=N';
                                        } else {
                                            var sT = $.getDefaultEndtime() - 600;
                                            if ($.getDefaultEndtime() && sT) {
                                                if (value == 0) {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=8'+
                                                        '&busiId='+ busiId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&state=N';
                                                } else {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=8'+
                                                        '&busiId='+ busiId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&starttime=' + sT +
                                                        '&endtime=' + $.getDefaultEndtime() +
                                                        '&state=N';
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            type: "post",
                                            data: {
                                                moduleId: 8,
                                                kpiName: field
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                var chartUrl = "/appController/getPlotData.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    _watchpointId = watchpointId,
                                                    busiId = row.id;
                                                if (_starttime && _endtime) {
                                                    location.href = 'bssSession.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&plotId=' + plotId  +
                                                        '&moduleId=8'+
                                                        '&busiId='+ busiId +
                                                        '&plotTypeId=' + plotTypeId +
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&watchpointId=' + _watchpointId;
                                                } else {
                                                    location.href = 'bssSession.html?' +
                                                        'plotId=' + plotId +
                                                        '&moduleId=8'+
                                                        '&busiId='+ busiId +
                                                        '&plotTypeId=' + plotTypeId +
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&watchpointId=' + _watchpointId ;
                                                }
                                            }
                                        })
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/appController/getAppListColumn.do", {
                        moduleId: 8,
                        typeId: 18
                    }, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("urlkpiProp", "/url/getSimpleUrlStateList.do", {
                            "moduleId": 8,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime,
                            "busiId": busiId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "URL性能列表",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                return {
                                    classes: "cursor"
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if($("#urlkpiState").bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(8);
                }else {
                    jeBox.alert("请先添加URL业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(8, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    // _chart._createChart({
                    //     "type": 8,
                    //     "ipmCenterId": ipmCenterId,
                    //     "selectDataId": busiId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // }, {
                    //     "watchpointId": watchpointId, //若页面中有图形用户再次刷新则会出现此自动赋值为默认值
                    //     "busiId": busiId
                    // });
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("watchPoint", {
                    "moduleId": 10,
                    "ipmCenterId": ipmCenterId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("urlkpiState", {
                    "moduleId": 8,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("urlkpiProp", {
                    "moduleId": 8,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime,
                    "busiId": busiId
                });
            }
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
            }
            timeBackText();
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         */
        watpoint: function (refreTime) {
            var watchpointId = parseInt($.getUrlParams().dataId),
                watIndex,
                ipmCenterId,
                _starttime,
                _endtime,
                onOff,
                onOff2,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            timeBackText();
            $.ajax({
                url:"/center/getRemoteWatchpointKpiList.do",
                method:"POST",
                data:{
                    "starttime": _starttime,
                    "endtime": _endtime
                },
                dataType:"json",
                beforeSend:function (XMLHttpRequest) {},
                success:function (data,textStatus,XMLHttpRequest) {
                    if(data.length){
                        ipmCenterId = data[0].ipmCenterId;
                        watchpointId = data[0].id;
                        $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                            var csArr = [];
                            for (var i = 0, len = data.length; i < len; i++) {
                                csArr.push({
                                    id: data[i].id,
                                    field: data[i].columnen,
                                    title: data[i].columnzh,
                                    sortable: true,
                                    visible: !!data[i].checked
                                });
                            }
                            $.ptcsBSTable("centerTable", "/center/getRemoteWatchpointKpiList.do", {
                                "starttime": _starttime,
                                "endtime": _endtime
                            }, {
                                pageSize: 10,
                                columns: csArr,
                                ipm_title: "XPM服务器",
                                ipm_shrink: true,
                                ipm_column_save: true,
                                rowStyle: function (row, i) {
                                    var cla = {};
                                    if(onOff2){
                                        if(row.ipmCenterId == ipmCenterId){
                                            cla.classes = "custom-row-style cursor";
                                        }else {
                                            cla.classes = "cursor";
                                        }
                                    }else {
                                        if (i) {
                                            cla.classes = "cursor";
                                        } else {
                                            ipmCenterId = row.ipmCenterId;
                                            cla.classes = "custom-row-style cursor";
                                        }
                                    }
                                    return cla;
                                },
                                onClickCell: function (field, value, row, $element) {
                                    /**
                                     * 用户名称跳转 ipmCenterName  ip port
                                     * 观察点名称刷新 name
                                     * 其它单元格功能跟之前一样
                                     *
                                     */
                                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                                    $($element).parent().addClass("custom-row-style");
                                    ipmCenterId = row.ipmCenterId;
                                    watchpointId = row.id;
                                    onOff2 = true;
                                    _chart.refreNum = 0;
                                    switch (field){
                                        case "ipmCenterName":
                                        case "ip":
                                        case "port":
                                            location.href = window.location.protocol+"//"+row.ip+":"+row.port+"/login.html";
                                            break;
                                        case "name":
                                            $.ptcsBSTabRefresh("userSidekpi", {
                                                "ipmCenterId": ipmCenterId,
                                                "moduleId": 11,
                                                "watchpointId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                            $.ptcsBSTabRefresh("serverSidekpi", {
                                                "ipmCenterId": ipmCenterId,
                                                "moduleId": 12,
                                                "watchpointId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                            _chart._createChart({
                                                "type": 10,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                            break;
                                        case "alarmLevel":
                                            if(+value){
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=10'+
                                                    '&starttime=' + _starttime +
                                                    '&endtime=' + _endtime +
                                                    '&state=N';
                                            }else {
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&moduleId=10'+
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&state=N';
                                            }
                                            break;
                                        default:
                                            $.ajax({
                                                url:"/plot/getPlotByModuleKpiName.do",
                                                method:"POST",
                                                data:{
                                                    moduleId: 10,
                                                    kpiName: field
                                                },
                                                dataType:"json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success:function (data,textStatus,XMLHttpRequest) {
                                                    var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                        plotId = data.id,
                                                        plotTypeId = data.types[0].id;
                                                    location.href = 'commun_queue.html?' +
                                                        'starttime=' + _starttime +
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=10'+
                                                        '&endtime=' + _endtime +
                                                        '&chartUrl=' + chartUrl + '&' +
                                                        'plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + watchpointId;
                                                },
                                                error:function (XMLHttpRequest,textStatus,errorTHrown) {
                                                    console.error(XMLHttpRequest);
                                                    console.error(textStatus);
                                                    console.error(errorTHrown);
                                                },
                                                complete:function (XMLHttpRequest,textStatus) {}
                                            })
                                    }
                                },
                                onLoadSuccess: function () {
                                    if (onOff) {
                                        if($(".linedraw").length-1){
                                            if ($("#centerTable").bootstrapTable("getData").length) {
                                                _chart.reloadChart(watchpointId, {}, _starttime, _endtime, ipmCenterId);
                                            } else {
                                                defaultCockpit.removelinedraw();
                                            }
                                        }else{
                                            _chart._createChart({
                                                "type": 10,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                        }
                                    } else {
                                        if ($("#centerTable").bootstrapTable("getData").length) {
                                            _chart._createChart({
                                                "type": 10,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }
                                },
                                onLoadError: function () {
                                    defaultCockpit.removelinedraw();
                                }
                            });
                        }, "json");
                        $.post("/client/getClientUserListColumn.do", null, function (data) {
                            var csArr = [];
                            for (var i = 0, len = data.length; i < len; i++) {
                                csArr.push({
                                    id: data[i].id,
                                    field: data[i].columnen,
                                    title: data[i].columnzh,
                                    sortable: true,
                                    visible: !!data[i].checked
                                });
                            }
                            $.ptcsBSTable("userSidekpi", "/commonController/getNpmListRrdData.do", {
                                "ipmCenterId": ipmCenterId,
                                "moduleId": 11,
                                "watchpointId": watchpointId,
                                "starttime": _starttime,
                                "endtime": _endtime
                            }, {
                                pageSize: 10,
                                columns: csArr,
                                ipm_title: "客户端KPI",
                                ipm_shrink: true,
                                ipm_show: false,
                                ipm_column_save: true,
                                rowStyle: function (row, i) {
                                    return {
                                        classes: "cursor"
                                    }
                                },
                                onClickCell: function (field, value, row, $element) {
                                    if (field != "alarmLevel") {
                                        if(field != "name"){
                                            $.ajax({
                                                url:"/plot/getPlotByModuleKpiName.do",
                                                method:"POST",
                                                data:{
                                                    moduleId: 11,
                                                    kpiName: field
                                                },
                                                dataType:"json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success:function (data,textStatus,XMLHttpRequest) {
                                                    var chartUrl = "/client/getClientGraphical.do",
                                                        plotId = data.id,
                                                        plotTypeId = data.types[0].id,
                                                        clientId = row.id;
                                                    location.href = 'commun_queue.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&chartUrl=' + chartUrl + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=11'+
                                                        '&plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + watchpointId +
                                                        '&clientId=' + clientId;
                                                },
                                                error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                    console.error(XMLHttpRequest);
                                                    console.error(textStatus);
                                                    console.error(errorThrown);
                                                },
                                                complete:function (XMLHttpRequest,textStatus) {}
                                            });
                                        }
                                    } else {
                                        if(+value){
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&moduleId=11'+
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&clientId=' + row.id +
                                                '&state=N';
                                        }else {
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&moduleId=11'+
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&clientId=' + row.id + '&state=N';
                                        }
                                    }
                                }
                            });
                        }, "json");
                        $.post("/serverManagement/getServerSideUserListColumn.do", null, function (data) {
                            var csArr = [];
                            for (var i = 0, len = data.length; i < len; i++) {
                                csArr.push({
                                    id: data[i].id,
                                    field: data[i].columnen,
                                    title: data[i].columnzh,
                                    sortable: true,
                                    visible: !!data[i].checked
                                });
                            }
                            $.ptcsBSTable("serverSidekpi", "/commonController/getNpmListRrdData.do", {
                                "ipmCenterId": ipmCenterId,
                                "moduleId": 12,
                                "watchpointId": watchpointId,
                                "starttime": _starttime,
                                "endtime": _endtime
                            }, {
                                pageSize: 10,
                                columns: csArr,
                                ipm_title: "服务端KPI",
                                ipm_shrink: true,
                                ipm_show: false,
                                ipm_column_save: true,
                                rowStyle: function (row, i) {
                                    return {
                                        classes: "cursor"
                                    }
                                },
                                onClickCell: function (field, value, row, $element) {
                                    if (field != "alarmLevel") {
                                        if(field != "name"){
                                            $.ajax({
                                                url:"/plot/getPlotByModuleKpiName.do",
                                                method:"POST",
                                                data:{
                                                    moduleId: 12,
                                                    kpiName: field
                                                },
                                                dataType:"json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success:function (data,textStatus,XMLHttpRequest) {
                                                    var chartUrl = "/serverManagement/getServerSideGraphical.do",
                                                        plotId = data.id,
                                                        plotTypeId = data.types[0].id,
                                                        serverId = row.id;
                                                    location.href = 'commun_queue.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&chartUrl=' + chartUrl + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=12'+
                                                        '&plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + watchpointId +
                                                        '&serverId=' + serverId;
                                                },
                                                error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                    console.error(XMLHttpRequest);
                                                    console.error(textStatus);
                                                    console.error(errorThrown);
                                                },
                                                complete:function (XMLHttpRequest,textStatus) {}
                                            });
                                        }
                                    } else {
                                        if(+value){
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&moduleId=12'+
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&serverId=' + row.id +
                                                '&state=N';
                                        }else {
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&moduleId=12'+
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&serverId=' + row.id + '&state=N';
                                        }
                                    }
                                }
                            });
                        }, "json");
                    }else {
                        $('#centerTable').bootstrapTable('load', []);
                        $('#userSidekpi').bootstrapTable('load', []);
                        $('#serverSidekpi').bootstrapTable('load', []);
                    }
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                _chart.kpiName = [];
                for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                    _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                }
                _chart._kpiSelectM(10);
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(10, watchpointId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("centerTable", {
                    "ipmCenterId": ipmCenterId,
                    "moduleId": 10,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("serverSidekpi", {
                    "moduleId": 12,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("userSidekpi", {
                    "moduleId": 11,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            method:"POST",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data,textStatus,XMLHttpRequest) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         */
        userSide: function (refreTime) {
            var clientId = parseInt($.getUrlParams().dataId),
                ipmCenterId,
                watchpointId = parseInt($.getUrlParams().watpointId),
                _starttime,
                _endtime,
                onOff,
                watIndex,
                httpSeIndex,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            $.ptcsBSTable("centerTable", "/center/getCenterIpInfo.do", null, {
                pageSize: 10,
                columns: [{
                        field: "name",
                        title: "名称",
                        sortable: true
                    },
                    {
                        field: "ip",
                        title: "IP",
                        sortable: true
                    },
                    {
                        field: "port",
                        title: "端口",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                ipm_title: "XPM服务器",
                ipm_show: false,
                ipm_shrink: true,
                ipm_column_save: true,
                rowStyle: function (row, index) {
                    var cla = {};
                    if (index) {
                        cla.classes = "cursor";
                    } else {
                        ipmCenterId = row.id;
                        cla.classes = "custom-row-style cursor";
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                    $(tr).addClass("custom-row-style");
                    ipmCenterId = row.id;
                    _chart.refreNum = 0;
                    $.ajax({
                        url:"/commonController/getNpmListRrdData.do",
                        type:"post",
                        data:{
                            "ipmCenterId": ipmCenterId,
                            "moduleId": 10
                        },
                        dataType:"json",
                        success:function (data) {
                            if(data && data.length){
                                watchpointId = data[0].id;
                                $.ptcsBSTabRefresh("watchPoint", {
                                    "ipmCenterId": ipmCenterId,
                                    "moduleId": 10,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ptcsBSTabRefresh("userSidekpi", {
                                    "moduleId": 11,
                                    "ipmCenterId": ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 11,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (clientData) {
                                        if(clientData && clientData.length){
                                            clientId = clientData[0].id;
                                            $.ptcsBSTabRefresh("serverSidekpi", {
                                                "moduleId": 11,
                                                "ipmCenterId": ipmCenterId,
                                                "watchpointId": watchpointId,
                                                "appBusinessId": clientId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                        }
                                    }
                                });
                            }else {
                                $('#watchPoint').bootstrapTable('load', []);
                                $('#userSidekpi').bootstrapTable('load', []);
                                $('#serverSidekpi').bootstrapTable('load', []);
                            }
                        },
                        error:function () {
                            $('#watchPoint').bootstrapTable('load', []);
                            $('#userSidekpi').bootstrapTable('load', []);
                            $('#serverSidekpi').bootstrapTable('load', []);
                        }
                    });



                    // $.ptcsBSTabRefresh("watchPoint", {
                    //     "ipmCenterId": ipmCenterId,
                    //     "moduleId": 10,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("userSidekpi", {
                    //     "moduleId": 11,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("serverSidekpi", {
                    //     "moduleId": 11,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "appBusinessId": clientId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                },
                onLoadSuccess: function () {
                    $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("watchPoint", "/commonController/getNpmListRrdData.do", {
                            "ipmCenterId": ipmCenterId,
                            "moduleId": 10
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "观察点KPI",
                            ipm_shrink: true,
                            ipm_show: false,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!watIndex) {
                                    watIndex = 0;
                                }
                                if (i == watIndex) {
                                    cla.classes = "custom-row-style cursor";
                                    watchpointId = row.id;
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                watchpointId = row.id;
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 11,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (clientData) {
                                        if(clientData && clientData.length){
                                            clientId = clientData[0].id;
                                            switch (field) {
                                                case "id":
                                                case "name":
                                                    $("#watchPoint > tbody > .custom-row-style").removeClass("custom-row-style");
                                                    $($element).parent().addClass("custom-row-style");
                                                    watIndex = $($element).parent().attr("data-index");
                                                    _chart.refreNum = 0;
                                                    $.ptcsBSTabRefresh("userSidekpi", {
                                                        "moduleId": 11,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    $.ptcsBSTabRefresh("serverSidekpi", {
                                                        "moduleId": 11,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "appBusinessId": clientId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    _chart._createChart({
                                                        "type": 11,
                                                        "ipmCenterId": ipmCenterId,
                                                        "selectDataId": clientId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    }, {
                                                        "watchpointId": watchpointId,
                                                        "clientId": clientId
                                                    });
                                                    break;
                                                case "alarmLevel":
                                                    if (_starttime && _endtime) {
                                                        location.href = 'alarmSetting.html?' +
                                                            'watchpointId=' + watchpointId +
                                                            '&ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=10'+
                                                            '&starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&state=N';
                                                    } else {
                                                        var sT = $.getDefaultEndtime() - 600;
                                                        if ($.getDefaultEndtime() && sT) {
                                                            if (value == 0) {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'watchpointId=' + watchpointId +
                                                                    '&moduleId=10'+
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&state=N';
                                                            } else {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'watchpointId=' + watchpointId +
                                                                    '&moduleId=10'+
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&starttime=' + sT +
                                                                    '&endtime=' + $.getDefaultEndtime() +
                                                                    '&state=N';
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    $.ajax({
                                                        url: "/plot/getPlotByModuleKpiName.do",
                                                        type: "post",
                                                        data: {
                                                            moduleId: 10,
                                                            kpiName: field
                                                        },
                                                        dataType: "json",
                                                        success: function (data) {
                                                            var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                                plotId = data.id,
                                                                plotTypeId = data.types[0].id,
                                                                _watchpointId = row.id;
                                                            if (_starttime && _endtime) {
                                                                location.href = 'commun_queue.html?' +
                                                                    'starttime=' + _starttime +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&endtime=' + _endtime +
                                                                    '&chartUrl=' + chartUrl + '&' +
                                                                    'plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            } else {
                                                                location.href = 'commun_queue.html?' +
                                                                    'chartUrl=' + chartUrl + '&' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                });
                            },
                            onLoadSuccess: function () {
                                if (onOff) {
                                    if($(".linedraw").length-1){
                                        if ($("#watchPoint").bootstrapTable("getData").length) {
                                            _chart.reloadChart(watchpointId, {
                                                "clientId": clientId
                                            }, _starttime, _endtime, ipmCenterId);
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }else {
                                        _chart._createChart({
                                            "type": 11,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": clientId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "clientId": clientId
                                        });
                                    }
                                } else {
                                    if ($("#watchPoint").bootstrapTable("getData").length ||
                                        $("#serverSidekpi").bootstrapTable("getData").length) {
                                        _chart._createChart({
                                            "type": 11,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": clientId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "clientId": clientId
                                        });
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }
                            },
                            onLoadError: function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/client/getClientUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("userSidekpi", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 11,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "客户端KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!httpSeIndex) {
                                    httpSeIndex = 0;
                                }
                                if (i == httpSeIndex) {
                                    cla.classes = "custom-row-style cursor";
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                clientId = row.id;
                                switch (field) {
                                    case "id":
                                    case "name":
                                        $("#userSidekpi > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        httpSeIndex = $($element).parent().attr("data-index");
                                        _chart.refreNum = 0;
                                        $.ptcsBSTabRefresh("serverSidekpi", {
                                            "moduleId": 11,
                                            "watchpointId": watchpointId,
                                            "appBusinessId": clientId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        });
                                        _chart._createChart({
                                            "type": 11,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": clientId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "clientId": clientId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if (_starttime && _endtime) {
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&moduleId=11'+
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&clientId=' + clientId +
                                                '&state=N';
                                        } else {
                                            var sT = $.getDefaultEndtime() - 600;
                                            if ($.getDefaultEndtime() && sT) {
                                                if (value == 0) {
                                                    location.href = 'alarmSetting.html?' +
                                                        'watchpointId=' + watchpointId +
                                                        '&moduleId=11'+
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&clientId=' + clientId + '&state=N';
                                                } else {
                                                    location.href = 'alarmSetting.html?' +
                                                        'watchpointId=' + watchpointId +
                                                        '&moduleId=11'+
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&starttime=' + sT +
                                                        '&endtime=' + $.getDefaultEndtime() +
                                                        '&clientId=' + clientId +
                                                        '&state=N';
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            type: "post",
                                            data: {
                                                moduleId: 11,
                                                kpiName: field
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                var chartUrl = "/client/getClientGraphical.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    _watchpointId = watchpointId,
                                                    _ipmCenterId = ipmCenterId,
                                                    clientId = row.id;
                                                if (_starttime && _endtime) {
                                                    location.href = 'commun_queue.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&chartUrl=' + chartUrl + '&' +
                                                        'plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'ipmCenterId=' + _ipmCenterId +
                                                        '&moduleId=11'+
                                                        '&watchpointId=' + _watchpointId +
                                                        '&clientId=' + clientId;
                                                } else {
                                                    location.href = 'commun_queue.html?' +
                                                        'chartUrl=' + chartUrl + '&' +
                                                        'plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'ipmCenterId=' + _ipmCenterId +
                                                        '&moduleId=11'+
                                                        '&watchpointId=' + _watchpointId +
                                                        '&clientId=' + clientId;
                                                }
                                            }
                                        })
                                }
                            },
                            onLoadSuccess:function (data) {
                                if(!data.length){
                                    defaultCockpit.removelinedraw();
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/serverManagement/getServerSideUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("serverSidekpi", "/commonController/getNpmListRrdDataBySubPath.do", {
                            "moduleId": 11,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "appBusinessId": clientId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "服务端KPI",
                            ipm_shrink: true,
                            ipm_show: false,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                return {
                                    classes: "cursor"
                                }
                            }
                        });
                    }, "json");
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if(  $("#userSidekpi").bootstrapTable("getData").length){
                    _chart._kpiSelectM(11);
                }else {
                    jeBox.alert("请先添加客户端业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(11, clientId, {
                    "watchpointId": watchpointId,
                    "ipmCenterId": ipmCenterId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer3);
                    clearInterval(timer2);
                    // _chart._createChart({
                    //     "type": 11,
                    //     "ipmCenterId": ipmCenterId,
                    //     "selectDataId": clientId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // }, {
                    //     "watchpointId": watchpointId, //若页面中有图形用户再次刷新则会出现此自动赋值为默认值
                    //     "clientId": clientId
                    // });
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("watchPoint", {
                    "moduleId": 10,
                    "ipmCenterId": ipmCenterId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("userSidekpi", {
                    "moduleId": 11,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("serverSidekpi", {
                    "moduleId": 11,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "appBusinessId": clientId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            timeBackText();
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         */
        serverSide: function (refreTime) {
            var serverId = parseInt($.getUrlParams().dataId),
                ipmCenterId,
                watchpointId = parseInt($.getUrlParams().watpointId),
                _starttime,
                _endtime,
                onOff,
                watIndex,
                httpSeIndex,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            $.ptcsBSTable("centerTable", "/center/getCenterIpInfo.do", null, {
                pageSize: 10,
                columns: [{
                        field: "name",
                        title: "名称",
                        sortable: true
                    },
                    {
                        field: "ip",
                        title: "IP",
                        sortable: true
                    },
                    {
                        field: "port",
                        title: "端口",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                ipm_title: "XPM服务器",
                ipm_shrink: true,
                ipm_show: false,
                ipm_column_save: true,
                rowStyle: function (row, index) {
                    var cla = {};
                    if (index) {
                        cla.classes = "cursor";
                    } else {
                        ipmCenterId = row.id;
                        cla.classes = "custom-row-style cursor";
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                    $(tr).addClass("custom-row-style");
                    ipmCenterId = row.id;
                    _chart.refreNum = 0;
                    $.ajax({
                        url:"/commonController/getNpmListRrdData.do",
                        type:"post",
                        data:{
                            "ipmCenterId": ipmCenterId,
                            "moduleId": 10
                        },
                        dataType:"json",
                        success:function (data) {
                            if(data && data.length){
                                watchpointId = data[0].id;
                                $.ptcsBSTabRefresh("watchPoint", {
                                    "ipmCenterId": ipmCenterId,
                                    "moduleId": 10,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ptcsBSTabRefresh("serverSidekpi", {
                                    "moduleId": 12,
                                    "ipmCenterId": ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 12,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (clientData) {
                                        if(clientData && clientData.length){
                                            serverId = clientData[0].id;
                                            $.ptcsBSTabRefresh("userSidekpi", {
                                                "moduleId": 12,
                                                "ipmCenterId": ipmCenterId,
                                                "watchpointId": watchpointId,
                                                "appBusinessId": serverId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                        }
                                    }
                                });
                            }else {
                                $('#watchPoint').bootstrapTable('load', []);
                                $('#userSidekpi').bootstrapTable('load', []);
                                $('#serverSidekpi').bootstrapTable('load', []);
                            }
                        },
                        error:function () {
                            $('#watchPoint').bootstrapTable('load', []);
                            $('#userSidekpi').bootstrapTable('load', []);
                            $('#serverSidekpi').bootstrapTable('load', []);
                        }
                    });





                    // $.ptcsBSTabRefresh("watchPoint", {
                    //     "ipmCenterId": ipmCenterId,
                    //     "moduleId": 10,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("serverSidekpi", {
                    //     "moduleId": 12,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh("userSidekpi", {
                    //     "moduleId": 12,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "appBusinessId": serverId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                },
                onLoadSuccess: function () {
                    $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("watchPoint", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "观察点KPI",
                            ipm_shrink: true,
                            ipm_show: false,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!watIndex) {
                                    watIndex = 0;
                                }
                                if (i == watIndex) {
                                    cla.classes = "custom-row-style cursor";
                                    watchpointId = row.id;
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                watchpointId = row.id;
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": 12,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (clientData) {
                                        if(clientData && clientData.length){
                                            serverId = clientData[0].id;
                                            switch (field) {
                                                case "id":
                                                case "name":
                                                    $("#watchPoint > tbody > .custom-row-style").removeClass("custom-row-style");
                                                    $($element).parent().addClass("custom-row-style");
                                                    watIndex = $($element).parent().attr("data-index");
                                                    _chart.refreNum = 0;
                                                    $.ptcsBSTabRefresh("serverSidekpi", {
                                                        "moduleId": 12,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    $.ptcsBSTabRefresh("userSidekpi", {
                                                        "moduleId": 12,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "appBusinessId": serverId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    _chart._createChart({
                                                        "type": 12,
                                                        "ipmCenterId": ipmCenterId,
                                                        "selectDataId": serverId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    }, {
                                                        "watchpointId": watchpointId,
                                                        "serverId": serverId
                                                    });
                                                    break;
                                                case "alarmLevel":
                                                    if (_starttime && _endtime) {
                                                        location.href = 'alarmSetting.html?' +
                                                            'watchpointId=' + watchpointId +
                                                            '&ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=10'+
                                                            '&starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&state=N';
                                                    } else {
                                                        var sT = $.getDefaultEndtime() - 600;
                                                        if ($.getDefaultEndtime() && sT) {
                                                            if (value == 0) {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'watchpointId=' + watchpointId +
                                                                    '&moduleId=10'+
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&state=N';
                                                            } else {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'watchpointId=' + watchpointId +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&starttime=' + sT +
                                                                    '&endtime=' + $.getDefaultEndtime() +
                                                                    '&state=N';
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    $.ajax({
                                                        url: "/plot/getPlotByModuleKpiName.do",
                                                        type: "post",
                                                        data: {
                                                            moduleId: 10,
                                                            kpiName: field
                                                        },
                                                        dataType: "json",
                                                        success: function (data) {
                                                            var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                                plotId = data.id,
                                                                plotTypeId = data.types[0].id,
                                                                _watchpointId = row.id;
                                                            if (_starttime && _endtime) {
                                                                location.href = 'commun_queue.html?' +
                                                                    'starttime=' + _starttime +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&endtime=' + _endtime +
                                                                    '&chartUrl=' + chartUrl + '&' +
                                                                    'plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            } else {
                                                                location.href = 'commun_queue.html?' +
                                                                    'chartUrl=' + chartUrl + '&' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                });
                            },
                            onLoadSuccess: function () {
                                if(onOff){
                                    if($(".linedraw").length-1){
                                        if ($("#watchPoint").bootstrapTable("getData").length) {
                                            _chart.reloadChart(watchpointId, {
                                                "serverId": serverId
                                            }, _starttime, _endtime, ipmCenterId);
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }else {
                                        _chart._createChart({
                                            "type": 12,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": serverId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "serverId": serverId
                                        });
                                    }
                                }else {
                                    if ($("#watchPoint").bootstrapTable("getData").length ||
                                        $("#userSidekpi").bootstrapTable("getData").length) {
                                        _chart._createChart({
                                            "type": 12,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": serverId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "serverId": serverId
                                        });
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }
                            },
                            onLoadError:function(){
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/serverManagement/getServerSideUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("serverSidekpi", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 12,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "服务端KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!httpSeIndex) {
                                    httpSeIndex = 0;
                                }
                                if (i == httpSeIndex) {
                                    cla.classes = "custom-row-style cursor";
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                serverId = row.id;
                                switch (field) {
                                    case "id":
                                    case "name":
                                        $("#serverSidekpi > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        httpSeIndex = $($element).parent().attr("data-index");
                                        _chart.refreNum = 0;
                                        $.ptcsBSTabRefresh("userSidekpi", {
                                            "moduleId": 12,
                                            "ipmCenterId": ipmCenterId,
                                            "watchpointId": watchpointId,
                                            "appBusinessId": serverId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        });
                                        _chart._createChart({
                                            "type": 12,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": serverId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "serverId": serverId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if (_starttime && _endtime) {
                                            location.href = 'alarmSetting.html?' +
                                                'ipmCenterId=' + ipmCenterId +
                                                '&moduleId=12'+
                                                '&watchpointId=' + watchpointId +
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&serverId=' + serverId +
                                                '&state=N';
                                        } else {
                                            var sT = $.getDefaultEndtime() - 600;
                                            if ($.getDefaultEndtime() && sT) {
                                                if (value == 0) {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=12'+
                                                        '&watchpointId=' + watchpointId +
                                                        '&serverId=' + serverId + '&state=N';
                                                } else {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=12'+
                                                        '&watchpointId=' + watchpointId +
                                                        '&starttime=' + sT +
                                                        '&endtime=' + $.getDefaultEndtime() +
                                                        '&serverId=' + serverId +
                                                        '&state=N';
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            type: "post",
                                            data: {
                                                moduleId: 12,
                                                kpiName: field
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                var chartUrl = "/serverManagement/getServerSideGraphical.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    _watchpointId = watchpointId,
                                                    serverId = row.id;
                                                if (_starttime && _endtime) {
                                                    location.href = 'commun_queue.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&chartUrl=' + chartUrl + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=12'+
                                                        '&plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&serverId=' + serverId;
                                                } else {
                                                    location.href = 'commun_queue.html?' +
                                                        'chartUrl=' + chartUrl + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=12'+
                                                        '&plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&serverId=' + serverId;
                                                }
                                            }
                                        })
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/client/getClientUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("userSidekpi", "/commonController/getNpmListRrdDataBySubPath.do", {
                            "moduleId": 12,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "appBusinessId": serverId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: "客户端KPI",
                            ipm_shrink: true,
                            ipm_show: false,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                return {
                                    classes: "cursor"
                                }
                            }
                        });
                    }, "json");
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if($("#serverSidekpi").bootstrapTable("getData").length){
                    _chart._kpiSelectM(12);
                }else {
                    jeBox.alert("请先添加服务端业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(12, serverId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("watchPoint", {
                    "moduleId": 10,
                    "ipmCenterId": ipmCenterId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("serverSidekpi", {
                    "moduleId": 12,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("userSidekpi", {
                    "moduleId": 12,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "appBusinessId": serverId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            timeBackText();
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param tableId
         * @param moduleId
         * @param ipm_title
         * @param refreTime
         */
        baowenJy:function (tableId, moduleId, ipm_title,refreTime) {
            var busiId = parseInt($.getUrlParams().dataId),
                ipmCenterId,
                watchpointId = parseInt($.getUrlParams().watpointId),
                watIndex,
                httpSeIndex,
                onOff,
                _starttime,
                _endtime,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            $.ptcsBSTable("centerTable", "/center/getCenterIpInfo.do", null, {
                pageSize: 10,
                columns: [{
                    field: "name",
                    title: "名称",
                    sortable: true
                },
                    {
                        field: "ip",
                        title: "IP",
                        sortable: true
                    },
                    {
                        field: "port",
                        title: "端口",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                ipm_title: "XPM服务器",
                ipm_shrink: true,
                ipm_show: false,
                ipm_column_save: true,
                rowStyle: function (row, index) {
                    var cla = {};
                    if (index) {
                        cla.classes = "cursor";
                    } else {
                        ipmCenterId = row.id;
                        cla.classes = "custom-row-style cursor";
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                    $(tr).addClass("custom-row-style");
                    ipmCenterId = row.id;
                    _chart.refreNum = 0;
                    $.ajax({
                        url:"/commonController/getNpmListRrdData.do",
                        type:"post",
                        data:{
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        },
                        dataType:"json",
                        success:function (data) {
                            if(data && data.length){
                                watchpointId = data[0].id;
                                $.ptcsBSTabRefresh("watchPoint", {
                                    "ipmCenterId": ipmCenterId,
                                    "moduleId": 10,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                                $.ptcsBSTabRefresh(tableId, {
                                    "moduleId": moduleId,
                                    "ipmCenterId": ipmCenterId,
                                    "watchpointId": watchpointId,
                                    "starttime": _starttime,
                                    "endtime": _endtime
                                });
                            }else {
                                $('#watchPoint').bootstrapTable('load', []);
                                $('#'+tableId).bootstrapTable('load', []);
                            }
                        },
                        error:function () {
                            $('#watchPoint').bootstrapTable('load', []);
                            $('#'+tableId).bootstrapTable('load', []);
                        }
                    });
                    // $.ptcsBSTabRefresh("watchPoint", {
                    //     "ipmCenterId": ipmCenterId,
                    //     "moduleId": 10,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                    // $.ptcsBSTabRefresh(tableId, {
                    //     "moduleId": moduleId,
                    //     "ipmCenterId": ipmCenterId,
                    //     "watchpointId": watchpointId,
                    //     "starttime": _starttime,
                    //     "endtime": _endtime
                    // });
                },
                onLoadSuccess: function () {
                    $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("watchPoint", "/commonController/getNpmListRrdData.do", {
                            "moduleId": 10,
                            "ipmCenterId": ipmCenterId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_show: false,
                            ipm_title: "观察点KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!watIndex) {
                                    watIndex = 0;
                                }
                                if (i == watIndex) {
                                    cla.classes = "custom-row-style cursor";
                                    watchpointId = row.id;
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                watchpointId = row.id;
                                $.ajax({
                                    url:"/commonController/getNpmListRrdData.do",
                                    type:"post",
                                    data:{
                                        "moduleId": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId
                                    },
                                    dataType:"json",
                                    success:function (data) {
                                        if(data && data.length){
                                            busiId = data[0].id;
                                            switch (field) {
                                                case "id":
                                                case "name":
                                                    $("#watchPoint > tbody > .custom-row-style").removeClass("custom-row-style");
                                                    $($element).parent().addClass("custom-row-style");
                                                    watIndex = $($element).parent().attr("data-index");
                                                    _chart.refreNum = 0;
                                                    $.ptcsBSTabRefresh(tableId, {
                                                        "moduleId": moduleId,
                                                        "ipmCenterId": ipmCenterId,
                                                        "watchpointId": watchpointId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    });
                                                    _chart._createChart({
                                                        "type": moduleId,
                                                        "ipmCenterId": ipmCenterId,
                                                        "selectDataId": busiId,
                                                        "starttime": _starttime,
                                                        "endtime": _endtime
                                                    }, {
                                                        "watchpointId": watchpointId,
                                                        "busiId": busiId
                                                    });
                                                    break;
                                                case "alarmLevel":
                                                    if (_starttime && _endtime) {
                                                        location.href = 'alarmSetting.html?' +
                                                            'ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=10'+
                                                            '&busiId='+busiId+
                                                            '&watchpointId=' + watchpointId +
                                                            '&starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&state=N';
                                                    } else {
                                                        var sT = $.getDefaultEndtime() - 600;
                                                        if ($.getDefaultEndtime() && sT) {
                                                            if (value == 0) {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&state=N';
                                                            } else {
                                                                location.href = 'alarmSetting.html?' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&watchpointId=' + watchpointId +
                                                                    '&starttime=' + sT +
                                                                    '&endtime=' + $.getDefaultEndtime() +
                                                                    '&state=N';
                                                            }
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    $.ajax({
                                                        url: "/plot/getPlotByModuleKpiName.do",
                                                        type: "post",
                                                        data: {
                                                            moduleId: 10,
                                                            kpiName: field
                                                        },
                                                        dataType: "json",
                                                        success: function (data) {
                                                            var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                                plotId = data.id,
                                                                plotTypeId = data.types[0].id,
                                                                _watchpointId = row.id;
                                                            if (_starttime && _endtime) {
                                                                location.href = 'commun_queue.html?' +
                                                                    'starttime=' + _starttime +
                                                                    '&ipmCenterId=' + ipmCenterId +
                                                                    '&endtime=' + _endtime +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&chartUrl=' + chartUrl + '&' +
                                                                    'plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            } else {
                                                                location.href = 'commun_queue.html?' +
                                                                    'chartUrl=' + chartUrl + '&' +
                                                                    'ipmCenterId=' + ipmCenterId +
                                                                    '&moduleId=10'+
                                                                    '&busiId='+busiId+
                                                                    '&plotId=' + plotId + '&' +
                                                                    'plotTypeId=' + plotTypeId + '&' +
                                                                    'watchpointId=' + _watchpointId;
                                                            }
                                                        }
                                                    })
                                            }
                                        }
                                    }
                                });
                            },
                            onLoadSuccess: function () {
                                if(onOff){
                                    if($(".linedraw").length-1){
                                        if ($("#watchPoint").bootstrapTable("getData").length) {
                                            _chart.reloadChart(watchpointId, {
                                                "busiId": busiId
                                            }, _starttime, _endtime, ipmCenterId);
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                    }else {
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    }
                                }else {
                                    if ($("#watchPoint").bootstrapTable("getData").length ||
                                        $("#"+tableId).bootstrapTable("getData").length) {
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                    $.post("/appController/getAppListColumn.do", {
                        "moduleId": moduleId,
                        "typeId": moduleId
                    }, function (data) {
                        var csArr = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            csArr.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable(tableId, "/commonController/getNpmListRrdData.do", {
                            "moduleId": moduleId,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId
                        }, {
                            pageSize: 10,
                            columns: csArr,
                            ipm_title: ipm_title + "KPI",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (!httpSeIndex) {
                                    httpSeIndex = 0;
                                }
                                if (i == httpSeIndex) {
                                    cla.classes = "custom-row-style cursor";
                                } else {
                                    cla.classes = "cursor";
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                busiId = row.id;
                                switch (field) {
                                    case "id":
                                    case "name":
                                        $("#" + tableId + " > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        httpSeIndex = $($element).parent().attr("data-index");
                                        _chart.refreNum = 0;
                                        _chart._createChart({
                                            "type": moduleId,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if (_starttime && _endtime) {
                                            location.href = 'alarmSetting.html?' +
                                                'ipmCenterId=' + ipmCenterId +
                                                '&moduleId='+ moduleId +
                                                '&watchpointId=' + watchpointId +
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&busiId=' + busiId +
                                                '&state=N';
                                        } else {
                                            var sT = $.getDefaultEndtime() - 600;
                                            if ($.getDefaultEndtime() && sT) {
                                                if (value == 0) {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId='+ moduleId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&busiId=' + busiId + '&state=N';
                                                } else {
                                                    location.href = 'alarmSetting.html?' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId='+ moduleId +
                                                        '&watchpointId=' + watchpointId +
                                                        '&starttime=' + sT +
                                                        '&endtime=' + $.getDefaultEndtime() +
                                                        '&busiId=' + busiId +
                                                        '&state=N';
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            type: "post",
                                            data: {
                                                moduleId: moduleId,
                                                kpiName: field
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                var chartUrl = "/appController/getPlotData.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    _watchpointId = watchpointId,
                                                    busiId = row.id;
                                                if (_starttime && _endtime) {
                                                    location.href = 'bssSession.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&plotId=' + plotId + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=' + moduleId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&busiId=' + busiId;
                                                } else {
                                                    location.href = 'bssSession.html?' +
                                                        'plotId=' + plotId + '&' +
                                                        'ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=' + moduleId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + _watchpointId +
                                                        '&busiId=' + busiId;
                                                }
                                            }
                                        })
                                }
                            }
                        });
                    }, "json");
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if($("#"+tableId).bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(moduleId);
                }else {
                    jeBox.alert("请先添加"+ipm_title+"业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(moduleId, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer3);
                    clearInterval(timer2);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("watchPoint", {
                    "moduleId": 10,
                    "ipmCenterId": ipmCenterId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh(tableId, {
                    "moduleId": moduleId,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime();
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            timeBackText();
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         *
         */
        systemCapital:function (refreTime) {
            var ipmCenterId = 1,
                moduleId = 0,
                busiId = 0,
                onOff,
                _starttime = $.getUrlParams().starttime,
                _endtime = $.getUrlParams().endtime,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime),
                initFun = function (){
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    if ((new Date().getFullYear() - $.myTime.UnixToDate(_starttime).split("-")[0]) ||
                        (new Date().getFullYear() - $.myTime.UnixToDate(_endtime).split("-")[0])) {
                        $(".timeBackText").text($.myTime.UnixToDate(_starttime) + " ~ " + $.myTime.UnixToDate(_endtime))
                    } else {
                        var index = $.myTime.UnixToDate(_starttime).split("-")[0].length + 1;
                        $(".timeBackText").text($.myTime.UnixToDate(_starttime).slice(index) + " ~ " + $.myTime.UnixToDate(_endtime).slice(index))
                    }
                };
            initFun();
            _chart._createChart({
                "type": moduleId,
                "ipmCenterId": ipmCenterId,
                "selectDataId":busiId,
                "starttime": _starttime,
                "endtime": _endtime,
                "dblclick":true
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                _chart.kpiName = [];
                for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                    _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                }
                _chart._kpiSelectM(moduleId);
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(moduleId, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "starttime": _starttime,
                    "endtime": _endtime,
                    "dblclick":true
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime) {
                onOff = true;
                _chart.reloadChart(1,
                    {
                    "busiId": busiId
                    },
                    starttime?starttime:_starttime,
                    endtime?endtime:_endtime,
                    ipmCenterId
                );
            }
            //为右上角赋值时间
            function timeBackText() {
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    clearInterval(timer2);
                    clearInterval(timer3);
                    timer2 = setInterval(timeBackText, timeVal);
                    timer3 = setInterval(refreshTableData, timeVal);
                    $('a[data-drawer="refresh"]').trigger("click");
                }else {
                    location.reload();
                }
            })
        },
        /**
         *
         * @param tableId
         * @param moduleId
         * @param ipm_title
         * @param refreTime
         */
        pubTabConfig:function (tableId,moduleId,ipm_title,refreTime) {
            var ipmCenterId = parseInt($.getUrlParams().ipmCenterId),
                watchpointId = parseInt($.getUrlParams().watchpointId),
                clientId = tableId == "userSidekpi"?parseInt($.getUrlParams().databsId):undefined,
                serverId = tableId == "serverSidekpi"?parseInt($.getUrlParams().databsId):undefined,
                _starttime,
                _endtime,
                onOff,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("centerTable", {
                    "ipmCenterId": ipmCenterId,
                    "moduleId": 10,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("serverSidekpi", {
                    "moduleId": tableId=="centerTable"?12:moduleId,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "appBusinessId": clientId,//观察点 服务端没有
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("userSidekpi", {
                    "moduleId": tableId=="centerTable"?11:moduleId,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "appBusinessId": serverId,//观察点 用户端 没有
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            timeBackText();
            if(watchpointId && ipmCenterId){
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "/center/getRemoteWatchpointKpiList.do", {
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: tableId == "centerTable"?true:false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if(watchpointId != undefined || ipmCenterId != undefined){
                                if(row.id == watchpointId && row.ipmCenterId == ipmCenterId){
                                    cla.classes = "custom-row-style cursor";
                                }else {
                                    cla.classes = "cursor";
                                }
                            }else {
                                if (i) {
                                    cla.classes = "cursor";
                                } else {
                                    watchpointId = row.id;
                                    ipmCenterId = row.ipmCenterId;
                                    cla.classes = "custom-row-style cursor";
                                }
                            }
                            return cla;
                        },
                        onClickCell: function (field, value, row, $element) {
                            $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                            $($element).parent().addClass("custom-row-style");
                            ipmCenterId = row.ipmCenterId;
                            watchpointId = row.id;
                            _chart.refreNum = 0;
                            switch (field){
                                case "ipmCenterName":
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    if(field == "ipmCenterName" && ipmCenterId != 1){
                                        window.open(window.location.protocol+"//"+row.ip+":"+row.port+"/login.html");
                                    }
                                    $.ptcsBSTabRefresh("userSidekpi", {
                                        "ipmCenterId": ipmCenterId,
                                        "moduleId":tableId=="centerTable"?11:moduleId,
                                        "watchpointId": watchpointId,
                                        "appBusinessId": tableId=="serverSidekpi"?serverId:undefined,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    });
                                    $.ptcsBSTabRefresh("serverSidekpi", {
                                        "ipmCenterId": ipmCenterId,
                                        "moduleId": tableId=="centerTable"?12:moduleId,
                                        "watchpointId": watchpointId,
                                        "appBusinessId": tableId=="userSidekpi"?clientId:undefined,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    });
                                    switch (tableId){
                                        case "centerTable":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                            break;
                                        case "userSidekpi":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": clientId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            }, {
                                                "watchpointId": watchpointId,
                                                "clientId": clientId
                                            });
                                            break;
                                        case "serverSidekpi":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": serverId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            }, {
                                                "watchpointId": watchpointId,
                                                "serverId": serverId
                                            });
                                            break;
                                    }
                                    break;
                                case "alarmLevel":
                                    if(+value){
                                        location.href = 'alarmSetting.html?' +
                                            'watchpointId=' + watchpointId +
                                            '&ipmCenterId=' + ipmCenterId +
                                            '&moduleId=10'+
                                            '&starttime=' + _starttime +
                                            '&endtime=' + _endtime +
                                            '&state=N';
                                    }else {
                                        location.href = 'alarmSetting.html?' +
                                            'watchpointId=' + watchpointId +
                                            '&moduleId=10'+
                                            '&ipmCenterId=' + ipmCenterId +
                                            '&state=N';
                                    }
                                    break;
                                default:
                                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                        $.ajax({
                                            url:"/plot/getPlotByModuleKpiName.do",
                                            method:"POST",
                                            data:{
                                                moduleId: 10,
                                                kpiName: field
                                            },
                                            dataType:"json",
                                            beforeSend:function (XMLHttpRequest) {},
                                            success:function (data,textStatus,XMLHttpRequest) {
                                                var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id;
                                                location.href = 'commun_queue.html?' +
                                                    'starttime=' + _starttime +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=10'+
                                                    '&endtime=' + _endtime +
                                                    '&chartUrl=' + chartUrl + '&' +
                                                    'plotId=' + plotId + '&' +
                                                    'plotTypeId=' + plotTypeId + '&' +
                                                    'watchpointId=' + watchpointId;
                                            },
                                            error:function (XMLHttpRequest,textStatus,errorTHrown) {
                                                console.error(XMLHttpRequest);
                                                console.error(textStatus);
                                                console.error(errorTHrown);
                                            },
                                            complete:function (XMLHttpRequest,textStatus) {}
                                        })
                                    }
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (field){
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    break;
                                case "ipmCenterName":
                                    if(row.ipmCenterId != 1){
                                        $($element).addClass("text-underline");
                                    }
                                    break;
                                default:
                                    $($element).addClass("text-underline");
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        },
                        onLoadSuccess: function () {
                            if (onOff) {
                                if($(".linedraw").length-1){
                                    if ($("#centerTable").bootstrapTable("getData").length) {
                                        switch (tableId){
                                            case "centerTable":
                                                _chart.reloadChart(watchpointId, {}, _starttime, _endtime, ipmCenterId);
                                                break;
                                            case "userSidekpi":
                                                _chart.reloadChart(watchpointId, {
                                                    "clientId": clientId
                                                }, _starttime, _endtime, ipmCenterId);
                                                break;
                                            case "serverSidekpi":
                                                _chart.reloadChart(watchpointId, {
                                                    "serverId": serverId
                                                }, _starttime, _endtime, ipmCenterId);
                                                break;
                                        }
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }else {
                                    switch (tableId){
                                        case "centerTable":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                            break;
                                        case "userSidekpi":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": clientId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            }, {
                                                "watchpointId": watchpointId,
                                                "clientId": clientId
                                            });
                                            break;
                                        case "serverSidekpi":
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": serverId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            }, {
                                                "watchpointId": watchpointId,
                                                "serverId": serverId
                                            });
                                            break;
                                    }
                                }
                            } else {
                                switch (tableId){
                                    case "centerTable":
                                        if ($("#centerTable").bootstrapTable("getData").length) {
                                            _chart._createChart({
                                                "type": moduleId,
                                                "ipmCenterId": ipmCenterId,
                                                "selectDataId": watchpointId,
                                                "starttime": _starttime,
                                                "endtime": _endtime
                                            });
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                        break;
                                    case "userSidekpi":
                                        if ($("#centerTable").bootstrapTable("getData").length ||
                                            $("#userSidekpi").bootstrapTable("getData").length) {
                                            if(clientId){
                                                _chart._createChart({
                                                    "type": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "selectDataId": clientId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                }, {
                                                    "watchpointId": watchpointId,
                                                    "clientId": clientId
                                                });
                                            }
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                        break;
                                    case "serverSidekpi":
                                        if ($("#centerTable").bootstrapTable("getData").length ||
                                            $("#serverSidekpi").bootstrapTable("getData").length) {
                                            if(serverId){
                                                _chart._createChart({
                                                    "type": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "selectDataId": serverId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                }, {
                                                    "watchpointId": watchpointId,
                                                    "serverId": serverId
                                                });
                                            }
                                        } else {
                                            defaultCockpit.removelinedraw();
                                        }
                                        break;
                                }
                            }
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/client/getClientUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("userSidekpi", tableId=="centerTable" || tableId=="userSidekpi"?
                        "/commonController/getNpmListRrdData.do":
                        "/commonController/getNpmListRrdDataBySubPath.do", {
                        "ipmCenterId": ipmCenterId,
                        "moduleId": tableId=="centerTable" || tableId=="userSidekpi"?11:12,
                        "appBusinessId": tableId=="serverSidekpi"?serverId:undefined,
                        "watchpointId": watchpointId,
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "客户端KPI",
                        ipm_shrink: true,
                        ipm_show: tableId == "userSidekpi"?true:false,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            if(tableId == "userSidekpi"){
                                var cla = {};
                                if(clientId != undefined && clientId != "0"){
                                    if(row.id == clientId){
                                        cla.classes = "custom-row-style cursor";
                                    }else {
                                        cla.classes = "cursor";
                                    }
                                }else {
                                    if (i) {
                                        cla.classes = "cursor";
                                    } else {
                                        clientId = row.id;
                                        cla.classes = "custom-row-style cursor";
                                    }
                                }
                                return cla;
                            }else {
                                return {
                                    classes: "cursor"
                                }
                            }
                        },
                        onClickCell: function (field, value, row, $element) {
                            switch (tableId){
                                case "centerTable":
                                case "userSidekpi":
                                    if(tableId == "userSidekpi"){
                                        clientId = row.id;
                                        _chart.refreNum = 0;
                                        $("#userSidekpi > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                    }
                                    switch (field){
                                        case "id":
                                        case "name":
                                            if(tableId == "userSidekpi"){
                                                $.ptcsBSTabRefresh("serverSidekpi", {
                                                    "moduleId": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "watchpointId": watchpointId,
                                                    "appBusinessId": clientId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                });
                                                _chart._createChart({
                                                    "type": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "selectDataId": clientId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                }, {
                                                    "watchpointId": watchpointId,
                                                    "clientId": clientId
                                                });
                                            }
                                            break;
                                        case "alarmLevel":
                                            if(+value){
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=11'+
                                                    '&starttime=' + _starttime +
                                                    '&endtime=' + _endtime +
                                                    '&clientId=' + row.id +
                                                    '&state=N';
                                            }else {
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&moduleId=11'+
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&clientId=' + row.id +
                                                    '&state=N';
                                            }
                                            break;
                                        default:
                                            if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                                $.ajax({
                                                    url:"/plot/getPlotByModuleKpiName.do",
                                                    method:"POST",
                                                    data:{
                                                        moduleId: 11,
                                                        kpiName: field
                                                    },
                                                    dataType:"json",
                                                    beforeSend:function (XMLHttpRequest) {},
                                                    success:function (data,textStatus,XMLHttpRequest) {
                                                        var chartUrl = "/client/getClientGraphical.do",
                                                            plotId = data.id,
                                                            plotTypeId = data.types[0].id;
                                                        location.href = 'commun_queue.html?' +
                                                            'starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&chartUrl=' + chartUrl + '&' +
                                                            'ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=11'+
                                                            '&plotId=' + plotId + '&' +
                                                            'plotTypeId=' + plotTypeId + '&' +
                                                            'watchpointId=' + watchpointId +
                                                            '&clientId=' + row.id;
                                                    },
                                                    error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                        console.error(XMLHttpRequest);
                                                        console.error(textStatus);
                                                        console.error(errorThrown);
                                                    },
                                                    complete:function (XMLHttpRequest,textStatus) {}
                                                });
                                            }
                                    }
                                    break;
                                case "serverSidekpi":
                                    break;
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (tableId){
                                case "centerTable":
                                case "userSidekpi":
                                    switch (field){
                                        case "id":
                                        case "name":
                                            break;
                                        default:
                                            $($element).addClass("text-underline");
                                    }
                                    break;
                                case "serverSidekpi":
                                    break;
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        },
                        onLoadSuccess:function (data) {
                            if(tableId == "userSidekpi"){
                                if(!data.length){
                                    defaultCockpit.removelinedraw();
                                }
                            }
                            if(tableId == "serverSidekpi"){
                                if(!serverId && $("#"+tableId).bootstrapTable("getData").length){
                                    $.ptcsBSTabRefresh("userSidekpi",{
                                        "ipmCenterId": ipmCenterId,
                                        "moduleId": tableId=="centerTable" || tableId=="userSidekpi"?11:12,
                                        "appBusinessId": tableId=="serverSidekpi"?serverId:undefined,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    })
                                }
                            }
                        },
                        onLoadError:function () {
                            if(tableId == "userSidekpi"){
                                defaultCockpit.removelinedraw();
                            }
                        }
                    });
                }, "json");
                $.post("/serverManagement/getServerSideUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("serverSidekpi", tableId=="centerTable" || tableId=="serverSidekpi"?
                        "/commonController/getNpmListRrdData.do":
                        "/commonController/getNpmListRrdDataBySubPath.do", {
                        "ipmCenterId": ipmCenterId,
                        "moduleId": tableId=="centerTable" || tableId=="serverSidekpi"?12:11,
                        "appBusinessId": tableId=="userSidekpi"?clientId:undefined,
                        "watchpointId": watchpointId,
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "服务端KPI",
                        ipm_shrink: true,
                        ipm_show: tableId == "serverSidekpi" ?true:false,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            if(tableId == "serverSidekpi"){
                                var cla = {};
                                if(serverId != undefined && serverId != "0"){
                                    if(row.id == serverId){
                                        cla.classes = "custom-row-style cursor";
                                    }else {
                                        cla.classes = "cursor";
                                    }
                                }else {
                                    if (i) {
                                        cla.classes = "cursor";
                                    } else {
                                        serverId = row.id;
                                        cla.classes = "custom-row-style cursor";
                                    }
                                }
                                return cla;
                            }else {
                                return {
                                    classes: "cursor"
                                }
                            }
                        },
                        onClickCell: function (field, value, row, $element) {
                            switch (tableId){
                                case "centerTable":
                                case "serverSidekpi":
                                    if(tableId == "serverSidekpi"){
                                        serverId = row.id;
                                        $("#serverSidekpi > tbody > .custom-row-style").removeClass("custom-row-style");
                                        $($element).parent().addClass("custom-row-style");
                                        _chart.refreNum = 0;
                                    }
                                    switch (field){
                                        case "id":
                                        case "name":
                                            if(tableId == "serverSidekpi"){
                                                $.ptcsBSTabRefresh("userSidekpi", {
                                                    "moduleId": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "watchpointId": watchpointId,
                                                    "appBusinessId": serverId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                });
                                                _chart._createChart({
                                                    "type": moduleId,
                                                    "ipmCenterId": ipmCenterId,
                                                    "selectDataId": serverId,
                                                    "starttime": _starttime,
                                                    "endtime": _endtime
                                                }, {
                                                    "watchpointId": watchpointId,
                                                    "serverId": serverId
                                                });
                                            }
                                            break;
                                        case "alarmLevel":
                                            if(+value){
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=12'+
                                                    '&starttime=' + _starttime +
                                                    '&endtime=' + _endtime +
                                                    '&serverId=' + row.id +
                                                    '&state=N';
                                            }else {
                                                location.href = 'alarmSetting.html?' +
                                                    'watchpointId=' + watchpointId +
                                                    '&moduleId=12'+
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&serverId=' + row.id +
                                                    '&state=N';
                                            }
                                            break;
                                        default:
                                            if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                                $.ajax({
                                                    url:"/plot/getPlotByModuleKpiName.do",
                                                    method:"POST",
                                                    data:{
                                                        moduleId: 12,
                                                        kpiName: field
                                                    },
                                                    dataType:"json",
                                                    beforeSend:function (XMLHttpRequest) {},
                                                    success:function (data,textStatus,XMLHttpRequest) {
                                                        var chartUrl = "/serverManagement/getServerSideGraphical.do",
                                                            plotId = data.id,
                                                            plotTypeId = data.types[0].id;
                                                        location.href = 'commun_queue.html?' +
                                                            'starttime=' + _starttime +
                                                            '&endtime=' + _endtime +
                                                            '&chartUrl=' + chartUrl + '&' +
                                                            'ipmCenterId=' + ipmCenterId +
                                                            '&moduleId=12'+
                                                            '&plotId=' + plotId + '&' +
                                                            'plotTypeId=' + plotTypeId + '&' +
                                                            'watchpointId=' + watchpointId +
                                                            '&serverId=' + row.id;
                                                    },
                                                    error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                        console.error(XMLHttpRequest);
                                                        console.error(textStatus);
                                                        console.error(errorThrown);
                                                    },
                                                    complete:function (XMLHttpRequest,textStatus) {}
                                                });
                                            }
                                    }
                                    break;
                                case "userSidekpi":
                                    break;
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (tableId){
                                case "centerTable":
                                case "serverSidekpi":
                                    switch (field){
                                        case "id":
                                        case "name":
                                            break;
                                        default:
                                            $($element).addClass("text-underline");
                                    }
                                    break;
                                case "userSidekpi":
                                    break;
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        },
                        onLoadSuccess:function(data){
                            if(tableId == "serverSidekpi"){
                                if(!data.length){
                                    defaultCockpit.removelinedraw();
                                }
                            }
                            if(tableId == "userSidekpi"){
                                if(!clientId && $("#"+tableId).bootstrapTable("getData").length){
                                    $.ptcsBSTabRefresh("serverSidekpi",{
                                        "ipmCenterId": ipmCenterId,
                                        "moduleId": tableId=="centerTable" || tableId=="serverSidekpi"?12:11,
                                        "appBusinessId": tableId=="userSidekpi"?clientId:undefined,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    })
                                }
                            }
                        },
                        onLoadError:function () {
                            if(tableId == "serverSidekpi"){
                                defaultCockpit.removelinedraw();
                            }
                        }
                    });
                }, "json");
            }else {
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: tableId == "centerTable"?true:false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        onLoadSuccess: function () {
                            defaultCockpit.removelinedraw();
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/client/getClientUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("userSidekpi", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "客户端KPI",
                        ipm_shrink: true,
                        ipm_show: tableId == "userSidekpi"?true:false,
                        ipm_column_save: true,
                        onLoadSuccess:function (data) {
                            if(tableId == "userSidekpi"){
                                if(!data.length){
                                    defaultCockpit.removelinedraw();
                                }
                            }
                        },
                        onLoadError:function () {
                            if(tableId == "userSidekpi"){
                                defaultCockpit.removelinedraw();
                            }
                        }
                    });
                }, "json");
                $.post("/serverManagement/getServerSideUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("serverSidekpi", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "服务端KPI",
                        ipm_shrink: true,
                        ipm_show: tableId == "serverSidekpi"?true:false,
                        ipm_column_save: true,
                        onLoadSuccess:function(data){
                            if(tableId == "serverSidekpi"){
                                if(!data.length){
                                    defaultCockpit.removelinedraw();
                                }
                            }
                        },
                        onLoadError:function () {
                            if(tableId == "serverSidekpi"){
                                defaultCockpit.removelinedraw();
                            }
                        }
                    });
                }, "json");
            }
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if(!$("#centerTable").bootstrapTable("getData").length){
                    jeBox.alert("请先添加观察点业务");
                    return;
                }
                if($("#"+tableId).bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(moduleId);
                }else {
                    jeBox.alert("请先添加"+ipm_title+"业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                switch (tableId){
                    case "centerTable":
                        _chart._saveJson(moduleId, watchpointId, {
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        });
                        break;
                    case "userSidekpi":
                        _chart._saveJson(moduleId, clientId, {
                            "watchpointId": watchpointId,
                            "ipmCenterId": ipmCenterId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        });
                        break;
                    case "serverSidekpi":
                        _chart._saveJson(moduleId, serverId, {
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        });
                        break;
                }
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            method:"POST",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data,textStatus,XMLHttpRequest) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                }else {
                    location.reload();
                }
            });
            /**
             * 单击箭头跳转页面
             */
            $("#content").on("click","#nextPage",function () {
                if(clientId != undefined || serverId != undefined){
                    if(moduleId == 11){
                        location.href = "netCockpit.html?moduleId="+moduleId+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId+"&busiId="+clientId;
                    }else {
                        location.href = "netCockpit.html?moduleId="+moduleId+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId+"&busiId="+serverId;
                    }
                }else {
                    location.href = "netCockpit.html?moduleId="+moduleId+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId;
                }
            })
        },
        /**
         *
         * @param tableId
         * @param moduleId
         * @param ipm_title
         * @param refreTime
         */
        pubTabConfig_buSer:function (tableId, moduleId, ipm_title,refreTime) {
            var ipmCenterId = parseInt($.getUrlParams().ipmCenterId),
                watchpointId = parseInt($.getUrlParams().watchpointId),
                busiId = parseInt($.getUrlParams().databsId),
                _starttime,
                _endtime,
                onOff,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("centerTable", {
                    "ipmCenterId": ipmCenterId,
                    "moduleId": 10,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh(tableId, {
                    "moduleId": moduleId,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
            }
            timeBackText();
            if(ipmCenterId && watchpointId){
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "/center/getRemoteWatchpointKpiList.do", {
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if(watchpointId != undefined || ipmCenterId != undefined){
                                if(row.id == watchpointId && row.ipmCenterId == ipmCenterId){
                                    cla.classes = "custom-row-style cursor";
                                }else {
                                    cla.classes = "cursor";
                                }
                            }else {
                                if (i) {
                                    cla.classes = "cursor";
                                } else {
                                    watchpointId = row.id;
                                    ipmCenterId = row.ipmCenterId;
                                    cla.classes = "custom-row-style cursor";
                                }
                            }
                            return cla;
                        },
                        onClickCell: function (field, value, row, $element) {
                            $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                            $($element).parent().addClass("custom-row-style");
                            ipmCenterId = row.ipmCenterId;
                            watchpointId = row.id;
                            _chart.refreNum = 0;
                            switch (field){
                                case "ipmCenterName":
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    if(field == "ipmCenterName" && ipmCenterId != 1){
                                        window.open(window.location.protocol+"//"+row.ip+":"+row.port+"/login.html");
                                    }
                                    $.ptcsBSTabRefresh(tableId, {
                                        "moduleId": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    });
                                    _chart._createChart({
                                        "type": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                    break;
                                case "alarmLevel":
                                    if(+value){
                                        location.href = 'alarmSetting.html?' +
                                            'ipmCenterId=' + ipmCenterId +
                                            '&moduleId=10'+
                                            '&busiId='+busiId+
                                            '&watchpointId=' + watchpointId +
                                            '&starttime=' + _starttime +
                                            '&endtime=' + _endtime +
                                            '&state=N';
                                    }else {
                                        location.href = 'alarmSetting.html?' +
                                            'ipmCenterId=' + ipmCenterId +
                                            '&moduleId=10'+
                                            '&busiId='+busiId+
                                            '&watchpointId=' + watchpointId +
                                            '&state=N';
                                    }
                                    break;
                                default:
                                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                        $.ajax({
                                            url:"/plot/getPlotByModuleKpiName.do",
                                            method:"POST",
                                            data:{
                                                moduleId: 10,
                                                kpiName: field
                                            },
                                            dataType:"json",
                                            beforeSend:function (XMLHttpRequest) {},
                                            success:function (data,textStatus,XMLHttpRequest) {
                                                var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id;
                                                location.href = 'commun_queue.html?' +
                                                    'starttime=' + _starttime +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=10'+
                                                    '&busiId='+busiId+
                                                    '&endtime=' + _endtime +
                                                    '&chartUrl=' + chartUrl + '&' +
                                                    'plotId=' + plotId + '&' +
                                                    'plotTypeId=' + plotTypeId + '&' +
                                                    'watchpointId=' + watchpointId;
                                            },
                                            error:function (XMLHttpRequest,textStatus,errorTHrown) {
                                                console.error(XMLHttpRequest);
                                                console.error(textStatus);
                                                console.error(errorTHrown);
                                            },
                                            complete:function (XMLHttpRequest,textStatus) {}
                                        })
                                    }
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (field){
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    break;
                                case "ipmCenterName":
                                    if(row.ipmCenterId != 1){
                                        $($element).addClass("text-underline");
                                    }
                                    break;
                                default:
                                    $($element).addClass("text-underline");
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        },
                        onLoadSuccess: function () {
                            if (onOff) {
                                if($(".linedraw").length-1){
                                    if ($("#centerTable").bootstrapTable("getData").length) {
                                        _chart.reloadChart(watchpointId, {
                                            // "serverId": serverId
                                            "busiId": busiId
                                        }, _starttime, _endtime, ipmCenterId);
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }else {
                                    _chart._createChart({
                                        "type": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                }
                            } else {
                                if ($("#centerTable").bootstrapTable("getData").length ||
                                    $("#"+tableId).bootstrapTable("getData").length) {
                                    _chart._createChart({
                                        "type": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                } else {
                                    defaultCockpit.removelinedraw();
                                }
                            }
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/appController/getAppListColumn.do", {
                    "moduleId": moduleId,
                    "typeId": moduleId
                }, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable(tableId, "/commonController/getNpmListRrdData.do", {
                        "moduleId": moduleId,
                        "ipmCenterId": ipmCenterId,
                        "watchpointId": watchpointId,
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: ipm_title != "报文"?ipm_title + "服务KPI":ipm_title + "KPI",
                        ipm_shrink: true,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if(busiId != undefined){
                                if(row.id == busiId){
                                    cla.classes = "custom-row-style cursor";
                                }else {
                                    cla.classes = "cursor";
                                }
                            }else {
                                if (i) {
                                    cla.classes = "cursor";
                                } else {
                                    busiId = row.id;
                                    cla.classes = "custom-row-style cursor";
                                }
                            }
                            return cla;
                        },
                        onClickCell: function (field, value, row, $element) {
                            busiId = row.id;
                            $("#" + tableId + " > tbody > .custom-row-style").removeClass("custom-row-style");
                            $($element).parent().addClass("custom-row-style");
                            _chart.refreNum = 0;
                            switch (field) {
                                case "id":
                                case "name":
                                    _chart._createChart({
                                        "type": moduleId,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                    break;
                                case "alarmLevel":
                                    if(+value){
                                        location.href = 'alarmSetting.html?' +
                                            'ipmCenterId=' + ipmCenterId +
                                            '&watchpointId=' + watchpointId +
                                            '&moduleId='+ moduleId +
                                            '&busiId=' + busiId +
                                            '&starttime=' + _starttime +
                                            '&endtime=' + _endtime +
                                            '&state=N';
                                    }else {
                                        location.href = 'alarmSetting.html?' +
                                            'ipmCenterId=' + ipmCenterId +
                                            '&moduleId='+ moduleId +
                                            '&watchpointId=' + watchpointId +
                                            '&busiId=' + busiId +
                                            '&state=N';
                                    }
                                    break;
                                default:
                                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                        $.ajax({
                                            url: "/plot/getPlotByModuleKpiName.do",
                                            method:"POST",
                                            data:{
                                                moduleId: moduleId,
                                                kpiName: field
                                            },
                                            dataType:"json",
                                            beforeSend:function (XMLHttpRequest) {},
                                            success:function (data,textStatus,XMLHttpRequest) {
                                                var chartUrl = "/appController/getPlotData.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id,
                                                    busiId = row.id;
                                                location.href = 'bssSession.html?' +
                                                    'starttime=' + _starttime +
                                                    '&endtime=' + _endtime +
                                                    '&plotId=' + plotId + '&' +
                                                    'ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=' + moduleId + '&' +
                                                    'plotTypeId=' + plotTypeId + '&' +
                                                    'watchpointId=' + watchpointId +
                                                    '&busiId=' + busiId;
                                            },
                                            error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                console.error(XMLHttpRequest);
                                                console.error(textStatus);
                                                console.error(errorThrown);
                                            },
                                            complete:function (XMLHttpRequest,textStatus) {}
                                        });
                                    }
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (field){
                                case "id":
                                case "name":
                                    break;
                                default:
                                    $($element).addClass("text-underline");
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        }
                    });
                }, "json");
            }else {
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        onLoadSuccess: function () {
                            defaultCockpit.removelinedraw();
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/appController/getAppListColumn.do", {
                    "moduleId": moduleId,
                    "typeId": moduleId
                }, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable(tableId, "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: ipm_title != "报文"?ipm_title + "服务KPI":ipm_title + "KPI",
                        ipm_shrink: true,
                        ipm_column_save: true
                    });
                }, "json");
            }
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if(!$("#centerTable").bootstrapTable("getData").length){
                    jeBox.alert("请先添加观察点业务");
                    return;
                }
                if($("#"+tableId).bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(moduleId);
                }else {
                    jeBox.alert("请先添加"+ipm_title+"业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(moduleId, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            method:"POST",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data,textStatus,XMLHttpRequest) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                }else {
                    location.reload();
                }
            });
            /**
             * 单击箭头跳转页面
             */
            $("#content").on("click","#nextPage",function () {
                if(busiId != undefined){
                    location.href = "netCockpit.html?moduleId="+moduleId+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId+"&busiId="+busiId;
                }else {
                    location.href = "netCockpit.html?moduleId="+moduleId+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId;
                }
            })
        },
        /**
         *
         * @param refreTime
         */
        pubTabConfig_url:function (refreTime) {
            var ipmCenterId = parseInt($.getUrlParams().ipmCenterId),
                watchpointId = parseInt($.getUrlParams().watchpointId),
                busiId = parseInt($.getUrlParams().databsId),
                _starttime,
                _endtime,
                onOff,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            //为右上角赋值时间
            function timeBackText() {
                /*
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
            }
            function refreshTableData(starttime, endtime) {
                onOff = true;
                $.ptcsBSTabRefresh("centerTable", {
                    "ipmCenterId": ipmCenterId,
                    "moduleId": 10,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("urlkpiState", {
                    "moduleId": 8,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime
                });
                $.ptcsBSTabRefresh("urlkpiProp", {
                    "moduleId": 8,
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": starttime?starttime:_starttime,
                    "endtime": endtime?endtime:_endtime,
                    "busiId": busiId
                });
            }
            timeBackText();
            if(ipmCenterId && watchpointId){
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "/center/getRemoteWatchpointKpiList.do", {
                        "starttime": _starttime,
                        "endtime": _endtime
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if(watchpointId != undefined || ipmCenterId != undefined){
                                if(row.id == watchpointId && row.ipmCenterId == ipmCenterId){
                                    cla.classes = "custom-row-style cursor";
                                }else {
                                    cla.classes = "cursor";
                                }
                            }else {
                                if (i) {
                                    cla.classes = "cursor";
                                } else {
                                    watchpointId = row.id;
                                    ipmCenterId = row.ipmCenterId;
                                    cla.classes = "custom-row-style cursor";
                                }
                            }
                            return cla;
                        },
                        onClickCell: function (field, value, row, $element) {
                            $("#centerTable>tbody> .custom-row-style").removeClass("custom-row-style");
                            $($element).parent().addClass("custom-row-style");
                            ipmCenterId = row.ipmCenterId;
                            watchpointId = row.id;
                            _chart.refreNum = 0;
                            switch (field){
                                case "ipmCenterName":
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    if(field == "ipmCenterName" && ipmCenterId != 1){
                                        window.open(window.location.protocol+"//"+row.ip+":"+row.port+"/login.html");
                                    }
                                    $.ptcsBSTabRefresh("urlkpiState", {
                                        "moduleId": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    });
                                    $.ptcsBSTabRefresh("urlkpiProp", {
                                        "moduleId": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "watchpointId": watchpointId,
                                        "appBusinessId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime,
                                        "busiId": busiId
                                    });
                                    _chart._createChart({
                                        "type": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                    break;
                                case "alarmLevel":
                                    if(+value){
                                        location.href = 'alarmSetting.html?' +
                                            'watchpointId=' + watchpointId +
                                            '&ipmCenterId=' + ipmCenterId +
                                            '&moduleId=10'+
                                            '&starttime=' + _starttime +
                                            '&endtime=' + _endtime +
                                            '&state=N';
                                    }else {
                                        location.href = 'alarmSetting.html?' +
                                            'watchpointId=' + watchpointId +
                                            '&moduleId=10'+
                                            '&ipmCenterId=' + ipmCenterId +
                                            '&state=N';
                                    }
                                    break;
                                default:
                                    if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                        $.ajax({
                                            url:"/plot/getPlotByModuleKpiName.do",
                                            method:"POST",
                                            data:{
                                                moduleId: 10,
                                                kpiName: field
                                            },
                                            dataType:"json",
                                            beforeSend:function (XMLHttpRequest) {},
                                            success:function (data,textStatus,XMLHttpRequest) {
                                                var chartUrl = "/watchpointController/getWatchpointGraphical.do",
                                                    plotId = data.id,
                                                    plotTypeId = data.types[0].id;
                                                location.href = 'commun_queue.html?' +
                                                    'starttime=' + _starttime +
                                                    '&ipmCenterId=' + ipmCenterId +
                                                    '&moduleId=10'+
                                                    '&endtime=' + _endtime +
                                                    '&chartUrl=' + chartUrl + '&' +
                                                    'plotId=' + plotId + '&' +
                                                    'plotTypeId=' + plotTypeId + '&' +
                                                    'watchpointId=' + watchpointId;
                                            },
                                            error:function (XMLHttpRequest,textStatus,errorTHrown) {
                                                console.error(XMLHttpRequest);
                                                console.error(textStatus);
                                                console.error(errorTHrown);
                                            },
                                            complete:function (XMLHttpRequest,textStatus) {}
                                        })
                                    }
                            }
                        },
                        onMouseOverCell:function(field, value, row, $element){
                            switch (field){
                                case "ip":
                                case "port":
                                case "id":
                                case "name":
                                case "contacts":
                                case "email":
                                case "telephone":
                                case "load_5":
                                    break;
                                case "ipmCenterName":
                                    if(row.ipmCenterId != 1){
                                        $($element).addClass("text-underline");
                                    }
                                    break;
                                default:
                                    $($element).addClass("text-underline");
                            }
                        },
                        onMouseOutCell:function(field, value, row, $element){
                            if($($element).hasClass("text-underline")){
                                $($element).removeClass("text-underline");
                            }
                        },
                        onLoadSuccess: function () {
                            if (onOff) {
                                if($(".linedraw").length-1){
                                    if ($("#centerTable").bootstrapTable("getData").length) {
                                        _chart.reloadChart(watchpointId, {
                                            "busiId": busiId
                                        }, _starttime, _endtime, ipmCenterId);
                                    } else {
                                        defaultCockpit.removelinedraw();
                                    }
                                }else {
                                    _chart._createChart({
                                        "type": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                }
                            } else {
                                if ($("#centerTable").bootstrapTable("getData").length ||
                                    $("#urlkpiProp").bootstrapTable("getData").length) {
                                    _chart._createChart({
                                        "type": 8,
                                        "ipmCenterId": ipmCenterId,
                                        "selectDataId": busiId,
                                        "starttime": _starttime,
                                        "endtime": _endtime
                                    }, {
                                        "watchpointId": watchpointId,
                                        "busiId": busiId
                                    });
                                } else {
                                    defaultCockpit.removelinedraw();
                                }
                            }
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/appController/getAppListColumn.do", {
                        moduleId: 8,
                        typeId: 8
                    },
                    function (data) {
                        var columns = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            columns.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("urlkpiState", "/url/getUrlStateList.do", {
                            "moduleId": 8,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        }, {
                            pageSize: 10,
                            columns: columns,
                            ipm_title: "URL状态列表",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            ipm_show: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if(busiId != undefined){
                                    if(row.id == busiId){
                                        cla.classes = "custom-row-style cursor";
                                    }else {
                                        cla.classes = "cursor";
                                    }
                                }else {
                                    if (i) {
                                        cla.classes = "cursor";
                                    } else {
                                        busiId = row.id;
                                        cla.classes = "custom-row-style cursor";
                                    }
                                }
                                return cla;
                            },
                            onClickCell: function (field, value, row, $element) {
                                busiId = row.id;
                                $("#urlkpiState > tbody > .custom-row-style").removeClass("custom-row-style");//
                                $($element).parent().addClass("custom-row-style");//
                                _chart.refreNum = 0;
                                switch (field){
                                    case "id":
                                    case "name":
                                        $.ptcsBSTabRefresh("urlkpiState", {
                                            "moduleId": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "watchpointId": watchpointId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        });
                                        $.ptcsBSTabRefresh("urlkpiProp", {
                                            "moduleId": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "watchpointId": watchpointId,
                                            "appBusinessId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime,
                                            "busiId": busiId
                                        });
                                        _chart._createChart({
                                            "type": 8,
                                            "ipmCenterId": ipmCenterId,
                                            "selectDataId": busiId,
                                            "starttime": _starttime,
                                            "endtime": _endtime
                                        }, {
                                            "watchpointId": watchpointId,
                                            "busiId": busiId
                                        });
                                        break;
                                    case "alarmLevel":
                                        if(+value){
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&moduleId=8'+
                                                '&starttime=' + _starttime +
                                                '&endtime=' + _endtime +
                                                '&busiId='+ busiId +
                                                '&state=N';
                                        }else {
                                            location.href = 'alarmSetting.html?' +
                                                'watchpointId=' + watchpointId +
                                                '&moduleId=12'+
                                                '&ipmCenterId=' + ipmCenterId +
                                                '&busiId='+ busiId +
                                                '&state=N';
                                        }
                                        break;
                                    default:
                                        if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                                            $.ajax({
                                                url:"/plot/getPlotByModuleKpiName.do",
                                                method:"POST",
                                                data:{
                                                    moduleId: 8,
                                                    kpiName: field
                                                },
                                                dataType:"json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success:function (data,textStatus,XMLHttpRequest) {
                                                    var chartUrl = "/appController/getPlotData.do",
                                                        plotId = data.id,
                                                        plotTypeId = data.types[0].id;
                                                    location.href = 'bssSession.html?' +
                                                        'starttime=' + _starttime +
                                                        '&endtime=' + _endtime +
                                                        '&ipmCenterId=' + ipmCenterId +
                                                        '&moduleId=8'+
                                                        '&plotId=' + plotId + '&' +
                                                        'plotTypeId=' + plotTypeId + '&' +
                                                        'watchpointId=' + watchpointId +
                                                        '&busiId='+ busiId ;
                                                },
                                                error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                    console.error(XMLHttpRequest);
                                                    console.error(textStatus);
                                                    console.error(errorThrown);
                                                },
                                                complete:function (XMLHttpRequest,textStatus) {}
                                            });
                                        }
                                }
                            },
                            onMouseOverCell:function(field, value, row, $element){
                                switch (field){
                                    case "id":
                                    case "name":
                                        break;
                                    default:
                                        $($element).addClass("text-underline");
                                }
                            },
                            onMouseOutCell:function(field, value, row, $element){
                                if($($element).hasClass("text-underline")){
                                    $($element).removeClass("text-underline");
                                }
                            },
                            onLoadSuccess:function(){},
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                $.post("/appController/getAppListColumn.do", {
                    moduleId: 8,
                    typeId: 18
                }, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("urlkpiProp", "/url/getSimpleUrlStateList.do", {
                        "moduleId": 8,
                        "ipmCenterId": ipmCenterId,
                        "watchpointId": watchpointId,
                        "starttime": _starttime,
                        "endtime": _endtime,
                        "busiId": busiId
                    }, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "URL性能列表",
                        ipm_shrink: true,
                        ipm_column_save: true,
                        rowStyle: function (row, i) {
                            return {
                                classes: "cursor"
                            }
                        },
                        onLoadError:function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
            }else {
                $.post("/watchpointController/getWatchpointUserListColumn.do", null, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("centerTable", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "XPM服务器",
                        ipm_show: false,
                        ipm_shrink: true,
                        ipm_column_save: true,
                        onLoadSuccess: function () {
                            defaultCockpit.removelinedraw();
                        },
                        onLoadError: function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
                $.post("/appController/getAppListColumn.do", {
                        moduleId: 8,
                        typeId: 8
                    },
                    function (data) {
                        var columns = [];
                        for (var i = 0, len = data.length; i < len; i++) {
                            columns.push({
                                id: data[i].id,
                                field: data[i].columnen,
                                title: data[i].columnzh,
                                sortable: true,
                                visible: !!data[i].checked
                            });
                        }
                        $.ptcsBSTable("urlkpiState", "/url/getUrlStateList.do", {
                            "moduleId": 8,
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "starttime": _starttime,
                            "endtime": _endtime
                        }, {
                            pageSize: 10,
                            columns: columns,
                            ipm_title: "URL状态列表",
                            ipm_shrink: true,
                            ipm_column_save: true,
                            ipm_show: true,
                            onLoadSuccess:function(){
                                defaultCockpit.removelinedraw();
                            },
                            onLoadError:function () {
                                defaultCockpit.removelinedraw();
                            }
                        });
                    }, "json");
                $.post("/appController/getAppListColumn.do", {
                    moduleId: 8,
                    typeId: 18
                }, function (data) {
                    var columns = [];
                    for (var i = 0, len = data.length; i < len; i++) {
                        columns.push({
                            id: data[i].id,
                            field: data[i].columnen,
                            title: data[i].columnzh,
                            sortable: true,
                            visible: !!data[i].checked
                        });
                    }
                    $.ptcsBSTable("urlkpiProp", "", {}, {
                        pageSize: 10,
                        columns: columns,
                        ipm_title: "URL性能列表",
                        ipm_shrink: true,
                        ipm_column_save: true,
                        onLoadError:function () {
                            defaultCockpit.removelinedraw();
                        }
                    });
                }, "json");
            }
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if(!$("#centerTable").bootstrapTable("getData").length){
                    jeBox.alert("请先添加观察点业务");
                    return;
                }
                if($("#urlkpiState").bootstrapTable("getData").length){
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                    _chart._kpiSelectM(8);
                }else {
                    jeBox.alert("请先添加URL业务");
                }
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(8, busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime);
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            method:"POST",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data,textStatus,XMLHttpRequest) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                }else {
                    location.reload();
                }
            });
            /**
             * 单击箭头跳转页面
             */
            $("#content").on("click","#nextPage",function () {
                if(busiId != undefined){
                    location.href = "netCockpit.html?moduleId="+8+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId+"&busiId="+busiId;
                }else {
                    location.href = "netCockpit.html?moduleId="+8+"&ipmCenterId="+ipmCenterId+"&watchpointId="+watchpointId;
                }
            })
        },
        /**
         * 四个小格式默认回溯时间跟小列表一样
         * 由于小列表没有传时间
         * 故这里也不传时间
         * 但实际情况是不传不行哇。。
         * 四个小图形回溯的是10秒
         * 其它元素是10分钟
         *
         * @param refreTime
         * ipmCenterId 1
         * moduIeId 10
         */
        netCockpit:function (refreTime) {
            var ipmCenterId = 1,
                watchpointId = 1,
                moduleId = 10,
                _starttime,
                _endtime,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime);
            //为右上角赋值时间
            function timeBackText() {
                /**
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
                $(".timeBackText").attr({
                    "data-strTime":_starttime,
                    "data-endTime":_endtime
                })
            }
            timeBackText();
            _chart._createChart({
                type: moduleId,
                ipmCenterId: ipmCenterId,
                selectDataId: watchpointId,
                starttime: _starttime,
                endtime: _endtime,
                netAlarmChart:{
                    titleId:"draw_title_alarm0",
                    itemId:"alarm_chartemId0",
                    url:"/alarmLog/getRemoteMonLogInfo.do",
                    ipmCenterId:ipmCenterId,
                    watchpointId:watchpointId,
                    moduleId:moduleId,
                    columnNum:10,
                    handledflag:"N",
                    businessId:1,
                    starttime:_starttime,
                    endtime:_endtime
                },
                fsParams:{
                    url:"/watchpointController/getCrossGridData.do",
                    // plotIdsArray:[1,317,2,276],
                    // plotIdsArray:[1,317,15,276],
                    plotIdsArray:[1,317,323,276],
                    ajaxParam:{
                        ipmCenterId: 1,
                        watchpointId: 1,
                        moduleId: 10,
                        // plotIds:"1,317,2,276",
                        // plotIds:"1,317,15,276",
                        plotIds:"1,317,323,276",
                        dblclickUrl:"/watchpointController/getWatchpointGraphical.do",
                        // starttime:_endtime-10,
                        // endtime:_endtime
                    }
                }
            });
            $("#list-draw").off("dblclick").on("dblclick", function () {
                _chart.kpiName = [];
                for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                    if(!_t.eq(i).hasClass("smChBox")){
                        _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                    }
                }
                _chart._kpiSelectM(10);
            });
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(10, watchpointId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime,
                    netAlarmChart:{
                        titleId:"draw_title_alarm0",
                        itemId:"alarm_chartemId0",
                        url:"/alarmLog/getRemoteMonLogInfo.do",
                        ipmCenterId:ipmCenterId,
                        watchpointId:watchpointId,
                        moduleId:moduleId,
                        columnNum:10,
                        handledflag:"N",
                        businessId:1,
                        starttime:_starttime,
                        endtime:_endtime
                    },
                    fsParams:{
                        url:"/watchpointController/getCrossGridData.do",
                        // plotIdsArray:[1,317,2,276],
                        // plotIdsArray:[1,317,15,276],
                        plotIdsArray:[1,317,323,276],
                        ajaxParam:{
                            ipmCenterId: 1,
                            watchpointId: 1,
                            moduleId: 10,
                            // plotIds:"1,317,2,276",
                            // plotIds:"1,317,15,276",
                            plotIds:"1,317,323,276",
                            dblclickUrl:"/watchpointController/getWatchpointGraphical.do",
                            // starttime:_endtime-_starttime !=600?_starttime:undefined,
                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                            // endtime:_endtime-_starttime !=600?_endtime:undefined
                        }
                    }
                });
            });
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    refreshTableData(_starttime, _endtime,"netCockpit");
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $(".timeBackText").attr({
                        "data-strTime":_starttime,
                        "data-endTime":_endtime
                    });
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            function refreshTableData(starttime, endtime,netCockpit) {
                _chart.reloadChart(
                    watchpointId,
                    {
                        "busiId": undefined,
                        "timeback":netCockpit
                    },
                    starttime?starttime:_starttime,
                    endtime?endtime:_endtime,
                    ipmCenterId
                );
            }
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                    _chart.netCockpitHuiSu = undefined;
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    _chart.netCockpitHuiSu = undefined;
                    location.reload();
                }
            });
        },
        /**
         *
         * @param refreTime
         */
        XPM_netCockpit:function (refreTime) {
            var ipmCenterId,
                watchpointId,
                moduleId,
                busiId,
                _starttime,
                _endtime,
                timer2 = setInterval(timeBackText, refreTime),
                timer3 = setInterval(refreshTableData, refreTime),
                fs_url = "/watchpointController/getCrossGridData.do",
                fs_dblclickUrl = "/watchpointController/getWatchpointGraphical.do",
                fs_plotIdsArray = [1,317,323,276],
                tabUrl = "watchpointController/getFindAll.do",
                fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
            //为右上角赋值时间
            function timeBackText() {
                /**
                 * 判断开始时间和结束时间是否与现在年相同 若都满足则不显示年否则显示年
                 */
                var getDefaultEndtime = $.getDefaultEndtime()-20;//此处强行减去20只是为咯减去差异满足需求
                if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
                    (new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
                } else {
                    var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
                    $(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
                }
                _starttime = getDefaultEndtime-600;
                _endtime = getDefaultEndtime;
                $(".timeBackText").attr({
                    "data-strTime":_starttime,
                    "data-endTime":_endtime
                })
            }
            timeBackText();
            /**
             * 请求上次保存的 ipmCenterId  watchpointId moduleId busiId
             */
            $.ajax({
                url:"/watchpointController/getUserConfigureBeanByKey.do",
                method: "POST",
                async:false,
                data: {
                    key: "cockpitConfig"
                },
                dataType: "text",
                beforeSend: function(XMLHttpRequest) {},
                success: function(data, textStatus, XMLHttpRequest) {
                    if(data != ""){
                        var dataArray = eval("[" + data + "]");
                        ipmCenterId = dataArray[0];
                        moduleId = dataArray[1];
                        watchpointId = dataArray[2];
                        if(dataArray[3]){
                            busiId = dataArray[3];
                        }
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete: function(XMLHttpRequset, textStatus) {}
            });
            /**
             * 为xmp下拉框赋值
             * 以及默认的观察点下拉框赋值
             * 设备
             */
            $.ajax({
                url:"/saasuser/getUserByDelay.do ",
                method:"POST",
                async:false,
                data:{},
                dataType:"json",
                beforeSend:function(XMLHttpRequest){},
                success:function (data,textStatus,XMLHttpRequest) {
                    var tempIpmcenterId = [];
                    data.forEach(function (item,index) {
                        tempIpmcenterId.push(item.id);
                    });
                    data.forEach(function (item,index) {
                        if(!ipmCenterId && !index && tempIpmcenterId.indexOf(ipmCenterId) == -1){
                            ipmCenterId = item.id;
                        }
                        if(ipmCenterId == item.id){
                            $("#xpm_select").append('<option data-ipmCenterId="'+item.id+'" title="设备"  selected="selected">'+item.name+'</option>');
                        }else {
                            $("#xpm_select").append('<option data-ipmCenterId="'+item.id+'" title="设备" >'+item.name+'</option>');
                        }
                    });
                },
                error:function (XMLhttpRequest,textStatus,errorThrown) {
                    console.error(XMLhttpRequest);
                    console.error(textStatus);
                    console.error(errorThrown);
                },
                complete:function (XMLhttpRequest,textStatus) {}
            });
            /**
             * 为业务下拉框赋值
             * 供用户选择某个业务
             * 此处应该预设三个变量
             * 四个小图形的url
             * 四个小图形的plotIds
             * 四个小图形的dblclickUrl
             * 获取表格的数据
             * 监控类型
             */
            $.ajax({
                url: "/authorizeModuleController/getFindAll.do",
                method: "POST",
                async:false,
                data: {},
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success: function (data,textStatus,XMLHttpRequest) {
                    $("#busNamecn_select").empty().append('<option  title="监控类型" disabled="disabled">监控类型</option>');
                    data.forEach(function (item,index) {
                        /**
                         * 排除1 应用可用性 3 用户管理
                         */
                        var data_url,
                            data_dblclickUrl,
                            data_plotIdsArray,
                            data_tabUrl,
                            data_pageName,
                            data_monitorId;
                        if(item.id != "1" && item.id != "3" && item.id != "13"){
                            switch (item.id){
                                case "4"://HTTP
                                    data_url = "/appController/getSimpleData.do";
                                    data_dblclickUrl = "/appController/getPlotData.do";
                                    data_plotIdsArray = [102,131,133,273];
                                    data_tabUrl = "/appController/getAllAppByModuleId.do";
                                    data_pageName = "httpSerkpi";
                                    data_monitorId = 5;
                                    break;
                                case "5"://ORACLE
                                    data_url = "/appController/getSimpleData.do";
                                    data_dblclickUrl = "/appController/getPlotData.do";
                                    data_plotIdsArray = [158,165,277,279];
                                    data_tabUrl = "/appController/getAllAppByModuleId.do";
                                    data_pageName = "oracleSerkpi";
                                    data_monitorId = 6;
                                    break;
                                case "6"://MYSQL
                                    data_url = "/appController/getSimpleData.do";
                                    data_dblclickUrl = "/appController/getPlotData.do";
                                    data_plotIdsArray = [192,199,280,282];
                                    data_tabUrl = "/appController/getAllAppByModuleId.do";
                                    data_pageName = "mysqlSerkpi";
                                    data_monitorId = 7;
                                    break;
                                case "7"://SQLSERVER
                                    data_url = "/appController/getSimpleData.do";
                                    data_dblclickUrl = "/appController/getPlotData.do";
                                    data_plotIdsArray = [226,233,283,285];
                                    data_tabUrl = "/appController/getAllAppByModuleId.do";
                                    data_pageName = "sqlSerkpi";
                                    data_monitorId = 8;
                                    break;
                                case "8"://URL
                                    data_url = "/url/getSimpleData.do";
                                    data_dblclickUrl = "/url/getPlotData.do";
                                    data_plotIdsArray = [289,290,291,294];
                                    data_tabUrl = "/url/get.do";
                                    data_pageName = "urlkpi";
                                    data_monitorId = 9;
                                    break;
                                case "9"://MESSAGE
                                    data_url = "/depthanaly/getSimpleData.do";
                                    data_dblclickUrl = "/depthanaly/depthanalyGraphical.do";
                                    data_plotIdsArray = [304,305,306,307];
                                    data_tabUrl = "/depthanaly/selectAll.do";
                                    data_pageName = "baowenJy";
                                    data_monitorId = 10;
                                    break;
                                case "10"://WATCHPOINT
                                    data_url = "/watchpointController/getCrossGridData.do";
                                    data_dblclickUrl = "/watchpointController/getWatchpointGraphical.do";
                                    data_plotIdsArray = [1,317,323,276];
                                    data_tabUrl = "watchpointController/getFindAll.do";
                                    data_pageName = "observationPointkpi";
                                    data_monitorId = 2;
                                    break;
                                case "11"://CLIENT
                                    data_url = "/client/getClientSingleValueData.do";
                                    data_dblclickUrl = "/client/getClientGraphical.do";
                                    data_plotIdsArray = [32,35,40,58];
                                    data_tabUrl = "/client/getClient.do";
                                    data_pageName = "userSidekpi";
                                    data_monitorId = 3;
                                    break;
                                case "12"://SERVER
                                    data_url = "/serverManagement/getServerSideSingleValueData.do";
                                    data_dblclickUrl = "/serverManagement/getServerSideGraphical.do";
                                    data_plotIdsArray = [60,68,97,319];
                                    data_tabUrl = "/serverManagement/getAllServerSide.do";
                                    data_pageName = "serverSidekpi";
                                    data_monitorId = 4;
                                    break;
                            }
                            if(!moduleId){
                                moduleId = 10;
                                fs_url = "/watchpointController/getCrossGridData.do";
                                fs_dblclickUrl = "/watchpointController/getWatchpointGraphical.do";
                                fs_plotIdsArray = [1,317,323,276];
                                tabUrl = "watchpointController/getFindAll.do";
                                fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                            }else {
                                switch (String(moduleId)){
                                    case "4"://HTTP
                                        fs_url = "/appController/getSimpleData.do";
                                        fs_dblclickUrl = "/appController/getPlotData.do";
                                        fs_plotIdsArray = [102,131,133,273];
                                        tabUrl = "/appController/getAllAppByModuleId.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "5"://ORACLE
                                        fs_url = "/appController/getSimpleData.do";
                                        fs_dblclickUrl = "/appController/getPlotData.do";
                                        fs_plotIdsArray = [158,165,277,279];
                                        tabUrl = "/appController/getAllAppByModuleId.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "6"://MYSQL
                                        fs_url = "/appController/getSimpleData.do";
                                        fs_dblclickUrl = "/appController/getPlotData.do";
                                        fs_plotIdsArray = [192,199,280,282];
                                        tabUrl = "/appController/getAllAppByModuleId.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "7"://SQLSERVER
                                        fs_url = "/appController/getSimpleData.do";
                                        fs_dblclickUrl = "/appController/getPlotData.do";
                                        fs_plotIdsArray = [226,233,283,285];
                                        tabUrl = "/appController/getAllAppByModuleId.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "8"://URL
                                        fs_url = "/url/getSimpleData.do";
                                        fs_dblclickUrl = "/url/getPlotData.do";
                                        fs_plotIdsArray = [289,290,291,294];
                                        tabUrl = "/url/get.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "9"://MESSAGE
                                        fs_url = "/depthanaly/getSimpleData.do";
                                        fs_dblclickUrl = "/depthanaly/depthanalyGraphical.do";
                                        fs_plotIdsArray = [304,305,306,307];
                                        tabUrl = "/depthanaly/selectAll.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "10"://WATCHPOINT
                                        fs_url = "/watchpointController/getCrossGridData.do";
                                        fs_dblclickUrl = "/watchpointController/getWatchpointGraphical.do";
                                        fs_plotIdsArray = [1,317,323,276];
                                        tabUrl = "watchpointController/getFindAll.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "11"://CLIENT
                                        fs_url = "/client/getClientSingleValueData.do";
                                        fs_dblclickUrl = "/client/getClientGraphical.do";
                                        fs_plotIdsArray = [32,35,40,58];
                                        tabUrl = "/client/getClient.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                    case "12"://SERVER
                                        fs_url = "/serverManagement/getServerSideSingleValueData.do";
                                        fs_dblclickUrl = "/serverManagement/getServerSideGraphical.do";
                                        fs_plotIdsArray = [60,68,97,319];
                                        tabUrl = "/serverManagement/getAllServerSide.do";
                                        fs_plotIds = String(fs_plotIdsArray).replace("[","").replace("]","");
                                        break;
                                }
                            }
                            if(item.id == moduleId){
                                $("#busNamecn_select").append('<option data-url="'+data_url+'"' +
                                    '  data-dblclickUrl="'+data_dblclickUrl+'"' +
                                    '  data-plotIdsArray="'+data_plotIdsArray+'"' +
                                    '  data-tabUrl="'+data_tabUrl+'"' +
                                    '  data-pageName="'+data_pageName+'"' +
                                    '  data-monitorId="'+data_monitorId+'"' +
                                    '  title="监控类型" '+
                                    ' data-moduleId="'+item.id+'" selected="selected">'+item.namezh.replace("观察点","网络")+'</option>')
                            }else {
                                $("#busNamecn_select").append('<option data-url="'+data_url+'"' +
                                    '  data-dblclickUrl="'+data_dblclickUrl+'"' +
                                    '  data-plotIdsArray="'+data_plotIdsArray+'"' +
                                    '  data-tabUrl="'+data_tabUrl+'"' +
                                    '  data-pageName="'+data_pageName+'"' +
                                    '  data-monitorId="'+data_monitorId+'"' +
                                    ' data-moduleId="'+item.id+'" title="监控类型">'+item.namezh.replace("观察点","网络")+'</option>')
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
            /**
             * 为观察点下拉框赋值
             *  观察点
             */
            $.ajax({
                url:"watchpointController/getFindAll.do",
                method:"POST",
                async:false,
                data:{
                    moduleId: 10
                },
                dataType:"json",
                beforeSend:function (XMLHttpRequest) {},
                success:function(data,textStatus,XMLHttpRequest){
                    $("#obser_select").empty().append('<option  title="观察点"  disabled="disabled">观察点</option>');
                    var tempWatchpointId = [];
                    data.forEach(function (item,index) {
                        tempWatchpointId.push(item.id);
                    });
                    data.forEach(function (item,index) {
                        if(!watchpointId && !index && tempWatchpointId.indexOf(watchpointId) == -1){
                            watchpointId = item.id;
                        }
                        if(item.id == watchpointId){
                            $("#obser_select").append('<option data-watchpointId="'+item.id+'"  title="观察点" selected="selected">'+item.name+'</option>');
                        }else {
                            $("#obser_select").append('<option data-watchpointId="'+item.id+'"  title="观察点">'+item.name+'</option>');
                        }
                    })
                },
                error:function (XMLHttpRequest,textStatus,errorThrown) {
                    console.error(XMLhttpRequest);
                    console.error(textStatus);
                    console.error(errorThrown);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
            /**
             * 为业务值下拉框赋值
             * 监控对象
             */
            if(moduleId-10){
                $.ajax({
                    url:tabUrl,
                    method:"POST",
                    data:{
                        ipmCenterId:ipmCenterId,
                        moduleId: moduleId
                    },
                    dataType:"json",
                    beforeSend:function (XMLHttpRequest) {},
                    success:function (data,textStatus,XMLHttpRequest) {
                        $("#bus_select").empty().append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                        var tempBusiId = [];
                        data.forEach(function (item,index) {
                            tempBusiId.push(item.id);
                        });
                        data.forEach(function (item,index) {
                            if(!busiId && !index && tempBusiId.indexOf(busiId) == -1){
                                busiId = item.id;
                            }
                            if(item.id == busiId){
                                $("#bus_select").append('<option data-busiId="'+item.id+'" title="监控对象" selected="selected">'+item.name+'</option>')
                            }else {
                                $("#bus_select").append('<option data-busiId="'+item.id+'" title="监控对象">'+item.name+'</option>')
                            }
                        });
                        if(data.length){
                            $("#bus_select").removeClass("none");
                            _chart._createChart({
                                type: moduleId,
                                ipmCenterId: ipmCenterId,
                                selectDataId: moduleId == 10? watchpointId : busiId,
                                watchpointId:watchpointId,
                                starttime: _starttime,
                                endtime: _endtime,
                                netAlarmChart:{
                                    titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                    itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                    url:"/alarmLog/getRemoteMonLogInfo.do",
                                    ipmCenterId:ipmCenterId,
                                    watchpointId:watchpointId,
                                    moduleId:moduleId,
                                    columnNum:10,
                                    handledflag:"N",
                                    businessId:moduleId == 10 ? watchpointId :busiId,
                                    starttime:_starttime,
                                    endtime:_endtime
                                },
                                fsParams:{
                                    url:fs_url,
                                    plotIdsArray:fs_plotIdsArray,
                                    ajaxParam:{
                                        ipmCenterId: ipmCenterId,
                                        watchpointId: watchpointId,
                                        moduleId: moduleId,
                                        busiId: busiId,
                                        clientId: busiId,
                                        serverId: busiId,
                                        plotIds:fs_plotIds,
                                        dblclickUrl:fs_dblclickUrl,
                                        starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                                        endtime:_chart.netCockpitHuiSu?_endtime:undefined
                                    }
                                }
                            });
                        }else {
                            $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                            busiId = undefined;
                            defaultCockpit.removelinedraw();
                            jeBox.alert("请先添加"+$("#busNamecn_select").children("option:selected").text().replace("服务","")+"业务")
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThrown) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThrown);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                })
            }else {
                busiId = undefined;
                $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                _chart._createChart({
                    type: moduleId,
                    ipmCenterId: ipmCenterId,
                    selectDataId: moduleId == 10? watchpointId : busiId,
                    starttime: _starttime,
                    endtime: _endtime,
                    netAlarmChart:{
                        titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        url:"/alarmLog/getRemoteMonLogInfo.do",
                        ipmCenterId:ipmCenterId,
                        watchpointId:watchpointId,
                        moduleId:moduleId,
                        columnNum:10,
                        handledflag:"N",
                        businessId:moduleId == 10 ? watchpointId :busiId,
                        starttime:_starttime,
                        endtime:_endtime
                    },
                    fsParams:{
                        url:fs_url,
                        plotIdsArray:fs_plotIdsArray,
                        ajaxParam:{
                            ipmCenterId: ipmCenterId,
                            watchpointId: watchpointId,
                            moduleId: moduleId,
                            plotIds:fs_plotIds,
                            dblclickUrl:fs_dblclickUrl,
                            busiId: busiId,
                            clientId: busiId,
                            serverId: busiId,
                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                        }
                    }
                });
            }
            /**
             * 弹出模态框
             * 此处得限制弹出模态框时是否有业务
             */
            $("#list-draw").off("dblclick").on("dblclick", function () {
                if(moduleId != 10 && $("#bus_select").hasClass("none")){
                    jeBox.alert("请先添加"+$("#busNamecn_select").children("option:selected").text().replace("服务","")+"业务");
                }else {
                    _chart.kpiName = [];
                    for (var i = 0, _t = $(".linedraw"); i < _t.length - 1; i++) {
                        if(!_t.eq(i).hasClass("smChBox")){
                            _chart.kpiName.push(_t.eq(i).children().attr("data-plotId"));
                        }
                    }
                    _chart._kpiSelectM(moduleId);
                }
            });
            /**
             * 保存数据
             */
            $("#list-save").click(function () {
                _chart.refreNum = 0;
                _chart._saveJson(moduleId, moduleId == 10? watchpointId : busiId, {
                    "ipmCenterId": ipmCenterId,
                    "watchpointId": watchpointId,
                    "starttime": _starttime,
                    "endtime": _endtime,
                    netAlarmChart:{
                        titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        url:"/alarmLog/getRemoteMonLogInfo.do",
                        ipmCenterId:ipmCenterId,
                        watchpointId:watchpointId,
                        moduleId:moduleId,
                        columnNum:10,
                        handledflag:"N",
                        businessId:moduleId == 10 ? watchpointId :busiId,
                        starttime:_starttime,
                        endtime:_endtime
                    },
                    fsParams:{
                        url:fs_url,
                        plotIdsArray:fs_plotIdsArray,
                        ajaxParam:{
                            ipmCenterId: ipmCenterId,
                            watchpointId: watchpointId,
                            moduleId: moduleId,
                            plotIds:fs_plotIds,
                            dblclickUrl:fs_dblclickUrl,
                            busiId: busiId,
                            clientId: busiId,
                            serverId: busiId,
                            // starttime:_endtime-_starttime !=600?_starttime:undefined,
                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                            // endtime:_endtime-_starttime !=600?_endtime:undefined
                        }
                    }
                });
            });
            /**
             * XPM下拉框更改
             * 四个小图形的url以及id应该设置为变量
             */
            $("#xpm_select").change(function () {
                ipmCenterId = $(this).children("option:selected").attr("data-ipmCenterId");
                /**
                 * 改变观察点下拉框的值
                 */
                $.ajax({
                    url:"watchpointController/getFindAll.do",
                    method:"POST",
                    data:{
                        ipmCenterId:ipmCenterId,
                        moduleId:10
                    },
                    dataType:"json",
                    beforeSend:function (XMLHttpRequest) {},
                    success:function(data,textStatus,XMLHttpRequest){
                        $("#obser_select").empty().append('<option  title="观察点"  disabled="disabled">观察点</option>');
                        data.forEach(function (item,index) {
                            if(!index){
                                watchpointId = item.id;
                            }
                            $("#obser_select").append('<option data-watchpointId="'+item.id+'" title="观察点" >'+item.name+'</option>');
                        });
                        if(moduleId-10){
                            $.ajax({
                                url:tabUrl,
                                method:"POST",
                                data:{
                                    ipmCenterId:ipmCenterId,
                                    moduleId: moduleId
                                },
                                dataType:"json",
                                beforeSend:function (XMLHttpRequest) {},
                                success:function (data,textStatus,XMLHttpRequest) {
                                    $("#bus_select").empty().append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                                    data.forEach(function (item,index) {
                                        if(!index){
                                            busiId = item.id;
                                        }
                                        $("#bus_select").append('<option data-busiId="'+item.id+'" title="监控对象">'+item.name+'</option>')
                                    });
                                    if(data.length){
                                        $("#bus_select").removeClass("none");
                                        _chart._createChart({
                                            type: moduleId,
                                            ipmCenterId: ipmCenterId,
                                            selectDataId: moduleId == 10? watchpointId : busiId,
                                            watchpointId:watchpointId,
                                            starttime: _starttime,
                                            endtime: _endtime,
                                            netAlarmChart:{
                                                titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                                itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                                url:"/alarmLog/getRemoteMonLogInfo.do",
                                                ipmCenterId:ipmCenterId,
                                                watchpointId:watchpointId,
                                                moduleId:moduleId,
                                                columnNum:10,
                                                handledflag:"N",
                                                businessId:moduleId == 10 ? watchpointId :busiId,
                                                starttime:_starttime,
                                                endtime:_endtime
                                            },
                                            fsParams:{
                                                url:fs_url,
                                                plotIdsArray:fs_plotIdsArray,
                                                ajaxParam:{
                                                    ipmCenterId: ipmCenterId,
                                                    watchpointId: watchpointId,
                                                    moduleId: moduleId,
                                                    busiId: busiId,
                                                    clientId: busiId,
                                                    serverId: busiId,
                                                    plotIds:fs_plotIds,
                                                    dblclickUrl:fs_dblclickUrl,
                                                    // starttime:_endtime-_starttime !=600?_starttime:undefined,
                                                    starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                                                    endtime:_chart.netCockpitHuiSu?_endtime:undefined
                                                    // endtime:_endtime-_starttime !=600?_endtime:undefined
                                                }
                                            }
                                        });
                                        _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]))
                                    }else {
                                        $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                                        busiId = undefined;
                                        defaultCockpit.removelinedraw();
                                        jeBox.alert("请先添加"+$("#busNamecn_select").children("option:selected").text().replace("服务","")+"业务");
                                        _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]))
                                    }
                                },
                                error:function (XMLHttpRequest,textStatus,errorThrown) {
                                    console.error(XMLHttpRequest);
                                    console.error(textStatus);
                                    console.error(errorThrown);
                                },
                                complete:function (XMLHttpRequest,textStatus) {}
                            })
                        }else {
                            busiId = undefined;
                            $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                            _chart._createChart({
                                type: moduleId,
                                ipmCenterId: ipmCenterId,
                                selectDataId: moduleId == 10? watchpointId : busiId,
                                starttime: _starttime,
                                endtime: _endtime,
                                netAlarmChart:{
                                    titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                    itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                    url:"/alarmLog/getRemoteMonLogInfo.do",
                                    ipmCenterId:ipmCenterId,
                                    watchpointId:watchpointId,
                                    moduleId:moduleId,
                                    columnNum:10,
                                    handledflag:"N",
                                    businessId:moduleId == 10 ? watchpointId :busiId,
                                    starttime:_starttime,
                                    endtime:_endtime
                                },
                                fsParams:{
                                    url:fs_url,
                                    plotIdsArray:fs_plotIdsArray,
                                    ajaxParam:{
                                        ipmCenterId: ipmCenterId,
                                        watchpointId: watchpointId,
                                        moduleId: moduleId,
                                        plotIds:fs_plotIds,
                                        dblclickUrl:fs_dblclickUrl,
                                        busiId: busiId,
                                        clientId: busiId,
                                        serverId: busiId,
                                        // starttime:_endtime-_starttime !=600?_starttime:undefined,
                                        starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                                        endtime:_chart.netCockpitHuiSu?_endtime:undefined
                                        // endtime:_endtime-_starttime !=600?_endtime:undefined
                                    }
                                }
                            });
                            _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]))
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThrown) {
                        console.error(XMLhttpRequest);
                        console.error(textStatus);
                        console.error(errorThrown);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
            });
            /**
             * 业务下拉框更改
             * 此处若为观察点则只显示观察点的下拉框
             * 否则显示观察点下拉框和业务的下拉框
             */
            $("#busNamecn_select").change(function () {
                moduleId = +$(this).children("option:selected").attr("data-moduleId");
                fs_url = $(this).children("option:selected").attr("data-url");
                fs_dblclickUrl = $(this).children("option:selected").attr("data-dblclickUrl");
                fs_plotIdsArray = eval("["+$(this).children("option:selected").attr("data-plotIdsArray")+"]");
                fs_plotIds = $(this).children("option:selected").attr("data-plotIdsArray").replace("[","").replace("]","");
                tabUrl = $(this).children("option:selected").attr("data-tabUrl");
                if(moduleId - 10){
                    $.ajax({
                        url:tabUrl,
                        method:"POST",
                        data:{
                            ipmCenterId:ipmCenterId,
                            moduleId: moduleId
                        },
                        dataType:"json",
                        beforeSend:function (XMLHttpRequest) {},
                        success:function (data,textStatus,XMLHttpRequest) {
                            $("#bus_select").empty().append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                            data.forEach(function (item,index) {
                                if(!index){
                                    busiId = item.id;
                                }
                                $("#bus_select").append('<option data-busiId="'+item.id+'" title="监控对象">'+item.name+'</option>')
                            });
                            if(data.length){
                                $("#bus_select").removeClass("none");
                                _chart._createChart({
                                    type: moduleId,
                                    ipmCenterId: ipmCenterId,
                                    selectDataId: moduleId == 10? watchpointId : busiId,
                                    watchpointId:watchpointId,
                                    starttime: _starttime,
                                    endtime: _endtime,
                                    netAlarmChart:{
                                        titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                        itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                                        url:"/alarmLog/getRemoteMonLogInfo.do",
                                        ipmCenterId:ipmCenterId,
                                        watchpointId:watchpointId,
                                        moduleId:moduleId,
                                        columnNum:10,
                                        handledflag:"N",
                                        businessId:moduleId == 10 ? watchpointId :busiId,
                                        starttime:_starttime,
                                        endtime:_endtime
                                    },
                                    fsParams:{
                                        url:fs_url,
                                        plotIdsArray:fs_plotIdsArray,
                                        ajaxParam:{
                                            ipmCenterId: ipmCenterId,
                                            watchpointId: watchpointId,
                                            moduleId: moduleId,
                                            busiId: busiId,
                                            clientId: busiId,
                                            serverId: busiId,
                                            plotIds:fs_plotIds,
                                            dblclickUrl:fs_dblclickUrl,
                                            // starttime:_endtime-_starttime !=600?_starttime:undefined,
                                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                                            // endtime:_endtime-_starttime !=600?_endtime:undefined
                                        }
                                    }
                                });
                                _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]))
                            }else {
                                $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                                busiId = undefined;
                                defaultCockpit.removelinedraw();
                                jeBox.alert("请先添加"+$("#busNamecn_select").children("option:selected").text().replace("服务","")+"业务");
                                _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]))
                            }
                        },
                        error:function (XMLHttpRequest,textStatus,errorThrown) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThrown);
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    })
                }else {
                    busiId = undefined;
                    $("#bus_select").empty().addClass("none").append('<option  title="监控对象"  disabled="disabled">监控对象</option>');
                    _chart._createChart({
                        type: moduleId,
                        ipmCenterId: ipmCenterId,
                        selectDataId: moduleId == 10? watchpointId : busiId,
                        starttime: _starttime,
                        endtime: _endtime,
                        netAlarmChart:{
                            titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                            itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                            url:"/alarmLog/getRemoteMonLogInfo.do",
                            ipmCenterId:ipmCenterId,
                            watchpointId:watchpointId,
                            moduleId:moduleId,
                            columnNum:10,
                            handledflag:"N",
                            businessId:moduleId == 10 ? watchpointId :busiId,
                            starttime:_starttime,
                            endtime:_endtime
                        },
                        fsParams:{
                            url:fs_url,
                            plotIdsArray:fs_plotIdsArray,
                            ajaxParam:{
                                ipmCenterId: ipmCenterId,
                                watchpointId: watchpointId,
                                moduleId: moduleId,
                                busiId: busiId,
                                clientId: busiId,
                                serverId: busiId,
                                plotIds:fs_plotIds,
                                dblclickUrl:fs_dblclickUrl,
                                starttime:_endtime-_starttime !=600?_starttime:undefined,
                                endtime:_endtime-_starttime !=600?_endtime:undefined
                            }
                        }
                    });
                    _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]));
                }
            });
            /**
             * 观察点值下拉框更改
             * 此处确定一事 此处值改变是否更改业务的值（已确定 无联动关系）
             */
            $("#obser_select").change(function () {
                watchpointId = $(this).children("option:selected").attr("data-watchpointId");
                _chart._createChart({
                    type: moduleId,
                    ipmCenterId: ipmCenterId,
                    selectDataId: moduleId == 10? watchpointId : busiId,
                    watchpointId:watchpointId,
                    starttime: _starttime,
                    endtime: _endtime,
                    netAlarmChart:{
                        titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        url:"/alarmLog/getRemoteMonLogInfo.do",
                        ipmCenterId:ipmCenterId,
                        watchpointId:watchpointId,
                        moduleId:moduleId,
                        columnNum:10,
                        handledflag:"N",
                        businessId:moduleId == 10 ? watchpointId :busiId,
                        starttime:_starttime,
                        endtime:_endtime
                    },
                    fsParams:{
                        url:fs_url,
                        plotIdsArray:fs_plotIdsArray,
                        ajaxParam:{
                            ipmCenterId: ipmCenterId,
                            watchpointId: watchpointId,
                            moduleId: moduleId,
                            busiId: busiId,
                            clientId: busiId,
                            serverId: busiId,
                            plotIds:fs_plotIds,
                            dblclickUrl:fs_dblclickUrl,
                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                        }
                    }
                });
                _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]));
            });
            /**
             * 业务值下拉框更改
             */
            $("#bus_select").change(function () {
                busiId = $(this).children("option:selected").attr("data-busiId");
                _chart._createChart({
                    type: moduleId,
                    ipmCenterId: ipmCenterId,
                    selectDataId: moduleId == 10? watchpointId : busiId,
                    watchpointId:watchpointId,
                    starttime: _starttime,
                    endtime: _endtime,
                    netAlarmChart:{
                        titleId:"draw_title_alarm"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        itemId:"alarm_chartemId"+moduleId+ipmCenterId+watchpointId+(busiId?busiId:0),
                        url:"/alarmLog/getRemoteMonLogInfo.do",
                        ipmCenterId:ipmCenterId,
                        watchpointId:watchpointId,
                        moduleId:moduleId,
                        columnNum:10,
                        handledflag:"N",
                        businessId:moduleId == 10 ? watchpointId :busiId,
                        starttime:_starttime,
                        endtime:_endtime
                    },
                    fsParams:{
                        url:fs_url,
                        plotIdsArray:fs_plotIdsArray,
                        ajaxParam:{
                            ipmCenterId: ipmCenterId,
                            watchpointId: watchpointId,
                            moduleId: moduleId,
                            busiId: busiId,
                            clientId: busiId,
                            serverId: busiId,
                            plotIds:fs_plotIds,
                            dblclickUrl:fs_dblclickUrl,
                            starttime:_chart.netCockpitHuiSu?_starttime:undefined,
                            endtime:_chart.netCockpitHuiSu?_endtime:undefined
                        }
                    }
                });
                _chart._savaNetCockpitIds(String([ipmCenterId,moduleId,watchpointId,busiId]));
            });
            /**
             * 时间回溯
             */
            $(".timesure").click(function () {
                if ($("#inpstart").val() && $("#inpend").val()) {
                    _starttime = $.myTime.DateToUnix($("#inpstart").val());
                    _endtime = $.myTime.DateToUnix($("#inpend").val());
                    if(_starttime-_endtime>0){
                        jeBox.alert("结束时间必须大于开始时间");
                        return;
                    }
                    clearInterval(timer2);
                    clearInterval(timer3);
                    _chart.refrebar = true;
                    _chart.netCockpitHuiSu = true;
                    refreshTableData(_starttime, _endtime,"netCockpit");
                    if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                        (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                        $(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                    } else {
                        var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                        $(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                    }
                    $(".timeBackText").attr({
                        "data-strTime":_starttime,
                        "data-endTime":_endtime
                    });
                    $('a[data-drawer="times"]').trigger("click");
                }
            });
            /**
             * 刷新图形和表格数据（此页面无表格）
             * @param starttime
             * @param endtime
             * @param netCockpit
             */
            function refreshTableData(starttime, endtime,netCockpit) {
                _chart.reloadChart(
                    watchpointId,
                    {
                        "busiId": busiId,
                        "timeback":netCockpit
                    },
                    starttime?starttime:_starttime,
                    endtime?endtime:_endtime,
                    ipmCenterId
                );
            }
            /**
             * 刷新力度
             */
            $('input[name="timelidu"]').click(function () {
                var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
                if(timeVal){
                    if(timeVal){
                        $.ajax({
                            url:"/watchpointController/updateUserConfigureByKey.do",
                            type:"post",
                            data:{
                                key: "dataRefreshTime",
                                value:timeVal/1000
                            },
                            dataType:"json",
                            success:function (data) {
                                if(!data){
                                    jeBox.alert("修改时间粒度失败，请稍后再试");
                                }else {
                                    clearInterval(timer2);
                                    clearInterval(timer3);
                                    timer2 = setInterval(timeBackText, timeVal);
                                    timer3 = setInterval(refreshTableData, timeVal);
                                    _chart.netCockpitHuiSu = undefined;
                                }
                                $('a[data-drawer="refresh"]').trigger("click");
                            },
                            error:function () {
                                jeBox.alert("修改时间粒度失败，请稍后再试");
                                $('a[data-drawer="refresh"]').trigger("click");
                            }
                        })
                    }
                }else {
                    _chart.netCockpitHuiSu = undefined;
                    location.reload();
                }
            });
            /**
             * 单击箭头跳转页面
             */
            $("#content").on("click","#nextPage",function () {
                var pageName = $("#busNamecn_select").children("option:selected").attr("data-pageName"),
                    monitorId = $("#busNamecn_select").children("option:selected").attr("data-monitorId");
                if(busiId){
                    location.href = pageName + ".html?ipmCenterId="+ipmCenterId + "&watchpointId="+watchpointId + "&busiId="+monitorId + "&databsId="+busiId;
                }else {
                    location.href = pageName + ".html?ipmCenterId="+ipmCenterId + "&watchpointId="+watchpointId + "&busiId="+monitorId;
                }
            });
        }
    };
    switch (defaultCockpit.pageName) {
        case "observationPointkpi":
            defaultCockpit.pubTabConfig("centerTable",10,"观察点",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "userSidekpi":
            defaultCockpit.pubTabConfig("userSidekpi",11,"客户端",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "serverSidekpi":
            defaultCockpit.pubTabConfig("serverSidekpi",12,"服务端",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "httpSerkpi":
            defaultCockpit.pubTabConfig_buSer("httpSerkpi", 4, "HTTP",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "oracleSerkpi":
            defaultCockpit.pubTabConfig_buSer("oracleSerkpi", 5, "ORACLE",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "mysqlSerkpi":
            defaultCockpit.pubTabConfig_buSer("mysqlSerkpi", 6, "MYSQL",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "sqlSerkpi":
            defaultCockpit.pubTabConfig_buSer("sqlSerkpi", 7, "SQLSERVER",+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "urlkpi":
            defaultCockpit.pubTabConfig_url(+$('input[name="timelidu"]:checked').val()*1000);//之前是60000
            break;
        case "baowenJy":
            defaultCockpit.pubTabConfig_buSer("baowenJySerkpi", 9, "报文",+$('input[name="timelidu"]:checked').val()*1000);//之前是60000
            break;
        case "systemCapital":
            defaultCockpit.systemCapital(+$('input[name="timelidu"]:checked').val()*1000);
            break;
        case "netCockpit":
            defaultCockpit.XPM_netCockpit(+$('input[name="timelidu"]:checked').val()*1000);
            break;
        default:
            jeBox.alert("未书写此页面的js代码");
    }
});