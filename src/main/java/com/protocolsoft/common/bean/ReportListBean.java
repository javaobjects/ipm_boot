/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ReportListBean.java
 *创建人: WWW    创建时间: 2018年7月16日
 */
package com.protocolsoft.common.bean;

/**
 * @ClassName: ReportListBean
 * @Description: 报表KPI列表
 * @author WWW
 *
 */
public class ReportListBean {

    /**
     * 业务编号
     */
    private int id;
    
    /**
     * 业务名称
     */
    private String name;
    
    /**
     * 告警数量
     */
    private long alarmNum;
    
    /**
     * 服务质量 或 服务通信时延
     */
    private double qosOrDelay;
    
    /**
     * 流量
     */
    private double ethernetTraffic;
    
    /**
     * 会话数量
     */
    private double synPkts;
    
    /**
     * 网络丢包率
     */
    private double netPktLostRatio;
    
    /**
     * 带宽占用率
     */
    private double bandWidthRatio;

    /**
     * <br />获取 <font color="red"><b>业务编号<b/></font>
     * @return id 业务编号
     */
    public int getId() {
        return id;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>业务编号</b></font>
     * @param id 业务编号  
     */
    public void setId(int id) {
        this.id = id;
    }
    

    /**
     * <br />获取 <font color="red"><b>业务名称<b/></font>
     * @return name 业务名称
     */
    public String getName() {
        return name;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>业务名称</b></font>
     * @param name 业务名称  
     */
    public void setName(String name) {
        this.name = name;
    }
    

    /**
     * <br />获取 <font color="red"><b>告警数量<b/></font>
     * @return alarmNum 告警数量
     */
    public long getAlarmNum() {
        return alarmNum;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>告警数量</b></font>
     * @param alarmNum 告警数量  
     */
    public void setAlarmNum(long alarmNum) {
        this.alarmNum = alarmNum;
    }
    


    /**
     * <br />获取 <font color="red"><b>服务质量或服务通信时延<b/></font>
     * @return qosOrDelay 服务质量或服务通信时延
     */
    public double getQosOrDelay() {
        return qosOrDelay;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>服务质量或服务通信时延</b></font>
     * @param qosOrDelay 服务质量或服务通信时延  
     */
    public void setQosOrDelay(double qosOrDelay) {
        this.qosOrDelay = qosOrDelay;
    }
    

    /**
     * <br />获取 <font color="red"><b>流量<b/></font>
     * @return ethernetTraffic 流量
     */
    public double getEthernetTraffic() {
        return ethernetTraffic;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>流量</b></font>
     * @param ethernetTraffic 流量  
     */
    public void setEthernetTraffic(double ethernetTraffic) {
        this.ethernetTraffic = ethernetTraffic;
    }
    

    /**
     * <br />获取 <font color="red"><b>会话数量<b/></font>
     * @return synPkts 会话数量
     */
    public double getSynPkts() {
        return synPkts;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>会话数量</b></font>
     * @param synPkts 会话数量  
     */
    public void setSynPkts(double synPkts) {
        this.synPkts = synPkts;
    }
    

    /**
     * <br />获取 <font color="red"><b>网络丢包率<b/></font>
     * @return netPktLostRatio 网络丢包率
     */
    public double getNetPktLostRatio() {
        return netPktLostRatio;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>网络丢包率</b></font>
     * @param netPktLostRatio 网络丢包率  
     */
    public void setNetPktLostRatio(double netPktLostRatio) {
        this.netPktLostRatio = netPktLostRatio;
    }
    

    /**
     * <br />获取 <font color="red"><b>带宽占用率<b/></font>
     * @return bandWidthRatio 带宽占用率
     */
    public double getBandWidthRatio() {
        return bandWidthRatio;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>带宽占用率</b></font>
     * @param bandWidthRatio 带宽占用率  
     */
    public void setBandWidthRatio(double bandWidthRatio) {
        this.bandWidthRatio = bandWidthRatio;
    }
}
