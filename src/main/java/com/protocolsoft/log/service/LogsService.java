/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:LogsService
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.service;

import com.protocolsoft.log.bean.LogsBean;

/**
 * 日志管理业务逻辑接口
 * 2017年9月4日 下午3:07:52
 * @author yan
 * @version
 * @see
 */
public interface LogsService {

    /**
     * 新增
     * 2017年9月4日 下午3:08:36
     * @param LogsBean
     * @return String
     * @exception 
     * @see
     */
    String addLogs(LogsBean bean);
}
