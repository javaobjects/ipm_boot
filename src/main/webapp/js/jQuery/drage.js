/**
 * Created by yanbo on 2017/10/18.
 */
$(function(){
    $(document).mousemove(function(e) {
        if (!!this.move) {
            var posix = !document.move_target ?
            {'x': 0, 'y': 0} :
                document.move_target.posix,
                callback = document.call_down || function() {
                        if($(this.move_target).hasClass("titleDrage")){
                            $(this.move_target).parent().css({
                                'top': e.pageY - posix.y>0?e.pageY - posix.y:0,
                                'left': e.pageX - posix.x>-0?e.pageX - posix.x:-0
                            });
                        }else {
                            $(this.move_target).css({
                                'top': e.pageY - posix.y>0?e.pageY - posix.y:0,
                                'left': e.pageX - posix.x>0?e.pageX - posix.x:0
                            });
                        }
                    };
            callback.call(this, e, posix);
        }
    }).mouseup(function(e) {
        if (!!this.move) {
            var callback = document.call_up || function(){};
            callback.call(this, e);
            $.extend(this, {
                'move': false,
                'move_target': null,
                'call_down': false,
                'call_up': false
            });
        }
    });
});



