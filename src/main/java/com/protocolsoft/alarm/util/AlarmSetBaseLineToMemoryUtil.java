/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmSetBaseLineToMemoryUtil.java
 *创建人: chensq    创建时间: 2018年5月29日
 */
package com.protocolsoft.alarm.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.protocolsoft.alarm.bean.AlarmBaseLineBean;
import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
import com.protocolsoft.alarm.service.AlarmKpisService;
import com.protocolsoft.kpi.bean.RetainTimeBean;

/**
 * @ClassName: AlarmSetBaseLineToMemoryUtil
 * @Description: 智能告警使用，区分不同线程时间批次与kpis的工具类
 * @author chensq
 *
 */
public class AlarmSetBaseLineToMemoryUtil {
       
    /**
     * @Title: getCurrentKey
     * @Description: 获取当前类使用的key的方法
     * @param alarmSetBean
     * @param watchpointId
     * @return String
     * @author chensq
     */
    public static String getCurrentKey(AlarmSetBean alarmSetBean, long watchpointId) {
        long moduleId=alarmSetBean.getModuleId();
        long businessId=alarmSetBean.getBusinessId();

        StringBuffer buf=new StringBuffer();
        buf.append(moduleId);
        buf.append(",");
        buf.append(watchpointId);
        buf.append(",");
        buf.append(businessId);
        
        return buf.toString();
    }
        
    /**
     * 
     * @Title: getColumns
     * @Description: 经过排序的kpi名称，获取当前业务所有告警的kpi名称
     * @param moduleId 模块名称
     * @return List<String>
     * @author chensq
     */
    public static List<String> getColumns(long moduleId){
        //定义集合
        List<String> list =new ArrayList<String>();
        //获取所有的模块下的告警kpi项目
        Map<String, String> kpisIdNameMap=AlarmKpisService.alarmKpisMap.get(String.valueOf(moduleId));
        if(moduleId!=2 && moduleId!=1){
            if(moduleId==10 || moduleId==11 || moduleId==12){
                kpisIdNameMap.put("900", "bandWidth");
            }
        }
        //迭代
        for (Map.Entry<String, String> entry : kpisIdNameMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }
    
    
    
    /**
     * 
     * @Title: busKpisCountMap
     * @Description: 判断当前业务下的智能告警喂数据是否都完成了
     * @param outKpiIdList
     * @return boolean
     * @author chensq
     */
    public static boolean busKpisAllFinish(List<AlarmRemoveRedundancyBlBean> valueinfo, List<AlarmBaseLineBean> outKpiIdList){
        //返回
        boolean flag=false;
 
        //step3通过查询数据库迭代数据库的基线告警设置
        int count=0; //计数器
        if(outKpiIdList!=null && outKpiIdList.size()>0){
            for(int x=0; x<outKpiIdList.size(); x++){
                AlarmBaseLineBean tempBean= outKpiIdList.get(x);
                long startTimeTemp=tempBean.getStartTime();
                long etartTimeTemp=tempBean.getEndTime();                
                if((etartTimeTemp-startTimeTemp)>=AlarmConstantUtil.CHECK_BASELINE_TIME){
                    count++;
                }
            }
        }
        
        //比对
        if(valueinfo.size()==count){
            flag=true;
        }
        
        return flag;
    }
    
    
    /**
     * @Title: getInsertRrdData
     * @Description: 获取初始化或者修改rrd的数据格式
     * @param finalBaseLineLowHighVal                 kpis,time,0/1,value
     * @return Map<String,Map<String,List<Number>>>   0/1, time.values(按照kpi顺序)
     * @author chensq
     */
    public static Map<String, Map<String, List<Number>>> getInsertRrdData(Map<String, Map<String, Map<String, Double>>> finalBaseLineLowHighVal){
        
        //返回值类型
        Map<String, Map<String, List<Number>>> returnMap=new LinkedHashMap<String, Map<String, List<Number>>>();
        //high map
        Map<String, List<Number>> highMap=new LinkedHashMap<String, List<Number>>(); //0  time ,values
        //low map
        Map<String, List<Number>> lowMap=new LinkedHashMap<String, List<Number>>();  //1  time ,values
        
        //迭代
        for (Map.Entry<String, Map<String, Map<String, Double>>> entry : finalBaseLineLowHighVal.entrySet()) {
            entry.getKey(); //kpiName
            Map<String, Map<String, Double>> valueMap=entry.getValue(); //time 0/1 value 
            
            for(Map.Entry<String, Map<String, Double>> subEntry : valueMap.entrySet()){
                String time=subEntry.getKey(); //time 
                Map<String, Double> valueDetailMap=subEntry.getValue(); //0/1 value
                //high
                if(highMap.get(time)==null){
                    List<Number> list=new ArrayList<Number>();
                    list.add(valueDetailMap.get("0"));
                    highMap.put(time, list);
                }else{
                    List<Number> list= highMap.get(time);
                    list.add(valueDetailMap.get("0"));
                }
                //low
                if(lowMap.get(time)==null){
                    List<Number> list=new ArrayList<Number>();
                    list.add(valueDetailMap.get("1"));
                    lowMap.put(time, list);
                }else{
                    List<Number> list= lowMap.get(time);
                    list.add(valueDetailMap.get("1"));
                }
            }
            
        }
        
        returnMap.put("0", highMap);
        returnMap.put("1", lowMap);

        return returnMap;
    }
    
    /**
     * 
     * @Title: getNeedAlarmKpis
     * @Description: 获取某个业务需要智能告警的kpis
     * @param kpiIdList
     * @return Map<String,String> kpiName,kpiName
     * @author chensq
     */
    public static Map<String, String> getNeedAlarmKpis(List<AlarmBaseLineBean> kpiIdList){
        Map<String, String> map=new LinkedHashMap<String, String>();
        if(kpiIdList!=null && kpiIdList.size()>0){
            for(int x=0; x<kpiIdList.size(); x++){
                AlarmBaseLineBean tempBean= kpiIdList.get(x);
                map.put(tempBean.getKpiName(), tempBean.getKpiName());
            }
        }
        return map;
    }
    
    /**
     * @Title: getRetainTimeList
     * @Description: 根据不同的模块id获取不同的枚举值
     * @param moduleId
     * @return List<RetainTimeBean>
     * @author chensq
     */
    public static List<RetainTimeBean> getRetainTimeList(long moduleId){
        List<RetainTimeBean> list=new ArrayList<RetainTimeBean>();
        
        Map<Integer, Long> map=AlarmBaseLineStepSaveMaxTimeUtil.getStepMaxTime((int)moduleId);
        
        RetainTimeBean retainTimeBean=null;
        
        for (Map.Entry<Integer, Long> entry : map.entrySet()) {
            retainTimeBean=new RetainTimeBean();
            retainTimeBean.setStep(entry.getKey());
            retainTimeBean.setTime(entry.getValue());
            list.add(retainTimeBean);
        }
        
        return list;
    }
}
