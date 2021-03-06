/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.email.service.impl;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.bean.MailBean;
import com.protocolsoft.email.dao.EmailDao;
import com.protocolsoft.email.service.EmailService;


/**
 * 
 * @ClassName: EmailServiceImpl
 * @author 刘斌
 *
 */
@Service
public class EmailServiceImpl implements EmailService{
    
    /**
     * 
     */
    @Autowired
    EmailDao emailDao;

    /**
     * @return int
     */
    @Override
    public int updateEmail(EmailBean bean) {
        int flag =0;
        //验证STMP服务器与端口
        int bools = isPortComm(bean);
        if(bools == 2){
            flag = 2;
        }else{
            //修改SMtP设置
            flag = emailDao.updateEmail(bean);
        }
        return flag;
    }

    /**
     * @return EmailBean
     */
    @Override
    public EmailBean getEmail() {
        return emailDao.getEmail();
    }
    
    /**
     * @return String
     */
    @Override
    public boolean senEmail(MailBean mail,
            String recipient)  {
        EmailBean bean =  emailDao.getEmail();
      //发件人邮箱地址：这里填写要发送出去的邮箱地址即可
        if(null==bean){
            return false;
        }
        String username = bean.getEmailUserName();
        String password = bean.getEmailPassword();
        MailSenderServiceImpl mailSender = new MailSenderServiceImpl(username, password);
        mailSender.send(recipient, mail);
        
        return true;
    }

    @Override
    public int pingStmp(String ipAddress) {
        int flag = 0;
        try {
            Runtime.getRuntime().exec("ping -c 3"+ ipAddress);
        } catch (IOException e) {
            flag = 2;
        }
        return flag;
    }

    @Override
    public int isPortComm(EmailBean bean) {
        int flag=0;
        Socket socket=null;
        try{
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(bean.getEmailServer(), 
                    Integer.parseInt(bean.getEmailPort()));
            socket.connect(socketAddress, 10000);
        }catch (Exception e){
            flag=2;
        }finally {
            try{
                socket.close();
            }catch (Exception e){

            }
        }
        return flag;
    }
}
