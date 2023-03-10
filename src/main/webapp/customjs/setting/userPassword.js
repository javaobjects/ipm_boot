/**
 * 用户管理
 */
$(function() {
    var key = 'GmxeaZP0Q5';
    var selectRow = null;
    // 固定表头
    var columns = [{
        field: "id",
        title: "Num",
        sortable: true
    }, {
        field: "userName",
        title: "User",
        sortable: true
    }, {
        field: "email",
        title: "Email",
        sortable: true
    }, {
        field: "telephone",
        title: "Phone",
        sortable: true
    }, {
        field: "roleName",
        title: "Role",
        sortable: true
    }];
    $.ptcsBSTable("userTable", "/user/getUserList.do", null, {
        columns: columns,
        ipm_title: "Account Management",
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
            name: "Change Password",
            type: "lock",
            call: function(e) {
                window.operateEvents['click .changepsws'](e, null, selectRow)
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
        addemail = null,
        addtel = null,
        addrole = null;
    var uprole = null;
    var ids = [];
    var idpsw = [];
    var field = ["userName", "password", "confirmPsw", "email", "telephone", "roleName"],
        titleText = ["User", "Password", "Confirm password", "Email", "Phone", "Role"];
    var fields = ["oldpsw", "newpsw", "surepsw"],
        nameVal = ["Old Password", "New Password", "Confirm password"];
    var query = window.location.search.substring(1);
    if ("" != query) {
      var div=document.getElementById("passwordHint");
      div.style.display='block';
    }
    window.operateEvents = {
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
                    case "Old Password":
                        var formGroup = $('<div class="form-group">' +
                            '<div class="col-md-9">' +
                            '<input type="password" id="aba" style="visibility: hidden;position: absolute;height: 0;" /><input type="password" class="form-control input-sm m-t-15 psw-old" pattern="/^.{6,}$/" value="" autocomplete="nope">' +
                            '</div>' + '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            nameVal[i] + ':</label>');
                        $(formGroup).prepend(label);
                        $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                    case "New Password":
                        var formGroup = $('<div class="form-group">' +
                            '<div class="col-md-9">' +
                            '<input type="password" class="form-control input-sm m-t-15 psw-new" pattern="/^.{6,}$/" value="" autocomplete="new-password">' +
                            '</div>' + '</div>');
                        var label = $('<label class="col-md-2 control-label">' +
                            nameVal[i] + ':</label>');
                        $(formGroup).prepend(label);
                        $("#user-changepsw>.modal-dialog>.modal-content>.modal-body>.form-horizontal").append(formGroup);
                        break;
                    case "Confirm password":
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
        }
    };
    /*  修改密码请求 */
    $("#changepswsure").click(function() {
    	var pw = idpsw[0];
		var opsw = $(".psw-old").val();
		var npsw = $(".psw-new").val();
		var spsw = $(".psw-sure").val();
		var regex = /^..*$/;

		if (!regex.test(npsw)) {
			jeBox.alert("Password does not follow the rules，must contain numbers and letters");
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
			jeBox.alert("The two passwords do not match. Please re-enter them");
			return false;
		}

		if (opsw == npsw) {
			jeBox.alert("The new password cannot be the same as the old password");
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
					jeBox.alert("Password changed successfully");
					$("#changepsw").modal("hide");
				} else if (data.success == "0") {
					jeBox.alert("Failed to modify password，Please try again later");
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
