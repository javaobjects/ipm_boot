/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreadInsertBean
 *创建人:chensq    创建时间:2018年4月17日
 */
package com.protocolsoft.alarm.bean;

import java.util.Map;

import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.system.service.impl.SyslogService;


/**
 * @ClassName: AlarmThreadInsertBean
 * @Description: 告警插入使用的bean
 * @author chensq
 *
 */
public class AlarmThreadInsertBean {
    /**
     * @Fields key : 等待插入的key
     */
    private String key;
     
    /**
     * @Fields checkCountBean : 等待插入的checkCountBean
     */
    private AlarmCheckCountBean checkCountBean;
    
    /**
     * @Fields alarmLogDao : alarmLog Dao
     */
    private AlarmLogDao alarmLogDao;
    
    /**
     * @Fields syslogService : syslog Service
     */
    private SyslogService syslogService;
    
    /**
     * @Fields alarmSetService : alarmSet service
     */
    private AlarmSetService alarmSetService;
     
    /**
     * @Fields customMap : custom Map
     */
    private Map<String, AlarmCustomUnionKpiThreadBean> customMap;
    
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return the checkCountBean
     */
    public AlarmCheckCountBean getCheckCountBean() {
        return checkCountBean;
    }
    /**
     * @param checkCountBean the checkCountBean to set
     */
    public void setCheckCountBean(AlarmCheckCountBean checkCountBean) {
        this.checkCountBean = checkCountBean;
    }
    /**
     * @return the alarmLogDao
     */
    public AlarmLogDao getAlarmLogDao() {
        return alarmLogDao;
    }
    /**
     * @param alarmLogDao the alarmLogDao to set
     */
    public void setAlarmLogDao(AlarmLogDao alarmLogDao) {
        this.alarmLogDao = alarmLogDao;
    }
    /**
     * @return the syslogService
     */
    public SyslogService getSyslogService() {
        return syslogService;
    }
    /**
     * @param syslogService the syslogService to set
     */
    public void setSyslogService(SyslogService syslogService) {
        this.syslogService = syslogService;
    }
    /**
     * @return the alarmSetService
     */
    public AlarmSetService getAlarmSetService() {
        return alarmSetService;
    }
    /**
     * @param alarmSetService the alarmSetService to set
     */
    public void setAlarmSetService(AlarmSetService alarmSetService) {
        this.alarmSetService = alarmSetService;
    }
    /**
     * @return the customMap
     */
    public Map<String, AlarmCustomUnionKpiThreadBean> getCustomMap() {
        return customMap;
    }
    /**
     * @param customMap the customMap to set
     */
    public void setCustomMap(Map<String, AlarmCustomUnionKpiThreadBean> customMap) {
        this.customMap = customMap;
    }
    
}
