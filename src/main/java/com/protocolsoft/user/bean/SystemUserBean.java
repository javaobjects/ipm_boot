/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserBean
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.bean;

import java.sql.Timestamp;

/**
 * 用户信息bean
 * 2017年9月1日 下午1:46:12
 * @author long
 * @version
 * @see
 */
public class SystemUserBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 名称
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPsw;
    /**
     * 真实名称
     */
    private String realName;
    /**
     * email
     */
    private String email;
    /**
     * 电话
     */
    private String telephone;
    /**
     * roleId
     */
    private int roleId;
    
    /**
     * 登录状态
     */
    private int loginState;
    
    /**
     * 超时状态
     */
    private int loginOvertime;
    
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 状态信息
     */
    private String success;
    /**
     * 模块ids
     */
    private String moduleIds;
    /**
     * 角色名称
     */
    private String roleName;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }
    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }
    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
     * @return the createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
     * @return the moduleIds
     */
    public String getModuleIds() {
        return moduleIds;
    }
    /**
     * @param moduleIds the moduleIds to set
     */
    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }
    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }
    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    /**
     * @return the confirmPsw
     */
    public String getConfirmPsw() {
        return confirmPsw;
    }
    /**
     * @param confirmPsw the confirmPsw to set
     */
    public void setConfirmPsw(String confirmPsw) {
        this.confirmPsw = confirmPsw;
    }
    /**
     * <br />获取 <font color="red"><b>loginState<b/></font>
     * @return loginState loginState
     */
    public int getLoginState() {
        return loginState;
    }
    /**  
     * <br />设置 <font color='#333399'><b>loginState</b></font>
     * @param loginState loginState  
     */
    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }
    /**
     * <br />获取 <font color="red"><b>loginOvertime<b/></font>
     * @return loginOvertime loginOvertime
     */
    public int getLoginOvertime() {
        return loginOvertime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>loginOvertime</b></font>
     * @param loginOvertime loginOvertime  
     */
    public void setLoginOvertime(int loginOvertime) {
        this.loginOvertime = loginOvertime;
    }
   
    
}
