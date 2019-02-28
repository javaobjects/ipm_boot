/**
 * Created by yanb on 2018/06/27
 * QQ 511948469
 * 需要实现的功能：
 * 1、首次页面布局好看（d3自带 这也是吸引我用d3的原因）实现
 * 2、点的自定义属性 实现
 * 3、点的自定义颜色 实现
 * 4、点的自定义大小 实现
 * 5、点的自定义图片（可实现 此demo未做体现）
 * 6、点的mouseover事件 实现
 * 7、点的mouseout事件 实现
 * 8、点的dblclick事件 实现
 * 9、点的mousedown事件 实现
 * 10、点的mouseup事件 实现
 * 11、点的click事件 实现
 * 12、点的拖拽功能 实现
 * 13、点的增加功能 实现
 * 14、点的删除功能 实现（单个）
 * 15、点的更改功能
 * 16、点的查找功能
 * 17、点的自定义位置 不受力的作用 实现
 * 18、点的自定义文字 实现
 * 19、线的自定义颜色 实现
 * 20、线的自定义粗细 实现
 * 21、线的自定义属性 实现
 * 22、线的箭头及其它形形状（可实现 此demo未做体现）
 * 23、线的流动性样式（未做研究）
 * 24、虚线（未做研究）
 * 25、曲线（未做研究）
 * 26、拆线（未做研究）
 * 27、贝塞尔曲线（未做研究）
 * 28、线的mouseover事件 实现
 * 29、线的mouseout事件 实现
 * 30、线的dblclick事件 实现
 * 31、线的mousedown事件 实现
 * 32、线的mouseup事件 实现
 * 33、线的click事件 实现
 * 34、线的自定义文字 实现
 * 35、线的增加功能
 * 36、线的删除功能 删除点的时候删除 实现
 * 37、线的更改功能
 * 38、线的查找功能
 * 38、根据连线值的大小渲染连线颜色的深浅功能 实现
 * 40、双击点增加新点并与之相连的功能 实现
 * 41、清除所有的点线功能 完成
 * 42、鼠标缩放画布功能 完成
 * 43、鼠标拖动画布功能 完成
 * 44、拖动以及缩放画布后居中和还原缩放的功能（暂未实现）
 * 45、画布自适应浏览器大小变化重载功能（暂未实现）
 * 46、自定义点的位置并实现中心点吸引画圆功能 实现
 * 47、鹰眼功能（暂未实现）
 * 48、显示隐藏所有的node文字
 * 49、显示隐藏所有的line文字
 * 50、缩放设定范围 完成
 * 51、点可以更换成图片 实现
 * 52、连线颜色根据值的大小渲染深浅 完成
 * 53、框选删除 暂未实现
 *
 * 目前还差两功能未实现：
 * 1、点按住ctrol键加click可以选择多个 并可以通过delet键删除
 * 2、框选删除
 * bug：拖动某个点之后此点被固定住
 *  要造框选首先需要造好右键菜单功能
 * 右键菜单也要做成可配置的 包括图片 文字 click的执行的函数
 *
 * 命名规范：
 *  stage_box
 *  stage_svg
 *  stage_g
 *  lineG line lineText
 *  nodeG node nodeText
 *  lineG22_25 linG25_22
 *  nodeId_265
 *
 * 右键菜单功能思维：
 * 1、弹出并填充内容
 * 2、click执行对应的功能
 * 3、关闭并清空子元素
 * 4、右键菜单外部传入方法未在内部执行成功
 * 5、mouseup点的时候点被定住咯的bug
 * 6、框选功能
 *
 *
 *
 */
;
!function(){
    window.d3graph = new Object();
    var _this = window.d3graph;
    _this.options = {
        version:"1.5",
        elementID : "#stage_box",
        nodeImg:false,//是否将用图片作node
        style:{
            svgStyle:{
                attr:{
                    tabindex:"1"
                },
                css:{}
            },
            mapGStyle:{
                attr:{
                    tabindex:"2"
                },
                css:{}
            },
            lineGStyle:{
                attr:{
                    tabindex:"3"
                },
                css:{}
            },
            lineStyle:{
                attr:{
                    tabindex:"4"
                },
                css:{}
            },
            lineTextStyle:{
              attr:{
                  tabindex:"4"
              },
              css:{}
            },
            nodeGStyle:{
                attr:{
                    tabindex:"3"
                },
                css:{}
            },
            nodeStyle:{
                attr:{
                    tabindex:"4"
                },
                css:{}
            },
            rectStyle:{
                attr:{
                    tabindex:"4"
                },
                css:{}
            },
            nodeTextStyle:{
                attr:{
                    tabindex:"4"
                },
                css:{}
            }
        },
        contextmenuMap:{
            stage_contextmenu:[
                {
                    id:"ctm_reload",
                    name:"刷新",
                    iconClass:"",
                    event:function (d,i,n) {
                        window.location.reload();
                    }
                }
            ],
            nodeG_contextmenu:[],
            linkG_contextmenu:[]
        },
        event:{
            stageEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            mapGEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            linkGEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            linkEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            linkTextEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            nodeGEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            nodeEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            nodeRectEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            },
            nodeTextEvent:{
                mousedown:function(d,i){},
                mouseup:function(d,i){},
                mouseover:function(d,i){},
                mouseout:function(d,i){},
                mouseenter:function(d,i){},
                mouseleave:function(d,i){},
                mousemove:function(d,i){},
                click:function(d,i){},
                dblclick:function(d,i){},
                contextmenu:function(d,i){},
                keydown:function (d,i) {},
                keypress:function (d,i) {},
                keyup:function (d,i) {},
                touchstart:function (d,i) {},
                touchmove:function (d,i) {},
                touchend:function (d,i) {}
            }
        },
        forc:{
            distance:200,
            collision:20,
            alpha:0.8,//设置最小alpha阈值
            alphaTarget:0,//设置目标alpha
            alphaMin:0.005,//设置最小alpha阈值
            velocityDecay:0.4,//设置速度衰减率
            alphaDecay:0.02//设置α指数衰减率
        },
        datas:{
            nodes:[],
            edges:[]
        },
        lineColor:function(edges,tVal){
            var linkVal = [];
            for(var i=0;i<edges.length;i++){
                if(linkVal.indexOf(edges[i].value)==-1){
                    linkVal.push(edges[i].value);
                }
            }
            var sortLinkVal = linkVal.sort(function (a,b) {
                return a-b;
            });
            if(sortLinkVal.length == 1){
                return _this.options.colorChoose[0];
            }else {
                return _this.options.colorChoose[Math.round((_this.options.colorChoose.length-1)*sortLinkVal.indexOf(tVal)/(sortLinkVal.length-1))]
            }
        },
        palette : {
            "lightgray": "#D9DEDE",
            "gray": "#C3C8C8",
            "mediumgray": "#536870",
            "orange": "#BD3613",
            "purple": "#595AB7",
            "yellowgreen": "#738A05"
        },
        colorChoose :[
            "#CFECF9",
            "#B6E5F9",
            "#9FE3F9",
            "#87DBF9",
            "#75D1F9",
            "#65C3F9",
            "#52B7F9",
            "#36A8F9",
            "#1e81f9"
        ]
    };
    _this.init=function(option){
        if(testObject(option)){
            $.extend(true, _this.options ,option);
        }
        _this.fdGraph = d3.select( _this.options.elementID)
            .append('svg')
            .call(_this.d3css( _this.options.style.svgStyle.css))
            .call(_this.d3attr(_this.options.style.svgStyle.attr))
            .call(_this.zoom)
            .on("dblclick.zoom",null)
            .call(_this.d3event( _this.options.event.stageEvent));
        _this.mapG = _this.fdGraph.append("g")
            .call(_this.d3css( _this.options.style.mapGStyle.css))
            .call(_this.d3attr( _this.options.style.mapGStyle.attr))
            .call(_this.d3event(_this.options.event.mapGEvent));
        return _this;
    };
    _this.zoom = d3.zoom().scaleExtent([0.2,10]).on("zoom",function(){_this.zoomed();});
    _this.zoomed=function(){
        _this.mapG.attr("transform", d3.event.transform);
    };
    _this.loadData=function(data){
        if("object" === $.type(data) && testObject(data)){
            //添加新的节点数据，并根据id去重
            if(isnotarray(data.nodes)){
                _this.options.datas.nodes =  ListUniq(ListAddList(_this.options.datas.nodes,data.nodes  ),'id');
            }
            //添加新的关系数据，并去重
            if(isnotarray(data.edges)){
                _this.options.datas.edges =  ListUniq(ListAddList( _this.options.datas.edges,data.edges  ),[ 'fromID','toID'  ]);
            }
        }
        _this.removeAll();
        //重置数据索引
        _this.resetData();
        _this.force = d3.forceSimulation()  //力学模仿图
            .alpha(_this.options.forc.alpha)
            .nodes(_this.options.datas.nodes)
            .velocityDecay(_this.options.forc.velocityDecay)//衰减因子
            .alphaMin(_this.options.forc.alphaMin)
            .alphaDecay(_this.options.forc.alphaDecay)
            .alphaTarget( _this.options.forc.alphaTarget)
            .force("links", d3.forceLink(_this.options.datas.edges).distance(_this.options.forc.distance).id(function(d) { return d.index; }))
            .force("charge", d3.forceManyBody().strength(-50))          //force.charge - 取得或者设置电荷强度。
            .force("collision", d3.forceCollide(_this.options.forc.collision))     //控制相邻2点的排斥力度
            .force("center", d3.forceCenter(_this.options.style.svgStyle.css.width / 2,_this.options.style.svgStyle.css.height / 2));//设置力的中心

        _this.link = _this.mapG
            .selectAll(".lineG")
            .data(_this.options.datas.edges)
            .enter()
            .append("g")
            .call(_this.d3css(_this.options.style.lineGStyle.css))
            .call(_this.d3attr(_this.options.style.lineGStyle.attr))
            .call(_this.d3event(_this.options.event.linkGEvent));
        _this.link
            .append("line")
            .data(_this.options.datas.edges)
            .call(_this.d3css( _this.options.style.lineStyle.css))
            .call(_this.d3attr( _this.options.style.lineStyle.attr))
            .call(_this.d3event(_this.options.event.linkEvent));
        _this.link
            .append("text")
            .call(_this.d3css((_this.options.style.lineTextStyle.css)))
            .call(_this.d3attr((_this.options.style.lineTextStyle.attr)))
            .call(_this.d3event(_this.options.event.linkTextEvent))
            .text(function(d) { return String(d.valueText) || '' });
        _this.node = _this.mapG
            .selectAll(".nodeG")
            .data(_this.options.datas.nodes)
            .enter()
            .append("g")
            .call(_this.d3css(_this.options.style.nodeGStyle.css))
            .call(_this.d3attr(_this.options.style.nodeGStyle.attr))
            .call(_this.d3event( _this.options.event.nodeGEvent))
            .call(_this.nodeDrag);
        if(_this.options.nodeImg){
            _this.node
                .append("rect")
                .call(_this.d3css(_this.options.style.rectStyle.css))
                .call(_this.d3attr(_this.options.style.rectStyle.attr))
                .call(_this.d3event(_this.options.event.nodeRectEvent));
            _this.node
                .append("image")
                .call(_this.d3css( _this.options.style.nodeStyle.css))
                .call(_this.d3attr( _this.options.style.nodeStyle.attr))
                .call(_this.d3event(_this.options.event.nodeEvent));
        }else {
            _this.node
                .append("circle")
                // .attr("fill",function (d,i) {
                //     if(i%2){
                //         return _this.options.palette.yellowgreen
                //     }else {
                //         return _this.options.palette.purple
                //     }
                // })
                .call(_this.d3css( _this.options.style.nodeStyle.css ))
                .call(_this.d3attr( _this.options.style.nodeStyle.attr))
                .call(_this.d3event(_this.options.event.nodeEvent));
        }
        _this.node
            .append('text')
            .call(_this.d3css(_this.options.style.nodeTextStyle.css))
            .call(_this.d3attr(_this.options.style.nodeTextStyle.attr))
            .call(_this.d3event(_this.options.event.nodeTextEvent))
            .text(function(d){
                return String(d.name) || "" ;
            });
        _this.startSport();
        return  _this;
    };
    _this.dragstart=function(d, i) {
        // if (!d3.event.active)  _this.force.alphaTarget(0.5).restart();
        // d.fx = d.x;
        // d.fy = d.y;
        if(!d3.event.active){
            _this.force.alphaTarget(0.5).restart();
            d.fx = d.x;
            d.fy = d.y;
        }
    };
    _this.dragmove=function(d, i) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
    };
    _this.dragend=function(d, i) {
        // d.fixed = true;    //拖拽开始后设定被拖拽对象为固定
        if (!d3.event.active){
            _this.force.alphaTarget(0);
        }
        //_this.force.stop();
        if(_this.flag && d3.event.sourceEvent.type == "mouseup"){
            // console.log(d);

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
            d3.select(this).select(".node")
                .attr("class","node cursor selected");
            d3.select(this).select(".nodeRect")
                .attr("class","nodeRect selected");
            if(d.center){
                var centerNum = 0,
                    _tIndex = 0;
                for(var i = 0;i<d.target.length;i++){
                    for(j = 0;j<_this.options.datas.nodes.length;j++){
                        if(d.target[i] == _this.options.datas.nodes[j].id && _this.options.datas.nodes[j].center){
                            centerNum += 1;
                        }
                    }
                }
                for(var i = 0;i<d.target.length;i++){
                    for(j = 0;j<_this.options.datas.nodes.length;j++){
                        if(d.target[i] == _this.options.datas.nodes[j].id && !_this.options.datas.nodes[j].center){
                            _this.options.datas.nodes[j].x = _this.LocationXy(_tIndex,d.x,d.y,(d.target.length-centerNum),100).x;
                            _this.options.datas.nodes[j].fx = _this.LocationXy(_tIndex,d.x,d.y,(d.target.length-centerNum),100).x;
                            _this.options.datas.nodes[j].y = _this.LocationXy(_tIndex,d.x,d.y,(d.target.length-centerNum),100).y;
                            _this.options.datas.nodes[j].fy = _this.LocationXy(_tIndex,d.x,d.y,(d.target.length-centerNum),100).y;
                            _tIndex +=1;
                        }
                    }
                }
                d3graph.loadData();
            }
        }
    };
    _this.nodeDrag = d3.drag()
        .on("start", _this.dragstart)
        .on("drag", _this.dragmove)
        .on("end", _this.dragend);
    _this.startSport=function(){
        _this.force.on("tick", _this.tick);
    };
    _this.tick=function() {
        _this.node.attr('transform', function(d, i) {
            return 'translate(' + d.x + ', ' + d.y + ')'
        });
        _this.link.select("text")
            .attr('dy', function(d){
                if(d.target != undefined){
                    return (d.target.y-d.source.y)/2+d.source.y
                }else {
                    return 0
                }
            })
            .attr('dx',function(d){
                if(d.target != undefined){
                    return (d.target.x-d.source.x)/2+d.source.x
                }else {
                    return 0
                }
            });
        _this.link.select("line")
            .attr('x1', function(d) {
                return d.source.x
            })
            .attr('y1', function(d) {
                return d.source.y
            })
            .attr('x2', function(d) {
                if (d.target !== undefined) {
                    return d.target.x
                } else {
                    return d.source.x
                }
            })
            .attr('y2', function(d) {
                if (d.target !== undefined) {
                    return d.target.y
                } else {
                    return d.source.y
                }
            });
        _this.node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
    };
    _this.removeAll=function(){
        _this.mapG.html("");
        return  _this;
    };
    _this.resetData=function(){
        var index = 0;
        if( isnotarray( _this.options.datas.nodes) ) {
            for(var j=0,k = _this.options.datas.nodes.length ;j<k;j++){
                _this.options.datas.nodes[j].index = j;
            }
            index =  _this.options.datas.nodes.length;
        }
        if(isnotarray( _this.options.datas.edges)) {
            for(var j=0,k=_this.options.datas.edges.length;j<k;j++){
                if( isnotarray( _this.options.datas.nodes) ) {
                    for(var i=0,m = _this.options.datas.nodes.length;i<m;i++){
                        if(_this.options.datas.edges[j].fromID === _this.options.datas.nodes[i].id) {
                            _this.options.datas.edges[j].source = _this.options.datas.nodes[i].index;
                        }
                        if(_this.options.datas.edges[j].toID === _this.options.datas.nodes[i].id) {
                            _this.options.datas.edges[j].target = _this.options.datas.nodes[i].index;
                        }
                        _this.options.datas.edges[j].uid = _this.options.datas.edges[j].source + "_" + _this.options.datas.edges[j].target ;
                    }
                }
            }
        }
        //清理断开的关系线
        for(var j=0;j<_this.options.datas.edges.length;j++){
            if( null !== _this.options.datas.edges[j].target && null !==  _this.options.datas.edges[j].source ){

            }else{
                _this.options.datas.edges = arraydelbyindex(_this.options.datas.edges,j );
                j--;
            }
        }
        return _this;
    };
    _this.d3css=function(value){
        function d3css(selection){
            if( testObject(selection)){
                if( 'object' == $.type(value) &&  testObject(value)){
                    for( var key in value ){
                        selection.style(key,value[key] );
                    }
                }
            }
        }
        return d3css;
    };
    _this.d3attr=function(value){
        function d3attr(selection){
            if( testObject(selection)){
                if( 'object' == $.type(value) &&  testObject(value)){
                    for( var key in value ){
                        selection.attr(key,value[key] );
                    }
                }
            }
        }
        return d3attr;
    };
    _this.d3event=function(value){
        function d3event(selection){
            if( testObject(selection)){
                if( 'object' == $.type(value) &&  testObject(value)){
                    for( var key in value ){
                        selection.on(key,value[key] );
                    }
                }
            }
        }
        return d3event;
    };
    _this.flag = false;//是否为钻取状态
    /**
     * 创建右键菜单母容器
     *
     */
    _this.graphContextmenu = d3.select("body").append("ul")
        .attr("class","graphContextmenu none")
        .on("mouseleave",function () {
            _this.highlightContextmenu()
        });
    /**
     * 开启或关闭右键菜单功能
     * 开启传参 stage_contextmenu nodeG_contextmenu linkG_contextmenu
     * 关闭传参 除上述外任意参数
     * @param str
     */
    _this.highlightContextmenu = function(str){
        $(".graphContextmenu").empty();
        $(".graphContextmenu").show();
        switch (str){
            case "stage_contextmenu":
            case "stage_contextmenu2":
            case "nodeG_contextmenu":
            case "linkG_contextmenu":
                d3.map(_this.options.contextmenuMap[str]).each(function (item,index) {
                    d3.select("body").select(".graphContextmenu")
                        .append("li")
                        .attr("class","control_li_ctm")
                        .attr("id",item.id)
                        .on("click",function () {
                            item.event();
                            $(".graphContextmenu").hide();
                        })
                        .append("a")
                        .attr("class","white")
                        .html('<i class="'+item.iconClass+'"></i>'+item.name)
                });
                _this.graphContextmenu
                    .style("left",(d3.event.pageX)+"px")
                    .style("top",(d3.event.pageY-20)+"px")
                    .attr("class","graphContextmenu nav nav-pills nav-stacked");
                break;
            default:
                $(".graphContextmenu").hide();
        }
    };

    function testObject(obj){
        var flag = false;
        if(null !== obj && ""  !== obj){
            flag=true;
        }
        return flag;
    }
    /**
     * 数组去重
     * @param list
     * @param key 去重依据属性(字符串,字符串数组)
     * @returns {Array}
     */
    function ListUniq (list,key){
        var rs = [],keys={} ;
        if("array" == $.type(list)){
            $(list).each(function(){
                if($.inArray( this ,rs)<0 ){
                    if( null != key && "" != key  && 'object' == $.type( this)  ){
                        if( 'string' == $.type( key)  ){
                            if( !keys[this[key]] ){
                                rs.push(this);
                                keys[this[key]] = true;
                            }
                        }else if( 'array' == $.type( key)  ){
                            var temp = "";
                            for(var q =0,w=key.length;q<w;q++){
                                temp+=this[key[q]]||'_';
                            }
                            if( !keys[temp] ){
                                rs.push(this);
                                keys[temp] = true;
                            }
                        }
                    }else{
                        rs.push(this);
                    }
                }

            });
        }
        return rs;
    }
    /**
     * 将数组添加到另一数组中,未去重
     * @param list1
     * @param list2
     * @returns {Array}
     */
    function ListAddList(list1,list2){
        var arr = [];
        if("array"== $.type(list1) && "array"== $.type(list2)){
            arr = list1.concat(list2);
        }
        return arr;
    }
    /**
     * 将数组添加到另一数组中,去重
     * @param list1
     * @param list2
     * @returns {Array}
     */
    function  ListAddListUniq(list1,list2){
        var arr = [];
        if("array"== $.type(list1) && "array"== $.type(list2)){
            arr = list1.concat(list2);
            arr = ListUniq(arr);
        }
        return arr;
    }
    function isnotarray(list){
        var rs = false;
        if($.isArray(list)){
            if(list.length>0){
                rs = true;
            }
        }
        return rs;
    }
    function arraydelbyindex(arr,n){
        if("array" == $.type(arr) && n>=0 ){
            return arr.slice(0,n).concat(arr.slice(n+1,arr.length));
        }
        return arr;
    }

    /**
     *
     * @param i
     * @param centerPointX
     * @param centerPointY
     * @param Num
     * @param cicR
     * @returns {{x: *, y: *}}
     * @constructor
     */
    _this.LocationXy = function (i,centerPointX,centerPointY,Num,cicR) {
        /**
         *﻿﻿
         圆点坐标：(x0,y0)
         半径：r
         角度：a0
         则圆上任一点为：（x1,y1）
         x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
         y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
         *
         */
        var angle = 360/Num,
            hudu = (2*Math.PI/360) * angle * (i+1),
            x = centerPointX + Math.sin(hudu) * cicR,
            y = centerPointY + Math.cos(hudu) * cicR;
        return {
            x:x,
            y:y
        }
    }

}();