/**
 * 用户管理
 */
$(function() {
    var key = 'GmxeaZP0Q5';
    var selectRow = null;
    // 固定表头
    var columns = [{
        field: "id",
        title: "编号",
        sortable: true
    }, {
        field: "userName",
        title: "用户名",
        sortable: true
    }, {
        field: "realName",
        title: "实际姓名",
        sortable: true
    }, {
        field: "email",
        title: "邮箱",
        sortable: true
    }, {
        field: "telephone",
        title: "电话",
        sortable: true
    }, {
        field: "roleName",
        title: "角色",
        sortable: true
    }];
    $.ptcsBSTable("userTable", "/user/getUserList.do", null, {
        columns: columns,
        ipm_title: "帐号管理",
        ipm_shrink: true,
        ipm_column_save: false,
        search: false,
        showColumns: false,
        rowStyle: function(row, i) {
            var cla = {};
            if (row.id == 1) {
                cla.classes = "custom-row-style";
                selectRow = row;
            }
            return cla;
        },
        onClickRow: function(row, tr) {
            $("#userTable > tbody > .custom-row-style").removeClass();
            $(tr).addClass("custom-row-style");
            selectRow = row;
        },
        ipm_toolbar: [{
            name: "新增",
            type: "plus",
            call: function(e) {
                window.operateEvents['click .add']()
            }
        }, {
            name: "修改",
            type: "edit",
            call: function(e) {
                window.operateEvents['click .edit'](e, null, selectRow)
            }
        }, {
            name: "修改密码",
            type: "lock",
            call: function(e) {
                window.operateEvents['click .changepsws'](e, null, selectRow)
            }
        }, {
            name: "删除",
            type: "remove",
            call: function(e) {
                window.operateEvents['click .remove'](e, null, selectRow)
            }
        }]
    });
    //以下为增删改查部分
    var uprealname = null,
        upname = null,
        uptel = null,
        upemail = null;
    var addname = null,
        addpsw = null,
        addsurepsw = null,
        addrealname = null,
        addemail = null,
        addtel = null,
        addrole = null;
    var uprole = null;
    var ids = [];
    var idpsw = [];
    var field = ["userName", "password", "confirmPsw", "realName", "email", "telephone", "roleName"],
        titleText = ["用户名", "密码", "确认密码", "实际姓名", "Email", "电话", "角色"];
    var fields = ["oldpsw", "newpsw", "surepsw"],
        nameVal = ["旧密码", "新密码", "确认密码"];
    window.operateEvents = {
        /* -----------------添加------------- */
        'click .add': function() {
            $("#userLoad").hide();
            var strSel = [];

            function testadd() {
                $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
                var length = titleText.length;
                for (var i = 0; i < length; i++) {
                    switch (titleText[i]) {
                        case "用户名":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="text" id="abaa" style="visibility: hidden;position: absolute;height: 0;" /><input type="text" class="form-control input-sm m-t-15 addtableRowInput addname" value="" autocomplete="nope">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            break;
                        case "密码":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="password" id="abac" style="visibility: hidden;position: absolute;height: 0;" /><input type="password" class="form-control input-sm m-t-15 addtableRowInput addpsw" value="" autocomplete="nope">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            break;
                        case "确认密码":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="password" class="form-control input-sm m-t-15 addtableRowInput addsurepsw">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            break;
                        case "实际姓名":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="text" class="form-control input-sm m-t-15 addtableRowInput addrealname">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            break;
                        case "Email":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="email" class="form-control input-sm m-t-15 addtableRowInput addemail">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                .append(formGroup);
                            break;
                        case "电话":
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="tel" class="form-control input-sm m-t-15 addtableRowInput addtel">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                .append(formGroup);
                            break;
                        case "角色":
                            $.ajax({
                                url: "/userRole/getUserRoleList.do",
                                type: "post",
                                dataType: "json",
                                success: function(data) {
                                    var select = $('<select  class="form-control m-t-15" id="role-select"></select>');
                                    if (strSel.length != "") {
                                        select.append('<option value="">' + strSel + '</option>');
                                    } else {
                                        select.append('<option value="">--请选择--</option>');
                                    }
                                    for (var i = 0; i < data.length; i++) {
                                        if (strSel == data[i].name) {

                                        } else {
                                            select.append('<option value="" id="' + data[i].id + '">' + data[i].name + '</option>');
                                        }
                                    }
                                    var formGroup = $('<div class="form-group"></div>').append($('<div class="col-md-9"></div>').append(select));
                                    var label = $('<label class="col-md-2 control-label">角色:</label>');
                                    var sel = null;
                                    $(formGroup).prepend(label);
                                    $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                    $("#role-select").change(function() {
                                        if ($("#role-select option:selected").text() == "普通用户") {
                                            sel = $("#role-select option:selected").text();
                                            $("#btn-addsave").hide();
                                            $("#btn-addnext").show();
                                            $("#btn-addnext").off("click").on("click", function() {
                                                $("#userLoad").show();
                                                var modelHeight = $("#addtableRow-modal .modal-content").outerHeight();
                                                var modelWidth = $("#addtableRow-modal .modal-content").outerWidth();
                                                $("#userLoad").css({
                                                    "height": modelHeight
                                                });
                                                $("#userLoad").css({
                                                    "width": modelWidth
                                                });
                                                addname = $(".addname").val();
                                                addpsw = $(".addpsw").val();
                                                addsurepsw = $(".addsurepsw").val();
                                                addrealname = $(".addrealname").val();
                                                addemail = $(".addemail").val();
                                                addtel = $(".addtel").val();
                                                addrole = "2";
                                                addName = [];
                                                strSel = [];
                                                if (addpower()) {
                                                    $("#title-check").find("h4").html("权限配置");
                                                    $("#btn-addclose").hide();
                                                    $("#btn-addback").show();
                                                    $("#btn-addnext").hide();
                                                    $("#btn-addsave").show();
                                                    $("#btn-addback").off("click").on("click", function() {
                                                        if ($(this).text() == "上一步") {
                                                            $("#btn-addsave").hide();
                                                            $("#btn-addnext").show();
                                                            strSel.push(sel);
                                                            testadd();
                                                            $(".addname").val(addname);
                                                            $(".addpsw").val(addpsw);
                                                            $(".addsurepsw").val(addsurepsw);
                                                            $(".addrealname").val(addrealname);
                                                            $(".addemail").val(addemail);
                                                            $(".addtel").val(addtel);
                                                            $("#title-check").find("h4").html("新增");
                                                            $("#btn-addclose").show();
                                                            $("#btn-addback").hide();
                                                        } else {
                                                            $("#addtableRow-modal").modal("hide");
                                                        }
                                                    });
                                                    var addlist = '<div id="add-list" class="check-list"></div>';
                                                    $.ajax({
                                                        url: "/userAuthorize/getJurisModuleList.do?requestType=add",
                                                        type: "post",
                                                        dataType: "json",
                                                        success: function(data) {
                                                            for (var i = 0; i < data.length; i++) {
                                                                //第一层循环
                                                                var addThirds = $('<div class="secondTree"></div>');
                                                                if (data[i].id < 100) {
                                                                    var addpower = $('<div class="firsts checkbox"></div>');
                                                                    var addselect = '<input name="testAdd" class="firstSelect" type="checkbox"  value="' + data[i].id + ':0">' +
                                                                        '<lable id="fistTree' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                    $(addpower).append(addselect);
                                                                    $("#add-list").append(addpower);
                                                                    //第二层循环
                                                                    for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                        if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                            var addpowers = $('<div class="checkbox"></div>');
                                                                            var addselects = '<input class="secondSelect" name="testAdd" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                            $(addpowers).append(addselects);
                                                                            $(addThirds).append(addpowers);
                                                                            $("#fistTree" + data[i].id).append(addThirds);
                                                                        } else {
                                                                            var addpowers = $('<div class="checkbox"></div>');
                                                                            var addselects = '<input class="secondSelect" name="testAdd" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':' + data[i].monitorViewBeanList[j].centerId + ':' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                            $(addpowers).append(addselects);
                                                                            $(addThirds).append(addpowers);
                                                                            $("#fistTree" + data[i].id).append(addThirds);
                                                                        }
                                                                    }
                                                                    //全选部分
                                                                    $(".firstSelect").click(function() {
                                                                        var tr = $(this).is(':checked');
                                                                        if (tr == true) {
                                                                            $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                            $(this).next().children(".secondTree").show();
                                                                            $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                        } else {
                                                                            $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                        }
                                                                    });
                                                                    //反选部分
                                                                    $(".secondSelect").click(function() {
                                                                        if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                            $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                        } else {
                                                                            $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                        }
                                                                    });
                                                                    // 点击出现隐藏
                                                                    $(".firstTree").off("click").on("click", function() {
                                                                        if ($(this).siblings().css("display") == "none") {
                                                                            $(this).siblings().show();
                                                                        } else {
                                                                            $(this).siblings().hide();
                                                                        }
                                                                        if ($(this).find("i").hasClass("ta-plus")) {
                                                                            $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        } else {
                                                                            $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                        }
                                                                    });
                                                                } else if (data[i].id == 107) {
                                                                    var addpower = $('<div class="firsts checkbox"></div>');
                                                                    var addselect = '<input name="testAdd" class="firstSelect" type="checkbox"  value="' + data[i].id + ':0">' +
                                                                        '<lable id="fistTree' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                    $(addpower).append(addselect);
                                                                    $("#add-list").append(addpower);
                                                                    //第二层循环
                                                                    for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                        if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                            var addpowers = $('<div class="checkbox"></div>');
                                                                            var addselects = '<input class="secondSelect" name="testAdd" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                            $(addpowers).append(addselects);
                                                                            $(addThirds).append(addpowers);
                                                                            $("#fistTree" + data[i].id).append(addThirds);
                                                                        }
                                                                    }
                                                                    //全选部分
                                                                    $(".firstSelect").click(function() {
                                                                        var tr = $(this).is(':checked');
                                                                        if (tr == true) {
                                                                            $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                            $(this).next().children(".secondTree").show();
                                                                            $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                        } else {
                                                                            $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                        }
                                                                    });
                                                                    //反选部分
                                                                    $(".secondSelect").click(function() {
                                                                        if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                            $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                        } else {
                                                                            $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                        }
                                                                    });
                                                                    // 点击出现隐藏
                                                                    $(".firstTree").off("click").on("click", function() {
                                                                        if ($(this).siblings().css("display") == "none") {
                                                                            $(this).siblings().show();
                                                                        } else {
                                                                            $(this).siblings().hide();
                                                                        }
                                                                        if ($(this).find("i").hasClass("ta-plus")) {
                                                                            $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        } else {
                                                                            $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                        }
                                                                    });


                                                                } else {
                                                                    if (sel == "普通用户") {
                                                                        if (data[i].namezh == "系统设置") {

                                                                        } else {
                                                                            var addpower = $(' <div  class="checkbox"></div>');
                                                                            var addselect = '<label><input name="testAdd" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                            $(addpower).append(addselect);
                                                                            $("#add-list").append(addpower);
                                                                        }
                                                                    } else {
                                                                        var addpower = $(' <div class="checkbox"></div>');
                                                                        var addselect = '<label><input name="testAdd" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                        $(addpower).append(addselect);
                                                                        $("#add-list").append(addpower);
                                                                    }
                                                                }
                                                            }
                                                            $("#userLoad").hide();
                                                        }
                                                    });
                                                    $("#addtableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html(addlist);
                                                }
                                            })
                                        } else {
                                            //系统管理员
                                            $("#btn-addclose").show();
                                            $("#btn-addback").hide();
                                            $("#btn-addnext").hide();
                                            $("#btn-addsave").show();
                                            addrole = "1";
                                        }
                                    });
                                }
                            });
                            break;
                    }
                }
                $("#addtableRow-modal").modal("show");
            }
            testadd();
        },

        /* -----------------修改密码------------- */
        'click .changepsws': function(e, value, row, index) {
            idpsw.push(row.id);
            $("#user-changepsw").modal("show");
            $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
            var val = [];
            for (var j = 0; j < fields.length; j++) {
                val.push(fields[j]);
            }
            var valLen = nameVal.length;
            for (var i = 0; i < valLen; i++) {
                switch (nameVal[i]) {
                    case "旧密码":
                        var formGroup = $('<div class="form-group">' +
                            '<div class="col-md-9">' +
                            '<input type="password" id="aba" style="visibility: hidden;position: absolute;height: 0;" /><input type="password" class="form-control input-sm m-t-15 psw-old" pattern="/^.{6,}$/" value="" autocomplete="nope">' +
                            '</div>' + '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            nameVal[i] + ':</label>');
                        $(formGroup).prepend(label);
                        $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                    case "新密码":
                        var formGroup = $('<div class="form-group">' +
                            '<div class="col-md-9">' +
                            '<input type="password" class="form-control input-sm m-t-15 psw-new" pattern="/^.{6,}$/" value="" autocomplete="new-password">' +
                            '</div>' + '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            nameVal[i] + ':</label>');
                        $(formGroup).prepend(label);
                        $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                    case "确认密码":
                        var formGroup = $('<div class="form-group">' +
                            '<div class="col-md-9">' +
                            '<input type="password" class="form-control input-sm m-t-15 psw-sure" pattern="/^.{6,}$/" value="" autocomplete="new-password">' +
                            '</div>' + '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            nameVal[i] + ':</label>');
                        $(formGroup).prepend(label);
                        $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                }
            }
        },


        /*----------------- 删除部分------------------------- */
        'click .remove': function(e, value, row, index) {
            if (row.id == 1) {
                jeBox.alert("admin帐号禁止删除");
                return;
            }
            $("#Confirm-modal").modal("show");
            $("#btn-ConfirmdelRow").off("click").on("click", function() {
                $.ajax({
                    url: "/user/delUser.do",
                    type: "post",
                    data: {
                        userId: row.id
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.success == "1") {
                            $.ptcsBSTabRefresh("userTable");
                            jeBox.alert("删除成功");
                        } else {
                            jeBox.alert("删除失败");
                        }
                    }
                });
            });
        },

        /*  ---------------------修改内容部分-------------------- */
        'click .edit': function(e, value, index, row) {
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            ids = [];
            ids.push(index.id);
            var strSel = [];

            function test() {
                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html("");
                var InputVal = [];
                for (var j = 0; j < field.length; j++) {
                    InputVal.push(index[field[j]]);
                }
                var length = titleText.length;
                for (var i = 0; i < length; i++) {
                    switch (titleText[i]) {
                        case "用户名":
                            upname = InputVal[i];
                            if (upname == "admin") {
                                var formGroup = $('<div class="form-group">' +
                                    '<div class="col-md-9">' +
                                    '<input type="text" disabled="disabled" class="form-control input-sm m-t-15 change-name"  value="' + InputVal[i] + '">' +
                                    '</div>' + '</div>');
                                var label = $('<label class="col-md-2 control-label">' +
                                    titleText[i] + ':</label>');
                                $(formGroup).prepend(label);
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                    .append(formGroup);
                            } else {
                                var formGroup = $('<div class="form-group">' +
                                    '<div class="col-md-9">' +
                                    '<input type="text" class="form-control input-sm m-t-15 change-name"  value="' + InputVal[i] + '">' +
                                    '</div>' + '</div>');
                                var label = $('<label class="col-md-2 control-label">' +
                                    titleText[i] + ':</label>');
                                $(formGroup).prepend(label);
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                    .append(formGroup);
                            }
                            break;
                        case "实际姓名":
                            uprealname = InputVal[i];
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="text" class="form-control input-sm m-t-15 change-realname"  value="' + InputVal[i] + '">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                .append(formGroup);
                            break;
                        case "电话":
                            uptel = InputVal[i];
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="text" class="form-control input-sm m-t-15 change-tel"  value="' + InputVal[i] + '">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            break;
                        case "角色":
                            var strName = InputVal[0];
                            if (strName == "admin") {
                                var str = InputVal[i];
                                var select = $('<select style="height: 30px;" data-placeholder="请选择" disabled="disabled" class="form-control m-t-15 change-roles" id="change-role"></select>');
                                if (strSel.length > 0) {
                                    select.append('<option value="">' + strSel + '</option>');
                                } else {
                                    select.append('<option value="' + index.roleId + '">' + InputVal[i] + '</option>');
                                }
                                $.ajax({
                                    url: "/userRole/getUserRoleList.do",
                                    type: "post",
                                    dataType: "json",
                                    success: function(data) {
                                        for (var j = 0; j < data.length; j++) {
                                            if (strSel.length > 0) {
                                                if (strSel == data[j].name) {} else {
                                                    select.append('<option value="' + data[j].id + '">' + data[j].name + '</option>');
                                                }
                                            } else {
                                                if (str == data[j].name) {} else {
                                                    select.append('<option value="' + data[j].id + '">' + data[j].name + '</option>');
                                                }
                                            }

                                        }
                                    }
                                });

                                var formGroup = $('<div class="form-group"></div>').append(
                                    $('<div class="col-md-9"></div>').append(select));
                                var label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                                var sel = null;
                                $(formGroup).prepend(label);
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                            } else {
                                var str = InputVal[i];
                                var select = $('<select data-placeholder="请选择" style="height: 30px;" class="form-control m-t-15 change-roles" id="change-role"></select>');
                                if (strSel.length > 0) {
                                    select.append('<option value="">' + strSel + '</option>');
                                } else {
                                    select.append('<option value="' + index.roleId + '">' + InputVal[i] + '</option>');
                                }
                                $.ajax({
                                    url: "/userRole/getUserRoleList.do",
                                    type: "post",
                                    dataType: "json",
                                    success: function(data) {
                                        for (var j = 0; j < data.length; j++) {
                                            if (strSel.length > 0) {
                                                if (strSel == data[j].name) {} else {
                                                    select.append('<option value="' + data[j].id + '">' + data[j].name + '</option>');
                                                }
                                            } else {
                                                if (str == data[j].name) {} else {
                                                    select.append('<option value="' + data[j].id + '">' + data[j].name + '</option>');
                                                }
                                            }

                                        }
                                    }
                                });

                                var formGroup = $('<div class="form-group"></div>').append(
                                    $('<div class="col-md-9"></div>').append(select));
                                var label = $('<label class="col-md-2 control-label">' + titleText[i] + ':</label>');
                                var sel = null;
                                $(formGroup).prepend(label);
                                $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                                if ($("#change-role option:selected").text() != "系统管理员") {
                                    sel = $("#change-role option:selected").text();
                                    $("#btn-changesave").hide();
                                    $("#btn-changeclose").show();
                                    $("#btn-changeback").hide();
                                    $("#btn-changenext").show();
                                    $("#title-change").find("h4").html("修改");
                                    $("#btn-changenext").off("click").on("click", function() {
                                        $("#cuserLoad").show();
                                        var cmodelHeight = $("#changetableRow-modal .modal-content").outerHeight();
                                        var cmodelWidth = $("#changetableRow-modal .modal-content").outerWidth();
                                        $("#cuserLoad").css({
                                            "height": cmodelHeight + 97
                                        });
                                        $("#cuserLoad").css({
                                            "width": cmodelWidth + 97
                                        });
                                        $("#changetableRow-modal .modal-body").css({
                                            "height": "378px"
                                        });
                                        uprole = "2";
                                        upname = $(".change-name").val();
                                        uptel = $(".change-tel").val();
                                        upemail = $(".change-email").val();
                                        uprealname = $(".change-realname").val();
                                        strSel = [];
                                        if (cpower()) {
                                            $("#title-change").find("h4").html("权限配置");
                                            $("#btn-changeclose").hide();
                                            $("#btn-changeback").show();
                                            $("#btn-changenext").hide();
                                            $("#btn-changesave").show();
                                            $("#btn-changeback").off("click").on("click", function() {
                                                $("#changetableRow-modal .modal-body").css({
                                                    "height": "285px"
                                                });
                                                if ($(this).text() == "上一步") {
                                                    $("#btn-changesave").hide();
                                                    $("#btn-changenext").show();
                                                    strSel.push(sel);
                                                    test();
                                                    $("#title-change").find("h4").html("修改");
                                                    $("#btn-changeclose").show();
                                                    $("#btn-changeback").hide();
                                                } else {
                                                    $("#changetableRow-modal").modal("hide");
                                                }
                                            });
                                            var changelist = '<div id="power-list" class="check-list"></div>';
                                            $.ajax({
                                                url: "/userAuthorize/getJurisModuleList.do?requestType=update",
                                                type: "post",
                                                data: {
                                                    userId: index.id
                                                },
                                                dataType: "json",
                                                success: function(data) {
                                                    for (var i = 0; i < data.length; i++) {
                                                        //第一层循环
                                                        var changeThirds = $('<div class="secondTree"></div>');
                                                        if (data[i].id < 100) {
                                                            if (data[i].checked == "1") {
                                                                var changepower = $('<div class="firsts checkbox"></div>');
                                                                var changeselect = '<input name="test" class="firstSelect" checked type="checkbox"  value="' + data[i].id + ':0">' +
                                                                    '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                            } else {
                                                                var changepower = $('<div class="firsts checkbox"></div>');
                                                                var changeselect = '<input name="test" class="firstSelect" type="checkbox"  value="' + data[i].id + ':0">' +
                                                                    '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                            }
                                                            $(changepower).append(changeselect);
                                                            $("#power-list").append(changepower);
                                                            //第二层循环
                                                            for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                    if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    } else {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    }
                                                                } else {
                                                                    if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':' + data[i].monitorViewBeanList[j].centerId + ':' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    } else {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':' + data[i].monitorViewBeanList[j].centerId + ':' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    }
                                                                }
                                                                $(changepowers).append(changeselects);
                                                                $(changeThirds).append(changepowers);
                                                                $("#fistTrees" + data[i].id).append(changeThirds);
                                                            }
                                                            //全选部分
                                                            $(".firstSelect").click(function() {
                                                                var tr = $(this).is(':checked');
                                                                if (tr == true) {
                                                                    $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                    $(this).next().children(".secondTree").show();
                                                                    $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                } else {
                                                                    $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                }
                                                            });
                                                            //反选部分
                                                            $(".secondSelect").click(function() {
                                                                if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                    $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                } else {
                                                                    $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                }
                                                            });
                                                            // 点击出现隐藏
                                                            $(".firstTree").off("click").on("click", function() {
                                                                if ($(this).find("i").hasClass("ta-plus")) {
                                                                    $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                    $(this).siblings().show();
                                                                } else {
                                                                    $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                    $(this).siblings().hide();
                                                                }
                                                            });
                                                        } else if (data[i].id == 107) {
                                                            if (data[i].checked == "1") {
                                                                var changepower = $('<div class="firsts checkbox"></div>');
                                                                var changeselect = '<input name="test" class="firstSelect" checked type="checkbox"  value="' + data[i].id + ':0">' +
                                                                    '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                            } else {
                                                                var changepower = $('<div class="firsts checkbox"></div>');
                                                                var changeselect = '<input name="test" class="firstSelect" type="checkbox"  value="' + data[i].id + ':0">' +
                                                                    '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                            }
                                                            $(changepower).append(changeselect);
                                                            $("#power-list").append(changepower);
                                                            //第二层循环
                                                            for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                    if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    } else {
                                                                        var changepowers = $('<div class="checkbox"></div>');
                                                                        var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                            '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                    }
                                                                }
                                                                $(changepowers).append(changeselects);
                                                                $(changeThirds).append(changepowers);
                                                                $("#fistTrees" + data[i].id).append(changeThirds);
                                                            }
                                                            //全选部分
                                                            $(".firstSelect").click(function() {
                                                                var tr = $(this).is(':checked');
                                                                if (tr == true) {
                                                                    $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                    $(this).next().children(".secondTree").show();
                                                                    $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                } else {
                                                                    $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                }
                                                            });
                                                            //反选部分
                                                            $(".secondSelect").click(function() {
                                                                if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                    $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                } else {
                                                                    $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                }
                                                            });
                                                            // 点击出现隐藏
                                                            $(".firstTree").off("click").on("click", function() {
                                                                if ($(this).find("i").hasClass("ta-plus")) {
                                                                    $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                    $(this).siblings().show();
                                                                } else {
                                                                    $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                    $(this).siblings().hide();
                                                                }
                                                            });


                                                        } else {
                                                            if (sel == "普通用户") {
                                                                if (data[i].namezh == "系统设置") {

                                                                } else {
                                                                    if (data[i].checked == "1") {
                                                                        var changepower = $(' <div  class="checkbox"></div>');
                                                                        var changeselect = '<label><input name="test" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                    } else {
                                                                        var changepower = $(' <div  class="checkbox"></div>');
                                                                        var changeselect = '<label><input name="test" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                    }
                                                                    $(changepower).append(changeselect);
                                                                    $("#power-list").append(changepower);
                                                                }
                                                            } else {
                                                                if (data[i].checked == "1") {
                                                                    var changepower = $(' <div class="checkbox"></div>');
                                                                    var changeselect = '<label><input name="test" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                } else {
                                                                    var changepower = $(' <div class="checkbox"></div>');
                                                                    var changeselect = '<label><input name="test" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                }
                                                                $(changepower).append(changeselect);
                                                                $("#power-list").append(changepower);
                                                            }
                                                        }
                                                    }
                                                    $("#cuserLoad").hide();
                                                }
                                            });
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html(changelist);
                                        }
                                    });
                                } else {
                                    $("#btn-changesave").show();
                                    $("#btn-changenext").hide();
                                    $("#btn-changeclose").show();
                                    $("#btn-changeback").hide();
                                }

                                $("#change-role").change(function() {
                                    if ($("#change-role option:selected").text() != "系统管理员") {
                                        sel = $("#change-role option:selected").text();
                                        $("#btn-changesave").hide();
                                        $("#btn-changenext").show();
                                        $("#btn-changenext").off("click").on("click", function() {
                                            $("#cuserLoad").show();
                                            var cmodelHeight = $("#changetableRow-modal .modal-content").outerHeight();
                                            var cmodelWidth = $("#changetableRow-modal .modal-content").outerWidth();
                                            $("#cuserLoad").css({
                                                "height": cmodelHeight + 97
                                            });
                                            $("#cuserLoad").css({
                                                "width": cmodelWidth + 97
                                            });
                                            $("#changetableRow-modal .modal-body").css({
                                                "height": "378px"
                                            });
                                            strSel = [];
                                            uprole = "2";
                                            upname = $(".change-name").val();
                                            uptel = $(".change-tel").val();
                                            upemail = $(".change-email").val();
                                            uprealname = $(".change-realname").val();
                                            $("#title-change").find("h4").html("权限配置");
                                            $("#btn-changeclose").hide();
                                            $("#btn-changeback").show();
                                            $("#btn-changenext").hide();
                                            $("#btn-changesave").show();
                                            $("#btn-changeback").off("click").on("click", function() {
                                                $("#changetableRow-modal .modal-body").css({
                                                    "height": "285px"
                                                });
                                                if ($(this).text() == "上一步") {
                                                    $("#btn-changesave").hide();
                                                    $("#btn-changenext").show();
                                                    strSel.push(sel);
                                                    test();
                                                    $("#title-change").find("h4").html("修改");
                                                    $("#btn-changeclose").show();
                                                    $("#btn-changeback").hide();
                                                } else {
                                                    $("#changetableRow-modal").modal("hide");
                                                }
                                            });
                                            var changelist = '<div id="power-list" class="check-list"></div>';
                                            $.ajax({
                                                url: "/userAuthorize/getJurisModuleList.do?requestType=update",
                                                type: "post",
                                                data: {
                                                    userId: index.id,
                                                    changeUser: "123"
                                                },
                                                dataType: "json",
                                                success: function(data) {
                                                    var changepower = null;
                                                    var changeselcet = null;
                                                    for (var i = 0; i < data.length; i++) {
                                                        for (var i = 0; i < data.length; i++) {
                                                            //第一层循环
                                                            var changeThirds = $('<div class="secondTree"></div>');
                                                            if (data[i].id < 100) {
                                                                if (data[i].checked == "1") {
                                                                    var changepower = $('<div class="firsts checkbox"></div>');
                                                                    var changeselect = '<input name="test" class="firstSelect" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' +
                                                                        '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                } else {
                                                                    var changepower = $('<div class="firsts checkbox"></div>');
                                                                    var changeselect = '<input name="test" class="firstSelect" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' +
                                                                        '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                }
                                                                $(changepower).append(changeselect);
                                                                $("#power-list").append(changepower);
                                                                //第二层循环
                                                                for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                    if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                        if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        } else {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        }
                                                                        $(changepowers).append(changeselects);
                                                                        $(changeThirds).append(changepowers);
                                                                        $("#fistTrees" + data[i].id).append(changeThirds);
                                                                    } else {
                                                                        if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':' + data[i].monitorViewBeanList[j].centerId + ':' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        } else {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':' + data[i].monitorViewBeanList[j].centerId + ':' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        }
                                                                        $(changepowers).append(changeselects);
                                                                        $(changeThirds).append(changepowers);
                                                                        $("#fistTrees" + data[i].id).append(changeThirds);
                                                                    }

                                                                }
                                                                //全选部分
                                                                $(".firstSelect").click(function() {
                                                                    var tr = $(this).is(':checked');
                                                                    if (tr == true) {
                                                                        $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        $(this).next().children(".secondTree").show();
                                                                        $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                    } else {
                                                                        $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                    }
                                                                });
                                                                //反选部分
                                                                $(".secondSelect").click(function() {
                                                                    if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                        $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                    } else {
                                                                        $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                    }
                                                                });
                                                                // 点击出现隐藏
                                                                $(".firstTree").off("click").on("click", function() {
                                                                    if ($(this).find("i").hasClass("ta-plus")) {
                                                                        $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        $(this).siblings().show();
                                                                    } else {
                                                                        $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                        $(this).siblings().hide();
                                                                    }
                                                                });
                                                            } else if (data[i].id == 107) {
                                                                if (data[i].checked == "1") {
                                                                    var changepower = $('<div class="firsts checkbox"></div>');
                                                                    var changeselect = '<input name="test" class="firstSelect" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' +
                                                                        '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                } else {
                                                                    var changepower = $('<div class="firsts checkbox"></div>');
                                                                    var changeselect = '<input name="test" class="firstSelect" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' +
                                                                        '<lable id="fistTrees' + data[i].id + '" style="margin-left: 10px;" class="cursor">' + data[i].namezh + '<div class="treeIcon firstTree" style="display: inline-block; margin-left: 10px;"><i class="ta-plus"></i></div></lable>';
                                                                }
                                                                $(changepower).append(changeselect);
                                                                $("#power-list").append(changepower);
                                                                //第二层循环
                                                                for (var j = 0; j < data[i].monitorViewBeanList.length; j++) {
                                                                    if (data[i].monitorViewBeanList[j].centerId == undefined) {
                                                                        if (data[i].monitorViewBeanList[j].checked == "1") {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" checked name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        } else {
                                                                            var changepowers = $('<div class="checkbox"></div>');
                                                                            var changeselects = '<input class="secondSelect" name="test" type="checkbox" style="margin-left: 10px;" value="' + data[i].id + ':1:' + data[i].monitorViewBeanList[j].id + '">' +
                                                                                '<lable id="' + data[i].monitorViewBeanList[j].id + '" style="margin-left: 15px;" class="cursor">' + data[i].monitorViewBeanList[j].name + '</lable>'
                                                                        }
                                                                        $(changepowers).append(changeselects);
                                                                        $(changeThirds).append(changepowers);
                                                                        $("#fistTrees" + data[i].id).append(changeThirds);
                                                                    }
                                                                }
                                                                //全选部分
                                                                $(".firstSelect").click(function() {
                                                                    var tr = $(this).is(':checked');
                                                                    if (tr == true) {
                                                                        $(this).parent().find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        $(this).next().children(".secondTree").show();
                                                                        $(this).next().find(".secondTree input[type='checkbox']").prop("checked", true);
                                                                    } else {
                                                                        $(this).next().find(".secondTree input[type='checkbox']").prop("checked", false);
                                                                    }
                                                                });
                                                                //反选部分
                                                                $(".secondSelect").click(function() {
                                                                    if ($(this).parent().parent().find("input[type='checkbox']:checked").length > 0) {
                                                                        $(this).parent().parent().parent().siblings().prop("checked", true);
                                                                    } else {
                                                                        $(this).parent().parent().parent().siblings().prop("checked", false);
                                                                    }
                                                                });
                                                                // 点击出现隐藏
                                                                $(".firstTree").off("click").on("click", function() {
                                                                    if ($(this).find("i").hasClass("ta-plus")) {
                                                                        $(this).find("i").removeClass("ta-plus").addClass("ta-minus");
                                                                        $(this).siblings().show();
                                                                    } else {
                                                                        $(this).find("i").removeClass("ta-minus").addClass("ta-plus");
                                                                        $(this).siblings().hide();
                                                                    }
                                                                });
                                                            } else {
                                                                if (sel == "普通用户") {
                                                                    if (data[i].namezh == "系统设置") {

                                                                    } else {
                                                                        if (data[i].checked == "1") {
                                                                            var changepower = $(' <div  class="checkbox"></div>');
                                                                            var changeselect = '<label><input name="test" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                        } else {
                                                                            var changepower = $(' <div  class="checkbox"></div>');
                                                                            var changeselect = '<label><input name="test" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                        }
                                                                        $(changepower).append(changeselect);
                                                                        $("#power-list").append(changepower);
                                                                    }
                                                                } else {
                                                                    if (data[i].checked == "1") {
                                                                        var changepower = $(' <div class="checkbox"></div>');
                                                                        var changeselect = '<label><input name="test" checked type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                    } else {
                                                                        var changepower = $(' <div class="checkbox"></div>');
                                                                        var changeselect = '<label><input name="test" type="checkbox"  value="' + data[i].id + ':' + data[i].monitorId + '">' + data[i].namezh + '</label>';
                                                                    }
                                                                    $(changepower).append(changeselect);
                                                                    $("#power-list").append(changepower);
                                                                }
                                                            }
                                                        }
                                                    }
                                                    $("#cuserLoad").hide();
                                                }
                                            });
                                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal").html(changelist);
                                        });
                                    } else {
                                        $("#btn-changesave").show();
                                        $("#btn-changenext").hide();
                                        $("#btn-changeclose").show();
                                        $("#btn-changeback").hide();
                                        uprole = $(".change-roles").val();
                                    }
                                });
                            }
                            break;
                        case "Email":
                            upemail = InputVal[i];
                            var formGroup = $('<div class="form-group">' +
                                '<div class="col-md-9">' +
                                '<input type="text" class="form-control input-sm m-t-15 change-email"  value="' + InputVal[i] + '">' +
                                '</div>' + '</div>');
                            var label = $('<label class="col-md-2 control-label">' +
                                titleText[i] + ':</label>');
                            $(formGroup).prepend(label);
                            $("#changetableRow-modal>.modal-dialog>.modal-content>.modal-body>.form-horizontal")
                                .append(formGroup);
                            break;
                    }
                }
                $('#changetableRow-modal').modal('show');
            }
            test();
        }
    };
    /*  修改密码请求 */
    $("#changepswsure").click(function() {
    	var pw = idpsw[0];
		var opsw = $(".psw-old").val();
		var npsw = $(".psw-new").val();
		var spsw = $(".psw-sure").val();
		var regex = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$/;

		if (!regex.test(npsw)) {
			jeBox.alert("输入错误：密码不符合规则，必须包含数字和字母");
			return false;
		}

		if ("" == $.trim(npsw)) {
			jeBox.alert("新密码不能为空");
			return false;
		}

		if ("" == $.trim(spsw)) {
			jeBox.alert("确认密码不能为空");
			return false;
		}

		if (npsw != spsw) {
			jeBox.alert("两次密码不一致，请重新输入");
			return false;
		}

		if (opsw == npsw) {
			jeBox.alert("新密码不能和旧密码相同");
			return false;
		}
		
		var eopsw = encryptByDES(opsw);
		var enpsw = encryptByDES(npsw);
		var espsw = encryptByDES(spsw);
		$.ajax({
			url: "/user/updateUserPassword.do",
			type: "post",
			data: {
				userId: pw,
				oldPsw: eopsw,
				newPsw: enpsw,
				confirmPsw: espsw
			},
			dataType: "json",
			success: function(data) {
				if (data.success == "1") {
					$.ptcsBSTabRefresh("userTable");
					jeBox.alert("修改密码成功");
					$("#changepsw").modal("hide");
				} else if (data.success == "0") {
					jeBox.alert("修改密码失败，请稍后再试");
				} else if (data.success == "3") {
					jeBox.alert("原始密码不正确");
				} else if (data.success == "4") {
					jeBox.alert("新密码两次输入不一致");
				}
			}
		});
    });

    
    /*   关闭修改方法 */
    $("#btn-changeclose").click(function() {
        $("#changetableRow-modal").modal('hide');
        $("#btn-changenext").hide();
        $("#btn-changesave").show();
    });

    /*   修改请求 */
    $("#btn-changesave").click(function() {
        var _this = $(this).button("loading");
        var id = ids[0];
        var chk_value = [];
        var changevalue = "";
        $('input[name="test"]:checked').each(function() {
            chk_value.push($(this).val());
            $.unique(chk_value);
        });

        if (uprole != null && uprole != "1") {
            if (chk_value.length == 0) {
                _this.button("reset");
                jeBox.alert("你还没有选择任何内容");
                return false;
            } else {
                for (var j = 0; j < chk_value.length; j++) {
                    changevalue += chk_value[j] + ",";
                }
            }
        }
        var changemoduleIds = changevalue.substring("", changevalue.length - 1);
        if (uprole != null && uprole != "1") {
            if (cpower()) {
                $.ajax({
                    url: "/user/updateUser.do",
                    type: "post",
                    data: {
                        id: id,
                        userName: upname,
                        realName: uprealname,
                        email: upemail,
                        telephone: uptel,
                        roleId: uprole,
                        moduleIds: changemoduleIds
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.success == "1") {
                            _this.button("reset");
                            $("#changetableRow-modal").modal("hide");
                            $.ptcsBSTabRefresh("userTable");
                            jeBox.alert("修改成功");
                        } else if (data.success == "2") {
                            _this.button("reset");
                            jeBox.alert("用户名已经存在");
                        } else {
                            _this.button("reset");
                            jeBox.alert("修改失败");
                        }
                    }
                });
            }
        } else {
            if (cc()) {
                var cname = $(".change-name").val();
                var crealname = $(".change-realname").val();
                var ctel = $(".change-tel").val();
                var crole = $(".change-roles").val();
                var cemail = $(".change-email").val();
                $.ajax({
                    url: "/user/updateUser.do",
                    type: "post",
                    data: {
                        id: id,
                        userName: cname,
                        realName: crealname,
                        email: cemail,
                        telephone: ctel,
                        roleId: crole,
                        moduleIds: changemoduleIds
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.success == "1") {
                            _this.button("reset");
                            $("#changetableRow-modal").modal("hide");
                            $.ptcsBSTabRefresh("userTable");
                            jeBox.alert("修改成功");
                        } else if (data.success == "2") {
                            _this.button("reset");
                            jeBox.alert("用户名已经存在");
                        } else {
                            _this.button("reset");
                            jeBox.alert("修改失败");
                        }
                    }
                });
            }
        }


    });
    //当角色为普通用户时的验证
    function cpower() {
        if ("" == $.trim(uprealname)) {
            jeBox.alert("真实姓名不能为空");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(upname)) {
            jeBox.alert("用户名不能为空");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(uptel)) {
            jeBox.alert("电话不能为空");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(upemail)) {
            jeBox.alert("email不能为空");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        if (!upemail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            jeBox.alert("邮箱格式不正确，请重新输入");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        if (!(/^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/.test(uptel)) && !(/^1[3|4|5|7|8][0-9]{9}$/.test(uptel))) {
            jeBox.alert("电话号码格式不正确，请重新输入");
            $("#cuserLoad").hide();
            $("#changetableRow-modal .modal-body").css({
                "height": "285px"
            });
            $("#btn-changesave").button("reset");
            return false;
        }
        return true;
    }
    //当角色为修改系统管理员时的验证
    function cc() {
        var cname = $(".change-name").val();
        var ctel = $(".change-tel").val();
        var crole = $(".change-roles").val();
        var cemail = $(".change-email").val();
        var crealname = $(".change-realname").val();
        if ("" == $.trim(crealname)) {
            jeBox.alert("真实姓名不能为空");
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(cname)) {
            jeBox.alert("用户名不能为空");
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(ctel)) {
            jeBox.alert("电话不能为空");
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("" == $.trim(cemail)) {
            jeBox.alert("email不能为空");
            $("#btn-changesave").button("reset");
            return false;
        }
        if ("--请选择--" == $.trim(crole)) {
            jeBox.alert("角色不能为空");
            $("#btn-changesave").button("reset");
            return false;
        }
        if (!cemail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            jeBox.alert("邮箱格式不正确，请重新输入");
            $("#btn-changesave").button("reset");
            return false;
        }
        if (!(/^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/.test(ctel)) && !(/^1[3|4|5|7|8][0-9]{9}$/.test(ctel))) {
            jeBox.alert("电话号码格式不正确，请重新输入");
            $("#btn-changesave").button("reset");
            return false;
        }
        return true;

    }

    //新增用户点击保存
    $("#btn-addsave").click(function() {
        var _this = $(this).button("loading");
        var addVal = addrole;
        var chk_value = [];
        var addvalue = "";
        if (addVal != "1" && addVal != null && addVal == "2" && addVal != "") {
            if (addpower()) {
                var pname = addname;
                var ppsw = encryptByDES(addpsw);
                var psurepsw = encryptByDES(addsurepsw);
                var prealname = addrealname;
                var pemail = addemail;
                var paddtel = addtel;
                $('input[name="testAdd"]:checked').each(function() {
                    chk_value.push($(this).val());
                });
                if (chk_value.length == 0) {
                    _this.button("reset");
                    jeBox.alert("你还没有选择任何内容");
                    return false;
                } else {
                    for (var j = 0; j < chk_value.length; j++) {
                        addvalue += chk_value[j] + ",";
                    }
                }
                var addmoduleIds = addvalue.substring("", addvalue.length - 1);
                $.ajax({
                    url: "/user/addUser.do",
                    type: "post",
                    data: {
                        userName: pname,
                        password: ppsw,
                        confirmPsw: psurepsw,
                        realName: prealname,
                        email: pemail,
                        telephone: paddtel,
                        roleId: addVal,
                        moduleIds: addmoduleIds
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.success == "1") {
                            _this.button("reset");
                            $("#addtableRow-modal").modal("hide");
                            $.ptcsBSTabRefresh("userTable");
                            jeBox.alert("添加成功");
                        } else if (data.success == "2") {
                            _this.button("reset");
                            jeBox.alert("用户名已经存在");
                        } else {
                            _this.button("reset");
                            jeBox.alert("添加失败");
                        }
                    }
                });
            }
        } else {
            if (ck()) {
                var sVal = $("#role-select option:selected")[0].id;
                var sname = $(".addname").val();
                var spsw = encryptByDES($(".addpsw").val());
                var ssurepsw = encryptByDES($(".addsurepsw").val());
                var srealname = $(".addrealname").val();
                var semail = $(".addemail").val();
                var stel = $(".addtel").val();
                $.ajax({
                    url: "/user/addUser.do",
                    type: "post",
                    data: {
                        userName: sname,
                        password: spsw,
                        confirmPsw: ssurepsw,
                        realName: srealname,
                        email: semail,
                        telephone: stel,
                        roleId: sVal,
                        moduleIds: addmoduleIds
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.success == "1") {
                            _this.button("reset");
                            $("#addtableRow-modal").modal("hide");
                            $.ptcsBSTabRefresh("userTable");
                            jeBox.alert("添加成功");
                        } else if (data.success == "2") {
                            _this.button("reset");
                            jeBox.alert("用户名已经存在");
                        } else {
                            _this.button("reset");
                            jeBox.alert("添加失败");
                        }
                    }
                });
            }
        }
    });

    //新增用户取消按钮
    $("#btn-addclose").click(function() {
        $("#addtableRow-modal").modal('hide');
        $("#btn-addnext").hide();
        $("#btn-addsave").show();
    });
    //新增用户角色为普通用户时的验证
    function addpower() {
        var sVal = addrole;
        var sname = addname;
        var spsw = addpsw;
        var ssurepsw = addsurepsw;
        var srealname = addrealname;
        var semail = addemail;
        var stel = addtel;
        var regex = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$/;

        if (!regex.test(spsw)) {
            jeBox.alert("输入错误：密码不符合规则，必须包含数字和字母");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }

        if ("" == $.trim(sname)) {
            jeBox.alert("用户名不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if ("" == $.trim(spsw)) {
            jeBox.alert("密码不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }

        if ("" == $.trim(ssurepsw)) {
            jeBox.alert("确认密码不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }

        if ("" == $.trim(srealname)) {
            jeBox.alert("真实姓名不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if ("" == $.trim(semail)) {
            jeBox.alert("email不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if ("" == $.trim(stel)) {
            jeBox.alert("电话不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if ("--请选择--" == $.trim(sVal)) {
            jeBox.alert("角色不能为空");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if (!semail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            jeBox.alert("邮箱格式不正确，请重新输入");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }

        if (spsw != ssurepsw) {
            jeBox.alert("两次密码不一致 请重新输入");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        if (!(/^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/.test(stel)) && !(/^1[3|4|5|7|8][0-9]{9}$/.test(stel))) {
            jeBox.alert("电话号码格式不正确，请重新输入");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }
        return true;
    }
    //新增用户角色为系统管理员时的验证
    function ck() {
        var sVal = $("#role-select option:selected").text();
        var sname = $(".addname").val();
        var spsw = $(".addpsw").val();
        var ssurepsw = $(".addsurepsw").val();
        var srealname = $(".addrealname").val();
        var semail = $(".addemail").val();
        var stel = $(".addtel").val();
        var regex = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$/;

        if ("" == $.trim(sname)) {
            jeBox.alert("用户名不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }

        if ("" == $.trim(spsw)) {
            jeBox.alert("密码不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }

        if ("" == $.trim(ssurepsw)) {
            jeBox.alert("确认密码不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }

        if (!regex.test(spsw)) {
            jeBox.alert("输入错误：密码不符合规则，必须包含数字和字母");
            $("#btn-addsave").button("reset");
            $("#userLoad").hide();
            return false;
        }

        if ("" == $.trim(srealname)) {
            jeBox.alert("真实姓名不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }

        if ("" == $.trim(semail)) {
            jeBox.alert("email不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }
        if ("" == $.trim(stel)) {
            jeBox.alert("电话不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }

        if ("--请选择--" == $.trim(sVal)) {
            jeBox.alert("角色不能为空");
            $("#btn-addsave").button("reset");
            return false;
        }
        if (!semail.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
            jeBox.alert("邮箱格式不正确，请重新输入");
            $("#btn-addsave").button("reset");
            return false;
        }

        if (spsw != ssurepsw) {
            jeBox.alert("两次密码不一致，请重新输入");
            $("#btn-addsave").button("reset");
            return false;
        }
        if (!(/^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/.test(stel)) && !(/^1[3|4|5|7|8][0-9]{9}$/.test(stel))) {
            jeBox.alert("电话号码格式不正确，请重新输入");
            $("#btn-addsave").button("reset");
            return false;
        }
        return true;
    }
    //解决多次切换角色时下方按钮问题
    $("#addtableRow-modal").on('hide.bs.modal', function() {
        $("#title-check").find("h4").html("添加");
        $("#btn-addsave").show();
        $("#btn-addclose").show();
        $("#btn-addback").hide();
        $("#btn-addnext").hide();
    });

    // DES加密
    function encryptByDES(message) {
        //把私钥转换成16进制的字符串
        var keyHex = CryptoJS.enc.Utf8.parse(key);
        //模式为ECB padding为Pkcs7
        var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        //加密出来是一个16进制的字符串
        return encrypted.ciphertext.toString();

    }
});