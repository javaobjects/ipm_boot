/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MonitorViewService
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.view.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.protocolsoft.view.bean.MonitorViewBean;

/**
 * MonitorViewService
 * 2017年9月15日 上午10:47:47
 * @author long
 * @version
 * @see
 */
public interface MonitorViewService {
    /**
     * 驾驶舱信息
     * 2017年9月15日 上午10:48:48
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    List<MonitorViewBean> getMonitorViewList();
    
    /**
     * 添加驾驶舱
     * 2017年9月15日 下午1:57:21
     * @param
     * @return MonitorViewBean
     * @exception 
     * @see
     */
    MonitorViewBean addMonitorView(HttpServletRequest request, MonitorViewBean monitorViewBean);
    
    /**
     * 修改驾驶舱
     * 2017年9月15日 下午3:18:06
     * @param
     * @return MonitorViewBean
     * @exception 
     * @see
     */
    MonitorViewBean updateMonitorViewById(HttpServletRequest request, MonitorViewBean monitorViewBean);
    
    /**
     * 删除驾驶舱
     * 2017年9月15日 下午4:09:29
     * @param
     * @return Map<String,String>
     * @exception 
     * @see
     */
    Map<String, String> delMonitorView(HttpServletRequest request, int id);
    
    /**
     * 根据userId获取驾驶舱
     * 2017年11月6日 下午1:49:22
     * @param
     * @return List<MonitorViewBean>
     * @exception 
     * @see
     */
    List<MonitorViewBean> getMonitorViewList(int userId, int roleId);

    /**
     * @Title: getViewById
     * @Description: 获取信息
     * @param id 编号
     * @return MonitorViewBean
     * @author www
     */
    MonitorViewBean getViewById(int id);

    /**
     * @Title: updateMonitorViewStatus
     * @Description: 更改状态
     * @param id
     * @param status
     * @return boolean
     * @author www
     */
    boolean updateMonitorViewStatus(int id, int status);

}
