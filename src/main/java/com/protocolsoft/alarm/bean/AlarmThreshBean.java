/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmThreshBean
 *创建人:chensq    创建时间:2018年3月28日
 */
package com.protocolsoft.alarm.bean;

/**
 * 告警设置阈值bean
 * 2018年3月28日 下午3:52:59
 * @author chensq
 * @version
 * @see
 */
public class AlarmThreshBean {    
    
    /**
     * @Fields single : 单独值
     */
    private AlarmThreshSetBean single;
     
    /**
     * @Fields global : 全局值
     */
    private AlarmThreshSetBean global;
    
    /**
     * @return the single
     */
    public AlarmThreshSetBean getSingle() {
        return single;
    }
    /**
     * @param single the single to set
     */
    public void setSingle(AlarmThreshSetBean single) {
        this.single = single;
    }
    /**
     * @return the global
     */
    public AlarmThreshSetBean getGlobal() {
        return global;
    }
    /**
     * @param global the global to set
     */
    public void setGlobal(AlarmThreshSetBean global) {
        this.global = global;
    }
    
}
