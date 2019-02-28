/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CenterParamBean.java
 *创建人: www    创建时间: 2018年4月24日
 */
package com.protocolsoft.common.bean;

/**
 * @ClassName: CenterParamBean
 * @Description: Center Bean
 * @author www
 *
 */
public class CenterParamBean {

    /**
     * Center编号
     */
    private Integer ipmCenterId;
    
    /**
     * Center名称
     */
    private String ipmCenterName;
    
    /**
     * 联系人
     */
    private String contacts;
    
    /**
     * 电话
     */
    private String telephone;
    
    /**
     * 邮件
     */
    private String email;

    /**
     * <br />获取 <font color="red"><b>Center编号<b/></font>
     * @return ipmCenterId Center编号
     */
    public Integer getIpmCenterId() {
        return ipmCenterId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>Center编号</b></font>
     * @param ipmCenterId Center编号  
     */
    public void setIpmCenterId(Integer ipmCenterId) {
        this.ipmCenterId = ipmCenterId;
    }

    /**
     * <br />获取 <font color="red"><b>Center名称<b/></font>
     * @return ipmCenterName Center名称
     */
    public String getIpmCenterName() {
        return ipmCenterName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>Center名称</b></font>
     * @param ipmCenterName Center名称  
     */
    public void setIpmCenterName(String ipmCenterName) {
        this.ipmCenterName = ipmCenterName;
    }

    /**
     * <br />获取 <font color="red"><b>联系人<b/></font>
     * @return contacts 联系人
     */
    public String getContacts() {
        return contacts;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>联系人</b></font>
     * @param contacts 联系人  
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    

    /**
     * <br />获取 <font color="red"><b>电话<b/></font>
     * @return telephone 电话
     */
    public String getTelephone() {
        return telephone;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>电话</b></font>
     * @param telephone 电话  
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    

    /**
     * <br />获取 <font color="red"><b>邮件<b/></font>
     * @return email 邮件
     */
    public String getEmail() {
        return email;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>邮件</b></font>
     * @param email 邮件  
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
