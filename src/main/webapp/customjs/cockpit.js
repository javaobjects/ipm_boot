/**
 * Created by yanbo on 2017/11/2.
 * <script src="js/jQuery/timeStamp.js"></script>
 * <script src="customjs/sidebar.js"></script>
 *
 *  有一个bug图形保存的位置在大屏跟小屏会不一样
 *  关键点是图形的高度在大屏与小屏不一样
 *  而高度则是由宽度的百分比而定的
 *  而大屏与小屏的区别在于其宽度不一样
 *  而存储的top值又是绝对值
 *  解决方法：
 *  保存时计算top值与容器的宽度的占比
 *
 *  读取时根据此占比重新赋值top值
 *
 *  图形未考虑到其h标签是绝对值不随宽高而改变所以还是存在误差。
 *
 **/
$(function () {
    var _cockpit = {
        /**
         * 控制column刷新的变量
         * 其值为Boolean
         **/
        refreNum:0,
        /**
         * 是否为回溯状态
         * 默认为false
         * 若是回溯状态则为tru
         * 并取回溯的开始结束时间赋值
         * @param onOff
         * @param strTime
         * @param endTime
         */
        huiSu:undefined,
        /**
         * 获取URL参数的函数
         * 调用 getUrlParams.key
         * @returns {Object}
         */
        getUrlParams: function () {
            var url = location.search,
                params = new Object();
            if (url.indexOf("?") != -1) {
                var arr = url.substr(1).split("&"),
                    tmp = null;
                for (var i = 0, len = arr.length; i < len; i++) {
                    tmp = arr[i].split("=");
                    params[tmp[0]] = tmp[1];
                }
            }
            return params;
        },
        /**
         * 验证数字是否为整数
         * @param obj Number
         * @returns {boolean}
         */
        isInteger: function (obj) {
            return typeof obj === 'number' && obj % 1 === 0
        },
        /**
         * 转换单位 将其转换成最小的单位值 如 1万个 == 10000个
         * @param unit String 单位
         * @param value Number 值
         * @returns {{numValue: Number, nuitStr: String}}
         */
        conversionUnit: function (unit, value) {
            var num, _nuit;
            switch (unit) {
                case "万个":
                    num = value * 10000;
                    _nuit = "个";
                    break;
                case "亿个":
                    num = value * 100000000;
                    _nuit = "个";
                    break;
                case "Kb":
                    num = value * 1000;
                    _nuit = "b";
                    break;
                case "Mb":
                    num = value * 1000000;
                    _nuit = "b";
                    break;
                case "Gb":
                    num = value * 1000000000;
                    _nuit = "b";
                    break;
                case "Tb":
                    num = value * 1000000000000;
                    _nuit = "b";
                    break;
                case "个":
                    num = value - 0;
                    _nuit = "个";
                    break;
                case "b":
                    num = value - 0;
                    _nuit = "b";
                    break;
            }
            return {
                numValue: num,
                nuitStr: _nuit
            };
        },
        /**
         * 将最小单位转换成最适应其的单位 如10240b == 10kb
         * @param nuit String 单位
         * @param value Number 值
         * @returns {{value: Number, unit:String}}
         */
        numforUnit: function (nuit, value) {
            var _value, unitType;
            switch (nuit) {
                case "个":
                    if (value >= 100000000) {
                        _value = (value / 100000000).toFixed(2);
                        unitType = "亿个";
                    } else if (value >= 10000) {
                        _value = (value / 10000).toFixed(2);
                        unitType = "万个";
                    } else {
                        _value = value;
                        unitType = "个";
                    }
                    break;
                case "b":
                    if (value < 1000) {
                        _value = value;
                        unitType = "b";
                    } else if (value >= 1000 && value < 1000000) {
                        _value = (value / 1000).toFixed(2);
                        unitType = "Kb";
                    } else if (value >= 1000000 && value < 1000000000) {
                        _value = (value / 1000000).toFixed(2);
                        unitType = "Mb";
                    } else if (value >= 1000000000 && value < 1000000000000) {
                        _value = (value / 1000000000).toFixed(2);
                        unitType = "Gb";
                    } else if (value >= 1000000000000) {
                        _value = (value / 1000000000000).toFixed(2);
                        unitType = "Tb";
                    }
                    break;
            }
            return {
                value: _value,
                unit: unitType
            };
        },
        /**
         * 判断字符串中某个字符串出现的次数
         * @param strA String 某个字符串
         * @param str String 整个字符串
         * @returns {Number}
         */
        patch: function (strA, str) {
            var re = eval("/" + strA + "/ig");
            return str.match(re).length;
        },
        /**
         * 连线差值
         * @param connInfo  Object 连线对象
         * @param has Boolean 判断是否是connection对象
         *             如果为请求保存的连线则此值为false否则其它均传true
         */
        lineReduceNum: function (connInfo, has,IsClose) {
            var tableSource = [],
                tableTarget = [],
                resultStr = "",
                tableHtml;
            for (var i = 0, $tr = $(connInfo.source).find(".SavecheckedId"); i < $tr.length; i++) {
                var $trObj = {
                    "plotName": $tr.eq(i).children(".tdKey").text(),
                    "value": $tr.eq(i).children(".tdVal").text(),
                    "unit": $tr.eq(i).next().children().text()
                };
                tableSource.push($trObj);
            }
            for (var i = 0, $tr = $(connInfo.target).find(".SavecheckedId"); i < $tr.length; i++) {
                var $trObj = {
                    "plotName": $tr.eq(i).children(".tdKey").text(),
                    "value": $tr.eq(i).children(".tdVal").text(),
                    "unit": $tr.eq(i).next().children().text()
                };
                tableTarget.push($trObj);
            }
            for (var i = 0; i < tableSource.length; i++) {
                for (var j = 0; j < tableTarget.length; j++) {
                    if (tableSource[i].plotName != "未响应告警数量" && tableSource[i].plotName == tableTarget[j].plotName) {
                        if (tableSource[i].unit == tableTarget[j].unit) {
                            var numVal = String(tableSource[i].value - tableTarget[j].value),
                                arrNumVal = numVal.split(".");
                            if (arrNumVal.length - 1) {
                                if (arrNumVal[1].length > 2) {
                                    resultStr += "<tr><td>"+tableSource[i].plotName+"</td><td>"+Math.abs(Number(numVal).toFixed(2))+"</td><td>"+tableSource[i].unit+"</td></tr>"
                                } else {
                                    resultStr += "<tr><td>"+tableSource[i].plotName+"</td><td>"+Math.abs(numVal)+"</td><td>"+tableSource[i].unit+"</td></tr>"
                                }
                            } else {
                                resultStr += "<tr><td>"+tableSource[i].plotName+"</td><td>"+Math.abs(numVal)+"</td><td>"+tableSource[i].unit+"</td></tr>"
                            }
                        } else {
                            /*============================
                             * 这里的减法比较复杂 涉及到的可能有单位的各种换算
                             * 小列表单位就四种 数量 流量 比率 时延
                             * 可能单位不同的只有两种 数量 流量
                             * 判断数量或流量两对象里谁不是最小单位则换算谁 而后进行减法最后进行
                             * 差值的单位换算
                             * 个 万个 亿个
                             * b Kb Mb Gb Tb
                             *
                             ==============================*/
                            var num = Math.abs(_cockpit.conversionUnit(tableSource[i].unit, tableSource[i].value).numValue -
                                _cockpit.conversionUnit(tableTarget[j].unit, tableTarget[j].value).numValue),
                                unit = _cockpit.conversionUnit(tableSource[i].unit, tableSource[i].value).nuitStr;
                            resultStr += "<tr><td>"+tableSource[i].plotName+"</td><td>"+
                                _cockpit.numforUnit(unit, num).value+"</td><td>"+
                                _cockpit.numforUnit(unit, num).unit+"</td></tr>"
                        }
                    }
                }
            }
            tableHtml ='<table class="table text-center reduceTable"><thead><tr class="text-center" ><th colspan=3>差值</th></tr></thead><tbody>'+resultStr+'</tbody></table>';
            tableHtml += '<div class="closeBox"></div>';
            if(resultStr && !IsClose){
                if (has) {
                    if (_cockpit.isInteger(_cockpit.patch("tr", resultStr) / 4)) {
                        $(connInfo.getOverlay('xx-info').getElement()).html(tableHtml).css({
                            "margin-top": -$(connInfo.getOverlay('xx-info').getElement()).height() / 2,
                            "margin-left": -$(connInfo.getOverlay('xx-info').getElement()).width() / 2,
                            "z-index":9
                        });
                    } else {
                        $(connInfo.getOverlay('xx-info').getElement()).html(tableHtml).css({
                            "margin-top": -$(connInfo.getOverlay('xx-info').getElement()).height() / 2 + 8,
                            "margin-left": -$(connInfo.getOverlay('xx-info').getElement()).width() / 2,
                            "z-index":9
                        });
                    }
                } else {
                    if (_cockpit.isInteger(_cockpit.patch("tr", resultStr) / 4)) {
                        $(connInfo.connection.getOverlay('xx-info').getElement()).html(tableHtml).css({
                            "margin-top": -$(connInfo.connection.getOverlay('xx-info').getElement()).height() / 2,
                            "margin-left": -$(connInfo.connection.getOverlay('xx-info').getElement()).width() / 2,
                            "z-index":9
                        });
                    } else {
                        $(connInfo.connection.getOverlay('xx-info').getElement()).html(tableHtml).css({
                            "margin-top": -$(connInfo.connection.getOverlay('xx-info').getElement()).height() / 2 + 8 ,
                            "margin-left": -$(connInfo.connection.getOverlay('xx-info').getElement()).width() / 2,
                            "z-index":9
                        });
                    }
                }
            }
            $(".closeBox").click(function () {
                if (_cockpit.Islock()) {
                    $(this).parent().next().addClass("_tclick");
                    $(this).parent().remove();
                }
            });
        },
        /**
         * 所有连线差值数据刷新
         */
        allLineReduceNum: function () {
            $.each(jsPlumb.getAllConnections(), function (idx, connection) {
                _cockpit.lineReduceNum(connection, true);
            });
        },
        /**
         * 元素连线功能（功能包含创建四个连线点和直接将两元素相连的功能）
         * @param item 元素或id
         * @param starEle 开始元素om
         *
         * @param endEle 结束元素
         * @param starDirection 开始方向
         * @param endDirection 结束方向
         */
        jsPlumbLine: function (item, starEle, endEle, starDirection, endDirection,IsClose) {
            jsPlumb.ready(function () {
                var connectorPaintStyle = { //基本连接线样式
                        lineWidth: 2,
                        strokeStyle: "#cccccc",
                        joinstyle: "round",
                        outlineColor: "#cccccc",
                        outlineWidth: 0
                    },
                    connectorHoverStyle = { // 鼠标悬浮在连接线上的样式
                        lineWidth: 4,
                        strokeStyle: "#216477",
                        outlineColor: "white",
                        outlineWidth: 0
                    },
                    origin = {
                        endpoint: ["Dot", {
                            radius: 8
                        }], //端点的形状
                        connectorStyle: connectorPaintStyle, //连接线的颜色，大小样式
                        connectorHoverStyle: connectorHoverStyle,
                        paintStyle: {
                            fillStyle: "#00000059"
                        }, //端点的颜色样式
                        cssClass: "vsisbliH",
                        hoverClass: "pointHover vsisbliV",
                        isSource: true, //是否可以拖动（作为连线起点）
                        connector: "Flowchart",
                        isTarget: true, //是否可以放置（连线终点）
                        maxConnections: -1, // 设置连接点最多可以连接几条线,-1表示无限大
                        connectorOverlays: [
                            ["Custom", {
                                create: function () {
                                    return $("<div></div>")
                                },
                                location: 0.5,
                                id: "xx-info"
                            }, {
                                cssClass: 'connector'
                            }]
                        ],
                        dropOptions: {
                            hoverClass: "dropHover",
                            activeClass: "vsisbliV dragActive"
                        }
                    };
                if (!starEle) {
                    jsPlumb.addEndpoint(item, {
                        anchors: "BottomCenter"
                    }, origin);
                    jsPlumb.addEndpoint(item, {
                        anchors: "LeftMiddle"
                    }, origin);
                    jsPlumb.addEndpoint(item, {
                        anchors: "TopCenter"
                    }, origin);
                    jsPlumb.addEndpoint(item, {
                        anchors: "RightMiddle"
                    }, origin);
                }
                jsPlumb.bind("dblclick", function (conn, originalEvent) { //点击线段删除
                    if(_cockpit.Islock()){
                        jsPlumb.detach(conn);
                    }
                });
                jsPlumb.bind("connection", function (connInfo, originalEvent) { //自己连自己管控
                    if (connInfo.connection.sourceId == connInfo.connection.targetId) {
                        setTimeout(function () {
                            jsPlumb.detach(connInfo.connection);
                        }, 100);
                    } else {
                        _cockpit.lineReduceNum(connInfo,null,IsClose);
                    }
                });
                if (starEle) {
                    jsPlumb.connect({
                        source: starEle,
                        target: endEle,
                        anchors: [starDirection, endDirection],
                        connector: "Flowchart",
                        overlays: [
                            ["Custom", {
                                create: function () {
                                    return $("<div></div>")
                                },
                                location: 0.5,
                                id: "xx-info"
                            }, {
                                cssClass: IsClose?"_tclick none":""
                            }],
                            ["Label", {
                            cssClass: "aLabel"}]
                        ],
                        paintStyle: connectorPaintStyle,
                        hoverPaintStyle: connectorHoverStyle,
                        endpoint: ["Dot",
                            {
                                radius: 8,
                                cssClass: "vsisbliH"
                            }
                        ],
                        endpointStyle: {
                            cssClass: "vsisbliH",
                            fillStyle: "#00000059"
                        }
                    });
                }
            })
        },
        /**
         * 缩放跟关闭功能
         * 此处还有些代码未删除
         */
        moveElement: function () {
            //防止bug 阻止事件冒泡
            $(".closeBox").mousedown(function (e) {
                e.stopPropagation();
            });
            $(".closeBox").click(function () {
                if (_cockpit.Islock()) {
                    if ($(this).parent().hasClass("tableBox")) {
                        jsPlumb.removeAllEndpoints($(this).parent().attr("id"));
                    }
                    if($(this).parent().hasClass("drageHuitu")){
                        jsPlumb.removeAllEndpoints($(this).parent().attr("id"));
                    }
                    $(this).parent().remove();
                }
            });
            $(".coor").mousedown(function (e) {
                if (_cockpit.Islock()) {
                    var _t = $(this).parent(),
                        domId = _t.children(".linedraw").children().attr("id");
                    if (_t.hasClass("tableBox")) {
                        var posix = {
                            'w': _t.width(),
                            'h': _t.height(),
                            'x': e.pageX,
                            'y': e.pageY,
                            "_h": _t.find(".drageTabletr").eq(0).height()?
                                _t.find(".drageTabletr").eq(0).height():
                                _t.find(".listThStyl").outerHeight(),
                            "tdkeyFont": _t.find(".tdKey").css("font-size")?
                                _t.find(".tdKey").css("font-size").replace(/px/, ""):"",
                            "tdkeyLineH": _t.find(".tdKey").css("line-height")?
                                _t.find(".tdKey").css("line-height").replace(/px/, ""):"",
                            "tdValFont": _t.find(".tdVal").css("font-size")?
                                _t.find(".tdVal").css("font-size").replace(/px/, ""):"",
                            "thFont": _t.find(".listThStyl").css("font-size").replace(/px/, "")
                        };
                    } else {
                        var posix = {
                            'w': _t.width(),
                            'h': _t.height(),
                            'x': e.pageX,
                            'y': e.pageY
                        };
                    }
                    $.extend(document, {
                        'move': true,
                        'call_down': function (e) {
                            var _width = Math.max(30, e.pageX - posix.x + posix.w), //此为缩放时当前鼠标位置距离元素最左侧的宽度
                                _height = Math.max(30, e.pageY - posix.y + posix.h), //此为缩放时当前鼠标位置距离元素最顶侧的高度
                                _index = e.pageY - posix.y,
                                //Multiple = Math.round(_width/($(".block-area").outerWidth()*0.01));
                                Multiple = Math.ceil(_width / ($(".block-area").outerWidth() * 0.01));
                            if (_t.hasClass("_chartDragBox")) {
                                function oldcode() {
                                    var _$width = _width - 360 > 0 ? _width : 360,
                                        _$height = _$width * 0.41 + "px";
                                    _t.css({
                                        'width': _$width
                                    });
                                    $.reFlow(domId);
                                    _t.find(".linedraw>div").css({
                                        'height': _$height
                                    });
                                }
                                //var Multiple = Math.round(_width/($(".block-area").outerWidth()*0.01));
                                if (_cockpit.isInteger(Multiple)) {
                                    //var _$width = _width-360>0?
                                    //    Multiple+"%":
                                    //    (360/$(".block-area").outerWidth()*100)+"%",
                                    //    _$height = _width-360>0?
                                    //    _$width.replace(/%/,"")/100*$(".block-area").outerWidth()*0.4+"px":
                                    //    360*0.4+"px";

                                    //var _$width = Multiple>48?
                                    //    Multiple+"%":"48%",
                                    //    _$height = Multiple>48?
                                    //    _$width.replace(/%/,"")/100*$(".block-area").outerWidth()*0.4+"px":
                                    //    0.48*$(".block-area").outerWidth()*0.4+"px";
                                    var _$width = Multiple > 30 ?
                                        Multiple + "%" : "30%",
                                        _$height = Multiple > 30 ?
                                        _$width.replace(/%/, "") / 100 * $(".block-area").outerWidth() * 0.4 + "px" :
                                        0.30 * $(".block-area").outerWidth() * 0.4 + "px";
                                    _t.css({
                                        'width': _$width
                                    });
                                    $.reFlow(domId);
                                    _t.find(".linedraw>div").css({
                                        'height': _$height
                                    });
                                }
                            } else if (_t.hasClass("drageBox")||_t.hasClass("drageHuitu")) {
                                var Multiple = _index / Math.round($(".block-area").outerWidth() * 0.01);
                                if (_cockpit.isInteger(Multiple)) {
                                    var _$width = _width > 30 ?
                                        _width : 30;
                                    _t.css({
                                        'width': _$width,
                                        'height': _$width
                                    });
                                    _t.css("backgroundSize", _$width + "px " + _$width + "px");
                                }
                                jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
                            } else if (_t.hasClass("tableBox")) {
                                function oldCode() {
                                    var _$width = _width >= 248 ? _width :
                                        "248",
                                        _$height = _width >= 248 ? _$width * (posix._h / posix.w) : 0,
                                        $tdKeyFont = _width >= 248 ?
                                        posix.tdkeyFont * (_$height / posix._h) > 13 ? posix.tdkeyFont * (_$height / posix._h) : 13 :
                                        13,
                                        $thFont = _width >= 248 ?
                                        posix.thFont * (_$height / posix._h) > 13 ? posix.thFont * (_$height / posix._h) : 13 :
                                        13,
                                        $tdValFont = _width >= 248 ?
                                        posix.tdValFont * (_$height / posix._h) > 23 ? posix.tdValFont * (_$height / posix._h) : 23 :
                                        23;
                                    _t.css("width", _$width);
                                    _t.find(".drageTabletr").css("height", _$height);
                                    _t.find(".tdKey").css("font-size", $tdKeyFont);
                                    _t.find(".tdVal").css("fontSize", $tdValFont);
                                    _t.find(".listThStyl").css("fontSize", $thFont);
                                    jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
                                }
                                //var Multiple = Math.round(_width/($(".block-area").outerWidth()*0.01));
                                if (_cockpit.isInteger(Multiple)) {
                                    //var _$width = Multiple>=21? Multiple+"%":
                                    //        "21%",
                                    //    //_$width1 = Multiple>=19? _width:
                                    //    //    "210",
                                    //    _$height = Multiple>=21?$(".block-area").outerWidth()*(Multiple/100)*(posix._h/posix.w): "",
                                    //    $tdKeyFont = Multiple>=21?
                                    //        posix.tdkeyFont*(_$height/posix._h)>12?posix.tdkeyFont*(_$height/posix._h):""
                                    //        :"",
                                    //    $thFont = Multiple>=21?
                                    //        posix.thFont*(_$height/posix._h)>14?posix.thFont*(_$height/posix._h):""
                                    //        :"",
                                    //    $tdKeyLineH =  Multiple>=21?
                                    //        $thFont!=""?posix.tdkeyLineH*(_$height/posix._h):""
                                    //        :"",
                                    //    $tdValFont = Multiple>=21?
                                    //        posix.tdValFont*(_$height/posix._h)>23?posix.tdValFont*(_$height/posix._h):""
                                    //        :"";
                                    //var _$width = Multiple>15? Multiple+"%":
                                    //        "15%",
                                    //    _$height = Multiple>15?$(".block-area").outerWidth()*(Multiple/100)*(posix._h/posix.w): "",
                                    //    $tdKeyFont = Multiple>15?posix.tdkeyFont*(_$height/posix._h):"",
                                    //    //$thFont = Multiple>15?posix.thFont*(_$height/posix._h):"",
                                    //    $thFont = Multiple>15?$tdKeyFont*(13/12):"",
                                    //    $tdKeyLineH =  Multiple>15?posix.tdkeyLineH*(_$height/posix._h)+"px":"",
                                    //    $tdValFont = Multiple>15?posix.tdValFont*(_$height/posix._h):"";

                                    var _$width = Multiple > 20 ? Multiple + "%" :
                                        "20%",
                                        _$height = Multiple > 20 ? $(".block-area").outerWidth() * (Multiple / 100) * (posix._h / posix.w) : "",
                                        $tdKeyFont = _t.find("tr.drageTabletr").length && Multiple > 20 ? posix.tdkeyFont * (_$height / posix._h) : "",
                                        $thFont = $tdKeyFont && Multiple > 20 ? $tdKeyFont * (13 / 12) : "",
                                        $tdKeyLineH = _t.find("tr.drageTabletr").length && Multiple > 20 ? posix.tdkeyLineH * (_$height / posix._h) + "px" : "",
                                        $tdValFont = _t.find("tr.drageTabletr").length && Multiple > 20 ? posix.tdValFont * (_$height / posix._h) : "",
                                        $healId = _t.find("tr.healTableTr").children().children().attr("id");
                                    _t.css("width", _$width);
                                    // if($tdKeyFont || $tdKeyLineH || $tdValFont){
                                    if(_t.find("tr.drageTabletr").length){
                                        _t.find(".tdKey").css({
                                            "font-size": $tdKeyFont,
                                            "line-height": $tdKeyLineH
                                        });
                                        _t.find(".tdVal").css("fontSize", $tdValFont);
                                        _t.find(".listThStyl").css("fontSize", $thFont);
                                    }else {
                                        var $thFont2 = Multiple > 20 ? posix.thFont * (_$height / posix._h) : "";
                                        _t.find(".listThStyl").css("fontSize", $thFont2);
                                    }
                                    // 此处还得增加热度图的刷新 和高度的赋值
                                    if($healId){
                                        $("#"+$healId).css({
                                            width:_t.width(),
                                            height:_t.find(".tdVal").outerHeight()?
                                                _t.find(".tdVal").outerHeight():
                                                parseInt(_t.find(".listThStyl").outerHeight()*1.5)
                                        });
                                        $.reFlow($healId);
                                    }

                                }
                                jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
                            } else {
                                //var Multiple = Math.round(_width/($(".block-area").outerWidth()*0.01));
                                if (_cockpit.isInteger(Multiple)) {
                                    var _$width = Multiple + "%";
                                    _t.css({
                                        'width': _$width,
                                        'height': _height
                                    });
                                }
                                //_t.css({
                                //    'width': _width,
                                //    'height': _height
                                //});
                            }
                        }
                    });
                    return false;
                }
            });
        },
        /**
         * 定时刷新功能
         * 刷新小列表 图形 连线差值 热度图等
         * @param _starttime 回溯的开始时间
         * @param _endtime 回溯的结束时间
         * @param ctrolParms String  back/clearTime/column
         */
        refreshSmallListOrChart: function (_starttime, _endtime, ctrolParms) {
            /*
             * interTime,clearTimer
             * url watchpointId
             * plotIds clientId||serverId
             * starttime endtime  step
             *
             */
            if ($(".drageTable").length) {
                var $drT = $(".drageTable");
                for (var i = 0; i < $drT.length; i++) {
                    var url = $drT.eq(i).attr("data-selfUrl"),
                        ipmCenterId = $drT.eq(i).attr("data-ipmCenterId"),
                        watchpointId = $drT.eq(i).attr("data-watchPointId"),
                        clientId = $drT.eq(i).attr("data-clientId"),
                        serverId = $drT.eq(i).attr("data-serverId"),
                        trPlotId = $drT.eq(i).find(".SavecheckedId"),
                        plotIds = [];
                    for (var j = 0; j < trPlotId.length; j++) {
                        plotIds.push(trPlotId.eq(j).attr("data-plotId"))
                    }
                    var params = {
                        ipmCenterId: ipmCenterId,
                        watchpointId: watchpointId,
                        plotIds: String(plotIds)
                        //starttime:$.myTime.DateToUnix($.myTime.nowTime())-600,
                        //endtime:$.myTime.DateToUnix($.myTime.nowTime()),
                        //step:step
                    };
                    if (clientId || serverId) {
                        clientId && clientId != "undefined" ? params.clientId = clientId :
                            serverId && serverId != "undefined" ? params.serverId = serverId : "";
                    }
                    if (_starttime && _endtime) {
                        params.starttime = _starttime;
                        params.endtime = _endtime;
                    }
                    if (url == "/appController/getSimpleData.do" ||
                        url == "/url/getSimpleData.do"||
                        url == "/depthanaly/getSimpleData.do") {
                        params.moduleId = $drT.eq(i).find(".drageTableth").attr("data-moduleId");
                        params.busiId = $drT.eq(i).attr("data-busiId");
                    }
                    if(plotIds.length){
                        $.ajax({
                            url: url,
                            async: false,
                            method: "POST",
                            data: params,
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                //此处与小列表的name匹配
                                var dataArry = data.data;
                                dataArry.forEach(function (item, index) {
                                    var numObj = numForUtil(item.value, item.unit);
                                    for (var i = 0; i < trPlotId.length; i++) {
                                        if (item.plotName == trPlotId.eq(i).children("td:first").text()) {
                                            if ($.trim(item.plotName) != "未响应告警数量") {
                                                trPlotId.eq(i).children("td:last").text(numObj.value);
                                                trPlotId.eq(i).next().children("td").text(numObj.unit);
                                                trPlotId.eq(i).attr({
                                                    "data-starttime": data.starttime,
                                                    "data-endtime": data.endtime
                                                });
                                                trPlotId.eq(i).next().attr({
                                                    "data-starttime": data.starttime,
                                                    "data-endtime": data.endtime
                                                })
                                            } else {
                                                var alrmbgStyle = _cockpit.alarmBgStyle(item.alarmLevelId);
                                                trPlotId.eq(i).children("td:last").text(numObj.value);
                                                trPlotId.eq(i).next().children("td").text(numObj.unit);
                                                trPlotId.eq(i).attr({
                                                    "data-starttime": item.starttime,
                                                    "data-endtime": item.endtime,
                                                    "data-alarmLevelId": item.alarmLevelId
                                                });
                                                trPlotId.eq(i).next().attr({
                                                    "data-starttime": item.starttime,
                                                    "data-endtime": item.endtime,
                                                    "data-alarmLevelId": item.alarmLevelId
                                                });
                                                trPlotId.eq(i).removeClass("alarNumStylg");
                                                trPlotId.eq(i).removeClass("alarNumStyly");
                                                trPlotId.eq(i).removeClass("alarNumStylr");
                                                trPlotId.eq(i).next().removeClass("alarNumStylg");
                                                trPlotId.eq(i).next().removeClass("alarNumStyly");
                                                trPlotId.eq(i).next().removeClass("alarNumStylr");
                                                trPlotId.eq(i).addClass(alrmbgStyle);
                                                trPlotId.eq(i).next().addClass(alrmbgStyle);
                                            }
                                        }
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
                    }
                }
            }
            /*
             * 此处应该增加刷新健康度的图形的代码
             * 应该单独写
             */
            if($(".healTableTr").length){
                var $healTr = $(".healTableTr");
                for(var i =0;i<$healTr.length;i++){
                    var domId = $healTr.eq(i).children().children().attr("id"),
                        url = $healTr.eq(i).attr("data-ChartUrl"),
                        ipmCenterId = $healTr.eq(i).attr("data-ipmCenterId") ,
                        watchpointId = $healTr.eq(i).attr("data-watchpointId"),
                        plotId = $healTr.eq(i).attr("data-plotId"),
                        plotTypeId = $healTr.eq(i).attr("data-plotTypeId"),
                        serverId = $healTr.eq(i).attr("data-serverId"),
                        clientId = $healTr.eq(i).attr("data-clientId"),
                        busiId = $healTr.eq(i).attr("data-busiId"),
                        moduleId = $healTr.eq(i).attr("data-moduleId"),
                        params = {
                            "ipmCenterId": ipmCenterId,
                            "watchpointId": watchpointId,
                            "plotId": plotId,
                            "plotTypeId": plotTypeId
                        };
                    if (_starttime && _endtime) {
                        params.starttime = _starttime;
                        params.endtime = _endtime;
                        $healTr.eq(i).attr({
                            "data-starttime": _starttime,
                            "data-endtime": _endtime
                        });
                    } else {
                        $healTr.eq(i).attr({
                            "data-starttime": "",
                            "data-endtime": ""
                        });
                    }
                    serverId && serverId != "undefined" ? params.serverId = serverId :
                        clientId && clientId != "undefined" ? params.clientId = clientId : "";
                    if (url == "/appController/getPlotData.do" ||
                        url == "/url/getPlotData.do"||
                        url == "/depthanaly/depthanalyGraphical.do") {
                        params.moduleId = moduleId;
                        params.busiId = busiId;
                    }
                    $.drawHChart(domId, null, url, params);
                }
            }
            /*
             * chartemId+j 盛放图形的容器id domId
             * charTitleId+j 标题id titleId
             * dataChartUrl 画图形的url
             * drawCharparem ｛
             *            watchpointId
             *            plotId
             *            plotTypeId
             *            serverId||clientId
             *               ｝
             *
             */
            if ($("._lineChartdb").length) {
                var $lChar = $("._lineChartdb");
                for (var i = 0; i < $lChar.length; i++) {
                    var domId = $lChar.eq(i).children().attr("id"),
                        isDrawColumn = $("#" + domId).attr("data-chartType"),
                        titleId = $lChar.eq(i).prev("div.titleDrage").children("h2.tile-title").attr("id"),
                        url = $lChar.eq(i).attr("data-ChartUrl"),
                        ipmCenterId = $lChar.eq(i).attr("data-ipmCenterId"),
                        ipmCenterName = $lChar.eq(i).prev(".titleDrage").children().text().split("-")[0],
                        watchpointId = $lChar.eq(i).attr("data-watchPointId"),
                        plotId = $lChar.eq(i).attr("data-plotId"),
                        plotTypeId = $lChar.eq(i).attr("data-plotTypeId"),
                        serverId = $lChar.eq(i).attr("data-serverId"),
                        clientId = $lChar.eq(i).attr("data-clientId"),
                        busiId = $lChar.eq(i).attr("data-busiId"),
                        moduleId = $lChar.eq(i).attr("data-moduleId"),
                        //starttime = $.myTime.DateToUnix($.myTime.nowTime())-600,
                        //endtime = $.myTime.DateToUnix($.myTime.nowTime()),
                        params = {
                            "ipmCenterId": ipmCenterId,
                            "ipmCenterName": ipmCenterName,
                            "watchpointId": watchpointId,
                            "plotId": plotId,
                            "plotTypeId": plotTypeId
                        };
                    if (_starttime && _endtime) {
                        params.starttime = _starttime;
                        params.endtime = _endtime;
                        // $lChar.attr({
                        //     "data-starttime": _starttime,
                        //     "data-endtime": _endtime
                        // });
                        $lChar.eq(i).attr({
                            "data-starttime": _starttime,
                            "data-endtime": _endtime
                        });
                    } else {
                        // $lChar.attr({
                        //     "data-starttime": "",
                        //     "data-endtime": ""
                        // });
                        $lChar.eq(i).attr({
                            "data-starttime": "",
                            "data-endtime": ""
                        });
                    }
                    serverId && serverId != "undefined" ? params.serverId = serverId :
                        clientId && clientId != "undefined" ? params.clientId = clientId : "";
                    if (url == "/appController/getPlotData.do" ||
                        url == "/url/getPlotData.do"||
                        url == "/depthanaly/depthanalyGraphical.do") {
                        params.moduleId = moduleId;
                        params.busiId = busiId;
                    }
                    if (!isDrawColumn && isDrawColumn != "column" && isDrawColumn != "bar") {
                        $.drawHChart(domId, titleId, url, params);
                    } else {
                        // if (isRefreClum) {
                        //     $.drawHChart(domId, titleId, url, params);
                        // }
                        /*
                         * bar 不刷新 column一分钟
                         * 此处涉及到以下几个逻辑
                         *  1、回溯 或 刷新 两种柱图都刷新
                         *  2、10秒累加数值 column刷新
                         *  3、清除定时器时将 控制刷新数值给归零
                         */
                        if(ctrolParms == "back"){
                            $.drawHChart(domId, titleId, url, params);
                        }else {
                            if(ctrolParms == "clearTime"){
                                _cockpit.refreNum = 0;
                            }else {
                                if(isDrawColumn == "column"){
                                    // _cockpit.refreNum += 10;
                                    // if(_cockpit.refreNum == 60){
                                    //     _cockpit.refreNum = 0;
                                    //     $.drawHChart(domId, titleId, url, params);
                                    // }
                                    $.drawHChart(domId, titleId, url, params);
                                }
                            }
                        }
                    }
                }
            }
            /**
             * 更新连线上的差值数据
             */
            // _cockpit.allLineReduceNum();
        },
        /**
         * 创建一个唯一ID号 （此仅为解决Wdowns10下IE浏览器的兼容问题）
         * 在同一微秒内重复的概率为万分之一
         * 返回为 idName + Number
         * @param idName
         * @returns {string}
         */
        tDrageId: function (idName) {
            var id = $.myTime.CurMilTime() + String(parseInt(Math.random() * 10000));
            return idName + id;
        },
        /**
         * 拖拽功能
         * @param element 本身元素 .class||#id|| otherSlector
         * @param handElement  手柄 .class||#id || otherSlector
         */
        tDrage: function (element, handElement) {
            $(element).Tdrag({
                handle: handElement,
                // grid:[Math.round($(".block-area").outerWidth()*0.01),Math.round($(".block-area").outerHeight()*0.01)],
                grid: [Math.round($(".block-area").outerWidth() * 0.01), 5],
                //grid:[Math.round($(".block-area").outerWidth()*0.02),10],
                cbMove: function (self, obj) {
                    //限制元素被拖出容器最顶部
                    if ($(self).css("top").replace(/px/, "") <= 0) {
                        $(self).css("top", 0)
                    }else {
                        //将top值强行赋值为1%的挪动
                        // var xPoint = parseInt($(self).css("top").replace(/px/, "") / ($(".block-area").outerHeight() * 0.01));
                        // $(self).css("top", xPoint + "%");
                    }
                    //限制元素被拖出容器最左部
                    if ($(self).css("left").replace(/px/, "") <= 0) {
                        $(self).css("left", 0)
                    } else {
                        //将left值强行赋值为1%的挪动
                        var xPoint = parseInt($(self).css("left").replace(/px/, "") / ($(".block-area").outerWidth() * 0.01));
                        $(self).css("left", xPoint + "%");
                    }
                    //为特殊元素做特殊处理
                    if ($(element).hasClass("tableBox")||
                        $(element).hasClass("drageHuitu")) {
                        jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
                    }
                },
                cbEnd: function () {
                    if ($(element).hasClass("group_div_area")) {
                        $(element).css("z-index", "8");
                    }
                }
            });
        },
        /**
         *   画出图片功能(可弹出模态框）
         *                creatImg {
         *  @param String            bgSrc:"",//图片的完整路径
         *  @param String          creatClass:"",//用于区分某个模块的属性 data-class = creatImg.creatClass
         *  @param String          titleText:"" //给用户的中文提示 title="'+creatImg.titleText+'"
         *                        }
         */
        creatImg: function (creatImg) {
            var drageBoxId = creatImg.id && creatImg.id != "undefined"?creatImg.id:this.tDrageId("drageBox");
            $("#content .block-area").append('<div id="' + drageBoxId + '" ' +
                ' class="cur_text drageBox"' +
                ' data-class="' + creatImg.creatClass + '" ' +
                ' title="' + creatImg.titleText + '"' +
                'style="' + creatImg.bgSrc + '">' +
                '<div class="coor"></div>' +
                '<div class="closeBox"></div>' +
                '</div>');
            this.moveElement();
            this.tDrage("#" + drageBoxId);
        },
        /**
         *   画出图片功能（不可弹出模态框）
         *                creatHuitu {
         *  @param String            bgSrc:"",//图片的完整路径
         *  @param String          creatClass:"",//用于区分某个模块的属性 data-class = creatImg.creatClass
         *  @param String          titleText:"" //给用户的中文提示 title="'+creatImg.titleText+'"
         *                        }
         */
        creatHuitu:function(creatHuitu){
            var drageBoxId = creatHuitu.id && creatHuitu.id != "undefined"?creatHuitu.id:this.tDrageId("drageHuitu");
            $("#content .block-area").append('<div id="' + drageBoxId + '" ' +
                ' class="drageHuitu"' +
                ' data-class="' + creatHuitu.creatClass + '" ' +
                ' title="' + creatHuitu.titleText + '"' +
                'style="' + creatHuitu.bgSrc + '">' +
                '<div class="coor"></div>' +
                '<div class="closeBox"></div>' +
                '</div>');
            this.moveElement();
            this.tDrage("#" + drageBoxId);
            // this.jsPlumbLine(drageBoxId);
        },
        /**
         * 未响应告警数量列的颜色
         * @param Number alarmLevelId
         * 返回颜色的className
         */
        alarmBgStyle: function (alarmLevelId) {
            var alarmBgStyle;
            switch (alarmLevelId) {
                case 2:
                    alarmBgStyle = "alarNumStylg";
                    break;
                case 3:
                    alarmBgStyle = "alarNumStyly";
                    break;
                case 4:
                    alarmBgStyle = "alarNumStylr";
                    break;
                default:
                    alarmBgStyle = "";
            }
            return alarmBgStyle;
        },
        /**
         * 画出小列表
         *  {
         *      @param dataChartUrl
         *      @param url
         *      @param ipmCenterId
         *      @param ipmCenterName
         *      @param watchpointId
         *      @param moduleId
         *      @param cliSerAttr
         *      @param select2AttrC
         *      @param clientServerId
         *      @param selectText
         *      @param dataArry
         *      @param kpiNames
         *      @param plotIds
         *      @param plotTypeIds
         *      @param dataStarttime
         *      @param dataEndtime
         *      @param healthId
         *      @param healthPlotId
         *      @param healStartTime
         *      @param healEndtime
         * }
         */
        creatlistTable: function (creatlistTable) {
            var tableBoxId = creatlistTable.id && creatlistTable.id != "undefined" ? creatlistTable.id : this.tDrageId("tableBox"),
                healId = this.tDrageId("heal"),
                style = creatlistTable.style ? creatlistTable.style : "",
                tdKeyFontSz = creatlistTable.tdKeyFontSz ? "font-size:" + creatlistTable.tdKeyFontSz + "px;line-height:" + creatlistTable.tdKeyLineH + ";" : "",
                tdValFontSz = creatlistTable.tdValFontSz ? "font-size:" + creatlistTable.tdValFontSz + "px;" : "",
                thFontSz = creatlistTable.thFontSz ? "font-size:" + creatlistTable.thFontSz + "px;" : "",
                drageTabletrHeigh = creatlistTable.drageTabletrHeigh,
                hsClsDtb = !$(_chart.drageBox).hasClass("tableBox"),
                paramBusid,
                tableBox;
            //此处专为解决bug而写的代码
            if(!hsClsDtb){
                tdKeyFontSz = $(_chart.drageBox).find(".tdKey").attr("style");
                tdValFontSz = $(_chart.drageBox).find(".tdVal").attr("style");
                thFontSz = $(_chart.drageBox).find(".listThStyl").attr("style");
            }
            tableBox = hsClsDtb ? '<div id="' + tableBoxId + '" tdrage-Id="' + tableBoxId + '" class="tableBox" style="' + style + '">' : "";
            tableBox += '<table  class="table drageTable"' +
                ' data-ChartUrl="' + creatlistTable.dataChartUrl + '" data-selfUrl="' + creatlistTable.url + '"' +
                'data-ipmCenterId="' + creatlistTable.ipmCenterId + '"' +
                ' data-watchPointId="' + creatlistTable.watchpointId + '"  ' + creatlistTable.cliSerAttr + '>' +
                '<thead><tr class="drageTableth"  data-Name="' + creatlistTable.select2AttrC + '" ' +
                'data-watchpointId="' + creatlistTable.watchpointId + '"  data-ipmCenterId="' + creatlistTable.ipmCenterId + '" ' +
                ' data-moduleId="' + creatlistTable.moduleId + '"  data-clientServerId="' + creatlistTable.clientServerId + '">' +
                '<th  class="text-center cssMove listThStyl" colspan="2" style="' + thFontSz + '">' + creatlistTable.selectText + '</th>' +
                '</tr></thead>';
           /**
            * 在此处应该判断是否有勾选健康度
            * 如何勾选咯创建对应的容器
            *             "healthId":healthId,
            *            "healthPlotid":healthPlotid
            *
            *
            */
           if(creatlistTable.healthId && creatlistTable.healthPlotId){
               tableBox += '<tr class="healTableTr" ' +
                   ' data-ChartUrl="'+creatlistTable.dataChartUrl+'"  ' +
                   ' data-ipmCenterId="'+creatlistTable.ipmCenterId+'"  ' +
                   ' data-watchPointId="'+creatlistTable.watchpointId+'"  ' +
                   ' data-plotId="'+creatlistTable.healthId+'"  ' +
                   ' data-plotTypeId="'+creatlistTable.healthPlotId+'"  ' +
                   ' data-moduleId="'+creatlistTable.moduleId+  '"' +
                     creatlistTable.cliSerAttr +
                   '>' +
                   '<td colspan="2" style="padding:0;">' +
                   '<div id="'+healId+'"></div>'+
                   '</td></tr>';
           }
            creatlistTable.dataArry.forEach(function (item, index) {
                var numObj = numForUtil(item.value, item.unit);
                for (var i = 0; i < creatlistTable.kpiNames.length; i++) {
                    if (item.plotName == creatlistTable.kpiNames[i]) {
                        if ($.trim(item.plotName) != "未响应告警数量") {
                            tableBox += '<tr class="drageTabletr SavecheckedId" ' +
                                'data-plotId="' + creatlistTable.plotIds[i] + '" data-plotTypeId="' + creatlistTable.plotTypeIds[i] + '" ' +
                                'data-starttime="' + creatlistTable.dataStarttime + '" data-endtime="' + creatlistTable.dataEndtime + '">' +
                                '<td  class="tdKey" style="' + tdKeyFontSz + '">' + item.plotName + '</td>' +
                                '<td  class="tdVal cursor"  rowspan="2" style="' + tdValFontSz + '">' + numObj.value + '</td>' +
                                '</tr>' +
                                '<tr class="drageTabletr drageTabletrUnit" data-plotId="' + creatlistTable.plotIds[i] + '" ' +
                                'data-plotTypeId="' + creatlistTable.plotTypeIds[i] + '" ' +
                                'data-starttime="' + creatlistTable.dataStarttime + '" data-endtime="' + creatlistTable.dataEndtime + '">' +
                                '<td class="tdKey tdKey2Style" style="' + tdKeyFontSz + '">' + numObj.unit + '</td>' +
                                '</tr>'
                        } else {
                            tableBox += '<tr class="drageTabletr SavecheckedId ' + _cockpit.alarmBgStyle(item.alarmLevelId) + '" ' +
                                'data-plotId="' + creatlistTable.plotIds[i] + '" data-plotTypeId="' + creatlistTable.plotTypeIds[i] + '" ' +
                                'data-starttime="' + item.starttime + '" data-endtime="' + item.endtime + '"  data-alarmLevelId="' + item.alarmLevelId + '">' +
                                '<td  class="tdKey" style="' + tdKeyFontSz + '">' + item.plotName + '</td>' +
                                '<td  class="tdVal cursor"  rowspan="2" style="' + tdValFontSz + '">' + numObj.value + '</td>' +
                                '</tr>' +
                                '<tr class="drageTabletr drageTabletrUnit ' + _cockpit.alarmBgStyle(item.alarmLevelId) + '"  data-plotId="' + creatlistTable.plotIds[i] + '" ' +
                                'data-plotTypeId="' + creatlistTable.plotTypeIds[i] + '" ' +
                                'data-starttime="' + item.starttime + '" data-endtime="' + item.endtime + '" data-alarmLevelId="' + item.alarmLevelId + '">' +
                                '<td class="tdKey tdKey2Style" style="' + tdKeyFontSz + '">' + numObj.unit + '</td>' +
                                '</tr>'
                        }
                    }
                }
            });
            tableBox += '<div class="closeBox"></div>' +
                '<div class="editBox"></div>' +
                '<div class="coor"></div>' +
                '</table>';
            tableBox += hsClsDtb ? '</div>' : "";
            if (hsClsDtb) {
                $("#content .block-area").append($(tableBox));
            } else {
                $(_chart.drageBox).css("height", $(_chart.drageBox).height()).empty().append(tableBox).css("height", "");
                this.tDrage('div[tdrage-id="' + $(_chart.drageBox).attr("tdrage-id") + '"]', ".drageTableth");
                jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
                // _cockpit.allLineReduceNum();//重新连线上的差值
            }
            //if(drageTabletrHeigh){
            //    $(".drageTabletr").css("height",drageTabletrHeigh);
            //}
            this.moveElement();
            if(creatlistTable.healthId && creatlistTable.healthPlotId){
                if(!hsClsDtb && $(_chart.drageBox).find(".tdVal").outerHeight()){
                    if($(_chart.drageBox).find(".tdVal").outerHeight()){
                        $("#"+healId).height($(_chart.drageBox).find(".tdVal").outerHeight());
                    }else {
                        // $("#"+healId).height(parseInt($("#"+tableBoxId).find(".listThStyl").outerHeight()*1.5));
                        $("#"+healId).height(parseInt($("#"+healId).parents(".drageTable").find(".listThStyl").outerHeight()*1.5));
                    }
                }else {
                    // $("#"+healId).height($("#"+tableBoxId).find(".tdVal").outerHeight()-9);//此处减去的数值是css渲染后的结果值 必减！！！

                    if($("#"+tableBoxId).find(".tdVal").outerHeight()){
                        $("#"+healId).height($("#"+tableBoxId).find(".tdVal").outerHeight());
                    }else {
                        // console.log(tableBoxId);
                        // console.log($("#"+tableBoxId).find(".listThStyl").outerHeight());
                        // console.log($("#"+healId).parents(".drageTable").find(".listThStyl").outerHeight()*1.5);
                        // $("#"+healId).height(parseInt($("#"+tableBoxId).find(".listThStyl").outerHeight()*1.5));
                        $("#"+healId).height(parseInt($("#"+healId).parents(".drageTable").find(".listThStyl").outerHeight()*1.5));
                    }
                }
                /**
                 * 此处应该调用画图的方法
                 * serverId clientId busiId
                 */
                if(creatlistTable.cliSerAttr &&
                    creatlistTable.cliSerAttr.split("=")[1] &&
                    creatlistTable.cliSerAttr.split("=")[1]!="undefined"){
                    paramBusid = creatlistTable.cliSerAttr.split("=")[1];
                }
                $.drawHChart(
                    healId,
                    null,
                    creatlistTable.dataChartUrl,
                    {
                        "ipmCenterId":creatlistTable.ipmCenterId,
                        "watchpointId":creatlistTable.watchpointId,
                        "plotId":creatlistTable.healthId,
                        "serverId":paramBusid,
                        "clientId":paramBusid,
                        "busiId":paramBusid,
                        "plotTypeId":creatlistTable.healthPlotId,
                        "starttime":creatlistTable.healStartTime,
                        "endtime":creatlistTable.healEndtime
                    }
                );
            }
            // if (hsClsDtb) {
            //     this.jsPlumbLine(tableBoxId);
            // }
            this.tDrage('div[tdrage-id="' + tableBoxId + '"]', ".drageTableth");
        },
        /**
         * 画出线图
         * ｛
         *      @param chartPlotIds
         *      @param select2AttrC
         *      @param dataChartUrl
         *      @param ipmCenterId
         *      @param ipmCenterName
         *      @param watchpointId
         *      @param cliSerAttr
         *      @param moduleId
         *      @param chartplotTypeIds
         *      @param clientServerId
         *      @param selectText
         *      @param chartTname
         *      @param starttime
         *      @param endtime
         *   ｝
         * 读取保存画出线图
         *   {
         *      @param requestSave
         *      @param className
         *      @param style
         *      @param titleId
         *      @param titleText
         *      @param url
         *      @param ipmCenterId
         *      @param watchpointId
         *      @param clientId
         *      @param serverId
         *      @param busiId
         *      @param moduleId
         *      @param starttime
         *      @param endtime
         *      @param plotId
         *      @param plotTypeId
         *      @param domId
         *      @param domIdStyle
         *      @param params
         *   }
         */
        creathiLineChart: function (creathiLineChart) {
            if (creathiLineChart.requestSave) {
                //此为请求保存的js
                var cliSerAttr;
                if (creathiLineChart.params.clientId && creathiLineChart.params.clientId != "undefined") {
                    cliSerAttr = "data-clientId=" + creathiLineChart.params.clientId;
                } else if (creathiLineChart.params.serverId && creathiLineChart.params.serverId != "undefined") {
                    cliSerAttr = "data-serverId=" + creathiLineChart.params.serverId;
                } else {
                    cliSerAttr = "data-busiId=" + creathiLineChart.params.busiId;
                }
                var _chartDragBoxId = this.tDrageId("chartDragBox"),
                    html = '<div id="' + _chartDragBoxId + '"  class="' + creathiLineChart.className + '"' +
                    'style="' + creathiLineChart.style + '">' +
                    '<div class="closeBox"></div>' +
                    '<div class="coor"></div>' +
                    '<div  class="title cssMove titleDrage">' +
                    '<h2 class="tile-title" id =' + creathiLineChart.titleId + '></h2>' +
                    '</div>' +
                    '<div class="linedraw _lineChartdb cur_text" ' +
                    'data-ipmCenterId = "' + creathiLineChart.ipmCenterId + '"' +
                    'data-moduleId = "' + creathiLineChart.moduleId + '" ' +
                    'data-ChartUrl="' + creathiLineChart.url + '" ' +
                    ' data-watchPointId="' + creathiLineChart.watchpointId + '" ' +
                    '  ' + cliSerAttr + ' ' +
                    'data-starttime="' + creathiLineChart.starttime + '"  data-endtime="' + creathiLineChart.endtime + '"' +
                    ' data-plotId="' + creathiLineChart.plotId + '" data-plotTypeId="' + creathiLineChart.plotTypeId + '">' +
                    '<div id=' + creathiLineChart.domId + ' style="' + creathiLineChart.domIdStyle + '"></div>' +
                    '</div>' +
                    '</div>';
                $("#content .block-area").append(html);
                $.drawHChart(
                    creathiLineChart.domId,
                    creathiLineChart.titleId,
                    creathiLineChart.url,
                    creathiLineChart.params
                );
                this.tDrage("#" + _chartDragBoxId, ".titleDrage");
            } else {
                var _chartPlotIds = [],
                    _chartTname = [],
                    drawObChart = [],
                    noRemoveChart = [],
                    eleArry = [], //将页面中由同一个center且同一个观察点或同一个center且同一个观察点且同一个业务生成的图形存进此数组
                    domIdArry = [],
                    sortDomIdarry;
                for (var i = 0; i < creathiLineChart.chartPlotIds.length; i++) {
                    _chartPlotIds.push(creathiLineChart.chartPlotIds[i]);
                    _chartTname.push(creathiLineChart.chartTname[i])
                }
                // substrIndex id名的长度
                function dubRemovFun(element, watchpointId, ipmCenterId, cliSerAttr, substrIndex) {
                    /*
                     * 因新加入咯服务器IP
                     * 所以去重变成三维去重
                     * 此处代码得重写
                     *
                     */
                    if ($(element).length) {
                        var _t = $(element);
                        for (var i = 0; i < _t.length; i++) {
                            var _watchpointId = _t.eq(i).find("._lineChartdb").attr("data-watchpointId"),
                                _ipmCenterId = _t.eq(i).find("._lineChartdb").attr("data-ipmCenterId"),
                                _serverId = _t.eq(i).find("._lineChartdb").attr("data-serverId"),
                                _busiId = _t.eq(i).find("._lineChartdb").attr("data-busiId"),
                                _clientId = _t.eq(i).find("._lineChartdb").attr("data-clientId");
                            switch (element) {
                                case ".draw_ob":
                                    //if(_watchpointId == watchpointId){
                                    //    eleArry.push(_t.eq(i));
                                    //}
                                    if (_ipmCenterId == ipmCenterId && _watchpointId == watchpointId) {
                                        eleArry.push(_t.eq(i));
                                    }
                                    break;
                                case ".draw_user":
                                    if (_ipmCenterId == ipmCenterId && _watchpointId == watchpointId && _clientId == cliSerAttr.split("=")[1]) {
                                        eleArry.push(_t.eq(i));
                                    }
                                    //if(_watchpointId == watchpointId&&_clientId == cliSerAttr.split("=")[1]){
                                    //    eleArry.push(_t.eq(i));
                                    //}
                                    break;
                                case ".draw_serv":
                                    if (_ipmCenterId == ipmCenterId && _watchpointId == watchpointId && _serverId == cliSerAttr.split("=")[1]) {
                                        eleArry.push(_t.eq(i));
                                    }
                                    //if(_watchpointId == watchpointId&&_serverId == cliSerAttr.split("=")[1]){
                                    //    eleArry.push(_t.eq(i));
                                    //}
                                    break;
                                case ".draw_web":
                                case ".draw_oracle":
                                case ".draw_mysql":
                                case ".draw_sqlserver":
                                case ".draw_url":
                                case ".draw_baowenJy":
                                    if (_ipmCenterId == ipmCenterId && _watchpointId == watchpointId && _busiId == cliSerAttr.split("=")[1]) {
                                        eleArry.push(_t.eq(i));
                                    }
                                    //if(_watchpointId == watchpointId&&_busiId == cliSerAttr.split("=")[1]){
                                    //    eleArry.push(_t.eq(i));
                                    //}
                                    break;
                                default:
                                    jeBox.alert("暂未书写这部分代码");
                            }
                        }
                    }
                    if (eleArry.length) {
                        var _t = eleArry;
                        for (var i = 0; i < _t.length; i++) {
                            drawObChart.push(_t[i].find("._lineChartdb").attr("data-plotid"));
                        }
                        for (var j = 0; j < _chartPlotIds.length; j++) {
                            for (var i = 0; i < drawObChart.length; i++) {
                                if (_chartPlotIds[j] == drawObChart[i]) {
                                    noRemoveChart.push(drawObChart[i])
                                }
                            }
                        }
                        //然后再两两组较 删除重合元素
                        for (var i = 0; i < drawObChart.length; i++) {
                            for (var j = 0; j < noRemoveChart.length; j++) {
                                if (drawObChart[i] == noRemoveChart[j]) {
                                    drawObChart.splice(i, 1);
                                }
                            }
                        }
                        for (var i = 0; i < _chartPlotIds.length; i++) {
                            for (var j = 0; j < noRemoveChart.length; j++) {
                                if (_chartPlotIds[i] == noRemoveChart[j]) {
                                    _chartPlotIds.splice(i, 1);
                                    creathiLineChart.chartplotTypeIds.splice(i, 1);
                                    _chartTname.splice(i, 1);
                                }
                            }
                        }
                        for (var i = 0; i < _t.length; i++) {
                            for (var j = 0; j < drawObChart.length; j++) {
                                if (_t[i].find("._lineChartdb").attr("data-plotid") == drawObChart[j]) {
                                    $(_t[i]).remove()
                                }
                            }
                        }
                    }
                    for (var i = 0; i < $(element).length; i++) {
                        domIdArry.push($(element).eq(i).find("._lineChartdb").children().attr("id").substr(substrIndex));
                    }
                    sortDomIdarry = domIdArry.sort(function (a, b) {
                        return a - b
                    });
                }
                switch (creathiLineChart.select2AttrC) {
                    case "watchPoinSelect":
                        dubRemovFun(".draw_ob", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 22);
                        break;
                    case "userSideSelect":
                        dubRemovFun(".draw_user", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 27);
                        break;
                    case "serverSideSelect":
                        dubRemovFun(".draw_serv", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 29);
                        break;
                    case "webSelect":
                        dubRemovFun(".draw_web", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 26);
                        break;
                    case "oracleSelect":
                        dubRemovFun(".draw_oracle", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 29);
                        break;
                    case "mysqlSelect":
                        dubRemovFun(".draw_mysql", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 28);
                        break;
                    case "sqlserverSelect":
                        dubRemovFun(".draw_sqlserver", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 32);
                        break;
                    case "urlSelect":
                        dubRemovFun(".draw_url", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 26);
                        break;
                    case "baowenJySelect":
                        dubRemovFun(".draw_baowenJy", creathiLineChart.watchpointId, creathiLineChart.ipmCenterId, creathiLineChart.cliSerAttr, 31);
                        break;
                    default:
                        jeBox.alert("未书写这部分代码");
                }
                for (var j = 0; j < _chartPlotIds.length; j++) {
                    //这里面得再次判断id是否有重
                    /*
                     *跟数组 sortDomIdarry 相比较 如果相等
                     * 则新的id为 sortDomIdarry[sortDomIdarry.length-1]-0+_chartPlotIds[j]
                     *
                     *domId,titleId
                     *
                     */
                    var watchpointId = creathiLineChart.select2AttrC == "watchPoinSelect" ?
                        $(".watchPoinSelect").find("option:selected").attr("data-id") : 1,
                        chartemId,
                        charTitleId,
                        drawClass,
                        domId,
                        selectText = creathiLineChart.selectText,
                        titleIdTex = selectText + _chartTname[j] ? selectText + "-" + _chartTname[j] : "",
                        domHeight = $(".block-area").outerWidth() * 0.48 * 0.4 + "px;", //此处赋值高度必须与拖放一致
                        titleId;
                    switch (creathiLineChart.select2AttrC) {
                        case "watchPoinSelect":
                            chartemId = "draw_ober_plot_plotype";
                            charTitleId = "draw_title_ob";
                            drawClass = "draw_ob";
                            break;
                        case "userSideSelect":
                            chartemId = "draw_ober_user_plot_plotype";
                            charTitleId = "draw_title_user";
                            drawClass = "draw_user";
                            break;
                        case "serverSideSelect":
                            chartemId = "draw_ober_server_plot_plotype";
                            charTitleId = "draw_title_serv";
                            drawClass = "draw_serv";
                            break;
                        case "webSelect":
                            chartemId = "draw_ober_web_plot_plotype";
                            charTitleId = "draw_title_web";
                            drawClass = "draw_web";
                            break;
                        case "oracleSelect":
                            chartemId = "draw_ober_oracle_plot_plotype";
                            charTitleId = "draw_title_oracle";
                            drawClass = "draw_oracle";
                            break;
                        case "mysqlSelect":
                            chartemId = "draw_ober_mysql_plot_plotype";
                            charTitleId = "draw_title_mysql";
                            drawClass = "draw_mysql";
                            break;
                        case "sqlserverSelect":
                            chartemId = "draw_ober_sqlserver_plot_plotype";
                            charTitleId = "draw_title_sqlserver";
                            drawClass = "draw_sqlserver";
                            break;
                        case "urlSelect":
                            chartemId = "draw_ober_url_plot_plotype";
                            charTitleId = "draw_title_url";
                            drawClass = "draw_url";
                            break;
                        case "baowenJySelect":
                            chartemId = "draw_ober_baowenJy_plot_plotype";
                            charTitleId = "draw_title_baowenJy";
                            drawClass = "draw_baowenJy";
                            break;
                        default:
                            jeBox.alert("未书写此部分代码");
                    }
                    domId = chartemId + j;
                    titleId = charTitleId + j;
                    if (sortDomIdarry && sortDomIdarry.length) {
                        for (var k = 0; k < sortDomIdarry.length; k++) {
                            if (j == sortDomIdarry[k]) {
                                var index = (sortDomIdarry[sortDomIdarry.length - 1] - 0 + 1) + (j - 0);
                                domId = chartemId + index;
                                titleId = charTitleId + index;
                                sortDomIdarry.push(index); //此步骤很重要 若不添加进去则会出bug
                            }
                        }
                    }
                    var _chartDragBoxId = this.tDrageId("chartDragBox"),
                        html = '<div id="' + _chartDragBoxId + '"  class="' + drawClass + ' _chartDragBox">' +
                        '<div class="closeBox"></div>' +
                        '<div class="coor"></div>' +
                        '<div class="title cssMove titleDrage">' +
                        '<h2 class="tile-title" id =' + titleId + '></h2>' +
                        '</div>' +
                        '<div class="linedraw _lineChartdb cur_text" ' +
                        'data-ipmCenterId = "' + creathiLineChart.ipmCenterId + '"' +
                        'data-moduleId = "' + creathiLineChart.moduleId + '" ' +
                        'data-ChartUrl="' + creathiLineChart.dataChartUrl + '"' +
                        ' data-watchPointId="' + creathiLineChart.watchpointId + '"' +
                        '  ' + creathiLineChart.cliSerAttr + '' +
                        ' data-starttime=""  data-endtime=""' +
                        ' data-plotId="' + _chartPlotIds[j] + '" data-plotTypeId="' + creathiLineChart.chartplotTypeIds[j] + '">' +
                        '<div id=' + domId + ' style="width:100%;height:' + domHeight + '"></div>' +
                        '</div>' +
                        '</div>',
                        drawCharparem = {
                            "ipmCenterId": creathiLineChart.ipmCenterId,
                            "ipmCenterName": creathiLineChart.ipmCenterName,
                            "watchpointId": creathiLineChart.watchpointId,
                            "plotId": _chartPlotIds[j],
                            "plotTypeId": creathiLineChart.chartplotTypeIds[j],
                            "starttime": creathiLineChart.starttime,
                            "endtime": creathiLineChart.endtime
                        };
                    $("#content .block-area").append(html);
                    creathiLineChart.select2AttrC == "serverSideSelect" ? drawCharparem.serverId = creathiLineChart.clientServerId :
                        creathiLineChart.select2AttrC == "userSideSelect" ? drawCharparem.clientId = creathiLineChart.clientServerId : "";
                    if (creathiLineChart.dataChartUrl == "/appController/getPlotData.do" ||
                        creathiLineChart.dataChartUrl == "/url/getPlotData.do"||
                        creathiLineChart.dataChartUrl == "/depthanaly/depthanalyGraphical.do") {
                        drawCharparem.busiId = creathiLineChart.clientServerId;
                        drawCharparem.moduleId = creathiLineChart.moduleId;
                    }
                    $.drawHChart(
                        domId,
                        titleId,
                        creathiLineChart.dataChartUrl,
                        drawCharparem
                    );
                    this.tDrage("#" + _chartDragBoxId, ".titleDrage");
                }
                _chart.kpiNames = creathiLineChart.chartPlotIds;
            }
            this.moveElement();
        },
        /**
         * 画出文字
         * @param style
         * @param spantext
         * @param spanFontSz
         */
        creatWords: function (style, spantext, spanFontSz) {
            var _style = style ? style : "",
                _spanFontSz = spanFontSz ? spanFontSz : "",
                id = this.tDrageId(""),
                html = '<div class="flow_text" tdrage-id="flowText' + id + '" style="' + _style + '">' +
                '<span style="' + _spanFontSz + '" class="cssMove" data-id="span' + id + '"></span>' +
                '<input type="text" style="color:#000;" data-id="input' + id + '">' +
                '<div class="text_tool" style="display: none;">' +
                '<div class="modifyText" data-id="modifyText' + id + '" title="更改文字"></div>' +
                '<div class="biggerText" data-id="biggerText' + id + '" title="字体变大"></div>' +
                '<div class="smallerText" data-id="smallerText' + id + '" title="字体变小"></div>' +
                '<div class="deleteText" data-id="deleteText' + id + '" title="删除元素"></div>' +
                '</div>' +
                '</div>';
            $("#content .block-area").append(html);
            if (spantext) {
                $('input[data-id="input' + id + '"]').val(spantext);
            }
            $('input[data-id="input' + id + '"]').focus().blur(function () {
                if ($.trim($(this).val()) && $.trim($(this).val()) != "") {
                    $(this).hide().prev().text($(this).val());
                } else {
                    $(this).parent().remove();
                }
            }).keypress(function (e) {
                var key = e.which; // e.which是按键的值
                if (key == 13) {
                    $(this).blur();
                }
            }).click(function () {
                $(this).focus();
            });
            if (spantext) {
                $('input[data-id="input' + id + '"]').blur();
            }
            // $('span[data-id="span' + id + '"]').mousedown(function (e) {
            //     e.stopPropagation();
            // });
            $('span[data-id="span' + id + '"]').click(function (e) {
                $(this).parent().children(".text_tool").show();
                e.stopPropagation();
            });
            // $('div[data-id="modifyText' + id + '"]').mousedown(function (e) {
            //     e.stopPropagation();
            // });
            $('div[data-id="modifyText' + id + '"]').click(function (e) {
                var text = $(this).parents(".flow_text").children("span").hide().text();
                $(this).parents(".flow_text").children("input").val(text).show().focus();
                $(this).parents(".flow_text").children("input").blur(function () {
                    if ($.trim($(this).val()) && $.trim($(this).val()) != "") {
                        $(this).hide().prev().show().text($(this).val());
                    } else {
                        $(this).parent().remove();
                    }
                });
                e.stopPropagation();
            });
            $('div[data-id="biggerText' + id + '"]').mousedown(function (e) {
                e.stopPropagation();
            });
            $('div[data-id="biggerText' + id + '"]').click(function (e) {
                var fontSize = $(this).parents(".flow_text").children("span").css("font-size").replace(/px/, "") - 0;
                fontSize += 1;
                $(this).parents(".flow_text").children("span").css("font-size", fontSize);
                $('div[tdrage-id="flowText' + id + '"]').width($('div[tdrage-id="flowText' + id + '"]').width() + 1000);
                $('div[tdrage-id="flowText' + id + '"]').width($('span[data-id="span' + id + '"]').width() + 30);
                e.stopPropagation();
            });
            $('div[data-id="smallerText' + id + '"]').mousedown(function (e) {
                e.stopPropagation();
            });
            $('div[data-id="smallerText' + id + '"]').click(function (e) {
                var fontSize = $(this).parents(".flow_text").children("span").css("font-size").replace(/px/, "") - 0;
                fontSize > 12 ? fontSize -= 1 : 12;
                $(this).parents(".flow_text").children("span").css("font-size", fontSize);
                $('div[tdrage-id="flowText' + id + '"]').width($('div[tdrage-id="flowText' + id + '"]').width() + 1000);
                $('div[tdrage-id="flowText' + id + '"]').width($('span[data-id="span' + id + '"]').width() + 30);
                e.stopPropagation();
            });
            $('div[data-id="deleteText' + id + '"]').mousedown(function (e) {
                e.stopPropagation();
            });
            $('div[data-id="deleteText' + id + '"]').click(function (e) {
                $(this).parents(".flow_text").remove();
                e.stopPropagation();
            });
            $(document).click(function () {
                $('div[tdrage-id="flowText' + id + '"]').children(".text_tool").hide();
            });
            this.tDrage('div[tdrage-id="flowText' + id + '"]');
        },
        /**
         * 画出圆角框
         * @param style
         */
        creatRoundedBox: function (style) {
            var _style = style ? style : "",
                id = this.tDrageId("RoundBox"),
                html = '<div class="group_div_area" tdrage-id="' + id + '" style="' + _style + '">' +
                '<div class="closeBox"></div>' +
                '<div class="coor"></div>' +
                '</div>';
            $("#content .block-area").append(html);
            this.tDrage('div[tdrage-id="' + id + '"]');
            this.moveElement();
        },
        /**
         * 双击图片弹出模态框功能
         * @param dataName
         * @param chartName
         * @param csAttrName
         * @param ModuleId
         * @param urlArry
         */
        alertModal: function (dataName, chartName, csAttrName, ModuleId, urlArry) {
            if ($("tr[data-name='" + dataName + "']").length || $("." + chartName).length) {
                //判断页面中是否已经出现小列表或对应的图形
                var _t = $("tr[data-name='" + dataName + "']"),
                    plotIds = [],
                    wcsIds = [],
                    watchpointId = _t.eq(_t.length - 1).attr("data-watchpointId"),
                    ClSevId = _t.eq(_t.length - 1).attr(csAttrName) - 0;
                for (var i = 0; i < _t.length; i++) {
                    var SavecheckedId = _t.eq(i).parent().children(".SavecheckedId");
                    for (var j = 0; j < SavecheckedId.length; j++) {
                        if (plotIds.indexOf(SavecheckedId.eq(j).attr("data-plotId") - 0) == -1) {
                            plotIds.push(SavecheckedId.eq(j).attr("data-plotId") - 0)
                        }
                    }
                }
                if (_t.length) {
                    if (ClSevId) {
                        wcsIds.push(watchpointId, ClSevId)
                    } else {
                        wcsIds.push(watchpointId);
                    }
                }
                var dataPlotIdArry = [];
                for (var j = 0; j < $("." + chartName).length; j++) {
                    dataPlotIdArry.push($("." + chartName).eq(j).children("._lineChartdb").attr("data-plotid"));
                }
                _chart.kpiNames = dataPlotIdArry;
                _chart._kpiSelectM(ModuleId,
                    urlArry,
                    plotIds, wcsIds);
            } else {
                _chart.kpiNames = [];
                _chart._kpiSelectM(ModuleId, urlArry);
            }
        },
        /**
         * 保存有多种元素  1 drageBox 2 drageTable 3 _chartDragBox等。。我就不写后边加的咯。。懒。。
         * 共有生成条件 width height left top
         * 生成drageBox条件 creatClass  text
         * 生成drageTable条件 url
         *      watchpointId
         *      plotIds:String(plotIds)
         *      clientId||serverId
         *      starttime
         *      endtime
         *      step
         * 生成_chartDragBox条件
         *      * chartemId+j 盛放图形的容器id domId
         * charTitleId+j 标题id titleId
         * dataChartUrl 画图形的url
         * drawCharparem ｛
         *            watchpointId
         *            plotId
         *            plotTypeId
         *            serverId||clientId
         *               ｝
         *   {
         *   drageBox:[{
         *          style:{
         *             width:"",
         *             height:"",
         *             left:"",
         *             height:""
         *              },
         *           creatClass:"",
         *           text:""
         *            }],
         *  drageTable:[
         *          {
         *          style:{width:"",height:"",left:"",top:""},
         *          url:"",
         *          params:{
         *                watchpointId:"",
         *                plotIds:"",
         *                clientId:"",
         *                serverId:"",
         *                starttime:"",
         *                endtime:"",
         *                step:""
         *              },
         *          attribute:{
         *               data-ChartUrl:""
         *               data-Name:""
         *               data-clientId||data-serverId:""
         *           }
         *           titleText:""
         *          }
         *           ],
         * _chartDragBox:[
         *          {
         *          style:{width:"",height:"",left:"",top:""},
         *          domId:"",
         *          titleId:"",
         *          url:"",
         *          className:"".
         *          params:{
         *              watchpointId
         *              plotId
         *              plotTypeId
         *              serverId||clientId
         *              }
         *          attribute:{
         *                 data-clientId||data-serverId:""
         *               }
         *          }
         *      ]
         *   }
         */
        saveElement: function (isTip) {
            clearInterval(timer);
            var contentObj = {
                drageBox: [],
                drageHuitu:[],
                drageTable: [],
                _lineChartdb: [],
                connects: [],
                flowText: [],
                RoundBox: []
            },
                boxWidth = $(".block-area").outerWidth();
            if ($(".drageBox").length) {
                for (var i = 0; i < $(".drageBox").length; i++) {
                    var _t = $(".drageBox").eq(i),
                        _id = _t.attr("id"),
                        _width = _t.width(),
                        _height = _t.height(),
                        _left = _t.css("left").replace(/px/, "") - 0,
                        _right = _t.css("right").replace(/px/, "") - 0,
                        _leftPoint = _right < 1 ? _left / $(".block-area").outerWidth() * 100 + "%" :
                        Math.round(_left / $(".block-area").outerWidth() * 100) + "%",
                        _top = _t.css("top").replace(/px/, "") - 0,
                        _topPoint = _top/boxWidth,
                        _style = _t.attr("style"),
                        _bgM = _t.css("background-image"), //此变量不存
                        _img = _bgM.substr(_bgM.indexOf("click_lefticon") + "click_lefticon".length + 1).split(".")[0],
                        _bgSz = _t.css("backgroundSize"),
                        creatClass = _t.attr("data-class"),
                        text = _t.attr("title"),
                        paramsObj = {
                            style: {
                                width: _width,
                                height: _height,
                                left: _left,
                                leftPoint: _leftPoint,
                                top: _top,
                                topPoint:_topPoint,
                                img: _img,
                                bgSz: _bgSz,
                                attrStyle: _style
                            },
                            id: _id,
                            creatClass: creatClass,
                            text: text
                        };
                    contentObj.drageBox.push(paramsObj);
                }
            }
            if ($(".drageHuitu").length) {
                for (var i = 0; i < $(".drageHuitu").length; i++) {
                    var _t = $(".drageHuitu").eq(i),
                        _id = _t.attr("id"),
                        _width = _t.width(),
                        _height = _t.height(),
                        _left = _t.css("left").replace(/px/, "") - 0,
                        _right = _t.css("right").replace(/px/, "") - 0,
                        _leftPoint = _right < 1 ? _left / $(".block-area").outerWidth() * 100 + "%" :
                            Math.round(_left / $(".block-area").outerWidth() * 100) + "%",
                        _top = _t.css("top").replace(/px/, "") - 0,
                        _topPoint = _top/boxWidth,
                        _style = _t.attr("style"),
                        _bgM = _t.css("background-image"), //此变量不存
                        _img = _bgM.substr(_bgM.indexOf("click_lefticon") + "click_lefticon".length + 1).split(".")[0],
                        _bgSz = _t.css("backgroundSize"),
                        creatClass = _t.attr("data-class"),
                        text = _t.attr("title"),
                        paramsObj = {
                            style: {
                                width: _width,
                                height: _height,
                                left: _left,
                                leftPoint: _leftPoint,
                                top: _top,
                                topPoint:_topPoint,
                                img: _img,
                                bgSz: _bgSz,
                                attrStyle: _style
                            },
                            id: _id,
                            creatClass: creatClass,
                            text: text
                        };
                    contentObj.drageHuitu.push(paramsObj);
                }
            }
            if ($(".drageTable").length) {
                for (var i = 0; i < $(".drageTable").length; i++) {
                    var _t = $(".drageTable").eq(i),
                        _top = _t.parent(".tableBox").css("top").replace(/px/, "") - 0,
                        _topPoint = _top/boxWidth,
                        _height = _t.parent(".tableBox").height(),
                        _id = _t.parent(".tableBox").attr("id"),
                        _StyleAttrArray = _t.parent(".tableBox").attr("style").split(";"),
                        _leftVal = "",
                        _Left,
                        _widthVal = "",
                        _Width,
                        _moduleId = _t.find(".drageTableth").attr("data-moduleId"),
                        healthId,
                        healthPlotId,
                        plotIds = [],
                        plotTypeIds = [],
                        kpiNames = [];
                    for (var j = 0; j < _StyleAttrArray.length; j++) {
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "width") {
                            _widthVal = _StyleAttrArray[j].split(":")[1];
                        }
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "left") {
                            _leftVal = _StyleAttrArray[j].split(":")[1];
                        }
                    }
                    _Width = _widthVal ? _widthVal : "";
                    _Left = _leftVal ? _leftVal : "";
                    for (var j = 0; j < _t.find("tr.SavecheckedId").length; j++) {
                        plotIds.push(_t.find("tr.SavecheckedId").eq(j).attr("data-plotId"));
                        plotTypeIds.push(_t.find("tr.SavecheckedId").eq(j).attr("data-plottypeId"));
                        kpiNames.push(_t.find("tr.SavecheckedId").eq(j).children("td").first().text())
                    }
                    healthId = _t.find("tr.healTableTr").attr("data-plotId");
                    healthPlotId = _t.find("tr.healTableTr").attr("data-plotTypeId");
                    var paramsObj = {
                        style: {
                            width: _Width,
                            height: _t.parent(".tableBox").height(),
                            drageTabletrHeigh: _t.find(".drageTabletr").height(),
                            tdKeyFontSz: plotIds.length && _t.find(".tdKey").css("font-size").replace(/px/, "") != 12?
                                _t.find(".tdKey").css("font-size").replace(/px/, "") : "",
                            tdKeyLineH: plotIds.length && _t.find(".tdKey").css("line-height") != "12px"?
                                _t.find(".tdKey").css("line-height") : "",
                            tdValFontSz: plotIds.length && _t.find(".tdVal").css("font-size").replace(/px/, "") != 21?
                                _t.find(".tdVal").css("font-size").replace(/px/, "") : "",
                            thFontSz: _t.find(".listThStyl").css("font-size").replace(/px/, "") != 13?
                                _t.find(".listThStyl").css("font-size").replace(/px/, "") : "",
                            left: _Left,
                            top: _top,
                            topPoint:_topPoint
                        },
                        url: _t.attr("data-selfUrl"),
                        params: {
                            ipmCenterId: _t.attr("data-ipmCenterId"),
                            watchpointId: _t.attr("data-watchPointId"),
                            plotIds: String(plotIds),
                            busiId: _t.attr("data-busiId"),
                            moduleId: _t.find(".drageTableth").attr("data-moduleId")
                        },
                        attribute: {
                            dataChartUrl: _t.attr("data-ChartUrl"),
                            dataName: _t.find("tr.drageTableth").attr("data-Name")
                        },
                        titleText: _t.find("th").text(),
                        kpiNames: kpiNames,
                        moduleId: _moduleId,
                        id: _id,
                        plotIds: plotIds,
                        plotTypeIds: plotTypeIds,
                        healthId:healthId,
                        healthPlotId:healthPlotId
                    };
                    _t.attr("data-clientId") && _t.attr("data-clientId") != "undefined" ?
                        paramsObj.attribute.dataClientId = _t.attr("data-clientId") :
                        paramsObj.attribute.dataServerId = _t.attr("data-serverId");
                    if (_t.attr("data-clientId") && _t.attr("data-clientId") != "undefined") {
                        paramsObj.attribute.dataClientId = _t.attr("data-clientId");
                        paramsObj.params.clientId = _t.attr("data-clientId");
                    } else if (_t.attr("data-serverId") && _t.attr("data-serverId") != "undefined") {
                        paramsObj.attribute.dataServerId = _t.attr("data-serverId");
                        paramsObj.params.serverId = _t.attr("data-serverId");
                    }
                    contentObj.drageTable.push(paramsObj);
                }
            }
            if ($("._lineChartdb").length) {
                for (var i = 0; i < $("._lineChartdb").length; i++) {
                    var _t = $("._lineChartdb").eq(i),
                        _height = _t.parents("._chartDragBox").height(),
                        _StyleAttrArray = _t.parents("._chartDragBox").attr("style").split(";"),
                        _WidthVal = "",
                        _Width,
                        _leftValChart = "",
                        _$Width,
                        _Left,
                        _top = _t.parents("._chartDragBox").css("top").replace(/px/, "") - 0,
                        _topPoint = _top/boxWidth,
                        _titleText = _t.parents("._chartDragBox").find("h2.tile-title").text();
                    for (var j = 0; j < _StyleAttrArray.length; j++) {
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "width") {
                            _WidthVal = _StyleAttrArray[j].split(":")[1];
                        }
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "left") {
                            _leftValChart = _StyleAttrArray[j].split(":")[1];
                        }
                    }
                    _$Width = _WidthVal ? _WidthVal : "";
                    _Left = _leftValChart ? _leftValChart : "";
                    var paramsObj = {
                        style: {
                            _height: _t.children().height(),
                            _width: _$Width,
                            left: _Left,
                            top: _top,
                            topPoint:_topPoint
                        },
                        domId: _t.children("div").attr("id"),
                        titleId: _t.prev().children("h2").attr("id"),
                        titleText: _titleText,
                        url: _t.attr("data-ChartUrl"),
                        className: _t.parents("._chartDragBox").attr("class"),
                        params: {
                            ipmCenterId: _t.attr("data-ipmCenterId"),
                            watchpointId: _t.attr("data-watchPointId"),
                            plotId: _t.attr("data-plotId"),
                            plotTypeId: _t.attr("data-plotTypeId"),
                            moduleId: _t.attr("data-moduleid")
                        },
                        attribute: {
                            dataClientId: _t.attr("data-clientId"),
                            dataServerId: _t.attr("data-serverId")
                        }
                    };
                    _t.attr("data-serverId") && _t.attr("data-serverId") != "undefined" ?
                        paramsObj.params.serverId = _t.attr("data-serverId") :
                        _t.attr("data-clientId") && _t.attr("data-clientId") != "undefined" ?
                        paramsObj.params.clientId = _t.attr("data-clientId") :
                        paramsObj.params.busiId = _t.attr("data-busiId"),
                        paramsObj.params.deptId = _t.attr("data-busiId");
                    contentObj._lineChartdb.push(paramsObj);
                }
            }
            // $.each(jsPlumb.getAllConnections(), function (idx, connection) {
            //     contentObj.connects.push({
            //         start: connection.sourceId,
            //         end: connection.targetId,
            //         startPointDirection: connection.endpoints[0].anchor.type,
            //         endPointDirection: connection.endpoints[1].anchor.type,
            //         IsClose:$(connection.canvas.nextElementSibling).hasClass("_tclick")?true:false
            //     });
            // });
            if ($(".flow_text").length) {
                for (var i = 0; i < $(".flow_text").length; i++) {
                    var _t = $(".flow_text").eq(i),
                        _StyleAttrArray = _t.attr("style").split(";"),
                        _WidthVal = "",
                        _leftVal = "",
                        _style,
                        params,
                        _top = _t.css("top").replace(/px/, "") - 0,
                        _topPoint = _top/boxWidth,
                        _spantext = _t.children("span").text(),
                        _spanFontSz = "font-size:" + _t.children("span").css("font-size") + ";";
                    for (var j = 0; j < _StyleAttrArray.length; j++) {
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "left") {
                            _leftVal = _StyleAttrArray[j].split(":")[1];
                        }
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "width") {
                            _WidthVal = _StyleAttrArray[j].split(":")[1];
                        }
                    }
                    _style = "left:" + _leftVal  + ";width:" + _WidthVal + ";";
                    params = {
                        style: _style,
                        topPoint:_topPoint,
                        spantext: _spantext,
                        spanFontSz: _spanFontSz
                    };
                    contentObj.flowText.push(params);
                }
            }
            if ($(".group_div_area").length) {
                for (var i = 0; i < $(".group_div_area").length; i++) {
                    var _t = $(".group_div_area").eq(i),
                        _WidthVal = "",
                        _leftValRoundBox = "",
                        _style,
                        params,
                        _top = _t.css("top").replace(/px/, "") - 0,
                        _height= _t.css("height").replace(/px/, "") - 0,
                        _heightPoint = _height/boxWidth,
                        _topPoint = _top/boxWidth,
                        _StyleAttrArray = _t.attr("style").split(";");
                    for (var j = 0; j < _StyleAttrArray.length; j++) {
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "width") {
                            _WidthVal = _StyleAttrArray[j].split(":")[1];
                        }
                        if ($.trim(_StyleAttrArray[j].split(":")[0]) == "left") {
                            _leftValRoundBox = _StyleAttrArray[j].split(":")[1];
                        }
                    }
                    _style = "left:" + _leftValRoundBox +  ";width:" + _WidthVal +";";
                    params = {
                        style: _style,
                        topPoint:_topPoint,
                        heightPoint:_heightPoint
                    };
                    contentObj.RoundBox.push(params);
                }
            }
            timer = setInterval(_cockpit.refreshSmallListOrChart, 10000);
            $.ajax({
                url: "/viewConfig/updModuleConfig.do",
                method: "POST",
                async: false,
                data: {
                    moduleId: 0, //默认为0
                    busiId: _cockpit.getUrlParams().busiId, //驾驶仓id
                    content: JSON.stringify(contentObj)
                },
                dataType: "json",
                beforeSend:function (XMLHttpRequest) {},
                success: function (data,textStatus,XMLHttpRequest) {
                    if (isTip) {
                        if (data.stauts == true) {
                             jeBox.alert("保存成功");
                        }
                    } else {
                        if (data.stauts != true) {
                            jeBox.alert("保存失败");
                        }
                    }
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            });
        },
        /**
         * 请求用户所保存的元素
         * @param Array data
         */
        getSaveDate: function (data) {
            var boxWith = $(".block-area").outerWidth();
            //画出drageBox
            if (data.drageBox && data.drageBox.length) {
                for (var i = 0; i < data.drageBox.length; i++) {
                    var _tdata = data.drageBox[i],
                        _id = _tdata.id,
                        creatClass = _tdata.creatClass,
                        width = _tdata.style.width + "px;", //style
                        height = _tdata.style.height + "px;", //style
                        leftPoint = _tdata.style.leftPoint + ";",
                        top = Math.round(boxWith*_tdata.style.topPoint) + "px",
                        img = _tdata.style.img, //style
                        bgSz = _tdata.style.bgSz, //style
                        text = _tdata.text,
                        style = "background:url(../img/click_lefticon/" + img + ".png);" +
                        "background-repeat:no-repeat;" +
                        "background-size:" + bgSz +
                        ";width:" + width + "height:" + height + "left:" + leftPoint + "top:" + top;
                    _cockpit.creatImg({
                        creatClass: creatClass,
                        bgSrc: style,
                        titleText: text,
                        id: _id
                    });
                }
            }
            //画出drageHuitu
            if (data.drageHuitu && data.drageHuitu.length) {
                for (var i = 0; i < data.drageHuitu.length; i++) {
                    var _tdata = data.drageHuitu[i],
                        _id = _tdata.id,
                        creatClass = _tdata.creatClass,
                        width = _tdata.style.width + "px;", //style
                        height = _tdata.style.height + "px;", //style
                        leftPoint = _tdata.style.leftPoint + ";",
                        top = Math.round(boxWith*_tdata.style.topPoint) + "px",
                        img = _tdata.style.img, //style
                        bgSz = _tdata.style.bgSz, //style
                        text = _tdata.text,
                        style = "background:url(../img/click_lefticon/" + img + ".png);" +
                            "background-repeat:no-repeat;" +
                            "background-size:" + bgSz +
                            ";width:" + width + "height:" + height + "left:" + leftPoint + "top:" + top;
                    _cockpit.creatHuitu({
                        creatClass: creatClass,
                        bgSrc: style,
                        titleText: text,
                        id: _id
                    });
                }
            }
            //画出小列表
            if (data.drageTable && data.drageTable.length) {
                for (var i = 0; i < data.drageTable.length; i++) {
                    var _tdata = data.drageTable[i],
                        url = _tdata.url,
                        titleText = _tdata.titleText,
                        params = _tdata.params,
                        width = _tdata.style.width + ";",
                        height = _tdata.style.height + "px;",
                        left = _tdata.style.left + ";",
                        top = Math.round(boxWith*_tdata.style.topPoint) + "px",
                        style = "width:" + width + "left:" + left + "top:" + top,
                        drageTabletrHeigh = _tdata.style.drageTabletrHeigh,
                        tdKeyFontSz = _tdata.style.tdKeyFontSz,
                        tdKeyLineH = _tdata.style.tdKeyLineH,
                        tdValFontSz = _tdata.style.tdValFontSz,
                        thFontSz = _tdata.style.thFontSz,
                        dataChartUrl = _tdata.attribute.dataChartUrl,
                        dataName = _tdata.attribute.dataName,
                        dataClientId = _tdata.attribute.dataClientId,
                        dataServerId = _tdata.attribute.dataServerId,
                        kpiNames = _tdata.kpiNames,
                        _id = _tdata.id,
                        plotIds = _tdata.plotIds,
                        plotTypeIds = _tdata.plotTypeIds,
                        _moduleId = _tdata.moduleId,
                        healthId = _tdata.healthId,
                        healthPlotId = _tdata.healthPlotId,
                        clientServerId = dataClientId ? dataClientId : dataServerId ? dataServerId : params.busiId,
                        cliSerAttr = dataClientId ? "data-clientId=" + dataClientId :
                        dataServerId ? "data-serverId=" + dataServerId : "data-busiId=" + params.busiId;
                    if(plotIds.length){
                        $.ajax({
                            url: url,
                            async: false,
                            method: "POST",
                            data: params,
                            dataType: "json",
                            beforeSend:function (XMLHttpRequest) {},
                            success: function (data,textStatus,XMLHttpRequest) {
                                var creatlistTableParams = {
                                    "dataChartUrl": dataChartUrl,
                                    "url": url,
                                    "ipmCenterId": params.ipmCenterId,
                                    "watchpointId": data.watchpointId ? data.watchpointId : data.watchPointId,
                                    "cliSerAttr": cliSerAttr,
                                    "select2AttrC": dataName, //保存为dataName变量名咯
                                    "clientServerId": clientServerId,
                                    "selectText": titleText,
                                    "dataArry": data.data,
                                    "moduleId": _moduleId,
                                    "kpiNames": kpiNames,
                                    "id": _id,
                                    "plotIds": plotIds,
                                    "plotTypeIds": plotTypeIds,
                                    "dataStarttime": data.starttime,
                                    "dataEndtime": data.endtime,
                                    "style": style,
                                    "tdKeyFontSz": tdKeyFontSz,
                                    "tdKeyLineH": tdKeyLineH,
                                    "tdValFontSz": tdValFontSz,
                                    "thFontSz": thFontSz,
                                    "drageTabletrHeigh": drageTabletrHeigh,
                                    "healthId":healthId,
                                    "healthPlotId":healthPlotId
                                };
                                _cockpit.creatlistTable(creatlistTableParams);
                            },
                            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                console.error(XMLHttpRequest);
                                console.error(textStatus);
                                console.error(errorThorwn);
                            },
                            complete:function (XMLHttpRequest,textStatus) {}
                        })
                    }else {
                        var creatlistTableParams = {
                            "dataChartUrl": dataChartUrl,
                            "url": url,
                            "ipmCenterId": params.ipmCenterId,
                            // "watchpointId": data.watchpointId ? data.watchpointId : data.watchPointId,
                            "watchpointId": params.watchpointId,
                            "cliSerAttr": cliSerAttr,
                            "select2AttrC": dataName, //保存为dataName变量名咯
                            "clientServerId": clientServerId,
                            "selectText": titleText,
                            "dataArry": [],
                            "moduleId": _moduleId,
                            "kpiNames": kpiNames,
                            "id": _id,
                            "plotIds": plotIds,
                            "plotTypeIds": plotTypeIds,
                            // "dataStarttime": data.starttime,
                            // "dataEndtime": data.endtime,
                            "style": style,
                            "tdKeyFontSz": tdKeyFontSz,
                            "tdKeyLineH": tdKeyLineH,
                            "tdValFontSz": tdValFontSz,
                            "thFontSz": thFontSz,
                            "drageTabletrHeigh": drageTabletrHeigh,
                            "healthId":healthId,
                            "healthPlotId":healthPlotId
                        };
                        _cockpit.creatlistTable(creatlistTableParams);
                    }
                }
            }
            //starttime = $.myTime.DateToUnix($.myTime.nowTime())-600,//开始时间
            //    endtime = $.myTime.DateToUnix($.myTime.nowTime()),//结束时间
            //画出图形
            if (data._lineChartdb && data._lineChartdb.length) {
                for (var i = 0; i < data._lineChartdb.length; i++) {
                    var _tdata = data._lineChartdb[i],
                        url = _tdata.url,
                        titleId = _tdata.titleId,
                        params = _tdata.params,
                        ipmCenterId = _tdata.params.ipmCenterId,
                        watchpointId = _tdata.params.watchpointId,
                        plotTypeId = _tdata.params.plotTypeId,
                        plotId = _tdata.params.plotId,
                        clientId = _tdata.params.clientId,
                        serverId = _tdata.params.serverId,
                        busiId = _tdata.params.busiId,
                        moduleId = _tdata.params.moduleId,
                        starttime = _tdata.params.starttime,
                        endtime = _tdata.params.endtime,
                        domId = _tdata.domId,
                        className = _tdata.className,
                        _titleText = _tdata.titleText,
                        width = _tdata.style._width + ";",
                        left = _tdata.style.left + ";",
                        top = Math.round(boxWith*_tdata.style.topPoint) + "px",
                        domIdStyleHeight = _tdata.style._width != "" && _tdata.style._width ?
                        _tdata.style._width.replace(/%/, "") / 100 * boxWith * 0.4 :
                        0.48 * boxWith * 0.4,
                        domIdStyle = "width:100%;" + "height:" + domIdStyleHeight + "px;",
                        style = "width:" + width + "left:" + left + "top:" + top;
                    _cockpit.creathiLineChart({
                        requestSave: true,
                        className: className,
                        style: style,
                        titleId: titleId,
                        titleText: _titleText,
                        url: url,
                        ipmCenterId: ipmCenterId,
                        watchpointId: watchpointId,
                        clientId: clientId,
                        serverId: serverId,
                        busiId: busiId,
                        moduleId: moduleId,
                        //starttime:starttime,
                        starttime: "",
                        //endtime:endtime,
                        endtime: "",
                        plotId: plotId,
                        plotTypeId: plotTypeId,
                        domId: domId,
                        domIdStyle: domIdStyle,
                        params: params
                    });
                }
            }
            //连线
            // if (data.connects && data.connects.length) {
            //     jsPlumb.ready(function () {
            //         for (var i = 0; i < data.connects.length; i++) {
            //             _cockpit.jsPlumbLine(null,
            //                 data.connects[i].start,
            //                 data.connects[i].end,
            //                 data.connects[i].startPointDirection,
            //                 data.connects[i].endPointDirection,
            //                 data.connects[i].IsClose
            //             );
            //         }
            //     })
            // }
            //文字
            if (data.flowText && data.flowText.length) {
                for (var i = 0; i < data.flowText.length; i++) {
                    var _tdata = data.flowText[i],
                        top = Math.round(boxWith*_tdata.topPoint) + "px",
                        _style = _tdata.style+"top:"+top+";",
                        _spantext = _tdata.spantext,
                        _spanFontSz = _tdata.spanFontSz;
                    _cockpit.creatWords(_style, _spantext, _spanFontSz);
                }
            }
            //圆角框
            if (data.RoundBox && data.RoundBox.length) {
                for (var i = 0; i < data.RoundBox.length; i++) {
                    var _tdata = data.RoundBox[i],
                        top = Math.round(boxWith*_tdata.topPoint) + "px",
                        height = Math.ceil(boxWith*_tdata.heightPoint) + "px",
                        _style = _tdata.style+"top:"+top+";"+"height:"+height +";";
                        _cockpit.creatRoundedBox(_style)
                }
            }
        },
        /**
         *  验证观察点或业务或center是否被用户删除
         * 若被删除咯则提示用户无法钻取到通信对页面
         * @param moduleId
         * @param businessId
         * @param watchpointId
         * @param ipmCenterId
         * @returns {boolean}
         */
        verWatBusName: function (moduleId, businessId, watchpointId, ipmCenterId) {
            var result = true;
            function _$ajax(url, thisId, alertText) {
                $.ajax({
                    url: url,
                    method: "POST",
                    async: false,
                    data: {},
                    dataType: "json",
                    beforeSend:function (XMLHttpRequest) {},
                    success: function (data,textStatus,XMLHttpRequest) {
                        result = true;
                        data.forEach(function (item, index) {
                            if (item.id == thisId) {
                                result = false;
                            }
                        });
                        if (result) {
                            jeBox.alert(alertText + "可能已被删除，建议删除此元素重新创建");
                        }
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                });
                return result;
            }
            switch (moduleId - 0) {
                case 4:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/appController/getAllAppByModuleId.do?moduleId=4", businessId, "HTTP服务");
                    break;
                case 5:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/appController/getAllAppByModuleId.do?moduleId=5", businessId, "ORACLE服务");
                    break;
                case 6:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/appController/getAllAppByModuleId.do?moduleId=6", businessId, "MYSQL服务");
                    break;
                case 7:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/appController/getAllAppByModuleId.do?moduleId=7", businessId, "SQLSERVER服务");
                    break;
                case 8:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/url/get.do", businessId, "URL交易");
                    break;
                case 9:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/depthanaly/selectAll.do", businessId, "报文交易");
                    break;
                case 10:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点");
                    break;
                case 11:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/client/getClient.do?moduleId=11", businessId, "客户端");
                    break;
                case 12:
                    result = _$ajax("/center/getCenterIpInfo.do", ipmCenterId, "服务器IP") ||
                        _$ajax("/watchpointController/getFindAll.do", watchpointId, "观察点") ||
                        _$ajax("/serverManagement/getAllServerSide.do", businessId, "服务端");
                    break;
                default:
                    jeBox.alert("暂未书写此部分代码");
            }
            return result;
        },
        /**
         * 判断是否为锁定状态
         * @returns {boolean}
         * @constructor
         */
        Islock: function () {
            var Islock;
            if ($("a[data-drawer='lockStatus']").children("span").text() != "锁定") {
                Islock = true;
            } else {
                Islock = false;
                jeBox.alert("当前为锁定状态，请先解锁再进行操作");
            }
            return Islock;
        }
    };
    /**
     * 调用小列表和图形自动刷新
     * @type {number}
     */
    var timer = setInterval(_cockpit.refreshSmallListOrChart, 10000);
    $("#sidebar>ul").on("click", "._creatImg", function () {
        if (_cockpit.Islock()) {
            var creatClass = $(this).attr("data-className").split("-")[2], //遵循此class作为区分是哪个模块的图片
                imgSrc = "../img/click_lefticon/" + $(this).attr("data-className").split("-")[2] + ".png",
                bgSrc = "background-image: url(../img/click_lefticon/" + $(this).attr("data-className").split("-")[2] + ".png);" +
                "background-repeat:no-repeat;",
                titleText = $(this).children("span").text();
            if($(this).hasClass("_huitu")){
                _cockpit.creatHuitu({
                    creatClass: creatClass,
                    bgSrc: bgSrc+"background-size:70px 70px;",
                    titleText: $(this).attr("title")
                })
            }else {
                _cockpit.creatImg({
                    creatClass: creatClass,
                    bgSrc: bgSrc,
                    titleText: titleText
                })
            }
        }
    });
    /**
     *  点击.drageBox中的图片弹出对应的模态框
     */
    $("#content .block-area").on("dblclick", ".drageBox", function () {
        if (_cockpit.Islock()) {
            var creatClass = $(this).attr("data-class");
            switch (creatClass) {
                case "observationPoint":
                    _cockpit.alertModal("watchPoinSelect", "draw_ob", "data-data", 10, ["/watchpointController/getFindAll.do"]);
                    break;
                case "userSide":
                    _cockpit.alertModal("userSideSelect", "draw_user", "data-clientServerId", 11, ["/watchpointController/getFindAll.do", "/client/getClient.do?moduleId=11"]);
                    break;
                case "serverSide":
                    _cockpit.alertModal("serverSideSelect", "draw_serv", "data-clientServerId", 12, ["/watchpointController/getFindAll.do", "/serverManagement/getAllServerSide.do"]);
                    break;
                case "web":
                    _cockpit.alertModal("webSelect", "draw_web", "data-clientServerId", 4, ["/watchpointController/getFindAll.do", "/appController/getAllAppByModuleId.do?moduleId=4"]);
                    break;
                case "oracle":
                    _cockpit.alertModal("oracleSelect", "draw_oracle", "data-clientServerId", 5, ["/watchpointController/getFindAll.do", "/appController/getAllAppByModuleId.do?moduleId=5"]);
                    break;
                case "mysql":
                    _cockpit.alertModal("mysqlSelect", "draw_mysql", "data-clientServerId", 6, ["/watchpointController/getFindAll.do", "/appController/getAllAppByModuleId.do?moduleId=6"]);
                    break;
                case "sqlserver":
                    _cockpit.alertModal("sqlserverSelect", "draw_sqlserver", "data-clientServerId", 7, ["/watchpointController/getFindAll.do", "/appController/getAllAppByModuleId.do?moduleId=7"]);
                    break;
                case "url":
                    _cockpit.alertModal("urlSelect", "draw_url", "data-clientServerId", 8, ["/watchpointController/getFindAll.do", "/url/get.do"]);
                    break;
                case "baowenJy":
                    _cockpit.alertModal("baowenJySelect", "draw_baowenJy", "data-clientServerId", 9, ["/watchpointController/getFindAll.do", "/depthanaly/selectAll.do"]);
                    break;
                default:
                    console.log(creatClass);
            }
            _chart.drageBox = $(this);
            $("#listDraw").modal("show");
        }
    });
    /**
     * 点击模态框确定按钮 生成对应的小列表或hichart图形
     */
    $("#list-save_cockpit").click(function () {
        var IpmCenterId = $(".centerSelect").find("option:selected").attr("data-id"),
            ipmCenterName = $(".centerSelect").find("option:selected").val(),
            watchpointId = $(".watchPoinSelect").find("option:selected").attr("data-id"), //观察点Id
            clientServerId = $(".watchPoinSelect").parent().next().children().find("option:selected").attr("data-id"), //客户端或服务端id
            select2AttrC = $(".watchPoinSelect").parent().next().children().attr("data-class") ?
            $(".watchPoinSelect").parent().next().children().attr("data-class") :
            $(".watchPoinSelect").attr("data-class"), //此变量用来区分观察点 用户端 服务端 下拉框
            starttime = _cockpit.huiSu?$("#time").attr("datah-strTime"):
                $.myTime.DateToUnix($.myTime.nowTime()) - 600, //开始时间
            endtime = _cockpit.huiSu?$("#time").attr("datah-endTime"):
                $.myTime.DateToUnix($.myTime.nowTime()), //结束时间
            step, //力度
            _moduleId,
            veCtionResult,
            charVcRsul,
            healthId,
            healthPlotId,
            selectText = $(".watchPoinSelect").parent().next().children().find("option:selected").text() ?
            ipmCenterName + "-" + $(".watchPoinSelect").find("option:selected").text() + "-" +
            $(".watchPoinSelect").parent().next().children().find("option:selected").text() :
            ipmCenterName + "-" + $(".watchPoinSelect").find("option:selected").text(),
            kpiNames = [], //勾选的数据列对应的中文名称
            plotTypeIds = [], //数据列复先框typeid
            plotIds = [], //数据列复先框id
            chartTname = [], //图形列标题名字
            chartPlotIds = [], //图形列复选框id
            chartplotTypeIds = []; //图形列复选框typeid
        $('input[name="litlisChart"]:checked').each(function () {
            chartPlotIds.push($(this).attr("data-plotid"));
            chartplotTypeIds.push($(this).attr("data-plottypeid"));
            chartTname.push($(this).parent().prev().text());
            _moduleId = $(this).attr("data-moduleId");
        });
        $('input[name="litlisDate"]:checked').each(function (index, element) {
            plotIds.push($(this).attr("data-id"));
            plotTypeIds.push($(this).attr("data-plottypeid"));
            kpiNames.push($(this).parent("td").prev().prev().text());
            _moduleId = $(this).attr("data-moduleId");
        });
        switch (select2AttrC) {
            case "userSideSelect":
            case "serverSideSelect":
                var url = select2AttrC == "userSideSelect" ?
                    "/client/getClientSingleValueData.do" :
                    "/serverManagement/getServerSideSingleValueData.do",
                    dataChartUrl = select2AttrC == "userSideSelect" ?
                    "/client/getClientGraphical.do" :
                    "/serverManagement/getServerSideGraphical.do",
                    //cliSerAttr = select2AttrC=="userSideSelect"?
                    //'data-clientId="'+clientServerId+'"':
                    //'data-serverId="'+clientServerId+'"',
                    cliSerAttr = select2AttrC == "userSideSelect" ?
                    'data-clientId=' + clientServerId :
                    'data-serverId=' + clientServerId,
                    veCtionResult = IpmCenterId && watchpointId && plotIds.length && clientServerId,
                    charVcRsul = IpmCenterId && watchpointId && chartPlotIds && clientServerId;
                break;
            case "webSelect":
            case "oracleSelect":
            case "mysqlSelect":
            case "sqlserverSelect":
                var url = "/appController/getSimpleData.do",
                    dataChartUrl = "/appController/getPlotData.do",
                    cliSerAttr = 'data-busiId=' + clientServerId,
                    veCtionResult = IpmCenterId && watchpointId && plotIds.length && clientServerId,
                    charVcRsul = IpmCenterId && watchpointId && chartPlotIds && clientServerId;
                break;
            case "urlSelect":
                var url = "/url/getSimpleData.do",
                    dataChartUrl = "/url/getPlotData.do",
                    cliSerAttr = 'data-busiId=' + clientServerId,
                    veCtionResult = IpmCenterId && watchpointId && plotIds.length && clientServerId,
                    charVcRsul = IpmCenterId && watchpointId && chartPlotIds && clientServerId;
                break;
            case "baowenJySelect":
                var url = "/depthanaly/getSimpleData.do",
                    dataChartUrl = "/depthanaly/depthanalyGraphical.do",
                    cliSerAttr = 'data-busiId=' + clientServerId,
                    veCtionResult = IpmCenterId && watchpointId && plotIds.length && clientServerId,
                    charVcRsul = IpmCenterId && watchpointId && chartPlotIds && clientServerId;
                break;
            default:
                var url = "/watchpointController/getCrossGridData.do",
                    dataChartUrl = "/watchpointController/getWatchpointGraphical.do",
                    veCtionResult = IpmCenterId && watchpointId && plotIds.length,
                    charVcRsul = IpmCenterId && watchpointId && chartPlotIds.length;
        }
        //判断是否有勾选健康度
        /*
         * 此处应该将 ploIDs里的关于健康度的id删除
         * 此处应该将 plotTypeIds里的关于健康度的id删除
         * 此处应该将 kpiNames里的关于健康度的元素删除
         *  并保存相对应的id以为画热力图所用
         */
        if(kpiNames.indexOf("健康度")!=-1){
            var _hindex = kpiNames.indexOf("健康度");
            healthId = plotIds[_hindex];
            healthPlotId = plotTypeIds[_hindex];
            /*
             * 此处要不要把健康度的id或plotyid保存起来
             */
            plotIds.splice(_hindex,1);
            plotTypeIds.splice(_hindex,1);
            kpiNames.splice(_hindex,1);
        }
        //出小列表
        if (veCtionResult) {
            if(plotIds.length){
                $.ajax({
                    url: url,
                    method: "POST",
                    data: {
                        ipmCenterId: IpmCenterId,
                        watchpointId: watchpointId,
                        moduleId: _moduleId,
                        busiId: clientServerId,
                        clientId: clientServerId,
                        serverId: clientServerId,
                        plotIds: String(plotIds),
                        starttime:_cockpit.huiSu?$("#time").attr("datah-strTime"):undefined,
                        endtime:_cockpit.huiSu?$("#time").attr("datah-endTime"):undefined,
                        //step:step
                    },
                    dataType: "json",
                    beforeSend:function(XMLHttpRequest){},
                    success: function (data,textStatus,XMLHttpRequest) {
                        var creatlistTableParams = {
                            "dataChartUrl": dataChartUrl,
                            "url": url,
                            "ipmCenterId": IpmCenterId,
                            "ipmCenterName": ipmCenterName,
                            "watchpointId": watchpointId,
                            "moduleId": _moduleId,
                            "cliSerAttr": cliSerAttr,
                            "select2AttrC": select2AttrC,
                            "clientServerId": clientServerId,
                            "selectText": selectText,
                            "dataArry": data.data,
                            "kpiNames": kpiNames,
                            "plotIds": plotIds,
                            "plotTypeIds": plotTypeIds,
                            "dataStarttime": data.starttime,
                            "dataEndtime": data.endtime,
                            "healthId":healthId,
                            "healthPlotId":healthPlotId,
                            "healStartTime":_cockpit.huiSu?$("#time").attr("datah-strTime"):undefined,
                            "healEndtime":_cockpit.huiSu?$("#time").attr("datah-endTime"):undefined,
                            // "healStartTime":starttime,
                            // "healEndtime":endtime
                        };
                        _cockpit.creatlistTable(creatlistTableParams);
                    },
                    error:function (XMLHttpRequest,textStatus,errorThorwn) {
                        console.error(XMLHttpRequest);
                        console.error(textStatus);
                        console.error(errorThorwn);
                    },
                    complete:function (XMLHttpRequest,textStatus) {}
                })
            }else {
                var creatlistTableParams = {
                    "dataChartUrl": dataChartUrl,
                    "url": url,
                    "ipmCenterId": IpmCenterId,
                    "ipmCenterName": ipmCenterName,
                    "watchpointId": watchpointId,
                    "moduleId": _moduleId,
                    "cliSerAttr": cliSerAttr,
                    "select2AttrC": select2AttrC,
                    "clientServerId": clientServerId,
                    "selectText": selectText,
                    "dataArry": [],
                    "kpiNames": kpiNames,
                    "plotIds": plotIds,
                    "plotTypeIds": plotTypeIds,
                    "dataStarttime": _cockpit.huiSu?$("#time").attr("datah-strTime"):undefined,
                    "dataEndtime": _cockpit.huiSu?$("#time").attr("datah-strTime"):undefined,
                    "healthId":healthId,
                    "healthPlotId":healthPlotId,
                    "healStartTime":_cockpit.huiSu?$("#time").attr("datah-strTime"):undefined,
                    "healEndtime":_cockpit.huiSu?$("#time").attr("datah-strTime"):undefined
                    // "healStartTime":starttime,
                    // "healEndtime":endtime
                };
                _cockpit.creatlistTable(creatlistTableParams);
            }
        }
        //出线图
        if (charVcRsul) {
            var creathiLineChartParams = {
                "chartPlotIds": chartPlotIds,
                "select2AttrC": select2AttrC,
                "dataChartUrl": dataChartUrl,
                "ipmCenterId": IpmCenterId,
                "ipmCenterName": ipmCenterName,
                "watchpointId": watchpointId,
                "cliSerAttr": cliSerAttr,
                "moduleId": _moduleId,
                "chartplotTypeIds": chartplotTypeIds,
                "clientServerId": clientServerId,
                "selectText": selectText,
                "chartTname": chartTname,
                "starttime": starttime,
                "endtime": endtime
            };
            _cockpit.creathiLineChart(creathiLineChartParams);
        }
        if (veCtionResult || charVcRsul) {
            $("#listDraw").modal("hide");
            if ($(_chart.drageBox).attr("class") && !$(_chart.drageBox).hasClass("tableBox")) {
                $(_chart.drageBox).remove();
            }
        } else {
            if (!IpmCenterId) {
                jeBox.alert("请选择XPM服务器");
                return;
            } else if (!watchpointId) {
                jeBox.alert("请选择观察点");
                return;
            } else if (!plotIds.length && !chartPlotIds.length) {
                jeBox.alert("请检查是否勾选图形或数据复选框");
                return;
            } else {
                jeBox.alert("请选择业务");
            }
        }
    });
    /**
     * 点击小列表的编辑按钮 弹出对应的模态框
     */
    $("#content .block-area").on("dblclick", ".editBox", function () {
        /*
         * 此处还有图形复选框勾选bug
         */
        if (_cockpit.Islock()) {
            var _t = $(this).parent().find("tr.drageTableth"),
                dataName = _t.attr("data-Name"),
                plotIds = [],
                wcsIds = [],
                watchpointId = _t.attr("data-watchpointId"),
                clientServerId = _t.attr("data-clientServerId") - 0,
                SavecheckedId = $(this).parent().find(".SavecheckedId"),
                healSavecheckedId = $(this).parent().find(".healTableTr");//增加健康度
            if (clientServerId) {
                wcsIds.push(watchpointId, clientServerId);
            } else {
                wcsIds.push(watchpointId);
            }
            _chart.drageBox = $(this).parent();
            for (var i = 0; i < SavecheckedId.length; i++) {
                plotIds.push(SavecheckedId.eq(i).attr("data-plotId") - 0); //数据勾选列
            }
            plotIds.push((healSavecheckedId.attr("data-plotId")));//增加健康度
            function kpiSelectM(element, type, urlArry) {
                var drawPlotId = [];
                for (var j = 0; j < $("." + element).length; j++) {
                    drawPlotId.push($("." + element).eq(j).children("._lineChartdb").attr("data-plotid"));
                }
                _chart.kpiNames = drawPlotId;
                _chart._kpiSelectM(type, urlArry,
                    plotIds, wcsIds);
            }
            switch (dataName) {
                case "watchPoinSelect":
                    kpiSelectM("draw_ob", 10, ["/watchpointController/getFindAll.do"]);
                    break;
                case "userSideSelect":
                    kpiSelectM("draw_user", 11, ["/watchpointController/getFindAll.do",
                        "/client/getClient.do?moduleId=11"
                    ]);
                    break;
                case "serverSideSelect":
                    kpiSelectM("draw_serv", 12, ["/watchpointController/getFindAll.do",
                        "/serverManagement/getAllServerSide.do"
                    ]);
                    break;
                case "webSelect":
                    kpiSelectM("draw_web", 4, ["/watchpointController/getFindAll.do",
                        "/appController/getAllAppByModuleId.do?moduleId=4"
                    ]);
                    break;
                case "oracleSelect":
                    kpiSelectM("draw_oracle", 5, ["/watchpointController/getFindAll.do",
                        "/appController/getAllAppByModuleId.do?moduleId=5"
                    ]);
                    break;
                case "mysqlSelect":
                    kpiSelectM("draw_mysql", 6, ["/watchpointController/getFindAll.do",
                        "/appController/getAllAppByModuleId.do?moduleId=6"
                    ]);
                    break;
                case "sqlserverSelect":
                    kpiSelectM("draw_sqlserver", 7, ["/watchpointController/getFindAll.do",
                        "/appController/getAllAppByModuleId.do?moduleId=7"
                    ]);
                    break;
                case "urlSelect":
                    kpiSelectM("draw_url", 8, ["/watchpointController/getFindAll.do",
                        "/url/get.do"
                    ]);
                    break;
                case "baowenJySelect":
                    kpiSelectM("draw_url", 9, ["/watchpointController/getFindAll.do",
                        "/depthanaly/selectAll.do"
                    ]);
                    break;
                default:
                    jeBox.alert("暂未写此部分代码");
            }
            $("#listDraw").modal("show");
        }
    });
    /**
     * 点击表格tdval获取对应参数跳转页面生成对应图形
     */
    $("#content .block-area").on("click", ".tdVal", function () {
        var _t = $(this).parent(".drageTabletr"),
            chartUrl = $(this).parents(".drageTable").attr("data-ChartUrl"),
            ipmCenterId = $(this).parents(".drageTable").attr("data-ipmCenterId"),
            watchpointId = $(this).parents(".drageTable").attr("data-watchPointId"),
            clientId = $(this).parents(".drageTable").attr("data-clientId"),
            serverId = $(this).parents(".drageTable").attr("data-serverId"),
            plotId = _t.attr("data-plotId"),
            starttime = _t.attr("data-starttime"),
            moduleId = $(this).parents(".drageTable").find(".drageTableth").attr("data-moduleId"),
            busiId = _t.parents(".drageTable").attr("data-busiId"),
            businessId = moduleId == 10 ? watchpointId : $(this).parents(".drageTable").find(".drageTableth").attr("data-clientServerId"),
            endtime = _t.attr("data-endtime"),
            _strTime = endtime - 600,
            plotTypeId = _t.attr("data-plotTypeId"),
            csParam = clientId && clientId != "undefined" ? '&clientId=' + clientId :
            serverId && serverId != "undefined" ? '&serverId=' + serverId : "";
        if (!_cockpit.verWatBusName(moduleId, businessId, watchpointId, ipmCenterId)) {
            if ($.trim($(this).prev().text()) != "未响应告警数量") {
                if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
                    if (chartUrl == "/appController/getPlotData.do" ||
                        chartUrl == "/url/getPlotData.do"||
                        chartUrl == "/depthanaly/depthanalyGraphical.do") {
                        if ($("#time").children().length) {
                            location.href = 'bssSession.html?' +
                                //'chartUrl='+chartUrl+'&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'watchpointId=' + watchpointId + '&' +
                                'moduleId=' + moduleId + '&' +
                                'busiId=' + busiId + '&' +
                                'starttime=' + _strTime + '&' +
                                'endtime=' + endtime;
                        } else {
                            location.href = 'bssSession.html?' +
                                //'chartUrl='+chartUrl+'&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'watchpointId=' + watchpointId + '&' +
                                'moduleId=' + moduleId + '&' +
                                'busiId=' + busiId + '&' +
                                'starttime=' + starttime + '&' +
                                'endtime=' + endtime;
                        }
                    } else {
                        // 此处只为观察点用户端服务端跳转
                        if ($("#time").children().length) {
                            location.href = 'commun_queue.html?' +
                                'chartUrl=' + chartUrl + '&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'moduleId=' + moduleId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'watchpointId=' + watchpointId + csParam + '&' +
                                'starttime=' + _strTime + '&' +
                                'endtime=' + endtime;
                        } else {
                            location.href = 'commun_queue.html?' +
                                'chartUrl=' + chartUrl + '&' +
                                'plotId=' + plotId + '&' +
                                'plotTypeId=' + plotTypeId + '&' +
                                'ipmCenterId=' + ipmCenterId + '&' +
                                'moduleId=' + moduleId + '&' +
                                'watchpointId=' + watchpointId + '&' +
                                'starttime=' + starttime + '&' +
                                'endtime=' + endtime + csParam;
                        }
                    }
                }
            } else {
                if (chartUrl == "/appController/getPlotData.do" ||
                    chartUrl == "/url/getPlotData.do"||
                    chartUrl == "/depthanaly/depthanalyGraphical.do") {
                    if (starttime != "0" && endtime != "0") {
                        location.href = 'alarmSetting.html?' +
                            'ipmCenterId=' + ipmCenterId + '&' +
                            'watchpointId=' + watchpointId +'&' +
                            'moduleId=' + moduleId +'&' +
                            'starttime=' + starttime + '&' +
                            'endtime=' + endtime + '&' +
                            'state=N&busiId=' + busiId;
                    } else {
                        location.href = 'alarmSetting.html?' +
                            'ipmCenterId=' + ipmCenterId + '&' +
                            'watchpointId=' + watchpointId +'&' +
                            'moduleId=' + moduleId +
                            '&state=N&busiId=' + busiId;
                    }
                } else {
                    // 此处只为观察点用户端服务端跳转
                    if (starttime != "0" && endtime != "0") {
                        location.href = 'alarmSetting.html?' +
                            'ipmCenterId=' + ipmCenterId + '&' +
                            'watchpointId=' + watchpointId +'&' +
                            'moduleId=' + moduleId + '&' +
                            'starttime=' + starttime + '&' +
                            'endtime=' + endtime + '&' +
                            'state=N' + csParam;
                    } else {
                        location.href = 'alarmSetting.html?' +
                            'ipmCenterId=' + ipmCenterId + '&' +
                            'moduleId=' + moduleId + '&' +
                            'watchpointId=' + watchpointId +
                            '&state=N' + csParam;
                    }
                }
            }
        }
    });
    /**
     * 点击页面中的图形获取对应参数跳转页面生成对应图形
     */
    $("#content .block-area").on("dblclick", "._lineChartdb", function () {
        if($("#header").attr("data-trafficpair") == undefined || +$("#header").attr("data-trafficpair")){
            var chartUrl = $(this).attr("data-ChartUrl"),
                ipmCenterId = $(this).attr("data-ipmCenterId"),
                watchpointId = $(this).attr("data-watchPointId"),
                clientId = $(this).attr("data-clientId"),
                serverId = $(this).attr("data-serverId"),
                busiId = $(this).attr("data-busiId"),
                moduleId = $(this).attr("data-moduleId"),
                plotId = $(this).attr("data-plotId"),
                starttime = $(this).attr("data-starttime"),
                endtime = $(this).attr("data-endtime"),
                plotTypeId = $(this).attr("data-plotTypeId"),
                csParam = clientId && clientId != "undefined" ? '&clientId=' + clientId :
                    serverId && serverId != "undefined" ? '&serverId=' + serverId : "";
            if (chartUrl == "/appController/getPlotData.do" ||
                chartUrl == "/url/getPlotData.do"||
                chartUrl == "/depthanaly/depthanalyGraphical.do") {
                if (!_cockpit.verWatBusName(moduleId, busiId, watchpointId, ipmCenterId)) {
                    location.href = 'bssSession.html?' +
                        //'chartUrl='+chartUrl+'&' +
                        'plotId=' + plotId + '&' +
                        'moduleId=' + moduleId + '&' +
                        'busiId=' + busiId + '&' +
                        'plotTypeId=' + plotTypeId + '&' +
                        'ipmCenterId=' + ipmCenterId + '&' +
                        'watchpointId=' + watchpointId + '&' +
                        'starttime=' + starttime + '&' +
                        'endtime=' + endtime;
                }
            } else {
                var result;
                if (clientId && clientId != "undefined") {
                    result = _cockpit.verWatBusName(moduleId, clientId, watchpointId, ipmCenterId);
                } else {
                    result = _cockpit.verWatBusName(moduleId, serverId, watchpointId, ipmCenterId);
                }
                if (!result) {
                    location.href = 'commun_queue.html?' +
                        'moduleId=' + moduleId + '&' +
                        'ipmCenterId=' + ipmCenterId + '&' +
                        'watchpointId=' + watchpointId + '&' +
                        'chartUrl=' + chartUrl + '&' +
                        'plotId=' + plotId + '&' +
                        'plotTypeId=' + plotTypeId + '&' +
                        'starttime=' + starttime + '&' +
                        'endtime=' + endtime + csParam;
                }
            }
        }
    });
    /**
     * 点击左侧侧边栏保存按钮 保存此驾驶仓页面中的图片 小列表 图形 连线 文字 圆角框
     */
    $(".sa-side-save").click(function () {
        if (_cockpit.Islock()) {
            _cockpit.saveElement(true);
        }
    });
    /**
     * 当用户点击驾驶仓进入驾驶仓页面时请求之前用户所保存的此驾驶仓的数据
     */
    $.ajax({
        url: "/viewConfig/getModuleConfig.do",
        method: "POST",
        data: {
            moduleId: 0,
            busiId: _cockpit.getUrlParams().busiId
        },
        dataType: "json",
        beforeSend:function (XMLHttpRequest) {},
        success: function (data,textStatus,XMLHttpRequest) {
            _cockpit.getSaveDate(data);
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    /**
     * 点击时间回溯确定按钮
     */
    $(".timesure").click(function () {
        //先要验证开始时间与结束时间是否为空且格式正胡且开始时间小于结束时间
        var numS = $.myTime.DateToUnix($("#inpend").val()) - $.myTime.DateToUnix($("#inpstart").val());
        if ($("#inpstart").val() && $("#inpend").val() && (numS - 10 >= 0)) {
            if ($(".drageTable").length || $("._lineChartdb").length) {
                _cockpit.huiSu = true;
                $("#time").attr({
                    "datah-strTime":$.myTime.DateToUnix($("#inpstart").val()),
                    "datah-endTime":$.myTime.DateToUnix($("#inpend").val())
                });
                _cockpit.refreshSmallListOrChart($.myTime.DateToUnix($("#inpstart").val()),
                    $.myTime.DateToUnix($("#inpend").val()), "back");
                //停止10秒一次自动刷新数据
                clearInterval(timer);
                //将时间赋值到右上角
                if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
                    (new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
                    $("#time").text($("#inpstart").val() + " ~ " + $("#inpend").val())
                } else {
                    var index = $("#inpstart").val().split("-")[0].length + 1;
                    $("#time").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
                }
                $('a[data-drawer="times"]').trigger("click");
            }else {
                jeBox.alert("当前未创建元素，无法进行回溯");
                $('a[data-drawer="times"]').trigger("click");
            }
        } else {
            if(!$("#inpstart").val() || !$("#inpend").val()){
                jeBox.alert("时间不能为空");
                return;
            }
            if(numS<=0){
                jeBox.alert("结束时间必须大于开始时间");
                return;
            }
            if(numS<10){
                jeBox.alert("最小间隔为10秒钟");
                return;
            }
        }
    });
    /**
     * 刷新粒度选择
     */
    $('input[name="timelidu"]').click(function (e) {
        var timeVal = +$('input[name="timelidu"]:checked').val()*1000;
        if(timeVal){
            clearInterval(timer);
            timer = setInterval(_cockpit.refreshSmallListOrChart, timeVal);
        }else {
            _cockpit.refreshSmallListOrChart(undefined, undefined, "back");
            clearInterval(timer);
            timer = setInterval(_cockpit.refreshSmallListOrChart, 10000);
            //将右上角时间复原
            $("#time").html('<span id="hours"></span>:' +
                '<span id="min"></span>:' +
                '<span id="sec"></span>');
            e.preventDefault();
        }
        _cockpit.huiSu = undefined;
        $("#time").removeAttr("datah-strTime datah-endTime");
        $('a[data-drawer="refresh"]').trigger("click");
    });
    /**
     * 创建文字
     */
    $(".sa-side-words").click(function () {
        if (_cockpit.Islock()) {
            _cockpit.creatWords();
        }
    });
    /**
     *  创建圆角框
     */
    $(".sa-side-roundedBox").click(function () {
        if (_cockpit.Islock()) {
            _cockpit.creatRoundedBox();
        }
    });
    $(window).resize(function () {
        jsPlumb.setSuspendDrawing(false, true); //重绘四个点以及点上的连线
        for(var i=0,_$tab=$(".drageTable");i<_$tab.length;i++){
            var _width = _$tab.eq(i).parent().width();
            _$tab.eq(i).find("tr.healTableTr").children().children().width(_width);
        }
    })
});