/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ServerManagementController
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.servers.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.servers.service.ServerManagementService;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;
import com.protocolsoft.utils.DateAppsUtils;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.utils.bean.TimeDefaultBean;

/**
 * 服务端管理Controller
 * 2017年9月1日 上午10:21:46
 * @author yan
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/serverManagement")
public class ServerManagementController {

    /**
     * 服务端管理业务对象
     */
    @Autowired
    private ServerManagementService serverManagementService;
    
    /**
     * 用户配置列表字段
     */
    @Autowired
    private UserListColumnService userListColumnService;
    
    /**
     * 用户
     */
    @Autowired
    private SystemUserService systemUserService;
    

    /**
     * 
     * @Title: addServerSide
     * @Description: 增加服务端
     * @param request 请求
     * @param bean 接收添加服务端参数
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    @RequestMapping(value="addServerSide.do")
    @ResponseBody 
    public Map<String, Integer> addServerSide(HttpServletRequest request, AppBusinessBean bean){
        bean.setModuleId(12);
        
        return serverManagementService.addServerSide(bean, request);
    }
    
    /**
     * 获取全部
     * 2017年9月6日 下午3:57:58
     * @return List<AppBusinessBean>
     * @exception 
     * @see
     */
    @RequestMapping(value="getAllServerSide.do")
    @ResponseBody 
    public List<AppBusinessBean> getAllServerSide(HttpServletRequest request){
        return serverManagementService.getAllServerSide(request, 12);
    }
    
    /**
     * 获取
     * 2017年9月1日 下午4:31:58
     * @param id
     * @return AppBusinessBean
     * @exception 
     * @see
     */
    @RequestMapping(value="getServerSide.do")
    @ResponseBody 
    public AppBusinessBean getServerSide(Integer id){
        AppBusinessBean bean = serverManagementService.getServerSide(id);
        return bean;
    }

    /**
     * 
     * @Title: updateServerSide
     * @Description: 修改服务端
     * @param request 请求
     * @param bean  接收修改服务端参数
     * @return Map<String, Integer> 
     * @author wangjianmin
     */
    @RequestMapping(value="updateServerSide.do")
    @ResponseBody 
    public Map<String, Integer> updateServerSide(HttpServletRequest request, AppBusinessBean bean){
        
        return serverManagementService.updateServerSide(request, bean);
    }

    /**
     * 
     * @Title: deleteServerSide
     * @Description 删除 服务端
     * @param request 请求
     * @param id  业务id
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    @RequestMapping(value="deleteServerSide.do")
    @ResponseBody 
    public Map<String, Integer> deleteServerSide(HttpServletRequest request, Integer id){
        
        return serverManagementService.deleteServerSide(12, id, request);
    }
    
    /**
     * 获取用户配置对应列表字段
     * 2017年9月15日 下午4:51:29
     * @return List<ListColumnBean>
     * @exception 
     * @see
     */
    @RequestMapping(value="getServerSideUserListColumn.do")
    @ResponseBody 
    public List<ListColumnBean> getServerSideUserListColumn(HttpServletRequest request){
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        List<ListColumnBean> beans = userListColumnService.getUserListColumn(user.getId(), 
                ModuleType.SERVER.getModuleId());
        return beans;
    }
    
    /**
     * 获取服务端图形
     * 2017年9月26日 下午5:54:12
     * @param drawingOptionsBean
     * @param serverId
     * @return Map<String,Object>
     * @exception 
     * @see
     */
    @RequestMapping(value="getServerSideGraphical.do")
    @ResponseBody 
    public Map<String, Object> getServerSideGraphical(DrawingOptionsBean drawingOptionsBean, Integer serverId){
        Map<String, Object> resultMap = null;
        resultMap = serverManagementService.getServerSideGraphical(drawingOptionsBean, serverId);
        
        return resultMap;
    }
    
    /**
     * 获取服务端单个点
     * 2017年10月23日 上午11:12:02
     * @param drawingOptionsBean
     * @param serverId
     * @return Map<String,Object>
     * @exception 
     * @see
     */
    @RequestMapping(value="getServerSideSingleValueData.do")
    @ResponseBody 
    public Map<String, Object> getServerSideSingleValueData(DrawingOptionsBean drawingOptionsBean, Integer serverId){
        
        Map<String, Object>  resultMap = null;
        
        String isCalcul = "";
        
        if (null == drawingOptionsBean.getStep()){
            drawingOptionsBean.setStep(10);
        }
        
        TimeDefaultBean timeDefaultBean = DateAppsUtils.getRrdDefaultTime();
        
        //如果等于true 代表前端给后端传了时间
        boolean flag = true;
        
        //如果默认时间 就取平均值
        if (null == drawingOptionsBean.getStarttime()){
            drawingOptionsBean.setStarttime(timeDefaultBean.getStarttime());
            isCalcul = "AVG";
            flag = false;
        } 
        //如果默认时间 就取平均值
        if (null == drawingOptionsBean.getEndtime()){
            drawingOptionsBean.setEndtime(timeDefaultBean.getEndtime());
            isCalcul = "AVG";
            flag = false;
        }
        
        if (null != drawingOptionsBean.getWatchpointId() 
                && (null != drawingOptionsBean.getPlotIds() && !"".equals(drawingOptionsBean.getPlotIds())) 
                && null != serverId){
            resultMap = serverManagementService.getServerSideSingleValueData(drawingOptionsBean, serverId, 
                    isCalcul, flag);
        }
        return resultMap;
    }
}
