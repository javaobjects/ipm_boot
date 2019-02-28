/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlKpiDataBea.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.bean;

/**
 * @ClassName: UrlKpiDataBea
 * @Description: KPI数据
 * @author www
 *
 */
public class UrlKpiDataBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 访问量
     */
    private long sessionNum;
    
    /**
     * 流量
     */
    private double ethernetTraffic;
    
    /**
     * 响应时延
     */
    private float responseDelay;
    
    /**
     * 加载时延
     */
    private float pageloadDelay;
    
    /**
     * 网络通信时延
     */
    private float rttDurDelay;
    
    /**
     * 400个数
     */
    private int http400Count;
    
    /**
     * 500个数
     */
    private int http500Count;
    
    /**
     * 未响应数量
     */
    private int unresponsiveNum;

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
     * <br />获取 <font color="red"><b>访问量<b/></font>
     * @return sessionNum 访问量
     */
    public long getSessionNum() {
        return sessionNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>访问量</b></font>
     * @param sessionNum 访问量  
     */
    public void setSessionNum(long sessionNum) {
        this.sessionNum = sessionNum;
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
     * <br />获取 <font color="red"><b>响应时延<b/></font>
     * @return responseDelay 响应时延
     */
    public float getResponseDelay() {
        return responseDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>响应时延</b></font>
     * @param responseDelay 响应时延  
     */
    public void setResponseDelay(float responseDelay) {
        this.responseDelay = responseDelay;
    }

    /**
     * <br />获取 <font color="red"><b>加载时延<b/></font>
     * @return pageloadDelay 加载时延
     */
    public float getPageloadDelay() {
        return pageloadDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>加载时延</b></font>
     * @param pageloadDelay 加载时延  
     */
    public void setPageloadDelay(float pageloadDelay) {
        this.pageloadDelay = pageloadDelay;
    }

    /**
     * <br />获取 <font color="red"><b>网络通信时延<b/></font>
     * @return rttDurDelay 网络通信时延
     */
    public float getRttDurDelay() {
        return rttDurDelay;
    }

    /**  
     * <br />设置 <font color='#333399'><b>网络通信时延</b></font>
     * @param rttDurDelay 网络通信时延  
     */
    public void setRttDurDelay(float rttDurDelay) {
        this.rttDurDelay = rttDurDelay;
    }

    /**
     * <br />获取 <font color="red"><b>400个数<b/></font>
     * @return http400Count 400个数
     */
    public int getHttp400Count() {
        return http400Count;
    }

    /**  
     * <br />设置 <font color='#333399'><b>400个数</b></font>
     * @param http400Count 400个数  
     */
    public void setHttp400Count(int http400Count) {
        this.http400Count = http400Count;
    }

    /**
     * <br />获取 <font color="red"><b>500个数<b/></font>
     * @return http500Count 500个数
     */
    public int getHttp500Count() {
        return http500Count;
    }

    /**  
     * <br />设置 <font color='#333399'><b>500个数</b></font>
     * @param http500Count 500个数  
     */
    public void setHttp500Count(int http500Count) {
        this.http500Count = http500Count;
    }

    /**
     * <br />获取 <font color="red"><b>未响应数量<b/></font>
     * @return unresponsiveNum 未响应数量
     */
    public int getUnresponsiveNum() {
        return unresponsiveNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>未响应数量</b></font>
     * @param unresponsiveNum 未响应数量  
     */
    public void setUnresponsiveNum(int unresponsiveNum) {
        this.unresponsiveNum = unresponsiveNum;
    }
}
