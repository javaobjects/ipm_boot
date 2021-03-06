/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmSetSecondBLRunAble.java
 *创建人: chensq    创建时间: 2018年8月21日
 */
package com.protocolsoft.alarm.thread;

import java.util.List;

import com.protocolsoft.alarm.bean.AlarmBaseLineUseDaoBean;
import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;
import com.protocolsoft.alarm.bean.AlarmUseDaoBean;
import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.util.AlarmSecondBaseLineUtil;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmSetSecondBLRunAble
 * @Description: 秒使用的智能告警类
 * @author chensq
 *
 */
public class AlarmSetSecondBLRunAble implements Runnable{
    
    //---------------------传递过来的dao--------------------
    
    /**
     * kpi与模块关系表 dao(传递，非注入)
     */
    private AlarmKpiDao alarmKpiDao;
    
    /**
     * 告警日志dao(传递，非注入)
     */
    private AlarmLogDao alarmLogDao;
    
    /**
     * 观察点(传递，非注入)
     */
    private WatchpointDao watchpointDao;
    
    /**
     * 服务端dao(传递，非注入)
     */
    private AppIpPortDao appIpPortDao;

    /**
     * kpi dao(传递，非注入)
     */
    private KpisDao kpisDao;
    
    /**
     * syslogService service(传递，非注入)
     */
    private SyslogService syslogService;
    
    /**
     * AlarmBaseLine Dao(传递,非注入)
     */
    private AlarmBaseLineDao alarmBaseLineDao;
    
    /**
     * appBusiness Dao(传递,非注入)
     */
    private AppBusinessDao appBusinessDao;
    
    //---------------------传递过来的dao--------------------
    
    /**
     * key信息
     */
    private String  key;
    
    /**
     * 值信息
     */
    private List<AlarmRemoveRedundancyBlBean> valueinfo;
    
    /**
     * 构造方法
     */
    public AlarmSetSecondBLRunAble(
            long moduleId,
            String key,
            List<AlarmRemoveRedundancyBlBean> valueinfo,
            AlarmUseDaoBean alarmUseDaoBean) {
        this.key=key;
        this.valueinfo=valueinfo;

        this.alarmKpiDao=alarmUseDaoBean.getAlarmKpiDao();
        this.alarmLogDao=alarmUseDaoBean.getAlarmLogDao();
        this.watchpointDao=alarmUseDaoBean.getWatchpointDao();
        this.appIpPortDao=alarmUseDaoBean.getAppIpPortDao();
        this.kpisDao=alarmUseDaoBean.getKpisDao();
        this.syslogService=alarmUseDaoBean.getSyslogService();
        this.alarmBaseLineDao=alarmUseDaoBean.getAlarmBaseLineDao();
        this.appBusinessDao=alarmUseDaoBean.getAppBusinessDao();
    }
    
    
    /**
     * run方法
     */
    public void run() {
        try {
            //将用到的dao service放入对象中
            AlarmBaseLineUseDaoBean alarmBaseLineUseDaoBean=new AlarmBaseLineUseDaoBean();
            alarmBaseLineUseDaoBean.setAlarmBaseLineDao(alarmBaseLineDao);
            alarmBaseLineUseDaoBean.setAlarmKpiDao(alarmKpiDao);
            alarmBaseLineUseDaoBean.setKpisDao(kpisDao);
            alarmBaseLineUseDaoBean.setAppIpPortDao(appIpPortDao);
            alarmBaseLineUseDaoBean.setWatchpointDao(watchpointDao);
            alarmBaseLineUseDaoBean.setAlarmLogDao(alarmLogDao);
            alarmBaseLineUseDaoBean.setSyslogService(syslogService); 
            alarmBaseLineUseDaoBean.setAppBusinessDao(appBusinessDao);
            
            //调用
            AlarmSecondBaseLineUtil.checkBaseLineInfo(key, valueinfo, alarmBaseLineUseDaoBean);
            
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
                    
}
