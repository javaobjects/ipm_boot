/**
 * Created by yanb on 2017/10/24.
 * <script src="js/jquery.min.js"></script>
 * <script src="js/draw.js"></script>
 * <script src="js/timeStamp.js"></script>
 * 导航条时间回溯功能
 */
$(function(){
    if(location.pathname.split(".")[0].replace(/\//,"")=="jnode"){
        var start = {
            format: 'YYYY-MM-DD hh:mm:ss',
            isinitVal:true,
            initDate:[{mm:"-1"},true],
            okfun: function(obj){
                endDates();
            }
        };
        var end = {
            format: 'YYYY-MM-DD hh:mm:ss',
            isinitVal:true,
            initDate:[{},true],
            okfun: function(obj){
            }
        };
    }else {
        var start = {
            format: 'YYYY-MM-DD hh:mm:ss',
            //maxDate: $.nowDate({DD:0}),
            okfun: function(obj){
                //end.minDate = obj.val; //开始日选好后，重置结束日的最小日期
                endDates();
            }
        };
        var end = {
            format: 'YYYY-MM-DD hh:mm:ss',
            //maxDate: $.nowDate({DD:0}),
            okfun: function(obj){
                //start.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
            }
        };
    }
//这里是日期联动的关键
    function endDates() {
        //将结束日期的事件改成 false 即可
        end.trigger = false;
        $("#inpend").jeDate(end);
    }
    $('#inpstart').jeDate(start);
    $('#inpend').jeDate(end);
    $('a[data-drawer="times"]').click(function(){
        $('#inpstart').val("");
        $('#inpend').val("");
    });
});