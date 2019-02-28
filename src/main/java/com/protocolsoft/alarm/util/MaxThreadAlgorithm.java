/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MaxThreadAlgorithm
 *创建人:chensq    创建时间:2017年11月8日
 */
package com.protocolsoft.alarm.util;

import java.util.Map;

/**
 * @ClassName: MaxThreadAlgorithm
 * @Description: 线程最大数算法
 * @author chensq
 *
 */
public class MaxThreadAlgorithm {   
    
    /**
     * @Title: getMaxThreadNum
     * @Description: 获取最大的线程数
     * @return long
     * @author chensq
     */
    public static long getMaxThreadNum(){
        //默认情况
        long maxThread=1;
        
        Map<String, Long> map=AlarmNeedSystemInfo.getPerformanceInfoUbuntu();
        Long successFlag=map.get("success");
        if (successFlag==1) {
            Long totalCpuNum=map.get("totalCpuNum");
            maxThread= totalCpuNum * maxThread;
        } 
        return maxThread;
    }
    
    
    
}
