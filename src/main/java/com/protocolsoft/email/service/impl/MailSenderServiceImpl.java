package com.protocolsoft.email.service.impl;

import java.io.File;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.protocolsoft.email.bean.MailAuthenticatorBean;
import com.protocolsoft.email.bean.MailBean;
import com.protocolsoft.email.service.MailSernderService;


/**
 * 
 * @ClassName: MailSenderServiceImpl
 * @author 刘斌
 *
 */
public class MailSenderServiceImpl implements MailSernderService{
    
    /**
     * 发送邮件的Properties文件
     */
    private final transient Properties props = System.getProperties();
    
    /**
     * 服务器邮箱登录验证
     */
    private transient MailAuthenticatorBean mailAuthenticator;
    
    /**
     * 邮箱Session
     */
    private transient Session session;
    
    /**
     * 初始化邮箱发送器
     * 
     * @param mailServiceUrl
     *          服务器邮箱地址
     * @param username
     *          服务器邮箱用户名
     * @param password
     *          服务器邮箱登录密码
     */
    public MailSenderServiceImpl(final String mailServiceUrl, final String username, final String password){
        init(mailServiceUrl, username, password);
    }
    
    /**
     * 初始化邮箱发送器
     * 
     * @param username
     *          服务器邮箱用户名
     * @param password
     *          服务器邮箱登录密码
     */
    public MailSenderServiceImpl(final String username, final String password){
        final String mailServiceUrl = "smtp."  + username.split("@")[1];
        init(mailServiceUrl, username, password);
    }
    
    /**
     * 初始化操作
     * @param mailServiceUrl
     *          服务器邮箱地址
     * @param username
     *          服务器邮箱用户名
     * @param password
     *          服务器邮箱登录密码
     */
    public void init(String mailServiceUrl, String username, String password){
        //初始化props
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailServiceUrl);
        props.put("mail.smtp.starttls.enable" , "true");
        props.put("mail.smtp.password" , password);
        //服务器邮箱验证
        mailAuthenticator = new MailAuthenticatorBean(username, password);
        //创建session，想当于邮箱登录
        session = Session.getInstance(props, mailAuthenticator);
    }
    /**
     * 发送邮件
     * 
     * @param recipient
     *          收信人邮箱地址
     * @param subject
     *          邮件标题
     * @param content
     *          邮件内容
     */
    public void send(String recipient, String subject, String content){
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setContent(content, "text/html;charset=utf-8");
            msg.saveChanges();
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: sendAddFile
     * @param recipient
     * @param mail void
     * @author 刘斌
     */
    public void sendAddFile(String recipient, MailBean mail){
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
            msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(mail.getSubject());
            File attachment = new File(mail.getAddFile());
            Multipart multipart = new MimeMultipart();
            // 1.保存内容
            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mailContentPart = new MimeBodyPart();
            mailContentPart.setContent(mail.getContent(),
                    "text/html;charset=GBK");
            msg.setContent(mail.getContent(), "text/html;charset=GBK");
            // 这句很重要，千万不要忘了
            mp.setSubType("related");
            mp.addBodyPart(mailContentPart);
            // 添加附件的内容
            if (attachment != null) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));

                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility
                        .encodeWord(mail.getRealName()));
                multipart.addBodyPart(attachmentBodyPart);
                mp.addBodyPart(attachmentBodyPart);
            }
            // 将multipart对象放到message中
            msg.setContent(mp);
            msg.saveChanges();
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 群发邮件
     * 
     * @param recipients
     *          收信人们的邮箱地址
     * @param subject
     *          邮件标题
     * @param content
     *          邮件内容
     */
    public void send(List<String> recipients, String subject, String content){
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
            int num = recipients.size();
            InternetAddress[] addresses = new InternetAddress[num];
            for (int i = 0; i < num; i++) {
                addresses[i] = new InternetAddress(recipients.get(i));
            }
            msg.setRecipients(RecipientType.TO, addresses);
            msg.setSubject(subject);
            msg.setContent(content, "text/html;charset=utf-8");
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Title: send
     * @param recipients
     * @param mail
     * @param list void
     * @author 刘斌
     */
    public void send(List<String> recipients, MailBean mail, String[] list){
        final MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
            int num = recipients.size();
            InternetAddress[] addresses = new InternetAddress[num];
            for (int i = 0; i < num; i++) {
                addresses[i] = new InternetAddress(recipients.get(i));
            }
            msg.setRecipients(RecipientType.TO, addresses);
            msg.setSubject(mail.getSubject());
            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mailContentPart = new MimeBodyPart();
            mailContentPart.setContent(mail.getContent(),
                    "text/html;charset=GBK");
            msg.setContent(mail.getContent(), "text/html;charset=GBK");
            mp.setSubType("related");
            mp.addBodyPart(mailContentPart);
            for (int index = 0; index < list.length; index++) {
                MimeBodyPart mailArchieve = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(list[index]);
                mailArchieve.setDataHandler(new DataHandler(fds));
                mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
                        "GBK", "B"));
                mp.addBodyPart(mailArchieve);
            }
            msg.setContent(mp);
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 发送邮件
     * 
     * @param recipient
     *          收信人邮箱地址
     * @param mail
     *          邮件对象
     * @throws AddressException
     * @throws MessagingException
     * @throws
     */
    public void send(String recipient, MailBean mail){
        if(null== mail.getAddFile()){
            this.send(recipient, mail.getSubject(), mail.getContent());
        }else{
            this.sendAddFile(recipient, mail);
        }
    }
    /**
     * 群发邮件
     * 
     * @param recipients
     *          收信人们的邮箱地址
     * @param mail
     *          邮件对象
     * @throws AddressException
     * @throws MessagingException
     * @throws
     */
    public void send(List<String> recipients, MailBean mail){
        if(recipients.size()<1){
            return;
        }
        this.send(recipients, mail.getSubject(), mail.getContent());
    }
}