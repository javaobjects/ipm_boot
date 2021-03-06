/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: HeatmapBlock.java
 *创建人: www    创建时间: 2018年2月26日
 */
package com.protocolsoft.common.bean;

/**
 * @ClassName: HeatmapBlock
 * @Description: 热度块
 * @author www
 *
 */
public class HeatmapBlock {

    /**
     * X坐标
     */
    private int x;
    
    /**
     * Y坐标
     */
    private int y;
    
    /**
     * 数值
     */
    private double value;
    
    /**
     * 客户端编号
     */
    private int clientId;
    
    /**
     * 服务端编号
     */
    private int serverId;

    /**
     * <br />获取 <font color="red"><b>X坐标<b/></font>
     * @return x X坐标
     */
    public int getX() {
        return x;
    }

    /**  
     * <br />设置 <font color='#333399'><b>X坐标</b></font>
     * @param x X坐标  
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * <br />获取 <font color="red"><b>Y坐标<b/></font>
     * @return y Y坐标
     */
    public int getY() {
        return y;
    }

    /**  
     * <br />设置 <font color='#333399'><b>Y坐标</b></font>
     * @param y Y坐标  
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * <br />获取 <font color="red"><b>数值<b/></font>
     * @return value 数值
     */
    public double getValue() {
        return value;
    }

    /**  
     * <br />设置 <font color='#333399'><b>数值</b></font>
     * @param value 数值  
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * <br />获取 <font color="red"><b>客户端编号<b/></font>
     * @return clientId 客户端编号
     */
    public int getClientId() {
        return clientId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端编号</b></font>
     * @param clientId 客户端编号  
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * <br />获取 <font color="red"><b>服务端编号<b/></font>
     * @return serverId 服务端编号
     */
    public int getServerId() {
        return serverId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端编号</b></font>
     * @param serverId 服务端编号  
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
