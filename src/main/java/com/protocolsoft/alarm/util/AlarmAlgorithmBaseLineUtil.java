/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmAlgorithmBaseLineUtil.java
 *创建人: chensq    创建时间: 2018年7月23日
 */
package com.protocolsoft.alarm.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import com.protocolsoft.alarm.bean.AlarmAlgorithmBean;
import com.protocolsoft.alarm.bean.AlarmCheckCountBean;
import com.protocolsoft.alarm.bean.AlarmRemoveRedundancyBlBean;

/**
 * @ClassName: AlarmAlgorithmBaseLineUtil
 * @Description: 智能告警的值比较
 * @author chensq
 *
 */
public class AlarmAlgorithmBaseLineUtil {

    /**
     * 
     * @Title: compareBaseLineValue
     * @Description: 智能告警验证
     * @param alarmSetBean
     * @param alarmAlgorithmBean
     * @param point
     * @param alarmCheckCountBean
     * @param lowPoint
     * @param highPoint
     * @return AlarmCheckCountBean
     * @author chensq
     */
    public static AlarmCheckCountBean compareBaseLineValue(
            AlarmRemoveRedundancyBlBean arrblBean,
            AlarmAlgorithmBean alarmAlgorithmBean,
            SimpleEntry<Long, Double> point,
            AlarmCheckCountBean alarmCheckCountBean,
            SimpleEntry<Long, Double> lowPoint,
            SimpleEntry<Long, Double> highPoint){
 
        //算法参数
        String []algorithminfoArray=alarmAlgorithmBean.getAlgorithminfo().split(",");
        int minuteParam=Integer.parseInt(algorithminfoArray[0]);
        int countParam=Integer.parseInt(algorithminfoArray[1]);
         
        //恒设置
        alarmCheckCountBean.setEndtime(point.getKey());
        alarmCheckCountBean.setTriggerflag(arrblBean.getHighLowBaselineFlag());      
        
        //通过高低阈值验证
        //时间超过  并且 阈值次数超过  标识值
        Map<String, Integer> levelValueMap=alarmCheckCountBean.getLevelValueMap();
        //init
        if (levelValueMap==null) {
            levelValueMap=new LinkedHashMap<String, Integer>();
            levelValueMap.put("0", 0);
            levelValueMap.put("1", 0);
        }
        
        int lowNumCount=levelValueMap.get("1");
        int highNumCount=levelValueMap.get("0");
        int checkResult=0;
        
        //与低阈值比较
        if(lowPoint!=null && lowPoint.getValue()!=0){
            if(point.getValue()<=lowPoint.getValue()){
                lowNumCount++;
                levelValueMap.put("1", lowNumCount);
            }
        }
        
        //与高阈值比较
        if(highPoint!=null && highPoint.getValue()!=0){
            if(point.getValue()>=highPoint.getValue()){
                highNumCount++;
                levelValueMap.put("0", highNumCount);
            }
        }
        
        //恒重新设置
        alarmCheckCountBean.setLevelValueMap(levelValueMap);
        
        if(lowNumCount+highNumCount>0){
            if(alarmCheckCountBean.getFirstCount()==0){
                alarmCheckCountBean.setStarttime(point.getKey());
                alarmCheckCountBean.setFirstCount(1);
            }
        }
        //验证超限个数
        if (lowNumCount+highNumCount >= countParam) {
            checkResult++;
        }
        
        //阈值时间进行比较(开始时间与结束时间间隔大于设定分钟) 结束条件
        int recalculate=1; // 1 未超时 2 超时
        if (alarmCheckCountBean.getEndtime()-alarmCheckCountBean.getStarttime() >= minuteParam * 60) {
            recalculate=2;
            checkResult++;
        }
        
        if (checkResult==2) {//符合告警条件
            alarmCheckCountBean.setEndFlag(3); 
            alarmCheckCountBean.setFinalAlarmSetId(Integer.parseInt(arrblBean.getIdList()));
        } else {
            alarmCheckCountBean.setEndFlag(recalculate); // 1 可继续计算   2停止计算 
        }
     
        return alarmCheckCountBean;
    }
    

}
