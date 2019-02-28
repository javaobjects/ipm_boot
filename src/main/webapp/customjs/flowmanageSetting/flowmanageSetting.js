/**
 * Created by yanb on 2018/3/1.
 */
;$(function() {
    var floSet = {
        flSet1:function(){
            $.post("/watchpointController/getFindAll.do", null, function(data) {
                var watchpointHtml = "";
                for(var i = 0, len = data.length; i < len; i ++) {
                    watchpointHtml += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#watchpoint").append(watchpointHtml);
                $.post("/storage/getStorageById.do", { id: 1 }, function(d) {
                    if(d.watchpointId) {
                        var watchpointId = d.watchpointId.split(",");
                        var len = watchpointId.length;
                        if(len > 0) {
                            $("#watchpoint").find("option").each(function(i, n){
                                for(var i = 0; i < len; i ++) {
                                    if(watchpointId[i] == n.value) {
                                        n.selected = true;
                                        break;
                                    }
                                }
                            });
                        }
                    }
                    $("#watchpoint").chosen();
                    $("#ip").val(d.ip);
                    $("#port").val(d.port == 0 ? "" : d.port);
                    $("#bpf").val(d.bpf);
                    $("#state").val(d.state ? 1 : 0);
                }, "json");
                $("#tandardStorageOK").click(function(){
                    var watchpoint = $("#watchpoint").val(),
                        ip = $("#ip").val(),
                        port = $("#port").val(),
                        bpf = $("#bpf").val(),
                        state = parseInt($("#state").val()) == 1;
                    var params = {
                        id: 1,
                        name: "标准存储方案",
                        state: state
                    };
                    var ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                    var ips =null;
                    var regChineseWord = new RegExp("[\\u4E00-\\u9FFF]+","g");
                    if(ip != ""){
                        if(ip.indexOf("-") > -1){
                            //第一种  以 - 分隔 ，验证前后ip 是不是 正确的 ip 格式  和  验证 前后 ip 不能相同
                            ips =ip.split("-");
                            if(!ipReg.test(ips[0]) || !ipReg.test(ips[1])){
                                jeBox.alert("IP格式不正确");
                                return;
                            }else if(ips[0] == ips[1]){
                            	jeBox.alert("前后IP格式不能一样");
                                return;
                            }
                        }else if(ip.indexOf("/") > -1 ){
                            ips =ip.split("/");
                            if(!ipReg.test(ips[0])){
                                jeBox.alert("IP格式不正确");
                                return;
                            }else if(ips[1] < 0 || ips[1] > 32 ){
                            	jeBox.alert("网段格式不正确,支持 0  到 32");
                                return;
                            }
                        }else{
                            //方式 三  ip 逗号分隔 不能 超过 8个
                            ips =ip.split(",");
                            if(ips.length > 8){
                                jeBox.alert("IP数量不能大于8个");
                                return;
                            }else{
                                var tmp = new Array();
                                for(var i in ips){
                                    if(!ipReg.test(ips[i])){
                                    	jeBox.alert("IP格式不正确");
                                        return;
                                    }else{
                                        if(tmp.indexOf(ips[i])==-1){
                                            tmp.push(ips[i]);
                                        }
                                    }
                                }
                                if(ips.length > tmp.length){
                                	jeBox.alert("IP格式不能有一样的");
                                    return;
                                }
                            }
                        }
                    }
                    if($.trim(bpf)!=""){
                        if(!isNaN(Number(bpf))){
                            jeBox.alert("BPF过滤条件不可为数字");
                            return;
                        }else if(regChineseWord.test(bpf)){
                        	jeBox.alert("BPF过滤条件不可包含汉字");
                            return;
                        }
                    }
                    if(port && (parseInt(port) > 65535 || parseInt(port) < 0)) {
                        jeBox.alert("端口必须在0到65535之间");
                        return;
                    } else {
                        if(watchpoint) {
                            params.watchpointId = watchpoint.join(",");
                        }
                        if(ip) {
                            params.ip = ip;
                        }
                        if(port) {
                            params.port = port;
                        }
                        if(bpf) {
                            params.bpf = bpf;
                        }
                        var _this = $(this).button("loading");
                        $.post("/storage/updateStorageById.do", params, function(d) {
                            if(d.success == "0") {
                                jeBox.alert("保存成功");
                            } else {
                                jeBox.alert("保存失败");
                            }
                            _this.button("reset");
                        }, "json");
                    }
                });
            }, "json");
        },
        flSet2:function(){
            var hourHtml = "", minuteHtml = "";
            for(var i = 0; i < 24; i ++) {
                hourHtml += "<option value='" + i + "'>" + i + "</option>";
            }
            for(var i = 0; i < 60; i ++) {
                minuteHtml += "<option value='" + i + "'>" + i + "</option>";
            }
            $("#starttimeHour").append(hourHtml);
            $("#endtimeHour").append(hourHtml);
            $("#starttimeMinute").append(minuteHtml);
            $("#endtimeMinute").append(minuteHtml);
            $.post("/watchpointController/getFindAll.do", null, function(data) {
                var watchpointHtml = "", map = {};
                for(var i = 0, len = data.length; i < len; i ++) {
                    watchpointHtml += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                    map[data[i].id] = data[i].name;
                }
                $("#watchpoint").append(watchpointHtml).chosen();
                var columns = [
                    { field: "name", title: "名称", sortable: true },
                    { field: "watchpointId", title: "观察点名称", sortable: true, formatter: function(v) {
                        var arr = v.split(","), name = "";
                        for(var i = 0; i < arr.length; i ++) {
                        	if (i != 0) {
                        	   name += ",";
                        	}
                            name += map[arr[i]];
                        }

                        return name;
                    } },
                    { field: "starttime", title: "开始时间", sortable: true },
                    { field: "endtime", title: "结束时间", sortable: true },
                    { field: "ip", title: "IP", sortable: true },
                    { field: "port", title: "端口", sortable: true, formatter: function(v) {
                        return  v == 0 ? null : v;
                    } },
                    { field: "bpf", title: "BPF过滤条件", sortable: true },
                    { field: "size", title: "数据包截取大小", sortable: true, formatter: function(v) {
                        return  v == 0 ? null : v;
                    } },
                    { field: "state", title: "状态", sortable: true, formatter: function(v) {
                        return v ? "启动" : "禁用";
                    } }
                ], selectRow = null, isUpd = true;
                $.ptcsBSTable("seniorStoreTab", "/storage/getStorageHigh.do", null, {
                    pageSize: 10,
                    columns: columns,
                    ipm_title: "高级存储方案",
                    ipm_shrink: true,
                    rowStyle: function(row, i) {
                        var cla = {};
                        if(i == 0) {
                            cla.classes = "custom-row-style";
                            selectRow = row;
                        }
                        return cla;
                    },
                    onClickRow: function(row, tr) {
                        $("#seniorStoreTab > tbody > .custom-row-style").removeClass();
                        $(tr).addClass("custom-row-style");
                        selectRow = row;
                    },
                    ipm_toolbar: [
                        { name: "新增", type: "plus", call: function(e) {
                            isUpd = false;
                            $("#watchpoint").find("option").removeAttr("selected");
                            $("#watchpoint").trigger("liszt:updated");
                            $("#auSeniorStoreForm")[0].reset();
                            $("#auSeniorStoreLabel").html("添加");
                            $('#auSeniorStore').modal({ backdrop: "static" });
                        } },
                        { name: "修改", type: "edit", call: function(e) {
                            if(selectRow == null) {
                                jeBox.alert("请先添加高级存储方案");
                                return;
                            }
                            isUpd = true;
                            var watchpointId = selectRow.watchpointId.split(","),
                                starttime = selectRow.starttime.split(":"),
                                endtime = selectRow.endtime.split(":");
                            $("#name").val(selectRow.name);

                            var ss =parseInt(starttime[0]); //开始时间   时
                            var sf =parseInt(starttime[1]);//开始时间    分
                            var es=parseInt(endtime[0]); //结束时间   时
                            var ef=parseInt(endtime[1]);//结束时间  分

                            $("#starttimeHour").val(ss);
                            $("#starttimeMinute").val(sf);
                            $("#endtimeHour").val(es);
                            $("#endtimeMinute").val(ef);
                            $("#ip").val(selectRow.ip);
                            $("#port").val(selectRow.port == 0 ? null : selectRow.port);
                            $("#size").val(selectRow.size == 0 ? null : selectRow.size);
                            $("#bpf").val(selectRow.bpf);
                            $("#state").val(selectRow.state ? 1 : 0);
                            $("#auSeniorStoreLabel").html("修改");
                            $("#watchpoint").find("option").each(function(i, o) {
                                for (var i = 0, len = watchpointId.length; i < len; i ++) {
                                    if(parseInt($(o).val()) == parseInt(watchpointId[i])) {
                                        $(o).attr("selected", "selected");
                                        break;
                                    }
                                }
                            });
                            $("#watchpoint").trigger("liszt:updated");
                            $('#auSeniorStore').modal({ backdrop: "static" });
                        } },
                        { name: "删除", type: "remove", call: function(e) {
                            if(selectRow == null) {
                                jeBox.alert("请先添加高级存储方案");
                                return;
                            }
                            jeBox({
                                title: '提示',
                                content: '您确定删除吗？',
                                btnAlign: 'center',
                                maskLock : true,
                                button: [{
                                    name: "确定",
                                    callback: function(index) {
                                        $.post("/storage/delStorageById.do", { id: selectRow.id }, function(d) {
                                            if(d.success == "0") {
                                                jeBox.alert("删除成功");
                                                $.ptcsBSTabRefresh("seniorStoreTab");
                                                selectRow = null;
                                            } else {
                                                jeBox.alert("删除失败");
                                            }
                                        }, "json");
                                        jeBox.close(index);
                                    }
                                }, {
                                    name: '取消'
                                }]
                            });
                        } }
                    ]
                });

                $("#auSeniorStoreOK").click(function() {
                    var watchpoint = $("#watchpoint").val(),
                        name = $("#name").val(),
                        starttimeHour = $("#starttimeHour").val(),
                        starttimeMinute = $("#starttimeMinute").val(),
                        endtimeHour = $("#endtimeHour").val(),
                        endtimeMinute = $("#endtimeMinute").val(),
                        ip = $("#ip").val(),
                        port = $("#port").val(),
                        size = $("#size").val(),
                        bpf = $("#bpf").val(),
                        state = +$("#state").val();
                    if(starttimeMinute < 10) {
                        starttimeMinute = "0" + starttimeMinute;
                    }
                    if(endtimeMinute < 10) {
                        endtimeMinute = "0" + endtimeMinute;
                    }
                    var starttime = parseInt(starttimeHour + "" + starttimeMinute + "00"),
                        endtime = parseInt(endtimeHour + "" + endtimeMinute + "59");
                    var ipReg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                    var ips =null;
                    if(ip !=""){
                        if(ip.indexOf("-") > -1){
                            //第一种  以 - 分隔 ，验证前后ip 是不是 正确的 ip 格式  和  验证 前后 ip 不能相同
                            ips =ip.split("-");
                            if(!ipReg.test(ips[0]) || !ipReg.test(ips[1])){
                                jeBox.alert("IP格式不正确");
                                return;
                            }else if(ips[0] == ips[1]){
                            	jeBox.alert("前后IP格式不能一样");
                                return;
                            }
                        }else if(ip.indexOf("/") > -1 ){
                            ips =ip.split("/");
                            if(!ipReg.test(ips[0])){
                                jeBox.alert("IP格式不正确");
                                return;
                            }else if(ips[1] < 0 || ips[1] > 32 ){
                            	jeBox.alert("网段格式不正确,支持 0  到 32");
                                return;
                            }
                        }else{
                            //方式 三  ip 逗号分隔 不能 超过 8个
                            ips =ip.split(",");
                            if(ips.length > 8){
                                jeBox.alert("IP数量不能大于8个");
                                return;
                            }else{
                                var tmp = new Array();
                                for(var i in ips){
                                    if(!ipReg.test(ips[i])){
                                    	jeBox.alert("IP格式不正确");
                                        return;
                                    }else{
                                        if(tmp.indexOf(ips[i])==-1){
                                            tmp.push(ips[i]);
                                        }
                                    }
                                }
                                if(ips.length > tmp.length){
                                    jeBox.alert("IP格式不能有一样的");
                                    return;
                                }
                            }
                        }
                    }
                    if(!watchpoint) {
                        jeBox.alert("观察点不能为空");
                    } else if(name == "") {
                        jeBox.alert("名称不能为空");
                    } else if(!sameName(name)) {
                        jeBox.alert("名称不能相同");
                    } else if(starttime >= endtime) {
                    	jeBox.alert("开始时间不能大于或等于结束时间");
                    } else if(!timeValidate(starttime, endtime)) {
                    	jeBox.alert("开始时间或结束时间不能与已存在记录时间重叠");
                    } else if(port && (parseInt(port) > 65535 || parseInt(port) < 0)) {
                        jeBox.alert("端口必须在0到65535之间");
                    } else {
                        var params = {
                            watchpointId: watchpoint.join(","),
                            name: name,
                            starttime: starttimeHour + ":" + starttimeMinute + ":00",
                            endtime: endtimeHour + ":" + endtimeMinute + ":59",
                            state: !!state
                        };
                        if(isUpd) {
                            params.id = selectRow.id;
                        }
                        if(ip) {
                            params.ip = ip;
                        }
                        if(port) {
                            params.port = port;
                        }
                        if(bpf) {
                            params.bpf = bpf;
                        }
                        if(size) {
                            params.size = size;
                        }
                        var _this = $(this).button("loading");
                        $.post(isUpd ? "/storage/updateStorageById.do" : "/storage/addStorage.do", params, function(d) {
                            if(d.success == "0") {
                                jeBox.alert("操作成功");
                                $.ptcsBSTabRefresh("seniorStoreTab");
                            } else {
                                jeBox.alert("操作失败");
                            }
                            _this.button("reset");
                            $('#auSeniorStore').modal("hide");
                        }, "json");
                    }
                });
                
                var sameName = function(name) {
                    var data = $("#seniorStoreTab").bootstrapTable("getData"),
                        bool = true, tmp = null,
                        len = data.length;
                    if(len != 0) {
                        for(var i = 0; i < len; i ++) {
                            tmp = data[i];
                            if(isUpd && tmp.id == selectRow.id) {
                                continue;
                            }
                            if(tmp.name == name) {
                                bool = false;
                                break;
                            }
                        }
                    }
                    
                    return bool;
                }

                var timeValidate = function(starttime, endtime) {
                    var data = $("#seniorStoreTab").bootstrapTable("getData"),
                        bool = true,
                        len = data.length;
                    if(len != 0) {
                        var tmp = null, start = 0, end = 0;
                        for(var i = 0; i < len; i ++) {
                            tmp = data[i];
                            if(isUpd && tmp.id == selectRow.id) {
                                continue;
                            }
                            start = parseInt(tmp.starttime.replace(/:/g, ""));
                            end = parseInt(tmp.endtime.replace(/:/g, ""));

                            if ((starttime >= start && starttime <= end) ||
                                (endtime >= start && endtime <= end) || 
                                (start >= starttime && start <= endtime) ||
                                (end >= starttime  && end <= endtime)) {
                                bool = false;
                                break;
                            }
                        }
                    }

                    return bool;
                }
            }, "json");
        },
        flSet3:function(){
            $.post("/watchpointController/getFindAll.do", null, function(data) {
                var watchpointHtml = "";
                for(var i = 0, len = data.length; i < len; i ++) {
                    watchpointHtml += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                }
                $("#watchpoint").append(watchpointHtml);
                $("#watchpoint").chosen();
            }, "json");
            //业务类型
            $.ajax({
                url:"/authorizeModuleController/getFindAllForFlowCtl.do",
                type:"post",
                data:{},
                dataType:"json",
                success:function(data){
                    data.forEach(function(item,index){
                        var optionHtml,url;
                        switch (item.nameen){
                            // case "观察点":
                            //     url = "/watchpointController/getFindAll.do";
                            //     break;
                            case "SERVER":
                                url = "/serverManagement/getAllServerSide.do";
                                break;
                            case "CLIENT":
                                url = "/client/getClient.do?moduleId=11";
                                break;
                            case "HTTP":
                                url = "/appController/getAllAppByModuleId.do?moduleId=4";
                                break;
                            case "ORACLE":
                                url = "/appController/getAllAppByModuleId.do?moduleId=5";
                                break;
                            case "MYSQL":
                                url = "/appController/getAllAppByModuleId.do?moduleId=6";
                                break;
                            case "SQLSERVER":
                                url = "/appController/getAllAppByModuleId.do?moduleId=7";
                                break;
                            // case "URL服务":
                            //     url = "/url/get.do";
                            //     break;
                            case "MESSAGE":
                                url = "/appController/getAllAppByModuleId.do?moduleId=9";
                                break;
                            default:
                                jeBox.alert("未书写此模块代码，请联系开发人员");
                        }
                        if(index){
                            optionHtml = '<option value="" data-id="'+item.id+'" data-nameen="'+item.nameen+'" data-url="'+url+'">'+item.namezh+'</option>';
                        }else {
                            optionHtml = '<option value="" selected="selected">请选择</option>';
                            optionHtml += '<option value="" data-id="'+item.id+'" data-nameen="'+item.nameen+'" data-url="'+url+'">'+item.namezh+'</option>';
                        }
                        $("#busiIdTypeSelect").append(optionHtml);
                    });
                    $("#buSiIdSelect").html("").append('<option value="" selected="selected">请选择</option>');
                }
            });
            //业务类型变化时为业务赋值
            $("#busiIdTypeSelect").change(function(){
                var url = $(this).children("option:selected").attr("data-url");
                if(url){
                    $.ajax({
                        url:url,
                        type:"post",
                        data:{},
                        dataType:"json",
                        success:function(data){
                            $("#buSiIdSelect").html("").append('<option value="" selected="selected">请选择</option>');
                            data.forEach(function(item,index){
                                var optionhtml = '<option value="" data-id="'+item.id+'">'+item.name+'</option>';
                                $("#buSiIdSelect").append(optionhtml);
                            })
                        }
                    })
                }else {
                    $("#buSiIdSelect").html("").append('<option value="" selected="selected">请选择</option>');
                }
            });
            /*
             * 服务器端IP和客户端IP要不都输入 要不都不输入
             * 当其输入时禁用IP 端口 bpf过滤条件 输入框的输入
             *
             * 当IP 或 端口输入时 禁用 服务器端IP 和 客户端IP 和 BPF过滤条件
             *
             * 当BPF过滤条件输入时 禁用 服务器端IP 和 客户端IP 和 IP 和 端口
             *
             */
            $("#serverIp").blur(function () {
                if($(this).val()){
                    $("#ip").val("").prop("disabled",true);
                    $("#port").val("").prop("disabled",true);
                    $("#bpf").val("").prop("disabled",true);
                }else {
                    if(!$("#clientIp").val()){
                        $("#ip").val("").prop("disabled",false);
                        $("#port").val("").prop("disabled",false);
                        $("#bpf").val("").prop("disabled",false);
                    }
                }
            });
            $("#clientIp").blur(function () {
                if($(this).val()){
                    $("#ip").val("").prop("disabled",true);
                    $("#port").val("").prop("disabled",true);
                    $("#bpf").val("").prop("disabled",true);
                }else {
                    if(!$("#serverIp").val()){
                        $("#ip").val("").prop("disabled",false);
                        $("#port").val("").prop("disabled",false);
                        $("#bpf").val("").prop("disabled",false);
                    }
                }
            });
            $("#ip").blur(function(){
                if($(this).val()){
                    $("#serverIp").val("").prop("disabled",true);
                    $("#clientIp").val("").prop("disabled",true);
                    $("#bpf").val("").prop("disabled",true);
                }else {
                    if(!$("#port").val()){
                        $("#serverIp").val("").prop("disabled",false);
                        $("#clientIp").val("").prop("disabled",false);
                        $("#bpf").val("").prop("disabled",false);
                    }
                }
            });
            $("#port").blur(function(){
                if($(this).val()){
                    $("#serverIp").val("").prop("disabled",true);
                    $("#clientIp").val("").prop("disabled",true);
                    $("#bpf").val("").prop("disabled",true);
                }else {
                    if(!$("#ip").val()){
                        $("#serverIp").val("").prop("disabled",false);
                        $("#clientIp").val("").prop("disabled",false);
                        $("#bpf").val("").prop("disabled",false);
                    }
                }
            });
            $("#bpf").blur(function(){
                if($(this).val()){
                    $("#serverIp").val("").prop("disabled",true);
                    $("#clientIp").val("").prop("disabled",true);
                    $("#ip").val("").prop("disabled",true);
                    $("#port").val("").prop("disabled",true);
                }else {
                    $("#serverIp").val("").prop("disabled",false);
                    $("#clientIp").val("").prop("disabled",false);
                    $("#ip").val("").prop("disabled",false);
                    $("#port").val("").prop("disabled",false);
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
            $("#dataExtractOK").click(function() {
                var starttime = $("#starttime").val(),
                    endtime = $("#endtime").val();
                if(!starttime || !endtime) {
                    jeBox.alert("开始时间或结束时间不能为空");
                } else {
                    starttime = $.timeStampDate(starttime);
                    endtime = $.timeStampDate(endtime);
                    var watchpointId = $("#watchpoint").val(),
                        serverIp = $("#serverIp").val(),
                        clientIp = $("#clientIp").val(),
                        ip = $("#ip").val(),
                        port = $("#port").val(),
                        bpf = $("#bpf").val(),
                        busiIdTypeSelectId = $("#busiIdTypeSelect").children("option:selected").attr("data-id"),
                        buSiIdSelectId = $("#buSiIdSelect").children("option:selected").attr("data-id"),
                        aGreementVal = $("#aGreement").children("option:selected").attr("value");
                    var params = {
                        starttime: starttime,
                        endtime: endtime,
                        ipmCenterId: 1,
                        historyGetFlow: 1
                    };
                    if(watchpointId){
                        params.watchpointIds = watchpointId.join(",");
                    }else {
                        params.watchpointIds = 0;
                    }
                    if(serverIp){
                        params.serverip = serverIp;
                        if(!clientIp){
                            jeBox.alert("请输入客户端IP");
                            return;
                        }
                    }
                    if(clientIp){
                        // params.clientIpStr = clientIp;
                        params.clientip = clientIp;
                        if(!serverIp){
                            jeBox.alert("请输入服务端IP");
                            return;
                        }
                    }
                    if(ip) {
                        params.ipStr = ip;
                    }
                    if(port) {
                        params.serverport = port;
                    }
                    if(bpf) {
                        params.bpf = bpf;
                    }
                    if(busiIdTypeSelectId){
                        params.moduleId = busiIdTypeSelectId;
                    }
                    if(buSiIdSelectId){
                        params.businessId = buSiIdSelectId;
                    }
                    if(aGreementVal){
                        params.protocolStr = aGreementVal;
                    }
                    var _this = $(this);
                    if(!$(this).hasClass("groupKpiCanUsed")){
                        _this.button("loading");
                        _this.addClass("groupKpiCanUsed");
                        var authInfo = {};
                        $.ajax({
                            async: false,
                            url: "/authorizeModuleController/getSelectIsOpen.do",
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                authInfo = data;
                            }
                        });
                        $.ajax({
                            url:"/commpairFlow/historyExtract.do",
                            type:"post",
                            data:params,
                            dataType:"json",
                            success:function (data) {
                                if(data.typeId != "0") {
                                    jeBox.alert("暂无数据");
                                    _this.button("reset");
                                    _this.removeClass("groupKpiCanUsed")
                                } else {
                                    var tabPackRow,
                                        folderName = data.folderName,
                                        html = '<div class="form-horizontal">' +
                                            '<div class="row"><div class="col-md-12">' +
                                            '<div id="tableLoadbox" style="cursor: wait;' +
                                            'width:auto;height:57px;line-height: 57px;padding-left: 50px;' +
                                            'padding-right: 5px;color:#696969;font-family:Microsoft YaHei;' +
                                            'background: #ddd;">' +
                                            '<img src="../images/gif.gif" style="position: relative;left:-20px">'+
                                            '正在请求数据，请您耐心等候。。。</div>'+
                                            '</div></div></div>' +
                                            '<div class="form-horizontal">'+
                                            '<div class="row"><div class="col-md-12">' +
                                            '<table id="tabPackDownload"></table>'+
                                            '</div></div>'+
                                            '</div>';
                                    var downToolbar = [{
                                        name: "下载",
                                        type: "download-alt",
                                        call: function () {
                                            window.open("/commpairFlow/listExtract.do?fileName=" + tabPackRow.name+"&folderName="+folderName);
                                        }
                                    }],
                                    digger = {
                                        name:"在线解析",
                                        type:"arrow-down",
                                        call: function () {
                                            var url = '/cgi-bin/iDigger.cgi?filename=' +folderName+"/"+ tabPackRow.name
                                                + '&ip=' + location.host.split(":")[0];
                                            var tmp = window.open("about:blank", "", "fullscreen=1");
                                            tmp.moveTo(0, 0);
                                            tmp.resizeTo(screen.width + 20, screen.height);
                                            tmp.focus();
                                            tmp.location = url;
                                        }
                                    };
                                    if (authInfo.digger) {
                                        downToolbar.push(digger);
                                    }
                                    $("#packDownload>.modal-dialog>.modal-content>.modal-body").html(html);
                                    $("#packDownload").modal("show");
                                    $.ptcsBSTable("tabPackDownload","/commpairFlow/historyNameList.do",{
                                        folderName:folderName
                                    },{
                                        columns: [
                                            {
                                                field: "name",
                                                title: "文件名称",
                                                sortable: true
                                            }
                                        ],
                                        responseHandler:function(data){
                                            var cusData = data.fileName?data.fileName:[{}];
                                            cusData[0].nodataFlag = data.nodataFlag;
                                            cusData[0].finishFlag = data.finishFlag;
                                            return cusData
                                        },
                                        ipm_title: "",
                                        ipm_shrink: false,
                                        rowStyle:function (row,i) {
                                            var cla = {};
                                            if(i == 0) {
                                                cla.classes = "custom-row-style";
                                                tabPackRow = row;
                                            }
                                            cla.css = { "white-space" : "nowrap" };
                                            return cla;
                                        },
                                        onClickRow:function (row,tr) {
                                            $("#tabPackDownload > tbody > .custom-row-style").removeClass();
                                            $(tr).addClass("custom-row-style");
                                            tabPackRow = row;
                                        },
                                        ipm_toolbar: downToolbar,
                                        onLoadSuccess:function (data) {
                                            /*
                                             * fileName  ：[{},{}]
                                             * finishFlag:  0-未结束  1-结束
                                             * nodataFlag:  0-有数据  1-无数据
                                             */
                                            if(data[0].nodataFlag){
                                                //无数据
                                                $("#packDownload .modal-body").html("无数据");
                                            }else {
                                                //有数据
                                                if(data[0].finishFlag){
                                                    //结束
                                                    $("#tableLoadbox").hide();
                                                }else {
                                                    //未结束
                                                    setTimeout(function () {
                                                        if($("#packDownload").hasClass("in")){
                                                            $.ptcsBSTabRefresh("tabPackDownload",{
                                                                folderName:folderName
                                                            });
                                                        }
                                                    },15000)
                                                }
                                            }
                                            setTimeout(function () {
                                                _this.button("reset");
                                                _this.removeClass("groupKpiCanUsed")
                                            },3000);
                                        },
                                        onLoadError:function () {
                                            _this.button("reset");
                                            _this.removeClass("groupKpiCanUsed")
                                        }
                                    });
                                }
                            },
                            error:function () {
                                jeBox.alert("暂无数据");
                                _this.button("reset");
                                _this.removeClass("groupKpiCanUsed")
                            }
                        });
                    }
                }
            });
            //在线解析
            function oldDCOld() {
                $("#analysis").click(function() {
                    var starttime = $("#starttime").val(),
                        endtime = $("#endtime").val();
                    if(!starttime || !endtime) {
                        jeBox.alert("开始时间或结束时间不能为空");
                    } else {
                        starttime = $.timeStampDate(starttime);
                        endtime = $.timeStampDate(endtime);
                        var watchpointId = $("#watchpoint").val(),
                            serverIp = $("#serverIp").val(),
                            clientIp = $("#clientIp").val(),
                            ip = $("#ip").val(),
                            port = $("#port").val(),
                            bpf = $("#bpf").val(),
                            busiIdTypeSelectId = $("#busiIdTypeSelect").children("option:selected").attr("data-id"),
                            buSiIdSelectId = $("#buSiIdSelect").children("option:selected").attr("data-id");
                        var params = {
                            starttime: starttime,
                            endtime: endtime,
                            historyGetFlow: 1
                        };
                        if(watchpointId){
                            params.watchpointIds = watchpointId.join(",");
                        }else {
                            params.watchpointIds = 0;
                        }
                        if(serverIp){
                            params.serverip = serverIp;
                            if(!clientIp){
                                jeBox.alert("请输入客户端IP");
                                return;
                            }
                        }
                        if(clientIp){
                            params.clientip = clientIp;
                            if(!serverIp){
                                jeBox.alert("请输入服务端IP");
                                return;
                            }
                        }
                        if(ip) {
                            params.ipStr = ip;
                        }
                        if(port) {
                            params.serverport = port;
                        }
                        if(bpf) {
                            params.bpf = bpf;
                        }
                        if(busiIdTypeSelectId){
                            params.moduleId = busiIdTypeSelectId;
                        }
                        if(buSiIdSelectId){
                            params.businessId = buSiIdSelectId;
                        }
                        var _this = $(this).button("loading");
                        $.ajax({
                            url: "/commpairFlow/historyExtract.do",
                            data: params,
                            async:false,
                            dataType: "json",
                            method: "POST",
                            success: function(data) {
                                if(data.typeId != 0) {
                                    jeBox.alert("暂无数据");
                                } else {
                                    var fname = data.fileName,
                                        url='/cgi-bin/iDigger.cgi?filename=' + fname +'&ip='+location.host.split(":")[0],
                                        tmp=window.open("about:blank","","fullscreen=1");
                                    tmp.moveTo(0,0);
                                    tmp.resizeTo(screen.width+20,screen.height);
                                    tmp.focus();
                                    tmp.location=url;
                                }
                                _this.button("reset");
                            },
                            error: function() {
                                jeBox.alert("暂无数据");
                                _this.button("reset");
                            }
                        });
                    }
                });
            }
        },
        flSet4:function(){
            var columns = [
                { field: "name", title: "文件名称", sortable: true },
                { field: "type", title: "类型", sortable: true, formatter: function(v) {
                    var o = null;
                    switch(parseInt(v)) {
                        case 1:
                            o = "文件夹";
                            break;
                        case 2:
                            o = "文件";
                            break;
                    }
                    return o;
                } },
                { field: "size", title: "文件大小", sortable: true, formatter: function(v) {
                    var o = numForUtil(v, "flow");
                    return o.value + o.unit;
                } },
                { field: "time", title: "时间", sortable: true, formatter: function(v) {
                    return $.timeStampDate(parseInt(v / 1000), "YYYY-MM-DD hh:mm:ss");
                } }
            ], selectRow = null, selectDir = null;
            $.ptcsBSTable("downloadFileTable", "/storage/getDownloadFileList.do", null, {
                pageSize: 10,
                columns: columns,
                ipm_title: "数据提取列表",
                ipm_shrink: true,
                rowStyle: function(row, i) {
                    var cla = {};
                    if(i == 0) {
                        cla.classes = "custom-row-style";
                        selectRow = row;
                    }
                    return cla;
                },
                onClickRow: function(row, tr) {
                    $("#downloadFileTable > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = row;
                },
                onDblClickRow: function(row) {
                	selectRow = row;
                    if (selectRow.type == 1) {
                    	selectDir = selectRow.name;
                        downloadFileDetaTable();
                    } else {
                    	selectDir = null;
                        $("#downloadFileDetaTableDiv").hide("slow");
                    }
                    if($("#tableHover").children("div").length - 1) {
                        $("#content").animate({
							scrollTop : $("#content").outerHeight()
							    + $("#tableHover").children("div").outerHeight()
						}, 800);
                    }
                },
                ipm_toolbar: [
                    { name: "下载", type: "download-alt", call: function(e) {
                        if(selectRow) {
                        	if (selectRow.type == 2) {
                                window.open("/commpairFlow/listExtract.do?fileName=" + selectRow.name);
                        	} else {
                                jeBox.alert("请选择一个文件 下载，请双击该行钻取");
                        	}
                        } else {
                            jeBox.alert("请选择一个文件 下载，请双击该行钻取");
                        }
                    } }
                ]
            });
            
            var downloadFileDetaTable = function() {
                if($("#downloadFileDetaTableDiv").length == 0) {
                    var selectRow = null;
                    $("#tableHover").append("<div id='downloadFileDetaTableDiv'><table id ='downloadFileDetaTable'></table></div>");
                    $.ptcsBSTable("downloadFileDetaTable", "/storage/getDownloadFileList.do", { dir: selectDir }, {
                        columns: columns,
                        ipm_title: "数据详细列表",
                        ipm_shrink: false,
                        rowStyle: function(row, i) {
                            var cla = {};
                            if(i == 0) {
                                cla.classes = "custom-row-style";
                                selectRow = row;
                            }
                            cla.css = { "white-space" : "nowrap" };
                            return cla;
                        },
                        onClickRow: function(row, tr) {
                            $("#downloadFileDetaTable > tbody > .custom-row-style").removeClass();
                            $(tr).addClass("custom-row-style");
                            selectRow = row;
                        },
                        ipm_toolbar: [{ name: "下载", type: "download-alt", call: function() {
                            window.open("/commpairFlow/listExtract.do?folderName=" + selectDir + "&fileName=" + selectRow.name);
                        }}]
                    });
                } else {
                    $("#downloadFileDetaTableDiv").show("slow");
                    $.ptcsBSTabRefresh("downloadFileDetaTable", { dir: selectDir });
                }
            }
        }
    };
    switch (location.pathname.split(".")[0].replace(/\//,"")){
        case "flowmanageSetting":
            floSet.flSet1();
            break;
        case "flowmanageSetting2":
            floSet.flSet2();
            break;
        case "flowmanageSetting3":
            floSet.flSet3();
            break;
        case "flowmanageSetting4":
            floSet.flSet4();
            break;
        default:
            jeBox.alert("未书写此页面的js代码");
    }
});