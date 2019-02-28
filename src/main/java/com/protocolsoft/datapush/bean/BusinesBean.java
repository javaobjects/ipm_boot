/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusiProcessingBean.java
 *创建人: wangjianmin    创建时间: 2018年6月15日
 */
package com.protocolsoft.datapush.bean;

/**
 * @ClassName: BusiProcessingBean
 * @Description: 导出rrd数据的参数bean
 * @author wangjianmin
 *
 */
public class BusinesBean {
    /**
     * 业务编号
     */
    private Integer id;
    
    /**
     * 粒度
     */
    private String granularity;
    
    /**
     * 模块类型
     */
    private Integer moduleType;
    
    /**
     * 观察点ID
     */
    private Integer watchpointId;
    
    /**
     * 观察点名称
     */
    private String watchpointName;
    
    /**
     * 业务id
     */
    private Integer busiId;
    
    /**
     * 业务名称
     */
    private String busiName;
    
    /**
     * kpi集合
     */
    private String  kpiIds;
    
    /**
     * kafka主题
     */
    private String topic;
    
    /**
     * kafka ip
     */
    private String brokerIp;
    
    /**
     * kafka 端口
     */
    private String port;
    
    /**
     * 推送方式 kafka
     */
    private String type;
    
    /**
     * 服务端ip
     */
    private String serverIp; 
    
    /**
     * 客户端ip
     */
    private String clientIp;
    
    /**
     * 推送类型
     */
    private String pushType;
    /**
     * <br />获取 <font color="red"><b>granularity<b/></font>
     * @return granularity granularity
     */
    public String getGranularity() {
        return granularity;
    }
    /**  
     * <br />设置 <font color='#333399'><b>granularity</b></font>
     * @param granularity granularity  
     */
    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }
    /**
     * <br />获取 <font color="red"><b>moduleType<b/></font>
     * @return moduleType moduleType
     */
    public Integer getModuleType() {
        return moduleType;
    }
    /**  
     * <br />设置 <font color='#333399'><b>moduleType</b></font>
     * @param moduleType moduleType  
     */
    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }
    /**
     * <br />获取 <font color="red"><b>watchpointId<b/></font>
     * @return watchpointId watchpointId
     */
    public Integer getWatchpointId() {
        return watchpointId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>watchpointId</b></font>
     * @param watchpointId watchpointId  
     */
    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * <br />获取 <font color="red"><b>busiId<b/></font>
     * @return busiId busiId
     */
    public Integer getBusiId() {
        return busiId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>busiId</b></font>
     * @param busiId busiId  
     */
    public void setBusiId(Integer busiId) {
        this.busiId = busiId;
    }
    /**
     * <br />获取 <font color="red"><b>kpiIds<b/></font>
     * @return kpiIds kpiIds
     */
    public String getKpiIds() {
        return kpiIds;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kpiIds</b></font>
     * @param kpiIds kpiIds  
     */
    public void setKpiIds(String kpiIds) {
        this.kpiIds = kpiIds;
    }
    /**
     * <br />获取 <font color="red"><b>topic<b/></font>
     * @return topic topic
     */
    public String getTopic() {
        return topic;
    }
    /**  
     * <br />设置 <font color='#333399'><b>topic</b></font>
     * @param topic topic  
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
    /**
     * <br />获取 <font color="red"><b>type<b/></font>
     * @return type type
     */
    public String getType() {
        return type;
    }
    /**  
     * <br />设置 <font color='#333399'><b>type</b></font>
     * @param type type  
     */
    public void setType(String type) {
        this.type = type;
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
     * <br />获取 <font color="red"><b>busiName<b/></font>
     * @return busiName busiName
     */
    public String getBusiName() {
        return busiName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>busiName</b></font>
     * @param busiName busiName  
     */
    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }
    /**
     * <br />获取 <font color="red"><b>brokerIp<b/></font>
     * @return brokerIp brokerIp
     */
    public String getBrokerIp() {
        return brokerIp;
    }
    /**  
     * <br />设置 <font color='#333399'><b>brokerIp</b></font>
     * @param brokerIp brokerIp  
     */
    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }
    /**
     * <br />获取 <font color="red"><b>port<b/></font>
     * @return port port
     */
    public String getPort() {
        return port;
    }
    /**  
     * <br />设置 <font color='#333399'><b>port</b></font>
     * @param port port  
     */
    public void setPort(String port) {
        this.port = port;
    }
    /**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public Integer getId() {
        return id;
    }
    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * <br />获取 <font color="red"><b>serverIp<b/></font>
     * @return serverIp serverIp
     */
    public String getServerIp() {
        return serverIp;
    }
    /**  
     * <br />设置 <font color='#333399'><b>serverIp</b></font>
     * @param serverIp serverIp  
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
    /**
     * <br />获取 <font color="red"><b>clientIp<b/></font>
     * @return clientIp clientIp
     */
    public String getClientIp() {
        return clientIp;
    }
    /**  
     * <br />设置 <font color='#333399'><b>clientIp</b></font>
     * @param clientIp clientIp  
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    /**
     * <br />获取 <font color="red"><b>pushType<b/></font>
     * @return pushType pushType
     */
    public String getPushType() {
        return pushType;
    }
    /**  
     * <br />设置 <font color='#333399'><b>pushType</b></font>
     * @param pushType pushType  
     */
    public void setPushType(String pushType) {
        this.pushType = pushType;
    }
}
