/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ServerManagementService
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.servers.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.common.bean.AppBusinessBean;
import com.protocolsoft.common.bean.DrawingOptionsBean;
import com.protocolsoft.servers.bean.AppIpPortBean;

/**
 * 服务端管理业务逻辑接口
 * 2017年9月1日 上午10:52:09
 * @author yan
 * @version
 * @see
 */
public interface ServerManagementService {

    /**
     * 新增
     * 2017年9月1日 下午4:32:54
     * @param AppBusinessBean
     * @return Map<String, Integer>
     * @exception 
     * @see
     */
    Map<String, Integer> addServerSide(AppBusinessBean bean, HttpServletRequest request);
    
    /**
     * 获取服务端
     * 2017年9月1日 下午4:32:39
     * @param id
     * @return AppBusinessBean
     * @exception 
     * @see
     */
    AppBusinessBean getServerSide(Integer id);
    
    /**
     * 修改
     * 2017年9月1日 下午4:33:31
     * @param AppBusinessBean
     * @return String
     * @exception 
     * @see
     */
    /**
     * 
     * @Title: updateServerSide
     * @Description: 修改服务端
     * @param request 请求
     * @param bean   接收修改服务端参数
     * @return Map<String, Integer>
     * @author wangjianmin
     */
    Map<String, Integer> updateServerSide(HttpServletRequest request, AppBusinessBean bean);
    
    /**
     * 获取所有服务端
     * 2017年9月6日 下午3:55:43
     * @param moduleId
     * @return List<AppBusinessBean>
     * @exception 
     * @see
     */
    List<AppBusinessBean> getAllServerSide(Integer moduleId);
    
    /**
     * 获取所有服务端(简单获取)
     * 2017年9月6日 下午3:55:43
     * @param appId
     * @return List<AppIpPortBean>
     * @exception 
     * @see
     */
    List<AppIpPortBean> getAppIpPortByAppId(Integer appId);
    
    /**
     * 获取服务端图形
     * 2017年9月26日 下午4:16:52
     * @param drawingOptionsBean
     * @param serverId
     * @return Map<String, Object>
     * @exception 
     * @see
     */
    Map<String, Object> getServerSideGraphical(DrawingOptionsBean drawingOptionsBean, Integer serverId);
    
    /**
     * 获取服务端单个点
     * 2017年10月20日 下午5:18:56
     * @param drawingOptionsBean
     * @param serverId
     * @param isCalcul
     * @return Map<String, Object>
     * @exception 
     * @see
     */
    Map<String, Object> getServerSideSingleValueData(DrawingOptionsBean drawingOptionsBean, Integer serverId,
            String isCalcul, boolean flag);

    /**
     * @Title: deleteServerSide
     * @Description: 删除
     * @param moduleId
     * @param id
     * @return Map<String, Integer>
     * @author www
     */
    Map<String, Integer> deleteServerSide(int moduleId, Integer id, HttpServletRequest request);

    /**
     * @Title: getAllServerSide
     * @Description: 获取
     * @param moduleId
     * @param ids
     * @return List<AppBusinessBean>
     * @author www
     */
    List<AppBusinessBean> getAllServerSide(Integer moduleId, String ids);

    /**
     * @Title: getAllServerSide
     * @Description: 获取所有的服务端信息
     * @param request
     * @param moduleId
     * @return List<AppBusinessBean>
     * @author www
     */
    List<AppBusinessBean> getAllServerSide(HttpServletRequest request,
            Integer moduleId);
}
