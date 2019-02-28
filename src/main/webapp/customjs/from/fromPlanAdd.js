/**
 * 报表计划添加部分
 */
$(function() {
    var x = 0;
    var emailStr = [];
    var namesAll = [];
    var clientAll = []; //客户端所有业务的长度
    var serverAll = []; //服务端所有业务的长度
    $(".plancontent").hide();
    $(".cplancontent").hide();
    var selectRow = null,
        selectRowId = null;

    var columns = [{
        field: "name",
        title: "报表名称",
        sortable: true
    }, {
        field: "modalName",
        title: "模板",
        sortable: true
    }, {
        field: "typeId",
        title: "类型",
        sortable: true,
        formatter: function(v) {
            if (v == 1) {
                return "自定义";
            } else if (v == 2) {
                return "日报";
            } else if (v == 3) {
                return "周报";
            } else if (v == 4) {
                return "月报";
            }

        }
    }, {
        field: "sendTimes",
        title: "发送次数",
        sortable: true
    }, {
        field: "nextSendTime",
        title: "下次发送时间",
        sortable: true,
        formatter: function(v) {
            if (v == 0) {
                return null;
            } else {
                return $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss");
            }
        }
    }, {
        field: "compareToLastOne",
        title: "环比",
        sortable: true,
        formatter: function(v) {
            if (v == 2) {
                return "是";
            } else if (v == 1) {
                return "否";
            }
        }
    }, {
        field: "state",
        title: "状态",
        sortable: true,
        formatter: function(v) {
            if (v == 1) {
                return "开启";
            } else {
                return "关闭";
            }

        }
    }];
    $.ptcsBSTable("fromPlan", "/ReportPlanController/allPlan.do", null, {
        columns: columns,
        ipm_title: "报表计划",
        ipm_shrink: true,
        pageSize: 10,
        rowStyle: function(row, i) {
            var cla = {};
            if (i == 0) {
                cla.classes = "custom-row-style";
                selectRow = row;
                selectRowId = row.id;
                selectName = row.name;
            }
            return cla;
        },
        onClickRow: function(row, tr) {
            $("#fromPlan > tbody > .custom-row-style").removeClass("custom-row-style");
            $(tr).addClass("custom-row-style");
            selectRow = row;
            selectRowId = row.id;
            selectName = row.name;
        },
        ipm_toolbar: [{
            name: "新增",
            type: "plus",
            call: function() {
            	selectAllName(); //添加时先获取现有的报表名称
                $(".plancontent").show();
                $("#content").animate({scrollTop :$(".plancontent").offset().top+$("#content").scrollTop()-50},800);
                $(".plantable").css("margin-bottom","0");
                $(".cplancontent").hide();
                $("#nameFrom").val("");
                $("#archivedFrom").val("1"); //类型初始值
                $(".ppiForm").hide();
                $(".timeformall").show();
                $("#watchpointForm").val("0"); //观察点初始值
                addmoudle(); //模块初始值
                $("#oprationFrom").empty();
                $("#fromPpi").val("1");
                $("#fromPpi").prop("checked", false);
                $('.selectpicker').selectpicker('val', '');
                $('.selectpicker').selectpicker('refresh');
                $("#formtb").empty(); //初始化邮件数据
                emailStr = []; //清空保存邮件的数组
                x = 0;
                $.ajax({
                    url: "/systemSet/readDateTimeSet.do",
                    type: "post",
                    data: "",
                    dataType: "json",
                    success: function(data) {
                        $("#planend").val(data.nowTime);
                        var planends = $.myTime.DateToUnix(data.nowTime)-600;
                        $("#planstart").val($.myTime.UnixToDate(planends));
                    }
                });
            }
        }, {
            name: "修改",
            type: "edit",
            call: function() {
                if (selectRow == null) {
                    jeBox.alert('请先添加模板计划');
                    return;
                }
                changeForm(selectRowId);
            }
        }, {
            name: "删除",
            type: "remove",
            call: function() {
                if (selectRow == null) {
                    jeBox.alert('请先添加报表计划');
                    return;
                }
                $("#formPlanDelete").modal("show");
                $(".plancontent").hide();
                $(".cplancontent").hide();
            }
        }]
    });
    //删除请求
    $("#btn-formPlanDelete").click(function() {
        $.ajax({
            url: "/ReportPlanController/deletePlan.do",
            type: "post",
            data: {
                id: selectRowId,
                name: selectName
            },
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    $.ptcsBSTabRefresh("fromPlan");
                    selectAllName();
                } else {
                    jeBox.alert(data.msg);
                }
            }
        });
    });

    //新增时默认模板
    function addmoudle() {
        $("#moduleType").empty();
        $.post("/ReportModalController/allModal.do", null, function(data) {
            addOptions("moduleType", data);
            clienNo = data[0].clientKpiNum; //默认客户端kpi数量
            serverNo = data[0].serverKpiNum; //默认服务端kpi数量
            oberNo = data[0].watchpointKpiNum; //默认观察点kpi数量
            var module = data[0].modulesOf.split(',');
            if (module.length == 1 && module == "10") {
                $(".oprationFroms").hide();
            } else {
                for (var j = 0; j < module.length; j++) {
                    $(".oprationFroms").show();
                    selectChecked(module[j]);
                }
            }
        }, "json");
    }
    /*模板名称展示*/
    $.post("/ReportModalController/allModal.do", null, function(data) {
        addOptions("moduleType", data);
        var module = data[0].modulesOf.split(',');
        if (module.length == 1 && module == "10") {
            $(".oprationFroms").hide();
        } else {
            $(".oprationFroms").show();
            for (var j = 0; j < module.length; j++) {
                selectChecked(module[j]);
            }
        }

        //模块切换
        $("#moduleType").off().change(function() {
            $("#oprationFrom").empty();
            $("#oprationFrom").append("<option value='0'>全部</option>");
            var moduleText = $("#moduleType option:selected").text();
            for (var i = 0; i < data.length; i++) {
                if (moduleText == data[i].name) {
                    clienNo = data[i].clientKpiNum; //切换时客户端kpi数量
                    serverNo = data[i].serverKpiNum; //切换时服务端kpi数量
                    oberNo = data[i].watchpointKpiNum; //切换时观察点kpi数量
                    var module = data[i].modulesOf.split(',');
                    if (module.length == 1 && module == "10") {
                        $(".oprationFroms").hide();
                    } else {
                        for (var j = 0; j < module.length; j++) {
                            $(".oprationFroms").show();
                            selectChecked(module[j]);
                        }
                    }
                }
            }
        });
    }, "json");

    function selectChecked(module) {
        if (module == "10") {
			
        } else {
            $("#oprationFrom").append("<option value=" + 0 + ">全部</option>");
        }
        switch (module) {
            case "11":
                $.post("/client/getClient.do", null, function(data) {
                    select("oprationFrom", data);
    				 clientAll.length = data.length; //客户端所有业务的长度
                }, "json");
                break;
            case "12":
                $.post("/serverManagement/getAllServerSide.do", null, function(data) {
                    select("oprationFrom", data);
                    serverAll.length = data.length;  //服务端所有业务的长度
                }, "json");
                break;
        }
        $("#oprationFrom option").each(function() {
            var val = $(this).val();
            if ($("#oprationFrom option[value='" + val + "']").length > 1)
                $("#oprationFrom option[value='" + val + "']:gt(0)").remove();
        });
    }
    /*观察点展示*/
    $.post("/watchpointController/getFindAll.do", null, function(data) {
        addOption("watchpointForm", data);
    }, "json");

    //业务默认显示全部
    $(".selectpicker").selectpicker({
        title: '全部'
    });

    $(window).on('load', function() {
        $('.selectpicker').selectpicker('val', "");
        $('.selectpicker').selectpicker('refresh');
    });

    //业务选中全部时其他业务禁用，选中其他时，全部禁用
    $("#oprationFrom").change(function() {
        var oprationFromAll = $("#oprationFrom").val();
        if (oprationFromAll == "0") { //判断是否是全部
            //获取所有下拉的option的值，拼接字符串，以逗号分割
            var vkorg = $("#oprationFrom option").map(function() {
                return $(this).val();
            }).get().join(",");
            //
            var option = vkorg.split(","); //分割字符串，禁用处0意外的
            for (var i = 1; i < option.length; i++) {
                $('#oprationFrom option:eq(' + i + ')').prop('disabled', true);
                $('#oprationFrom').selectpicker('refresh');
            }
        } else if (oprationFromAll == null) { //是否是请选择
            $('#oprationFrom option').prop('disabled', false);
            $('#oprationFrom').selectpicker('refresh');
        } else { // 非全部和非请选择
            $('#oprationFrom option:eq(0)').prop('disabled', true);
            $('#oprationFrom').selectpicker('refresh');
        }
    });

    /**
     * 添加下拉框信息
     * @domId String 下拉框ID
     * @data {} 数据
     */
    var addOption = function(domId, data) {
        $("#" + domId).append("<option value='0'>全部</option>");
        for (var i = 0, len = data.length; i < len; i++) {
            $("#" + domId).append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
        }
        $('.selectpicker').selectpicker('val', '');
        $('.selectpicker').selectpicker('refresh');
    };
    var addOptions = function(domId, data) {
        for (var i = 0, len = data.length; i < len; i++) {
            $("#" + domId).append("<option value=" + data[i].id + ">" + data[i].name + "</option>");
        }
        $('.selectpicker').selectpicker('val', '');
        $('.selectpicker').selectpicker('refresh');
    };
    var select = function(domId, data) {
        for (var i = 0, len = data.length; i < len; i++) {
            $("#" + domId).append("<option value=" + data[i].moduleId + ":" + data[i].id + ">" + data[i].name + "</option>");
        }
        $('.selectpicker').selectpicker('val', '');
        $('.selectpicker').selectpicker('refresh');
    };

    //环比默认隐藏
    $(".ppiForm").hide();
    //类型改变事件
    $("#archivedFrom").change(function() {
        var archivedFroms = $(this).val();
        if (archivedFroms != "1") {
            $(".ppiForm").show();
            $(".timeformall").hide();
        } else {
            $(".ppiForm").hide();
            $(".timeformall").show();
        }
    });

    //环比选中值为2 未选中为1
    $("#fromPpi").click(function() {
        if ($(this).is(":checked") == true) {
            $("#fromPpi").val("2");
        } else {
            $("#alarmForm").val("1");
        }
    });

    //邮件内容部分
    $("#emailform").click(function() {
        x++;
        var table = $('#formtb'); //获取要添加的容器
        //创建div
        var row = '<div class="formEmail text-left m-ll-12" id=formEmail' + x + '>' +
            '<label class="navtime lablestep">发件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="sendPerson">' +
            '<label class="saveemail navtime lablestep">收件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="recriver">' +
            '<label class="mailbox navtime lablestep">收件人Email地址</label>' +
            '<input class="datainp inptime formstep" type="text" name="emailPlan">' +
            '<input class="btn-alt delform" type="button" value="删除此行"></div>';
        table.append(row);
        emailStr.push("formEmail" + x);
    });
    $("body").on("click", ".delform", function(e) {
            for (var i = 0; i < emailStr.length; i++) {
                if (emailStr[i] == $(this).parent()[0].id) {
                    emailStr.splice($.inArray(emailStr[i], emailStr), 1);
                }
            }
            $(this).parent().remove();
        })
        //获取表格所有名称
    function selectAllName() {
        namesAll = [];
        $.ajax({
            url: "/ReportPlanController/allPlan.do",
            type: "post",
            dataType: "json",
            success: function(data) {
                for (var i = 0; i < data.length; i++) {
                    namesAll.push(data[i].name);
                }
            }
        });
    }
    selectAllName();


    //提交按钮事件
    $("#planSure").click(function() {
        var _this = $(this).button("loading");
        var paramFrom = {};
        var listBuss = [];
        var lists = [];
        var listobj = null;
        var listBusobj = null;
        var nameFrom = $("#nameFrom").val(); //名称
        var moduleType = $("#moduleType").val(); // 模块id
        var watchpointId = $("#watchpointForm").val(); //观察点id
        var oprationFrom = $("#oprationFrom").val(); //业务
        var archivedFrom = $("#archivedFrom").val(); //类型
        var fromPpi = $("#fromPpi").val(); //环比
        var planstart = Date.parse(new Date($("#planstart").val())) / 1000; //开始时间
        var planend = Date.parse(new Date($("#planend").val())) / 1000; //结束时间
        var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im, //名称特殊字符验证
            regCn = /[·！#￥（）：；“”‘、，|《。》？、【】[\]]/im;

        //名称特殊字符验证
        if (regEn.test(nameFrom) || regCn.test(nameFrom)) {
            jeBox.alert('名称不能包含特殊字符，请重新输入');
            _this.button("reset");
            return false;
        }
        //验证名称是否重复
        for (c = 0; c < namesAll.length; c++) {
            if (nameFrom == namesAll[c]) {
                jeBox.alert("添加失败，名称重复");
                _this.button("reset");
                return false;
            }
        }

        //名称验证
        if (nameFrom.length > 20 || nameFrom == "") {
            jeBox.alert('名称不能超过20个字符且不能为空');
            _this.button("reset");
            return false;
        }

        //开始时间结束时间验证
        if (planstart > planend) {
            jeBox.alert('开始时间不能大于结束时间');
            _this.button("reset");
            return;
        }

        var oprationNo = []; //存储客户端服务端的长度
        var oprationOber = []; //存储观察点的长度
        //所有业务客户端、服务端
        if (oprationFrom == null || oprationFrom == "0") {
            listBusobj = {
                bussinessId: 0
            };
            listBuss.push(listBusobj);
        } else {
            for (var k = 0; k < oprationFrom.length; k++) {
                var module = oprationFrom[k].split(":")[0];
                oprationNo.push(module);
                var bussinessIds = oprationFrom[k].split(":")[1];
                listBusobj = {
                    modulId: module,
                    bussinessId: bussinessIds
                };
                listBuss.push(listBusobj);
            }
        }

        //验证kpi与业务相乘不超过50的数量，超过时报表加载时间很长
        var _clientres = []; //客户端个数
        var _serverres = []; //服务端个数
        var _oberres = []; //观察点个数
        //观察点所有业务
        if(watchpointId == "0"){ 
        	_oberres.length = $("#watchpointForm option").size()-1;
        }else{
        	_oberres.length = 1;
        }
        
        if($(".oprationFroms").is(":hidden")){   //判断业务模块是否显示
        	clientAll.length = 0;
        	serverAll.length = 0;
        } 
        if (oprationFrom == null || oprationFrom == "0") {
        	busiNumber = clientAll.length * clienNo + serverAll.length * serverNo + oberNo * _oberres.length;
        }else{
        	for (var s = 0; s < oprationNo.length; s++) {
	            if (oprationNo[s] == "11") {
	                _clientres.push(oprationNo[s]);
	            } else {
	                _serverres.push(oprationNo[s]);
	            }
       		 }
       		 busiNumber = _clientres.length * clienNo + _serverres.length * serverNo + oberNo * _oberres.length;
        }
        //验证kpi与业务相乘不超过50的数量，超过时报表加载时间很长
        if (busiNumber > 156) {
            jeBox.alert('您所要生成的文件过大，请减少业务数量或更换模板');
            _this.button("reset");
            return false;
        }
        
        //所有邮件
        for(var i = emailStr.length-1; i >= 0; i--){
        	var sender = $("#" + emailStr[i] + " input[name='sendPerson']").val();
            var recriver = $("#" + emailStr[i] + " input[name='recriver']").val();
            var email = $("#" + emailStr[i] + " input[name='emailPlan']").val();
            
            //邮件非空验证
            if (sender == "" || recriver == "" || email == "") {
                jeBox.alert('发件人、收件人、邮箱都不可为空');
                _this.button("reset");
                return;
            }
            
            //邮箱格式验证
            if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
                jeBox.alert("邮箱格式不正确，请重新输入");
                _this.button("reset");
                return;
            }
            var targetNode = emailStr[i];
            for(var j = 0; j < i; j++){
            	 var emails = $("#" + emailStr[j] + " input[name='emailPlan']").val();
                 if(emails== email){
                	 _this.button("reset");
                	 jeBox.alert("收件人Email地址不能重复");
                	 return;
                 }
            }
            listobj = {
                    sender: sender,
                    recriver: recriver,
                    email: email
            };
            lists.push(listobj);
      }
        
        if (archivedFrom == "1") {
            paramFrom = {
                name: nameFrom, // 名称
                modalId: moduleType, //模板
                watchpointIds: watchpointId,
                typeId: archivedFrom, //类型
                startTime: planstart,
                endTime: planend
            }
        } else {
            paramFrom = {
                name: nameFrom, // 名称
                modalId: moduleType, //模板
                watchpointIds: watchpointId,
                typeId: archivedFrom, //类型
                compareToLastOne: fromPpi //环比
            }
        }
        // 循环所有业务
        for (var s = 0; s < listBuss.length; s++) {
            for (var a in listBuss[s]) {
                paramFrom['listBus[' + s + '].' + a] = listBuss[s][a];
            }
        }
        
       
        // 循环所有邮件
        for (var s = 0; s < lists.length; s++) {
            for (var a in lists[s]) {
                paramFrom['list[' + s + '].' + a] = lists[s][a];
            }
        }
        //请求
        $.ajax({
            url: "/ReportPlanController/savePlan.do",
            type: "post",
            data: paramFrom,
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    if (data.id != undefined) {
                        loadId = data.id;
                        if (paramFrom.typeId == 1) {
                            _this.button("reset");
                            $(".plancontent").hide();
                            $("#formLoads").css("display", "block");
                            var openWin = window.open("/downLoadController/downLoadWordByName.do?name=" + paramFrom.name + "&" + "id=" + loadId);
                            $("#formLoads").css("display", "none");
                            var listen = setInterval(function() {
                                    if (openWin.closed) {
                                        clearInterval(listen);
                                    }
                                },
                                1000);
                        }
                    } else {
                        _this.button("reset");
                        $.ptcsBSTabRefresh("fromPlan");
                        selectAllName();
                        $(".plancontent").hide();
                    }
                } else if (data.result == false) {
                    jeBox.alert(data.msg);
                    _this.button("reset");
                    return false;
                }
            }
        });
    });


    /**
     * 设置时间 秒级时间戳
     * @starttimes number 开始时间
     * @endtimes number 结束时间 
     * 改为系统时间
     */
    $("#planstart").jeDate({
        isinitVal: true,
        ishmsVal: false,
        isClear: false,
        isServerMaxDate: false,
        format: "YYYY-MM-DD hh:mm:ss"
    });
    $("#planend").jeDate({
        isinitVal: true,
        ishmsVal: false,
        isClear: false,
        isServerMaxDate: false,
        format: "YYYY-MM-DD hh:mm:ss"
    });
    $.ajax({
        url: "/systemSet/readDateTimeSet.do",
        type: "post",
        data: "",
        dataType: "json",
        success: function(data) {
            $("#planend").val(data.nowTime);
            var planends = $.myTime.DateToUnix(data.nowTime)-600;
            $("#planstart").val($.myTime.UnixToDate(planends));
        }
    });
});
//  取消按钮事件
$("#plancancel").click(function() {
    $(".plancontent").hide();
    $("#planSure").button('reset');
    $(".plantable").css("margin-bottom","20px");
});