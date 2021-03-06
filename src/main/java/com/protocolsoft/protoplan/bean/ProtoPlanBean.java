/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ProtoPlanBean
 *创建人:chensq    创建时间:2017年10月18日
 */
package com.protocolsoft.protoplan.bean;

/**
 * 对应表ipm_proto_plan
 * 2017年10月18日 上午17:50:54
 * @author chensq
 * @version
 * @see
 */
public class ProtoPlanBean {
    /**
     * id
     */
    private Integer id;
    /**
     * 应用id
     */
    private Integer appId;
    /**
     * ip(long)
     */
    private Long ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 协议
     */
    private Integer proto;
    /**
     * 名称
     */
    private String name;
    /**
     * 显示名称
     */
    private String displayName;
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
    public Long getIp() {
        return ip;
    }
    /**
     * @param ip the ip to set
     */
    public void setIp(Long ip) {
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
    /**
     * @return the proto
     */
    public Integer getProto() {
        return proto;
    }
    /**
     * @param proto the proto to set
     */
    public void setProto(Integer proto) {
        this.proto = proto;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }
    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    
}
