/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserRoleDao
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.protocolsoft.user.bean.SystemRoleBean;

/**
 * UserRoleDao信息
 * 2017年9月4日 上午9:34:51
 * @author long
 * @version
 * @see
 */
@Component
public interface SystemRoleDao {
    /**
     * user role 信息
     * 2017年9月4日 上午9:35:31
     * @param
     * @return List<UserRoleBean>
     * @exception 
     * @see
     */
    @Select("select id,name,descrption  from ipm_system_role ")
    List<SystemRoleBean> getUserRoleBean();

}
