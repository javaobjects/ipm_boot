/**
 * Created by yanb on 2018/1/30.
 */
;$(function(){
    $.ajax({
        url:"/desUtil/getDesUtilKey.do",
        type:"post",
        data:"",
        dataType:"json",
        success:function(data){
            console.log(data);
        }
    })
});