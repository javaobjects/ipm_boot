/**
 * Bootstarp Table 封装
 *  
 * @version 1.0.0
 */
 $(function(){
 	
 	/**
 	 * 表格工具
 	 */
    var tableUtil = {
    	
    	/**
    	 * jQuery对象
    	 */
    	J: $,
    	
    	/**
    	 * 列表列对应单位
    	 */
    	columnUnit: {
            'activeHostNum' : 'num',
            'appDelay' : 'ms',
            'attemptedNum' : 'num',
            'attemptedVSHost' : 'num',
            'clientConDelay' : 'ms',
            'clientDurDelay' : 'ms',
            'clientPktLost' : 'ratio',
            'connNum' : 'num',
            'connRatio' : 'ratio',
            'customAppDelay' : 'ms',
            'ethernetPkts' : 'pps',
            'ethernetTraffic' : 'flow',
            'finAckPkts' : 'num',
            'finRatio' : 'ratio',
            'flow' : 'flow',
            'netPktLost' : 'ratio',
            'rstPkts' : 'num',
            'rstRatio' : 'ratio',
            'rttDurDelay' : 'ms',
            'serverClientRst' : 'num',
            'serverConDelay' : 'ms',
            'serverDurDelay' : 'ms',
            'serverPktLost' : 'ratio',
            'sessionNum' : 'num',
            'tcpTraffic' : 'flow',
            'tcpTrafficVStraffic' : 'ratio',
            'tinyPkts' : 'pps',
            'tinyPktsRatio' : 'ratio',
            'udpTraffic' : 'flow',
            'unKnowCliTraffic' : 'flow',
            'unknownVStraffic' : 'ratio',
            'unKnowSerTraffic' : 'flow',
            'upTo128Pkts' : 'flow',
            'arpTraffic' : 'flow',
            'arpPkts' : 'pps',
            'responseDelay' : 'ms',
            'pageloadDelay' : 'ms',
            'synPkts' : 'num',
            'finPkts' : 'num',
            'l7SessionCountTotal' : 'num',
            'http400Count' : 'num',
            'pageload' : 'ms',
            'rtt' : 'ms',
            'appllatency' : 'ms',
            'clientlatency' : 'ms',
            'serverlatency' : 'ms',
            'queryduration' : 'ms',
            'inTraffic' : 'flow',
            'outTraffic' : 'flow',
            'serverRetransDelay' : 'ms',
            'clientRetransDelay' : 'ms',
            'failRespRatio' : 'ratio',
            'noRespCount' : 'num',
            'noRespRatio' : 'ratio',
            'http500Count' : 'num',
            'avgPktsLen' : 'flowB',
            'zeroWinCount' : 'num',
            'serverRetrans' : 'num',
            'clientRetrans' : 'num',
            'serTcpWinSize' : 'flow',
            'cliTcpWinSize' : 'flow',
            'serverPkts' : 'num',
            'clientPkts' : 'num',
            'averagePacketLength' : 'flowB',
            'zeroWinPkts' : 'num',
            'transCount' : 'num',
            'transDelay' : 'ms',
            'successRatio' : 'ratio',
            'respRatio' : 'ratio',
            'bandWidthRatio' : 'ratio',
            'netPktLostRatio' : 'ratio',
            'serverPkt' : 'num',
            'rtt' : 'ms',
            'unknowPublicTraffic' : 'flow',
            'unknowUserTraffic' : 'flow',
            'loadDelay' : 'ms',
            'qos' : 'ms',
            'clientPktLostRatio' : 'ratio',
            'clientPkt' : 'num',
            'serverPktLostRatio' : 'ratio',
            'load_5' : 'ratio',
            'synAckPkts' : 'num',
            'fin1Pkts' : 'num',
            'fin2Pkts' : 'num',
            'inTraffic' : 'flow',
            'outTraffic' : 'flow',
            'delay': 'ms',
            'url400Count': 'num',
            'httpResponseDelay': 'ms'
        },
    	
		/**
		 * 单位对应格式化函数
		 */
    	formatFun: {
            "num": function(v, r, i) {  
            	var result = "", counter = 0, num = (v || 0).toString();
                for (var i = num.length - 1; i >= 0; i--) {
					counter++;
					result = num.charAt(i) + result;
					if (!(counter % 3) && i != 0) {
						result = ',' + result;
					}
				}
                
                return result;
            },
            
            "ms": function(v, r, i) {
                return (v < 1 ? "<1" : v.toFixed(2)) + "ms";
            },
            
            "ratio": function(v, r, i) {
                return v.toFixed(2) + "%";
            },
            
            "flow": function(v, r, i) {
                var fmt = "";
				if (v < 1000) {
					fmt = v.toFixed(0) + "b";
				} else if (v >= 1000 && v < 1000000) {
					fmt = (v / 1000).toFixed(2) + "Kb";
				} else if (v >= 1000000 && v < 1000000000) {
					fmt = (v / 1000000).toFixed(2) + "Mb";
				} else if (v >= 1000000000 && v < 1000000000000) {
					fmt = (v / 1000000000).toFixed(2) + "Gb";
				} else if (v >= 1000000000000) {
				    fmt = (v / 1000000000000).toFixed(2) + "Tb";
				}
				
				return fmt;
            },
            
            "flowB": function(v, r, i) {
            	
                return parseInt(v) + "B";
            },
            
            "pps": function(v, r, i) {
                var result = "", counter = 0, num = (Math.ceil(v) || 0).toString();
                for (var i = num.length - 1; i >= 0; i--) {
                    counter++;
                    result = num.charAt(i) + result;
                    if (!(counter % 3) && i != 0) {
                        result = ',' + result;
                    }
                }
                
                return result;
            },
            
            "time": function(v, r, i) {
                return v;
            }
    	},
    	
    	/**
    	 * 默认表格配置
    	 */
        defaultConfig: {
        	buttonsClass: "default btn-sm", // 默认更改按钮样式
        	height: undefined,              // 表格自定义高度
            striped: false,                 // 是否有隔行变色效果 'false' 或者 'true'
            sortName: undefined,            // 表格默认排序列名
            sortOrder: "asc",               // 排序方式 'asc' 或者 'desc'
            columns: [],                    // 列配置项
            data: [],                       // 加载json格式数据
            url:  undefined,                // 数据加载地址
            method: "post",                 // 数据请求方式 'get' 或者 'post'
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",// 发送数据编码类型
            dataType: "json",               // 返回数据类型
            sidePagination: "client",       // 分页方式 'client' 或者 'server'
            pagination: true,               // 是否在表格底部显示分页条 'false' 或者 'true'
            paginationLoop: false,          // 是否启用分页条无限循环的功能 'false' 或者 'true'
            pageNumber: 1,                  // 如果分页，默认展示页数
            pageSize: 15,                   // 如果分页，默认展示条数
            pageList: [15],                 // 如果分页，设置可供选择的页面数据条数。设置为All 则显示所有记录
            smartDisplay: true,             // 是否隐藏选择展示页数 'false' 或者 'true'
            escape: false,                  // 是否转义HTML字符，替换 &, <, >, ", `, 和 ' 字符
            search: true,                   // 是否展示搜素框 'false' 或者 'true'
            searchOnEnterKey: false,        // 'true'回车触发 'false'自动触发
            strictSearch: false,            // 'true'全匹配搜索 'false'模糊搜索
            searchText: '',                 // 默认搜素字符
            searchTimeOut: 1000,            // 搜索超时时间，单位为ms
            trimOnSearch: false,            // 是否可以空字符搜索 'false' 或者 'true'
            showHeader: true,               // 是否显示头部
            showColumns: true,              // 是否展示列信息
            showRefresh: false,             // 是否展示刷新按钮
            showToggle: false,              // 是否展示视图切换按钮
            showExport: true,               // 是否展示导出按钮
            exportDataType: "all",          // 'all' 全部数据  'basic'当前页数据 'selected'选中数据
            idField: undefined,             // 主键列
            uniqueId: "id",                 // 唯一列
            paginationHAlign: "left",       // 分页条在水平方向的位置 'left' 或者 'right'
            paginationDetailHAlign: "right",// 分页详细信息在水平方向的位置 'left' 或者 'right'
            
            // 以下为自定义属性，非官方
            ipm_title: '数据表格',             // 表格名称
            ipm_shrink: true,               // 是否可以收缩 'false' 或者 'true'
            ipm_show: true,                 // 是否默认展示表格 'false' 或者 'true'
            ipm_show_title: true,           // 是否展示表头说明
            ipm_column_save: false,         // 是否开启列保存配置.注意事项使用时，必须在每个column中,加上后端的ID值.如:[{id: 后端ID, title... field... }]
            ipm_toolbar: undefined,         // 自定义工具按钮  例：[ { name: '按钮名称' type: Glyphicons图标库后缀'plus'、'minus'等, call: '事件' } ]
            ipm_toolbar_style: undefined    // 自定义工具按钮添加行内样式
        },
        
        /**
         * 表格创建
         * 
         * @url String     服务器URL
         * @param {}       服务器参数
         * @tableDomId String   表格ID
         * @tableConfig {} 表格配置
         */
        initTable: function(url, params, tableDomId, tableConfig) {
        	this.initConfig(url, params, tableConfig);
        	var table = this.J("#" + tableDomId);
			if(tableConfig.ipm_show_title) {
                var Jtitle = this.initTitle(tableConfig);
                table.parent().prepend(Jtitle);
			}
			var tbarId = this.customToolbar(tableDomId, tableConfig);
			if(tbarId) {
                tableConfig.toolbar = "#" + tbarId;
			}
			this.initColumnFormat(tableConfig.columns);
			this.initColumnSave(tableConfig);
		    table.bootstrapTable(tableConfig);
		    if(!tableConfig.ipm_show) {
                Jtitle.siblings().toggle();
		    }
        },
        
        /**
         * 初始化配置
         * 
         * @url String     服务器URL
         * @param {}       服务器参数
         * @tableConfig {} 表格配置
         */
        initConfig: function(url, params, tableConfig) {
            if(tableConfig) {
               this.applyIf(tableConfig, this.defaultConfig);
            } else {
               tableConfig = this.defaultConfig;
            }
            if(url) {
               tableConfig.url = url;
            }
            if(params) {
               tableConfig.queryParams = function(p) {
               	   if(params) {
               	       $.extend(p, params || {});
               	       params = null;
               	   }
                   return p;
               };
            }
        },
        
        /**
         * 初始化表名
         * @config {} 表格配置
         * 
         * @return JQ对象
         */
        initTitle: function(config) {
            var titleHtml = '<span class="block-title blockHead ';
            if(config.ipm_shrink) {
                titleHtml += 'cursor">';
            } else {
                titleHtml += '">'
            }
            titleHtml += config.ipm_title;
            if(config.ipm_shrink) {
               titleHtml += ' <i class="ta-';
               titleHtml += (config.ipm_show ? 'minus' : 'plus');
               titleHtml += '"></i>';
            }
            titleHtml += '</span>';
            
            var Jtitle = this.J(titleHtml);
            if(config.ipm_shrink) {
               Jtitle.click(this.tabToggle);
            }
            return Jtitle;
        },
        /**
         * 列值格式化
         */
        initColumnFormat: function(columns) {
            if(columns) {
            	var tmp = null, unit = null;
                for(var i = 0, len = columns.length; i < len; i ++) {
                    tmp = columns[i];
                    unit = this.columnUnit[tmp.field];
                    if(unit) {
                    	if(!tmp.formatter) {
                            tmp.formatter = this.formatFun[unit];
                    	}
                    } else {
//                        console.log(tmp.field + " not formatter! " + tmp.title);
                    }
                }
            }
        },
        
        /**
         * 是否有保存列
         */
        initColumnSave: function(config) {
            if(config.ipm_column_save) {
                config.onColumnSwitch = function(n, c) {
                    var id = 0, columns = this.columns[0];
                    for(var i = 0, len = columns.length; i < len; i ++) {
                        if(columns[i].field == n) {
                            id = columns[i].id;
                            break;
                        }
                    }
                    if(id) {
                        var url = null;
                        if(c) {
                            url = "userConfigure/checkedUserListColumn.do";
                        } else {
                            url = "userConfigure/unCheckedUserListColumn.do";
                        }
                        $.post(url, { columnId: id });
                    }
                }
            }
        },
        
        /**
         * 自定义工具按钮
         * 
         * @domId String 表格ID
         * @config {} 表格配置
         * 
         * @return String 自定义按钮工具ID
         */
        customToolbar: function(domId, config) {
        	var tbar = config.ipm_toolbar, tbarId = null;
            if(tbar && this.J.isArray(tbar)) {
            	tbarId = domId + "CustomToolbar";
            	var Jtbar = this.J("<div id='" + tbarId + "' class='btn-group'></div>"), 
            	    singleHtml = null, single = null,
            	    tbarCss = config.buttonsClass,
            	    tbarStyle = config.ipm_toolbar_style;
            	if(!tbarCss) {
            	   tbarCss = "default";
            	}
            	for(var i = 0, len = tbar.length; i < len; i ++ ) {
                    single = tbar[i];
                    singleHtml = "";
                    singleHtml += "<button class='btn btn-";
                    singleHtml += tbarCss;
                    singleHtml += "'";
                    if(tbarStyle) {
                        singleHtml += (" style='" + tbarStyle + "'");
                    }
                    singleHtml += " title='";
                    singleHtml += single.name;
                    singleHtml += "'><span class='glyphicon glyphicon-";
                    singleHtml += single.type;
                    singleHtml += "'></span></button>";
                    Jtbar.append(this.J(singleHtml).click(single.call));
            	}
            	$("body").eq(0).append(Jtbar);
            }
            
            return tbarId;
        },
        
        /**
         * 表格数据刷新
         * 
         * @tableDomId String 表格ID
         * @params {} 请求参数
         */
        tableRefresh: function(tableDomId, params) {
        	$("#" + tableDomId).bootstrapTable("refresh", {
        	   silent: false,
        	   query: params
        	});
        },
        
        /**
         * 应用属性
         */
        applyIf: function(object, config) {
            if (object && config && typeof config === 'object') {
                for (var i in config) {
                	if(typeof object[i] == "undefined") {
                        object[i] = config[i];
                	}
                }
            }
        },
        
        /**
         * 表格显示或隐藏
         */
        tabToggle: function() {
        	var T = $(this), icon = T.children("i");
			if (icon.hasClass("ta-minus")) {
				icon.removeClass("ta-minus").addClass("ta-plus");
			} else {
				icon.removeClass("ta-plus").addClass("ta-minus");
			}
			T.siblings().toggle(600);
		}
        
    };
    
    $.extend({
        
    	/**
    	 * bootstrap table 统一入口
    	 * 
    	 * @domId String   表格ID
    	 * @url String     服务器URL
    	 * @param {}       服务器参数
    	 * @tableConfig {} 表格配置
    	 */
    	ptcsBSTable: function(domId, url, params, tableConfig) {
    	   tableUtil.initTable(url, params, domId, tableConfig);
    	},
    	
    	/**
    	 * 表格数据刷新
    	 * 
    	 * @domId String 表格ID
    	 * @params {} 刷新参数
    	 */
    	ptcsBSTabRefresh: function(domId, params) {
    	   tableUtil.tableRefresh(domId, params);
    	},
    	
    	/**
    	 * 获取URL参数
    	 */
    	getUrlParams : function() {
            var url = location.href,
               params = new Object(),
               index = url.indexOf("?");
            if (index != -1) {
                var arr = url.substr(index + 1).split("&"), tmp = null;
                for (var i = 0, len = arr.length; i < len; i ++) {
                    tmp = arr[i].split("=");
                    params[tmp[0]] = tmp[1];
                }
            }
            return params;
        },
        
        /**
         * 获取默认时间
         */
        getDefaultEndtime: function() {
            var endtime = 0;
            $.ajax({
                url: "/systemSet/readDateTimeSet.do",
                type: "POST",
                async: false,
                dataType: "json",
                success: function(date) {
                    endtime = $.timeStampDate(date.nowTime);
                }
            });
            endtime -= (endtime % 10);
            // 同步rrd，延迟2分钟
            return endtime - 120;
        }
    });
 });