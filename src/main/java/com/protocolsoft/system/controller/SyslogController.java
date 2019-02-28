/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SyslogController.java
 *创建人: www    创建时间: 2018年3月31日
 */
package com.protocolsoft.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.system.bean.SyslogBean;
import com.protocolsoft.system.service.impl.SyslogService;

/**
 * @ClassName: SyslogController
 * @Description: SYSLOG
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/syslog")
public class SyslogController {

    /**
     * 业务
     */
    @Autowired
    private SyslogService service;
    
    /**
     * 
     * @Title: getSyslogInfo
     * @Description: 获取服务器信息
     * @return List<SyslogBean>
     * @author www
     */
    @RequestMapping(value = "/getSyslogInfo.do")
    @ResponseBody
    public List<SyslogBean> getSyslogInfo() {
        
        return service.getSyslogInfo();
    }
    
    /**
     * 
     * @Title: addSyslog
     * @Description: 添加
     * @param bean void
     * @return Map<String, Boolean>
     * @author www
     */
    @RequestMapping(value = "/addSyslog.do")
    @ResponseBody
    public Map<String, Boolean> addSyslog(HttpServletRequest request, SyslogBean bean) {
        
        return service.addSyslog(request, bean);
    }

    /**
     * 
     * @Title: delSyslog
     * @Description: 删除
     * @param id 编号
     * @return Map<String, Boolean>
     * @author www
     */
    @RequestMapping(value = "/delSyslog.do")
    @ResponseBody
    public Map<String, Boolean> delSyslog(HttpServletRequest request, int id) {
        
        return service.delSyslog(request, id);
    }

    /**
     * 
     * @Title: updSyslog
     * @Description: 修改服务器信息
     * @param bean 参数
     * @return boolean
     * @author www
     */
    @RequestMapping(value = "/updSyslog.do")
    @ResponseBody
    public Map<String, Boolean> updSyslog(HttpServletRequest request, SyslogBean bean) {
        
        return service.updSyslog(request, bean);
    }
}
