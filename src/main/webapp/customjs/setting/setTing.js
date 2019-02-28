/**
 * Created by yanb on 2018/3/22.
 */
;
$(function() {
    var setTing = {
        setHtm1: function() {
            // 系统厂商信息
            $.ajax({
                url: "/systemSet/readProductLicensSet.do",
                method: "POST",
                data: {},
                dataType: "json",
                beforeSend: function(XMLHttpRequest) {},
                success: function(data, textStatus, XMLHttpRequest) {
                    for (var key in data[0]) {
                        switch (key) {
                            case "client":
                                $("#client").append(data[0][key]);
                                break;
                            case "contacts":
                                $("#contacts").append(data[0][key]);
                                break;
                            case "email":
                                $("#email").append(data[0][key]);
                                break;
                            case "flowStorage":
                                $("#flowStorage").append(data[0][key]);
                                break;
                            case "http":
                                $("#http").append(data[0][key]);
                                break;
                            case "manyWatchpoint":
                                $("#manyWatchpoint").append(data[0][key]);
                                break;
                            case "map":
                                $("#map").append(data[0][key]);
                                break;
                            case "maxFlow":
                                $("#maxFlow").append(data[0][key]);
                                break;
                            case "message":
                                $("#message").append(data[0][key]);
                                break;
                            case "mysql":
                                $("#mysql").append(data[0][key]);
                                break;
                            case "oracle":
                                $("#oracle").append(data[0][key]);
                                break;
                            case "productNo":
                                $("#productNo").append(data[0][key]);
                                break;
                            case "server":
                                $("#server").append(data[0][key]);
                                break;
                            case "sqlserver":
                                $("#sqlserver").append(data[0][key]);
                                break;
                            case "telephone":
                                $("#telephone").append(data[0][key]);
                                break;
                            case "topo":
                                $("#topo").append(data[0][key]);
                                break;
                            case "trafficPair":
                                $("#trafficPair").append(data[0][key]);
                                break;
                            case "url":
                                $("#url").append(data[0][key]);
                                break;
                            case "userName":
                                $("#userName").append(data[0][key]);
                                break;
                            case "validterm":
                                $("#validterm").append(data[0][key]);
                                break;
                            case "digger":
                                $("#digger").append(data[0][key]); // 许可证有效期
                                break;
                        }
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThorwn) {},
                complete: function(XMLHttpRequest, textStatus) {}
            });
            // 管理口设置
            $.ajax({
                url: "/sysNetworkSet/netConfig.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#ipAdress").val(data.ipAddress);
                    $("#defaultGateway").val(data.defaultDateway);
                    $("#maskByte").val(data.maskBits);
                }
            });
            // 管理口设置提交按钮
            $("#updateManageNetworkInfoUbuntu")
                .click(
                    function() {
                        $("#updateManageNetworkInfoUbuntu").attr({
                            "disabled": "disabled"
                        });
                        $
                            .ajax({
                                url: "/sysNetworkSet/updateManageNetworkInfoUbuntu.do",
                                type: "post",
                                data: {
                                    "ipAddress": $("#ipAdress")
                                        .val(),
                                    "defaultDateway": $(
                                            "#defaultGateway")
                                        .val(),
                                    "maskBits": $("#maskByte")
                                        .val()
                                },
                                dataType: "json",
                                success: function(data) {
                                    if (data.success == "1") {
                                        $(
                                                ".updateManageNetworkInfoUbuntuSpan")
                                            .text("保存成功,重启生效")
                                            .removeClass("red")
                                            .show();
                                        setTimeout(
                                            function() {
                                                $(
                                                        ".updateManageNetworkInfoUbuntuSpan")
                                                    .hide();
                                            }, 1500);
                                    } else {
                                        $(
                                                ".updateManageNetworkInfoUbuntuSpan")
                                            .text("保存失败，请稍后再试")
                                            .addClass("red")
                                            .show();
                                        setTimeout(
                                            function() {
                                                $(
                                                        ".updateManageNetworkInfoUbuntuSpan")
                                                    .hide();
                                            }, 1500);
                                    }
                                    if (data) {
                                        setTimeout(
                                            function() {
                                                $(
                                                        "#updateManageNetworkInfoUbuntu")
                                                    .removeAttr(
                                                        "disabled");
                                            }, 3000)
                                    }
                                }
                            })
                    });
            // 数据存储设置
            $.ajax({
                url: "/systemSet/readDataStorageSet.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#dataSave-input").val(data.dataSaveDir);
                    $("#histroyfile").val(data.hisDataDir);
                    $("#AnalysisData-input").val(data.anaDataDir);
                    $("#DiskUse-input").val(data.diskUsage);
                    switch (data.fileSize) {
                        // case "10MB":
                        // $("input[name='optionsRadios']:eq(0)").attr("checked",
                        // true);
                        // break;
                        // case "100MB":
                        // $("input[name='optionsRadios']:eq(1)").attr("checked",
                        // true);
                        // break;
                        case "500MB":
                            $("input[name='optionsRadios']:eq(0)").attr("checked",
                                true);
                            break;
                        case "1G":
                            $("input[name='optionsRadios']:eq(1)").attr("checked",
                                true);
                            break;
                        default:
                            $("input[name='optionsRadios']:eq(0)").attr("checked",
                                true);
                            break;
                    }
                }
            });
            // 数据存储设置的提交
            $("#operationDataStorageSet")
                .click(
                    function() {
                        $("#operationDataStorageSet").attr({
                            "disabled": "disabled"
                        });
                        var reg = /^[0-9]*$/;
                        if (reg.test($("#DiskUse-input").val()) &&
                            $("#DiskUse-input").val() != "") {
                            $
                                .ajax({
                                    url: "/systemSet/operationDataStorageSet.do",
                                    type: "post",
                                    data: {
                                        "fileSize": $(
                                                "input[name='optionsRadios']:checked")
                                            .val(),
                                        "diskUsage": $(
                                                "#DiskUse-input")
                                            .val()
                                    },
                                    dataType: "text",
                                    success: function(data) {
                                        if (data == "success") {
                                            $(
                                                    ".operationDataStorageSet-span")
                                                .text("保存成功")
                                                .removeClass(
                                                    "red")
                                                .show();
                                            setTimeout(
                                                function() {
                                                    $(
                                                            ".operationDataStorageSet-span")
                                                        .hide();
                                                }, 1500);
                                        } else {
                                            $(
                                                    ".operationDataStorageSet-span")
                                                .text(
                                                    "保存失败，请稍后再试")
                                                .addClass("red")
                                                .show();
                                            setTimeout(
                                                function() {
                                                    $(
                                                            ".operationDataStorageSet-span")
                                                        .hide();
                                                }, 1500);
                                        }
                                        if (data) {
                                            setTimeout(
                                                function() {
                                                    $(
                                                            "#operationDataStorageSet")
                                                        .removeAttr(
                                                            "disabled");
                                                }, 3000)
                                        }
                                    }
                                })
                        } else {
                            $(".operationDataStorageSet-span").text(
                                    "输入格式不正确，请重新输入").addClass("red")
                                .show();
                            setTimeout(function() {
                                $(".operationDataStorageSet-span")
                                    .hide();
                            }, 1500);
                        }

                    });
            // 数据存储的清空按钮
            $(".clearTargetDir").click(
                function() {
                    var index = $(".clearTargetDir").index($(this));
                    $.ajax({
                        url: "/systemSet/clearTargetDir.do",
                        type: "post",
                        data: {
                            "targetDir": $(this).parent().prev().val()
                        },
                        dataType: "text",
                        success: function(data) {
                            if (data == "success") {
                                $(".clearTargetDirSpan").eq(index).text(
                                    "清空成功").removeClass("red").show();
                                setTimeout(function() {
                                    $(".clearTargetDirSpan").eq(index)
                                        .hide()
                                }, 1500);
                            } else {
                                $(".clearTargetDirSpan").eq(index).text(
                                        "清空失败，请待会再试").addClass("red")
                                    .show();
                                setTimeout(function() {
                                    $(".clearTargetDirSpan").eq(index)
                                        .hide()
                                }, 1500);
                            }
                        }
                    })
                });
            /* 数据包去重 */
            $.ajax({
                url: "/sysNetworkSet/getDataDedupState.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    if (data.state == "1") {
                        $("#onqc").attr("checked", "checked");
                    } else {
                        $("#offqc").attr("checked", "checked");
                    }
                }
            });
            $(".dataDedup").click(
                function() {
                    $.ajax({
                        url: "/sysNetworkSet/onOffDataDedup.do",
                        type: "post",
                        data: {
                            state: $(
                                    'input[name="optionsRadios1"]:checked')
                                .val()
                        },
                        dataType: "json",
                        success: function(data) {
                            if (data.success == "1") {
                                $(".dataDedupSpan").text("保存成功")
                                    .removeClass("red").show();
                                setTimeout(function() {
                                    $(".dataDedupSpan").hide();
                                }, 1500);
                            } else {
                                $(".dataDedupSpan").text("保存失败，请稍后再试")
                                    .removeClass("red").show();
                                setTimeout(function() {
                                    $(".dataDedupSpan").hide();
                                }, 1500);
                            }
                        }
                    });
                });
            // 智能基线展示设置 获取
            $.ajax({
                url: "/sysNetworkSet/getIntelligentBaseline.do",
                type: "post",
                data: {},
                dataType: "json",
                success: function(data) {
                    if (data.state) {
                        $("#alarmJOpen").attr("checked", "checked");
                    } else {
                        $("#alarmJClose").attr("checked", "checked");
                    }
                },
                error: function() {
                    $(".alarmJEnterSpan").addClass("red")
                        .text("获取智能基线失败，请稍后再试");
                }
            });
            // 提交智能基线展示设置
            $(".alarmJEnter")
                .click(
                    function() {
                        var _$this = $(this);
                        if (!_$this.hasClass("notAllowed")) {
                            _$this.addClass("notAllowed");
                            $
                                .ajax({
                                    url: "/sysNetworkSet/setIntelligentBaseline.do ",
                                    type: "post",
                                    data: {
                                        state: Number($(
                                                'input[name="alarmJ"]:checked')
                                            .val())
                                    },
                                    dataType: "json",
                                    success: function(data) {
                                        if (data.success == "1") {
                                            $(".alarmJEnterSpan")
                                                .text("修改成功")
                                                .removeClass(
                                                    "red")
                                                .show();
                                        } else {
                                            $(".alarmJEnterSpan")
                                                .text(
                                                    "修改失败，请稍后再试")
                                                .addClass("red")
                                                .show();
                                        }
                                        setTimeout(
                                            function() {
                                                $(
                                                        ".alarmJEnterSpan")
                                                    .text(
                                                        "");
                                                _$this
                                                    .removeClass("notAllowed");
                                            }, 1500);
                                    },
                                    error: function() {
                                        $(".alarmJEnterSpan").text(
                                                "修改失败，请稍后再试")
                                            .addClass("red")
                                            .show();
                                        _$this
                                            .removeClass("notAllowed");
                                    }
                                })
                        }
                    });
            // 抽样比例设置
            $.ajax({
                url: "/sysNetworkSet/getSamplingRatio.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#samProp").val(data.samplingRatio);
                }
            });
            // 抽样比例确定按钮
            $(".samPropSure").click(
                function() {
                    $(".samPropSure").attr({
                        "disabled": "disabled"
                    });
                    var reg = /^[12]?[0-9]$|^3[012]$/;
                    if (reg.test($("#samProp").val())) {
                        $.ajax({
                            url: "/sysNetworkSet/updSamplingRatio.do",
                            type: "post",
                            data: {
                                "samplingRatio": $("#samProp").val()
                            },
                            dataType: "json",
                            success: function(data) {
                                if (data.success == "1") {
                                    $(".samPropspan").text("保存成功")
                                        .removeClass("red").show();
                                    setTimeout(function() {
                                        $(".samPropspan").hide();
                                    }, 1500)
                                } else {
                                    $(".samPropspan").text("保存失败，请稍后再试")
                                        .addClass("red").show();
                                    setTimeout(function() {
                                        $(".samPropspan").hide();
                                    }, 1500)
                                }
                            }
                        })
                    } else {
                        $(".samPropspan").text("只能输入1-32之间的数字，请重新输入！")
                            .addClass("red").show();
                        setTimeout(function() {
                            $(".samPropspan").hide();
                        }, 1500)
                    }
                    setTimeout(function() {
                        $(".samPropSure").removeAttr("disabled");
                    }, 1500);
                });

            // 内网网段设置
            $.ajax({
                url: "/sysNetworkSet/intranetSegment.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#innerIp").val(data.state);
                }
            });

            // 内网网段确定按钮
            $(".innerIpSure")
                .click(
                    function() {
                        $(".innerIpSure").attr({
                            "disabled": "disabled"
                        });
                        // 网段部分
                        var regex = /^\s*((([0-9A-Fa-f]{1,4}:){7}(([0-9A-Fa-f]{1,4})|:))|(([0-9A-Fa-f]{1,4}:){6}(:|((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})|(:[0-9A-Fa-f]{1,4})))|(([0-9A-Fa-f]{1,4}:){5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){4}(:[0-9A-Fa-f]{1,4}){0,1}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){3}(:[0-9A-Fa-f]{1,4}){0,2}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:){2}(:[0-9A-Fa-f]{1,4}){0,3}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(([0-9A-Fa-f]{1,4}:)(:[0-9A-Fa-f]{1,4}){0,4}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(:(:[0-9A-Fa-f]{1,4}){0,5}((:((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})?)|((:[0-9A-Fa-f]{1,4}){1,2})))|(((25[0-5]|2[0-4]\d|[01]?\d{1,2})(\.(25[0-5]|2[0-4]\d|[01]?\d{1,2})){3})))(%.+)?\s*$/;
                        var displayIp = $("#innerIp").val().split(",");
                        for (var i = 0; i < displayIp.length; i++) {
                            if (displayIp[i].indexOf("/") > -1) {
                                if (regex
                                    .test(displayIp[i].split("/")[0])) {
                                    if (displayIp[i].split(".")[3]
                                        .split("/")[1] != "") {
                                        if (displayIp[i].split(".")[3]
                                            .split("/")[1] <= 32) {
                                            $
                                                .ajax({
                                                    url: "/sysNetworkSet/updIntranetSegment.do",
                                                    type: "post",
                                                    data: {
                                                        ip: $(
                                                                "#innerIp")
                                                            .val()
                                                    },
                                                    dataType: "json",
                                                    success: function(
                                                        data) {
                                                        if (data.success == "1") {
                                                            $(
                                                                    ".innerIpspan")
                                                                .text(
                                                                    "保存成功")
                                                                .removeClass(
                                                                    "red")
                                                                .show();
                                                            setTimeout(
                                                                function() {
                                                                    $(
                                                                            ".innerIpspan")
                                                                        .hide();
                                                                },
                                                                1500);
                                                            setTimeout(
                                                                function() {
                                                                    $(
                                                                            ".innerIpSure")
                                                                        .removeAttr(
                                                                            "disabled");
                                                                },
                                                                1500);
                                                        }
                                                    }
                                                });
                                        } else {
                                            $(".innerIpspan")
                                                .text(
                                                    "网段不正确，请输入小于32位网段！")
                                                .addClass("red")
                                                .show();
                                            setTimeout(function() {
                                                $(".innerIpspan")
                                                    .hide();
                                            }, 1500);
                                            setTimeout(
                                                function() {
                                                    $(
                                                            ".innerIpSure")
                                                        .removeAttr(
                                                            "disabled");
                                                }, 1500);
                                            return false;
                                        }
                                    } else {
                                        $(".innerIpspan").text(
                                                "网段不正确，请重新输入！")
                                            .addClass("red").show();
                                        setTimeout(function() {
                                            $(".innerIpspan").hide();
                                        }, 1500);
                                        setTimeout(
                                            function() {
                                                $(".innerIpSure")
                                                    .removeAttr(
                                                        "disabled");
                                            }, 1500);
                                        return false;
                                    }
                                } else {
                                    $(".innerIpspan").text("不是正确的IP！")
                                        .addClass("red").show();
                                    setTimeout(function() {
                                        $(".innerIpspan").hide();
                                    }, 1500);
                                    setTimeout(function() {
                                        $(".innerIpSure").removeAttr(
                                            "disabled");
                                    }, 1500);
                                    return false;
                                }
                            } else if (displayIp[i].indexOf("-") > -1) {
                                if (regex
                                    .test(displayIp[i].split("-")[0]) &&
                                    regex.test(displayIp[i]
                                        .split("-")[1])) {
                                    if (displayIp[i].split("-")[0] != displayIp[i]
                                        .split("-")[1]) {
                                        $
                                            .ajax({
                                                url: "/sysNetworkSet/updIntranetSegment.do",
                                                type: "post",
                                                data: {
                                                    ip: $(
                                                            "#innerIp")
                                                        .val()
                                                },
                                                dataType: "json",
                                                success: function(
                                                    data) {
                                                    if (data.success == "1") {
                                                        $(
                                                                ".innerIpspan")
                                                            .text(
                                                                "保存成功")
                                                            .removeClass(
                                                                "red")
                                                            .show();
                                                        setTimeout(
                                                            function() {
                                                                $(
                                                                        ".innerIpspan")
                                                                    .hide();
                                                            },
                                                            1500);
                                                        setTimeout(
                                                            function() {
                                                                $(
                                                                        ".innerIpSure")
                                                                    .removeAttr(
                                                                        "disabled");
                                                            },
                                                            1500);
                                                    }
                                                }
                                            });
                                    } else {
                                        $(".innerIpspan").text(
                                            "前后网段不能想同！").addClass(
                                            "red").show();
                                        setTimeout(function() {
                                            $(".innerIpspan").hide();
                                        }, 1500);
                                        setTimeout(
                                            function() {
                                                $(".innerIpSure")
                                                    .removeAttr(
                                                        "disabled");
                                            }, 1500);
                                        return false;
                                    }
                                } else {
                                    $(".innerIpspan").text(
                                        "网段不正确，请重新输入！").addClass(
                                        "red").show();
                                    setTimeout(function() {
                                        $(".innerIpspan").hide();
                                    }, 1500);
                                    setTimeout(function() {
                                        $(".innerIpSure").removeAttr(
                                            "disabled");
                                    }, 1500);
                                    return false;
                                }
                            } else {
                                if (regex.test(displayIp[i])) {
                                    $
                                        .ajax({
                                            url: "/sysNetworkSet/updIntranetSegment.do",
                                            type: "post",
                                            data: {
                                                ip: $("#innerIp")
                                                    .val()
                                            },
                                            dataType: "json",
                                            success: function(data) {
                                                if (data.success == "1") {
                                                    $(
                                                            ".innerIpspan")
                                                        .text(
                                                            "保存成功")
                                                        .removeClass(
                                                            "red")
                                                        .show();
                                                    setTimeout(
                                                        function() {
                                                            $(
                                                                    ".innerIpspan")
                                                                .hide();
                                                        }, 1500);
                                                    setTimeout(
                                                        function() {
                                                            $(
                                                                    ".innerIpSure")
                                                                .removeAttr(
                                                                    "disabled");
                                                        }, 1500);
                                                }
                                            }
                                        });
                                } else {
                                    $(".innerIpspan").text("不是正确的IP！")
                                        .addClass("red").show();
                                    setTimeout(function() {
                                        $(".innerIpspan").hide();
                                    }, 1500);
                                    setTimeout(function() {
                                        $(".innerIpSure").removeAttr(
                                            "disabled");
                                    }, 1500);
                                    return false;
                                }
                            }
                        }
                    });

            $("#SystemTimeIng").jeDate({
                isinitVal: true,
                ishmsVal: false,
                format: "YYYY-MM-DD hh:mm:ss"
            });
            // 当前系统时间
            $.ajax({
                url: "/systemSet/readDateTimeSet.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#SystemTimeIng").val(data.nowTime);
                    $("#asyncFalseServerTime").val(data.sameServer);
                }
            });
            // 系统时间设置确定按钮
            $("#sameServer").click(
                function() {
                    $("#sameServer").attr({
                        "disabled": "disabled"
                    });
                    var reg = /^\s+$/; // 非空验证
                    if ($("#SystemTimeIng").val() != "" &&
                        !reg.test($("#SystemTimeIng").val())) {
                        $.ajax({
                            url: "/systemSet/operateDateTimeSet.do",
                            type: "post",
                            data: {
                                "nowTime": $("#SystemTimeIng").val(),
                                "sameServer": $("#asyncFalseServerTime")
                                    .val()
                            },
                            dataType: "text",
                            success: function(data) {
                                switch (data) {
                                    case "success":
                                        $(".sameServer-span").text("提交成功")
                                            .removeClass("red").show();
                                        setTimeout(function() {
                                            $(".sameServer-span").hide();
                                        }, 1500);
                                        break;
                                    case "fail":
                                        $(".sameServer-span").text("程序异常")
                                            .addClass("red").show();
                                        setTimeout(function() {
                                            $(".sameServer-span").hide();
                                        }, 1500);
                                        break;
                                    case "nonExistent":
                                        $(".sameServer-span").text(
                                            "输入的 时间同步服务器 不存在").addClass(
                                            "red").show();
                                        setTimeout(function() {
                                            $(".sameServer-span").hide();
                                        }, 1500);
                                        break
                                }
                                if (data) {
                                    setTimeout(function() {
                                        $("#sameServer").removeAttr(
                                            "disabled");
                                    }, 3000);
                                }
                            },
                            error: function(er) {
                                console.log(er);
                            }
                        });
                    } else {
                        $(".sameServer-span").text("请输入正确的时间格式").addClass(
                            "red").show();
                        setTimeout(function() {
                            $(".sameServer-span").hide();
                        }, 1500);
                    }
                });
            // 邮件发送设置 获取上次配置
            $.ajax({
                url: "/Email/getEmailCtrl.do",
                type: "post",
                data: {},
                dataType: "json",
                success: function(data) {
                    if (data.result) {
                        $("#emailServer").val(data.emailBean.emailServer);
                        $("#emailPort").val(data.emailBean.emailPort);
                        $("#emailUserName").val(data.emailBean.emailUserName);
                        $("#emailPassword").val(data.emailBean.emailPassword);
                        $("#sentMname").val(data.emailBean.userName);
                    } else {
                        $(".emailEnter-span").addClass("red").text(
                            "获取邮件发送失败，请稍后再试").show();
                    }
                },
                error: function() {
                    $(".emailEnter-span").addClass("red")
                        .text("获取邮件发送失败，请稍后再试").show();
                }
            });
            // 提交 邮件发送设置 非空验证
            $("#emailEnter").click(
                function() {
                    var _$this = $(this);
                    _$this.button('loading');
                    if (!_$this.hasClass("notAllowed")) {
                        _$this.addClass("notAllowed");
                        if (!$("#emailServer").val()) {
                            $(".emailEnter-span").addClass("red").text(
                                "邮件服务器不能为空").show();
                            setTimeout(function() {
                                $(".emailEnter-span").text("");
                                _$this.removeClass("notAllowed");
                                _$this.button('reset');
                            }, 1500);
                            return;
                        }
                        if (!$("#emailPort").val() ||
                            isNaN($("#emailPort").val()) ||
                            +$("#emailPort").val() < 0 ||
                            +$("#emailPort").val() > 65535) {
                            if (!$("#emailPort").val()) {
                                $(".emailEnter-span").addClass("red").text(
                                    "端口不能为空").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                            if (isNaN($("#emailPort").val())) {
                                $(".emailEnter-span").addClass("red").text(
                                    "端口不能为非数字").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                            if (+$("#emailPort").val() < 0) {
                                $(".emailEnter-span").addClass("red").text(
                                    "端口不能小于0").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                            if (+$("#emailPort").val() > 65535) {
                                $(".emailEnter-span").addClass("red").text(
                                    "端口不能大于65535").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                        }
                        if (!$("#emailUserName").val() ||
                            !/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
                            .test($("#emailUserName").val())) {
                            if (!$("#emailUserName").val()) {
                                $(".emailEnter-span").addClass("red").text(
                                    "邮箱用户名不能为空").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                            if (!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test($(
                                    "#emailUserName").val())) {
                                $(".emailEnter-span").addClass("red").text(
                                    "邮箱用户名不合法").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                                return;
                            }
                        }
                        if (!$("#emailPassword").val()) {
                            $(".emailEnter-span").addClass("red").text(
                                "邮箱密码不能为空").show();
                            setTimeout(function() {
                                $(".emailEnter-span").text("");
                                _$this.removeClass("notAllowed");
                                _$this.button('reset');
                            }, 1500);
                            return;
                        }
                        if (!$("#sentMname").val()) {
                            $(".emailEnter-span").addClass("red").text(
                                "发送人名称不能为空").show();
                            setTimeout(function() {
                                $(".emailEnter-span").text("");
                                _$this.removeClass("notAllowed");
                                _$this.button('reset');
                            }, 1500);
                            return;
                        }
                        $.ajax({
                            url: "/Email/updateEmailCtrl.do",
                            type: "post",
                            data: {
                                emailServer: $("#emailServer").val(),
                                emailPort: $("#emailPort").val(),
                                emailUserName: $("#emailUserName").val(),
                                emailPassword: $("#emailPassword").val(),
                                emailAuthorCode: "",
                                userName: $("#sentMname").val()
                            },
                            dataType: "json",
                            success: function(data) {
                                switch (data.result) {
                                    case 0:
                                        $(".emailEnter-span").addClass("red")
                                            .text("提交失败，请稍后再试").show();
                                        break;
                                    case 1:
                                        $(".emailEnter-span")
                                            .removeClass("red")
                                            .text("提交成功").show();
                                        break;
                                    case 2:
                                        $(".emailEnter-span").addClass("red")
                                            .text("未连接到邮件服务器").show();
                                        break;
                                    default:
                                        $(".emailEnter-span").addClass("red")
                                            .text("提交失败，请稍后再试").show();
                                }
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                            },
                            error: function(data) {
                                $(".emailEnter-span").addClass("red").text(
                                    "提交失败，请稍后再试").show();
                                setTimeout(function() {
                                    $(".emailEnter-span").text("");
                                    _$this.removeClass("notAllowed");
                                    _$this.button('reset');
                                }, 1500);
                            }
                        })
                    }
                });
            // Syslog服务器设置
            var selectRow = null;
            $
                .ptcsBSTable(
                    "SyslogTable",
                    "/syslog/getSyslogInfo.do",
                    null, {
                        columns: [{
                            field: "name",
                            title: "名称",
                            sortable: true
                        }, {
                            field: "ip",
                            title: "IP",
                            sortable: true
                        }, {
                            field: "port",
                            title: "端口",
                            sortable: true
                        }, {
                            field: "descrption",
                            title: "备注",
                            sortable: true
                        }],
                        ipm_title: "Syslog服务器设置",
                        ipm_shrink: true,
                        ipm_show: false,
                        ipm_column_save: false,
                        rowStyle: function(row, i) {
                            var cla = {};
                            if (i == 0) {
                                cla.classes = "custom-row-style";
                                selectRow = row;
                            }
                            return cla;
                        },
                        onClickRow: function(row, tr) {
                            $(
                                    "#SyslogTable > tbody > .custom-row-style")
                                .removeClass();
                            $(tr).addClass("custom-row-style");
                            selectRow = row;
                        },
                        ipm_toolbar: [{
                                name: "新增",
                                type: "plus",
                                call: function(e) {
                                    $.JaddbsModalRow(["名称", "IP",
                                            "端口", "备注"
                                        ],
                                        "SyslogTable", null,
                                        "SyslogTable");
                                }
                            },
                            {
                                name: "修改",
                                type: "edit",
                                call: function(e) {
                                    if (selectRow == null) {
                                        jeBox
                                            .alert('请先添加SYSLOG服务器');
                                        return;
                                    }
                                    $
                                        .JeditRowModal(
                                            "#SyslogTable",
                                            "/syslog/getSyslogInfo.do", ["name", "ip",
                                                "port",
                                                "descrption"
                                            ], ["名称", "IP",
                                                "端口",
                                                "备注"
                                            ],
                                            "SyslogTable",
                                            null,
                                            "SyslogTable");
                                }
                            },
                            {
                                name: "删除",
                                type: "remove",
                                call: function(e) {
                                    if (selectRow == null) {
                                        jeBox
                                            .alert('请先添加SYSLOG服务器');
                                        return;
                                    }
                                    var selectRowId = $(
                                            "#SyslogTable .custom-row-style")
                                        .attr("data-id");
                                    if (selectRowId) {
                                        $("#Confirm-modal").attr(
                                            "data-tableAttr",
                                            "SyslogTable");
                                        $("#Confirm-modal").modal(
                                            "show");
                                    }
                                }
                            }
                        ]
                    });
            // button功能按钮
            $("#btn-addtableRow")
                .click(
                    function() {
                        if ($("#addtableRow-modal").attr(
                                "data-tableAttr") == "centerTable") {
                            $.JaddbsTableRow("#centerTable",
                                "/center/addCenterInfo.do", null,
                                "centerTable");
                        }
                        if ($("#addtableRow-modal").attr(
                                "data-tableAttr") == "SyslogTable") {
                            $.JaddbsTableRow("#SyslogTable",
                                "/syslog/addSyslog.do ", null,
                                "SyslogTable");
                        }
                    });
            $("#btn-changetableRow")
                .click(
                    function() {
                        if ($("#changetableRow-modal").attr(
                                "data-tableAttr") == "centerTable") {
                            $.JediTableRow("#centerTable",
                                "/center/updCenterById.do",
                                "centerTable");
                        }
                        if ($("#changetableRow-modal").attr(
                                "data-tableAttr") == "SyslogTable") {
                            $.JediTableRow("#SyslogTable",
                                "/syslog/updSyslog.do",
                                "SyslogTable");
                        }
                    });
            $("#btn-ConfirmdelRow")
                .click(
                    function() {
                        if ($("#Confirm-modal").attr("data-tableAttr") == "centerTable") {
                            if ($("#centerTable .custom-row-style")
                                .attr("data-id") != 1) {
                                $.JremovebsTableRow("#centerTable",
                                    "/center/delCenterById.do",
                                    "centerTable");
                            }
                        }
                        if ($("#Confirm-modal").attr("data-tableAttr") == "SyslogTable") {
                            $.JremovebsTableRow("#SyslogTable",
                                "/syslog/delSyslog.do",
                                "SyslogTable");
                            selectRow = null;
                        }
                    });
            // xpm服务器ip设置
            $.ajax({
                url: "/sysNetworkSet/xpmIps.do",
                type: "post",
                data: "",
                dataType: "json",
                success: function(data) {
                    $("#xpmIp").val(data.xpm_ips);
                }
            });

            // xpm服务器确定按钮
            $(".xpmIpSure").click(
                    function() {
                        $(".xpmIpSure").attr({
                            "disabled": "disabled"
                        });
                        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                        var valdata = $("#xpmIp").val().split(',');
                        var nary = valdata.sort();
                        if ($("#xpmIp").val() != "") {
                            if (valdata.length > 1) {
                                for (i = 0; i < valdata.length; i++) {
                                    if (nary[i] == nary[i + 1]) {
                                        $(".xpmIpspan").text("ip不能重复！")
                                            .addClass("red").show();
                                        setTimeout(function() {
                                            $(".xpmIpspan").hide();
                                        }, 1500);
                                        setTimeout(function() {
                                            $(".xpmIpSure").removeAttr(
                                                "disabled");
                                        }, 1500);
                                        return false;
                                    }
                                    if (reg.test(valdata[i]) == false) {
                                        $(".xpmIpspan")
                                            .text(
                                                "IP格式需正确且输入单个IP或者多个IP以英文逗号隔开,末尾不能加逗号！")
                                            .addClass("red").show();
                                        setTimeout(function() {
                                            $(".xpmIpspan").hide();
                                        }, 1500);
                                        setTimeout(function() {
                                            $(".xpmIpSure").removeAttr(
                                                "disabled");
                                        }, 1500);
                                        return false;
                                    } else {
                                        $.ajax({
                                            url: "/cgi-bin/pm.cgi",
                                            type: "get",
                                            data: {
                                                ips: $("#xpmIp").val()
                                            },
                                            success: function(
                                                data) {
                                                data = data.replace(/[\r\n]/g,"");
                                                if (data == "Y") {
                                                    $.ajax({
                                                            url: "/sysNetworkSet/updxpmIps.do",
                                                            type: "post",
                                                            data: {
                                                                ip: $("#xpmIp").val()
                                                            },
                                                            dataType: "json",
                                                            success: function(
                                                                data) {
                                                                if (data.success == "1") {
                                                                    $(".xpmIpspan").text("保存成功").removeClass("red").show();
                                                                    setTimeout(
                                                                        function() {
                                                                            $(".xpmIpspan").hide();
                                                                        },
                                                                        1500);
                                                                    setTimeout(
                                                                        function() {
                                                                            $(
                                                                                    ".xpmIpSure")
                                                                                .removeAttr(
                                                                                    "disabled");
                                                                        },
                                                                        1500);
                                                                } else {
                                                                    $(
                                                                            ".xpmIpspan")
                                                                        .text(
                                                                            "保存失败，请稍后再试")
                                                                        .addClass(
                                                                            "red")
                                                                        .show();
                                                                    setTimeout(
                                                                        function() {
                                                                            $(
                                                                                    ".xpmIpspan")
                                                                                .hide();
                                                                        },
                                                                        1500);
                                                                    setTimeout(
                                                                        function() {
                                                                            $(
                                                                                    ".xpmIpSure")
                                                                                .removeAttr(
                                                                                    "disabled");
                                                                        },
                                                                        1500);
                                                                }
                                                            }
                                                        });
                                                } else {
                                                    $(
                                                            ".xpmIpspan")
                                                        .text(
                                                            data)
                                                        .addClass(
                                                            "red")
                                                        .show();
                                                    setTimeout(
                                                        function() {
                                                            $(
                                                                    ".xpmIpspan")
                                                                .hide();
                                                        },
                                                        20000);
                                                    setTimeout(
                                                        function() {
                                                            $(
                                                                    ".xpmIpSure")
                                                                .removeAttr(
                                                                    "disabled");
                                                        },
                                                        1500);
                                                }
                                            }
                                        })
                                    }
                                }
                            } else {
                                if (reg.test($("#xpmIp").val())) {
                                    $.ajax({
                                        url: "/cgi-bin/pm.cgi",
                                        type: "get",
                                        data: {
                                            ips: $("#xpmIp").val()
                                        },
                                        success: function(data) {
                                            data = data.replace(/[\r\n]/g,"");
                                            if (data == "Y") {
                                                $.ajax({
                                                        url: "/sysNetworkSet/updxpmIps.do",
                                                        type: "post",
                                                        data: {
                                                            ip: $("#xpmIp").val(),
                                                        },
                                                        dataType: "json",
                                                        success: function(
                                                            data) {
                                                            if (data.success == "1") {
                                                                $(".xpmIpspan").text("保存成功").removeClass("red").show();
                                                                setTimeout(
                                                                    function() {
                                                                        $(".xpmIpspan").hide();
                                                                    },
                                                                    1500);
                                                                setTimeout(
                                                                    function() {
                                                                        $(
                                                                                ".xpmIpSure")
                                                                            .removeAttr(
                                                                                "disabled");
                                                                    },
                                                                    1500);
                                                            } else {
                                                                $(
                                                                        ".xpmIpspan")
                                                                    .text(
                                                                        "保存失败，请稍后再试")
                                                                    .addClass(
                                                                        "red")
                                                                    .show();
                                                                setTimeout(
                                                                    function() {
                                                                        $(
                                                                                ".xpmIpspan")
                                                                            .hide();
                                                                    },
                                                                    1500);
                                                                setTimeout(
                                                                    function() {
                                                                        $(
                                                                                ".xpmIpSure")
                                                                            .removeAttr(
                                                                                "disabled");
                                                                    },
                                                                    1500);
                                                            }
                                                        }
                                                    });
                                            } else {
                                                $(".xpmIpspan").text(data).addClass("red").show();
                                                setTimeout(
                                                    function() {
                                                        $(
                                                                ".xpmIpspan")
                                                            .hide();
                                                    },
                                                    20000);
                                                setTimeout(
                                                    function() {
                                                        $(
                                                                ".xpmIpSure")
                                                            .removeAttr(
                                                                "disabled");
                                                    }, 1500);
                                            }
                                        }
                                    })
                                } else {
                                    $(".xpmIpspan")
                                        .text(
                                            "IP格式需正确且输入单个IP或者多个IP以英文逗号隔开,末尾不能加逗号！")
                                        .addClass("red").show();
                                    setTimeout(function() {
                                        $(".xpmIpspan").hide();
                                    }, 1500);
                                    setTimeout(function() {
                                        $(".xpmIpSure").removeAttr(
                                            "disabled");
                                    }, 1500);
                                }
                            }
                        } else {
                            $(".xpmIpspan").text("IP不能为空！").addClass(
                                "red").show();
                            setTimeout(function() {
                                $(".xpmIpspan").hide();
                            }, 1500);
                            setTimeout(function() {
                                $(".xpmIpSure").removeAttr("disabled");
                            }, 1500);
                        }
                    });
        },
        setHtm3: function() {
            // 配置导出
            $("#configExport").click(function() {
                var _this = $(this).button("loading");
                var openWin = window.open("/systemSet/systemConfigExport.do");
                var listen = setInterval(function() {
                    if (openWin.closed) {
                        clearInterval(listen);
                        _this.button("reset");
                    }
                }, 1000);
            });
            // 配置导入
            $("#configImport").click(function() {
                if (typeof FormData == 'undefined') {
                    jeBox.alert("此浏览器不支持上传，请使用高版本或者其他浏览器");
                    return;
                }
                var _this = $(this).button("loading");
                var file = $("#uploadFile").prop("files")[0];
                if (file.name.lastIndexOf(".CONFIG") == -1) {
                    jeBox.alert("配置文件不正确");
                    _this.button("reset");
                } else {
                    var formData = new FormData();
                    formData.append("name", file.name);
                    formData.append("file", file);
                    $.ajax({
                        url: '/systemSet/systemConfigImport.do',
                        type: 'POST',
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function() {
                            jeBox.alert("导入成功");
                            _this.button("reset");
                        },
                        error: function() {
                            jeBox.alert("导入失败");
                            _this.button("reset");
                        }
                    });
                }
            });

            // 应用配置导入
            $("#mouldImport").click(function() {
                if (typeof FormData == 'undefined') {
                    jeBox.alert("此浏览器不支持上传，请使用高版本或者其他浏览器");
                    return;
                }
                var _this = $(this).button("loading");
                var file = $("#mouldloadFile").prop("files")[0];
                if (file.name.lastIndexOf(".xlsx") == -1) {
                    jeBox.alert("配置文件不正确");
                    _this.button("reset");
                } else {
                    var formData = new FormData();
                    formData.append("name", file.name);
                    formData.append("file", file);
                    $.ajax({
                        url: '/systemSet/appConfigImport.do',
                        type: 'POST',
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function() {
                            jeBox.alert("导入成功");
                            _this.button("reset");
                        },
                        error: function() {
                            jeBox.alert("导入失败");
                            _this.button("reset");
                        }
                    });
                }
            });
            // 模板下载
            $("#mouldLoad").click(function() {
                var _this = $(this).button("loading");
                var openWin = window.open("/systemSet/tempDowload.do");
                var listen = setInterval(function() {
                    if (openWin.closed) {
                        clearInterval(listen);
                        _this.button("reset");
                    }
                }, 1000);
            });

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

                        if (!email
                            .match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
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
	                                        setTimeout(function() {
	                                                $("#uploadriza").removeAttr(
	                                                    "disabled");
	                                            }, 1500);
                                        } else {
                                        	$(".uploadrizaSpan").text(data).addClass("red").show();
	                                        setTimeout(function() {
	                                            $(".uploadrizaSpan").hide();
	                                        }, 1500);
	                                        setTimeout(function() {
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
        },
        systemLogs: function() {
            var columns = [{
                    field: "id",
                    title: "编号",
                    sortable: true
                }, {
                    field: "time",
                    title: "时间",
                    sortable: true,
                    formatter: function(v) {
                        return $.timeStampDate(v);
                    }
                }, {
                    field: "userName",
                    title: "用户",
                    sortable: true
                }, {
                    field: "moduleName",
                    title: "模块",
                    sortable: true
                }, {
                    field: "msg",
                    title: "信息",
                    sortable: true
                }],
                selectRow = null;
            $.ptcsBSTable("systemLogsTab", "/systemLogs/getAllLogs.do", null, {
                columns: columns,
                ipm_title: "系统日志",
                ipm_shrink: true,
                ipm_column_save: true,
                onClickRow: function(row, tr) {
                    $("#systemLogsTab > tbody > .custom-row-style")
                        .removeClass();
                    $(tr).addClass("custom-row-style");
                }
            });
        },
        systemTool: function() {
            /* 设备操作 */
            $("#rcSerBtn").click(function() {
                if ($('input[name="optionsRadios2"]:checked').val() == "1") {
                    $.ajax({
                        url: "/systemSet/rebootServer.do",
                        type: "post",
                        dataType: "json",
                        success: function(data) {}
                    });
                } else {
                    $.ajax({
                        url: "/systemSet/haltServer.do ",
                        type: "post",
                        dataType: "json",
                        success: function(data) {}
                    });
                }
            });
            /**
             * 系统资源消耗 涉及到开始时间结束时间的非空验证以及是否为时间的验证
             */
            var tempTime = $.myTime.CurTime();
            $("#starttime").val($.myTime.UnixToDate(tempTime - 300));
            $("#endtime").val($.myTime.UnixToDate(tempTime));
            $("#historyPeriod").change(
                function() {
                    var timeCase = $(this).children("option:selected")
                        .val(),
                        nowTime = $.myTime.CurTime();
                    $("#starttime").val(
                        $.myTime.UnixToDate(nowTime - timeCase));
                    $("#endtime").val($.myTime.UnixToDate(nowTime));
                });
            $("#systemCapitalEnter")
                .click(
                    function() {
                        if ($.myTime.DateToUnix($("#starttime").val()) != -2209104000 &&
                            $.myTime.DateToUnix($("#endtime")
                                .val()) != -2209104000 &&
                            ($.myTime.DateToUnix($("#endtime")
                                    .val()) - $.myTime
                                .DateToUnix($("#starttime")
                                    .val())) > 0) {
                            var newHtml = "systemCapital.html?starttime=" +
                                $.myTime.DateToUnix($(
                                    "#starttime").val()) +
                                "&endtime=" +
                                $.myTime.DateToUnix($("#endtime")
                                    .val());
                            window.open(newHtml);
                        } else {
                            if (($.myTime.DateToUnix($("#endtime")
                                    .val()) - $.myTime.DateToUnix($(
                                    "#starttime").val())) > 0) {
                                jeBox.alert("结束时间不能大于开始时间");
                            } else {
                                jeBox.alert("请输入正确的开始时间或结束时间");
                            }
                        }
                    });
            $("#starttime").jeDate({
                isTime: true,
                isClear: false,
                format: 'YYYY-MM-DD hh:mm:ss'
            });
            $("#endtime").jeDate({
                isTime: true,
                isClear: false,
                format: 'YYYY-MM-DD hh:mm:ss'
            });
        }
    };
    switch (location.pathname.split(".")[0].replace(/\//, "")) {
        case "settingindex":
            setTing.setHtm1();
            break;
        case "settingindex3":
            setTing.setHtm3();
            break;
        case "systemLogs":
            setTing.systemLogs();
            break;
        case "systemTool":
            setTing.systemTool();
            break;
        default:
            jeBox.alert("未书写此页面的js代码");
    }
});