/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmTriggerBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmTriggerBean
 * @Description: 告警阈值历史实体类(普通阈值)
 * @author chensq
 *
 */
public class AlarmTriggerBean {
    
    /**
     * @Fields id : id
     */
    private long id;
   
    /**
     * @Fields alarmsetId : 告警设置id
     */
    private long alarmsetId;
    
    /**
     * @Fields triggerflag : 阈值设置类性(上下阈值功能) 0为上阈值，1为下阈值
     */
    private int triggerflag;
    
    /**
     * @Fields triggerusestatus : 阈值设置类性上下阈值使用状态   0为启用，1为不启用
     */
    private int triggerusestatus;
 
    /**
     * @Fields triggervalue : 阈值
     */
    private Double triggervalue;
    
    /**
     * @Fields triggerunit : 阈值单位
     */
    private String triggerunit;
     
    /**
     * @Fields triggertime : 阈值时间
     */
    private long triggertime;
   
    /**
     * @Fields triggertimeStr : 阈值时间str
     */
    private String triggertimeStr;
    
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
     * @return the alarmsetId
     */
    public long getAlarmsetId() {
        return alarmsetId;
    }
    /**
     * @param alarmsetId the alarmsetId to set
     */
    public void setAlarmsetId(long alarmsetId) {
        this.alarmsetId = alarmsetId;
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
     * @return the triggervalue
     */
    public Double getTriggervalue() {
        return triggervalue;
    }
    /**
     * @param triggervalue the triggervalue to set
     */
    public void setTriggervalue(Double triggervalue) {
        this.triggervalue = triggervalue;
    }
    /**
     * @return the triggerunit
     */
    public String getTriggerunit() {
        return triggerunit;
    }
    /**
     * @param triggerunit the triggerunit to set
     */
    public void setTriggerunit(String triggerunit) {
        this.triggerunit = triggerunit;
    }
    /**
     * @return the triggertime
     */
    public long getTriggertime() {
        return triggertime;
    }
    /**
     * @param triggertime the triggertime to set
     */
    public void setTriggertime(long triggertime) {
        this.triggertime = triggertime;
    }
    /**
     * @return the triggertimeStr
     */
    public String getTriggertimeStr() {
        return triggertimeStr;
    }
    /**
     * @param triggertimeStr the triggertimeStr to set
     */
    public void setTriggertimeStr(String triggertimeStr) {
        this.triggertimeStr = triggertimeStr;
    }
    
    
}
