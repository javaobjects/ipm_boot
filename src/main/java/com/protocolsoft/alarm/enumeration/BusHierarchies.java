/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:BusHierarchies
 *创建人:chensq    创建时间:2017年11月15日
 */
package com.protocolsoft.alarm.enumeration;

/**
 * @ClassName: BusHierarchies
 * @Description: 业务层级关系枚举
 * @author chensq
 *
 */
public enum BusHierarchies {
    /**
     * @Fields watchpoint : 观察点
     */
    watchpoint(10),
    /**
     * @Fields watchpoint_client : 观察点 下 客户端
     */
    watchpoint_client(11),    
    /**
     * @Fields watchpoint_client_server : 观察点 下  客户端 下 服务端
     */
    watchpoint_client_server(3),    
    /**
     * @Fields watchpoint_server : 观察点 下 服务端
     */
    watchpoint_server(12),
     
    /**
     * @Fields watchpoint_server_client : 观察点 下  服务端 下 客户端
     */
    watchpoint_server_client(5);
     
    //目前使用的是 10 11 12 
     
    /**
     * @Fields index : index
     */
    int index;

    /**
     * @param index index 
     */
    private BusHierarchies(int index) {
        this.index = index;
    }
    
    /**
     * @Title: toInt
     * @Description: 
     * @return int
     * @author chensq
     */
    public int toInt() {
        return index;
    }

}
