/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmKpiBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmKpiBean
 * @Description: 告警级别实体类
 * @author chensq
 *
 */
public class AlarmKpiBean {
    /**
     * @Fields id : id
     */
    private long id;
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    /**
     * @Fields kpiId : kpi id
     */
    private long kpiId;
    /**
     * @Fields displayName : kpi显示名 与绘图选项中的保持一致
     */
    private String displayName;
    
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
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
}
