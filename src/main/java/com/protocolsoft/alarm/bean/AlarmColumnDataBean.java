/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmColumnDataBean
 *创建人:chensq    创建时间:2017年12月13日
 */
package com.protocolsoft.alarm.bean;

import java.util.List;

/**
 * @ClassName: AlarmColumnDataBean
 * @Description: 告警柱图数据对象
 * @author chensq
 *
 */
public class AlarmColumnDataBean {
    
    /**
     * @Fields alarmLevelId : 告警级别
     */
    private long alarmLevelId;
    
    /**
     * @Fields alarmLevelName : 级别名称
     */
    private String alarmLevelName;
    
    /**
     * @Fields alarmColumnDetailBeanList : 告警柱图数据对象
     */
    private List<AlarmColumnDetailBean>  alarmColumnDetailBeanList;
    
    /**
     * @return the alarmLevelId
     */
    public long getAlarmLevelId() {
        return alarmLevelId;
    }
    /**
     * @param alarmLevelId the alarmLevelId to set
     */
    public void setAlarmLevelId(long alarmLevelId) {
        this.alarmLevelId = alarmLevelId;
    }
    /**
     * @return the alarmLevelName
     */
    public String getAlarmLevelName() {
        return alarmLevelName;
    }
    /**
     * @param alarmLevelName the alarmLevelName to set
     */
    public void setAlarmLevelName(String alarmLevelName) {
        this.alarmLevelName = alarmLevelName;
    }
    /**
     * @return the alarmColumnDetailBeanList
     */
    public List<AlarmColumnDetailBean> getAlarmColumnDetailBeanList() {
        return alarmColumnDetailBeanList;
    }
    /**
     * @param alarmColumnDetailBeanList the alarmColumnDetailBeanList to set
     */
    public void setAlarmColumnDetailBeanList(
            List<AlarmColumnDetailBean> alarmColumnDetailBeanList) {
        this.alarmColumnDetailBeanList = alarmColumnDetailBeanList;
    }
    
}
