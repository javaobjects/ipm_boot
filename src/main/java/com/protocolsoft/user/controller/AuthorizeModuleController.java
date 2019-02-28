/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AuthorizeModuleController
 *创建人:wjm    创建时间:2017年9月8日
 */
package com.protocolsoft.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.service.impl.AuthorizeModuleServer;

/**
 * ipm_authorize_module表  Controller 层
 * 2017年9月8日 下午3:44:11
 * @author wjm
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/authorizeModuleController")
public class AuthorizeModuleController {
    
    /**
     * 注入authorizeModuleServer
     */
    @Autowired
    AuthorizeModuleServer authorizeModuleServer;
    
    /**
     * 查询所有
     * 2017年9月8日 下午3:57:40
     * @param
     * @return List<Map<String,String>>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getFindAll.do")
    @ResponseBody
    public List<Map<String, String>> selectAllAuthorizeModule(){
        List<Map<String, String>> list = authorizeModuleServer.selectAllFilterAuthorizeModule();
        return list;
    }
    
    
    /**
     * 查询所有
     * 2018年3月28日 下午3:57:40
     * @param
     * @return List<Map<String,String>>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getFindAllForFlowCtl.do")
    @ResponseBody
    public List<Map<String, String>> selectAuthorizeModuleForFlowCtl(){
        List<Map<String, String>> list = authorizeModuleServer.selectAuthorizeModuleForFlowCtl();
        return list;
    }
    
    
    /**
     * 
     * @Title: selectIsOpen
     * @Description: 是否支持多个
     * @return Map<String, Boolean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getSelectIsOpen.do")
    @ResponseBody
    public Map<String, Boolean> selectIsOpen(){
        Map<String, Boolean> list = authorizeModuleServer.selectIsopen();
        return list;
    }
    
    /**
     * 
     * @Title: getAuthorizeAppModule
     * @Description: 获取应用模块信息
     * @return List<JurisModuleBean>
     * @author WWW
     */
    @RequestMapping(value = "/getAuthorizeAppModule.do")
    @ResponseBody
    public List<JurisModuleBean> getAuthorizeAppModule() {
        
        return authorizeModuleServer.getAuthorizeAppModule();
    }
}
