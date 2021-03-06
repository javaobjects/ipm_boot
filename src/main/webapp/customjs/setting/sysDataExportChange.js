/**
 * 报表修改
 * @param {string} changeId 选中行id
 *
 */
/**
  * 添加下拉框信息
  * @domId String 下拉框ID
  * @data {} 数据
  */
var addOption = function (domId, data, busid) {
	var option = "";
	option += "<option value='0'>全部</option>";
	for (var i = 0, len = data.length; i < len; i++) {
		if (data[i].id == busid) {
			option += "<option selected=selected value=" + data[i].id + ">" + data[i].name + "</option>";
		} else {
			option += "<option  value=" + data[i].id + ">" + data[i].name + "</option>";
		}
	}
	$("#" + domId).append(option);
};

var addOptions = function (domId, data, mId) {
	if (mId == "10") {
		$(".cwatchpointData").hide();
	} else {
		$(".cwatchpointData").show();
	}
	var option = "";
	for (var i = 0, len = data.length; i < len; i++) {
		if (data[i].id == "1" || data[i].id == "4" || data[i].id == "5" || data[i].id == "6" || data[i].id == "7" || data[i].id == "8" || data[i].id == "9") {

		} else {
			if (data[i].id == mId) {
				option += "<option selected=selected value=" + data[i].id + ">" + data[i].namezh + "</option>";
			} else {
				option += "<option value=" + data[i].id + ">" + data[i].namezh + "</option>";
			}

		}
	}
	$("#" + domId).append(option);
};

var kpiNames = [];
var changeId = null;

function changeData(changeId) {
	$(".dataContent").hide();
	$(".dataDeleteContent").show();
	//修改
	$.ajax({
		url: "/dataPush/getSelectById.do",
		type: "post",
		data: {
			id: changeId
		},
		dataType: "json",
		success: function (data) {
			console.log(data);
			$("#carchivedType").val(data.pushType); //推送类型
			$("#cdataIp").val(data.brokerIp);  //推送ip
			$("#cdataPort").val(data.port);   // 端口
			$("#cdataTopic").val(data.topic);  //主题
			cmodule(data.moduleType);          //模块
			cwatchpointData(data.watchpointId);  //观察点
			allBusiId(data.moduleType, data.busiId);   //业务名称
			$("#cipServer").val(data.serverIp);  //服务端IP
			$("#cipUser").val(data.clientIp);  //客户端IP
			//用于接收所有KPI
			var ckpiId = data.kpiIds.split(",");
			var cpushType = data.pushType;
			if (cpushType == "0") {
				$(".cdataIpAll").hide();
				$(".clist").show();
				$(".ccolumn").hide();
				$.post("/plot/getPlotByModuleId.do", {
					moduleId: data.moduleType
				}, function (datas) {
					for (var i = 0; i < ckpiId.length; i++) {
						for (var j = 0; j < datas.length; j++) {
							if (datas[j].id == ckpiId[i]) {
								datas[j].checked = 1;
							}
						}
					}
					ctypeKpi(datas);
				}, "json");
			} else {
				$(".cdataIpAll").show();
				$(".clist").hide();
				$(".ccolumn").show();
				$.post("/commpair/getCommpairListColumn.do", null, function (cdata) {
					for (var i = 0; i < ckpiId.length; i++) {
						for (var j = 0; j < cdata.length; j++) {
							if (cdata[j].columnen == ckpiId[i]) {
								cdata[j]["checkeds"] = 1;
							}
						}
					}
					cqueue(cdata);
				}, "json");
			}
		}
	});

	//模块类型
	function cmodule(dataModule) {
		$("#cdataModuleType").empty();
		$.post("/authorizeModuleController/getFindAll.do", null, function (data) {
			addOptions("cdataModuleType", data, dataModule);
		}, "json");
	}

	//查询所有观察点
	function cwatchpointData(datawatchpoint) {
		$("#cdataWatchpoint").empty();
		$.post("/watchpointController/getFindAll.do", null, function (data) {
			addOption("cdataWatchpoint", data, datawatchpoint);
		}, "json");
	}

	//根据模块ID 获取所有业务
	function allBusiId(allmoduleId, dataOpration) {
		switch (allmoduleId) {
			case 10:
				$("#cdataOpration").empty();
				$.post("/watchpointController/getFindAll.do", null, function (dataBus) {
					addOption("cdataOpration", dataBus, dataOpration);
				}, "json");
				break;
			case 11:
				$("#cdataOpration").empty();
				$.post("/client/getClient.do", null, function (dataBus) {
					addOption("cdataOpration", dataBus, dataOpration);
				}, "json");
				break;
			case 12:
				$("#cdataOpration").empty();
				$.post("/serverManagement/getAllServerSide.do", null, function (dataBus) {
					addOption("cdataOpration", dataBus, dataOpration);
				}, "json");
				break;
		}
	}

	//模块类型change事件
	$("#cdataModuleType").off().change(function () {
		var cmoduleType = parseInt($(this).val());
		$.ajax({
			url: "/dataPush/getSelectById.do",
			type: "post",
			data: {
				id: changeId
			},
			dataType: "json",
			success: function (data) {
				cwatchpointData($("#cdataWatchpoint").val());  //观察点
				allBusiId(cmoduleType, data.busiId);   //业务名称
				//用于接收所有KPI
				var ckpiId = data.kpiIds.split(",");
				$.post("/plot/getPlotByModuleId.do", {
					moduleId: cmoduleType
				}, function (datas) {
					for (var i = 0; i < ckpiId.length; i++) {
						if (cmoduleType == 10) {
							$(".cwatchpointData").hide();
							for (var j = 0; j < datas.length; j++) {
								if (datas[j].id == ckpiId[i]) {
									datas[j].checked = 1;
								}
							}
						} else {
							$(".cwatchpointData").show();
							for (var j = 0; j < datas.length; j++) {
								if (datas[j].id == ckpiId[i]) {
									datas[j].checked = 1;
								}
							}
						}
					}
					ctypeKpi(datas);
				}, "json");
			}
		});
	});

	/*切换推送类型
		  类型为kpi时，服务端ip和客户端ip隐藏
		  类型为通信对时，服务端ip和客户端ip显示，并且下方表格内容显示不同
	*/
	$("#carchivedType").off().change(function () {
		var carchivedTypes = $(this).val();
		if (carchivedTypes == "1") {
			$(".cdataIpAll").show();
			$(".ccolumn").show();
			$(".clist").hide();
			$.post("/commpair/getCommpairListColumn.do", null, function (cdata) {
				for (var i = 0; i < ckpiId.length; i++) {
					for (var j = 0; j < cdata.length; j++) {
						if (cdata[j].columnen == ckpiId[i]) {
							cdata[j].checked = 1;
						}
					}
				}
				cqueue(cdata);
			}, "json");
		} else {
			$(".cdataIpAll").hide();
			$(".clist").show();
			$(".ccolumn").hide();
			$.post("/plot/getPlotByModuleId.do", {
				moduleId: data.moduleType
			}, function (datas) {
				for (var i = 0; i < ckpiId.length; i++) {
					for (var j = 0; j < datas.length; j++) {
						if (datas[j].id == ckpiId[i]) {
							datas[j].checked = 1;
						}
					}
				}
				ctypeKpi(datas);
			}, "json");
		}
	});

	//确定按钮
	$("#dataDeleteSure").off().click(function () {
		console.log(kpiNames);
		var _this = $(this).button("loading");
		var paramFrom = null;
		var kpiIdsData = null;
		var dataTime = 10;
		var brokerIp = $("#cdataIp").val();
		var port = $("#cdataPort").val();
		var topic = $("#cdataTopic").val();
		var types = $("#cdataArchived option:checked").text();
		var moduleType = $("#cdataModuleType option:checked").val();
		var busiId = $("#cdataOpration option:checked").val();
		var watchpointId = $("#cdataWatchpoint option:checked").val();
		var pushType = $("#carchivedType").val();
		var serverIp = $("#cipServer").val();
		var clientIp = $("#cipUser").val();

		var regex = /^[0-9]*$/;
		if (!regex.test(port) || port == "" || port == null) {
			jeBox.alert('端口不能为空且必须输入0以上的数字');
			_this.button("reset");
			return;
		}

		//ip、端口、主题不能为空
		if (brokerIp == "" || brokerIp == null || topic == "" || topic == null) {
			jeBox.alert('IP、主题不能为空');
			_this.button("reset");
			return;
		}


		if ($("#archivedType").val() == "0") {
			// 推送类型为kpi时的列名称处理
			if (kpiNames.length == 0) {
				kpiIdsDatas = 0;    //kpi没有选择时，为全部传值为0
			} else {
				for (var h = 0; h < kpiNames.length; h++) {
					var kpiNamesData = kpiNames[h].split(":")[0];
					var kpiIdsData = kpiIdsData + kpiNamesData + ",";
				}
				kpiIdsDatas = kpiIdsData.substring(4, kpiIdsData.length - 1);
			}
			paramFrom = {
				pushType: pushType,
				granularity: dataTime,
				type: types,
				brokerIp: brokerIp,
				port: port,
				moduleType: moduleType,
				busiId: busiId,
				watchpointId: watchpointId,
				kpiIds: kpiIdsDatas,
				topic: topic
			};
		} else {
			if (!isValidIP(serverIp) || !isValidIP(clientIp)) {
				jeBox.alert('服务端、客户端需输入正确的IP');
				_this.button("reset");
				return;
			} 

			// 推送类型为通信对时的kpi处理
			if (colmunNames.length == 0) {
				jeBox.alert('请勾选至少一项列名称');
				_this.button("reset");
				return false;
			} else {
				for (var h = 0; h < colmunNames.length; h++) {
					var colmunNamesData = colmunNames[h];
					var colmunIdsData = colmunIdsData + colmunNamesData + ",";
				}
				colmunIdsDatas = colmunIdsData.substring(9, colmunIdsData.length - 1);
			}

			paramFrom = {
				pushType: pushType,
				granularity: dataTime,
				type: types,
				brokerIp: brokerIp,
				port: port,
				moduleType: moduleType,
				busiId: busiId,
				watchpointId: watchpointId,
				kpiIds: colmunIdsDatas,
				topic: topic,
				serverIp: serverIp,
				clientIp: clientIp
			};
		}
		if (kpiNames.length == 0) {
			kpiIdsDatas = 0;    //kpi没有选择时，为全部传值为0
		} else {
			for (var h = 0; h < kpiNames.length; h++) {
				var kpiNamesData = kpiNames[h].split(":")[0];
				var kpiIdsData = kpiIdsData + kpiNamesData + ",";
				kpiIdsDatas = kpiIdsData.substring(4, kpiIdsData.length - 1);
			}
		}


		paramFrom = {
			granularity: dataTime,
			type: types,
			brokerIp: brokerIp,
			port: port,
			moduleType: moduleType,
			busiId: busiId,
			watchpointId: watchpointId,
			kpiIds: kpiIdsDatas,
			topic: topic,
			id: changeId
		};
		//请求
		$.ajax({
			url: "/dataPush/updateDataPush.do",
			type: "post",
			async: false,
			data: paramFrom,
			dataType: "json",
			success: function (data) {
				if (data == true) {
					$.ptcsBSTabRefresh("DataSet");
					_this.button("reset");
					$(".dataDeleteContent").hide();
				} else {
					jeBox.alert('x修改失败，请稍后再试');
					_this.button("reset");
					return;
				}
			}
		});
	});

}
/**通信对列表展示*/
function cqueue(data) {
	var str;
	$(".column-content #tableColumn").empty();
	$(".ccolumn-content #ctableColumn").empty();
	for (var i = 0; i < data.length; i++) {
		if (data[i].typeId == "13") {
			if (data[i].checkeds == 1) {
				str = '<td><input checked=checked  name="cqueuelist" data-columnen="' + data[i].columnen + '"  type="checkbox" style="opacity: 1;"></td>';
			} else {
				str = '<td><input name="cqueuelist" data-columnen="' + data[i].columnen + '"  type="checkbox" style="opacity: 1;"></td>';
			}
		} else {
			str = '<td></td>';
		}
		trs = $('<tr>' +
			'<td>' + data[i].columnzh + '</td>' + str +
			'</tr>');
		$(".ccolumn-content #ctableColumn").append(trs);
	}
	//保存被选中的kpi
	$(".dataDeleteContent input[name='cqueuelist']").click(function () {
		if ($(this).is(":checked") == true) {
			console.log($(this));
			console.log(this.dataset.columnen);
			if ($(".ccolumn-content #ctableColumn input[name='cqueuelist']").is(":checked")) {
				colmunNames.push(this.dataset.columnen);
				colmunNames = $.unique(colmunNames);
			}
		}
		if ($(".ccolumn-content #ctableColumn input[name='cqueuelist']:checkbox:checked").length == 0) {
			for (var i = 0; i < colmunNames.length; i++) {
				var colmunId = colmunNames[i];
				if (colmunId == this.dataset.columnen) {
					colmunNames.splice($(this));
				}
			}
			return;
		}
	});
}

/**
  * 所有kei分类
  * @param {} data 所有kpi数据 <br>
  * 
*/
function ctypeKpi(data) {
	var str, classId, trs;
	var groupName = []; //标题数组  ["告警类","时间类","流量类","会话类","数据包类","其他"]
	$(".list-content-c #table-listChange").empty();
	$(".list-content #table-list").empty();
	data.forEach(function (item, index) {
		if (item.groupName == "告警类") {

		} else {
			if (groupName.indexOf(item.groupName) == -1) {
				groupName.push(item.groupName);
				switch (item.groupName) {
					case "流量类":
						classId = "flowId";
						break;
					case "会话类":
						classId = "huihuaId";
						break;
					case "数据包类":
						classId = "dataId";
						break;
					case "时间类":
						classId = "timeId";
						break;
					case "其他":
						classId = "otherId";
						break;
				}
				trs = $('<tr class="text-center tr-classc" id="' + classId + '"  style="background: rgba(0,0,0,.55);"><td colspan="3" style="font-size: 14px;color: #fff;">' + item.groupName + '</td></tr>');
				$(".list-content-c #table-listChange").append(trs);
			}
		}
	});
	for (var i = 0; i < data.length; i++) {
		if (data[i].number == true) {
			if (data[i].checked == 1) {
				str = '<td><input checked=checked name="test"  value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
			} else {
				str = '<td><input  name="test"  value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';
			}

		} else {
			str = '<td></td>';
		}
		trs = $('<tr><td>' + data[i].name + '</td>' + str + '</tr>');
		switch (data[i].groupName) {
			case "时间类":
				if ($(".tr-classc").eq($(".tr-classc").index($("#timeId")) + 1).length) {
					$(".tr-classc").eq($(".tr-classc").index($("#timeId")) + 1).before(trs);
				} else {
					$(".list-content-c #table-listChange").append(trs);
				}
				break;
			case "流量类":
				if ($(".tr-classc").eq($(".tr-classc").index($("#flowId")) + 1).length) {
					$(".tr-classc").eq($(".tr-classc").index($("#flowId")) + 1).before(trs);
				} else {
					$(".list-content-c #table-listChange").append(trs);
				}
				break;
			case "会话类":
				if ($(".tr-classc").eq($(".tr-classc").index($("#huihuaId")) + 1).length) {
					$(".tr-classc").eq($(".tr-classc").index($("#huihuaId")) + 1).before(trs);
				} else {
					$(".list-content-c #table-listChange").append(trs);
				}
				break;
			case "数据包类":
				if ($(".tr-classc").eq($(".tr-classc").index($("#dataId")) + 1).length) {
					$(".tr-classc").eq($(".tr-classc").index($("#dataId")) + 1).before(trs);
				} else {
					$(".list-content-c #table-listChange").append(trs);
				}
				break;
			case "其他":
				if ($(".tr-classc").eq($(".tr-classc").index($("#otherId")) + 1).length) {
					$(".tr-classc").eq($(".tr-classc").index($("#otherId")) + 1).before(trs);
				} else {
					$(".list-content-c #table-listChange").append(trs);
				}
				break;
		}
	}
	//保存被选中的kpi
	$(".dataDeleteContent input[name='test']").click(function () {
		if ($(this).is(":checked") == true) {
			if ($(".list-content-c #table-listChange input[name='test']").is(":checked")) {
				kpiNames.push($(this).val());
				kpiNames = $.unique(kpiNames);
				console.log(kpiNames);
			}
		}
		if ($(".list-content-c #table-listChange input[name='test']:checkbox:checked").length == 0) {
			for (var i = 0; i < kpiNames.length; i++) {
				var plotId = kpiNames[i];
				if (plotId == $(this).val().split(":")[0]) {
					kpiNames.splice($(this));
				}
			}
			return;
		}
	});
}

//取消按钮
$("#dataDeletecancel").click(function () {
	$(".dataDeleteContent").hide();
});