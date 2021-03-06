/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: MyEmailBean.java
 *创建人: wangjianmin    创建时间: 2018年7月25日
 */
package com.protocolsoft.sendemail.bean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @ClassName: MyEmailBean
 * @Description: 登录邮箱账号信息类
 * @author wangjianmin
 *
 */
public class MyEmailBean  extends Authenticator{
    /**
     * 收件人的登录账号
     */
    private String myEmailAccount;
    
    /**
     * 收件人的登录密码（授权码）
     */
    private String myEmailPassword;
    
    /**
     * 发件人的邮箱的 SMTP 服务器地址
     */
    private String myEmailSMTPHost;
    
    /**
     * SMTP 服务器的端口
     */
    private String smtpPort;

    /**
     * <br />获取 <font color="red"><b>myEmailAccount<b/></font>
     * @return myEmailAccount myEmailAccount
     */
    public String getMyEmailAccount() {
        return myEmailAccount;
    }

    /**  
     * <br />设置 <font color='#333399'><b>myEmailAccount</b></font>
     * @param myEmailAccount myEmailAccount  
     */
    public void setMyEmailAccount(String myEmailAccount) {
        this.myEmailAccount = myEmailAccount;
    }

    /**
     * <br />获取 <font color="red"><b>myEmailPassword<b/></font>
     * @return myEmailPassword myEmailPassword
     */
    public String getMyEmailPassword() {
        return myEmailPassword;
    }

    /**  
     * <br />设置 <font color='#333399'><b>myEmailPassword</b></font>
     * @param myEmailPassword myEmailPassword  
     */
    public void setMyEmailPassword(String myEmailPassword) {
        this.myEmailPassword = myEmailPassword;
    }

    /**
     * <br />获取 <font color="red"><b>myEmailSMTPHost<b/></font>
     * @return myEmailSMTPHost myEmailSMTPHost
     */
    public String getMyEmailSMTPHost() {
        return myEmailSMTPHost;
    }

    /**  
     * <br />设置 <font color='#333399'><b>myEmailSMTPHost</b></font>
     * @param myEmailSMTPHost myEmailSMTPHost  
     */
    public void setMyEmailSMTPHost(String myEmailSMTPHost) {
        this.myEmailSMTPHost = myEmailSMTPHost;
    }

    /**
     * <br />获取 <font color="red"><b>smtpPort<b/></font>
     * @return smtpPort smtpPort
     */
    public String getSmtpPort() {
        return smtpPort;
    }

    /**  
     * <br />设置 <font color='#333399'><b>smtpPort</b></font>
     * @param smtpPort smtpPort  
     */
    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(myEmailAccount, myEmailPassword);
    }
    
}
