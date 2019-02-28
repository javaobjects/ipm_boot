/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserRoleController
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.user.bean.SystemRoleBean;
import com.protocolsoft.user.service.SystemRoleService;

/**
 * UserRoleController
 * 2017年9月4日 上午9:40:56
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/userRole")
public class SystemRoleController {
    
    /**
     * userRoleService注入
     */
    @Autowired(required = false)
    private SystemRoleService userRoleService;
    /**
     * 获取user role信息
     * 2017年9月4日 上午9:44:46
     * @param
     * @return List<UserRoleBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getUserRoleList.do")
    @ResponseBody
    public List<SystemRoleBean> getUserRoleList(HttpServletRequest request, HttpServletResponse response) {
        List<SystemRoleBean>  userRoleList= userRoleService.getUserRoleBean();
        return userRoleList;
    }

}
