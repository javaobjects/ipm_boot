/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:LogsService
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.log.service.LogsService;

/**
 * 日志管理业务逻辑接口实现类
 * 2017年9月4日 下午3:09:14
 * @author yan
 * @version
 * @see
 */
@Service
public class LogsServiceImpl implements LogsService{

    /**
     * ipm_logs表Dao
     */
    @Autowired
    private LogsDao logsDao;
    
    /* (non-Javadoc)
     * @see com.protocolsoft.log.service.LogsService#addLogs(com.protocolsoft.log.bean.LogsBean)
     */
    @Override
    public String addLogs(LogsBean bean) {
        try {
            logsDao.insertLogs(bean);
            return "success";
        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * @return the logsDao
     */
    public LogsDao getLogsDao() {
        return logsDao;
    }

    /**
     * @param logsDao the logsDao to set
     */
    public void setLogsDao(LogsDao logsDao) {
        this.logsDao = logsDao;
    }
}
