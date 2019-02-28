/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineDaoUsedUtil.java
 *创建人: chensq    创建时间: 2018年5月25日
 */
package com.protocolsoft.alarm.util;

import java.util.HashMap;
import java.util.Map;

import com.protocolsoft.alarm.bean.AlarmBaseLineBean;
import com.protocolsoft.alarm.dao.AlarmBaseLineDao;

/**
 * @ClassName: AlarmBaseLineDaoUsedUtil
 * @Description: 智能告警设置dao使用的工具类
 * @author chensq
 *
 */
public class AlarmBaseLineDaoUsedUtil {
    
    /**
     * 
     * @Title: insertOrUpdateAlarmBaseLineSet
     * @Description: 判断ipm_alarm_baselineset当前应该进行的状态
     * @param alarmBaseLineDao
     * @param alarmBaseLineBean
     * @return Map<String, String>
     * @author chensq
     */
    public static Map<String, String> insertOrUpdateAlarmBaseLineSet(AlarmBaseLineDao alarmBaseLineDao, AlarmBaseLineBean alarmBaseLineBean){
        //type : 1:添加记录  2：可以计算智能基线   3:更新结束时间   startTime endTime id
        Map<String, String> returnMap=new HashMap<String, String>();
        int type=1;
        
        AlarmBaseLineBean alarmBaseLineBeanReturn=alarmBaseLineDao.getAlarmBaseLineByParam(alarmBaseLineBean);
        if(alarmBaseLineBeanReturn!=null && alarmBaseLineBeanReturn.getId()!=0){//存在
            //开始结束时间
            long startTime=alarmBaseLineBeanReturn.getStartTime();
            long endTime=alarmBaseLineBeanReturn.getEndTime();
            long startEndBwt=endTime-startTime;
            //返回map使用
            returnMap.put("startTime", String.valueOf(startTime));
            returnMap.put("endTime", String.valueOf(endTime));
            returnMap.put("id", String.valueOf(alarmBaseLineBeanReturn.getId()));
            
            //验证开始时间与结束时间是否小于'智能告警学习秒数'
            if(startEndBwt<AlarmConstantUtil.CHECK_BASELINE_TIME){
                type=3;
            }else{
                type=2;
            }
        }else{//不存在
            type=1;
        }
        
        returnMap.put("type", String.valueOf(type));

        return returnMap;
    }
   
    
}
