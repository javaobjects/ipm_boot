/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: ReportAlarmBean.java
 *创建人: wangjianmin    创建时间: 2018年7月16日
 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: ReportAlarmBean
 * @Description: 报表告警类
 * @author wangjianmin
 *
 */
public class ReportAlarmBean {
    
   /**
    * 模块id
    */
    private long moduleId;
    
    /**
     * 观察点id
     */
    private long watchpointId;
    
    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime; 
    
    /**
     * 名称
     */
    private  String name;
    
    /**
     * 业务id
     */
    private long businessId;
    
    /**
     * 自定义柱子显示数
     */
    private int spot;
    
    /**
     * 环比开始时间
     */
    private long ringRatioStart;
    /**
     * 环比结束时间
     */
    private long ringRatioEnd;
    
    /**
     * 环比名称
     */
    private String ringRatioName;
    
    /**
     * 报表类型 2 日报  3 周报  4月报
     */
    private int type;
    

    /**
     * <br />获取 <font color="red"><b>moduleId<b/></font>
     * @return moduleId moduleId
     */
    public long getModuleId() {
        return moduleId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>moduleId</b></font>
     * @param moduleId moduleId  
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * <br />获取 <font color="red"><b>watchpointId<b/></font>
     * @return watchpointId watchpointId
     */
    public long getWatchpointId() {
        return watchpointId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>watchpointId</b></font>
     * @param watchpointId watchpointId  
     */
    public void setWatchpointId(long watchpointId) {
        this.watchpointId = watchpointId;
    }

    /**
     * <br />获取 <font color="red"><b>starttime<b/></font>
     * @return starttime starttime
     */
    public long getStarttime() {
        return starttime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>starttime</b></font>
     * @param starttime starttime  
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    /**
     * <br />获取 <font color="red"><b>endtime<b/></font>
     * @return endtime endtime
     */
    public long getEndtime() {
        return endtime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>endtime</b></font>
     * @param endtime endtime  
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    /**
     * <br />获取 <font color="red"><b>businessId<b/></font>
     * @return businessId businessId
     */
    public long getBusinessId() {
        return businessId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>businessId</b></font>
     * @param businessId businessId  
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    /**
     * <br />获取 <font color="red"><b>spot<b/></font>
     * @return spot spot
     */
    public int getSpot() {
        return spot;
    }

    /**  
     * <br />设置 <font color='#333399'><b>spot</b></font>
     * @param spot spot  
     */
    public void setSpot(int spot) {
        this.spot = spot;
    }

    /**
     * <br />获取 <font color="red"><b>ringRatioStart<b/></font>
     * @return ringRatioStart ringRatioStart
     */
    public long getRingRatioStart() {
        return ringRatioStart;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ringRatioStart</b></font>
     * @param ringRatioStart ringRatioStart  
     */
    public void setRingRatioStart(long ringRatioStart) {
        this.ringRatioStart = ringRatioStart;
    }

    /**
     * <br />获取 <font color="red"><b>ringRatioEnd<b/></font>
     * @return ringRatioEnd ringRatioEnd
     */
    public long getRingRatioEnd() {
        return ringRatioEnd;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ringRatioEnd</b></font>
     * @param ringRatioEnd ringRatioEnd  
     */
    public void setRingRatioEnd(long ringRatioEnd) {
        this.ringRatioEnd = ringRatioEnd;
    }

    @Override
    public String toString() {
        return "ReportAlarmBean [moduleId=" + moduleId + ", watchpointId="
                + watchpointId + ", starttime=" + starttime + ", endtime="
                + endtime + ", businessId=" + businessId + ", spot=" + spot
                + ", ringRatioStart=" + ringRatioStart + ", ringRatioEnd="
                + ringRatioEnd + "]";
    }

    /**
     * <br />获取 <font color="red"><b>name<b/></font>
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>name</b></font>
     * @param name name  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>ringRatioName<b/></font>
     * @return ringRatioName ringRatioName
     */
    public String getRingRatioName() {
        return ringRatioName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>ringRatioName</b></font>
     * @param ringRatioName ringRatioName  
     */
    public void setRingRatioName(String ringRatioName) {
        this.ringRatioName = ringRatioName;
    }

    /**
     * <br />获取 <font color="red"><b>type<b/></font>
     * @return type type
     */
    public int getType() {
        return type;
    }

    /**  
     * <br />设置 <font color='#333399'><b>type</b></font>
     * @param type type  
     */
    public void setType(int type) {
        this.type = type;
    }

    
}
