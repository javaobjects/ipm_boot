/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairVoBean
 *创建人:yan    创建时间:2017-10-17
 */
package com.protocolsoft.jtopo.bean;

import java.io.Serializable;

/**
 * CommpairVo
 * 2017-10-17 上午10:16:38
 * @author yan
 * @version
 * @see
 */
public class CommpairVoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 目前存储的是服务端IP
     */
    private String fromIp;
    
    /**
     * 目前存储的是服务端IP 
     */
    private String realIp;
    
    /**
     * 目前存储的是客户端IP
     */
    private String toInfo;
    
    /**
     * 目前存储的是server
     */
    private String fromType;
    
    /**
     * 目前存储的是server
     */
    private String toType;
    
    /**
     * 目前存储的是0
     */
    private Long fromAlarm;
    
    /**
     * 目前存储的是0
     */
    private Long toAlarm;
    
    /**
     * 根据传过来的kpi(比如流量、数据包速率等)生成对应的计算列,value作为别名
     */
    private Double value;
    
    /**
     * 开始时间
     */
    private Long startTime;
    
    /**
     * 结束时间
     */
    private Long endTime;
    
    /**
     * IP
     */
    private String ip;
    
    /**
     * 端口
     */
    private String port;
    
    /**
     * 类型
     */
    private String type;

    /**
     * @return the fromIp
     */
    public String getFromIp() {
        return fromIp;
    }

    /**
     * @param fromIp the fromIp to set
     */
    public void setFromIp(String fromIp) {
        this.fromIp = fromIp;
    }

    /**
     * @return the realIp
     */
    public String getRealIp() {
        return realIp;
    }

    /**
     * @param realIp the realIp to set
     */
    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    /**
     * @return the toInfo
     */
    public String getToInfo() {
        return toInfo;
    }

    /**
     * @param toInfo the toInfo to set
     */
    public void setToInfo(String toInfo) {
        this.toInfo = toInfo;
    }

    /**
     * @return the fromType
     */
    public String getFromType() {
        return fromType;
    }

    /**
     * @param fromType the fromType to set
     */
    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    /**
     * @return the toType
     */
    public String getToType() {
        return toType;
    }

    /**
     * @param toType the toType to set
     */
    public void setToType(String toType) {
        this.toType = toType;
    }

    /**
     * @return the fromAlarm
     */
    public Long getFromAlarm() {
        return fromAlarm;
    }

    /**
     * @param fromAlarm the fromAlarm to set
     */
    public void setFromAlarm(Long fromAlarm) {
        this.fromAlarm = fromAlarm;
    }

    /**
     * @return the toAlarm
     */
    public Long getToAlarm() {
        return toAlarm;
    }

    /**
     * @param toAlarm the toAlarm to set
     */
    public void setToAlarm(Long toAlarm) {
        this.toAlarm = toAlarm;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
