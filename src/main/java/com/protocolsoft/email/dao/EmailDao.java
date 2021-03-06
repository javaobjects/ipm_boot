/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.email.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.email.bean.EmailBean;

/**
 * 
 * @ClassName: EmailDao
 * @Description: 邮件Dao层接口
 * @author wangjianmin
 *
 */
@Repository
public interface EmailDao {
    
    /**
     * 
     * @Title: getEmail
     * @Description: 查询系统邮箱设置
     * @return EmailBean
     * @author wangjianmin
     */
    @Select("select id,email_server as emailServer,email_user_name as emailUserName,"
            + "email_password as emailPassword,user_name as userName,email_port  as emailPort"
            + " from ipm_email_control where id =1")
    EmailBean getEmail();
    
    /**
     * 
     * @Title: updateEmail
     * @Description: 修改邮箱设置
     * @param bean 接收修改邮箱参数
     * @return int
     * @author wangjianmin
     */
    @Update("update ipm_email_control set email_server = #{emailServer},email_user_name = #{emailUserName},"
            + " email_password = #{emailPassword},user_name = #{userName},"
            + " email_port=#{emailPort} where id = 1")
    int updateEmail(EmailBean bean);
}
