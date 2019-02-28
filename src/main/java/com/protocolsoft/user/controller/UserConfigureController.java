/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserConfigureController
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

import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.bean.UserConfigureBean;
import com.protocolsoft.user.bean.UserListColumnBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserConfigureService;
import com.protocolsoft.user.service.UserListColumnService;

/**
 * UserConfigureController
 * 2017年9月4日 下午3:22:15
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/userConfigure")
public class UserConfigureController {
    /**
     * userConfigureService注入
     */
    @Autowired(required = false)
    private UserConfigureService userConfigureService;
    
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
     * 获取用户配置信息
     * 2017年9月4日 下午3:25:46
     * @param request
     * @return List<UserConfigureBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getSessionUserConfigureList.do")
    @ResponseBody
    public List<UserConfigureBean> getSessionUserConfigureList(HttpServletRequest request) {
        List<UserConfigureBean> userConfigureList=userConfigureService.getSessionUserConfigureBean(request);
        return userConfigureList;
    }

    /**
     * 保存户配置列表
     * 2017年9月15日 下午6:07:40
     * @param request
     * @param columnId
     * @return String
     * @exception 
     * @see
     */
    @RequestMapping(value = "/checkedUserListColumn.do")
    @ResponseBody
    public String checkedUserListColumn(HttpServletRequest request, Integer columnId){
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        String result = "fail";
        if ((null != user) && null != columnId){
            UserListColumnBean bean = new UserListColumnBean();
            bean.setUserId(user.getId());
            bean.setColumnId(columnId);
            result = userListColumnService.checkedUserListColumn(bean);
        }
        return result;
    }
    
    /**
     * 删除用户配置列表
     * 2017年9月15日 下午6:07:40
     * @param request
     * @param columnId
     * @return String
     * @exception 
     * @see
     */
    @RequestMapping(value = "/unCheckedUserListColumn.do")
    @ResponseBody
    public String unCheckedUserListColumn(HttpServletRequest request, Integer columnId){
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        String result = "fail";
        if ((null != user) && null != columnId){
            UserListColumnBean bean = new UserListColumnBean();
            bean.setUserId(user.getId());
            bean.setColumnId(columnId);
            result = userListColumnService.unCheckedUserListColumn(bean);
        }
        return result;
    }
    
    /**
     * 更新列字段信息
     * 2017年9月19日 上午10:12:51
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateUserListColumn.do")
    @ResponseBody
    public Map<String, String> updateUserListColumn(HttpServletRequest request, int typeId, String columnIds){
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        Map<String, String> map=null;
        if ((null != user) && (null != columnIds && !"".equals(columnIds.trim()))){
            map = userListColumnService.updateUserListColumn(user.getId(), typeId, columnIds.trim());
        } else {
            map=new HashMap<String, String>();
            map.put("success", "0");
        }
        return map;
    }
}
