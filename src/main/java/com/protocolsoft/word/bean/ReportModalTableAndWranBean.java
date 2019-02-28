/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

/**
 * 
 * @ClassName: ReportModalTableAndWranBean
 * @Description: 
 * @author 刘斌
 *
 */
public class ReportModalTableAndWranBean {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 主键Id
     */
    private Integer modalId;
    /**
     * 主键Id
     */
    private Integer modultId;
    /**
     * 主键Id
     */
    private Integer tableHaving;
    /**
     * 主键Id
     */
    private Integer warningHaving;
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param id
     * @param modalId
     * @param modultId
     * @param tableHaving
     * @param warningHaving
     */
    public ReportModalTableAndWranBean(Integer id, Integer modalId,
            Integer modultId, Integer tableHaving, Integer warningHaving) {
        super();
        this.id = id;
        this.modalId = modalId;
        this.modultId = modultId;
        this.tableHaving = tableHaving;
        this.warningHaving = warningHaving;
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public ReportModalTableAndWranBean() {
        super();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getModalId() {
        return modalId;
    }
    public void setModalId(Integer modalId) {
        this.modalId = modalId;
    }
    public Integer getModultId() {
        return modultId;
    }
    public void setModultId(Integer modultId) {
        this.modultId = modultId;
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
        return "ReportModalTableAndWranBean [id=" + id + ", modalId=" + modalId
                + ", modultId=" + modultId + ", tableHaving=" + tableHaving
                + ", warningHaving=" + warningHaving + "]";
    }
    
}
