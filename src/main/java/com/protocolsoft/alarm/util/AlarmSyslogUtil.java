/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSyslogUtil
 *创建人:chensq    创建时间:2018年4月4日
 */
package com.protocolsoft.alarm.util;

import java.lang.reflect.Field;

import org.productivity.java.syslog4j.Syslog;

import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmSysLogBean;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.system.service.impl.SyslogService;
 
/**
 * @ClassName: AlarmSyslogUtil
 * @Description: Syslog工具类
 * @author chensq
 *
 */
public class AlarmSyslogUtil {
    
    /**
     * @Fields syslogService : 传递 syslog service
     */
    private SyslogService syslogService;
    
    /**
     * @Fields alarmLogDao : alarm log service
     */
    private AlarmLogDao alarmLogDao;
     
    /**
     * @Fields insertBean : 传递 AlarmLog Bean
     */
    private AlarmLogBean insertBean;
    
   
    /**
     * <p>Title:AlarmSyslogUtil </p>
     * <p>Description: 构造方法</p>
     * @param syslogService
     * @param insertBean
     * @param alarmLogDao
     */ 
    public AlarmSyslogUtil(SyslogService syslogService, AlarmLogBean insertBean, AlarmLogDao alarmLogDao){
        this.syslogService=syslogService;
        this.insertBean=insertBean;
        this.alarmLogDao=alarmLogDao;
    }
    
    /**
     * @Title: toSendSysLog
     * @Description: syslog发送
     * @return boolean
     * @author chensq
     */
    public boolean toSendSysLog() {
        boolean flag=false;
        try{
            AlarmLogBean alarmLogBean =alarmLogDao.getAlarmLogDataById(insertBean.getId());
            
            AlarmSysLogBean alarmSysLogBean=new AlarmSysLogBean();
            alarmSysLogBean.setStart(alarmLogBean.getStarttimeStr());
            alarmSysLogBean.setEnd(alarmLogBean.getEndtimeStr());
            alarmSysLogBean.setOrigin(alarmLogBean.getWatchpointName());
            alarmSysLogBean.setBusi(alarmLogBean.getBusinessName());
            alarmSysLogBean.setKpi(alarmLogBean.getKpisDisplayName());
            alarmSysLogBean.setLevel(alarmLogBean.getAlarmLevelZh());
            alarmSysLogBean.setResp(alarmLogBean.getHandledflag().equalsIgnoreCase("Y")?"已响应":"未响应");
            
            StringBuffer buf=new StringBuffer();
            
            //list
            Field[] field = alarmSysLogBean.getClass().getDeclaredFields();
            
            for (int i = 0; i < field.length; i++) {  
                field[i].setAccessible(true);
                String tempKey=field[i].getName() ;
                String tempValue=String.valueOf(field[i].get(alarmSysLogBean));
                //拼接
                buf.append("[");
                buf.append(tempKey.toUpperCase());
                buf.append("=");
                buf.append(tempValue);
                buf.append("]");
                
                if(i!=field.length-1){
                    buf.append(" ");
                }
            }  
            
            String finalSendSysLogStr=buf.toString();
            
            //判断级别
            if(alarmLogBean.getAlarmLevelId()==2){
                syslogService.logSend(finalSendSysLogStr, Syslog.LEVEL_WARN);
            }else if(alarmLogBean.getAlarmLevelId()==3){
                syslogService.logSend(finalSendSysLogStr, Syslog.LEVEL_CRITICAL);
            }else if(alarmLogBean.getAlarmLevelId()==4){
                syslogService.logSend(finalSendSysLogStr, Syslog.LEVEL_EMERGENCY);
            }
            
            flag=true;
        }catch(IllegalArgumentException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return flag;
    }
}
