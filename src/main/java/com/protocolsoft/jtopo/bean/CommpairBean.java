/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairBean
 *创建人:yan    创建时间:2017-10-16
 */
package com.protocolsoft.jtopo.bean;

import java.io.Serializable;

/**
 * 对应commpair_观察点_粒度_log_000000001
 * 2017-10-16 下午3:23:56
 * @author yan
 * @version
 * @see
 */
public class CommpairBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Integer id;
    
    /**
     * 快照时间
     */
    private Integer snaptime;
    
    /**
     * 服务端Ip
     */
    private Integer serverip;
    
    /**
     * 客户端Ip
     */
    private Integer clientip;
    
    /**
     * 服务端端口
     */
    private Integer serverport;
    
    /**
     * 服务端流量
     */
    private Long serverTraffic;
    
    /**
     * 客户端流量
     */
    private Long clientTraffic;
    
    /**
     * 服务端包数
     */
    private Integer serverPkts;
    
    /**
     * 客户端包数
     */
    private Integer clientPkts;
    
    /**
     * 服务端通信时延
     */
    private Double serverDurDelay;
    
    /**
     * 客户端通信时延
     */
    private Double clientDurDelay;
    
    /**
     * 服务端握手时延
     */
    private Double serverConDelay;
    
    /**
     * 客户端握手时延
     */
    private Double clientConDelay; 
    
    /**
     * 服务端重传包数
     */
    private Integer serverRetrans;
    
    /**
     * 客户端重传包数
     */
    private Integer clientRetrans;
    
    /**
     * 服务端中断包数
     */
    private Integer serverRst;
    
    /**
     * 客户端中断包数
     */
    private Integer clientRst;
    
    /**
     * 服务端关闭包数
     */
    private Integer serverFin;
    
    /**
     * 客户端关闭包数
     */
    private Integer clientFin;
    
    /**
     * 服务端同步包数
     */
    private Integer serverSyn;
    
    /**
     * 客户端同步包数
     */
    private Integer clientSyn;
    
    /**
     * 服务端小包数
     */
    private Integer serverTiny;
    
    /**
     * 客户端小包数
     */
    private Integer clientTiny;
    
    /**
     * 服务端TCP流量
     */
    private Long serverTcpTraffic;
    
    /**
     * 客户端TCP流量
     */
    private Long clientTcpTraffic;
    
    /**
     * 服务端UDP流量
     */
    private Long serverUdpTraffic;
    
    /**
     * 客户端UDP流量
     */
    private Long clientUdpTraffic;
    
    /**
     * 应用处理时延
     */
    private Double appDelay;
    
    /**
     * 服务端归属Id
     */
    private String serverUideId;
    
    /**
     * 客户端归属Id
     */
    private String clientUideId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSnaptime() {
        return snaptime;
    }

    public void setSnaptime(Integer snaptime) {
        this.snaptime = snaptime;
    }

    public Integer getServerip() {
        return serverip;
    }

    public void setServerip(Integer serverip) {
        this.serverip = serverip;
    }

    public Integer getClientip() {
        return clientip;
    }

    public void setClientip(Integer clientip) {
        this.clientip = clientip;
    }

    public Integer getServerport() {
        return serverport;
    }

    public void setServerport(Integer serverport) {
        this.serverport = serverport;
    }

    public Long getServerTraffic() {
        return serverTraffic;
    }

    public void setServerTraffic(Long serverTraffic) {
        this.serverTraffic = serverTraffic;
    }

    public Long getClientTraffic() {
        return clientTraffic;
    }

    public void setClientTraffic(Long clientTraffic) {
        this.clientTraffic = clientTraffic;
    }

    public Integer getServerPkts() {
        return serverPkts;
    }

    public void setServerPkts(Integer serverPkts) {
        this.serverPkts = serverPkts;
    }

    public Integer getClientPkts() {
        return clientPkts;
    }

    public void setClientPkts(Integer clientPkts) {
        this.clientPkts = clientPkts;
    }

    public Double getServerDurDelay() {
        return serverDurDelay;
    }

    public void setServerDurDelay(Double serverDurDelay) {
        this.serverDurDelay = serverDurDelay;
    }

    public Double getClientDurDelay() {
        return clientDurDelay;
    }

    public void setClientDurDelay(Double clientDurDelay) {
        this.clientDurDelay = clientDurDelay;
    }

    public Double getServerConDelay() {
        return serverConDelay;
    }

    public void setServerConDelay(Double serverConDelay) {
        this.serverConDelay = serverConDelay;
    }

    public Double getClientConDelay() {
        return clientConDelay;
    }

    public void setClientConDelay(Double clientConDelay) {
        this.clientConDelay = clientConDelay;
    }

    public Integer getServerRetrans() {
        return serverRetrans;
    }

    public void setServerRetrans(Integer serverRetrans) {
        this.serverRetrans = serverRetrans;
    }

    public Integer getClientRetrans() {
        return clientRetrans;
    }

    public void setClientRetrans(Integer clientRetrans) {
        this.clientRetrans = clientRetrans;
    }

    public Integer getServerRst() {
        return serverRst;
    }

    public void setServerRst(Integer serverRst) {
        this.serverRst = serverRst;
    }

    public Integer getClientRst() {
        return clientRst;
    }

    public void setClientRst(Integer clientRst) {
        this.clientRst = clientRst;
    }

    public Integer getServerFin() {
        return serverFin;
    }

    public void setServerFin(Integer serverFin) {
        this.serverFin = serverFin;
    }

    public Integer getClientFin() {
        return clientFin;
    }

    public void setClientFin(Integer clientFin) {
        this.clientFin = clientFin;
    }

    public Integer getServerSyn() {
        return serverSyn;
    }

    public void setServerSyn(Integer serverSyn) {
        this.serverSyn = serverSyn;
    }

    public Integer getClientSyn() {
        return clientSyn;
    }

    public void setClientSyn(Integer clientSyn) {
        this.clientSyn = clientSyn;
    }

    public Integer getServerTiny() {
        return serverTiny;
    }

    public void setServerTiny(Integer serverTiny) {
        this.serverTiny = serverTiny;
    }

    public Integer getClientTiny() {
        return clientTiny;
    }

    public void setClientTiny(Integer clientTiny) {
        this.clientTiny = clientTiny;
    }

    public Long getServerTcpTraffic() {
        return serverTcpTraffic;
    }

    public void setServerTcpTraffic(Long serverTcpTraffic) {
        this.serverTcpTraffic = serverTcpTraffic;
    }

    public Long getClientTcpTraffic() {
        return clientTcpTraffic;
    }

    public void setClientTcpTraffic(Long clientTcpTraffic) {
        this.clientTcpTraffic = clientTcpTraffic;
    }

    public Long getServerUdpTraffic() {
        return serverUdpTraffic;
    }

    public void setServerUdpTraffic(Long serverUdpTraffic) {
        this.serverUdpTraffic = serverUdpTraffic;
    }

    public Long getClientUdpTraffic() {
        return clientUdpTraffic;
    }

    public void setClientUdpTraffic(Long clientUdpTraffic) {
        this.clientUdpTraffic = clientUdpTraffic;
    }

    public Double getAppDelay() {
        return appDelay;
    }

    public void setAppDelay(Double appDelay) {
        this.appDelay = appDelay;
    }

    public String getServerUideId() {
        return serverUideId;
    }

    public void setServerUideId(String serverUideId) {
        this.serverUideId = serverUideId;
    }

    public String getClientUideId() {
        return clientUideId;
    }

    public void setClientUideId(String clientUideId) {
        this.clientUideId = clientUideId;
    }
}
