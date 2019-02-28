/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.sendemail;

import java.io.File;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.sendemail.bean.MailSenderInfoBean;
import com.protocolsoft.sendemail.bean.MyEmailBean;
import com.protocolsoft.sendemail.server.MailSenderInfoServer;
import com.protocolsoft.word.bean.ReportEmailBean;

/**
 * 
 * @ClassName: MailSendService
 * @Description: 
 * @author 刘斌
 *
 */
@Service
public class MailSendService {

    /**
     * 
     * @Title: send
     * @Description: 
     * @param beanEmail
     * @param reportBean
     * @param filePath void
     * @author 刘斌
     * @throws MessagingException 
     */
    public void send(EmailBean beanEmail, ReportEmailBean reportBean, String filePath, String fileName) throws MessagingException{
        MailSenderInfoServer  info = new MailSenderInfoServer();
        MyEmailBean  bean = new MyEmailBean();
        bean.setMyEmailAccount(beanEmail.getEmailUserName());
        bean.setMyEmailPassword(beanEmail.getEmailPassword());
        bean.setMyEmailSMTPHost(beanEmail.getEmailServer());
        bean.setSmtpPort(beanEmail.getEmailPort()+"");
        MailSenderInfoBean  beans= new  MailSenderInfoBean();
        
        beans.setEmailName(fileName);
        //收件人信息
        beans.setReceiveMail(reportBean.getEmail());
        beans.setReceiveName(reportBean.getRecriver());
        //发件人信息
        beans.setSendMail(beanEmail.getEmailUserName());
        beans.setSendName(reportBean.getSender());
        
        String re = reportBean.getRecriver();
        String se = reportBean.getSender();
        //主题
        beans.setSubject("来至于" + se + "的报表邮件");
        //内容
        beans.setContent("请" + re + "接收来自于"+ se + "的报表");
        //附件
        File affix = new File(filePath);
        beans.setAttachment(affix);
        info.init(bean, beans);
    }
}
