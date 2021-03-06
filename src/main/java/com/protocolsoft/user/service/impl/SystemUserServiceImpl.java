/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserServiceImpl
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protocolsoft.log.dao.LogsDao;
import com.protocolsoft.user.bean.ListColumnBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.user.dao.AuthorizeJurisDao;
import com.protocolsoft.user.dao.SystemUserDao;
import com.protocolsoft.user.dao.UserConfigureDao;
import com.protocolsoft.user.dao.UserListColumnDao;
import com.protocolsoft.user.service.AuthorizeJurisService;
import com.protocolsoft.user.service.SystemUserService;
import com.protocolsoft.user.service.UserListColumnService;
import com.protocolsoft.view.bean.MonitorViewBean;
import com.protocolsoft.view.dao.MonitorViewDao;
import com.protocolsoft.watchpoint.service.WatchpointService;

/**
 *
 * 2017年9月1日 下午2:19:53
 * @author long
 * @version
 * @see
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    /**
     * userDao注入
     */
    @Autowired(required=false)
    private SystemUserDao userDao;
    
    /**
     * userConfigureDao注入
     */
    @Autowired(required=false)
    private UserConfigureDao userConfigureDao;
    
    /**
     * userAuthorizeDao注入
     */
    @Autowired(required=false)
    private AuthorizeJurisDao userAuthorizeDao;
    
    /**
     * userListColumnDao注入
     */
    @Autowired(required=false)
    private UserListColumnDao userListColumnDao;
    
    /**
     * monitorViewDao注入
     */
    @Autowired(required=false)
    private MonitorViewDao monitorViewDao;
    /**
     * logsDao注入
     */
    @Autowired(required=false)
    private LogsDao logsDao;
    /**
     * watchpointServer
     */
    @Autowired(required=false)
    private WatchpointService watchpointServer;
    
    /**
     * authorizeJurisService注入
     */
    @Autowired(required=false)
    private AuthorizeJurisService authorizeJurisService;
    
    /**
     * authorizeJurisService注入
     */
    @Autowired(required=false)
    private UserListColumnService userListColumnService;
    
    @Override
    public SystemUserBean getUserBean(String userName, String password) {
        SystemUserBean userBean=userDao.getUserBean(userName, password);
        if (userBean!=null){
            userBean.setSuccess("1");
        } else {
            userBean = new SystemUserBean();
            boolean isExist=userDao.getUserBeanByName(userName, 0);
            if (isExist){
                userBean.setSuccess("3");
            } else {
                userBean.setSuccess("2");
            }
        }
        return userBean;
    }

    @Override
    public SystemUserBean getSessionUserBean(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SystemUserBean userBean=(SystemUserBean) session.getAttribute("userBean");
        return userBean;
    }

    @Override
    public SystemUserBean addUser(SystemUserBean userBean) {
        if (userBean!=null){
            boolean isExist=userDao.getUserBeanByName(userBean.getUserName(), userBean.getId());
            if (!isExist){
                userDao.addUser(userBean);
                watchpointServer.addConfigure(userBean.getId(), "name", "1");
                watchpointServer.addConfigure(userBean.getId(), "dataRefreshTime", "30");
                watchpointServer.addConfigure(userBean.getId(), "openIpLocationFlag", "0");
                watchpointServer.addConfigure(userBean.getId(), "cockpitConfig", "");
                if (userBean.getRoleId()==1){
                    List<MonitorViewBean> monitorViewBeanList=monitorViewDao.getMonitorViewAll();
                    if (monitorViewBeanList!=null && monitorViewBeanList.size()>0){
                        userBean.setModuleIds("");
                        for (int j=0; j<monitorViewBeanList.size(); j++){
                            userBean.setModuleIds(userBean.getModuleIds()+","
                                    +107+":1:"+monitorViewBeanList.get(j).getId());
                        }
                    }
                    if (userBean.getModuleIds()!=null&&!"".equals(userBean.getModuleIds().trim())){
                        userBean.setModuleIds(userBean.getModuleIds().substring(1));
                    }
                }
                userBean=authorizeJurisService.addAuthorizeJurisBySystemUserBean(userBean);
                List<ListColumnBean> listColumnList =userListColumnService.getListColumnList(1);
                userListColumnService.addUserListColumn(userBean.getId(), listColumnList);
                userBean.setSuccess("1");
            } else {
                userBean.setSuccess("2");
            }
        } else {
            userBean=new SystemUserBean();
            userBean.setSuccess("0");
        }
        return userBean;
    }

    @Override
    public List<SystemUserBean> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public Map<String, String> delUser(int userId) {
        Map<String, String> map =new HashMap<String, String>();
        boolean isSuccess=userDao.delUser(userId);
        if (isSuccess){
            userConfigureDao.delUserConfigureByUserId(userId);
            userAuthorizeDao.delUserAuthorizeByUserId(userId);
            userListColumnDao.delUserListByUserId(userId);
            monitorViewDao.delMonitorViewByUserId(userId);
            logsDao.delLogsByUserId(userId);
            map.put("success", "1");
        } else {
            map.put("success", "0");
        }
        return map;
    }

    @Override
    public SystemUserBean updateUserPassword(int userId,
            String oldPassword, String newPassword, String confirmPassword) {
        SystemUserBean systemUserBean=userDao.getUserBeanById(userId);
        if (systemUserBean!=null){
            if (!systemUserBean.getPassword().equals(oldPassword)){
                systemUserBean.setSuccess("3");
            } else {
                if (newPassword==null || !newPassword.equals(confirmPassword)){
                    systemUserBean.setSuccess("4");
                } else {
                    int isSuccess=userDao.updateUserPasswordById(userId, newPassword);
                    if (isSuccess>0){
                        systemUserBean.setSuccess("1");
                    } else {
                        systemUserBean.setSuccess("0");
                    }
                }
            }
        } else {
            systemUserBean =new SystemUserBean();
            systemUserBean.setSuccess("0");
        }
        return systemUserBean;
    }

    @Override
    public SystemUserBean updateUser(SystemUserBean systemUserBean) {
        boolean isExist=userDao.getUserBeanByName(systemUserBean.getUserName(), systemUserBean.getId());
        if (!isExist){
            int isSuccess=userDao.updateUserById(systemUserBean);
            if (isSuccess>0){
                userAuthorizeDao.delUserAuthorizeByUserId(systemUserBean.getId());
                List<MonitorViewBean> monitorViewBeanList=monitorViewDao.getMonitorViewList();
                if (systemUserBean.getModuleIds().equals("")) {
                    if (monitorViewBeanList!=null && monitorViewBeanList.size()>0){
                        systemUserBean.setModuleIds("");
                        for (int j=0; j<monitorViewBeanList.size(); j++){
                            systemUserBean.setModuleIds(systemUserBean.getModuleIds() + ","
                                 +107+":"+monitorViewBeanList.get(j).getId());
                        }
                    }
                }
                systemUserBean=authorizeJurisService.addAuthorizeJurisBySystemUserBean(systemUserBean);
                systemUserBean.setSuccess("1");
            } else {
                systemUserBean.setSuccess("0");
            }
        } else {
            systemUserBean.setSuccess("2");
        }
        return systemUserBean;
    }

    @Override
    public void cancellation(HttpServletRequest request, SystemUserBean systemUserBean) {
        //获取所有已存储的用户登录信息
        Enumeration<String> enumeration = request.getServletContext().getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String st = (String) enumeration.nextElement(); 
            if(st.indexOf(".") <= -1){ //过滤 ServletContext 自带内容
                if(st.equals(systemUserBean.getUserName())){  //得到当前用户的session信息   
                    request.getServletContext().removeAttribute(st); //注销当前用户
                }
            }
        }
    }

    @Override
    public SystemUserBean getUserBeanById(int id) {
        
        return userDao.getUserBeanById(id);
    }

    @Override
    public void updateUserLoginState(int id, int loginState) {
        
        userDao.getUserLoginState(id, loginState, 0);
    }

    @Override
    public void duplicateLogin(List<Map<String, Object>> map, String userName) {
        for (int i = 0; i < map.size(); i++) {
            try {
				HttpSession session = (HttpSession) map.get(i).get("userSession");
				if(session.getAttribute("userBean") != null){
				    SystemUserBean bean = (SystemUserBean) session.getAttribute("userBean");
				    if(bean.getUserName().equals(userName)){
				        session.invalidate();
				    }
				}else {
				    map.remove(i);
				}
			} catch (Exception e) {
				map.remove(i);
				continue;
			}
        }
    }
    
    @Override
    public void duplicateLogins(List<Map<String, Object>> map, String userName) {
        for (int i = 0; i < map.size(); i++) {
            HttpSession session = (HttpSession) map.get(i).get("userSession");
            SystemUserBean bean = (SystemUserBean) session.getAttribute("userBean");
            if(bean.getUserName().equals(userName)){
                session.invalidate();
            }
        }
    }

    @Override
    public int getUserNameLoginOvertime(String userName) {
      
        return userDao.getUserNameLoginOvertime(userName);
    }
}
