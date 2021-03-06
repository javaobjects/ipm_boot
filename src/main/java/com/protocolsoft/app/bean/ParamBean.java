/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ParamBean.java
 *创建人: www    创建时间: 2018年1月11日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: ParamBean
 * @Description: 参数
 * @author www
 *
 */
public class ParamBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * Center编号
     */
    private Integer ipmCenterId;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 观察点编号
     */
    private Integer watchpointId;
    
    /**
     * 业务编号
     */
    private Integer busiId;
    
    /**
     * 服务端IP
     */
    private String serverip;
    
    /**
     * 服务端端口
     */
    private Integer serverport;
    
    /**
     * 客户端IP
     */
    private String clientip;
    
    /**
     * 客户端端口
     */
    private Integer clientport;
    
    /**
     * 返回码
     */
    private String code;
    
    /**
     * URL or SQL
     */
    private String uos;

    /**
     * <br />获取 <font color="red"><b>开始时间<b/></font>
     * @return starttime 开始时间
     */
    public long getStarttime() {
        return starttime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>开始时间</b></font>
     * @param starttime 开始时间  
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    /**
     * <br />获取 <font color="red"><b>结束时间<b/></font>
     * @return endtime 结束时间
     */
    public long getEndtime() {
        return endtime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>结束时间</b></font>
     * @param endtime 结束时间  
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    /**
     * <br />获取 <font color="red"><b>Center编号<b/></font>
     * @return ipmCenterId Center编号
     */
    public Integer getIpmCenterId() {
        return ipmCenterId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>Center编号</b></font>
     * @param ipmCenterId Center编号  
     */
    public void setIpmCenterId(Integer ipmCenterId) {
        this.ipmCenterId = ipmCenterId;
    }

    /**
     * <br />获取 <font color="red"><b>模块编号<b/></font>
     * @return moduleId 模块编号
     */
    public int getModuleId() {
        return moduleId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>模块编号</b></font>
     * @param moduleId 模块编号  
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * <br />获取 <font color="red"><b>观察点编号<b/></font>
     * @return watchpointId 观察点编号
     */
    public Integer getWatchpointId() {
        return watchpointId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>观察点编号</b></font>
     * @param watchpointId 观察点编号  
     */
    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }

    /**
     * <br />获取 <font color="red"><b>业务编号<b/></font>
     * @return busiId 业务编号
     */
    public Integer getBusiId() {
        return busiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>业务编号</b></font>
     * @param busiId 业务编号  
     */
    public void setBusiId(Integer busiId) {
        this.busiId = busiId;
    }

    /**
     * <br />获取 <font color="red"><b>服务端IP<b/></font>
     * @return serverip 服务端IP
     */
    public String getServerip() {
        return serverip;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端IP</b></font>
     * @param serverip 服务端IP  
     */
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    /**
     * <br />获取 <font color="red"><b>服务端端口<b/></font>
     * @return serverport 服务端端口
     */
    public Integer getServerport() {
        return serverport;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端端口</b></font>
     * @param serverport 服务端端口  
     */
    public void setServerport(Integer serverport) {
        this.serverport = serverport;
    }

    /**
     * <br />获取 <font color="red"><b>客户端IP<b/></font>
     * @return clientip 客户端IP
     */
    public String getClientip() {
        return clientip;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端IP</b></font>
     * @param clientip 客户端IP  
     */
    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    /**
     * <br />获取 <font color="red"><b>客户端端口<b/></font>
     * @return clientport 客户端端口
     */
    public Integer getClientport() {
        return clientport;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端端口</b></font>
     * @param clientport 客户端端口  
     */
    public void setClientport(Integer clientport) {
        this.clientport = clientport;
    }

    /**
     * <br />获取 <font color="red"><b>返回码<b/></font>
     * @return code 返回码
     */
    public String getCode() {
        return code;
    }

    /**  
     * <br />设置 <font color='#333399'><b>返回码</b></font>
     * @param code 返回码  
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <br />获取 <font color="red"><b>URLorSQL<b/></font>
     * @return uos URLorSQL
     */
    public String getUos() {
        return uos;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URLorSQL</b></font>
     * @param uos URLorSQL  
     */
    public void setUos(String uos) {
        this.uos = uos;
    }
}
