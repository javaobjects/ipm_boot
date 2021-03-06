/**
  * 热力图页面
 */
var startTimes =[];
var endTimes=[];
$(function () {
	//为右上角赋值时间
	function timeBackText() {
		var getDefaultEndtime = $.getDefaultEndtime();
		if ((new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime).split("-")[0]) ||
			(new Date().getFullYear() - $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0])) {
			$(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime))
		} else {
			var index = $.myTime.UnixToDate(getDefaultEndtime - 600).split("-")[0].length + 1;
			$(".timeBackText").text($.myTime.UnixToDate(getDefaultEndtime - 600).slice(index) + " ~ " + $.myTime.UnixToDate(getDefaultEndtime).slice(index))
		}
	}
	timeBackText();
	/**
	 * 添加下拉框信息
	 * @domId String 下拉框ID
	 * @data {} 数据
	 */
	var addOption = function (domId, data) {
		var option = "";
		for (var i = 0, len = data.length; i < len; i++) {
			if (data[i].id == "87" || data[i].id == "59" || data[i].id == "57" || data[i].id == "58" || data[i].id == "89" || data[i].id == "90" || data[i].id == "91" || data[i].id == "92" || data[i].id == "302" || data[i].id == "309") {

			} else {
				if (data[i].types != undefined) {
					option += "<option value=" + data[i].id + " name=" + data[i].types[0].id + " >" + data[i].name + "</option>";
				} else {
					option += "<option value=" + data[i].id + " >" + data[i].name + "</option>";
				}

			}
		}
		$("#" + domId).append(option);
	};

	/*观察点展示*/
	$.ajax({
		url: "/watchpointController/getFindAll.do",
		type: "post",
		async: false,
		dataType: "json",
		success: function (data) {
			addOption("watchpoints", data);
		}
	});
	//观察点切换重新请求画图数据
	$("#watchpoints").change(function () {
		watchpoints = $(this).val();
		drawHeat();
	});

	/* 客户端KPI展示*/
	$.ajax({
		url: "/plot/getPlotByModuleId.do",
		type: "post",
		async: false,
		data: { moduleId: 11 },
		dataType: "json",
		success: function (data) {
			addOption("allKpi", data);
		}
	});
	//客户端切换重新请求画图数据
	$("#allKpi").change(function () {
		plotIds = $(this).val();
		plotTypeId = $("#allKpi option:selected").attr("name");
		drawHeat();
	});

	//下拉框与x轴第一列对齐
	var selectPositions = function () {
		var totalCount = 0;
		var names = [];
		//判断服务端字符多少
		$.post("/serverManagement/getAllServerSide.do", { moduleId: 12 }, function (data) {
			for (var i = 0; i < data.length; i++) {
				var nameArr = data[i].name;
				names.push(getStrLeng(nameArr));   //获取到的服务端的名称放到数组里
			}
			var max = names[0];
			for (var j = 0; j < names.length; j++) {
				if (names[j] > max) {
					max = names[j];   //取出数组里元素长度的最大值
				}
			}
			if (max < 6) {
				$("#wposition").css("margin-left", "0px");
			} else {
				if (window.ActiveXObject || "ActiveXObject" in window) {
					var selectPosition = $("#heatMaps").find(".highcharts-plot-background").first().attr("x") - 59;
				} else if (navigator.userAgent.indexOf("Firefox") > -1) {
					var selectPosition = $("#heatMaps").find(".highcharts-plot-background").first().attr("x") - 59.5;
				} else {
					var selectPosition = $("#heatMaps").find(".highcharts-plot-background").first().attr("x") - 62;
				}
				$("#wposition").css({
					"margin-left": selectPosition + "px"
				});
			}
		});
	}
	//生成图形
	function drawHeat() {
		var strTime = $.myTime.DateToUnix($("#inpstart").val());
		var endTime = $.myTime.DateToUnix($("#inpend").val());
		if (strTime == "" || strTime < 0) {
			strTime = 0;
		}
		if (endTime == "" || endTime < 0) {
			endTime = 0;
		}
		plotIds = $("#allKpi").val();
		plotTypeId = $("#allKpi option:selected").attr("name");
		watchpoints = $("#watchpoints").val();
		params = { starttime: strTime, endtime: endTime, watchpointId: watchpoints, plotId: plotIds, plotTypeId: plotTypeId };
		$.drawHChart("heatMaps", "heattile", "/commonController/getHeatmap.do", params, selectPositions);
	}
	drawHeat();

	/**
	 * 整个热力图的默认高度
	 */
	function heatHeiht() {
		var allHeight = document.documentElement.clientHeight;
		$(".heatmapalls").css({ "height": allHeight - 50 });
		$(".heatDrag").css({ "height": allHeight - 50 });
		var hs = $('.heatDrag').height();  // 整个拖动框的高度
		$("#heatMaps").css({ "height": hs - 70 });
	}
	heatHeiht();

	//拖拽、放大缩小
	var _heatMap = {
		/*
		* 验证数字是否为整数
		*
		*/
		isInteger: function (obj) {
			return typeof obj === 'number' && obj % 1 === 0
		},
		/*
		 * 判断是否为锁定状态
		 *
		 */
		Islock: function () {
			var Islock;
			if ($("a[data-drawer='lockStatus']").children("span").text() != "锁定") {
				Islock = true;
			} else {
				Islock = false;
			}
			return Islock;
		},
		/*
		 * 拖拽
		 *  @param {string} heatId 容器id <br>
		 */
		tdragElemnt: function (heatId) {
			$(heatId).Tdrag({
				handle: ".title",
				grid: [Math.round($(".block-area").outerWidth() * 0.01), 5],
				cbMove: function (self, obj) {
					//限制元素被拖出容器最顶部
					if ($(self).css("top").replace(/px/, "") <= 0) {
						$(self).css("top", 0)
					}
					//限制元素被拖出容器最左部
					if ($(self).css("left").replace(/px/, "") <= 0) {
						$(self).css("left", 0)
					} else {
						var xPoint = parseInt($(self).css("left").replace(/px/, "") / ($(".block-area").outerWidth() * 0.01));
						$(self).css("left", xPoint + "%");
					}
				},
				cbEnd: function () {
					$.reFlow("heatMaps");
				}
			});
		},
		/*
		 * 放大缩小
		 *
		 */
		moveElement: function () {
			$(".coor").mousedown(function (e) {
				if (_heatMap.Islock()) {
					var _t = $(this).parent();
					var posix = {
						'w': _t.width(),
						'h': _t.height(),
						'x': e.pageX,
						'y': e.pageY
					};
					$.extend(document, {
						'move': true,
						'call_down': function (e) {
							var _width = Math.max(30, e.pageX - posix.x + posix.w),//此为缩放时当前鼠标位置距离元素最左侧的宽度
								_height = Math.max(30, e.pageY - posix.y + posix.h),//此为缩放时当前鼠标位置距离元素最顶侧的高度
								_index = e.pageY - posix.y,
								Multiple = Math.ceil(_width / ($(".heatmapalls").outerWidth() * 0.01));
							if (_heatMap.isInteger(Multiple)) {
								var _$width = Multiple > 30 ?
									Multiple + "%" : "30%",
									_$height = Multiple > 50 ?
										_$width.replace(/%/, "") / 100 * $(".heatmapalls").outerWidth() * 0.3 + "px" :
										0.50 * $(".heatmapalls").outerWidth() * 0.3 + "px";
								_t.css({
									'width': _$width
								});
								_t.find("#heatMaps").css({
									'height': _height - 70
								});
								$.reFlow("heatMaps");
							}

							var _$width = Multiple + "%";
							_t.css({
								'width': _$width,
								'height': _height
							});
							$.reFlow("heatMaps");
						}
					});
					return false;
				}
			});
		}
	}
	_heatMap.moveElement();
	_heatMap.tdragElemnt(".heatDrag");


	//点击时间回溯确定按钮
	$(".timesure").click(function () {
		 startTimes =[];
         endTimes=[];
		var numS = $.myTime.DateToUnix($("#inpend").val()) - $.myTime.DateToUnix($("#inpstart").val());
		if ($("#inpstart").val() && $("#inpend").val() && (numS - 10 >= 0)) {
			//给后台时间重新传参数
			var strTime = $.myTime.DateToUnix($("#inpstart").val());
			var endTime = $.myTime.DateToUnix($("#inpend").val());
			startTimes.push(strTime);
			endTimes.push(endTime);
			drawHeat();
			//停止30秒一次自动刷新数据
			claerTimerten();
            clearInterval(timer);
			//将时间赋值到右上角
			if ((new Date().getFullYear() - $("#inpstart").val().split("-")[0]) ||
				(new Date().getFullYear() - $("#inpend").val().split("-")[0])) {
				$(".timeBackText").text($("#inpstart").val() + " ~ " + $("#inpend").val())
			} else {
				var index = $.myTime.UnixToDate($.getDefaultEndtime() - 600).split("-")[0].length + 1;
				$(".timeBackText").text($("#inpstart").val().slice(index) + " ~ " + $("#inpend").val().slice(index))
			}
			$('a[data-drawer="times"]').trigger("click");
		} else {
			jeBox.alert("开始时间不能为空，且开时间不能大于结束时间，最小间隔为10秒钟");
		}
	});

	// 窗口大小变化自适应
	$(window).resize(function () {
		heatHeiht();
	});
	
 //定时刷新30秒
  function setTimerten() {
    timerten = setInterval(function () {
      timeBackText();
	  drawHeat();
    }, 30000);
  }
  setTimerten();
  //清除定时器
  function claerTimerten() {
    clearInterval(timerten);
  }
  //获取中文字符的长度
  function getStrLeng(str){
    var realLength = 0;
    var len = str.length;
    var charCode = -1;
    for(var i = 0; i < len; i++){
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) {
            realLength += 1;
        }else{
            // 如果是中文则长度加2
            realLength += 2;
        }
    }
    return realLength;
}

  //刷新
  function timerefresh() {
   timeBackText();
   drawHeat();
  }
  var timer = null;
  $('input[name="timelidu"]').click(function () {
  	claerTimerten();
    var timeVal = +$('input[name="timelidu"]:checked').val() * 1000;
    if (timeVal) {
      if (timeVal) {
        $.ajax({
          url: "/watchpointController/updateUserConfigureByKey.do",
          type: "post",
          data: {
            key: "dataRefreshTime",
            value: timeVal / 1000
          },
          dataType: "json",
          success: function (data) {
            if (!data) {
              jeBox.alert("修改时间粒度失败，请稍后再试");
            } else {
              clearInterval(timer);
              timer = setInterval(timerefresh, timeVal);
            }
            $('a[data-drawer="refresh"]').trigger("click");
          },
          error: function () {
            jeBox.alert("修改时间粒度失败，请稍后再试");
            $('a[data-drawer="refresh"]').trigger("click");
          }
        })
      }
    } else {
      location.reload();
    }
  });
});

//x轴y轴点击跳转事件
function heatmapXClick(clientId,param) {
	var clientIdparam = null;
	$.ajax({
		url: "/client/getClient.do",
		type: "post",
		data: "",
		async: false,
		dataType: "json",
		success: function (data) {
			for (var i = 0; i < data.length; i++) {
				if (clientId == data[i].name) {
					clientIdparam = data[i].id;
				}
			}
		}
	});
	var start = startTimes[0];
	var end = endTimes[0];
	var ipmCenterId = 1;
	var chartUrl = "/client/getClientGraphical.do";
	if (start < 0) {
		start = "";
	}
	if (end < 0) {
		end = "";
	}
	var plotId = $("#allKpi").val();
	var plotTypeId = $("#allKpi option:selected").attr("name");
	var watchpointId = $("#watchpoints").val();
	location.href = 'commun_queue.html?' +
		'starttime=' + start + '&' +
		'endtime=' + end + '&' +
		'ipmCenterId=' + ipmCenterId + '&' +
		'plotId=' + plotId + '&' +
		'plotTypeId=' + plotTypeId + '&' +
		'clientId=' + clientIdparam + '&' +
		'chartUrl=' + chartUrl + '&' +
		'watchpointId=' + watchpointId;
}
function heatmapYClick(serverId) {
	var serverIdparam = null;
	$.ajax({
		url: "/serverManagement/getAllServerSide.do",
		type: "post",
		data: "",
		async: false,
		dataType: "json",
		success: function (data) {
			for (var i = 0; i < data.length; i++) {
				if (serverId == data[i].name) {
					serverIdparam = data[i].id;
				}
			}
		}
	});
	var start = startTimes[0];
	var end = endTimes[0];
	var ipmCenterId = 1;
	var chartUrl = "/serverManagement/getServerSideGraphical.do";
	if (start < 0) {
		start = "";
	}
	if (end < 0) {
		end = "";
	}
	var plotId = $("#allKpi").val();
	var plotTypeId = $("#allKpi option:selected").attr("name");
	var watchpointId = $("#watchpoints").val();
	location.href = 'commun_queue.html?' +
		'starttime=' + start + '&' +
		'endtime=' + end + '&' +
		'ipmCenterId=' + ipmCenterId + '&' +
		'plotId=' + plotId + '&' +
		'plotTypeId=' + plotTypeId + '&' +
		'serverId=' + serverIdparam + '&' +
		'chartUrl=' + chartUrl + '&' +
		'watchpointId=' + watchpointId;
}

