/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetMinuteRunAble
 *创建人:chensq    创建时间:2018年4月15日
 */
package com.protocolsoft.alarm.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;
import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmCustomUnionKpiThreadBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;
import com.protocolsoft.alarm.bean.AlarmKpiAlgorithmBean;
import com.protocolsoft.alarm.bean.AlarmKpiBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.bean.AlarmThreadInsertBean;
import com.protocolsoft.alarm.bean.AlarmUseDaoBean;
import com.protocolsoft.alarm.bean.PointEntryByAlarmTypeBean;
import com.protocolsoft.alarm.dao.AlarmCustomkpiDao;
import com.protocolsoft.alarm.dao.AlarmKpiAlgorithmDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.service.AlarmAlgorithmService;
import com.protocolsoft.alarm.service.AlarmSetService;
import com.protocolsoft.alarm.util.AlarmAlgorithmUtil;
import com.protocolsoft.alarm.util.AlarmConstantUtil;
import com.protocolsoft.alarm.util.AlarmThreadInsertUtil;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.bean.UrlRrdParamBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.kpi.service.UrlRrdService;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * @ClassName: AlarmSetMinuteRunAble
 * @Description: 1分钟,告警线程类,执行方法
 * @author chensq
 *
 */
public class AlarmSetMinuteRunAble implements Runnable {

    /**
     * @Fields checkAlarmMinuteList : 告警内容list
     */
    public static List<Map<String, AlarmCheckCountBean>> checkAlarmMinuteList;

    /**
     * @Fields alarmSetBean : 当前使用的设置
     */
    private AlarmSetBean alarmSetBean;
     
    /**
     * @Fields customMap : 组合告警使用的map
     */
    private Map<String, AlarmCustomUnionKpiThreadBean> customMap;

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
     * @Fields alarmKpiAlgorithmDao : 告警kpi与算法关系表(传递，非注入)
     */
    private AlarmKpiAlgorithmDao alarmKpiAlgorithmDao;
     
    /**
     * @Fields watchpointDao : 观察点(传递，非注入)
     */
    private WatchpointDao watchpointDao;
     
    /**
     * @Fields kpisDao : kpi dao(传递，非注入)
     */
    private KpisDao kpisDao;
    
    /**
     * @Fields alarmCustomkpiDao : alarmCustomkpi dao(传递，非注入)
     */
    private AlarmCustomkpiDao alarmCustomkpiDao;
    
    /**
     * @Fields urlRrdService : urlRrdService service(传递，非注入)
     */
    private UrlRrdService urlRrdService;
  
    /**
     * @Fields syslogService : syslogService service(传递，非注入)
     */
    private SyslogService syslogService;
    
    /**
     * @Fields alarmSetService : AlarmSet Service(传递，非注入)
     */
    private AlarmSetService alarmSetService;

    //---------------------传递过来的dao--------------------
     
    /**
     * @Fields alarmAlgorithmMap : 告警算法集合
     */
    private  Map<String, AlarmAlgorithmBean> alarmAlgorithmMap=AlarmAlgorithmService.alarmAlgorithmMap;
    
    /**
     * 构造方法
     */
    public AlarmSetMinuteRunAble(
            AlarmSetBean alarmSetBean,
            AlarmUseDaoBean alarmUseDaoBean,
            Map<String, AlarmCustomUnionKpiThreadBean> customMap
    ) {
        this.alarmSetBean=alarmSetBean;
        this.customMap=customMap;
        
        this.alarmKpiDao=alarmUseDaoBean.getAlarmKpiDao();
        this.alarmLogDao=alarmUseDaoBean.getAlarmLogDao();
        this.alarmKpiAlgorithmDao=alarmUseDaoBean.getAlarmKpiAlgorithmDao();
        this.watchpointDao=alarmUseDaoBean.getWatchpointDao();
        this.kpisDao=alarmUseDaoBean.getKpisDao();
        this.alarmCustomkpiDao=alarmUseDaoBean.getAlarmCustomkpiDao();
        this.urlRrdService=new UrlRrdService();
        this.syslogService=alarmUseDaoBean.getSyslogService();
        this.alarmSetService=alarmUseDaoBean.getAlarmSetService();
        
    }
    
    
    /**
     * run方法
     */
    public void run() {
        try {
            //组合kpi
            if(this.alarmSetBean.getHighLowBaselineFlag()==4){
              
            //应用可用性
            }else if(this.alarmSetBean.getHighLowBaselineFlag()==3){
                
            //智能告警
            }else if(this.alarmSetBean.getHighLowBaselineFlag()==2){
               
            }else{ 
                checkAlarmInfo(this.alarmSetBean); 
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    /**
     * @Title: checkAlarmInfo
     * @Description: 执行告警检查
     * @param alarmSetBean void
     * @author chensq
     */
    public void checkAlarmInfo(AlarmSetBean alarmSetBean){
        //当前时间，并取整
        long now =  DateUtils.normalize(System.currentTimeMillis() / 1000, 60) ;
        //主要为报文设置开始结束时间，url会在下面的代码中进行覆盖
        long startTime=now-AlarmConstantUtil.URL_BUSI_RRD_GET_BETWEEN;
        long endTime=now;
        
        //查询告警设置对应的算法
        AlarmKpiAlgorithmBean kpiAlgorithmlist= alarmKpiAlgorithmDao.getAlarmKpiAlgorithmByKpiId(
               alarmSetBean.getKpiId(),
               alarmSetBean.getKpitype());
        
        //算法id
        long kpiAlgorithm=kpiAlgorithmlist.getAlgorithmId();
        //算法详情
        AlarmAlgorithmBean alarmAlgorithmBean=alarmAlgorithmMap.get(String.valueOf(kpiAlgorithm));
        
        //获取告警信息
        long moduleId=0;   //模块id
        long kpiId=0;      //kpi id
        String kpiName=""; //kpi name
        
        if (alarmSetBean.getKpitype()==1) {//原有kpi
            AlarmKpiBean tempAlarmKpiBean=alarmKpiDao.getAlarmKpiById(alarmSetBean.getKpiId());
            moduleId=tempAlarmKpiBean.getModuleId();
            kpiId=tempAlarmKpiBean.getKpiId();
            KpisBean kpisBean=kpisDao.getKpisById((int)kpiId);
            kpiName=kpisBean.getName();
        } else if (alarmSetBean.getKpitype()==2) {
            AlarmCustomkpiBean tempAlarmCustomkpiBean=alarmCustomkpiDao.getAlarmCustomKpiById(alarmSetBean.getKpiId());
            moduleId=tempAlarmCustomkpiBean.getModuleId();
            kpiId=tempAlarmCustomkpiBean.getId();
            kpiName=tempAlarmCustomkpiBean.getNameen();
        }
        
        //观察点集合
        List<WatchpointBean> wplist=watchpointDao.getFindAll();
        
        //创建rrd对象
        List<BusiKpiService> rrdList=new ArrayList<BusiKpiService>();
        BusiKpiService busiKpiService=null;   

        switch ((int)moduleId) {
            case 8://url
                //time
                long uEndTime=now-AlarmConstantUtil.URL_RRD_GET_BEFORE;
                long uStartTime=uEndTime-AlarmConstantUtil.URL_BUSI_RRD_GET_BETWEEN;
                //结束时间
                startTime=uStartTime;
                endTime=uEndTime;
                
                //item bean
                UrlRrdParamBean urlRrdParamBean=null;
                if (wplist!=null && wplist.size()>0) {
                    for (int w=0; w<wplist.size(); w++) {
                        urlRrdParamBean =new UrlRrdParamBean();
                        urlRrdParamBean.setWatchpointId(wplist.get(w).getId());
                        urlRrdParamBean.setBusiId((int)alarmSetBean.getBusinessId());
                        urlRrdParamBean.setStarttime(uStartTime);
                        urlRrdParamBean.setEndtime(uEndTime);
                        urlRrdParamBean.setAlg(RrdAlgorithm.AVG);
                        busiKpiService=urlRrdService.getUrlKpiService(urlRrdParamBean, kpiName);
                        rrdList.add(busiKpiService);
                    }
                }
                break;
                
            case 9://报文                
                //item bean
                BusiKpiService kpiService=null;
                if (wplist!=null && wplist.size()>0) {                    
                    for (int w=0; w<wplist.size(); w++) {
                        kpiService=new BusiKpiService((int)wplist.get(w).getId(), (int)alarmSetBean.getBusinessId(), true);
                        rrdList.add(kpiService);
                    }
                }
                break;
                
            default://观察点
                break;
        }
              
        //迭代rrd对象
        if (rrdList!=null && rrdList.size()>0) {            
            //观察点下 URL或报文     (rrdList的顺序与观察点集合的顺序是一致的)
            //新加 url(8)
            if (moduleId==8) {
                for (int x=0; x<rrdList.size(); x++) {
                    BusiKpiService busiKpiServiceTemp = rrdList.get(x);
                    //key
                    StringBuffer buf=new StringBuffer();
                    buf.append(moduleId);
                    buf.append(",");
                    buf.append(wplist.get(x).getId());
                    buf.append(",");
                    buf.append(alarmSetBean.getBusinessId());
                    buf.append(",");
                    buf.append(alarmSetBean.getKpitype());
                    buf.append(",");
                    buf.append(alarmSetBean.getKpiId());
                    buf.append(",");
                    buf.append(alarmSetBean.getHighLowBaselineFlag());

                    //到公共方法中进行处理，根据不同的告警类型(原生/自定义kpi)
                    PointEntryByAlarmTypeBean pointEntryByAlarmTypeBean =new PointEntryByAlarmTypeBean();
                    pointEntryByAlarmTypeBean.setStarttime(startTime);
                    pointEntryByAlarmTypeBean.setEndtime(endTime);
                    pointEntryByAlarmTypeBean.setAlarmSetBean(alarmSetBean);
                    pointEntryByAlarmTypeBean.setBusiKpiService(busiKpiServiceTemp);
                    pointEntryByAlarmTypeBean.setAlarmAlgorithmBean(alarmAlgorithmBean);
                    pointEntryByAlarmTypeBean.setKpiName(kpiName);
                    
                    //rrd point
                    SimpleEntry<Long, Double> point=AlarmAlgorithmUtil.getPointEntryByAlarmType(
                            pointEntryByAlarmTypeBean);
                    
                    //set to collection
                    this.setToCollection(buf.toString(), point , alarmAlgorithmBean);
                }
            }
            
            //新加 busi(9)
            if (moduleId==9) {
                
                long bEndTime=now-(AlarmConstantUtil.URL_BUSI_RRD_GET_BETWEEN*2);
                long bStartTime=bEndTime-AlarmConstantUtil.URL_BUSI_RRD_GET_BETWEEN;
                
                for (int x=0; x<rrdList.size(); x++) {
                    BusiKpiService busiKpiServiceTemp = rrdList.get(x);
                    //key
                    StringBuffer buf=new StringBuffer();
                    buf.append(moduleId);
                    buf.append(",");
                    buf.append(wplist.get(x).getId());
                    buf.append(",");
                    buf.append(alarmSetBean.getBusinessId());
                    buf.append(",");
                    buf.append(alarmSetBean.getKpitype());
                    buf.append(",");
                    buf.append(alarmSetBean.getKpiId());
                    buf.append(",");
                    buf.append(alarmSetBean.getHighLowBaselineFlag());

                    //到公共方法中进行处理，根据不同的告警类型(原生/自定义kpi)
                    PointEntryByAlarmTypeBean pointEntryByAlarmTypeBean =new PointEntryByAlarmTypeBean();
                    pointEntryByAlarmTypeBean.setStarttime(bStartTime);
                    pointEntryByAlarmTypeBean.setEndtime(bEndTime);
                    pointEntryByAlarmTypeBean.setAlarmSetBean(alarmSetBean);
                    pointEntryByAlarmTypeBean.setBusiKpiService(busiKpiServiceTemp);
                    pointEntryByAlarmTypeBean.setAlarmAlgorithmBean(alarmAlgorithmBean);
                    pointEntryByAlarmTypeBean.setKpiName(kpiName);
                    
                    //rrd point
                    SimpleEntry<Long, Double> point=AlarmAlgorithmUtil.getPointEntryByAlarmType(
                            pointEntryByAlarmTypeBean);
                    
                    //set to collection
                    this.setToCollection(buf.toString(), point , alarmAlgorithmBean);
                }
            }
        }
    }
       
    /**
     * @Title: setToCollection
     * @Description: 判断新的值是否设置入 map 与 list 集合
     * @param key  
     * @param point  
     * @param alarmAlgorithmBean void
     * @author chensq
     */
    public void setToCollection(
            String key,
            SimpleEntry<Long, Double> point,
            AlarmAlgorithmBean alarmAlgorithmBean){
        //初始化
        if (checkAlarmMinuteList == null) {
            checkAlarmMinuteList=new CopyOnWriteArrayList<Map<String, AlarmCheckCountBean>>();
        }
        
        //验证以前是否存在过该key
        boolean beforeIsHave=false; //false未存在过  true存在过
        if (checkAlarmMinuteList!=null && checkAlarmMinuteList.size()>0) {
            for (int x=0; x<checkAlarmMinuteList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=checkAlarmMinuteList.get(x);
                //迭代map
                Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                try {
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        if (itemKey.endsWith(key)) {
                            beforeIsHave=true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        //设置与否判断
        if (!beforeIsHave) {//未设置
            AlarmCheckCountBean alarmCheckCountBean=new AlarmCheckCountBean();
            alarmCheckCountBean.setStarttime(point.getKey());
            alarmCheckCountBean.setEndtime(point.getKey());
            alarmCheckCountBean.setEndFlag(1);
            //计算告警相关信息
            AlarmCheckCountBean itemCheckCountReturn=AlarmAlgorithmUtil.alarmAlgorithm(alarmSetBean,
                    alarmAlgorithmBean, point, alarmCheckCountBean);
            
            //item
            Map<String, AlarmCheckCountBean> checkAlarmItemMap=new ConcurrentHashMap<String, AlarmCheckCountBean>();
            checkAlarmItemMap.put(key, itemCheckCountReturn);
            
            checkAlarmMinuteList.add(checkAlarmItemMap);
        } else {//已设置
             
            try {
               //设置入集合中
                for (int x=0; x<checkAlarmMinuteList.size(); x++) {
                    Map<String, AlarmCheckCountBean> tempItemsMap= checkAlarmMinuteList.get(x);
                     //迭代map
                    Set<Entry<String, AlarmCheckCountBean>> it = tempItemsMap.entrySet(); 
                    Iterator<Entry<String, AlarmCheckCountBean>> its =it.iterator();
                    while (its.hasNext()) {
                        Entry<String, AlarmCheckCountBean> entry = its.next();
                        String itemKey = entry.getKey(); 
                        AlarmCheckCountBean valueBefore = entry.getValue(); 
                        //判断是否合并新值
                        if (itemKey.equals(key)) {
                            //原来的值 (使用最新的阈值与算法)
                            AlarmCheckCountBean itemCheckCountReturn=AlarmAlgorithmUtil.alarmAlgorithm(alarmSetBean,
                                    alarmAlgorithmBean, point, valueBefore);
                            //判断返回结果的状态
                            int checkResult=itemCheckCountReturn.getEndFlag();
                            if (checkResult==1) {// 1为继续
                                tempItemsMap.put(key, itemCheckCountReturn);
                            } else if (checkResult==2){//2为清除
                                checkAlarmMinuteList.remove(tempItemsMap);
//                                its.remove();
                            } else {//3为符合告警条件
                                AlarmThreadInsertBean insertBean=new AlarmThreadInsertBean();
                                insertBean.setKey(itemKey);
                                insertBean.setCheckCountBean(itemCheckCountReturn);
                                insertBean.setAlarmLogDao(alarmLogDao);
                                insertBean.setSyslogService(syslogService);
                                insertBean.setAlarmSetService(this.alarmSetService);
                                insertBean.setCustomMap(customMap);
                                //判断并或者添加组合告警
                                AlarmThreadInsertUtil.isInsertCustomBean(insertBean);
                                //添加基础告警
                                boolean insertFlag=AlarmThreadInsertUtil.doInsertAlarm(insertBean);
                                if (insertFlag) {//添加成功
                                    checkAlarmMinuteList.remove(tempItemsMap);
//                                    its.remove();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
                
    }
    
    
    
}
