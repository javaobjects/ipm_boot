/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmDataBean.java
 *创建人: www    创建时间: 2018年1月11日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: AlarmDataBean
 * @Description: 告警
 * @author www
 *
 */
public class AlarmDataBean extends PointDataBean {
    
    /**
     * 告警级别
     */
    private long alarmLevelId;
    
    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;

    /**
     * <br />获取 <font color="red"><b>告警级别<b/></font>
     * @return alarmLevelId 告警级别
     */
    public long getAlarmLevelId() {
        return alarmLevelId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>告警级别</b></font>
     * @param alarmLevelId 告警级别  
     */
    public void setAlarmLevelId(long alarmLevelId) {
        this.alarmLevelId = alarmLevelId;
    }

    /**
     * <br />获取 <font color="red"><b>开始时间<b/></font>
     * @return starttime 开始时间
     */
    public long getStarttime() {
        return starttime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>开始时间</b></font>
     * @param starttime 开始时间  
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    /**
     * <br />获取 <font color="red"><b>结束时间<b/></font>
     * @return endtime 结束时间
     */
    public long getEndtime() {
        return endtime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>结束时间</b></font>
     * @param endtime 结束时间  
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
}
