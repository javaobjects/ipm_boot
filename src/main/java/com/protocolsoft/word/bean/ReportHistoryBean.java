/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: ReportHistoryBean
 * @Description: 历史报表对象 用于创建历史报表信息的对象
 * @author 刘斌
 *
 */
public class ReportHistoryBean {
    /**
     * @Fields id : id
     */
    private Integer id;
    /**
     * @Fields name : 名称
     */
    private String name;
    /**
     * @Fields typeId : 报表归档类型Id
     */
    private int typeId;
    /**
     * @Fields createtime : 报表创建时间
     */
    private Long createtime;
    /**
     * @Fields datastime : 报表开始时间
     */
    private Long datastime;
    /**
     * @Fields dataetime : 报表结束时间
     */
    private Long dataetime;
    
    /**
     * @Fields typeName : 报表类型名称
     */
    private String typeName;
    
    /**
     * @Fields userId : 创建用户的ID
     */
    private Integer userId;
    
    /**
     * @Fields sendStatus : 发送状态
     */
    private Integer sendStatus;
    
    /**
     * @Fields sendTime : 发送时间
     */
    private Long sendTime;
    
    /**
     * @Fields sendNames : 发送者名称
     */
    private String sendNames;
    
    /**
     * @Fields recriveNames : 接收者名称
     */
    private String recriveNames;

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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getDatastime() {
        return datastime;
    }

    public void setDatastime(Long datastime) {
        this.datastime = datastime;
    }

    public Long getDataetime() {
        return dataetime;
    }

    public void setDataetime(Long dataetime) {
        this.dataetime = dataetime;
    }
    
    /**
     * <p>Title: </p>
     * <p>Description: 必备</p>
     */ 
    public ReportHistoryBean() {
        super();
    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param id
     * @param name
     * @param typeId
     * @param createtime
     * @param datastime
     * @param dataetime
     * @param typeName
     * @param userId
     * @param sendStatus
     * @param sendTime
     * @param sendNames
     * @param recriveNames
     */
    public ReportHistoryBean(Integer id, String name, int typeId,
            Long createtime, Long datastime, Long dataetime, String typeName,
            Integer userId, Integer sendStatus, Long sendTime,
            String sendNames, String recriveNames) {
        super();
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.createtime = createtime;
        this.datastime = datastime;
        this.dataetime = dataetime;
        this.typeName = typeName;
        this.userId = userId;
        this.sendStatus = sendStatus;
        this.sendTime = sendTime;
        this.sendNames = sendNames;
        this.recriveNames = recriveNames;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendNames() {
        return sendNames;
    }

    public void setSendNames(String sendNames) {
        this.sendNames = sendNames;
    }

    public String getRecriveNames() {
        return recriveNames;
    }

    public void setRecriveNames(String recriveNames) {
        this.recriveNames = recriveNames;
    }

    @Override
    public String toString() {
        return "ReportHistoryBean [id=" + id + ", name=" + name + ", typeId="
                + typeId + ", createtime=" + createtime + ", datastime="
                + datastime + ", dataetime=" + dataetime + ", typeName="
                + typeName + ", userId=" + userId + ", sendStatus="
                + sendStatus + ", sendTime=" + sendTime + ", sendNames="
                + sendNames + ", recriveNames=" + recriveNames + "]";
    }
    
}
