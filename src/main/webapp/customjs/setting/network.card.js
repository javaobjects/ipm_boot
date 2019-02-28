/**
 * 网卡信息
 */
$(function() {
    var columns = [{
        field: "name",
        title: "网卡名称",
        sortable: true,
        formatter: function(v, r) {
            if (r.ip == "127.0.0.1") {
                v += "内部通信";
            }

            return v;
        }
    }, {
        field: "ip",
        title: "IP地址",
        sortable: true,
        formatter: function(v, r) {
            if (v == "0.0.0.0") {
                v = "";
            }
            return v;
        }
    }, {
        field: "rxPackets",
        title: "接收数据包数",
        sortable: true
    }, {
        field: "rxBytes",
        title: "接收字节数",
        sortable: true
    }];
    $.ptcsBSTable("networkCard", "/sysNetworkSet/getNetworkCardInfo.do", null, {
        columns: columns,
        ipm_title: "网卡管理与设置",
        ipm_shrink: true,
        ipm_show: false,
        ipm_column_save: false,
        ipm_toolbar: [{
            name: "重新分析",
            type: "repeat",
            call: function() {
                $.post("/sysNetworkSet/startStatistic.do", null, function(bool) {
                    if (bool == true || bool == "true") {
                        jeBox.alert("已重新开始分析");
                    } else {
                        jeBox.alert("重新分析失败");
                    }
                });
            }
        }/*, {
            name: "清空数据",
            type: "trash",
            call: function() {
                $.post("/sysNetworkSet/getDataEmptying.do", null, function() {
                    $.ptcsBSTabRefresh("networkCard");
                });
            }
        }*/]
    });
    setInterval(function() {
        $.ptcsBSTabRefresh("networkCard");
    }, 10000);
});