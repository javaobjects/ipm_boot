/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:DrawingOptionsBean
 *创建人:wjm    创建时间:2017年9月20日
 */
package com.protocolsoft.common.bean;

/**
 *
 * 2017年9月20日 下午5:05:43
 * 
 * @author wjm
 * @version
 * @see
 */
public class DrawingOptionsBean {
    /**
     * 观察点ID
     */
    Integer watchpointId;

    /**
     * 绘图选项ID
     */
    Integer plotId;

    /**
     * 绘图类型ID
     */
    Integer plotTypeId;

    /**
     * 开始时间
     */
    Long starttime;

    /**
     * 结束时间
     */
    Long endtime;

    /**
     * 粒度
     */
    Integer step;

    /**
     * 客户端Id
     */
    private int clientId;

    /**
     * 十字数据
     */
    String plotIds;

    /**
     * 区分观察点/客户端/服务端
     */
    private Integer modleId;
    
    /**
     * 列表数据
     */
    private Integer tableHaving;
    
    /**
     * 告警数据
     */
    private Integer warningHaving;

    /**
     * @return Integer
     */
    public Integer getModleId() {
        return modleId;
    }

    public void setModleId(Integer modleId) {
        this.modleId = modleId;
    }

    /**
     * @return the watchpointId
     */
    public Integer getWatchpointId() {
        return watchpointId;
    }

    /**
     * @param watchpointId
     *            the watchpointId to set
     */
    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }

    /**
     * @return the plotId
     */
    public Integer getPlotId() {
        return plotId;
    }

    /**
     * @param plotId
     *            the plotId to set
     */
    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }

    /**
     * @return the plotTypeId
     */
    public Integer getPlotTypeId() {
        return plotTypeId;
    }

    /**
     * @param plotTypeId
     *            the plotTypeId to set
     */
    public void setPlotTypeId(Integer plotTypeId) {
        this.plotTypeId = plotTypeId;
    }

    /**
     * @return the starttime
     */
    public Long getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     *            the starttime to set
     */
    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    /**
     * @return the endtime
     */
    public Long getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     *            the endtime to set
     */
    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    /**
     * @return the step
     */
    public Integer getStep() {
        return 0;
    }

    /**
     * @param step
     *            the step to set
     */
    public void setStep(Integer step) {
        this.step = step;
    }

    /**
     * @return the clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the plotIds
     */
    public String getPlotIds() {
        return plotIds;
    }

    /**
     * @param plotIds
     *            the plotIds to set
     */
    public void setPlotIds(String plotIds) {
        this.plotIds = plotIds;
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
}
