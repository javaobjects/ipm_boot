/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>

 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: ReportTimerDetailBean
 * @Description: 图表对象创建 与接收对象略有区别
 * @author 刘斌
 *
 */
public class ReportTimerDetailBean {
    
    /**
     * @Fields id : ID
     */
    private Integer id;
    
    /**
     * @Fields reportId : 图表归属id
     */
    private Integer reportId;
    
    /**
     * @Fields moduleId : 模块id
     */
    private Integer moduleId;
    
    /**
     * @Fields watchpointId : 观察点Id
     */
    private Integer watchpointId;
    
    /**
     * @Fields appId : 业务Id
     */
    private Integer appId;
    
    /**
     * @Fields plotId : 接口Id
     */
    private Integer plotId;
    
    /**
     * @Fields plotTypeId : 接口图表类型Id
     */
    private Integer plotTypeId;
    
    /**
     * @Fields tableHaving : 列表标识
     */
    private Integer tableHaving;
    
    /**
     * @Fields warningHaving : 告警标识
     */
    private Integer warningHaving;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getWatchpointId() {
        return watchpointId;
    }

    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    public Integer getPlotTypeId() {
        return plotTypeId;
    }

    public void setPlotTypeId(Integer plotTypeId) {
        this.plotTypeId = plotTypeId;
    }
    
    /**
     * <p>Title: </p>
     * <p>Description: 无参数构造</p>
     */ 
    public ReportTimerDetailBean() {
        super();
    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param id
     * @param reportId
     * @param moduleId
     * @param watchpointId
     * @param appId
     * @param plotId
     * @param plotTypeId
     * @param tableHaving
     * @param warningHaving
     */
    public ReportTimerDetailBean(Integer id, Integer reportId,
            Integer moduleId, Integer watchpointId, Integer appId,
            Integer plotId, Integer plotTypeId, Integer tableHaving,
            Integer warningHaving) {
        super();
        this.id = id;
        this.reportId = reportId;
        this.moduleId = moduleId;
        this.watchpointId = watchpointId;
        this.appId = appId;
        this.plotId = plotId;
        this.plotTypeId = plotTypeId;
        this.tableHaving = tableHaving;
        this.warningHaving = warningHaving;
    }

    public Integer getTableHaving() {
        return tableHaving;
    }



    public void setTableHaving(Integer tableHaving) {
        this.tableHaving = tableHaving;
    }



    public Integer getWarningHaving() {
        return warningHaving;
    }



    public void setWarningHaving(Integer warningHaving) {
        this.warningHaving = warningHaving;
    }

    @Override
    public String toString() {
        return "ReportTimerDetailBean [id=" + id + ", reportId=" + reportId
                + ", moduleId=" + moduleId + ", watchpointId=" + watchpointId
                + ", appId=" + appId + ", plotId=" + plotId + ", plotTypeId="
                + plotTypeId + ", tableHaving=" + tableHaving
                + ", warningHaving=" + warningHaving + "]";
    }
}
