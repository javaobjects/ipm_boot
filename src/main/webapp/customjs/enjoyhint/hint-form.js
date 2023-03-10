/* DaTouWang URL: www.datouwang.com */
var enjoyhint_instance = new EnjoyHint({
	onStart: function(){
	$.post("/watchpointController/getUserConfigureBeanByKey.do", { key: "formplan" }, function(v) {
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
                key:"formplan",
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
  	'next .formnext' : '点击模板功能先建立报表模板',
  	'nextButton' : {className: "myNext", text: "下一步"},
  	'skipButton' : {className: "mySkip", text: "不了!"}
  },
  {
  	'next #fromPlanCustomToolbar' : "分别点击按钮，增加、修改、删除报表计划",
  	'radius': 70,
  	'showSkip' : false,
  	'nextButton' : {className: "myNext", text: "知道了!"}
  }
];

enjoyhint_instance.set(enjoyhint_script_steps);
enjoyhint_instance.run();