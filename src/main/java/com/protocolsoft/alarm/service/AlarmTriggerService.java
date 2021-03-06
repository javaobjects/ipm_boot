/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmTriggerService
 *创建人:chensq    创建时间:2018年4月11日
 */
package com.protocolsoft.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.dao.AlarmTriggerDao;

/**
 * @ClassName: AlarmTriggerService
 * @Description: 告警阈值历史记录表
 * @author chensq
 */
@Service
public class AlarmTriggerService {
 
    /**
     * @Fields alarmTriggerDao : 告警阈值DAO
     */
    @Autowired
    private AlarmTriggerDao alarmTriggerDao;
    
    /**
     * @Title: loopDelAlertTrigger
     * @Description: 告警定时清空使用的方法
     * @param endTime void
     * @author chensq
     */
    public  void  loopDelAlertTrigger (long endTime){
        try {
            Long startTime= alarmTriggerDao.getMinStartTime(endTime);
            if (startTime!=null && startTime!=0) {
                alarmTriggerDao.loopDelAlertTrigger(startTime, endTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: loopDelAlertTriggerByCount
     * @Description: 删除小于指定时间的数据
     * @param startTime void
     * @author chensq
     */
    public  void  loopDelAlertTriggerByCount(long startTime){
        try {
            alarmTriggerDao.loopDelAlertTriggerByCount(startTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
