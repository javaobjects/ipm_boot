/**
 * 系统设置-数据推送功能
 */
$(function () {
	$(".dataContent").hide();
	$(".dataDeleteContent").hide();
	//定义全局变量，保存kpi选中所有input框
	var kpiNames = [];
	var colmunNames = [];
	var selectRow = null,
		selectRowId = null;

	var columns = [{
		field: "type",
		title: "推送方式",
		sortable: true
	}, {
		field: "brokerIp",
		title: "IP",
		sortable: true
	}, {
		field: "port",
		title: "端口",
		sortable: true
	}, {
		field: "topic",
		title: "主题",
		sortable: true
	}, {
		field: "granularity",
		title: "时间粒度",
		sortable: true
	}];
	$.ptcsBSTable("DataSet", "/dataPush/getAll.do", null, {
		columns: columns,
		ipm_title: "数据导出",
		ipm_shrink: true,
		pageSize: 10,
		rowStyle: function (row, i) {
			var cla = {};
			if (i == 0) {
				cla.classes = "custom-row-style";
				selectRow = row;
				selectRowId = row.id;
			}
			return cla;
		},
		onClickRow: function (row, tr) {
			$("#DataSet > tbody > .custom-row-style").removeClass("custom-row-style");
			$(tr).addClass("custom-row-style");
			selectRow = row;
			selectRowId = row.id;
		},
		ipm_toolbar: [{
			name: "新增",
			type: "plus",
			call: function (e) {
				$(".column").hide();
				$(".dataContent").show();
				$(".dataDeleteContent").hide();
				$(".dataIpAll").hide();
				$("#dataIp").val("");
				$("#dataPort").val("");
				$("#dataTopic").val("");
				$("#dataModuleType").val('10');
				$("#archivedType").val('0');
				$(".watchpointData").hide();
				$("#dataOpration").val("0");
				$.ajax({
					url: "/watchpointController/getFindAll.do",
					type: "post",
					async: false,
					dataType: "json",
					success: function (data) {
						typeKpi(10);
					}
				});
			}
		}, {
			name: "修改",
			type: "edit",
			call: function (row, e) {
				if (selectRow == null) {
					jeBox.alert('请先添加推送业务');
					return;
				}
				changeData(selectRowId);
			}
		}, {
			name: "删除",
			type: "remove",
			call: function (e) {
				if (selectRow == null) {
					jeBox.alert('请先添加推送业务');
					return;
				}
				$("#dataDelete").modal("show");
				$(".dataContent").hide();
				$(".dataDeleteContent").hide();
			}
		}]
	});
	//删除 
	$("#btn-dataDelete").click(function () {
		$.ajax({
			url: "/dataPush/delDataPush.do",
			type: "post",
			data: {
				id: selectRowId
			},
			dataType: "json",
			success: function (data) {
				if (data == true) {
					$.ptcsBSTabRefresh("DataSet");
					selectRow = null;
				} else {
					jeBox.alert('删除失败，请稍后再试');
				}
			}
		});
	});
	/**
	 * 添加下拉框信息
	 * @domId String 下拉框ID
	 * @data {} 数据
	 */
	var addOption = function (domId, data) {
		var option = "";
		option += "<option value='0'>全部</option>";
		for (var i = 0, len = data.length; i < len; i++) {
			option += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
		}
		$("#" + domId).append(option);
	};

	var addOptions = function (domId, data) {
		var option = "";
		for (var i = 0, len = data.length; i < len; i++) {
			if (data[i].id == "1" || data[i].id == "4" || data[i].id == "5" || data[i].id == "6" || data[i].id == "7" || data[i].id == "8" || data[i].id == "9") {

			} else {
				option += "<option value=" + data[i].id + ">" + data[i].namezh + "</option>";
			}
		}
		$("#" + domId).append(option);
	};

	/*观察点展示*/
	$.post("/watchpointController/getFindAll.do", null, function (data) {
		addOption("dataWatchpoint", data);
	}, "json");

	/*模块类型展示*/
	$.post("/authorizeModuleController/getFindAll.do", null, function (data) {
		addOptions("dataModuleType", data);
		$(".watchpointData").hide();
		$.post("/watchpointController/getFindAll.do", null, function (data) {
			addOption("dataOpration", data);
			typeKpi(10);
		}, "json");
	}, "json");


	//切换模块类型更换各自的kpi及业务名称
	$("#dataModuleType").off().change(function () {
		var moduleType = $(this).val();
		switch (moduleType) {
			case "10":
				$("#dataOpration").empty();
				$(".watchpointData").hide();
				$.post("/watchpointController/getFindAll.do", null, function (data) {
					addOption("dataOpration", data);
					typeKpi(moduleType);
				}, "json");
				break;
			case "11":
				$("#dataOpration").empty();
				$(".watchpointData").show();
				$.post("/client/getClient.do", null, function (data) {
					addOption("dataOpration", data);
					typeKpi(moduleType);
				}, "json");
				break;
			case "12":
				$("#dataOpration").empty();
				$(".watchpointData").show();
				$.post("/serverManagement/getAllServerSide.do", null, function (data) {
					addOption("dataOpration", data);
					typeKpi(moduleType);
				}, "json");
				break;
		}
	});

	/*切换推送类型
		  类型为kpi时，服务端ip和客户端ip隐藏
		  类型为通信对时，服务端ip和客户端ip显示，并且下方表格内容显示不同
	*/
	$("#archivedType").off().change(function () {
		var archivedTypes = $(this).val();
		if (archivedTypes == "1") {
			$(".dataIpAll").show();
			$(".column").show();
			$(".list").hide();
			queue();
			
		} else {
			$(".dataIpAll").hide();
			$(".list").show();
			$(".column").hide();
		}
	});

	/*端口验证*/
	function isPort(ports) {
		var parten = /^(\d)+$/g;
		if (parten.test(ports) && parseInt(ports) <= 65535 && parseInt(ports) > 0) {
			return true;
		} else {
			return false;
		}
	}
	/*ip验证（不包含IPv4 和 IPv6）*/
	function isValidIP(ips) {
		var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
		return reg.test(ips);
	}
	/**
	 * 新增报表确定按钮
	 */
	$("#dataSure").click(function () {
		var _this = $(this).button("loading");
		var paramFrom = null;
		var kpiIdsData = null;
		var dataTime = 10;
		var brokerIp = $("#dataIp").val();
		var port = $("#dataPort").val();
		var topic = $("#dataTopic").val();
		var types = $("#dataArchived option:checked").text();
		var moduleType = $("#dataModuleType option:checked").val();
		var busiId = $("#dataOpration option:checked").val();
		var watchpointId = $("#dataWatchpoint option:checked").val();
		var pushType = $("#archivedType").val();
		var serverIp = $("#ipServer").val();
		var clientIp = $("#ipUser").val();

		if (!isPort(port)) {
			jeBox.alert('端口必须是0到65535内的整数');
			_this.button("reset");
			return;
		}
		if (!isValidIP(brokerIp)) {
			jeBox.alert('需输入正确的IP');
			_this.button("reset");
			return;
		}

		//ip、端口、主题不能为空
		if (brokerIp == "" || brokerIp == null || topic == "" || topic == null || port == "" || port == null) {
			jeBox.alert('IP、端口、主题不能为空');
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


		$.ajax({
			url: "/dataPush/addDataPush.do",
			type: "post",
			data: paramFrom,
			dataType: "json",
			success: function (data) {
				if (data == true) {
					$.ptcsBSTabRefresh("DataSet");
					_this.button("reset");
					$(".dataContent").hide();
				} else {
					jeBox.alert('添加失败，请稍后再试');
					_this.button("reset");
					return;
				}
			}
		});
	});

	/**
	 * 取消按钮
	 */
	$("#datacancel").click(function () {
		$("#dataIp").val("");
		$("#dataPort").val("");
		$("#dataTopic").val("");
		$("#dataModuleType").val('10');
		$("#archivedType").val('0');
		$(".watchpointData").hide();
		$("#dataOpration").val("0");
		$.ajax({
			url: "/watchpointController/getFindAll.do",
			type: "post",
			async: false,
			dataType: "json",
			success: function (data) {
				typeKpi(10);
			}
		});
		kpiNames = [];
		$(".dataContent").hide();
	});

	/**通信对列表展示*/
	function queue() {
		$.post("/commpair/getCommpairListColumn.do", null, function (data) {
			var str;
			$(".column-content #tableColumn").empty();
			for (var i = 0; i < data.length; i++) {
				if (data[i].typeId == "13") {
					str = '<td><input name="queuelist" data-columnen="' + data[i].columnen + '"  type="checkbox" style="opacity: 1;"></td>';
				} else {
					str = '<td></td>';
				}
				trs = $('<tr>' +
					'<td>' + data[i].columnzh + '</td>' + str +
					'</tr>');
				$(".column-content #tableColumn").append(trs);
			}
			//保存被选中的kpi
			$(".dataContent input[name='queuelist']").click(function () {
				if ($(this).is(":checked") == true) {
					console.log($(this));
					console.log(this.dataset.columnen);
					if ($(".column-content #tableColumn input[name='queuelist']").is(":checked")) {
						colmunNames.push(this.dataset.columnen);
						colmunNames = $.unique(colmunNames);
					}
				}
				if ($(".column-content #tableColumn input[name='queuelist']:checkbox:checked").length == 0) {
					for (var i = 0; i < colmunNames.length; i++) {
						var colmunId = colmunNames[i];
						if (colmunId == this.dataset.columnen) {
							colmunNames.splice($(this));
						}
					}
					return;
				}
			});
		}, "json");
	}

	/**
	 * kpi列表展示
	 * @param {string} moduleId 模块id <br>
	 *        {string} plotId 图标类型id <br>
	 */
	function typeKpi(moduleId) {
		$.post("/plot/getPlotByModuleId.do", {
			moduleId: moduleId
		}, function (data) {
			var str,
				str1,
				groupName = [], //标题数组  ["告警类","时间类","流量类","会话类","数据包类","其他"]
				classId,
				trs;
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
						trs = $('<tr class="text-center tr-class" id="' + classId + '"  style="background: rgba(0,0,0,.55);"><td colspan="3" style="font-size: 14px;color: #fff;">' + item.groupName + '</td></tr>');
						$(".list-content #table-list").append(trs);
					}
				}
			});
			for (var i = 0; i < data.length; i++) {
				if (data[i].number == true) {
					str = '<td><input name="litlisChart" data-id="' + data[i].id + '" data-moduleId="' + data[i].moduleId + '" ' +
						'data-plotId="' + data[i].id + '" data-plotTypeId="' + data[i].types[0].id + '"' +
						'value="' + data[i].id + ':' + data[i].types[0].id + '" type="checkbox" style="opacity: 1;"></td>';

				} else {
					str = '<td></td>';
				}
				trs = $('<tr>' +
					'<td>' + data[i].name + '</td>' + str +
					'</tr>');
				switch (data[i].groupName) {
					case "时间类":
						if ($(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).length) {
							$(".tr-class").eq($(".tr-class").index($("#timeId")) + 1).before(trs);
						} else {
							$(".list-content #table-list").append(trs);
						}
						break;
					case "流量类":
						if ($(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).length) {
							$(".tr-class").eq($(".tr-class").index($("#flowId")) + 1).before(trs);
						} else {
							$(".list-content #table-list").append(trs);
						}
						break;
					case "会话类":
						if ($(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).length) {
							$(".tr-class").eq($(".tr-class").index($("#huihuaId")) + 1).before(trs);
						} else {
							$(".list-content #table-list").append(trs);
						}
						break;
					case "数据包类":
						if ($(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).length) {
							$(".tr-class").eq($(".tr-class").index($("#dataId")) + 1).before(trs);
						} else {
							$(".list-content #table-list").append(trs);
						}
						break;
					case "其他":
						if ($(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).length) {
							$(".tr-class").eq($(".tr-class").index($("#otherId")) + 1).before(trs);
						} else {
							$(".list-content #table-list").append(trs);
						}
						break;
				}

			}
			//保存被选中的kpi
			$(".dataContent input[name='litlisChart']").click(function () {
				if ($(this).is(":checked") == true) {
					if ($(".list-content #table-list input[name='litlisChart']").is(":checked")) {
						kpiNames.push($(this).val());
						kpiNames = $.unique(kpiNames);
					}
				}
				if ($(".list-content #table-list input[name='litlisChart']:checkbox:checked").length == 0) {
					for (var i = 0; i < kpiNames.length; i++) {
						var plotId = kpiNames[i];
						if (plotId == $(this).val().split(":")[0]) {
							kpiNames.splice($(this));
						}
					}
					return;
				}
			});
		}, "json");
	}

});