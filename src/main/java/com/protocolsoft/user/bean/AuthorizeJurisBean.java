/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserAuthorizeBean
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.bean;

/**
 * 
 * @ClassName: AuthorizeJurisBean
 * @Description: 授权bean
 * @author wangjianmin
 *
 */
public class AuthorizeJurisBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * userId
     */
    private int userId;
    /**
     * moduleId
     */
    private int moduleId;
    /**
     * 状态
     */
    private String status;
    
    /**
     * centerId
     */
    private int centerId;
    
    /**
     * appId
     */
    private int appId;
    
    /**
     * 排序
     */
    private int order;
    
    /**
     * 是否选中
     */
    private int checked;
    /**
     * 状态信息
     */
    private String success;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the checked
     */
    public int getChecked() {
        return checked;
    }
    /**
     * @param checked the checked to set
     */
    public void setChecked(int checked) {
        this.checked = checked;
    }
    /**
     * @return the success
     */
    public String getSuccess() {
        return success;
    }
    /**
     * @param success the success to set
     */
    public void setSuccess(String success) {
        this.success = success;
    }
    /**
     * @return the centerId
     */
    public int getCenterId() {
        return centerId;
    }
    /**
     * @param centerId the centerId to set
     */
    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }
    /**
     * @return the appId
     */
    public int getAppId() {
        return appId;
    }
    /**
     * @param appId the appId to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }
    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }
    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }
    
}
