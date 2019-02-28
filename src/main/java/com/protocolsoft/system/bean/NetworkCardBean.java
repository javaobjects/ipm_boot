/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: NetworkCardBean.java
 *创建人: www    创建时间: 2017年11月30日
 */
package com.protocolsoft.system.bean;

/**
 * @ClassName: NetworkCardBean
 * @Description: 网卡信息bean
 * @author www
 *
 */
public class NetworkCardBean {

    /**
     * 网卡名称
     */
    private String name;
    
    /**
     * 网卡地址
     */
    private String ip;
    
    /**
     * 接受总字节数
     */
    private long rxBytes;
    
    /**
     * 接收总包数
     */
    private long rxPackets;
    
    /**
     * 网卡是否为混杂模式
     */
    private boolean promisc;

    /**
     * <br />获取 <font color="red"><b>网卡名称<b/></font>
     * @return name 网卡名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网卡名称</b></font>
     * @param name 网卡名称  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>网卡地址<b/></font>
     * @return ip 网卡地址
     */
    public String getIp() {
        return ip;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网卡地址</b></font>
     * @param ip 网卡地址  
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * <br />获取 <font color="red"><b>接受总字节数<b/></font>
     * @return rxBytes 接受总字节数
     */
    public long getRxBytes() {
        return rxBytes;
    }

    /**  
     * <br />设置 <font color='#333399'><b>接受总字节数</b></font>
     * @param rxBytes 接受总字节数  
     */
    public void setRxBytes(long rxBytes) {
        this.rxBytes = rxBytes;
    }

    /**
     * <br />获取 <font color="red"><b>接收总包数<b/></font>
     * @return rxPackets 接收总包数
     */
    public long getRxPackets() {
        return rxPackets;
    }

    /**  
     * <br />设置 <font color='#333399'><b>接收总包数</b></font>
     * @param rxPackets 接收总包数  
     */
    public void setRxPackets(long rxPackets) {
        this.rxPackets = rxPackets;
    }

    /**
     * <br />获取 <font color="red"><b>网卡是否为混杂模式<b/></font>
     * @return promisc 网卡是否为混杂模式
     */
    public boolean isPromisc() {
        return promisc;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网卡是否为混杂模式</b></font>
     * @param promisc 网卡是否为混杂模式  
     */
    public void setPromisc(boolean promisc) {
        this.promisc = promisc;
    }
}
