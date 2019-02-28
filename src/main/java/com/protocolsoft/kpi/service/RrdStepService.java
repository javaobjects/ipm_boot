/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: StepUtils.java
 *创建人: WWW    创建时间: 2018年7月4日
 */
package com.protocolsoft.kpi.service;

/**
 * @ClassName: RrdStepUtils
 * @Description: RRD粒度
 * @author WWW
 *
 */
class RrdStepService {

    /**
     * 
     * @Title: getStep
     * @Description: 获取粒度，注意，粒度或者最大保存时间有变动时，记得修改"AlarmBaseLineStepSaveMaxTimeUtil.java"
     * @param moduleId 模块
     * @param time 时间
     * @param step 粒度
     * @return int
     * @author www
     */
    static int getStep(int moduleId, long time, int step) {
        // 粒度等于0时，需要计算粒度
        if (step == 0) { 
            if (moduleId == 8) { // URL
                step = getUrlStep(time);
            } else if (moduleId == 9) { // 报文 
                step = getMsgStep(time);
            } else { // 其他
                step = getStep(time);
            }
        }
        
        return step;
    }
    
    /**
     * 
     * @Title: getStep
     * @Description: 获取粒度
     * @param time 开始时间
     * @return int 粒度
     * @author www
     */
    static int getStep(long time) {
        int step = 0;
        long ctmp = (System.currentTimeMillis() / 1000) - time;
        if (ctmp <= 14400) { // 10秒  --> 4个小时内
            step = 10;
        } else if (ctmp <= 86400) { // 1分钟  --> 24小时内
            step = 60;
        } else if (ctmp <= 864000) { // 10分钟  --> 10天内
            step = 600;
        } else if (ctmp <= 20736000) { // 4小时 --> 240天内
            step = 14400;
        }
        
        return step;
    }
    
    /**
     * 
     * @Title: getCustomStep
     * @Description: 获取URL粒度
     * @param time 
     * @return int
     * @author www
     */
    private static int getUrlStep(long time) {
        int step = 0;
        long ctmp = (System.currentTimeMillis() / 1000) - time;
        if (ctmp <= 86400) { // 1分钟  --> 24小时内
            step = 60;
        } else if (ctmp <= 604800) { // 10分钟  --> 一周内
            step = 600;
        } else if (ctmp <= 7776000){ // 1小时 --> 90天内
            step = 3600;
        } else { // 1天 --> 1年内
            step = 86400;
        }
        
        return step;
    }
    
    /**
     * 
     * @Title: getCustomStep
     * @Description: 获取报文粒度
     * @param time 
     * @return int
     * @author www
     */
    private static int getMsgStep(long time) {
        int step = 0;
        long ctmp = (System.currentTimeMillis() / 1000) - time;
        if (ctmp <= 604800) { // 1分钟  --> 一周内
            step = 60;
        } else if (ctmp <= 2592000) { // 10分钟  --> 一月内
            step = 600;
        } else { // 1小时 --> 一年内
            step = 3600;
        }
        
        return step;
    }
}
