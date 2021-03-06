/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserAuthorizeService
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.user.bean.AuthorizeJurisBean;
import com.protocolsoft.user.bean.JurisModuleBean;
import com.protocolsoft.user.bean.SystemUserBean;
import com.protocolsoft.view.bean.MonitorViewBean;

/**
 * UserAuthorizeService
 * 2017年9月4日 上午10:00:31
 * @author long
 * @version
 * @see
 */
public interface AuthorizeJurisService {

    /**
     * 
     * @Title: getUserAuthorizeBean
     * @Description: 授权信息
     * @param userId 用户ID
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    List<AuthorizeJurisBean> getUserAuthorizeBean(int userId);

    /**
     * 
     * @Title: getSessionUserAuthorizeBean
     * @Description: 获取session授权信息
     * @param request 请求
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    List<AuthorizeJurisBean> getSessionUserAuthorizeBean(HttpServletRequest request);

    /**
     * 
     * @Title: getJurisModuleList
     * @Description: 源授权信息
     * @param userId 用户id
     * @param requestType 用于接收是add还是update或者get
     * @param changeUser 用于修改
     * @return List<JurisModuleBean>
     * @author wangjianmin
     */
    List<JurisModuleBean> getJurisModuleList(int userId, String requestType, String changeUser);

    /**
     * 
     * @Title: delUserAuthorizeByUserId
     * @Description: 根据userId删除授权信息
     * @param userId 用户id
     * @return Map<String,String>
     * @author wangjianmin
     */
    Map<String, String> delUserAuthorizeByUserId(int userId);

    /**
     * 
     * @Title: addAuthorizeJurisBySystemUserBean
     * @Description: 根据SystemUserBean添加授权信息
     * @param systemUserBean 接收添加SystemUserBean的参数
     * @return SystemUserBean
     * @author wangjianmin
     */
    SystemUserBean addAuthorizeJurisBySystemUserBean(SystemUserBean systemUserBean);
    
    /**
     * 根据SystemUserBean添加授权信息
     * 2017年11月3日 下午2:23:19
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> addAuthorizeJurisByList(List<SystemUserBean> list);

    /**
     * 
     * @Title: addAuthorizeJurisByMonitorViewBean
     * @Description: 根据MonitorViewBean添加授权信息
     * @param monitorViewBean 接收MonitorViewBean参数
     * @return Map<String,String>
     * @author wangjianmin
     */
    Map<String, String> addAuthorizeJurisByMonitorViewBean(MonitorViewBean monitorViewBean);

    /**
     * 
     * @Title: getSystemUserBean
     * @Description: 根据MonitorViewBean获取用户信息
     * @param monitorViewBean 接收monitorViewBean参数
     * @return List<SystemUserBean>
     * @author wangjianmin
     */
    List<SystemUserBean> getSystemUserBean(MonitorViewBean monitorViewBean);

    /**
     * 
     * @Title: getUserAuthorizeByModuleId
     * @Description: 根据userId和moduleId获取信息
     * @param userId 用户id
     * @param moduleId 模块id
     * @return AuthorizeJurisBean
     * @author wangjianmin
     */
    AuthorizeJurisBean getUserAuthorizeByModuleId(int userId, int moduleId);

    /**
     * 
     * @Title: getUserAuthorizeByModuleList
     * @Description: 根据userId和moduleId获取信息
     * @param userId 用户id
     * @param moduleId 模块id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    List<AuthorizeJurisBean> getUserAuthorizeByModuleList(int userId, int moduleId);
    
    /**
     * 
     * @Title: getUserAdminAuthorizeModuleList
     * @Description: 根据模块id 查询所有驾驶舱
     * @param moduleId 模块id
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    List<AuthorizeJurisBean> getUserAdminAuthorizeModuleList(int moduleId);

    /**
     * 
     * @Title: updateUserAuthorize
     * @Description: 修改授权信息
     * @param authorizeJurisBean 接收 authorizeJurisBean 参数
     * @return AuthorizeJurisBean
     * @author wangjianmin
     */
    AuthorizeJurisBean updateUserAuthorize(AuthorizeJurisBean authorizeJurisBean);

    /**
     * 
     * @Title: updateUserAuthorizeSort
     * @Description: T根据userId修改信息
     * @param userId  用户id
     * @param status 业务id(多个)
     * @return AuthorizeJurisBean
     * @author wangjianmin
     */
    AuthorizeJurisBean updateUserAuthorizeSort(int userId, String status);
    

    /**
     * 
     * @Title: updateUserAuthorizeSortList
     * @Description: 根据userId和moduleId修改信息
     * @param userId  用户ID
     * @param status  驾驶舱ID
     * @return List<AuthorizeJurisBean>
     * @author wangjianmin
     */
    List<AuthorizeJurisBean> updateUserAuthorizeSortList(int userId, String status, int roleId);
    
    /**
     * 
     * @Title: selectModuleRole
     * @Description: 查询权限
     * @param userId 用户编号
     * @param moduleId 模块编号
     * @return String 权限编号
     * @author www
     */
    String selectModuleRole(int userId, int moduleId, int centerId);

    /**
     * 
     * @Title: addUserAuthorize
     * @Description: 添加业务时，添加权限
     * @param userId 用户id
     * @param id 业务id
     * @param moduleId void 模块id
     * @author wangjianmin
     */
    void addUserAuthorize(int userId, int id, int moduleId);

    /**
     * 
     * @Title: deleteUserAuthorize
     * @Description: 根据业务id,删除权限
     * @param userId  用户id
     * @param id   业务id
     * @param moduleId void 模块id
     * @author wangjianmin
     */
    void deleteUserAuthorize(int userId, int id, int moduleId);

    /**
     * 
     * @Title: deleteCenter
     * @Description: 根据用户和centerID 删除权限
     * @param centerId void 远端id
     * @author wangjianmin
     */
    void deleteCenter(int centerId);
}
