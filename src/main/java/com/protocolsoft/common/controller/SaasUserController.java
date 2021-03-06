/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SaasUserController.java
 *创建人: WWW    创建时间: 2018年8月9日
 */
package com.protocolsoft.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.common.bean.SaasUserBean;
import com.protocolsoft.common.service.impl.CenterIpService;
import com.protocolsoft.common.service.impl.SaasUserService;

/**
 * @ClassName: SaasUserController
 * @Description: 用户控制类
 * @author WWW
 *
 */
@Controller
@RequestMapping(value = "/saasuser")
public class SaasUserController {

    /**
     * 用户业务
     */
    @Autowired
    private SaasUserService service;
    
    /**
     * 
     * @Title: addSaasUser
     * @Description: 添加用户信息
     * @param request 请求
     * @param bean 参数
     * @return boolean
     * @author WWW
     */
    @RequestMapping(value = "/addSaasUser.do")
    public @ResponseBody Map<String, Boolean> addSaasUser(HttpServletRequest request, SaasUserBean bean) {
        boolean bool = service.addSaasUser(request, bean);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: updSaasUser
     * @Description: 更新用户信息
     * @param request 请求
     * @param bean 参数
     * @return boolean
     * @author WWW
     */
    @RequestMapping(value = "/updSaasUser.do")
    public @ResponseBody Map<String, Boolean> updSaasUser(HttpServletRequest request, SaasUserBean bean) {
        boolean bool = service.updSaasUser(request, bean);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: delSaasUser
     * @Description: 删除用户信息
     * @param request 请求
     * @param id 编号
     * @return boolean
     * @author WWW
     */
    @RequestMapping(value = "/delSaasUser.do")
    public @ResponseBody Map<String, Boolean> delSaasUser(HttpServletRequest request, int id) {
        boolean bool = service.delSaasUser(request, id);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("stauts", bool);
        
        return map;
    }
    
    /**
     * 
     * @Title: getAllUser
     * @Description: 获取接入用户信息除本机
     * @return List<SaasUserBean>
     * @author WWW
     */
    @RequestMapping(value = "/getAllUser.do")
    public @ResponseBody List<SaasUserBean> getAllUser() {
        
        return service.getAllUser();
    }
    
    /**
     * 
     * @Title: getUserByDelay
     * @Description: 获取服务器通信时延小于某个时延的用户信息
     * @return List<SaasUserBean>
     * @author WWW
     */
    @RequestMapping(value = "/getUserByDelay.do")
    public @ResponseBody List<SaasUserBean> getUserByDelay() {
        int maxDelay = CenterIpService.MAX_DELAY;
        
        return service.getUserByDelay(maxDelay);
    }
    
    /**
     * 
     * @Title: getUserById
     * @Description: 通过编号获取用户信息
     * @param id 编号
     * @return SaasUserBean
     * @author WWW
     */
    @RequestMapping(value = "/getUserById.do")
    public @ResponseBody SaasUserBean getUserById(int id) {
        
        return service.getUserById(id);
    }
}
