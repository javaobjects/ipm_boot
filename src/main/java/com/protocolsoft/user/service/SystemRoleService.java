/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserRoleService
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service;

import java.util.List;

import com.protocolsoft.user.bean.SystemRoleBean;

/**
 * UserRoleService
 * 2017年9月4日 上午9:38:25
 * @author long
 * @version
 * @see
 */
public interface SystemRoleService {
    
    /**
     * 获取user role信息
     * 2017年9月4日 上午9:39:11
     * @param
     * @return List<UserRoleBean>
     * @exception 
     * @see
     */
    List<SystemRoleBean> getUserRoleBean();
    
    
}
