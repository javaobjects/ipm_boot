/**
 * 报表模板添加部分
 */
$(function() {
    $(".formContent").hide();
    $(".formDeleteContent").hide();
    //定义全局变量，保存kpi选中所有input框
    var kpiNames = [];
    //存放列表是否选中的值
    var listwarn = [];
    //存放告警是否选中
    var alarm = [];

    var selectRow = null,
        selectRowId = null;

    var columns = [{
        field: "name",
        title: "模板名称",
        sortable: true
    }, {
        field: "moduleIdstwo",
        title: "关联模块",
        sortable: true
    }, {
        field: "userId",
        title: "制作人",
        sortable: true
    }, {
        field: "createTime",
        title: "制作时间",
        sortable: true,
        formatter: function(v) {
            if (v == 0) {
                return null;
            } else {
                return $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss");
            }
        }
    }, {
        field: "description",
        title: "说明",
        sortable: true
    }];
    $.ptcsBSTable("fromSet", "/ReportModalController/allModal.do", null, {
        columns: columns,
        ipm_title: "模板功能",
        ipm_shrink: true,
        pageSize: 10,
        rowStyle: function(row, i) {
            var cla = {};
            if (i == 0) {
                cla.classes = "custom-row-style";
                selectRow = row;
                selectRowId = row.id;
                selectRowState = row.state;
                selectRowName = row.name;
                selecttypeId = row.typeId;
            }
            return cla;
        },
        onClickRow: function(row, tr) {
            $("#fromSet > tbody > .custom-row-style").removeClass("custom-row-style");
            $(tr).addClass("custom-row-style");
            selectRow = row;
            selectRowId = row.id;
            selectRowState = row.state;
            selectRowName = row.name;
            selecttypeId = row.typeId;
        },
        ipm_toolbar: [{
            name: "新增",
            type: "plus",
            call: function(e) {
                kpiNames = [];
                //存放列表是否选中的值
                listwarn = [];
                //存放告警是否选中
                alarm = [];
                $(".formContent").show();
                $(".formDeleteContent").hide();
                $("#content").animate({scrollTop :$(".formContent").offset().top+$("#content").scrollTop()-50},800);
                $("#nameFrom").val("");
                $("input[name='litlisChart']").attr("checked", false);
                $("input[name='listwarn']").attr("checked", false);
                $("#listForm").val('2');
                $("#alarmForm").val('2');
                $("#archivedFrom").val("");
                $("#moduleType").val('10');
                $.ajax({
                    url: "/watchpointController/getFindAll.do",
                    type: "post",
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        typeKpi(10);
                    }
                });
            }
        }, {
            name: "修改",
            type: "edit",
            call: function(e) {
                if (selectRow == null) {
                    jeBox.alert('请先添加模板');
                    return;
                }
                changeForm(selectRowId);
            }
        }, {
            name: "删除",
            type: "remove",
            call: function(e) {
                if (selectRow == null) {
                    jeBox.alert('请先添加模板');
                    return;
                }
                $("#formDelete").modal("show");
                $(".formContent").hide();
                $(".formDeleteContent").hide();
            }
        }]
    });
    //删除 
    $("#btn-fromDelete").click(function() {
        $.ajax({
            url: "/ReportModalController/deleteModal.do",
            type: "post",
            data: {
                id: selectRowId
            },
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    selectAllName();
                    $.ptcsBSTabRefresh("fromSet");
                    selectRow = null;
                } else {
                    jeBox.alert(data.msg);
                }
            }
        });
    });
    /**
     * 添加下拉框信息
     * @domId String 下拉框ID
     * @data {} 数据
     */
    var addOption = function(domId, data) {
        var option = "";
        for (var i = 0, len = data.length; i < len; i++) {
            option += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
        }
        $("#" + domId).append(option);
    };

    var addOptions = function(domId, data) {
        var option = "";
        for (var i = 0, len = data.length; i < len; i++) {
        	option += "<option value=" + data[i].id + ">" + data[i].namezh + "</option>";
        }
        $("#" + domId).append(option);
    };

    /*模块类型展示*/
    $.post("/authorizeModuleController/getAuthorizeAppModule.do", null, function(data) {
        addOptions("moduleType", data);
    }, "json");

    //切换模块类型更换各自的kpi及列表和告警
    $("#moduleType").off().change(function() {
        var moduleType = $(this).val();
        var moduleId = null,
            plotId = {};
        switch (moduleType) {
            case "10":
                checked(listwarn, alarm);
                $.post("/watchpointController/getFindAll.do", null, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
            case "11":
                checked(listwarn, alarm);
                $.post("/client/getClient.do", null, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
            case "12":
                checked(listwarn, alarm);
                $.post("/serverManagement/getAllServerSide.do", null, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
            case "4":
            case "5":
            case "6":
            case "7":
                checked(listwarn, alarm);
                $.post("/appController/getAllAppByModuleId.do", {
                    moduleId: moduleType
                }, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
            case "8":
                checked(listwarn, alarm);
                $.post("/url/get.do", {
                    moduleId: 8
                }, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
            case "9":
                checked(listwarn, alarm);
                $.post("/depthanaly/selectAll.do", {
                    moduleId: 9
                }, function() {
                    kpiNames.forEach(function(word) {
                        moduleId = word.moduleId;
                        if (moduleType == moduleId) {
                            plotId[word.plotId] = 1;
                        }
                    })
                    typeKpi(moduleType, plotId);
                }, "json");
                break;
        }
    });

    function checked(listwarn, alarm) {
        //列表是否选中
        if (listwarn.length > 0) {
            $("#listForm").attr("checked", false);
            for (var i = 0; i < listwarn.length; i++) {
                liststr = listwarn[i].split(":");
                if ($("#moduleType").val() == liststr[0] && liststr[1] == $("#listForm").val()) {
                    $("#listForm:checkbox[value='" + liststr[1] + "']").prop("checked", true);
                } else if ($("#moduleType").val() == liststr[0] && liststr[1] == "2") {
                    $("#listForm:checkbox[value='" + liststr[1] + "']").prop("checked", false);
                }
            }
        }
        //告警是否选中
        if (alarm.length > 0) {
            $("#alarmForm").attr("checked", false);
            for (var i = 0; i < alarm.length; i++) {
                alarmstr = alarm[i].split(":");
                if ($("#moduleType").val() == alarmstr[0] && alarmstr[1] == $("#alarmForm").val()) {
                    $("#alarmForm:checkbox[value='" + alarmstr[1] + "']").prop("checked", true);
                } else if ($("#moduleType").val() == alarmstr[0] && alarmstr[1] == "2") {
                    $("#alarmForm:checkbox[value='" + alarmstr[1] + "']").prop("checked", false);
                }
            }
        }
    }
    //接收列表选中的值
    var liststr = null;
    //接收告警选中的值
    var alarmstr = null;
    //列表选中值为1  未选中值为2
    $("#listForm").click(function() {
        if ($(this).is(":checked") == true) {
            $("#listForm").val("1");
            liststr = $("#moduleType").val() + ":1";
            listwarn.push(liststr);
        } else {
            // $("#listForm").val("2");
            for (var i = 0; i < listwarn.length; i++) {
                liststr = listwarn[i].split(":");
                if ($("#moduleType").val() == liststr[0] && liststr[1] == $("#listForm").val()) {
                    listwarn.splice(i, 1);
                }
            }
        }
    });
    //告警选中值为2  未选中值为1
    $("#alarmForm").click(function() {
        if ($(this).is(":checked") == true) {
            $("#alarmForm").val("1");
            alarmstr = $("#moduleType").val() + ":1";
            alarm.push(alarmstr);
        } else {
            // $("#alarmForm").val("2");
            for (var i = 0; i < alarm.length; i++) {
                alarmstr = alarm[i].split(":");
                if ($("#moduleType").val() == alarmstr[0] && alarmstr[1] == $("#alarmForm").val()) {
                    alarm.splice(i, 1);
                }
            }
        }
    });

    //获取表格所有名称
    function selectAllName() {
        namesAll = [];
        $.ajax({
            url: "/ReportModalController/allModal.do",
            type: "post",
            dataType: "json",
            success: function(data) {
                for (var a = 0; a < data.length; a++) {
                    namesAll.push(data[a].name);
                }
            }
        });
    }
    selectAllName();

    //获取中文字符的长度，验证模板说明
    function getStrLeng(str) {
        var realLength = 0;
        var len = str.length;
        var charCode = -1;
        for (var i = 0; i < len; i++) {
            charCode = str.charCodeAt(i);
            if (charCode >= 0 && charCode <= 128) {
                realLength += 1;
            } else {
                // 如果是中文则长度加2
                realLength += 2;
            }
        }
        if (realLength < 200 || realLength == "0") {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 新增报表确定按钮
     */
    $("#fromSure").click(function() {
        var _this = $(this).button("loading");
        var paramFrom = {};
        var description = $("#archivedFrom").val(); //模块说明
        var nameFrom = $("#nameFrom").val();
        var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im, //名称特殊字符验证
            regCn = /[·！#￥（）：；“”‘、，|《。》？、【】[\]]/im;

        var regEns = /[`~!@#$%^&*()_+<>?:"{}\/;'[\]]/im, //模板说明特殊字符验证
            regCns = /[·！#￥（——）：；“”‘、|《》？、【】[\]]/im;

        //验证名称是否重复
        for (c = 0; c < namesAll.length; c++) {
            if (nameFrom == namesAll[c]) {
                jeBox.alert("添加失败，名称重复");
                _this.button("reset");
                return false;
            }
        }
        //名称特殊字符验证
        if (regEn.test(nameFrom) || regCn.test(nameFrom)) {
            jeBox.alert('名称不能包含特殊字符，请重新输入');
            _this.button("reset");
            return false;
        }

        //名称字符限制验证
        if (nameFrom.length > 20 || nameFrom == "") {
            jeBox.alert('名称不能超过20个字符且不能为空');
            _this.button("reset");
            return false;
        }

        //模块特殊字符验证
        if (regEns.test(description) || regCns.test(description)) {
            jeBox.alert('模块说明只支持中英文版的逗号和句号，其他特殊字符不支持，请重新输入');
            _this.button("reset");
            return false;
        }
        //模块字符限制验证
        if (!getStrLeng(description)) {
            jeBox.alert('模块说明字符不能超过200个，请重新输入');
            _this.button("reset");
            return false;
        }

        var group = [];
        var groups = [];
        var listobj = {};
        var alarmobj = {};
        var kpiobj = {};

        paramFrom = {
            name: nameFrom, // 名称
            description: description //模板说明
        };
        //列表
        for (var j = 0; j < listwarn.length; j++) {
            var moudlelist = listwarn[j].split(":")[0];
            var statelist = listwarn[j].split(":")[1];
            listobj = {
                moduleId: moudlelist,
                tableHaving: statelist,
                type: 2
            };
            groups.push(listobj);
        }
        //告警
        for (var k = 0; k < alarm.length; k++) {
            var moudlealarm = alarm[k].split(":")[0];
            var statealarm = alarm[k].split(":")[1];
            alarmobj = {
                moduleId: moudlealarm,
                warningHaving: statealarm,
                type: 3
            };
            groups.push(alarmobj);
        }
        //kpi
        for (var h = 0; h < kpiNames.length; h++) {
            var kpilist = kpiNames[h];
            groups.push(kpilist);
        }

        // 循环groups，包含kpi，列表，告警项
        for (var s = 0; s < groups.length; s++) {
            for (var a in groups[s]) {
                paramFrom['group[' + s + '].' + a] = groups[s][a];
            }
        }
        if (groups.length == 0) {
            jeBox.alert('列表、告警、kpi至少选择一项');
            _this.button("reset");
            return;
        }
        $.ajax({
            url: "/ReportModalController/saveModal.do",
            type: "post",
            async: true,
            data: paramFrom,
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    _this.button("reset");
                    groups = [];
                    selectAllName();
                    $.ptcsBSTabRefresh("fromSet");
                    $(".formContent").hide();
                } else {
                    jeBox.alert(data.msg);
                    _this.button("reset");
                    return false;
                }
            }
        });
    });

    /**
     * kpi列表展示
     * @param {string} moduleId 模块id <br>
     *        {string} plotId 图标类型id <br>
     */
    function typeKpi(moduleId, plotId) {
        $.post("/plot/getPlotByModuleId.do", {
            moduleId: moduleId
        }, function(data) {
            var str,
                str1,
                groupName = [], //标题数组  ["告警类","时间类","流量类","会话类","数据包类","其他"]
                classId,
                trs;
            $(".list-content #table-list").empty();
            data.forEach(function(item, index) {
                if (item.groupName == "告警类") {

                } else {
                    if (groupName.indexOf(item.groupName) == -1) {
                        groupName.push(item.groupName);
                        switch (item.groupName) {
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
                        }
                        trs = $('<tr class="text-center tr-class" id="' + classId + '"  style="background: rgba(0,0,0,.55);"><td colspan="3" style="font-size: 14px;color: #fff;">' + item.groupName + '</td></tr>');
                        $(".list-content #table-list").append(trs);
                    }
                }
            });
            for (var i = 0; i < data.length; i++) {
                if (data[i].id != "321") {
                    if (data[i].graph == true) {
                        if (plotId != undefined) {
                            if (plotId[data[i].id] == 1) {
                                str = '<td><input name="litlisChart" data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '" ' +
                                    'data-plotId="' + data[i].id + '" data-plotTypeId="' + data[i].types[0].id + '"' +
                                    'value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" checked style="opacity: 1;"></td>';
                            } else {
                                str = '<td><input name="litlisChart" data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '" ' +
                                    'data-plotId="' + data[i].id + '" data-plotTypeId="' + data[i].types[0].id + '"' +
                                    'value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
                            }
                        } else {
                            str = '<td><input name="litlisChart" data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '" ' +
                                'data-plotId="' + data[i].id + '" data-plotTypeId="' + data[i].types[0].id + '"' +
                                'value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
                        }
                        trs = $('<tr>' +
                            '<td>' + data[i].name + '</td>' + str +
                            '</tr>');
                        switch (data[i].groupName) {
                            case "时间类":
                                if ($(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).length) {
                                    $(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).before(trs);
                                } else {
                                    $(".list-content #table-list").append(trs);
                                }
                                break;
                            case "流量类":
                                if ($(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).length) {
                                    $(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).before(trs);
                                } else {
                                    $(".list-content #table-list").append(trs);
                                }
                                break;
                            case "会话类":
                                if ($(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).length) {
                                    $(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).before(trs);
                                } else {
                                    $(".list-content #table-list").append(trs);
                                }
                                break;
                            case "数据包类":
                                if ($(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).length) {
                                    $(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).before(trs);
                                } else {
                                    $(".list-content #table-list").append(trs);
                                }
                                break;
                            case "其他":
                                if ($(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).length) {
                                    $(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).before(trs);
                                } else {
                                    $(".list-content #table-list").append(trs);
                                }
                                break;
                        }
                    }
                }
            }
            //保存被选中的kpi
            $(".formContent input[name='litlisChart']").click(function() {
                var moduleId = $("#moduleType").val(); //模块类型
                var plotId = $(this).val().split(":")[0];
                var plotTypeId = $(this).val().split(":")[1];
                if ($(this).is(":checked") == true) {
                    if ($(".list-content #table-list input[name='litlisChart']").is(":checked")) {
                        selectId = {
                            'moduleId': moduleId,
                            "plotId": plotId,
                            "plotTypeId": plotTypeId,
                            'type': 1
                        };
                        kpiNames.push(selectId);
                        kpiNames = $.unique(kpiNames);
                    }
                } else {
                    for (var i = 0; i < kpiNames.length; i++) {
                        var plotId = kpiNames[i].plotId;
                        if (plotId == $(this).val().split(":")[0]) {
                            kpiNames.splice($.inArray(kpiNames[i], kpiNames), 1);
                        }
                    }
                }
                if ($(".list-content #table-list input[name='litlisChart']:checkbox:checked").length == 0) {
                    for (var i = 0; i < kpiNames.length; i++) {
                        var plotId = kpiNames[i].plotId;
                        if (plotId == $(this).val().split(":")[0]) {
                            kpiNames.splice($(this));
                        }
                    }
                    return;
                }
            });
        }, "json");
    }
});

/**
 * 取消按钮
 */
$("#fromcancel").click(function() {
    $(".formContent").hide();
});