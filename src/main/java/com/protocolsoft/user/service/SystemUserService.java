/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserService
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;




import com.protocolsoft.user.bean.SystemUserBean;

/**
 * UserService
 * 2017年9月1日 下午2:04:21
 * @author long
 * @version
 * @see
 */
public interface SystemUserService {
    
    /**
     * 用户信息
     * 2017年9月1日 下午2:18:47
     * @param
     * @return UserBean
     * @exception 
     * @see
     */
    SystemUserBean getUserBean(String userName, String password);
    
    /**
     * session用户信息
     * 2017年9月4日 上午10:10:33
     * @param
     * @return UserBean
     * @exception 
     * @see
     */
    SystemUserBean getSessionUserBean(HttpServletRequest request);
    
    /**
     * 添加用户
     * 2017年9月4日 下午5:28:31
     * @param
     * @return UserBean
     * @exception 
     * @see
     */
    SystemUserBean addUser(SystemUserBean userBean);
    
    /**
     * 获得用户信息
     * 2017年9月5日 上午9:52:37
     * @param
     * @return List<UserBean>
     * @exception 
     * @see
     */
    List<SystemUserBean> getUserList();
    
    /**
     * 根据id删除用户
     * 2017年9月5日 下午2:36:15
     * @param
     * @return Map<String, String>
     * @exception 
     * @see
     */
    Map<String, String> delUser(int id);
    
    /**
     * 修改password
     * 2017年9月5日 下午6:28:54
     * @param
     * @return SystemUserBean
     * @exception 
     * @see
     */
    SystemUserBean updateUserPassword(int userId, String oldPassword,
            String newPassword, String confirmPassword);

    /**
     * 更新用户
     * 2017年9月6日 下午2:55:56
     * @param
     * @return SystemUserBean
     * @exception 
     * @see 
     */
    SystemUserBean updateUser(SystemUserBean systemUserBean);
    
    /**
     * 
     * @Title: cancellation
     * @Description: 注销用户方法
     * @param request  请求信息
     * @param systemUserBean void 用户信息
     * @author wangjianmin
     */
    void  cancellation(HttpServletRequest request, SystemUserBean systemUserBean);
    
    /**
     * 
     * @Title: getUserBeanById
     * @Description: 查询一个用户
     * @param id 用户id
     * @return SystemUserBean
     * @author wangjianmin
     */
    SystemUserBean getUserBeanById(int id);

    /**
     * 
     * @Title: updateUserLoginState
     * @Description: 修改登录状态
     * @param id 用户id
     * @param loginState void 用户登录状态
     * @author wangjianmin
     */
    void  updateUserLoginState(int id, int loginState);
    
    /**
     * 
     * @Title: duplicateLogin
     * @Description: 同用户登录，踢掉第一个
     * @param userName 当前登录名称
     * @param map void 接收session信息
     * @author wangjianmin
     */
    void duplicateLogin(List<Map<String, Object>> map, String userName);
    
    /**
     * 
     * @Title: duplicateLogins
     * @Description: 删除保存的session信息
     * @param map   session信息集合
     * @param userName void  注销用户
     * @author wangjianmin
     */
    void duplicateLogins(List<Map<String, Object>> map, String userName);
    
    /**
     * 
     * @Title: getUserNameLoginOvertime
     * @Description: 根据用户名，查询超时状态
     * @param userName 用户名
     * @return int
     * @author wangjianmin
     */
    int getUserNameLoginOvertime(String userName);
}
