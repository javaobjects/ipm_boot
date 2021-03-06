/**
 * 报表计划修改部分
 * @param {string} changeId 选中行id
 *
 */
var modules = [];
var x = 0; //增加邮件地址
var emailStr = [];
var clientAll = []; //客户端所有业务的长度
var serverAll = []; //服务端所有业务的长度

function changeForm(changeId) {
    $(".plancontent").hide();
    $(".cplancontent").show();
    $(".plantable").css("margin-bottom","0");
    $("#content").animate({scrollTop :$(".cplancontent").offset().top+$("#content").scrollTop()-50},800);
    $("#cmoduleType").empty(); //清空模板
    $("#cwatchpointForm").empty(); //清空观察点
    $("#cformtb").empty(); //清空邮件
    //修改
    $.ajax({
        url: "/ReportPlanController/searchPlanById.do",
        type: "post",
        data: {
            id: changeId
        },
        dataType: "json",
        success: function(data) {
        	cSelectAllName(); //获取所有已添加的业务名称
            cmodalId = data.modalId; //保存模块id
            $("#cnameFrom").val(data.name); //名称默认
            $("#cplanState").val(data.state); //状态默认
            $("#carchivedFrom").val(data.typeId); //状态默认
            template(data); //默认模板
            cwatchpointFrom(data); //观察点默认
            ctypes(data); //类型默认
            busid(data); //默认模块及业务
            cemail(data); //邮件默认
        }
    });

    //默认观察点
    function cwatchpointFrom(data) {
        $.post("/watchpointController/getFindAll.do", null, function(watcdata) {
            addOption("cwatchpointForm", watcdata, data);
        }, "json");
    }

    //默认模板
    function template(data) {
        $.post("/ReportModalController/allModal.do", null, function(tempdata) {
            addOptions("cmoduleType", tempdata, data);
            clienNo = data.clientKpiNum; //默认客户端kpi数量
            serverNo = data.serverKpiNum; //默认服务端kpi数量
            oberNo = data.watchpointKpiNum; //默认观察点kpi数量
        }, "json");

    }

    //类型改变事件
    $("#carchivedFrom").change(function() {
        var archivedFroms = $(this).val();
        if (archivedFroms != "1") {
            $(".cppiForm").show();
            $(".ctimeformall").hide();
        } else {
            $(".cppiForm").hide();
            $(".ctimeformall").show();
        }
    });

    //模板切换
    $("#cmoduleType").change(function() {
        var moduleType = $("#cmoduleType").val();
        if (moduleType == cmodalId) {
            $.post("/ReportPlanController/searchPlanById.do", { id: changeId }, function(data) {
                busid(data);
                clienNo = data.clientKpiNum; //客户端kpi数量
                serverNo = data.serverKpiNum; //服务端kpi数量
                oberNo = data.watchpointKpiNum; //观察点kpi数量
            }, "json");
        } else {
            $.post("/ReportModalController/allModal.do", null, function(data) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == moduleType) {
                        busid(data[i]);
                        clienNo = data[i].clientKpiNum; //客户端kpi数量
                        serverNo = data[i].serverKpiNum; //服务端kpi数量
                        oberNo = data[i].watchpointKpiNum; //观察点kpi数量
                    }
                }
            }, "json");
            $("#coprationFrom option").each(function() {
                var val = $(this).val();
                if ($("#coprationFrom option[value='" + val + "']").length > 1)
                    $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
            });
        }
    });

    //业务选中全部时其他业务禁用，选中其他时，全部禁用
    $("#coprationFrom").change(function() {
        var oprationFromAll = $("#coprationFrom").val();
        if (oprationFromAll == "0") { //判断是否是全部
            //获取所有下拉的option的值，拼接字符串，以逗号分割
            document.getElementById("coprationFrom").options.selectedIndex = 0; //回到初始状态
            $("#coprationFrom").selectpicker('refresh'); //对searchPayState这个下拉框进行重置刷新
            var vkorg = $("#coprationFrom option").map(function() {
                return $(this).val();
            }).get().join(",");
            //
            var option = vkorg.split(","); //分割字符串，禁用处0意外的
            for (var i = 1; i < option.length; i++) {
                $('#coprationFrom option:eq(' + i + ')').prop('disabled', true);
                $('#coprationFrom').selectpicker('refresh');
            }
        } else if (oprationFromAll == null) { //是否是请选择
            $('#coprationFrom option').prop('disabled', false);
            $('#coprationFrom').selectpicker('refresh');
        } else { // 非全部和非请选择
            $('#coprationFrom option:eq(0)').prop('disabled', true);
            $('#coprationFrom').selectpicker('refresh');
        }
    });

    //提交按钮事件
    $("#cplanSure").off().click(function() {
        var _this = $(this).button("loading");
        var paramFrom = {};
        var listBuss = [];
        var lists = [];
        var listobj = null;
        var listBusobj = null;
        var nameFrom = $("#cnameFrom").val(); //名称
        var moduleType = $("#cmoduleType").val(); // 模块id
        var watchpointId = $("#cwatchpointForm").val(); //观察点id
        var oprationFrom = $("#coprationFrom").val(); //业务
        var archivedFrom = $("#carchivedFrom").val(); //类型
        var states = $("#cplanState").val(); //状态
        var planstart = Date.parse(new Date($("#cplanstart").val())) / 1000; //开始时间
        var planend = Date.parse(new Date($("#cplanend").val())) / 1000; //结束时间
        //环比
        var fromPpi = null;
        if ($("#cfromPpi").is(":checked") == true) {
            fromPpi = 2;
        } else {
            fromPpi = 1;
        }
        var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im, //特殊字符
            regCn = /[·！#￥（）：；“”‘、，|《。》？、【】[\]]/im;

        //特殊字符验证
        if (regEn.test(nameFrom) || regCn.test(nameFrom)) {
            jeBox.alert('名称不能包含特殊字符，请重新输入');
            _this.button("reset");
            return false;
        }
        //判断名称是否重复
        if (isName(changeId, nameFrom)) {
            jeBox.alert('修改失败，名称重复');
            _this.button("reset");
            return false;
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
            return {
                "result": false
            };
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
        
        if($(".coprationFroms").is(":hidden")){   //判断业务模块是否显示
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
        	var sender = $("#" + emailStr[i] + " input[name='csendPerson']").val();
            var recriver = $("#" + emailStr[i] + " input[name='crecriver']").val();
            var email = $("#" + emailStr[i] + " input[name='cemailPlan']").val();
            
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
            	 var emails = $("#" + emailStr[i] + " input[name='cemailPlan']").val();
                 if(emails == email){
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
                id: changeId,
                name: nameFrom, // 名称
                modalId: moduleType, //模板
                watchpointIds: watchpointId,
                typeId: archivedFrom, //类型
                state: states,
                startTime: planstart,
                endTime: planend
            }
        } else {
            paramFrom = {
                id: changeId,
                name: nameFrom, // 名称
                modalId: moduleType, //模板
                watchpointIds: watchpointId,
                state: states,
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
            url: "/ReportPlanController/updatePlan.do",
            type: "post",
            data: paramFrom,
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    if (data.id != undefined) {
                        loadId = data.id;
                        if (paramFrom.typeId == 1) {
                            _this.button("reset");
                            $(".cplancontent").hide();
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
                        $(".cplancontent").hide();
                    }
                } else if (data.result == false) {
                    jeBox.alert(data.msg);
                    _this.button("reset");
                    return false;
                }
            }
        });
    });

}

//默认业务模块
function busid(data) {
    //分割模块
    $("#coprationFrom").empty();
    var module = null;
    if (data.moduleIds == undefined) {
        module = data.modulesOf.split(",");
    } else {
        module = data.moduleIds.split(",");
    }
    $("#coprationFrom option").each(function() {
        var val = $(this).val();
        if ($("#coprationFrom option[value='" + val + "']").length > 1)
            $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
    });
    for (var i = 0; i < module.length; i++) {
        if (module[i] == "10" && module.length == "1") {
            $(".coprationFroms").hide();
        } else {
            $(".coprationFroms").show();
            if (module[i] == "11") {
                $.post("/client/getClient.do", null, function(busiDatas) {
                    if (data.listBus == undefined) {
                        //切换模块时，加载业务
                        select("coprationFrom", null, busiDatas);
                    } else {
                        //默认业务
                        for (var j = 0; j < data.listBus.length; j++) {
                            if (data.listBus[j].moduleId == 11) {
                                var cbusiede = data.listBus[j].businessIds.split(",");
                                for (var ii = 0; ii < busiDatas.length; ii++) {
                                    for (var k = 0; k < cbusiede.length; k++) {
                                        if (busiDatas[ii].id == cbusiede[k]) {
                                            busiDatas[ii].checked = 1;
                                        }
                                    }
                                }
                            }
                        }
                        select("coprationFrom", data, busiDatas);
                        clientAll.length = busiDatas.length; //客户端所有业务的长度
                    }
                }, "json");

            } else if (module[i] == "12") {
                $.post("/serverManagement/getAllServerSide.do", null, function(busiDatas) {
                    if (data.listBus == undefined) {
                        //切换模块时，加载业务
                        select("coprationFrom", null, busiDatas);
                    } else {
                        for (var j = 0; j < data.listBus.length; j++) {
                            if (data.listBus[j].moduleId == 12) {
                                var cbusiede = data.listBus[j].businessIds.split(",");
                                for (var ii = 0; ii < busiDatas.length; ii++) {
                                    for (var k = 0; k < cbusiede.length; k++) {
                                        if (busiDatas[ii].id == cbusiede[k]) {
                                            busiDatas[ii].checked = 1;
                                        }
                                    }
                                }
                            }
                        }
                        select("coprationFrom", data, busiDatas);
                        serverAll.length = busiDatas.length;  //服务端所有业务的长度
                    }
                }, "json");
            }
        }
    }
}

//邮件默认
function cemail(data) {
    $("#cformtb").empty(); //清空邮件
    emailStr = [];
    if (data.list != undefined) {
        x = data.list.length;
        //邮件
        for (var k = 0; k < data.list.length; k++) {
            var emailNO = k + 1;
            var table1 = $('#cformtb'); //获取要添加的容器
            //创建div
            var row1 = '<div class="formEmail text-left m-ll-12" id=cformEmail' + emailNO + '>' +
                '<label class="navtime lablestep">发件人称呼</label>' +
                '<input class="datainp inptime formstep" type="text" name="csendPerson" value="' + data.list[k].sender + '">' +
                '<label class="saveemail navtime lablestep">收件人称呼</label>' +
                '<input class="datainp inptime formstep" type="text" name="crecriver"  value="' + data.list[k].recriver + '">' +
                '<label class="mailbox navtime lablestep">收件人Email地址</label>' +
                '<input class="datainp inptime formstep" type="text" name="cemailPlan"  value="' + data.list[k].email + '">' +
                '<input class="btn-alt cdelform" type="button" value="删除此行"></div>';
            table1.append(row1);
            emailStr.push("cformEmail" + emailNO);
        }
    }
}

//邮件内容部分
$("#cemailform").click(function() {
    x++;
    var table1 = $('#cformtb'); //获取要添加的容器
    //创建div
    var row1 = '<div class="formEmail text-left m-ll-12" id=cformEmail' + x + '>' +
        '<label class="navtime lablestep">发件人称呼</label>' +
        '<input class="datainp inptime formstep" type="text" name="csendPerson">' +
        '<label class="saveemail navtime lablestep">收件人称呼</label>' +
        '<input class="datainp inptime formstep" type="text" name="crecriver">' +
        '<label class="mailbox navtime lablestep">收件人Email地址</label>' +
        '<input class="datainp inptime formstep" type="text" name="cemailPlan">' +
        '<input class="btn-alt cdelform" type="button" value="删除此行"></div>';
    table1.append(row1);
    emailStr.push("cformEmail" + x);
});

$("body").on("click", ".cdelform", function(e) {
    for (var i = 0; i < emailStr.length; i++) {
        if (emailStr[i] == $(this).parent()[0].id) {
            emailStr.splice($.inArray(emailStr[i], emailStr), 1);
        }
    }
    $(this).parent().remove();
})

//类型默认
function ctypes(data) {
    if (data.typeId == "1") { //判断类型是否为1，为1则显示开始时间结束时间，否则显示环比
        $(".cppiForm").hide();
        $(".ctimeformall").show();
        $("#cplanstart").val($.myTime.UnixToDate(data.startTime));
        $("#cplanend").val($.myTime.UnixToDate(data.endtime));
    } else {
        $(".cppiForm").show();
        $(".ctimeformall").hide();
        if (data.compareToLastOne == "1") { //判断环比是否选中
            $("#cfromPpi").attr("checked", false);
        } else {
            $("#cfromPpi").prop("checked", true);
        }
    }
}

//获取表格所有名称判断是否命名重复
var namesAll = [];
var busName = {};
function cSelectAllName(){
	namesAll = [];
    busName = {};
	$.ajax({
	    url: "/ReportPlanController/allPlan.do",
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

/**
 * 添加下拉框信息
 * @domId String 下拉框ID
 * @data {} 数据
 */
var addOption = function(domId, watcdata, data) {
    var option = "";
    if (data.watchpointIds == "0") {
        option += "<option selected=selected value=" + 0 + ">全部</option>";
        for (var i = 0; i < watcdata.length; i++) {
            option += "<option  value=" + watcdata[i].id + ">" + watcdata[i].name + "</option>";
        }
    } else {
        option += "<option value=" + 0 + ">全部</option>";
        for (var i = 0; i < watcdata.length; i++) {
            if (watcdata[i].id == parseInt(data.watchpointIds)) {
                option += "<option selected=selected value=" + watcdata[i].id + ">" + watcdata[i].name + "</option>";
            } else {
                option += "<option  value=" + watcdata[i].id + ">" + watcdata[i].name + "</option>";
            }
        }
    }
    $("#" + domId).append(option);
};
//模板下拉
var addOptions = function(domId, tempdata, data) {
    for (var i = 0; i < tempdata.length; i++) {
        if (data.modalId == tempdata[i].id) {
            $("#" + domId).append("<option selected=selected value=" + tempdata[i].id + ">" + tempdata[i].name + "</option>");
        } else {
            $("#" + domId).append("<option value=" + tempdata[i].id + ">" + tempdata[i].name + "</option>");
        }
    }
    $('.selectpicker').selectpicker('val', '');
    $('.selectpicker').selectpicker('refresh');
};
//业务下拉
var select = function(domId, data, busiDatas) {
    if (data == null) {
        $("#" + domId).append("<option value=" + 0 + ">全部</option>");
        for (var i = 0, len = busiDatas.length; i < len; i++) {
            $("#" + domId).append("<option value=" + busiDatas[i].moduleId + ":" + busiDatas[i].id + ">" + busiDatas[i].name + "</option>");
        }
        $("#coprationFrom option").each(function() {
            var val = $(this).val();
            if ($("#coprationFrom option[value='" + val + "']").length > 1)
                $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
        });
    } else {
        if (data.businessIds == 0) {
            $("#" + domId).append("<option selected=selected value=" + 0 + ">全部</option>");
            for (var i = 0, len = busiDatas.length; i < len; i++) {
                $("#" + domId).append("<option disabled value=" + busiDatas[i].moduleId + ":" + busiDatas[i].id + ">" + busiDatas[i].name + "</option>");
            }
            $("#coprationFrom option").each(function() {
                var val = $(this).val();
                if ($("#coprationFrom option[value='" + val + "']").length > 1)
                    $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
            });
        } else {
            $("#" + domId).append("<option disabled value=" + 0 + ">全部</option>");
            for (var i = 0, len = busiDatas.length; i < len; i++) {
                if (busiDatas[i].checked == 1) {
                    $("#" + domId).append("<option selected=selected value=" + busiDatas[i].moduleId + ":" + busiDatas[i].id + ">" + busiDatas[i].name + "</option>");
                } else {
                    $("#" + domId).append("<option value=" + busiDatas[i].moduleId + ":" + busiDatas[i].id + ">" + busiDatas[i].name + "</option>");
                }
            }
            $("#coprationFrom option").each(function() {
                var val = $(this).val();
                if ($("#coprationFrom option[value='" + val + "']").length > 1)
                    $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
            });
        }
    }
    $('.selectpicker').selectpicker('val', '');
    $('.selectpicker').selectpicker('refresh');
    $("#coprationFrom option").each(function() {
        var val = $(this).val();
        if ($("#coprationFrom option[value='" + val + "']").length > 1)
            $("#coprationFrom option[value='" + val + "']:gt(0)").remove();
    });
};

//取消按钮
$("#cplancancel").click(function() {
    $(".cplancontent").hide();
    $(".plantable").css("margin-bottom","20px");
    $("#coprationFrom option").each(function() {
        var getText = $(this).text();
        if ($("#coprationFrom option:contains(" + getText + ")").length > 1) {
            $("#coprationFrom option:contains(" + getText + "):gt(0)").remove();
        }
    });
});

 //实现日期选择联动
var start = {
    format: 'YYYY-MM-DD hh:mm:ss',
    trigger: "focus click",
	isServerMaxDate: false,
    okfun: function(obj){
        endDates();
    }
    
};
var end = {
	isServerMaxDate: false,
   	format: 'YYYY-MM-DD hh:mm:ss',
   	trigger: "focus click",
    okfun: function(obj){}
};
function endDates() {
    end.trigger = false;
    $("#cplanend").jeDate(end);
}
$("#cplanstart").jeDate(start);
$("#cplanend").jeDate(end);
