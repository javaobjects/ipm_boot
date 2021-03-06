/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommpairPropellingBean.java
 *创建人: chensq    创建时间: 2018年9月26日
 */
package com.protocolsoft.commpair.bean;

/**
 * @ClassName: CommpairPropellingBean
 * @Description: 通信对推送使用的bean,如有需要的字段需要维护
 * @author chensq
 *
 */
public class CommpairPropellingBean {
    /**
     * @Fields id : 程序中设置
     */
    private long id;
    /**
     * @Fields watchpointId : 观察点id
     */
    private int watchpointId;
    /**
     * @Fields watchpointName : 观察点名称
     */
    private String watchpointName;
    /**
     * @Fields snaptime : 快照时间(发起时间)
     */
    private long snaptime;
    /**
     * @Fields starttime : 查询时的开始时间
     */
    private long starttime;
    /**
     * @Fields endtime : 查询时的结束时间
     */
    private long endtime;
    /**
     * @Fields serverip : 服务端ip
     */
    private String serverip;
    /**
     * @Fields serverport : 服务端端口
     */
    private long serverport;
    /**
     * @Fields clientip : 客户端ip
     */
    private String clientip;
    /**
     * @Fields serverName : 应用/服务端name
     */
    private String serverName;
    /**
     * @Fields clientName : 子网/name
     */
    private String clientName;
    /**
     * @Fields protoPlanId : 协议id
     */
    private int protoPlanId;
    /**
     * @Fields protocol : 协议显示名
     */
    private String protocol;
    

    /**
     * @Fields ethernetTraffic : 网络流量
     */
    private double  ethernetTraffic;
    /**
     * @Fields qoS : 服务质量   serverConDelay+responseDelay+loadDelay
     */
    private double qos;
    /**
     * @Fields netPktLostRatio :  网络丢包率
     */
    private double netPktLostRatio;
    /**
     * @Fields synPkts : SYN包数
     */
    private long synPkts;
    /**
     * @Fields rtt : 链路时延RTT
     */
    private double rtt;
    /**
     * @Fields avgPktsLen :  平均包长   ethernetTraffic / 8 / ethernetPkts
     */
    private double avgPktsLen;

    
    
    /**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public long getId() {
        return id;
    }
    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * <br />获取 <font color="red"><b>watchpointId<b/></font>
     * @return watchpointId watchpointId
     */
    public int getWatchpointId() {
        return watchpointId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>watchpointId</b></font>
     * @param watchpointId watchpointId  
     */
    public void setWatchpointId(int watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * <br />获取 <font color="red"><b>watchpointName<b/></font>
     * @return watchpointName watchpointName
     */
    public String getWatchpointName() {
        return watchpointName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>watchpointName</b></font>
     * @param watchpointName watchpointName  
     */
    public void setWatchpointName(String watchpointName) {
        this.watchpointName = watchpointName;
    }
    /**
     * <br />获取 <font color="red"><b>snaptime<b/></font>
     * @return snaptime snaptime
     */
    public long getSnaptime() {
        return snaptime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>snaptime</b></font>
     * @param snaptime snaptime  
     */
    public void setSnaptime(long snaptime) {
        this.snaptime = snaptime;
    }
    /**
     * <br />获取 <font color="red"><b>starttime<b/></font>
     * @return starttime starttime
     */
    public long getStarttime() {
        return starttime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>starttime</b></font>
     * @param starttime starttime  
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }
    /**
     * <br />获取 <font color="red"><b>endtime<b/></font>
     * @return endtime endtime
     */
    public long getEndtime() {
        return endtime;
    }
    /**  
     * <br />设置 <font color='#333399'><b>endtime</b></font>
     * @param endtime endtime  
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
    /**
     * <br />获取 <font color="red"><b>serverip<b/></font>
     * @return serverip serverip
     */
    public String getServerip() {
        return serverip;
    }
    /**  
     * <br />设置 <font color='#333399'><b>serverip</b></font>
     * @param serverip serverip  
     */
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }
    /**
     * <br />获取 <font color="red"><b>serverport<b/></font>
     * @return serverport serverport
     */
    public long getServerport() {
        return serverport;
    }
    /**  
     * <br />设置 <font color='#333399'><b>serverport</b></font>
     * @param serverport serverport  
     */
    public void setServerport(long serverport) {
        this.serverport = serverport;
    }
    /**
     * <br />获取 <font color="red"><b>clientip<b/></font>
     * @return clientip clientip
     */
    public String getClientip() {
        return clientip;
    }
    /**  
     * <br />设置 <font color='#333399'><b>clientip</b></font>
     * @param clientip clientip  
     */
    public void setClientip(String clientip) {
        this.clientip = clientip;
    }
    /**
     * <br />获取 <font color="red"><b>serverName<b/></font>
     * @return serverName serverName
     */
    public String getServerName() {
        return serverName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>serverName</b></font>
     * @param serverName serverName  
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    /**
     * <br />获取 <font color="red"><b>clientName<b/></font>
     * @return clientName clientName
     */
    public String getClientName() {
        return clientName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>clientName</b></font>
     * @param clientName clientName  
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * <br />获取 <font color="red"><b>protoPlanId<b/></font>
     * @return protoPlanId protoPlanId
     */
    public int getProtoPlanId() {
        return protoPlanId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>protoPlanId</b></font>
     * @param protoPlanId protoPlanId  
     */
    public void setProtoPlanId(int protoPlanId) {
        this.protoPlanId = protoPlanId;
    }
    /**
     * <br />获取 <font color="red"><b>protocol<b/></font>
     * @return protocol protocol
     */
    public String getProtocol() {
        return protocol;
    }
    /**  
     * <br />设置 <font color='#333399'><b>protocol</b></font>
     * @param protocol protocol  
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    /**
     * <br />获取 <font color="red"><b>ethernetTraffic<b/></font>
     * @return ethernetTraffic ethernetTraffic
     */
    public double getEthernetTraffic() {
        return ethernetTraffic;
    }
    /**  
     * <br />设置 <font color='#333399'><b>ethernetTraffic</b></font>
     * @param ethernetTraffic ethernetTraffic  
     */
    public void setEthernetTraffic(double ethernetTraffic) {
        this.ethernetTraffic = ethernetTraffic;
    }
    /**
     * <br />获取 <font color="red"><b>qos<b/></font>
     * @return qos qos
     */
    public double getQos() {
        return qos;
    }
    /**  
     * <br />设置 <font color='#333399'><b>qos</b></font>
     * @param qos qos  
     */
    public void setQos(double qos) {
        this.qos = qos;
    }
    /**
     * <br />获取 <font color="red"><b>netPktLostRatio<b/></font>
     * @return netPktLostRatio netPktLostRatio
     */
    public double getNetPktLostRatio() {
        return netPktLostRatio;
    }
    /**  
     * <br />设置 <font color='#333399'><b>netPktLostRatio</b></font>
     * @param netPktLostRatio netPktLostRatio  
     */
    public void setNetPktLostRatio(double netPktLostRatio) {
        this.netPktLostRatio = netPktLostRatio;
    }
    /**
     * <br />获取 <font color="red"><b>synPkts<b/></font>
     * @return synPkts synPkts
     */
    public long getSynPkts() {
        return synPkts;
    }
    /**  
     * <br />设置 <font color='#333399'><b>synPkts</b></font>
     * @param synPkts synPkts  
     */
    public void setSynPkts(long synPkts) {
        this.synPkts = synPkts;
    }
    /**
     * <br />获取 <font color="red"><b>rtt<b/></font>
     * @return rtt rtt
     */
    public double getRtt() {
        return rtt;
    }
    /**  
     * <br />设置 <font color='#333399'><b>rtt</b></font>
     * @param rtt rtt  
     */
    public void setRtt(double rtt) {
        this.rtt = rtt;
    }
    /**
     * <br />获取 <font color="red"><b>avgPktsLen<b/></font>
     * @return avgPktsLen avgPktsLen
     */
    public double getAvgPktsLen() {
        return avgPktsLen;
    }
    /**  
     * <br />设置 <font color='#333399'><b>avgPktsLen</b></font>
     * @param avgPktsLen avgPktsLen  
     */
    public void setAvgPktsLen(double avgPktsLen) {
        this.avgPktsLen = avgPktsLen;
    }    
    
}
