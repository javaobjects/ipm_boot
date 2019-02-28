/**
    * 登录弹出框
*/

$(function() {
    var key = 'GmxeaZP0Q5';
    // 点击登录按钮
    $("#button-s").click(function() {
        $(this).button('loading');
        if (check()) {
            var username = $.trim($("#name").val());
            var pwd = $.trim(encryptByDES($("#pwd").val()));
            $.ajax({
                url: "/user/login.do",
                method: "POST",
                data: {
                    userName: username,
                    password: pwd,
                    start: 0
                },
                timeout: 9000000,
                dataType: "json",
                beforeSend: function(XMLHttpRequest) {},
                success: function(data, textStatus, XMLHttpRequest) {
                    switch (data.success) {
                    case "0":
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-psw").text("");
                        $(".login-admin").text("");
                        $(".login-times").text("");
                        $(".login-lose").text("登录失败");
                        $("#button-s").button('reset');
                        break;
                    case "1":
                    	$.ajax({
                            url: "/systemSet/getValidterm.do",
                            method: "POST",
                            data: "",
                            dataType: "text",
                            success: function(data) {
                                if (data != "0") {
                                    var myDate = new Date();
                                    var year = myDate.getFullYear();//年
                                    var month = myDate.getMonth()+1; //月
                                    var date = myDate.getDate(); //日
                                    var time = year +"-"+month+"-"+date;
                                    if(time >= data){
                                    	location.href="/setSq.html";
                                    }else{
                                    	location.href = "/netCockpit.html";
                                    }
                                } else {
                                	location.href="/setSq.html";
                                }
                            }
                        })
                        break;
                    case "2":
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-lose").text("");
                        $(".login-psw").text("");
                        $(".login-times").text("");
                        $(".login-admin").text("用户名错误");
                        $("#name").focus();
                        $("#button-s").button('reset');
                        break;
                    case "3":
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-lose").text("");
                        $(".login-admin").text("");
                        $(".login-times").text("");
                        $(".login-psw").text("密码错误");
                        $("#pwd").focus();
                        $("#button-s").button('reset');
                        break;
                    case "4":
                        jeBox({
                            cell:
                            "jbx",
                            title: "提示信息",
                            content: '当前用户已登录，是否要替换掉该用户上线？',
                            maskLock: true,
                            btnAlign: "center",
                            button: [{
                                name: '确定',
                                callback: function(index, id) {
                                    $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").text("请稍后...");
                                    $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").attr("disabled", true);
                                    $.ajax({
                                        url: "/user/login.do",
                                        method: "POST",
                                        data: {
                                            userName: username,
                                            password: pwd,
                                            start: 1
                                        },
                                        dataType: "json",
                                        beforeSend: function(XMLHttpRequest) {},
                                        success: function(_data, textStatus, XMLHttpRequest) {
                                            if (_data.success == "1") {
                                                $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").text("确定");
                                                $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").attr("disabled", false);
                                                location.href = "/netCockpit.html";
                                            } else {
                                                $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").text("确定");
                                                $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").attr("disabled", false);
                                                $(".login-adair").text("");
                                                $(".login-pwair").text("");
                                                $(".login-psw").text("");
                                                $(".login-admin").text("");
                                                $(".login-times").text("");
                                                $(".login-lose").text("登录失败");
                                                $("#button-s").button('reset');
                                            }
                                            jeBox.close(index);
                                        },
                                        error: function(XMLHttpRequest, textStatus, errorThorwn) {
                                            $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").text("确定");
                                            $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0").attr("disabled", false);
                                            $(".login-adair").text("");
                                            $(".login-pwair").text("");
                                            $(".login-psw").text("");
                                            $(".login-admin").text("");
                                            $(".login-times").text("");
                                            $(".login-lose").text("登录失败");
                                            $("#button-s").button('reset');
                                            console.error(XMLHttpRequest);
                                            console.error(textStatus);
                                            console.error(errorThorwn);
                                        },
                                        complete: function(XMLHttpRequest, textStatus) {}
                                    })
                                }
                            },
                            {
                                name: '取消',
                                callback: function(index) {
                                    jeBox.close(index);
                                }
                            }]
                        });
                        $("#button-s").button('reset');
                        break;
                    case "5":
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-psw").text("");
                        $(".login-admin").text("");
                        $(".login-lose").text("");
                        $(".login-times").text("登录次数已经到达系统上限");
                        $("#button-s").button('reset');
                        break;
                    case "6":
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-psw").text("");
                        $(".login-admin").text("");
                        $(".login-lose").text("");
                        $(".login-times").text("同IP只能登录一个账号");
                        $("#button-s").button('reset');
                        break;
                    default:
                        $(".login-adair").text("");
                        $(".login-pwair").text("");
                        $(".login-psw").text("");
                        $(".login-admin").text("");
                        $(".login-times").text("");
                        $(".login-lose").text("登录失败");
                        $("#button-s").button('reset');
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThorwn) {
                    $(".login-adair").text("");
                    $(".login-pwair").text("");
                    $(".login-psw").text("");
                    $(".login-admin").text("");
                    $(".login-times").text("");
                    $(".login-lose").text("登录失败");
                    $("#button-s").button('reset');
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete: function(XMLHttpRequest, textStatus) {}
            });
        }
    });
    //  回车事件
    $(document).keydown(function(event) {
        if (event.keyCode == 13) {
            $("#button-s").trigger("click");
        }
    });

    // 用户名及密码为空验证
    function check() {
        var username = $("#name").val();
        var pwd = $("#pwd").val();
        if ("" == $.trim(username)) {
            $(".login-pwair").text("");
            $(".login-lose").text("");
            $(".login-psw").text("");
            $(".login-admin").text("");
            $(".login-times").text("");
            $(".login-adair").text("用户名不能为空");
            $("#name").focus();
            $("#button-s").button('reset');
            return false;
        } else if ("" == $.trim(pwd)) {
            $(".login-adair").text("");
            $(".login-lose").text("");
            $(".login-psw").text("");
            $(".login-admin").text("");
            $(".login-times").text("");
            $(".login-pwair").text("密码不能为空");
            $("#pwd").focus();
            $("#button-s").button('reset');
            return false;
        } else {
            return true;
        }
    }
    // logo边线计算-------------------------------------------------------------
    (function() {
        var jquery_div = $(".footer-width");
        var div_width = function() {
            var width = $(window).width() / 2;
            jquery_div.each(function() {
                $(this).css("width", width - 275);
            });
        };
        window.onresize = div_width;
        div_width();
    })();

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