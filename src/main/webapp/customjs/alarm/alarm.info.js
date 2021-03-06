/**
 * 通信对页面
 */
$(function() {
	
	/**
	 * 参数列表添加展示隐藏
	 */
	$.customEleShrink({ domId: 'cond_query' });
    
	/**
	 * 添加下拉框信息
	 * @domId String 下拉框ID
	 * @data {} 数据
	 */
    var addOption = function(domId, data, isDef) {
        var option = isDef ? "" : "<option value=0>请选择...</option>";
        for(var i = 0, len = data.length; i < len; i++) {
            option += "<option value=" + data[i].id + ">" + (data[i].name || data[i].namezh) + "</option>";
        }
        $("#" + domId).append(option);
    };
    
    /**
     * 设置时间 秒级时间戳
     * @starttime number 开始时间
     * @endtime number 结束时间 
     */
    var setFormTime = function(starttime, endtime) {
    	$("#starttime").val("");
    	$("#endtime").val("");
        if(starttime && endtime) {
            var startArr = $.timeStampDate(urlParams.starttime, "YYYY MM DD hh mm ss").split(" "),
                endArr = $.timeStampDate(urlParams.endtime, "YYYY MM DD hh mm ss").split(" ");
            $("#starttime").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[ { YYYY: startArr[0], MM: startArr[1], DD: startArr[2],
                    hh: startArr[3], mm: startArr[4], ss: startArr[5] }, false]
            });
            
            $("#endtime").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[ { YYYY: endArr[0], MM: endArr[1], DD: endArr[2],
                    hh: endArr[3], mm: endArr[4], ss: endArr[5] }, false]
            });
        } else {
            $("#starttime").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[{ mm: -10, ss: -new Date().getSeconds() }, true]
            });
            
            $("#endtime").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[{ ss: 00 }, false]
            });
        }
    };
    
    var urlParams = $.getUrlParams(), columns = [
        { field: "starttime", title: "开始时间", class: "textNowrap", sortable: true, formatter: function(v) {
            return $.timeStampDate(v);
        } },
        { field: "endtime", title: "结束时间", class: "textNowrap", sortable: true, formatter: function(v) {
            return $.timeStampDate(v);
        } },
        { field: "ipmCenterName", title: "用户名称", class: "textNowrap", sortable: true },
        { field: "watchpointName", title: "告警来源", class: "textNowrap", sortable: true, formatter: function(v) {
            if(v == "") {
                v = null;
            }
            return v;
        } },
        { field: "moduleName", title: "模块名称", class: "textNowrap", sortable: true },
        { field: "businessName", title: "业务名称", class: "textNowrap", sortable: true },
        { field: "kpisDisplayName", title: "KPI名称", class: "textNowrap", sortable: true },
        { field: "triggerflagStr", title: "告警类型", class: "textNowrap", sortable: true },
        { field: "alarmLevelId", title: "告警级别", class: "textNowrap", sortable: true, formatter: function(v) {
        	var type = null;
            if(v == 2) {
                type = "普通";
            } else if(v == 3) {
            	type = "重要";
            } else {
                type = "紧急";
            }
            
            return type;
        } },
        { field: "handledflag", title: "响应状态", sortable: true, formatter: function(v) {
            var state = "未响应";
            if(v == "Y") {
                state = "已响应";
            }
            
            return state;
        } },
        { field: "responseuserDisplayName", title: "响应人", class: "textNowrap", sortable: true },
        { field: "responsetime", title: "响应时间", class: "textNowrap", sortable: true, formatter: function(v) {
        	var time = null;
            if(v != 0) {
                time = $.timeStampDate(v);
            }
            
            return time;
        } },
        { field: "cause", title: "可能产生原因", class: "textNowrap", sortable: true },
        { field: "contacts", title: "联系人", class: "textNowrap", sortable: true },
        { field: "telephone", title: "电话", class: "textNowrap", sortable: true },
        { field: "email", title: "Email", class: "textNowrap", sortable: true }
    ], selectRow = null, isFirst = true;
    setFormTime(urlParams.starttime, urlParams.endtime);
    
    $.post("/center/getCenterIpInfo.do", null, function(data) {
        addOption("xpmserver", data, true);
        $("#xpmserver").change(function() {
            refWatchpointData();
        }).selectpicker('refresh');
        $.post("/authorizeModuleController/getFindAll.do", null, function(data) {
        	var tmpData = [];
        	for (var i = 0, len = data.length; i < len; i ++) {
        	   if (data[i].id < 4 || data[i].id == 10) {
        	   	   continue;
        	   } else if (data[i].id == 13) {
        	       continue;
        	   }
        	   tmpData.push(data[i]);
        	}
        	addOption("moduletype", tmpData, true);
        	$("#moduletype").change(function() {
        	   refAppData();
        	}).selectpicker('refresh');
        	$.post("/plot/getAllKpis.do", null, function(data) {
        		addOption("kpi", data, true);
                $("#kpi").selectpicker('refresh');
                if(urlParams.ipmCenterId) {
                    $("#xpmserver").selectpicker('val', urlParams.ipmCenterId);
                    refWatchpointData();
                } else {
                    $("#watchpoint").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
                    $("#moduletype").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
                    $("#appname").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
                    setDefData();
                    $("#form_submit").click();
                }
        	}, "json");
        }, "json");
    }, "json");
    
    /**
     * 观察点信息
     */
    var refWatchpointData = function() {
        var centerId = parseInt($("#xpmserver").selectpicker('val'));
        if(centerId != 0) {
            $("#watchpoint").prop("disabled", false).selectpicker('refresh');
            $("#moduletype").prop("disabled", false).selectpicker('refresh');
            $("#appname").prop("disabled", false).selectpicker('refresh');
            $.post("/watchpointController/getFindAll.do", { ipmCenterId: centerId, moduleId: 10 }, function(data) {
                $("#watchpoint").empty();
                addOption("watchpoint", data);
                $("#watchpoint").selectpicker('refresh');
                if (isFirst) {
                    setDefData();
                    refAppData();
                }
            }, "json");
        } else {
            $("#watchpoint").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
            $("#moduletype").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
            $("#appname").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
        }
    };
    
    /**
     * 业务信息
     */
    var refAppData = function() {
        var centerId = parseInt($("#xpmserver").selectpicker('val'));
        var moduleId = parseInt($("#moduletype").selectpicker('val'));
        if (moduleId != 0) {
            var appUrl = null;
            switch(moduleId) {
                case 10: // 观察点
                    appUrl = "/watchpointController/getFindAll.do";
                    break;
                case 11: // 客户端
                    appUrl = "/client/getClient.do";
                    break;
                case 12: // 服务端
                    appUrl = "/serverManagement/getAllServerSide.do";
                    break;
                default: 
                    appUrl = "/appController/getAllAppByModuleId.do";
                    break;
            }
            $.post(appUrl, { ipmCenterId: centerId, moduleId: moduleId }, function(data) {
            	$("#appname").empty();
                addOption("appname", data);
                $("#appname").selectpicker('refresh');
                if(isFirst) {
                    var busiId = urlParams.clientId || urlParams.serverId || urlParams.busiId || 0; 
                    $("#appname").selectpicker('val', busiId);
                    $("#form_submit").click();
                }
            }, "json");
        } else if(isFirst) {
            $("#form_submit").click();
        }
    };
    
    /**
     * 设置默认值
     */
    var setDefData = function() {
    	var moduleId = 0;
    	if (urlParams.clientId) {
    	    moduleId = 11;
    	} else if(urlParams.serverId) {
    	    moduleId = 12;
    	} else if(urlParams.moduleId) {
    	    moduleId = parseInt(urlParams.moduleId); 
	    }
        if(moduleId == 10) {
        	moduleId = 0;
        }
	    if(urlParams.watchpointId) {
            $("#watchpoint").selectpicker('val', urlParams.watchpointId);
	    }
	    if(moduleId != 0) {
            $("#moduletype").selectpicker('val', moduleId);
	    }
        if(urlParams.kpi) {
            $("#kpi").selectpicker('val', urlParams.kpi);
        }
        if(urlParams.state) {
            $("#state").selectpicker('val', urlParams.state);
        }
        if(urlParams.alarmType) {
            $("#alarmType").selectpicker('val', urlParams.alarmType);
        }
    }
    
    $("#form_submit").click(function() {
    	if(!$("#starttime").val().trim()) {
    	   jeBox.alert("开始时间不能为空");
    	} else if(!$("#endtime").val().trim()) {
    	   jeBox.alert("结束时间不能为空");
    	} else {
            var params = {
                starttime: $.timeStampDate($("#starttime").val()),
                endtime: $.timeStampDate($("#endtime").val())
            };
            if(params.starttime >= params.endtime) {
            	jeBox.alert("开始时间不能大于等于结束时间");
                return;
            }
            /*
             * 为右上角赋值时间
             * 判断开始时间和结束时间是否与现在年相同
             * 若都满足则不显示年否则显示年
             *
             */
            if((new Date().getFullYear()-$("#starttime").val().split("-")[0])||
                (new Date().getFullYear()-$("#endtime").val().split("-")[0])){
                $(".timeBackText").text($("#starttime").val()+" ~ "+$("#endtime").val())
            }else {
                var index = $.myTime.UnixToDate($.getDefaultEndtime()-600).split("-")[0].length+1;
                $(".timeBackText").text($("#starttime").val().slice(index)+" ~ "+$("#endtime").val().slice(index))
            }

            params = $.extend(paramsFun(), params);
            $.drawHChart("draw_charts", "draw_title", "/alarmLog/getRemoteMonLogInfo.do", params, function(option) {
                $.ptcsBSTabRefresh("alarmInfoTab", $.extend(paramsFun(), {
                    starttime: option.starttime,
                    endtime: option.endtime,
                    alarmLevelIds: option.alarmLevel
                }));
            });
            if(isFirst) {
            	var alarmResp = function(info, isPrompt) {
            	    $.post("/alarmLog/updateAlarmLogs.do", { 
            	       ipmCenterId: info[0].ipmCenterId, 
            	       ids: info[0].ids.join(",") 
        	       }, function(bool) {
            	    	if (isPrompt) {
                            if(bool == true || bool == "true") {
                                $("#form_submit").click();
                            }
            	    	} else {
            	    		info.shift();
            	    	    alarmResp(info, info.length == 1);
            	    	}
                    });
            	}
                $.ptcsBSTable("alarmInfoTab", "/alarmLog/getRemoteAlarmLogData.do", params, {
                    columns: columns,
                    ipm_title: "告警信息",
                    ipm_shrink: false,
                    ipm_column_save: false,
                    rowStyle: function(row, i) {
                        var cla = {};
                        if(i == 0) {
                            cla.classes = "custom-row-style";
                            selectRow = row;
                        }
                        return cla;
                    },
                    onClickRow: function(r, tr) {
                        $("#alarmInfoTab > tbody > .custom-row-style").removeClass();
                        $(tr).addClass("custom-row-style");
                        selectRow = r;
                    },
                    onDblClickRow: function(r, tr) {
                        if(parseInt(r.kpitype) == 2) { // 组合告警
                        	txAlarmInfoTab(r);
                        	var tabDiv = $("#tables").children("div");
                            if(tabDiv.length - 1) {
                            	var content = $("#content"); 
                                content.animate({ scrollTop : content.outerHeight()+ tabDiv.outerHeight() }, 800);
                            }
                        } else { // 普通告警
                            $.post("/systemSet/readDateTimeSet.do", null, function(sd) {
                                var serverDate = $.timeStampDate(sd.nowTime);
                                // 服务器时间与告警时间时间跨度超过一天则提示用户
                                if (serverDate - r.starttime >= 86400) {
                                    jeBox.alert('告警时间距现在间隔时间较长,线图粒度会放大!', function(index){
                                        alarmClickFun(r);
                                        jeBox.close(index);
                                    }); 
                                } else {
                                    alarmClickFun(r);
                                }
                            });
                        }
                    },
                    ipm_toolbar: [
                        { name: "全部响应", type: "bell", call: function() {
                        	var data = $("#alarmInfoTab").bootstrapTable("getData");
                        	if(data.length != 0) {
                        		var ids = {}, count = 0, centerId = 0, tmpArr = 0;
                        	    for(var i = 0; i < data.length; i ++) {
                        	    	if (data[i].handledflag == "N") {
                            	    	centerId = data[i].ipmCenterId;
                            	    	if(ids[centerId]) {
                            	    	    ids[centerId].push(data[i].id);
                            	    	} else {
                            	    	    ids[centerId] = [data[i].id];
                            	    	    count ++;
                            	    	}
                        	    	}
                        	    }
                        	    if(count != 0) {
                        	    	// 循环响应
                        	    	var tmpArr = [];
                        	    	for (var key in ids) {
                        	    		tmpArr.push({
                        	    		    ipmCenterId: key,
                        	    		    ids: ids[key]
                        	    		});
                        	    	}
                        	    	alarmResp(tmpArr, count == 1);
                        	    } else {
                        	       jeBox.alert("当前告警已经全部响应，无需再响应");
                        	    }
                        	}
                        }},
                        { name: "响应", type: "bullhorn", call: function() {
                        	if(selectRow.handledflag == "Y") {
                        	   jeBox.alert("此告警已响应，不能重复响应");
                        	} else {
                                $.post("/alarmLog/updateAlarmLog.do", { 
                                    ipmCenterId: selectRow.ipmCenterId, 
                                    id: selectRow.id 
                                }, function(bool) {
                                    if(bool == true || bool == "true") {
                                        $("#form_submit").click();
                                    } else {
                                    	jeBox.alert("响应失败");
                                    }
                                });
                        	}
                        }},
                        { name: "删除", type: "remove", call: function() {
                            $.post("/alarmLog/deleteAlarmLog.do", { 
                                ipmCenterId: selectRow.ipmCenterId, 
                                id: selectRow.id 
                            }, function(bool) {
                                if(bool == true || bool == "true") {
                                    $("#form_submit").click();
                                } else {
                                    jeBox.alert("删除失败");
                                }
                            });
                        }}
                    ]
                });
                isFirst = false;
            } else {
                $.ptcsBSTabRefresh("alarmInfoTab", params);
            }
            $("#txAlarmInfoTabDiv").hide(800);
            $("#content").animate({scrollTop : 0}, 800);
    	}
    });
    
    var txAlarmInfoTab = function(r) {
    	var params = {
            ipmCenterId: r.ipmCenterId,
            id: r.id,
            starttime: r.starttime,
            endtime: r.endtime
        };
        if($("#txAlarmInfoTab").length == 0) {
            var selectRow = null, columns = [
                { field: "starttime", title: "开始时间", sortable: true, formatter: function(v) {
                    return $.timeStampDate(v);
                } },
                { field: "endtime", title: "结束时间", sortable: true, formatter: function(v) {
                    return $.timeStampDate(v);
                } },
                { field: "ipmCenterName", title: "XPM服务器名称", sortable: true },
                { field: "watchpointName", title: "告警来源", sortable: true, formatter: function(v) {
                    if(v == "") {
                        v = null;
                    }
                    return v;
                } },
                { field: "moduleName", title: "模块名称", sortable: true },
                { field: "businessName", title: "业务名称", sortable: true },
                { field: "kpisDisplayName", title: "KPI名称", sortable: true },
                { field: "triggerflagStr", title: "告警类型", sortable: true },
                { field: "alarmLevelId", title: "告警级别", sortable: true, formatter: function(v) {
                    var type = null;
                    if(v == 2) {
                        type = "普通";
                    } else if(v == 3) {
                        type = "重要";
                    } else {
                        type = "紧急";
                    }
                    
                    return type;
                } }
            ];
            $("#tables").append("<div id='txAlarmInfoTabDiv'><table id ='txAlarmInfoTab'></table></div>");
            $.ptcsBSTable("txAlarmInfoTab", "/customUnionKpi/getRemoteUnionKpiLog.do", params, {
                columns: columns,
                ipm_title: "组合告警详细信息",
                ipm_shrink: false,
                ipm_column_save: false,
                rowStyle: function(row, i) {
                    var cla = {};
                    if(i == 0) {
                        cla.classes = "custom-row-style";
                        selectRow = row;
                    }
                    return cla;
                },
                onClickRow: function(r, tr) {
                    $("#txAlarmInfoTab > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = r;
                },
                onDblClickRow: function(r, tr) {
                    $.post("/systemSet/readDateTimeSet.do", null, function(sd) {
                        var serverDate = $.timeStampDate(sd.nowTime);
                        // 服务器时间与告警时间时间跨度超过一天则提示用户
                        if (serverDate - r.starttime >= 86400) {
                            jeBox.alert('告警时间距现在间隔时间较长,线图粒度会放大!', function(index){
                            	alarmClickFun(r);
                                jeBox.close(index);
                            }); 
                        } else {
                        	alarmClickFun(r);
                        }
                    });
                }
            });
        } else {
            $("#txAlarmInfoTabDiv").show();
            $.ptcsBSTabRefresh("txAlarmInfoTab", params);
        }
    }
    
    var alarmClickFun = function(r) {
        $.post("/plot/getPlotByModuleKpiId.do", { moduleId: r.moduleId, kpiId: r.kpisId }, function(data) {
            var plotId = data.id,
                plotTypeId = data.types[0].id,
                chartUtl = null,
                starttime = r.starttime,
                endtime = r.endtime,
                urlStart = null,
                triggerflag = r.triggerflag;
            if(r.moduleId < 10) {
                urlStart = "bssSession.html";
            } else {
                urlStart = "commun_queue.html";
            }
            var url = urlStart + '?' + 'ipmCenterId=' + r.ipmCenterId + '&plotId=' 
                + plotId + '&plotTypeId=' + plotTypeId + '&starttime=' + starttime 
                + '&endtime=' + endtime + '&triggerflag=' + triggerflag;
            switch (r.moduleId) {
                case 10:
                    chartUrl = "/watchpointController/getWatchpointGraphical.do";
                    url += ("&moduleId=10&watchpointId=" + r.businessId);
                    break;
                case 11:
                    chartUrl = "/client/getClientGraphical.do";
                    url += ("&moduleId=11&watchpointId=" + r.watchpointId + "&clientId=" + r.businessId);
                    break;
                case 12:
                    chartUrl = "/serverManagement/getServerSideGraphical.do";
                    url += ("&moduleId=12&watchpointId=" + r.watchpointId + "&serverId=" + r.businessId);
                    break;
                default:
                    chartUrl = "/appController/getPlotData.do";
                    url += ("&moduleId=" + r.moduleId + "&watchpointId="
                            + r.watchpointId + "&busiId=" + r.businessId);
                    break;
            }
            url += '&chartUrl=' + chartUrl;
            location.href = url;
        }, "json");
    }

    var paramsFun = function() {
        var ipmCenterId = $("#xpmserver").selectpicker('val'),
            watchpointId = $("#watchpoint").selectpicker('val'), 
            moduleId = $("#moduletype").selectpicker('val'),
            appId = $("#appname").selectpicker('val'),
            alarmType = $("#alarmType").selectpicker('val'), 
            state = $("#state").selectpicker('val'), 
            kpi = $("#kpi").selectpicker('val'),
            params = {};
        if(ipmCenterId != 0) {
            params.ipmCenterId = ipmCenterId;
            if(moduleId != 0) {
                params.moduleId = moduleId;
                params.watchpointId = watchpointId;
                params.businessId = appId;
            } else if(watchpointId != 0) {
                params.moduleId = 10;
                params.businessId = watchpointId;
            }
        }
        if(alarmType != 0) {
            params.alarmLevelIds = alarmType;
        }
        if(state != 0) {
            params.handledflag = state;
        }
        if(kpi != 0) {
            params.kpisId = kpi;
        }
        
        return params;
    };
    /*
     * 头部时间功能
     */
    $(".timesure").click(function(){
        if(!$("#inpstart").val().trim()) {
            jeBox.alert("开始时间不能为空");
        } else if(!$("#inpend").val().trim()) {
            jeBox.alert("结束时间不能为空");
        } else {
            if($.timeStampDate($("#inpstart").val()) >= $.timeStampDate($("#inpend").val())) {
                jeBox.alert("开始时间不能大于等于结束时间");
                return;
            }else {
                $("#starttime").val($("#inpstart").val());
                $("#endtime").val($("#inpend").val());
                $("#form_submit").trigger("click");
                $('a[data-drawer="times"]').trigger("click");
            }
        }
    })
});