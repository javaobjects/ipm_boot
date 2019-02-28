/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSysLogBean
 *创建人:chensq    创建时间:2018年4月7日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmSysLogBean
 * @Description: 告警Syslog发送bean
 * @author chensq
 *
 */
public class AlarmSysLogBean {

    /**
     * @Fields start : 开始时间
     */
    private String start;
    
    /**
     * @Fields end : 结束时间
     */
    private String end;
   
    /**
     * @Fields origin : 观察点名称
     */
    private String origin;
   
    /**
     * @Fields busi : 业务名称
     */
    private String busi;
     
    /**
     * @Fields kpi : kpi显示名称
     */
    private String kpi;
     
    /**
     * @Fields level : 告警级别
     */
    private String level;
    
    /**
     * @Fields resp : 响应状态
     */
    private String resp;
    
    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }
    /**
     * @param start the start to set
     */
    public void setStart(String start) {
        this.start = start;
    }
    /**
     * @return the end
     */
    public String getEnd() {
        return end;
    }
    /**
     * @param end the end to set
     */
    public void setEnd(String end) {
        this.end = end;
    }
    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return the busi
     */
    public String getBusi() {
        return busi;
    }
    /**
     * @param busi the busi to set
     */
    public void setBusi(String busi) {
        this.busi = busi;
    }
    /**
     * @return the kpi
     */
    public String getKpi() {
        return kpi;
    }
    /**
     * @param kpi the kpi to set
     */
    public void setKpi(String kpi) {
        this.kpi = kpi;
    }
    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }
    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }
    /**
     * @return the resp
     */
    public String getResp() {
        return resp;
    }
    /**
     * @param resp the resp to set
     */
    public void setResp(String resp) {
        this.resp = resp;
    }
    
    
}
