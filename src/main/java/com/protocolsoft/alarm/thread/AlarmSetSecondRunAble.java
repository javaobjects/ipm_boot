/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetSecondRunAble
 *创建人:chensq    创建时间:2017年11月14日
 */
package com.protocolsoft.alarm.thread;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.protocolsoft.alarm.util.AlarmThreadInsertUtil;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.bean.TimeDefaultBean;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * 10S
 * 告警线程类,执行方法
 * 2017年11月14日 下午4:15:05
 * @author chensq
 * @version
 * @see
 */
public class AlarmSetSecondRunAble implements Runnable{
   
    /**
     * 告警内容list
     */
    public static List<Map<String, AlarmCheckCountBean>> checkAlarmSecondList;

    /**
     * 当前使用的设置
     */
    private AlarmSetBean alarmSetBean;
    
    /**
     * 组合告警使用的map
     */
    private Map<String, AlarmCustomUnionKpiThreadBean> customMap;
    
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
     * 告警kpi与算法关系表(传递，非注入)
     */
    private AlarmKpiAlgorithmDao alarmKpiAlgorithmDao;
    
    /**
     * 观察点(传递，非注入)
     */
    private WatchpointDao watchpointDao;

    /**
     * kpi dao(传递，非注入)
     */
    private KpisDao kpisDao;
    
    /**
     * alarmCustomkpi dao(传递，非注入)
     */
    private AlarmCustomkpiDao alarmCustomkpiDao;
    /**
     * syslogService service(传递，非注入)
     */
    private SyslogService syslogService;
    /**
     * AlarmSet Service(传递，非注入)
     */
    private AlarmSetService alarmSetService;
    
    //---------------------传递过来的dao--------------------

    /**
     * 告警算法集合
     */
    private  Map<String, AlarmAlgorithmBean> alarmAlgorithmMap=AlarmAlgorithmService.alarmAlgorithmMap;
    
    /**
     * 构造方法
     */
    public AlarmSetSecondRunAble(
            AlarmSetBean alarmSetBean,
            AlarmUseDaoBean alarmUseDaoBean,
            Map<String, AlarmCustomUnionKpiThreadBean> customMap) {
        this.alarmSetBean=alarmSetBean;
        this.customMap=customMap;

        this.alarmKpiDao=alarmUseDaoBean.getAlarmKpiDao();
        this.alarmLogDao=alarmUseDaoBean.getAlarmLogDao();
        this.alarmKpiAlgorithmDao=alarmUseDaoBean.getAlarmKpiAlgorithmDao();
        this.watchpointDao=alarmUseDaoBean.getWatchpointDao();
        this.kpisDao=alarmUseDaoBean.getKpisDao();
        this.alarmCustomkpiDao=alarmUseDaoBean.getAlarmCustomkpiDao();
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
                
            //高低阈值
            }else{ 
                checkAlarmInfo(this.alarmSetBean); 
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    /**
     * 执行告警检查
     * 2017年11月14日 下午4:06:59
     * @param
     * @exception 
     * @see
     */
    public void checkAlarmInfo(AlarmSetBean alarmSetBean){
        //获取当前时间 并计算 开始时间 与 结束时间
        TimeDefaultBean timeBean=DateAppsUtils.getRrdDefaultTime();
        long startTime=timeBean.getStarttime();
        long endTime=timeBean.getEndtime();
        
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
            case 10://观察点
                busiKpiService=new BusiKpiService((int)alarmSetBean.getBusinessId());
                rrdList.add(busiKpiService);
                break;
            case 11://观察点 下 客户端
            case 12://观察点 下 服务端
                if (wplist!=null && wplist.size()>0) {
                    for (int w=0; w<wplist.size(); w++) {
                        busiKpiService=new BusiKpiService(wplist.get(w).getId(), (int)alarmSetBean.getBusinessId());
                        rrdList.add(busiKpiService);
                    }
                }
                break;
            case 4://http 
            case 5://oracle
            case 6://mysql
            case 7://sqlserver
                if (wplist!=null && wplist.size()>0) {
                    for (int w=0; w<wplist.size(); w++) {
                        busiKpiService=new BusiKpiService(wplist.get(w).getId(), (int)alarmSetBean.getBusinessId());
                        rrdList.add(busiKpiService);
                    }
                }
                break;
            case 2://system
                busiKpiService=new BusiKpiService();
                rrdList.add(busiKpiService);
                break;
            default://观察点
                busiKpiService=new BusiKpiService((int)alarmSetBean.getBusinessId());
                rrdList.add(busiKpiService);
                break;
        }
              
        //迭代rrd对象
        if (rrdList!=null && rrdList.size()>0) {
            
            //观察点情况 即 moduleId为10的情况
            if (moduleId==10) {
                //key
                StringBuffer buf=new StringBuffer();
                buf.append(moduleId);
                buf.append(",");
                buf.append(alarmSetBean.getWatchpointId());
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
                pointEntryByAlarmTypeBean.setBusiKpiService(rrdList.get(0));
                pointEntryByAlarmTypeBean.setAlarmAlgorithmBean(alarmAlgorithmBean);
                pointEntryByAlarmTypeBean.setKpiName(kpiName);
                
                //rrd point
                SimpleEntry<Long, Double> point=AlarmAlgorithmUtil.getPointEntryByAlarmType(pointEntryByAlarmTypeBean);
                 
                //set to collection
                this.setToCollection(buf.toString(), point , alarmAlgorithmBean);
            }
            
            //观察点情况 即 moduleId为2的情况
            if (moduleId==2) {
                //key
                StringBuffer buf=new StringBuffer();
                buf.append(moduleId);
                buf.append(",");
                buf.append(0);
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
                pointEntryByAlarmTypeBean.setBusiKpiService(rrdList.get(0));
                pointEntryByAlarmTypeBean.setAlarmAlgorithmBean(alarmAlgorithmBean);
                pointEntryByAlarmTypeBean.setKpiName(kpiName);
                
                //rrd point
                SimpleEntry<Long, Double> point=AlarmAlgorithmUtil.getPointEntryByAlarmType(pointEntryByAlarmTypeBean);
                 
                //set to collection
                this.setToCollection(buf.toString(), point , alarmAlgorithmBean);
            }
            
            //观察点下客户端 或 服务端     (rrdList的顺序与观察点集合的顺序是一致的)
            //新加 http(4)、oracle(5)、mysql(6)、sqlserver(7)与moduleId!=10适用
            if (moduleId!=10 && moduleId!=2) {
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
        }
    }
    
    /**
     * 判断新的值是否设置入 map 与 list 集合
     * 2017年11月16日 下午1:26:52
     * @param 新的key
     * @param 新的值
     * @param map
     * @param list
     * @exception 
     * @see
     */
    public void setToCollection(
            String key,
            SimpleEntry<Long, Double> point,
            AlarmAlgorithmBean alarmAlgorithmBean){
        //初始化
        if (checkAlarmSecondList == null) {
            checkAlarmSecondList=new CopyOnWriteArrayList<Map<String, AlarmCheckCountBean>>();
        }
        
        //验证以前是否存在过该key
        boolean beforeIsHave=false; //false未存在过  true存在过
        if (checkAlarmSecondList!=null && checkAlarmSecondList.size()>0) {
            for (int x=0; x<checkAlarmSecondList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=checkAlarmSecondList.get(x);
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
            
            checkAlarmSecondList.add(checkAlarmItemMap);
        } else {//已设置
             
            try {
               //设置入集合中
                for (int x=0; x<checkAlarmSecondList.size(); x++) {
                    Map<String, AlarmCheckCountBean> tempItemsMap= checkAlarmSecondList.get(x);
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
                                checkAlarmSecondList.remove(tempItemsMap);
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
                                    checkAlarmSecondList.remove(tempItemsMap);
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
