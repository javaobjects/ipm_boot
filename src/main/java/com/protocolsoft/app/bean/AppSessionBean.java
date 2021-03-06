/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppSessionBean.java
 *创建人: www    创建时间: 2018年1月12日
 */
package com.protocolsoft.app.bean;

import com.protocolsoft.common.bean.CenterParamBean;

/**
 * @ClassName: AppSessionBean
 * @Description: 会话列表
 * @author www
 *
 */
public class AppSessionBean extends CenterParamBean {

    /**
     * 开始时间
     */
    private long begintime;
    
    /**
     * 观察点名称
     */
    private String name;
    
    /**
     * 服务端端口
     */
    private int port;
    
    /**
     * 客户端IP
     */
    private String client;
    
    /**
     * 客户端端口
     */
    private int clientport;
    
    /**
     * 字节数
     */
    private int bytes;
    
    /**
     * 客户端通信时延
     */
    private float clientlatency;
    
    /**
     * 服务端通信时延
     */
    private float serverlatency;
    
    /**
     * 网络通信时延
     */
    private float rtt;

    /**
     * <br />获取 <font color="red"><b>开始时间<b/></font>
     * @return begintime 开始时间
     */
    public long getBegintime() {
        return begintime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>开始时间</b></font>
     * @param begintime 开始时间  
     */
    public void setBegintime(long begintime) {
        this.begintime = begintime;
    }

    /**
     * <br />获取 <font color="red"><b>观察点名称<b/></font>
     * @return name 观察点名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>观察点名称</b></font>
     * @param name 观察点名称  
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * <br />获取 <font color="red"><b>服务端端口<b/></font>
     * @return port 服务端端口
     */
    public int getPort() {
        return port;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端端口</b></font>
     * @param port 服务端端口  
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * <br />获取 <font color="red"><b>客户端IP<b/></font>
     * @return client 客户端IP
     */
    public String getClient() {
        return client;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端IP</b></font>
     * @param client 客户端IP  
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * <br />获取 <font color="red"><b>客户端端口<b/></font>
     * @return clientport 客户端端口
     */
    public int getClientport() {
        return clientport;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端端口</b></font>
     * @param clientport 客户端端口  
     */
    public void setClientport(int clientport) {
        this.clientport = clientport;
    }

    /**
     * <br />获取 <font color="red"><b>字节数<b/></font>
     * @return bytes 字节数
     */
    public int getBytes() {
        return bytes;
    }

    /**  
     * <br />设置 <font color='#333399'><b>字节数</b></font>
     * @param bytes 字节数  
     */
    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    /**
     * <br />获取 <font color="red"><b>客户端通信时延<b/></font>
     * @return clientlatency 客户端通信时延
     */
    public float getClientlatency() {
        return clientlatency;
    }

    /**  
     * <br />设置 <font color='#333399'><b>客户端通信时延</b></font>
     * @param clientlatency 客户端通信时延  
     */
    public void setClientlatency(float clientlatency) {
        this.clientlatency = clientlatency;
    }

    /**
     * <br />获取 <font color="red"><b>服务端通信时延<b/></font>
     * @return serverlatency 服务端通信时延
     */
    public float getServerlatency() {
        return serverlatency;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端通信时延</b></font>
     * @param serverlatency 服务端通信时延  
     */
    public void setServerlatency(float serverlatency) {
        this.serverlatency = serverlatency;
    }

    /**
     * <br />获取 <font color="red"><b>网络通信时延<b/></font>
     * @return rtt 网络通信时延
     */
    public float getRtt() {
        return rtt;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网络通信时延</b></font>
     * @param rtt 网络通信时延  
     */
    public void setRtt(float rtt) {
        this.rtt = rtt;
    }
}
