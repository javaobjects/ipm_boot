package com.protocolsoft.email.service;

import java.util.List;

import com.protocolsoft.email.bean.MailBean;

/**
 * 
 * @ClassName: MailSernderService
 * @author 刘斌
 *
 */
public interface MailSernderService {

    /**
     * 
     * @Title: send
     * @Description: 群发邮件
     * @param recipients
     * @param mail void
     * @author 刘斌
     */
    void send(List<String> recipients, MailBean mail);
    
    
    /**
     * 
     * @Title: send
     * @Description: 单发邮件
     * @param recipient
     * @param mail void
     * @author 刘斌
     */
    void send(String recipient, MailBean mail);
    
    /**
     * 
     * @Title: send
     * @Description: 群发多附件邮件
     * @param recipients
     * @param mail
     * @param list void
     * @author 刘斌
     */
    void send(List<String> recipients, MailBean mail, String[] list);
    
    /**
     * 
     * @Title: send
     * @Description: 群发纯邮件
     * @param recipients
     * @param subject
     * @param content void
     * @author 刘斌
     */
    void send(List<String> recipients, String subject, String content);
    
    /**
     * 
     * @Title: sendAddFile
     * @Description: 单发纯邮件
     * @param recipient
     * @param mail void
     * @author 刘斌
     */
    void sendAddFile(String recipient, MailBean mail);
    
    /**
     * 
     * @Title: send
     * @Description: 单发纯邮件
     * @param recipient
     * @param subject
     * @param content void
     * @author 刘斌
     */
    void send(String recipient, String subject, String content);
    
    /**
     * 
     * @Title: init
     * @Description: 初始化
     * @param mailServiceUrl
     * @param username
     * @param password void
     * @author 刘斌
     */
    void init(String mailServiceUrl, String username, String password);
    
}
