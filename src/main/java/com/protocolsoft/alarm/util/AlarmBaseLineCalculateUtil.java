/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineCalculateUtil.java
 *创建人: chensq    创建时间: 2018年6月6日
 */
package com.protocolsoft.alarm.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import com.protocolsoft.utils.MathUtlis;

/**
 * @ClassName: AlarmBaseLineCalculateUtil
 * @Description: 智能告警计算
 * @author chensq
 *
 */
public class AlarmBaseLineCalculateUtil {
    
    /**
     * @Title: getBaseLineValue
     * @Description: 获取每组的中位数值
     * @param innerMap
     * @return Map<String,Double>
     * @author chensq
     */
    public static Map<String, Double> getBaseLineValue(Map<String, List<Double>> innerMap){
        Map<String, Double> map=null;
        if(innerMap!=null && innerMap.size()>0){
            map=new LinkedHashMap<String, Double>();
            for (Map.Entry<String, List<Double>> entry : innerMap.entrySet()) {
                String key=entry.getKey();
                List<Double> list=entry.getValue();
                Double blAvg=MathUtlis.getMedian(list);
                map.put(key, blAvg);
            }
        }
        return map;
    }
    
    /**
     * 
     * @Title: getAlarmValueMap
     * @Description: 根据阈值信息获取高低阈值
     * @param alarmValueList
     * @return Map<String,Double>
     * @author chensq
     */
    public static Map<String, Double> getAlarmValueMap(String alarmValueList){
        Map<String, Double> map=new HashMap<String, Double>();
        String [] baselineTrigger=alarmValueList.split("\\|");
        if(baselineTrigger!=null && baselineTrigger.length>0){
            for(int x=0; x<baselineTrigger.length; x++){
                String temp=baselineTrigger[x];
                String []subStr=temp.split("-");
                map.put(subStr[0], Double.parseDouble(subStr[1]));
            }
        }
        return map;
    }
    
    /**
     * @Title: isHaveLow
     * @Description: 基线的阈值信息中是否有低阈值智能告警
     * @param alarmValueList
     * @return boolean
     * @author chensq
     */
    public static boolean isHaveLow(String alarmValueList){
        //返回boolean
        boolean flag=false;
        
        String [] baselineTrigger=alarmValueList.split("\\|");
        
        if(baselineTrigger!=null && baselineTrigger.length>0){
            for(int x=0; x<baselineTrigger.length; x++){
                String temp=baselineTrigger[x];
                String []subStr=temp.split("-");
                if("1".endsWith(subStr[0])){
                    flag=true;
                }
            }
        }
        
        return flag;
    }
    
    /**
     * @Title: isHaveHigh
     * @Description: 基线的阈值信息中是否有高阈值智能告警
     * @param alarmValueList
     * @return boolean
     * @author chensq
     */
    public static boolean isHaveHigh(String alarmValueList){
        //返回boolean
        boolean flag=false;
        
        String [] baselineTrigger=alarmValueList.split("\\|");
        
        if(baselineTrigger!=null && baselineTrigger.length>0){
            for(int x=0; x<baselineTrigger.length; x++){
                String temp=baselineTrigger[x];
                String []subStr=temp.split("-");
                if("0".endsWith(subStr[0])){
                    flag=true;
                }
            }
        }
        
        return flag;
    }
    
    /**
     * @Title: getBaseLineLowHighThreshold
     * @Description: 根据中位数以及阈值设置信息获取高低阈值,以后的布林算法就在这里完善
     * @param kpiMedianMap
     * @param alarmValueMap
     * @return Map<String,Map<String,Double>>
     * @author chensq
     */
    public static Map<String, Map<String, Double>> getBaseLineLowHighThreshold(Map<String, Double> kpiMedianMap, Map<String, Double> alarmValueMap){
        //返回值
        Map<String, Map<String, Double>> timeHighLowMap=new LinkedHashMap<String,  Map<String, Double>>();
         
        //处理
        for (Map.Entry<String, Double> entry : kpiMedianMap.entrySet()) {
            //中位数值
            String key= entry.getKey();            
            Double value= entry.getValue();
            
            //subMap
            Map<String, Double> subMap=new HashMap<String, Double>();
            //high
            if(alarmValueMap!=null && alarmValueMap.get("0")!=null){
                Double valueh=value==null?0:MathUtlis.addTractionPercent(value, alarmValueMap.get("0"));
                subMap.put("0", valueh);
            }else{
                subMap.put("0", Double.parseDouble("0"));
            }
            //low
            if(alarmValueMap!=null && alarmValueMap.get("1")!=null){
                Double valuel=value==null?0:MathUtlis.subTractionPercent(value, alarmValueMap.get("1"));
                subMap.put("1", valuel);
            }else{
                subMap.put("1", Double.parseDouble("0"));
            }
            
            timeHighLowMap.put(key, subMap);
        }
                
        return timeHighLowMap;
    }
    
    /**
     * 
     * @Title: getRrdCurrentKpiValueFromFinalData
     * @Description: 根据计算最终的rrd值，当前模块的kpis列表，当前需要获取值得kpi名称获取其具体值
     * @param highOrLowMap
     * @param kpiColumns
     * @param kpiName
     * @return double
     * @author chensq
     */
    public static SimpleEntry<Long, Double> getRrdCurrentKpiValueFromFinalData(
            Map<String, List<Number>> highOrLowMap, 
            List<String> kpiColumns, 
            String kpiName){
        //init
        List<Number> numberList=null;
        //具体游标值
        int index=0;
        //time
        String time=null;
        //迭代map
        for(Map.Entry<String, List<Number>> highOrLowEntry : highOrLowMap.entrySet()){
            //获取值的集合
            time= highOrLowEntry.getKey();
            numberList= highOrLowEntry.getValue();
        }
        //迭代kpiName
        if(kpiColumns!=null && kpiColumns.size()>0){
            for(int x=0; x<kpiColumns.size(); x++){
                String itemKpiName=kpiColumns.get(x);
                if(itemKpiName.equalsIgnoreCase(kpiName)){
                    index=x;
                }
            }
        }
        
        //返回SimpleEntry对象
        SimpleEntry<Long, Double> entry = new SimpleEntry<Long, Double>(Long.parseLong(time), numberList.get(index).doubleValue());
        
        return entry;
    }
    
}
