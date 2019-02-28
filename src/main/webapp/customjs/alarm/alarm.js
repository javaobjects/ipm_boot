/**
 * 告警浮动窗口
 */
$(function() {
	$.post("/userAuthorize/selectAuthorizeByUser.do", { jurisId: 104 }, function(data) {
		if(data.stauts) {
            var html = '<div id="alarmWin">';
            html += '<div class="modal-content">';
            html += '<div id="alarmWinHeader" class="modal-header" style="cursor: move;">';
            html += '<span class="modal-title">告警信息</span>';
            html += '<i id="alarmWinMaximize" class="glyphicon glyphicon-plus"></i>';
            html += '<i id="alarmWinMinimize" class="glyphicon glyphicon-minus"></i>';
            html += '</div>';
            html += '<div id="alarmWinBody" class="modal-body" style="height: 380px; overflow-y: auto;padding: 0;">';
            html += '<div class="togglebox">';
            html += '<table id="alarmTable" style="cursor:pointer;"></table>';
            html += '</div>';
            html += '</div>';
            html += '<div id="alarmWinFooter" class="modal-footer" style="margin-top: 0;border: 1px solid rgba(255,255,255,.2);padding:5px 5px;text-align: center;">';
            html += '<button type="button" class="btn btn-default btn-sm btn-alarmStyle" id="alarmSta" style="font-size: 13px;padding:3px 10px;">告警统计</button>';
            html += '<button type="button" class="btn btn-default btn-sm btn-alarmStyle" id="totalResponse" style="font-size: 13px;padding:3px 10px;position: relative; left: 20px;">全部响应</button>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            $("body").eq(0).prepend(html);
            var columns = [{
                field: "alarmLevelId",
                title: "级别",
                sortable: true,
                formatter: function(v) {
                    var img = "";
                    if (v == 2) {
                        img = '<img style="padding-left: 8px;" src="/img/alarm/pt.png" title="普通">';
                    } else if (v == 3) {
                        img = '<img style="padding-left: 8px;" src="/img/alarm/zy.png" title="重要">';
                    } else {
                        img = '<img style="padding-left: 8px;" src="/img/alarm/jj.png" title="紧急">';
                    }
                    return img;
                }
            },
            {
                field: "starttime",
                title: "时间",
                sortable: true,
                formatter: function(v) {
                    return $.timeStampDate(v, 'DD hh:mm');
                }
            },
            {
                field: "businessName",
                title: "告警来源",
                sortable: true,
                formatter: function(v, d) {
                    return [d.ipmCenterName, d.businessName, d.kpisDisplayName].join("/");
                }
            }];
            
            var alarmParams = {
                sortStr : "desc",
                handledflag : "N",
                limitNum : 50
            };
            
            var clickFun = function(data, r) {
                var plotId = data.id,
                    plotTypeId = data.types[0].id,
                    chartUtl = null,
                    starttime = r.starttime,
                    endtime = r.endtime,
                    urlStart = null;
                if(r.moduleId < 10) {
                    urlStart = "bssSession.html";
                } else {
                    urlStart = "commun_queue.html";
                }
                var url = urlStart + '?' + 'ipmCenterId=' + r.ipmCenterId + '&plotId=' 
                    + plotId + '&plotTypeId=' + plotTypeId + '&starttime=' + starttime 
                    + '&endtime=' + endtime + '&triggerflag=' + r.triggerflag + "&moduleId=" + r.moduleId;
                switch (r.moduleId) {
                    case 10:
                        chartUrl = "/watchpointController/getWatchpointGraphical.do";
                        url += ("&watchpointId=" + r.businessId);
                        break;
                    case 11:
                        chartUrl = "/client/getClientGraphical.do";
                        url += ("&watchpointId=" + r.watchpointId + "&clientId=" + r.businessId);
                        break;
                    case 12:
                        chartUrl = "/serverManagement/getServerSideGraphical.do";
                        url += ("&watchpointId=" + r.watchpointId + "&serverId=" + r.businessId);
                        break;
                    default:
                        chartUrl = "/appController/getPlotData.do";
                        url += ("&moduleId=" + r.moduleId + "&watchpointId="
                                + r.watchpointId + "&busiId=" + r.businessId);
                        break;
                }
                url += '&chartUrl=' + chartUrl;
                location.href = url;
            } 
            $.ptcsBSTable("alarmTable", "/alarmLog/getRemoteAlarmLogData4Win.do", alarmParams, {
                columns: columns,
                ipm_title: "告警信息",
                search: false,
                pagination: false,
                showExport: false,
                showColumns: false,
                ipm_shrink: false,
                ipm_show_title: false,
                ipm_column_save: false,
                sortName: "starttime",
                sortOrder: "desc",
                onClickRow: function(r) {
                	if (r.triggerflag == 4) { // 组合告警
                		jeBox.alert("组合告警无法钻取！");
                		return;
                	}
                    $.post("/plot/getPlotByModuleKpiId.do", {
                        moduleId: r.moduleId,
                        kpiId: r.kpisId
                    }, function(data) {
                        $.post("/systemSet/readDateTimeSet.do", null, function(sd) {
                            var serverDate = $.timeStampDate(sd.nowTime);
                            // 服务器时间与告警时间时间跨度超过一天则提示用户
                            if (serverDate - r.starttime >= 86400) {
                                jeBox.alert('告警时间距现在间隔时间较长,线图粒度会放大!', function(index){
                                	clickFun(data, r);
                                    jeBox.close(index);
                                }); 
                            } else {
                                clickFun(data, r);
                            }
                        });
                    }, "json");
                }
            });
            $("#alarmWinMinimize").click(function(e) {
                $("#alarmWinBody").hide();
                $("#alarmWinFooter").hide();
                $("#alarmWinMinimize").hide();
                $("#alarmWinMaximize").show();
                $("#alarmWin").removeAttr("style").css({
                    "bottom": 30,
                    "right": 20,
                    "height":35
                });
                e.stopPropagation();
                $("#alarmWinHeader").css("background","rgba(0,0,0,.3)");
            });
            $("#alarmWinMaximize").click(function(e) {
                $("#alarmWinBody").show();
                $("#alarmWinFooter").show();
                $("#alarmWinMinimize").show();
                $("#alarmWinMaximize").hide();
                $("#alarmWin").removeAttr("style").css({
                    "bottom": 30,
                    "right": 20,
                    "height":454
                });
                $("#alarmWinHeader").css("background","rgba(0,0,0,.88)");
                e.stopPropagation();
            }).hide();
            $("#alarmWin").Tdrag({
                scope: "body",
                handle: "#alarmWinHeader",
                cbMove: function(self, obj) {
                    if ($("#alarmWinBody").css("display") != "none") {
                        if ($(self).css("top").replace(/px/, "") - 0 + $(self).children(".modal-content").outerHeight() > $("body").height()) {
                            $(self).css("top", $("body").height() - $(self).children(".modal-content").outerHeight());
                        }
                    } else {
                        if ($(self).css("top").replace(/px/, "") - 0 + $("#alarmWinHeader").outerHeight() + 35 > $("body").height()) {
                            $(self).css("top", $("body").height() - $(self).height() - 35);
                        }
                    }
                }
            });
            $("#alarmWinMinimize").trigger("click");
            $("#alarmSta").click(function() {
                location.href = "/alarmSetting.html";
            });
            $("#totalResponse").click(function(){
                var data = $("#alarmTable").bootstrapTable("getData");
                if(data.length != 0) {
                    var alarmResp = function(info, isPrompt) {
                        $.post("/alarmLog/updateAlarmLogs.do", { 
                           ipmCenterId: info[0].ipmCenterId, 
                           ids: info[0].ids.join(",") 
                       }, function(bool) {
                            if (isPrompt) {
                                if(bool == true || bool == "true") {
                                    $.ptcsBSTabRefresh("alarmTable", alarmParams);
                                }
                            } else {
                                info.shift();
                                alarmResp(info, info.length == 1);
                            }
                        });
                    };
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
                    }
                }
            });
            
            // 每一分自动刷新
            setInterval(function() {
                $.ptcsBSTabRefresh("alarmTable", alarmParams);
            }, 60000);
		}
    }, "json");
});