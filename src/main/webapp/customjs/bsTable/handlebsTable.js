/**
 * Created by yanbo on 2017/10/9.
 * 操作表格 增删改
 *
 * 初始化js条件
 * <script src="js/jquery.min.js"></script>
 * <script src="js/bootstrap.min.js"></script>
 * <script src="js/bstable/bootstrap-table.js"></script>
 * <script src="js/bstable/bootstrap-table-zh-CN.js"></script>
 */
/*===================================================================================
 ===表示这几段代码不是我写的========================================================
 =*/
var dataList = [];

function xs(id) {
    var xstmp = 0;
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i] == id) {
            xstmp = i;
        }
    }
    if (xstmp == 0) {
        return;
    }
    dataList[xstmp] = dataList[xstmp - 1];
    dataList[xstmp - 1] = id;
    var status = "";
    for (var i = 0; i < dataList.length; i++) {
        status = status + "," + dataList[i];
    }
    status = status.substring(1);
    $.ajax({
        url: "/userAuthorize/updateUserAuthorizeSort.do",
        async: false,
        type: "post",
        data: {
            status: status
        },
        dataType: "json",
        success: function (cdata) {
            dataList = [];
            $("#cockMange").bootstrapTable("refresh");
        }
    });
}

function xx(id) {
    var xstmp = 0;
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i] == id) {
            xstmp = i;
        }
    }
    if (dataList.length - 1 == xstmp) {
        return;
    }
    dataList[xstmp] = dataList[xstmp + 1];
    dataList[xstmp + 1] = id;
    var status = "";
    for (var i = 0; i < dataList.length; i++) {
        status = status + "," + dataList[i];
    }
    status = status.substring(1);
    $.ajax({
        url: "/userAuthorize/updateUserAuthorizeSort.do",
        async: false,
        type: "post",
        data: {
            status: status
        },
        dataType: "json",
        success: function (cdata) {
            dataList = [];
            $("#cockMange").bootstrapTable("refresh");
        }
    });
}

function xt(id) {
    var xstmp = 0;
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i] == id) {
            xstmp = i;
        }
    }
    var tmp = dataList[0];
    var tmp2 = dataList[1];
    for (var i = 0; i < dataList.length; i++) {
        if (i != 0 && i <= xstmp) {
            tmp2 = dataList[i];
            dataList[i] = tmp;
            tmp = tmp2;
        }
    }
    dataList[0] = id;
    var status = "";
    for (var i = 0; i < dataList.length; i++) {
        status = status + "," + dataList[i];
    }
    status = status.substring(1);
    $.ajax({
        url: "/userAuthorize/updateUserAuthorizeSort.do",
        async: false,
        type: "post",
        data: {
            status: status
        },
        dataType: "json",
        success: function (cdata) {
            dataList = [];
            $("#cockMange").bootstrapTable("refresh");
        }
    });
}

function xw(id) {
    var xstmp = 0;
    for (var i = 0; i < dataList.length; i++) {
        if (dataList[i] == id) {
            xstmp = i;
        }
    }
    var tmp = dataList[dataList.length - 1];
    var tmp2 = dataList[dataList.length - 2];
    for (var i = dataList.length - 1; i >= 0; i--) {
        if (i <= dataList.length - 1 && i > xstmp) {
            tmp2 = dataList[i - 1];
            dataList[i - 1] = tmp;
            tmp = tmp2;
        }
    }
    dataList[dataList.length - 1] = id;
    var status = "";
    for (var i = 0; i < dataList.length; i++) {
        status = status + "," + dataList[i];
    }
    status = status.substring(1);
    $.ajax({
        url: "/userAuthorize/updateUserAuthorizeSort.do",
        async: false,
        type: "post",
        data: {
            status: status
        },
        dataType: "json",
        success: function (cdata) {
            dataList = [];
            $("#cockMange").bootstrapTable("refresh");
        }
    });
}
/*====================================================================================*/
$(function () {
    var bsTable = {
        /**
         * 重新开始分析弹出框
         */
        fenXalert:function(){
            jeBox({
                cell:"jbx",
                title:"提示信息",
                maskLock : true,
                boxSize: ["280px", "130px"],
                padding: "8px 14px 0",
                content:'<span>修改成功，重新分析后配置生效。</span><br/>' +
                '<span>功能位置：系统设置->网卡管理与设置-></span><img src="images/fenxi.png">',
                success: function(element, index){
                    $(element).children(".jeBox-content").addClass("text-left").css("border-bottom","none");
                },
                button:[
                    {
                        name: '确定'
                    }
                ]
            });
        },
        /**
         * 重新开始分析调用后端接口
         */
        analysisAgain:function(modalId){
            if($(modalId+" #analysis_again").prop("checked")){
                $.ajax({
                    url:"/sysNetworkSet/startStatistic.do",
                    method:"POST",
                    data:{},
                    dataType:"json",
                    beforeSend:function (XMLHttpRequest) {},
                    success:function (data,textStatus,XMLHttpRequest) {
                        if(data){
                            jeBox.alert("已重新开始分析");
                        }else {
                            jeBox.alert("重新分析失败");
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function(XMLHttpRequest,textStatus){}
                })
            }
        },
        /**
         *
         * @param tableId
         * @param iniurl
         * @param toolbarId
         * @param _queryParams
         * @param columnsArry
         */
        iniKpiSetTab: function (tableId, iniurl, toolbarId, _queryParams, columnsArry) {
            $(tableId).bootstrapTable({
                toggle: "table",
                url: iniurl,
                toolbar: toolbarId,
                pagination: true,
                showColumns: true,
                queryParams: function (params) {
                    return _queryParams
                }, //参数
                columns: columnsArry,
                onClickRow: function (e, row) {
                    $(tableId + " .custom-row-style").removeClass("custom-row-style");
                    var idList = [];
                    idList.push(e.deviateId, e.normalId, e.importantId, e.urgentId);
                    $(row).addClass("custom-row-style").attr("data-idList", String(idList));
                }
            })
        },
        /**
         * 组合告警弹出模态框功能
         * @param tableId 表格ID
         * @param iniUrl 初始化URL
         * @param urlParma 初始化url参数
         * @param columns 展示表格的列数组
         * @param modalAttr 模态框属性
         */
        zuheKpisetModal: function (tableId, iniUrl, urlParma, columns, modalAttr) {
            $.ajax({
                url: "/customUnionKpi/getCustomkpiInfo.do",
                method: "POST",
                data: urlParma,
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success: function (data,textStatus,XMLHttpRequest) {
                    $("#zuheKpitableRow-modal #groupKpiName").val(data.groupKpiName);
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
            if ($(tableId).parent().hasClass("form-horizontal")) {
                $(tableId).bootstrapTable({
                    toggle: "table",
                    url: iniUrl,
                    columns: columns,
                    height: 300,
                    rowStyle: function (row, index) {
                        var cla = {};
                        if (!Number(row.groupKpiCanUsed)) {
                            cla.classes = "groupKpiCanUsed";
                        }
                        return cla;
                    },
                    queryParams: function (p) {
                        if (urlParma) {
                            $.extend(p, urlParma || {});
                            urlParma = null;
                        }
                        return p;
                    },
                    onClickRow: function (row, tr) {

                    },
                    onLoadSuccess: function () {
                        $(tableId).parent(".fixed-table-body").css("height", 276);
                    }
                });
            } else {
                $(tableId).bootstrapTable("refresh", {
                    silent: false,
                    query: urlParma
                })
            }
            if (modalAttr) {
                $("#zuheKpitableRow-modal").attr("data-tableAttr", modalAttr);
            }
            setTimeout(function () {
                $("#zuheKpitableRow-modal").modal("show");
                setTimeout(function () {
                    $("#zuheKpitableRow-modal #groupKpiName").focus();
                }, 600);
            }, 200);
        },
        /**
         * 组合告警提交功能
         * @param url
         * @param urlParama
         */
        zuheKpicommitData: function (url, urlParama) {
            var groupKpiName = $.trim($("#groupKpiName").val()),
                $tr = $("#alarmZhtab tbody tr"),
                reVal = true,
                groupKpis = [];
            for (var i = 0; i < $tr.length; i++) {
                if ($tr.eq(i).hasClass("selected")) {
                    var id = $tr.eq(i).attr("data-id");
                    groupKpis.push(id);
                }
            }
            if (groupKpiName == "") {
                jeBox.alert("名称不能为空");
                reVal = false;
                return;
            }
            if (!bsTable.verification(groupKpiName)) {
                jeBox.alert("名称不能超过32个字符");
                reVal = false;
                return;
            }
            if (groupKpis.length < 2) {
                jeBox.alert("必须勾选两个名称或两以上");
                reVal = false;
                return;
            }
            urlParama.groupKpiName = groupKpiName;
            urlParama.groupKpis = String(groupKpis);
            if (reVal) {
                $.ajax({
                    url: url,
                    method: "POST",
                    data: urlParama,
                    dataType: "json",
                    beforeSend:function (XMLHttpRequest) {},
                    success: function (data,textStatus,XMLHttpRequest) {
                        if (data) {
                            jeBox.alert("组合告警成功");
                        } else {
                            jeBox.alert("组合告警失败，请您稍后再试");
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
                $("#zuheKpitableRow-modal").modal("hide");
            }
        },
        /**
         * 修改表格时弹出模态框
         * @param tableId
         * @param titleUrl
         * @param iniUrl
         * @param watchpointId
         * @param businessId
         * @param moduleId
         * @param kpiId
         * @param modalAttr
         */
        editKpisetModal: function (tableId, titleUrl, iniUrl, watchpointId, businessId, moduleId, kpiId, modalAttr) {
            var selectRowId = $(tableId + " .custom-row-style").attr("data-id"),
                index = $(tableId + " .custom-row-style").attr("data-index"),
                field = [],
                titleText = [],
                titIdArry = [];
            if (selectRowId) {
                $.ajax({
                    url: titleUrl,
                    method: "POST",
                    async: false,
                    data: {},
                    dataType: "json",
                    beforeSend:function (XMLHttpRequest) {},
                    success: function (data,textStatus,XMLHttpRequest) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].namezh != "编号" && data[i].namezh != "KPI名称") {
                                field.push(data[i].nameen + "Value");
                                titleText.push(data[i].namezh);
                                titIdArry.push(data[i].id);
                            }
                        }
                        /**
                         * 此处逻辑是必传kpiId才能取到正确的值
                         */
                        $.ajax({
                            url: iniUrl,
                            method: "POST",
                            data: {
                                "watchpointId": watchpointId,
                                "businessId": businessId,
                                "moduleId": moduleId,
                                "kpiId": kpiId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (tabData,textStatus,XMLHttpRequest) {
                                for(var j = 0;j<tabData.length;j++){
                                    if(tabData[j].id == kpiId){
                                        $("#ediKpitableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
                                        var InputVal = [];
                                        for(var k =0;k<field.length;k++){
                                            if(tabData[j][field[k]]){
                                                if(tabData[j].alarmValueCompareFlag){
                                                    InputVal.push(tabData[j][field[k]].global)
                                                }else {
                                                    InputVal.push(tabData[j][field[k]].single)
                                                }
                                            }else {
                                                InputVal.push("");
                                            }
                                        }
                                        for (var i = 0; i < titleText.length; i++) {
                                            var lowThreshVal = typeof InputVal[i].lowThresh === "number" ? tabData[j].kpiUnit=="b"?i==(titleText.length-1)?InputVal[i].lowThresh:InputVal[i].lowThresh/1000000:InputVal[i].lowThresh : "",
                                                highThreshVal = typeof InputVal[i].highThresh === "number" ?  tabData[j].kpiUnit=="b"?i==(titleText.length-1)?InputVal[i].highThresh:InputVal[i].highThresh/1000000:InputVal[i].highThresh : "",
                                                formGroup = $('<div class="form-group">' +
                                                    '<div class="col-sm-4">' +
                                                    '<input type="text"  data-titleId="' + titIdArry[i] + '" class="form-control input-sm m-t-15 addtableRowInput lowThresh"  value="' + lowThreshVal + '">' +
                                                    '</div>' +
                                                    '<div class="col-sm-4">' +
                                                    '<input type="text"  data-titleId="' + titIdArry[i] + '" class="form-control input-sm m-t-15 addtableRowInput highThresh"  value="' + highThreshVal + '">' +
                                                    '</div>' +
                                                    '</div>'),
                                                label = titleText[i] != "智能告警"?$('<label class="col-md-2 control-label" style="position: relative;left: 8px;">' +
                                                    titleText[i] +"["+(tabData[j].kpiUnit=="b"?"Mb":tabData[j].kpiUnit)+"]"+
                                                    '</label>'):$('<label class="col-md-2 control-label" style="position: relative;left: 8px;">' +
                                                    titleText[i] +"[%]"+
                                                    '</label>');
                                            $(formGroup).prepend(label);
                                            $("#ediKpitableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        }
                                        var checkboxHtml = $('<div class="form-group">' +
                                            '<label class="col-sm-2 control-label">阈值开启</label>' +
                                            '<div class="col-sm-4">' +
                                            '<label class="cursor" for="lowCheckbox" style="padding-top: 20px;letter-spacing: 1px;">低阈值</label>' +
                                            '<input type="checkbox" id="lowCheckbox" class="cursor" data-check="0" style="position: relative;top:3px;" />' +
                                            '</div>' +
                                            '<div class="col-sm-4">' +
                                            '<label class="cursor" for="highCheckbox"  style="padding-top: 20px;letter-spacing: 1px;">高阈值</label>' +
                                            '<input type="checkbox" id="highCheckbox" class="cursor" data-check="0" style="position: relative;top:3px;" />' +
                                            '</div>' +
                                            '</div>' +
                                            '<div class="form-group">' +
                                            '<label class="col-sm-2 control-label" for="Globcheckbox">全局设置</label>' +
                                            '<div class="col-sm-9">' +
                                            '<input type="checkbox" id="Globcheckbox" class="Globcheckbox cursor" data-check="0" style="margin-top: 22px;" />' +
                                            '</div>' +
                                            '</div>');
                                        $("#ediKpitableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(checkboxHtml);
                                        if (tabData[j].alarmValueCompareFlag) {
                                            $("#Globcheckbox").attr("checked", "checked")
                                        }
                                        if (!tabData[j].singleLowThreshUsedFlag) {
                                            $("#lowCheckbox").attr("checked", "checked")
                                        }
                                        if (!tabData[j].singleHighThreshUsedFlag) {
                                            $("#highCheckbox").attr("checked", "checked")
                                        }
                                        if (modalAttr) {
                                            $('#ediKpitableRow-modal').attr("data-tableAttr", modalAttr);
                                        }
                                        $('#ediKpitableRow-modal').modal('show');
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
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
            }
        },
        /**
         * 验证高阈值
         * @param normalValue 普通
         * @param importantValue 重要
         * @param urgentValue 紧急
         * @param deviateValue 智能
         * @returns {boolean}
         */
        vfchighKpiSet: function (normalValue, importantValue, urgentValue, deviateValue) {
            if (deviateValue == "" &&
                normalValue == "" &&
                importantValue == "" &&
                urgentValue == "") {
                return true;
            } else if (deviateValue == "" &&
                !isNaN(normalValue) && (normalValue != "") &&
                !isNaN(importantValue) && (importantValue != "") &&
                !isNaN(urgentValue)) {
                //验证这三个数字的大小关系
                if ((normalValue >= 0) && (importantValue >= 0) && (urgentValue >= 0)) {
                    var ComparisonResults = urgentValue - importantValue > 0 && importantValue - normalValue > 0;
                    if (ComparisonResults) {
                        return true;
                    } else {
                        jeBox.alert("高阈值必须普通小于重要小于紧急");
                        // jeBox({
                        //     button: [{
                        //         name: '确定'
                        //     }],
                        //     boxSize: ["320px", "155px"],
                        //     padding: "5px 5px 5px 13px",
                        //     content: '<div style="padding:5px;">高阈值：智能告警必须为数字或为空;<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急</div>',
                        //     maskLock: true,
                        //     boxStyle: {
                        //         "text-align": "left"
                        //     }
                        // });
                        return false;
                    }
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else if (!isNaN(deviateValue) && (deviateValue != "") &&
                normalValue == "" &&
                importantValue == "" &&
                urgentValue == "") {
                if (deviateValue >= 0) {
                    return true
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else if (!isNaN(deviateValue) && (deviateValue != "") &&
                !isNaN(normalValue) && (normalValue != "") &&
                !isNaN(importantValue) && (importantValue != "") &&
                !isNaN(urgentValue) && (urgentValue != "")) {
                if ((deviateValue >= 0) && (normalValue >= 0) && (importantValue >= 0) && (urgentValue >= 0)) {
                    var ComparisonResults = urgentValue - importantValue > 0 && importantValue - normalValue > 0;
                    if (ComparisonResults) {
                        return true;
                    } else {
                        jeBox.alert("高阈值必须普通小于重要小于紧急");
                        // jeBox({
                        //     button: [{
                        //         name: '确定'
                        //     }],
                        //     boxSize: ["320px", "155px"],
                        //     padding: "5px 5px 5px 13px",
                        //     content: '<div style="padding:5px;">高阈值：智能告警必须为数字或为空;<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急</div>',
                        //     maskLock: true,
                        //     boxStyle: {
                        //         "text-align": "left"
                        //     }
                        // });
                        return false;
                    }
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else {
                jeBox.alert("高阈值：告警必须为数字或为空;");
                // jeBox({
                //     button: [{
                //         name: '确定'
                //     }],
                //     boxSize: ["320px", "155px"],
                //     padding: "5px 5px 5px 13px",
                //     content: '<div style="padding:5px;">高阈值：智能告警必须为数字或为空;<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急</div>',
                //     maskLock: true,
                //     boxStyle: {
                //         "text-align": "left"
                //     }
                // });
                return false;
            }
        },
        /**
         * 验证低阈值
         * @param normalValue 普通
         * @param importantValue 重要
         * @param urgentValue 紧急
         * @param deviateValue 智能
         * @returns {boolean}
         */
        vflowKpiSet: function (normalValue, importantValue, urgentValue, deviateValue) {
            if (deviateValue == "" &&
                normalValue == "" &&
                importantValue == "" &&
                urgentValue == "") {
                return true;
            } else if (deviateValue == "" &&
                !isNaN(normalValue) && (normalValue != "") &&
                !isNaN(importantValue) && (importantValue != "") &&
                !isNaN(urgentValue)) {
                //验证这三个数字的大小关系
                if ((normalValue >= 0) && (importantValue >= 0) && (urgentValue >= 0)) {
                    var ComparisonResults = urgentValue - importantValue < 0 && importantValue - normalValue < 0;
                    if (ComparisonResults) {
                        return true;
                    } else {
                        jeBox.alert("低阈值必须普通大于重要大于紧急");
                        // jeBox({
                        //     button: [{
                        //         name: '确定'
                        //     }],
                        //     boxSize: ["320px", "155px"],
                        //     padding: "5px 5px 5px 13px",
                        //     content: '<div style="padding:5px;">低阈值：智能告警必须为数字或为空；<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急</div>',
                        //     maskLock: true,
                        //     boxStyle: {
                        //         "text-align": "left"
                        //     }
                        // });
                        return false;
                    }
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else if (!isNaN(deviateValue) && (deviateValue != "") &&
                normalValue == "" &&
                importantValue == "" &&
                urgentValue == "") {
                if (deviateValue >= 0) {
                    return true
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else if (!isNaN(deviateValue) && (deviateValue != "") &&
                !isNaN(normalValue) && (normalValue != "") &&
                !isNaN(importantValue) && (importantValue != "") &&
                !isNaN(urgentValue) && (urgentValue != "")) {
                if ((deviateValue >= 0) && (normalValue >= 0) && (importantValue >= 0) && (urgentValue >= 0)) {
                    var ComparisonResults = urgentValue - importantValue < 0 && importantValue - normalValue < 0;
                    if (ComparisonResults) {
                        return true;
                    } else {
                        jeBox.alert("低阈值必须普通大于重要大于紧急");
                        // jeBox({
                        //     button: [{
                        //         name: '确定'
                        //     }],
                        //     boxSize: ["320px", "155px"],
                        //     padding: "5px 5px 5px 13px",
                        //     content: '<div style="padding:5px;">低阈值：智能告警必须为数字或为空；<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急</div>',
                        //     maskLock: true,
                        //     boxStyle: {
                        //         "text-align": "left"
                        //     }
                        // });
                        return false;
                    }
                } else {
                    jeBox.alert("数值必须大于或等于0");
                    return false;
                }
            } else {
                jeBox.alert("低阈值：告警必须为数字或为空");
                // jeBox({
                //     button: [{
                //         name: '确定'
                //     }],
                //     boxSize: ["320px", "155px"],
                //     padding: "5px 5px 5px 13px",
                //     content: '低阈值：智能告警必须为数字或为空；<br>普通告警、重要告警、紧急告警 ：必须全为空或全为数字并且满足数值普通小于重要小于紧急',
                //     maskLock: true,
                //     boxStyle: {
                //         "text-align": "left"
                //     }
                // });
                return false;
            }
        },
        /**
         * 验证低阈值与高阈值的关系
         * @param lowNormalValue
         * @param higNormalValue
         * @returns {boolean}
         */
        vfclowHigh: function (lowNormalValue, higNormalValue) {
            var result = true;
            if (lowNormalValue != "") {
                if (higNormalValue != "") {
                    if (lowNormalValue - higNormalValue >= 0) {
                        jeBox.alert("低阈值普通告警必须小于高阈值普通告警");
                        result = false;
                    }
                }
            }
            return result;
        },
        /**
         *
         * @param tableId
         * @param ediUrl
         * @param params
         */
        editKpiSetTable: function (tableId, ediUrl, params,alarmSelectRow) {
            var alarmValueList = [],
                lowThresh = [],
                highThresh = [],
                alarmValueListROW = [],
                index = $(tableId + " .custom-row-style").attr("data-index");
            for (var i = 0, edtIpt = $("#ediKpitableRow-modal .lowThresh"); i < edtIpt.length; i++) {
                if ($.trim(edtIpt.eq(i).val()) == "") {
                    lowThresh.push("null");
                } else {
                    if(alarmSelectRow.kpiUnit == "b"){
                        lowThresh.push(i==(edtIpt.length-1)?$.trim(edtIpt.eq(i).val()):$.trim(edtIpt.eq(i).val())*1000000);
                    }else {
                        lowThresh.push($.trim(edtIpt.eq(i).val()));
                    }
                }
            }
            for (var i = 0, edtIpt = $("#ediKpitableRow-modal .highThresh"); i < edtIpt.length; i++) {
                if ($.trim(edtIpt.eq(i).val()) == "") {
                    highThresh.push("null");
                } else {
                    if(alarmSelectRow.kpiUnit == "b"){
                        highThresh.push(i==(edtIpt.length-1)?$.trim(edtIpt.eq(i).val()):$.trim(edtIpt.eq(i).val())*1000000);
                    }else {
                        highThresh.push($.trim(edtIpt.eq(i).val()));
                    }
                }
            }
            params.lowthreshList = String(lowThresh);
            params.highthreshList = String(highThresh);
            params.pageSetIsGlobalId = Number($(".Globcheckbox").prop("checked"));
            params.singleLowThreshUsedFlag = Number(!$("#lowCheckbox").prop("checked"));
            params.singleHighThreshUsedFlag = Number(!$("#highCheckbox").prop("checked"));
            if (
                bsTable.vflowKpiSet(
                    $.trim($("#ediKpitableRow-modal .lowThresh:eq(0)").val()),
                    $.trim($("#ediKpitableRow-modal .lowThresh:eq(1)").val()),
                    $.trim($("#ediKpitableRow-modal .lowThresh:eq(2)").val()),
                    $.trim($("#ediKpitableRow-modal .lowThresh:eq(3)").val())
                ) && bsTable.vfchighKpiSet(
                    $.trim($("#ediKpitableRow-modal .highThresh:eq(0)").val()),
                    $.trim($("#ediKpitableRow-modal .highThresh:eq(1)").val()),
                    $.trim($("#ediKpitableRow-modal .highThresh:eq(2)").val()),
                    $.trim($("#ediKpitableRow-modal .highThresh:eq(3)").val())
                ) && bsTable.vfclowHigh(
                    $.trim($("#ediKpitableRow-modal .lowThresh:eq(0)").val()),
                    $.trim($("#ediKpitableRow-modal .highThresh:eq(0)").val())
                )
            ) {
                $.ajax({
                    url: ediUrl,
                    method: "POST",
                    data: params,
                    dataType: "json",
                    beforeSend:function (XMLHttpRequest) {},
                    success: function (data) {
                        if (data) {
                            $(tableId).bootstrapTable("refresh", {
                                silent: false,
                                query: {
                                    watchpointId: params.watchpointId,
                                    businessId: params.businessId,
                                    moduleId: params.moduleId
                                }
                            });
                        } else {
                            jeBox.alert("修改失败，请稍后再试");
                        }
                        $('#ediKpitableRow-modal').modal('hide');
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                })
            }
        },
        /**
         *
         * @param tableId example:#table2
         * @param iniurl example:/monitor/getMonitorViewList.do
         * @param toolbarId  example:#btnAddRemove 盛放增删改按钮的容器
         * @param fieldArry  Array 每列标题的英文 需要以此来匹配对应的数据
         * @param titleArry Array  每列标题的中文 需要以此来展示在表格的列头
         * @param page 某个页面
         * @param _queryParams
         */
        initializ: function (tableId, iniurl, toolbarId, fieldArry, titleArry, page, _queryParams) {
            var columnsArry = [];
            fieldArry.forEach(function (item, index) {
                //only cockpitmanage
                if (page) {
                    if (item == "updateTime") {
                        var colObject = {
                            field: item,
                            title: titleArry[index],
                            formatter: function (value, row, index) {
                                return bsTable.getLocalTime(value);
                            },
                            sortable: true
                        };
                    } else if (item == "lockStatus") {
                        var colObject = {
                            field: item,
                            title: titleArry[index],
                            formatter: function (value, row, index) {
                                if (value) {
                                    return "锁定";
                                } else {
                                    return "未锁定";
                                }
                            },
                            sortable: true
                        };
                    } else if (item == "caozuo") {
                        var colObject = {
                            field: item,
                            title: titleArry[index],
                            formatter: function (value, row, index) {
                                var rowId = row.id;
                                if (rowId < 3000000) {
                                    dataList.push(rowId);
                                }
                                return "<span class='business-arrow glyphicon glyphicon-arrow-up' onclick='xs(" + rowId + ")'>" +
                                    "</span><span class='business-arrow glyphicon glyphicon-arrow-down' onclick='xx(" + rowId + ")'>" +
                                    "</span><span class='business-arrow glyphicon glyphicon-open' onclick='xt(" + rowId + ")'></span>" +
                                    "<span class='business-arrow glyphicon glyphicon-save' onclick='xw(" + rowId + ")'></span>";
                            },
                            sortable: true
                        };
                    } else {
                        var colObject = {
                            field: item,
                            title: titleArry[index],
                            sortable: true
                        };
                    }
                    columnsArry.push(colObject);
                } else {
                    var colObject = {
                        field: item,
                        title: titleArry[index],
                        sortable: true
                    };
                    columnsArry.push(colObject);
                }
            });
            $(tableId).bootstrapTable({
                toggle: "table",
                url: iniurl,
                toolbar: toolbarId,
                pagination: true,
                showColumns: true,
                queryParams: function (params) {
                    return _queryParams
                }, //参数
                columns: columnsArry,
                onClickRow: function (e, row) {
                    $(tableId + " .custom-row-style").removeClass("custom-row-style");
                    $(row).addClass("custom-row-style");
                },
                onDblClickRow: function (row, $element) {
                    if (tableId == "#table4" || "#table5" || "#table6") {
                        var moduleId = tableId == "#table4" ? 10 :
                            tableId == "#table5" ? 11 : 12;
                        if ($(tableId).parents(".table-responsive").next().hasClass("none")) {
                            $.ajax({
                                url: "/alarmSet/getAlarmSetTitle.do",
                                method: "POST",
                                data: {},
                                dataType: "json",
                                beforeSend:function (XMLHttpRequest) {},
                                success: function (data) {
                                    var columnsArry = [];
                                    data.forEach(function (item, index) {
                                        if (item.nameen != "id") {
                                            var colObj = {
                                                field: item.nameen + "Value",
                                                title: item.namezh,
                                                sortable: true
                                            };
                                            columnsArry.push(colObj);
                                        }
                                    });
                                    bsTable.iniKpiSetTab("#kpiSetTab",
                                        "/alarmSet/getAlarmSetData.do", "#kpieditBox", {
                                            "watchpointId": row.id,
                                            "businessId": row.id,
                                            "moduleId": moduleId
                                        }, columnsArry);
                                    $("#kpieditBtn").attr({
                                        "data-watchpointId": row.id,
                                        "data-businessId": row.id,
                                        "data-moduleId": moduleId
                                    });
                                    $(tableId).parents(".table-responsive").next().removeClass("none");
                                },
                                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                    console.error(XMLHttpRequest);
                                    console.error(textStatus);
                                    console.error(errorThorwn);
                                },
                                complete:function (XMLHttpRequest,textStatus) {}
                            });
                        } else {
                            $.ajax({
                                url: "/alarmSet/getAlarmSetData.do",
                                method: "POST",
                                data: {
                                    "watchpointId": row.id,
                                    "businessId": row.id,
                                    "moduleId": moduleId
                                },
                                dataType: "json",
                                beforeSend:function (XMLHttpRequest) {},
                                success: function (data) {
                                    $("#kpieditBtn").attr({
                                        "data-watchpointId": row.id,
                                        "data-businessId": row.id,
                                        "data-moduleId": moduleId
                                    });
                                    $("#kpiSetTab").bootstrapTable("load", data);
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
            });
        },
        /**
         * 验证用户名的长度 不超过32个字符
         * @param str
         * @returns {boolean}
         */
        verification: function (str) {
            /*验证用户名 将一个汉字转换成两个字符*/
            var totalCount = 0;
            if (!str) {
                return false;
            }
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                    totalCount++;
                } else {
                    totalCount += 2;
                }
            }
            if (totalCount) {
                if (totalCount < 32) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        },
        /**
         * 观察点表格的验证
         * @param element
         * @returns {*}
         */
        watPointVerif: function (element) {
            var did = [],
                didId = [],
                vid = [],
                // lid = [],
                vxid = [],
                lid1 = [],
                lid2 = [],
                lid3 = [],
                lid4 = [],
                lid5 = [],
                strName = $("#" + element + " .addtableRowInput").val(),
                bdWval = $("#" + element + " .addtableRowInputbdWidth").val();
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(0).children(".search-choice").length; k++) {
                did.push($("#" + element + " .chzn-choices").eq(0).children(".search-choice").eq(k).children("span").text());
            }
            var optionArry = $("#" + element + " .chzn-choices").eq(0).parent().prev().children("option");
            for (var i = 0; i < did.length; i++) {
                for (var j = 0; j < optionArry.length; j++) {
                    if (did[i] == optionArry.eq(j).text()) {
                        didId.push(optionArry.eq(j).attr("value"))
                    }
                }
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(1).children(".search-choice").length; k++) {
                vid.push($("#" + element + " .chzn-choices").eq(1).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(2).children(".search-choice").length; k++) {
                vxid.push($("#" + element + " .chzn-choices").eq(2).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(3).children(".search-choice").length; k++) {
                lid1.push($("#" + element + " .chzn-choices").eq(3).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(4).children(".search-choice").length; k++) {
                lid2.push($("#" + element + " .chzn-choices").eq(4).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(5).children(".search-choice").length; k++) {
                lid3.push($("#" + element + " .chzn-choices").eq(5).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(6).children(".search-choice").length; k++) {
                lid4.push($("#" + element + " .chzn-choices").eq(6).children(".search-choice").eq(k).children("span").text());
            }
            for (var k = 0; k < $("#" + element + " .chzn-choices").eq(7).children(".search-choice").length; k++) {
                lid5.push($("#" + element + " .chzn-choices").eq(7).children(".search-choice").eq(k).children("span").text());
            }
            var result = bsTable.verification(strName) &&
                bsTable.ValiBandWidth(bdWval) &&
                String(did).length &&
                String(vid).length &&
                String(lid1).length &&
                String(lid2).length &&
                String(lid3).length &&
                String(lid4).length &&
                String(lid5).length &&
                String(vxid).length;
                // String(lid).length;
            if (!bsTable.verification(strName)) {
                jeBox.alert("业务名称不能超过32个字符且不能为空");
                return {
                    "result": false
                };
            }
            if (!String(did).length) {
                jeBox.alert("网卡名不能为空");
                return {
                    "result": false
                };
            }
            if (!String(vid).length) {
                jeBox.alert("VLAN ID不能为空");
                return {
                    "result": false
                };
            } else {
                if (vid.indexOf("0") != -1) {
                    if (vid.length - 1) {
                        jeBox.alert("VLAN ID选择为0的情况下不能选其它VLAN");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(vxid).length) {
                jeBox.alert("VXLAN ID不能为空");
                return {
                    "result": false
                };
            } else {
                if (vxid.indexOf("0") != -1) {
                    if (vxid.length - 1) {
                        jeBox.alert("VXLAN ID选择为0的情况下不能选其它VXLAN ID");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(lid1).length) {
                jeBox.alert("MPLS LABLE1不能为空");
                return {
                    "result": false
                };
            } else {
                if (lid1.indexOf("0") != -1) {
                    if (lid1.length - 1) {
                        jeBox.alert("MPLS LABLE1选择为0的情况下不能选其它MPLS LABLE1");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(lid2).length) {
                jeBox.alert("MPLS LABLE2不能为空");
                return {
                    "result": false
                };
            } else {
                if (lid2.indexOf("0") != -1) {
                    if (lid2.length - 1) {
                        jeBox.alert("MPLS LABLE2选择为0的情况下不能选其它MPLS LABLE2");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(lid3).length) {
                jeBox.alert("MPLS LABLE3不能为空");
                return {
                    "result": false
                };
            } else {
                if (lid3.indexOf("0") != -1) {
                    if (lid3.length - 1) {
                        jeBox.alert("MPLS LABLE3选择为0的情况下不能选其它MPLS LABLE3");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(lid4).length) {
                jeBox.alert("MPLS LABLE4不能为空");
                return {
                    "result": false
                };
            } else {
                if (lid4.indexOf("0") != -1) {
                    if (lid4.length - 1) {
                        jeBox.alert("MPLS LABLE4选择为0的情况下不能选其它MPLS LABLE4");
                        return {
                            "result": false
                        };
                    }
                }
            }
            if (!String(lid5).length) {
                jeBox.alert("MPLS LABLE5不能为空");
                return {
                    "result": false
                };
            } else {
                if (lid5.indexOf("0") != -1) {
                    if (lid5.length - 1) {
                        jeBox.alert("MPLS LABLE5选择为0的情况下不能选其它MPLS LABLE5");
                        return {
                            "result": false
                        };
                    }
                }
            }
            // if (!String(lid).length) {
            //     jeBox.alert("VXLAN ID不能为空");
            //     return {
            //         "result": false
            //     };
            // } else {
            //     if (lid.indexOf("0") != -1) {
            //         if (lid.length - 1) {
            //             jeBox.alert("VXLAN ID选择为0的情况下不能选其它VXLAN ID");
            //             return {
            //                 "result": false
            //             };
            //         }
            //     }
            // }
            return {
                "result": result,
                "did": String(didId),
                "didProtype": String(did),
                "vid": String(vid),
                "vxid": String(vxid),
                "lid1": String(lid1),
                "lid2": String(lid2),
                "lid3": String(lid3),
                "lid4": String(lid4),
                "lid5": String(lid5),
                // "lid": String(lid),
                "bandwidth": bdWval
            };
        },
        /**
         * 验证数字是否为大于0的整数
         * @param obj
         * @returns {boolean}
         */
        isInteger: function (obj) {
            return typeof obj === 'number' && obj % 1 === 0 && obj > 0
        },
        /**
         * 用户端表格增加 或 修改的 用户输入验证
         * @param element
         * @returns {boolean}
         */
        userValiDate: function (element) {
            //名称部分
            var name = $("#" + element + "-modal .addtableRowInput:eq(0)").val();
            if (!bsTable.verification(name)) {
                jeBox.alert("名称应小于32个字符且不能为空");
                return false;
            }

            //网段部分
            var regex = /^\s*((([0-9A-Fa-f]{1,4}:){7}(([0-9A-Fa-f]{1,4})|:))|(([0-9A-Fa-f]{1,4}:){6}(:|((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})|(:[0-9A-Fa-f]{1,4})))|(([0-9A-Fa-f]{1,4}:){5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:)(:[0-9A-Fa-f]{1,4}){0,4}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(:(:[0-9A-Fa-f]{1,4}){0,5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})))(%.+)?\s*$/;
            var displayIp = $("#" + element + "-modal .addtableRowInput:eq(1)").val().split(",");
            for (var i = 0; i < displayIp.length; i++) {
                if (displayIp[i].indexOf("/") > -1) {
                    if (regex.test(displayIp[i].split("/")[0])) {
                        if (displayIp[i].split(".")[3].split("/")[1] != "") {
                            if (displayIp[i].split(".")[3].split("/")[1] >= 16 && displayIp[i].split(".")[3].split("/")[1] <= 32) {

                            } else {
                                jeBox.alert("网段不正确，请输入16-32位网段");
                                return false;
                            }
                        } else {
                            jeBox.alert("网段不正确，请重新输入");
                            return false;
                        }
                    } else {
                        jeBox.alert("不是正确的IP");
                        return false;
                    }
                } else if (displayIp[i].indexOf("-") > -1) {
                    if (regex.test(displayIp[i].split("-")[0]) && regex.test(displayIp[i].split("-")[1])) {
						if(displayIp[i].split("-")[0] != displayIp[i].split("-")[1]){
						
						}else{
							jeBox.alert("前后网段不能相同");
                       		return false;
						}
                    } else {
                        jeBox.alert("网段不正确，请重新输入");
                        return false;
                    }
                }else {
                    if (regex.test(displayIp[i])) {

                    } else {
                        jeBox.alert("不是正确的IP");
                        return false;
                    }
                }
            }

            var bandwidth = $("#" + element + "-modal .addtableRowInput:eq(2)").val();
            var p = /^[0-9]{1,20}$/;
            if (!p.test(bandwidth)) {
                jeBox.alert("带宽输入错误");
                return false;
            } else if (bandwidth == "0" || bandwidth == "") {
                jeBox.alert("带宽不能为空或者0");
                return false;
            } else if (bandwidth > 10000000000) {
                jeBox.alert("宽带最大为10G");
                return false;
            }
            return true;
        },
        /**
         *  验证客户端IP（暂用报文验证）clientIpPort
         * @param clientIpPort
         * @returns {boolean}
         * @constructor
         */
        ValclientIpPort: function (clientIpPort) {
            //网段部分
            var regex = /^\s*((([0-9A-Fa-f]{1,4}:){7}(([0-9A-Fa-f]{1,4})|:))|(([0-9A-Fa-f]{1,4}:){6}(:|((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})|(:[0-9A-Fa-f]{1,4})))|(([0-9A-Fa-f]{1,4}:){5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:)(:[0-9A-Fa-f]{1,4}){0,4}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(:(:[0-9A-Fa-f]{1,4}){0,5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})))(%.+)?\s*$/,
                displayIp = clientIpPort.split(","),
                result = true;
            for (var i = 0; i < displayIp.length; i++) {
                if (displayIp[i].indexOf("/") > -1) {
                    if (regex.test(displayIp[i].split("/")[0])) {
                        if (displayIp[i].split(".")[3].split("/")[1] != "") {
                            if (displayIp[i].split(".")[3].split("/")[1] >= 16 && displayIp[i].split(".")[3].split("/")[1] <= 32) {

                            } else {
                                // alert("网段不正确，请输入16-32位网段！");
                                result = false;
                                return result;
                            }
                        } else {
                            // alert("网段不正确，请重新输入");
                            result = false;
                            return result;
                        }
                    } else {
                        // alert("不是正确的IP!");
                        result = false;
                        return result;
                    }
                } else if (displayIp[i].indexOf("-") > -1) {
                    if (regex.test(displayIp[i].split("-")[0]) || regex.test(displayIp[i].split("-")[1])) {

                    } else {
                        // alert("网段不正确，请重新输入 ！");
                        result = false;
                        return result;
                    }
                } else {
                    if (regex.test(displayIp[i])) {

                    } else {
                        // alert("不是正确的IP！");
                        result = false;
                        return result;
                    }
                }
            }
            return result;
        },
        /**
         * 带宽验证
         * @param data
         * @returns {boolean}
         * @constructor
         */
        ValiBandWidth: function (data) {
            var p = /^[0-9]{1,20}$/;
            if (!p.test(data)) {
                jeBox.alert("带宽输入错误");
                return false;
            } else if (data == "0" || data == "") {
                jeBox.alert("带宽不能为空或者0");
                return false;
            } else if (data > 10000000000) {
                jeBox.alert("宽带最大为10G");
                return false;
            } else {
                return true;
            }
        },
        /**
         * 验证单个端口的合法性
         * @param port
         * @returns {boolean}
         */
        valiPort: function (port) {
            /*
             * 大于0小于等于65535且必须为整数
             */
            return port % 1 === 0 && 0 < port && port <= 65535
        },
        /**
         * 验证单个ip的合法性
         * @param ip
         * @returns {Boolean}
         */
        valiIp: function (ip) {
            var regIp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; //验证IP的合法性
            return regIp.test(ip);
        },
        /**
         *
         * @param email
         */
        valEmail:function(email){
            var regEmail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
            return regEmail.test(email)
        },
        /**
         * 验证电话号码是否合法
         * @param phoneNum
         * @returns {Boolean}
         */
        valphone:function(phoneNum){
            var reg1 = /^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/,
                reg2 = /^1[3|4|5|7|8][0-9]{9}$/;
            return reg1.test(phoneNum) || reg2.test(phoneNum);
        },
        /**
         * 四个新模块服务端IP输入验证
         * @param serverIpVal
         * @returns {boolean}
         */
        servValiDate: function (serverIpVal) {
            var serverBoolan = true,
                serverIp = serverIpVal,
                hash = {},
                regIp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; //验证IP的合法性
            for (var i = 0; i < serverIp.split(",").length; i++) {
                var serIpChild = serverIp.split(",")[i];
                if (serIpChild.split(":").length - 1) {
                    if (serIpChild.split("-").length > 3) {
                        serverBoolan = false;
                    } else {
                        switch (serIpChild.split("-").length) {
                            case 1:
                                //192.168.1.1:80
                                if (!(regIp.test(serIpChild.split(":")[0]) && serIpChild.split(":")[1] <= 65535)) {
                                    serverBoolan = false;
                                }
                                break;
                            case 2:
                                if (serIpChild.split("-")[0].split(":").length - 1) {
                                    //192.168.1.1:80-100
                                    if (!(regIp.test(serIpChild.split("-")[0].split(":")[0]) &&
                                            serIpChild.split("-")[0].split(":")[1] <= 65535 &&
                                            Number(serIpChild.split("-")[0].split(":")[1]) < Number(serIpChild.split("-")[1]) &&
                                            serIpChild.split("-")[1] <= 65535)) {
                                        serverBoolan = false;
                                    }
                                } else {
                                    //192.168.1.1-192.168.1.10:80
                                    if (serIpChild.split("-")[1].split(":").length - 1) {
                                        if (!(regIp.test(serIpChild.split("-")[0]) && regIp.test(serIpChild.split("-")[1].split(":")[0]) && serIpChild.split("-")[1].split(":")[1] <= 65535)) {
                                            serverBoolan = false;
                                        }
                                    } else {
                                        serverBoolan = false;
                                    }
                                }
                                break;
                            case 3:
                                //192.168.1.1-192.168.1.10:80-100
                                if (serIpChild.split("-")[1].split(":").length - 1) {
                                    var IpLen3Bol = regIp.test(serIpChild.split("-")[0]) &&
                                        regIp.test(serIpChild.split("-")[1].split(":")[0]) &&
                                        serIpChild.split("-")[1].split(":")[1] <= 65535 &&
                                        Number(serIpChild.split("-")[1].split(":")[1]) < Number(serIpChild.split("-")[2]) &&
                                        serIpChild.split("-")[2] <= 65535;
                                    if (!IpLen3Bol) {
                                        serverBoolan = false;
                                    }
                                } else {
                                    serverBoolan = false;
                                }
                                break;
                            default:
                                serverBoolan = false;
                        }
                    }
                } else {
                    serverBoolan = false;
                }
            }
            for (var i in serverIp.split(",")) {
                if (hash[serverIp.split(",")[i]]) {
                    serverBoolan = false;
                } else {
                    hash[serverIp.split(",")[i]] = true;
                }
            }
            return serverBoolan;
        },
        /**
         * 服务端IP输入验证
         * 服务端验证新增可输入端口且输入端口时不可输入IP两者只能输入其一咱
         * 端口输入的规则
         * 82，82，90
         * 80-90，100-300
         * 可为一个
         * @param serverIpVal
         * @param isServer
         * @returns {boolean}
         */
        $servValiDate: function (serverIpVal, isServer) {
            var serverBoolan = true,
                serverIp = serverIpVal,
                hash = {},
                reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
                regIp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; //验证IP的合法性(带端口)
            for (var i = 0; i < serverIp.split(",").length; i++) {
                var serIpChild = serverIp.split(",")[i];
                if (serIpChild.split(":").length - 1) {
                    if (serIpChild.split("-").length > 3) {
                        serverBoolan = false;
                    } else {
                        switch (serIpChild.split("-").length) {
                            case 1:
                                //192.168.1.1:80
                                if (!(regIp.test(serIpChild.split(":")[0]) && serIpChild.split(":")[1] <= 65535)) {
                                    serverBoolan = false;
                                }
                                break;
                            case 2:
                                if (serIpChild.split("-")[0].split(":").length - 1) {
                                    //192.168.1.1:80-100
                                    if (!(regIp.test(serIpChild.split("-")[0].split(":")[0]) &&
                                            serIpChild.split("-")[0].split(":")[1] <= 65535 &&
                                            Number(serIpChild.split("-")[0].split(":")[1]) < Number(serIpChild.split("-")[1]) &&
                                            serIpChild.split("-")[1] <= 65535)) {
                                        serverBoolan = false;
                                    }
                                } else {
                                    //192.168.1.1-192.168.1.10:80
                                    if (serIpChild.split("-")[1].split(":").length - 1) {
                                        if (!(regIp.test(serIpChild.split("-")[0]) && regIp.test(serIpChild.split("-")[1].split(":")[0]) && serIpChild.split("-")[1].split(":")[1] <= 65535)) {
                                            serverBoolan = false;
                                        }
                                    } else {
                                        //192.168.1.1-192.168.1.10
                                        //serverBoolan = false;
                                        if (serIpChild.split("-")[0].split(".")[0] == serIpChild.split("-")[1].split(".")[0] &&
                                            serIpChild.split("-")[0].split(".")[1] == serIpChild.split("-")[1].split(".")[1] &&
                                            serIpChild.split("-")[0].split(".")[2] == serIpChild.split("-")[1].split(".")[2] &&
                                            (serIpChild.split("-")[1].split(".")[3] - serIpChild.split("-")[0].split(".")[3]) &&
                                            reg.test(serIpChild.split("-")[0]) && reg.test(serIpChild.split("-")[1])) {

                                        } else {
                                            serverBoolan = false;
                                        }
                                    }
                                }
                                break;
                            case 3:
                                //192.168.1.1-192.168.1.10:80-100
                                if (serIpChild.split("-")[1].split(":").length - 1) {
                                    var IpLen3Bol = regIp.test(serIpChild.split("-")[0]) &&
                                        regIp.test(serIpChild.split("-")[1].split(":")[0]) &&
                                        serIpChild.split("-")[1].split(":")[1] <= 65535 &&
                                        Number(serIpChild.split("-")[1].split(":")[1]) < Number(serIpChild.split("-")[2]) &&
                                        serIpChild.split("-")[2] <= 65535;
                                    if (!IpLen3Bol) {
                                        serverBoolan = false;
                                    }
                                } else {
                                    serverBoolan = false;
                                }
                                break;
                            default:
                                serverBoolan = false;
                        }
                    }
                } else {
                    //192.168.1.100-192.168.1.110    或者为纯端口 80-90
                    if (serIpChild.split("-").length == 2) {
                        if (serIpChild.split("-")[0].split(".")[0] == serIpChild.split("-")[1].split(".")[0] &&
                            serIpChild.split("-")[0].split(".")[1] == serIpChild.split("-")[1].split(".")[1] &&
                            serIpChild.split("-")[0].split(".")[2] == serIpChild.split("-")[1].split(".")[2] &&
                            (serIpChild.split("-")[1].split(".")[3] - serIpChild.split("-")[0].split(".")[3]) &&
                            reg.test(serIpChild.split("-")[0]) && reg.test(serIpChild.split("-")[1])) {

                        } else {
                            if (isServer) {
                                // 此处进行端口验证 80-90
                                if (serIpChild.split("-").length == 2) {
                                    if (serIpChild.split("-")[1] - serIpChild.split("-")[0] > 0) {
                                        if (!bsTable.valiPort(serIpChild.split("-")[0]) || !bsTable.valiPort(serIpChild.split("-")[1])) {
                                            serverBoolan = false;
                                        }
                                    } else {
                                        serverBoolan = false;
                                    }
                                } else {
                                    serverBoolan = false;
                                }
                            } else {
                                serverBoolan = false;
                            }
                        }
                    } else {
                        if (!reg.test(serIpChild)) {
                            if (isServer) {
                                //此处进行端口验证 80 valiPort
                                if (!bsTable.valiPort(serIpChild)) {
                                    serverBoolan = false;
                                }
                            } else {
                                serverBoolan = false;
                            }
                        }
                    }
                }
            }
            //此代码是为咯防止用户输入为空并还用逗号隔开咯和验证用户是否输入有重
            for (var i in serverIp.split(",")) {
                if (hash[serverIp.split(",")[i]]) {
                    serverBoolan = false;
                } else {
                    hash[serverIp.split(",")[i]] = true;
                }
            }
            return serverBoolan;
        },
        /**
         * 将后端时间转换成用户可看的时间
         * @param nS
         * @returns {*}
         */
        getLocalTime: function (nS) {
            return $.myTime.UnixToDate(nS * 1000);
        },
        /**
         * 删除某一行的功能
         * @param tableId 某个表格的id
         * @param revUrl 删除所需要的url
         * @param page 某个页面 用来区分删除的不同实现的代码差异
         * @param iniUrl 初始化url
         * @param _moduleId 模块ID
         * @param _thisBtn 当前button
         */
        removeRow: function (tableId, revUrl, page, iniUrl, _moduleId,_thisBtn) {
            var selectRowId = $(tableId + " .custom-row-style").attr("data-id");
            if (selectRowId != undefined) {
                if(_thisBtn){
                    _thisBtn.button("loading");
                    _thisBtn.addClass("groupKpiCanUsed");
                }
                switch (page) {
                    case "cenTer":
                        $.ajax({
                            url:revUrl,
                            method:"POST",
                            data:{
                                id:selectRowId
                            },
                            dataType:"json",
                            beforeSend:function () {},
                            success:function (data) {
                                if(data.stauts){
                                    $(tableId).bootstrapTable("refresh");
                                }else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (xhr,textStatus,error) {
                                console.error(xhr);
                                console.error(textStatus);
                                console.error(error);
                                jeBox.alert("删除失败，请稍后再试");
                            },
                            complete:function () {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                            }
                        });
                        break;
                    case "userSide":
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                moduleId:_moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                // if (data.success == "1") {
                                //     $(tableId).bootstrapTable('remove', {
                                //         field: 'id',
                                //         values: [selectRowId - 0]
                                //     });
                                // } else {
                                //     jeBox.alert("删除失败，请稍后再试");
                                // }
                                if(+data.state){
                                    $(tableId).bootstrapTable('remove', {
                                        field: 'id',
                                        values: [selectRowId - 0]
                                    });
                                }else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "serverSide":
                        $.ajax({
                            url:revUrl,
                            method:"POST",
                            data:{
                                id:selectRowId,
                                moduleId:_moduleId
                            },
                            dataType:"json",
                            beforeSend:function (XMLHttpRequest) {},
                            success:function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                if(+data.state){
                                    $(tableId).bootstrapTable("refresh");
                                }else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "webSer":
                    case "oraSer":
                    case "mySqlSer":
                    case "sqlSer":
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                moduleId: _moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                // if (data.success) {
                                //     $(tableId).bootstrapTable("refresh", {
                                //         silent: false,
                                //         query: {
                                //             moduleId: _moduleId
                                //         }
                                //     });
                                // } else {
                                //     jeBox.alert("删除失败，请稍后再试");
                                // }
                                if(+data.state){
                                    $(tableId).bootstrapTable("refresh", {
                                        silent: false,
                                        query: {
                                            moduleId: _moduleId
                                        }
                                    });
                                }else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "urlSer":
                    case "centerTable":
                    case "SyslogTable":
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                moduleId:page == "urlSer" ? _moduleId : undefined
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                if(page == "urlSer"){
                                    if(+data.state){
                                        $(tableId).bootstrapTable("refresh");
                                    }else {
                                        jeBox.alert("删除失败，请稍后再试");
                                    }
                                }else {
                                    if (data.stauts) {
                                        $(tableId).bootstrapTable("refresh");
                                    } else {
                                        jeBox.alert("删除失败，请稍后再试");
                                    }
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "baowenJySer":
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                appId: selectRowId,
                                moduleId:_moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                // if (data.success) {
                                //     $(tableId).bootstrapTable("refresh");
                                // } else {
                                //     jeBox.alert("删除失败，请稍后再试");
                                // }
                                if(+data.state){
                                    $(tableId).bootstrapTable("refresh");
                                }else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "usabilityDel":
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                id: selectRowId
                            },
                            dataType: "json",
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                if (data == true) {
                                    $(tableId).bootstrapTable("refresh");
                                } else {
                                    jeBox.alert("删除失败，请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    default:
                        $.ajax({
                            url: revUrl,
                            method: "POST",
                            data: {
                                id: selectRowId
                            },
                            dataType: page == "serverSide" ? "text" : "json",
                            success: function (data,textStatus,XMLHttpRequest) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                switch (page) {
                                    case "observationPoint":
                                        $(tableId).bootstrapTable("refresh");
                                        break;
                                    case "serverSide":
                                        if (data == "success") {
                                            $(tableId).bootstrapTable("refresh");
                                        } else {
                                            jeBox.alert("删除失败，请稍后再试");
                                        }
                                        break;
                                    default:
                                        if (data.success - 0) {
                                            dataList = [];
                                            $(tableId).bootstrapTable('remove', {
                                                field: 'id',
                                                values: [selectRowId - 0]
                                            });
                                            $(tableId).bootstrapTable("refresh");
                                        } else {
                                            jeBox.alert("删除失败，请稍后再试");
                                        }
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                if(_thisBtn){
                                    _thisBtn.button("reset");
                                    _thisBtn.removeClass("groupKpiCanUsed");
                                }
                                $('#Confirm-modal').modal('hide');
                                jeBox.alert("删除失败，请稍后再试");
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                }
            }
        },
        /**
         * 编辑表格行功能实现
         * @param tableId
         * @param ediUrl
         * @param page
         * @param iniUrl
         * @param _moduleId
         */
        ediTableRow: function (tableId, ediUrl, page, iniUrl, _moduleId) {
            var selectRowId = $(tableId + " .custom-row-style").attr("data-id"),
                serverIpVal = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),
                index = $(tableId + " .custom-row-style").attr("data-index"),
                modifyData,
                beChange;
            for(var i=0;i<$(tableId).bootstrapTable("getData").length;i++){
                if($(tableId).bootstrapTable("getData")[i].id == selectRowId){
                    modifyData = $(tableId).bootstrapTable("getData")[i];
                }
            }
            switch (page) {
                case "cenTer":
                    var name = $("#changetableRow-modal input.addtableRowInput").eq(0).val(),
                        ip = $("#changetableRow-modal input.addtableRowInput").eq(1).val(),
                        port = $("#changetableRow-modal input.addtableRowInput").eq(2).val(),
                        contacts = $("#changetableRow-modal input.addtableRowInput").eq(3).val(),
                        telephone = $("#changetableRow-modal input.addtableRowInput").eq(4).val(),
                        email = $("#changetableRow-modal input.addtableRowInput").eq(5).val(),
                        uptime = $.myTime.DateToUnix($("#changetableRow-modal input.addtableRowInput").eq(6).val()+" 00:00:00"),
                        limitdate = $.myTime.DateToUnix($("#changetableRow-modal input.addtableRowInput").eq(7).val()+" 00:00:00"),
                        state = $("#changetableRow-modal  #center-stage option:selected").val(),
                        version = $("#changetableRow-modal  #center-version option:selected").val();
                    if(
                        bsTable.verification(name) &&
                        bsTable.valiIp(ip) &&
                        bsTable.valiPort(port) &&
                        bsTable.verification(contacts) &&
                        isNaN(contacts) &&
                        telephone &&
                        bsTable.valphone(telephone) &&
                        bsTable.valEmail(email) &&
                        uptime &&
                        limitdate &&
                        $("#changetableRow-modal input.addtableRowInput").eq(6).val() &&
                        $("#changetableRow-modal input.addtableRowInput").eq(7).val() &&
                        (limitdate - uptime > 0 )
                    ){
                        $("#changetableRow-modal").modal("hide");
                        $.ajax({
                            url:ediUrl,
                            method:"POST",
                            data:{
                                name:name,
                                ip:ip,
                                port:port,
                                contacts:contacts,
                                telephone:telephone,
                                email:email,
                                uptime:uptime,
                                limitdate:limitdate,
                                state:state,
                                version:version,
                                id:selectRowId
                            },
                            dataType:"json",
                            beforeSend:function(){},
                            success:function(data){
                                if(data.stauts){
                                    $(tableId).bootstrapTable("refresh");
                                }else {
                                    jeBox.alert("修改失败,请稍后再试");
                                }
                            },
                            error:function(xhr,textStatus,error){
                                console.error(xhr);
                                console.error(textStatus);
                                console.error(error);
                                jeBox.alert("修改失败,请稍后再试");
                            },
                            complete:function(){}
                        })
                    }else {
                        if(!name){
                            jeBox.alert("用户名不能为空");
                            return
                        }
                        if(!bsTable.verification(name)){
                            jeBox.alert("用户名的长度不得超过32个字符");
                            return
                        }
                        if(!ip){
                            jeBox.alert("IP不能为空");
                            return
                        }
                        if(!bsTable.valiIp(ip)){
                            jeBox.alert("IP不合法");
                            return
                        }
                        if(!port){
                            jeBox.alert("端口不能为空");
                            return
                        }
                        if(!bsTable.valiPort(port)){
                            jeBox.alert("端口不合法");
                            return
                        }
                        if(!contacts){
                            jeBox.alert("联系人不能为空");
                            return
                        }
                        if(!isNaN(contacts)){
                            jeBox.alert("联系人不能为纯数字");
                            return
                        }
                        if(!bsTable.verification(contacts)){
                            jeBox.alert("联系人的长度不得超过32个字符");
                            return
                        }
                        if(!telephone){
                            jeBox.alert("电话不能为空");
                            return
                        }
                        if(!bsTable.valphone(telephone)){
                            jeBox.alert("电话号码不符合规范");
                            return
                        }
                        if(!email){
                            jeBox.alert("Email不能为空");
                            return
                        }
                        if(!bsTable.valEmail(email)){
                            jeBox.alert("Email不合法");
                            return
                        }
                        if(!$("#changetableRow-modal input.addtableRowInput").eq(6).val()){
                            jeBox.alert("上线时间不能为空");
                            return
                        }
                        if(!$("#changetableRow-modal input.addtableRowInput").eq(7).val()){
                            jeBox.alert("服务时限不能为空");
                            return
                        }
                        if(limitdate - uptime <= 0 ){
                            jeBox.alert("服务时限必须大于上线时间");
                            return
                        }
                    }
                    break;
                case "observationPoint":
                    var resultObject = bsTable.watPointVerif("changetableRow-modal");
                    if (resultObject.result) {
                        resultObject.id = selectRowId;
                        var count = getCardNumCount($(tableId).bootstrapTable('getData'), resultObject, false);
                        if (!bsTable.verName(tableId, $.trim($("#changetableRow-modal .addtableRowInput").val()), index)) {
                            return;
                        }
                        // if (!bsTable.verObserConten(tableId, resultObject, index)) {
                        //     return;
                        // }
                        if (count.uc > 3) {
                            jeBox.alert("网卡数量不能超过3个，请返回修改");
                            return;
                        }
                        $('#changetableRow-modal').modal('hide');
                        if(
                            modifyData.vid != resultObject.vid ||
                            modifyData.vxid != resultObject.vxid ||
                            modifyData.lid1 != resultObject.lid1 ||
                            modifyData.lid2 != resultObject.lid2 ||
                            modifyData.lid3 != resultObject.lid3 ||
                            modifyData.lid4 != resultObject.lid4 ||
                            modifyData.lid5 != resultObject.lid5 ||
                            modifyData.bandwidth != resultObject.bandwidth
                        ){
                            beChange = true;
                        }
                        if(modifyData.did.replace(/ /g,"") != resultObject.didProtype){
                            //网卡名修改 重启机器
                            jeBox({
                                cell:"jbx",
                                title:"提示",
                                //boxSize:["400px","150px"],
                                content:'检测到您修改了网卡名,需要重启物理机器,是否继续？',
                                maskLock : true ,
                                btnAlign:"center",
                                button:[
                                    {
                                        name: '确定',
                                        callback: function(index){
                                            jeBox.close(index);
                                            $.ajax({
                                                url: ediUrl,
                                                method: "POST",
                                                data: {
                                                    id: selectRowId,
                                                    "name": $.trim($("#changetableRow-modal .addtableRowInput").val()),
                                                    "did": resultObject.did,
                                                    "vid": resultObject.vid,
                                                    "vxid": resultObject.vxid,
                                                    "lid1": resultObject.lid1,
                                                    "lid2": resultObject.lid2,
                                                    "lid3": resultObject.lid3,
                                                    "lid4": resultObject.lid4,
                                                    "lid5": resultObject.lid5,
                                                    // "lid": resultObject.lid,
                                                    "bandwidth": resultObject.bandwidth
                                                },
                                                dataType: "json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success: function (data,textStatus,XMLHttpRequest) {
                                                    //1 success 0 fail
                                                    if(data){
                                                        $.post("/systemSet/rebootServer.do");
                                                        setTimeout(function() {
                                                            setInterval(function() {
                                                                $.post("/sysNetworkSet/getNetworkCardInfo.do", null, function(d, s) {
                                                                    if(s == "success") {
                                                                        window.location.href = window.location.protocol + "//" + window.location.host;
                                                                    }
                                                                });
                                                            }, 3000);
                                                        }, 30000);
                                                    }else {
                                                        jeBox.alert("修改失败,请稍后再试");
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
                                    },
                                    {
                                        name: '取消',
                                        callback:function (index) {
                                            jeBox.close(index);
                                            $.ajax({
                                                url: ediUrl,
                                                method: "POST",
                                                data: {
                                                    id: selectRowId,
                                                    "name": $.trim($("#changetableRow-modal .addtableRowInput").val()),
                                                    "did": resultObject.did,
                                                    "vid": resultObject.vid,
                                                    "vxid": resultObject.vxid,
                                                    "lid1": resultObject.lid1,
                                                    "lid2": resultObject.lid2,
                                                    "lid3": resultObject.lid3,
                                                    "lid4": resultObject.lid4,
                                                    "lid5": resultObject.lid5,
                                                    // "lid": resultObject.lid,
                                                    "bandwidth": resultObject.bandwidth
                                                },
                                                dataType: "json",
                                                beforeSend:function (XMLHttpRequest) {},
                                                success: function (data,textStatus,XMLHttpRequest) {
                                                    //1 success 0 fail
                                                    if(data){
                                                        $(tableId).bootstrapTable("refresh",{
                                                            silent: false,
                                                            query: {
                                                                moduleId: _moduleId
                                                            }
                                                        });
                                                        bsTable.analysisAgain("#changetableRow-modal");
                                                    }else {
                                                        jeBox.alert("修改失败,请稍后再试");
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
                                    }
                                ]
                            })
                        }else {
                            $.ajax({
                                url: ediUrl,
                                method: "POST",
                                data: {
                                    id: selectRowId,
                                    "name": $.trim($("#changetableRow-modal .addtableRowInput").val()),
                                    "did": resultObject.did,
                                    "vid": resultObject.vid,
                                    "vxid": resultObject.vxid,
                                    "lid1": resultObject.lid1,
                                    "lid2": resultObject.lid2,
                                    "lid3": resultObject.lid3,
                                    "lid4": resultObject.lid4,
                                    "lid5": resultObject.lid5,
                                    // "lid": resultObject.lid,
                                    "bandwidth": resultObject.bandwidth
                                },
                                dataType: "json",
                                beforeSend:function (XMLHttpRequest) {},
                                success: function (data,textStatus,XMLHttpRequest) {
                                    //1 success 0 fail
                                    if(data){
                                        $(tableId).bootstrapTable("refresh",{
                                            silent: false,
                                            query: {
                                                moduleId: _moduleId
                                            }
                                        });
                                        bsTable.analysisAgain("#changetableRow-modal");
                                    }else {
                                        jeBox.alert("修改失败,请稍后再试");
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
                    }
                    break;
                case "userSide":
                    var name = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),
                        displayIp = $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()),
                        bandwidth = $.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val()),
                        descrption = $.trim($("#changetableRow-modal .addtableRowInput:eq(3)").val());
                    if (!bsTable.verName(tableId, name, index) || !bsTable.verSerUseIp(tableId, displayIp, index)) {
                        return;
                    }
                    if (bsTable.userValiDate("changetableRow")) {
                        $('#changetableRow-modal').modal('hide');
                        if(
                            modifyData.displayIp != displayIp ||
                            modifyData.bandwidth != bandwidth
                        ){
                            beChange = true;
                        }
                        $.ajax({
                            url: ediUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                bandwidth: bandwidth,
                                moduleId: _moduleId,
                                name: name,
                                displayIp: displayIp,
                                descrption: descrption
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.success == "1") {
                                //     $(tableId).bootstrapTable("refresh",{
                                //         silent: false,
                                //         query: {
                                //             moduleId: _moduleId
                                //         }
                                //     });
                                //     bsTable.analysisAgain("#changetableRow-modal");
                                //     // if(beChange){
                                //     //     bsTable.fenXalert();
                                //     // }
                                // } else {
                                //     jeBox.alert("后端验证不通过，修改失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh",{
                                            silent: false,
                                            query: {
                                                moduleId: _moduleId
                                            }
                                        });
                                        bsTable.analysisAgain("#changetableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("修改失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("修改失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("修改失败,请稍后再试");
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
                    break;
                case "serverSide":
                    var serverIp = $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()),
                        strName = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),
                        _bandwidth = $.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val()),
                        _descrption = $.trim($("#changetableRow-modal .addtableRowInput:eq(3)").val());
                    if (!bsTable.verName(tableId, strName, index) || !bsTable.verSerUseIp(tableId, serverIp, index)) {
                        return;
                    }
                    if (bsTable.verification(strName) && bsTable.$servValiDate(serverIp, true) && bsTable.ValiBandWidth(_bandwidth)) {
                        $('#changetableRow-modal').modal('hide');
                        if(
                            modifyData.displayIp != serverIp ||
                            modifyData.bandwidth != _bandwidth
                        ){
                            beChange = true;
                        }
                        $.ajax({
                            url: ediUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                name: strName,
                                displayIp: serverIp,
                                bandwidth: _bandwidth,
                                descrption: _descrption,
                                moduleId:_moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data == "success") {
                                //     // $(tableId).bootstrapTable('updateRow', {
                                //     //     index: index,
                                //     //     row: {
                                //     //         id: selectRowId,
                                //     //         name: strName,
                                //     //         displayIp: serverIp,
                                //     //         bandwidth: _bandwidth,
                                //     //         descrption: _descrption
                                //     //     }
                                //     // });
                                //     $(tableId).bootstrapTable("refresh",{
                                //         silent: false,
                                //         query: {
                                //             moduleId: _moduleId
                                //         }
                                //     });
                                //     bsTable.analysisAgain("#changetableRow-modal");
                                //     // if(beChange){
                                //     //     bsTable.fenXalert();
                                //     // }
                                // } else {
                                //     jeBox.alert("后端验证不通过，修改失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh",{
                                            silent: false,
                                            query: {
                                                moduleId: _moduleId
                                            }
                                        });
                                        bsTable.analysisAgain("#changetableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("修改失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("修改失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("修改失败,请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    } else {
                        if (!bsTable.verification(strName)) {
                            jeBox.alert("名称不能超过32个字符且不能为空");
                            return;
                        }
                        if (!bsTable.$servValiDate(serverIp, true)) {
                            jeBox({
                                button: [{
                                    name: '确定'
                                }],
                                boxSize: ["320px", "166px"],
                                padding: "5px 5px 5px 13px",
                                content: '<div style="padding:5px;">服务端IP端口不能为空且必须合法!<br>多个IP或端口之间用英文逗号隔开，且不可重复!<br>纯端口监控不能与IP端口监控混合输入!</div>',
                                maskLock: true,
                                boxStyle: {
                                    "text-align": "left"
                                }
                            });
                            return;
                        }
                    }
                    break;
                case "urlSer":
                    var urlName = $("#urlName").val(),
                        urlDescrption = $("#urlps").val(),
                        TabData = $("#handlUrl").bootstrapTable("getData"),
                        rVal = true,
                        dataObj = {
                            id: selectRowId,
                            name: urlName,
                            descrption: urlDescrption
                        };
                    if (!bsTable.verification(urlName)) {
                        jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                        rVal = false;
                        return;
                    }
                    if(!bsTable.verName(tableId,urlName,index)){
                        rVal = false;
                        return;
                    }
                    var nameObj = {
                        num:[],
                        name:[]
                    };
                    if (TabData.length) {
                        //还差判断数组中编号是否为重
                        for (var i = 0; i < TabData.length; i++) {
                            if (!bsTable.isInteger(TabData[i].num - 0)) {
                                jeBox.alert("编号必须为大于0的整数");
                                rVal = false;
                                return;
                            }
                            if(nameObj.num.indexOf(String(TabData[i].num))==-1){
                                nameObj.num.push(String(TabData[i].num))
                            }else {
                                jeBox.alert("编号不可重复");
                                rVal = false;
                                return;
                            }
                            if (!bsTable.verification(TabData[i].name)) {
                                jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                                rVal = false;
                                return;
                            }
                            if(nameObj.name.indexOf(TabData[i].name)==-1){
                                nameObj.name.push(TabData[i].name)
                            }else {
                                jeBox.alert("名称不可重复");
                                rVal = false;
                                return;
                            }
                            if ($.trim(TabData[i].url) == "") {
                                jeBox.alert("URL不能为空");
                                return;
                            }
                        }
                    } else {
                        jeBox.alert("至少保留一条URL");
                        rVal = false;
                        return;
                    }
                    if (rVal) {
                        var set = [];
                        $.ajax({
                            url: "/url/get.do",
                            method: "POST",
                            data: {
                                id: $("#urlSer .custom-row-style").attr("data-id")
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                var reData = data.set,
                                    _index = 0;
                                for (var j = 0; j < reData.length; j++) {
                                    for (var k = 0; k < TabData.length; k++) {
                                        dataObj["set[" + j + "]"] = {};
                                        /*
                                         * 要实现的功能是 新的表格内的数据跟老的表格数据比对
                                         * 若是新增的状态写为1
                                         * 若是修改的则状态写为2
                                         * 若是删除的则状态写为3
                                         *  两数组a b  a为原数组 b为新数组
                                         *  要实现a数组元素id与b数组元素id相同则标记状态为2则为修改
                                         *  若a数组元素id在b数组元素id中找不到则标记状态为3则为删除
                                         *  若b数组元素id在a数组元素id中找不到则标记状态为1则为新增
                                         *
                                         *
                                         */
                                        if (reData[j].id == TabData[k].id) {
                                            //未删除 修改
                                            dataObj["set[" + j + "].name"] = TabData[k].name;
                                            dataObj["set[" + j + "].num"] = TabData[k].num;
                                            dataObj["set[" + j + "].id"] = TabData[k].id;
                                            dataObj["set[" + j + "].url"] = TabData[k].url;
                                            dataObj["set[" + j + "].stauts"] = 2;
                                            break;
                                        } else {
                                            if (k == (TabData.length - 1)) {
                                                //删除
                                                dataObj["set[" + j + "].name"] = reData[j].name;
                                                dataObj["set[" + j + "].num"] = reData[j].num;
                                                dataObj["set[" + j + "].id"] = reData[j].id;
                                                dataObj["set[" + j + "].url"] = reData[j].url;
                                                dataObj["set[" + j + "].stauts"] = 3;
                                                break;
                                            }
                                        }
                                    }
                                }
                                for (var i = 0; i < TabData.length; i++) {
                                    if (String(TabData[i].id).indexOf("s") != -1) {
                                        dataObj["set[" + (reData.length + _index) + "]"] = {};
                                        //这是添加的
                                        dataObj["set[" + (reData.length + _index) + "].name"] = TabData[i].name;
                                        dataObj["set[" + (reData.length + _index) + "].num"] = TabData[i].num;
                                        dataObj["set[" + (reData.length + _index) + "].url"] = TabData[i].url;
                                        dataObj["set[" + (reData.length + _index) + "].stauts"] = 1;
                                        _index++;
                                    }
                                }
                                dataObj.moduleId = _moduleId;
                                $('#changetableRow-modal').modal('hide');
                                $.ajax({
                                    url: ediUrl,
                                    async: false,
                                    method: "POST",
                                    data: dataObj,
                                    dataType: "json",
                                    beforeSend:function (XMLHttpRequest) {},
                                    success: function (data,textStatus,XMLHttpRequest) {
                                        // if (data.stauts) {
                                        //     $(tableId).bootstrapTable("refresh");
                                        // } else {
                                        //     jeBox.alert("修改失败，请稍后再试");
                                        // }
                                        switch (+data.state){
                                            case 1:
                                                $(tableId).bootstrapTable("refresh");
                                                break;
                                            case 0:
                                                jeBox.alert("修改失败,请稍后再试");
                                                break;
                                            case 2:
                                                jeBox.alert("修改失败,与其他业务IP重复");
                                                break;
                                            default:
                                                jeBox.alert("修改失败,请稍后再试");
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
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    }
                    break;
                case "baowenJySer":
                    // var TabData = $("#handlUrl").bootstrapTable("getData"),
                    //     rVal = true,
                    //     baoWenName = [],
                    //     dataObj = {};
                    // for (var i = 0; i < TabData.length; i++) {
                    //     //判断交易名称是否合法
                    //     if (!bsTable.verification($.trim(TabData[i].name))) {
                    //         jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     if(baoWenName.indexOf(TabData[i].name)==-1){
                    //         baoWenName.push(TabData[i].name)
                    //     }else {
                    //         jeBox.alert("名称不可重复");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     //判断服务端IP是否合法
                    //     if (!bsTable.servValiDate($.trim(TabData[i].displayIp))) {
                    //         jeBox.alert("‘服务端IP端口’不能为空，且必须合法，多个IP之间用英文逗号隔开");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     //判断客户端IP是否合法 ValclientIpPort
                    //     if (!($.trim(TabData[i].clientIpport) == "") && !bsTable.ValclientIpPort($.trim(TabData[i].clientIpport))) {
                    //         jeBox.alert("‘客户端IP’不能为空，且必须合法，多个IP之间用英文逗号隔开");
                    //         rVal = false;
                    //         return;
                    //     }
                    // }
                    // if (rVal) {
                    //     for (var j = 0; j < TabData.length; j++) {
                    //         dataObj["set[" + j + "]"] = {};
                    //         dataObj["set[" + j + "].appId"] = $.trim(TabData[j].id);
                    //         dataObj["set[" + j + "].name"] = $.trim(TabData[j].name);
                    //         dataObj["set[" + j + "].clientIpport"] = $.trim(TabData[j].clientIpport);
                    //         dataObj["set[" + j + "].displayIp"] = $.trim(TabData[j].displayIp);
                    //         dataObj["set[" + j + "].statusCode"] = $.trim(TabData[j].statusCode);
                    //         dataObj["set[" + j + "].successCode"] = $.trim(TabData[j].successCode);
                    //         dataObj["set[" + j + "].failedCode"] = $.trim(TabData[j].failedCode);
                    //         dataObj["set[" + j + "].busTag0"] = $.trim(TabData[j].busTag0);
                    //         dataObj["set[" + j + "].busTag1"] = $.trim(TabData[j].busTag1);
                    //         dataObj["set[" + j + "].busTag2"] = $.trim(TabData[j].busTag2);
                    //         dataObj["set[" + j + "].busTag3"] = $.trim(TabData[j].busTag3);
                    //         dataObj["set[" + j + "].busTag4"] = $.trim(TabData[j].busTag4);
                    //         dataObj["set[" + j + "].busTag5"] = $.trim(TabData[j].busTag5);
                    //         dataObj["set[" + j + "].busTag6"] = $.trim(TabData[j].busTag6);
                    //         dataObj["set[" + j + "].busTag7"] = $.trim(TabData[j].busTag7);
                    //         dataObj["set[" + j + "].busTag8"] = $.trim(TabData[j].busTag8);
                    //         dataObj["set[" + j + "].busTag9"] = $.trim(TabData[j].busTag9);
                    //         dataObj["set[" + j + "].busTag10"] = $.trim(TabData[j].busTag10);
                    //         dataObj["set[" + j + "].busTag11"] = $.trim(TabData[j].busTag11);
                    //         dataObj["set[" + j + "].busTag12"] = $.trim(TabData[j].busTag12);
                    //         dataObj["set[" + j + "].busTag13"] = $.trim(TabData[j].busTag13);
                    //         dataObj["set[" + j + "].busTag14"] = $.trim(TabData[j].busTag14);
                    //         dataObj["set[" + j + "].busTag15"] = $.trim(TabData[j].busTag15);
                    //         dataObj["set[" + j + "].busTag16"] = $.trim(TabData[j].busTag16);
                    //         dataObj["set[" + j + "].busTag17"] = $.trim(TabData[j].busTag17);
                    //         dataObj["set[" + j + "].busTag18"] = $.trim(TabData[j].busTag18);
                    //         dataObj["set[" + j + "].busTag19"] = $.trim(TabData[j].busTag19);
                    //     }
                    //     $('#changetableRow-modal').modal('hide');
                    //     $.ajax({
                    //         url: "/depthanaly/updateBusTag.do",
                    //         async: false,
                    //         type: "post",
                    //         data: dataObj,
                    //         dataType: "json",
                    //         success: function (data) {
                    //             if (data.success) {
                    //                 $(tableId).bootstrapTable("refresh");
                    //             } else {
                    //                 jeBox.alert("修改失败，请稍后再试");
                    //             }
                    //         }
                    //     })
                    // }





                    var rVal = true,
                        baoWenName = [],
                        dataObj = {};
                    //判断交易名称是否合法
                    if (!bsTable.verification($.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()))) {
                        jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                        rVal = false;
                        return;
                    }
                    if(!bsTable.verName(tableId, $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),index)){
                        rVal = false;
                        return;
                    }

                    // if(baoWenName.indexOf($.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()))==-1){
                    //     baoWenName.push($.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()))
                    // }else {
                    //     jeBox.alert("名称不可重复");
                    //     rVal = false;
                    //     return;
                    // }
                    //判断服务端IP是否合法
                    if (!bsTable.servValiDate($.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()))) {
                        jeBox.alert("‘服务端IP端口’不能为空且必须合法，多个IP之间用英文逗号隔开");
                        rVal = false;
                        return;
                    }
                    //判断客户端IP是否合法 ValclientIpPort
                    if (!($.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val()) == "") &&
                        !bsTable.ValclientIpPort($.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val()))) {
                        jeBox.alert("‘客户端IP’不能为空且必须合法，多个IP之间用英文逗号隔开");
                        rVal = false;
                        return;
                    }
                    if (rVal) {
                        dataObj["set[0]"] = {};
                        dataObj["set[0].appId"] = $.trim(selectRowId);
                        dataObj["set[0].name"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val());
                        dataObj["set[0].displayIp"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val());
                        dataObj["set[0].clientIpport"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val());
                        dataObj["set[0].statusCode"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(3)").val());
                        dataObj["set[0].successCode"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(4)").val());
                        dataObj["set[0].failedCode"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(5)").val());
                        dataObj["set[0].busTag0"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(6)").val());
                        dataObj["set[0].busTag1"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(7)").val());
                        dataObj["set[0].busTag2"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(8)").val());
                        dataObj["set[0].busTag3"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(9)").val());
                        dataObj["set[0].busTag4"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(10)").val());
                        dataObj["set[0].busTag5"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(11)").val());
                        dataObj["set[0].busTag6"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(12)").val());
                        dataObj["set[0].busTag7"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(13)").val());
                        dataObj["set[0].busTag8"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(14)").val());
                        dataObj["set[0].busTag9"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(15)").val());
                        dataObj["set[0].busTag10"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(16)").val());
                        dataObj["set[0].busTag11"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(17)").val());
                        dataObj["set[0].busTag12"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(18)").val());
                        dataObj["set[0].busTag13"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(19)").val());
                        dataObj["set[0].busTag14"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(20)").val());
                        dataObj["set[0].busTag15"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(21)").val());
                        dataObj["set[0].busTag16"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(22)").val());
                        dataObj["set[0].busTag17"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(23)").val());
                        dataObj["set[0].busTag18"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(24)").val());
                        dataObj["set[0].busTag19"] = $.trim($("#changetableRow-modal .addtableRowInput:eq(25)").val());
                        dataObj.moduleId = _moduleId;
                        $("#changetableRow-modal").modal("hide");
                        $.ajax({
                            url: "/depthanaly/updateBusTag.do",
                            async: false,
                            method: "POST",
                            data: dataObj,
                            dataType: "json",
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.success) {
                                //     $(tableId).bootstrapTable("refresh");
                                //     bsTable.fenXalert();
                                // } else {
                                //     jeBox.alert("修改失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh");
                                        bsTable.fenXalert();
                                        break;
                                    case 0:
                                        jeBox.alert("修改失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("修改失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("修改失败,请稍后再试");
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
                    break;
                case "webSer":
                case "oraSer":
                case "mySqlSer":
                case "sqlSer":
                    var serverIp = $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()),
                        strName = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),
                        _descrption = $.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val());
                    if (!bsTable.verName(tableId, strName, index) || !bsTable.verSerUseIp(tableId, serverIp, index)) {
                        return;
                    }
                    if (bsTable.verification(strName) && bsTable.servValiDate(serverIp)) {
                        $('#changetableRow-modal').modal('hide');
                        if(
                            modifyData.displayIp != serverIp
                        ){
                            beChange = true;
                        }
                        $.ajax({
                            url: ediUrl,
                            method: "POST",
                            data: {
                                moduleId: _moduleId,
                                id: selectRowId,
                                name: strName,
                                displayIp: serverIp,
                                descrption: _descrption
                            },
                            dataType: "json",
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.success) {
                                //     // $(tableId).bootstrapTable('updateRow', {
                                //     //     index: index,
                                //     //     row: {
                                //     //         id: selectRowId,
                                //     //         name: strName,
                                //     //         displayIp: serverIp
                                //     //     }
                                //     // });
                                //     $(tableId).bootstrapTable("refresh",{
                                //         silent: false,
                                //         query: {
                                //             moduleId: _moduleId
                                //         }
                                //     });
                                //     bsTable.analysisAgain("#changetableRow-modal");
                                //     // if(beChange){
                                //     //     bsTable.fenXalert();
                                //     // }
                                // } else {
                                //     jeBox.alert("后端验证不通过，修改失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh",{
                                            silent: false,
                                            query: {
                                                moduleId: _moduleId
                                            }
                                        });
                                        bsTable.analysisAgain("#changetableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("修改失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("修改失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("修改失败,请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    } else {
                        if (!bsTable.verification(strName)) {
                            jeBox.alert("名称不能超过32个字符且不能为空");
                        }
                        if (!bsTable.servValiDate(serverIp)) {
                            jeBox.alert("IP端口不能为空且必须合法，多个IP之间用英文逗号隔开，且不可重复");
                        }
                    }
                    break;
                case "usabilityUpdate":
                    var name = $("#changetableRow-modal .addtableRowInput:eq(0)").val(),
                        ip = $("#changetableRow-modal .addtableRowInput:eq(1)").val(),
                        port = $("#changetableRow-modal .addtableRowInput:eq(2)").val(),
                        interval = $("#changetableRow-modal .addtableRowInput:eq(3)").val() - 0,
                        status = $("#changetableRow-modal .addtableRowInput:eq(4)").val(),
                        des = $("#changetableRow-modal .addtableRowInput:eq(5)").val();
                    if (!bsTable.verification(name)) {
                        jeBox.alert("名称不能超过32个字符且不能为空");
                        return;
                    }
                    if (!bsTable.verName(tableId, name, index)) {
                        //验证名称是否有重
                        return;
                    }
                    if (!bsTable.valiIp(ip)) {
                        jeBox.alert("IP 格式不正确且不能为空，请重新输入");
                        return;
                    }
                    if (!bsTable.valiPort(port)) {
                        jeBox.alert("端口不正确且不能为空，请重新输入");
                        return;
                    }
                    if (!bsTable.verUsabilityIpPort(tableId, ip, port, index)) {
                        //验证IP端口是否有重
                        return
                    }
                    if (!bsTable.isInteger(interval)) {
                        jeBox.alert("间隔时间不正确且不能为空，请重新输入");
                        return;
                    }
                    if (status == "") {
                        jeBox.alert("状态不能为空，请重新输入");
                        return;
                    }
                    $.ajax({
                        url: ediUrl,
                        method: "POST",
                        data: {
                            id: selectRowId,
                            name: name,
                            ip: ip,
                            port: port,
                            interval: interval,
                            status: status,
                            des: des
                        },
                        dataType: "json",
                        beforeSend:function (XMLHttpRequest) {},
                        success: function (data,textStatus,XMLHttpRequest) {
                            $("#changetableRow-modal").modal("hide");
                            if (data == 2) {
                                // jeBox.alert("名称或端口重复，请重新输入");
                                jeBox.alert("后端验证不通过，请您稍后再试");
                                // return false;
                            } else {
                                $(tableId).bootstrapTable("refresh");
                            }
                        },
                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThorwn);
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    });
                    break;
                case "centerTable":
                case "SyslogTable":
                    var strName = $.trim($("#changetableRow-modal .addtableRowInput:eq(0)").val()),
                        ipText = $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()),
                        portText = $.trim($("#changetableRow-modal .addtableRowInput:eq(2)").val()),
                        descriptionText = $.trim($("#changetableRow-modal .addtableRowInput:eq(3)").val()),
                        rVal = true;
                    if (!bsTable.verification(strName)) {
                        jeBox.alert("名称不能超过32个字符且不能为空");
                        rVal = false;
                        return;
                    }
                    if (!bsTable.verName(tableId, strName, index)) {
                        rVal = false;
                        return;
                    }
                    if (!bsTable.valiIp(ipText)) {
                        jeBox.alert("请输入正确的IP");
                        rVal = false;
                        return;
                    }
                    if (!bsTable.valiPort(portText)) {
                        jeBox.alert("请输入正确的端口");
                        rVal = false;
                        return;
                    }
                    if (rVal) {
                        $('#changetableRow-modal').modal('hide');
                        $.ajax({
                            url: ediUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                name: strName,
                                ip: ipText,
                                port: portText,
                                descrption: descriptionText
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if (data.stauts) {
                                    $(tableId).bootstrapTable("refresh");
                                } else {
                                    jeBox.alert("修改失败，请稍后再试");
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
                    break;
                default:
                    if (!bsTable.verName(tableId, serverIpVal, index)) {
                        return;
                    }
                    if (bsTable.verification(serverIpVal)) {
                        $("#changetableRow-modal").modal("hide");
                        $.ajax({
                            url: ediUrl,
                            method: "POST",
                            data: {
                                id: selectRowId,
                                name: serverIpVal,
                                descrption: $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val()),
                                lockStatus: 0
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                switch (data.success - 0) {
                                    case 1:
                                        dataList = [];
                                        $(tableId).bootstrapTable('updateRow', {
                                            index: index,
                                            row: {
                                                id: selectRowId,
                                                name: serverIpVal,
                                                descrption: $.trim($("#changetableRow-modal .addtableRowInput:eq(1)").val())
                                            }
                                        });
                                        break;
                                    case 0:
                                        jeBox.alert("保存失败，请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("驾驶舱名称已存在，请更改驾驶仓名稍后再次保存");
                                        break
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    } else {
                        jeBox.alert("驾驶仓名称不能超过32个字符且不能为空");
                    }
            }
        },
        /**
         * 验证服务端IP跟之前的是否有重
         * @param tableId
         * @param newdisplayIp
         * @param index
         * @returns {boolean}
         */
        verSerUseIp: function (tableId, newdisplayIp, index) {
            var result = true;
            for (var i = 0, arr = $(tableId).bootstrapTable('getData'); i < arr.length; i++) {
                if (i != index) {
                    var displayIpArry = arr[i].displayIp.split(","),
                        newdisplayIpArry = newdisplayIp.split(",");
                    for (var j = 0; j < displayIpArry.length; j++) {
                        for (var k = 0; k < newdisplayIpArry.length; k++) {
                            if (displayIpArry[j] == newdisplayIpArry[k]) {
                                jeBox.alert("IP已存在，请更改后再次保存");
                                result = false;
                                return result;
                            }
                        }
                    }
                }
            }
            return result;
        },
        /**
         * 验证应用可用性的IP端口跟之前的是否有重
         * @param tableId
         * @param ip
         * @param port
         * @param index
         * @returns {boolean}
         */
        verUsabilityIpPort: function (tableId, ip, port, index) {
            var result = true;
            for (var i = 0, arr = $(tableId).bootstrapTable('getData'); i < arr.length; i++) {
                if (i != index) {
                    var ipPort = arr[i].ip + ":" + arr[i].port,
                        newIpPort = ip + ":" + port;
                    if (ipPort == newIpPort) {
                        jeBox.alert("IP端口已存在，请更改后再次保存");
                        result = false;
                        return result;
                    }
                }
            }
            return result;
        },
        /**
         * 验证名称是否重复
         *
         * 此处代码完成可以写成验证任何项
         * 是否重复当时未考虑到那么多
         * 其键可以配 name||other
         * alert 是否开启
         * alert 内容
         * 参数 tableId 表格ID
         *      strText 传入比较的内容
         *      index 哪个下标不需要比较（常用在修改）
         *      keyWord 与传入内容比较的数据的键
         *      isAlert 是否弹出alert
         *      alertText 弹出alert的内容
         * 五个参数形成万能检查是否有重 -^^^-
         *
         * @param tableId
         * @param name
         * @param index
         * @returns {boolean}
         */
        verName: function (tableId, name, index) {
            var result = true;
            for (var i = 0, arr = $(tableId).bootstrapTable('getData'); i < arr.length; i++) {
                if (i != index) {
                    if (arr[i].name == name) {
                        jeBox.alert("名称已存在，请更改名称后再次保存");
                        result = false;
                        return result;
                    }
                }
            }
            return result;
        },
        /**
         * 验证观察点表格内容是否有重
         * @param tableId
         * @param resultObject
         * @param index
         * @returns {boolean}
         */
        verObserConten: function (tableId, resultObject, index) {
            var result = true;
            for (var i = 0, arr = $(tableId).bootstrapTable('getData'); i < arr.length; i++) {
                if (i != index) {
                    var didArry = arr[i].did.split(",");
                    for (var j = 0; j < didArry.length; j++) {
                        var strgetDate = didArry[j] + arr[i].lid + arr[i].vid,
                            reDidArry = resultObject.didProtype.split(",");
                        for (var k = 0; k < reDidArry.length; k++) {
                            var strNow = reDidArry[k] + resultObject.lid + resultObject.vid;
                            if (strNow == strgetDate) {
                                jeBox.alert("网卡名，VLAN，MPLS不能重合");
                                result = false;
                                return result;
                            }
                        }
                    }
                }
            }
            return result;
        },
        /**
         * 添加表格行功能实现
         * @param tableId
         * @param addUrl
         * @param iniUrl
         * @param page
         * @param _moduleId
         */
        addTableRow: function (tableId, addUrl, iniUrl, page, _moduleId) {
            switch (page) {
                case "cenTer":
                    var name = $("#addtableRow-modal input.addtableRowInput").eq(0).val(),
                        ip = $("#addtableRow-modal input.addtableRowInput").eq(1).val(),
                        port = $("#addtableRow-modal input.addtableRowInput").eq(2).val(),
                        contacts = $("#addtableRow-modal input.addtableRowInput").eq(3).val(),
                        telephone = $("#addtableRow-modal input.addtableRowInput").eq(4).val(),
                        email = $("#addtableRow-modal input.addtableRowInput").eq(5).val(),
                        uptime = $.myTime.DateToUnix($("#addtableRow-modal input.addtableRowInput").eq(6).val()+" 00:00:00"),
                        limitdate = $.myTime.DateToUnix($("#addtableRow-modal input.addtableRowInput").eq(7).val()+" 00:00:00"),
                        state = $("#addtableRow-modal  #center-stage option:selected").val(),
                        version = $("#addtableRow-modal  #center-version option:selected").val();
                    if(
                        bsTable.verification(name) &&
                        bsTable.valiIp(ip) &&
                        bsTable.valiPort(port) &&
                        bsTable.verification(contacts) &&
                        isNaN(contacts)&&
                        telephone &&
                        bsTable.valphone(telephone) &&
                        bsTable.valEmail(email) &&
                        uptime &&
                        limitdate &&
                        $("#addtableRow-modal input.addtableRowInput").eq(6).val() &&
                        $("#addtableRow-modal input.addtableRowInput").eq(7).val() &&
                        (limitdate - uptime > 0 )
                    ){
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url:addUrl,
                            method:"POST",
                            data:{
                                name:name,
                                ip:ip,
                                port:port,
                                contacts:contacts,
                                telephone:telephone,
                                email:email,
                                uptime:uptime,
                                limitdate:limitdate,
                                state:state,
                                version:version
                            },
                            dataType:"json",
                            beforeSend:function(){},
                            success:function(data){
                                if(data.stauts){
                                    $(tableId).bootstrapTable("refresh");
                                }else {
                                    jeBox.alert("新增失败,请稍后再试");
                                }
                            },
                            error:function(xhr,textStatus,error){
                                console.error(xhr);
                                console.error(textStatus);
                                console.error(error);
                                jeBox.alert("新增失败,请稍后再试");
                            },
                            complete:function(){}
                        })
                    }else {
                        if(!name){
                            jeBox.alert("用户名不能为空");
                            return
                        }
                        if(!bsTable.verification(name)){
                            jeBox.alert("用户名的长度不得超过32个字符");
                            return
                        }
                        if(!ip){
                            jeBox.alert("IP不能为空");
                            return
                        }
                        if(!bsTable.valiIp(ip)){
                            jeBox.alert("IP不合法");
                            return
                        }
                        if(!port){
                            jeBox.alert("端口不能为空");
                            return
                        }
                        if(!bsTable.valiPort(port)){
                            jeBox.alert("端口不合法");
                            return
                        }
                        if(!contacts){
                            jeBox.alert("联系人不能为空");
                            return
                        }
                        if(!isNaN(contacts)){
                            jeBox.alert("联系人不能为纯数字");
                            return
                        }
                        if(!bsTable.verification(contacts)){
                            jeBox.alert("联系人的长度不得超过32个字符");
                            return
                        }
                        if(!telephone){
                            jeBox.alert("电话不能为空");
                            return
                        }
                        if(!bsTable.valphone(telephone)){
                            jeBox.alert("电话号码不符合规范");
                            return
                        }
                        if(!email){
                            jeBox.alert("Email不能为空");
                            return
                        }
                        if(!bsTable.valEmail(email)){
                            jeBox.alert("Email不合法");
                            return
                        }
                        if(!$("#addtableRow-modal input.addtableRowInput").eq(6).val()){
                            jeBox.alert("上线时间不能为空");
                            return
                        }
                        if(!$("#addtableRow-modal input.addtableRowInput").eq(7).val()){
                            jeBox.alert("服务时限不能为空");
                            return
                        }
                        if(limitdate - uptime <= 0 ){
                            jeBox.alert("服务时限必须大于上线时间");
                            return
                        }
                    }
                    break;
                case "observationPoint":
                    var resultObject = bsTable.watPointVerif("addtableRow-modal");
                    if (resultObject.result) {
                        var count = getCardNumCount($(tableId).bootstrapTable('getData'), resultObject, true);
                        if (!bsTable.verName(tableId, $.trim($("#addtableRow-modal .addtableRowInput").val()))) {
                            return;
                        }
                        if(!bsTable.verification($.trim($("#addtableRow-modal .addtableRowInput").val()))){
                            jeBox.alert("名称不能超过32个字符且不能为空");
                            return
                        }
                        // if (!bsTable.verObserConten(tableId, resultObject)) {
                        //     return;
                        // }
                        if (count.uc > 3) {
                            jeBox.alert("网卡数量不能超过3个，请返回修改");
                            return;
                        }
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                "name": $.trim($("#addtableRow-modal .addtableRowInput").val()),
                                "did": resultObject.did,
                                "vid": resultObject.vid,
                                "vxid":resultObject.vxid,
                                "lid1":resultObject.lid1,
                                "lid2":resultObject.lid2,
                                "lid3":resultObject.lid3,
                                "lid4":resultObject.lid4,
                                "lid5":resultObject.lid5,
                                // "lid": resultObject.lid,
                                "bandwidth": resultObject.bandwidth
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // 1 success 0 false
                                if(data){
                                    $(tableId).bootstrapTable("refresh");
                                    bsTable.analysisAgain("#addtableRow-modal");
                                }else {
                                    jeBox.alert("新增失败，请稍后再试");
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
                    break;
                case "userSide":
                    var name = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val());
                    var displayIp = $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val());
                    var bandwidth = $.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val());
                    var descrption = $.trim($("#addtableRow-modal .addtableRowInput:eq(3)").val());
                    if (!bsTable.verSerUseIp(tableId, displayIp) || !bsTable.verName(tableId, name)) {
                        return;
                    }
                    if(!bsTable.verification(name)){
                        jeBox.alert("名称不能超过32个字符且不能为空");
                        return
                    }
                    if (bsTable.userValiDate("addtableRow")) {
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                bandwidth: bandwidth,
                                moduleId: 11,
                                name: name,
                                displayIp: displayIp,
                                descrption: descrption,
                                moduleId:_moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // switch (data.success - 0) {
                                //     case 1:
                                //         $(tableId).bootstrapTable("refresh");
                                //         bsTable.analysisAgain("#addtableRow-modal");
                                //         break;
                                //     case 0:
                                //         jeBox.alert("后端验证不通过，保存失败，请稍后再试");
                                //         break;
                                //     case 2:
                                //         jeBox.alert("名称已存在，请重新命名");
                                //         break;
                                //     case 3:
                                //         jeBox.alert("网段不正确，请重新填写");
                                //         break;
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh");
                                        bsTable.analysisAgain("#addtableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("新增失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("新增失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("新增失败,请稍后再试");
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
                    break;
                case "serverSide":
                    var serverIpVal = $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val()),
                        strName = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()),
                        _bandwidth = $.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val()),
                        _descrption = $.trim($("#addtableRow-modal .addtableRowInput:eq(3)").val());
                    if (!bsTable.verSerUseIp(tableId, serverIpVal) || !bsTable.verName(tableId, strName)) {
                        return;
                    }
                    if (bsTable.verification(strName) && bsTable.$servValiDate(serverIpVal, true) && bsTable.ValiBandWidth(_bandwidth)) {
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                name: strName,
                                displayIp: serverIpVal,
                                bandwidth: _bandwidth,
                                descrption: _descrption,
                                moduleId:_moduleId
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data == "success") {
                                //     $(tableId).bootstrapTable("refresh");
                                //     bsTable.analysisAgain("#addtableRow-modal");
                                // } else {
                                //     jeBox.alert("后端验证不通过，保存失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh");
                                        bsTable.analysisAgain("#addtableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("新增失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("新增失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("新增失败,请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    } else {
                        if (!bsTable.verification(strName)) {
                            jeBox.alert("名称不能超过32个字符且不能为空");
                            return;
                        }
                        if (!bsTable.$servValiDate(serverIpVal, true)) {
                            jeBox({
                                button: [{
                                    name: '确定'
                                }],
                                boxSize: ["320px", "166px"],
                                padding: "5px 5px 5px 13px",
                                content: '<div style="padding:5px;">服务端IP端口不能为空且必须合法!<br>多个IP或端口之间用英文逗号隔开，且不可重复!<br>纯端口监控不能与IP端口监控混合输入!</div>',
                                maskLock: true,
                                boxStyle: {
                                    "text-align": "left"
                                }
                            });
                            return;
                        }
                    }
                    break;
                case "webSer":
                case "oraSer":
                case "mySqlSer":
                case "sqlSer":
                    var serverIpVal = $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val()),
                        strName = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()),
                        _descrption = $.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val());
                    if (!bsTable.verSerUseIp(tableId, serverIpVal) || !bsTable.verName(tableId, strName)) {
                        return;
                    }
                    if (bsTable.verification(strName) && bsTable.servValiDate(serverIpVal)) {
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                moduleId: _moduleId,
                                name: strName,
                                displayIp: serverIpVal,
                                descrption: _descrption
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.success) {
                                //     $(tableId).bootstrapTable("refresh", {
                                //         silent: false,
                                //         query: {
                                //             moduleId: _moduleId
                                //         }
                                //     });
                                //     bsTable.analysisAgain("#addtableRow-modal");
                                // } else {
                                //     jeBox.alert("后端验证不通过，保存失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh", {
                                            silent: false,
                                            query: {
                                                moduleId: _moduleId
                                            }
                                        });
                                        bsTable.analysisAgain("#addtableRow-modal");
                                        break;
                                    case 0:
                                        jeBox.alert("新增失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("新增失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("新增失败,请稍后再试");
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    } else {
                        if (!bsTable.verification(strName)) {
                            jeBox.alert("名称不能超过32个字符且不能为空");
                            return;
                        }
                        if (!bsTable.servValiDate(serverIpVal)) {
                            jeBox.alert("‘服务端IP端口’不能为空且必须合法，多个IP之间用英文逗号隔开");
                        }
                    }
                    break;
                case "urlSer":
                    /* ===============================
                     * 此处为URL的增加行到表格的代码
                     * 在传后台之前得先验证名称的合法性
                     * 以及应该在此方法新增一个传参 为
                     * 新增的模态框里的表格里的所有数据
                     * 而后验证其编辑 名称 URL的合法性 为true
                     * 则传后台
                     *
                     ==================================*/
                    var urlName = $("#urlName").val(),
                        urlDescrption = $("#urlps").val(),
                        TabData = $("#handlUrl").bootstrapTable("getData"),
                        rVal = true,
                        dataObj = {
                            name: urlName,
                            descrption: urlDescrption
                        };
                    if (!bsTable.verification(urlName)) {
                        jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                        rVal = false;
                        return;
                    }
                    if(!bsTable.verName(tableId,urlName)){
                        rVal = false;
                        return;
                    }
                    if (TabData.length) {
                        //还差判断数组中编号是否为重
                        var nameObj = {
                            name:[],
                            num:[]
                        };
                        for (var i = 0; i < TabData.length; i++) {
                            if (!bsTable.isInteger(TabData[i].num - 0)) {
                                jeBox.alert("编号必须为大于0的整数");
                                rVal = false;
                                return;
                            }
                            if(nameObj.num.indexOf(String(TabData[i].num))==-1){
                                nameObj.num.push(String(TabData[i].num))
                            }else {
                                jeBox.alert("编号不可重复");
                                rVal = false;
                                return;
                            }
                            if (!bsTable.verification(TabData[i].name)) {
                                jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                                rVal = false;
                                return;
                            }
                            if(nameObj.name.indexOf(TabData[i].name)==-1){
                                nameObj.name.push(TabData[i].name)
                            }else {
                                jeBox.alert("名称不可重复");
                                rVal = false;
                                return;
                            }
                            if ($.trim(TabData[i].url) == "") {
                                jeBox.alert("URL不能为空");
                                return;
                            }
                        }
                    } else {
                        jeBox.alert("至少添加一条URL");
                        rVal = false;
                        return;
                    }
                    if (rVal) {
                        for (var j = 0; j < TabData.length; j++) {
                            dataObj["set[" + j + "]"] = {};
                            dataObj["set[" + j + "].name"] = TabData[j].name;
                            dataObj["set[" + j + "].num"] = TabData[j].num;
                            dataObj["set[" + j + "].url"] = TabData[j].url;
                        }
                        dataObj.moduleId = _moduleId;
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            async: false,
                            method: "POST",
                            data: dataObj,
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.stauts) {
                                //     $(tableId).bootstrapTable("refresh");
                                // } else {
                                //     jeBox.alert("添加失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh");
                                        break;
                                    case 0:
                                        jeBox.alert("新增失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("新增失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("新增失败,请稍后再试");
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
                    break;
                case "baowenJySer":
                    // var TabData = $("#handlUrl").bootstrapTable("getData"),
                    //     rVal = true,
                    //     baoWenName = [],
                    //     dataObj = {};
                    // for (var i = 0; i < TabData.length; i++) {
                    //     //判断交易名称是否合法
                    //     if (!bsTable.verification($.trim(TabData[i].name))) {
                    //         jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     if(baoWenName.indexOf(TabData[i].name)==-1){
                    //         baoWenName.push(TabData[i].name)
                    //     }else {
                    //         jeBox.alert("名称不可重复");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     //判断服务端IP是否合法
                    //     if (!bsTable.servValiDate($.trim(TabData[i].displayIp))) {
                        //         jeBox.alert("‘服务端IP端口’不能为空，必须合法，多个IP之间用英文逗号隔开");
                    //         rVal = false;
                    //         return;
                    //     }
                    //     //判断客户端IP是否合法 ValclientIpPort
                    //     if (!($.trim(TabData[i].clientIpport) == "") && !bsTable.ValclientIpPort($.trim(TabData[i].clientIpport))) {
                    //         jeBox.alert("‘客户端IP’不能为空，必须合法，多个IP之间用英文逗号隔开");
                    //         rVal = false;
                    //         return;
                    //     }
                    // }
                    // if (rVal) {
                    //     for (var j = 0; j < TabData.length; j++) {
                    //         dataObj["set[" + j + "]"] = {};
                    //         dataObj["set[" + j + "].name"] = $.trim(TabData[j].name);
                    //         dataObj["set[" + j + "].clientIpport"] = $.trim(TabData[j].clientIpport);
                    //         dataObj["set[" + j + "].displayIp"] = $.trim(TabData[j].displayIp);
                    //         dataObj["set[" + j + "].statusCode"] = $.trim(TabData[j].statusCode);
                    //         dataObj["set[" + j + "].successCode"] = $.trim(TabData[j].successCode);
                    //         dataObj["set[" + j + "].failedCode"] = $.trim(TabData[j].failedCode);
                    //         dataObj["set[" + j + "].busTag0"] = $.trim(TabData[j].busTag0);
                    //         dataObj["set[" + j + "].busTag1"] = $.trim(TabData[j].busTag1);
                    //         dataObj["set[" + j + "].busTag2"] = $.trim(TabData[j].busTag2);
                    //         dataObj["set[" + j + "].busTag3"] = $.trim(TabData[j].busTag3);
                    //         dataObj["set[" + j + "].busTag4"] = $.trim(TabData[j].busTag4);
                    //         dataObj["set[" + j + "].busTag5"] = $.trim(TabData[j].busTag5);
                    //         dataObj["set[" + j + "].busTag6"] = $.trim(TabData[j].busTag6);
                    //         dataObj["set[" + j + "].busTag7"] = $.trim(TabData[j].busTag7);
                    //         dataObj["set[" + j + "].busTag8"] = $.trim(TabData[j].busTag8);
                    //         dataObj["set[" + j + "].busTag9"] = $.trim(TabData[j].busTag9);
                    //         dataObj["set[" + j + "].busTag10"] = $.trim(TabData[j].busTag10);
                    //         dataObj["set[" + j + "].busTag11"] = $.trim(TabData[j].busTag11);
                    //         dataObj["set[" + j + "].busTag12"] = $.trim(TabData[j].busTag12);
                    //         dataObj["set[" + j + "].busTag13"] = $.trim(TabData[j].busTag13);
                    //         dataObj["set[" + j + "].busTag14"] = $.trim(TabData[j].busTag14);
                    //         dataObj["set[" + j + "].busTag15"] = $.trim(TabData[j].busTag15);
                    //         dataObj["set[" + j + "].busTag16"] = $.trim(TabData[j].busTag16);
                    //         dataObj["set[" + j + "].busTag17"] = $.trim(TabData[j].busTag17);
                    //         dataObj["set[" + j + "].busTag18"] = $.trim(TabData[j].busTag18);
                    //         dataObj["set[" + j + "].busTag19"] = $.trim(TabData[j].busTag19);
                    //     }
                    //     $("#addtableRow-modal").modal("hide");
                    //     $.ajax({
                    //         url: "/depthanaly/addBusTag.do",
                    //         async: false,
                    //         type: "post",
                    //         data: dataObj,
                    //         dataType: "json",
                    //         success: function (data) {
                    //             if (data.success) {
                    //                 $(tableId).bootstrapTable("refresh");
                    //             } else {
                    //                 jeBox.alert("添加失败，请稍后再试");
                    //             }
                    //         }
                    //     })
                    // }



                    var rVal = true,
                        baoWenName = [],
                        dataObj = {};
                    //判断交易名称是否合法
                    if (!bsTable.verification($.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()))) {
                        jeBox.alert("名称不能超过32个字符且不能为空或不允许提交空行");
                        rVal = false;
                        return;
                    }
                    if(!bsTable.verName(tableId, $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()))){
                        rVal = false;
                        return;
                    }
                    // if(baoWenName.indexOf($.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()))==-1){
                    //     baoWenName.push($.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()))
                    // }else {
                    //     jeBox.alert("名称不可重复");
                    //     rVal = false;
                    //     return;
                    // }
                    //判断服务端IP是否合法
                    if (!bsTable.servValiDate($.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val()))) {
                        jeBox.alert("‘服务端IP端口’不能为空且必须合法，多个IP之间用英文逗号隔开");
                        rVal = false;
                        return;
                    }
                    //判断客户端IP是否合法 ValclientIpPort
                    if (!($.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val()) == "") &&
                        !bsTable.ValclientIpPort($.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val()))) {
                        jeBox.alert("‘客户端IP’不能为空且必须合法，多个IP之间用英文逗号隔开");
                        rVal = false;
                        return;
                    }
                    // displayIp clientIpport
                    if (rVal) {
                        dataObj["set[0]"] = {};
                        dataObj["set[0].name"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val());
                        dataObj["set[0].displayIp"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val());
                        dataObj["set[0].clientIpport"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val());
                        dataObj["set[0].statusCode"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(3)").val());
                        dataObj["set[0].successCode"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(4)").val());
                        dataObj["set[0].failedCode"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(5)").val());
                        dataObj["set[0].busTag0"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(6)").val());
                        dataObj["set[0].busTag1"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(7)").val());
                        dataObj["set[0].busTag2"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(8)").val());
                        dataObj["set[0].busTag3"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(9)").val());
                        dataObj["set[0].busTag4"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(10)").val());
                        dataObj["set[0].busTag5"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(11)").val());
                        dataObj["set[0].busTag6"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(12)").val());
                        dataObj["set[0].busTag7"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(13)").val());
                        dataObj["set[0].busTag8"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(14)").val());
                        dataObj["set[0].busTag9"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(15)").val());
                        dataObj["set[0].busTag10"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(16)").val());
                        dataObj["set[0].busTag11"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(17)").val());
                        dataObj["set[0].busTag12"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(18)").val());
                        dataObj["set[0].busTag13"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(19)").val());
                        dataObj["set[0].busTag14"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(20)").val());
                        dataObj["set[0].busTag15"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(21)").val());
                        dataObj["set[0].busTag16"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(22)").val());
                        dataObj["set[0].busTag17"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(23)").val());
                        dataObj["set[0].busTag18"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(24)").val());
                        dataObj["set[0].busTag19"] = $.trim($("#addtableRow-modal .addtableRowInput:eq(25)").val());
                        dataObj.moduleId = _moduleId;
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: "/depthanaly/addBusTag.do",
                            async: false,
                            method: "POST",
                            data: dataObj,
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                // if (data.success) {
                                //     $(tableId).bootstrapTable("refresh");
                                // } else {
                                //     jeBox.alert("添加失败，请稍后再试");
                                // }
                                switch (+data.state){
                                    case 1:
                                        $(tableId).bootstrapTable("refresh");
                                        break;
                                    case 0:
                                        jeBox.alert("新增失败,请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("新增失败,与其他业务IP重复");
                                        break;
                                    default:
                                        jeBox.alert("新增失败,请稍后再试");
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
                    break;
                case "usabilityAddRow":
                    var name = $("#addtableRow-modal .addtableRowInput:eq(0)").val(),
                        ip = $("#addtableRow-modal .addtableRowInput:eq(1)").val(),
                        port = $("#addtableRow-modal .addtableRowInput:eq(2)").val(),
                        interval = $("#addtableRow-modal .addtableRowInput:eq(3)").val() - 0,
                        status = $("#addtableRow-modal .addtableRowInput:eq(4)").val(),
                        des = $("#addtableRow-modal .addtableRowInput:eq(5)").val();
                    if (!bsTable.verification(name)) {
                        jeBox.alert("名称不能超过32个字符且不能为空");
                        return;
                    }
                    if (!bsTable.verName(tableId, name)) {
                        //验证名称是否有重
                        return;
                    }
                    if (!bsTable.valiIp(ip)) {
                        jeBox.alert("IP 格式不正确且不能为空，请重新输入");
                        return;
                    }
                    if (!bsTable.valiPort(port)) {
                        jeBox.alert("端口不正确且不能为空，请重新输入");
                        return;
                    }
                    if (!bsTable.verUsabilityIpPort(tableId, ip, port)) {
                        //验证IP端口是否有重
                        return
                    }
                    if (!bsTable.isInteger(interval)) {
                        jeBox.alert("间隔时间不正确且不能为空，请重新输入");
                        return;
                    }
                    if (status == "") {
                        jeBox.alert("状态不能为空,请重新输入");
                        return;
                    }
                    $.ajax({
                        url: addUrl,
                        method: "POST",
                        data: {
                            name: name,
                            ip: ip,
                            port: port,
                            interval: interval,
                            status: status,
                            des: des
                        },
                        dataType: "json",
                        beforeSend:function (XMLHttpRequest) {},
                        success: function (data,textStatus,XMLHttpRequest) {
                            $("#addtableRow-modal").modal("hide");
                            if (data == 2) {
                                // jeBox.alert("名称或端口重复，请重新输入");
                                jeBox.alert("后端验证不通过，请您秒后再试");
                            } else {
                                $(tableId).bootstrapTable("refresh");
                            }
                        },
                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThorwn);
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    });
                    break;
                case "centerTable":
                case "SyslogTable":
                    var strName = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val()),
                        ipText = $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val()),
                        portText = $.trim($("#addtableRow-modal .addtableRowInput:eq(2)").val()),
                        descriptionText = $.trim($("#addtableRow-modal .addtableRowInput:eq(3)").val()),
                        rVal = true;
                    if (!bsTable.verification(strName)) {
                        jeBox.alert("名称不能超过32个字符且不能为空");
                        rVal = false;
                        return;
                    }
                    if (!bsTable.verName(tableId, strName, -1)) {
                        rVal = false;
                        return;
                    }
                    if (!bsTable.valiIp(ipText)) {
                        jeBox.alert("请输入正确的IP");
                        rVal = false;
                        return;
                    }
                    if (!bsTable.valiPort(portText)) {
                        jeBox.alert("请输入正确的端口");
                        rVal = false;
                        return;
                    }
                    if (rVal) {
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                name: strName,
                                ip: ipText,
                                port: portText,
                                descrption: descriptionText
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if (data.stauts) {
                                    $(tableId).bootstrapTable("refresh");
                                } else {
                                    jeBox.alert("添加失败，请稍后再试");
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
                    break;
                default:
                    var serverIpVal = $.trim($("#addtableRow-modal .addtableRowInput:eq(0)").val());
                    if (!bsTable.verName(tableId, serverIpVal)) {
                        return;
                    }
                    if (bsTable.verification(serverIpVal)) {
                        $("#addtableRow-modal").modal("hide");
                        $.ajax({
                            url: addUrl,
                            method: "POST",
                            data: {
                                name: serverIpVal,
                                descrption: $.trim($("#addtableRow-modal .addtableRowInput:eq(1)").val()),
                                lockStatus: 0
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                switch (data.success - 0) {
                                    case 1:
                                        $.ajax({
                                            url: iniUrl,
                                            method: "POST",
                                            data: "",
                                            dataType: "json",
                                            beforeSend:function (XMLHttpRequest) {},
                                            success: function (addData,textStatus,XMLHttpRequest) {
                                                dataList = [];
                                                $(tableId).bootstrapTable("refresh");
                                            },
                                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                                console.error(XMLHttpRequest);
                                                console.error(textStatus);
                                                console.error(errorThorwn);
                                            },
                                            complete:function (XMLHttpRequest,textStatus) {}
                                        });
                                        break;
                                    case 0:
                                        jeBox.alert("保存失败，请稍后再试");
                                        break;
                                    case 2:
                                        jeBox.alert("驾驶舱名称已存在，请更改驾驶仓名稍后再次保存");
                                        break
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    } else {
                        jeBox.alert("驾驶仓名称不能超过32个字符且不能为空");
                    }
            }
        },
        /**
         * 添加表格行时 弹出模态框的实现
         * @param titleText
         * @param page
         * @param watgetUrl
         * @param modalAttr
         */
        addRowModal: function (titleText, page, watgetUrl, modalAttr) {
            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
            if(modalAttr == "baowenJySer"){
                $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").css({
                    "height":200,
                    "overflow-y":"scroll",
                    "overflow-x":"hidden",
                    "margin-left":"1px",
                    "position":"relative",
                    "left":"12px"
                });
            }else {
                $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").removeAttr("style");
            }
            var length = titleText.length;
            switch (page) {
                case "cenTer":
                    for (var i = 0; i < length; i++) {
                        var formGroup;
                        switch (titleText[i]){
                            case "上线时间":
                                formGroup = $('<div class="form-group group-Style">' +
                                    '<div class="col-md-9">' +
                                    '<input type="text" id="center-SxTime" class="form-control input-sm m-t-15 addtableRowInput">' +
                                    '</div>' +
                                    '</div>');
                                break;
                            case "服务时限":
                                formGroup = $('<div class="form-group group-Style">' +
                                    '<div class="col-md-9">' +
                                    '<input type="text" id="center-SerTime" class="form-control input-sm m-t-15 addtableRowInput">' +
                                    '</div>' +
                                    '</div>');
                                break;
                            case "状态":
                                formGroup = $('<div class="form-group group-Style">' +
                                    '<div class="col-md-9">' +
                                    '<select class="form-control m-t-15" id="center-stage" style="height: 30px;">' +
                                    '<option value="1">正式用户</option>' +
                                    '<option value="2">测试用户</option>' +
                                    '</select>' +
                                    '</div>' +
                                    '</div>');
                                break;
                            case "硬件版本":
                                formGroup = $('<div class="form-group group-Style">' +
                                    '<div class="col-md-9">' +
                                    '<select class="form-control m-t-15" id="center-version"  style="height: 30px;">' +
                                    '<option value="1">专线宝B</option>' +
                                    '<option value="2">专线宝U</option>' +
                                    '<option value="3">专线宝S</option>' +
                                    '<option value="4">XPM管理平台</option>' +
                                    '</select>' +
                                    '</div>' +
                                    '</div>');
                                break;
                            default:
                                formGroup = $('<div class="form-group group-Style">' +
                                    '<div class="col-md-9">' +
                                    '<input type="text" class="form-control input-sm m-t-15 addtableRowInput">' +
                                    '</div>' +
                                    '</div>');
                        }
                        var label = $('<label class="col-md-2 control-label">' +
                            titleText[i] +
                            '</label>');
                        $(formGroup).prepend(label);
                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                    }
                    /**
                     * 此处调用日历插件
                     */
                    var start = {
                        format: 'YYYY-MM-DD',
                        trigger: "focus click",
                        okfun: function(obj){
                            endDates();
                        }
                    };
                    var end = {
                        format: 'YYYY-MM-DD',
                        trigger: "focus click",
                        isServerMaxDate: false,
                        okfun: function(obj){}
                    };
                    function endDates() {
                        //将结束日期的事件改成 false 即可
                         end.trigger = false;
                         $("#addtableRow-modal #center-SerTime").jeDate(end);
                    }
                    $("#addtableRow-modal #center-SxTime").jeDate(start);
                    $("#addtableRow-modal #center-SerTime").jeDate(end);
                    break;
                case "observationPoint":
                    $.ajax({
                        url: watgetUrl,
                        method: "POST",
                        data: {},
                        dataType: "json",
                        beforeSend:function (XMLHttpRequest) {},
                        success: function (data,textStatus,XMLHttpRequest) {
                            for (var i = 0; i < length; i++) {
                                switch (titleText[i]) {
                                    case "名称":
                                    case "备注":
                                        var formGroup = $('<div class="form-group">' +
                                            '<div class="col-md-9">' +
                                            '<input type="text" class="form-control input-sm m-t-15 addtableRowInput">' +
                                            '</div>' +
                                            '</div>');
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "带宽[Mb]":
                                        var formGroup = $('<div class="form-group">' +
                                            '<div class="col-md-9">' +
                                            '<input type="text" class="form-control input-sm m-t-15 addtableRowInputbdWidth">' +
                                            '</div>' +
                                            '</div>');
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "网卡名":
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < data["name"].length; j++) {
                                            select.append('<option value="' + data["name"][j]["id"] + '">' + data["name"][j]["name"] + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "VLAN ID":
                                        var vidArry = data["vid"][0]["vid"].split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < vidArry.length; j++) {
                                            select.append('<option value="">' + $.trim(vidArry[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "VXLAN ID":
                                        // var lidArry = data["lid"][0]["lid"].split(",");
                                        // var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        // for (var j = 0; j < lidArry.length; j++) {
                                        //     select.append('<option value="">' + $.trim(lidArry[j]) + '</option>');
                                        // }
                                        // var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        // var label = $('<label class="col-md-2 control-label">' +
                                        //     titleText[i] +
                                        //     ':</label>');
                                        // $(formGroup).prepend(label);
                                        // $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);

                                        var vxid = data.vxid[0].vxid.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < vxid.length; j++) {
                                            select.append('<option value="">' + $.trim(vxid[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "MPLS LABLE1":
                                        var lid1 = data.lid1[0].lid1.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < lid1.length; j++) {
                                            select.append('<option value="">' + $.trim(lid1[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "MPLS LABLE2":
                                        var lid2 = data.lid2[0].lid2.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < lid2.length; j++) {
                                            select.append('<option value="">' + $.trim(lid2[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "MPLS LABLE3":
                                        var lid3 = data.lid3[0].lid3.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < lid3.length; j++) {
                                            select.append('<option value="">' + $.trim(lid3[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "MPLS LABLE4":
                                        var lid4 = data.lid4[0].lid4.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < lid4.length; j++) {
                                            select.append('<option value="">' + $.trim(lid4[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                    case "MPLS LABLE5":
                                        var lid5 = data.lid5[0].lid5.split(",");
                                        var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                        for (var j = 0; j < lid5.length; j++) {
                                            select.append('<option value="">' + $.trim(lid5[j]) + '</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' +
                                            titleText[i] +
                                            '</label>');
                                        $(formGroup).prepend(label);
                                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                        break;
                                }
                            }
                            $(".tag-select-limited").chosen({
                                max_selected_options: 110
                            });
                            var formGroup = $('<div class="form-group">' +
                                '<label class="col-md-2 control-label">重新开始分析</label>' +
                                '<div class="col-md-9">' +
                                '<input type="checkbox" class="input-sm m-t-15 cursor" id="analysis_again">' +
                                '</div></div>' +
                                '<div class="form-group">' +
                                '<span class="red" style="font-family: sans-serif;">' +
                                '此次操作需要重启分析程序，如果您需要继续修改其他同类设置，' +
                                '可以最后一个修改完成后，再勾选“重启分析程序”。</span></div>');
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        },
                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                            console.error(XMLHttpRequest);
                            console.error(textStatus);
                            console.error(errorThorwn);
                        },
                        complete:function (XMLHttpRequest,textStatus) {}
                    });
                    break;
                case "urlSer":
                    var html = '<div class="row">' +
                        '<div class="col-md-5">' +
                        '<label for="urlName">业务名称</label>' +
                        '<input type="text" id="urlName" style="color:#000;margin-left: 10px;">' +
                        '</div>' +
                        '<div class="col-md-5">' +
                        '<label for="urlps">备注</label>' +
                        '<input type="text" id="urlps" style="color:#000;margin-left: 10px;">' +
                        '</div>' +
                        '<div class="col-md-2">' +
                        '<span style="color:red;position: relative;left: -30px;top:3px;">URL通配符:***</span>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12" style="max-height: 200px;overflow-y: scroll;">' +
                        '<table id="handlUrl" style="background: #fff;"></table>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                    $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(html);
                    if (!$("#add-UrlhandTab").length) {
                        $("#addtableRow-modal .modal-footer").prepend('' +
                            '<button type="button" class="btn"  id="add-UrlhandTab" >新增</button>' +
                            '<button type="button" class="btn" id="del-UrlhandTab" ' +
                            'style="margin-right: 7px;margin-left: 17px;">删除</button>'
                        );
                    }
                    break;
                // case "baowenJySer":
                    // var html = '<div class="row">' +
                    //     '<div class="col-md-12" style="max-height: 200px;overflow-y: scroll;">' +
                    //     '<table id="handlUrl" style="background: #fff;"></table>' +
                    //     '</div>' +
                    //     '</div>';
                    // $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(html);
                    // if (!$("#add-UrlhandTab").length) {
                    //     $("#addtableRow-modal .modal-footer").prepend('' +
                    //         '<button type="button" class="btn"  id="add-UrlhandTab" >新增</button>' +
                    //         '<button type="button" class="btn" id="del-UrlhandTab" ' +
                    //         'style="margin-right: 7px;margin-left: 17px;">删除</button>'
                    //     );
                    // }
                    // break;
                case "usabilityAddRow":
                    for (var i = 0; i < length; i++) {
                        if (titleText[i] == "状态") {
                            var select = $('<select class="form-control input-sm addtableRowInput"></select>');
                            select.append('<option value="N">关闭</option>');
                            select.append('<option value="Y">开启</option>');
                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select)),
                                label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        } else {
                            var formGroup = $('<div class="form-group group-Style">' +
                                '<div class="col-md-9">' +
                                '<input type="text" class="form-control input-sm m-t-15 addtableRowInput">' +
                                '</div>' +
                                '</div>');
                            var label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        }
                    }
                    break;
                default:
                    for (var i = 0; i < length; i++) {
                        var formGroup = $('<div class="form-group group-Style">' +
                            '<div class="col-md-9">' +
                            '<input type="text" class="form-control input-sm m-t-15 addtableRowInput">' +
                            '</div>' +
                            '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            titleText[i] +
                            '</label>');
                        $(formGroup).prepend(label);
                        $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                    }
            }
            switch (modalAttr){
                // case "oberSet":
                case "userSet":
                case "serSet":
                case "webSer":
                case "oraSer":
                case "mySqlSer":
                case "sqlSer":
                    var formGroup = $('<div class="form-group">' +
                        '<label class="col-md-2 control-label">重新开始分析</label>' +
                        '<div class="col-md-9">' +
                        '<input type="checkbox" class="input-sm m-t-15 cursor" id="analysis_again">' +
                        '</div></div>' +
                        '<div class="form-group">' +
                        '<span class="red" style="font-family: sans-serif;">' +
                        '此次操作需要重启分析程序，如果您需要继续修改其他同类设置，' +
                        '可以最后一个修改完成后，再勾选“重启分析程序”。</span></div>');
                    $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                    break;
            }
            if (modalAttr) {
                $("#addtableRow-modal").attr("data-tableAttr", modalAttr);
            }
            if (page == "observationPoint") {
                setTimeout(function () {
                    $("#add-UrlhandTab").remove();
                    $("#del-UrlhandTab").remove();
                    $("#addtableRow-modal").modal("show");
                }, 300)
            } else {
                //urlSer baowenJySer
                // if (page == "urlSer" || page == "baowenJySer") {
                if (page == "urlSer") {
                    var trId,
                        coloumns;
                    if (page == "urlSer") {
                        coloumns = [{
                            field: "num",
                            title: "编号",
                            sortable: true
                        }, {
                            field: "name",
                            title: "名称",
                            sortable: true
                        }, {
                            field: "url",
                            title: "URL",
                            sortable: true
                        }];
                    } else {
                        // page == "baowenJySer"
                        coloumns = [{
                            field: "name",
                            title: "报文交易名称",
                            sortable: true
                        }, {
                            field: "displayIp",
                            title: "服务端IP端口",
                            sortable: true
                        }, {
                            field: "clientIpport",
                            title: "客户端IP",
                            sortable: true
                        }, {
                            field: "statusCode",
                            title: "交易返回码",
                            sortable: true
                        }, {
                            field: "successCode",
                            title: "成功返回码",
                            sortable: true
                        }, {
                            field: "failedCode",
                            title: "失败返回码",
                            sortable: true
                        }];
                        for (var i = 0; i < 20; i++) {
                            var columnsObj = {
                                field: "busTag" + i,
                                title: "自定义列" + i,
                                sortable: true
                            };
                            coloumns.push(columnsObj)
                        }
                    }
                    $("#addtableRow-modal #handlUrl").bootstrapTable({
                        toggle: "table",
                        columns: coloumns,
                        undefinedText: "",
                        onClickRow: function (row, trEle) {
                            $("#addtableRow-modal #handlUrl > tbody > .custom-row-style").removeClass();
                            $(trEle).addClass("custom-row-style");
                            trId = row.id;
                        },
                        onDblClickCell: function (field, value, row, $element) {
                            if (!$($element).children().hasClass("rowCellInput")) {
                                var input = $('<input type="text" class="rowCellInput" value="' + $($element).text() + '"/>')
                                    .width($($element).outerWidth(true))
                                    .height($($element).outerHeight(true)).css("background", "none"),
                                    rowObj = {};
                                $($element).html(input).css({
                                    "padding": 0,
                                    "text-align": "center",
                                    "width": $($element).children().width(),
                                    "height": $($element).children().height()
                                });
                                $($element).children(input).focus().blur(function () {
                                    rowObj[field] = $($element).children(input).val();
                                    $("#addtableRow-modal #handlUrl").bootstrapTable("updateRow", {
                                        index: $($element).parent("tr").attr("data-index"),
                                        row: rowObj
                                    });
                                    $($element).css("padding", "");
                                });
                            }
                        }
                    });
                    $("#add-UrlhandTab").off("click").on("click", function () {
                        // 此处还应该区别是 httP 还是 baowenJySer 两者的row 值不一样 wb wu i g su 
                        $("#addtableRow-modal #handlUrl").bootstrapTable('insertRow', {
                            index: 0,
                            row: {
                                id: "s" + $("#addtableRow-modal #handlUrl").bootstrapTable("getData").length
                                // num: "",
                                // name: "",
                                // url: ""
                            }
                        });
                    });
                    $("#del-UrlhandTab").off("click").on("click", function () {
                        $("#addtableRow-modal #handlUrl").bootstrapTable('remove', {
                            field: 'id',
                            values: [trId]
                        });
                    });
                    $("#addtableRow-modal").modal("show");
                } else {
                    $("#add-UrlhandTab").remove();
                    $("#del-UrlhandTab").remove();
                    $("#addtableRow-modal").modal("show");
                }
            }
        },
        /**
         *  编辑表格内容时 弹出模态框的实现
         * @param tableId
         * @param iniUrl 初始化url
         * @param field array 表头中文名称的英文名 bsTable会根据此英文名去找数据
         * @param titleText array 表头的中文名
         * @param page 某个页面
         * @param watgetUrl 观察点特有的url
         * @param modalAttr
         * @param ajaxParams
         */
        editRowModal: function (tableId, iniUrl, field, titleText, page, watgetUrl, modalAttr, ajaxParams) {
            var selectRowId = $(tableId + " .custom-row-style").attr("data-id"),
                index = $(tableId + " .custom-row-style").attr("data-index"),
                dataItem;
            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
            if(modalAttr == "baowenJySer"){
                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").css({
                    "height":200,
                    "overflow-y":"scroll",
                    "overflow-x":"hidden",
                    "margin-left":"1px",
                    "position":"relative",
                    "left":"12px"
                });
            }else {
                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").removeAttr("style");
            }
            if (selectRowId != undefined) {
                switch (page) {
                    case "cenTer":
                        $(tableId).bootstrapTable("getData").forEach(function (item,index) {
                           if(item.id == selectRowId){
                               for (var i = 0; i < titleText.length; i++) {
                                   var formGroup;
                                   switch (titleText[i]){
                                       case "上线时间":
                                           formGroup = $('<div class="form-group group-Style">' +
                                               '<div class="col-md-9">' +
                                               '<input type="text" id="center-SxTime" class="form-control input-sm m-t-15 addtableRowInput" value="'+$.myTime.UnixToDate(item[field[i]]).split(" ")[0]+'">' +
                                               '</div>' +
                                               '</div>');
                                           break;
                                       case "服务时限":
                                           formGroup = $('<div class="form-group group-Style">' +
                                               '<div class="col-md-9">' +
                                               '<input type="text" id="center-SerTime" class="form-control input-sm m-t-15 addtableRowInput" value="'+$.myTime.UnixToDate(item[field[i]]).split(" ")[0]+'">' +
                                               '</div>' +
                                               '</div>');
                                           break;
                                       case "状态":
                                           formGroup = $('<div class="form-group group-Style">' +
                                               '<div class="col-md-9">' +
                                               '<select class="form-control m-t-15" id="center-stage" style="height: 30px;">' +
                                               '<option value="1">正式用户</option>' +
                                               '<option value="2">测试用户</option>' +
                                               '</select>' +
                                               '</div>' +
                                               '</div>');
                                           break;
                                       case "硬件版本":
                                           formGroup = $('<div class="form-group group-Style">' +
                                               '<div class="col-md-9">' +
                                               '<select class="form-control m-t-15" id="center-version"  style="height: 30px;">' +
                                               '<option value="1">专线宝B</option>' +
                                               '<option value="2">专线宝U</option>' +
                                               '<option value="3">专线宝S</option>' +
                                               '<option value="4">XPM管理平台</option>' +
                                               '</select>' +
                                               '</div>' +
                                               '</div>');
                                           break;
                                       default:
                                           if((titleText[i]=="IP" || titleText[i] == "端口") && selectRowId == "1"){
                                               formGroup = $('<div class="form-group group-Style">' +
                                                   '<div class="col-md-9">' +
                                                   '<input type="text" class="form-control input-sm m-t-15 addtableRowInput" value="'+item[field[i]]+'" readonly="readonly">' +
                                                   '</div>' +
                                                   '</div>');
                                           }else {
                                               formGroup = $('<div class="form-group group-Style">' +
                                                   '<div class="col-md-9">' +
                                                   '<input type="text" class="form-control input-sm m-t-15 addtableRowInput" value="'+item[field[i]]+'">' +
                                                   '</div>' +
                                                   '</div>');
                                           }
                                   }
                                   var label = $('<label class="col-md-2 control-label">' +
                                       titleText[i] +
                                       '</label>');
                                   $(formGroup).prepend(label);
                                   $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                               }
                               $("#changetableRow-modal #center-stage").children("option").eq(item.state-1).attr("selected","selected");
                               $("#changetableRow-modal #center-version").children("option").eq(item.version-1).attr("selected","selected");
                               /**
                                * 此处调用日历插件
                                */
                               var start = {
                                   format: 'YYYY-MM-DD',
                                   trigger: "focus click",
                                   okfun: function(obj){
                                       endDates();
                                   }
                               };
                               var end = {
                                   format: 'YYYY-MM-DD',
                                   trigger: "focus click",
                                   isServerMaxDate: false,
                                   okfun: function(obj){}
                               };
                               function endDates() {
                                   //将结束日期的事件改成 false 即可
                                   end.trigger = false;
                                   $("#changetableRow-modal #center-SerTime").jeDate(end);
                               }
                               $("#changetableRow-modal #center-SxTime").jeDate(start);
                               $("#changetableRow-modal #center-SerTime").jeDate(end);
                           }
                        });
                        break;
                    case "observationPoint":
                        $.ajax({
                            url: iniUrl,
                            async: false,
                            method: "POST",
                            data: {},
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (cdata,textStatus,XMLHttpRequest) {
                                cdata.forEach(function (item) {
                                    if (item.id == selectRowId) {
                                        dataItem = item;
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
                        $.ajax({
                            url: watgetUrl,
                            method: "POST",
                            data: "",
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                var InputVal = [];
                                for (var j = 0; j < field.length; j++) {
                                    InputVal.push(dataItem[field[j]]);
                                }
                                for (var i = 0; i < titleText.length; i++) {
                                    switch (titleText[i]) {
                                        case "名称":
                                        case "备注":
                                            var formGroup = $('<div class="form-group">' +
                                                '<div class="col-md-9">' +
                                                '<input type="text" class="form-control input-sm m-t-15 addtableRowInput"  value="' + InputVal[i] + '">' +
                                                '</div>' +
                                                '</div>');
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "带宽[Mb]":
                                            var formGroup = $('<div class="form-group">' +
                                                '<div class="col-md-9">' +
                                                '<input type="text" class="form-control input-sm m-t-15 addtableRowInputbdWidth"  value="' + InputVal[i] + '">' +
                                                '</div>' +
                                                '</div>');
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "网卡名":
                                            var wangName = [],
                                                wangNameId = [];
                                            for (var k = 0; k < data["name"].length; k++) {
                                                wangName.push(data["name"][k]["name"]);
                                                wangNameId.push(data["name"][k]["id"]);
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                for (var k = 0; k < wangName.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(wangName[k])) {
                                                        select.append('<option value="' + wangNameId[k] + '" selected="selected">' +
                                                            $.trim(InputVal[i].split(",")[j]) + '</option>');
                                                        wangName.splice(k, 1);
                                                        wangNameId.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if (wangName.length) {
                                                for (var j = 0; j < wangName.length; j++) {
                                                    select.append('<option value="' + wangNameId[j] + '">' + wangName[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "VLAN ID":
                                            var vlanId = [];
                                            for (var k = 0; k < data["vid"][0]["vid"].split(",").length; k++) {
                                                vlanId.push($.trim(data["vid"][0]["vid"].split(",")[k]));
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < vlanId.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(vlanId[k])) {
                                                        vlanId.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if (vlanId.length) {
                                                for (var j = 0; j < vlanId.length; j++) {
                                                    select.append('<option value="">' + vlanId[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "VXLAN ID":
                                            // var mpls = [];
                                            // for (var k = 0; k < data["lid"][0]["lid"].split(",").length; k++) {
                                            //     mpls.push($.trim(data["lid"][0]["lid"].split(",")[k]))
                                            // }
                                            // var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            // for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                            //     select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                            //     for (var k = 0; k < mpls.length; k++) {
                                            //         if ($.trim(InputVal[i].split(",")[j]) == $.trim(mpls[k])) {
                                            //             mpls.splice(k, 1);
                                            //         }
                                            //     }
                                            // }
                                            // if (mpls.length) {
                                            //     for (var j = 0; j < mpls.length; j++) {
                                            //         select.append('<option value="">' + mpls[j] + '</option>');
                                            //     }
                                            // }
                                            // var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            // var label = $('<label class="col-md-2 control-label">' +
                                            //     titleText[i] +
                                            //     ':</label>');
                                            // $(formGroup).prepend(label);
                                            // $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            var mpls = [];
                                            for (var k = 0; k < data["vxid"][0]["vxid"].split(",").length; k++) {
                                                mpls.push($.trim(data["vxid"][0]["vxid"].split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < mpls.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(mpls[k])) {
                                                        mpls.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if (mpls.length) {
                                                for (var j = 0; j < mpls.length; j++) {
                                                    select.append('<option value="">' + mpls[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "MPLS LABLE1":
                                            var lid1 = [];
                                            for(var k = 0;k<data.lid1[0].lid1.split(",").length;k++){
                                                lid1.push($.trim(data.lid1[0].lid1.split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < lid1.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(lid1[k])) {
                                                        //此处代码很有可能因为删除数据元素改变咯数组长度而导致bug 待验证
                                                        lid1.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if(lid1.length){
                                                for(var j = 0;j<lid1.length;j++){
                                                    select.append('<option value="">' + lid1[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "MPLS LABLE2":
                                            var lid2 = [];
                                            for(var k = 0;k<data.lid2[0].lid2.split(",").length;k++){
                                                lid2.push($.trim(data.lid2[0].lid2.split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < lid2.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(lid2[k])) {
                                                        //此处代码很有可能因为删除数据元素改变咯数组长度而导致bug 待验证
                                                        lid2.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if(lid2.length){
                                                for(var j = 0;j<lid2.length;j++){
                                                    select.append('<option value="">' + lid2[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "MPLS LABLE3":
                                            var lid3 = [];
                                            for(var k = 0;k<data.lid3[0].lid3.split(",").length;k++){
                                                lid3.push($.trim(data.lid3[0].lid3.split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < lid3.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(lid3[k])) {
                                                        //此处代码很有可能因为删除数据元素改变咯数组长度而导致bug 待验证
                                                        lid3.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if(lid3.length){
                                                for(var j = 0;j<lid3.length;j++){
                                                    select.append('<option value="">' + lid3[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "MPLS LABLE4":
                                            var lid4 = [];
                                            for(var k = 0;k<data.lid4[0].lid4.split(",").length;k++){
                                                lid4.push($.trim(data.lid4[0].lid4.split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < lid4.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(lid4[k])) {
                                                        //此处代码很有可能因为删除数据元素改变咯数组长度而导致bug 待验证
                                                        lid4.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if(lid4.length){
                                                for(var j = 0;j<lid4.length;j++){
                                                    select.append('<option value="">' + lid4[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                        case "MPLS LABLE5":
                                            var lid5 = [];
                                            for(var k = 0;k<data.lid5[0].lid5.split(",").length;k++){
                                                lid5.push($.trim(data.lid5[0].lid5.split(",")[k]))
                                            }
                                            var select = $('<select data-placeholder="请选择" class="tag-select-limited" multiple></select>');
                                            for (var j = 0; j < InputVal[i].split(",").length; j++) {
                                                select.append('<option value="" selected="selected">' + InputVal[i].split(",")[j] + '</option>');
                                                for (var k = 0; k < lid5.length; k++) {
                                                    if ($.trim(InputVal[i].split(",")[j]) == $.trim(lid5[k])) {
                                                        //此处代码很有可能因为删除数据元素改变咯数组长度而导致bug 待验证
                                                        lid5.splice(k, 1);
                                                    }
                                                }
                                            }
                                            if(lid5.length){
                                                for(var j = 0;j<lid5.length;j++){
                                                    select.append('<option value="">' + lid5[j] + '</option>');
                                                }
                                            }
                                            var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                            var label = $('<label class="col-md-2 control-label">' +
                                                titleText[i] +
                                                ':</label>');
                                            $(formGroup).prepend(label);
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                            break;
                                    }
                                }
                                $(".tag-select-limited").chosen({
                                    max_selected_options: 110
                                });
                                var formGroup = $('<div class="form-group">' +
                                    '<label class="col-md-2 control-label">重新开始分析</label>' +
                                    '<div class="col-md-9">' +
                                    '<input type="checkbox" class="input-sm m-t-15 cursor" id="analysis_again">' +
                                    '</div></div>' +
                                    '<div class="form-group">' +
                                    '<span class="red" style="font-family: sans-serif;">' +
                                    '此次操作需要重启分析程序，如果您需要继续修改其他同类设置，' +
                                    '可以最后一个修改完成后，再勾选“重启分析程序”。</span></div>');
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    case "urlSer":
                        $.ajax({
                            url: iniUrl,
                            method: "POST",
                            data: ajaxParams,
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                var html = '<div class="row">' +
                                    '<div class="col-md-5">' +
                                    '<label for="urlName">业务名称</label>' +
                                    '<input type="text" id="urlName" value="' + data.name + '" style="color:#000;margin-left: 10px;">' +
                                    '</div>' +
                                    '<div class="col-md-5">' +
                                    '<label for="urlps">备注</label>' +
                                    '<input type="text" id="urlps" value="' + data.descrption + '" style="color:#000;margin-left: 10px;">' +
                                    '</div>' +
                                    '<div class="col-md-2">' +
                                    '<span style="color:red;position: relative;left: -30px;top:3px;">URL通配符:***</span>' +
                                    '</div>' +
                                    '</div>' +
                                    '<div class="row">' +
                                    '<div class="col-md-12" style="max-height: 200px;overflow-y: scroll;">' +
                                    '<table id="handlUrl" style="background: #fff;"></table>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(html);
                                if (!$("#add-UrlhandTab-edi").length) {
                                    //此处的button id 应该更改成与添的时不一样的id 避免重复
                                    $("#changetableRow-modal .modal-footer").prepend('' +
                                        '<button type="button" class="btn"  id="add-UrlhandTab-edi" >新增</button>' +
                                        '<button type="button" class="btn" id="del-UrlhandTab-edi" ' +
                                        'style="margin-right: 7px;margin-left: 17px;">删除</button>'
                                    );
                                }
                                var trId;
                                $("#changetableRow-modal #handlUrl").bootstrapTable({
                                    toggle: "table",
                                    columns: [{
                                        field: "num",
                                        title: "编号",
                                        sortable: true
                                    }, {
                                        field: "name",
                                        title: "名称",
                                        sortable: true
                                    }, {
                                        field: "url",
                                        title: "URL",
                                        sortable: true
                                    }],
                                    onClickRow: function (row, trEle) {
                                        $("#changetableRow-modal #handlUrl > tbody > .custom-row-style").removeClass();
                                        $(trEle).addClass("custom-row-style");
                                        trId = row.id;
                                    },
                                    onDblClickCell: function (field, value, row, $element) {
                                        if (!$($element).children().hasClass("rowCellInput")) {
                                            var input = $('<input type="text" class="rowCellInput" value="' + $($element).text() + '"/>')
                                                .width($($element).outerWidth(true))
                                                .height($($element).outerHeight(true)).css({
                                                    "background": "none",
                                                    "padding": 0
                                                }),
                                                rowObj = {};
                                            $($element).html(input).css({
                                                "padding": 0,
                                                "text-align": "center",
                                                "width": $($element).children().width(),
                                                "height": $($element).children().height()
                                            });
                                            $($element).children(input).focus().blur(function () {
                                                rowObj[field] = $($element).children(input).val();
                                                $("#changetableRow-modal #handlUrl").bootstrapTable("updateRow", {
                                                    index: $($element).parent("tr").attr("data-index"),
                                                    row: rowObj
                                                });
                                                $($element).css("padding", "");
                                            });
                                        }
                                    }
                                });
                                $("#changetableRow-modal #handlUrl").bootstrapTable("load", data.set);
                                $("#add-UrlhandTab-edi").off("click").on("click", function () {
                                    $("#changetableRow-modal #handlUrl").bootstrapTable('insertRow', {
                                        index: 0,
                                        row: {
                                            id: "s" + $("#changetableRow-modal #handlUrl").bootstrapTable("getData").length,
                                            num: "",
                                            name: "",
                                            url: ""
                                        }
                                    });
                                });
                                $("#del-UrlhandTab-edi").off("click").on("click", function () {
                                    $("#changetableRow-modal #handlUrl").bootstrapTable('remove', {
                                        field: 'id',
                                        values: [trId]
                                    });
                                });
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                        break;
                    // case "baowenJySer":
                    //     $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
                    //     var html = '<div class="row">' +
                    //         '<div class="col-md-12" style="max-height: 200px;overflow-y: scroll;">' +
                    //         '<table id="handlUrl" style="background: #fff;"></table>' +
                    //         '</div>' +
                    //         '</div>',
                    //         baowenJyColmns = [{
                    //             field: "name",
                    //             title: "报文交易名称",
                    //             sortable: true
                    //         }, {
                    //             field: "displayIp",
                    //             title: "服务端IP端口",
                    //             sortable: true
                    //         }, {
                    //             field: "clientIpport",
                    //             title: "客户端IP",
                    //             sortable: true
                    //         }, {
                    //             field: "statusCode",
                    //             title: "交易返回码",
                    //             sortable: true
                    //         }, {
                    //             field: "successCode",
                    //             title: "成功返回码",
                    //             sortable: true
                    //         }, {
                    //             field: "failedCode",
                    //             title: "失败返回码",
                    //             sortable: true
                    //         }],
                    //         trId;
                    //     $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(html);
                    //     for (var i = 0; i < 20; i++) {
                    //         var columnsObj = {
                    //             field: "busTag" + i,
                    //             title: "自定义列" + i,
                    //             sortable: true
                    //         };
                    //         baowenJyColmns.push(columnsObj)
                    //     }
                    //     $("#changetableRow-modal #handlUrl").bootstrapTable({
                    //         url: "/depthanaly/selectAll.do",
                    //         toggle: "table",
                    //         columns: baowenJyColmns,
                    //         onClickRow: function (row, trEle) {
                    //             $("#changetableRow-modal #handlUrl > tbody > .custom-row-style").removeClass();
                    //             $(trEle).addClass("custom-row-style");
                    //             trId = row.id;
                    //         },
                    //         rowStyle: function (row, index) {
                    //             var cal = {};
                    //             if (row.id == selectRowId) {
                    //                 cal.classes = "custom-row-style";
                    //             }
                    //             return cal;
                    //         },
                    //         onDblClickCell: function (field, value, row, $element) {
                    //             if (!$($element).children().hasClass("rowCellInput")) {
                    //                 var input = $('<input type="text" class="rowCellInput" value="' + $($element).text() + '"/>')
                    //                     .width($($element).outerWidth(true))
                    //                     .height($($element).outerHeight(true)).css({
                    //                         "background": "none",
                    //                         "padding": 0
                    //                     }),
                    //                     rowObj = {};
                    //                 $($element).html(input).css({
                    //                     "padding": 0,
                    //                     "text-align": "center",
                    //                     "width": $($element).children().width(),
                    //                     "height": $($element).children().height()
                    //                 });
                    //                 $($element).children(input).focus().blur(function () {
                    //                     rowObj[field] = $($element).children(input).val();
                    //                     $("#changetableRow-modal #handlUrl").bootstrapTable("updateRow", {
                    //                         index: $($element).parent("tr").attr("data-index"),
                    //                         row: rowObj
                    //                     });
                    //                     $($element).css("padding", "");
                    //                 });
                    //             }
                    //         }
                    //     });
                    //     break;
                    case "usabilityUp":
                        $.ajax({
                            url: iniUrl,
                            method: "POST",
                            data: ajaxParams ? ajaxParams : {},
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (cdata,textStatus,XMLHttpRequest) {
                                cdata.forEach(function (item) {
                                    if (item.id == selectRowId) {
                                        dataItem = item;
                                    }
                                });
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
                                var InputVal = [];
                                for (var j = 0; j < field.length; j++) {
                                    InputVal.push(dataItem[field[j]]);
                                }
                                for (var i = 0; i < titleText.length; i++) {
                                    if (titleText[i] == "状态") {
                                        var select = $('<select class="form-control input-sm addtableRowInput"></select>');
                                        if (InputVal[i] == "N") {
                                            select.append('<option value="N" selected="selected">关闭</option>');
                                            select.append('<option value="Y">开启</option>');
                                        } else {
                                            select.append('<option value="N">关闭</option>');
                                            select.append('<option value="Y"  selected="selected">开启</option>');
                                        }
                                        var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9 m-t-15"></div>').append(select));
                                        var label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                                        $(formGroup).prepend(label);
                                        $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                    } else {
                                        var formGroup = $('<div class="form-group">' +
                                            '<div class="col-md-9">' +
                                            '<input type="text" class="form-control input-sm m-t-15 addtableRowInput"  value="' + InputVal[i] + '">' +
                                            '</div>' +
                                            '</div>');
                                        var label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                                        $(formGroup).prepend(label);
                                        $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
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
                        break;
                    default:
                        $.ajax({
                            url: iniUrl,
                            method: "POST",
                            async:false,
                            data: ajaxParams ? ajaxParams : {},
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (cdata,textStatus,XMLHttpRequest) {
                                cdata.forEach(function (item) {
                                	if(item.descrption == null){
                                	    item.descrption = "";
                                	}
                                    if (item.id == selectRowId) {
                                        dataItem = item;
                                    }
                                });
                                var InputVal = [];
                                for (var j = 0; j < field.length; j++) {
                                    InputVal.push(dataItem[field[j]]);
                                }
                                for (var i = 0; i < titleText.length; i++) {
                                    var formGroup = $('<div class="form-group">' +
                                        '<div class="col-md-9">' +
                                        '<input type="text" class="form-control input-sm m-t-15 addtableRowInput"  value="' + InputVal[i] + '">' +
                                        '</div>' +
                                        '</div>');
                                    var label = $('<label class="col-md-2 control-label">' +
                                        titleText[i] +
                                        ':</label>');
                                    $(formGroup).prepend(label);
                                    $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
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
                switch (modalAttr){
                    case "userSet":
                    case "serSet":
                    case "webSer":
                    case "oraSer":
                    case "mySqlSer":
                    case "sqlSer":
                        var formGroup = $('<div class="form-group">' +
                            '<label class="col-md-2 control-label">重新开始分析</label>' +
                            '<div class="col-md-9">' +
                            '<input type="checkbox" class="input-sm m-t-15 cursor" id="analysis_again">' +
                            '</div></div>' +
                            '<div class="form-group">' +
                            '<span class="red" style="font-family: sans-serif;">' +
                            '此次操作需要重启分析程序，如果您需要继续修改其他同类设置，' +
                            '可以最后一个修改完成后，再勾选“重启分析程序”。</span></div>');
                        $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                }
                if (modalAttr) {
                    $('#changetableRow-modal').attr("data-tableAttr", modalAttr);
                }
                if (page != "urlSer") {
                    $("#add-UrlhandTab-edi").remove();
                    $("#del-UrlhandTab-edi").remove();
                }
                if (page == "observationPoint" ||
                    page == "usabilityUp") {
                    setTimeout(function () {
                        $('#changetableRow-modal').modal('show');
                    }, 300);
                } else {
                    $('#changetableRow-modal').modal('show');
                }
            }
        }
    };
    $.extend({
        JinibsTable: function (tableId, iniurl, toolbarId, fieldArry, titleArry, page, _queryParams) {
            bsTable.initializ(tableId, iniurl, toolbarId, fieldArry, titleArry, page, _queryParams);
        },
        JaddbsModalRow: function (titleText, page, watgetUrl, modalAttr) {
            bsTable.addRowModal(titleText, page, watgetUrl, modalAttr);
        },
        JaddbsTableRow: function (tableId, addUrl, iniUrl, page, _moduleId) {
            bsTable.addTableRow(tableId, addUrl, iniUrl, page, _moduleId);
        },
        JeditRowModal: function (tableId, iniUrl, field, titleText, page, watgetUrl, modalAttr, ajaxParams) {
            bsTable.editRowModal(tableId, iniUrl, field, titleText, page, watgetUrl, modalAttr, ajaxParams);
        },
        JediTableRow: function (tableId, ediUrl, page, iniUrl, _moduleId) {
            bsTable.ediTableRow(tableId, ediUrl, page, iniUrl, _moduleId);
        },
        JremovebsTableRow: function (tableId, revUrl, page, iniUrl, _moduleId,_thisBtn) {
            bsTable.removeRow(tableId, revUrl, page, iniUrl, _moduleId,_thisBtn);
        },
        JeditKpisetModal: function (tableId, titleUrl, iniUrl, watchpointId, businessId, moduleId, kpiId, modalAttr) {
            bsTable.editKpisetModal(tableId, titleUrl, iniUrl, watchpointId, businessId, moduleId, kpiId, modalAttr);
        },
        JeditKpiSetTable: function (tableId, ediUrl, params,alarmSelectRow) {
            bsTable.editKpiSetTable(tableId, ediUrl, params,alarmSelectRow);
        },
        JzuheKpisetModal: function (tableId, iniUrl, urlParma, columns, modalAttr) {
            bsTable.zuheKpisetModal(tableId, iniUrl, urlParma, columns, modalAttr);
        },
        JzuheKpicommitData: function (url, urlParma) {
            bsTable.zuheKpicommitData(url, urlParma)
        }
    });
    /**
     * 获取网卡数量
     * @param source
     * @param data
     * @param isAdd
     * @returns {{sc: number, uc: number}}
     */
    var getCardNumCount = function (source, data, isAdd) {
        var count = {
                sc: 0, // 修改前
                uc: 0 // 修改后
            },
            arr = null,
            map = {};
        for (var i = 0, len = source.length; i < len; i++) {
            arr = source[i].did.split(",");
            for (var j = 0, jlen = arr.length; j < jlen; j++) {
                map[arr[j]] = true;
            }
        }
        for (var key in map) {
            count.sc++;
        }
        if (isAdd) { // 添加
            map = {};
            arr = data.didProtype.split(",");
            for (var j = 0, jlen = arr.length; j < jlen; j++) {
                map[arr[j]] = true;
            }
            for (var key in map) {
                count.uc++;
            }
            count.uc += count.sc;
        } else {
            map = {};
            for (var i = 0, len = source.length; i < len; i++) {
                if (source[i].id == data.id) continue;
                arr = source[i].did.split(",");
                for (var j = 0, jlen = arr.length; j < jlen; j++) {
                    map[arr[j]] = true;
                }
            }
            for (var key in map) {
                count.uc++;
            }
            count.uc += data.didProtype.split(",").length;
        }

        return count;
    }
});