/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmColumnTimeLineBean
 *创建人:chensq    创建时间:2017年12月13日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmColumnTimeLineBean
 * @Description: 告警柱图时间对象
 * @author chensq
 *
 */
public class AlarmColumnTimeLineBean {
   
    /**
     * @Fields longTime : long类型的时间
     */
    private long longTime;
   
    /**
     * @Fields columnstime : 柱图开始时间
     */
    private long columnstime;
    
    /**
     * @Fields columnetime : 柱图结束时间
     */
    private long columnetime;

    /**
     * @return the longTime
     */
    public long getLongTime() {
        return longTime;
    }
    /**
     * @param longTime the longTime to set
     */
    public void setLongTime(long longTime) {
        this.longTime = longTime;
    }
    /**
     * @return the columnstime
     */
    public long getColumnstime() {
        return columnstime;
    }
    /**
     * @param columnstime the columnstime to set
     */
    public void setColumnstime(long columnstime) {
        this.columnstime = columnstime;
    }
    /**
     * @return the columnetime
     */
    public long getColumnetime() {
        return columnetime;
    }
    /**
     * @param columnetime the columnetime to set
     */
    public void setColumnetime(long columnetime) {
        this.columnetime = columnetime;
    }

}
