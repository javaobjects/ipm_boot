/**
    * 注销弹出
*/
$(function(){
    $(".logout-btn").click(function () {
        $.ajax({
            url: "/user/logout.do",
            method: "POST",
            data: {},
            dataType:"json",
            beforeSend:function (XMLHttpRequest) {},
            success:function(data,textStatus,XMLHttpRequest){
                if(+data.success){
                    window.location = "/login.html";
                }else {
                    jeBox.alert("注销不成功");
                }
            },
            error:function (XMLHttpRequest,textStatus,errorThorwn) {
                console.error(XMLHttpRequest);
                console.error(textStatus);
                console.error(errorThorwn);
            },
            complete:function (XMLHttpRequest,textStatus) {}
        })
    });
});