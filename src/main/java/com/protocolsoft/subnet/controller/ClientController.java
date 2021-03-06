/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ClientController
 *创建人:long    创建时间:2017年9月8日
 */
package com.protocolsoft.subnet.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.subnet.service.ClientService;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;

/**
 * ClientController 2017年9月8日 上午10:27:23
 * 
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    /**
     * clientService注入
     */
    @Autowired(required = false)
    private ClientService clientService;

    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService systemUserService;

    /**
     * 用户配置列表字段
     */
    @Autowired
    private UserListColumnService userListColumnService;

    /**
     * 
     * @Title: addClient
     * @Description: 添加客户端
     * @param request 请求
     * @param appBusinessBean 接收添加参数
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/addClient.do")
    @ResponseBody
    public Map<String, Integer> addClient(HttpServletRequest request, AppBusinessBean appBusinessBean) {
        Map<String, Integer> map = clientService.addClient(request, appBusinessBean);

        return map;
    }

    /**
     * 
     * @Title: delClientByAppId
     * @Description: 删除客户端
     * @param request  请求
     * @param id  业务id
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/delClient.do")
    @ResponseBody
    public Map<String, Integer> delClientByAppId(HttpServletRequest request, int id) {

        return clientService.delClientByAppId(request, id);
    }

    /**
     * 
     * @Title: updateClient
     * @Description: 修改客户端
     * @param request 请求
     * @param appBusinessBean 接收添加参数
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateClient.do")
    @ResponseBody
    public Map<String, Integer> updateClient(HttpServletRequest request, AppBusinessBean appBusinessBean) {

        return clientService.updateClient(request, appBusinessBean);
    }

    /**
     * 获取子网 2017年9月11日 上午10:46:47
     * 
     * @param
     * @return List<ClientBean>
     * @exception @see
     */
    @RequestMapping(value = "/getClient.do")
    @ResponseBody
    public List<AppBusinessBean> getClient(HttpServletRequest request) {

        return clientService.getClient(request);
    }

    /**
     * 获取用户配置对应列表字段 2017年9月18日 上午11:12:55
     * 
     * @param
     * @return List<ListColumnBean>
     * @exception @see
     */
    @RequestMapping(value = "getClientUserListColumn.do")
    @ResponseBody
    public List<ListColumnBean> getClientUserListColumn(HttpServletRequest request) {
        SystemUserBean user = systemUserService.getSessionUserBean(request);
        List<ListColumnBean> beans = userListColumnService.getUserListColumn(user.getId(),
                ModuleType.CLIENT.getModuleId());
        
        return beans;
    }

    /**
     * 客户端绘图 2017年9月21日 下午4:47:50
     * 
     * @param
     * @return Map<String,Object>
     * @exception @see
     */
    @RequestMapping(value = "/getClientGraphical.do")
    @ResponseBody
    public Map<String, Object> getClientGraphical(HttpServletRequest request, DrawingOptionsBean drawingOptionsBean) {
        Map<String, Object> map = clientService.getClientGraphical(drawingOptionsBean);
        
        return map;
    }

    /**
     * 客户端单个点 2017年10月17日 下午2:01:54
     * 
     * @param
     * @return Map<String,Object>
     * @exception @see
     */
    @RequestMapping(value = "/getClientSingleValueData.do")
    @ResponseBody
    public Map<String, Object> getClientSingleValueData(DrawingOptionsBean drawingOptionsBean) {
        Map<String, Object> map = clientService.getClientSingleValueData(drawingOptionsBean);
        
        return map;
    }
}
