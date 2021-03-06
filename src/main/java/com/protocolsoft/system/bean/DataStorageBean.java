/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: NetworkCardBean.java
 *创建人: www    创建时间: 2017年11月30日
 */
package com.protocolsoft.system.bean;

/**
 * DataStorageBean
 * 2017年12月8日 下午3:48:48
 * @author long
 * @version
 * @see
 */
public class DataStorageBean {
    /**
     * id
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 观察点
     */
    private String watchpointId;
    /**
     * 开始时间
     */
    private String starttime;
    /**
     * 结束时间
     */
    private String endtime;
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * bpf
     */
    private String bpf;
    /**
     * 长度
     */
    private int size;
    /**
     * 状态
     */
    private boolean state;
    /**
     * 返回状态
     */
    private String success;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the watchpointId
     */
    public String getWatchpointId() {
        return watchpointId;
    }
    /**
     * @param watchpointId the watchpointId to set
     */
    public void setWatchpointId(String watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * @return the starttime
     */
    public String getStarttime() {
        return starttime;
    }
    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    /**
     * @return the endtime
     */
    public String getEndtime() {
        return endtime;
    }
    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
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
    public int getPort() {
        return port;
    }
    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * @return the bpf
     */
    public String getBpf() {
        return bpf;
    }
    /**
     * @param bpf the bpf to set
     */
    public void setBpf(String bpf) {
        this.bpf = bpf;
    }
    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
    /**
     * @return the state
     */
    public boolean isState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(boolean state) {
        this.state = state;
    }
    /**
     * @return the success
     */
    public String getSuccess() {
        return success;
    }
    /**
     * @param success the success to set
     */
    public void setSuccess(String success) {
        this.success = success;
    }
    
}
