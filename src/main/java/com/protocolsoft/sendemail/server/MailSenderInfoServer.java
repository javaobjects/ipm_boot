/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: Test001.java
 *创建人: wangjianmin    创建时间: 2018年7月25日
 */
package com.protocolsoft.sendemail.server;

import java.util.Date;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.protocolsoft.sendemail.bean.MailSenderInfoBean;
import com.protocolsoft.sendemail.bean.MyEmailBean;

/**
 * @ClassName: Test001
 * @Description: 邮件发送实现
 * @author wangjianmin
 *
 */
public class MailSenderInfoServer {
    /**
     * 
     * @Title: init
     * @Description: 开始发送（初始化参数）
     * @param mailSenderInfoBean 接收邮件信息
     * @param bean void  接收发件人的邮箱账号
     * @author wangjianmin
     * @throws MessagingException 
     */
    public void init(MyEmailBean bean,  MailSenderInfoBean  mailSenderInfoBean) throws MessagingException{
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", bean.getMyEmailSMTPHost());   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        
         // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
         //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
         //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        if(bean.getSmtpPort() != null){
            props.setProperty("mail.smtp.port", bean.getSmtpPort());
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", bean.getSmtpPort());
        }
        
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        //session.setDebug(true);             // 设置为debug模式, 可以查看详细的发送 log
        
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, mailSenderInfoBean);

        Transport transport = null;
        
        try {
            // 4. 根据 Session 获取邮件传输对象
            transport = session.getTransport();
            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(bean.getMyEmailAccount(),
                    bean.getMyEmailPassword());
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != transport){
                try {
                    transport.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * 
     * @Title: createMimeMessage
     * @Description: 创建发送邮件
     * @param session 和服务器交互的会话
     * @param bean  接收发送邮件参数
     * @return MimeMessage
     * @author wangjianmin
     */
    public  MimeMessage createMimeMessage(Session session, MailSenderInfoBean bean) {
        // 1. 创建一封邮件
        MimeMessage message = null;
        try {
            message = new MimeMessage(session);

            // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
            message.setFrom(new InternetAddress(bean.getSendMail(), bean.getSendName(), "UTF-8"));

            // 3. To: 收件人（可以增加多个收件人、抄送、密送）
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(bean.getReceiveMail(), bean.getReceiveName(), "UTF-8"));

            // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
            message.setSubject(bean.getSubject(), "UTF-8");

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            
            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(bean.getContent(), "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            
            // 添加附件的内容
            if(bean.getAttachment() != null){
                BodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(bean.getAttachment());
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                //MimeUtility.encodeWord可以避免文件名乱码
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(bean.getEmailName(), "gb2312", "B"));
                multipart.addBodyPart(attachmentBodyPart);
            }
            
            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
            message.setContent(multipart, "text/html;charset=UTF-8");
            
            // 6. 设置发件时间
            message.setSentDate(new Date());

            // 7. 保存设置
            message.saveChanges();
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return message;
    }

}
