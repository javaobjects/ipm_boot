/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineStepSaveMaxTime.java
 *创建人: chensq    创建时间: 2018年8月23日
 */
package com.protocolsoft.alarm.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: AlarmBaseLineStepSaveMaxTime
 * @Description: 基线使用，rrd每个粒度的存放时间
 * @author chensq
 *
 */
public class AlarmBaseLineStepSaveMaxTimeUtil {
    /**
     * 
     * @Title: getStepMaxTime
     * @Description: 根据模块id查询出各个模块的rrd粒度以及粒度的最大保存时间
     * @param moduleId 模块id
     * @return Map <Integer, Long>
     * @author chensq
     */
    static Map<Integer, Long> getStepMaxTime(int moduleId){
        Map<Integer, Long> map=new LinkedHashMap<Integer, Long>();
        if (moduleId == 8) { // URL
            map.put(60, 86400L); //24小时
            map.put(600, 604800L); //一周
            map.put(3600, 7776000L); //90天
            map.put(86400, 31536000L); //一年
        } else if (moduleId == 9) { // 报文 
            map.put(60, 604800L); //一周
            map.put(600, 2592000L); //一月内
            map.put(3600, 31536000L); //一年
        } else { // 其他
            map.put(10, 14400L); //4个小时内
            map.put(60, 86400L); //24小时内
            map.put(600, 864000L); //10天内
            map.put(14400, 20736000L); //240天内
        }
        return map;
    }
}
