/**
  * 头部搜索功能
 */
$(function() {
	/**
	 * 添加下拉框信息
	 * @domId String 下拉框ID
	 * @data {} 数据
	 */
    var addOption = function(domId, data) {
        var option = "";
        for(var i = 0, len = data.length; i < len; i++) {
            option += "<option value=" + data[i].id + ">" + data[i].name + "</option>";
        }
        $("#" + domId).append(option);
    };
    
    /**
     * 设置时间 秒级时间戳
     * @starttimes number 开始时间
     * @endtimes number 结束时间 
     */
    var searchFormTimes = function(starttimes, endtimes) {
    	$("#searchstart").val("");
    	$("#searchend").val("");
        if(starttimes && endtimes) {
            var startArr = $.timeStampDate(urlParams.searchstart, "YYYY MM DD hh mm ss").split(" "),
                endArr = $.timeStampDate(urlParams.searchend, "YYYY MM DD hh mm ss").split(" ");
            $("#searchstart").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[ { YYYY: startArr[0], MM: startArr[1], DD: startArr[2],
                    hh: startArr[3], mm: startArr[4], ss: startArr[5] }, false]
            });
            $("#searchend").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[ { YYYY: endArr[0], MM: endArr[1], DD: endArr[2],
                    hh: endArr[3], mm: endArr[4], ss: endArr[5] }, false]
            });
        } else {
            $("#searchstart").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[{ mm: -10, ss: -new Date().getSeconds() }, true]
            });
            $("#searchend").jeDate({
                isTime: true,
                isClear: false,
                isinitVal: true,
                initDate:[{ ss: 00 }, false]
            });
        }
    };
    var searchurlParams = $.getUrlParams(), columns = null, isFirst = true, tabParams = null;
    searchFormTimes(searchurlParams.searchstart, searchurlParams.searchend);
    
    /*XPM数据请求*/
    $.ajax({
 	   url:"/center/getCenterIpInfo.do",
 	   type:"post",
 	   async:false,
 	   dataType:"json",
 	   success:function(data){
 		  addOption("xpmid", data);
 	   }
    });
   // xpm change事件设置观察点的值
    $("#xpmid").change(function(){
    	var xpmId = $("#xpmid").val();
    	if(xpmId == "0"){
    	    $("#watchpointSearch").html("<option value='0'>请选择...</option>");
    	}else {
    		$("#watchpointSearch").html("<option value='0'>请选择...</option>");
    	   $.ajax({
		 	   url:"/watchpointController/getFindAll.do",
		 	   type:"post",
		 	   async:false,
		 	   dataType:"json",
		 	   data: {ipmCenterId:xpmId},
		 	   success:function(data){
		 	   	 addOption("watchpointSearch", data);
		 	   }
           });
    	}
    });
    /**
     * URL，报文，SQL选择填入事件，当url、报文有一个输入，SQL就不能输入，反之亦是
     */
    if ($("#urlSearch").val() != "" || $("#messagessSearch").val() != "") {
		$("#sqlSearch").attr("disabled", true);
	} else {
		$("#sqlSearch").attr("disabled", false);
	}

	if ($("#sqlSearch").val() != "") {
		$("#messagessSearch").attr("disabled", true);
		$("#urlSearch").attr("disabled", true);
	} else {
		$("#messagessSearch").attr("disabled", false);
		$("#urlSearch").attr("disabled", false);
	}
	
	$("#urlSearch").blur(function() {
		var url = $("#urlSearch").val();
		if (url != "") {
			$("#sqlSearch").attr("disabled", true);
		} else {
			$("#sqlSearch").attr("disabled", false);
		}
	});

	$("#messagessSearch").blur(function() {
		var messagess = $("#messagessSearch").val();
		if (messagess != "") {
			$("#sqlSearch").attr("disabled", true);
		} else {
			$("#sqlSearch").attr("disabled", false);
		}
	});
	
	$("#sqlSearch").blur(function() {
		var sqlSearch = $("#sqlSearch").val();
		if (sqlSearch != "") {
			$("#messagessSearch").attr("disabled", true);
			$("#urlSearch").attr("disabled", true);
		} else {
			$("#messagessSearch").attr("disabled", false);
			$("#urlSearch").attr("disabled", false);
		}
	});
	
	$("#urlSearch").focus(function() {
		var url = $("#urlSearch").val();
		var sqlSearch = $("#sqlSearch").val();
		var messagess = $("#messagessSearch").val();
		if(sqlSearch != ""){
			$("#messagessSearch").attr("disabled", true);
			$("#urlSearch").attr("disabled", true);
		}else {
			$("#messagessSearch").attr("disabled", false);
			$("#urlSearch").attr("disabled", false);
		}
	});

	$("#messagessSearch").focus(function() {
		var url = $("#urlSearch").val();
		var sqlSearch = $("#sqlSearch").val();
		var messagess = $("#messagessSearch").val();
		if(sqlSearch != ""){
			$("#messagessSearch").attr("disabled", true);
			$("#urlSearch").attr("disabled", true);
		}else {
			$("#messagessSearch").attr("disabled", false);
			$("#urlSearch").attr("disabled", false);
		}
	});
	
	$("#sqlSearch").focus(function() {
		var url = $("#urlSearch").val();
		var sqlSearch = $("#sqlSearch").val();
		var messagess = $("#messagessSearch").val();
		if(url != "" || messagess != ""){
			$("#sqlSearch").attr("disabled", true);
		}else {
			$("#sqlSearch").attr("disabled", false);
		}
	});
	
	
    /* 搜索提交按钮 */
    $("#searchTimesure").click(function() {
    	var xpmId = $("#xpmid").val();
        var searchStart = $.myTime.DateToUnix($("#searchstart").val());
		var searchend = $.myTime.DateToUnix($("#searchend").val());
		if (searchStart == "" || searchStart < 0) {
			searchStart = 0;
			searchend = 0;
		}
		var seacAll = null;
		var watchpointSearch = $("#watchpointSearch option:selected").val();
		var serveripSearch = $("#serveripSearch").val();
		var clientipSearch = $("#clientipSearch").val();
		var urlSearch = $("#urlSearch").val();
		var messagessSearch = $("#messagessSearch").val();
		if(messagessSearch.length > 200){
		     jeBox.alert("内容不能超过200个字符，请重新输入");
		     return;
		}
		var sqlSearch = $("#sqlSearch").val();
		var searchInput = $("input[type='radio']:checked").val();
    	if (urlSearch == "" && messagessSearch == "" && sqlSearch == "") {
    		var hrefUrl = "commun_queue.html?starttime=" + searchStart + "&endtime=" + searchend;
    		if(xpmId != 0) {
                hrefUrl += "&ipmCenterId=" + xpmId;
    		}
    		if(watchpointSearch != 0) {
                hrefUrl += "&watchpointId=" + watchpointSearch;
    		}
    		if(clientipSearch) {
    		    hrefUrl += "&clientIp=" + clientipSearch;
    		}
    		if(serveripSearch) {
    		    hrefUrl += "&serverIp=" + serveripSearch;
    		}
    		location.href = hrefUrl;
    	} else {
    		if (xpmId == 0) {

			} else {
				seacAll += "&ipmCenterId=" + xpmId;
			}
			
			if (watchpointSearch == 0) {

			} else {
				seacAll += "&watchpointId=" + watchpointSearch;
			}

			if (clientipSearch == "") {

			} else {
				seacAll += "&client=" + clientipSearch;
			}

			if (serveripSearch == "") {

			} else {
				seacAll += "&server=" + serveripSearch;
			}
			//----------------------------------------------------------------------
			if (urlSearch != "") {
				if (seacAll == null) {
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&uos=" + urlSearch;
				} else {
					var strUrlPam = seacAll.substring(4, seacAll.length);
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&uos=" + urlSearch + strUrlPam;
				}
			}
            if (messagessSearch != "") {
				if (seacAll == null) {
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&message=" + messagessSearch;
				} else {
					var strUrlPam = seacAll.substring(4, seacAll.length);
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&message=" + messagessSearch + strUrlPam;
				}
			}
			if (urlSearch != "" && messagessSearch != "") {
				if (seacAll == null) {
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&uos=" + urlSearch + "&message=" + messagessSearch;
				} else {
					var strUrlPam = seacAll.substring(4, seacAll.length);
					location.href = "bssSession.html?starttime=" + searchStart
							+ "&endtime=" + searchend + "&moduleId=4&uos=" + urlSearch  + "&message=" + messagessSearch + strUrlPam;
				}
			}
			if (sqlSearch != "") {
				if (searchInput == "1") {
					if (seacAll == null) {
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=5&uos=" + sqlSearch;
					} else {
						var strUrlPam = seacAll.substring(4, seacAll.length);
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=5&uos=" + sqlSearch + strUrlPam;
					}
				} else if (searchInput == "2") {
					if (seacAll == null) {
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=6&uos=" + sqlSearch;
					} else {
						var strUrlPam = seacAll.substring(4, seacAll.length);
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=6&uos=" + sqlSearch + strUrlPam;
					}
				} else if (searchInput == "3") {
					if (seacAll == null) {
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=7&uos=" + sqlSearch;
					} else {
						var strUrlPam = seacAll.substring(4, seacAll.length);
						location.href = "bssSession.html?starttime=" + searchStart 
						    + "&endtime=" + searchend + "&moduleId=7&uos=" + sqlSearch + strUrlPam;
					}
				}

			}
		}
    });
});

