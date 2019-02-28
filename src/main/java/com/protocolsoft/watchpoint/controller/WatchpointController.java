/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:WatchpointController
 *创建人:wjm    创建时间:2017年9月1日
 */
package com.protocolsoft.watchpoint.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.bean.UserConfigureBean;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;
import com.protocolsoft.utils.JsonFileUtil.ModuleType;
import com.protocolsoft.watchpoint.bean.WatchpointBean;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 * 
 * @ClassName: WatchpointController
 * @Description: 观察点Controller层
 * @author wangjianmin
 *
 */
@Controller
@RequestMapping(value = "/watchpointController")
public class WatchpointController {

    /**
     *WatchpointServer对象
     */
    @Autowired
    private WatchpointService watchpointServer;
     
     /**
      * userService注入
      */
    @Autowired(required = false)
    private SystemUserService userService;
    
    /**
     * 用户配置 UserListColumnService注入
     */
    @Autowired
    private UserListColumnService userListColumnService;
    
    /**
     * 
     * @Title: getFindAll
     * @Description: 查询所有观察点
     * @param request 请求
     * @return List<Map<String,String>>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getFindAll.do")
    @ResponseBody
    public List<Map<String, Object>> getFindAll(HttpServletRequest request){
        List<Map<String, Object>> dataList = watchpointServer.getFindAll(request);
        return dataList;
    }
    
    /**
     * 
     * @Title: getNetworkAll
     * @Description: 查询所有网卡信息
     * @return Map<String,Object>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getNetworkAll.do")
    @ResponseBody
    public   Map<String, Object> getNetworkAll(){
        Map<String, Object> dataList = watchpointServer.getNetworkAll();
        return dataList;
    }
     
    /**
     * 
     * @Title: delWatchpoin
     * @Description: 删除 一条观察点
     * @param id   观察点业务ID
     * @return int
     * @author wangjianmin
     * @throws IOException 
     */
    @RequestMapping(value = "/getdelFind.do")
    @ResponseBody
    public int delWatchpoin(HttpServletRequest request, int id) throws IOException{
        int ids = watchpointServer.delWatchpoin(request, id);
        return ids;
    }
     
    /**
     * 
     * @Title: addWatchpoint
     * @Description: 添加观察点业务
     * @param request 请求
     * @param bean  接收参数实体
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/getAddWatchpoint.do")
    @ResponseBody
    public  int addWatchpoint(HttpServletRequest request, WatchpointBean bean){
        int watchpoint = watchpointServer.addWatchpoint(bean, request);
        return watchpoint;
    }
    
    
    /**
     * 
     * @Title: addWatchpoint
     * @Description: 添加观察点业务
     * @param request 请求
     * @param bean  接收参数实体
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/getAddWatchpointY.do")
    @ResponseBody
    public  Integer addWatchpointY(HttpServletRequest request, WatchpointBean bean){
        Integer watchpoint = watchpointServer.addWatchpointY(bean, request);
        return watchpoint;
    }
     
    
    /**
     * 
     * @Title: updWatchpoint
     * @Description: 修改观察点业务
     * @param bean  接收参数实体
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/getUpdWatchpoint.do")
    @ResponseBody
    public int updWatchpoint(HttpServletRequest request, WatchpointBean bean){
        int updWatchpoint = watchpointServer.updWatchpoint(request, bean);
        return updWatchpoint;
    }
     
    /**
     * 
     * @Title: getUserConfigureBeanByKey
     * @Description: 查询用户配置
     * @param request  请求
     * @param key    背景色键
     * @return String
     * @author wangjianmin
     */
    @RequestMapping(value = "/getUserConfigureBeanByKey.do")
    @ResponseBody
    public String getUserConfigureBeanByKey(HttpServletRequest request, String key){
        SystemUserBean systemUserBean = userService.getSessionUserBean(request);
        int userId = 0;
        String val = null;
        if (systemUserBean != null) {
            userId = systemUserBean.getId();
        }
        if (key == null || "".equals(key)) {
            key = "name";
        }
        List<UserConfigureBean> list = watchpointServer.getUserConfigureBean(userId);
        for (UserConfigureBean userConfigureBean : list) {
            if (userConfigureBean.getKey().equals(key)) {
                val = userConfigureBean.getValue();
            }
        }
        return val;
    }
     
    /**
     * 
     * @Title: updateUserConfigureByKey
     * @Description: 修改用户配置之背景
     * @param userConfigureBean 参数实体
     * @param request  请求
     * @return int
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateUserConfigureByKey.do")
    @ResponseBody
    public int updateUserConfigureByKey(UserConfigureBean userConfigureBean, HttpServletRequest request){
        SystemUserBean  systemUserBean = userService.getSessionUserBean(request);
        if (systemUserBean != null){
            userConfigureBean.setUserId(systemUserBean.getId());
        }
        int ids = watchpointServer.updateUserConfigureByKey(request, userConfigureBean);
        return ids;
    }
    
    /**
     * 
     * @Title: getServerSideListColumn
     * @Description: 获取所有列表字段
     * @return List<ListColumnBean>
     * @author wangjianmin
     */
    @RequestMapping(value="getWatchpointListColumn.do")
    @ResponseBody 
    public List<ListColumnBean> getServerSideListColumn(){
        List<ListColumnBean> beans = userListColumnService.getListColumn(ModuleType.WATCHPOINT.getModuleId());
        return beans;
    }
    
    /**
     * 
     * @Title: getServerSideUserListColumn
     * @Description: 获取用户配置列表字段
     * @param request 请求
     * @return List<ListColumnBean>
     * @author wangjianmin
     */
    @RequestMapping(value="getWatchpointUserListColumn.do")
    @ResponseBody 
    public List<ListColumnBean> getServerSideUserListColumn(HttpServletRequest request){
        SystemUserBean  systemUserBean =userService.getSessionUserBean(request);
        List<ListColumnBean> beans = userListColumnService.getUserListColumn(systemUserBean.getId(),
                ModuleType.WATCHPOINT.getModuleId());
        return beans;
    }
        
    /**
     * 
     * @Title: updateUserListColumn
     * @Description: 修改列字段信息
     * @param request 请求
     * @param typeId
     * @param columnIds
     * @return Map<String,String>
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateUserListColumn.do")
    @ResponseBody
    public Map<String, String> updateUserListColumn(HttpServletRequest request, int typeId, String columnIds){
        SystemUserBean user = userService.getSessionUserBean(request);
        Map<String, String> map = null;
        if ((null != user) && (null != columnIds && !"".equals(columnIds.trim()))){
            map = userListColumnService.updateUserListColumn(user.getId(), typeId, columnIds.trim());
        } else {
            map = new HashMap<String, String>();
            map.put("success", "0");
        }
        return map;
    }
    
    /**
     * 
     * @Title: getWatchpointGraphical
     * @Description: 观察点绘图
     * @param drawingOptionsBean 参数实体
     * @return Map<String,Object>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getWatchpointGraphical.do")
    @ResponseBody
    public Map<String, Object> getWatchpointGraphical(DrawingOptionsBean drawingOptionsBean){
        Map<String, Object> mapList = watchpointServer.getWatchpointGraphical(drawingOptionsBean);
        return mapList;
    }
    
    /**
     * 
     * @Title: getCrossGridData
     * @Description: 十字格数据
     * @param drawingOptionsBean 十字格数据参数
     * @return Map<String,Object>
     * @author wangjianmin
     */
    @RequestMapping(value = "/getCrossGridData.do")
    @ResponseBody
    public Map<String, Object>  getCrossGridData(DrawingOptionsBean drawingOptionsBean){
        Map<String, Object> list = watchpointServer.getCrossGridData(drawingOptionsBean);
        return list;
        
    }
    
    /**
     * 
     * @Title: updateByName
     * @Description: 修改观察点名称
     * @param name   名称
     * @param id 编号
     * @return boolean
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateByName.do")
    @ResponseBody
    public boolean updateByName(String name, int id){
        return  watchpointServer.updateByName(name, id);
    }
    
    /**
     * 
     * @Title: updateWatchpoint
     * @Description: 修改观察点
     * @param bean   参数
     * @return Object
     * @author wangjianmin
     */
    @RequestMapping(value = "/updateWatchpoint.do")
    @ResponseBody
    public Integer updateWatchpoint(WatchpointBean bean){
        return  watchpointServer.updateWatchpoint(null, bean);
    }
}
