/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: MailSenderInfoBean.java
 *创建人: wangjianmin    创建时间: 2018年7月25日
 */
package com.protocolsoft.sendemail.bean;

import java.io.File;

/**
 * @ClassName: MailSenderInfoBean
 * @Description: 邮件发送参数类
 * @author wangjianmin
 *
 */
public class MailSenderInfoBean {
    /**
     * 收件人邮箱
     */
    private  String  receiveMail;
    
    /**
     * 收件人名称
     */
    private  String  receiveName;
    
    /**
     * 发件人邮箱
     */
    private  String sendMail;
    
    /**
     * 发件人名称
     */
    private  String sendName;
    
    /**
     * 邮件主题
     */
    private  String subject;
    
    /**
     * 邮件内容
     */
    private  String content;
    
    /**
     * 附件
     */
    private File attachment;
    
    /**
     * 附件名称
     */
    private String emailName;
    
    /**
     * <br />获取 <font color="red"><b>receiveMail<b/></font>
     * @return receiveMail receiveMail
     */
    public String getReceiveMail() {
        return receiveMail;
    }

    /**  
     * <br />设置 <font color='#333399'><b>receiveMail</b></font>
     * @param receiveMail receiveMail  
     */
    public void setReceiveMail(String receiveMail) {
        this.receiveMail = receiveMail;
    }

    /**
     * <br />获取 <font color="red"><b>receiveName<b/></font>
     * @return receiveName receiveName
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>receiveName</b></font>
     * @param receiveName receiveName  
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * <br />获取 <font color="red"><b>sendMail<b/></font>
     * @return sendMail sendMail
     */
    public String getSendMail() {
        return sendMail;
    }

    /**  
     * <br />设置 <font color='#333399'><b>sendMail</b></font>
     * @param sendMail sendMail  
     */
    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    /**
     * <br />获取 <font color="red"><b>sendName<b/></font>
     * @return sendName sendName
     */
    public String getSendName() {
        return sendName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>sendName</b></font>
     * @param sendName sendName  
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * <br />获取 <font color="red"><b>subject<b/></font>
     * @return subject subject
     */
    public String getSubject() {
        return subject;
    }

    /**  
     * <br />设置 <font color='#333399'><b>subject</b></font>
     * @param subject subject  
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * <br />获取 <font color="red"><b>content<b/></font>
     * @return content content
     */
    public String getContent() {
        return content;
    }

    /**  
     * <br />设置 <font color='#333399'><b>content</b></font>
     * @param content content  
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * <br />获取 <font color="red"><b>attachment<b/></font>
     * @return attachment attachment
     */
    public File getAttachment() {
        return attachment;
    }

    /**  
     * <br />设置 <font color='#333399'><b>attachment</b></font>
     * @param attachment attachment  
     */
    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    /**
     * <br />获取 <font color="red"><b>emailName<b/></font>
     * @return emailName emailName
     */
    public String getEmailName() {
        return emailName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>emailName</b></font>
     * @param emailName emailName  
     */
    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

}
