/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmMinuteBusiBaseLineUtil.java
 *创建人: chensq    创建时间: 2018年5月31日
 */
package com.protocolsoft.alarm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;
import com.protocolsoft.alarm.bean.AlarmBaseLineBean;
import com.protocolsoft.alarm.bean.AlarmBaseLineUseDaoBean;
import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmKpiBean;
import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;
import com.protocolsoft.alarm.bean.AlarmThreadInsertBean;
import com.protocolsoft.alarm.dao.AlarmBaseLineDao;
import com.protocolsoft.alarm.dao.AlarmKpiDao;
import com.protocolsoft.alarm.dao.AlarmLogDao;
import com.protocolsoft.alarm.enumeration.BaselineTriggerGrain;
import com.protocolsoft.alarm.service.AlarmAlgorithmService;
import com.protocolsoft.kpi.bean.KpisBean;
import com.protocolsoft.kpi.dao.KpisDao;
import com.protocolsoft.kpi.enumeration.AlarmBaseType;
import com.protocolsoft.kpi.enumeration.RrdAlgorithm;
import com.protocolsoft.kpi.service.BusiKpiService;
import com.protocolsoft.system.service.impl.SyslogService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.dao.WatchpointDao;

/**
 * 
 * @ClassName: AlarmMinuteBusiBaseLineUtil
 * @Description: 报文智能告警工具类
 * @author chensq
 */
public class AlarmMinuteBusiBaseLineUtil {
    /**
     * 告警内容list
     */
    public static List<Map<String, AlarmCheckCountBean>> checkBaseLineAlarmMinuteBusiList;
    
    /**
     * 告警算法集合
     */
    private static Map<String, AlarmAlgorithmBean> alarmAlgorithmMap=AlarmAlgorithmService.alarmAlgorithmMap;
    
    /**
     * 告警logDao
     */
    private static AlarmLogDao alarmLogDao;
    
    /**
     * 系统logService
     */
    private static SyslogService syslogService;
    
    /**
     * 
     * @Title: checkBaseLineInfo
     * @Description: 智能告警计算等相关操作
     * @param moduleId
     * @param key
     * @param valueinfo
     * @param alarmBaseLineUseDaoBean void
     * @author chensq
     */
    public static void checkBaseLineInfo(
            String key, 
            List<AlarmRemoveRedundancyBlBean> valueinfo, 
            AlarmBaseLineUseDaoBean alarmBaseLineUseDaoBean){
        //智能告警算法详情
        AlarmAlgorithmBean alarmAlgorithmBean=alarmAlgorithmMap.get(String.valueOf(3)); 

        //当前时间，并取整
        long now =  DateUtils.normalize(System.currentTimeMillis() / 1000, 60) ;
        //主要为报文设置开始结束时间，url会在下面的代码中进行覆盖
        long startTime=now-AlarmConstantUtil.URL_BUSI_RRD_GET_BETWEEN;
        long endTime=now;
        //告警rrd间隔时间
        int stepTime=60;
        
        //获取使用的dao service等
        AlarmBaseLineDao alarmBaseLineDao=alarmBaseLineUseDaoBean.getAlarmBaseLineDao();
        KpisDao kpisDao=alarmBaseLineUseDaoBean.getKpisDao();
        AlarmKpiDao alarmKpiDao=alarmBaseLineUseDaoBean.getAlarmKpiDao();
        WatchpointDao watchpointDao=alarmBaseLineUseDaoBean.getWatchpointDao();
        alarmLogDao=alarmBaseLineUseDaoBean.getAlarmLogDao();
        syslogService=alarmBaseLineUseDaoBean.getSyslogService();

        //当前业务的基础信息
        String []keyArray=key.split(",");
        long moduleId=Long.parseLong(keyArray[0]);
        long businessId=Long.parseLong(keyArray[2]);
        long kpitype=Long.parseLong(keyArray[3]);
        
        //观察点集合
        List<WatchpointBean> wplist=watchpointDao.getFindAll();
        //map<watchpointId,busiKpiService>
        Map<String, BusiKpiService> serviceMap =new HashMap<String, BusiKpiService>();
        
        //创建rrd对象
        BusiKpiService busiKpiService=null;    
        switch ((int)moduleId) {
            case 9://报文
                if (wplist!=null && wplist.size()>0) {                    
                    for (int w=0; w<wplist.size(); w++) {
                        busiKpiService=new BusiKpiService((int)wplist.get(w).getId(), (int)businessId, true);
                        serviceMap.put(String.valueOf(wplist.get(w).getId()), busiKpiService);
                    }
                }
                break;   
            default://观察点
                busiKpiService=new BusiKpiService((int)businessId);
                serviceMap.put(String.valueOf(0), busiKpiService);
                break;
        }
                      
        //某个业务的某个kpi在多个观察点下的基线数据记录
        if(serviceMap!=null && serviceMap.size()>0){

            //智能告警设置对象
            AlarmBaseLineBean alarmBaseLineBean=null;
            for (Map.Entry<String, BusiKpiService> entry : serviceMap.entrySet()) {
                
                //rrd对象
                String wpId=entry.getKey();
                BusiKpiService tempBusiKpiService= entry.getValue();

                //智能告警kpi对应的阈值设置信息
                Map<String, Map<String, Double>>  kpiAlarmValueMap=null;
                
                //维护智能告警设置表
                alarmBaseLineBean=new AlarmBaseLineBean();
                alarmBaseLineBean.setWatchpointId(Long.parseLong(wpId));
                alarmBaseLineBean.setModuleId(moduleId);
                alarmBaseLineBean.setBusinessId(businessId);
                
                //迭代详细kpi集合
                if(valueinfo!=null && valueinfo.size()>0){
                    //in it
                    kpiAlarmValueMap=new HashMap<String, Map<String, Double>>();
                    
                    for(int x=0; x<valueinfo.size(); x++){
                        //告警设置对象
                        AlarmRemoveRedundancyBlBean arrblBean=valueinfo.get(x);
                        //基线阈值信息
                        String alarmValueList=arrblBean.getAlarmValueList();
                        //基础阈值map
                        Map<String, Double> alarmValueMap=AlarmBaseLineCalculateUtil.getAlarmValueMap(alarmValueList);
                        //获取kpi信息
                        AlarmKpiBean tempAlarmKpiBean=alarmKpiDao.getAlarmKpiById(arrblBean.getKpiId());
                        long kpiId=tempAlarmKpiBean.getKpiId();
                        KpisBean kpisBean=kpisDao.getKpisById((int)kpiId);
                        String kpiName=kpisBean.getName();
                        //set to baseline bean
                        alarmBaseLineBean.setKpiId(kpiId);
                        alarmBaseLineBean.setKpiName(kpiName);
                        
                        //阈值设置map put
                        kpiAlarmValueMap.put(kpiName, alarmValueMap);
                        
                        //应该进行的操作
                        Map<String, String> returnMap=AlarmBaseLineDaoUsedUtil.insertOrUpdateAlarmBaseLineSet(alarmBaseLineDao, alarmBaseLineBean);
                        //获取详细值
                        int type=Integer.parseInt(returnMap.get("type"));              

                        //1.对数据库的操作
                        if(type==1){// 1:添加记录 
                            //组合参数
                            alarmBaseLineBean.setStartTime(startTime);    
                            alarmBaseLineBean.setTempFlag(0);
                            alarmBaseLineBean.setEndTime(endTime);
                            //添加
                            alarmBaseLineDao.addAlarmBaseLine(alarmBaseLineBean);
                        }else if(type==3){// 3:更新结束时间
                            alarmBaseLineBean.setEndTime(endTime);
                            //执行修改
                            alarmBaseLineDao.updateAlarmBaseLineEndTime(alarmBaseLineBean);
                        }else{//当前已经ok,开始与结束时间都添加
                            alarmBaseLineBean.setStepTime(stepTime);
                            alarmBaseLineDao.updateAlarmBaseLineStartEndTimeByParam(alarmBaseLineBean);
                        }
                    }
                }
                
              //wpId循环周期内
                //------------
                //业务已有的基线表告警记录信息
                List<AlarmBaseLineBean> outKpiIdList=alarmBaseLineDao.getAlarmBaseLineOutKpiId(alarmBaseLineBean);
                //当前业务的智能告警喂数据是否已经完成
                boolean flag =AlarmSetBaseLineToMemoryUtil.busKpisAllFinish(valueinfo, outKpiIdList);
                //可以产生基线
                if(flag){
                    //查询该业务在基线表的最小结束时间
                    long thisKpiBusEndTime=startTime; //alarmBaseLineDao.getAlarmBaseLineMinEndTimeByParam(alarmBaseLineBean);
                    //指定时间前的数据
                    List<Long> beforeTimeList=DateAppsUtils.getManyDayBeforeTimes(thisKpiBusEndTime);
                    
                    // kpiName,Map<Time,value>   注释：kpiName有顺序的   Time是有顺序的，由远至近
                    Map<String, Map<String, List<Double>>> finalBaseLineValueInfo=new 
                            LinkedHashMap<String, Map<String, List<Double>>>();
               
                    //该业务对应模块下的kpis
                    List<String> kpiColumns=AlarmSetBaseLineToMemoryUtil.getColumns(moduleId);
                    
                    //需要告警的kpi <kpiName,kpiName>
                    Map<String, String> kpisIdIdMap=AlarmSetBaseLineToMemoryUtil.getNeedAlarmKpis(outKpiIdList);
                    
                    //迭代模块下的kpi,各个kpi都设置基础值，设置入rrd
                    for(int x=0; x<kpiColumns.size(); x++){
                        //kpiName
                        String tempKpiName=kpiColumns.get(x); 
                        //subMap
                        Map<String, List<Double>> subMap=new LinkedHashMap<String, List<Double>>();
                        
                        //迭代基线时间
                        if(kpisIdIdMap.get(tempKpiName)==null){//非告警kpi
                            //以前的当前时间段时间
                            List<Double> valueList=new ArrayList<Double>();
                            for(int b=0; b<beforeTimeList.size(); b++){
                                valueList.add(Double.parseDouble("0"));
                            }                              
                            subMap.put(String.valueOf(thisKpiBusEndTime), valueList);
                            
                        }else{//告警kpi
                            List<Double> valueList=new ArrayList<Double>();
                            for(int b=0; b<beforeTimeList.size(); b++){
                                long beforeTime=beforeTimeList.get(b);
                                //rrd对象获取rrd的方法
                                SimpleEntry<Long, Double> point=tempBusiKpiService.getRrdDataPointByName(//rrd取值
                                       beforeTime,
                                       beforeTime+BaselineTriggerGrain._60S.toInt(),              
                                       BaselineTriggerGrain._60S.toInt(),
                                       tempKpiName,
                                       RrdAlgorithm.AVG);                                 
                                valueList.add(point.getValue());
                            }        
                            subMap.put(String.valueOf(thisKpiBusEndTime), valueList);         
                        }
                        
                        //设置入总map
                        finalBaseLineValueInfo.put(tempKpiName, subMap);
                    }
                        
                    //计算中位数，保存中位数的值  kpiName,<time,value>
                    Map<String, Map<String, Double>> finalBaseLineMedianInfo=new LinkedHashMap<String, Map<String, Double>>();
                        
                    for (Map.Entry<String, Map<String, List<Double>>> finalValueEntry : finalBaseLineValueInfo.entrySet()) {
                        String kpiNameKey=finalValueEntry.getKey();
                        Map<String, List<Double>> timeValueListMap=finalValueEntry.getValue();
                        Map<String, Double> itemMap =AlarmBaseLineCalculateUtil.getBaseLineValue(timeValueListMap);
                        finalBaseLineMedianInfo.put(kpiNameKey, itemMap);
                    }

                    //根据职能告警阈值设置计算中位数的高低阈值 kpi,<Time,<0/1,value>>
                    Map<String, Map<String, Map<String, Double>>> finalBaseLineLowHighVal=new 
                                LinkedHashMap<String, Map<String, Map<String, Double>>>();

                    for (Map.Entry<String, Map<String, Double>> finalMedianEntry : finalBaseLineMedianInfo.entrySet()) {
                        String kpiNameKey=finalMedianEntry.getKey();
                        Map<String, Double> kpiMedianMap=finalMedianEntry.getValue();
                            //time,0/1,value
                        Map<String, Map<String, Double>> itemMap =AlarmBaseLineCalculateUtil.
                                    getBaseLineLowHighThreshold(kpiMedianMap, kpiAlarmValueMap.get(kpiNameKey));
                        finalBaseLineLowHighVal.put(kpiNameKey, itemMap);
                    }
                        
                    //迭代,这里存在两种特殊的情况，即为：
                    //1.同一业务设置的kpi智能告警不是一个时间设置的，可能跨天了。这种情况，就按照一个kpi的设置的时间走
                    //2.各个业务开启的KPI高低阈值不同，这里，都按照高低阈值都存在了。只不过全为0
                    Map<String, Map<String, List<Number>>> highLowUsedRrdData=AlarmSetBaseLineToMemoryUtil.
                                getInsertRrdData(finalBaseLineLowHighVal);
                        
                    //低阈值循环
                    for(Map.Entry<String, List<Number>> lowEntry : highLowUsedRrdData.get("1").entrySet()){
                        AlarmBaseLineRrdInitCreateUtil alarmBaseLineRrdInitCreateUtill=new AlarmBaseLineRrdInitCreateUtil(
                                    moduleId, 
                                    businessId, 
                                    Long.parseLong(wpId), 
                                    AlarmBaseType.LOW, 
                                    false);
                        alarmBaseLineRrdInitCreateUtill.toCreateUpdate(Long.parseLong(lowEntry.getKey()), lowEntry.getValue());
                    }
                        
                        //高阈值循环
                    for(Map.Entry<String, List<Number>> highEntry : highLowUsedRrdData.get("0").entrySet()){
                        AlarmBaseLineRrdInitCreateUtil alarmBaseLineRrdInitCreateUtill=new AlarmBaseLineRrdInitCreateUtil(
                                    moduleId, 
                                    businessId, 
                                    Long.parseLong(wpId), 
                                    AlarmBaseType.HIGH, 
                                    false);
                        alarmBaseLineRrdInitCreateUtill.toCreateUpdate(Long.parseLong(highEntry.getKey()), highEntry.getValue());
                    }
                                                                    
                    //验证告警，判断是否产生告警
                    if(valueinfo!=null && valueinfo.size()>0){
                        for(int x=0; x<valueinfo.size(); x++){
                            //告警设置对象
                            AlarmRemoveRedundancyBlBean arrblBean=valueinfo.get(x);
                            //kpi信息
                            AlarmKpiBean tempAlarmKpiBean=alarmKpiDao.getAlarmKpiById(arrblBean.getKpiId());
                            long kpiId=tempAlarmKpiBean.getKpiId();
                            KpisBean kpisBean=kpisDao.getKpisById((int)kpiId);
                            String kpiName=kpisBean.getName();
                            long highLowBaselineFlag=arrblBean.getHighLowBaselineFlag();
                            
                            //当前业务的kpi值
                            SimpleEntry<Long, Double> basicPoint=tempBusiKpiService.getRrdDataPointByName(//rrd取值
                                    startTime,
                                    endTime,                     
                                    BaselineTriggerGrain._60S.toInt(),
                                    kpiName,
                                    RrdAlgorithm.AVG);
                            
                            //当前时间基线的低阈值
                            SimpleEntry<Long, Double> lowPoint=AlarmBaseLineCalculateUtil.
                                    getRrdCurrentKpiValueFromFinalData(highLowUsedRrdData.get("1"), kpiColumns, kpiName);
                            
                            //当前时间基线的高阈值
                            SimpleEntry<Long, Double> highPoint=AlarmBaseLineCalculateUtil.
                                    getRrdCurrentKpiValueFromFinalData(highLowUsedRrdData.get("0"), kpiColumns, kpiName);

                            //key
                            StringBuffer buf=new StringBuffer();
                            buf.append(moduleId);
                            buf.append(",");
                            buf.append(wpId);
                            buf.append(",");
                            buf.append(businessId);
                            buf.append(",");
                            buf.append(kpitype);
                            buf.append(",");
                            buf.append(kpiId);
                            buf.append(",");
                            buf.append(highLowBaselineFlag);
                            
                            setToCollection(arrblBean, buf.toString(), basicPoint, alarmAlgorithmBean, lowPoint, highPoint);
                        }
                    }
                }
            }
        }
    }
         
    /**
     * 
     * @Title: setToCollection
     * @Description: 产生智能告警
     * @param arrblBean 对象信息
     * @param key key
     * @param point 基本值
     * @param alarmAlgorithmBean 算法
     * @param lowPoint 低阈值
     * @param highPoint void
     * @author chensq
     */
    public static void setToCollection(
            AlarmRemoveRedundancyBlBean arrblBean,
            String key,
            SimpleEntry<Long, Double> point,
            AlarmAlgorithmBean alarmAlgorithmBean,
            SimpleEntry<Long, Double> lowPoint,
            SimpleEntry<Long, Double> highPoint){
        //初始化
        if (checkBaseLineAlarmMinuteBusiList == null) {
            checkBaseLineAlarmMinuteBusiList=new CopyOnWriteArrayList<Map<String, AlarmCheckCountBean>>();
        }
        
        //验证以前是否存在过该key
        boolean beforeIsHave=false; //false未存在过  true存在过
        if (checkBaseLineAlarmMinuteBusiList!=null && checkBaseLineAlarmMinuteBusiList.size()>0) {
            for (int x=0; x<checkBaseLineAlarmMinuteBusiList.size(); x++) {
                Map<String, AlarmCheckCountBean> tempItemsMap=checkBaseLineAlarmMinuteBusiList.get(x);
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
            AlarmCheckCountBean itemCheckCountReturn=AlarmAlgorithmBaseLineUtil.compareBaseLineValue(arrblBean,
                    alarmAlgorithmBean, point, alarmCheckCountBean, lowPoint, highPoint);
            
            //item
            Map<String, AlarmCheckCountBean> checkAlarmItemMap=new ConcurrentHashMap<String, AlarmCheckCountBean>();
            checkAlarmItemMap.put(key, itemCheckCountReturn);
            
            checkBaseLineAlarmMinuteBusiList.add(checkAlarmItemMap);
        } else {//已设置
             
            try {
               //设置入集合中
                for (int x=0; x<checkBaseLineAlarmMinuteBusiList.size(); x++) {
                    Map<String, AlarmCheckCountBean> tempItemsMap= checkBaseLineAlarmMinuteBusiList.get(x);
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
                            AlarmCheckCountBean itemCheckCountReturn=AlarmAlgorithmBaseLineUtil.compareBaseLineValue(arrblBean,
                                    alarmAlgorithmBean, point, valueBefore, lowPoint, highPoint);
                            //判断返回结果的状态
                            int checkResult=itemCheckCountReturn.getEndFlag();
                            if (checkResult==1) {// 1为继续
                                tempItemsMap.put(key, itemCheckCountReturn);
                            } else if (checkResult==2){//2为清除
                                checkBaseLineAlarmMinuteBusiList.remove(tempItemsMap);
//                                its.remove();
                            } else {//3为符合告警条件
                                AlarmThreadInsertBean insertBean=new AlarmThreadInsertBean();
                                insertBean.setKey(itemKey);
                                insertBean.setCheckCountBean(itemCheckCountReturn);
                                insertBean.setAlarmLogDao(alarmLogDao);
                                insertBean.setSyslogService(syslogService);
                                
                                //添加基线告警记录告警
                                boolean insertFlag=AlarmThreadInsertUtil.doInsertAlarm(insertBean);
                                if (insertFlag) {//添加成功
                                    checkBaseLineAlarmMinuteBusiList.remove(tempItemsMap);
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


