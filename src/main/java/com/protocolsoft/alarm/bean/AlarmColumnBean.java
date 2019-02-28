/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmColumnBean
 *创建人:chensq    创建时间:2017年12月13日
 */
package com.protocolsoft.alarm.bean;

import java.util.List;

/**
 * @ClassName: AlarmColumnBean
 * @Description: 告警柱图对象
 * @author chensq
 *
 */
public class AlarmColumnBean {
    
    /**
     * @Fields alarmColumnTimeLineBeanList : 时间集合
     */
    private List<AlarmColumnTimeLineBean> alarmColumnTimeLineBeanList;
    
    /**
     * @Fields alarmColumnDataBeanList : 数据集合
     */
    private List<AlarmColumnDataBean> alarmColumnDataBeanList;
    
    /**
     * @return the alarmColumnTimeLineBeanList
     */
    public List<AlarmColumnTimeLineBean> getAlarmColumnTimeLineBeanList() {
        return alarmColumnTimeLineBeanList;
    }
    /**
     * @param alarmColumnTimeLineBeanList the alarmColumnTimeLineBeanList to set
     */
    public void setAlarmColumnTimeLineBeanList(
            List<AlarmColumnTimeLineBean> alarmColumnTimeLineBeanList) {
        this.alarmColumnTimeLineBeanList = alarmColumnTimeLineBeanList;
    }
    /**
     * @return the alarmColumnDataBeanList
     */
    public List<AlarmColumnDataBean> getAlarmColumnDataBeanList() {
        return alarmColumnDataBeanList;
    }
    /**
     * @param alarmColumnDataBeanList the alarmColumnDataBeanList to set
     */
    public void setAlarmColumnDataBeanList(
            List<AlarmColumnDataBean> alarmColumnDataBeanList) {
        this.alarmColumnDataBeanList = alarmColumnDataBeanList;
    }
    
}
