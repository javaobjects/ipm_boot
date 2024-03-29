/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiThreadBean
 *创建人:chensq    创建时间:2018年4月16日
 */
package com.protocolsoft.alarm.bean;

import java.util.Map;

/**
 * @ClassName: AlarmCustomUnionKpiThreadBean
 * @Description: 组合告警使用线程bean
 * @author chensq
 *
 */
public class AlarmCustomUnionKpiThreadBean {
    //---------------获取的字段-------------    
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;
    
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
     
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
  
    /**
     * @Fields kpitype : kpi类型(组合告警的kpitype)
     */
    private int kpitype;  
  
    /**
     * @Fields kpiId : kpi id(组合告警的kpi_id)
     */
    private long kpiId;
    
    /**
     * @Fields idList : 告警阈值设置id集合
     */
    private String idList;
   
    /**
     * @Fields levelList : 阈值级别id集合
     */
    private String levelList;
     
    /**
     * @Fields alarmValueList : kpi id(ipm_alarm_set中的kpi_id,被组合的kpi_id)
     */
    private String alarmValueList;
    //---------------获取的字段-------------

    //---------------运算使用的字段-------------
     
    /**
     * @Fields maxLevel : 最大级别
     */
    private int maxLevel;
   
    /**
     * @Fields beforeCount : 聚合数量 
     */
    private int beforeCount;
    
    /**
     * @Fields kpiIdCountMap : 已经计算的kpi_id集合
     */
    private Map<String, Integer> kpiIdCountMap;
    //---------------运算使用的字段-------------

    //---------------插入使用字段-------------
    
    /**
     * @Fields alarmsetId : 告警设置id
     */
    private long alarmsetId; //ipm_alarm_set表的Id等同
    
    /**
     * @Fields starttime : 开始时间
     */
    private long starttime;
     
    /**
     * @Fields endtime : 结束时间
     */
    private long endtime;
    //---------------插入使用字段-------------
    /**
     * @return the watchpointId
     */
    public long getWatchpointId() {
        return watchpointId;
    }
    /**
     * @param watchpointId the watchpointId to set
     */
    public void setWatchpointId(long watchpointId) {
        this.watchpointId = watchpointId;
    }
    /**
     * @return the moduleId
     */
    public long getModuleId() {
        return moduleId;
    }
    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }
    /**
     * @return the businessId
     */
    public long getBusinessId() {
        return businessId;
    }
    /**
     * @param businessId the businessId to set
     */
    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }
    /**
     * @return the kpitype
     */
    public int getKpitype() {
        return kpitype;
    }
    /**
     * @param kpitype the kpitype to set
     */
    public void setKpitype(int kpitype) {
        this.kpitype = kpitype;
    }
    /**
     * @return the kpiId
     */
    public long getKpiId() {
        return kpiId;
    }
    /**
     * @param kpiId the kpiId to set
     */
    public void setKpiId(long kpiId) {
        this.kpiId = kpiId;
    }
    /**
     * @return the idList
     */
    public String getIdList() {
        return idList;
    }
    /**
     * @param idList the idList to set
     */
    public void setIdList(String idList) {
        this.idList = idList;
    }
    /**
     * @return the levelList
     */
    public String getLevelList() {
        return levelList;
    }
    /**
     * @param levelList the levelList to set
     */
    public void setLevelList(String levelList) {
        this.levelList = levelList;
    }
    /**
     * @return the alarmValueList
     */
    public String getAlarmValueList() {
        return alarmValueList;
    }
    /**
     * @param alarmValueList the alarmValueList to set
     */
    public void setAlarmValueList(String alarmValueList) {
        this.alarmValueList = alarmValueList;
    }
    /**
     * @return the maxLevel
     */
    public int getMaxLevel() {
        return maxLevel;
    }
    /**
     * @param maxLevel the maxLevel to set
     */
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
    /**
     * @return the beforeCount
     */
    public int getBeforeCount() {
        return beforeCount;
    }
    /**
     * @param beforeCount the beforeCount to set
     */
    public void setBeforeCount(int beforeCount) {
        this.beforeCount = beforeCount;
    }
    /**
     * @return the kpiIdCountMap
     */
    public Map<String, Integer> getKpiIdCountMap() {
        return kpiIdCountMap;
    }
    /**
     * @param kpiIdCountMap the kpiIdCountMap to set
     */
    public void setKpiIdCountMap(Map<String, Integer> kpiIdCountMap) {
        this.kpiIdCountMap = kpiIdCountMap;
    }
    /**
     * @return the alarmsetId
     */
    public long getAlarmsetId() {
        return alarmsetId;
    }
    /**
     * @param alarmsetId the alarmsetId to set
     */
    public void setAlarmsetId(long alarmsetId) {
        this.alarmsetId = alarmsetId;
    }
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
       
}
