/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.email.bean;

/**
 * 
 * @ClassName: EmailConBean
 * @Description: 邮箱控制对象
 * @author liubin
 *
 */
public class EmailBean {
    
    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 邮件服务器（STMP）
     */
    private String emailServer;
    /**
     * 邮箱名称
     */
    private String emailUserName;
    /**
     * 邮箱密码
     */
    private String emailPassword;
    /**
     * 发送用户名称
     */
    private String userName;
    
    /**
     *  邮件服务器端口（STMP）
     */
    private String emailPort;
    
    /**
     * 授权码
     */
    private String emailAuthorCode;
    
    
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param id
     * @param emailServer
     * @param emailUserName
     * @param emailPassword
     * @param userNamel
     */
    public EmailBean(Integer id, String emailServer, String emailUserName,
            String emailPassword, String userNamel) {
        super();
        this.id = id;
        this.emailServer = emailServer;
        this.emailUserName = emailUserName;
        this.emailPassword = emailPassword;
        this.userName = userNamel;
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public EmailBean() {
        super();
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getEmailServer() {
        return emailServer;
    }
    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }
    public String getEmailUserName() {
        return emailUserName;
    }
    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }
    public String getEmailPassword() {
        return emailPassword;
    }
    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userNamel) {
        this.userName = userNamel;
    }
    @Override
    public String toString() {
        return "EmailConBean [id=" + id + ", emailServer=" + emailServer
                + ", emailUserName=" + emailUserName + ", emailPassword="
                + emailPassword + ", userName=" + userName + "]";
    }

    /**
     * <br />获取 <font color="red"><b>emailAuthorCode<b/></font>
     * @return emailAuthorCode emailAuthorCode
     */
    public String getEmailAuthorCode() {
        return emailAuthorCode;
    }

    /**  
     * <br />设置 <font color='#333399'><b>emailAuthorCode</b></font>
     * @param emailAuthorCode emailAuthorCode  
     */
    public void setEmailAuthorCode(String emailAuthorCode) {
        this.emailAuthorCode = emailAuthorCode;
    }

    /**
     * <br />获取 <font color="red"><b>emailPort<b/></font>
     * @return emailPort emailPort
     */
    public String getEmailPort() {
        return emailPort;
    }

    /**  
     * <br />设置 <font color='#333399'><b>emailPort</b></font>
     * @param emailPort emailPort  
     */
    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }
    
}
