/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: TimerReportBean
 * @Description: 报表对象 用于与前端交互
 * @author 刘斌
 *
 */
public class TimerReportBean {
    /**
     * @Fields id : Id
     */
    private Integer id;
    /**
     * @Fields typeId : 报表归档类型Id
     */
    private Integer typeId;
    /**
     * @Fields userId : 报表信息创建者ID
     */
    private Integer userId;
    /**
     * @Fields name : 报表名称
     */
    private String name;
    /**
     * @Fields createtime : 创建时间
     */
    private Long createtime;
    /**
     * @Fields state : 报表状态
     */
    private Integer state;
    /**
     * @Fields userName : 报表信息创建者名称
     */
    private String userName;
    /**
     * @Fields typeName : 报表归档类型
     */
    private String typeName;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * <p>Title: </p>
     * <p>Description: 有参构造器</p>
     * @param id
     * @param typeId
     * @param userId
     * @param name
     * @param createtime
     * @param state
     */ 
    public TimerReportBean(Integer id, Integer typeId, Integer userId, String name,
            Long createtime, Integer state) {
        super();
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.name = name;
        this.createtime = createtime;
        this.state = state;
    }
    /**
     * <p>Title: </p>
     * <p>Description: 无参构造器</p>
     */ 
    public TimerReportBean() {
        super();
    }

    @Override
    public String toString() {
        return "TimerReport [id=" + id + ", typeId=" + typeId + ", userId="
                + userId + ", name=" + name + ", createtime=" + createtime
                + ", state=" + state + "]";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
