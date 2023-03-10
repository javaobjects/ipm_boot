/* DaTouWang URL: www.datouwang.com */
var enjoyhint_instance = new EnjoyHint({
	onStart: function(){
		$.post("/watchpointController/getUserConfigureBeanByKey.do", { key: "cockpitmanna" }, function(v) {
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
                key:"cockpitmanna",
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
  	'next .glyphicon-bell' : '鼠标点击告警按钮弹出告警列表。',
  	'nextButton' : {className: "myNext", text: "下一步"},
  	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
  	'next #oberSetCustomToolbar' : "后几个按钮分别为添加业务，修改业务，及删除业务",
  	'radius': 70,
  	'showSkip' : false,
  	'nextButton' : {className: "myNext", text: "知道了!"}
  }
];

enjoyhint_instance.set(enjoyhint_script_steps);
enjoyhint_instance.run();