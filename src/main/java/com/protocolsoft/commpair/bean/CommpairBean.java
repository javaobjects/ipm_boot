/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairBean.java
 *创建人:chensq    创建时间:2017年10月17日
 */
package com.protocolsoft.commpair.bean;

/**
 * @ClassName: CommpairBean
 * @Description: 通信对实体类
 * @author chensq
 */
public class CommpairBean {
    
    //------------------------显示列start----------------------
    /**
     * @Fields snaptime : 快照时间(发起时间)
     */
    private long snaptime;
    /**
     * @Fields ipmCenterName : XPM服务器名称
     */
    private String ipmCenterName;
    /**
     * @Fields watchpointName : 观察点名称
     */
    private String watchpointName;
    /**
     * @Fields protocol : 协议显示名
     */
    private String protocol;
    /**
     * @Fields serverip : 服务端ip
     */
    private String serverip; 
    
    
    /**
     * @Fields serverport : 服务端端口
     */
    private long serverport;
    /**
     * @Fields serverName : 应用/服务端name
     */
    private String serverName;
    /**
     * @Fields clientip : 客户端ip
     */
    private String clientip;
    /**
     * @Fields clientName : 子网/name
     */
    private String clientName;
    /**
     * @Fields qoS : 服务质量   serverConDelay+responseDelay+loadDelay
     */
    private double qos;
    
    
    /**
     * @Fields serverDurDelay : 服务端通信时延
     */
    private double serverDurDelay;
    /**
     * @Fields clientDurDelay : 客户端通信时延
     */
    private double clientDurDelay;
    /**
     * @Fields rtt : 链路时延RTT
     */
    private double rtt;
    /**
     * @Fields serverConDelay : 服务端握手时延
     */
    private double serverConDelay;
    /**
     * @Fields clientConDelay : 客户端握手时延
     */
    private double clientConDelay;
    
    
    /**
     * @Fields responseDelay : 应用处理时延
     */
    private double responseDelay;    
    /**
     * @Fields loadDelay : 负载传输时延
     */
    private double loadDelay;
    /**
     * @Fields serverRetransDelay : 服务端重传时延
     */
    private double serverRetransDelay;
    /**
     * @Fields clientRetransDelay : 客户端重传时延
     */
    private double clientRetransDelay;
    /**
     * @Fields ethernetTraffic : 网络流量
     */
    private double  ethernetTraffic;
    
    
    /**
     * @Fields tcpTraffic : TCP流量
     */
    private double  tcpTraffic;
    /**
     * @Fields udpTraffic : UDP流量
     */
    private double  udpTraffic;
    /**
     * @Fields inTraffic : 入流量
     */
    private double inTraffic;
    /**
     * @Fields outTraffic :  出流量
     */
    private double outTraffic;
    /**
     * @Fields synPkts : SYN包数
     */
    private long synPkts;
    /**
     * @Fields synAckPkts : 连接应答数量
     */
    private long synAckPkts;
    /**
     * @Fields rstPkts : rst包数
     */
    private long rstPkts;
    /**
     * @Fields finPkts : fin包数
     */
    private long finPkts;
    /**
     * @Fields fin1Pkts : 主动关闭包数
     */
    private long fin1Pkts;
    /**
     * @Fields fin2Pkts : 被动关闭包数
     */
    private long fin2Pkts;     
    /**
     * @Fields ethernetPkts : 数据包个数
     */
    private long ethernetPkts;
    /**
     * @Fields netPktLostRatio :  网络丢包率
     */
    private double netPktLostRatio;
    /**
     * @Fields serverPktLostRatio :  服务端丢包率
     */
    private double serverPktLostRatio;
    
    
    /**
     * @Fields clientPktLostRatio :  客户端丢包率
     */
    private double clientPktLostRatio;
    /**
     * @Fields tinyPkts :  小包个数
     */
    private long tinyPkts;
    /**
     * @Fields tinyPktsRatio :  小包比率
     */
    private double tinyPktsRatio;
    /**
     * @Fields avgPktsLen :  平均包长   ethernetTraffic / 8 / ethernetPkts
     */
    private double avgPktsLen;
    /**
     * @Fields zeroWinCount :  零窗口包数
     */
    private long zeroWinCount;    
    
    
    /**
     * @Fields communicationProtocol: 通信协议-如果serverTcpTraffic大于0显示TCP，剩下显示UDP(有变化)
     */
    private String  communicationProtocol;
    /**
     * @Fields countryCn: 国家
     */
    private String countryCn;
    /**
     * @Fields regionCn: 省份
     */
    private String regionCn;
    /**
     * @Fields cityCn: 城市
     */
    private String cityCn;
    /**
     * @Fields ispCn: 运营商
     */
    private String ispCn;
    
    //------------------------显示列end----------------------
    
    
    //------------------------数据库字段end----------------------
    /**
     * @Fields netPktLost: 网络传输丢包率
     */
    private double netPktLost;   
    /**
     * @Fields serverPktLost: 服务端丢包率
     */
    private double serverPktLost;
    /**
     * @Fields clientPktLost: 客户端丢包率
     */
    private double clientPktLost;
     /**
     * @Fields serverPkt: 服务端包数
      */
    private long serverPkt;  
    /**
     * @Fields clientPkt: 客户端包数
     */
    private long clientPkt;
    //------------------------数据库字段end----------------------
    
 
    //------------------------其他程序使用的字段start-------------------
    /**
     * @Fields id : 程序中设置
     */
    private long id;
    /**
     * @Fields watchpointId : 观察点id
     */
    private int watchpointId;
    /**
     * @Fields watchpointIds : 观察点ids
     */
    private String watchpointIds;
    /**
     * @Fields clientUideIds : 子网/ids
     */
    private String clientUideIds;
    /**
     * @Fields serverUideIds : 应用/服务端ids
     */
    private String serverUideIds;
    
    
    /**
     * @Fields clientId : 子网/id
     */
    private int clientId;
    /**
     * @Fields serverId : 应用/服务端id
     */
    private int serverId;
    /**
     * @Fields kpiId : kpiId
     */
    private int kpiId;
    /**
     * @Fields kpiName : kpiname
     */
    private String kpiName;
    /**
     * @Fields kpiDisplayName : kpidisplayname
     */
    private String kpiDisplayName;


    /**
     * @Fields serverLocId : 对应的地址库服务端id
     */
    private long serverLocId;
    /**
     * @Fields clientLocId : 对应的地址库客户端id
     */
    private long clientLocId;
    /**
     * @Fields needSerCli : 查询时需要服务端ip与客户端ip使用or进行查询  0为不需要  1为需要
     */
    private int needSerCli;
    /**
     * @Fields serverportNotInSql : serverport not in
     */
    private String serverportNotInSql;
    /**
     * @Fields serverIpPortSql : server Ip Port Sql(自定义用户协议使用)
     */
    private String serverIpPortSql;
    
    
    /**
     * @Fields sortTime : 排序使用的时间字段
     */
    private double sortTime;
    /**
     * @Fields starttime : 查询时的开始时间
     */
    private long starttime;   
    /**
     * @Fields endtime : 查询时的结束时间
     */
    private long endtime;    
    /**
     * @Fields groupType : 分组类型
     */
    private int groupType;
    /**
     * @Fields lidu :  时间粒度
     */
    private long lidu;
    
    
    /**
     * @Fields openIpLocationFlag : 默认为0 不开启  1为开启
     */
    private int openIpLocationFlag;
    /**
     * @Fields continent : 洲际
     */
    private String continent; //continent 亚洲
    /**
     * @Fields countryEn : 国家英文
     */
    private String countryEn; //country_english China    
    /**
     * @Fields countryCode : 国家简写
     */
    private String countryCode; //country_code CN
    /**
     * @Fields otherCountry : 是否其他国家的标识
     */
    private int otherCountry; //其他国家标识 0:中国  1：其他国家
    
    
    /**
     * @Fields areaDictId : 地图钻取的时候，不传递中文，通过ipm_area_dict传递中文地址信息
     */
    private long areaDictId;
    /**
     * @Fields protoPlanId : 协议id
     */
    private int protoPlanId;
    /**
     * @Fields ipmCenterId : 远程编号
     */
    private Integer ipmCenterId;
    
    //------------------------其他程序使用的字段end---------------------

    //------------------------聚合使用的字段start---------------------
    /**
     * @Fields startEndstr : 组合的开始结束时间字符串
     */
    private String startEndstr;
    /**
     * @Fields serverIpColumn : 组合的服务端ip
     */
    private long serverIpColumn;
    /**
     * @Fields clientIpColumn : 组合的客户端ip
     */
    private long clientIpColumn;
    /**
     * @Fields serverPortColumn : 组合的端口
     */
    private long serverPortColumn;
    /**
     * @Fields serverportConcat : 分组后横向拼接端口
     */
    private String serverportConcat;
    
    
    /**
     * @Fields serverLocIdColumn : 组合的服务端地址id
     */
    private long serverLocIdColumn;
    /**
     * @Fields clientLocIdColumn : 组合的服务端地址id
     */
    private long clientLocIdColumn;
    //------------------------聚合使用的字段end-----------------------

    
    //------------------------查询使用的字段start---------------------
    /**
     * @Fields starttimeStr : 开始时间str
     */
    private String starttimeStr;
    /**
     * @Fields endtimeStr : 结束时间str
     */
    private String endtimeStr;
    /**
     * @Fields bpf : bpf条件
     */
    private String bpf;
    /**
     * @Fields clientport : 客户端端口
     */
    private Long clientport;
    /**
     * @Fields triggerflag : TODO
     */
    private int triggerflag;  //0:高阈值(降序)  1:低阈值(升序)   默认降序
    //------------------------查询使用的字段end-----------------------

    
    //------------------------流量提取使用的字段start------------------
    /**
     * @Fields historyGetFlow : 区分流量提取还是通信对下载
     */
    private int historyGetFlow; //默认情况即   0为通信对单条下载   1为页面提取   2为有业务 3为会话列表下载
   
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
   
    /**
     * @Fields ipStr : ipStr 对应页面提取的ip
     */
    private String ipStr;
   
    //------------------------流量提取使用的字段end--------------------

    
    //------------------------未知协议使用的字段start------------------
    /**
     * @Fields serCliUnknow : 未知流量
     */
    private String serCliUnknow; // 空或null为正常流量  其他为位置流量，通信对流量下载中有使用
    /**
     * @Fields unKnownFlow : 未知流量标识
     */
    private int unKnownFlow; //是否是查询未知服务   0代表全部(默认)  11未知   12服务端未知
    /**
     * @Fields protocolType : 协议查询类型
     * 0 为正常情况 
     * 1 为公有协议分布 
     * 2 为自定义用户协议
     */
    private long protocolType; //协议类型
    /**
     * @Fields protocolInfo : 公有协议情况内容，用“,”隔开，为协议表id
     * 1.单个 ：1
     * 2.其他     ：1,2,3,4,5
     * 3.未识别 ：对应id
     * --自定义用户协议情况--
     * 内容，用“,”隔开，为自定义用户协议id
     * 1.单个 ：1
     * 2.未识别 ：0
     */
    private String protocolInfo;  //内容   
    //------------------------未知协议使用的字段end------------------

    //------------------------分页使用的字段start------------------
    /**
     * @Fields startNum : 查询开始位置
     */
    private Integer startNum=0;
    /**
     * @Fields serverPageNumber :分页页数
     */
    private Integer serverPageNumber=1; //默认一页
     
    /**
     * @Fields stepNum : 分页页数
     */
    private Integer stepNum=1000; //默认步长
    
    /**
     * @Fields count : 通信对总数
     */
    private long count;
    
    /**
     * 推送与否的标识 0:标准情况 1：推送情况
     */
    private int propelling;
    
    /**
     * @Fields kpis : 推送的kpi集合
     */
    private String kpis;
    //------------------------分页使用的字段end------------------

    //////////////////getter setter方法//////////////////

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
     * <br />获取 <font color="red"><b>ipmCenterName<b/></font>
     * @return ipmCenterName ipmCenterName
     */
    public String getIpmCenterName() {
        return ipmCenterName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ipmCenterName</b></font>
     * @param ipmCenterName ipmCenterName  
     */
    public void setIpmCenterName(String ipmCenterName) {
        this.ipmCenterName = ipmCenterName;
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
     * <br />获取 <font color="red"><b>serverDurDelay<b/></font>
     * @return serverDurDelay serverDurDelay
     */
    public double getServerDurDelay() {
        return serverDurDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverDurDelay</b></font>
     * @param serverDurDelay serverDurDelay  
     */
    public void setServerDurDelay(double serverDurDelay) {
        this.serverDurDelay = serverDurDelay;
    }

    /**
     * <br />获取 <font color="red"><b>clientDurDelay<b/></font>
     * @return clientDurDelay clientDurDelay
     */
    public double getClientDurDelay() {
        return clientDurDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientDurDelay</b></font>
     * @param clientDurDelay clientDurDelay  
     */
    public void setClientDurDelay(double clientDurDelay) {
        this.clientDurDelay = clientDurDelay;
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
     * <br />获取 <font color="red"><b>serverConDelay<b/></font>
     * @return serverConDelay serverConDelay
     */
    public double getServerConDelay() {
        return serverConDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverConDelay</b></font>
     * @param serverConDelay serverConDelay  
     */
    public void setServerConDelay(double serverConDelay) {
        this.serverConDelay = serverConDelay;
    }

    /**
     * <br />获取 <font color="red"><b>clientConDelay<b/></font>
     * @return clientConDelay clientConDelay
     */
    public double getClientConDelay() {
        return clientConDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientConDelay</b></font>
     * @param clientConDelay clientConDelay  
     */
    public void setClientConDelay(double clientConDelay) {
        this.clientConDelay = clientConDelay;
    }

    /**
     * <br />获取 <font color="red"><b>responseDelay<b/></font>
     * @return responseDelay responseDelay
     */
    public double getResponseDelay() {
        return responseDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>responseDelay</b></font>
     * @param responseDelay responseDelay  
     */
    public void setResponseDelay(double responseDelay) {
        this.responseDelay = responseDelay;
    }

    /**
     * <br />获取 <font color="red"><b>loadDelay<b/></font>
     * @return loadDelay loadDelay
     */
    public double getLoadDelay() {
        return loadDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>loadDelay</b></font>
     * @param loadDelay loadDelay  
     */
    public void setLoadDelay(double loadDelay) {
        this.loadDelay = loadDelay;
    }

    /**
     * <br />获取 <font color="red"><b>serverRetransDelay<b/></font>
     * @return serverRetransDelay serverRetransDelay
     */
    public double getServerRetransDelay() {
        return serverRetransDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverRetransDelay</b></font>
     * @param serverRetransDelay serverRetransDelay  
     */
    public void setServerRetransDelay(double serverRetransDelay) {
        this.serverRetransDelay = serverRetransDelay;
    }

    /**
     * <br />获取 <font color="red"><b>clientRetransDelay<b/></font>
     * @return clientRetransDelay clientRetransDelay
     */
    public double getClientRetransDelay() {
        return clientRetransDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientRetransDelay</b></font>
     * @param clientRetransDelay clientRetransDelay  
     */
    public void setClientRetransDelay(double clientRetransDelay) {
        this.clientRetransDelay = clientRetransDelay;
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
     * <br />获取 <font color="red"><b>tcpTraffic<b/></font>
     * @return tcpTraffic tcpTraffic
     */
    public double getTcpTraffic() {
        return tcpTraffic;
    }

    /**  
     * <br />设置 <font color='#333399'><b>tcpTraffic</b></font>
     * @param tcpTraffic tcpTraffic  
     */
    public void setTcpTraffic(double tcpTraffic) {
        this.tcpTraffic = tcpTraffic;
    }

    /**
     * <br />获取 <font color="red"><b>udpTraffic<b/></font>
     * @return udpTraffic udpTraffic
     */
    public double getUdpTraffic() {
        return udpTraffic;
    }

    /**  
     * <br />设置 <font color='#333399'><b>udpTraffic</b></font>
     * @param udpTraffic udpTraffic  
     */
    public void setUdpTraffic(double udpTraffic) {
        this.udpTraffic = udpTraffic;
    }

    /**
     * <br />获取 <font color="red"><b>inTraffic<b/></font>
     * @return inTraffic inTraffic
     */
    public double getInTraffic() {
        return inTraffic;
    }

    /**  
     * <br />设置 <font color='#333399'><b>inTraffic</b></font>
     * @param inTraffic inTraffic  
     */
    public void setInTraffic(double inTraffic) {
        this.inTraffic = inTraffic;
    }

    /**
     * <br />获取 <font color="red"><b>outTraffic<b/></font>
     * @return outTraffic outTraffic
     */
    public double getOutTraffic() {
        return outTraffic;
    }

    /**  
     * <br />设置 <font color='#333399'><b>outTraffic</b></font>
     * @param outTraffic outTraffic  
     */
    public void setOutTraffic(double outTraffic) {
        this.outTraffic = outTraffic;
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
     * <br />获取 <font color="red"><b>synAckPkts<b/></font>
     * @return synAckPkts synAckPkts
     */
    public long getSynAckPkts() {
        return synAckPkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>synAckPkts</b></font>
     * @param synAckPkts synAckPkts  
     */
    public void setSynAckPkts(long synAckPkts) {
        this.synAckPkts = synAckPkts;
    }

    /**
     * <br />获取 <font color="red"><b>rstPkts<b/></font>
     * @return rstPkts rstPkts
     */
    public long getRstPkts() {
        return rstPkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>rstPkts</b></font>
     * @param rstPkts rstPkts  
     */
    public void setRstPkts(long rstPkts) {
        this.rstPkts = rstPkts;
    }

    /**
     * <br />获取 <font color="red"><b>finPkts<b/></font>
     * @return finPkts finPkts
     */
    public long getFinPkts() {
        return finPkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>finPkts</b></font>
     * @param finPkts finPkts  
     */
    public void setFinPkts(long finPkts) {
        this.finPkts = finPkts;
    }

    /**
     * <br />获取 <font color="red"><b>fin1Pkts<b/></font>
     * @return fin1Pkts fin1Pkts
     */
    public long getFin1Pkts() {
        return fin1Pkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>fin1Pkts</b></font>
     * @param fin1Pkts fin1Pkts  
     */
    public void setFin1Pkts(long fin1Pkts) {
        this.fin1Pkts = fin1Pkts;
    }

    /**
     * <br />获取 <font color="red"><b>fin2Pkts<b/></font>
     * @return fin2Pkts fin2Pkts
     */
    public long getFin2Pkts() {
        return fin2Pkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>fin2Pkts</b></font>
     * @param fin2Pkts fin2Pkts  
     */
    public void setFin2Pkts(long fin2Pkts) {
        this.fin2Pkts = fin2Pkts;
    }

    /**
     * <br />获取 <font color="red"><b>ethernetPkts<b/></font>
     * @return ethernetPkts ethernetPkts
     */
    public long getEthernetPkts() {
        return ethernetPkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ethernetPkts</b></font>
     * @param ethernetPkts ethernetPkts  
     */
    public void setEthernetPkts(long ethernetPkts) {
        this.ethernetPkts = ethernetPkts;
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
     * <br />获取 <font color="red"><b>serverPktLostRatio<b/></font>
     * @return serverPktLostRatio serverPktLostRatio
     */
    public double getServerPktLostRatio() {
        return serverPktLostRatio;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverPktLostRatio</b></font>
     * @param serverPktLostRatio serverPktLostRatio  
     */
    public void setServerPktLostRatio(double serverPktLostRatio) {
        this.serverPktLostRatio = serverPktLostRatio;
    }

    /**
     * <br />获取 <font color="red"><b>clientPktLostRatio<b/></font>
     * @return clientPktLostRatio clientPktLostRatio
     */
    public double getClientPktLostRatio() {
        return clientPktLostRatio;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientPktLostRatio</b></font>
     * @param clientPktLostRatio clientPktLostRatio  
     */
    public void setClientPktLostRatio(double clientPktLostRatio) {
        this.clientPktLostRatio = clientPktLostRatio;
    }

    /**
     * <br />获取 <font color="red"><b>tinyPkts<b/></font>
     * @return tinyPkts tinyPkts
     */
    public long getTinyPkts() {
        return tinyPkts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>tinyPkts</b></font>
     * @param tinyPkts tinyPkts  
     */
    public void setTinyPkts(long tinyPkts) {
        this.tinyPkts = tinyPkts;
    }

    /**
     * <br />获取 <font color="red"><b>tinyPktsRatio<b/></font>
     * @return tinyPktsRatio tinyPktsRatio
     */
    public double getTinyPktsRatio() {
        return tinyPktsRatio;
    }

    /**  
     * <br />设置 <font color='#333399'><b>tinyPktsRatio</b></font>
     * @param tinyPktsRatio tinyPktsRatio  
     */
    public void setTinyPktsRatio(double tinyPktsRatio) {
        this.tinyPktsRatio = tinyPktsRatio;
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

    /**
     * <br />获取 <font color="red"><b>zeroWinCount<b/></font>
     * @return zeroWinCount zeroWinCount
     */
    public long getZeroWinCount() {
        return zeroWinCount;
    }

    /**  
     * <br />设置 <font color='#333399'><b>zeroWinCount</b></font>
     * @param zeroWinCount zeroWinCount  
     */
    public void setZeroWinCount(long zeroWinCount) {
        this.zeroWinCount = zeroWinCount;
    }

    /**
     * <br />获取 <font color="red"><b>communicationProtocol<b/></font>
     * @return communicationProtocol communicationProtocol
     */
    public String getCommunicationProtocol() {
        return communicationProtocol;
    }

    /**  
     * <br />设置 <font color='#333399'><b>communicationProtocol</b></font>
     * @param communicationProtocol communicationProtocol  
     */
    public void setCommunicationProtocol(String communicationProtocol) {
        this.communicationProtocol = communicationProtocol;
    }

    /**
     * <br />获取 <font color="red"><b>countryCn<b/></font>
     * @return countryCn countryCn
     */
    public String getCountryCn() {
        return countryCn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>countryCn</b></font>
     * @param countryCn countryCn  
     */
    public void setCountryCn(String countryCn) {
        this.countryCn = countryCn;
    }

    /**
     * <br />获取 <font color="red"><b>regionCn<b/></font>
     * @return regionCn regionCn
     */
    public String getRegionCn() {
        return regionCn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>regionCn</b></font>
     * @param regionCn regionCn  
     */
    public void setRegionCn(String regionCn) {
        this.regionCn = regionCn;
    }

    /**
     * <br />获取 <font color="red"><b>cityCn<b/></font>
     * @return cityCn cityCn
     */
    public String getCityCn() {
        return cityCn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>cityCn</b></font>
     * @param cityCn cityCn  
     */
    public void setCityCn(String cityCn) {
        this.cityCn = cityCn;
    }

    /**
     * <br />获取 <font color="red"><b>ispCn<b/></font>
     * @return ispCn ispCn
     */
    public String getIspCn() {
        return ispCn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ispCn</b></font>
     * @param ispCn ispCn  
     */
    public void setIspCn(String ispCn) {
        this.ispCn = ispCn;
    }

    /**
     * <br />获取 <font color="red"><b>netPktLost<b/></font>
     * @return netPktLost netPktLost
     */
    public double getNetPktLost() {
        return netPktLost;
    }

    /**  
     * <br />设置 <font color='#333399'><b>netPktLost</b></font>
     * @param netPktLost netPktLost  
     */
    public void setNetPktLost(double netPktLost) {
        this.netPktLost = netPktLost;
    }

    /**
     * <br />获取 <font color="red"><b>serverPktLost<b/></font>
     * @return serverPktLost serverPktLost
     */
    public double getServerPktLost() {
        return serverPktLost;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverPktLost</b></font>
     * @param serverPktLost serverPktLost  
     */
    public void setServerPktLost(double serverPktLost) {
        this.serverPktLost = serverPktLost;
    }

    /**
     * <br />获取 <font color="red"><b>clientPktLost<b/></font>
     * @return clientPktLost clientPktLost
     */
    public double getClientPktLost() {
        return clientPktLost;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientPktLost</b></font>
     * @param clientPktLost clientPktLost  
     */
    public void setClientPktLost(double clientPktLost) {
        this.clientPktLost = clientPktLost;
    }

    /**
     * <br />获取 <font color="red"><b>serverPkt<b/></font>
     * @return serverPkt serverPkt
     */
    public long getServerPkt() {
        return serverPkt;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverPkt</b></font>
     * @param serverPkt serverPkt  
     */
    public void setServerPkt(long serverPkt) {
        this.serverPkt = serverPkt;
    }

    /**
     * <br />获取 <font color="red"><b>clientPkt<b/></font>
     * @return clientPkt clientPkt
     */
    public long getClientPkt() {
        return clientPkt;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientPkt</b></font>
     * @param clientPkt clientPkt  
     */
    public void setClientPkt(long clientPkt) {
        this.clientPkt = clientPkt;
    }

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
     * <br />获取 <font color="red"><b>watchpointIds<b/></font>
     * @return watchpointIds watchpointIds
     */
    public String getWatchpointIds() {
        return watchpointIds;
    }

    /**  
     * <br />设置 <font color='#333399'><b>watchpointIds</b></font>
     * @param watchpointIds watchpointIds  
     */
    public void setWatchpointIds(String watchpointIds) {
        this.watchpointIds = watchpointIds;
    }

    /**
     * <br />获取 <font color="red"><b>clientUideIds<b/></font>
     * @return clientUideIds clientUideIds
     */
    public String getClientUideIds() {
        return clientUideIds;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientUideIds</b></font>
     * @param clientUideIds clientUideIds  
     */
    public void setClientUideIds(String clientUideIds) {
        this.clientUideIds = clientUideIds;
    }

    /**
     * <br />获取 <font color="red"><b>serverUideIds<b/></font>
     * @return serverUideIds serverUideIds
     */
    public String getServerUideIds() {
        return serverUideIds;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverUideIds</b></font>
     * @param serverUideIds serverUideIds  
     */
    public void setServerUideIds(String serverUideIds) {
        this.serverUideIds = serverUideIds;
    }

    /**
     * <br />获取 <font color="red"><b>clientId<b/></font>
     * @return clientId clientId
     */
    public int getClientId() {
        return clientId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientId</b></font>
     * @param clientId clientId  
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * <br />获取 <font color="red"><b>serverId<b/></font>
     * @return serverId serverId
     */
    public int getServerId() {
        return serverId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverId</b></font>
     * @param serverId serverId  
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    /**
     * <br />获取 <font color="red"><b>kpiId<b/></font>
     * @return kpiId kpiId
     */
    public int getKpiId() {
        return kpiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>kpiId</b></font>
     * @param kpiId kpiId  
     */
    public void setKpiId(int kpiId) {
        this.kpiId = kpiId;
    }

    /**
     * <br />获取 <font color="red"><b>kpiName<b/></font>
     * @return kpiName kpiName
     */
    public String getKpiName() {
        return kpiName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>kpiName</b></font>
     * @param kpiName kpiName  
     */
    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    /**
     * <br />获取 <font color="red"><b>kpiDisplayName<b/></font>
     * @return kpiDisplayName kpiDisplayName
     */
    public String getKpiDisplayName() {
        return kpiDisplayName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>kpiDisplayName</b></font>
     * @param kpiDisplayName kpiDisplayName  
     */
    public void setKpiDisplayName(String kpiDisplayName) {
        this.kpiDisplayName = kpiDisplayName;
    }

    /**
     * <br />获取 <font color="red"><b>serverLocId<b/></font>
     * @return serverLocId serverLocId
     */
    public long getServerLocId() {
        return serverLocId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverLocId</b></font>
     * @param serverLocId serverLocId  
     */
    public void setServerLocId(long serverLocId) {
        this.serverLocId = serverLocId;
    }

    /**
     * <br />获取 <font color="red"><b>clientLocId<b/></font>
     * @return clientLocId clientLocId
     */
    public long getClientLocId() {
        return clientLocId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientLocId</b></font>
     * @param clientLocId clientLocId  
     */
    public void setClientLocId(long clientLocId) {
        this.clientLocId = clientLocId;
    }

    /**
     * <br />获取 <font color="red"><b>needSerCli<b/></font>
     * @return needSerCli needSerCli
     */
    public int getNeedSerCli() {
        return needSerCli;
    }

    /**  
     * <br />设置 <font color='#333399'><b>needSerCli</b></font>
     * @param needSerCli needSerCli  
     */
    public void setNeedSerCli(int needSerCli) {
        this.needSerCli = needSerCli;
    }

    /**
     * <br />获取 <font color="red"><b>serverportNotInSql<b/></font>
     * @return serverportNotInSql serverportNotInSql
     */
    public String getServerportNotInSql() {
        return serverportNotInSql;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverportNotInSql</b></font>
     * @param serverportNotInSql serverportNotInSql  
     */
    public void setServerportNotInSql(String serverportNotInSql) {
        this.serverportNotInSql = serverportNotInSql;
    }

    /**
     * <br />获取 <font color="red"><b>serverIpPortSql<b/></font>
     * @return serverIpPortSql serverIpPortSql
     */
    public String getServerIpPortSql() {
        return serverIpPortSql;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverIpPortSql</b></font>
     * @param serverIpPortSql serverIpPortSql  
     */
    public void setServerIpPortSql(String serverIpPortSql) {
        this.serverIpPortSql = serverIpPortSql;
    }

    /**
     * <br />获取 <font color="red"><b>sortTime<b/></font>
     * @return sortTime sortTime
     */
    public double getSortTime() {
        return sortTime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>sortTime</b></font>
     * @param sortTime sortTime  
     */
    public void setSortTime(double sortTime) {
        this.sortTime = sortTime;
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
     * <br />获取 <font color="red"><b>groupType<b/></font>
     * @return groupType groupType
     */
    public int getGroupType() {
        return groupType;
    }

    /**  
     * <br />设置 <font color='#333399'><b>groupType</b></font>
     * @param groupType groupType  
     */
    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    /**
     * <br />获取 <font color="red"><b>lidu<b/></font>
     * @return lidu lidu
     */
    public long getLidu() {
        return lidu;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lidu</b></font>
     * @param lidu lidu  
     */
    public void setLidu(long lidu) {
        this.lidu = lidu;
    }

    /**
     * <br />获取 <font color="red"><b>openIpLocationFlag<b/></font>
     * @return openIpLocationFlag openIpLocationFlag
     */
    public int getOpenIpLocationFlag() {
        return openIpLocationFlag;
    }

    /**  
     * <br />设置 <font color='#333399'><b>openIpLocationFlag</b></font>
     * @param openIpLocationFlag openIpLocationFlag  
     */
    public void setOpenIpLocationFlag(int openIpLocationFlag) {
        this.openIpLocationFlag = openIpLocationFlag;
    }

    /**
     * <br />获取 <font color="red"><b>continent<b/></font>
     * @return continent continent
     */
    public String getContinent() {
        return continent;
    }

    /**  
     * <br />设置 <font color='#333399'><b>continent</b></font>
     * @param continent continent  
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * <br />获取 <font color="red"><b>countryEn<b/></font>
     * @return countryEn countryEn
     */
    public String getCountryEn() {
        return countryEn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>countryEn</b></font>
     * @param countryEn countryEn  
     */
    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    /**
     * <br />获取 <font color="red"><b>countryCode<b/></font>
     * @return countryCode countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**  
     * <br />设置 <font color='#333399'><b>countryCode</b></font>
     * @param countryCode countryCode  
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * <br />获取 <font color="red"><b>otherCountry<b/></font>
     * @return otherCountry otherCountry
     */
    public int getOtherCountry() {
        return otherCountry;
    }

    /**  
     * <br />设置 <font color='#333399'><b>otherCountry</b></font>
     * @param otherCountry otherCountry  
     */
    public void setOtherCountry(int otherCountry) {
        this.otherCountry = otherCountry;
    }

    /**
     * <br />获取 <font color="red"><b>areaDictId<b/></font>
     * @return areaDictId areaDictId
     */
    public long getAreaDictId() {
        return areaDictId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>areaDictId</b></font>
     * @param areaDictId areaDictId  
     */
    public void setAreaDictId(long areaDictId) {
        this.areaDictId = areaDictId;
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
     * <br />获取 <font color="red"><b>ipmCenterId<b/></font>
     * @return ipmCenterId ipmCenterId
     */
    public Integer getIpmCenterId() {
        return ipmCenterId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ipmCenterId</b></font>
     * @param ipmCenterId ipmCenterId  
     */
    public void setIpmCenterId(Integer ipmCenterId) {
        this.ipmCenterId = ipmCenterId;
    }

    /**
     * <br />获取 <font color="red"><b>startEndstr<b/></font>
     * @return startEndstr startEndstr
     */
    public String getStartEndstr() {
        return startEndstr;
    }

    /**  
     * <br />设置 <font color='#333399'><b>startEndstr</b></font>
     * @param startEndstr startEndstr  
     */
    public void setStartEndstr(String startEndstr) {
        this.startEndstr = startEndstr;
    }

    /**
     * <br />获取 <font color="red"><b>serverIpColumn<b/></font>
     * @return serverIpColumn serverIpColumn
     */
    public long getServerIpColumn() {
        return serverIpColumn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverIpColumn</b></font>
     * @param serverIpColumn serverIpColumn  
     */
    public void setServerIpColumn(long serverIpColumn) {
        this.serverIpColumn = serverIpColumn;
    }

    /**
     * <br />获取 <font color="red"><b>clientIpColumn<b/></font>
     * @return clientIpColumn clientIpColumn
     */
    public long getClientIpColumn() {
        return clientIpColumn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientIpColumn</b></font>
     * @param clientIpColumn clientIpColumn  
     */
    public void setClientIpColumn(long clientIpColumn) {
        this.clientIpColumn = clientIpColumn;
    }

    /**
     * <br />获取 <font color="red"><b>serverPortColumn<b/></font>
     * @return serverPortColumn serverPortColumn
     */
    public long getServerPortColumn() {
        return serverPortColumn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverPortColumn</b></font>
     * @param serverPortColumn serverPortColumn  
     */
    public void setServerPortColumn(long serverPortColumn) {
        this.serverPortColumn = serverPortColumn;
    }

    /**
     * <br />获取 <font color="red"><b>serverportConcat<b/></font>
     * @return serverportConcat serverportConcat
     */
    public String getServerportConcat() {
        return serverportConcat;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverportConcat</b></font>
     * @param serverportConcat serverportConcat  
     */
    public void setServerportConcat(String serverportConcat) {
        this.serverportConcat = serverportConcat;
    }

    /**
     * <br />获取 <font color="red"><b>serverLocIdColumn<b/></font>
     * @return serverLocIdColumn serverLocIdColumn
     */
    public long getServerLocIdColumn() {
        return serverLocIdColumn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverLocIdColumn</b></font>
     * @param serverLocIdColumn serverLocIdColumn  
     */
    public void setServerLocIdColumn(long serverLocIdColumn) {
        this.serverLocIdColumn = serverLocIdColumn;
    }

    /**
     * <br />获取 <font color="red"><b>clientLocIdColumn<b/></font>
     * @return clientLocIdColumn clientLocIdColumn
     */
    public long getClientLocIdColumn() {
        return clientLocIdColumn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientLocIdColumn</b></font>
     * @param clientLocIdColumn clientLocIdColumn  
     */
    public void setClientLocIdColumn(long clientLocIdColumn) {
        this.clientLocIdColumn = clientLocIdColumn;
    }

    /**
     * <br />获取 <font color="red"><b>starttimeStr<b/></font>
     * @return starttimeStr starttimeStr
     */
    public String getStarttimeStr() {
        return starttimeStr;
    }

    /**  
     * <br />设置 <font color='#333399'><b>starttimeStr</b></font>
     * @param starttimeStr starttimeStr  
     */
    public void setStarttimeStr(String starttimeStr) {
        this.starttimeStr = starttimeStr;
    }

    /**
     * <br />获取 <font color="red"><b>endtimeStr<b/></font>
     * @return endtimeStr endtimeStr
     */
    public String getEndtimeStr() {
        return endtimeStr;
    }

    /**  
     * <br />设置 <font color='#333399'><b>endtimeStr</b></font>
     * @param endtimeStr endtimeStr  
     */
    public void setEndtimeStr(String endtimeStr) {
        this.endtimeStr = endtimeStr;
    }

    /**
     * <br />获取 <font color="red"><b>bpf<b/></font>
     * @return bpf bpf
     */
    public String getBpf() {
        return bpf;
    }

    /**  
     * <br />设置 <font color='#333399'><b>bpf</b></font>
     * @param bpf bpf  
     */
    public void setBpf(String bpf) {
        this.bpf = bpf;
    }

    /**
     * <br />获取 <font color="red"><b>clientport<b/></font>
     * @return clientport clientport
     */
    public Long getClientport() {
        return clientport;
    }

    /**  
     * <br />设置 <font color='#333399'><b>clientport</b></font>
     * @param clientport clientport  
     */
    public void setClientport(Long clientport) {
        this.clientport = clientport;
    }

    /**
     * <br />获取 <font color="red"><b>triggerflag<b/></font>
     * @return triggerflag triggerflag
     */
    public int getTriggerflag() {
        return triggerflag;
    }

    /**  
     * <br />设置 <font color='#333399'><b>triggerflag</b></font>
     * @param triggerflag triggerflag  
     */
    public void setTriggerflag(int triggerflag) {
        this.triggerflag = triggerflag;
    }

    /**
     * <br />获取 <font color="red"><b>historyGetFlow<b/></font>
     * @return historyGetFlow historyGetFlow
     */
    public int getHistoryGetFlow() {
        return historyGetFlow;
    }

    /**  
     * <br />设置 <font color='#333399'><b>historyGetFlow</b></font>
     * @param historyGetFlow historyGetFlow  
     */
    public void setHistoryGetFlow(int historyGetFlow) {
        this.historyGetFlow = historyGetFlow;
    }

    /**
     * <br />获取 <font color="red"><b>moduleId<b/></font>
     * @return moduleId moduleId
     */
    public long getModuleId() {
        return moduleId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>moduleId</b></font>
     * @param moduleId moduleId  
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * <br />获取 <font color="red"><b>businessId<b/></font>
     * @return businessId businessId
     */
    public long getBusinessId() {
        return businessId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>businessId</b></font>
     * @param businessId businessId  
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    /**
     * <br />获取 <font color="red"><b>ipStr<b/></font>
     * @return ipStr ipStr
     */
    public String getIpStr() {
        return ipStr;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ipStr</b></font>
     * @param ipStr ipStr  
     */
    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
    }

    /**
     * <br />获取 <font color="red"><b>serCliUnknow<b/></font>
     * @return serCliUnknow serCliUnknow
     */
    public String getSerCliUnknow() {
        return serCliUnknow;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serCliUnknow</b></font>
     * @param serCliUnknow serCliUnknow  
     */
    public void setSerCliUnknow(String serCliUnknow) {
        this.serCliUnknow = serCliUnknow;
    }

    /**
     * <br />获取 <font color="red"><b>unKnownFlow<b/></font>
     * @return unKnownFlow unKnownFlow
     */
    public int getUnKnownFlow() {
        return unKnownFlow;
    }

    /**  
     * <br />设置 <font color='#333399'><b>unKnownFlow</b></font>
     * @param unKnownFlow unKnownFlow  
     */
    public void setUnKnownFlow(int unKnownFlow) {
        this.unKnownFlow = unKnownFlow;
    }

    /**
     * <br />获取 <font color="red"><b>protocolType<b/></font>
     * @return protocolType protocolType
     */
    public long getProtocolType() {
        return protocolType;
    }

    /**  
     * <br />设置 <font color='#333399'><b>protocolType</b></font>
     * @param protocolType protocolType  
     */
    public void setProtocolType(long protocolType) {
        this.protocolType = protocolType;
    }

    /**
     * <br />获取 <font color="red"><b>protocolInfo<b/></font>
     * @return protocolInfo protocolInfo
     */
    public String getProtocolInfo() {
        return protocolInfo;
    }

    /**  
     * <br />设置 <font color='#333399'><b>protocolInfo</b></font>
     * @param protocolInfo protocolInfo  
     */
    public void setProtocolInfo(String protocolInfo) {
        this.protocolInfo = protocolInfo;
    }

    /**
     * <br />获取 <font color="red"><b>startNum<b/></font>
     * @return startNum startNum
     */
    public Integer getStartNum() {
        return startNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>startNum</b></font>
     * @param startNum startNum  
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    /**
     * <br />获取 <font color="red"><b>serverPageNumber<b/></font>
     * @return serverPageNumber serverPageNumber
     */
    public Integer getServerPageNumber() {
        return serverPageNumber;
    }

    /**  
     * <br />设置 <font color='#333399'><b>serverPageNumber</b></font>
     * @param serverPageNumber serverPageNumber  
     */
    public void setServerPageNumber(Integer serverPageNumber) {
        this.serverPageNumber = serverPageNumber;
    }

    /**
     * <br />获取 <font color="red"><b>stepNum<b/></font>
     * @return stepNum stepNum
     */
    public Integer getStepNum() {
        return stepNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>stepNum</b></font>
     * @param stepNum stepNum  
     */
    public void setStepNum(Integer stepNum) {
        this.stepNum = stepNum;
    }

    /**
     * <br />获取 <font color="red"><b>count<b/></font>
     * @return count count
     */
    public long getCount() {
        return count;
    }

    /**  
     * <br />设置 <font color='#333399'><b>count</b></font>
     * @param count count  
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * <br />获取 <font color="red"><b>propelling<b/></font>
     * @return propelling propelling
     */
    public int getPropelling() {
        return propelling;
    }

    /**  
     * <br />设置 <font color='#333399'><b>propelling</b></font>
     * @param propelling propelling  
     */
    public void setPropelling(int propelling) {
        this.propelling = propelling;
    }

    /**
     * <br />获取 <font color="red"><b>kpis<b/></font>
     * @return kpis kpis
     */
    public String getKpis() {
        return kpis;
    }

    /**  
     * <br />设置 <font color='#333399'><b>kpis</b></font>
     * @param kpis kpis  
     */
    public void setKpis(String kpis) {
        this.kpis = kpis;
    }
    
    //////////////////getter setter方法//////////////////
    
}
