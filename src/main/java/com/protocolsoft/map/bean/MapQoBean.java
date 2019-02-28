/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapQoBean
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: MapQoBean
 * @Description: 地图Qo
 * @author wangjianmin
 *
 */
public class MapQoBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 开始时间
     */
    private Long startTime;       
    
    /**
     * 结束时间
     */
    private Long endTime;
    
    /**
     * kpiName(比如流量、数据包速率等) 前端传参用
     */
    private String kpiName;
    
    /**
     * 拼SQL列 后端传参用
     */
    private String columnName;
    
    /**
     * 服务端业务Id
     */
    private Integer appBusinessId;
    
    /**
     * IP 前端传参用
     */
    private String ipStr;
    
    /**
     * 粒度 后端传参用
     */
    private Long granularity;
    
    /**
     * 拼列
     */
    private String tempColumnName;
    
    /**
     * 省份或直辖市
     */
    private String regionCn;
    
    /**
     * 观察点ID
     */
    private Integer watchpointId;
    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the kpiName
     */
    public String getKpiName() {
        return kpiName;
    }

    /**
     * @param kpiName the kpiName to set
     */
    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the appBusinessId
     */
    public Integer getAppBusinessId() {
        return appBusinessId;
    }

    /**
     * @param appBusinessId the appBusinessId to set
     */
    public void setAppBusinessId(Integer appBusinessId) {
        this.appBusinessId = appBusinessId;
    }

    /**
     * @return the ipStr
     */
    public String getIpStr() {
        return ipStr;
    }

    /**
     * @param ipStr the ipStr to set
     */
    public void setIpStr(String ipStr) {
        this.ipStr = ipStr;
    }

    /**
     * @return the granularity
     */
    public Long getGranularity() {
        return granularity;
    }

    /**
     * @param granularity the granularity to set
     */
    public void setGranularity(Long granularity) {
        this.granularity = granularity;
    }

    /**
     * @return the tempColumnName
     */
    public String getTempColumnName() {
        return tempColumnName;
    }

    /**
     * @param tempColumnName the tempColumnName to set
     */
    public void setTempColumnName(String tempColumnName) {
        this.tempColumnName = tempColumnName;
    }

    /**
     * @return the regionCn
     */
    public String getRegionCn() {
        return regionCn;
    }

    /**
     * @param regionCn the regionCn to set
     */
    public void setRegionCn(String regionCn) {
        this.regionCn = regionCn;
    }

    /**
     * @return the watchpointId
     */
    public Integer getWatchpointId() {
        return watchpointId;
    }

    /**
     * @param watchpointId the watchpointId to set
     */
    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }
}
