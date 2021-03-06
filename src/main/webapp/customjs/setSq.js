/**
 * 产品授权
 */
$(function() {
    /* 生成授权信息部分 */
    /* 点击确定按钮 */
    $("#authorize").click(
        function() {
            $("#authorize").attr({
                "disabled": "disabled"
            });
            var userName = $("#nfuserName").val(); // 用户名
            var contacts = $("#nfcontacts").val(); // 联系人
            var telephone = $("#nftel").val(); // 电话
            var email = $("#nfemail").val(); // 邮箱
            var maxFlow = $("#nfmaxFlow").val(); // 最大分析流量
            var reg = /^[0-9]*$/; // 最大分析值数字验证

            /* 用户名到最大分析流量都不能为空判断 */
            if (userName == "" || contacts == "" ||
                telephone == "" || email == "" ||
                maxFlow == "") {
                $(".authorizeSpan").text(
                        "用户名称、联系人、电话、邮箱、最大分析流量不能为空！")
                    .addClass("red").show();
                setTimeout(function() {
                    $(".authorizeSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#authorize").removeAttr("disabled");
                }, 1500);
                return false;
            }

            if (!(/^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})$/
                    .test(telephone)) &&
                !(/^1[3|4|5|7|8][0-9]{9}$/
                    .test(telephone))) {
                $(".authorizeSpan").text("电话格式不正确，请重新输入！")
                    .addClass("red").show();
                setTimeout(function() {
                    $(".authorizeSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#authorize").removeAttr("disabled");
                }, 1500);
                return false;
            }

            if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
                $(".authorizeSpan").text("邮箱格式不正确，请重新输入！")
                    .addClass("red").show();
                setTimeout(function() {
                    $(".authorizeSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#authorize").removeAttr("disabled");
                }, 1500);
                return false;
            }

            if (!reg.test(maxFlow)) {
                $(".authorizeSpan").text(
                    "最大分析流量必须为数字，请重新输入！").addClass(
                    "red").show();
                setTimeout(function() {
                    $(".authorizeSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#authorize").removeAttr("disabled");
                }, 1500);
                return false;
            }

            /* 判断授权模块是否选中 */
            if ($("#manyWatchpoint").is(':checked')) {
                $("#manyWatchpoint").val("1");
                manyWatchpoint = $("#manyWatchpoint").val();
            } else {
                manyWatchpoint = 0;
            }
            if ($("#server").is(':checked')) {
                $("#server").val("1");
                server = $("#server").val();
            } else {
                server = 0;
            }
            if ($("#client").is(':checked')) {
                $("#client").val("1");
                client = $("#client").val();
            } else {
                client = 0;
            }
            if ($("#http").is(':checked')) {
                $("#http").val("1");
                http = $("#http").val();
            } else {
                http = 0;
            }
            if ($("#url").is(':checked')) {
                $("#url").val("1");
                url = $("#url").val();
            } else {
                url = 0;
            }
            if ($("#message").is(':checked')) {
                $("#message").val("1");
                message = $("#message").val();
            } else {
                message = 0;
            }
            if ($("#mysql").is(':checked')) {
                $("#mysql").val("1");
                mysql = $("#mysql").val();
            } else {
                mysql = 0;
            }
            if ($("#oracle").is(':checked')) {
                $("#oracle").val("1");
                oracle = $("#oracle").val();
            } else {
                oracle = 0;
            }
            if ($("#sqlserver").is(':checked')) {
                $("#sqlserver").val("1");
                sqlserver = $("#sqlserver").val();
            } else {
                sqlserver = 0;
            }
            if ($("#topo").is(':checked')) {
                $("#topo").val("1");
                topo = $("#topo").val();
            } else {
                topo = 0;
            }
            if ($("#trafficPair").is(':checked')) {
                $("#trafficPair").val("1");
                trafficPair = $("#trafficPair").val();
            } else {
                trafficPair = 0;
            }
            if ($("#flowStorage").is(':checked')) {
                $("#flowStorage").val("1");
                flowStorage = $("#flowStorage").val();
            } else {
                flowStorage = 0;
            }
            if ($("#map").is(':checked')) {
                $("#map").val("1");
                map = $("#map").val();
            } else {
                map = 0;
            }
            if ($("#ideggers").is(':checked')) {
                $("#ideggers").val("1");
                digger = $("#ideggers").val();
            } else {
                digger = 0;
            }
            /* 请求 */
            $.ajax({
                    url: "/lisence/generationAuthorize.do",
                    type: "post",
                    data: {
                        userName: userName,
                        contacts: contacts,
                        telephone: telephone,
                        email: email,
                        maxFlow: maxFlow,
                        manyWatchpoint: manyWatchpoint,
                        server: server,
                        client: client,
                        http: http,
                        mysql: mysql,
                        oracle: oracle,
                        sqlserver: sqlserver,
                        url: url,
                        message: message,
                        flowStorage: flowStorage,
                        map: map,
                        topo: topo,
                        trafficPair: trafficPair,
                        digger: digger
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.state == true) {
                            $(".authorizeSpan").text(
                                "成功！").addClass(
                                "red").show();
                            setTimeout(function() {
                                $(".authorizeSpan")
                                    .hide();
                            }, 1500);
                            setTimeout(
                                function() {
                                    $("#authorize")
                                        .removeAttr(
                                            "disabled");
                                }, 1500);
                            var openWin = window
                                .open("/lisence/generationEncodeFile.do");
                            var listen = setInterval(
                                function() {
                                    if (openWin.closed) {
                                        clearInterval(listen);
                                    }
                                }, 1000);
                        } else {
                            $(".authorizeSpan").text(
                                    "生成授权文件失败！")
                                .addClass("red")
                                .show();
                            setTimeout(function() {
                                $(".authorizeSpan")
                                    .hide();
                            }, 1500);
                            setTimeout(
                                function() {
                                    $("#authorize")
                                        .removeAttr(
                                            "disabled");
                                }, 1500);
                        }
                    }
                });
        });
    
 // 云端管理设置
    var ips = null;
    $.ajax({
        url: "/sysNetworkSet/xpmIps.do",
        type: "post",
        data: "",
        dataType: "json",
        success: function(data) {
            ips = data.xpm_ips;
        }
    });
    // 上传授权文件
    $("#uploadriza").click(
        function() {
            $("#uploadriza").attr({
                "disabled": "disabled"
            });
            if (typeof FormData == 'undefined') {
                $(".uploadrizaSpan").text(
                        "此浏览器不支持上传，请使用高版本或者其他浏览器！").addClass("red")
                    .show();
                setTimeout(function() {
                    $(".uploadrizaSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#uploadriza").removeAttr("disabled");
                }, 1500);
                return;
            }
            var file = $("#uploadri").prop("files")[0];
            if (file == undefined) {
                $(".uploadrizaSpan").text("请上传授权文件！").addClass(
                    "red").show();
                setTimeout(function() {
                    $(".uploadrizaSpan").hide();
                }, 1500);
                setTimeout(function() {
                    $("#uploadriza").removeAttr("disabled");
                }, 1500);
                return;
            }
            var formData = new FormData();
            formData.append("name", file.name);
            formData.append("file", file);
            $.ajax({
                url: '/lisence/uploadAuthorizeFile.do',
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(data) {
                    if (data.state == true) {
                        $.ajax({
                            url: "/cgi-bin/pm.cgi",
                            type: "get",
                            data: {
                                ips: ips
                            },
                            success: function(data) {
                                data = data.replace(/[\r\n]/g,"");
                                if (data == "Y") {
                                	$(".uploadrizaSpan").text("上传成功！")
	                                    .addClass("red").show();
	                                setTimeout(function() {
	                                    $(".uploadrizaSpan").hide();
	                                }, 1500);
	                                setTimeout(
	                                    function() {
	                                        $("#uploadriza").removeAttr(
	                                            "disabled");
	                                    }, 1500);
                                	location.href='/netCockpit.html';
                                } else {
                                	$(".uploadrizaSpan").text(data).addClass("red").show();
	                                setTimeout(function() {
	                                    $(".uploadrizaSpan").hide();
	                                }, 1500);
	                                setTimeout(
	                                    function() {
	                                        $("#uploadriza").removeAttr(
	                                            "disabled");
	                                    }, 1500);
	                                }
                            }
                        })
                    } else {
                        $(".uploadrizaSpan").text("授权文件格式不正确！")
                            .addClass("red").show();
                        setTimeout(function() {
                            $(".uploadrizaSpan").hide();
                        }, 1500);
                        setTimeout(
                            function() {
                                $("#uploadriza").removeAttr(
                                    "disabled");
                            }, 1500);
                    }
                }
            });
        });
});