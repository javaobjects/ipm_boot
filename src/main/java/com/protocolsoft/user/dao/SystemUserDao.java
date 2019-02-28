/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserDao
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.protocolsoft.user.bean.SystemUserBean;

/**
 * 用户信息dao
 * 2017年9月1日 下午1:54:41
 * @author long
 * @version
 * @see
 */
@Component
public interface SystemUserDao {
    /**
     * 登陆信息 
     * 2017年9月1日 下午2:09:18
     * @param
     * @return UserBean
     * @exception
     * @see
     */
    @Select("select id,role_id as roleId,username as userName,password,realname as realName,"
            + "email,telephone,createtime as createTime,loginState,loginOvertime from ipm_system_user  "
            + "where binary username=#{userName} and binary password=#{password}")
    SystemUserBean getUserBean(@Param("userName") String userName, @Param("password") String password);
    
    /**
     * 获取用户信息
     * 2017年9月5日 上午9:45:26
     * @param
     * @return List<UserBean>
     * @exception 
     * @see
     */
    @Select(" select u.id,u.role_id as roleId,u.username as userName,u.`password`,u.realname as realName, "
            + " u.email,u.telephone,u.createtime as createTime,r.`name` as roleName "
            + " from ipm_system_user u left join ipm_system_role r on u.role_id=r.id ")
    List<SystemUserBean> getUserList();
    
    /**
     * 用户名是否存在
     * 2017年9月4日 上午11:35:09
     * @param
     * @return boolean
     * @exception 
     * @see
     */
    @Select("select count(1) from ipm_system_user where binary username=#{userName} and id!=#{id}")
    boolean getUserBeanByName(@Param("userName") String userName, @Param("id") int id);
    
    /**
     * 根据id获取用户信息 
     * 2017年9月6日 上午10:13:30
     * @param
     * @return SystemUserBean
     * @exception 
     * @see
     */
    @Select("select id,role_id as roleId,username as userName,password,realname as realName,"
            + " email,telephone,createtime as createTime from ipm_system_user  "
            + " where id=#{id} ")
    SystemUserBean getUserBeanById(@Param("id") int id);
    
    /**
     * 添加用户
     * 2017年9月4日 下午5:43:39
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Insert("insert into ipm_system_user (username,`password`,realname,email,telephone,role_id,createtime) "
            + " values (#{userName}, #{password},#{realName},#{email},#{telephone},#{roleId},UNIX_TIMESTAMP(NOW()))")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(SystemUserBean userBean);
    
    /**
     * 删除用户
     * 2017年9月5日 下午2:33:00
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Delete("delete from ipm_system_user where id=#{id}")
    boolean delUser(int id);
    
    /**
     * 根据用户id修改密码
     * 2017年9月6日 上午10:23:57
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update("update ipm_system_user set `password`=#{password}  where id=#{id}")
    int updateUserPasswordById(@Param("id") int id, @Param("password") String password);
    
    /**
     * 根据id更新用户信息
     * 2017年9月6日 下午3:21:58
     * @param
     * @return int
     * @exception 
     * @see
     */
    @Update("update ipm_system_user set `role_id`=#{roleId},`username`=#{userName}"
            + " ,`realname`=#{realName},`email`=#{email},`telephone`=#{telephone}  where id=#{id}")
    int updateUserById(SystemUserBean systemUserBean);

    /**
     * 
     * @Title: getUserListByRoleId
     * @Description: 根据userid,roleid获取用户信息 
     * @param id  用户id
     * @param roleId 权限id
     * @return List<SystemUserBean>
     * @author wangjianmin
     */
    @Select(" select u.id,u.role_id as roleId,u.username as userName,u.`password`,u.realname as realName, "
            + " u.email,u.telephone,u.createtime as createTime,r.`name` as roleName "
            + " from ipm_system_user u left join ipm_system_role r on u.role_id=r.id "
            + " where u.id=#{id} and u.role_id=#{roleId} ")
    List<SystemUserBean> getUserListByRoleId(@Param("id") int id, @Param("roleId") int roleId);
    
    /**
     * 
     * @Title: getUserLoginState
     * @Description: 修改登录状态
     * @param id    用户id
     * @param loginState void 用户登录状态
     * @author wangjianmin
     */
    @Update("update ipm_system_user set loginState =#{loginState},loginOvertime=#{loginOvertime} where id =#{id}")
    void  getUserLoginState(@Param("id") int id, @Param("loginState") int loginState,  @Param("loginOvertime") int loginOvertime);

    /**
     * 
     * @Title: getUserNameLoginOvertime
     * @Description: 根据用户名，查询超时状态
     * @param username 用户名
     * @return int
     * @author wangjianmin
     */
    @Select("SELECT loginOvertime FROM  ipm_system_user WHERE username = #{username}")
    int getUserNameLoginOvertime(@Param("username") String username);
}
