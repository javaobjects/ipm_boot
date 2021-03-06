/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmSetMinuteBLRunAble.java
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
import com.protocolsoft.alarm.util.AlarmMinuteBusiBaseLineUtil;
import com.protocolsoft.alarm.util.AlarmMinuteUrlBaseLineUtil;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.service.UrlRrdService;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmSetMinuteBLRunAble
 * @Description: 分钟使用的智能告警类
 * @author chensq
 *
 */
public class AlarmSetMinuteBLRunAble implements Runnable{
  
    //---------------------传递过来的dao--------------------
    
    /**
     * @Fields alarmKpiDao : kpi与模块关系表 dao(传递，非注入)
     */
    private AlarmKpiDao alarmKpiDao;
     
    /**
     * @Fields alarmLogDao : 告警日志dao(传递，非注入)
     */
    private AlarmLogDao alarmLogDao;
    
    /**
     * @Fields watchpointDao : 观察点(传递，非注入)
     */
    private WatchpointDao watchpointDao;
     
    /**
     * @Fields kpisDao : kpi dao(传递，非注入)
     */
    private KpisDao kpisDao;
    
    /**
     * @Fields urlRrdService : urlRrdService service(传递，非注入)
     */
    private UrlRrdService urlRrdService;
  
    /**
     * @Fields syslogService : syslogService service(传递，非注入)
     */
    private SyslogService syslogService;

    /**
     * @Fields alarmBaseLineDao : AlarmBaseLine Dao(传递,非注入)
     */
    private AlarmBaseLineDao alarmBaseLineDao;
    //---------------------传递过来的dao--------------------
    
    /**
     * 模块id
     */
    private long moduleId;
    
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
    public AlarmSetMinuteBLRunAble(
            long moduleId,
            String key,
            List<AlarmRemoveRedundancyBlBean> valueinfo,
            AlarmUseDaoBean alarmUseDaoBean) {
        this.moduleId=moduleId;
        this.key=key;
        this.valueinfo=valueinfo;
        
        this.alarmKpiDao=alarmUseDaoBean.getAlarmKpiDao();
        this.alarmLogDao=alarmUseDaoBean.getAlarmLogDao();
        this.watchpointDao=alarmUseDaoBean.getWatchpointDao();
        this.kpisDao=alarmUseDaoBean.getKpisDao();
        this.urlRrdService=new UrlRrdService();
        this.syslogService=alarmUseDaoBean.getSyslogService();
        this.alarmBaseLineDao=alarmUseDaoBean.getAlarmBaseLineDao();
    }
    
    /**
     * run方法
     */
    public void run() {
        try {
            if(moduleId==8){//url
                //将用到的dao service放入对象中
                AlarmBaseLineUseDaoBean alarmBaseLineUseDaoBean=new AlarmBaseLineUseDaoBean();
                alarmBaseLineUseDaoBean.setAlarmBaseLineDao(alarmBaseLineDao);
                alarmBaseLineUseDaoBean.setAlarmKpiDao(alarmKpiDao);
                alarmBaseLineUseDaoBean.setKpisDao(kpisDao);
                alarmBaseLineUseDaoBean.setUrlRrdService(urlRrdService);
                alarmBaseLineUseDaoBean.setWatchpointDao(watchpointDao);
                alarmBaseLineUseDaoBean.setAlarmLogDao(alarmLogDao);
                alarmBaseLineUseDaoBean.setSyslogService(syslogService);      
                
                AlarmMinuteUrlBaseLineUtil.checkBaseLineInfo(key, valueinfo, alarmBaseLineUseDaoBean);
            }else if(moduleId==9){//报文
                //将用到的dao service放入对象中
                AlarmBaseLineUseDaoBean alarmBaseLineUseDaoBean=new AlarmBaseLineUseDaoBean();
                alarmBaseLineUseDaoBean.setAlarmBaseLineDao(alarmBaseLineDao);
                alarmBaseLineUseDaoBean.setAlarmKpiDao(alarmKpiDao);
                alarmBaseLineUseDaoBean.setKpisDao(kpisDao);
                alarmBaseLineUseDaoBean.setWatchpointDao(watchpointDao);
                alarmBaseLineUseDaoBean.setAlarmLogDao(alarmLogDao);
                alarmBaseLineUseDaoBean.setSyslogService(syslogService);      
                
                AlarmMinuteBusiBaseLineUtil.checkBaseLineInfo(key, valueinfo, alarmBaseLineUseDaoBean);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
}
