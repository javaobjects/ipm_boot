/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiBean
 *创建人:chensq    创建时间:2018年4月4日
 */
package com.protocolsoft.alarm.bean;
 
/**
 * @ClassName: AlarmCustomUnionKpiBean
 * @Description: 告警组合kpi Bean
 * @author chensq
 *
 */
public class AlarmCustomUnionKpiBean {
    //-------------list返回使用的字段-------------
    /**
     * @Fields id : kpi's id
     */
    private long id;
   
    /**
     * @Fields kpiName : kpi 英文
     */
    private String kpiName;
    
    /**
     * @Fields nameValue : kpi 中文
     */
    private String nameValue;
  
    /**
     * @Fields kpiUnit : kpi 单位
     */
    private String kpiUnit;
    
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
    
    /**
     * @Fields groupKpiSelected : 选中状态
     */
    private int groupKpiSelected; // 0未选中过   1选中过

    /**
     * @Fields groupKpiCanUsed : 可用状态
     */
    private int groupKpiCanUsed; // 0不可用  1可用
    
    //-------------list返回使用的字段-------------
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
     * @return the kpiName
     */
    public String getKpiName() {
        return kpiName;
    }
    /**
     * @param kpiName the kpiName to set
     */
    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }
    /**
     * @return the nameValue
     */
    public String getNameValue() {
        return nameValue;
    }
    /**
     * @param nameValue the nameValue to set
     */
    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }
    /**
     * @return the kpiUnit
     */
    public String getKpiUnit() {
        return kpiUnit;
    }
    /**
     * @param kpiUnit the kpiUnit to set
     */
    public void setKpiUnit(String kpiUnit) {
        this.kpiUnit = kpiUnit;
    }
    /**
     * @return the groupKpiSelected
     */
    public int getGroupKpiSelected() {
        return groupKpiSelected;
    }
    /**
     * @param groupKpiSelected the groupKpiSelected to set
     */
    public void setGroupKpiSelected(int groupKpiSelected) {
        this.groupKpiSelected = groupKpiSelected;
    }
    /**
     * @return the groupKpiCanUsed
     */
    public int getGroupKpiCanUsed() {
        return groupKpiCanUsed;
    }
    /**
     * @param groupKpiCanUsed the groupKpiCanUsed to set
     */
    public void setGroupKpiCanUsed(int groupKpiCanUsed) {
        this.groupKpiCanUsed = groupKpiCanUsed;
    }
    /**
     * @return the moduleId
     */
    public long getModuleId() {
        return moduleId;
    }
    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }
    /**
     * @return the businessId
     */
    public long getBusinessId() {
        return businessId;
    }
    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }   
    
}
