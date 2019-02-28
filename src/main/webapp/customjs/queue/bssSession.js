/**
 * bss会话列表
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
    var urlParams = $.getUrlParams(), columns = null, isFirst = true, tabParams = null, selectRow = null;
    setFormTime(urlParams.starttime, urlParams.endtime);
    var moduleId = parseInt(urlParams.moduleId);
    if (moduleId == 9) {
        $("#uosDiv").hide();
        $("#codeDiv").hide();
    } else {
        if(moduleId == 4 || moduleId == 8) {
            $("#uoslabel").html("URL:");
        } else {
        	$("#messageDiv").hide();
        	$("#uoslabel").html("SQL:");
        }
        $("#successDiv").hide();
        $("#respDiv").hide();
    }
    
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
    
    /**
     * 刷新模块业务数据
     */
    var refModuleData = function() {
        var centerId = parseInt($("#xpmserver").selectpicker('val'));
        if(centerId != 0) {
            $("#watchpoint").prop("disabled", false).selectpicker('refresh');
            $("#business").prop("disabled", false).selectpicker('refresh');
        	$.post("/watchpointController/getFindAll.do", { ipmCenterId: centerId, moduleId: 10 }, function(data) {
                $("#watchpoint").empty();
                addOption("watchpoint", data);
                $("#watchpoint").selectpicker('refresh');
                $.post("/appController/getAllAppByModuleId.do", { ipmCenterId: centerId, moduleId: moduleId }, function(data) {
                    $("#business").empty();
                    addOption("business", data);
                    $("#business").selectpicker('refresh');
                    if (isFirst) {
                        setTabColumn();
                    }
                }, "json");
            }, "json");
        } else {
            $("#watchpoint").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
            $("#business").prop("disabled", true).selectpicker('val', 0).selectpicker('refresh');
            if (isFirst) {
                setTabColumn();
            }
        }
    }
    
    /**
     * 设置表格列
     */
    var setTabColumn = function() {
        setDefData();
        // URL与HTTP一致
        var typeId = parseInt(moduleId) == 8 ?  14 : parseInt(moduleId) + 10;
        $.post("/appController/getAppListColumn.do", { moduleId: moduleId, typeId: typeId }, function(data) {
            var csArr = [], columnen = null;
            for (var i = 0, len = data.length; i < len; i ++) {
                columnen = data[i].columnen;
                csArr.push({
                    id: data[i].id,
                    field: columnen,
                    title: data[i].columnzh,
                    sortable: true,
                    class: "textNowrap",
                    visible: !!data[i].checked
                });
                if(columnen == "begintime" || columnen == "endtime" 
                    || columnen == "endtimewithpayload" || columnen == "transTime") {
                    csArr[csArr.length - 1].formatter = function(v) {
                        return $.timeStampDate(v, "MM-DD hh:mm:ss");
                    }
                } else if(columnen == "url" || columnen == "sqlquery"
                    || columnen == "res" || columnen == "req"
                    || columnen == "source") {
                    csArr[csArr.length - 1].class = "urlWidth";
                    csArr[csArr.length - 1].formatter = function(v) {
                    	if (v) {
                            v = v.replace(/</g, "&lt;");
                    	}
                    	
                        return v;
                    }
                }
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
        if(urlParams.busiId) {
            $("#business").selectpicker('val', urlParams.busiId);
        }
        if(urlParams.client) {
            var clientInfo = urlParams.client.split(":");
            $("#clientip").val(clientInfo[0]);
            if(clientInfo[1]) {
                $("#clientport").val(clientInfo[1]);
            }
        } else {
            if(urlParams.clientIp) {
                $("#clientip").val(urlParams.client);
            }
            if(urlParams.clientPort) {
                $("#clientport").val(urlParams.clientPort);
            }
        }
        if(urlParams.server) {
            var serverInfo = urlParams.server.split(":");
            $("#serverip").val(serverInfo[0]);
            if(serverInfo[1]) {
                $("#serverport").val(serverInfo[1]);
            }
        } else {
            if(urlParams.serverIp) {
                $("#serverip").val(urlParams.serverIp);
            }
            if(urlParams.serverPort) {
                $("#serverport").val(urlParams.serverPort);
            }
        }
        if(urlParams.uos) {
            $("#uos").val(decodeURIComponent(urlParams.uos));
        }
        if(urlParams.message) {
            $("#message").val(urlParams.message);
        }
    };
    
    /**
     * 会话列表
     */
    var sessionListFun = function() {
        $("#messsage").hide();
        $("#sessionTableRespon").show();
        if(isFirst) {
        	var dataUrl = null;
        	if (moduleId == 9) {
        	    dataUrl = "/depthanaly/getRemoteData.do";
        	} else {
        	    dataUrl = "/appController/getRemoteAppStateList.do";
        	}
        	var tabToolbar = [], authInfo = {};
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
                    if(selectRow  == null) {
                       jeBox.alert("请您选中一行下载");
                       return;
                    }
                    var _thisBtn = $(this);
                    if(!_thisBtn.hasClass("groupKpiCanUsed")){
                        $(this).addClass("groupKpiCanUsed");
                        $.ajax({
                            url:"/commpairFlow/historyExtract.do",
                            type:"post",
                            data:{
                                ipmCenterId: selectRow.ipmCenterId,
                                starttime: selectRow.begintime || selectRow.transTime,
                                endtime: selectRow.endtime || selectRow.endtimewithpayload,
                                watchpointIds: $("#watchpoint").selectpicker("val"),
                                serverip: selectRow.serverip || selectRow.server,
                                clientip: selectRow.client,
                                serverport: selectRow.port || selectRow.serverPort,
                                clientport: selectRow.clientport || selectRow.clientPort,
                                historyGetFlow: 3
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
                                                'padding-right: 5px;color:#696969;font-family:Microsoft YaHei;' +
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
                                                    var url = '/cgi-bin/iDigger.cgi?filename=' +folderName+"/"+ tabPackRow.name
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
                                                                + folderName+"/"+tabPackRow.name + '&ip=' + d.ip;
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
            $.ptcsBSTable("sessionTable", dataUrl, tabParams, {
                columns: columns,
                ipm_title: "会话列表",
                ipm_shrink: false,
                ipm_column_save: true,
                ipm_server_page: true,
                onClickRow: function(row, tr) {
                    $("#sessionTable > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = row;
                    if (moduleId == 9) {
                        var json = JSON.parse(row.originalMessage);
                        $("#messageDetilPre").html(JSON.stringify(json, null, '\t'));
                        $('#messageDetil').modal({ backdrop: "static" });
                    }
                },
                ipm_toolbar: tabToolbar
            });
            isFirst = false;
        } else {
            $.ptcsBSTabRefresh("sessionTable", tabParams);
        }
    };
    
    $("#form_submit").click(function(){
        if(!$("#starttime").val().trim()) {
           jeBox.alert("开始时间不能为空");
        } else if(!$("#endtime").val().trim()) {
           jeBox.alert("结束时间不能为空");
        } else {
            tabParams = {
                starttime: $.timeStampDate($("#starttime").val()),
                endtime: $.timeStampDate($("#endtime").val()),
                moduleId: moduleId,
                plotId: urlParams.plotId
            };
            if(tabParams.starttime >= tabParams.endtime) {
                jeBox.alert("开始时间不能大于等于结束时间");
                return;
            }
            var ipmCenterId = $("#xpmserver").selectpicker("val"),
                watchpointId = $("#watchpoint").selectpicker("val"),
                busiId = $("#business").selectpicker("val"),
                serverip = $("#serverip").val(),
                serverport = $("#serverport").val(),
                clientip = $("#clientip").val(),
                clientport = $("#clientport").val(),
                uos = $("#uos").val(),
                code = $("#code").val(),
                message = $("#message").val(),
                success = $("#success").selectpicker("val"),
                resp = $("#resp").selectpicker("val");
            if(ipmCenterId && ipmCenterId != "0") {
                tabParams.ipmCenterId = ipmCenterId;
            }
            if(watchpointId && watchpointId != "0") {
                tabParams.watchpointId = watchpointId;
            }
            if(busiId && busiId != "0") {
                tabParams.busiId = busiId;
            }
            if(serverip) {
                tabParams.serverip = serverip;
            }
            if(serverport) {
                tabParams.serverport = serverport;
            }
            if(clientip) {
                tabParams.clientip = clientip;
            }
            if(clientport) {
                tabParams.clientport = clientport;
            }
            if(uos) {
                tabParams.uos = uos;
            }
            if(code != undefined && code != '' && code != null) {
               tabParams.code = code;
            }
            if(message) {
                tabParams.message = message;
            }
            if(success && success != "0") {
                tabParams.success = parseInt(success) - 1;
            }
            if(resp && resp != "0") {
                tabParams.resp = parseInt(resp) - 1;
            }

            if ((new Date().getFullYear() - $("#starttime").val().split("-")[0])
                    || (new Date().getFullYear() - $("#endtime").val().split("-")[0])) {
                $(".timeBackText").text($("#starttime").val() + " ~ " + $("#endtime").val());
            } else {
                var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
                $(".timeBackText").text($("#starttime").val().slice(index) + " ~ " + $("#endtime").val().slice(index));
            }
            if(urlParams.plotId && tabParams.busiId && tabParams.watchpointId) {
                $("#messsage").show();
                $("#sessionTableRespon").hide("normal");
                $.hChartDispose("draw_charts");
                $("#draw_charts_div").show();
                var chartParams = {
                    starttime: tabParams.starttime,
                    endtime: tabParams.endtime,
                    moduleId: moduleId,
                    watchpointId: tabParams.watchpointId,
                    busiId: tabParams.busiId,
                    plotId: urlParams.plotId,
                    plotTypeId: urlParams.plotTypeId
                }, plotCallBack = null;
                if(ipmCenterId && ipmCenterId != "0") {
                    chartParams.ipmCenterId = ipmCenterId;
                }
                switch(parseInt(chartParams.plotId)) {
                    case 237: case 238: case 239: case 240:
                    case 241: case 242: case 243: case 244:
                    case 245: case 246: case 306: case 307: // 柱图
                       plotCallBack = columnCallBack;
                       break;
                    default:
                       plotCallBack = lineCallback;
                }
                var plotUrl = null;
                if(moduleId == 8) {
                    plotUrl = "/url/getPlotData.do"
                } else if(moduleId == 9) {
                    plotUrl = "/depthanaly/depthanalyGraphical.do";
                } else {
                    plotUrl = "/appController/getPlotData.do";
                }
                $.drawHChart("draw_charts", "draw_title", plotUrl, chartParams, plotCallBack);
            } else {
                $("#draw_charts_div").hide("normal", sessionListFun);
            }
            $("#content").animate({scrollTop : 0}, 800);
        }
    });
    
    /**
     * 线图事件
     */
    var lineCallback = function(starttime, endtime) {
    	if (starttime && endtime) {
            tabParams.starttime = starttime;
            tabParams.endtime = endtime;
            sessionListFun();
    	} else {
            $("#messsage").show();
            $("#sessionTableRespon").hide("normal");
    	}
    };
    
    /**
     * 柱图事件
     */
    var columnCallBack = function(params) {
        tabParams.starttime = params.starttime;
        tabParams.endtime = params.endtime;
        if (moduleId == 9) {
            switch(parseInt(urlParams.plotId)) {
                case 306: // 交易成功率
                    tabParams.success = 1;
                    $("#success").selectpicker('val', 2);
                    break;
                case 307: // 交易响应率
                    tabParams.resp = 1;
                    $("#resp").selectpicker('val', 2);
                    break;
            }
        } else {
            tabParams.uos = params.category;
            $("#uos").val(params.category);
        }
        sessionListFun();
    };
    
    /**
     * 头部时间功能
     */
    $(".timesure").click(function() {
        if (!$("#inpstart").val().trim()) {
            jeBox.alert("开始时间不能为空");
        } else if (!$("#inpend").val().trim()) {
            jeBox.alert("结束时间不能为空");
        } else {
        	if ($.timeStampDate($("#inpstart").val()) >= $
                .timeStampDate($("#inpend").val())) {
                jeBox.alert("开始时间不能大于等于结束时间");
            	return;
            } else {
            	$("#starttime").val($("#inpstart").val());
                $("#endtime").val($("#inpend").val());
                $("#form_submit").trigger("click");
                $('a[data-drawer="times"]').trigger("click");
    		}
    	}
    });
 });