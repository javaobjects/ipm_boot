/**
 * 报表模板修改部分
 * @param {string} changeId 选中行id
 *
 */
var kpiNames = [];
//存放列表是否选中的值
var listwarn = [];
//存放告警是否选中
var alarm = [];

function changeForm(changeId) {
    $(".formContent").hide();
    $(".formDeleteContent").show();
    $("#content").animate({scrollTop :$(".formDeleteContent").offset().top+$("#content").scrollTop()-50},800);
    //修改
    $.ajax({
        url: "/ReportModalController/searchModalById.do",
        type: "post",
        data: {
            id: changeId
        },
        dataType: "json",
        success: function(data) {
            cSelectAllName(); //获取所有已添加的业务名称
            $("#clistForm").prop("checked", false); //列表默认
            $("#calarmForm").prop("checked", false); //告警默认
            $("#cnameFrom").val(data.name); //名称默认
            $("#carchivedFrom").val(data.description); //模板说明
            defaultPush(data); //默认模块业务方法

            //判断哪个选中并判断哪个先选中
            if (data[10].KPI.length > 0 || data[10].hasOwnProperty("tableHaving") || data[10].hasOwnProperty("warningHaving")) {
                cmodule(10);
                if (data[10].hasOwnProperty("tableHaving")) {
                    if (data[10].tableHaving.tableHaving == "1") {
                        $("#clistForm").prop("checked", true);
                        $("#clistForm").val("1");
                    }
                }
                if (data[10].hasOwnProperty("warningHaving")) {
                    if (data[10].warningHaving.warningHaving == "1") {
                        $("#calarmForm").prop("checked", true);
                        $("#calarmForm").val("1");
                    }
                }
                $.post("/plot/getPlotByModuleId.do", {
                    moduleId: 10
                }, function(datas) {
                    if (data[10].KPI.length > 0) {
                        for (var i = 0; i < data[10].KPI.length; i++) {
                            for (var j = 0; j < datas.length; j++) {
                                if (datas[j].id == data[10].KPI[i].plotId) {
                                    datas[j].checked = 1;
                                }
                            }
                        }
                    }
                    ctypeKpi(datas);
                }, "json");
            } else if (data[11].KPI.length > 0 || data[11].hasOwnProperty("tableHaving") || data[11].hasOwnProperty("warningHaving")) {
                cmodule(11);
                if (data[11].hasOwnProperty("tableHaving")) {
                    if (data[11].tableHaving.tableHaving == "1") {
                        $("#clistForm").prop("checked", true);
                        $("#clistForm").val("1");
                    }
                }
                if (data[11].hasOwnProperty("warningHaving")) {
                    if (data[11].warningHaving.warningHaving == "1") {
                        $("#calarmForm").prop("checked", true);
                        $("#calarmForm").val("1");
                    }
                }
                $.post("/plot/getPlotByModuleId.do", {
                    moduleId: 11
                }, function(datas) {
                    if (data[11].KPI.length > 0) {
                        for (var i = 0; i < data[11].KPI.length; i++) {
                            for (var j = 0; j < datas.length; j++) {
                                if (datas[j].id == data[11].KPI[i].plotId) {
                                    datas[j].checked = 1;
                                }
                            }
                        }
                    }
                    ctypeKpi(datas);
                }, "json");
            } else if (data[12].KPI.length > 0 || data[12].hasOwnProperty("tableHaving") || data[12].hasOwnProperty("warningHaving")) {
                cmodule(12);
                if (data[12].hasOwnProperty("tableHaving")) {
                    if (data[12].tableHaving.tableHaving == "1") {
                        $("#clistForm").prop("checked", true);
                        $("#clistForm").val("1");
                    }
                }
                if (data[12].hasOwnProperty("warningHaving")) {
                    if (data[12].warningHaving.warningHaving == "1") {
                        $("#calarmForm").prop("checked", true);
                        $("#calarmForm").val("1");
                    }
                }
                $.post("/plot/getPlotByModuleId.do", {
                    moduleId: 12
                }, function(datas) {
                    if (data[12].KPI.length > 0) {
                        for (var i = 0; i < data[11].KPI.length; i++) {
                            for (var j = 0; j < datas.length; j++) {
                                if (datas[j].id == data[12].KPI[i].plotId) {
                                    datas[j].checked = 1;
                                }
                            }
                        }
                    }
                    ctypeKpi(datas);
                }, "json");
            }
        }
    });

    //模块类型change事件
    $("#cmoduleType").off().change(function() {
        var cmoduleType = $(this).val();
        $.ajax({
            url: "/ReportModalController/searchModalById.do",
            type: "post",
            data: {
                id: changeId
            },
            dataType: "json",
            success: function(data) {
                if (cmoduleType == 10) {
                    if (data[10].hasOwnProperty("tableHaving")) {
                        if (data[10].tableHaving.tableHaving == "1") {
                            $("#clistForm").val("1");
                        }
                    }
                    if (data[10].hasOwnProperty("warningHaving")) {
                        if (data[10].warningHaving.warningHaving == "1") {
                            $("#calarmForm").val("1");
                        }
                    }
                    checked(cmoduleType);
                    $.post("/plot/getPlotByModuleId.do", {
                        moduleId: 10
                    }, function(datas) {
                        for (var i = 0; i < kpiNames.length; i++) {
                            if (kpiNames[i].moduleId == cmoduleType) {
                                for (var j = 0; j < datas.length; j++) {
                                    if (datas[j].id == kpiNames[i].plotId) {
                                        datas[j].checked = 1;
                                    }
                                }
                            }
                        }
                        ctypeKpi(datas);
                    }, "json");
                } else if (cmoduleType == 11) {
                    if (data[11].hasOwnProperty("tableHaving")) {
                        if (data[11].tableHaving.tableHaving == "1") {
                            $("#clistForm").val("1");
                        }
                    }
                    if (data[11].hasOwnProperty("warningHaving")) {
                        if (data[11].warningHaving.warningHaving == "1") {
                            $("#calarmForm").val("1");
                        }
                    }
                    checked(cmoduleType);
                    $.post("/plot/getPlotByModuleId.do", {
                        moduleId: 11
                    }, function(datas) {
                        for (var i = 0; i < kpiNames.length; i++) {
                            if (kpiNames[i].moduleId == cmoduleType) {
                                for (var j = 0; j < datas.length; j++) {
                                    if (datas[j].id == kpiNames[i].plotId) {
                                        datas[j].checked = 1;
                                    }
                                }
                            }
                        }
                        ctypeKpi(datas);
                    }, "json");
                } else if (cmoduleType == 12) {
                    if (data[12].hasOwnProperty("tableHaving")) {
                        if (data[12].tableHaving.tableHaving == "1") {
                            $("#clistForm").val("1");
                        }
                    }
                    if (data[12].hasOwnProperty("warningHaving")) {
                        if (data[12].warningHaving.warningHaving == "1") {
                            $("#calarmForm").val("1");
                        }
                    }
                    checked(cmoduleType);
                    $.post("/plot/getPlotByModuleId.do", {
                        moduleId: 12
                    }, function(datas) {
                        for (var i = 0; i < kpiNames.length; i++) {
                            if (kpiNames[i].moduleId == cmoduleType) {
                                for (var j = 0; j < datas.length; j++) {
                                    if (datas[j].id == kpiNames[i].plotId) {
                                        datas[j].checked = 1;
                                    }
                                }
                            }
                        }
                        ctypeKpi(datas);
                    }, "json");
                } else {
                    $("#clistForm").attr("checked", false);
                    $("#calarmForm").attr("checked", false);
                    $.post("/plot/getPlotByModuleId.do", {
                        moduleId: cmoduleType
                    }, function(datas) {
                        ctypeKpi(datas);
                    }, "json");
                }
            }
        });
    });

    //接收列表选中的值
    var liststr = null;
    //接收告警选中的值
    var alarmstr = null;
    //列表选中值为2  未选中值为1
    $("#clistForm").click(function() {
        if ($(this).is(":checked") == true) {
            $("#clistForm").val("1");
            liststr = $("#cmoduleType").val() + ":1";
            listwarn.push(liststr);
        } else {
            for (var i = 0; i < listwarn.length; i++) {
                liststr = listwarn[i].split(":");
                if ($("#cmoduleType").val() == liststr[0] && liststr[1] == $("#clistForm").val()) {
                    listwarn.splice(i, 1);
                }
            }
        }
    });
    //告警选中值为2  未选中值为1
    $("#calarmForm").click(function() {
        if ($(this).is(":checked") == true) {
            $("#calarmForm").val("1");
            alarmstr = $("#cmoduleType").val() + ":1";
            alarm.push(alarmstr);
        } else {
            for (var i = 0; i < alarm.length; i++) {
                alarmstr = alarm[i].split(":");
                if ($("#cmoduleType").val() == alarmstr[0] && alarmstr[1] == $("#calarmForm").val()) {
                    alarm.splice(i, 1);
                }
            }
        }
    });

    //确定按钮
    $("#fromDeleteSure").off().click(function() {
        var _this = $(this).button("loading");
        var paramFrom = {};
        var description = $("#carchivedFrom").val();
        var nameFrom = $("#cnameFrom").val();
        var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im, // 名称特殊字符
            regCn = /[·！#￥（）：；“”‘、，|《。》？、【】[\]]/im;

        var regEns = /[`~!@#$%^&*()_+<>?:"{}\/;'[\]]/im, //模板说明特殊字符验证
            regCns = /[·！#￥（——）：；“”‘、|《》？、【】[\]]/im;

        //名称特殊字符验证
        if (regEn.test(nameFrom) || regCn.test(nameFrom)) {
            jeBox.alert('名称不能包含特殊字符，请重新输入');
            _this.button("reset");
            return false;
        }
        //名称长度验证
        if (nameFrom.length > 20 || nameFrom == "") {
            jeBox.alert('名称不能超过20个字符且不能为空');
            _this.button("reset");
            return false;
        }
        //名称重复验证
        if (isName(changeId, nameFrom)) {
            jeBox.alert('修改失败，名称重复');
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
            return {
                "result": false
            };
        }
        var group = [];
        var groups = [];
        var listobj = null;
        var alarmobj = null;
        paramFrom = {
            id: changeId,
            name: nameFrom, // 名称
            description: description //模板说明
        };
        //kpi
        for (var i = 0; i < kpiNames.length; i++) {
            var kpilist = kpiNames[i];
            groups.push(kpilist);
        }
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

        for (var s = 0; s < groups.length; s++) {
            for (var a in groups[s]) {
                paramFrom['group[' + s + '].' + a] = groups[s][a];
            }
        }
        if (groups.length == 0) {
            jeBox.alert('至少选择一项列表或者告警或者kpi');
            _this.button("reset");
            return;
        }

        //请求
        $.ajax({
            url: "/ReportModalController/updateModal.do",
            type: "post",
            data: paramFrom,
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    _this.button("reset");
                    $.ptcsBSTabRefresh("fromSet");
                    $(".formDeleteContent").hide();
                } else if (data.result == false) {
                    jeBox.alert(data.msg);
                    _this.button("reset");
                    return false;
                }
            }
        });
    });

}

//获取表格所有名称判断是否命名重复

var namesAll = [];
var busName = {};

function cSelectAllName() {
    $.ajax({
        url: "/ReportModalController/allModal.do",
        type: "post",
        dataType: "json",
        success: function(data) {
            for (var a = 0; a < data.length; a++) {
                busName = {
                    id: data[a].id,
                    name: data[a].name
                };
                namesAll.push(busName);
            }
        }
    });
}
/**
 * 判断表格命名是否重复
 * @param {string} appId 表格行id <br>
 *        {} name 表格内所有名称 <br>
 * 
 */
function isName(appId, name) {
    for (var i = 0; i < namesAll.length; i++) {
        if (namesAll[i].id == appId && namesAll[i].name == name) {
            return false;
        } else if (namesAll[i].id != appId && namesAll[i].name == name) {
            return true;
        }
    }
}


//模块类型
function cmodule(moduleId) {
    $("#cmoduleType").empty();
    $.post("/authorizeModuleController/getAuthorizeAppModule.do", null, function(data) {
        addOptions("cmoduleType", data, moduleId);
    }, "json");
}

var changeId = null,
    cplotId = null;

/**
 * 所有kei分类
 * @param {} data 所有kpi数据 <br>
 * 
 */
function ctypeKpi(data) {
    var str, classId, trs;
    //kpiNames = [];
    var groupName = []; //标题数组  ["告警类","时间类","流量类","会话类","数据包类","其他"]
    $(".list-content-c #table-listChange").empty();
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
                trs = $('<tr class="text-center tr-classc" id="' + classId + '"  style="background: rgba(0,0,0,.55);"><td colspan="3" style="font-size: 14px;color: #fff;">' + item.groupName + '</td></tr>');
                $(".list-content-c #table-listChange").append(trs);
            }
        }
    });
    for (var i = 0; i < data.length; i++) {
        if (data[i].id != "321") {
            if (data[i].graph == true) {
                if (data[i].checked == 1) {
                    str = '<td><input checked=checked name="test"  value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
                } else {
                    str = '<td><input  name="test"  value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
                }
                trs = $('<tr><td>' + data[i].name + '</td>' + str + '</tr>');
                switch (data[i].groupName) {
                    case "时间类":
                        if ($(".tr-classc").eq($(".tr-classc").index($("#timeId")) + 1).length) {
                            $(".tr-classc").eq($(".tr-classc").index($("#timeId")) + 1).before(trs);
                        } else {
                            $(".list-content-c #table-listChange").append(trs);
                        }
                        break;
                    case "流量类":
                        if ($(".tr-classc").eq($(".tr-classc").index($("#flowId")) + 1).length) {
                            $(".tr-classc").eq($(".tr-classc").index($("#flowId")) + 1).before(trs);
                        } else {
                            $(".list-content-c #table-listChange").append(trs);
                        }
                        break;
                    case "会话类":
                        if ($(".tr-classc").eq($(".tr-classc").index($("#huihuaId")) + 1).length) {
                            $(".tr-classc").eq($(".tr-classc").index($("#huihuaId")) + 1).before(trs);
                        } else {
                            $(".list-content-c #table-listChange").append(trs);
                        }
                        break;
                    case "数据包类":
                        if ($(".tr-classc").eq($(".tr-classc").index($("#dataId")) + 1).length) {
                            $(".tr-classc").eq($(".tr-classc").index($("#dataId")) + 1).before(trs);
                        } else {
                            $(".list-content-c #table-listChange").append(trs);
                        }
                        break;
                    case "其他":
                        if ($(".tr-classc").eq($(".tr-classc").index($("#otherId")) + 1).length) {
                            $(".tr-classc").eq($(".tr-classc").index($("#otherId")) + 1).before(trs);
                        } else {
                            $(".list-content-c #table-listChange").append(trs);
                        }
                        break;
                }
            }
        }
    }
    //保存被选中的kpi
    $(".formDeleteContent input[name='test']").click(function() {
        var moduleId = $("#cmoduleType").val(); //模块类型
        var plotId = $(this).val().split(":")[0];
        var plotTypeId = $(this).val().split(":")[1];
        if ($(this).is(":checked") == true) {
            if ($(".list-content-c #table-listChange input[name='test']").is(":checked")) {
                selectId = {
                    'moduleId': moduleId,
                    "plotId": plotId,
                    "plotTypeId": plotTypeId,
                    "type": 1
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
        if ($(".list-content-c #table-listChange input[name='test']:checkbox:checked").length == 0) {
            for (var i = 0; i < kpiNames.length; i++) {
                var plotId = kpiNames[i].plotId;
                if (plotId == $(this).val().split(":")[0]) {
                    kpiNames.splice($(this));
                    kpiNames = $.unique(kpiNames);
                }
            }
            return;
        }

    });
}

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
var addOptions = function(domId, data, moduleId) {
    var option = "";
    for (var i = 0, len = data.length; i < len; i++) {
    	 if (moduleId == data[i].id) {
             option += "<option selected=selected value=" + data[i].id + ">" + data[i].namezh + "</option>";
         } else {
             option += "<option value=" + data[i].id + ">" + data[i].namezh + "</option>";
         }
    }
    $("#" + domId).append(option);
};

/**
 * 选中告警和列表
 * @param {} cmoduleType
 */
function checked(cmoduleType) {
    $("#clistForm").prop("checked", false);
    $("#calarmForm").prop("checked", false);
    for (var i = 0; i < listwarn.length; i++) {
        liststr = listwarn[i].split(":");
        if (cmoduleType == liststr[0] && liststr[1] == "1") {
            $("#clistForm").prop("checked", true);
        }
    }
    for (var i = 0; i < alarm.length; i++) {
        alarmstr = alarm[i].split(":");
        if (cmoduleType == alarmstr[0] && alarmstr[1] == 1) {
            $("#calarmForm").prop("checked", true);

        }
    }
}
/**
 * 默认选中的值
 * @param {} data
 */
function defaultPush(data) {
    kpiNames = [];
    listwarn = [];
    alarm = [];
    if (data[10]) {
        if (data[10].hasOwnProperty("tableHaving")) {
            var liststr = data[10].tableHaving.moduleId + ":" + data[10].tableHaving.tableHaving;
            listwarn.push(liststr);
        }
        if (data[10].hasOwnProperty("warningHaving")) {
            var alarmstr = data[10].warningHaving.moduleId + ":" + data[10].warningHaving.warningHaving;
            alarm.push(alarmstr);
        }
        if (data[10].KPI.length > 0) {
            for (var i = 0; i < data[10].KPI.length; i++) {
                var kpiChecked = {
                    'moduleId': data[10].KPI[i].moduleId,
                    "plotId": data[10].KPI[i].plotId,
                    "plotTypeId": data[10].KPI[i].plotTypeId,
                    "type": 1
                };
                kpiNames.push(kpiChecked);
            }
        }
    }
    if (data[11]) {
        if (data[11].hasOwnProperty("tableHaving")) {
            var liststr = data[11].tableHaving.moduleId + ":" + data[11].tableHaving.tableHaving;
            listwarn.push(liststr);
        }
        if (data[11].hasOwnProperty("warningHaving")) {
            var alarmstr = data[11].warningHaving.moduleId + ":" + data[11].warningHaving.warningHaving;
            alarm.push(alarmstr);
        }
        if (data[11].KPI.length > 0) {
            for (var i = 0; i < data[11].KPI.length; i++) {
                var kpiChecked = {
                    'moduleId': data[11].KPI[i].moduleId,
                    "plotId": data[11].KPI[i].plotId,
                    "plotTypeId": data[11].KPI[i].plotTypeId,
                    "type": 1
                };
                kpiNames.push(kpiChecked);
            }
        }
    }
    if (data[12]) {
        if (data[12].hasOwnProperty("tableHaving")) {
            var liststr = data[12].tableHaving.moduleId + ":" + data[12].tableHaving.tableHaving;
            listwarn.push(liststr);
        }
        if (data[12].hasOwnProperty("warningHaving")) {
            var alarmstr = data[12].warningHaving.moduleId + ":" + data[12].warningHaving.warningHaving;
            alarm.push(alarmstr);
        }
        if (data[12].KPI.length > 0) {
            for (var i = 0; i < data[12].KPI.length; i++) {
                var kpiChecked = {
                    'moduleId': data[12].KPI[i].moduleId,
                    "plotId": data[12].KPI[i].plotId,
                    "plotTypeId": data[12].KPI[i].plotTypeId,
                    "type": 1
                };
                kpiNames.push(kpiChecked);
            }
        }
    }

}
//取消按钮
$("#fromDeletecancel").click(function() {
    $(".formDeleteContent").hide();
});