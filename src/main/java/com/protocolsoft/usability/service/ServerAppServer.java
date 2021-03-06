/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ServerAppServer.java
 *创建人: wangjianmin    创建时间: 2018年9月13日
 */
package com.protocolsoft.usability.service;


/**
 * @ClassName: ServerAppServer
 * @Description: 服务端添加到应用可用性类
 * @author wangjianmin
 *
 */
public interface ServerAppServer {
    
    /**
     * 
     * @Title: addServerUsab
     * @Description: 把服务端业务信息添加到应用可用性中
     * @param id   业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean  addServerUsab(int id);
    
    /**
     * 
     * @Title: updateServerUsab
     * @Description: 把服务端业务信息添加到应用可用性中，根据id进行修改
     * @param id   业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean  updateServerUsab(int id);
    
    /**
     * 
     * @Title: deleteServerUsab
     * @Description: 根据业务id,删除添加到应用可用性中服务端业务
     * @param id  业务id
     * @return boolean
     * @author wangjianmin
     */
    boolean deleteServerUsab(int id);
    
    
    
    
    

}
