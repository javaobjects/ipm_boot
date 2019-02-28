/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SaasUserDao.java
 *创建人: WWW    创建时间: 2018年8月6日
 */
package com.protocolsoft.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.common.bean.SaasUserBean;

/**
 * @ClassName: SaasUserDao
 * @Description: 用户DAO
 * @author WWW
 *
 */
@Repository
public interface SaasUserDao {

    /**
     * 
     * @Title: addSaasUser
     * @Description: 添加用户信息
     * @param bean 参数
     * @return int 
     * @author WWW
     */
    @Insert("insert into ipm_center_user(center_id, contacts, telephone, email, uptime, limitdate, state, version) "
            + "values (#{id}, #{contacts}, #{telephone}, #{email}, #{uptime}, #{limitdate}, #{state}, #{version})")
    int addSaasUser(SaasUserBean bean);
    
    /**
     * 
     * @Title: updSaasUser
     * @Description: 更新用户信息
     * @param bean 参数
     * @return int
     * @author WWW
     */
    @Update("update ipm_center_user set contacts=#{contacts}, telephone=#{telephone}, email=#{email}, uptime=#{uptime}, "
            + "limitdate=#{limitdate}, state=#{state}, version=#{version} where center_id = #{id}")
    int updSaasUser(SaasUserBean bean);
    
    /**
     * 
     * @Title: delSaasUser
     * @Description: 删除用户信息
     * @param id 编号
     * @return int
     * @author WWW
     */
    @Delete("delete from ipm_center_user where center_id = #{id}")
    int delSaasUser(@Param("id") int id);
    
    /**
     * 
     * @Title: getAllUser
     * @Description: 获取所有用户信息
     * @return List<SaasUserBean>
     * @author WWW
     */
    @Select("select i.id id, i.name name, ip, port, contacts, telephone, email, uptime, limitdate, state, "
            + "version, delay from ipm_center_ip i join ipm_center_user u on i.id = u.center_id")
    List<SaasUserBean> getAllUser();
    
    /**
     * 
     * @Title: getAccessUser
     * @Description: 获取接入用户信息除本机
     * @return List<SaasUserBean>
     * @author WWW
     */
    @Select("select i.id id, i.name name, ip, port, contacts, telephone, email, uptime, limitdate, state, "
            + "version, delay from ipm_center_ip i join ipm_center_user u on i.id = u.center_id where i.id != 1")
    List<SaasUserBean> getAccessUser();
    
    /**
     * 
     * @Title: getUserByDelay
     * @Description: 获取服务器通信时延小于某个时延的用户信息
     * @return List<SaasUserBean>
     * @author WWW
     */
    @Select("select i.id id, i.name name, ip, port, contacts, telephone, email, uptime, limitdate, state, version, delay "
            + "from ipm_center_ip i join ipm_center_user u on i.id = u.center_id where delay <= #{delay}")
    List<SaasUserBean> getUserByDelay(@Param("delay") int delay);
    
    /**
     * 
     * @Title: getUserById
     * @Description: 通过编号获取用户信息
     * @param id 编号
     * @return SaasUserBean
     * @author WWW
     */
    @Select("select i.id id, i.name name, ip, port, contacts, telephone, email, uptime, limitdate, state, version "
            + "from ipm_center_ip i join ipm_center_user u on i.id = u.center_id where i.id = #{id}")
    SaasUserBean getUserById(@Param("id") int id);
    
    /**
     * 
     * @Title: updateCenterDelay
     * @Description: 更新时延
     * @param id     Center编号
     * @param delay  时延
     * @return int
     * @author wangjianmin
     */
    @Update(" update ipm_center_ip set delay = #{delay} where id = #{id}")
    int  updateCenterDelay(@Param("id") int id, @Param("delay") long delay);
}
