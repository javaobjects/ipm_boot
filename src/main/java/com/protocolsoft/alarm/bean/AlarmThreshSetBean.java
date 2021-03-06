/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreshSetBean
 *创建人:chensq    创建时间:2018年3月28日
 */
package com.protocolsoft.alarm.bean;

/**
 * 上下阈值对象
 * 2018年3月28日 下午3:50:26
 * @author chensq
 * @version
 * @see
 */
public class AlarmThreshSetBean {
   
    /**
     * @Fields lowThresh : 下阈值
     */
    private Double lowThresh;
     
    /**
     * @Fields highThresh : 上阈值
     */
    private Double highThresh;
    
    /**
     * @return the lowThresh
     */
    public Double getLowThresh() {
        return lowThresh;
    }
    /**
     * @param lowThresh the lowThresh to set
     */
    public void setLowThresh(Double lowThresh) {
        this.lowThresh = lowThresh;
    }
    /**
     * @return the highThresh
     */
    public Double getHighThresh() {
        return highThresh;
    }
    /**
     * @param highThresh the highThresh to set
     */
    public void setHighThresh(Double highThresh) {
        this.highThresh = highThresh;
    }
    
}
