/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:PointEntryByAlarmTypeBean
 *创建人:chensq    创建时间:2017年12月6日
 */
package com.protocolsoft.alarm.bean;

import com.protocolsoft.kpi.service.BusiKpiService;

/**
 * @ClassName: PointEntryByAlarmTypeBean
 * @Description: rrd点的数据
 * @author chensq
 *
 */
public class PointEntryByAlarmTypeBean {
    /**
     * @Fields starttime : 开始时间
     */
    private long starttime;
    
    /**
     * @Fields endtime : 结束时间
     */
    private long endtime;
    
    /**
     * @Fields alarmSetBean : 告警设置对象bean
     */
    private AlarmSetBean alarmSetBean;
 
    /**
     * @Fields busiKpiService : rrd对象bean
     */
    private BusiKpiService busiKpiService;
    
    /**
     * @Fields kpiName : kpi名称bean
     */
    private String kpiName;
    
    /**
     * @Fields alarmAlgorithmBean : 算法情况
     */
    private AlarmAlgorithmBean alarmAlgorithmBean;
    
    /**
     * @return the starttime
     */
    public long getStarttime() {
        return starttime;
    }
    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }
    /**
     * @return the endtime
     */
    public long getEndtime() {
        return endtime;
    }
    /**
     * @param endtime the endtime to set
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }
    /**
     * @return the alarmSetBean
     */
    public AlarmSetBean getAlarmSetBean() {
        return alarmSetBean;
    }
    /**
     * @param alarmSetBean the alarmSetBean to set
     */
    public void setAlarmSetBean(AlarmSetBean alarmSetBean) {
        this.alarmSetBean = alarmSetBean;
    }
    /**
     * @return the busiKpiService
     */
    public BusiKpiService getBusiKpiService() {
        return busiKpiService;
    }
    /**
     * @param busiKpiService the busiKpiService to set
     */
    public void setBusiKpiService(BusiKpiService busiKpiService) {
        this.busiKpiService = busiKpiService;
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
     * @return the alarmAlgorithmBean
     */
    public AlarmAlgorithmBean getAlarmAlgorithmBean() {
        return alarmAlgorithmBean;
    }
    /**
     * @param alarmAlgorithmBean the alarmAlgorithmBean to set
     */
    public void setAlarmAlgorithmBean(AlarmAlgorithmBean alarmAlgorithmBean) {
        this.alarmAlgorithmBean = alarmAlgorithmBean;
    }    
    
}
