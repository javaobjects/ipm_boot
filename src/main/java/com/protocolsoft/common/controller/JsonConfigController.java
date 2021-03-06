/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: JsonConfigController.java
 *创建人: www    创建时间: 2017年9月22日
 */
package com.protocolsoft.common.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.service.LogsService;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.JsonFileUtil;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.view.bean.MonitorViewBean;
import com.protocolsoft.view.service.MonitorViewService;

/**
 * @ClassName: JsonConfigController
 * @Description: 配置管理
 * @author www
 */
@Controller
@RequestMapping(value = "/viewConfig")
public class JsonConfigController {
    
    /**
     * JSON工具
     */
    private JsonFileUtil util = JsonFileUtil.getInstance();
    
    /**
     * monitorViewService注入
     */
    @Autowired(required = false)
    private MonitorViewService monitorViewService;
    
    /**
     * 日志管理业务对象
     */
    @Autowired
    private LogsService logsService;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * 
     * @Title: getModuleConfig
     * @Description: 获取业务视图配置
     * @param moduleId 模块编号
     * @param busiId 业务编号
     * @return String
     * @throws IOException
     * @author www
     */
    @RequestMapping(value = "/getModuleConfig.do")
    @ResponseBody
    public String getModuleConfig(int moduleId, int busiId) {
        ModuleType type = ModuleType.getModuleType(moduleId);
        String content = null;
        try {
            content = util.getJsonContent(type, busiId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return content;
    }
    
    /**
     * 
     * @Title: updModuleConfig
     * @Description: 修改业务视图设置
     * @param moduleId 模块编号
     * @param busiId 业务编号
     * @param request 请求
     * @param content 配置信息
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/updModuleConfig.do")
    @ResponseBody
    public Map<String, Boolean> updModuleConfig(HttpServletRequest request, int moduleId, int busiId, String content) {
        Map<String, Boolean> data = new HashMap<String, Boolean>();
        ModuleType type = ModuleType.getModuleType(moduleId);
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        try {
            boolean bool = util.updJsonFile(type, busiId, content);
            data.put("stauts", bool);
        } catch (IOException e) {
            data.put("stauts", false);
            e.printStackTrace();
        }
        if (moduleId == 0 && busiId != 0) {
            MonitorViewBean bean = monitorViewService.getViewById(busiId);
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(systemUserBean.getId());
            logsBean.setModuleId(8);
            logsBean.setMsg("保存" + bean.getName() + "驾驶舱视图内容");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsService.addLogs(logsBean);
        }
        
        return data;
    }
    
    /**
     * 
     * @Title: getViewConfig
     * @Description: 获取驾驶舱配置
     * @param busiId 业务编号
     * @return String
     * @throws IOException
     * @author www
     */
    @RequestMapping(value = "/getViewConfig.do")
    @ResponseBody
    public String getViewConfig(int busiId) {
        String content = null;
        try {
            content = util.getJsonContent(ModuleType.VIEW, busiId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return content;
    }
    
    /**
     * 
     * @Title: updViewConfig
     * @Description: 修改驾驶舱配置信息
     * @param busiId 驾驶舱编号
     * @param content 配置信息
     * @return Map<String,Boolean>
     * @author www
     */
    public Map<String, Boolean> updViewConfig(int busiId, String content) {
        Map<String, Boolean> data = new HashMap<String, Boolean>();
        try {
            boolean bool = util.updJsonFile(ModuleType.VIEW, busiId, content);
            data.put("stauts", bool);
        } catch (IOException e) {
            data.put("stauts", false);
            e.printStackTrace();
        }
        
        return data;
    }
}
