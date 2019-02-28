/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmRemoveRedundancyBlBean.java
 *创建人: chensq    创建时间: 2018年8月20日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmRemoveRedundancyBlBean
 * @Description: 去掉冗余bean
 * @author chensq
 *
 */
public class AlarmRemoveRedundancyBlBean {
    /**
     * @Fields alarmValueList :  存放高低阈值信息  demo:0-2.0|1-3.0
     */
    private String alarmValueList;

    /**
     * @Fields businessId :  业务id
     */
    private long businessId;
    
    /**
     * @Fields highLowBaselineFlag :  高、低、基线等类型id
     */
    private long highLowBaselineFlag;
    
    /**
     * @Fields idList :  告警设置id
     */
    private String idList;
    
    /**
     * @Fields kpiId :  kpis id
     */
    private long kpiId;
    
    /**
     * @Fields moduleId :  模块id
     */
    private long moduleId;
        
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;
      
    /**
     * <br />获取 <font color="red"><b>alarmValueList<b/></font>
     * @return alarmValueList alarmValueList
     */
    public String getAlarmValueList() {
        return alarmValueList;
    }

    /**  
     * <br />设置 <font color='#333399'><b>alarmValueList</b></font>
     * @param alarmValueList alarmValueList  
     */
    public void setAlarmValueList(String alarmValueList) {
        this.alarmValueList = alarmValueList;
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
     * <br />获取 <font color="red"><b>highLowBaselineFlag<b/></font>
     * @return highLowBaselineFlag highLowBaselineFlag
     */
    public long getHighLowBaselineFlag() {
        return highLowBaselineFlag;
    }

    /**  
     * <br />设置 <font color='#333399'><b>highLowBaselineFlag</b></font>
     * @param highLowBaselineFlag highLowBaselineFlag  
     */
    public void setHighLowBaselineFlag(long highLowBaselineFlag) {
        this.highLowBaselineFlag = highLowBaselineFlag;
    }

    /**
     * <br />获取 <font color="red"><b>idList<b/></font>
     * @return idList idList
     */
    public String getIdList() {
        return idList;
    }

    /**  
     * <br />设置 <font color='#333399'><b>idList</b></font>
     * @param idList idList  
     */
    public void setIdList(String idList) {
        this.idList = idList;
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
   
}
