package com.protocolsoft.email.bean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @ClassName: MailAuthenticatorBean
 * @author 刘斌
 *
 */
public class MailAuthenticatorBean extends Authenticator{

    /**
     * 用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 继承Authenticator类需重写此方法
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(username, password);
    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public MailAuthenticatorBean(){

    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param username
     * @param password
     */
    public MailAuthenticatorBean(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}