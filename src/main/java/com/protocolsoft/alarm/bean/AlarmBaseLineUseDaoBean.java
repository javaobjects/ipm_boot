/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineUseDaoBean.java
 *创建人: chensq    创建时间: 2018年5月24日
 */
package com.protocolsoft.alarm.bean;

import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.service.UrlRrdService;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmBaseLineUseDaoBean
 * @Description: 智能告警使用的dao service bean
 * @author chensq
 *
 */
public class AlarmBaseLineUseDaoBean {
    /**
     * @Fields alarmBaseLineDao : 智能告警设置dao
     */
    private AlarmBaseLineDao alarmBaseLineDao;
    /**
     * @Fields alarmKpiDao : alarm kpi dao
     */
    private AlarmKpiDao alarmKpiDao;
    /**
     * @Fields kpisDao : kpis dao
     */
    private KpisDao kpisDao;
    /**
     * @Fields watchpointDao : watchpoint dao
     */
    private WatchpointDao watchpointDao;
    /**
     * @Fields appIpPortDao : 非url、报文业务
     */
    private AppIpPortDao appIpPortDao; //非url、报文业务
    /**
     * @Fields urlRrdService : url使用的service
     */
    private UrlRrdService urlRrdService; 
    /**
     * @Fields alarmLogDao : alarmLog Dao
     */
    private AlarmLogDao alarmLogDao;
    /**
     * @Fields syslogService : syslog Service
     */
    private SyslogService syslogService;
    /**
     * @Fields appBusinessDao : appBusiness Dao
     */
    private AppBusinessDao appBusinessDao;
    
    /**
     * <br />获取 <font color="red"><b>alarmBaseLineDao<b/></font>
     * @return alarmBaseLineDao alarmBaseLineDao
     */
    public AlarmBaseLineDao getAlarmBaseLineDao() {
        return alarmBaseLineDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmBaseLineDao</b></font>
     * @param alarmBaseLineDao alarmBaseLineDao  
     */
    public void setAlarmBaseLineDao(AlarmBaseLineDao alarmBaseLineDao) {
        this.alarmBaseLineDao = alarmBaseLineDao;
    }
    /**
     * <br />获取 <font color="red"><b>alarmKpiDao<b/></font>
     * @return alarmKpiDao alarmKpiDao
     */
    public AlarmKpiDao getAlarmKpiDao() {
        return alarmKpiDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmKpiDao</b></font>
     * @param alarmKpiDao alarmKpiDao  
     */
    public void setAlarmKpiDao(AlarmKpiDao alarmKpiDao) {
        this.alarmKpiDao = alarmKpiDao;
    }
    /**
     * <br />获取 <font color="red"><b>kpisDao<b/></font>
     * @return kpisDao kpisDao
     */
    public KpisDao getKpisDao() {
        return kpisDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kpisDao</b></font>
     * @param kpisDao kpisDao  
     */
    public void setKpisDao(KpisDao kpisDao) {
        this.kpisDao = kpisDao;
    }
    /**
     * <br />获取 <font color="red"><b>watchpointDao<b/></font>
     * @return watchpointDao watchpointDao
     */
    public WatchpointDao getWatchpointDao() {
        return watchpointDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>watchpointDao</b></font>
     * @param watchpointDao watchpointDao  
     */
    public void setWatchpointDao(WatchpointDao watchpointDao) {
        this.watchpointDao = watchpointDao;
    }
    /**
     * <br />获取 <font color="red"><b>appIpPortDao<b/></font>
     * @return appIpPortDao appIpPortDao
     */
    public AppIpPortDao getAppIpPortDao() {
        return appIpPortDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>appIpPortDao</b></font>
     * @param appIpPortDao appIpPortDao  
     */
    public void setAppIpPortDao(AppIpPortDao appIpPortDao) {
        this.appIpPortDao = appIpPortDao;
    }
    /**
     * <br />获取 <font color="red"><b>urlRrdService<b/></font>
     * @return urlRrdService urlRrdService
     */
    public UrlRrdService getUrlRrdService() {
        return urlRrdService;
    }
    /**  
     * <br />设置 <font color='#333399'><b>urlRrdService</b></font>
     * @param urlRrdService urlRrdService  
     */
    public void setUrlRrdService(UrlRrdService urlRrdService) {
        this.urlRrdService = urlRrdService;
    }
    /**
     * <br />获取 <font color="red"><b>alarmLogDao<b/></font>
     * @return alarmLogDao alarmLogDao
     */
    public AlarmLogDao getAlarmLogDao() {
        return alarmLogDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmLogDao</b></font>
     * @param alarmLogDao alarmLogDao  
     */
    public void setAlarmLogDao(AlarmLogDao alarmLogDao) {
        this.alarmLogDao = alarmLogDao;
    }
    /**
     * <br />获取 <font color="red"><b>syslogService<b/></font>
     * @return syslogService syslogService
     */
    public SyslogService getSyslogService() {
        return syslogService;
    }
    /**  
     * <br />设置 <font color='#333399'><b>syslogService</b></font>
     * @param syslogService syslogService  
     */
    public void setSyslogService(SyslogService syslogService) {
        this.syslogService = syslogService;
    }
    /**
     * <br />获取 <font color="red"><b>appBusinessDao<b/></font>
     * @return appBusinessDao appBusinessDao
     */
    public AppBusinessDao getAppBusinessDao() {
        return appBusinessDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>appBusinessDao</b></font>
     * @param appBusinessDao appBusinessDao  
     */
    public void setAppBusinessDao(AppBusinessDao appBusinessDao) {
        this.appBusinessDao = appBusinessDao;
    }
    
}
