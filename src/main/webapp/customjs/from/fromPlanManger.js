/**
 * 报表计划管理页面
 */
$(function() {
	var selectRow = null,
        selectRowId = null,
        ipmCenterId = null;
    $(".emailManger").hide(); //默认隐藏邮件
    var x = 0; //邮件初始值为0
    var emailStr = [];
    var columns = [{
        field: "ipmCenterName",
        title: "用户名称",
        sortable: true
    }, {
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
    $.ptcsBSTable("fromPlanMange", "/center/getRemoteDownPlan.do", null, {
        columns: columns,
        ipm_title: "报表计划管理",
        ipm_shrink: true,
        pageSize: 10,
        rowStyle: function(row, i) {
            var cla = {};
            if (i == 0) {
                cla.classes = "custom-row-style";
                selectRow = row;
                ipmCenterId = row.ipmCenterId;
                selectRowId = row.id;
            }
            return cla;
        },
        onClickRow: function(row, tr) {
            $("#fromPlanMange > tbody > .custom-row-style").removeClass("custom-row-style");
            $(tr).addClass("custom-row-style");
            selectRow = row;
            ipmCenterId = row.ipmCenterId;
            selectRowId = row.id;
        },
        ipm_toolbar: [{
            name: "新增邮件",
            type: "plus",
            call: function() {
            	if (selectRow == null) {
                    jeBox.alert('请先选中一条数据再添加');
                    return;
                }
                $(".emailManger").show(); //展示添加邮件
                $("#content").animate({scrollTop :$(".emailManger").offset().top+$("#content").scrollTop()-50},800);
                $("#formManage").empty(); //初始化邮件数据
                emailStr = []; //清空保存邮件的数组
                emailload();  //默认加载一条邮件
                x = 0;
            }
        }]
    });

    //邮件默认加载一条
    function emailload(){
    	var table = $('#formManage'); //获取要添加的容器
        //创建div
        var row = '<div class="manageEmail text-left m-ll-12" id=manageEmail' + x + '>' +
            '<label class="navtime lablestep">发件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="sendPerson">' +
            '<label class="saveemail navtime lablestep">收件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="recriver">' +
            '<label class="mailbox navtime lablestep">收件人Email地址</label>' +
            '<input class="datainp inptime formstep" type="text" name="emailPlan">' +
            '<input class="btn-alt delform" type="button" value="删除此行"></div>';
        table.append(row);
        emailStr.push("manageEmail" + x);
    }
   
    //邮件内容部分
    $("#emailManage").click(function() {
        x++;
        var table = $('#formManage'); //获取要添加的容器
        //创建div
        var row = '<div class="manageEmail text-left m-ll-12" id=manageEmail' + x + '>' +
            '<label class="navtime lablestep">发件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="sendPerson">' +
            '<label class="saveemail navtime lablestep">收件人称呼</label>' +
            '<input class="datainp inptime formstep" type="text" name="recriver">' +
            '<label class="mailbox navtime lablestep">收件人Email地址</label>' +
            '<input class="datainp inptime formstep" type="text" name="emailPlan">' +
            '<input class="btn-alt delform" type="button" value="删除此行"></div>';
        table.append(row);
        emailStr.push("manageEmail" + x);
    });
    $("body").on("click", ".delform", function(e) {
        for (var i =  0; i < emailStr.length; i++) {
            if (emailStr[i] == $(this).parent()[0].id) {
                emailStr.splice($.inArray(emailStr[i], emailStr), 1);
            }
        }
        $(this).parent().remove();
    })
    
   /* 删除默认的那条邮件*/
    $(".managedelform").click(function(){
    	$(this).parent().remove();
    });

    /*提交按钮*/
    $("#manageSure").click(function() {
        var _this = $(this).button("loading");
        var paramFrom = {};
        var lists = [];
        var listobj = null;
        
        //判断邮件必须有一条
        if(emailStr.length == "0"){
            jeBox.alert('至少需要添加一条邮件');
            _this.button("reset");
            return;
        }
        //所有邮件
        for (var j = 0; j < emailStr.length; j++) {
            var sender = $("#" + emailStr[j] + " input[name='sendPerson']").val();
            var recriver = $("#" + emailStr[j] + " input[name='recriver']").val();
            var email = $("#" + emailStr[j] + " input[name='emailPlan']").val();

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
            listobj = {
                sender: sender,
                recriver: recriver,
                email: email,
                planId: selectRowId
            };
            lists.push(listobj);
        }
        paramFrom = {
           ipmCenterId: ipmCenterId // centerid
        }
            // 循环所有邮件
        for (var s = 0; s < lists.length; s++) {
            for (var a in lists[s]) {
                paramFrom['list[' + s + '].' + a] = lists[s][a];
            }
        }
        $.ajax({
            url: "/ReportPlanController/addEmail.do",
            type: "post",
            data: paramFrom,
            dataType: "json",
            success: function(data) {
            	if(data.result == true){
            		jeBox.alert('添加成功');
            		$(".emailManger").hide();
            		_this.button("reset");
            	}else {
            		jeBox.alert('添加失败，请稍后再试');
            		_this.button("reset");
            	}
            }
        });
    });
});

//取消按钮
$("#managecancel").click(function(){
	$(".emailManger").hide();
});