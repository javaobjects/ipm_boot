/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;


/**
 * @ClassName: GroupBean
 * @Description: 图表对象接收 用于报表包含的图表基础信息
 * @author 刘斌
 *
 */
public class GroupBean {
    
    /**
     * @Fields moduleId : 模块id
     */
    private Integer moduleId;
    /**
     * @Fields busiId : 业务ID
     */
    private Integer busiId;
    /**
     * @Fields watchpointId : 观察点ID
     */
    private Integer watchpointId;
    /**
     * @Fields plotId : Kpi ID
     */
    private Integer plotId ;
    /**
     * @Fields plotTypeId : 报表类型
     */
    private Integer plotTypeId ;
    
    /**
     * @Fields plotTypeId : 报表模板序号
     */
    private Integer modalScheldId ;
    
    /**
     * @Fields plotTypeId : 真假KPI
     */
    private Integer type;
    
    /**
     * @Fields tableHaving : 列表
     */
    private Integer tableHaving;
    
    /**
     * @Fields warningHaving : 告警
     */
    private Integer warningHaving;


    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param moduleId
     * @param busiId
     * @param watchpointId
     * @param plotId
     * @param plotTypeId
     * @param modalScheldId
     * @param type
     * @param tableHaving
     * @param warningHaving
     */
    public GroupBean(Integer moduleId, Integer busiId, Integer watchpointId,
            Integer plotId, Integer plotTypeId, Integer modalScheldId,
            Integer type, Integer tableHaving, Integer warningHaving) {
        super();
        this.moduleId = moduleId;
        this.busiId = busiId;
        this.watchpointId = watchpointId;
        this.plotId = plotId;
        this.plotTypeId = plotTypeId;
        this.modalScheldId = modalScheldId;
        this.type = type;
        this.tableHaving = tableHaving;
        this.warningHaving = warningHaving;
    }

    public Integer getBusiId() {
        return busiId;
    }

    public void setBusiId(Integer busiId) {
        this.busiId = busiId;
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
     * <p>Description: 空参实例方法</p>
     */ 
    public GroupBean() {
        super();
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
    
    public Integer getModalScheldId() {
        return modalScheldId;
    }

    public void setModalScheldId(Integer modalScheldId) {
        this.modalScheldId = modalScheldId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "GroupBean [moduleId=" + moduleId + ", busiId=" + busiId
                + ", watchpointId=" + watchpointId + ", plotId=" + plotId
                + ", plotTypeId=" + plotTypeId + ", modalScheldId="
                + modalScheldId + ", type=" + type + ", tableHaving="
                + tableHaving + ", warningHaving=" + warningHaving + "]";
    }
}
