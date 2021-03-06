/**
 * Created by yanb on 2018/1/24.
 * 因需求突然的变更 这些本不在同一页面的表格给拼凑到咯
 * 同一页面 所以代码如下。。。。。。
 * 本这些代码可以走同一套代码就行。有时间再精减它
 * 可以做成一个方法 传各种不同的参数 后端控制显示
 * 若后端让显示则传参调用此方法显示表格
 * 若后端不让显示则不调用表格无法显示
 *
 * 2018/09/28新增功能，增改增加IP重复后端验证
 * 服务端、客户端、HTTP、三个数据库、url、报文
 * {"state" : 1 成功 | 0 失败 | 2 与其他业务IP重复}
 *  [{
        "namezh": "观察点",
        "id": "10",
        "nameen": "WATCHPOINT"
    }, {
        "namezh": "服务端",
        "id": "12",
        "nameen": "SERVER"
    }, {
        "namezh": "客户端",
        "id": "11",
        "nameen": "CLIENT"
    }, {
        "namezh": "HTTP服务",
        "id": "4",
        "nameen": "HTTP"
    }, {
        "namezh": "ORACLE服务",
        "id": "5",
        "nameen": "ORACLE"
    }, {
        "namezh": "MYSQL服务",
        "id": "6",
        "nameen": "MYSQL"
    }, {
        "namezh": "SQLSERVER服务",
        "id": "7",
        "nameen": "SQLSERVER"
    }, {
        "namezh": "URL服务",
        "id": "8",
        "nameen": "URL"
    }, {
        "namezh": "报文服务",
        "id": "9",
        "nameen": "MESSAGE"
    }, {
        "namezh": "网络",
        "id": "13",
        "nameen": "NETWORK"
    }, {
        "namezh": "应用可用性",
        "id": "1",
        "nameen": "USABILITY"
    }, {
        "namezh": "用户管理",
        "id": "3",
        "nameen": "CENTER"
    }]
 *
 */
;
$(function () {
    ~function () {
        //用户设置
        var selectRow;
        $.ptcsBSTable("cenTer","/saasuser/getAllUser.do",null,{
            pageSize: 10,
            columns:[
                {
                    field:"name",
                    title:"用户名",
                    sortable:true
                },
                {
                    field:"ip",
                    title:"IP",
                    sortable:true
                },
                {
                    field:"port",
                    title:"端口",
                    sortable:true
                },
                {
                    field:"contacts",
                    title:"联系人",
                    sortable:true
                },
                {
                    field:"telephone",
                    title:"电话",
                    sortable:true
                },
                {
                    field:"email",
                    title:"邮件",
                    sortable:true
                },
                {
                    field:"uptime",
                    title:"上线时间",
                    sortable:true,
                    formatter: function (value, row, index) {
                        return $.myTime.UnixToDate(value).split(" ")[0]
                    }
                },
                {
                    field:"limitdate",
                    title:"服务时限",
                    sortable:true,
                    formatter: function (value, row, index) {
                        return $.myTime.UnixToDate(value).split(" ")[0]
                    }
                },
                {
                    field:"state",
                    title:"状态",
                    sortable:true,
                    formatter: function (value, row, index) {
                        var text;
                        if(value-1){
                            text = "测试用户";
                        }else {
                            text = "正式用户";
                        }
                        return text;
                    }
                },
                {
                    field:"version",
                    title:"硬件版本",
                    sortable:true,
                    formatter: function (value, row, index) {
                        var text;
                        switch (+value){
                            case 1:
                                text = "专线宝B";
                                break;
                            case 2:
                                text = "专线宝U";
                                break;
                            case 3:
                                text = "专线宝S";
                                break;
                            case 4:
                                text = "XPM管理平台";
                                break;
                        }
                        return text;
                    }
                },
                {
                    field:"delay",
                    title:"通信时延",
                    sortable:true,
                    visible:false,
                    formatter:function (value,row,index) {
                        return value + "ms"
                    }
                }
            ],
            ipm_title: "XPM服务器管理",
            ipm_shrink: true,
            ipm_show: false,
            ipm_column_save: true,
            rowStyle: function (row, i) {
                var cla = {};
                if (i == 0) {
                    cla.classes = "custom-row-style";
                    selectRow = row;
                }
                return cla;
            },
            ipm_toolbar:[
                {
                    name: "新增",
                    type: "plus",
                    call: function (e) {
                        $.JaddbsModalRow(
                            ["用户名", "IP", "端口", "联系人","电话",
                                "Email", "上线时间","服务时限","状态","硬件版本"],
                            "cenTer",null, "cenTer");
                    }
                },
                {
                    name: "修改",
                    type: "edit",
                    call: function (e) {
                        if (selectRow == null) {
                            jeBox.alert('请先添加用户');
                            return;
                        }
                        $.JeditRowModal("#cenTer", "/saasuser/getAllUser.do",
                            ["name", "ip", "port", "contacts","telephone","email","uptime","limitdate",
                                "state","version"],
                            ["用户名", "IP", "端口", "联系人","电话",
                                "Email", "上线时间","服务时限","状态","硬件版本"],
                            "cenTer", null, "cenTer");
                    }
                },
                {
                    name: "删除",
                    type: "remove",
                    call: function (e) {
                        if (selectRow == null) {
                            jeBox.alert('请先添加用户');
                            return;
                        }
                        if ($("#cenTer .custom-row-style").attr("data-id") != "1" &&
                            $("#cenTer .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr", "cenTer");
                            $("#Confirm-modal").modal("show");
                        }else {
                            if($("#cenTer .custom-row-style").attr("data-id") == "1" ){
                                jeBox.alert("此用户不可删除");
                            }
                        }
                    }
                }
            ],
            onClickRow: function (row, tr) {
                $("#cenTer > tbody > .custom-row-style").removeClass();
                $(tr).addClass("custom-row-style");
                selectRow = row;
            },
            onLoadSuccess:function () {},
            onLoadError:function () {}
        });
        $("#btn-addtableRow").click(function () {
            if ($("#addtableRow-modal").attr("data-tableAttr") == "cenTer") {
                $.JaddbsTableRow("#cenTer", "/saasuser/addSaasUser.do",
                    "/saasuser/getAllUser.do", "cenTer")
            }
        });
        $("#btn-changetableRow").click(function () {
            if ($("#changetableRow-modal").attr("data-tableAttr") == "cenTer") {
                $.JediTableRow("#cenTer", "/saasuser/updSaasUser.do", "cenTer");
            }
        });
        $("#btn-ConfirmdelRow").click(function () {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if ($("#Confirm-modal").attr("data-tableAttr") == "cenTer") {
                    $.JremovebsTableRow("#cenTer", "/saasuser/delSaasUser.do", "observationPoint",null,null,_this);
                    selectRow = null;
                }
            }
        });
    }(),
    function () {
        //观察点
        var columns = [{
                    field: "name",
                    title: "观察点名称",
                    sortable: true
                },
                {
                    field: "did",
                    title: "网卡名",
                    sortable: true
                },
                {
                    field: "vid",
                    title: "VLAN ID",
                    sortable: true
                },
                {
                    field: "vxid",
                    title: "VXLAN ID",
                    sortable: true
                },
                {
                    field: "lid1",
                    title: "MPLS LABLE1",
                    sortable: true
                },
                {
                    field: "lid2",
                    title: "MPLS LABLE2",
                    sortable: true
                },
                {
                    field: "lid3",
                    title: "MPLS LABLE3",
                    sortable: true
                },
                {
                    field: "lid4",
                    title: "MPLS LABLE4",
                    sortable: true
                },
                {
                    field: "lid5",
                    title: "MPLS LABLE5",
                    sortable: true
                },
                // {
                //     field: "lid",
                //     title: "VXLAN ID",
                //     sortable: true
                // },
                {
                    field: "bandwidth",
                    title: "带宽[Mb]",
                    sortable: true
                }
            ],
            selectRow = null,
            alarmSelectRow = null,
            alarmHide = true;
        $.ptcsBSTable("oberSet", "watchpointController/getFindAll.do", {
            "moduleId": 10
        }, {
            pageSize: 10,
            columns: columns,
            ipm_title: "观察点管理与设置",
            ipm_shrink: true,
            ipm_show: false,
            rowStyle: function (row, i) {
                var cla = {};
                if (i == 0) {
                    cla.classes = "custom-row-style";
                    selectRow = row;
                }
                return cla;
            },
            onClickRow: function (row, tr) {
                $("#oberSet > tbody > .custom-row-style").removeClass();
                $(tr).addClass("custom-row-style");
                selectRow = row;
                if (!alarmHide) {
                    $.ptcsBSTabRefresh("obserkpiSetTab", {
                        "watchpointId": selectRow.id,
                        "businessId": selectRow.id,
                        "moduleId": 10
                    });
                }
            },
            ipm_toolbar: [{
                    name: "告警设置",
                    type: "bell",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加观察点');
							return;
						}
                        if(selectRow && selectRow.id){
                            alarmTable();
                        }
                    }
                },
                {
                    name: "新增",
                    type: "plus",
                    call: function (e) {
                        if(
                            $("#header").attr("data-manywatchpoint") == undefined ||
                            +$("#header").attr("data-manywatchpoint") ||
                            $("#oberSet").bootstrapTable("getData").length < 1
                        ){
                            $.JaddbsModalRow(["名称", "网卡名", "VLAN ID", "VXLAN ID","MPLS LABLE1",
                                    "MPLS LABLE2", "MPLS LABLE3","MPLS LABLE4","MPLS LABLE5","带宽[Mb]"],
                                "observationPoint", "watchpointController/getNetworkAll.do", "oberSet");
                        }else {
                            jeBox.alert("观察点已达上限不可添加");
                        }
                    }
                },
                {
                    name: "修改",
                    type: "edit",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加观察点');
							return;
						}
                        $.JeditRowModal("#oberSet", "watchpointController/getFindAll.do",
                            ["name", "did", "vid", "vxid","lid1","lid2","lid3","lid4","lid5","bandwidth"],
                            ["名称", "网卡名", "VLAN ID", "VXLAN ID","MPLS LABLE1",
                                "MPLS LABLE2", "MPLS LABLE3","MPLS LABLE4","MPLS LABLE5", "带宽[Mb]"],
                            "observationPoint", "watchpointController/getNetworkAll.do", "oberSet");
                    }
                },
                {
                    name: "删除",
                    type: "remove",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加观察点');
							return;
						}
                        if ($("#oberSet .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr", "oberSet");
                            $("#Confirm-modal").modal("show");
                        }
                    }
                }
            ]
        });
        var alarmTable = function () {
            if (alarmHide) {
                alarmHide = false;
                $("#oberSet").parents(".table-responsive").next().addClass("overflowAlerm tableMinHH").removeClass("none");
                if(!$("#obserkpiSetTab").length){
                    $(".obserkpiSetTab").append('<table id="obserkpiSetTab"></table>');
                }
                $.post("/alarmSet/getAlarmSetTitle.do", null, function (data) {
                    var kpiColumns = [];
                    data.forEach(function (item, index) {
                        if (item.nameen != "id") {
                            if (item.nameen == "name") {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh,
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        if (value && value != "" && value != "-") {
                                            // return value + "[" + row.kpiUnit + "]";
                                            return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                                        }
                                    }
                                })
                            } else {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh + "[低]",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var valText = value.single.lowThresh;
                                        if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                            // return valText;
                                            return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                        }
                                    }
                                })
                            }
                        }
                    });
                    data.forEach(function (item, index) {
                        if (item.nameen != "id" && item.nameen != "name") {
                            kpiColumns.push({
                                field: item.nameen + "Value",
                                title: item.namezh + "[高]",
                                sortable: true,
                                formatter: function (value, row, index) {
                                    var valText = value.single.highThresh;
                                    if (typeof valText === "number" || (valText && valText != "" && valText != "-")) {
                                        // return valText;
                                        return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                    }
                                }
                            })
                        }
                    });
                    $.ptcsBSTable("obserkpiSetTab", "/alarmSet/getAlarmSetData.do",
                        {
                        "watchpointId": selectRow.id,
                        "businessId": selectRow.id,
                        "moduleId": 10
                        },
                        {
                            pageSize: 15,
                            columns: kpiColumns,
                            showColumns: false,
                            ipm_title: "观察点告警设置",
                            ipm_shrink: true,
                            rowStyle: function (row, i) {
                            var cla = {};
                            if (i == 0) {
                                cla.classes = "custom-row-style";
                                alarmSelectRow = row;
                            }
                            return cla;
                        },
                            onClickRow: function (row, tr) {
                            $("#obserkpiSetTab > tbody > .custom-row-style").removeClass();
                            $(tr).addClass("custom-row-style");
                            alarmSelectRow = row;
                        },
                            ipm_toolbar: [{
                                name: "修改",
                                type: "edit",
                                call: function (e) {
                                    $.JeditKpisetModal("#obserkpiSetTab", "/alarmSet/getAlarmSetTitle.do",
                                        "/alarmSet/getAlarmSetData.do", selectRow.id, selectRow.id, 10, alarmSelectRow.id, "oberSet");
                                }
                            }
                            // {
                            //     name: "组合告警",
                            //     type: "asterisk",
                            //     call: function (e) {
                            //         $.JzuheKpisetModal("#alarmZhtab",
                            //             "/customUnionKpi/getAlarmCustomUnionKpiList.do", {
                            //                 "watchpointId": selectRow.id,
                            //                 "businessId": selectRow.id,
                            //                 "moduleId": 10
                            //             }, [{
                            //                     field: "groupKpiSelected",
                            //                     checkbox: true,
                            //                     formatter: function (value, row, index) {
                            //                         return {
                            //                             disabled: !Boolean(row.groupKpiCanUsed), //设置是否可用
                            //                             checked: Boolean(row.groupKpiSelected) //设置选中
                            //                         };
                            //                     }
                            //                 },
                            //                 {
                            //                     field: "nameValue",
                            //                     title: "KPI名称",
                            //                     sortable: true,
                            //                     formatter: function (value, row, index) {
                            //                         if (value && value != "" && value != "-") {
                            //                             // return value + "[" + row.kpiUnit + "]";
                            //                             return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                            //                         }
                            //                     }
                            //                 }
                            //             ],
                            //             "oberSet")
                            //     }
                            // }
                        ]
                        });
                }, "json");
                $("#content").animate({scrollTop :$("#obserkpiSetTab").offset().top+$("#content").scrollTop()-50},800);
            } else {
                alarmHide = true;
                $("#oberSet").parents(".table-responsive").next().addClass("none").removeClass("overflowAlerm tableMinHH");
                $(".obserkpiSetTab").children().remove();
            }
        };
        $("#btn-addtableRow").click(function () {
            if ($("#addtableRow-modal").attr("data-tableAttr") == "oberSet") {
                $.JaddbsTableRow("#oberSet", "watchpointController/getAddWatchpoint.do",
                    "watchpointController/getFindAll.do", "observationPoint")
            }
        });
        $("#btn-changetableRow").click(function () {
            if ($("#changetableRow-modal").attr("data-tableAttr") == "oberSet") {
                $.JediTableRow("#oberSet", "watchpointController/getUpdWatchpoint.do", "observationPoint");
            }
        });
        $("#btn-ConfirmdelRow").click(function () {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if ($("#Confirm-modal").attr("data-tableAttr") == "oberSet") {
                    $.JremovebsTableRow("#oberSet", "watchpointController/getdelFind.do", "observationPoint",null,null,_this);
                    selectRow = null;
                }
            }
        });
        $("#btn-ediKpitableRow").click(function () {
            var _t = $(this);
            if ($("#ediKpitableRow-modal").attr("data-tableAttr") == "oberSet" && !_t.prop("disabled")) {
                _t.prop("disabled","disabled");
                if ($("#ediKpitableRow-modal .addtableRowInput").length != 3) {
                    $.JeditKpiSetTable("#obserkpiSetTab", "/alarmSet/updateAlarmSet.do", {
                        "watchpointId": selectRow.id, //观察点ID
                        "moduleId": 10, //模块ID
                        "businessId": selectRow.id, //业务ID
                        "kpitype": 1, //传1
                        "kpiId": alarmSelectRow.id, //此条数据的id
                        "idList": alarmSelectRow.normalId + "," +
                        alarmSelectRow.importantId + "," +
                        alarmSelectRow.urgentId + "," +
                        alarmSelectRow.deviateId //对应的告警数值的I
                    },alarmSelectRow);
                } else {
                    $.JeditKpiSetTable("#obserkpiSetTab",
                        "/alarmSet/updateAlarmSet.do", {
                            "watchpointId": selectRow.id, //观察点ID
                            "moduleId": 10, //模块ID
                            "businessId": selectRow.id, //业务ID
                            "kpitype": 1, //传1
                            "kpiId": alarmSelectRow.id, //此条数据的id
                            "idList": alarmSelectRow.normalId + "," +
                            alarmSelectRow.importantId + "," +
                            alarmSelectRow.urgentId //对应的告警数值的I
                        },alarmSelectRow);
                }
            }
            setTimeout(function () {
                _t.removeAttr("disabled");
            },3000);
        });
        $("#btn-zuheKpitableRow").click(function () {
            if ($("#zuheKpitableRow-modal").attr("data-tableAttr") == "oberSet") {
                $.JzuheKpicommitData("/customUnionKpi/addAlarmCustomUnionKpis.do", {
                    "moduleId": 10, //模块ID
                    "businessId": selectRow.id //业务ID
                })
            }
        })
    }(),
    function () {
        //用户端
        var columns = [{
                    field: "name",
                    title: "客户端名称",
                    sortable: true
                },
                {
                    field: "displayIp",
                    title: "子网/网段",
                    sortable: true
                },
                {
                    field: "bandwidth",
                    title: "带宽[Mb]",
                    sortable: true
                },
                {
                    field: "descrption",
                    title: "备注",
                    sortable: true
                }
            ],
            selectRow = null,
            alarmSelectRow = null,
            alarmHide = true;
        $.ptcsBSTable("userSet", "/client/getClient.do", {
            moduleId: 11
        }, {
            pageSize: 10,
            columns: columns,
            ipm_title: "客户端管理与设置",
            ipm_shrink: true,
            ipm_show: false,
            rowStyle: function (row, i) {
                var cla = {};
                if (i == 0) {
                    cla.classes = "custom-row-style";
                    selectRow = row;
                }
                return cla;
            },
            onClickRow: function (row, tr) {
                $("#userSet > tbody > .custom-row-style").removeClass();
                $(tr).addClass("custom-row-style");
                selectRow = row;
                if (!alarmHide) {
                    $.ptcsBSTabRefresh("userkpiSetTab", {
                        "watchpointId": 0,
                        "businessId": selectRow.id,
                        "moduleId": 11
                    });
                }
            },
            ipm_toolbar: [{
                    name: "告警设置",
                    type: "bell",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加客户端');
							return;
						}
                        if(selectRow && selectRow.id){
                            alarmTable();
                        }
                    }
                },
                {
                    name: "新增",
                    type: "plus",
                    call: function (e) {
                        $.JaddbsModalRow(["名称", "子网/网段", "带宽[Mb]", "备注"], null, null, "userSet");
                    }
                },
                {
                    name: "修改",
                    type: "edit",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加客户端')
							return;
						}
                        $.JeditRowModal("#userSet", "/client/getClient.do?moduleId=11", ["name", "displayIp", "bandwidth", "descrption"], ["名称", "子网/网段", "带宽[Mb]", "备注"], null, null, "userSet");
                    }
                },
                {
                    name: "删除",
                    type: "remove",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加客户端')
							return;
						}
                        if ($("#userSet .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr", "userSet");
                            $("#Confirm-modal").modal("show");
                        }
                    }
                }
            ]
        });
        var alarmTable = function () {
            if (alarmHide) {
                alarmHide = false;
                $("#userSet").parents(".table-responsive").next().addClass("overflowAlerm tableMinHH").removeClass("none");
                if(!$("#userkpiSetTab").length){
                    $(".userkpiSetTab").append('<table id="userkpiSetTab"></table>');
                }
                $.post("/alarmSet/getAlarmSetTitle.do", null, function (data) {
                    var kpiColumns = [];
                    data.forEach(function (item, index) {
                        if (item.nameen != "id") {
                            if (item.nameen == "name") {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh,
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        if (value && value != "" && value != "-") {
                                            // return value + "[" + row.kpiUnit + "]";
                                            return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                                        }
                                    }
                                })
                            } else {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh + "[低]",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var valText = value.single.lowThresh;
                                        if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                            // return valText;
                                            return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                        }
                                    }
                                })
                            }
                        }
                    });
                    data.forEach(function (item, index) {
                        if (item.nameen != "id" && item.nameen != "name") {
                            kpiColumns.push({
                                field: item.nameen + "Value",
                                title: item.namezh + "[高]",
                                sortable: true,
                                formatter: function (value, row, index) {
                                    var valText = value.single.highThresh;
                                    if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                        // return valText;
                                        return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                    }
                                }
                            })
                        }
                    });
                    $.ptcsBSTable("userkpiSetTab", "/alarmSet/getAlarmSetData.do", {
                        "watchpointId": 0,
                        "businessId": selectRow.id,
                        "moduleId": 11
                    }, {
                        pageSize: 15,
                        columns: kpiColumns,
                        showColumns: false,
                        ipm_title: "客户端告警设置",
                        ipm_shrink: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if (i == 0) {
                                cla.classes = "custom-row-style";
                                alarmSelectRow = row;
                            }
                            return cla;
                        },
                        onClickRow: function (row, tr) {
                            $("#userkpiSetTab > tbody > .custom-row-style").removeClass();
                            $(tr).addClass("custom-row-style");
                            alarmSelectRow = row;
                        },
                        ipm_toolbar: [{
                            name: "修改",
                            type: "edit",
                            call: function (e) {
                                $.JeditKpisetModal("#userkpiSetTab", "/alarmSet/getAlarmSetTitle.do",
                                    "/alarmSet/getAlarmSetData.do",
                                    0, selectRow.id, 11, alarmSelectRow.id,
                                    "userSet");
                            }
                        }
                        // {
                        //     name: "组合告警",
                        //     type: "asterisk",
                        //     call: function (e) {
                        //         $.JzuheKpisetModal("#alarmZhtab",
                        //             "/customUnionKpi/getAlarmCustomUnionKpiList.do", {
                        //                 "watchpointId": selectRow.id,
                        //                 "businessId": selectRow.id,
                        //                 "moduleId": 11
                        //             }, [{
                        //                     field: "groupKpiSelected",
                        //                     checkbox: true,
                        //                     formatter: function (value, row, index) {
                        //                         return {
                        //                             disabled: !Boolean(row.groupKpiCanUsed), //设置是否可用
                        //                             checked: Boolean(row.groupKpiSelected) //设置选中
                        //                         };
                        //                     }
                        //                 },
                        //                 {
                        //                     field: "nameValue",
                        //                     title: "KPI名称",
                        //                     sortable: true,
                        //                     formatter: function (value, row, index) {
                        //                         if (value && value != "" && value != "-") {
                        //                             // return value + "[" + row.kpiUnit + "]";
                        //                             return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                        //                         }
                        //                     }
                        //                 }
                        //             ],
                        //             "userSet")
                        //     }
                        // }
                        ]
                    });
                }, "json");
                $("#content").animate({scrollTop :$("#userkpiSetTab").offset().top+$("#content").scrollTop()-50},800);
            } else {
                alarmHide = true;
                $("#userSet").parents(".table-responsive").next().addClass("none").removeClass("overflowAlerm tableMinHH");
                $(".userkpiSetTab").children().remove();
            }
        };
        $("#btn-addtableRow").click(function () {
            if ($("#addtableRow-modal").attr("data-tableAttr") == "userSet") {
                $.JaddbsTableRow("#userSet", "/client/addClient.do", "/client/getClient.do", "userSide",11);
            }
        });
        $("#btn-changetableRow").click(function () {
            if ($("#changetableRow-modal").attr("data-tableAttr") == "userSet") {
                $.JediTableRow("#userSet", "/client/updateClient.do", "userSide", "/client/getClient.do?moduleId=11",11);
            }
        });
        $("#btn-ConfirmdelRow").click(function () {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if ($("#Confirm-modal").attr("data-tableAttr") == "userSet") {
                    $.JremovebsTableRow("#userSet", "/client/delClient.do", "userSide", "/client/getClient.do?moduleId=11",11,_this);
                    selectRow = null;
                }
            }
        });
        $("#btn-ediKpitableRow").click(function () {
            var _t = $(this);
            if ($("#ediKpitableRow-modal").attr("data-tableAttr") == "userSet" && !_t.prop("disabled")) {
                _t.prop("disabled","disabled");
                if ($("#ediKpitableRow-modal .addtableRowInput").length != 3) {
                    $.JeditKpiSetTable("#userkpiSetTab", "/alarmSet/updateAlarmSet.do", {
                        "watchpointId": 0, //观察点ID
                        "moduleId": 11, //模块ID
                        "businessId": selectRow.id, //业务ID
                        "kpitype": 1, //传1
                        "kpiId": alarmSelectRow.id, //此条数据的id
                        "idList": alarmSelectRow.normalId + "," +
                        alarmSelectRow.importantId + "," +
                        alarmSelectRow.urgentId + "," +
                        alarmSelectRow.deviateId //对应的告警数值的I
                    },alarmSelectRow);
                } else {
                    $.JeditKpiSetTable("#userkpiSetTab", "/alarmSet/updateAlarmSet.do", {
                        "watchpointId": 0, //观察点ID
                        "moduleId": 11, //模块ID
                        "businessId": selectRow.id, //业务ID
                        "kpitype": 1, //传1
                        "kpiId": alarmSelectRow.id, //此条数据的id
                        "idList": alarmSelectRow.normalId + "," +
                        alarmSelectRow.importantId + "," +
                        alarmSelectRow.urgentId //对应的告警数值的I
                    },alarmSelectRow);
                }
            }
            setTimeout(function () {
                _t.removeAttr("disabled");
            },3000);
        });
        $("#btn-zuheKpitableRow").click(function () {
            if ($("#zuheKpitableRow-modal").attr("data-tableAttr") == "userSet") {
                $.JzuheKpicommitData("/customUnionKpi/addAlarmCustomUnionKpis.do", {
                    "moduleId": 11, //模块ID
                    "businessId": selectRow.id //业务ID
                })
            }
        })
    }(),
    function () {
        //服务端
        var columns = [{
                    field: "name",
                    title: "服务端名称",
                    sortable: true
                },
                {
                    field: "displayIp",
                    title: "服务端IP端口",
                    sortable: true
                },
                {
                    field: "bandwidth",
                    title: "带宽[Mb]",
                    sortable: true
                },
                {
                    field: "descrption",
                    title: "备注",
                    sortable: true
                }
            ],
            selectRow = null,
            alarmSelectRow = null,
            alarmHide = true;
        $.ptcsBSTable("serSet", "/serverManagement/getAllServerSide.do", {
            "moduleId": 12
        }, {
            pageSize: 10,
            columns: columns,
            ipm_title: "服务端管理与设置",
            ipm_shrink: true,
            ipm_show: false,
            rowStyle: function (row, i) {
                var cla = {};
                if (i == 0) {
                    cla.classes = "custom-row-style";
                    selectRow = row;
                }
                return cla;
            },
            onClickRow: function (row, tr) {
                $("#serSet > tbody > .custom-row-style").removeClass();
                $(tr).addClass("custom-row-style");
                selectRow = row;
                if (!alarmHide) {
                    $.ptcsBSTabRefresh("serkpiSetTab", {
                        "watchpointId": 0,
                        "businessId": selectRow.id,
                        "moduleId": 12
                    });
                }
            },
            ipm_toolbar: [{
                    name: "告警设置",
                    type: "bell",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加服务端')
							return;
						}
                        if(selectRow && selectRow.id){
                            alarmTable();
                        }
                    }
                },
                {
                    name: "新增",
                    type: "plus",
                    call: function (e) {
                        $.JaddbsModalRow(["名称", "服务端IP端口", "带宽[Mb]", "备注"], null, null, "serSet");
                    }
                },
                {
                    name: "修改",
                    type: "edit",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加服务端')
							return;
						}
                        $.JeditRowModal("#serSet", "/serverManagement/getAllServerSide.do", ["name", "displayIp", "bandwidth", "descrption"], ["名称", "服务端IP端口", "带宽[Mb]", "备注"], null, null, "serSet");
                    }
                },
                {
                    name: "删除",
                    type: "remove",
                    call: function (e) {
                    	if (selectRow == null) {
							jeBox.alert('请先添加服务端');
							return;
						}
                        if ($("#serSet .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr", "serSet");
                            $("#Confirm-modal").modal("show");
                        }
                    }
                }
            ]
        });
        var alarmTable = function () {
            if (alarmHide) {
                alarmHide = false;
                $("#serSet").parents(".table-responsive").next().addClass("overflowAlerm tableMinHH").removeClass("none");
                if(!$("#serkpiSetTab").length){
                    $(".serkpiSetTab").append('<table id="serkpiSetTab"></table>');
                }
                $.post("/alarmSet/getAlarmSetTitle.do", null, function (data) {
                    var kpiColumns = [];
                    data.forEach(function (item, index) {
                        if (item.nameen != "id") {
                            if (item.nameen == "name") {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh,
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        if (value && value != "" && value != "-") {
                                            // return value + "[" + row.kpiUnit + "]";
                                            return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                                        }
                                    }
                                })
                            } else {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh + "[低]",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var valText = value.single.lowThresh;
                                        if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                            // return valText;
                                            return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                        }
                                    }
                                })
                            }
                        }
                    });
                    data.forEach(function (item, index) {
                        if (item.nameen != "id" && item.nameen != "name") {
                            kpiColumns.push({
                                field: item.nameen + "Value",
                                title: item.namezh + "[高]",
                                sortable: true,
                                formatter: function (value, row, index) {
                                    var valText = value.single.highThresh;
                                    if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                        // return valText;
                                        return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                    }
                                }
                            })
                        }
                    });
                    $.ptcsBSTable("serkpiSetTab", "/alarmSet/getAlarmSetData.do", {
                        "watchpointId": 0,
                        "businessId": selectRow.id,
                        "moduleId": 12
                    }, {
                        pageSize: 15,
                        columns: kpiColumns,
                        showColumns: false,
                        ipm_title: "服务端告警设置",
                        ipm_shrink: true,
                        rowStyle: function (row, i) {
                            var cla = {};
                            if (i == 0) {
                                cla.classes = "custom-row-style";
                                alarmSelectRow = row;
                            }
                            return cla;
                        },
                        onClickRow: function (row, tr) {
                            $("#serkpiSetTab > tbody > .custom-row-style").removeClass();
                            $(tr).addClass("custom-row-style");
                            alarmSelectRow = row;
                        },
                        ipm_toolbar: [{
                            name: "修改",
                            type: "edit",
                            call: function (e) {
                                $.JeditKpisetModal("#serkpiSetTab", "/alarmSet/getAlarmSetTitle.do",
                                    "/alarmSet/getAlarmSetData.do", 0, selectRow.id, 12, alarmSelectRow.id, "serSet");
                            }
                        }
                        // {
                        //     name: "组合告警",
                        //     type: "asterisk",
                        //     call: function (e) {
                        //         $.JzuheKpisetModal("#alarmZhtab",
                        //             "/customUnionKpi/getAlarmCustomUnionKpiList.do", {
                        //                 "watchpointId": selectRow.id,
                        //                 "businessId": selectRow.id,
                        //                 "moduleId": 12
                        //             }, [{
                        //                     field: "groupKpiSelected",
                        //                     checkbox: true,
                        //                     formatter: function (value, row, index) {
                        //                         return {
                        //                             disabled: !Boolean(row.groupKpiCanUsed), //设置是否可用
                        //                             checked: Boolean(row.groupKpiSelected) //设置选中
                        //                         };
                        //                     }
                        //                 },
                        //                 {
                        //                     field: "nameValue",
                        //                     title: "KPI名称",
                        //                     sortable: true,
                        //                     formatter: function (value, row, index) {
                        //                         if (value && value != "" && value != "-") {
                        //                             // return value + "[" + row.kpiUnit + "]";
                        //                             return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                        //                         }
                        //                     }
                        //                 }
                        //             ],
                        //             "serSet")
                        //     }
                        // }
                        ]
                    });
                }, "json");
                $("#content").animate({scrollTop :$("#serkpiSetTab").offset().top+$("#content").scrollTop()-50},800);
            } else {
                alarmHide = true;
                $("#serSet").parents(".table-responsive").next().addClass("none").removeClass("overflowAlerm tableMinHH");
                $(".serkpiSetTab").children().remove();
            }
        };
        $("#btn-addtableRow").click(function () {
            if ($("#addtableRow-modal").attr("data-tableAttr") == "serSet") {
                $.JaddbsTableRow("#serSet", " /serverManagement/addServerSide.do",
                    "/serverManagement/getAllServerSide.do", "serverSide",12)
            }
        });
        $("#btn-changetableRow").click(function () {
            if ($("#changetableRow-modal").attr("data-tableAttr") == "serSet") {
                $.JediTableRow("#serSet", "/serverManagement/updateServerSide.do", "serverSide",null,12);
            }
        });
        $("#btn-ConfirmdelRow").click(function () {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if ($("#Confirm-modal").attr("data-tableAttr") == "serSet") {
                    $.JremovebsTableRow("#serSet", "/serverManagement/deleteServerSide.do", "serverSide",null,12,_this);
                    selectRow = null;
                }
            }
        });
        $("#btn-ediKpitableRow").click(function () {
            var _t = $(this);
            if ($("#ediKpitableRow-modal").attr("data-tableAttr") == "serSet" && !_t.prop("disabled")) {
                _t.prop("disabled","disabled");
                if ($("#ediKpitableRow-modal .addtableRowInput").length != 3) {
                    $.JeditKpiSetTable("#serkpiSetTab", "/alarmSet/updateAlarmSet.do", {
                        "watchpointId": 0, //观察点ID
                        "moduleId": 12, //模块ID
                        "businessId": selectRow.id, //业务ID
                        "kpitype": 1, //传1
                        "kpiId": alarmSelectRow.id, //此条数据的id
                        "idList": alarmSelectRow.normalId + "," +
                        alarmSelectRow.importantId + "," +
                        alarmSelectRow.urgentId + "," +
                        alarmSelectRow.deviateId //对应的告警数值的I
                    },alarmSelectRow);
                } else {
                    $.JeditKpiSetTable("#serkpiSetTab", "/alarmSet/updateAlarmSet.do", {
                        "watchpointId": 0, //观察点ID
                        "moduleId": 12, //模块ID
                        "businessId": selectRow.id, //业务ID
                        "kpitype": 1, //传1
                        "kpiId": alarmSelectRow.id, //此条数据的id
                        "idList": alarmSelectRow.normalId + "," +
                        alarmSelectRow.importantId + "," +
                        alarmSelectRow.urgentId //对应的告警数值的I
                    },alarmSelectRow);
                }
            }
            setTimeout(function () {
                _t.removeAttr("disabled");
            },3000);
        });
        $("#btn-zuheKpitableRow").click(function () {
            if ($("#zuheKpitableRow-modal").attr("data-tableAttr") == "serSet") {
                $.JzuheKpicommitData("/customUnionKpi/addAlarmCustomUnionKpis.do", {
                    "moduleId": 12, //模块ID
                    "businessId": selectRow.id //业务ID
                })
            }
        })
    }(),
    function () {
        //HTTP服务 oracle服务  mySql服务 sqlServer服务 报文交易
        function table(Name, ModuleId, tableId, tableAlarmId,_columns) {
            var ipm_toolbar = [
                    {
                        name: "告警设置",
                        type: "bell",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert("请先添加"+Name);
								return;
						    }
                            if(selectRow && selectRow.id){
                                alarmTable();
                            }
                        }
                    },
                    {
                        name: "新增",
                        type: "plus",
                        call: function (e) {
                            // $.JaddbsModalRow(["名称", "IP端口", "备注"], tableId, null, tableId);
                            if(tableId == "baowenJySer"){
                                var _titleText = [
                                    "名称",
                                    "服务端IP端口",
                                    "客户端IP",
                                    "交易返回码",
                                    "成功返回码",
                                    "失败返回码"
                                ];
                                for (var i = 0; i < 20; i++) {
                                    var title = "自定义列" + i;
                                    _titleText.push(title);
                                }
                                $.JaddbsModalRow(_titleText, tableId, null, tableId);
                            }else {
                                $.JaddbsModalRow(["名称", "IP端口", "备注"], tableId, null, tableId);
                            }
                        }
                    },
                    {
                        name: "修改",
                        type: "edit",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert("请先添加"+Name);
								return;
						    }else {
                                // $.JeditRowModal("#" + tableId,
                                //     "/appController/getAllAppByModuleId.do",
                                //     ["name", "displayIp", "descrption"],
                                //     ["名称", "服务端IP端口", "备注"],
                                //     tableId, null, tableId, {
                                //         moduleId: ModuleId
                                //     });

                                if(tableId == "baowenJySer"){
                                    var _titleText = [
                                        "名称",
                                        "服务端IP端口",
                                        "客户端IP",
                                        "交易返回码",
                                        "成功返回码",
                                        "失败返回码"
                                    ],
                                        fieldArry = [
                                            "name",
                                            "displayIp",
                                            "clientIpport",
                                            "statusCode",
                                            "successCode",
                                            "failedCode"
                                        ];
                                    for (var i = 0; i < 20; i++) {
                                        var title = "自定义列" + i,
                                            field = "busTag" + i ;
                                        _titleText.push(title);
                                        fieldArry.push(field);
                                    }
                                    // $.JeditRowModal("#" + tableId,
                                    //     "/appController/getAllAppByModuleId.do",
                                    //     fieldArry,
                                    //     _titleText,
                                    //     tableId, null, tableId, {
                                    //         moduleId: ModuleId
                                    //     });
                                    $.JeditRowModal("#" + tableId,
                                        "/depthanaly/selectAll.do",
                                        fieldArry,
                                        _titleText,
                                        tableId, null, tableId, {
                                            moduleId: ModuleId
                                        });
                                }else {
                                    $.JeditRowModal("#" + tableId,
                                        "/appController/getAllAppByModuleId.do",
                                        ["name", "displayIp", "descrption"],
                                        ["名称", "服务端IP端口", "备注"],
                                        tableId, null, tableId, {
                                            moduleId: ModuleId
                                        });
                                }
                            }
                        }
                    },
                    {
                        name: "删除",
                        type: "remove",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert("请先添加"+Name);
								return;
						    }
                            if ($("#" + tableId + " .custom-row-style").attr("data-id") != undefined) {
                                $("#Confirm-modal").attr("data-tableAttr", tableId);
                                $("#Confirm-modal").modal("show");
                            }
                        }
                    }
                ],
                selectRow = null,
                alarmSelectRow = null,
                alarmHide = true;
            if (tableId == "webSer") {
                var typeClass,
                    nameText;
                $.ajax({
                    url: "/sysNetworkSet/getOnOffHttpLoad.do",
                    method: "POST",
                    async: false,
                    data: {},
                    dataType: "json",
                    beforeSend:function (XMLHttpRequest) {},
                    success: function (data,textStatus,XMLHttpRequest) {
                        if (data.state) {
                            // 1 开启状态remove-sign
                            typeClass = "ok-sign";
                            // nameText = "开启";
                            nameText = "关闭";
                        } else {
                            // 默认0 是关闭状态 ok-sign
                            typeClass = "remove-sign";
                            // nameText = "关闭";
                            nameText = "开启";
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
                ipm_toolbar.splice(1, 0, {
                    name: nameText + "负载入库",
                    type: typeClass,
                    call: function (e) {
                        var _$This = $(this);
                        //0 关闭
                        $.ajax({
                            url: "/sysNetworkSet/onOffHttpLoad.do",
                            method: "POST",
                            data: {
                                state: Number(!_$This.children().hasClass("glyphicon-ok-sign"))
                            },
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                if (Number(data.success)) {
                                    if (_$This.children().hasClass("glyphicon-ok-sign")) {
                                        // _$This.attr("title", "关闭负载入库");
                                        _$This.attr("title", "开启负载入库");
                                        _$This.children().removeClass("glyphicon-ok-sign").addClass("glyphicon-remove-sign");
                                        // alert("开启负载入库成功")
                                        jeBox.alert("关闭负载入库成功");
                                    } else {
                                        // _$This.attr("title", "开启负载入库");
                                        _$This.attr("title", "关闭负载入库");
                                        _$This.children().removeClass("glyphicon-remove-sign").addClass("glyphicon-ok-sign");
                                        // alert("关闭负载入库成功")
                                        jeBox.alert("开启负载入库成功");
                                    }
                                }
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }
                });
            }
            $.ptcsBSTable(tableId, tableId == "baowenJySer"?
            "/depthanaly/selectAll.do":
            "/appController/getAllAppByModuleId.do", {
                moduleId: ModuleId
            }, {
                pageSize: 10,
                columns: _columns,
                ipm_title: Name + "管理与设置",
                ipm_shrink: true,
                ipm_show: false,
                rowStyle: function (row, i) {
                    var cla = {};
                    if (i == 0) {
                        cla.classes = "custom-row-style";
                        selectRow = row;
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#" + tableId + " > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = row;
                    if (!alarmHide) {
                        $.ptcsBSTabRefresh(tableAlarmId, {
                            "watchpointId": 0,
                            "businessId": selectRow.id,
                            "moduleId": ModuleId
                        });
                    }
                },
                ipm_toolbar: ipm_toolbar
            });
            var alarmTable = function () {
                if (alarmHide) {
                    alarmHide = false;
                    $("#" + tableId).parents(".table-responsive").next().addClass("overflowAlerm tableMinHH").removeClass("none");
                    if(!$("#"+tableAlarmId).length){
                        $("."+tableAlarmId).append('<table id="'+tableAlarmId+'"></table>');
                    }
                    $.post("/alarmSet/getAlarmSetTitle.do", null, function (data) {
                        var kpiColumns = [];
                        data.forEach(function (item, index) {
                            if (item.nameen != "id") {
                                if (item.nameen == "name") {
                                    kpiColumns.push({
                                        field: item.nameen + "Value",
                                        title: item.namezh,
                                        sortable: true,
                                        formatter: function (value, row, index) {
                                            if (value && value != "" && value != "-") {
                                                // return value + "[" + row.kpiUnit + "]";
                                                return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                                            }
                                        }
                                    })
                                } else {
                                    kpiColumns.push({
                                        field: item.nameen + "Value",
                                        title: item.namezh + "[低]",
                                        sortable: true,
                                        formatter: function (value, row, index) {
                                            var valText = value.single.lowThresh;
                                            if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                                // return valText;
                                                return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                            }
                                        }
                                    })
                                }
                            }
                        });
                        data.forEach(function (item, index) {
                            if (item.nameen != "id" && item.nameen != "name") {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh + "[高]",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var valText = value.single.highThresh;
                                        if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                            // return valText;
                                            return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                        }
                                    }
                                })
                            }
                        });
                        $.ptcsBSTable(tableAlarmId, "/alarmSet/getAlarmSetData.do", {
                            "watchpointId": 0,
                            "businessId": selectRow.id,
                            "moduleId": ModuleId
                        }, {
                            pageSize: 15,
                            columns: kpiColumns,
                            showColumns: false,
                            ipm_title: Name + "告警设置",
                            ipm_shrink: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (i == 0) {
                                    cla.classes = "custom-row-style";
                                    alarmSelectRow = row;
                                }
                                return cla;
                            },
                            onClickRow: function (row, tr) {
                                $("#" + tableAlarmId + " > tbody > .custom-row-style").removeClass();
                                $(tr).addClass("custom-row-style");
                                alarmSelectRow = row;
                            },
                            ipm_toolbar: [{
                                name: "修改",
                                type: "edit",
                                call: function (e) {
                                    $.JeditKpisetModal("#" + tableAlarmId, "/alarmSet/getAlarmSetTitle.do",
                                        "/alarmSet/getAlarmSetData.do", 0, selectRow.id, ModuleId, alarmSelectRow.id, tableId);
                                }
                            }
                            // {
                            //     name: "组合告警",
                            //     type: "asterisk",
                            //     call: function (e) {
                            //         $.JzuheKpisetModal("#alarmZhtab",
                            //             "/customUnionKpi/getAlarmCustomUnionKpiList.do", {
                            //                 "watchpointId": selectRow.id,
                            //                 "businessId": selectRow.id,
                            //                 "moduleId": ModuleId
                            //             }, [{
                            //                     field: "groupKpiSelected",
                            //                     checkbox: true,
                            //                     formatter: function (value, row, index) {
                            //                         return {
                            //                             disabled: !Boolean(row.groupKpiCanUsed), //设置是否可用
                            //                             checked: Boolean(row.groupKpiSelected) //设置选中
                            //                         };
                            //                     }
                            //                 },
                            //                 {
                            //                     field: "nameValue",
                            //                     title: "KPI名称",
                            //                     sortable: true,
                            //                     formatter: function (value, row, index) {
                            //                         if (value && value != "" && value != "-") {
                            //                             // return value + "[" + row.kpiUnit + "]";
                            //                             return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                            //                         }
                            //                     }
                            //                 }
                            //             ],
                            //             tableId)
                            //     }
                            // }
                            ]
                        });
                    }, "json");
                    $("#content").animate({scrollTop :$("#"+tableAlarmId).offset().top+$("#content").scrollTop()-50},800);
                } else {
                    alarmHide = true;
                    $("#" + tableId).parents(".table-responsive").next().addClass("none").removeClass("overflowAlerm tableMinHH");
                    $("."+tableAlarmId).children().remove();
                }
            };
            $("#btn-addtableRow").click(function () {
                if ($("#addtableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JaddbsTableRow("#" + tableId, " /appController/addApp.do",
                        "/appController/getAllAppByModuleId.do", tableId, ModuleId)
                }
            });
            $("#btn-changetableRow").click(function () {
                if ($("#changetableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JediTableRow("#" + tableId, "/appController/updateApp.do", tableId, null, ModuleId);
                }
            });
            $("#btn-ConfirmdelRow").click(function () {
                var _this = $(this);
                if(!_this.hasClass("groupKpiCanUsed")){
                    if ($("#Confirm-modal").attr("data-tableAttr") == tableId) {
                        $.JremovebsTableRow("#" + tableId, tableId == "baowenJySer"?
                            "/depthanaly/deleteBusTag.do"
                            :"/appController/deleteApp.do",
                            tableId, null, ModuleId,_this);
                        selectRow = null;
                    }
                }
            });
            $("#btn-ediKpitableRow").click(function () {
                var _t = $(this);
                if ($("#ediKpitableRow-modal").attr("data-tableAttr") == tableId && !_t.prop("disabled")) {
                    _t.prop("disabled","disabled");
                    if ($("#ediKpitableRow-modal .addtableRowInput").length != 3) {
                        $.JeditKpiSetTable("#" + tableAlarmId, "/alarmSet/updateAlarmSet.do", {
                            "watchpointId": 0, //观察点ID
                            "moduleId": ModuleId, //模块ID
                            "businessId": selectRow.id, //业务ID
                            "kpitype": 1, //传1
                            "kpiId": alarmSelectRow.id, //此条数据的id
                            "idList": alarmSelectRow.normalId + "," +
                            alarmSelectRow.importantId + "," +
                            alarmSelectRow.urgentId + "," +
                            alarmSelectRow.deviateId //对应的告警数值的I
                        },alarmSelectRow);
                    } else {
                        $.JeditKpiSetTable("#" + tableAlarmId, "/alarmSet/updateAlarmSet.do", {
                            "watchpointId": 0, //观察点ID
                            "moduleId": ModuleId, //模块ID
                            "businessId": selectRow.id, //业务ID
                            "kpitype": 1, //传1
                            "kpiId": alarmSelectRow.id, //此条数据的id
                            "idList": alarmSelectRow.normalId + "," +
                            alarmSelectRow.importantId + "," +
                            alarmSelectRow.urgentId //对应的告警数值的I
                        },alarmSelectRow);
                    }
                }
                setTimeout(function () {
                    _t.removeAttr("disabled");
                },3000);
            });
            $("#btn-zuheKpitableRow").click(function () {
                if ($("#zuheKpitableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JzuheKpicommitData("/customUnionKpi/addAlarmCustomUnionKpis.do", {
                        "moduleId": ModuleId, //模块ID
                        "businessId": selectRow.id //业务ID
                    })
                }
            })
        }
        table("HTTP服务", 4, "webSer", "webSerSetTab",[
            {
                field: "name",
                title: "HTTP服务名称",
                sortable: true
            },
            {
                field: "displayIp",
                title: "服务端IP端口",
                sortable: true
            },
            {
                field: "descrption",
                title: "备注",
                sortable: true
            }
        ]);
        table("ORACLE服务", 5, "oraSer", "oraSerSetTab",[
            {
                field: "name",
                title: "ORACLE服务名称",
                sortable: true
            },
            {
                field: "displayIp",
                title: "服务端IP端口",
                sortable: true
            },
            {
                field: "descrption",
                title: "备注",
                sortable: true
            }
        ]);
        table("MYSQL服务", 6, "mySqlSer", "mySqlSerSetTab",[
            {
                field: "name",
                title: "MYSQL服务名称",
                sortable: true
            },
            {
                field: "displayIp",
                title: "服务端IP端口",
                sortable: true
            },
            {
                field: "descrption",
                title: "备注",
                sortable: true
            }
        ]);
        table("SQLSERVER服务", 7, "sqlSer", "sqlSerSetTab",[
            {
                field: "name",
                title: "SQLSERVER服务名称",
                sortable: true
            },
            {
                field: "displayIp",
                title: "服务端IP端口",
                sortable: true
            },
            {
                field: "descrption",
                title: "备注",
                sortable: true
            }
        ]);
        var baowenJyColmns = [
            {
                field: "name",
                title: "报文交易名称",
                sortable: true
            },        
            {
                field: "displayIp",
                title: "服务端IP端口",
                sortable: true
            },
            {
                field: "clientIpport",
                title: "客户端IP",
                sortable: true
            },
            {
                field: "statusCode",
                title: "交易返回码",
                sortable: true
            },
            {
                field: "successCode",
                title: "成功返回码",
                sortable: true
            },
            {
                field: "failedCode",
                title: "失败返回码",
                sortable: true
            }
        ];
        for(var i = 0;i < 20;i++){
            var columnsObj = {
                field: "busTag"+i,
                title: "自定义列"+i,
                sortable: true
            };
            baowenJyColmns.push(columnsObj)
        }
        table("报文交易",9,"baowenJySer","baowenJySerSetTab",baowenJyColmns)
    }(),
    function () {
        //URL服务管理与设置
        function taBle(Name, ModuleId, tableId, tableAlarmId) {
            var columns = [{
                        field: "name",
                        title: Name + "名称",
                        sortable: true
                    },
                    {
                        field: "descrption",
                        title: "备注",
                        sortable: true
                    }
                ],
                selectRow = null,
                alarmSelectRow = null,
                alarmHide = true;
            $.ptcsBSTable(tableId, "/url/get.do", {
                moduleId: ModuleId
            }, {
                pageSize: 10,
                columns: columns,
                ipm_title: Name + "管理与设置",
                ipm_shrink: true,
                ipm_show: false,
                rowStyle: function (row, i) {
                    var cla = {};
                    if (i == 0) {
                        cla.classes = "custom-row-style";
                        selectRow = row;
                    }
                    return cla;
                },
                onClickRow: function (row, tr) {
                    $("#" + tableId + " > tbody > .custom-row-style").removeClass();
                    $(tr).addClass("custom-row-style");
                    selectRow = row;
                    if (!alarmHide) {
                        $.ptcsBSTabRefresh(tableAlarmId, {
                            "watchpointId": 0,
                            "businessId": selectRow.id,
                            "moduleId": ModuleId
                        });
                    }
                },
                ipm_toolbar: [{
                        name: "告警设置",
                        type: "bell",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert('请先添加url服务')
								return;
							}
                            if(selectRow && selectRow.id){
                                alarmTable();
                            }
                        }
                    },
                    {
                        name: "新增",
                        type: "plus",
                        call: function (e) {
                            $.JaddbsModalRow([], "urlSer", null, tableId);
                        }
                    },
                    {
                        name: "修改",
                        type: "edit",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert('请先添加url服务')
								return;
							}
                            $.JeditRowModal("#" + tableId, "/url/get.do", [], [], "urlSer", null, tableId, {
                                id: selectRow.id
                            });
                        }
                    },
                    {
                        name: "删除",
                        type: "remove",
                        call: function (e) {
                        	if (selectRow == null) {
								jeBox.alert('请先添加url服务')
								return;
							}
                            if ($("#" + tableId + " .custom-row-style").attr("data-id") != undefined) {
                                $("#Confirm-modal").attr("data-tableAttr", tableId);
                                $("#Confirm-modal").modal("show");
                            }
                        }
                    }
                ]
            });
            var alarmTable = function () {
                if (alarmHide) {
                    alarmHide = false;
                    $("#" + tableId).parents(".table-responsive").next().addClass("overflowAlerm tableMinHH").removeClass("none");
                    if(!$("#"+tableAlarmId).length){
                        $("."+tableAlarmId).append('<table id="'+tableAlarmId+'"></table>');
                    }
                    $.post("/alarmSet/getAlarmSetTitle.do", null, function (data) {
                        var kpiColumns = [];
                        data.forEach(function (item, index) {
                            if (item.nameen != "id") {
                                if (item.nameen == "name") {
                                    kpiColumns.push({
                                        field: item.nameen + "Value",
                                        title: item.namezh,
                                        sortable: true,
                                        formatter: function (value, row, index) {
                                            if (value && value != "" && value != "-") {
                                                // return value + "[" + row.kpiUnit + "]";
                                                return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                                            }
                                        }
                                    })
                                } else {
                                    kpiColumns.push({
                                        field: item.nameen + "Value",
                                        title: item.namezh + "[低]",
                                        sortable: true,
                                        formatter: function (value, row, index) {
                                            var valText = value.single.lowThresh;
                                            if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                                // return valText;
                                                return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                            }
                                        }
                                    })
                                }
                            }
                        });
                        data.forEach(function (item, index) {
                            if (item.nameen != "id" && item.nameen != "name") {
                                kpiColumns.push({
                                    field: item.nameen + "Value",
                                    title: item.namezh + "[高]",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var valText = value.single.highThresh;
                                        if (typeof valText === "number" || (valText && valText != "" && valText != "-" )) {
                                            // return valText;
                                            return row.kpiUnit == "b" && item.nameen != "deviate" ? valText/1000000:valText;
                                        }
                                    }
                                })
                            }
                        });
                        $.ptcsBSTable(tableAlarmId, "/alarmSet/getAlarmSetData.do", {
                            "watchpointId": 0,
                            "businessId": selectRow.id,
                            "moduleId": ModuleId
                        }, {
                            pageSize: 15,
                            columns: kpiColumns,
                            showColumns: false,
                            ipm_title: Name + "告警设置",
                            ipm_shrink: true,
                            rowStyle: function (row, i) {
                                var cla = {};
                                if (i == 0) {
                                    cla.classes = "custom-row-style";
                                    alarmSelectRow = row;
                                }
                                return cla;
                            },
                            onClickRow: function (row, tr) {
                                $("#" + tableAlarmId + " > tbody > .custom-row-style").removeClass();
                                $(tr).addClass("custom-row-style");
                                alarmSelectRow = row;
                            },
                            ipm_toolbar: [{
                                name: "修改",
                                type: "edit",
                                call: function (e) {
                                    $.JeditKpisetModal("#" + tableAlarmId, "/alarmSet/getAlarmSetTitle.do",
                                        "/alarmSet/getAlarmSetData.do", 0, selectRow.id, ModuleId, alarmSelectRow.id, tableId);
                                }
                            }
                            // {
                            //     name: "组合告警",
                            //     type: "asterisk",
                            //     call: function (e) {
                            //         $.JzuheKpisetModal("#alarmZhtab",
                            //             "/customUnionKpi/getAlarmCustomUnionKpiList.do", {
                            //                 "watchpointId": selectRow.id,
                            //                 "businessId": selectRow.id,
                            //                 "moduleId": ModuleId
                            //             }, [{
                            //                     field: "groupKpiSelected",
                            //                     checkbox: true,
                            //                     formatter: function (value, row, index) {
                            //                         return {
                            //                             disabled: !Boolean(row.groupKpiCanUsed), //设置是否可用
                            //                             checked: Boolean(row.groupKpiSelected) //设置选中
                            //                         };
                            //                     }
                            //                 },
                            //                 {
                            //                     field: "nameValue",
                            //                     title: "KPI名称",
                            //                     sortable: true,
                            //                     formatter: function (value, row, index) {
                            //                         if (value && value != "" && value != "-") {
                            //                             // return value + "[" + row.kpiUnit + "]";
                            //                             return row.kpiUnit == "b"?value + "[Mb]":value + "[" + row.kpiUnit + "]";
                            //                         }
                            //                     }
                            //                 }
                            //             ],
                            //             tableId)
                            //     }
                            // }
                            ]
                        });
                    }, "json");
                    $("#content").animate({scrollTop :$("#"+tableAlarmId).offset().top+$("#content").scrollTop()-50},800);
                } else {
                    alarmHide = true;
                    $("#" + tableId).parents(".table-responsive").next().addClass("none").removeClass("overflowAlerm tableMinHH");
                    $("."+tableAlarmId).children().remove();
                }
            };
            $("#btn-addtableRow").click(function () {
                if ($("#addtableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JaddbsTableRow("#" + tableId, "/url/add.do",
                        null, tableId, ModuleId);
                }
            });
            $("#btn-changetableRow").click(function () {
                if ($("#changetableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JediTableRow("#" + tableId, "/url/update.do", tableId, null,ModuleId);
                }
            });
            $("#btn-ConfirmdelRow").click(function () {
                var _this = $(this);
                if(!_this.hasClass("groupKpiCanUsed")){
                    if ($("#Confirm-modal").attr("data-tableAttr") == tableId) {
                        $.JremovebsTableRow("#" + tableId, "/url/delete.do", tableId, null,ModuleId,_this);
                        selectRow = null;
                    }
                }
            });
            $("#btn-ediKpitableRow").click(function () {
                var _t = $(this);
                if ($("#ediKpitableRow-modal").attr("data-tableAttr") == tableId && !_t.prop("disabled")) {
                    _t.prop("disabled","disabled");
                    if ($("#ediKpitableRow-modal .addtableRowInput").length != 3) {
                        $.JeditKpiSetTable("#" + tableAlarmId, "/alarmSet/updateAlarmSet.do", {
                            "watchpointId": 0, //观察点ID
                            "moduleId": ModuleId, //模块ID
                            "businessId": selectRow.id, //业务ID
                            "kpitype": 1, //传1
                            "kpiId": alarmSelectRow.id, //此条数据的id
                            "idList": alarmSelectRow.normalId + "," +
                            alarmSelectRow.importantId + "," +
                            alarmSelectRow.urgentId + "," +
                            alarmSelectRow.deviateId //对应的告警数值的I
                        },alarmSelectRow);
                    } else {
                        $.JeditKpiSetTable("#" + tableAlarmId, "/alarmSet/updateAlarmSet.do", {
                            "watchpointId": 0, //观察点ID
                            "moduleId": ModuleId, //模块ID
                            "businessId": selectRow.id, //业务ID
                            "kpitype": 1, //传1
                            "kpiId": alarmSelectRow.id, //此条数据的id
                            "idList": alarmSelectRow.normalId + "," +
                            alarmSelectRow.importantId + "," +
                            alarmSelectRow.urgentId //对应的告警数值的I
                        },alarmSelectRow);
                    }
                }
                setTimeout(function () {
                    _t.removeAttr("disabled");
                },3000);
            });
            $("#btn-zuheKpitableRow").click(function () {
                if ($("#zuheKpitableRow-modal").attr("data-tableAttr") == tableId) {
                    $.JzuheKpicommitData("/customUnionKpi/addAlarmCustomUnionKpis.do", {
                        "moduleId": ModuleId, //模块ID
                        "businessId": selectRow.id //业务ID
                    })
                }
            })
        }
        taBle("URL服务", 8, "urlSer", "urlSerSetTab");
    }(),
    function () {
        //应用可用性
        var columns = [
            { field: "name", title: "名称", sortable: true },
            { field: "ip", title: "IP", sortable: true },
            { field: "port", title: "端口", sortable: true },
            { field: "interval", title: "间隔时间[分]", sortable: true },
            { field: "lastExecTime", title: "上一次执行时间", sortable: true,formatter: function(v) {
                    if(v){
                        return  $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss")
                    }
                }
            },
            { field: "status", title: "状态", sortable: true,formatter: function(v) {
                    if(v == "N") {
                        return "关闭";
                    } else {
                        return "开启";
                    }
                } },
            { field: "des", title: "备注", sortable: true }
        ],
            selectRow = null;
        $.ptcsBSTable("usability", "/usability/getUsability.do", null, {
            pageSize: 10,
            columns: columns,
            ipm_title: "应用可用性管理与设置",
            ipm_shrink: true,
            ipm_show: false,
            rowStyle: function(row, i) {
                var cla = {};
                if(i == 0) {
                    cla.classes = "custom-row-style";
                    selectRow = row;
                }
                return cla;
            },
            onClickRow: function(row, tr) {
                $("#usability > tbody > .custom-row-style").removeClass();
                $(tr).addClass("custom-row-style");
                selectRow = row;
            },
            ipm_toolbar: [
                { name: "新增", type: "plus", call: function(e) {
                        $.JaddbsModalRow(["名称","IP","端口 ","间隔时间[分]","状态","备注"],"usabilityAddRow",null,"usability");
                    }
                },
                { name: "修改", type: "edit", call: function(e) {
                	   if (selectRow == null) {
								jeBox.alert('请先添加应用可用性')
								return;
						}
                        $.JeditRowModal("#usability", "/usability/getUsability.do",
                            ["name","ip","port","interval","status","des"], ["名称","IP","端口 ","间隔时间[分]","状态","备注"],
                            "usabilityUp",null,"usability");
                    }
                },
                { name: "删除", type: "remove", call: function(e) {
                	    if (selectRow == null) {
								jeBox.alert('请先添加应用可用性')
								return;
						}
                        if ($("#usability .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr","usability");
                            $("#Confirm-modal").modal("show");
                        }
                    }
                }
            ]
        });
        $("#btn-addtableRow").click(function () {
            if($("#addtableRow-modal").attr("data-tableAttr")=="usability"){
                $.JaddbsTableRow("#usability", "/usability/addUsability.do",
                    "/usability/getUsability.do", "usabilityAddRow")
            }
        });
        $("#btn-changetableRow").click(function(){
            if($("#changetableRow-modal").attr("data-tableAttr")=="usability"){
                $.JediTableRow("#usability","/usability/updateUsabilityId.do","usabilityUpdate");
            }
        });
        $("#btn-ConfirmdelRow").click(function() {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if($("#Confirm-modal").attr("data-tableAttr")=="usability"){
                    $.JremovebsTableRow("#usability", "/usability/delUsabilityId.do", "usabilityDel",null,null,_this);
                    selectRow = null;
                }
            }
        });
    }(),
    function () {
        var columns = [{
                field: "name",
                title: "驾驶舱名称",
                sortable: true
            },
            {
                field: "descrption",
                title: "驾驶舱描述",
                sortable: true
            },
            {
                field: "userName",
                title: "创建用户",
                sortable: true
            },
            {
                field: "updateTime",
                title: "更新时间",
                sortable: true,
                formatter: function (v) {
                    if (v == 0) {
                        return null;
                    } else {
                        return $.timeStampDate(v, "YYYY-MM-DD hh:mm:ss");
                    }
                }
            },
            {
                field: "lockStatus",
                title: "状态",
                sortable: true,
                formatter: function (v) {
                    if (v) {
                        return "锁定";
                    } else {
                        return "未锁定";
                    }
                }
            },
            {
                field: "caozuo",
                title: "操作",
                sortable: true,
                formatter: function (v, r) {
                    if (r.id < 3000000) {
                        var rowId = r.id;
                        dataList.push(rowId);
                        return "<span class='business-arrow glyphicon glyphicon-arrow-up' onclick='xs(" + rowId + ")'>" +
                            "</span><span class='business-arrow glyphicon glyphicon-arrow-down' onclick='xx(" + rowId + ")'>" +
                            "</span><span class='business-arrow glyphicon glyphicon-open' onclick='xt(" + rowId + ")'>" +
                            "</span><span class='business-arrow glyphicon glyphicon-save' onclick='xw(" + rowId + ")'></span>";
                    } else {
                        return null;
                    }
                }
            }
        ],
            selectRow = null;
        $.ptcsBSTable("cockMange", "/monitor/getMonitorViewList.do", null, {
            pageSize: 10,
            columns: columns,
            ipm_title: "驾驶舱管理与设置",
            ipm_shrink: true,
            ipm_show: false,
            rowStyle: function (row, i) {
                var cla = {};
                if (i == 0) {
                    cla.classes = "custom-row-style cursor";
                    selectRow = row;
                } else {
                    cla.classes = "cursor";
                    selectRow = row;
                }
                return cla;
            },
            onClickRow: function (row, tr) {
                $("#cockMange > tbody > .custom-row-style").removeClass("custom-row-style");
                $(tr).addClass("custom-row-style cursor");
                selectRow = row;
            },
            onClickCell: function (field, value, row, $element) {
                if (field == "name") {
                    function toPage(url, page, nameZh) {
                        $.ajax({
                            url: url,
                            method: "POST",
                            data: {},
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                location.href = page + ".html?dataIndex=0&dataId=" + data[0].id + "&nameZh=" + nameZh;
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        });
                    }
                    switch (row.id) {
                        case 3000000:
                            toPage("/watchpointController/getFindAll.do", "observationPointkpi", row.name);
                            break;
                        case 3000002:
                            toPage("/serverManagement/getAllServerSide.do", "serverSidekpi", row.name);
                            break;
                        case 3000001:
                            toPage("/client/getClient.do?moduleId=11", "userSidekpi", row.name);
                            break;
                        default:
                            location.href = "cockpit.html?busiId=" + row.id + "&nameZh=" + row.name;
                    }
                }
            },
            onMouseOverCell:function(field, value, row, $element){
                if(field == "name"){
                    $($element).addClass("text-underline");
                }
            },
            onMouseOutCell:function(field, value, row, $element){
                if($($element).hasClass("text-underline")){
                    $($element).removeClass("text-underline");
                }
            },
            ipm_toolbar: [{
                    name: "新增",
                    type: "plus",
                    call: function (e) {
                        $.JaddbsModalRow(["驾驶舱名称", "驾驶舱描述"], null, null, "cockMange");
                    }
                },
                {
                    name: "修改",
                    type: "edit",
                    call: function (e) {
                    	if (selectRow == null) {
								jeBox.alert('请先添加驾驶舱');
								return;
						}
                        $.JeditRowModal("#cockMange", "/monitor/getMonitorViewList.do", ["name", "descrption"], ["驾驶舱名称", "驾驶舱描述"],
                            null, null, "cockMange");
                    }
                },
                {
                    name: "删除",
                    type: "remove",
                    call: function (e) {
                    	if (selectRow == null) {
								jeBox.alert('请先添加驾驶舱');
								return;
						}
                        if ($("#cockMange .custom-row-style").attr("data-id") != undefined) {
                            $("#Confirm-modal").attr("data-tableAttr", "cockMange");
                            $("#Confirm-modal").modal("show");
                        }
                    }
                }
            ]
        });
        $("#btn-addtableRow").click(function () {
            if ($("#addtableRow-modal").attr("data-tableAttr") == "cockMange") {
                $.JaddbsTableRow("#cockMange", "/monitor/addMonitorView.do", "/monitor/getMonitorViewList.do")
            }
        });
        $("#btn-changetableRow").click(function () {
            if ($("#changetableRow-modal").attr("data-tableAttr") == "cockMange") {
                $.JediTableRow("#cockMange", "/monitor/updateMonitorViewById.do");
            }
        });
        $("#btn-ConfirmdelRow").click(function () {
            var _this = $(this);
            if(!_this.hasClass("groupKpiCanUsed")){
                if ($("#Confirm-modal").attr("data-tableAttr") == "cockMange") {
                    $.JremovebsTableRow("#cockMange", "/monitor/delMonitorView.do",null,null,null,_this);
                    selectRow = null;
                }
            }
        })
    }();
    /**
     * 此处控制表格是否显示
     */
    $.ajax({
        url: "/authorizeModuleController/getFindAll.do",
        method: "POST",
        data: {},
        dataType: "json",
        beforeSend:function (XMLHttpRequest) {},
        success: function (data,textStatus,XMLHttpRequest) {
            var tableId;
            data.forEach(function (item, index) {
                switch (item.nameen) {
                    case "CENTER":
                        tableId = "#cenTer";
                        break;
                    // case "观察点":
                    case "WATCHPOINT":
                        tableId = "#oberSet";
                        break;
                    // case "客户端":
                    case "CLIENT":
                        tableId = "#userSet";
                        break;
                    // case "服务端":
                    case "SERVER":
                        tableId = "#serSet";
                        break;
                    // case "HTTP服务":
                    case "HTTP":
                        tableId = "#webSer";
                        break;
                    // case "ORACLE服务":
                    case "ORACLE":
                        tableId = "#oraSer";
                        break;
                    // case "MYSQL服务":
                    case "MYSQL":
                        tableId = "#mySqlSer";
                        break;
                    // case "SQLSERVER服务":
                    case "SQLSERVER":
                        tableId = "#sqlSer";
                        break;
                    // case "URL交易":
                    // case "URL服务":
                    case "URL":
                        tableId = "#urlSer";
                        break;
                    // case "报文交易":
                    // case "报文服务":
                    case "MESSAGE":
                        tableId = "#baowenJySer";
                        break;
                    // case "应用可用性":
                    case "USABILITY":
                        tableId = "#usability";
                        break;
                }
                if (tableId) {
                    $(tableId).parents(".tableMinH").removeClass("none").next().removeClass("none");
                }
            })
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    })
});