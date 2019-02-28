/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlStautsListBean.java
 *创建人: www    创建时间: 2018年3月20日
 */
package com.protocolsoft.url.bean;

/**
 * @ClassName: UrlStautsListBean
 * @Description: 状态信息
 * @author www
 *
 */
public class UrlStateListBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * URL信息
     */
    private String url;
    
    /**
     * 告警数量
     */
    private long alarmLevel;
    
    /**
     * 流量
     */
    private double ethernetTraffic;
    
    /**
     * 会话数量
     */
    private double sessionNum;  
    
    /**
     * 响应时延
     */
    private double responseDelay;
    
    /**
     * 加载时延 
     */
    private double pageloadDelay;
    
    /**
     * 链路时延RTT
     */
    private double rtt;
    
    /**
     * 400错误
     */
    private double url400Count;
    
    /**
     * 500错误
     */
    private double http500Count;
    
    /**
     * HTTP返回码错误率
     */
    private double failRespRatio;

    /**
     * <br />获取 <font color="red"><b>编号<b/></font>
     * @return id 编号
     */
    public int getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>编号</b></font>
     * @param id 编号  
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>名称<b/></font>
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>名称</b></font>
     * @param name 名称  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>URL信息<b/></font>
     * @return url URL信息
     */
    public String getUrl() {
        return url;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URL信息</b></font>
     * @param url URL信息  
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <br />获取 <font color="red"><b>告警数量<b/></font>
     * @return alarmLevel 告警数量
     */
    public long getAlarmLevel() {
        return alarmLevel;
    }

    /**  
     * <br />设置 <font color='#333399'><b>告警数量</b></font>
     * @param alarmLevel 告警数量  
     */
    public void setAlarmLevel(long alarmLevel) {
        this.alarmLevel = alarmLevel;
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
     * @return sessionNum 会话数量
     */
    public double getSessionNum() {
        return sessionNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>会话数量</b></font>
     * @param sessionNum 会话数量  
     */
    public void setSessionNum(double sessionNum) {
        this.sessionNum = sessionNum;
    }

    /**
     * <br />获取 <font color="red"><b>响应时延<b/></font>
     * @return responseDelay 响应时延
     */
    public double getResponseDelay() {
        return responseDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>响应时延</b></font>
     * @param responseDelay 响应时延  
     */
    public void setResponseDelay(double responseDelay) {
        this.responseDelay = responseDelay;
    }

    /**
     * <br />获取 <font color="red"><b>加载时延<b/></font>
     * @return pageloadDelay 加载时延
     */
    public double getPageloadDelay() {
        return pageloadDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>加载时延</b></font>
     * @param pageloadDelay 加载时延  
     */
    public void setPageloadDelay(double pageloadDelay) {
        this.pageloadDelay = pageloadDelay;
    }

    /**
     * <br />获取 <font color="red"><b>链路时延RTT<b/></font>
     * @return rtt 链路时延RTT
     */
    public double getRtt() {
        return rtt;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>链路时延RTT</b></font>
     * @param rtt 链路时延RTT  
     */
    public void setRtt(double rtt) {
        this.rtt = rtt;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>500错误</b></font>
     * @param http500Count 500错误  
     */
    public void setHttp500Count(double http500Count) {
        this.http500Count = http500Count;
    }

    /**
     * <br />获取 <font color="red"><b>400错误<b/></font>
     * @return url400Count 400错误
     */
    public double getUrl400Count() {
        return url400Count;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>400错误</b></font>
     * @param url400Count 400错误  
     */
    public void setUrl400Count(double url400Count) {
        this.url400Count = url400Count;
    }
    

    /**
     * <br />获取 <font color="red"><b>500错误<b/></font>
     * @return http500Count 500错误
     */
    public double getHttp500Count() {
        return http500Count;
    }

    /**  
     * <br />设置 <font color='#333399'><b>500错误</b></font>
     * @param http500Count 500错误  
     */
    public void setHttp500Count(int http500Count) {
        this.http500Count = http500Count;
    }

    /**
     * <br />获取 <font color="red"><b>HTTP返回码错误率<b/></font>
     * @return failRespRatio HTTP返回码错误率
     */
    public double getFailRespRatio() {
        return failRespRatio;
    }

    /**  
     * <br />设置 <font color='#333399'><b>HTTP返回码错误率</b></font>
     * @param failRespRatio HTTP返回码错误率  
     */
    public void setFailRespRatio(double failRespRatio) {
        this.failRespRatio = failRespRatio;
    }
}
