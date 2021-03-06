/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppIpPortBean
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.servers.bean;

import java.io.Serializable;

/**
 * 对应表ipm_app_ip_port
 * 2017年9月1日 上午11:51:14
 * @author yan
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class AppIpPortBean implements Serializable{
    
    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 应用业务id
     */
    private Integer appId;
    
    /**
     * ip
     */
    private String ip;
    
    /**
     * 端口
     */
    private Integer port;

    
    /**
     * 
     */
    public AppIpPortBean() {
        super();
    }

    /**
     * @param id
     * @param appId
     * @param ip
     * @param port
     */
    public AppIpPortBean(Integer id, Integer appId, String ip, Integer port) {
        super();
        this.id = id;
        this.appId = appId;
        this.ip = ip;
        this.port = port;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the appId
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(Integer appId) {
        this.appId = appId;
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
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }
}
