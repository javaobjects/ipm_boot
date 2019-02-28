/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewController
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.view.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.view.bean.MonitorViewBean;
import com.protocolsoft.view.service.MonitorViewService;

/**
 * MonitorViewController
 * 2017年9月15日 上午10:51:51
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorViewController {
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * monitorViewService注入
     */
    @Autowired(required = false)
    private MonitorViewService monitorViewService;
    
    /**
     * 获取驾驶舱信息
     * 2017年9月15日 上午10:54:27
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getMonitorViewList.do")
    @ResponseBody
    public List<MonitorViewBean> getMonitorViewList(HttpServletRequest request, HttpServletResponse response){
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        int userId=0;
        int roleId=0;
        if (systemUserBean!=null){
            userId=systemUserBean.getId();
            roleId=systemUserBean.getRoleId();
        }
        return monitorViewService.getMonitorViewList(userId, roleId);
    }
    
    /**
     * 添加驾驶舱
     * 2017年9月15日 下午2:05:14
     * @param
     * @return MonitorViewBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/addMonitorView.do")
    @ResponseBody
    public MonitorViewBean addMonitorView(HttpServletRequest request, MonitorViewBean monitorViewBean){
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        int userId=0;
        String userName="admin";
        int roleId=1;
        if (systemUserBean!=null){
            userId=systemUserBean.getId();
            userName=systemUserBean.getUserName();
            roleId=systemUserBean.getRoleId();
        }
        monitorViewBean.setCreateUserId(userId);
        monitorViewBean.setUserName(userName);
        monitorViewBean.setRoleId(roleId);
        return monitorViewService.addMonitorView(request, monitorViewBean);
    }
    
    /**
     * 修改驾驶舱
     * 2017年9月15日 下午3:19:56
     * @param
     * @return MonitorViewBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateMonitorViewById.do")
    @ResponseBody
    public MonitorViewBean updateMonitorViewById(HttpServletRequest request, MonitorViewBean monitorViewBean){
        SystemUserBean systemUserBean=userService.getSessionUserBean(request);
        int userId=0;
        String userName="admin";
        int roleId=1;
        if (systemUserBean!=null){
            userId=systemUserBean.getId();
            userName=systemUserBean.getUserName();
            roleId=systemUserBean.getRoleId();
        }
        monitorViewBean.setCreateUserId(userId);
        monitorViewBean.setUserName(userName);
        monitorViewBean.setRoleId(roleId);
        return monitorViewService.updateMonitorViewById(request, monitorViewBean);
    }
    
    /**
     * 删除驾驶舱
     * 2017年9月15日 下午4:13:39
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/delMonitorView.do")
    @ResponseBody
    public Map<String, String> delMonitorView(HttpServletRequest request, int id){
        return monitorViewService.delMonitorView(request, id);
    }
    
    /**
     * 
     * @Title: getViewById
     * @Description: 获取驾驶舱信息
     * @param id 编号
     * @return MonitorViewBean
     * @author www
     */
    @RequestMapping(value = "/getViewById.do")
    @ResponseBody
    public MonitorViewBean getViewById(int id) {
        return monitorViewService.getViewById(id);
    }
    
    /**
     * 
     * @Title: updateViewStatus
     * @Description: 修改状态
     * @param id
     * @param status
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/updateViewStatus.do")
    @ResponseBody
    public Map<String, Boolean> updateViewStatus(int id, int status) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("status", monitorViewService.updateMonitorViewStatus(id, status));
        
        return map;
    }
}
