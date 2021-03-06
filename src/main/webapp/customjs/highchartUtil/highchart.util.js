/**
 * highchart绘图工具
 * 
 * 初始化js条件(暂时需要那么多)
 * <script src="js/jQuery/jquery.min.js"></script>
 * <script src="js/highcharts/code/highcharts.js"></script>
 * <script src="js/highcharts/code/modules/series-label.js"></script>
 * <script src="js/highcharts/code/modules/exporting.js"></script>
 * <script src="customjs/highchartUtil/highchart.util.js"></script>
 *
 *----------------------------------------------------------------
 *
 *地图相关begin
 * <script src="js/jQuery/jquery.min.js"></script>
 * <!-- 地图所需js -->
 * <script src="js/highcharts/code/highcharts.js"></script>
 * <!-- 地图所需js -->
 * <script src="js/highcharts/code/map.js"></script>
 * <!-- 地图下钻所需js -->
 * <script src="js/highcharts/code/drilldown.js"></script>
 * <!-- 加载所有省份所需js -->
 * <script src="js/highcharts/mapdata/countries/cn/china.js"></script>
 * <!-- 引用封装util -->
 * <script src="customjs/highchartUtil/highchart.util.js"></script>
 *地图相关end
 *
 */
$(function() {
    
    /**
     * 默认配置
     */
    Highcharts.setOptions({
        lang : {
            noData : '暂无数据',
            drillUpText:"返回 ",
            resetZoom : "重置缩放比例",
            resetZoomTitle : "重置缩放比例"
        },
        global : {
            useUTC : false
        },
        chart : {
            backgroundColor : 'rgba(49,49,50,0)',
            resetZoomButton: {
				position: {
					align: 'left',
					y: -28
				}
			}
        },
        plotOptions : {
            series : {
                lineWidth : 1,
                lineColor : '#fff',
                fillOpacity : 0.25
            },
            pie : {
                borderWidth : 0
            }
        },
        colors : ['#fff', '#058DC7', '#64E572', '#ED561B', '#6AF9C4',
                '#DDDF00', '#50B432', '#FF9655', '#FFF363', '#016191'],
        title : {
            text : ""
        },
        subtitle : {
            style : {
                color : "#faf9f9"
            }
        },
        noData : {
            style : {
                color : '#fff'
            }
        },
        legend : {
            floating : true,
            layout : 'vertical',
            align : 'right',
            verticalAlign : 'top',
            itemDistance : 15,
            y : -3,
            itemStyle : {
                cursor : 'pointer',
                color : '#e0e0e3',
                fontWeight : 'normal'
            },
            itemHoverStyle : {
                color : '#f0ad4e'
            }
        },
        xAxis : {
            lineColor : "rgba(231,227,227,.4)"
        },
        yAxis : {
            gridLineColor : 'rgba(231,227,227,.4)'
        },
        credits : {
            enabled : false
        },
        exporting : {
            enabled : false
        }
    });
    
    /**
     * 初始化线图数据
     * 
     * @method initLineData
     * @param {object}
     *            data 线图数据
     * @return {object} info {array} dataArr 数据 {object} labelFormat 时间格式化
     *         {string} subtitle 图标子标题
     * 
     */
    var initLineData = function(data) {
        var initMaxAvgMin = function() { // 计算最大、最小、平均
            //最大值                         最小值                  平均值                  累加值
            var max = 0, min = 0, avg = 0, sum = 0;
            //数组长度
            var length = yAxisAllValue.length;
            // 无数据的时候，前端显示0h
            if (length == 0) {
                yAxisAllValue.push(0);
            }
            //获取数组最大值
            var maxTemp = Math.max.apply(null, yAxisAllValue);
            //获取数组最小值
            var minTemp = Math.min.apply(null, yAxisAllValue);
            if(maxTemp > max && maxTemp != -1) {
                max = maxTemp;
            }
            if(minTemp > min && minTemp != -1) {
                min = minTemp;
            }
            for(var j = 0; j < length; j++) {
               sum += yAxisAllValue[j];
            }
            avg = (sum / length).toFixed(2);
            if(avg == "NaN") {
                avg = 0;
            }
            
            var maxNumber = numForUtil(parseFloat(max), unit);
            var avgNumber = numForUtil(parseFloat(avg), unit);
            var minNumber = numForUtil(parseFloat(min), unit);
            
            var subtitle = "最大值: " + maxNumber.value + maxNumber.unit; 
            if (data.unit != "ratio") {
                subtitle += "   平均值:" + avgNumber.value + avgNumber.unit;;
            }
            subtitle += "   最小值:" + minNumber.value + minNumber.unit;
               
            return subtitle;
        }
        
        var info = new Object();
        var timestamp = Date.parse(new Date());
        var tmp = timestamp / 1000 - data.starttime;
        if (tmp <= 86400) { // 10秒
            info.labelFormat = {
                second : '%H:%M:%S'
            };
        } else if (tmp <= 604800) { // 1分钟
            info.labelFormat = {
                minute : '%d %H:%M'
            };
        } else if (tmp <= 1296000) { // 10分钟
            info.labelFormat = {
                minute : '%d %H:%M'
            };
        } else { // 4小时
            info.labelFormat = {
                minute : '%m-%d %H:%M'
            };
        }
        info.dataArr = [];
        var yAxisValue = null, valueArr = null, dataTmp = null, 
            yAxisAllValue = [], unit = data.unit,
            isQoS = (data && data.kpiName == "qos");
        for (var i = 0; i < data.data.length; i++) {
            yAxisValue = [];
            valueArr = data.data[i].value;
            if (valueArr) {
                for (var j = 0; j < valueArr.length; j++) {
                    tmp = valueArr[j];
                    for (var key in tmp) {
                        yAxisValue.push([key * 1000, tmp[key]]);
                    	if (isQoS) {
                    		if (i == 0) {
                                yAxisAllValue.push(tmp[key]);
                    		}
                    	} else {
                    	   yAxisAllValue.push(tmp[key]);
                    	}
                    }
                }
                dataTmp = {
                    name : data.data[i]['name'],
                    data : yAxisValue
                };
                if (data.data[i].type == 'spline') {
                	dataTmp.type = 'spline';
                	dataTmp.dashStyle = 'LongDash';
                	dataTmp.marker = {
                        enabled: false
                    };
                }
                info.dataArr.push(dataTmp);
            }
        }
        info.subtitle = initMaxAvgMin();
        
        return info;
    }
    
    /**
     * 初始化饼图数据
     * @method initPieData
     * @param  {object} data 线图数据
     * @return {array} info 数据
     * 
     */
    var initPieData = function(data) {
        var info = [];
        if (data) {
            var tmp = data.data;
            for (var i = 0; i < tmp.length; i++) {
                info.push({
                   id: tmp[i].id,
                   y: tmp[i].value,
                   name: tmp[i].name
                });
            }
        }
        return info;
    }
    
    /**
     * 初始化柱图数据
     * @method initColumnData
     * @param  {object} data 柱图数据
     * @return {object} info 数据
     *         {array}  xAxisValue X轴数据
     *         {array}  dataArr 柱图数据
     */
    var initColumnData = function(data) {
        
        var stackColumn = function() { // 告警三段图
            var labelFormat = getFormat(data.starttime, data.endtime),
                xdata = data.data.alarmColumnTimeLineBeanList,
                vdata = data.data.alarmColumnDataBeanList,
                tmpData = null, tmp = null, id = 0;
            for (var i = 0; i < xdata.length; i++) {
                xAxisValue.push($.timeStampDate(xdata[i]['longTime'], labelFormat));
            }
            for (var i = 0; i < vdata.length; i ++) {
                id = vdata[i].alarmLevelId;
                tmpData = [];
                dataArr.push({
                    id: "alarm" + id,
                    name : vdata[i].alarmLevelName,
                    color: id == 2 ? "#F4EA2A" : id == 3 ? "#FF862D" : "#D22408",
                    data : tmpData
                });
                tmp = vdata[i].alarmColumnDetailBeanList;
                for(var j = 0; j < tmp.length; j ++) {
                    tmpData.push({
                        starttime: xdata[j].columnstime,
                        endtime: xdata[j].columnetime,
                        alarmLevel: id,
                        y: tmp[j].value
                    });
                }
            }
        }
        
        var column = function() { // 时间柱图
            dataArr[0] = { name: '', data: [] };
            var timeformat = getFormat(data.starttime, data.endtime),
                value = dataArr[0].data, tmp = null, step = 60;
            if (data.data != null && data.data.length > 2) {
                step = parseInt(data.data[1].name) - parseInt(data.data[0].name);
            }
            for (var i = 0; i < data.data.length; i++) {
                tmp = data.data[i];
                xAxisValue.push($.timeStampDate(tmp['name'], timeformat));
                value.push({
                    starttime: tmp['name'] - step,
                    endtime: +tmp['name'],
                    y: tmp['value']
                });
            }
        }
    
        var bar = function() { // TOPN排名
            dataArr[0] = { name: '', data: [] };
            var value = dataArr[0].data, tmp = null;
            for (var i = 0; i < data.data.length; i++) {
                tmp = data.data[i];
                xAxisValue.push(tmp['name']);
                value.push({
                    starttime: data.starttime,
                    endtime: data.endtime,
                    y: tmp['value']
                });
            }
        }
    
        var getFormat = function(starttime, endtime) { // 获取格式
            var labelFormat = "hh:mm:ss";
            var diff = endtime - starttime;
            if(diff <= 14400) { // 4H
            } else if(diff <= 1296000) { // 15D
                labelFormat = "DD hh:mm";
            } else {
                labelFormat = "MM-DD hh";
            }
            
            return labelFormat;
        }
        
        var info = new Object();
        var type = data.type,
            xAxisValue = [],
            dataArr = [];
        switch(type) {
            case "stack_column":
                stackColumn();
                break;
            case "bar":
                bar();
                break;
            default:
                column();
                break;
        }
        info.xAxisValue = xAxisValue;
        info.dataArr = dataArr;
        
        return info;
    }

    /**
     * initMultimarkline
     * 
     * @method initMultimarkline
     * @param {object}
     *            data 多坐标线图数据
     * @return {object} info {array} dataArr 数据 {object} labelFormat 时间格式化
     *         {array} xAxis X轴数据
     * 
     */
    var initMultimarkline = function(data) {
        var info = new Object();
        var timestamp = Date.parse(new Date());
        var tmp = timestamp / 1000 - data.starttime;
        if (tmp <= 86400) { // 10秒
            info.labelFormat = {
                second : '%H:%M:%S'
            };
        } else if (tmp <= 604800) { // 1分钟
            info.labelFormat = {
                minute : '%d %H:%M'
            };
        } else if (tmp <= 1296000) { // 10分钟
            info.labelFormat = {
                minute : '%d %H:%M'
            };
        } else { // 4小时
            info.labelFormat = {
                minute : '%m-%d %H:%M'
            };
        }
        info.dataArr = [];
        var yAxisValue = null, valueArr = null, xAxis = [], unit = data.unit;
        for (var i = 0; i < data.data.length; i++) {
            yAxisValue = [];
            valueArr = data.data[i].value;
            if (valueArr) {
                for (var j = 0; j < valueArr.length; j++) {
                    tmp = valueArr[j];
                    for (var key in tmp) {
                        yAxisValue.push([key * 1000, tmp[key]]);
                        if (i == 0) {
                            xAxis.push(key * 1000);
                        }
                    }
                }
                info.dataArr.push({
                    name : data.data[i]['name'],
                    data : yAxisValue
                });
            }
        }
        info.xAxis = xAxis;
        
        return info;
    }
    
    var drawHChartUtil = {
        
        /**
         * 上下文
         */
        doc : window.document,
        
        /**
         * highchart工具
         */
        highchartUtil : {
            
            /**
             * 选图初始化
             */
            initLineData: initLineData,
            
            /**
             * 饼图初始化
             */
            initPieData: initPieData,
            
            /**
             * 柱图初始化
             */
            initColumnData: initColumnData,
            
            /**
             * 多坐标线图初始化
             */
            initMultimarkline: initMultimarkline,
            
            /**
             * 获取线图配置
             * @method getLineConf
             * @param {object} config 配置 <br>
             *        {string} domId 容器标签编号 <br>
             *        {array}  dataArr 数据 <br>
             *        {string} labelFormat 时间格式 <br>
             *        {string} subtitle 图标子标题 <br>
             *        {function} callback 回调函数 <br>
             * @return {object} 配置 
             * 
             */
            getLineConf : function(config) {
                var domId = config.domId, 
                    dataArr = config.dataArr, 
                    subtitle = config.subtitle, 
                    callback = config.callback, 
                    loadSuccess = config.loadSuccess,
                    labelFormat = config.labelFormat;
                var conf = {
                    chart : {
                        renderTo : domId,
                        type : 'area',
                        zoomType : 'x',
                        events : {
                            selection : function(e) {
                                if (callback) {
                                    if (e.xAxis) {
                                        var starttime = e.xAxis[0].min.toString().split(".")[0];
                                        var endtime = e.xAxis[0].max.toString().split(".")[0];
                                        starttime = (starttime / 1000).toString().split(".")[0];
                                        endtime = (endtime / 1000).toString().split(".")[0];
                                        callback(starttime, endtime);
                                    } else {
                                        callback();
                                    }
                                }
                            },
                            load: function() {
                                loadSuccess && loadSuccess();
                            }
                        }
                    },
                    subtitle : {
                        text : subtitle
                    },
                    xAxis : {
                        type : "datetime",
                        labels : {
                            style : {
                                color : '#f3f2f2'
                            }
                        },
                        dateTimeLabelFormats : labelFormat
                    },
                    tooltip : {
                        shared : false,
                        dateTimeLabelFormats : labelFormat
                    },
                    series : dataArr,
                    yAxis : {
                        title : {
                            text : ""
                        },
                        labels : {
                            style : {
                                color : '#f3f2f2'
                            }
                        },
                        min : 0,
                        minRange : 1,
                        minTickInterval : 1
                    },
                    plotOptions : {
                        area : {
                            marker : {
                                enabled : false,
                                symbol : 'circle',
                                radius : 2,
                                states : {
                                    hover : {
                                        enabled : true
                                    }
                                }
                            }
                        },
                        colorByPoint : true
                    },
                    responsive : {
                        rules : [{
                            condition : {
                                maxWidth : 500
                            }
                        }]
                    }
                };

                return conf;
            },
            
            /**
             * 获取多坐标线图配置
             * @method getMultiMarkLineConf
             * @param {object} config 配置 <br>
             *        {string} domId 容器标签编号 <br>
             *        {array}  dataArr 数据 <br>
             *        {string} labelFormat 时间格式 <br>
             *        {string} subtitle 图标子标题 <br>
             *        {function} callback 回调函数 <br>
             * @return {object} 配置 
             * 
             */
            getMultiMarkLineConf : function(config) {
                var domId = config.domId, 
                    dataArr = config.dataArr, 
                    subtitle = config.subtitle, 
                    callback = config.callback, 
                    labelFormat = config.labelFormat,
                    yAxis = [];
                for (var i = 0, len = dataArr.length; i < len; i ++) {
                	dataArr[i].yAxis = i;
                    yAxis.push({
                        title : {
                            text : dataArr[i].name,
                            style: {
                                color : '#f3f2f2'
                            }
                        },
                        labels : {
                            style : {
                                color : '#f3f2f2'
                            }
                        },
                        min : 0,
                        minRange : 1,
                        minTickInterval : 1,
                        opposite: i != 0
                    });
                }
                var conf = {
                    chart : {
                        renderTo : domId,
                        type : 'area',
                        zoomType : 'x',
                        events : {
                            selection : function(e) {
                                if (callback) {
                                    if (e.xAxis) {
                                        var starttime = e.xAxis[0].min.toString().split(".")[0];
                                        var endtime = e.xAxis[0].max.toString().split(".")[0];
                                        starttime = (starttime / 1000).toString().split(".")[0];
                                        endtime = (endtime / 1000).toString().split(".")[0];
                                        callback(starttime, endtime);
                                    } else {
                                        callback();
                                    }
                                }
                            }
                        }
                    },
                    subtitle : {
                        text : subtitle
                    },
                    xAxis : {
                        type : "datetime",
                        labels : {
                            style : {
                                color : '#f3f2f2'
                            }
                        },
                        dateTimeLabelFormats : labelFormat
                    },
                    tooltip : {
                        // shared : true,
                        shared : false,
                        dateTimeLabelFormats : labelFormat
                    },
                    series : dataArr,
                    yAxis : yAxis,
                    plotOptions : {
                        area : {
                            marker : {
                                enabled : false,
                                symbol : 'circle',
                                radius : 2,
                                states : {
                                    hover : {
                                        enabled : true
                                    }
                                }
                            }
                        },
                        colorByPoint : true
                    },
                    responsive : {
                        rules : [{
                            condition : {
                                maxWidth : 500
                            }
                        }]
                    }
                };

                return conf;
            },
            
            /**
             * 获取饼图配置
             * @method getPieConf
             * @param {object} config 配置 <br>
             *        {string} domId 容器标签编号 <br>
             *        {array}  dataArr 数据 <br>
             *        {function} callback 回调函数 <br>
             * @return {object} 配置 
             * 
             */
            getPieConf: function(config) {
                var domId = config.domId,
                    dataArr = config.dataArr,
                    callback = config.callback,
                    loadSuccess = config.loadSuccess;
                var conf = {
                    chart : {
                        renderTo : domId,
                        type : 'pie'
                    },
                    tooltip : {
                        pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions : {
                        pie : {
                            allowPointSelect : true,
                            cursor : 'pointer',
                            dataLabels : {
                                enabled : false
                            },
                            events : {
                                click : function(e) {
                                    callback && callback(e.point.options);
                                },
                                load: function() {
                                    loadSuccess && loadSuccess();
                                }
                            },
                            showInLegend : true
                        }
                    },
                    legend : {
                        layout : 'vertical',
                        align : 'right',
                        verticalAlign : 'top',
                        y : 8,
                        padding : 0,
                        itemMarginTop : 0,
                        itemMarginBottom : 5
                    },
                    colors : ['#ffb716', '#64e572', '#884898', '#fa911c', 
                        '#39a323', '#434348', '#e4d354', '#8085e8', '#f00840','#8d4653', '#91e8e1','#058dc7','#036630','#2c3093'],
                    series : [{
                        data : dataArr
                    }]
                };
                
                return conf;
            },
            
            /**
             * 获取柱图配置(横向、纵向)
             * @method getColumnBarConf
             * @param {object} config 配置 <br>
             *        {string} domId 容器标签编号 <br>
             *        {array}  dataArr 数据 <br>
             *        {function} callback 回调函数 <br>
             *        {string} type 绘图类型 <br>
             *        {array} xAxisValue X轴数据 <br>
             * @return {object} 配置 
             * 
             */
            getColumnBarConf: function(config) {
                var domId = config.domId,
                    type = config.type,
                    dataArr = config.dataArr,
                    callback = config.callback,
                    xAxisValue = config.xAxisValue;
                var conf = {
                    chart: {
                        renderTo: domId,
                        type: type
                    },
                    subtitle: {
                        text: ''
                    },
                    xAxis: {
                        categories: xAxisValue,
                        labels: {
                            style : {
                                color: '#f3f2f2'
                            }
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: ''
                        },
                        labels: {
                            style : {
                                color: '#f3f2f2'
                            }
                        }
                    },
                    tooltip: {
                        headerFormat: '',
                        pointFormat: '<span style="color:{point.color}">\u25CF </span><b>{point.y:.2f}</b>'
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        bar: {
                            pointPadding: 0.2,
                            borderWidth: 0,
                            events:{  
                                click: function(e) {  
                                    callback && callback(e.point);
                                }
                            }
                        },
                        column: {
                            events:{  
                                click: function(e) {
                                    callback && callback(e.point);
                                }
                            }
                        }
                    },
                    series: dataArr
                };
                
                return conf;
            },
            
            /**
             * 获取热度图配置
             * @method getHeatmapConf
             * @param {object} config 配置 <br>
             *        {string} domId 容器标签编号 <br>
             *        {array}  dataArr 数据 <br>
             *        {array} yAxisValue Y轴数据 <br>
             *        {array} xAxisValue X轴数据 <br>
             *        {function} formatter 格式化函数 <br>
             * @return {object} 配置 
             * 
             */
            getHeatmapConf: function(config) {
                var domId = config.domId,
                    xAxisValue = config.xAxisValue,
                    yAxisValue = config.yAxisValue,
                    dataArr = config.dataArr,
                    formatter = config.formatter,
                    ipmCenterId = config.ipmCenterId;
                var conf = {
                    chart : {
                        renderTo : domId,
                        type : 'heatmap',
                        backgroundColor : 'rgba(0, 0, 0,0)',
                        margin: 2
                    },
                    plotOptions : {
                        series : {
                        	lineWidth: 0,
                            turboThreshold : 0,
                            borderColor : 'transparent',
                            borderRadius : 3,
                        	animation: false,
                            pointPadding: 4,
                            cursor : 'pointer',
                            dataLabels : {
                                enabled : false,
                                color : '#000',
                                formatter : formatter
                            },
                            events : { // 默认为所有模块中小列表健康度的事件
                                click : function(e) {
                                	var options = e.point.options;
                                	var moduleId = options.moduleId;
                            	    var localUrl = "alarmSetting.html?ipmCenterId=" + ipmCenterId
                            	       + "&moduleId=" + moduleId + "&starttime=" + options.starttime
                            	       + "&endtime=" + options.endtime;
                        	        // 观察点情况，业务编号在busiId中
                            	    if (moduleId == 10) {
                            	    	localUrl += ("&watchpointId=" + options.busiId);
                            	    } else {
                            	    	localUrl += ("&watchpointId=" + options.watchpointId
                        	    	        + "&busiId=" + options.busiId);
                            	    }
                            	    location.href = localUrl;
                                }
                            }
                        }
                    },
                    xAxis : {
                        visible : false,
                        type: "category",
                        categories : xAxisValue
                    },
                    yAxis : {
                        visible : false,
                        categories : yAxisValue
                    },
                    legend : {
                        enabled : false
                    },
                    colorAxis : {
                    	visible: false,
                    	dataClassColor: "category",
                        min : 1,
                        max : 4,
                        stops: [[0, "#76EE00"], [0.25, "#F4EA2A"], [0.5, "#FF862D"], [1, "#D22408"]]
                    },
                    tooltip : {
                    	enabled: false
                    },
                    series : [{
                        data : dataArr
                    }]
                };
                
                return conf;
            },
            
            /**
             * 获取告警柱图配置
             * 
             * @method getStackColumnConf
             * @param {object}
             *            config 配置 <br>
             *            {string} domId 容器标签编号 <br>
             *            {array} dataArr 数据 <br>
             *            {function} callback 回调函数 <br>
             *            {array} xAxisValue X轴数据 <br>
             * @return {object} 配置
             * 
             */
            getStackColumnConf: function(config) {
                var domId = config.domId,
                    dataArr = config.dataArr,
                    callback = config.callback,
                    xAxisValue = config.xAxisValue;
                var conf = {
                    chart: {
                        renderTo: domId,
                        type: 'column'
                    },
                    xAxis: {
                        categories: xAxisValue,
                        labels: {
                            style : {
                                color: '#f3f2f2'
                            }
                        }
                    },
                    yAxis: {
                        allowDecimals: false,
                        min: 0,
                        title: {
                            text: '',
                            style:{
                                color:"#dedddd"
                            }
                        },
                        labels: {
                            style : {
                                color: '#f3f2f2'
                            }
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                        pointFormat: '<span style="color:{point.color}">{point.name}</span><b>{point.y:.2f}</b><br/>'
                    },
                    legend: {
                        enabled: false
                    },
                    plotOptions: {
                        column: {
                            stacking: "normal",
                            pointPadding: 0.2,
                            borderWidth: 0,
                            cursor: callback && 'pointer',
                            point : {
                                events: {
                                    click: function(e) {
                                        callback && callback(e.point);
                                    }
                                }
                            }
                        }
                    },
                    series: dataArr,
                    colorByPoint: true
                };
                
                return conf;
            },
            
            /**
             * 线图
             * @method drawLine
             * @param {string} domId 容器标签编号
             * @param {object} data 返回数据
             * @param {function} callback 回调
             * 
             */
            drawLine: function(domId, data, callback, loadSuccess) {
                var info = this.initLineData(data);
				var subtitle = info.subtitle, 
				    labelFormat = info.labelFormat, 
				    dataArr = info.dataArr, 
				    chart = $('#' + domId).highcharts();
				if (chart && chart.series && chart.series.length > 0) {
					chart.subtitle.update({ text : subtitle });
					chart.update({
						xAxis : {
							dateTimeLabelFormats : labelFormat
						},
						tooltip : {
							dateTimeLabelFormats : labelFormat
						},
						series : dataArr
					});
				} else {
					var lineConf = this.getLineConf({
						domId : domId,
						dataArr : dataArr,
						subtitle : subtitle,
						callback : callback,
						loadSuccess : loadSuccess,
						labelFormat : labelFormat
					});
					if (data.unit == "flow") {
						lineConf.tooltip.pointFormatter = function() {
							var nu = numForUtil(this.y, "flow");
							return '<span style="color:' + this.color
									+ '">\u25CF</span> ' + this.series.name
									+ ': <b>' + (nu.value + nu.unit)
									+ '</b><br/>';
						};
					} else if (data.unit == "flowB1024") {
                        lineConf.tooltip.pointFormatter = function() {
                            var nu = numForUtil(this.y, "flowB1024");
                            return '<span style="color:' + this.color
                                    + '">\u25CF</span> ' + this.series.name
                                    + ': <b>' + (nu.value + nu.unit)
                                    + '</b><br/>';
                        };
                    }
					chart = new Highcharts.Chart(lineConf);
					chart.showLoading();
				}
            },
            
            /**
			 * 多坐标线图
			 * 
			 * @method drawMultiMarkLine
			 * @param {string}
			 *            domId 容器标签编号
			 * @param {object}
			 *            data 返回数据
			 * @param {function}
			 *            callback 回调
			 * 
			 */
            drawMultiMarkLine: function(domId, data, callback) {
                var info = this.initMultimarkline(data);
                var subtitle = "多坐标线图", 
                    labelFormat = info.labelFormat, 
                    dataArr = info.dataArr, 
                    chart = $('#' + domId).highcharts();
                if (chart && chart.series && chart.series.length > 0) {
                    for (var i = 0, len = dataArr.length; i < len; i ++) {
                        dataArr[i].yAxis = i;
                    }
                    chart.update({
                        xAxis : {
                            dateTimeLabelFormats : labelFormat
                        },
                        tooltip : {
                            dateTimeLabelFormats : labelFormat
                        },
                        series : dataArr
                    });
                } else {
                    var lineConf = this.getMultiMarkLineConf({
                        domId : domId,
                        dataArr : dataArr,
                        subtitle : subtitle,
                        callback : callback,
                        labelFormat : labelFormat
                    });
                    chart = new Highcharts.Chart(lineConf);
                    chart.showLoading();
                }
            },
            
            /**
             * 饼图
             * @method drawPie
             * @param {string} domId 容器标签编号
             * @param {object} data 返回数据
             * @param {function} callback 回调
             * 
             */
            drawPie: function(domId, data, callback, loadSuccess) {
                var dataArr = this.initPieData(data),
                    chart = $('#' + domId).highcharts();
                if (chart && chart.series && chart.series.length > 0) {
                    chart.series[0].setData(dataArr);
                } else {
                    var config = this.getPieConf({
                        domId: domId,
                        dataArr: dataArr,
                        callback: callback,
                        loadSuccess: loadSuccess
                    });
                    chart = new Highcharts.Chart(config);
                    chart.showLoading();
                }
            },
            
            /**
             * 柱图(横向、纵向)
             * @method drawPie
             * @param {string} domId 容器标签编号
             * @param {object} data 返回数据
             * @param {function} callback 回调
             * 
             */
            drawColumn: function(domId, data, callback) {
                var info = this.initColumnData(data);
                var type = data.type,
                    dataArr = info.dataArr,
                    xAxisValue = info.xAxisValue;
                if (type == "column") {
                    $('#' + domId).attr("data-chartType", type);
                } else {
                    $('#' + domId).attr("data-chartType", type);
                }
                
                var chart = $('#' + domId).highcharts();
                if (chart && chart.series && chart.series.length > 0) {
                    chart.update({
                        xAxis : {
                            categories : xAxisValue
                        },
                        series : dataArr
                    });
                } else {
                    var config = this.getColumnBarConf({
                        type: type,
                        domId: domId,
                        dataArr: dataArr,
                        callback: callback,
                        xAxisValue: xAxisValue
                    });
                    if (type == "bar") {
                        var labels = config.xAxis.labels;
                        labels.useHTML = true;
                        labels.formatter = function () {
                            return '<div align="right" style="text-overflow: ellipsis; overflow: hidden; '
                                    + 'white-space: nowrap; width: ' + (this.chart.containerWidth * 0.3)
                                    + 'px;" title="' + this.value + '">' + this.value + '</div>';
                        };
                    }
                    chart = new Highcharts.Chart(config);
                    chart.showLoading();
                }
            },
            
            /**
             * 告警柱图
             * @method drawStackColumn
             * @param {string} domId 容器标签编号
             * @param {object} data 返回数据
             * @param {function} callback 回调
             * 
             */
            drawStackColumn: function(domId, data, callback) {
                var info = this.initColumnData(data);
                var dataArr = info.dataArr,
                    xAxisValue = info.xAxisValue,
                    chart = $('#' + domId).highcharts();
                if (chart && chart.series && chart.series.length > 0) {
                    chart.update({
                        xAxis : {
                            categories : xAxisValue
                        },
                        series : dataArr
                    });
                } else {
                    var config = this.getStackColumnConf({
                        domId: domId,
                        dataArr: dataArr,
                        callback: callback,
                        xAxisValue: xAxisValue
                    });
                    chart = new Highcharts.Chart(config);
                    chart.showLoading();
                }
            },
            
            /**
             * 热度图
             * @method drawHeatmap
             * @param {string} domId 容器标签编号
             * @param {object} data 返回数据
             * @param {object} params 参数
             * @param {function} callback 回调
             * 
             */
            drawHeatmap: function(domId, data, params, callback) {
                var unit = data.unit;
                    yAxisValue = data.yLabel,
                    xAxisValue = data.xLabel,
                    dataArr = data.data,
                    ipmCenterId = params.ipmCenterId || 1,
                    formatter = function() {
                        var nu = numForUtil(this.point.value, unit);
                        return nu.value + nu.unit;
                    };
                var chart = $('#' + domId).highcharts();
                if (chart && chart.series && chart.series.length > 0) {
                	var config = { 
                        xAxis: { categories: xAxisValue },
                        yAxis: { categories: yAxisValue },
                        series: {
                            data: dataArr,
                            dataLabels: {
                                formatter: formatter
                            }
                        }
                    };
                    if (data.global) { // 全局热力图
                        var starttime = params.starttime,
                            endtime = params.endtime;
                        if (params.starttime) {
                            starttime = starttime.toString();
                            endtime = endtime.toString();
                        } else {
                            starttime = "";
                            endtime = "";
                        }
                        config.tooltip = {
                            formatter : function() {
                                var nu = numForUtil(this.point.value, unit);
                                return '<span style="color:' + this.point.color
                                    + '">\u25CF</span>' + (nu.value + nu.unit) + '</b><br/>';
                            }
                        };
                        config.plotOptions = {
                        	series: {
                        	    events: {
                                    click : function(e) {
                                        var clientId = e.point.clientId;
                                        var serverId = e.point.serverId;
                                        var watchpointId = $("#watchpoints").val();
                                        location.href = 'commun_queue.html?'
                                            + 'starttime=' + starttime
                                            + '&endtime=' + endtime
                                            + '&ipmCenterId=' + ipmCenterId
                                            + '&clientId=' + clientId
                                            + '&serverId=' + serverId
                                            + '&watchpointId=' + watchpointId;
                                    }
                        	    }
                        	}
                        };
                    }
                    chart.update(config);
                } else {
                    var config = this.getHeatmapConf({
                        domId: domId,
                        yAxisValue: yAxisValue,
                        xAxisValue: xAxisValue,
                        dataArr: dataArr,
                        formatter: formatter,
                        ipmCenterId: ipmCenterId
                    });
                    if (data.global) { // 全局热力图
                    	var starttime = params.starttime,
                    	    endtime = params.endtime;
                        if (params.starttime) {
							starttime = starttime.toString();
							endtime = endtime.toString();
						} else {
							starttime = "";
							endtime = "";
						}
						config.chart.margin = null;
                    	config.xAxis = {
                            tickColor : 'rgba(0,0,0,.5)',
                            tickWidth : 1,
                            categories : xAxisValue,
                            labels : {
                                style : {
                                    color : '#f3f2f2',
                                    cursor : 'pointer'
                                },
                                formatter : function() {
                                    return '<a style="background:url(about:blank);" '
                                        + 'href="javascript:void(0);heatmapXClick(\''
                                        + encodeURI(this.value) + '\');">' + this.value + '</a>';
                                }
                            },
                            lineColor : 'rgba(0,0,0,.1)'
                        };
                        config.yAxis = {
                            title : null,
                            categories : yAxisValue,
                            labels : {
                                style : {
                                    color : '#f3f2f2',
                                    cursor : 'pointer',
                                    fontSize : '11px'
                                },
                                formatter : function() {
                                    return '<a style="background:url(about:blank);" '
                                        + 'href="javascript:void(0);heatmapYClick(\''
                                        + encodeURI(this.value) + '\');">' + this.value + '</a>';
                                }
                            },
                            gridLineColor : 'transparent'
                        };
                        config.legend = {
                            align : 'right',
                            layout : 'vertical',
                            margin : 0,
                            verticalAlign : 'top',
                            y : 25,
                            x : 10,
                            floating : false
                        };
                        config.colorAxis = {
                            min : 0,
                            minColor : 'rgba(0,0,0,.1)',
                            maxColor : '#f2ca13',
                            labels : {
                                style : {
                                    color : '#f3f2f2',
                                    cursor : 'pointer'
                                }
                            }
                        };
                        config.tooltip = {
                            formatter : function() {
                                var nu = numForUtil(this.point.value, unit);
                                return '<span style="color:' + this.point.color
                                    + '">\u25CF</span>' + (nu.value + nu.unit) + '</b><br/>';
                            }
                        };
                        config.plotOptions.series.events = {
                            click : function(e) {
                                var clientId = e.point.clientId;
                                var serverId = e.point.serverId;
                                var watchpointId = $("#watchpoints").val();
                                location.href = 'commun_queue.html?'
                                    + 'starttime=' + starttime
                                    + '&endtime=' + endtime
                                    + '&ipmCenterId=' + ipmCenterId
                                    + '&clientId=' + clientId
                                    + '&serverId=' + serverId
                                    + '&watchpointId=' + watchpointId;
                            }
                        }; 
                    }
                    chart = new Highcharts.Chart(config);
                    chart.showLoading();
                }
                if (data.global) { // 全局热力图
                	callback && callback();
                    var enableDataLabels = true;
                    $('.toggle').click(function () {
                        if(enableDataLabels == true){
                            $(this).removeClass("toggle--off");
                            $(this).addClass("toggle--on");
                        }else {
                            $(this).removeClass("toggle--on");
                            $(this).addClass("toggle--off");
                        }
                        chart.series[0].update({
                            dataLabels: {
                                 enabled: enableDataLabels
                            }
                        });
                        enableDataLabels = !enableDataLabels;
                    });
                    $("#heatLoad").hide();
                }
            }
            
        },
        
        /**
         * 绘图方法
         * @method drawChart
         * @param {string} domId 容器标签编号
         * @param {string} titleId 标签标签编号
         * @param {string} url 请求URL
         * @param {object} params 参数
         * @param {function} callback 回调
         * @param {function} loadSucess 图形加载完成回调
         * 
         */
        drawChart : function(domId, titleId, url, params, callback, loadSuccess) {
            var highchartUtil = this.highchartUtil;
            $.ajax({
                url : url,
                type : 'POST',
                async : true,
                data : params,
                timeout : 9000000,
                dataType : 'json',
                success : function(data) {
                    switch (data.type) {
                        case 'line' :
                            highchartUtil.drawLine(domId, data, callback, loadSuccess);
                            break;
                        case 'multimarkline':
                            highchartUtil.drawMultiMarkLine(domId, data, callback);
                            break;
                        case 'pie' :
                            highchartUtil.drawPie(domId, data, callback, loadSuccess);
                            break;
                        case 'stack_column' :
                            highchartUtil.drawStackColumn(domId, data, callback);
                            break;
                        case 'heatmap' :
                            highchartUtil.drawHeatmap(domId, data, params, callback);
                            break;
                        default :  // 柱图
                            highchartUtil.drawColumn(domId, data, callback);
                            break;
                    }
                    if (params.ipmCenterId) {
                    	
                    	//在TOP图形的名称后面，用“（）”，注明统计时间就好，要求精确到分钟，不用显示年、月、秒。只要时和分，就可以
                    	var timeStr = null;
                    	if (data.type == 'bar') {
                    		timeStr = "(" + $.timeStampDate(data.starttime, "hh:mm")  + "-" + $.timeStampDate(data.endtime, "hh:mm") + ")"; 
                    	}
                        if (params.ipmCenterName) {
                            $("#" + titleId).text(params.ipmCenterName + "-" + data.plotName + (timeStr == null ? "" : timeStr));
                        } else {
                            $.ajax({
                                url : "/center/getCenterIpInfo.do",
                                type : "post",
                                dataType : "json",
                                success : function(cdata) {
                                    cdata.forEach(function(item, index) {
                                        if (item.id == params.ipmCenterId) {
                                            $("#" + titleId).text(item.name + "-" + data.plotName + (timeStr == null ? "" : timeStr));
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        $("#" + titleId).text(data.plotName);
                    }
                },
                error : function(xhr, status) {
                    console.error(xhr);
                },
                complete : function() {
                    var chart = $('#' + domId).highcharts();
                    chart && chart.hideLoading();
                }
            });
        },
        
        /**
         * 地图
         * @method drawHMap
         * @param {string} domId 容器标签编号
         * @param {string} title 标题标签编号
         * @param {object} returnData 数据
         * @param {string} url 请求URL
         * @param {object} params 参数
         */
        drawMap : function(domId, title, returnData, url, params){
            var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#' + domId).width() < 400;
            //初始化地图所有省份
            $.each(data, function(i) {
                this.name = this.properties['cn-name'];
                this.drilldown = this.properties['drill-key'];
                var returnObj = returnData[this.name];
                if (returnObj != undefined) {
                    if (returnObj.value != undefined) {
                        this.value = Math.round(returnObj.value*100)/100;
                    }
                }
            });
            var kpiNameTop = $("#kpiName>option:selected").text();  //获取kpi
            var titName = topData(kpiNameTop, returnData);          //title 名称
            if(titName==null){
            	titName="";
            }
            $('#' + domId).highcharts('Map', {
                chart: {
                    spacingBottom:30,
                    backgroundColor: 'rgba(255, 255, 255, 0)',
                    events: {
                        drilldown: function (e) {
                            if (!e.seriesOptions) {
                                var chart = this;
                                var cname = e.point.name;
                                var returnMap = [];
                                var returnObj;
                                var tempColumnName = "";
                                if(cname == '北京市' || cname == '上海市' || cname == '重庆市' || cname == '天津市'){
                                    tempColumnName = "tempT.district";
                                } else {
                                    tempColumnName = "tempT.cityCn";
                                }
                                //直辖市按区分组、省份按市分组
                                params.tempColumnName = tempColumnName;
                             
                                //点击的是哪个省份或者直辖市
                                params.regionCn = cname;
                             
                                chart.showLoading();
                                $.ajax({
                                    url : url,
                                    type : 'POST',
                                    data : params,
                                    timeout : 300000,
                                    dataType : 'json',
                                    success : function(data) {
                                        returnMap = data;
                                        var titCityName = topData(kpiNameTop, returnMap);          //城市  title 名称
                                        if(titCityName==null){
                                        	titCityName="";
                                        }
                                        // 加载城市数据
                                        $.get("js/highcharts/mapdata/countries/cn/geomap/json/" + e.point.drilldown + ".geo.json").done(function(json){
                                             data = Highcharts.geojson(json);
                                             $.each(data, function (i) {
                                                 returnObj = returnMap[this.name];
                                                 if(returnObj != undefined){
                                                    if(returnObj.value != undefined){
                                                        this.value =Math.round(returnObj.value*100)/100;
                                                        this.id =Math.round(returnObj.id);
                                                    }
                                                 }
                                                 if(undefined == this.value || this.value == ""){
                                                     this.value = 0;
                                                 }
                                             });
                                             chart.hideLoading();
                                             //更新title
                                             chart.title.update({ 
                                             	text : titCityName,
							                    align: 'bottom',
							                    margin: -500,
							                    style:{
							                    	"color":"#fff",
							                        "fontWeight":"normal",
							                        "font-size":"12px"
							                    }  
							                  });
							                  //更新 Series data 数据
                                             chart.addSeriesAsDrilldown(e.point, {
                                                 name: e.point.name,
                                                 data: data,
                                                 dataLabels: {
                                                     enabled: true,
                                                     format: '{point.name}'
                                                 }
                                             });
                                        });
                                    },
                                    complete: function (XMLHttpRequest, status) {
                                        if(status == 'timeout') {
                                            chart.hideLoading();
                                            jeBox.alert("请求网络超时，稍后再试");
                                        }
                                     }
                                });
                            }
                        },
                        drillup:function(e){
                        	//更新title
                            chart.title.update({
                            	text : titName,
                            	useHTML:true,
							    align: 'bottom',
							    margin: -500,
							    style:{
							    	"color":"#fff",
							        "fontWeight":"normal",
							        "font-size":"10px"
							    }  
							});
                        }
                    }
                },
                title : {
                    text : titName,
                    useHTML:true,
                    align: 'bottom',
                    margin: -480,
                    style:{
                    	"color":"#fff",
                        "fontWeight":"normal",
                        "font-size":"12px"
                    }
                },
                colors : ['rgba(0,0,0,.15)'],
                tooltip: {
                    formatter : function() {
                        var kpiName = $("#kpiName>option:selected").text();
                        var kpiNameunit = null;
                        if (kpiName == "网络流量") {
                            kpiNameunit = "flow";
                        } else if (kpiName == "会话数量") {
                            kpiNameunit = "num";
                        } else if (kpiName == "服务质量") {
                            kpiNameunit = "ms";
                        } else if (kpiName == "网络丢包率") {
                            kpiNameunit = "ratio";
                        } 
                        if (this.point.value == undefined) {
                            this.value = 0;
                        } else {
                            this.value = this.point.value;
                        }
                        var nu = numForUtil(this.value, kpiNameunit);
                        return '<span style="color:' + this.point.color
                                + '">\u25CF</span> ' + this.series.name
                                + '<br/><span style="color:' + this.point.color
                                + '">\u25CF</span> ' + this.key + ': <b>'
                                + (nu.value + nu.unit) + '</b><br/>';
                    }
                },
                legend: small ? {} : {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle'
                },
                colorAxis: {
                    min: 0,
                    minColor: 'rgba(255,255,255,.3)',
                    maxColor: 'rgba(255,255,255,1)',
                    labels:{
                        style:{
                            "color":"#fff","fontWeight":"normal"
                        }
                    }
                },
                mapNavigation: {
                    enabled: true,
                    buttonOptions: {
                        verticalAlign: 'bottom'
                    }
                },
                plotOptions: {
                    map: {
                        states: {
                            hover: {
                                color: '#EEDD66'
                            }
                        }
                    },
                    series: {  
                        cursor: 'pointer',
                        borderWidth: 1, 
                        dataLabels: {  
                            enabled:true,
                            style:{
                                fontFamily: "trebuchet,trebuchet ms,Hiragino Sans GB,,STXihei,Microsoft YaHei,sans-serif",
                                textOutline:"none",
                                color: '#303030',
                                textShadow: 'none'
                            }
                        },
                        events:{  
                        click:function(e){//点击事件  
                            if(e.point.drilldown == undefined){
                                if(e.point.value == 0 ){
                                    jeBox.alert("温馨提示，该地区无数据");
                                    return false;
                                }else{
                                	var areaDictId =  e.point.id;
                                    var starttime = Date.parse(new Date($("#starttime").val())) / 1000;
                                    var endtime = Date.parse(new Date($("#endtime").val()))/ 1000;
                                    var serverId = $("#server").val();
                                    var plotId = $("#kpiName>option:selected").attr("id");
                                    var plotTypeId = $("#kpiName>option:selected").attr("data-plotTypeId");
                                    var chartUrl = "/serverManagement/getServerSideGraphical.do";
                                    var watchpointId = $("#watchpoint").val();
                                    var ipmCenterId = 1;
                                    var name = $("#kpiName>option:selected").text();
                                    var clickUrl='commun_queue.html?starttime=' + starttime + '&endtime=' + endtime + 
                                    '&ipmCenterId=' + ipmCenterId + '&watchpointId=' + watchpointId +'&areaDictId='+ areaDictId;
                                    if(serverId == "4001") {
                                         $.ajax({
                                            url : "/plot/getPlotByModuleId.do",
                                            type : "post",
                                            data : {
                                                moduleId : 10
                                            },
                                            dataType : "json",
                                            success : function(data) {
                                                chartUrl = "/watchpointController/getWatchpointGraphical.do";
                                                for (var i = 0; i < data.length; i++) {
                                                    if (data[i].name == name) {
                                                        plotId = data[i].id;
                                                        plotTypeId = data[i].types[0].id;
                                                    }
                                                }
                                                clickUrl += ('&chartUrl=' + chartUrl + '&plotId=' + plotId
                                                    + '&plotTypeId=' + plotTypeId);
                                                location.href = clickUrl;
                                            }
                                        });
                                    } else {
                                        clickUrl += ('&chartUrl=' + chartUrl + '&plotId=' + plotId
                                            + '&plotTypeId=' + plotTypeId + '&serverId=' + serverId);
                                        location.href = clickUrl;
                                    }
                                }
                            }
                          }  
                       }    
                    } 
                },
                series : [{
                    data : data,
                    name: '中国',
                    dataLabels: {
                        enabled: true,
                        format: '{point.properties.cn-name}'
                    }
                }],
                drilldown: {
                    activeDataLabelStyle: {
                        color: '#303030',
                        fontFamily: 'trebuchet,trebuchet ms,Hiragino Sans GB,,STXihei,Microsoft YaHei,sans-serif',
                        textDecoration: 'none',
                        textShadow: 'none'
                    },
                    drillUpButton: {
                        relativeTo: 'spacingBox',
                        position: {
                            x: -20,
                            y: 30
                        }
                    }
                },
                credits:{
                    enabled:false
                }
            });
            var chart = $('#' + domId).highcharts();
            chart.showLoading();
        }
        
    };
    
    
    /**
     * 封装入jQuery
     */
    $.extend({
        /**
         * 绘图方法
         * @method drawHChart
         * @param {string} domId 容器标签编号
         * @param {string} titleId 标签标签编号
         * @param {string} url 请求URL
         * @param {object} params 参数
         * @param {function} callback 事件回调
         * @param {function} loadSucess 图形加载完成回调
         * 
         */
        drawHChart: function(domId, titleId, url, params, callback, loadSuccess) {
            drawHChartUtil.drawChart(domId, titleId, url, params, callback, loadSuccess);
        },
    
        /**
         * 地图
         * @method drawHMap
         * @param {string} domId 容器标签编号
         * @param {string} title 标题标签编号
         * @param {string} url 请求URL
         * @param {object} params 参数
         */
        drawHMap: function(domId, title, url, params) {
            $.ajax({
                url : url,
                type : 'POST',
                data : params,
                timeout : 5000000,
                dataType : 'json',
                success : function(data) {
                    $("#mapLoad123").hide();
                    $("#mapLoad").hide();
                    $("#form_submit").button('reset');
                    drawHChartUtil.drawMap(domId, title, data, url, params);
                },
                error : function(xhr, status) {
                    console.error(xhr);
                },
                complete : function() {
                    var chart = $('#' + domId).highcharts();
                    chart.hideLoading();
                }
            });
        },
        
        /**
         * 画图自适应方法
         * @method reFlow
         * @param {string} domId 容器标签编号
         * 
         */
        reFlow: function(domId) {
            var chart = $('#' + domId).highcharts();
            if (chart) {
                chart.reflow();
            }
        },
        
        /**
         * 图形销毁
         * @method hChartDispose
         * @param {string} domId 容器标签编号
         */
        hChartDispose : function(domId) {
            var chart = $('#' + domId).highcharts();
            if(chart) {
                chart.destroy();
            }
        }
    });
    
    function  topData(kpiNameTop, data){
    	//获取TOP 数据
        var valTop = null;      //拼接TOP 10 数据
        var titTop = null;     // 最终：返回的title名称
        var kpiUnitTop = null; // 单位
        var value = null;      // 值
        for(var j = 0; j < data["sort"].resultMap.length; j++){
        	var i = j + 1;
            var sort = data["sort"].resultMap[j];
            if (kpiNameTop == "网络流量") {
            	kpiUnitTop = "flow";
            	value = sort.value;
            } else if (kpiNameTop == "会话数量") {
            	kpiUnitTop = "num";
            	value = sort.value;
            } else if (kpiNameTop == "服务质量") {
            	kpiUnitTop = "ms";
            	value = Math.round(sort.value*100)/100;
            } else if (kpiNameTop == "网络丢包率") {
            	kpiUnitTop = "ratio";
            	value = Math.round(sort.value*100)/100;
            }
            var nu = numForUtil(value, kpiUnitTop);
            var name = sort.key +" : "+ nu.value + nu.unit;
            var index = null;
            if(i < 10){
            	index ="0"+i;
            }else{
            	index = i;
            }
            valTop+="<span>"+index + "</span> <span style='padding-left: 5px'>"+ name +" <br /></span>";
        }
        if(valTop != null){
        	titTop = valTop.substring(4, valTop.length);
        }
        return titTop;
    }
});