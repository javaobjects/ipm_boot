/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairVoBean
 *创建人:yan    创建时间:2017-10-17
 */
package com.protocolsoft.jtopo.bean;

import java.io.Serializable;

/**
 * CommpairQo
 * 2017-10-17 上午10:16:38
 * @author yan
 * @version
 * @see
 */
public class CommpairQoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 开始时间 前端传参用
     */
    private Long startTime;
    
    /**
     * 结束时间 前端传参用
     */
    private Long endTime;
    
    /**
     * IP 前端传参用
     */
    private String ip;
    
    /**
     * 端口 前端传参用
     */
    private Integer port;
    
    /**
     * kpi编号
     */
    private int kpiId;
    
    /**
     * 拼SQL列 后端传参用
     */
    private String columnName;
    
    /**
     * 粒度 后端传参用
     */
    private Long granularity;
    
    /**
     * AVG or SUM
     */
    private String calcul;
    
    /**
     * 观察点编号
     */
    private Integer watchpointId;
    
    /**
     * 网络分段
     */
    private String segment;
    
    /**
     * 数量
     */
    private Integer num;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getKpiId() {
        return kpiId;
    }

    public void setKpiId(int kpiId) {
        this.kpiId = kpiId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Long getGranularity() {
        return granularity;
    }

    public void setGranularity(Long granularity) {
        this.granularity = granularity;
    }

    /**
     * <br />获取 <font color="red"><b>AVGorSUM<b/></font>
     * @return calcul AVGorSUM
     */
    public String getCalcul() {
        return calcul;
    }

    /**  
     * <br />设置 <font color='#333399'><b>AVGorSUM</b></font>
     * @param calcul AVGorSUM  
     */
    public void setCalcul(String calcul) {
        this.calcul = calcul;
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
     * <br />获取 <font color="red"><b>数量<b/></font>
     * @return num 数量
     */
    public Integer getNum() {
        return num;
    }

    /**  
     * <br />设置 <font color='#333399'><b>数量</b></font>
     * @param num 数量  
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * <br />获取 <font color="red"><b>网络分段<b/></font>
     * @return segment 网络分段
     */
    public String getSegment() {
        return segment;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网络分段</b></font>
     * @param segment 网络分段  
     */
    public void setSegment(String segment) {
        this.segment = segment;
    }
}
