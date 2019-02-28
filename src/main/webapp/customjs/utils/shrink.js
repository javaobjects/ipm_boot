/**
 * 收缩工具
 * 页面引入: <script src="customjs/utils/shrink.js"></script>
 * 工具使用: $.customEleShrink({ 
 *            domId: 需要隐藏的元素ID
 *            showFun: 展示回调，自定义后替换默认方法
 *            hideFun: 隐藏回调，自定义后替换默认方法
 *        });
 */
 
$(function() {
	/**
	 * 主函数
	 * @param domId 收缩元素
	 * @param sFun 展示回调
	 * @param hFun 隐藏回调
	 */
    var shrink = function(domId, sFun, hFun) {
    	var _t = this;
    	_t.s_obj = $("#" + domId);
        _t.init().addEvent(sFun, hFun);
    };
    
    /**
     * 初始化
     */
    shrink.prototype.init = function() {
        var j_btn_left = $('<button class="btn btn-xs" title="隐藏"><span class="glyphicon glyphicon-chevron-left"></span></button>'),
            j_btn_right = $('<button class="btn btn-xs" title="展示"><span class="glyphicon glyphicon-chevron-right"></span></button>');
        j_btn_left.css({
        	background: 'rgba(0,0,0,0)',
            position: 'absolute',
            color:'yellow',
            top: 8,
            right: 0
        });
        j_btn_right.css({
            background: 'rgba(0,0,0,0)',
            color:'yellow',
            position: 'absolute',
            top: 8,
            left: -13,
            display: 'none'
        });
        this.s_obj.append(j_btn_left);
        this.s_obj.parent().css('position', 'relative').append(j_btn_right);
        this.j_btn_left = j_btn_left;
        this.j_btn_right = j_btn_right;
        
        return this;
    };
    
    /**
     * 添加事件
     * @param sFun 展示事件
     * @param hFun 隐藏事件
     */
    shrink.prototype.addEvent = function(sFun, hFun) {
        var _t = this;
        this.j_btn_left.on('click', function() {
            _t.s_obj.hide();
            _t.j_btn_right.show();
            if (hFun) {
                hFun();
            } else {
                _t.defHideFun(_t);
            }
        });
        this.j_btn_right.on('click', function() {
            $(this).hide();
            _t.s_obj.show();
            if (sFun) {
                sFun();
            } else {
                _t.defShowFun(_t);
            }
        });
        
        return this;
    };
    
    /**
     * 默认展示事件
     */
    shrink.prototype.defShowFun = function(T) {
    	T.s_obj.next().removeClass('col-md-12').addClass('col-md-10');
    };
    
    /**
     * 默认隐藏事件
     */
    shrink.prototype.defHideFun = function(T) {
        T.s_obj.next().removeClass('col-md-10').addClass('col-md-12');
    };
    
    /**
     * 接入jQuery
     */
    $.extend({
        customEleShrink: function(cf) {
        	if (cf.domId) {
                new shrink(cf.domId, cf.showFun, cf.hideFun);
        	} else {
        	    console.log("Shrink no property DomId !");
        	}
        }
    });
});
 