/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserController
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.protocolsoft.log.bean.LogsBean;
import com.protocolsoft.log.service.LogsService;
import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.bean.UserConfigureBean;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserConfigureService;
import com.protocolsoft.utils.DateUtils;
import com.protocolsoft.utils.PropertiesUtil;
import com.protocolsoft.word.service.ReportModalService;
import com.protocolsoft.word.service.ReportPlanService;

/**
 * UserController
 * 2017年9月1日 下午1:54:25
 * @author long
 * @version
 * @see
 */
@Controller
@RequestMapping(value = "/user")
public class SystemUserController {
    
    /**
     * @Fields reportModalService : 模版Service
     */
    @Autowired
    ReportModalService reportModalService;
    
    /**
     * @Fields reportPlanService : 计划Service
     */
    @Autowired
    ReportPlanService reportPlanService;

    
    /**
     * userService注入
     */
    @Autowired(required = false)
    private SystemUserService userService;
    /**
     * userConfigureService注入
     */
    @Autowired(required = false)
    private UserConfigureService userConfigureService;
    /**
     * userAuthorizeService注入
     */
    @Autowired(required = false)
    private AuthorizeJurisService userAuthorizeService;
    
    /**
     * 记录登录次数
     */
    public static int  count;
    
    /**
     * 最大登录次数
     */
    private static String maxCount = "2";
    
    /**
     *存储请求信息 
     */
    private Map<String, Object> map = new HashMap<String, Object>(); 
    
    /**
     * 保存：最终结果
     */
    public static List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
    
    /**
     * 日志管理业务对象
     */
    @Autowired
    private LogsService logsService;
    
    static {
        PropertiesUtil propertiesUtil = new PropertiesUtil("sysset.properties");
        try {
            maxCount = propertiesUtil.readProperty("system_user_num");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 登陆
     * 2017年9月1日 下午2:35:16
     * @param
     * @return UserBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/login.do")
    @ResponseBody
    public SystemUserBean login(HttpServletRequest request, String userName, String password, Integer start, HttpServletResponse response) {
        SystemUserBean userBean = userService.getUserBean(userName, password);
        if(!userBean.getSuccess().equals("3") && !userBean.getSuccess().equals("2")){
            if(userBean.getLoginOvertime() == 1){
                userService.cancellation(request, userBean);
            }
            //获取所有存储的登录用户信息
            Enumeration<String> enumeration = request.getServletContext().getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String st = (String) enumeration.nextElement(); //得到当前用户的session信息   
                if(st.indexOf(".") <= -1){  //过滤掉 ServletContext() 自带内容
                    //获取登录的ip 地址                
                    String addr  = request.getServletContext().getAttribute(st).toString();
                    if(st.equals(userName) && !addr.equals(request.getRemoteAddr())){
                       //同时验证 登录名称与登录IP，如果登录名称相同，登录IP不同，切状态唯一的情况互踢
                        if(start ==1){
                            userService.duplicateLogin(listMap, userName);
                        }else{
                            userBean.setSuccess("4");
                            return userBean; 
                        }
                    } else if (st.equals(userName) && addr.equals(request.getRemoteAddr())){
                        //相同的用户名，相同的IP地址，不记录次数
                        userService.duplicateLogins(listMap, userName);
                        userService.cancellation(request, userBean);
                    } 
                }
            }  
            count++; //记录登录次数
            if(count > Integer.parseInt(maxCount)){
                count--;
                userBean.setSuccess("5");
                return userBean; 
            }
            //记录登录用户信息
            request.getServletContext().setAttribute(userBean.getUserName(), request.getRemoteAddr()); 
        }

        if (userBean!=null&& "1".equals(userBean.getSuccess())){
            userService.updateUserLoginState(userBean.getId(), 1);
            userBean.setLoginState(1);
            List<UserConfigureBean> userConfigureList=userConfigureService.getUserConfigureBean(userBean.getId());
            HttpSession session = request.getSession();
            SystemUserBean systemUserBean = userService.getSessionUserBean(request);
            if(systemUserBean != null){
                if(!systemUserBean.getUserName().equals(userName)){
                    userService.cancellation(request, systemUserBean); 
                    userService.duplicateLogins(listMap, systemUserBean.getUserName());
                }
            }
            session = request.getSession();
            //设置 session 失效时间
            session.setMaxInactiveInterval(120);  
            // 把当前登录的用户信息添加到session中
            session.setAttribute("userBean", userBean); 
            session.setAttribute("userConfigureList", userConfigureList);
            map = new HashMap<String, Object>();
            map.put("userSession", session);
            listMap.add(map);
            if (userBean.getRoleId()!=1){
                List<AuthorizeJurisBean> userAuthorizeList=userAuthorizeService.getUserAuthorizeBean(userBean.getId());
                session.setAttribute("userAuthorizeList", userAuthorizeList);
            }
            LogsBean logsBean = new LogsBean();
            logsBean.setUserId(userBean.getId());
            logsBean.setModuleId(4);
            logsBean.setMsg(userBean.getUserName()+"用户登录");
            logsBean.setTime(DateUtils.getNowTimeSecond());
            logsService.addLogs(logsBean);
        }
        return userBean;
    }
    
    /**
     * 登出
     * 2017年9月4日 下午1:42:53
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/logout.do")
    @ResponseBody
    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map=new HashMap<String, String>();
        try {
            HttpSession session = request.getSession();
            SystemUserBean systemUserBean=userService.getSessionUserBean(request);
            if(systemUserBean == null){
                map.put("success", "2");
            }else{
                //注销 ServletContext 里的当前登录的用户
                userService.cancellation(request, systemUserBean); 
                systemUserBean.setLoginState(0);
                for (int i = 0; i < listMap.size(); i++) {
                    HttpSession sessionInfo = (HttpSession) listMap.get(i).get("userSession");
                    SystemUserBean bean = (SystemUserBean) sessionInfo.getAttribute("userBean");
                    if(bean.getUserName().equals(systemUserBean.getUserName())){
                        listMap.remove(i);
                    }
                }
                count--;
                //注销session 
                session.invalidate();
                map.put("success", "1");
                if (systemUserBean!=null){
                    LogsBean logsBean = new LogsBean();
                    logsBean.setUserId(systemUserBean.getId());
                    logsBean.setModuleId(4);
                    logsBean.setMsg(systemUserBean.getUserName()+"用户注销");
                    logsBean.setTime(DateUtils.getNowTimeSecond());
                    logsService.addLogs(logsBean);
                } 
                
            }
        } catch (Exception e){
            e.printStackTrace();
            map.put("success", "0");
        }
        return map;
    }
    /**
     * 添加用户
     * 2017年9月4日 下午5:13:15
     * @param
     * @return UserBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/addUser.do")
    @ResponseBody
    public SystemUserBean addUser(HttpServletRequest request, HttpServletResponse response, SystemUserBean userBean){
        userService.addUser(userBean);
        SystemUserBean systemUserBean= userService.getSessionUserBean(request);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(4);
        logsBean.setMsg("添加"+userBean.getUserName()+"用户");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        return userBean;
    }
    
    /**
     * 获取用户信息
     * 2017年9月5日 上午9:55:03
     * @param
     * @return List<UserBean>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<SystemUserBean> getUserList(HttpServletRequest request, HttpServletResponse response){
        return userService.getUserList();
    }
    
    /**
     * 删除用户信息
     * 2017年9月5日 下午2:48:46
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/delUser.do")
    @ResponseBody
    public Map<String, String> delUser(HttpServletRequest request, HttpServletResponse response, Integer userId){
        SystemUserBean systemUserBean= userService.getSessionUserBean(request);
        Map<String, String> map=new HashMap<String, String>();
        if (systemUserBean!=null){
            if (systemUserBean.getId()!=userId){
                reportModalService.deleteModalByUserId(userId, systemUserBean);
                reportPlanService.deletePlanByUserId(userId);
                map=userService.delUser(userId);
            } else {
                map.put("success", "3");
            }
        } else {
            map.put("success", "0");
        }
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(systemUserBean.getId());
        logsBean.setModuleId(4);
        logsBean.setMsg("删除"+systemUserBean.getUserName()+"用户");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        return map;
    }
    
    /**
     * 修改密码
     * 2017年9月5日 下午5:59:11
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateUserPassword.do")
    @ResponseBody
    public SystemUserBean updateUserPassword(HttpServletRequest request, int userId,
            String oldPsw, String newPsw, String confirmPsw) {
        SystemUserBean systemUserBean=userService.updateUserPassword(userId, oldPsw, newPsw, confirmPsw);
        if ("1".equals(systemUserBean.getSuccess())){
            SystemUserBean systemUserBeanSession=userService.getSessionUserBean(request);
            if (systemUserBeanSession!=null &&systemUserBeanSession.getId()==userId){
                HttpSession session = request.getSession(true);
                session.setAttribute("userBean", systemUserBean);
            }
        }
        SystemUserBean bean= userService.getSessionUserBean(request);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(bean.getId());
        logsBean.setModuleId(4);
        logsBean.setMsg("修改"+systemUserBean.getUserName()+"用户密码");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        return systemUserBean;
    }
    
    /**
     * 修改用户
     * 2017年9月6日 下午2:50:26
     * @param
     * @return SystemUserBean
     * @exception 
     * @see
     */
    @RequestMapping(value = "/updateUser.do")
    @ResponseBody
    public SystemUserBean updateUser(HttpServletRequest request, SystemUserBean systemUserBean){
        SystemUserBean systemUser=userService.updateUser(systemUserBean);
        if ("1".equals(systemUserBean.getSuccess())){
            SystemUserBean systemUserBeanSession=userService.getSessionUserBean(request);
            if (systemUserBeanSession!=null &&systemUserBeanSession.getId()==systemUser.getId()){
                HttpSession session = request.getSession(true);
                session.setAttribute("userBean", systemUser);
            }
        }
        SystemUserBean bean= userService.getSessionUserBean(request);
        LogsBean logsBean = new LogsBean();
        logsBean.setUserId(bean.getId());
        logsBean.setModuleId(4);
        logsBean.setMsg("修改"+systemUser.getUserName()+"用户");
        logsBean.setTime(DateUtils.getNowTimeSecond());
        logsService.addLogs(logsBean);
        return systemUserBean;
    }

    /**
     * 
     * @Title: getUserRole
     * @Description: 获取用户权限
     * @param request
     * @return int
     * @author www
     */
    @RequestMapping(value = "/getUserRole.do")
    @ResponseBody
    public int getUserRole(HttpServletRequest request) {
        SystemUserBean bean = userService.getSessionUserBean(request);
        
        return bean.getRoleId();
    }
    
    /**
     * 
     * @Title: getUserRole
     * @Description: 获取用户权限
     * @param request
     * @return int
     * @author www
     */
    @RequestMapping(value = "/getSessionUserInfo.do")
    @ResponseBody
    public SystemUserBean getSessionUserInfo(HttpServletRequest request) {
        SystemUserBean bean = userService.getSessionUserBean(request);
        
        return bean;
    }

    /**
     * <br />获取 <font color="red"><b>count<b/></font>
     * @return count count
     */
    public static int getCount() {
        return count;
    }

    /**  
     * <br />设置 <font color='#333399'><b>count</b></font>
     * @param count count  
     */
    public static void setCount(int count) {
        SystemUserController.count = count;
    }

    /**
     * <br />获取 <font color="red"><b>listMap<b/></font>
     * @return listMap listMap
     */
    public static List<Map<String, Object>> getListMap() {
        return listMap;
    }

    /**  
     * <br />设置 <font color='#333399'><b>listMap</b></font>
     * @param listMap listMap  
     */
    public static void setListMap(List<Map<String, Object>> listMap) {
        SystemUserController.listMap = listMap;
    }
}
