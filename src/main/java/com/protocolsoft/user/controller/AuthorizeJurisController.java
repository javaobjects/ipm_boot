/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserAuthorizeController
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;

/**
 * 
 * @ClassName: AuthorizeJurisController
 * @Description: UserAuthorizeController
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/userAuthorize")
public class AuthorizeJurisController {
    /**
     * userAuthorizeService注入
     */
    @Autowired(required = false)
    private AuthorizeJurisService userAuthorizeService;
    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;

    /**
     * 
     * @Title: getSessionUserAuthorizeList
     * @Description: 获取授权信息
     * @param request 请求
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getSessionUserAuthorizeList.do")
    @ResponseBody
    public List<AuthorizeJurisBean> getSessionUserAuthorizeList(HttpServletRequest request) {
        List<AuthorizeJurisBean> userAuthorizeList =userAuthorizeService.getSessionUserAuthorizeBean(request);
        return userAuthorizeList;
    }
    
    /**
     * 
     * @Title: getJurisModuleList
     * @Description: 获取源授权信息
     * @param request  请求
     * @param userId 用户id
     * @param requestType 用于接收是add还是update或者get
     * @param changeUser 用于修改
     * @return List<JurisModuleBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getJurisModuleList.do")
    @ResponseBody
    public List<JurisModuleBean> getJurisModuleList(HttpServletRequest request, String userId, String requestType, String changeUser) {
        int userIdParam = 0;
        if (userId != null && !"".equals(userId.trim())){
            userIdParam = Integer.parseInt(userId);
        } else {
            if (requestType != null && "get".equals(requestType.trim())){
                SystemUserBean systemUserBean = userService.getSessionUserBean(request);
                if (systemUserBean != null){
                    userIdParam = systemUserBean.getId();
                }
            }
        }
        return  userAuthorizeService.getJurisModuleList(userIdParam, requestType.trim(), changeUser);
    }
    
    /**
     * 
     * @Title: updateUserAuthorizeSort
     * @Description: 修改授权信息
     * @param request  请求
     * @param status 接收多个驾驶舱ID，排序
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateUserAuthorizeSort.do")
    @ResponseBody
    public List<AuthorizeJurisBean> updateUserAuthorizeSort(HttpServletRequest request, String status) {
        int userId=0;
        int roleId =0;
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        if (systemUserBean != null){
            userId = systemUserBean.getId();
            roleId = systemUserBean.getRoleId();
        }
        return userAuthorizeService.updateUserAuthorizeSortList(userId, status, roleId);
    }
    
    /**
     * 
     * @Title: selectAuthorizeByUser
     * @Description: 获取权限
     * @param request 请求
     * @param jurisId 权限模块编号
     * @return Map<String,Boolean>
     * @author www
     */
    @RequestMapping(value = "/selectAuthorizeByUser.do")
    @ResponseBody
    public Map<String, Boolean> selectAuthorizeByUser(HttpServletRequest request, int jurisId) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        if (systemUserBean.getRoleId() == 1) {
            map.put("stauts", true);
        } else {
            String stauts = userAuthorizeService.selectModuleRole(systemUserBean.getId(), jurisId, 1);
            if (stauts == null || "N".equals(stauts) || "0".equals(stauts)) {
                map.put("stauts", false);
            } else {
                map.put("stauts", true);
            }
        }
        
        return map;
    }
}
