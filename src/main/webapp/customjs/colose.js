$(document).ready(function(){
	//  回车事件禁用
    $(document).keydown(function(event){
          switch(event.keyCode){
             case 13:return false; 
             }
      });
})