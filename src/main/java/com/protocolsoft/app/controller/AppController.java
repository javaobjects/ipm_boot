/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppController.java
 *创建人: www    创建时间: 2018年1月10日
 */
package com.protocolsoft.app.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.app.bean.PlotDataBean;
import com.protocolsoft.app.bean.PlotParamBean;
import com.protocolsoft.app.bean.SessionParamsBean;
import com.protocolsoft.app.bean.SimpleDataBean;
import com.protocolsoft.app.bean.SimpleParamBean;
import com.protocolsoft.app.service.AppService;
import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.user.bean.ListColumnBean;

/**
 * @ClassName: AppController
 * @Description: 业务信息
 * @author www
 *
 */
@Controller
@RequestMapping(value = "/appController")
public class AppController {

    /**
     * 业务
     */
    @Autowired
    private AppService appService;
    
    /**
     * 
     * @Title: addApp
     * @Description: 添加
     * @param request
     * @param bean
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/addApp.do")
    public @ResponseBody Map<String, Integer> addApp(HttpServletRequest request, AppBusinessBean bean) {
        
        return appService.addApp(request, bean);
    }
    
    /**
     * 
     * @Title: deleteApp
     * @Description: 删除
     * @param request
     * @param bean
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/deleteApp.do")
    public @ResponseBody Map<String, Integer> deleteApp(HttpServletRequest request, AppBusinessBean bean) {
        
        return appService.deleteApp(request, bean);
    }

    /**
     * 
     * @Title: deleteAppByName
     * @Description: 根据IP端口删除应用
     * @param request
     * @param displayIp
     * @return Map<String,Integer>
     * @author yangjn
     */
    @RequestMapping(value = "/deleteAppByName.do")
    public @ResponseBody Map<String, Integer> deleteAppByDisplayIp(HttpServletRequest request, 
        int moduleId, String displayIp) {
        return appService.deleteAppByDisplayIp(request, moduleId, displayIp);
    }
    
    /**
     * 
     * @Title: updateApp
     * @Description: 更新
     * @param request
     * @param bean
     * @return Map<String,Integer>
     * @author www
     */
    @RequestMapping(value = "/updateApp.do")
    public @ResponseBody Map<String, Integer> updateApp(HttpServletRequest request, AppBusinessBean bean) {
        
        return appService.updateApp(request, bean);
    }
    
    /**
     * 
     * @Title: getAllAppByModuleId
     * @Description: 获取某一模块下所有业务
     * @param request 请求
     * @param moduleId 模块编号
     * @return List<AppBusinessBean>
     * @author www
     */
    @RequestMapping(value = "/getAllAppByModuleId.do")
    public @ResponseBody List<AppBusinessBean> getAllAppByModuleId(HttpServletRequest request, int moduleId) {
        
        return appService.getAllAppByModuleId(request, moduleId);
    }

    /**
     * 
     * @Title: getPlotData
     * @Description: 获取绘图数据
     * @param bean
     * @return PlotDataBean
     * @author www
     */
    @RequestMapping(value = "/getPlotData.do")
    public @ResponseBody PlotDataBean getPlotData(PlotParamBean bean) {
        
        return appService.getPlotData(bean);
    }
    
    /**
     * 
     * @Title: getSimpleData
     * @Description: 获取十字格数据
     * @param bean
     * @return SimpleDataBean
     * @author www
     */
    @RequestMapping(value = "/getSimpleData.do")
    public @ResponseBody SimpleDataBean getSimpleData(SimpleParamBean bean) {
        
        return appService.getSimpleData(bean);
    }
    
    /**
     * 
     * @Title: getAppListColumnByTypeId
     * @Description: 获取应用相关列信息
     * @param request 请求
     * @param moduleId 模块编号
     * @param typeId 类型编号
     * @return List<ListColumnBean>
     * @author www
     */
    @RequestMapping(value = "/getAppListColumn.do")
    public @ResponseBody List<ListColumnBean> getAppListColumnByTypeId(
            HttpServletRequest request, int moduleId, int typeId) {
        
        return appService.getAppListColumnByTypeId(request, moduleId, typeId);
    }

    /**
     * 
     * @Title: getAppStateList
     * @Description: 获取会话列表
     * @param bean
     * @return List<AppSessionBean>
     * @author www
     */
    @RequestMapping(value = "/getAppStateList.do")
    public @ResponseBody List<Object> getAppStateList(SessionParamsBean bean) {
        
        return appService.getAppStateList(bean);
    }
    
    /**
     * 
     * @Title: getRemoteAppStateList
     * @Description: 获取会话列表
     * @param bean
     * @return List<AppSessionBean>
     * @author www
     */
    @RequestMapping(value = "/getRemoteAppStateList.do")
    public @ResponseBody List<Object> getRemoteAppStateList(SessionParamsBean bean) {
        
        return appService.getRemoteAppStateList(bean);
    }

    /**
     * 
     * @Title: getVPAllAppList
     * @Description: 获取VP的所有应用（服务端，HTTP，ORACLE，MYSQL，SQLSERVER）
     * @param request 请求
     * @return String
     * @author yangjn
     */
    @RequestMapping(value = "/getVPApp.do")
    public @ResponseBody String getVPApp(HttpServletRequest request) {
        return appService.getVPApp(request);
    }

    /**
     * 
     * @Title: updateVPApp
     * @Description: 添加或修改VP应用
     * @param request
     * @param bean
     * @return Map<String,Integer>
     * @author yangjn
     */
    @RequestMapping(value = "/updateVPApp.do")
    public @ResponseBody Map<String, Integer> updateVPApp(HttpServletRequest request, AppBusinessBean bean) {
        return appService.updateVPApp(request, bean);
    }

    /**
     * 
     * @Title: deleteVPApp
     * @Description: 添加或修改VP应用
     * @param request
     * @param bean
     * @return Map<String,Integer>
     * @author yangjn
     */
    @RequestMapping(value = "/deleteVPApp.do")
    public @ResponseBody Map<String, Integer> deleteVPApp(HttpServletRequest request, AppBusinessBean bean) {
        return appService.deleteVPApp(request, bean);
    }
    
}
