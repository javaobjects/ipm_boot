/**
 *  * Created by yanb on 2018/09/17
 */
;$(function () {
    var public_d3util = {
        /**
         * 验证用户名的长度 不超过32个字符
         * @param str
         * @returns {boolean}
         */
        verification: function (str) {
            /*验证用户名 将一个汉字转换成两个字符*/
            var totalCount = 0;
            if (!str) {
                return false;
            }
            for (var i = 0; i < str.length; i++) {
                var c = str.charCodeAt(i);
                if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                    totalCount++;
                } else {
                    totalCount += 2;
                }
            }
            if (totalCount) {
                if (totalCount < 32) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        },
        /**
         * 默认kpiid
         */
        kpiId:"1",
        /**
         * 加载Loading遮罩层
         * 做成可配置的
         * @param parmas
         */
        loadIngHtml:function(parmas){
            var loadingW = parmas.width?parmas.width:"calc(100% - "+$("#sidebar>ul").width()+"px)",
                loadingH = parmas.height?params.height:"calc(100% - "+$("#header").height()+"px)",
                loadingT = parmas.top?parmas.top:$("#header").height() + "px",
                loadingL = parmas.left?parmas.left:$("#sidebar>ul").width() + "px",
                loadingChildText = parmas.text?parmas.text:"加载中,请稍后。。。",
                loadingStyle = "position:absolute;width:"+loadingW+";height:"+loadingH+";left:"+loadingL+";top:"+loadingT+";"
                html = '<div id="_loading_box" class="mloadingDiv" style="'+loadingStyle+'">' +
                    '<div class="loadingDivChild">'+loadingChildText+'</div>'+
                    '</div>';
            if(parmas.toggle){
                $("body").append(html);
                var _Top = $(".mloadingDiv").height() > 61 ? ($(".mloadingDiv").height() - 61) / 2 : 0,
                    _Left = $(".mloadingDiv").width() > 215 ? ($(".mloadingDiv").width()  - 215) / 2 : 0;
                $(".loadingDivChild").css({
                    "left": _Left,
                    "top": _Top
                });
            }else {
                $("#_loading_box").remove();
            }
        },
        /**
         * 清空data数据
         */
        emptyD3graphDatas:function(){
            d3graph.options.datas = {
                nodes:[],
                edges:[]
            }
        },
        /**
         * 此处node存放所有点
         * 包含id 唯一
         * name 需要显示的文字
         * target 数组 与之相连的点 的id
         * value
         * links 里存放所有的连线
         * fromID
         * toID
         * value 连线的需要显示的文字
         * @param data
         * @returns {{nodes: Array, edges: Array}}
         * 此处有时间将ID统一下避免出来id为0的情况
         */
        d3graph_datas:function(data){
            public_d3util.emptyD3graphDatas();
            var nodes = [],
                links = [],
                nodeTempArray = [];
            data.forEach(function (item,index) {
                if(nodeTempArray.indexOf(item.fromIp) == -1){
                    nodeTempArray.push(item.fromIp);
                    nodes.push({
                        id:nodeTempArray.indexOf(item.fromIp),
                        name:item.fromIp,
                        type:item.fromType,
                        alarm:item.fromAlarm,
                        startTime:item.startTime,
                        endTime:item.endTime,
                        target:[]
                    })
                }
                if(nodeTempArray.indexOf(item.toInfo) == -1){
                    nodeTempArray.push(item.toInfo);
                    nodes.push({
                        id:nodeTempArray.indexOf(item.toInfo),
                        name:item.toInfo,
                        type:item.toType,
                        alarm:item.toAlarm,
                        startTime:item.startTime,
                        endTime:item.endTime,
                        target:[]
                    })
                }
                if(nodeTempArray.indexOf(item.fromIp) != -1){
                    if(nodes[nodeTempArray.indexOf(item.fromIp)].target.indexOf(nodeTempArray.indexOf(item.toInfo)) == -1){
                        nodes[nodeTempArray.indexOf(item.fromIp)].target.push(nodeTempArray.indexOf(item.toInfo))
                    }
                }
                if(nodeTempArray.indexOf(item.toInfo) != -1){
                    if(nodes[nodeTempArray.indexOf(item.toInfo)].target.indexOf(nodeTempArray.indexOf(item.fromIp)) == -1){
                        nodes[nodeTempArray.indexOf(item.toInfo)].target.push(nodeTempArray.indexOf(item.fromIp))
                    }
                }
                links.push({
                    fromID:nodeTempArray.indexOf(item.fromIp),
                    toID:nodeTempArray.indexOf(item.toInfo),
                    value:item.value,
                    // valueText:item.value,
                    valueText:public_d3util.lineValText(public_d3util.kpiId,item.value),
                    type:item.type,
                    startTime:item.startTime,
                    endTime:item.endTime
                })
            });
            return {
                nodes:nodes,
                edges:links
            }
        },
        /**
         * 初始化拓朴图 此方法只能调用一次
         * @param data
         */
        initTopo:function (data) {
            d3graph.init({
                nodeImg:true,//开启图片显示
                datas:data,
                style:{
                    svgStyle:{
                        attr:{
                            class:"stage_svg"//stage_svg为必传
                        },
                        css:{
                            width:$(d3graph.options.elementID).parent().width(),
                            height:$(document).height()-$("#header").height()-15-5>623?
                                $(document).height()-$("#header").height()-15-5:
                                623,
                            border: "1px solid rgba(255,255,255,0.15)"
                        }
                    },
                    mapGStyle:{
                        attr:{
                            class:"stage_g",//stage_g 为必传
                            id:"forceGroup"
                        }
                    },
                    lineGStyle:{
                        attr:{
                            class:function (d,i) {
                                return "lineG "+  // lineG为必传
                                    "lineG"+d.source.id + '_' + d.target.id + " "+
                                    "lineG"+d.target.id + '_' + d.source.id;
                            }
                        }
                    },
                    lineStyle:{
                        attr:{
                            class:"line cursor",//line为必传
                            "stroke-width":"1",
                            "stroke":function (d,i) {
                                return d3graph.options.lineColor(d3graph.options.datas.edges,d.value)
                            }
                        }
                    },
                    lineTextStyle:{
                        attr:{
                            class:"lineText none",//lineText 为必传
                            "font-size":"14",
                            "font-weight":"bold",
                            dy:"1.5em",
                            fill:"#fff"
                        }
                    },
                    nodeGStyle:{
                        attr:{
                            class:function (d,i) {
                                return "nodeG nodeId_"+d.id;//nodeG为必传
                            }
                        }
                    },
                    nodeStyle:{
                        attr:{
                            class:"node cursor",//node为必传
                            x:"-15px",
                            y:"-15px",
                            width:"30px",
                            height:"30px",
                            "xlink:href":function(d,i){
                                if(d.type == "server"){
                                    return "img/topo/server.png"
                                }else {
                                    return "img/topo/port.png"
                                }
                            }
                        }
                    },
                    rectStyle:{
                        attr:{
                            class:"nodeRect",//nodeRect为必传
                            x:"-15px",
                            y:"-15px",
                            width:"31px",
                            height:"31px",
                            fill:"none"
                        }
                    },
                    nodeTextStyle:{
                        attr:{
                            class:"nodeText none",//nodeText为必传
                            "font-size":"12",
                            dy:"1.5em",
                            fill:"#fff"
                        }
                    }
                },
                contextmenuMap:{
                    nodeG_contextmenu:[
                        {
                            id:"ctm_removeNode1",
                            name:"NODE右键测试1",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                                console.log(d);
                                console.log(i);
                                console.log(n);
                            }
                        },
                        {
                            id:"ctm_removeNode2",
                            name:"NODE右键测试2",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                                console.log(d);
                                console.log(i);
                                console.log(n)
                            }
                        }
                    ],
                    stage_contextmenu:[
                        {
                            id:"stage_contextmenu1",
                            name:"画布右键测试1",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                            }
                        },
                        {
                            id:"stage_contextmenu2",
                            name:"画布右键测试2",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                            }
                        },
                        {
                            id:"stage_getSaveData",
                            name:"请求保存数据",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                                $.ajax({
                                    url: "/jtopo/getJtopoNames.do",
                                    method:"POST",
                                    data:{},
                                    dataType:"json",
                                    success:function (data,textStatus,XMLHttpRequest) {
                                        console.log(data);
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
                    ],
                    stage_contextmenu2:[
                        {
                            id:"stage_saveData",
                            name:"保存数据",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                                jeBox({
                                    cell:"jbx",
                                    title:"保存",
                                    content:'<input type="text" class="saveTopoNameInput" placeholder="请输入名称" >' +
                                    '<span class="red saveTopoNameTipSpan"></span>',//此处输入内容
                                    maskLock : true ,
                                    btnAlign:"center",
                                    button:[{
                                        name:'确定',
                                        callback:function (index,id) {
                                            console.log(index);
                                            console.log(id);
                                            var _thisBtn = $("#" + id[0].id).children(".jeBox-footer").children(".jeBox-btn0"),
                                                savaName = $.trim($(".saveTopoNameInput").val());
                                            _thisBtn.text("请稍后...").attr("disabled",true);
                                            console.log(savaName);
                                            if(public_d3util.verification(savaName)){
                                                console.log(true);
                                                jeBox.close(index);
                                                $.ajax({
                                                    url: "/jtopo/saveJtopo.do",
                                                    method:"POST",
                                                    data:{
                                                        nodeJson:JSON.stringify(d3graph.options.datas),
                                                        name:new Date().getTime()+savaName
                                                    },
                                                    dataType:"json",
                                                    success:function (data,textStatus,XMLHttpRequest) {
                                                        if(!data){
                                                            jeBox.alert("保存失败,请稍后再试");
                                                        }
                                                    },
                                                    error:function (XMLHttpRequest,textStatus,errorThrown) {
                                                        jeBox.alert("保存失败,请稍后再试");
                                                        console.error(XMLHttpRequest);
                                                        console.error(textStatus);
                                                        console.error(errorThrown);
                                                    },
                                                    complete:function (XMLHttpRequest,textStatus) {}
                                                })
                                            }else {
                                                console.log(false);
                                                if(savaName.length){
                                                    $(".saveTopoNameTipSpan").text("名称必须小于32个字符");
                                                }else {
                                                    $(".saveTopoNameTipSpan").text("名称不能为空");
                                                }
                                                setTimeout(function () {
                                                    $(".saveTopoNameTipSpan").text("");
                                                    _thisBtn.text("确定").attr("disabled",false);
                                                },3000);
                                            }
                                        }
                                    },{
                                        name: '取消',
                                        callback: function(index) {
                                            jeBox.close(index);
                                        }
                                    }]
                                })
                            }
                        }
                        ],
                    linkG_contextmenu:[
                        {
                            id:"linkG_contextmenu1",
                            name:"连线右键测试1",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                            }},
                        {
                            id:"linkG_contextmenu2",
                            name:"连线右键测试2",
                            iconClass:"fontPtcolor glyphicon glyphicon-cog",
                            event:function (d,i,n) {
                            }}
                    ]
                },
                event:{
                    stageEvent:{
                        mousedown:function (d,i) {
                            //此处写框选代码
                        },
                        mouseup:function (d,i) {
                            // 此处为隐藏或删除框选框
                        },
                        mouseover:function(d,i){},
                        mouseout:function(d,i){},
                        click:function(d,i){
                            d3.selectAll(".node")
                                .attr("class","node cursor");
                            d3.selectAll(".nodeRect")
                                .attr("class","nodeRect");
                            d3graph.options.datas.nodes.forEach(function (node) {
                                node.clHight = false;
                            })
                        },
                        contextmenu:function(d,i){
                            console.log("stage contextmenu");
                            if(d3graph.flag){
                                d3graph.highlightContextmenu("stage_contextmenu2");
                            }else {
                                d3graph.highlightContextmenu("stage_contextmenu");
                            }
                            d3.event.preventDefault();
                            event.stopPropagation();
                        }
                    },
                    nodeGEvent:{
                        mouseover:function(d,i,n){
                            d3.select(this).select('text')
                                .attr('font-size', '16')
                                .attr('font-weight', 'bold')
                                .attr("class","nodeText");
                            d3.select(this).select('.node')
                                .attr("class","node cursor selected");
                            d3.select(this).select(".nodeRect")
                                .attr("class","nodeRect selected");
                            for(var i = 0; i < d.target.length; i++) {
                                d3.select(".nodeId_"+d.target[i]).select('text')
                                    .attr('font-size', '14')
                                    .attr('font-weight', 'bold')
                                    .attr("class","nodeText");
                            }
                            //此处还得找到别人target它
                            for(var y = 0;y < d3graph.options.datas.nodes.length;y++){
                                for(var j=0;j<d3graph.options.datas.nodes[y].target.length;j++){
                                    if(d3graph.options.datas.nodes[y].target[j] == d.id){
                                        d3.select(".nodeId_"+d3graph.options.datas.nodes[y].id).select('text')
                                            .attr('font-size', '14')
                                            .attr('font-weight', 'bold')
                                            .attr("class","nodeText");
                                        d3.select(".lineG"+d.id+"_"+d3graph.options.datas.nodes[y].id).select("line")
                                            .attr('stroke-width', 5);
                                        d3.select(".lineG"+d3graph.options.datas.nodes[y].id+"_"+d.id).select("line")
                                            .attr('stroke-width', 5);
                                    }
                                }
                            }
                            for(var x = 0; x < d.target.length; x++) {
                                d3.select(".lineG"+d.id+"_"+d.target[x]).select("line")
                                    .attr('stroke-width', 5);
                                d3.select(".lineG"+d.target[x]+"_"+d.id).select("line")
                                    .attr('stroke-width', 5);
                            }
                        },
                        mouseout:function(d,i,n){
                            d3.select(this).select('text')
                                .attr("class","nodeText none");
                            if(!d.clHight){
                                d3.select(this).select('.node')
                                    .attr('class', 'node cursor');
                                d3.select(this).select(".nodeRect")
                                    .attr("class","nodeRect");
                            }
                            d3.selectAll(".nodeText")
                                .attr("class","nodeText none");
                            d3.selectAll('.line')
                                .attr('stroke-width', 1);
                            for(var x = 0; x < d.target.length; x++) {
                                $("#"+d.index+"_"+d.target[x])
                                    .attr('stroke-width', 1);
                            }
                        },
                        keydown:function(d,i){
                            console.log(d3.event);
                        },
                        click:function(d,i,n){
                            // console.log(d3.event);
                            event.stopPropagation();
                        },
                        dblclick: function(d, i, n) {
                            public_d3util.loadIngHtml({toggle:true});//加载遮罩层
                            if(d3graph.flag && d.target.length == 1){
                                console.log(d);
                                /**
                                 * 此为钻取状态
                                 */
                                if(d.name.indexOf(".") != -1){
                                    /**
                                     * 此为请求IP的接口
                                     * 此处传观察点有用户更改观察点而
                                     * 造成bug的隐患，后续看如何避免
                                     */
                                    $.ajax({
                                        url: "/jtopo/getCommpairByIpPort.do",
                                        method:"POST",
                                        data:{
                                            startTime:d.startTime,
                                            endTime:d.endTime,
                                            ip:d.name,
                                            granularity:$.trim($("#timelidu").children("option:selected").val()),
                                            num:$.trim($("#Ipnumber").val()),
                                            watchpointId: $("#watchpoint").children("option:selected").val() &&
                                            $("#watchpoint").children("option:selected").val() != 0 ?
                                                $("#watchpoint").children("option:selected").val() : undefined,
                                            kpiId:public_d3util.kpiId
                                        },
                                        dataType:"json",
                                        beforeSend:function (XMLHttpRequest) {},
                                        success:function (data,textStatus,XMLHttpRequest) {
                                            if(data.length){
                                                console.log(data);
                                                d3graph.loadData(public_d3util.flagLoadDate(d,data));
                                                setTimeout(function () {
                                                    public_d3util.loadIngHtml({});
                                                },600)
                                            }else {
                                                public_d3util.loadIngHtml({});
                                                jeBox.alert("已经钻取到尽头了");
                                            }
                                        },
                                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                            console.error(XMLHttpRequest);
                                            console.error(textStatus);
                                            console.error(errorThorwn);
                                            public_d3util.loadIngHtml({});
                                            jeBox.alert("已经钻取到尽头了");
                                        },
                                        complete:function (XMLHttpRequest,textStatus) {}
                                    })
                                }else {
                                    /**
                                     * 此为请求端口的接口
                                     */
                                    $.ajax({
                                        url: "/jtopo/getCommpairByIpPort.do ",
                                        method:"POST",
                                        data:{
                                            startTime:d.startTime,
                                            endTime:d.endTime,
                                            // ip:d3graph.options.datas.nodes[+d.target[0]].name,
                                            ip:d3graph.options.datas.nodes[d.target[0] - 1].name,
                                            port:d.name,
                                            granularity:$.trim($("#timelidu").children("option:selected").val()),
                                            num:$.trim($("#Ipnumber").val()),
                                            watchpointId: $("#watchpoint").children("option:selected").val() &&
                                            $("#watchpoint").children("option:selected").val() != 0 ?
                                                $("#watchpoint").children("option:selected").val() : undefined,
                                            kpiId:public_d3util.kpiId
                                        },
                                        dataType:"json",
                                        beforeSend:function (XMLHttpRequest) {},
                                        success:function (data,textStatus,XMLHttpRequest) {
                                            if(data.length){
                                                console.log(data);
                                                d3graph.loadData(public_d3util.flagLoadDate(d,data));
                                                setTimeout(function () {
                                                    public_d3util.loadIngHtml({});
                                                },600);
                                            }else {
                                                public_d3util.loadIngHtml({});
                                                jeBox.alert("已经钻取到尽头了");
                                            }
                                        },
                                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                            console.error(XMLHttpRequest);
                                            console.error(textStatus);
                                            console.error(errorThorwn);
                                            public_d3util.loadIngHtml({});
                                            jeBox.alert("已经钻取到尽头了");
                                        },
                                        complete:function (XMLHttpRequest,textStatus) {}
                                    })
                                }
                            }else {
                                /**
                                 * 此为非钻取状态
                                 */
                                if(!d3graph.flag){
                                    $.ajax({
                                        url: "/jtopo/getCommpairByIpPort.do",
                                        method:"POST",
                                        data:{
                                            ip:d.name,
                                            kpiId:public_d3util.kpiId,
                                            startTime:d.startTime,
                                            endTime:d.endTime
                                        },
                                        dataType:"json",
                                        beforeSend:function (XMLHttpRequest) {},
                                        success:function (data,textStatus,XMLHttpRequest) {
                                            console.log(data);
                                            if(data.length){
                                                d3graph.loadData(public_d3util.enterFlagLoadData(data));
                                                d3graph.zoom.transform(d3graph.fdGraph,d3.zoomIdentity);//重置缩放
                                                d3graph.flag = true;//切换成钻取状态
                                                setTimeout(function () {
                                                    public_d3util.loadIngHtml({});
                                                },600);
                                            }else {
                                                public_d3util.loadIngHtml({});
                                                jeBox.alert("当前IP未找到");
                                            }
                                        },
                                        error:function (XMLHttpRequest,textStatus,errorThorwn) {
                                            console.error(XMLHttpRequest);
                                            console.error(textStatus);
                                            console.error(errorThorwn);
                                            public_d3util.loadIngHtml({});
                                            jeBox.alert("当前IP未找到");
                                        },
                                        complete:function (XMLHttpRequest,textStatus) {}
                                    })
                                }
                            }
                        },
                        contextmenu:function (d,i,n) {
                            d3.selectAll(".node")
                                .attr("class","node cursor");
                            d3.selectAll(".nodeRect")
                                .attr("class","nodeRect");
                            d3graph.options.datas.nodes.forEach(function (node,index) {
                                if(i == index){
                                    node.clHight = true;
                                }else {
                                    node.clHight = false;
                                }
                            });
                            d3.select(this).select('.node')
                                .attr("class","node cursor selected");
                            d3.select(this).select('.nodeRect')
                                .attr("class","nodeRect selected");
                            d3graph.highlightContextmenu("nodeG_contextmenu");
                            d3.event.preventDefault();
                            event.stopPropagation();
                        }
                    },
                    linkGEvent:{
                        mouseover:function (d,i,l) {
                            d3.select(this).select(".line")
                                .attr('stroke-width', 5);
                            d3.select(this).select(".lineText")
                                .attr("class","lineText");
                        },
                        mouseout:function (d,i,l) {
                            d3.select(this).select(".line")
                                .attr('stroke-width', 1);
                            d3.select(this).select(".lineText")
                                .attr("class","lineText none");
                        },
                        click:function (d,i,l) {
                            d3.selectAll(".node")
                                .attr('stroke-width', '')
                                .attr('stroke','');
                            d3graph.options.datas.nodes.forEach(function (node) {
                                node.clHight = false;
                            });
                            event.stopPropagation();
                        },
                        dblclick:function (d,i,l) {
                            var watchpointId = $("#watchpoint").children("option:selected").val()&&$("#watchpoint").children("option:selected").val()!=0?
                                $("#watchpoint").children("option:selected").val():"";
                            if(d.source.name.indexOf(".") != -1 && d.target.name.indexOf(".") != -1){
                                location.href = "commun_queue.html?ipmCenterId=1&starttime="+d.startTime
                                    +"&endtime="+d.endTime+"&watchpointId="+watchpointId+"&ip="+d.source.name+","+d.target.name;
                            }else {
                                if(d.source.name.indexOf(".") != -1){
                                    location.href = "commun_queue.html?ipmCenterId=1&starttime="+d.startTime
                                        +"&endtime="+d.endTime+"&watchpointId="+watchpointId+"&ip="+d.source.name+":"+d.target.name;
                                }else {
                                    location.href = "commun_queue.html?ipmCenterId=1&starttime="+d.startTime
                                        +"&endtime="+d.endTime+"&watchpointId="+watchpointId+"&ip="+d.target.name+":"+d.source.name;
                                }
                            }
                        },
                        contextmenu:function(d,i,l){
                            d3graph.highlightContextmenu("linkG_contextmenu");
                            console.log("link右键");
                            d3.event.preventDefault();
                            event.stopPropagation();
                        }
                    }
                }
            })
                .loadData();
        },
        /**
         * 初次钻取时处理数据方法
         * 默认已经初始化
         * 可能涉及到一个点或多个点
         * 还差与data数据对应起来。。
         * 此处假定java返回的数据是
         * ip-port1 - server1
         * ip-port2 - server2
         * ip-port不具有重复性
         * @param data
         * @returns {{nodes: Array, edges: Array}}
         */
        enterFlagLoadData:function (data) {
            public_d3util.emptyD3graphDatas();
            var nodes = [],
                edges = [],
                nodeServerArray = [],//只放server
                nodeServerArrayIndex = [],
                nodeServerPortArray = [],//放server:port 格式以冒号分开
                nodeTempArray = [],
                centerX = $(".stage_svg").width()/2,
                centerY = $(".stage_svg").height()/2;
            data.forEach(function (item,index) {
                if(nodeServerArray.indexOf(item.fromIp) == -1){
                    nodeTempArray.push(item.fromIp);
                    nodeServerArray.push(item.fromIp);
                }
                if(nodeServerPortArray.indexOf(item.fromIp + ":" + item.port) == -1){
                    nodeTempArray.push(item.fromIp + ":" + item.port);
                    nodeServerPortArray.push(item.fromIp + ":" + item.port);
                }
                nodeServerArrayIndex.push(item.fromIp);
            });
            /**
             *  192.168.1.1-192.168.1.50
             * 175.102.18.142
             * 192.168.1.44
             * 192.168.1.24      /192.168.1.13
             * 192.168.1.14   /10.207.5.200
             * 80 161    161 3097    2598 3074 3097 51983
             */
            nodeServerArray.forEach(function (item,index) {
                nodes.push({
                    id:nodes.length + 1,
                    // id:nodes.length,
                    fixed:false,
                    name:item,
                    target:[],
                    center:true,
                    type:data[nodeServerArrayIndex.indexOf(item)].fromType,
                    alarm:data[nodeServerArrayIndex.indexOf(item)].fromAlarm,
                    startTime:data[nodeServerArrayIndex.indexOf(item)].startTime,
                    endTime:data[nodeServerArrayIndex.indexOf(item)].endTime,
                    x:d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                    y:d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                    fx:d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                    fy:d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                });
                var itemTargeArray = [],
                    ServerId = nodes.length;
                    // ServerId = nodes.length - 1;
                for(var i = 0;i<nodeServerPortArray.length;i++){
                    if(nodeServerPortArray[i].indexOf(item) != -1){
                        itemTargeArray.push(nodeServerPortArray[i])
                    }
                }
                for(var j = 0;j < itemTargeArray.length;j++){
                    nodes.push({
                        id:nodes.length + 1,
                        // id:nodes.length,
                        fixed:false,
                        name:itemTargeArray[j].split(":")[1],
                        target:[ServerId],
                        type:"port",
                        alarm:data[nodeServerPortArray.indexOf(itemTargeArray[j])].fromAlarm,
                        startTime:data[nodeServerPortArray.indexOf(itemTargeArray[j])].startTime,
                        endTime:data[nodeServerPortArray.indexOf(itemTargeArray[j])].endTime,
                        x:d3graph.LocationXy(j,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                            itemTargeArray.length,100).x,
                        y:d3graph.LocationXy(j,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                            itemTargeArray.length,100).y,
                        fx:d3graph.LocationXy(j,d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                            itemTargeArray.length,100).x,
                        fy:d3graph.LocationXy(j,d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).x,
                            d3graph.LocationXy(nodeServerArray.indexOf(item),centerX,centerY,nodeServerArray.length,100).y,
                            itemTargeArray.length,100).y,
                    });
                    nodes[nodes.length-j-2].target.push(nodes.length);
                    // nodes[nodes.length - j - 1].target.push(nodes.length - 1);
                    edges.push({
                        fromID:ServerId,
                        toID:nodes.length,
                        // toID:nodes.length - 1,
                        value:data[nodeServerPortArray.indexOf(itemTargeArray[j])].value,
                        // valueText:data[nodeServerPortArray.indexOf(itemTargeArray[j])].value,
                        valueText:public_d3util.lineValText(public_d3util.kpiId,data[nodeServerPortArray.indexOf(itemTargeArray[j])].value),
                        type:data[nodeServerPortArray.indexOf(itemTargeArray[j])].type,
                        startTime:data[nodeServerPortArray.indexOf(itemTargeArray[j])].startTime,
                        endTime:data[nodeServerPortArray.indexOf(itemTargeArray[j])].endTime
                    })
                }
            });
            return {
                nodes:nodes,
                edges:edges
            }
        },
        /**
         * 钻取状态下的处理数据方法
         * 可能涉及到或为IP或为端口
         * 此处得判断当前node是IP还是端口
         * 若为端口则取toInfo与之相连
         * 若为IP则取port与之相连
         * @param _node
         * @param data
         * @returns {{nodes: Array, edges: Array}}
         * 1.192.193.174   175.102.132.85
         */
        flagLoadDate:function (_node,data) {
            var nodes = d3graph.options.datas.nodes,
                edges = d3graph.options.datas.edges;
            if(_node.name.indexOf(".") != -1){
                /**
                 * IP
                 */
                data.forEach(function (item,index) {
                    nodes.push({
                        id:nodes.length + 1,
                        fixed:false,
                        name:item.port,
                        target:[_node.id],
                        type:"port",
                        alarm:item.fromAlarm,
                        startTime:item.startTime,
                        endTime:item.endTime,
                        x:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).x,
                        y:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).y,
                        fx:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).x,
                        fy:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).y,
                    });
                    _node.target.push(nodes.length);
                    edges.push({
                        fromID:_node.id,
                        toID:nodes.length,
                        value:item.value,
                        // valueText:item.value,
                        valueText:public_d3util.lineValText(public_d3util.kpiId,item.value),
                        type:item.type,
                        startTime:item.startTime,
                        endTime:item.endTime
                    })
                });
                _node.center = true;
            }else{
                /**
                 * port
                 */
                data.forEach(function (item,index) {
                    nodes.push({
                        id:nodes.length + 1,
                        fixed:false,
                        name:item.toInfo,
                        target:[_node.id],
                        type:item.toType,
                        alarm:item.toAlarm,
                        startTime:item.startTime,
                        endTime:item.endTime,
                        x:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).x,
                        y:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).y,
                        fx:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).x,
                        fy:d3graph.LocationXy(index,_node.x,_node.y,data.length,100).y,
                    });
                    _node.target.push(nodes.length);
                    edges.push({
                        fromID:_node.id,
                        toID:nodes.length,
                        value:item.value,
                        // valueText:item.value,
                        valueText:public_d3util.lineValText(public_d3util.kpiId,item.value),
                        type:item.type,
                        startTime:item.startTime,
                        endTime:item.endTime
                    })
                });
                _node.center = true;
            }
            return {
                nodes:nodes,
                edges:edges
            }
        },
        /**
         *
         * @param kpiId
         * @param value
         * @returns {*}
         */
        lineValText:function (kpiId,value) {
            var valText;
            switch (kpiId){
                case "1"://网络流量
                case "21"://TCP流量
                case "22"://UDP流量
                    if(value){
                        if(((value / 1024) / 1024) / 1024 - 1 >= 0){
                            /**
                             * 满足此条件单位则为G
                             * @type {string}
                             */
                            valText = +(((value / 1024) / 1024) / 1024).toFixed(2) + "G";
                        }else if((value / 1024) / 1024 - 1 >= 0){
                            /**
                             * 满足此条件单位则为M
                             * @type {string}
                             */
                            valText = +((value / 1024) / 1024).toFixed(2) + "M";
                        }else if(value / 1024 - 1 >= 0){
                            /**
                             * 满足此条件单位则为K
                             * @type {string}
                             */
                            valText = +(value / 1024).toFixed(2) + "k";
                        }else {
                            /**
                             * 满足此条件则单位则为b
                             * @type {string}
                             */
                            valText = +value.toFixed(2) + "b";
                        }
                    }else {
                        valText = "0b";
                    }
                    break;
                case "2"://会话数量
                case "12"://TCP连接重置
                case "13"://TCP连接发起
                    if(value){
                        valText = +value.toFixed(2) + "个";
                    }else {
                        valText = "0个";
                    }
                    break;
                case "3"://数据包速率
                case "19"://小包速率
                    if(value){
                        valText = +value.toFixed(2) + "pps";
                    }else {
                        valText = "0pps";
                    }
                    break;
                case "4"://服务端通信时延
                case "5"://客户端通信时延
                case "6"://链路时延RTT
                    if(value){
                        if((value / 1000) / 60 -1 >=0 ){
                            /**
                             * 满足此条件则单位为分钟
                             * @type {string}
                             */
                            valText = +((value / 1000) / 60 ).toFixed(2) + "m";
                        }else if(value / 1000 - 1 >= 0){
                            /**
                             * 满足此条件单位则为秒
                             * @type {string}
                             */
                            valText = +(value / 1000).toFixed(2) + "s";
                        }else {
                            /**
                             * 满足此条件单位则为ms
                             * @type {string}
                             */
                            valText = +value.toFixed(3) + "ms";
                        }
                    }else {
                        valText = "0ms";
                    }
                    break;
                // case "7": //暂时没有
                // case "8": //暂时没有
                case "9": //网络丢包率
                case "10"://服务端丢包率
                case "11"://客户端丢包率
                case "20"://小包比率
                    if(value){
                        valText = +value.toFixed(2) + "%";
                    }else {
                        valText = "0%";
                    }
                    break;
            }
            return valText;
        }
    };
    /**
     * 参数列表添加展示隐藏
     */
    $.customEleShrink({
        domId: "cond_query",
        showFun:function () {
            $("#cond_query").next().removeClass("col-md-12").addClass("col-md-10");
            $(".stage_svg").css("width",$(d3graph.options.elementID).parent().width())
        },
        hideFun:function () {
            $("#cond_query").next().removeClass("col-md-10").addClass("col-md-12");
            $(".stage_svg").css("width",$(d3graph.options.elementID).parent().width())
        }
    });
    /**
     * 为KPI下拉框赋值
     */
    $.ajax({
        url:"/plot/getTopoKpis.do",
        method:"POST",
        data:{},
        dataType:"json",
        beforeSend:function(XMLHttpRequest){},
        success:function (data,textStatus,XMLHttpRequest) {
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
        error:function (XMLHttpRequest,textStatus,errorThrown) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThrown);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    /**
     * 为观察点下拉框赋值
     */
    $.ajax({
        url:"/watchpointController/getFindAll.do",
        method:"POST",
        data:{},
        dataType:"json",
        beforeSend:function (XMLHttpRequest) {},
        success:function (data,textStatus,XMLHttpRequest) {
            var optionHtml = "";
            data.forEach(function (item,index) {
                optionHtml += '<option value="'+item.id+'">'+item.name+'</option>'
            });
            $("#watchpoint").append(optionHtml);
            $("#watchpoint").selectpicker('refresh');
        },
        error:function (XMLHttpRequest,textStatus,errorThorwn) {
            console.error(XMLHttpRequest);
            console.error(textStatus);
            console.error(errorThorwn);
        },
        complete:function (XMLHttpRequest,textStatus) {}
    });
    /**
     * 加载拓朴图数据
     * 当页面头部传参true时默认不加载拓朴图数据
     */
    if(location.href.split("?").length == 1){
        public_d3util.loadIngHtml({toggle:true});
        $.ajax({
            url:"/jtopo/getAllCommpair.do",
            method:"POST",
            data:{
                startTime:$.timeStampDate($("#inpstart").val()),
                endTime: $.timeStampDate($("#inpend").val()),
                granularity:$("#timelidu").children("option:selected").val(),//力度
                watchpointId:$("#watchpoint").children("option:selected").val() &&
                $("#watchpoint").children("option:selected").val() != 0?
                    $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                num:$("#Ipnumber").val(),//数量
                kpiId: public_d3util.kpiId
            },
            dataType:"json",
            beforeSend:function (XMLHttpRequest) {},
            success:function (data,textStatus,XMLHttpRequest) {
                public_d3util.initTopo(public_d3util.d3graph_datas(data));
                setTimeout(function () {
                    public_d3util.loadIngHtml({});
                },1000)
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn);
                setTimeout(function () {
                    public_d3util.loadIngHtml({});
                },1000)
            },
            complete:function (XMLHttpRequest,textStatus) {}
        })
    }else {
        public_d3util.initTopo(public_d3util.d3graph_datas([]));
        setTimeout(function () {
            public_d3util.loadIngHtml({});
        },1000)
    }
    /**
     * 点击确定按钮生成对应的拓朴图
     * 此处服务端 客户端IP未做验证
     * 时间验证也不严格
     * 待后续做优化
     * 通信对数据是否可为空得与后端确定下
     */
    $("#searchButton").click(function () {
        var _startTime = $.timeStampDate($("#inpstart").val()),
            _endTime = $.timeStampDate($("#inpend").val()),
            granularity = $("#timelidu").children("option:selected").val(),//时间力度
            serverIp = $.trim($("#serverIp").val()) && $.trim($("#serverIp").val()) != "" ?
                $.trim($("#serverIp").val()) : undefined,//服务端IP
            clientIp = $.trim($("#clientIp").val()) && $.trim($("#clientIp").val()) != "" ?
                $.trim($("#clientIp").val()) : undefined,//客户端IP
            watchpointId = $("#watchpoint").children("option:selected").val() &&
            $("#watchpoint").children("option:selected").val()!=0?
                $("#watchpoint").children("option:selected").val():undefined,//观察点id
            num = $.trim($("#Ipnumber").val()) && $.trim($("#Ipnumber").val()) != "" ?
                $.trim($("#Ipnumber").val()) : undefined;//通信对数量
        if(($.trim($("#inpstart").val()) && $.trim($("#inpstart").val()) != "") &&
            ($.trim($("#inpend").val()) && $.trim($("#inpend").val()) != "")){
            if(_endTime - _startTime < 0){
                jeBox.alert("开始时间不能大于结束时间");
                return;
            }
        }else {
            if(!$.trim($("#inpstart").val()) || $.trim($("#inpstart").val()) == ""){
                jeBox.alert("请输入开始时间");
                return;
            }
            if(!$.trim($("#inpend").val()) || $.trim($("#inpend").val()) == ""){
                jeBox.alert("请输入结束时间");
                return;
            }
        }
        if(_endTime - _startTime - granularity < 0){
            jeBox.alert("开始时间结束时间差必须大于时间粒度");
            return;
        }
        public_d3util.loadIngHtml({toggle:true});//加载遮罩层
        if((serverIp && serverIp!="")
            ||(clientIp && clientIp!=""))
        {
            $.ajax({
                url:"/jtopo/getCommpairByIpPort.do",
                method:"POST",
                data:{
                    startTime:_startTime,
                    endTime:_endTime,
                    granularity:granularity,
                    ip:serverIp,
                    segment:clientIp,
                    watchpointId:watchpointId,
                    num:num,
                    kpiId:public_d3util.kpiId
                },
                dataType:"json",
                beforeSend:function(XMLHttpRequest){},
                success:function (data,textStatus,XMLHttpRequest) {
                    if(data.length){
                        d3graph.loadData(public_d3util.enterFlagLoadData(data));
                        d3graph.zoom.transform(d3graph.fdGraph,d3.zoomIdentity);//重置缩放
                        d3graph.flag = true;//将其设置为钻取状态
                        setTimeout(function () {
                            public_d3util.loadIngHtml({})
                        },1000)
                    }else {
                        public_d3util.loadIngHtml({});
                        if(serverIp && clientIp){
                            jeBox.alert("当前IP未找到端口或子网或网段未找到");
                            return;
                        }
                        if(serverIp){
                            jeBox.alert("当前IP未找到");
                            return;
                        }
                        if(clientIp){
                            jeBox.alert("子网或网段未找到");
                            return;
                        }
                    }
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    public_d3util.loadIngHtml({});//关闭遮罩层
                    if(serverIp && clientIp){
                        jeBox.alert("当前IP未找到端口或子网或网段未找到");
                        return;
                    }
                    if(serverIp){
                        jeBox.alert("当前IP未找到");
                        return;
                    }
                    if(clientIp){
                        jeBox.alert("子网或网段未找到");
                        return;
                    }
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            })
        }else {
            /**
             *   否则请求全部的接口
             *   此处应该切换状态为非钻取状态
             */
            $.ajax({
                url: "/jtopo/getAllCommpair.do",
                method:"POST",
                data:{
                    startTime:_startTime,
                    endTime:_endTime,
                    granularity:granularity,
                    watchpointId:watchpointId,
                    num:num,
                    kpiId:public_d3util.kpiId
                },
                dataType:"json",
                beforeSend:function(XMLHttpRequest){},
                success:function (data,textStatus,XMLHttpRequest) {
                    d3graph.loadData(public_d3util.d3graph_datas(data));
                    d3graph.zoom.transform(d3graph.fdGraph,d3.zoomIdentity);//重置缩放
                    d3graph.flag = false;//将状态切换成非钻取状态
                    setTimeout(function () {
                        public_d3util.loadIngHtml({})
                    },1000)
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    setTimeout(function () {
                        public_d3util.loadIngHtml({})
                    },1000)
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            })
        }
    });
    /**
     * 监听kpi下拉框变化渲染对应的
     * 拓朴数据
     * 此处的后端数据返回多为匹配
     * 不上。。待后续确定是否后端数据
     * 算法有误
     */
    $(".kpi").change(function () {
        public_d3util.loadIngHtml({toggle:true});
        public_d3util.kpiId = $(this).children("option:selected").val();
        console.log(public_d3util.kpiId);
        if(d3graph.flag){
            /**
             * 此处逻辑需找后端再次确认下。
             *
             */
            console.log(d3graph.options.datas.edges);

            public_d3util.loadIngHtml({});


        }else {
            /**
             * 按照之前的逻辑
             * 需要将页面中已生成的点与
             * 重新请求得到的数据相匹配
             * 然后重新赋值line的值
             */
            $.ajax({
                url: "/jtopo/getAllCommpair.do",
                method:"POST",
                data:{
                    startTime:$.timeStampDate($("#inpstart").val()),
                    endTime: $.timeStampDate($("#inpend").val()),
                    granularity:$("#timelidu").children("option:selected").val(),//力度
                    watchpointId:$("#watchpoint").children("option:selected").val() &&
                    $("#watchpoint").children("option:selected").val() != 0?
                        $("#watchpoint").children("option:selected").val():undefined,//观察点ID
                    num:$("#Ipnumber").val(),//数量
                    kpiId: public_d3util.kpiId
                },
                dataType:"json",
                beforeSend:function (XMLHttpRequest) {},
                success:function (data,textStatus,XMLHttpRequest) {
                    console.log(data);
                    d3graph.loadData(public_d3util.d3graph_datas(data));
                    setTimeout(function () {
                        public_d3util.loadIngHtml({})
                    },1000)
                },
                error:function (XMLHttpRequest,textStatus,errorThorwn) {
                    public_d3util.loadIngHtml({});
                    console.error(XMLHttpRequest);
                    console.error(textStatus);
                    console.error(errorThorwn);
                },
                complete:function (XMLHttpRequest,textStatus) {}
            })
        }
    });
    /**
     * 当页面大小发生变化时重新赋值topo舞台的宽高
     */
    $(window).resize(function () {
        $(".stage_svg").css({
            width:$(d3graph.options.elementID).parent().width(),
            height:$(document).height()-$("#header").height()-15-5>623?
                $(document).height()-$("#header").height()-15-5:
                623
        })
    })
});