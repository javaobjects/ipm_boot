/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLoopDelTask
 *创建人:chensq    创建时间:2017年12月20日
 */
package com.protocolsoft.alarm.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.service.AlarmLogService;
import com.protocolsoft.alarm.service.AlarmTriggerService;
import com.protocolsoft.alarm.util.AlarmConstantUtil;

/**
 * @ClassName: AlarmLoopDelTask
 * @Description: 告警删除历史数据线程池
 * @author chensq
 *
 */
@Service
public class AlarmLoopDelTask {
     
    /**
     * @Fields alarmTriggerService : 告警trigger service
     */
    @Autowired
    private AlarmTriggerService alarmTriggerService;
     
    /**
     * @Fields alarmLogService : 告警logservice
     */
    @Autowired
    private AlarmLogService alarmLogService;
     
    /**
     * <p>Title:AlarmLoopDelTask</p>
     * <p>Description: 构造方法</p>
     */ 
    public AlarmLoopDelTask (){
        
    }
        
    /**
     * @Title: start
     * @Description: 创建定时删除告警的方法
     * @author chensq
     */
    @Scheduled(cron = "0 0 1 * * ?")  //每晚1点进行
    public void start(){
       
        //删除指定数量以外的数据
        long standardTime=alarmLogService.loopDelAlertLogByCount(AlarmConstantUtil.LOOP_DEL_COUNT);
        //删除alertTrigger
        if(standardTime!=0){
            alarmTriggerService.loopDelAlertTriggerByCount(standardTime);
        }
        
    }
    
}
