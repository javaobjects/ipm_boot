/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: ReportEmailBean
 * @Description: 报表对象 用于与myBatis交互
 * @author 刘斌
 *
 */
public class ReportEmailBean {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 关联的计划ID
     */
    private Integer planId;
    /**
     * 发送者
     */
    private String sender;
    /**
     * 接收者
     */
    private String recriver;
    /**
     * 目标email
     */
    private String email;
    
    /**
     * <p>Title: </p>
     * <p>Description:无参构造器 </p>
     */ 
    public ReportEmailBean() {
        super();
    }
    
    /**
     * <p>Title: </p>
     * <p>Description:有参构造器 </p>
     * @param id
     * @param planId
     * @param sender
     * @param recriver
     * @param email
     */ 
    public ReportEmailBean(Integer id, Integer planId, String sender,
            String recriver, String email) {
        super();
        this.id = id;
        this.planId = planId;
        this.sender = sender;
        this.recriver = recriver;
        this.email = email;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPlanId() {
        return planId;
    }
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getRecriver() {
        return recriver;
    }
    public void setRecriver(String recriver) {
        this.recriver = recriver;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "ReportEmailBean [id=" + id + ", planId=" + planId + ", sender="
                + sender + ", recriver=" + recriver + ", email=" + email + "]";
    }
    
}
