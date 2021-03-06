/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewBean
 *创建人:long    创建时间:2017年9月6日
 */
package com.protocolsoft.view.bean;

import java.util.List;

/**
 * MonitorViewBean
 * 2017年9月6日 下午5:12:33
 * @author long
 * @version
 * @see
 */
public class MonitorViewBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String descrption;
    /**
     * 用户id
     */
    private int createUserId;
    /**
     * 更新时间
     */
    private int updateTime;
    /**
     * 状态
     */
    private int lockStatus;
    /**
     * 子节点
     */
    private List<?> monitorViewBeanList;
    /**
     * 是否选中
     */
    private int checked;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 状态信息
     */
    private String success;
    
    /**
     * 角色
     */
    private int roleId;
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
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the descrption
     */
    public String getDescrption() {
        return descrption;
    }
    /**
     * @param descrption the descrption to set
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
    /**
     * @return the createUserId
     */
    public int getCreateUserId() {
        return createUserId;
    }
    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }
    /**
     * @return the updateTime
     */
    public int getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * @return the lockStatus
     */
    public int getLockStatus() {
        return lockStatus;
    }
    /**
     * @param lockStatus the lockStatus to set
     */
    public void setLockStatus(int lockStatus) {
        this.lockStatus = lockStatus;
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
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }
    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    /**
     * @return the monitorViewBeanList
     */
    public List<?> getMonitorViewBeanList() {
        return monitorViewBeanList;
    }
    /**
     * @param monitorViewBeanList the monitorViewBeanList to set
     */
    public void setMonitorViewBeanList(List<?> monitorViewBeanList) {
        this.monitorViewBeanList = monitorViewBeanList;
    }
   
   
 

}
