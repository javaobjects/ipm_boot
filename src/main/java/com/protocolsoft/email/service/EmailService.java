/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * 
 */
package com.protocolsoft.email.service;



import com.protocolsoft.email.bean.EmailBean;
import com.protocolsoft.email.bean.MailBean;

/**
 * 
 * @ClassName: EmailService
 * @author 刘斌
 *
 */
public interface EmailService {
    
    /**
     * 
     * @Title: updateEmail
     * @param bean
     * @return int
     * @author 刘斌
     */
    int updateEmail(EmailBean bean);
    
    /**
     * 
     * @Title: getEmail
     * @return EmailBean
     * @author 刘斌
     */
    EmailBean getEmail();
    
    /**
     * 
     * @Title: senEmail
     * @return String
     * @author 刘斌
     */
    boolean senEmail(MailBean mail, String recipient);

    /**
     * 
     * @Title: pingStmp
     * @Description: STMP服务器地址是否能ping通
     * @param ipAddress STMP服务器地址
     * @return int
     * @author wangjianmin
     */
    int pingStmp(String ipAddress);
    
    /**
     * 
     * @Title: isPortComm
     * @Description: STMP服务器的端口是否可用
     * @param bean  接收参数
     * @return int
     * @author wangjianmin
     */
    int isPortComm(EmailBean bean);
}
