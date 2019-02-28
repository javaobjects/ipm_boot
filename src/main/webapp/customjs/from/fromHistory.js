/**
 * 报表历史记录页面
 */
$(function() {
    $("#formLoad").hide();
    var selectRow = null,
        selectRowId = null;

    var columns = [{
        field: "name",
        title: "报表名称",
        sortable: true
    }, {
        field: "createtime",
        title: "报表生成时间",
        sortable: true,
        formatter: function(v) {
            if (v == 0) {
                return null;
            } else {
                return $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss");
            }
        }
    }, {
        field: "sendNames",
        title: "发送人",
        sortable: true
    }, {
        field: "recriveNames",
        title: "接收人",
        sortable: true
    }, {
        field: "sendTime",
        title: "发送时间",
        sortable: true,
        formatter: function(v) {
            if (v == 0) {
                return null;
            } else {
                return $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss");
            }
        }
    }, {
        field: "sendStatus",
        title: "发送状态",
        sortable: true,
        formatter: function(v) {
            if (v == 1) {
                return "否";
            } else {
                return "是";
            }
        }
    }, {
        field: "typeId",
        title: "归档类型",
        sortable: true
    }];
    $.ptcsBSTable("fromHistory", "/reporthistoryController/getAllReportHistory.do", null, {
        columns: columns,
        ipm_title: "历史记录",
        ipm_shrink: true,
        pageSize: 10,
        rowStyle: function(row, i) {
	          var cla = {};
	          if(i == 0) {
	               cla.classes = "custom-row-style";
	               selectRow = row;
		           selectRowId = row.id;
		           selectName = row.name;
	          }
	          return cla;
	    },
        onClickRow: function(row, tr) {
            $("#fromHistory > tbody > .custom-row-style").removeClass("custom-row-style");
            $(tr).addClass("custom-row-style");
            selectRow = row;
            selectRowId = row.id;
            selectName = row.name;
        },
        ipm_toolbar: [{
            name: "下载",
            type: "download-alt",
            call: function() {
            	if (selectRow == null) {
					jeBox.alert('暂无历史报表');
					return;
				}
                $("#formLoad").show();
                var openWin = window.open("/downLoadController/downLoadWordByName.do?name=" + selectName + "&" + "id=" + selectRowId);
                $("#formLoad").hide();
                var listen = setInterval(function() {
                        if (openWin.closed) {
                            clearInterval(listen);
                        }
                    },
                    1000);
            }
        }, {
            name: "删除",
            type: "remove",
            call: function() {
            	if (selectRow == null) {
					jeBox.alert('暂无历史报表');
					return;
				}
                $("#formHisDelete").modal("show");
            }
        }]
    });
    //删除请求
    $("#btn-formHisDelete").click(function() {
        $.ajax({
            url: "/reporthistoryController/deleteReportHistoryById.do",
            type: "post",
            data: {
                id: selectRowId,
                name: selectName
            },
            dataType: "json",
            success: function(data) {
                if (data.result == true) {
                    $.ptcsBSTabRefresh("fromHistory");
                } else {
                     jeBox.alert(data.msg);
                }
            }
        });
    });

    //点击时间回溯确定按钮
    $(".timesure").click(function() {
        var starttime = Date.parse(new Date($("#inpstart").val())) / 1000;
        var endtime = Date.parse(new Date($("#inpend").val())) / 1000;
        //先要验证开始时间与结束时间是否为空且格式正胡且开始时间小于结束时间
        var numS = $.myTime.DateToUnix($("#inpend").val()) - $.myTime.DateToUnix($("#inpstart").val());
        if ($("#inpstart").val() && $("#inpend").val() && (numS - 10 >= 0)) {
            //将时间赋值到右上角
            if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                $("#time").text($("#inpstart").val() + " ~ " + $("#inpend").val())
            } else {
                var index = $("#inpstart").val().split("-")[0].length + 1;
                $("#time").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
            }
            $('a[data-drawer="times"]').trigger("click");
            //刷新表格
            $.ptcsBSTabRefresh("fromHistory", {
                "starttime": starttime,
                "endtime": endtime
            });
        } else {
            jeBox.alert("开始时间不能为空，且开始时间不能大于结束时间，最小间隔为10秒钟");
        }
    });
});