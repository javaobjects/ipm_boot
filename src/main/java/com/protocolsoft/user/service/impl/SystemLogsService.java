/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLogsService.java
 *创建人: www    创建时间: 2017年12月20日
 */
package com.protocolsoft.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.user.bean.SystemLogsBean;
import com.protocolsoft.user.dao.SystemLogsDao;

/**
 * @ClassName: SystemLogsService
 * @Description: 系统日志
 * @author www
 *
 */
@Service
public class SystemLogsService {

    /**
     * 系统DAO
     */
    @Autowired
    private SystemLogsDao dao;
    
    /**
     * 
     * @Title: getAllLogs
     * @Description: 获取所有的日志
     * @return List<SystemLogsBean>
     * @author www
     */
    public List<SystemLogsBean> getAllLogs() {
        
        return dao.getAllLogs();
    }
}
