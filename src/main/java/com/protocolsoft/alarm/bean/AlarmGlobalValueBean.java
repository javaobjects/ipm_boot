/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmGlobalValueBean
 *创建人:chensq    创建时间:2018年3月5日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmGlobalValueBean
 * @Description: 告警全局设置阈值表
 * @author chensq
 *
 */
public class AlarmGlobalValueBean {    
    /**
     * @Fields id : id
     */
    private long id;
     
    /**
     * @Fields globalsetId : globalseId
     */
    private long globalsetId;
    
    /**
     * @Fields levelId : 告警级别id
     */
    private int levelId;
    
    /**
     * @Fields triggerflag : 阈值设置类性(上下阈值功能) 0为上阈值(高阈值)，1为下阈值(低阈值)
     */
    private int triggerflag;
    
    /**
     * @Fields triggerusestatus : 阈值设置类性上下阈值使用状态   0为启用，1为不启用
     */
    private int triggerusestatus;
  
    /**
     * @Fields triggerValue : 告警级别具体值
     */
    private Double triggerValue;
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the globalsetId
     */
    public long getGlobalsetId() {
        return globalsetId;
    }
    /**
     * @param globalsetId the globalsetId to set
     */
    public void setGlobalsetId(long globalsetId) {
        this.globalsetId = globalsetId;
    }
    /**
     * @return the levelId
     */
    public int getLevelId() {
        return levelId;
    }
    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
    /**
     * @return the triggerflag
     */
    public int getTriggerflag() {
        return triggerflag;
    }
    /**
     * @param triggerflag the triggerflag to set
     */
    public void setTriggerflag(int triggerflag) {
        this.triggerflag = triggerflag;
    }
    /**
     * @return the triggerusestatus
     */
    public int getTriggerusestatus() {
        return triggerusestatus;
    }
    /**
     * @param triggerusestatus the triggerusestatus to set
     */
    public void setTriggerusestatus(int triggerusestatus) {
        this.triggerusestatus = triggerusestatus;
    }
    /**
     * @return the triggerValue
     */
    public Double getTriggerValue() {
        return triggerValue;
    }
    /**
     * @param triggerValue the triggerValue to set
     */
    public void setTriggerValue(Double triggerValue) {
        this.triggerValue = triggerValue;
    }

}
