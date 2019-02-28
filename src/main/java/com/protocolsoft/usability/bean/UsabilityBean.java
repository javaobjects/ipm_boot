/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UsabilityBean
 *创建人:wjm    创建时间:2018年3月16日
 */
package com.protocolsoft.usability.bean;

/**
 * 
 * @ClassName: UsabilityBean
 * @Description: 应用可用性信息类
 * @author wangjianmin
 *
 */
public class UsabilityBean {
    
    /**
     * 编号
     */
    private int id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * IP地址
     */
    private String ip;
    
    /**
     * 端口
     */
    private String port;
    
    /**
     * 间隔时间
     */
    private String interval;

    /**
     * 最后一次执行时间
     */
    private String lastExecTime;
    
    /**
     * 业务id 
     */
    private int appId;
    
    /**
     * 状态(Y:开启        N:关闭)
     */
    private String status;

    /**
     * 备注
     */
    private String des;
    
    /**
     * 删除所用到的
     */
    private int busiId;

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
     * @return the interval
     */
    public String getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(String interval) {
        this.interval = interval;
    }

    /**
     * @return the lastExecTime
     */
    public String getLastExecTime() {
        return lastExecTime;
    }

    /**
     * @param lastExecTime the lastExecTime to set
     */
    public void setLastExecTime(String lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des the des to set
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * <br />获取 <font color="red"><b>appId<b/></font>
     * @return appId appId
     */
    public int getAppId() {
        return appId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>appId</b></font>
     * @param appId appId  
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * <br />获取 <font color="red"><b>busiId<b/></font>
     * @return busiId busiId
     */
    public int getBusiId() {
        return busiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>busiId</b></font>
     * @param busiId busiId  
     */
    public void setBusiId(int busiId) {
        this.busiId = busiId;
    }
}
