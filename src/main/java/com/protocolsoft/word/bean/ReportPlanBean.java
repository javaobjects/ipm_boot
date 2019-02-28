/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

import java.util.List;

 /**
 * @ClassName: ReportPlanBean
 * @Description: 报表对象 用于与myBatis交互
 * @author 刘斌
 *
 */
public class ReportPlanBean {

    /**
     * @Fields id : id主键Id
     */
    private Integer id;
    /**
     * @Fields name 报表名称
     */
    private String name;
    /**
     * @Fields id : id 制作人Id
     */
    private Integer userId;
    /**
     * @Fields id : id 模板Id
     */
    private Integer modalId;
    /**
     * @Fields id : id 报表类型标记1：自定义 2：日报 3： 周报 4：月报
     */
    private Integer typeId;
    /**
     * @Fields id : id 创建时间
     */
    private Long createTime;
    /**
     * @Fields id : id 发送次数
     */
    private Integer sendTimes;
    /**
     * @Fields id : id 下次发送时间
     */
    private Long nextSendTime;
    /**
     * @Fields id : id 制作人名字
     */
    private String userName;
    /**
     * Email集合
     */
    private List<ReportEmailBean> list;
    
    /**
     * @Fields id : id 状态
     */
    private Integer state;
    /**
     * @Fields id : id 开始时间
     */
    private Long startTime;
    /**
     * @Fields id : id 结束时间
     */
    private Long endTime;
    
    /**
     * @Fields id : id 模板名称
     */
    private String modalName;
    
    /**
     * @Fields compareToLastOne : 环比标记
     */
    private Integer compareToLastOne;
    
    /**
     * @Fields listBus : 业务集合
     */
    private List<ReportBusinessBean> listBus;
    
    /**
     * @Fields watchpointIds : 观察点Id
     */
    private String watchpointIds;
    
    /**
     * @Fields moduleIds : 业务ID集合
     */
    private String moduleIds;
    
    /**
     * <p>Title: </p>
     * <p>Description: 无参数构造</p>
     */ 
    public ReportPlanBean() {
        super();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getModalId() {
        return modalId;
    }
    public void setModalId(Integer modalId) {
        this.modalId = modalId;
    }
    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Integer getSendTimes() {
        return sendTimes;
    }
    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }
    public Long getNextSendTime() {
        return nextSendTime;
    }
    public void setNextSendTime(Long nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ReportEmailBean> getList() {
        return list;
    }

    public void setList(List<ReportEmailBean> list) {
        this.list = list;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }

    public List<ReportBusinessBean> getListBus() {
        return listBus;
    }

    public void setListBus(List<ReportBusinessBean> listBus) {
        this.listBus = listBus;
    }

    public Integer getCompareToLastOne() {
        return compareToLastOne;
    }

    public void setCompareToLastOne(Integer compareToLastOne) {
        this.compareToLastOne = compareToLastOne;
    }


    public String getWatchpointIds() {
        return watchpointIds;
    }


    public void setWatchpointIds(String watchpointIds) {
        this.watchpointIds = watchpointIds;
    }

    @Override
    public String toString() {
        return "ReportPlanBean [id=" + id + ", name=" + name + ", userId="
                + userId + ", modalId=" + modalId + ", typeId=" + typeId
                + ", createTime=" + createTime + ", sendTimes=" + sendTimes
                + ", nextSendTime=" + nextSendTime + ", userName=" + userName
                + ", list=" + list + ", state=" + state + ", startTime="
                + startTime + ", endTime=" + endTime + ", modalName="
                + modalName + ", compareToLastOne=" + compareToLastOne
                + ", listBus=" + listBus + ", watchpointIds=" + watchpointIds
                + "]";
    }

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }
    
}
