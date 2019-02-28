/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairService
 *创建人:yan    创建时间:2017年10月16日
 */
package com.protocolsoft.jtopo.service;

import java.util.List;

import com.protocolsoft.jtopo.bean.CommpairQoBean;
import com.protocolsoft.jtopo.bean.CommpairVoBean;

/**
 * 通信对接口
 * 2017年9月19日 上午11:33:52
 * @author yan
 * @version
 * @see
 */
public interface CommpairService {
    
    /**
     * 通过时间、粒度获取去重、聚合、平均的通信对
     * 2017年10月16日 上午11:47:16
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    List<CommpairVoBean> getAllCommpair(CommpairQoBean commpairQoBean);
    
    /**
     * 通过时间、粒度、IP或者端口获取去重、聚合、平均的通信对
     * 2017-10-18 下午2:32:18
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    List<CommpairVoBean> getCommpairByIpPort(CommpairQoBean commpairQoBean);
    
    
    /*移植npm————jtopo 方法  begin **************/
    
    /**
     * 保存拓扑图
     * @param name
     * @return String
     */
    String getJtopo(String name);
    
    /**
     * 保存拓扑图
     * @param name
     * @param nodeJson
     */
    void saveJtopo(String name, String nodeJson);
    
    /*移植npm————jtopo 方法  end **************/
}
