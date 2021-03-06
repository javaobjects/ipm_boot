/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmGlobalSetBean
 *创建人:chensq    创建时间:2018年3月5日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmGlobalSetBean
 * @Description: 告警全局设置表
 * @author chensq
 *
 */
public class AlarmGlobalSetBean {
    
    /**
     * @Fields id : id
     */
    private long id;
    
    /**
     * @Fields moduleId : 模块id
     */
    private int moduleId;
     
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;
        
    /**
     * @Fields kpitype : kpi类型
     */
    private int kpitype; //1:kpi 2：custom
     
    /**
     * @Fields kpiId : kpi id
     */
    private long kpiId;
    
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
     * @return the moduleId
     */
    public int getModuleId() {
        return moduleId;
    }
    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
    /**
     * @return the watchpointId
     */
    public long getWatchpointId() {
        return watchpointId;
    }
    /**
     * @param watchpointId the watchpointId to set
     */
    public void setWatchpointId(long watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * @return the kpitype
     */
    public int getKpitype() {
        return kpitype;
    }
    /**
     * @param kpitype the kpitype to set
     */
    public void setKpitype(int kpitype) {
        this.kpitype = kpitype;
    }
    /**
     * @return the kpiId
     */
    public long getKpiId() {
        return kpiId;
    }
    /**
     * @param kpiId the kpiId to set
     */
    public void setKpiId(long kpiId) {
        this.kpiId = kpiId;
    }
         
}
