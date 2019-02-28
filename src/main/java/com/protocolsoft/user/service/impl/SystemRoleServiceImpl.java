/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserRoleServiceImpl
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.user.bean.SystemRoleBean;
import com.protocolsoft.user.dao.SystemRoleDao;
import com.protocolsoft.user.service.SystemRoleService;

/**
 *
 * 2017年9月4日 上午9:46:07
 * @author long
 * @version
 * @see
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {
    
    /**
     * UserRoleDao注入
     */
    @Autowired(required=false)
    private SystemRoleDao userRoleDao;
    @Override
    public List<SystemRoleBean> getUserRoleBean() {
        return userRoleDao.getUserRoleBean();
    }

}
