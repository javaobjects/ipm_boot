/* DaTouWang URL: www.datouwang.com */
var enjoyhint_instance = new EnjoyHint({
	 onStart: function(){
		 $.post("/watchpointController/getUserConfigureBeanByKey.do", { key: "netcockpit" }, function(v) {
			 if(v == 1){
		    	 $(".enjoyhint").remove();
			 }
		});
	 },
	 onEnd:function(){
		 $.ajax({
	            url:"/watchpointController/updateUserConfigureByKey.do",
	            type:"post",
	            data:{
	                key:"netcockpit",
	                value:"1"
	            },
	            dataType:"text",
	            success:function(data){
	            }
	    })
	 }
});

var enjoyhint_script_steps = [
  {
  	'next .navbar-brand' : '点击此处建立监控对象。',
  	'nextButton' : {className: "myNext", text: "下一步"},
  	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
  	'next #xpm_select' : "点击下拉框，选择监控内容设备",
  	'nextButton' : {className: "myNext", text: "下一步"},
  	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
  	'next #obser_select' : '点击下拉框，选择观察点',
  	'nextButton' : {className: "myNext", text: "下一步"},
  	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
	'next #busNamecn_select' : '点击下拉框，选择监控类型',
	'nextButton' : {className: "myNext", text: "下一步"},
	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
	'next .linedraw' : '双击列表以及以下所有图形，进入告警或者通信对页面',
  	'radius': 70,
  	'showSkip' : false,
  	'nextButton' : {className: "myNext", text: "知道了!"}
   }
];

enjoyhint_instance.set(enjoyhint_script_steps);
enjoyhint_instance.run();