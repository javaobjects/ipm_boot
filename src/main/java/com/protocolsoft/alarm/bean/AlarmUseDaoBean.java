/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmUseDaoBean
 *创建人:chensq    创建时间:2017年11月15日
 */
package com.protocolsoft.alarm.bean;

import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmCustomkpiDao;
import com.protocolsoft.alarm.dao.AlarmKpiAlgorithmDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmUseDaoBean
 * @Description: 告警产生使用的dao bean类
 * @author chensq
 *
 */
public class AlarmUseDaoBean {
    /**
     * @Fields alarmKpiDao : 告警kpi
     */
    private AlarmKpiDao alarmKpiDao;    
    /**
     * @Fields alarmLogDao : 告警日志
     */
    private AlarmLogDao alarmLogDao;
    /**
     * @Fields alarmKpiAlgorithmDao : 告警算法
     */
    private AlarmKpiAlgorithmDao alarmKpiAlgorithmDao;
    /**
     * @Fields watchpointDao : 观察点
     */
    private WatchpointDao watchpointDao;
    /**
     * @Fields appIpPortDao : 应用
     */
    private AppIpPortDao appIpPortDao;
    /**
     * @Fields kpisDao : kpi
     */
    private KpisDao kpisDao;
    /**
     * @Fields alarmCustomkpiDao : 自定义kpi
     */
    private AlarmCustomkpiDao alarmCustomkpiDao;
    /**
     * @Fields syslogService : 系统日志
     */
    private SyslogService syslogService;
    /**
     * @Fields alarmSetService : 告警设置
     */
    private AlarmSetService alarmSetService;
    /**
     * @Fields alarmBaseLineDao : 智能告警
     */
    private AlarmBaseLineDao alarmBaseLineDao;
    /**
     * @Fields appBusinessDao : app bus
     */
    private AppBusinessDao appBusinessDao;
    
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
     * <br />获取 <font color="red"><b>alarmKpiAlgorithmDao<b/></font>
     * @return alarmKpiAlgorithmDao alarmKpiAlgorithmDao
     */
    public AlarmKpiAlgorithmDao getAlarmKpiAlgorithmDao() {
        return alarmKpiAlgorithmDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmKpiAlgorithmDao</b></font>
     * @param alarmKpiAlgorithmDao alarmKpiAlgorithmDao  
     */
    public void setAlarmKpiAlgorithmDao(AlarmKpiAlgorithmDao alarmKpiAlgorithmDao) {
        this.alarmKpiAlgorithmDao = alarmKpiAlgorithmDao;
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
     * <br />获取 <font color="red"><b>alarmCustomkpiDao<b/></font>
     * @return alarmCustomkpiDao alarmCustomkpiDao
     */
    public AlarmCustomkpiDao getAlarmCustomkpiDao() {
        return alarmCustomkpiDao;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmCustomkpiDao</b></font>
     * @param alarmCustomkpiDao alarmCustomkpiDao  
     */
    public void setAlarmCustomkpiDao(AlarmCustomkpiDao alarmCustomkpiDao) {
        this.alarmCustomkpiDao = alarmCustomkpiDao;
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
     * <br />获取 <font color="red"><b>alarmSetService<b/></font>
     * @return alarmSetService alarmSetService
     */
    public AlarmSetService getAlarmSetService() {
        return alarmSetService;
    }
    /**  
     * <br />设置 <font color='#333399'><b>alarmSetService</b></font>
     * @param alarmSetService alarmSetService  
     */
    public void setAlarmSetService(AlarmSetService alarmSetService) {
        this.alarmSetService = alarmSetService;
    }
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
