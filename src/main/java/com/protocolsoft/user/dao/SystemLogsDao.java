/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLogsDao.java
 *创建人: www    创建时间: 2017年12月20日
 */
package com.protocolsoft.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.user.bean.SystemLogsBean;

/**
 * @ClassName: SystemLogsDao
 * @Description: 系统日志
 * @author www
 *
 */
@Repository
public interface SystemLogsDao {

    /**
     * 
     * @Title: getAllLogs
     * @Description: 获取所有的日志
     * @return List<SystemLogsBean>
     * @author www
     */
    @Select("select l.id, time, user_id userId, realname userName, m.id moduleId, " 
            + "m.`name` moduleName, msg from ipm_logs l join ipm_system_user u on l.user_id = u.id" 
            + " join ipm_small_module m on l.module_id = m.id order by time desc")
    List<SystemLogsBean> getAllLogs();
}
