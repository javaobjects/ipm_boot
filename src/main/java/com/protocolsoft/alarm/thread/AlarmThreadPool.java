/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreadPool
 *创建人:chensq    创建时间:2017年11月8日
 */
package com.protocolsoft.alarm.thread;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiThreadBean;
import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmUseDaoBean;
import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmCustomkpiDao;
import com.protocolsoft.alarm.dao.AlarmKpiAlgorithmDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.dao.AlarmSetThreadDao;
import com.protocolsoft.alarm.service.AlarmAlgorithmService;
import com.protocolsoft.alarm.service.AlarmKpisService;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.alarm.util.AlarmCustomUnionKpiUtil;
import com.protocolsoft.alarm.util.AlarmRedundanceDelUtil;
import com.protocolsoft.alarm.util.AlarmSetDelBaseLineUtil;
import com.protocolsoft.alarm.util.AlarmSetThreadUtil;
import com.protocolsoft.alarm.util.MaxThreadAlgorithm;
import com.protocolsoft.common.dao.AppBusinessDao;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.servers.dao.AppIpPortDao;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmThreadPool
 * @Description: 告警线程池
 * @author chensq
 *
 */
@Service
public class AlarmThreadPool {
    
    /**
     * @Fields count : 执行次数计数器,用于初始化告警阈值
     */
    static long count=0; 
     
    /**
     * @Fields oneMinuteCount : 用于验证一分钟,url、报文使用
     */
    static long oneMinuteCount=0; 
     
    /**
     * @Fields fixedThreadPool : 线程任务
     */
    public static ExecutorService fixedThreadPool=null;
    
    //------------------------------依赖注入---------start-----------------------
    /**
     * @Fields alarmKpiDao : 告警kpi模块dao
     */
    @Autowired
    private AlarmKpiDao alarmKpiDao;
     
    /**
     * @Fields alarmLogDao : 告警log
     */
    @Autowired
    private AlarmLogDao alarmLogDao;
    
    /**
     * @Fields alarmKpiAlgorithmDao : 告警kpi与算法关系表
     */
    @Autowired
    private AlarmKpiAlgorithmDao alarmKpiAlgorithmDao;
    
    /**
     * @Fields watchpointDao : 观察点dao
     */
    @Autowired
    private WatchpointDao watchpointDao;
     
    /**
     * @Fields appIpPortDao : 服务端dao
     */
    @Autowired
    private AppIpPortDao appIpPortDao;
   
    /**
     * @Fields kpisDao : kpi dao
     */
    @Autowired
    private KpisDao kpisDao;
     
    /**
     * @Fields alarmCustomkpiDao : Customkpi Dao
     */
    @Autowired
    private AlarmCustomkpiDao alarmCustomkpiDao;
    
    /**
     * @Fields alarmSetService : 告警阈值设置业务逻辑层对象
     */
    @Autowired
    private AlarmSetService alarmSetService;
     
    /**
     * @Fields alarmAlgorithmService : 告警算法业务逻辑层对象(所有告警算法集合)
     */
    @Autowired
    private AlarmAlgorithmService alarmAlgorithmService;
 
    /**
     * @Fields alarmKpisService : 告警使用的kpis的service
     */
    @Autowired
    AlarmKpisService alarmKpisService;
  
    /**
     * @Fields syslogService : syslog service
     */
    @Autowired
    private SyslogService syslogService;
    
    /**
     * @Fields alarmSetThreadDao : alarmSetThread Dao
     */
    @Autowired
    private AlarmSetThreadDao alarmSetThreadDao;   
    
    /**
     * @Fields alarmBaseLineDao : 智能告警设置dao
     */
    @Autowired
    private AlarmBaseLineDao alarmBaseLineDao;
    
    /**
     * @Fields appBusinessDao : 业务dao
     */
    @Autowired
    private AppBusinessDao appBusinessDao;
    //------------------------------依赖注入---------end-----------------------
 
    /**
     * <p>Title: AlarmThreadPool</p>
     * <p>Description: 构造方法</p>
     */ 
    public AlarmThreadPool (){
        
    }
        
    /**
     * @Title: start
     * @Description: 创建线程池工厂
     * @author chensq
     */
    @Scheduled(cron="0/10 * *  * * ? ")   //每10秒执行一次    
    public void start(){
        //首次加载时,将相关告警设置信息放入static内存中   并  加载所有算法

        if (count==0) {
            //线程最大数
            long maxThreadNum =MaxThreadAlgorithm.getMaxThreadNum();
            
            //创建线程
            fixedThreadPool = Executors.newFixedThreadPool((int)maxThreadNum);
            
            //将相关告警设置信息放入static
            AlarmSetThreadUtil.setAlarmSetThreadInit(alarmSetThreadDao);
            
            //获取所有算法,设置到static中
            alarmAlgorithmService.setAllAlarmAlgorithmToMap();
            
            //智能告警使用，所有kpis的信息(模块下的所有kpis信息)
            alarmKpisService.setAllKpisToMap();
         
            //计数器++
            count ++;
        }
        
        //计数器，一分钟
        if(oneMinuteCount<=7){
            oneMinuteCount++;
        }
        
        //获取所有需要告警的设置(其内容根据业务变动情况变动)
        Map<String, AlarmSetBean> alarmSetThreadMaps=AlarmSetService.alarmSetThreadMap;
        
        //将所有智能告警按照观察点,业务,模块捏在一起(不区分观察点)
        Map<String, List<AlarmRemoveRedundancyBlBean>> blMapList=AlarmSetDelBaseLineUtil.delRedundancyAlarmSet(alarmSetThreadMaps); //所有非智能告警的
        
        //组合告警map
        AlarmCustomUnionKpiUtil alarmCustomUnionKpiUtil=new AlarmCustomUnionKpiUtil();
        Map<String, AlarmCustomUnionKpiThreadBean> customMap =alarmCustomUnionKpiUtil.getAllThreadToCustomKpiMap(
                alarmSetThreadMaps, watchpointDao);
        
        //将后续用到的kpi dao放入dao bean中
        AlarmUseDaoBean alarmUseDaoBean= new AlarmUseDaoBean();
        alarmUseDaoBean.setAlarmKpiDao(alarmKpiDao);
        alarmUseDaoBean.setAlarmLogDao(alarmLogDao);
        alarmUseDaoBean.setAlarmKpiAlgorithmDao(alarmKpiAlgorithmDao);
        alarmUseDaoBean.setWatchpointDao(watchpointDao);
        alarmUseDaoBean.setAppIpPortDao(appIpPortDao);
        alarmUseDaoBean.setKpisDao(kpisDao);
        alarmUseDaoBean.setAlarmCustomkpiDao(alarmCustomkpiDao);
        alarmUseDaoBean.setSyslogService(syslogService);     
        alarmUseDaoBean.setAlarmSetService(alarmSetService);
        alarmUseDaoBean.setAlarmBaseLineDao(alarmBaseLineDao);  
        alarmUseDaoBean.setAppBusinessDao(appBusinessDao);
        
        //无观察点的情况
        List<WatchpointBean> wplist=watchpointDao.getFindAll();
        if(wplist!=null && wplist.size()>0){

            //执行检查(非智能)
            if(alarmSetThreadMaps!=null && alarmSetThreadMaps.size()>0){
                Set<Entry<String, AlarmSetBean>> it = alarmSetThreadMaps.entrySet(); 
                Iterator<Entry<String, AlarmSetBean>> its =it.iterator();
                while (its.hasNext()) {
                    Entry<String, AlarmSetBean> entry = its.next();                    
                    String []array=entry.getKey().split(",");
                    //非智能告警
                    if(!"2".equalsIgnoreCase(array[5])){
                        AlarmSetBean valueinfo = entry.getValue(); 
                        long moduleId=valueinfo.getModuleId();
                        //8为url 9为报文
                        if(moduleId==8 || moduleId==9){
                            if(oneMinuteCount==7){
                                fixedThreadPool.execute(new AlarmSetMinuteRunAble(valueinfo, alarmUseDaoBean, customMap));  
                            }
                        } else {//以10s计算的方式
                            fixedThreadPool.execute(new AlarmSetSecondRunAble(valueinfo, alarmUseDaoBean, customMap));
                        }
                    }
                }
            }
            
            //执行检查(智能)
            if(blMapList!=null && blMapList.size()>0){
                Set<Entry<String, List<AlarmRemoveRedundancyBlBean>>> it = blMapList.entrySet(); 
                Iterator<Entry<String, List<AlarmRemoveRedundancyBlBean>>> its =it.iterator();
                while (its.hasNext()) {
                    Entry<String, List<AlarmRemoveRedundancyBlBean>> entry = its.next();
                    String key=entry.getKey();
                    List<AlarmRemoveRedundancyBlBean> valueinfo = entry.getValue(); 
                    String []arrayValue=key.split(",");
                    String moduleId=arrayValue[0];
                
                    //8为url 9为报文
                    if("8".equalsIgnoreCase(moduleId) || "9".equalsIgnoreCase(moduleId)){
                        if(oneMinuteCount==7){
                            fixedThreadPool.execute(new AlarmSetMinuteBLRunAble(Long.parseLong(moduleId), key, valueinfo, alarmUseDaoBean));  
                        }
                    } else {//以10s计算的方式
                        fixedThreadPool.execute(new AlarmSetSecondBLRunAble(Long.parseLong(moduleId), key, valueinfo, alarmUseDaoBean));
                    }
                }
            }
            
            //开始下次的一分钟计数
            if(oneMinuteCount==7){
                oneMinuteCount=0;
            }
            
            //删除告警冗余告警信息
            AlarmRedundanceDelUtil.delAlarmRedundanceSet(watchpointDao, alarmSetThreadMaps);
            
        }
        
    }
      
    
}
