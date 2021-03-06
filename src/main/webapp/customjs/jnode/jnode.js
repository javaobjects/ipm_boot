/**
 * Created by yanbo on 2017/3/8
 * subnet 子网
 * server 服务器IP
 * app 业务/应用
 */
;
$(function() {
    // 定义mloadingDiv的宽高及其子元素
    // $(".mloadingDiv").height(window.innerHeight);
    // var _Top = window.innerHeight > 61 ? (window.innerHeight - 61) / 2 : 0,
    //     _Left = window.innerWidth > 215 ? (window.innerWidth - 215) / 2 : 0;
    // $(".loadingDivChild").css({
    //     "left": _Left,
    //     "top": _Top
    // });
    $(".mypanel").css("top", $(".navbar-logo").height());
    if(location.href.split("?").length==1){
        $(".mloadingDiv").removeClass("hide"); // 提示用户等待
    }
    var flag = 0,tryErr = 0; // 此变量控制双击事件 切换非取与非钻取模式
    var canvas = document.getElementById("canvas"), // 获取canvas
        canvasHeight = $(document).height()-$("#header").height()-15-5>623?
        $(document).height()-$("#header").height()-15-5:
            623; // 设置canvas高度值
    $("#canvas").attr({
        "width": $("#canvas").parent().width(),
        "height": canvasHeight
    }); // 将宽高值赋给canvas


    $(".mloadingDiv").css({
        width:$("#canvas").outerWidth(),
        height:$("#canvas").outerHeight(),
        top:$("#canvas").offset().top,
        left:$("#canvas").offset().left
    });
    var _Top = $(".mloadingDiv").height() > 61 ? ($(".mloadingDiv").height() - 61) / 2 : 0,
        _Left = $(".mloadingDiv").width() > 215 ? ($(".mloadingDiv").width()  - 215) / 2 : 0;
    $(".loadingDivChild").css({
        "left": _Left,
        "top": _Top
    });



    var stage = new JTopo.Stage(canvas); // 创建一个舞台对象
    //stage.wheelZoom = 1.2; // 设置鼠标缩放比例
    var scene = new JTopo.Scene(stage); // 创建一个场景对象
    //scene.alpha = 1;
    //scene.alpha = 0;
    //scene.backgroundColor = "51, 71, 81";
    // 在空白处右键显示
    scene.addEventListener("mouseup", function(event) {
        if (event.target == null) {
            if (event.button == 2) {
                $("#linkmenu").hide();
                if(window.innerWidth>=992){
                    if($("#sidebar").hasClass("toggled")){
                        $("#contextmenu").css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX-$("#cond_query").width()-40
                        }).show().children("li:lt(3)").hide();
                    }else {
                        $("#contextmenu").css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX-$("#sidebar").children().width()-$("#cond_query").width()-40
                        }).show().children("li:lt(3)").hide();
                    }
                }else {
                    if($("#sidebar").hasClass("toggled")){
                        $("#contextmenu").css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX
                        }).show().children("li:lt(3)").hide();
                    }else {
                        $("#contextmenu").css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX-$("#sidebar").children().width()
                        }).show().children("li:lt(3)").hide();
                    }
                }
            }
        }
    });
    function newNode(x, y, text, color) {
        var node = new JTopo.Node();
        node.setLocation(x, y);
        node.setSize(8, 8);
        node.radius = 4;
        node.layout = {
            type: "circle",
            radius: 160
        };
        node.fontColor = "218,218,218";
        node.font = "10px 微软雅黑";
        node.tip = text;
        node.fillColor = color;
        node.addEventListener('mouseup',
            function(event) {
                currentNode = this;
                handler(event);
            });
        node.mouseover(function(event) {
            this.text = this.tip;
        });
        node.mouseout(function() {
            this.text = null;
        });
        scene.add(node);
        return node;
    }
    function newCircleNode(x, y, text, color, alarmNum,startTime,endTime) {
        var node = new JTopo.CircleNode();
        node.setLocation(x, y);
        node.setSize(8, 8);
        node.radius = 5;
        node.layout = {
            type: "circle",
            radius: 160
        };
        node.fontColor = "255,255,255";
        node.font = "20px 微软雅黑 bloder";
        node.textPosition = "Top_Center";
        node.tip = text;
        node.startTime = startTime;
        node.endTime = endTime;
        node.alarmNum = alarmNum;
        node.fillColor = color;
        node.addEventListener('mouseup',
            function(event) {
                //监听鼠标松开事件
                currentNode = this;
                handler(event);
                var nodeThis=this;
                if(flag){
                    if(nodeThis.outLinks.length){
                        if(nodeThis.inLinks.length){
                            nodeThis.outLinks.forEach(function(itemZ){
                                if(itemZ.nodeZ.tip==nodeThis.portText){
                                    setTimeout(function(){
                                        if (nodeThis.x - nodeThis.inLinks[0].nodeA.x) {
                                            itemZ.nodeZ.x = Math.abs((nodeThis.x - nodeThis.inLinks[0].nodeA.x) / 2) + Math.min(nodeThis.x, nodeThis.inLinks[0].nodeA.x);
                                        } else {
                                            itemZ.nodeZ.x = nodeThis.x;
                                        }
                                        if (nodeThis.y - nodeThis.inLinks[0].nodeA.y) {
                                            itemZ.nodeZ.y = Math.abs((nodeThis.y - nodeThis.inLinks[0].nodeA.y) / 2) +
                                                Math.min(nodeThis.y, nodeThis.inLinks[0].nodeA.y);
                                        } else {
                                            itemZ.nodeZ.y = nodeThis.y;
                                        }
                                    },10)
                                }
                            })
                        }
                    }
                }
            });
        node.mouseover(function(event) {
            this.text = this.tip;
            var tNode=this;
        });
        node.mouseout(function() {
            this.text = null;
        });
        scene.add(node);
        return node;
    }
    function newLink(nodeA, nodeZ, text, dashedPattern) {
        var link = new JTopo.Link(nodeA, nodeZ);
        link.tip = text;
        // link.arrowsRadius = 3;
        // link.lineWidth = 0.5;
        link.lineWidth = 2;
        link.bundleOffset = 10;
        link.bundleGap = 10;
        //link.strokeColor = '166,183,53';
        link.strokeColor = '56,113,127';
        link.fontColor = "255,255,255";
        link.font = "20px 微软雅黑 bloder";
        link.textOffsetY = 3;
        link.addEventListener('mouseup', function(event) {
            currentLink = this;
            handlelink(event);
        }); // 线上的右键功能
        link.mouseover(function(event) {
            this.text = this.tip;
        });
        link.mouseout(function() {
            this.text = null;
        });
        scene.add(link);
        return link;
    }
    // 在node上右键
    function handler(event) {
        $("#linkmenu").hide();
        if (event.button == 2) { // 右键
            // 当前位置弹出菜单（div）
            if(window.innerWidth>=992){
                if($("#sidebar").hasClass("toggled")){
                    $("#contextmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#cond_query").width()-40
                    }).show().children("li").show();
                }else {
                    $("#contextmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#sidebar").children().width()-$("#cond_query").width()-40
                    }).show().children("li").show();
                }
            }else {
                if($("#sidebar").hasClass("toggled")){
                    $("#contextmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX
                    }).show().children("li").show();
                }else {
                    $("#contextmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#sidebar").children().width()
                    }).show().children("li").show();
                }
            }
        }
    }
    // 在连线上右键  或 左键单击值为0
    function handlelink(event) {
        $("#contextmenu").hide();
        // console.log(event.button);
        if (event.button == 2) {
            if(window.innerWidth>=992){
                if($("#sidebar").hasClass("toggled")){
                    $("#linkmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#cond_query").width()-40
                    }).show();
                }else {
                    $("#linkmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#sidebar").children().width()-$("#cond_query").width()-40
                    }).show();
                }
            }else {
                if($("#sidebar").hasClass("toggled")){
                    $("#linkmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX
                    }).show();
                }else {
                    $("#linkmenu").css({
                        top: event.pageY-$("#header").outerHeight(),
                        left: event.pageX-$("#sidebar").children().width()
                    }).show();
                }
            }
        }
    }
    var portal = {
        "showSname": function() {
            // 加载用户保存的数据
            $.ajax({
                url: "/jtopo/getJtopoNames.do",
                type: "post",
                dataType: "json",
                success: function(data) {
                    if (data.length) {
                        $(".qsave").removeClass("hide");
                        $(".addOption").html("");
                        data.forEach(function(item) {
                            $(".addOption").append($('<tr>' +
                                '<td class="tdtext cursor">' + item.substr(13) + '</td>' +
                                '<td class="Stime">' +
                                '<span class="showTime">' + portal.fun.getLocaltime(item.substr(0, 13) / 1000) + '</span>' +
                                '<span class="hideTime hide">' + item.substr(0, 13) + '</span></td>+' + '' +
                                '<td data-toggle="tooltip" data-original-title="删除此行">' +
                                '<i class="glyphicon glyphicon-remove removeli close "></i>' +
                                // '<div class="removeli close" ' +
                                // 'style="width:13px;height:13px;background:url(images/clear.png) center no-repeat;cursor:pointer;"></div>'+
                                '</td></tr>').on("click", function() {})).parent().addClass("hide");
                            $(".qsave").removeClass("hide");
                            $(".tdtext").click(function() {
                                $("#myModal").modal('hide');
                                var text = $(this).siblings(".Stime").children(".hideTime").text() + $(this).text();
                                $(".mloadingDiv").removeClass("hide");
                                $.ajax({
                                    url: "/jtopo/getJtopo.do",
                                    type: "post",
                                    data: {
                                        "name": text
                                    },
                                    dataType: "json",
                                    success: function(nodeJson) {
                                        portal.fun.tdtextClick(nodeJson);
                                        portal.tool.showCenter();
                                    }
                                });
                                setTimeout(function() {
                                    $(".mloadingDiv").addClass("hide");
                                }, 600);
                            });
                            //用户删除保存数据
                            $(".removeli").click(function() {
                                var close = $(this);
                                $.ajax({
                                    url: "/jtopo/delJtopo.do",
                                    type: "post",
                                    data: {
                                        name: close.parent().siblings(".Stime").children(".hideTime").text() + close.parent().siblings(".tdtext").text()
                                    },
                                    dataType: "json",
                                    success: function(what) {
                                        if (what) {
                                            close.parent().parent().remove();
                                            $(".t-click").click(function() {
                                                if ($(".addOption").children().length == 1) {
                                                    $(".qsave").addClass("hide");
                                                }
                                            })
                                        }
                                    }
                                });
                            });
                        })
                    }
                    
                    function newCode() {
                        // var html ='<div class="row">' +
                        //     '<div class="col-md-12" style="max-height: 200px;overflow-y: scroll;">' +
                        //     '<table id="jtopoTab" style="background: #fff;"></table>' +
                        //     '</div>';
                        //
                        // // 将其append到容器
                        //
                        // $("#myModal .modal-body>.form-horizontal").append(html);
                        // $("#myModal #jtopoTab").bootstrapTable({
                        //     toggle: "table",
                        //     columns: [
                        //         {
                        //             field:"name",
                        //             title:"名称",
                        //             sortable:true
                        //         }
                        //     ],
                        //     undefinedText: "",
                        //     onClickRow: function (row, trEle) {
                        //         $("#addtableRow-modal #handlUrl > tbody > .custom-row-style").removeClass();
                        //         $(trEle).addClass("custom-row-style");
                        //         trId = row.id;
                        //     },
                        // })
                    //     if (page == "urlSer") {
                    //         var trId,
                    //             coloumns;
                    //         if (page == "urlSer") {
                    //             coloumns = [{
                    //                 field: "num",
                    //                 title: "编号",
                    //                 sortable: true
                    //             }, {
                    //                 field: "name",
                    //                 title: "名称",
                    //                 sortable: true
                    //             }, {
                    //                 field: "url",
                    //                 title: "URL",
                    //                 sortable: true
                    //             }];
                    //         } else {
                    //             // page == "baowenJySer"
                    //             coloumns = [{
                    //                 field: "name",
                    //                 title: "报文交易名称",
                    //                 sortable: true
                    //             }, {
                    //                 field: "displayIp",
                    //                 title: "服务端IP端口",
                    //                 sortable: true
                    //             }, {
                    //                 field: "clientIpport",
                    //                 title: "客户端IP",
                    //                 sortable: true
                    //             }, {
                    //                 field: "statusCode",
                    //                 title: "交易返回码",
                    //                 sortable: true
                    //             }, {
                    //                 field: "successCode",
                    //                 title: "成功返回码",
                    //                 sortable: true
                    //             }, {
                    //                 field: "failedCode",
                    //                 title: "失败返回码",
                    //                 sortable: true
                    //             }];
                    //             for (var i = 0; i < 20; i++) {
                    //                 var columnsObj = {
                    //                     field: "busTag" + i,
                    //                     title: "自定义列" + i,
                    //                     sortable: true
                    //                 };
                    //                 coloumns.push(columnsObj)
                    //             }
                    //         }
                    //         $("#addtableRow-modal #handlUrl").bootstrapTable({
                    //             toggle: "table",
                    //             columns: coloumns,
                    //             undefinedText: "",
                    //             onClickRow: function (row, trEle) {
                    //                 $("#addtableRow-modal #handlUrl > tbody > .custom-row-style").removeClass();
                    //                 $(trEle).addClass("custom-row-style");
                    //                 trId = row.id;
                    //             },
                    //             onDblClickCell: function (field, value, row, $element) {
                    //                 if (!$($element).children().hasClass("rowCellInput")) {
                    //                     var input = $('<input type="text" class="rowCellInput" value="' + $($element).text() + '"/>')
                    //                             .width($($element).outerWidth(true))
                    //                             .height($($element).outerHeight(true)).css("background", "none"),
                    //                         rowObj = {};
                    //                     $($element).html(input).css({
                    //                         "padding": 0,
                    //                         "text-align": "center",
                    //                         "width": $($element).children().width(),
                    //                         "height": $($element).children().height()
                    //                     });
                    //                     $($element).children(input).focus().blur(function () {
                    //                         rowObj[field] = $($element).children(input).val();
                    //                         $("#addtableRow-modal #handlUrl").bootstrapTable("updateRow", {
                    //                             index: $($element).parent("tr").attr("data-index"),
                    //                             row: rowObj
                    //                         });
                    //                         $($element).css("padding", "");
                    //                     });
                    //                 }
                    //             }
                    //         });
                    //         $("#add-UrlhandTab").off("click").on("click", function () {
                    //             // 此处还应该区别是 httP 还是 baowenJySer 两者的row 值不一样 wb wu i g su
                    //             $("#addtableRow-modal #handlUrl").bootstrapTable('insertRow', {
                    //                 index: 0,
                    //                 row: {
                    //                     id: "s" + $("#addtableRow-modal #handlUrl").bootstrapTable("getData").length
                    //                     // num: "",
                    //                     // name: "",
                    //                     // url: ""
                    //                 }
                    //             });
                    //         });
                    //         $("#del-UrlhandTab").off("click").on("click", function () {
                    //             $("#addtableRow-modal #handlUrl").bootstrapTable('remove', {
                    //                 field: 'id',
                    //                 values: [trId]
                    //             });
                    //         });
                    //         $("#addtableRow-modal").modal("show");
                    //     } else {
                    //         $("#add-UrlhandTab").remove();
                    //         $("#del-UrlhandTab").remove();
                    //         $("#addtableRow-modal").modal("show");
                    //     }
                    // }


                    }
                }
            });
        },
        "tool": {
            "homeNode": function(data) {
                /*
                 * 首次展示在页面的IP点连线及其功能逻辑：
                 * 1、先把from_ip从data里面取出来去重并存进tempArry数组中
                 * 2、再把to_info从data里面取出来去重并存进tempArry数组中
                 * 所有点的IP信息均放在咯tempArry数组中 点与点的连接关系是默认左边点为中心点
                 * 在当前数据中会大量的存在 此点既是中心点又是别的中心点的子点 这里我默认把from_ip做为中心点
                 * to_info做为中心点的子点 3、同时把from_ip to_info与tempArry进行比较
                 * 得到一个tempArry里面的下标集合存进leftArry和rightArry中
                 * leftArry与rightArry中的下标是一一对应的关系 4、关键的一步骤。把这两个数组给关联起来
                 * 也是难点这个点如果处理不好直接引影页面的美观 如果再能处理得好第3个步骤就能把这给完美处理咯
                 *
                 */
                if(data.length){
                    if(data.length==2000){
                        jeBox.alert("当前通信较多，仅展示最近2000条数据");
                    }
                    if(scene.childs.length){
                        scene.clear();
                    }
                    var tempArry = [], // 所有的IP的集合
                        nodeArry = [], // 所有页面中的点的集合
                        leftArry = [], // 基于IP集合的下标集合
                        rightArry = []; // 基于IP集合的下标集合
                    // 去重 所有的IP的集合
                    $.each(data, function(i, v) {
                        if (tempArry.indexOf(v.fromIp) == -1) {
                            tempArry.push(v.fromIp);
                        }
                        if (tempArry.indexOf(v.toInfo) == -1) {
                            tempArry.push(v.toInfo);
                        }
                    });
                    // 确定起始点  此步骤会造成重复连线
                    $.each(data, function(i, v) {
                        for (var j = 0; j < tempArry.length; j++) {
                            if (data[i].fromIp == tempArry[j]) {
                                leftArry.push(j);
                            }
                            if (data[i].toInfo == tempArry[j]) {
                                rightArry.push(j);
                            }
                        }
                    });
                    function LocationXy(i){
                        //圆心点
                        /*
                         *﻿﻿
                         圆点坐标：(x0,y0)
                         半径：r
                         角度：a0

                         则圆上任一点为：（x1,y1）
                         x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
                         y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
                         *
                         * Math.random()*360 角度
                         * window.innerWidth 容器宽度
                         * (window.innerHeight - $(".navbar-logo ").height() - 20) 容器高度
                         * 半径以小的那边的0.35为半径
                         *
                         *
                         */
                        //var centerPointX = window.innerWidth/ 2,
                        //    centerPointY = (window.innerHeight - $(".navbar-logo ").height() - 20)/ 2,
                        //    _boxW = window.innerWidth,
                        //    _boxH = window.innerHeight - $(".navbar-logo ").height() - 20,
                        //    _angle = 360/tempArry.length,
                        //    hudu = (2*Math.PI / 360) * _angle*(i+1),
                        //    cicR;
                        var centerPointX = $("#canvas").width()/ 2,
                            centerPointY = $("#canvas").height()/ 2,
                            _boxW = $("#canvas").width(),
                            _boxH = $("#canvas").height(),
                            _angle = 360/tempArry.length,
                            hudu = (2*Math.PI / 360) * _angle*(i+1),
                            cicR;
                        if(_boxW>_boxH){
                            cicR = _boxH/2*0.8;
                        }else if(_boxW<_boxH){
                            cicR = _boxW/2*0.8
                        }else{
                            cicR = _boxW/2*0.8
                        }
                        var x = centerPointX + Math.sin(hudu) * cicR,
                            y = centerPointY + Math.cos(hudu) * cicR;
                        return {
                            x:x,
                            y:y
                        }
                    }
                    // 画点tempArry
                    $.each(tempArry, function(i, v) {
                        var x = Math.random() * parseInt(window.innerWidth * 0.6) +
                            parseInt(window.innerWidth * 0.2);
                        var y = Math.random() * parseInt((window.innerHeight - $(".navbar-logo").height() - 20) * 0.6) +
                            parseInt((window.innerHeight - $(".navbar-logo ").height() - 20) * 0.2);
                        //var x = LocationXy(i).x,
                        //    y = LocationXy(i).y;
                        //console.log(x+"          "+y);
                        for (var j = 0; j < data.length; j++) {
                            //现在type后端全改成server咯
                            //console.log(data[i].fromType);
                            //console.log(data[i].toType);
                            if (v == data[j].fromIp) {
                                if (data[j].fromType == "subnet") {
                                    var color = "255,255,0";
                                } else if (data[j].fromType == "server"
                                    ||data[j].fromType == "web"
                                    ||data[j].fromType == "costomer") {
                                    //var color = "67,67,67";
                                    var color = "7,237,15";
                                    //var color = "204,204,204";
                                    //var color = "102,102,102";
                                } else {
                                    var color = "223,227,219";
                                }
                                var alarmNum = data[j].fromAlarm,
                                    startTime = data[j].startTime,
                                    endTime = data[j].endTime;
                            } else if (v == data[j].toInfo) {
                                if (data[j].toType == "subnet") {
                                    var color = "255,255,0";
                                } else if (data[j].toType == "server"||
                                    data[j].toType == "web"
                                    ||data[j].toType == "costomer") {
                                    //var color = "67,67,67";
                                    var color = "7,237,15";
                                    //var color = "204,204,204";
                                    //var color = "102,102,102";
                                } else {
                                    var color = "223,227,219";
                                }
                                var alarmNum = data[j].toAlarm,
                                    startTime = data[j].startTime,
                                    endTime = data[j].endTime;
                            }
                        }
                        if(alarmNum){
                            console.log(alarmNum);
                        }
                        //console.log(color);
                        var a = newCircleNode(x, y, v, color,alarmNum,startTime,endTime);
                        if(a.tip.indexOf("/")!=-1){
                            for(var k=0;k<data.length;k++){
                                if(a.tip==data[k].fromIp){
                                    if(a.relAy){
                                        if(a.relAy[data[k].realIp]){
                                            if(a.relAy[data[k].realIp].indexOf(data[k].toInfo)==-1){
                                                a.relAy[data[k].realIp].push(data[k].toInfo);
                                            }
                                        }else{
                                            a.relAy[data[k].realIp] = [];
                                            a.relAy[data[k].realIp].push(data[k].toInfo);
                                        }
                                    }else{
                                        a.relAy=[];
                                        a.relAy[data[k].realIp] = [];
                                        a.relAy[data[k].realIp].push(data[k].toInfo);
                                    }
                                }else if(a.tip==data[k].toInfo){
                                    if(a.relAy){
                                        if(a.relAy[data[k].realIp]){
                                            if(a.relAy[data[k].realIp].indexOf(data[k].fromIp)==-1){
                                                a.relAy[data[k].realIp].push(data[k].fromIp);
                                            }
                                        }else{
                                            a.relAy[data[k].realIp] = [];
                                            a.relAy[data[k].realIp].push(data[k].fromIp);
                                        }
                                    }else{
                                        a.relAy=[];
                                        a.relAy[data[k].realIp] = [];
                                        a.relAy[data[k].realIp].push(data[k].fromIp);
                                    }
                                }
                            }
                        }
                        // 展示alarm告警
                        portal.Jnode.alarm(a, a.alarmNum);
                        if ($(".showIp").text() == "取消显示IP") {
                            a.text = a.tip;
                            a.mouseout(function() {
                                this.text = this.tip;
                            });
                        } else {
                            a.text = null;
                            a.mouseout(function() {
                                this.text = null;
                            });
                        }
                        nodeArry.push(a);
                    });
                    // 连线
                    for (var i = 0; i < data.length; i++) {
                        var ilink = newLink(nodeArry[leftArry[i]], nodeArry[rightArry[i]]);
                        // 此处应该判断KPI为啥值
                        portal.Jnode.linkValue($(".kpi").children("option:selected").val(),
                            data[i].value-0, ilink);
                        //try {
                        //    JTopo.layout.layoutNode(scene, nodeArry[leftArry[i]], true);
                        //}catch (err){
                        //    console.log(err);
                        //    console.log(i);
                        //    tryErr = 1;
                        //    $("button[name='center']").addClass("hide");
                        //    $("button[name='save']").addClass("hide");
                        //    $("button[name='export_image']").addClass("hide");
                        //}finally {
                        //}
                    }
                    //这里可以加一层逻辑
                    // 再次全面找一下页面中的点看其连线的个数
                    // 如果达到某一个数值 则以其为中心点画圆
                    scene.childs.forEach(function(item,index){
                        //console.log(item);
                        //console.log(index);
                        // err 155
                        //if(item.inLinks&&item.inLinks.length>=10&&item.inLinks.length<130){
                        //    console.log(item);
                        //    console.log(item.inLinks.length);
                        //    JTopo.layout.layoutNode(scene,item, true);
                        //
                        //}
                        if(item.elementType == "node"){
                            if(item.inLinks.length==150||item.inLinks.length==146){
                                console.log(item);
                                //JTopo.layout.layoutNode(scene,item, true);
                            }
                            //console.log(item.inLinks.length+"        "+item.outLinks.length+"          "+item.tip);
                        }
                    });
                    // 让子点随中心点移动
                    scene.addEventListener('mouseup', function(e) {
                        if(flag) {
                            if (e.target && e.target.layout) {
                                JTopo.layout.layoutNode(scene, e.target, true);
                            }
                        }
                        //if (e.target && e.target.layout) {
                        //    JTopo.layout.layoutNode(scene, e.target, true);
                        //}
                    });
                    portal.tool.showCenter();
                }else {
                    if(scene.childs.length){
                        scene.clear();
                    }
                }
                setTimeout(function() {
                    $(".mloadingDiv").addClass("hide");
                }, 200); // 关闭提示功能
            },
            "selectMode": function() {
                // 框选模式selectMode
                var changeMode = $(".sselect").parent().attr("data-original-title");
                if(changeMode != "框选模式"){
                    stage.mode = "normal";
                    $(".sselect").parent().attr("data-original-title", "框选模式");
                }else {
                    stage.mode = "select";
                    $(".sselect").parent().attr("data-original-title", "退出框选模式");
                }
            },
            "showCenter": function() {
                // 缩放并居中显示
                stage.centerAndZoom();
            },
            "exportImg": function(stage) {
                stage.saveImageInfo();
            },
            "fullScreen": function(canvas) {
                // 全屏显示 fullScreen()
                if (canvas.requestFullscreen) {
                    canvas.requestFullscreen();
                } else if (canvas.msRequestFullscreen) {
                    canvas.msRequestFullscreen();
                } else if (canvas.mozRequestFullScreen) {
                    canvas.mozRequestFullScreen();
                } else if (canvas.webkitRequestFullScreen) {
                    canvas.webkitRequestFullScreen();
                }
            },
            "saveNode": function() {
                if(flag){
                    var userSaveJson = prompt("请为你所保存的状态命名,命名长度不得超过7个汉字或字母或数字,不建议使用特殊字符以免保存失败");
                    /*     $(".modal-body-div").addClass("hide");
                     $(".addOption").parent().addClass("hide");
                     $(".modal-tipUser").removeClass("hide").children(".namedtext").val("");
                     $("#myModal").modal('show');
                     setTimeout(function(){
                     $(".namedtext").focus();
                     },600)*/
                    /*   $(".t-click").click(function(){
                     $("#myModal").modal('hide');
                     var userSaveJson = $(".namedtext").val();
                     })*/
                    if (userSaveJson) {
                        if (userSaveJson.length > 7) {
                            jeBox.alert("名字超长，请重新命名保存");
                            /*      $("#myModal").modal('hide');
                             setTimeout(function() {
                             $(".t-click").attr("disabled","disabled").show();
                             $(".addOption").parent().addClass("hide");
                             $(".modal-tipUser").addClass("hide");
                             $(".modal-body-div").removeClass("hide").text("名字超长，请重新命名保存,2秒后自动关闭");
                             $(".f-click").show();
                             $("#myModal").modal('show');
                             /!*   $(".t-click").click(function() {
                             $("#myModal").modal('hide');
                             })*!/
                             setTimeout(function(){
                             $("#myModal").modal('hide');
                             $(".t-click").removeAttr("disabled");
                             },5000)
                             }, 700);*/
                        } else {
                            var saveJson = "[",
                                time = new Date().getTime();
                            saveJson += "{\"flag\":" + '"' + flag + '"' + "},";
                            scene.childs.forEach(function(item, index, array) {
                                saveJson += "{";
                                if (item.elementType == "node") {
                                    item.id = index; // 解决源码id重复的问题
                                    if (item.snodeArry) {
                                        saveJson += "\"elementType\":" + '"' + item.elementType + '"';
                                        saveJson += ",\"id\":" + '"' + item.id + '"';
                                        saveJson += ",\"x\":" + '"' + item.x + '"';
                                        saveJson += ",\"y\":" + '"' + item.y + '"';
                                        saveJson += ",\"fillColor\":" + '"' + item.fillColor + '"';
                                        saveJson += ",\"tip\":" + '"' + item.tip + '"';

                                        saveJson += ",\"text\":" + '"' + item.text + '"';

                                        saveJson += ",\"radius\":" + '"' + item.radius + '"';
                                        saveJson += ",\"snodeArry\":" + "[";
                                        item.snodeArry.forEach(function(itmf, findx) {
                                            saveJson += "{";
                                            saveJson += "\"tip\":" + '"' + itmf.tip + '"';

                                            saveJson += "\"text\":" + '"' + itmf.text + '"';

                                            saveJson += ",\"radius\":" + '"' + itmf.radius + '"';
                                            saveJson += ",\"fillColor\":" + '"' + itmf.fillColor + '"';
                                            saveJson += ",\"x\":" + '"' + itmf.x + '"';
                                            saveJson += ",\"y\":" + '"' + itmf.y + '"';
                                            saveJson += ",\"alarmNum\":" + '"' + itmf.alarmNum + '"';
                                            if (itmf.nodeInlinks.length) {
                                                saveJson += ",\"inLinks\":" + "[";
                                                itmf.nodeInlinks.forEach(function(flink, flindex) {
                                                    saveJson += "{";
                                                    saveJson += "\"strokeColor\":" + '"' + flink.strokeColor + '"';
                                                    saveJson += ",\"tip\":" + '"' + flink.tip + '"';
                                                    saveJson += ",\"tiptext\":" + '"' + flink.tiptext + '"';
                                                    saveJson += ",\"nodeAtip\":" + '"' + flink.nodeA.tip + '"';
                                                    saveJson += "},";
                                                });
                                                saveJson = saveJson.substring(0, saveJson.length - 1); // 去掉最后的逗号
                                                saveJson += "]";
                                            }
                                            if (itmf.nodeOutlinks.length) {
                                                saveJson += ",\"outLinks\":" + "[";
                                                itmf.nodeOutlinks.forEach(function(flink, flindex) {
                                                    saveJson += "{";
                                                    saveJson += "\"strokeColor\":" + '"' + flink.strokeColor + '"';
                                                    saveJson += ",\"tip\":" + '"' + flink.tip + '"';
                                                    saveJson += ",\"tiptext\":" + '"' + flink.tiptext + '"';
                                                    saveJson += ",\"nodeZtip\":" + '"' + flink.nodeZ.tip + '"';
                                                    saveJson += "},";
                                                });
                                                saveJson = saveJson.substring(0, saveJson.length - 1); // 去掉最后的逗号
                                                saveJson += "]";
                                            }
                                            saveJson += "},";
                                        });
                                        saveJson = saveJson.substring(0, saveJson.length - 1); // 去掉最后的逗号
                                        saveJson += "]";
                                    } else {
                                        saveJson += "\"elementType\":" + '"' + item.elementType + '"';
                                        saveJson += ",\"id\":" + '"' + item.id + '"';
                                        saveJson += ",\"x\":" + '"' + item.x + '"';
                                        saveJson += ",\"y\":" + '"' + item.y + '"';
                                        saveJson += ",\"alarmNum\":" + '"' + item.alarmNum + '"';
                                        saveJson += ",\"fillColor\":" + '"' + item.fillColor + '"';
                                        saveJson += ",\"startTime\":" + '"' + item.startTime + '"';
                                        saveJson += ",\"endTime\":" + '"' + item.endTime + '"';

                                        saveJson += ",\"tip\":" + '"' + item.tip + '"';
                                        saveJson += ",\"text\":" + '"' + item.text + '"';

                                        saveJson += ",\"radius\":" + '"' + item.radius + '"';
                                        saveJson += ",\"relAy\":" + '"' + item.relAy + '"';
                                        saveJson += ",\"portText\":" + '"' + item.portText + '"';
                                    }
                                } else if (item.elementType == "link") {
                                    item.id = "link-" + index; // 解决源码id重复的问题
                                    saveJson += "\"elementType\":" + '"' + item.elementType + '"';
                                    saveJson += ",\"nodeAid\":" + '"' + item.nodeA.id + '"';
                                    saveJson += ",\"nodeZid\":" + '"' + item.nodeZ.id + '"';
                                    saveJson += ",\"lineWidth\":" + '"' + item.lineWidth + '"';
                                    saveJson += ",\"tip\":" + '"' + item.tip + '"';
                                    saveJson += ",\"strokeColor\":" + '"' + item.strokeColor + '"';
                                    saveJson += ",\"tiptext\":" + '"' + item.tiptext + '"';
                                }
                                saveJson += "},";
                            });
                            saveJson = saveJson.substring(0, saveJson.length - 1); // 去掉最后的逗号
                            saveJson += "]";
                            $.ajax({
                                url: "/jtopo/saveJtopo.do",
                                type: "post",
                                data: {
                                    "nodeJson": saveJson,
                                    "name": time + userSaveJson
                                },
                                dataType: "json",
                                success: function(sdata) {
                                    if (sdata) {
                                        portal.showSname();


                                        // $(".addOption").append($('<tr>' +
                                        //     '<td class="tdtext cursor">' + userSaveJson + '</td>' +
                                        //     '<td class="Stime">' +
                                        //     '<span class="showTime">' + portal.fun.getLocaltime(time / 1000) + '</span>' +
                                        //     '<span class="hideTime hide">' + time + '</span></td>+' + '' +
                                        //     '<td data-toggle="tooltip" data-original-title="删除此行">' +
                                        //     '<i class="glyphicon glyphicon-remove removeli close"></i>' +
                                        //     '</td></tr>').on("click", function() {})).parent().addClass("hide");
                                        // $(".qsave").removeClass("hide");
                                        // // 重新渲染用户保存的状态
                                        // $(".tdtext").click(function() {
                                        //     $("#myModal").modal('hide');
                                        //     var text = $(this).siblings(".Stime").children(".hideTime").text() + $(this).text();
                                        //     $(".mloadingDiv").removeClass("hide");
                                        //     $.ajax({
                                        //         url: "/jtopo/getJtopo.do",
                                        //         type: "post",
                                        //         data: {
                                        //             "name": text
                                        //         },
                                        //         dataType: "json",
                                        //         success: function(nodeJson) {
                                        //             portal.tool.showCenter();
                                        //             portal.fun.tdtextClick(nodeJson);
                                        //         }
                                        //     });
                                        //     setTimeout(function() {
                                        //             $(".mloadingDiv").addClass("hide");
                                        //         },
                                        //         600);
                                        // });
                                        // $(".removeli").click(function() {
                                        //     var close = $(this);
                                        //     $.ajax({
                                        //         url: "/jtopo/delJtopo.do",
                                        //         type: "post",
                                        //         data: {
                                        //             name: close.parent().siblings(".Stime").children(".hideTime").text() + close.parent().siblings(".tdtext").text()
                                        //         },
                                        //         dataType: "json",
                                        //         success: function(what) {
                                        //             if (what) {
                                        //                 close.parent().parent().remove();
                                        //                 $(".t-click").click(function() {
                                        //                     if ($(".addOption").children().length == 1) {
                                        //                         $(".qsave").addClass("hide");
                                        //                     }
                                        //                 })
                                        //             }
                                        //         }
                                        //     });
                                        // });


                                        $(".addOption").parent().addClass("hide");
                                        $(".modal-tipUser").addClass("hide");
                                        $(".modal-body-div").text("保存成功").removeClass("hide");
                                        $(".f-click").hide();
                                        $("#myModal").modal('show');
                                    } else {
                                        $(".addOption").parent().addClass("hide");
                                        $(".modal-tipUser").addClass("hide");
                                        $(".modal-body-div").text("保存失败，请您稍后再试").removeClass("hide");
                                        $(".f-click").hide();
                                        $("#myModal").modal('show');
                                    }
                                }
                            });
                        }
                    }
                }
            },
            "toggleIptext": {
                "showIptext": function() {
                    // 显示IP
                    scene.childs.forEach(function(item, index) {
                        if (item.elementType == "node") {
                            item.text = item.tip;
                            item.mouseout(function() {
                                this.text = this.tip;
                            });
                        }
                    });
                    $(".showIp").text("取消显示IP");
                },
                "hideIptext": function() {
                    scene.childs.forEach(function(item, index) {
                        if (item.elementType == "node") {
                            item.text = null;
                            item.mouseout(function() {
                                this.text = null;
                            });
                        }
                    });
                    $(".showIp").text("显示IP地址");
                }
            },
            "IPmerge": {
                "reset_rest_from4": function(cform) {
                    cform.bcast_1.value = "";
                    cform.bcast_2.value = "";
                    cform.bcast_3.value = "";
                    cform.bcast_4.value = "";
                    //
                    cform.nwadr_1.value = "";
                    cform.nwadr_2.value = "";
                    cform.nwadr_3.value = "";
                    cform.nwadr_4.value = "";
                    //
                    cform.firstadr_1.value = "";
                    cform.firstadr_2.value = "";
                    cform.firstadr_3.value = "";
                    cform.firstadr_4.value = "";
                    //
                    cform.lastadr_1.value = "";
                    cform.lastadr_2.value = "";
                    cform.lastadr_3.value = "";
                    cform.lastadr_4.value = "";
                    //
                    cform.snm_1.value = "";
                    cform.snm_2.value = "";
                    cform.snm_3.value = "";
                    cform.snm_4.value = "";
                    //
                    cform.numofaddr.value = "";
                },
                "calNBFL": function(cform) {
                    var rt = 0;
                    portal.tool.IPmerge.reset_rest_from4(cform);
                    tmpvar = parseInt(cform.ip_1.value, 10);
                    if (isNaN(tmpvar) || tmpvar > 255 || tmpvar < 0) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    tmpvar = parseInt(cform.ip_2.value, 10);
                    if (isNaN(tmpvar) || tmpvar > 255 || tmpvar < 0) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    tmpvar = parseInt(cform.ip_3.value, 10);
                    if (isNaN(tmpvar) || tmpvar > 255 || tmpvar < 0) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    tmpvar = parseInt(cform.ip_4.value, 10);
                    if (isNaN(tmpvar) || tmpvar > 255 || tmpvar < 0) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    rt = portal.tool.IPmerge.calcNWmask(cform);
                    if (rt != 0) {
                        // error
                        return (1);
                    }
                    tmpvar = parseInt(cform.bits.value, 10);
                    if (tmpvar < 0) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    if (tmpvar > 32) {
                        cform.numofaddr.value = '错误';
                        return (1);
                    }
                    if (tmpvar == 31) {
                        cform.numofaddr.value = "two hosts";
                        cform.firstadr_1.value = cform.ip_1.value & cform.snm_1.value;
                        cform.firstadr_2.value = cform.ip_2.value & cform.snm_2.value;
                        cform.firstadr_3.value = cform.ip_3.value & cform.snm_3.value;
                        cform.firstadr_4.value = cform.ip_4.value & cform.snm_4.value;
                        //
                        cform.lastadr_1.value = cform.ip_1.value | (~cform.snm_1.value & 0xff);
                        cform.lastadr_2.value = cform.ip_2.value | (~cform.snm_2.value & 0xff);
                        cform.lastadr_3.value = cform.ip_3.value | (~cform.snm_3.value & 0xff);
                        cform.lastadr_4.value = cform.ip_4.value | (~cform.snm_4.value & 0xff);
                        return (1);
                    }
                    if (tmpvar == 32) {
                        cform.numofaddr.value = "one host";
                        cform.firstadr_1.value = cform.ip_1.value;
                        cform.firstadr_2.value = cform.ip_2.value;
                        cform.firstadr_3.value = cform.ip_3.value;
                        cform.firstadr_4.value = cform.ip_4.value;
                        return (1);
                    }
                    cform.numofaddr.value = Math.pow(2, 32 - tmpvar) - 2;
                    //
                    cform.bcast_1.value = cform.ip_1.value | (~cform.snm_1.value & 0xff);
                    cform.bcast_2.value = cform.ip_2.value | (~cform.snm_2.value & 0xff);
                    cform.bcast_3.value = cform.ip_3.value | (~cform.snm_3.value & 0xff);
                    cform.bcast_4.value = cform.ip_4.value | (~cform.snm_4.value & 0xff);
                    //
                    cform.nwadr_1.value = cform.ip_1.value & cform.snm_1.value;
                    cform.nwadr_2.value = cform.ip_2.value & cform.snm_2.value;
                    cform.nwadr_3.value = cform.ip_3.value & cform.snm_3.value;
                    cform.nwadr_4.value = cform.ip_4.value & cform.snm_4.value;
                    //
                    cform.firstadr_1.value = cform.nwadr_1.value;
                    cform.firstadr_2.value = cform.nwadr_2.value;
                    cform.firstadr_3.value = cform.nwadr_3.value;
                    cform.firstadr_4.value = parseInt(cform.nwadr_4.value) + 1;
                    //
                    cform.lastadr_1.value = cform.bcast_1.value;
                    cform.lastadr_2.value = cform.bcast_2.value;
                    cform.lastadr_3.value = cform.bcast_3.value;
                    cform.lastadr_4.value = parseInt(cform.bcast_4.value) - 1;
                    return (0);
                },
                "calcNWmask": function(cform) {
                    tmpvar = parseInt(cform.bits.value, 10);
                    if (isNaN(tmpvar) || tmpvar > 32 || tmpvar < 0) {
                        cform.snm_1.value = '错误';
                        cform.snm_2.value = "";
                        cform.snm_3.value = "";
                        cform.snm_4.value = "";
                        return (1);
                    }
                    cform.snm_1.value = 0;
                    cform.snm_2.value = 0;
                    cform.snm_3.value = 0;
                    cform.snm_4.value = 0;
                    if (tmpvar >= 8) {
                        cform.snm_1.value = 255;
                        tmpvar -= 8;
                    } else {
                        cform.snm_1.value = portal.tool.IPmerge.h_fillbitsfromleft(tmpvar);
                        return (0);
                    }
                    if (tmpvar >= 8) {
                        cform.snm_2.value = 255;
                        tmpvar -= 8;
                    } else {
                        cform.snm_2.value = portal.tool.IPmerge.h_fillbitsfromleft(tmpvar);
                        return (0);
                    }
                    if (tmpvar >= 8) {
                        cform.snm_3.value = 255;
                        tmpvar -= 8;
                    } else {
                        cform.snm_3.value = portal.tool.IPmerge.h_fillbitsfromleft(tmpvar);
                        return (0);
                    }
                    cform.snm_4.value = portal.tool.IPmerge.h_fillbitsfromleft(tmpvar);
                    return (0);
                },
                "h_fillbitsfromleft": function(num) {
                    if (num >= 8) {
                        return (255);
                    }
                    bitpat = 0xff00;
                    while (num > 0) {
                        bitpat = bitpat >> 1;
                        num--;
                    }
                    return (bitpat & 0xff);
                }
            },
            "help": function() {
                // 产品说明
                $(".modal-title").text("产品说明");
                /*      "<li>" + "本产品中每个非中心点都围绕着中心点进行展示，若拖动非中心点，则" +
                 "中心点不会改变位置,若拖动中心点，则非中心点会跟着位移。" +
                 +"当在右上角输入框输入IP" +
                 "地址进行钻取时，进入钻取状态，双击该点本产品会对IP进行由IP到PORT到IP到PORT的通信关系进行梳理。" +"</li>" +*/
                var tuli = "<ul>" +
                    "<li>" + "为了获得好的体验效果请务必使用谷歌内核浏览器浏览本产品,万分感谢!" + "</li>" +
                    "<li>" + "初次进入此页面时，若数据量过大为防止浏览器崩溃，默认仅显示最近200条数据（最多显示2000条数据），在钻取时若数据量过大，默认展示最近255条数据;" + "</li>" +
                    "<li>" +"导航栏" +"</li>" +
                    "<li>" +"下拉框：可根据您的选择重新渲染页面的对应KPI值;" +"</li>" +
                    //"<li>" +"查询：在输入框输入IP进行查询可找到当前页面的所有IP;支持模糊查找;如输入“90”;找到“192.190.1.20”;" +
                    //"若查询的是某个网段的某个IP则建议输入前三位即可;如输入“192.168.1”找到“192.168.1.1/24”" +"</li>" +
                    //"<li>" +"告警提示：可切换是否显示告警(默认显示告警)" +"</li>" +
                    "<li>" +"工具栏" +"</li>" +
                    //"<li>" +"初始状态：可回到进入此页面初始的画面" +"</li>" +
                    "<li>" +"框选/退出框选模式：不能拖个整个画布;只能对IP点进行多个或选择的多个进行拖动或可选把多个进行删除操作;" +"</li>" +
                    "<li>" +"居中显示：可将整个IP进行自动缩放并居中显示;" +"</li>" +
                    //"<li>" +"全屏显示：当点击这个功能入口会启用浏览器的全屏模式显示画布;" +"</li>" +
                    "<li>" +"保存状态：可将页面中的点保存起来;" +"</li>" +
                    "<li>" +"请求保存：可将上次的保存重新渲染在页面;" +"</li>" +
                    "<li>" +"显示/取消IP：将所有的IP文字显示/取消显示在画布;" + "</li>" +
                    //"<li>" +"导出PNG:将画布上所有的对象生成PNG图片并在浏览器另一窗口打开；" +"</li>" +
                    //"<li>" +"匹配用户设置:可将您设置的网段在此页面进行聚合" +"</li>" +
                    "<li>" +"在空白处右键：" +"</li>" +
                    "<li>" +"隐藏/显示工具栏：可对工具栏进行隐藏显示操作;" +"</li>" +
                    "<li>" +"在IP点上右键：" +"</li>" +
                    "<li>" +"复制当前对象IP：会出现一个输入框;需要ctrl+A、ctrl+C进行复制;" +"</li>" +
                    "<li>" +"以此为中心显示：当一个点与多个点相连时;中心点却不是此点;可进行此操作对当前页面进行重新渲染;" +"</li>" +
                    "<li>" +"删除对象：可对当前对象或选中为高亮状态的对象进行删除(通过鼠标右键或键盘DELETE键);" +"</li>" +
                    "<li>" + "在连线上右键：" +"</li>" +
                    "<li>" +"修改颜色（随机）：可随机更改连线的颜色;" +"</li>" +
                    "<li>" +"连线加粗：每点击一次连线加粗1个单位;" +"</li>" +
                    "<li>" +"连线变细：每点击一次连线减细1个单位，直到小于1则不再减小;" +"</li>" +
                    "<li>" +"变成虚线：将连线变成虚线;" +"</li>" +
                    "<li>" + "变成实线：将连线变成实线;" + "</li>" +
                    "</ul>";
                $(".modal-body-div").html(tuli).removeClass("hide");
                $(".addOption").parent().addClass("hide");
                $(".f-click").hide();
                $("#myModal").modal('show');
            }
        },
        "Rclick": {
            "delNode": function() {
                var selectAllNode = [];
                for (var i = 0; i < scene.childs.length; i++) {
                    if (scene.childs[i].elementType == "node") {
                        if (scene.childs[i].selected == true) {
                            selectAllNode.push(scene.childs[i]);
                        }
                    }
                }
                if (selectAllNode.length != 0) {
                    $(".modal-title").text("操作框");
                    $(".addOption").parent().addClass("hide");
                    $(".modal-body-div").text("执行删除" + selectAllNode.length + "个节点且不可恢复，确定要这样操作吗？")
                        .removeClass("hide");
                    $(".f-click").show();
                    $("#myModal").modal('show');
                    $(".t-click").click(function() {
                        for (var i = 0; i < selectAllNode.length; i++) {
                            scene.remove(selectAllNode[i]);
                        }
                    });
                } else {
                    $(".modal-title").text("操作框");
                    $(".addOption").parent().addClass("hide");
                    $(".modal-body-div").text("您已经选择了0个对象，无法执行删除操作").removeClass("hide");
                    $(".f-click").hide();
                    $(" #myModal").modal('show');
                }
            },
            "copyNodetext": function() {
                var textfield = $("#jtopo_textfield");
                if (currentNode.tip == null) return;
                var e = currentNode.tip;
                if(window.innerWidth>=992){
                    if($("#sidebar").hasClass("toggled")){
                        textfield.css({
                            top: event.pageY-$("#header").outerHeight()-30,
                            left: event.pageX-$("#cond_query").width()-90
                        }).show().focus().select().val(e);
                    }else {
                        textfield.css({
                            top: event.pageY-$("#header").outerHeight()-30,
                            left: event.pageX-$("#sidebar").children().width()-$("#cond_query").width()-90
                        }).show().focus().select().val(e);
                    }
                }else {
                    if($("#sidebar").hasClass("toggled")){
                        textfield.css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX
                        }).show().focus().select().val(e);
                    }else {
                        textfield.css({
                            top: event.pageY-$("#header").outerHeight(),
                            left: event.pageX-$("#sidebar").children().width()
                        }).show().focus().select().val(e);
                    }
                }
                e.text = "";
                textfield[0].JTopoNode = e;
                $("#jtopo_textfield").blur(function() {
                    textfield.hide().val();
                }).keypress(function(e) {
                    var key = e.which; // e.which是按键的值
                    if (key == 13) {
                        $(this).blur();
                    }
                });
            },
            "showthisNodeCenter": function() {
                /*
                 * 思路： 1、获取该点的tip 2、获取当前页面与之相连线的点、、、这是难点、、如何获取
                 * 3、去除与之相关连的点的边线。。 4、以此点为中心点，把与之相关联的点重新连线
                 */
                // 在inLinks集合中是从nodeA连接到nodeZ的后者为本点
                // 在outLinks则相反
                // console.log("currentNode.cx:"+currentNode.cx);
                // 此处应该还有更好的方法
                $(".mloadingDiv").removeClass("hide");
                setTimeout(function() {
                    var nodeAarry = [],
                        linkArry = [];
                    if (currentNode.inLinks.length != 0) {
                        for (var i = 0; i < currentNode.inLinks.length; i++) {
                            if (nodeAarry.indexOf(currentNode.inLinks[i].nodeA) == -1) {
                                nodeAarry.push(currentNode.inLinks[i].nodeA);
                            }
                            if (linkArry.indexOf(currentNode.inLinks[i]) == -1) {
                                linkArry.push(currentNode.inLinks[i]);
                            }
                        }
                    }
                    if (currentNode.outLinks.length != 0) {
                        for (var i = 0; i < currentNode.outLinks.length; i++) {
                            if (nodeAarry.indexOf(currentNode.outLinks[i].nodeZ) == -1) {
                                nodeAarry.push(currentNode.outLinks[i].nodeZ);
                            }
                            if (linkArry.indexOf(currentNode.outLinks[i]) == -1) {
                                linkArry.push(currentNode.outLinks[i]);
                            }
                        }
                    }
                    for (var i = 0; i < linkArry.length; i++) {
                        scene.remove(linkArry[i]);
                    }
                    for (var i = 0; i < nodeAarry.length; i++) {
                        newLink(currentNode, nodeAarry[i]);
                        JTopo.layout.layoutNode(scene, currentNode, true);
                    }
                    $(".mloadingDiv").removeClass("hide");
                    var thisOption = $(".kpi").children("option:selected").val();
                    $.ajax({
                        //url: "/jtopo/getJtopoSys.do",
                        url: "/jtopo/getAllCommpair.do",
                        type: "post",
                        data: {
                            "kpiId": thisOption
                            // "kpiName": thisOption
                        },
                        dataType: "json",
                        success: function(system) {
                            portal.Jnode.linkedVal(thisOption, system);
                            $(".mloadingDiv").addClass("hide");
                        }
                    })
                }, 600);
                setTimeout(function() {
                    $(".mloadingDiv").addClass("hide");
                }, 600);
            },
            "selectAll": function() {
                for (var i = 0; i < scene.childs.length; i++) {
                    if (scene.childs[i].elementType == "node") {
                        scene.childs[i].selected = true;
                    }
                }
            },
            "selectNotselectednode": function() {
                for (var i = 0; i < scene.childs.length; i++) {
                    if (scene.childs[i].elementType == "node") {
                        if (scene.childs[i].selected == true) {
                            scene.childs[i].selected = false;
                        } else if (scene.childs[i].selected == false) {
                            scene.childs[i].selected = true;
                        }
                    }
                }
            },
            "toggleToolbar": {
                "hideToolbar": function() {
                    $(".mypanel").addClass("hide");
                    $(".controlToolbar").siblings("a").text("显示工具栏");
                },
                "showToolbar": function() {
                    $(".mypanel").removeClass("hide");
                    $(".controlToolbar").siblings("a").text("隐藏工具栏");
                }
            }
        },
        "searchNode": function() {
            if($("#inpstart").val()||$("#inpend").val()){
                if($.timeStampDate($("#inpend").val())-$.timeStampDate($("#inpstart").val())<0){
                    jeBox.alert("开始时间不能大于结束时间");
                }
            }
            var _startTime,
                _endTime;
            if($.trim($("#inpstart").val())&&$.trim($("#inpstart").val())!=""){
                _startTime = $.timeStampDate($("#inpstart").val())
            }
            if($.trim($("#inpend").val())&& $.trim($("#inpend").val())!=""){
                _endTime = $.timeStampDate($("#inpend").val())
            }
            if(($.trim($("#findText1").val())&&$.trim($("#findText1").val())!="")||($.trim($("#findText2").val())&&$.trim($("#findText2").val())!="")){
                if(_endTime-_startTime-$("#timelidu").children("option:selected").val()>0){
                    $(".mloadingDiv").removeClass("hide");
                    $.ajax({
                        url: "/jtopo/getCommpairByIpPort.do",
                        type: "post",
                        data: {
                            "ip": $.trim($("#findText1").val())&&$.trim($("#findText1").val())!=""?
                                $.trim($("#findText1").val()):undefined,
                            "segment": $.trim($("#findText2").val())&&$.trim($("#findText2").val())!=""?
                                $.trim($("#findText2").val()):undefined,
                            "granularity":$("#timelidu").children("option:selected").val(),//力度
                            "num":$("#Ipnumber").val(),//数量
                            "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                            "kpiId": $(".kpi").children("option:selected").val(),
                            // "kpiName": $(".kpi").children("option:selected").val(),
                            "startTime":_startTime,
                            "endTime": _endTime
                        },
                        dataType: "json",
                        success: function(search) {
                            //核心修改的地方在此
                            /*==========================
                             数据格式有很多Port 和不同的toinfo
                             有多个IP搜索时得跟数据比对 若存在则画页面中 不存在则不画
                             并将存在的ip画一个圆 而后再随机其子端口
                             fromIp 跟 port 都得去重
                            * =================================*/
                            if(search.length){
                                $("#content").animate({scrollTop : $("#content").outerHeight()}, 800);
                                if(scene.childs.length){
                                    scene.clear();
                                }
                                flag = 1;//此为钻取状态
                                var arrIp = [],
                                    arrPort = [],
                                    searchNode = [];
                                search.forEach(function(itme,index){
                                    if(arrIp.indexOf(itme.fromIp) ==-1){
                                        arrIp.push(itme.fromIp)
                                    }
                                });
                                arrIp.forEach(function(item,index){
                                    var locationXY = portal.fun.cicLocationXy(arrIp.length,index,0.2),
                                        sNode = newCircleNode(locationXY.x,locationXY.y,item,"7,237,15");
                                    sNode.radius = 8;
                                    //portal.Jnode.alarm(sNode, 0);
                                    searchNode.push(sNode);
                                });
                                search.forEach(function(item,index){
                                    if(arrIp.indexOf(item.fromIp) !=-1){
                                        if(!arrPort[arrIp.indexOf(item.fromIp)]){
                                            arrPort[arrIp.indexOf(item.fromIp)] = {
                                                "ip":item.fromIp,
                                                "port":[],
                                                "value":[],
                                                "fromAlarm":item.fromAlarm,
                                                "type":item.fromType,
                                                "startTime":item.startTime,
                                                "endTime":item.endTime
                                            };
                                        }                                       
                                        if(arrPort[arrIp.indexOf(item.fromIp)].port.indexOf(item.port)==-1){
                                            arrPort[arrIp.indexOf(item.fromIp)].port.push(item.port);
                                            arrPort[arrIp.indexOf(item.fromIp)].value.push(item.value);
                                        }                
                                    }
                                });
                                arrPort.forEach(function(item,index){
                                    item.port.forEach(function(ptem,pndex){
                                        var portNode = newCircleNode(undefined,undefined,ptem,"102,204,255",undefined,item.startTime,item.endTime);
                                        portal.Jnode.alarm(portNode,item.fromAlarm);
                                        portNode.radius = 8;
                                        var ilink = newLink(searchNode[index],portNode);
                                        ilink.lineWidth = 2;
                                        portal.Jnode.linkValue($(".kpi").children("option:selected").val(),item.value[pndex],ilink);
                                        JTopo.layout.layoutNode(scene, searchNode[index], true);
                                    })
                                });
                                // 让子点随中心点移动
                                scene.addEventListener('mouseup', function(e) {
                                    if (e.target && e.target.layout) {
                                        JTopo.layout.layoutNode(scene, e.target, true);
                                    }
                                });
                                $(".mloadingDiv").addClass("hide");
                                $("button[name='center']").removeClass("hide");
                                $("button[name='save']").removeClass("hide");
                                $("button[name='export_image']").removeClass("hide");
                                portal.tool.showCenter();
                                $(".sshowIP").trigger("click");
                                $(".showCenter").addClass("hide");
                            }else {
                                $(".mloadingDiv").addClass("hide");
                                $(".addOption").parent().addClass("hide");
                                // 弹出框提示IP不存在
                                $("#myModalLabel").text("提示框");
                                $(".modal-body-div").text("当前IP未找到端口").removeClass("hide");
                                $(".f-click").hide();
                                $("#myModal").modal('show');
                            }
                        },
                        error:function () {
                            $(".mloadingDiv").addClass("hide");
                            $(".addOption").parent().addClass("hide");
                            // 弹出框提示IP不存在
                            $("#myModalLabel").text("提示框");
                            $(".modal-body-div").text("当前IP未找到端口或子网或网段未找到").removeClass("hide");
                            $(".f-click").hide();
                            $("#myModal").modal('show');
                        }
                    })
                }else {
                    jeBox.alert("开始时间结束时间差必须大于时间粒度");
                }
            }else {
                //否则请求全部的接口
                if(_endTime-_startTime-$("#timelidu").children("option:selected").val()>0){
                    $(".mloadingDiv").removeClass("hide");
                    $.ajax({
                        url: "/jtopo/getAllCommpair.do",
                        data: {
                            "granularity":$("#timelidu").children("option:selected").val(),//力度
                            "num":$("#Ipnumber").val(),//数量
                            "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                            "kpiId": $(".kpi").children("option:selected").val(),
                            // "kpiName": $(".kpi").children("option:selected").val(),
                            "startTime":_startTime,//$.timeStampDate($("#inpstart").val())
                            "endTime": _endTime//$.timeStampDate($("#inpend").val())
                        },
                        type: 'post',
                        dataType: 'json',
                        success: function(data) {
                            //此处还应该将状态更改为非钻取状态
                            flag = 0;
                            portal.tool.homeNode(data);
                            $("#content").animate({scrollTop : $("#content").outerHeight()}, 800);
                            setTimeout(function(){
                                portal.showSname();
                            },600)
                        }
                    });
                }else {
                    jeBox.alert("开始时间结束时间差必须大于时间粒度");
                }
            }
        },
        "Jnode": {
            "alarm": function(node, nodeValue) {
                if ($("#alarm-button").prop("checked")) {
                    switch (nodeValue) {
                        case "0":
                            node.alarmNum = 0;
                            node.alarmTip = null;
                            node.alarm = node.alarmTip;
                            break;
                        case "1":
                            node.alarmNum = 1;
                            node.alarmTip = "普通告警";
                            node.alarm = node.alarmTip;
                            break;
                        case "2":
                            node.alarmNum = 2;
                            node.alarmTip = "重要告警";
                            node.alarm = node.alarmTip;
                            break;
                        case "3":
                            node.alarmNum = 3;
                            node.alarmTip = "紧急告警";
                            node.alarm = node.alarmTip;
                            break;
                    }
                } else {
                    switch (nodeValue) {
                        case "0":
                            node.alarmNum = 0;
                            node.alarmTip = null;
                            node.alarm = null;
                            break;
                        case "1":
                            node.alarmNum = 1;
                            node.alarmTip = "普通告警";
                            node.alarm = null;
                            break;
                        case "2":
                            node.alarmNum = 2;
                            node.alarmTip = "重要告警";
                            node.alarm = null;
                            break;
                        case "3":
                            node.alarmNum = 3;
                            node.alarmTip = "紧急告警";
                            node.alarm = null;
                            break;
                    }
                }

            },
            "linkValue": function(kpiOptionVal, dataVal, linkName) {
                var dataVal=dataVal-0;
                switch (kpiOptionVal) {
                    //网络流量 TCP流量 UDP流量
                    case "1":
                    case "21":
                    case "22":
                    case "flow":
                    case "tcpTraffic":
                    case "udpTraffic":
                        // 0-->255,255-->100,0 410
                        // 0-->255,0,255
                        // 实时流量
                        linkName.tiptext = dataVal;
                        if (((dataVal / 1024) / 1024) / 1024 - 1 >= 0) {
                            // 此处设置的是G单位的相关展示 类似红色 0-->255 0 255
                            //if (parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) >= 255) {
                            //    linkName.strokeColor = "255,0,255";
                            //} else {
                            //    linkName.strokeColor =
                            //        parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) + ",0,255";
                            //}

                            if (parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) >= 255) {
                                linkName.strokeColor = "255,0,0";
                            } else {
                                var rgbR = (140 + parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024)))>255?255:(140 + parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024))),
                                    rgbB = rgbR==255?(255-(parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024))-(255-140))):0;
                                linkName.strokeColor = rgbB + ",0," + rgbB;
                                    //parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024)) + ",0,255";
                            }


                            if ((((dataVal / 1024) / 1024) / 1024) %
                                parseInt((((dataVal / 1024) / 1024) / 1024))) {
                                linkName.tip = (((dataVal / 1024) / 1024) / 1024).toFixed(2) + "G";
                            } else {
                                linkName.tip = ((dataVal / 1024) / 1024) / 1024 + "G";
                            }
                        } else if ((dataVal / 1024) / 1024 - 1 >= 0) {
                            // 此处设置的是M单位的相关展示 类似橙色 0 255-->0 255
                            //linkName.strokeColor = "0," +
                            //    255 - parseInt(((dataVal / 1024) / 1024) * (255 / 1024)) + ",255";

                            linkName.strokeColor = (255 - parseInt(((dataVal / 1024) / 1024) * ((255-200) / 1024))) + ",255,0";


                            if (((dataVal / 1024) / 1024) %
                                parseInt(((dataVal / 1024) / 1024))) {
                                linkName.tip = ((dataVal / 1024) / 1024).toFixed(2) + "M";
                            } else {
                                linkName.tip = (dataVal / 1024) / 1024 + "M";
                            }
                        } else if (dataVal / 1024 - 1 >= 0) {
                            // 此处设置的是KB单位的相关展示 类似server 0 255 0-->255
                            //linkName.strokeColor = "0,255," + parseInt((dataVal / 1024) * (255 / 1024));


                            linkName.strokeColor = "255," + (213 + parseInt((dataVal / 1024) * ((255-233) / 1024))) + ",0";



                            if ((dataVal / 1024) % parseInt(dataVal / 1024)) {
                                linkName.tip = (dataVal / 1024).toFixed(2) + "kb";
                            } else {
                                linkName.tip = dataVal / 1024 + "kb";
                            }
                        } else {
                            // 此处设置的为B单位的的相关展示 类似subnet 255-->0 255 0
                            //linkName.strokeColor = 255 - parseInt(dataVal * (255 / 1024)) + ",255,0";


                            // 此处设置的为B单位的的相关展示 0,255-->0,255  123
                            linkName.strokeColor = "0,"+(255 - parseInt(dataVal * (255 / 1024))) +",255";


                            if(dataVal>1){
                                if (dataVal%parseInt(dataVal)) {
                                    linkName.tip = dataVal.toFixed(2) + "b";
                                } else {
                                    linkName.tip = dataVal + "b";
                                }
                            }else{
                                linkName.tip = dataVal.toFixed(3) + "b";
                            }
                        }
                        //console.log(linkName.strokeColor);
                        break;
                        // 数据包速率 小包速率
                    case "3":
                    case "19":
                    case "ethernetPkts":
                    case "tinyPkts":
                        //case "tinyPktsRatio":
                        // 数据包速率
                        linkName.tiptext = dataVal;
                        if (((dataVal / 1024) / 1024) / 1024 - 1 >= 0) {
                            // 此处设置的是G单位的相关展示 类似红色 0-->255 0 255
                            //if (parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) >= 255) {
                            //    linkName.strokeColor = "255,0,255";
                            //} else {
                            //    linkName.strokeColor =
                            //        parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) + ",0,255";
                            //}

                            if (parseInt((((dataVal / 1024) / 1024) / 1024) * (255 / 1024)) >= 255) {
                                linkName.strokeColor = "255,0,0";
                            } else {
                                var rgbR = (140 + parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024)))>255?255:(140 + parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024))),
                                    rgbB = rgbR==255?(255-(parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024))-(255-140))):0;
                                linkName.strokeColor = rgbB + ",0," + rgbB;
                                //parseInt((((dataVal / 1024) / 1024) / 1024) * ((255-140+255) / 1024)) + ",0,255";
                            }


                            //if ((((dataVal / 1024) / 1024) / 1024) %
                            //    parseInt((((dataVal / 1024) / 1024) / 1024))) {
                            //    linkName.tip = (((dataVal / 1024) / 1024) / 1024).toFixed(2) + "GPS";
                            //} else {
                            //    linkName.tip = ((dataVal / 1024) / 1024) / 1024 + "GPS";
                            //}
                            linkName.tip = dataVal  + "pps";
                        } else if ((dataVal / 1024) / 1024 - 1 >= 0) {
                            // 此处设置的是M单位的相关展示 类似橙色 0 255-->0 255
                            //linkName.strokeColor = "0," +
                            //    255 - parseInt(((dataVal / 1024) / 1024) * (255 / 1024)) + ",255";

                            linkName.strokeColor = (255 - parseInt(((dataVal / 1024) / 1024) * ((255-200) / 1024))) + ",255,0";


                            //if (((dataVal / 1024) / 1024) %
                            //    parseInt(((dataVal / 1024) / 1024))) {
                            //    linkName.tip = ((dataVal / 1024) / 1024).toFixed(2) + "MPS";
                            //} else {
                            //    linkName.tip = (dataVal / 1024) / 1024 + "MPS";
                            //}
                            linkName.tip = dataVal  + "pps";
                        } else if (dataVal / 1024 - 1 >= 0) {
                            // 此处设置的是KB单位的相关展示 类似server 0 255 0-->255
                            //linkName.strokeColor = "0,255," + parseInt((dataVal / 1024) * (255 / 1024));

                            linkName.strokeColor = "255," + (213 + parseInt((dataVal / 1024) * ((255-233) / 1024))) + ",0";



                            //if ((dataVal / 1024) % parseInt(dataVal / 1024)) {
                            //    linkName.tip = (dataVal / 1024).toFixed(2) + "kbps";
                            //} else {
                            //    linkName.tip = dataVal / 1024 + "kbps";
                            //}
                            linkName.tip = dataVal  + "pps";
                        } else {
                            // 此处设置的为B单位的的相关展示 类似subnet 255-->0 255 0
                            //linkName.strokeColor = 255 - parseInt(dataVal * (255 / 1024)) + ",255,0";
                            linkName.strokeColor = "0,"+(255 - parseInt(dataVal * (255 / 1024))) +",255";




                            if(dataVal>1){
                                if (dataVal%parseInt(dataVal)) {
                                    linkName.tip = dataVal.toFixed(2) + "pps";
                                } else {
                                    linkName.tip = dataVal + "pps";
                                }
                            }else{
                                linkName.tip = dataVal.toFixed(3) + "pps";
                            }
                        }
                        break;
                        // 服务端通信时延 客户端通信时延 链路时延RTT
                    case "4":
                    case "5":
                    case "6":
                    case "serverDurDelay":
                    case "clientDurDelay":
                    case "serverConDelay":
                    case "clientConDelay":
                    case "appDelay":
                        // 服务端时延
                        linkName.tiptext = dataVal;
                        if ((dataVal / 1000) / 60 - 1 >= 0) {
                            // 此处设置的是M单位的相关展示 类似橙色 0 255-->0 255
                            //if(parseInt(((dataVal / 1000) / 60) * (255 / 60))>=255){
                            //    linkName.strokeColor = "255,0,255";
                            //}else{
                            //    linkName.strokeColor = "0,255," + parseInt(((dataVal / 1000) / 60) * (255 / 60));
                            //}

                           // 140-->255,0,255-->0  370

                            if(parseInt(((dataVal / 1000) / 60) * (370 / 60))>=370){
                                linkName.strokeColor = "255,0,0";
                            }else{
                                var colorR = 140 + parseInt(((dataVal / 1000) / 60) * (370 / 60))>=255?
                                    255:
                                140 + parseInt(((dataVal / 1000) / 60) * (370 / 60)),
                                    colorB = colorR==255?
                                        (255 - (parseInt(((dataVal / 1000) / 60) * (370 / 60))-115)>=0?
                                    255 - (parseInt(((dataVal / 1000) / 60) * (370 / 60))-115):0):255;


                                    //colorB = 255 - (parseInt(((dataVal / 1000) / 60) * (370 / 60))-115)>=0?
                                    //255 - (parseInt(((dataVal / 1000) / 60) * (370 / 60))-115)>=0:0;

                                linkName.strokeColor = colorR + ",0," + colorB;
                            }

                            if (((dataVal / 1000) / 60) %
                                parseInt(((dataVal / 1000) / 60))) {
                                linkName.tip = ((dataVal / 1000) / 60).toFixed(2) + "m";
                            } else {
                                linkName.tip = (dataVal / 1000) / 60 + "m";
                            }
                        } else if (dataVal / 1000 - 1 >= 0) {
                            // 此处设置的是s单位的相关展示 类似server 0 255 0-->255
                            //linkName.strokeColor = "0,255," + parseInt((dataVal / 1000) * (255 / 60));


                            //255-->200,213-->255,0     55+12 = 67
                            //var colorR = (255- parseInt((dataVal / 1000) * (67 / 60)))>=200?
                            //    (255- parseInt((dataVal / 1000) * (67 / 60))):200,
                            //    colorG = 231 + (parseInt((dataVal / 1000) * (67 / 60))-55);


                            var colorG = 231 + parseInt((dataVal / 1000) * (67 / 60))>=255?255:231 + parseInt((dataVal / 1000) * (67 / 60)),
                                colorR = colorG==255?255- (parseInt((dataVal / 1000) * (67 / 60))-12):200;


                            linkName.strokeColor = colorG + "," + colorR + ",0";

                            if ((dataVal / 1000) % parseInt(dataVal / 1000)) {
                                linkName.tip = (dataVal / 1000).toFixed(2) + "s";
                            } else {
                                linkName.tip = dataVal / 1000 + "s";
                            }
                        } else if (dataVal) {
                            // 此处设置的为ms单位的的相关展示 类似subnet 255-->0 255 0
                            //linkName.strokeColor = parseInt(255 - dataVal * (255 / 1000)) + ",255,0";



                            linkName.strokeColor = "0,"+parseInt(255 - dataVal * (255 / 1000)) + ",255";

                            if(dataVal>1){
                                if(dataVal%parseInt(dataVal)){
                                    linkName.tip = dataVal.toFixed(2) + "ms";
                                }else{
                                    linkName.tip = dataVal + "ms";
                                }
                            }else{
                                linkName.tip = dataVal.toFixed(3) + "ms";
                            }

                        } else {
                            linkName.tip = dataVal + "ms";
                        }
                        break;
                        //网络丢包率 服务端丢包率 客户端丢包率 小包比率
                    case "9":
                    case "10":
                    case "11":
                    case "20":
                    case "netPktLost":
                    case "serverPktLost":
                    case "clientPktLost":
                    case "tinyPktsRatio":
                        // 网络传输丢包率
                        linkName.tiptext = dataVal;
                        if (dataVal > 100) {
                            //linkName.strokeColor = "0,255,255";

                            linkName.strokeColor = "255,0,0";



                            if(dataVal%parseInt(dataVal)){
                                linkName.tip = dataVal.toFixed(2) + "%";
                            }else{
                                linkName.tip = dataVal + "%";
                            }
                        } else if (dataVal) {
                            // 255-->0 255 0
                            //linkName.strokeColor = parseInt(255 - dataVal * (100 / 255)) + ",255,0";

                            // 0,255-->0,255    255-->200,213--->255,0        255+ 55+ 12 = 322 暂未写好

                            var colorG = 213 + parseInt(dataVal * (100 /62))>=255?
                                255:
                            213 + parseInt(dataVal * (100 /62)),
                                colorR = colorG == 255?255 - (parseInt(dataVal * (100 /62))-12):255;

                                //colorR = 255 - (parseInt(dataVal * (100 /62))-12)>=200?
                                //    (255 - (parseInt(dataVal * (100 /62))-12)<=255?255 - (parseInt(dataVal * (100 /62))-12):255):200;
                            linkName.strokeColor = colorR + "," + colorG + ",0";



                            if(dataVal>1){
                                if(dataVal%parseInt(dataVal)){
                                    linkName.tip = dataVal.toFixed(2) + "%";
                                }else{
                                    linkName.tip = dataVal + "%";
                                }
                            }else{
                                linkName.tip = dataVal.toFixed(3) + "%";
                            }

                        } else {
                            linkName.strokeColor = "0,255,255";

                            linkName.tip = dataVal + "%";
                        }
                        break;
                        //会话数量 尝试连接数量 中断会话数量
                    case "2":
                    case "12":
                    case "13":
                    case "attemptedNum":
                    case "finAckPkts":
                        // 通信中断数量
                        linkName.tiptext = dataVal;
                        if (dataVal > 1020) {
                            linkName.strokeColor = "255,0,255";



                            if(dataVal%parseInt(dataVal)){
                                linkName.tip = dataVal.toFixed(2) + "个";
                            }else{
                                linkName.tip = dataVal + "个";
                            }
                        } else if (dataVal > 765) {
                            // 0-->255 0 255
                            //linkName.strokeColor = parseInt(dataVal - 766) + ",0,255";

                            // 140-->255,0,255-->0    115

                            console.log("255,0,"+(255 - parseInt((dataVal - 765)*(255/255))));
                            linkName.strokeColor = "255,0,"+(255 - parseInt((dataVal - 765)*(255/255)));



                            if(dataVal%parseInt(dataVal)){
                                linkName.tip = dataVal.toFixed(2) + "个";
                            }else{
                                linkName.tip = dataVal + "个";
                            }
                        } else if (dataVal > 510) {
                            // 0 255-->0 255
                            //linkName.strokeColor = "0," + parseInt(dataVal - 510) + ",255";

                            // 140-->255,0,255-->0    115
                            //console.log(140 + parseInt((dataVal - 510)*(115/255)) + "0,255");
                            linkName.strokeColor = 140 + parseInt((dataVal - 510)*(115/255)) + "0,255";



                            if(dataVal % parseInt(dataVal)){
                                linkName.tip = dataVal.toFixed(2) + "个";
                            }else{
                                linkName.tip = dataVal + "个";
                            }
                        } else if (dataVal > 255) {
                            // 0 255 0-->255
                            //linkName.strokeColor = "0,255," + parseInt(dataVal - 255);

                            //255-->200,213-->255,0  55+12=67
                            var colorG = 231+ parseInt((dataVal - 255)*(67/255))>=255?255:231+ parseInt((dataVal - 255)*(67/255)),
                                //colorR = colorG==255?255-(parseInt((dataVal - 255)*(255/67)-12)):255;
                                colorR = colorG==255?255-(parseInt((dataVal - 255)*(67/255)-12)):255;
                            //console.log(colorR + "," + colorG + ",0");
                            linkName.strokeColor = colorR + "," + colorG + ",0";

                            // 140-->255,0,255-->0





                            if(dataVal % parseInt(dataVal)){
                                linkName.tip = dataVal.toFixed(2) + "个";
                            }else{
                                linkName.tip = dataVal + "个";
                            }
                        } else if (dataVal) {
                            // 255-->0 255 0
                            //linkName.strokeColor = parseInt(255 - dataVal) + ",255,0";

                            //0,255-->0,255
                            linkName.strokeColor = "0,"+parseInt(255 - dataVal) + ",255";



                            if(dataVal>1){
                                if(dataVal % parseInt(dataVal)){
                                    linkName.tip = dataVal.toFixed(2) + "个";
                                }else{
                                    linkName.tip = dataVal + "个";
                                }
                            }else{
                                linkName.tip = dataVal.toFixed(3) + "个";
                            }
                        } else {
                            //linkName.strokeColor = 255 - dataVal + ",255,0";

                            linkName.strokeColor = "0,255,255";
                            linkName.tip = dataVal + "个";
                        }
                        break;
                }
            },
            "flgLinkValueIp": function(node, kpiOptionVal, data) {
                data.forEach(function(kpitem) {
                    node.outLinks.forEach(function(linkName) {
                        if (linkName.nodeA.tip.split(":")[0] == kpitem.ip) {
                            if (linkName.nodeZ.tip == kpitem.port) {
                                portal.Jnode.linkValue(kpiOptionVal,kpitem.value,linkName);
                            }
                        }
                    })
                });
            },
            "flgLinkValuePort": function(node, kpiOptionVal, data) {
                data.forEach(function(kpitem) {
                    node.outLinks.forEach(function(linkName) {
                        if (linkName.nodeZ.tip.split(":")[0] == kpitem.ip) {
                            portal.Jnode.linkValue(kpiOptionVal,kpitem.value,linkName);
                        }
                    })
                });
            },
            "linkedVal": function(kpiOptionVal, data) {
                data.forEach(function(item, index) {
                    scene.childs.forEach(function(ilink, ndex) {
                        if (ilink.elementType == "link") {
                            if (ilink.nodeA.tip == item.fromIp) {
                                if (ilink.nodeZ.tip == item.toInfo) {
                                    portal.Jnode.linkValue(kpiOptionVal,item.value,ilink);
                                }
                            } else if (ilink.nodeZ.tip == item.fromIp) {
                                if (ilink.nodeA.tip == item.toInfo) {
                                    portal.Jnode.linkValue(kpiOptionVal,item.value,ilink);
                                }
                            }
                        } else if (ilink.elementType == "node") {
                            if (ilink.snodeArry) {
                            } else {
                                if (ilink.tip == item.fromIp) {
                                    portal.Jnode.alarm(ilink,item.fromAlarm);
                                } else if (ilink.tip == item.toInfo) {
                                    portal.Jnode.alarm(ilink,item.toAlarm);
                                }
                            }
                        }
                    });
                });
            }
        },
        "fun": {
            "getLocaltime": function(nS) {
                return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
            },
            "enterinputIpval": function(ip) {
                var ipNet = ip.split("/"),
                    ipd = ipNet[0].split(".");
                $("input[name='ip_1']").val(ipd[0]);
                $("input[name='ip_2']").val(ipd[1]);
                $("input[name='ip_3']").val(ipd[2]);
                $("input[name='ip_4']").val(ipd[3]);
                $("input[name='bits']").val(ipNet[1]);
            },
            "tdtextClick":function(nodeJson){
                scene.clear();
                flag = nodeJson[0].flag - 0;
                var getJsonNodeArray = [],//所有得到点的集合数组
                    itemNodeText;
                nodeJson.forEach(function(item, index) {
                    if (item.elementType == "node") {
                        var itemNode = newCircleNode(item.x - 0, item.y - 0, item.tip, item.fillColor);
                        if (item.snodeArry) {
                            itemNode.id = item.id; //将id赋值给已画的点
                            itemNode.tip = item.tip; //将tip赋值给已画的点
                            itemNode.text = item.text&&item.text!="null"&&item.text!="undefined"?item.text:null;
                            itemNode.radius = item.radius; //将大小赋值给已画的点
                            itemNode.snodeArry = item.snodeArry; //将合成此点的初始点集合赋值以将将来赞释读
                            portal.Jnode.alarm(itemNode, item.alarmNum); //释读告警数值
                            getJsonNodeArray[itemNode.id] = itemNode; //将此点存进数组
                            itemNodeText = itemNode.text;
                        } else {
                            itemNode.id = item.id;
                            itemNode.tip = item.tip;
                            itemNode.text = item.text&&item.text!="null"&&item.text!="undefined"?item.text:null;
                            itemNode.radius = item.radius;
                            itemNode.startTime = item.startTime;
                            itemNode.endTime = item.endTime;
                            itemNode.relAy = item.relAy;
                            itemNode.portText = item.portText; //将portText赋值给已画的点
                            portal.Jnode.alarm(itemNode, item.alarmNum);
                            getJsonNodeArray[itemNode.id] = itemNode;
                            itemNodeText = itemNode.text;
                        }
                    }
                });
                nodeJson.forEach(function(item, index) {
                    if (item.elementType == "link") {
                        var ilink = newLink(getJsonNodeArray[item.nodeAid], getJsonNodeArray[item.nodeZid]);
                        ilink.lineWidth = item.lineWidth;
                        ilink.strokeColor = item.strokeColor;
                        ilink.tip = item.tip;
                        ilink.tiptext = item.tiptext;
                        JTopo.layout.layoutNode(scene, getJsonNodeArray[item.nodeAid], true);
                    }
                });
                if (flag) {
                    $("button[name='center']").removeClass("hide");
                    $("button[name='save']").removeClass("hide");
                    $("button[name='export_image']").removeClass("hide");
                    $(".showCenter").addClass("hide");
                } else {
                    $(".showCenter").removeClass("hide");
                    $("button[name='save']").addClass("hide");
                    if(tryErr){
                        $("button[name='center']").addClass("hide");
                        $("button[name='export_image']").addClass("hide");
                    }else {
                        $("button[name='center']").removeClass("hide");
                        $("button[name='export_image']").removeClass("hide");
                    }
                }
                if(itemNodeText){
                    portal.tool.toggleIptext.showIptext();
                    $(".sshowIP").parent().attr("data-original-title","取消显示IP");
                }else {
                    portal.tool.toggleIptext.hideIptext();
                    $(".sshowIP").parent().attr("data-original-title","显示IP");
                }
                portal.tool.showCenter();
            },
            /**
             @params totalNum 总数量
             @params i 下标 第多少个 从0开始
             @params radius 半径 (0~0.8）
             */
            "cicLocationXy":function(totalNum,i,radius){
                var centerPointX = $("#canvas").width()/ 2,
                    centerPointY = $("#canvas").height()/ 2,
                    _boxW = $("#canvas").width(),
                    _boxH = $("#canvas").height(),
                    _angle = 360/totalNum,
                    hudu = (2*Math.PI / 360) * _angle*(i+1),
                    cicR;
                if(_boxW>_boxH){
                    cicR = _boxH/2*radius;
                }else if(_boxW<_boxH){
                    cicR = _boxW/2*radius
                }else{
                    cicR = _boxW/2*radius
                }
                var x = centerPointX + Math.sin(hudu) * cicR,
                    y = centerPointY + Math.cos(hudu) * cicR;
                return {
                    x:x,
                    y:y
                }
            },
            "canvasResize":function(){
                $("#canvas").attr({
                    "width": $("#canvas").parent().width(),
                    "height": $(document).height()-$("#header").height()-15-5>623?
                    $(document).height()-$("#header").height()-15-5:
                        623
                }); // 对canvas宽高的自适应
                portal.tool.showCenter();
                $(".mloadingDiv").height(window.innerHeight);
                $(".loadingDivChild").css({
                    "left": ($(".block-area").width() - $(".loadingDivChild").width()) / 2,
                    //"top": (canvasHeight - $(".loadingDivChild").height()) / 2
                    "top": ($(document).height()-$("#header").height()-15-5 - $(".loadingDivChild").height()) / 2
                }); // 对提示用户的自适应
            }
        }
    };
    //为kpi下拉框赋值
    $.ajax({
        url:"/plot/getTopoKpis.do",
        type:"post",
        async:false,
        data:{},
        dataType:"json",
        success:function (data) {
           data.forEach(function (item,index) {
               var optionHtml;
               if(index){
                   optionHtml = '<option value='+item.id+' data-name='+item.name+
                       ' data-unit='+item.unit+' data-moduleId='+item.moduleId+'>'+item.displayName+'</option>';
               }else {
                   optionHtml = '<option value='+item.id+' data-name='+item.name+
                       ' data-unit='+item.unit+' data-moduleId='+item.moduleId+' selected="selected">'+item.displayName+'</option>';
               }
               $("#groupType").append(optionHtml);
           })
       },
        error:function () {
        }
    });
    //为观察点选择框赋值
    $.post("/watchpointController/getFindAll.do",null,function(data){
        var option = "";
        for(var i = 0, len = data.length; i < len; i++) {
            option += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
        }
        $("#watchpoint").append(option);
        $("#watchpoint").selectpicker('refresh');
    },"json");
    $.ajax({
        url: "/jtopo/getAllCommpair.do",
        data: {
            "granularity":$("#timelidu").children("option:selected").val(),//力度
            "num":$("#Ipnumber").val(),//数量
            "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                $("#watchpoint").children("option:selected").val():undefined,//观察点ID
            // "kpiName": $(".kpi").children("option:selected").val(),
            "kpiId": $(".kpi").children("option:selected").val(),
            "startTime":$.timeStampDate($("#inpstart").val()),
            "endTime": $.timeStampDate($("#inpend").val())
        },
        type: 'post',
        dataType: 'json',
        success: function(data) {
            if(location.href.split("?").length==1){
                portal.tool.homeNode(data);
                setTimeout(function(){
                    portal.showSname();
                },600)
            }else {
                $(".mloadingDiv").addClass("hide");
            }
        }
    });
    /* 右键菜单处理 */
    - function() {
        // 当单击到stage场景对象的时候右键菜单隐藏
        stage.click(function(event) {
            if (event.button == 0) { // 左键
                $("#contextmenu").hide();
                $("#linkmenu").hide();
            }
        });
        // 右键所有的功能入口
        $("#contextmenu li").click(function() {
            var text = $(this).children("a").text();
            switch (text) {
                case "复制当前对象IP":
                    portal.Rclick.copyNodetext();
                    $("#contextmenu").hide();
                    break;
                case "以此为中心显示":
                    portal.Rclick.showthisNodeCenter();
                    $("#contextmenu").hide();
                    break;
                case "删除对象":
                    portal.Rclick.delNode();
                    $("#contextmenu").hide();
                    break;
                case "全选":
                    portal.Rclick.selectAll();
                    break;
                case "反选":
                    portal.Rclick.selectNotselectednode();
                    break;
                case "隐藏工具栏":
                    portal.Rclick.toggleToolbar.hideToolbar();
                    setTimeout(function() {
                        $("#contextmenu").hide();
                    }, 50);
                    break;
                case "显示工具栏":
                    portal.Rclick.toggleToolbar.showToolbar();
                    $("#contextmenu").hide();
                    break;
                case "框选模式":
                    stage.mode = "select";
                    $(".kxcheck").text("退出框选模式");
                    $(".sselect").parent().attr("data-original-title", "退出框选模式");
                    $("#contextmenu").hide();
                    break;
                case "退出框选模式":
                    stage.mode = "normal";
                    $(".kxcheck").text("框选模式");
                    $(".sselect").parent().attr("data-original-title", "框选模式");
                    $("#contextmenu").hide();
                    break;
                case "居中显示":
                    portal.tool.showCenter();
                    $("#contextmenu").hide();
                    break;
                case "全屏显示":
                    portal.tool.fullScreen(canvas);
                    $("#contextmenu").hide();
                    break;
                case "显示IP地址":
                    portal.tool.toggleIptext.showIptext();
                    $("#contextmenu").hide();
                    break;
                case "取消显示IP":
                    portal.tool.toggleIptext.hideIptext();
                    $("#contextmenu").hide();
                    break;
                case "导出PNG":
                    portal.tool.exportImg();
                    $("#contextmenu").hide();
                    break;
                case "保存状态":
                    portal.tool.saveNode();
                    break;
                case "产品说明":
                    portal.tool.help();
                    $("#contextmenu").hide();
                    break;
                case "放大对象":
                    currentNode.scaleX += 0.2;
                    currentNode.scaleY += 0.2;
                    break;
                case "缩小对象":
                    currentNode.scaleX -= 0.2;
                    currentNode.scaleY -= 0.2;
                    break;
            }
        });
        // 右键在连线上的所有的功能入口
        $("#linkmenu li").click(function() {
            var text = $(this).children("a").text();
            switch (text) {
                case "修改颜色(随机)":
                    currentLink.strokeColor = JTopo.util.randomColor(); // 线条颜色随机
                    break;
                case "连线加粗":
                    currentLink.lineWidth += 1;
                    break;
                case "连线变细":
                    currentLink.lineWidth -= 1;
                    break;
                case "变成虚线":
                    currentLink.dashedPattern = 5;
                    break;
                case "变成实线":
                    currentLink.dashedPattern = null;
                    break;
                case "删除连线":
                    scene.remove(currentLink);
                    break;
            }
        });
        $("#contextmenu").hover(function() {
            $(this).fadeIn();
        }, function() {
            $(this).fadeOut();
        }); // 右键菜单栏的显示和消失
        $("#linkmenu").hover(function() {
            $(this).fadeIn();
        }, function() {
            $(this).fadeOut();
        }); // 连线的右键菜单的显示和消失
        // 增加咯键盘DELL键盘删除功能
        $(window).keydown(function(event) {
            if (event.keyCode == 46 || event.which == 46) {
                portal.Rclick.delNode();
            }
        });
    }();
    // 工具栏
    ! function() {
        // 默认模式
        $(".sdefault").click(function() {
            $(".mloadingDiv").removeClass("hide");
            $(".showCenter").removeClass("hide"); // 把以此为中心功能给开放
            flag = 0;
            $("button[name='save']").addClass("hide");
            if(tryErr){
                $("button[name='center']").addClass("hide");
                $("button[name='export_image']").addClass("hide");
            }else {
                $("button[name='center']").removeClass("hide");
                $("button[name='export_image']").removeClass("hide");
            }
            $.ajax({
                //url: "/jtopo/getJtopoSys.do",
                url: "/jtopo/getAllCommpair.do",
                type: "post",
                data: {
                    // "kpiName": $(".kpi").children("option:selected").val()
                    "kpiId": $(".kpi").children("option:selected").val()
                },
                dataType: "json",
                success: function(data) {
                    scene.clear();
                    //$(".showIp").text("显示IP地址");
                    portal.tool.homeNode(data);
                    //portal.tool.showCenter();
                }
            })
        });
        // 框选模式
        $(".sselect").click(function() {
            portal.tool.selectMode();
        });
        // 缩放并居中显示
        $(".scenter").click(function() {
            portal.tool.showCenter();
        });
        // 导出PNG
        $(".sexport_image").click(function() {
            portal.tool.exportImg(stage);
        });
        // 全屏显示
        $(".sfull_screen").click(function() {
            portal.tool.fullScreen(canvas);
        });
        // 保存状态
        $(".ssave").click(function() {
            portal.tool.saveNode();
        });
        // 显示IP
        $(".sshowIP").click(function() {
            var changeIptext = $(".sshowIP").parent().attr("data-original-title");
            if(changeIptext == "显示IP"){
                portal.tool.toggleIptext.showIptext();
                $(".sshowIP").parent().attr("data-original-title","取消显示IP");
            }else{
                portal.tool.toggleIptext.hideIptext();
                $(".sshowIP").parent().attr("data-original-title","显示IP");
            }
        });
        // 匹配用户设置
        $(".susrerSeting").click(function() {
            if(!flag){
                $(".mloadingDiv").removeClass("hide");
                $.ajax({
                    url: "/jtopo/getJtopoIpnet.do",
                    type: "post",
                    data: "",
                    dataType: "json",
                    success: function(data) {
                        $(".mloadingDiv").removeClass("hide");
                        data.forEach(function(item) {
                            portal.fun.enterinputIpval(item.ipnet); //将用户设置的网段传入
                            portal.tool.IPmerge.calNBFL($(".form_ip")[0]); //将form表单作为参数传进去
                            var s1 = $("input[name='firstadr_1']").val(),
                                s2 = $("input[name='firstadr_2']").val(),
                                s3 = $("input[name='firstadr_3']").val(),
                                s4 = $("input[name='firstadr_4']").val(),
                                t1 = $("input[name='lastadr_1']").val(),
                                t2 = $("input[name='lastadr_2']").val(),
                                t3 = $("input[name='lastadr_3']").val(),
                                t4 = $("input[name='lastadr_4']").val(),
                                resultArry = [], //将得到的范围值存给变量 将页面中的点与这范围进行筛选并将筛选结果赋值给resultArry数组
                                x = Math.random() * parseInt(window.innerWidth * 0.6) +
                                    parseInt(window.innerWidth * 0.2),
                                y = Math.random() * parseInt((window.innerHeight -
                                        $(".navbar-logo").height() - 20) * 0.6) +
                                    parseInt((window.innerHeight - $(".navbar-logo ").height() - 20) * 0.2),
                                kpiVal = $(".kpi").children("option:selected").val();
                            scene.childs.forEach(function(jtem) {
                                if (jtem.elementType == "node") {
                                    if (jtem.tip.indexOf("/") == -1) {
                                        //无“/”一种算法
                                        var nodeTip = jtem.tip.split(".");
                                        if (s1 != "") {
                                            if (t1 != "") {
                                                if (nodeTip[0] >= s1 && nodeTip[0] <= t1 &&
                                                    nodeTip[1] >= s2 && nodeTip[1] <= t2 &&
                                                    nodeTip[2] >= s3 && nodeTip[2] <= t3 &&
                                                    nodeTip[3] >= s4 && nodeTip[3] <= t4) {
                                                    resultArry.push(jtem);
                                                }
                                            } else {
                                                //最后可用IP为空的情况 即范围值(min-max)max不存在的情况
                                                if (nodeTip[0] == s1 && nodeTip[1] == s2 && nodeTip[2] == s3 && nodeTip[3] == s4) {
                                                    resultArry.push(jtem);
                                                }
                                            }
                                        }
                                    } else {
                                        //带“/”一种算带
                                        portal.fun.enterinputIpval(jtem.tip);
                                        portal.tool.IPmerge.calNBFL($(".form_ip")[0]);
                                        var js1 = $("input[name='firstadr_1']").val(),
                                            js2 = $("input[name='firstadr_2']").val(),
                                            js3 = $("input[name='firstadr_3']").val(),
                                            js4 = $("input[name='firstadr_4']").val(),
                                            jt1 = $("input[name='lastadr_1']").val(),
                                            jt2 = $("input[name='lastadr_2']").val(),
                                            jt3 = $("input[name='lastadr_3']").val(),
                                            jt4 = $("input[name='lastadr_4']").val();
                                        if (js1 != "") {
                                            if (jt1 != "") {
                                                if (js1 >= s1 && js1 <= t1 &&
                                                    js2 >= s2 && js2 <= t2 &&
                                                    js3 >= s3 && js3 <= t3 &&
                                                    js4 >= s4 && js4 <= t4 &&
                                                    jt1 >= s1 && jt1 <= t1 &&
                                                    jt2 >= s2 && jt2 <= t2 &&
                                                    jt3 >= s3 && jt3 <= t3 &&
                                                    jt4 >= s4 && jt4 <= t4) {
                                                    resultArry.push(jtem);
                                                }
                                            } else {
                                                if (js1 >= s1 && js1 <= t1 &&
                                                    js2 >= s2 && js2 <= t2 &&
                                                    js3 >= s3 && js3 <= t3 &&
                                                    js4 >= s4 && js4 <= t4) {
                                                    resultArry.push(jtem);
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                            if (resultArry.length) {
                                var centerNewNode = newCircleNode(x, y, item.name, "89,0,255"),
                                    centempArry = [], //暂存centerNewNode的已连接点的tip
                                    pice = 0, //连线重复的次数
                                    linkIndex = [], //重复连线的下标暂存
                                    alarmAy = []; //告警暂存
                                centerNewNode.setSize(16, 16);
                                centerNewNode.snodeArry = resultArry; //把合并咯的点给保存在centerNode的属性里面
                                resultArry.forEach(function(node, index) {
                                    //console.log(node);
                                    centerNewNode.snodeArry[index].nodeInlinks = node.inLinks; //将此点所有内连的线给存起来
                                    centerNewNode.snodeArry[index].nodeOutlinks = node.outLinks; //将此点所有外连的线给存起来
                                    alarmAy.push(node.alarmNum);
                                    scene.remove(node);
                                });
                                portal.Jnode.alarm(centerNewNode, Math.max.apply(null, alarmAy)); //展示最大告警
                                centerNewNode.snodeArry.forEach(function(eNode) {
                                    if (eNode.nodeInlinks.length) {
                                        for (var i = 0; i < eNode.nodeInlinks.length; i++) {
                                            scene.childs.forEach(function(scNode) {
                                                if (scNode.elementType == "node") {
                                                    if (eNode.nodeInlinks[i].nodeA.tip == scNode.tip) {
                                                        if (centerNewNode.outLinks && centerNewNode.outLinks.length) {
                                                            for (var j = 0; j < centerNewNode.outLinks.length; j++) {
                                                                if (centempArry.indexOf(centerNewNode.outLinks[j].nodeZ.tip) == -1) {
                                                                    centempArry.push(centerNewNode.outLinks[j].nodeZ.tip);
                                                                }
                                                            }
                                                            if (centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip) == -1) {
                                                                var tlink = newLink(centerNewNode, scNode);
                                                                tlink.tip = eNode.nodeInlinks[i].tip;
                                                                tlink.strokeColor = eNode.nodeInlinks[i].strokeColor;
                                                                tlink.tiptext = eNode.nodeInlinks[i].tiptext;
                                                            } else {
                                                                centerNewNode.outLinks[centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip)].tiptext += eNode.nodeInlinks[i].tiptext;
                                                                if (kpiVal != 1 && kpiVal != 20 && kpiVal != 21 && kpiVal != 10 && kpiVal != 11 && kpiVal != 12 && kpiVal != 13) {
                                                                    var keyl = centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip);
                                                                    linkIndex[keyl] = ++pice;
                                                                } else {
                                                                    portal.Jnode.linkValue(kpiVal,
                                                                        centerNewNode.outLinks[centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip)].tiptext,
                                                                        centerNewNode.outLinks[centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip)]);
                                                                }
                                                            }
                                                        } else {
                                                            var tlink = newLink(centerNewNode, scNode);
                                                            tlink.tip = eNode.nodeInlinks[i].tip;
                                                            tlink.strokeColor = eNode.nodeInlinks[i].strokeColor;
                                                            tlink.tiptext = eNode.nodeInlinks[i].tiptext;
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    if (eNode.nodeOutlinks.length) {
                                        for (var i = 0; i < eNode.nodeOutlinks.length; i++) {
                                            scene.childs.forEach(function(scNode) {
                                                if (scNode.elementType == "node") {
                                                    if (eNode.nodeOutlinks[i].nodeZ.tip == scNode.tip) {
                                                        if (centerNewNode.outLinks && centerNewNode.outLinks.length) {
                                                            for (var j = 0; j < centerNewNode.outLinks.length; j++) {
                                                                if (centempArry.indexOf(centerNewNode.outLinks[j].nodeZ.tip) == -1) {
                                                                    centempArry.push(centerNewNode.outLinks[j].nodeZ.tip);
                                                                }
                                                            }
                                                            if (centempArry.indexOf(eNode.nodeOutlinks[i].nodeZ.tip) == -1) {
                                                                var tlink = newLink(centerNewNode, scNode);
                                                                tlink.tip = eNode.nodeOutlinks[i].tip;
                                                                tlink.strokeColor = eNode.nodeOutlinks[i].strokeColor;
                                                                tlink.tiptext = eNode.nodeOutlinks[i].tiptext;
                                                            } else {
                                                                centerNewNode.outLinks[centempArry.indexOf(eNode.nodeOutlinks[i].nodeZ.tip)].tiptext += eNode.nodeOutlinks[i].tiptext;
                                                                if (kpiVal != 1 && kpiVal != 20 && kpiVal != 21 && kpiVal != 10 && kpiVal != 11 && kpiVal != 12 && kpiVal != 13) {
                                                                    var keyl = centempArry.indexOf(eNode.nodeInlinks[i].nodeA.tip);
                                                                    linkIndex[keyl] = ++pice;
                                                                } else {
                                                                    portal.Jnode.linkValue(kpiVal,
                                                                        centerNewNode.outLinks[centempArry.indexOf(eNode.nodeOutlinks[i].nodeZ.tip)].tiptext,
                                                                        centerNewNode.outLinks[centempArry.indexOf(eNode.nodeOutlinks[i].nodeZ.tip)]);
                                                                }
                                                            }
                                                        } else {
                                                            var tlink = newLink(centerNewNode, scNode);
                                                            tlink.tip = eNode.nodeOutlinks[i].tip;
                                                            tlink.strokeColor = eNode.nodeOutlinks[i].strokeColor;
                                                            tlink.tiptext = eNode.nodeOutlinks[i].tiptext;
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    }
                                });
                                if (pice) {
                                    for (var k = 0; k < linkIndex.length; k++) {
                                        if (linkIndex[k] != undefined) {
                                            var setvalue = centerNewNode.outLinks[k].tiptext / linkIndex[k];
                                            portal.Jnode.linkValue(kpiVal, setvalue, centerNewNode.outLinks[k]);
                                        }
                                    }
                                }
                            }
                            $(".mloadingDiv").addClass("hide");
                        });
                    }
                })
            }
        });
        // 产品说明
        $(".sexplain").click(function() {
            portal.tool.help();
        });
        // 请求保存
        $(".qsave").click(function() {
            $(".modal-body-div").addClass("hide");
            $(".addOption").parent().removeClass("hide");
            $(".f-click").hide();
            $("#myModalLabel").text("拓朴列表");
            $("#myModal").modal('show');
        });
    }();
    // 导航栏功能区
    ~ function() {
        // 告警提示的显示与否
        $("#alarm-button").click(function() {
            if ($(this).prop("checked")) {
                scene.childs.forEach(function(item) {
                    if (item.elementType == "node") {
                        item.alarm = item.alarmTip;
                    }
                })
            } else {
                scene.childs.forEach(function(item) {
                    if (item.elementType == "node") {
                        item.alarm = null;
                    }
                })
            }
        });
        // 监视kpi子元素的变化
        $(".kpi").change(function() {
            $(".mloadingDiv").removeClass("hide");
            var thisOption = $(this).children("option:selected").val();
            scene.childs.forEach(function(item) {
                if (item.elementType == "link") {
                    item.tip = null;
                    //item.strokeColor="166,183,53";
                    item.strokeColor = '56,113,127';
                    item.tiptext = 0;
                }
            });
            if (flag) {
                // 如果为钻取状态
                $(".showCenter").removeClass("hide");
                scene.childs.forEach(function(item, index) {
                    if (item.elementType == "node") {
                        if (item.tip.indexOf(".") != -1) {
                            // 此为IP 请求IP端口
                            if (item.outLinks.length) {
                                $.ajax({
                                    url: "/jtopo/getCommpairByIpPort.do ",
                                    type: "post",
                                    data: {
                                        "ip": item.tip.split(":")[0],
                                        // "kpiName": thisOption,
                                        "kpiId": thisOption,
                                        "startTime":event.target.startTime,
                                        "endTime":event.target.endTime
                                    },
                                    dataType: "json",
                                    success: function(okpi) {
                                        portal.Jnode.flgLinkValueIp(item, thisOption, okpi);
                                    }
                                })
                            }
                        } else {
                            // 此为端口 请求端口接口
                            if (item.outLinks.length) {
                                $.ajax({
                                    //url: "/jtopo/getJtopoSysPort.do",
                                    url: "/jtopo/getCommpairByIpPort.do ",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        "ip": item.inLinks[0].nodeA.tip.split(":")[0],
                                        "port": item.tip,
                                        // "kpiName": thisOption,
                                        "kpiId": thisOption,
                                        "startTime":event.target.startTime,
                                        "endTime":event.target.endTime
                                    },
                                    success: function(okpi) {
                                        portal.Jnode.flgLinkValuePort(item, thisOption, okpi);
                                    }
                                })
                            }
                        }
                    }
                });
                setTimeout(function() {
                    $(".mloadingDiv").addClass("hide");
                }, 600);
            } else {
                // 如果为非钻取状态
                $(".showCenter").removeClass("hide");
                $.ajax({
                    //url: "/jtopo/getJtopoSys.do",
                    url: "/jtopo/getAllCommpair.do",
                    type: "post",
                    data: {
                        "granularity":$("#timelidu").children("option:selected").val(),//力度
                        "num":$("#Ipnumber").val(),//数量
                         "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                             $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                         // "kpiName": $(".kpi").children("option:selected").val(),
                         "kpiId": $(".kpi").children("option:selected").val(),
                          "startTime":$.timeStampDate($("#inpstart").val()),//$.timeStampDate($("#inpstart").val())
                          "endTime": $.timeStampDate($("#inpend").val())//$.timeStampDate($("#inpend").val())
                        //"kpiName": thisOption
                    },
                    dataType: "json",
                    success: function(system) {
                        portal.Jnode.linkedVal(thisOption, system);
                        function oldCodetemp(){
                            //此代码暂时用不到 故将其封装在此
                            //计算匹配用户设置的点的连线的KPI值
                            scene.childs.forEach(function(node) {
                                if (node.snodeArry) {
                                    //console.log(node);
                                    var pices = 0,
                                        linkArry = [];
                                    node.snodeArry.forEach(function(snode) {
                                        system.forEach(function(item) {
                                            if (snode.tip == item.fromIp) {
                                                node.outLinks.forEach(function(outLink, index) {
                                                    if (outLink.nodeZ.tip == item.toInfo) {
                                                        //此处把value值赋给link
                                                        if (outLink.tiptext) {
                                                            pices++;
                                                            linkArry[index] = pices;
                                                            outLink.tiptext += item.value;
                                                        } else {
                                                            pices++;
                                                            linkArry[index] = pices;
                                                            outLink.tiptext = item.value;
                                                        }
                                                    }
                                                })
                                            } else if (snode.tip == item.toInfo) {
                                                node.outLinks.forEach(function(outLink) {
                                                    if (outLink.nodeZ.tip == item.fromIp) {
                                                        //此处把value值赋给link
                                                        if (outLink.tiptext) {
                                                            pices++;
                                                            linkArry[index] = pices;
                                                            outLink.tiptext += item.value;
                                                        } else {
                                                            pices++;
                                                            linkArry[index] = pices;
                                                            outLink.tiptext = item.value;
                                                        }
                                                    }
                                                })
                                            }
                                        })
                                    });
                                    //上面循环都走完之后取出Link上的value判断kpi是该累加还是该平均
                                    if (pices) {
                                        var kpiVal = $(".kpi").children("option:selected").val();
                                        if (kpiVal != 1 && kpiVal != 20 && kpiVal != 21 && kpiVal != 10 && kpiVal != 11 && kpiVal != 12 && kpiVal != 13) {
                                            //平均
                                            for (var k = 0; k < linkArry.length; k++) {
                                                if (linkArry[k] != undefined) {
                                                    var setvalue = node.outLinks[k].tiptext / linkArry[k];
                                                    portal.Jnode.linkValue(kpiVal, setvalue, node.outLinks[k]);
                                                }
                                            }
                                        } else {
                                            //累加
                                            for (var k = 0; k < linkArry.length; k++) {
                                                if (linkArry[k] != undefined) {
                                                    var setvalue = node.outLinks[k].tiptext;
                                                    portal.Jnode.linkValue(kpiVal, setvalue, node.outLinks[k]);
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        }
                        setTimeout(function() {
                            $(".mloadingDiv").addClass("hide");
                        }, 600);
                    }
                })
            }
        });
        // 搜索IP
        $("#searchButton").click(function() {
            portal.searchNode();
        });
        // 当在输入框按下enter键的时候触发searchButton的click事件
        //window.enterPressHandler = function(event) {
        //    if (event.keyCode == 13 || event.which == 13) {
        //        $('#searchButton').click();
        //    }
        //};
    }();
    // 探索功能
    + function() {
        scene.dbclick(function(event) {
            if (event.target == null) {
                return
            } else {
                if (event.target.tip == undefined) {
                    return;
                } else {
                    if (event.target.elementType == "node") {
                        var thisOption = $(".kpi").children("option:selected").val();
                        if (flag) {
                            $(".showCenter").addClass("hide");
                            var clickTip = event.target.tip;
                            if (clickTip.indexOf(".") != -1) {
                                // 非等于-1 则为IP 如果为IP则请求这个接口
                                var beginTime = (new Date()).getTime();
                                if (event.target.inLinks.length) {
                                    $(".mloadingDiv").removeClass("hide");
                                    $.ajax({
                                        url: "/jtopo/getCommpairByIpPort.do",
                                        type: "post",
                                        data: {
                                            "ip": event.target.tip,
                                            // "kpiName": thisOption,
                                            "kpiId": thisOption,
                                            "granularity":$("#timelidu").children("option:selected").val(),//力度
                                            "num":$("#Ipnumber").val(),//数量
                                            "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                                $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                                            "startTime":event.target.startTime,
                                            "endTime":event.target.endTime
                                        },
                                        dataType: "json",
                                        success: function(searchIn) {
                                            if (searchIn.length) {
                                                // 防止用户再次双击事件
                                                if (event.target.outLinks.length == 0) {
                                                    if(searchIn.length==255){
                                                        jeBox.alert("当前通信较多，仅展示最近255条数据");
                                                    }
                                                    var portNodeArray = [];
                                                    searchIn.forEach(function(item, index) {
                                                        var portNode = newCircleNode(undefined, undefined, item.port, "102,204,255",undefined,item.startTime,item.endTime),
                                                            ilink = newLink(event.target, portNode);
                                                        portNode.radius = 8;
                                                        ilink.lineWidth = 2;
                                                        JTopo.layout.layoutNode(scene, event.target, true);
                                                        if ($(".kpi").css("display") != "none") {
                                                            portal.Jnode.linkValue(thisOption, item.value, ilink);
                                                        }
                                                        portal.Jnode.alarm(portNode, item.toAlarm);

                                                        /*   if ($(".showIp").text() == "取消显示IP") {
                                                         portNode.text = portNode.tip;
                                                         portNode.mouseout(function() {
                                                         this.text = this.tip;
                                                         });
                                                         } else {
                                                         portNode.text = null;
                                                         portNode.mouseout(function() {
                                                         this.text = null;
                                                         });
                                                         }*/
                                                        if ( $(".sshowIP").parent().attr("data-original-title") == "取消显示IP") {
                                                            portNode.text = portNode.tip;
                                                            portNode.mouseout(function() {
                                                                this.text = this.tip;
                                                            });
                                                        } else {
                                                            portNode.text = null;
                                                            portNode.mouseout(function() {
                                                                this.text = null;
                                                            });
                                                        }
                                                        portNodeArray.push(portNode);
                                                    });
                                                    portNodeArray.forEach(function(portNode, index) {
                                                        if (portNode.tip == event.target.portText) {
                                                            if (event.target.x - event.target.inLinks[0].nodeA.x) {
                                                                portNode.x = Math.abs((event.target.x - event.target.inLinks[0].nodeA.x) / 2) + Math.min(event.target.x, event.target.inLinks[0].nodeA.x);
                                                            } else {
                                                                portNode.x = event.target.x;
                                                            }
                                                            if (event.target.y - event.target.inLinks[0].nodeA.y) {
                                                                portNode.y = Math.abs((event.target.y - event.target.inLinks[0].nodeA.y) / 2) +
                                                                    Math.min(event.target.y, event.target.inLinks[0].nodeA.y);
                                                            } else {
                                                                portNode.y = event.target.y;
                                                            }
                                                        }
                                                    });
                                                    $(".mloadingDiv").addClass("hide");
                                                }
                                                $(".mloadingDiv").addClass("hide");
                                            } else {
                                                $(".mloadingDiv").addClass("hide");
                                                // 弹出框提示未找到
                                                $(".addOption").parent().addClass("hide");
                                                $(".modal-body-div").text("已经钻取到尽头了").removeClass("hide");
                                                $(".f-click").hide();
                                                $("#myModal").modal('show');
                                            }
                                        }
                                    });
                                }
                            } else {
                                // 否则请求这个接口 此为端口的接口
                                $(".mloadingDiv").removeClass("hide");
                                $.ajax({
                                    url: "/jtopo/getCommpairByIpPort.do ",
                                    type: "post",
                                    data: {
                                        "ip": event.target.inLinks[0].nodeA.tip,
                                        "port": event.target.tip,
                                        "granularity":$("#timelidu").children("option:selected").val(),//力度
                                        "num":$("#Ipnumber").val(),//数量
                                        "watchpointId":$("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                            $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                                        // "kpiName": thisOption,
                                        "kpiId": thisOption,
                                        "startTime":event.target.startTime,
                                        "endTime":event.target.endTime
                                    },
                                    dataType: "json",
                                    success: function(searchIn) {
                                        if (searchIn.length) {
                                            if (event.target.outLinks.length == 0) {
                                                if(searchIn.length==255){
                                                    jeBox.alert("当前通信较多，仅展示最近255条数据");
                                                }
                                                if (event.target.inLinks[0].nodeA.portText != undefined) {
                                                    var strNodetip;
                                                    if(event.target.inLinks[0] &&
                                                        event.target.inLinks[0].nodeA &&
                                                        event.target.inLinks[0].nodeA.inLinks[0] &&
                                                        event.target.inLinks[0].nodeA.inLinks[0].nodeA &&
                                                        event.target.inLinks[0].nodeA.inLinks[0].nodeA.inLinks[0] &&
                                                        event.target.inLinks[0].nodeA.inLinks[0].nodeA.inLinks[0].nodeA &&
                                                        event.target.inLinks[0].nodeA.inLinks[0].nodeA.inLinks[0].nodeA.tip){
                                                        strNodetip = event.target.inLinks[0].nodeA.inLinks[0].nodeA.inLinks[0].nodeA.tip;
                                                    }
                                                }
                                                /*   if (event.target.inLinks[0].nodeA.tip.split(":")[1] != undefined) {
                                                 var strNodetip = event.target.inLinks[0].nodeA.inLinks[0].nodeA.inLinks[0].nodeA.tip.split(":")[0] + ":" + event.target.inLinks[0].nodeA.inLinks[0].nodeA.tip;
                                                 console.log(strNodetip);
                                                 }*/
                                                var portNodeArray = [];
                                                searchIn.forEach(function(item, index) {
                                                    switch (item.type){
                                                        case "server":
                                                            var color = "7,237,15";
                                                            break;
                                                        case "client":
                                                            var color = "255,255,255";
                                                            break;
                                                    }
                                                    var portNode = newCircleNode(undefined, undefined, item.ip , color,undefined,item.startTime,item.endTime),
                                                        ilink = newLink(event.target, portNode);
                                                    portNode.radius = 8;
                                                    ilink.lineWidth = 2;
                                                    JTopo.layout.layoutNode(scene, event.target, true);
                                                    if ($(".kpi").css("display") != "none") {
                                                        portal.Jnode.linkValue(thisOption, item.value, ilink);
                                                    }
                                                    portal.Jnode.alarm(portNode, item.fromAlarm);
                                                    portNode.portText=item.port;
                                                    if ( $(".sshowIP").parent().attr("data-original-title") == "取消显示IP") {
                                                        portNode.text = portNode.tip;
                                                        portNode.mouseout(function() {
                                                            this.text = this.tip;
                                                        });
                                                    } else {
                                                        portNode.text = null;
                                                        portNode.mouseout(function() {
                                                            this.text = null;
                                                        });
                                                    }
                                                    if (portNode.tip != strNodetip) {
                                                        portNodeArray.push(portNode);
                                                    } else {
                                                        scene.remove(portNode);
                                                        if (searchIn.length == 1) {
                                                            $(".mloadingDiv").addClass("hide");
                                                            // 弹出框提示未找到
                                                            $(".addOption").parent().addClass("hide");
                                                            $(".modal-body-div").text("已经钻取到尽头了").removeClass("hide");
                                                            $(".f-click").hide();
                                                            $("#myModal").modal('show');
                                                        }
                                                    }
                                                });
                                            }
                                            $(".mloadingDiv").addClass("hide");
                                        } else {
                                            $(".mloadingDiv").addClass("hide");
                                            // 弹出框提示未找到
                                            $(".addOption").parent().addClass("hide");
                                            $(".modal-body-div").text("已经钻取到尽头了").removeClass("hide");
                                            $(".f-click").hide();
                                            $("#myModal").modal('show');
                                        }
                                    }
                                });
                            }
                        } else {
                            if (event.target.snodeArry) {
                                $(".mloadingDiv").removeClass("hide");
                                //此为非保存的状态
                                var kpiVal = $(".kpi").children("option:selected").val();
                                var initialNodeArry = event.target.snodeArry; //把最初的点的数组给解读出来
                                initialNodeArry.forEach(function(nodef) {
                                    if (nodef.elementType) {
                                        scene.add(nodef);
                                        portal.Jnode.alarm(nodef, nodef.alarmNum); //判断告警
                                        //判断KPI值
                                        if (nodef.nodeInlinks.length) {
                                            for (var i = 0; i < nodef.nodeInlinks.length; i++) {
                                                scene.childs.forEach(function(fnode) {
                                                    if (fnode.elementType == "node") {
                                                        if (fnode.tip == nodef.nodeInlinks[i].nodeA.tip) {
                                                            var rlink = newLink(fnode, nodef);
                                                            $.ajax({
                                                                url: "/jtopo/getAllCommpair.do",
                                                                type: "post",
                                                                data: {
                                                                    // "kpiName": kpiVal
                                                                    "kpiId": kpiVal
                                                                },
                                                                dataType: "json",
                                                                success: function(system) {
                                                                    system.forEach(function(item) {
                                                                        if (item.fromIp == nodef.tip) {
                                                                            if (item.toInfo == fnode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        } else if (item.toInfo == nodef.tip) {
                                                                            if (item.fromIp == fnode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        }
                                                                    })
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                        if (nodef.nodeOutlinks.length) {
                                            for (var j = 0; j < nodef.nodeOutlinks.length; j++) {
                                                scene.childs.forEach(function(fnode) {
                                                    if (fnode.elementType == "node") {
                                                        if (fnode.tip == nodef.nodeOutlinks[j].nodeZ.tip) {
                                                            var rlink = newLink(nodef, fnode);
                                                            $.ajax({
                                                                url: "/jtopo/getAllCommpair.do",
                                                                type: "post",
                                                                data: {
                                                                    // "kpiName": kpiVal
                                                                    "kpiId": kpiVal
                                                                },
                                                                dataType: "json",
                                                                success: function(system) {
                                                                    system.forEach(function(item) {
                                                                        if (item.fromIp == nodef.tip) {
                                                                            if (item.toInfo == fnode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        } else if (item.toInfo == nodef.tip) {
                                                                            if (item.fromIp == fnode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        }
                                                                    })
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    } else {
                                        $(".mloadingDiv").removeClass("hide");
                                        //从保存状重新渲染后的释放过程
                                        var enode = newCircleNode(nodef.x - 0, nodef.y - 0);
                                        enode.radius = nodef.radius;
                                        enode.fillColor = nodef.fillColor;
                                        enode.tip = nodef.tip;
                                        enode.inLinks = nodef.inLinks;
                                        enode.outLinks = nodef.outLinks;
                                        portal.Jnode.alarm(enode, nodef.alarmNum);
                                        if (nodef.inLinks) {
                                            for (var i = 0; i < enode.inLinks.length; i++) {
                                                scene.childs.forEach(function(fnode) {
                                                    if (fnode.elementType == "node") {
                                                        if (fnode.tip == enode.inLinks[i].nodeAtip) {
                                                            var rlink = newLink(fnode, enode);
                                                            $.ajax({
                                                                url: "/jtopo/getAllCommpair.do",
                                                                type: "post",
                                                                data: {
                                                                    // "kpiName": kpiVal
                                                                    "kpiId": kpiVal
                                                                },
                                                                dataType: "json",
                                                                success: function(system) {
                                                                    system.forEach(function(item) {
                                                                        if (item.fromIp == enode.tip) {
                                                                            if (item.toInfo == enode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        } else if (item.toInfo == enode.tip) {
                                                                            if (item.fromIp == enode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        }
                                                                    })
                                                                }
                                                            });
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                        if (nodef.outLinks) {
                                            for (var j = 0; j < enode.outLinks.length; j++) {
                                                scene.childs.forEach(function(fnode) {
                                                    if (fnode.elementType == "node") {
                                                        if (fnode.tip == enode.outLinks[j].nodeZtip) {
                                                            var rlink = newLink(enode, fnode);
                                                            $.ajax({
                                                                url: "/jtopo/getAllCommpair.do",
                                                                type: "post",
                                                                data: {
                                                                    // "kpiName": kpiVal
                                                                    "kpiId": kpiVal
                                                                },
                                                                dataType: "json",
                                                                success: function(system) {
                                                                    system.forEach(function(item) {
                                                                        if (item.fromIp == enode.tip) {
                                                                            if (item.toInfo == enode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        } else if (item.toInfo == enode.tip) {
                                                                            if (item.fromIp == enode.tip) {
                                                                                portal.Jnode.linkValue(kpiVal, item.value, rlink);
                                                                            }
                                                                        }
                                                                    })
                                                                }
                                                            });
                                                        }
                                                    }
                                                })
                                            }

                                        }
                                    }
                                });
                                scene.remove(event.target);
                                setTimeout(function() {
                                    $(".mloadingDiv").addClass("hide");
                                }, 600)
                            } else {
                                $(".mloadingDiv").removeClass("hide");
                                if(event.target.tip.indexOf("/") != -1){
                                    //找到有带“/”的字符
                                    var eRel = event.target.relAy;
                                    for(var v in eRel) {
                                        if (v != "del") {
                                            var eRelNode = newCircleNode(event.target.x, event.target.y, v, "255,255,255", event.target.alarmNum);
                                            portal.Jnode.alarm(eRelNode, eRelNode.alarmNum);
                                            for (var j = 0; j < eRel[v].length; j++) {
                                                scene.childs.forEach(function (item) {
                                                    if (item.elementType == "node") {
                                                        if (eRel[v][j] == item.tip) {
                                                            var eRlink = newLink(eRelNode, item);
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    }
                                    scene.remove(event.target);
                                    setTimeout(function(){
                                        $(".mloadingDiv").addClass("hide");
                                    },60);
                                }else{
                                    $.ajax({
                                        url: "/jtopo/getCommpairByIpPort.do ",
                                        type: "post",
                                        data: {
                                            "ip": event.target.tip,
                                            "kpiId": thisOption,
                                            // "kpiName": thisOption,
                                            "startTime":event.target.startTime,
                                            "endTime":event.target.endTime
                                        },
                                        dataType: "json",
                                        success: function(search) {
                                            var beginTime = (new Date()).getTime();
                                            if (search.length) {
                                                scene.clear();
                                                switch (search[0].type){
                                                    case "server":
                                                        var color = "7,237,15";
                                                        break;
                                                    case "client":
                                                        var color = "255,255,255";
                                                        break;
                                                }
                                                if(search.length==255){
                                                    jeBox.alert("当前通信较多，仅展示最近255条数据");
                                                }
                                                var searchNode = newCircleNode(parseInt(window.innerWidth * 0.7),
                                                    parseInt((window.innerHeight - $(".navbar-logo ").height() - 20) * 0.3),
                                                    event.target.tip, color);
                                                searchNode.radius = 8;
                                                portal.Jnode.alarm(searchNode, search[0]["fromAlarm"]);
                                                var portNodeArray = [];
                                                search.forEach(function(item, index) {
                                                    var portNode = newCircleNode(undefined, undefined, item.port, "102,204,255",undefined,item.startTime,item.endTime);
                                                    portal.Jnode.alarm(portNode, item.toAlarm);
                                                    portNode.radius = 8;
                                                    var ilink = newLink(searchNode, portNode);
                                                    ilink.lineWidth = 2;
                                                    portal.Jnode.linkValue(thisOption, item.value, ilink);
                                                    JTopo.layout.layoutNode(scene, searchNode, true);
                                                });
                                                $(".mloadingDiv").addClass("hide");
                                                flag = 1;
                                                $("button[name='center']").removeClass("hide");
                                                $("button[name='save']").removeClass("hide");
                                                $("button[name='export_image']").removeClass("hide");
                                                portal.tool.showCenter();
                                                $(".sshowIP").trigger("click");
                                                $(".showCenter").addClass("hide");
                                            } else {
                                                $(".mloadingDiv").addClass("hide");
                                                $(".addOption").parent().addClass("hide");
                                                // 弹出框提示IP不存在
                                                $("#myModalLabel").text("提示框");
                                                $(".modal-body-div").text("当前IP未找到端口").removeClass("hide");
                                                $(".f-click").hide();
                                                $("#myModal").modal('show');
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    }else if(event.target.elementType == "link"){
                        if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                            var _nodeAtip = event.target.nodeA.tip,
                                _nodeZtip = event.target.nodeZ.tip,
                                _strTime = event.target.nodeA.startTime || event.target.nodeZ.startTime,
                                _endTime = event.target.nodeA.endTime || event.target.nodeZ.endTime,
                                _watId = $("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                    $("#watchpoint").children("option:selected").val():"";
                            if(_nodeAtip.indexOf(".")!=-1 && _nodeZtip.indexOf(".")!=-1){
                                // ip=ip1,ip2
                                location.href = "commun_queue.html?ipmCenterId=1&starttime="+_strTime
                                    +"&endtime="+_endTime+"&watchpointId="+_watId+"&ip="+_nodeAtip+","+_nodeZtip;
                            }else {
                                // serverIp=[图片]192.168.1.12:80
                                if(_nodeAtip.indexOf(".")!=-1){
                                    location.href = "commun_queue.html?ipmCenterId=1&starttime="+_strTime
                                        +"&endtime="+_endTime+"&watchpointId="+_watId+"&serverIp="+_nodeAtip+":"+_nodeZtip;
                                }else {
                                    location.href = "commun_queue.html?ipmCenterId=1&starttime="+_strTime
                                        +"&endtime="+_endTime+"&watchpointId="+_watId+"&serverIp="+_nodeZtip+":"+_nodeAtip;
                                }
                            }
                        }
                    }
                }
            }
        });
    }();
    // 拖动工具栏
    (function() {
        var move = false; // 移动标记
        var _x, _y; // 鼠标离控件左上角的相对位置
        $("div[name='topo_toolbar']").mousedown(function(e) {
            move = true;
            _x = window.innerWidth - e.pageX - parseInt($("div[name='topo_toolbar']").css("right"));
            _y = e.pageY - parseInt($("div[name='topo_toolbar']").css("top"));
        });
        $(document).mousemove(function(e) {
            if (move) {
                var x = window.innerWidth - e.pageX - _x; // 控件右上角到屏幕右上角的相对位置
                var y = e.pageY - _y;
                $("div[name='topo_toolbar']").css({
                    "top": y,
                    "right": x
                });
            }
        }).mouseup(function() {
            move = false;
        });
    }());

    $('#myModal').on('shown.bs.modal', function() {
        $(window).keydown(function(event) {
            if (event.keyCode == 13) {
                $(".t-click").click();
            }
        });
    });
    /* center modal */
    function centerModals(){
        $('.modal').each(function(i){
            var $clone = $(this).clone().css('display', 'block').appendTo('body');
            var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
            top = top > 0 ? top : 0;
            $clone.remove();
            $(this).find('.modal-content').css("margin-top", top);
        });
    }
    $('.modal').on('show.bs.modal', centerModals);
    $(window).on('resize', centerModals);
    $('[data-toggle="tooltip"]').tooltip(); // 弹出bootstrap提示框
    $(window).resize(function() {
        portal.fun.canvasResize()
    });
    $("#menu-toggle").click(function(){
        setTimeout(function(){
            $("#canvas").attr({
                "width": $("#canvas").parent().width()
            }); // 对canvas宽高的自适应
        },30)
    });
    $("#canvas").parent().bind("mousewheel",function(event,delta){
        if(scene.childs.length){
            stage.wheelZoom = 1.2; // 设置鼠标缩放比例
        }
    })
});