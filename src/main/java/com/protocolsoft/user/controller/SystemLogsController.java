/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLogsController.java
 *创建人: www    创建时间: 2017年12月20日
 */
package com.protocolsoft.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.user.bean.SystemLogsBean;
import com.protocolsoft.user.service.impl.SystemLogsService;

/**
 * @ClassName: SystemLogsController
 * @Description: 系统日志
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/systemLogs")
public class SystemLogsController {

    /**
     * 系统业务类
     */
    @Autowired
    private SystemLogsService service;
    
    /**
     * 
     * @Title: getAllLogs
     * @Description: 系统日志
     * @return List<SystemLogsBean>
     * @author www
     */
    @RequestMapping(value = "/getAllLogs")
    @ResponseBody
    public List<SystemLogsBean> getAllLogs() {
        
        return service.getAllLogs();
    }
}
