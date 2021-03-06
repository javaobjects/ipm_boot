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
            option += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
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
    
    var urlParams = $.getUrlParams(), // URL参数 
        columns = null, // 表格列
        isFirst = true,  // 是否第一次请求通信队
        plotId = parseInt(urlParams.plotId), // 绘图选项编号
        kpisBean = null, // KPI信息
        sortName = 'ethernetTraffic', // 表格默认流量排序
        sortOrder = 'desc', // 表格默认降序
        openIpLocationFlag = 0, // 地址库开关 
        tabParams = null, // 表格参数
        isListLoadData = true; // 默认是否展示通信队数据
    switch(parseInt(urlParams.triggerflag)) {
        case 0:
            sortOrder = 'desc';
        break;
        case 1:
            sortOrder = 'asc';
        break;
    }
    
    // 最大会话数量线图对应会话数量线图
    switch(plotId) {
        case 323:
            plotId = 2;
        break;
        case 324:
            plotId = 33;
        break;
        case 325:
            plotId = 61;
        break;
    }
    
    // 设置时间
    setFormTime(urlParams.starttime, urlParams.endtime);
    
    /**
     * 获取XMP接入信息
     */
    $.post("/center/getCenterIpInfo.do", null, function(data) {
    	addOption("xpmserver", data, true);
    	$("#xpmserver").change(function() {
            refModuleData();
        }).selectpicker('refresh');
        if(urlParams.ipmCenterId) {
            $("#xpmserver").selectpicker('val', urlParams.ipmCenterId);
            refModuleData();
        } else {
        	setTabColumn();
        }
    }, "json");
    
    $.post("/protoPlan/getProtoPlanList.do", null, function(data) {
        addOption("proto", data, true);
        $("#proto").selectpicker('refresh');
        if(urlParams.proto) {
            $("#proto").selectpicker('val', urlParams.proto);
        }
    }, "json");
    
    /**
     * 刷新模块业务数据
     */
    var refModuleData = function() {
    	var centerId = parseInt($("#xpmserver").selectpicker('val'));
    	if(centerId != 0) {
            $("#watchpoint").prop("disabled", false).selectpicker('refresh');
            $("#client").prop("disabled", false).selectpicker('refresh');
            $("#server").prop("disabled", false).selectpicker('refresh');
            $.post("/watchpointController/getFindAll.do", { ipmCenterId: centerId, moduleId: 10 }, function(data) {
            	$("#watchpoint").empty();
                addOption("watchpoint", data);
                $("#watchpoint").selectpicker('refresh');
                $.post("/client/getClient.do", { ipmCenterId: centerId, moduleId: 11 }, function(data) {
                	$("#client").empty();
                    addOption("client", data);
                    $("#client").selectpicker('refresh');
                    $.post("/serverManagement/getAllServerSide.do", { ipmCenterId: centerId, moduleId: 12 }, function(data) {
                    	$("#server").empty();
                        addOption("server", data);
                        $("#server").selectpicker('refresh');
                        if (isFirst) {
                            setTabColumn();
                        }
                    }, "json");
                }, "json");
            }, "json");
    	} else {
    	    $("#watchpoint").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
    	    $("#client").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
    	    $("#server").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
            if (isFirst) {
                setTabColumn();
            }
    	}
    };
    
    /**
     * 设置表格列
     */
    var setTabColumn = function() {
        setDefData();
        if (plotId) {
            $.ajax({
                async: false,
                url: "/plot/getKpisByPlotId.do",
                type: "post",
                data: {
                    plotId: plotId
                },
                dataType: "json",
                success: function (data) {
                	// 会话数量通信队删除，过滤
                	if (data.id != 2) {
                    	kpisBean = data;
                    	sortName = kpisBean.name;
                	}
                }
            });
        }
        $.post("/commpair/getCommpairListColumn.do", null, function(data) {
            var csArr = [], columnName = null, visible = false, cstmp = null;
            for (var i = 0, len = data.length; i < len; i ++) {
            	columnName = data[i].columnen;
            	if (kpisBean) {
            		if (kpisBean.name == columnName) {
            	        visible = true;
            		} else {
            		    visible = !!data[i].checked;
            		}
            	} else {
                	visible = !!data[i].checked;
            	}
            	cstmp = {
                    id: data[i].id,
                    field: columnName,
                    title: data[i].columnzh,
                    sortable: true,
                    visible: visible
                };
                if(columnName == "startEndstr") {
                    cstmp.formatter = function(v) {
                    	var time = "";
                    	if (v.indexOf("-") == -1) {
                    	    time = $.timeStampDate(v, "MM-DD hh:mm:ss");
                    	} else {
                    		v = v.split("-");
                    	    time = $.timeStampDate(v[0], "MM-DD hh:mm:ss") 
                    	       + " ~ " + $.timeStampDate(v[1], "MM-DD hh:mm:ss");
                    	}
                    	
                        return time;
                    }
                } else if(columnName == "serverport") {
                    cstmp.formatter = function(v) {
                        if(v == 0) {
                            return '';
                        }
                        return v;
                    }
                }
                csArr.push(cstmp);
            }
            columns = csArr;
            $("#form_submit").click();
        }, "json");
    }
    
    /**
     * 设置默认值
     */
    var setDefData = function() {
        if(urlParams.watchpointId) {
            $("#watchpoint").selectpicker('val', urlParams.watchpointId);
        }
        if(urlParams.clientIp) {
            $("#clientip").val(urlParams.clientIp);
        }
        if(urlParams.serverIp) {
            var serverInfo = urlParams.serverIp.split(":");
            $("#serverip").val(serverInfo[0]);
            if(serverInfo[1]) {
                $("#serverport").val(serverInfo[1]);
            }
        }
        if(urlParams.clientId) {
            $("#client").selectpicker('val', urlParams.clientId);
        }
        if (urlParams.serverId) {
            $("#server").selectpicker('val', urlParams.serverId);
        }
    };
    
    /**
     * 集合通信队
     */
    var txTable = function() {
        $("#messsage").hide();
        $("#txTableResponsive").show();
        $.post("/watchpointController/getUserConfigureBeanByKey.do", { key: "openIpLocationFlag" }, function(v) {
            var addState = parseInt(v);
            if(urlParams.areaDictId) { // 地图默认开启地址库
            	addState = 1;
            }
            openIpLocationFlag = addState;
            tabParams.openIpLocationFlag = openIpLocationFlag;
            if(isFirst) {
                $.ptcsBSTable("txTable", "/commpair/getRemoteCommpairMergeData.do", tabParams, {
                    columns: columns,
                    ipm_title: "聚合通信对",
                    ipm_shrink: false,
                    ipm_column_save: false,
                    ipm_server_page: true,
                    sortClass : 'custom-column-style',
                    sortName : sortName,
                    sortOrder : sortOrder,
                    rowStyle: function() {
                        return { css: { "white-space" : "nowrap" } };
                    },
                    ipm_toolbar: [{ name: !addState ? "开启地址库" : "关闭地址库", type: "map-marker", call: function() {
                        if(addState == 1) {
                            addState = 0;
                        } else {
                            addState = 1;
                        }
                        openIpLocationFlag = addState;
                        $(this).attr("title", !addState ? "开启地址库" : "关闭地址库");
                        $.post("/watchpointController/updateUserConfigureByKey.do", 
                            { key: "openIpLocationFlag", value: addState }, function(v) {
                            tabParams.openIpLocationFlag = openIpLocationFlag;
                            $.ptcsBSTabRefresh("txTable", tabParams);
                        });
                    }}],
                    onClickRow: function(row, tr) {
                        $("#txTable > tbody > .custom-row-style").removeClass();
                        $(tr).addClass("custom-row-style");
                        txDetaTab(row);
                        if($("#txTable").parents("#tables").children("div").length-1){
                            $("#content").animate({scrollTop : $("#content").outerHeight()+$("#txTable").parents("#tables").children("div").outerHeight()}, 800);
                        }
                    },
                    onLoadSuccess: function(arr) {
                    	if (arr.length >= 1000) {
                            $.ajax({
                                url: "/commpair/getRemoteCommpairMergeCountData.do",
                                type: "post",
                                async: false,
                                data: tabParams,
                                success: function (data) {
                                	var queueSumCount = parseInt(data);
                                	if (queueSumCount > arr.length) {
                                        jeBox.alert("<div style='text-align:left;margin-left:14px;'>符合聚合条件的数据总数为："
                                            + queueSumCount + "条，列表显示为" + arr.length
                                            + "条，<br>如需查看更多数据，请点击列表下部的“ »”按钮。</div>");
                                    }
                                }
                            });
                        }
                    }
                });
                isFirst = false;
            } else {
                $.ptcsBSTabRefresh("txTable", tabParams);
            }
        });
    }
    
    /**
     * 所选参数与跳转模块是否相同
     */
    var isSameModule = function() {
        var bool = false;
        if (tabParams.watchpointId) {
            var moduleId = parseInt(urlParams.moduleId),
                serverId = parseInt(tabParams.serverId),
                clientId = parseInt(tabParams.clientId);
            if (serverId != 0 && clientId == 0) {
                if (moduleId == 12) {
                    bool = true;
                }
            } else if (serverId == 0 && clientId != 0) {
                if (moduleId == 11) {
                    bool = true;
                } 
            } else if (serverId == 0 && clientId == 0) {
                if (moduleId == 10) {
                    bool = true;
                }
            }
        }
        
        return bool;
    }
    
    $("#form_submit").click(function() {
    	if(!$("#starttime").val().trim()) {
    	   jeBox.alert("开始时间不能为空");
    	} else if(!$("#endtime").val().trim()) {
    	   jeBox.alert("结束时间不能为空");
    	} else {
			tabParams = {
                starttime: $.timeStampDate($("#starttime").val()),
                endtime: $.timeStampDate($("#endtime").val()),
                ipmCenterId: $("#xpmserver").selectpicker("val"),
                clientId: $("#client").selectpicker("val"),
                serverId: $("#server").selectpicker("val"),
                protoPlanId: $("#proto").selectpicker("val"),
                groupType: $("#groupType").selectpicker("val"),
                clientip: $("#clientip").val(),
                serverip: $("#serverip").val()
            };
			
            if(tabParams.starttime >= tabParams.endtime) {
                jeBox.alert("开始时间不能大于等于结束时间");
                return;
            }
            
            if(parseInt(tabParams.ipmCenterId) == 0) {
                delete tabParams.ipmCenterId;
            }
            
            if($("#serverport").val()) {
                tabParams.serverport = $("#serverport").val();  
            } else {
                tabParams.serverport = 0;
            }
            
    		if(urlParams.areaDictId) { // 地图使用，地址库编号
    			tabParams.areaDictId = urlParams.areaDictId;
    		}
    		
    		var watchpointId = $("#watchpoint").selectpicker("val");
	        if(parseInt(watchpointId) == 0) {
	            delete tabParams.watchpointId;
	        } else {
	            tabParams.watchpointId = watchpointId;
	        }
	        
	        // 排序
	        if (plotId && kpisBean) {
	           tabParams.kpiId = kpisBean.id;
	           if (urlParams.triggerflag != undefined) {
	               tabParams.triggerflag = urlParams.triggerflag;
	           }
	        }
	        
	        // 地址库参数
	        tabParams.openIpLocationFlag = openIpLocationFlag;
    		
            /*
             * 为右上角赋值时间
             * 判断开始时间和结束时间是否与现在年相同
             * 若都满足则不显示年否则显示年
             *
             */
            if ((new Date().getFullYear() - $("#starttime").val().split("-")[0])
					|| (new Date().getFullYear() - $("#endtime").val().split("-")[0])) {
				$(".timeBackText").text($("#starttime").val() + " ~ " + $("#endtime").val())
			} else {
				var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
				$(".timeBackText").text($("#starttime").val().slice(index) + " ~ " + $("#endtime").val().slice(index))
			}
			
            if(plotId && isSameModule()) {
            	isListLoadData = false;
                $("#draw_charts_div").show();
                var chartParams = {
                    starttime: tabParams.starttime,
                    endtime: tabParams.endtime,
                    moduleId: urlParams.moduleId,
                    watchpointId: tabParams.watchpointId,
                    plotId: plotId,
                    plotTypeId: urlParams.plotTypeId
                };
                if(tabParams.ipmCenterId) {
                   chartParams.ipmCenterId = tabParams.ipmCenterId;
                }
                if(tabParams.clientId) {
                    chartParams.clientId = tabParams.clientId;
                }
                if(tabParams.serverId) {
                    chartParams.serverId = tabParams.serverId;
                }
                var callbackFun = lineCallback; // 默认线图
                if(chartParams.plotTypeId == 3) { // 饼图
                    callbackFun = pieCallback;
                } else if(chartParams.plotTypeId == 2) { // 柱图
                    
                }
                $.drawHChart("draw_charts", "draw_title", urlParams.chartUrl, chartParams, callbackFun);
            } else {
            	isListLoadData = true;
                $("#draw_charts_div").hide("normal");
            }
            var charts = $("#draw_charts").highcharts();
            charts && charts.zoomOut();
            if (isListLoadData) {
            	txTable();
            } else {
            	hideTxTab();
            }
            $("#content").animate({scrollTop : 0}, 800);
            delTxDetaTab();
    	}
    });
    
    var hideTxTab = function() {
        $("#messsage").show();
        $("#txTableResponsive").hide(800);
        $('#txTable').bootstrapTable('load', []);
    }
    
    /**
     * 隐藏并删除详细列表
     */
    var delTxDetaTab = function() {
    	$("#txDetaTableDiv").hide(800);
        setTimeout(function() {
            $("#txDetaTableDiv").remove();
        }, 800);
    }
    
    /**
     * 通信队详细列表
     */
    var txDetaTab = function(row) {
        var params = {
            starttime: row.starttime,
            endtime: row.endtime,
            ipmCenterId: row.ipmCenterId,
            watchpointId: row.watchpointId,
            lidu: row.lidu,
            serverip: row.serverip,
            clientip: row.clientip,
            serverport: row.serverport,
            protoPlanId: row.protoPlanId,
            openIpLocationFlag: tabParams.openIpLocationFlag
        };
        if(tabParams.protocolType) {
            params.protocolType = tabParams.protocolType;
        }
        if(tabParams.protocolInfo) {
            params.protocolInfo = tabParams.protocolInfo;
        }
        if($("#txDetaTable").length == 0) {
            var selectRow = null, authInfo = {}, tabToolbar = [];
            $.ajax({
                async: false,
                url: "/authorizeModuleController/getSelectIsOpen.do",
                type: "post",
                dataType: "json",
                success: function (data) {
                	authInfo = data;
                }
            });
            var downloadConfig = { name: "下载", type: "download-alt", call: function() {
                    function temp() {
                        $("#downLoadingsLabel").html("下载");
                        $('#downLoadings').modal({ backdrop: "static" });
                        $("input[name=optionsRadios]:eq(0)").prop("checked",'checked');
                        $("#loadSure").button("reset");
                        $("#loadSure").off("click").on("click",function(){
                            var loadCheck = $('#loadSs input[name=optionsRadios]:checked ').val();
                            var _this = $(this).button("loading");
                            $.ajax({
                                url: "/commpairFlow/historyExtract.do",
                                data: {
                                    starttime: selectRow.snaptime,
                                    endtime: selectRow.snaptime,
                                    ipmCenterId: selectRow.ipmCenterId,
                                    watchpointIds: selectRow.watchpointId,
                                    serverip: selectRow.serverip,
                                    clientip: selectRow.clientip,
                                    serverport: selectRow.serverport,
                                    lidu: selectRow.lidu
                                },
                                dataType: "json",
                                method: "POST",
                                success: function(data) {
                                    if(data.typeId != "0") {
                                        jeBox.alert("暂无数据");
                                    } else {
                                        if (parseInt(selectRow.ipmCenterId) == 1) {
                                            if(loadCheck == "0") {
                                                window.open("/commpairFlow/listExtract.do?fileName=" + data.fileName);
                                            } else {
                                                var url = '/cgi-bin/iDigger.cgi?filename=' + data.fileName
                                                    + '&ip=' + location.host.split(":")[0];
                                                var tmp = window.open("about:blank", "", "fullscreen=1");
                                                tmp.moveTo(0, 0);
                                                tmp.resizeTo(screen.width + 20, screen.height);
                                                tmp.focus();
                                                tmp.location = url;
                                            }
                                        } else {
                                            $.post("/center/getCenterById.do", { id: selectRow.ipmCenterId },
                                                function(d) {
                                                    if(loadCheck == "0") {
                                                        window.open("http://" + d.ip + "/commpairFlow/listExtract.do?fileName=" + data.fileName);
                                                    } else {
                                                        var url = '/cgi-bin/iDigger.cgi?filename='
                                                            + data.fileName + '&ip=' + d.ip;
                                                        var tmp = window.open("about:blank", "", "fullscreen=1");
                                                        tmp.moveTo(0, 0);
                                                        tmp.resizeTo(screen.width + 20, screen.height);
                                                        tmp.focus();
                                                        tmp.location = url;
                                                    }
                                                }, "json");
                                        }
                                    }
                                    _this.button("reset");
                                    $("#downLoadings").modal("hide");
                                },
                                error: function() {
                                    jeBox.alert("暂无数据");
                                    _this.button("reset");
                                    $("#downLoadings").modal("hide");
                                }
                            });
                        });
                    }
                    var _thisBtn = $(this);
                    if(!_thisBtn.hasClass("groupKpiCanUsed")){
                        $(this).addClass("groupKpiCanUsed");
                        $.ajax({
                            url:"/commpairFlow/historyExtract.do",
                            type:"post",
                            data:{
                                starttime: selectRow.snaptime,
                                endtime: selectRow.snaptime,
                                ipmCenterId: selectRow.ipmCenterId,
                                watchpointIds: selectRow.watchpointId,
                                serverip: selectRow.serverip,
                                clientip: selectRow.clientip,
                                serverport: selectRow.serverport,
                                lidu: selectRow.lidu
                            },
                            dataType:"json",
                            success:function (data) {
                                if(data.typeId != "0") {
                                    jeBox.alert("暂无数据");
                                    _thisBtn.removeClass("groupKpiCanUsed")
                                } else {
                                    var tabPackRow,
                                        folderName = data.folderName,
                                        html = '<div class="form-horizontal">' +
                                            '<div class="row"><div class="col-md-12">' +
                                            '<div id="tableLoadbox" style="cursor: wait;' +
                                            'width:auto;height:57px;line-height: 57px;padding-left: 50px;' +
                                            'padding-right: 5px;color:#696969;font-family:Microsoft YaHei; ' +
                                            'background: #ddd;">' +
                                            '<img src="../images/gif.gif" style="position: relative;left:-20px">'+
                                            '正在请求数据，请您耐心等候。。。</div>'+
                                            '</div></div></div>' +
                                            '<div class="form-horizontal">'+
                                            '<div class="row"><div class="col-md-12">' +
                                            '<table id="tabPackDownload"></table>'+
                                            '</div></div>'+
                                            '</div>';
                                    var downToolbar = [{
                                        name: "下载",
                                        type: "download-alt",
                                        call: function () {
                                            if (parseInt(selectRow.ipmCenterId) == 1) {
                                                window.open("/commpairFlow/listExtract.do?fileName=" + tabPackRow.name+"&folderName="+folderName);
                                            } else {
                                                $.post("/center/getCenterById.do", { id: selectRow.ipmCenterId },
                                                    function(d) {
                                                        window.open("http://" + d.ip + "/commpairFlow/listExtract.do?fileName=" + tabPackRow.name+"&folderName="+folderName);
                                                    }, "json");
                                            }
                                        }
                                    }],
                                    digger = {
                                        name:"在线解析",
                                        type:"arrow-down",
                                        call: function () {
                                            if (parseInt(selectRow.ipmCenterId) == 1) {
                                                var url = '/cgi-bin/iDigger.cgi?filename=' + folderName+"/"+tabPackRow.name
                                                    + '&ip=' + location.host.split(":")[0];

                                                var tmp = window.open("about:blank", "", "fullscreen=1");
                                                tmp.moveTo(0, 0);
                                                tmp.resizeTo(screen.width + 20, screen.height);
                                                tmp.focus();
                                                tmp.location = url;
                                            } else {
                                                $.post("/center/getCenterById.do", { id: selectRow.ipmCenterId },
                                                    function(d) {
                                                        var url = '/cgi-bin/iDigger.cgi?filename='
                                                        +folderName+"/"+ tabPackRow.name + '&ip=' + d.ip;
                                                        var tmp = window.open("about:blank", "", "fullscreen=1");
                                                        tmp.moveTo(0, 0);
                                                        tmp.resizeTo(screen.width + 20, screen.height);
                                                        tmp.focus();
                                                        tmp.location = url;
                                                    }, "json");
                                            }
                                        }
                                    };
                                    if (authInfo.digger) {
                                        downToolbar.push(digger);
                                    }
                                    $("#packDownload>.modal-dialog>.modal-content>.modal-body").html(html);
                                    $("#packDownload").modal("show");
                                    $.ptcsBSTable("tabPackDownload","/commpairFlow/historyNameList.do",{
                                        folderName:folderName
                                    },{
                                        columns: [
                                            {
                                                field: "name",
                                                title: "文件名称",
                                                sortable: true
                                            }
                                        ],
                                        responseHandler:function(data){
                                            var cusData = data.fileName?data.fileName:[{}];
                                            cusData[0].nodataFlag = data.nodataFlag;
                                            cusData[0].finishFlag = data.finishFlag;
                                            return cusData
                                        },
                                        ipm_title: "",
                                        ipm_shrink: false,
                                        rowStyle:function (row,i) {
                                            var cla = {};
                                            if(i == 0) {
                                                cla.classes = "custom-row-style";
                                                tabPackRow = row;
                                            }
                                            cla.css = { "white-space" : "nowrap" };
                                            return cla;
                                        },
                                        onClickRow:function (row,tr) {
                                            $("#tabPackDownload > tbody > .custom-row-style").removeClass();
                                            $(tr).addClass("custom-row-style");
                                            tabPackRow = row;
                                        },
                                        ipm_toolbar: downToolbar,
                                        onLoadSuccess:function (data) {
                                            /*
                                             * fileName  ：[{},{}]
                                             * finishFlag:  0-未结束  1-结束
                                             * nodataFlag:  0-有数据  1-无数据
                                             */
                                            if(data[0].nodataFlag){
                                                //无数据
                                                $("#packDownload .modal-body").html("无数据");
                                            }else {
                                                //有数据
                                                if(data[0].finishFlag){
                                                    //结束
                                                    $("#tableLoadbox").hide();
                                                }else {
                                                    //未结束
                                                    setTimeout(function () {
                                                        if($("#packDownload").hasClass("in")){
                                                            $.ptcsBSTabRefresh("tabPackDownload",{
                                                                folderName:folderName
                                                            });
                                                        }
                                                    },15000)
                                                }
                                            }
                                            setTimeout(function () {
                                                _thisBtn.removeClass("groupKpiCanUsed")
                                            },3000)
                                        },
                                        onLoadError:function () {
                                            _thisBtn.removeClass("groupKpiCanUsed")
                                        }
                                    });
                                }
                            },
                            error:function () {
                                jeBox.alert("暂无数据");
                                _thisBtn.removeClass("groupKpiCanUsed")
                            }
                        })
                    }
                }                
            };
            if (authInfo.flowstorage) {
                tabToolbar.push(downloadConfig);
            }
            $("#tables").append("<div id='txDetaTableDiv'><table id ='txDetaTable'></table></div>");
            $.ptcsBSTable("txDetaTable", "/commpair/getRemoteCommpairDetailData.do", params, {
                columns: columns,
                ipm_title: "详细列表",
                ipm_shrink: false,
                rowStyle: function(row, i) {
                    var cla = {};
                    if(i == 0) {
                        cla.classes = "custom-row-style";
                        selectRow = row;
                    }
                    cla.css = { "white-space" : "nowrap" };
                    return cla;
                },
                onClickRow: function(row, tr) {
                    $("#txDetaTable > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = row;
                },
                ipm_toolbar: tabToolbar
            });
        } else {
            $.ptcsBSTabRefresh("txDetaTable", params);
        }
    };
    
    /**
     * 线图回调函数
     */
    var lineCallback = function(starttime, endtime) {
    	delTxDetaTab();
    	if (starttime && endtime) {
    		if (endtime - starttime < 10) {
    		    starttime = endtime - 10;
    		}
            tabParams.starttime = starttime;
            tabParams.endtime = endtime;
            if(plotId == 29 || plotId == 94) { // 未定义客户端流量通信对
            	tabParams.unknownFlow = 11;
            } else if(plotId == 28 || plotId == 90) { // 未定义服务端流量通信对
            	tabParams.unknownFlow = 12;
            } else {
            	tabParams.unknownFlow = 0;
            } 
            txTable();
    	} else {
    		hideTxTab();
    	}
    };
    
    /**
     * 饼图回调函数
     */
    var pieCallback = function(option) {
        delTxDetaTab();
    	if(plotId == 31 || plotId == 89) { // 公有协议分布
        	tabParams.protocolInfo = option.id;
        	tabParams.protocolType = 1;
    	} else if(plotId == 30 || plotId == 59) { // 用户协议分布
    		tabParams.protocolInfo = option.id;
            tabParams.protocolType = 2;
    	}
        txTable();
    }
    
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
            } else {
                $("#starttime").val($("#inpstart").val());
                $("#endtime").val($("#inpend").val());
                $("#form_submit").trigger("click");
                $('a[data-drawer="times"]').trigger("click");
            }
        }
    })
});

