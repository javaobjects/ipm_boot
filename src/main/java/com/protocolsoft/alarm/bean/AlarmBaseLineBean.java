/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineBean.java
 *创建人: chensq    创建时间: 2018年5月24日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmBaseLineBean
 * @Description: 智能基线告警bean
 * @author chensq
 *
 */
public class AlarmBaseLineBean {
    /**
     * @Fields id : id
     */
    private long id;
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
    /**
     * @Fields kpiId : kpiId
     */
    private long kpiId; //ipm_alarm_kpi中的kpi_id 或者 ipm_kpis的id
    /**
     * @Fields tempFlag : 临时修改标记 0：未修改 1：刚修改
     */
    private long tempFlag;
    /**
     * @Fields kpiName : kpi Name
     */
    private String kpiName;
    /**
     * @Fields startTime : 开始时间
     */
    private long startTime;
    /**
     * @Fields endTime : 结束时间
     */
    private long endTime;
    /**
     * @Fields stepTime : 设定一次更新的秒数
     */
    private long stepTime;

    /**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public long getId() {
        return id;
    }
    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * <br />获取 <font color="red"><b>watchpointId<b/></font>
     * @return watchpointId watchpointId
     */
    public long getWatchpointId() {
        return watchpointId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>watchpointId</b></font>
     * @param watchpointId watchpointId  
     */
    public void setWatchpointId(long watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * <br />获取 <font color="red"><b>moduleId<b/></font>
     * @return moduleId moduleId
     */
    public long getModuleId() {
        return moduleId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>moduleId</b></font>
     * @param moduleId moduleId  
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }
    /**
     * <br />获取 <font color="red"><b>businessId<b/></font>
     * @return businessId businessId
     */
    public long getBusinessId() {
        return businessId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>businessId</b></font>
     * @param businessId businessId  
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }
    /**
     * <br />获取 <font color="red"><b>kpiId<b/></font>
     * @return kpiId kpiId
     */
    public long getKpiId() {
        return kpiId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kpiId</b></font>
     * @param kpiId kpiId  
     */
    public void setKpiId(long kpiId) {
        this.kpiId = kpiId;
    }
    /**
     * <br />获取 <font color="red"><b>tempFlag<b/></font>
     * @return tempFlag tempFlag
     */
    public long getTempFlag() {
        return tempFlag;
    }
    /**  
     * <br />设置 <font color='#333399'><b>tempFlag</b></font>
     * @param tempFlag tempFlag  
     */
    public void setTempFlag(long tempFlag) {
        this.tempFlag = tempFlag;
    }
    /**
     * <br />获取 <font color="red"><b>kpiName<b/></font>
     * @return kpiName kpiName
     */
    public String getKpiName() {
        return kpiName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kpiName</b></font>
     * @param kpiName kpiName  
     */
    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }
    /**
     * <br />获取 <font color="red"><b>startTime<b/></font>
     * @return startTime startTime
     */
    public long getStartTime() {
        return startTime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>startTime</b></font>
     * @param startTime startTime  
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    /**
     * <br />获取 <font color="red"><b>endTime<b/></font>
     * @return endTime endTime
     */
    public long getEndTime() {
        return endTime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>endTime</b></font>
     * @param endTime endTime  
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    /**
     * <br />获取 <font color="red"><b>stepTime<b/></font>
     * @return stepTime stepTime
     */
    public long getStepTime() {
        return stepTime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>stepTime</b></font>
     * @param stepTime stepTime  
     */
    public void setStepTime(long stepTime) {
        this.stepTime = stepTime;
    }
    
}
