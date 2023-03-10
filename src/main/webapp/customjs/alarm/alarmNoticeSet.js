/**
 * 告警通知设置
 */
$(function() {
    var selectRow = null;
    $.ptcsBSTable('alarmNoticeSet','/alarmNoticeSet/selectByUserId.do',null,{
        pageSize: 10,
        columns:[ 
        	{ field:'name', title:'告警级别' },
            { field:'state', title:'通知状态',
                formatter: function (value, row, index) {
                    if (value) {
                    	return '开启';
                    }
                    
                    return '关闭';
                }
            },
            { field:'restrain', title:'通知抑制(分钟)' },
            { field:'email', title:'邮件通知', sortable: true },
            { field:'message', title:'短信通知', sortable: true },
            { field:'im', title:'IM通知', sortable: true },
            { field:'desp', title:'备注', sortable: true }
        ],
        ipm_title: '告警通知设置',
        ipm_shrink: true,
        ipm_show: true,
        ipm_column_save: true,
        rowStyle: function (row, i) {
            var cla = {};
            if (i == 0) {
                cla.classes = 'custom-row-style';
                selectRow = row;
            }
            return cla;
        },
        ipm_toolbar:[
            {
                name: '修改',
                type: 'edit',
                call: function (e) {
                    $.JeditRowModal('#alarmNoticeSet', '/alarmNoticeSet/selectByUserId.do',
                        ['name', 'state', 'restrain', 'email', 'message', 'im', 'desp'],
                        ['告警级别', '通知状态', '通知抑制', '邮件通知','短信通知', 'IM通知', '备注'],
                        'alarmNoticeSet', null, 'alarmNoticeSet');
                }
            }
        ],
        onClickRow: function (row, tr) {
            $('#alarmNoticeSet > tbody > .custom-row-style').removeClass();
            $(tr).addClass('custom-row-style');
            selectRow = row;
        },
        onLoadSuccess:function () {},
        onLoadError:function () {}
    });
    
    $('#changetableRow-modal').on('click', '#alarmNoticeUpdateEmailTest', null, function(d) {
    	var prev = $(this).prev();
    	if ($.trim(prev.val())) {
    		var er = jeBox.loading(2, '正在发送邮件，请稍候');
    		$.post('/alarmNoticeSet/checkEmail.do', { emails: prev.val() }, function(d) {
    			jeBox.close(er);
				jeBox.alert(d.msg);
    		});
    	} else {
			jeBox.alert('邮箱不能为空');
    	}
    });
    
    $('#btn-changetableRow').click(function() {
    	var form = $('#changetableRow-modal .modal-body').find('input, select');
    	var param = {};
    	for (var i = 0, len = form.length; i < len; i ++) {
    		var tmp = $(form[i]);
    		if (tmp.attr('name'))
    			param[tmp.attr('name')] = tmp.val();
    	}
    	param.id = selectRow.id;
    	
		var numRex = /^[1-9][0-9]+$/gi;
		if (numRex.test(param.restrain)) {
	    	$.post('/alarmNoticeSet/updateById.do', param, function(d) {
	    		if (d.state == 1) {
	                $('#changetableRow-modal').modal('hide');
	    			$.ptcsBSTabRefresh('alarmNoticeSet');
	    		} else {
	    			jeBox.alert('修改失败');
	    		}
	    	});
		} else {
			jeBox.alert('通知抑制格式错误，只能输入数字');
		}
    });
});