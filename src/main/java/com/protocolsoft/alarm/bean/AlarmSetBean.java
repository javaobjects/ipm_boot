/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

import java.util.List;

/**
 * @ClassName: AlarmSetBean
 * @Description: 告警设置实体类
 * @author chensq
 *
 */
public class AlarmSetBean {
    /**
     * @Fields id : id
     */
    private long id;
   
    /**
     * @Fields idList : 告警阈值设置id集合
     */
    private String idList;
    
    /**
     * @Fields idsInSql : ids in sql(多个or拼接的sql,组合告警使用)
     */
    private String idsInSql;
     
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
     * @Fields kpitype : kpi类型   1:kpi 2：custom
     */
    private int kpitype; 
   
    /**
     * @Fields kpiId : kpi id
     */
    private long kpiId;
   
    /**
     * @Fields kpiName : kpiName
     */
    private String kpiName;
    
    /**
     * @Fields kpiDisplayName : kpiDisplayName
     */
    private String kpiDisplayName;
    
    /**
     * @Fields levelId : 告警类型id
     */
    private long levelId;
  
    /**
     * @Fields levelList : 阈值级别id集合
     */
    private String levelList;
    
    /**
     * @Fields alarmValueList : 告警阈值集合
     */
    private String alarmValueList;
  
    /**
     * @Fields alarmLevelList : 告警阈值级别集合
     */
    private List<AlarmLevelBean> alarmLevelList;
    
    /**
     * @Fields updateflag : 是否修改 N:阈值未修改 Y：阈值已修改
     */
    private String updateflag;  
     
    /**
     * @Fields pageSetIsGlobalId : 是否为全局设置   0为未选中 1为选中
     */
    private Integer pageSetIsGlobalId; 
    
    //---------high low info------------ 
   
    /**
     * @Fields lowthresh : 低阈值阈值
     */
    private Double lowthresh;
    
    /**
     * @Fields highthresh : 高阈值阈值
     */
    private Double highthresh;
    
    /**
     * @Fields highthreshList : 高
     */
    private String highthreshList;
    
    /**
     * @Fields lowthreshList : 低
     */
    private String lowthreshList;
   
    /**
     * @Fields singleLowThreshUsedFlag : 单独低值启用状态  0为启用，1为不启用
     */
    private int singleLowThreshUsedFlag; 
    
    /**
     * @Fields singleHighThreshUsedFlag : 单独高值启用用状态  0为启用，1为不启用   
     */
    private int singleHighThreshUsedFlag; 
     
    /**
     * @Fields highLowBaselineFlag :  区分告警是  高阈值,低阈值,基线    0高,1低,2基线,3应用可用性,4组合kpi
     */
    private int highLowBaselineFlag=0; 
    
    //---------high low info------------ 

    
    //---------trigger info------------ 
    /**
     * @Fields triggerflag : 阈值trigger对应的高低阈值状态
     */
    private  int triggerflag;
    
    /**
     * @Fields triggerusestatus : 阈值trigger对应的开启状态
     */
    private  int triggerusestatus;
   
    /**
     * @Fields triggerunit : 阈值trigger对应的阈值单位
     */
    private  String triggerunit;
    
    /**
     * @Fields triggertime : 阈值trigger对应的阈值时间
     */
    private  long triggertime;
    //---------trigger info------------
     
    /**
     * @Fields triggerSearchSetIds : 简化查询使用
     */
    private String triggerSearchSetIds;
    
    //---------组合告警使用 ------------
    /**
     * @Fields starttime : 开始时间
     */
    private long starttime;
    /**
     * @Fields endtime : 结束时间
     */
    private long endtime;  
    //---------组合告警使用 ------------
    
    ///////////////////setter getter///////////////////
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * @return the idsInSql
     */
    public String getIdsInSql() {
        return idsInSql;
    }
    /**
     * @param idsInSql the idsInSql to set
     */
    public void setIdsInSql(String idsInSql) {
        this.idsInSql = idsInSql;
    }
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
     * @return the kpiDisplayName
     */
    public String getKpiDisplayName() {
        return kpiDisplayName;
    }
    /**
     * @param kpiDisplayName the kpiDisplayName to set
     */
    public void setKpiDisplayName(String kpiDisplayName) {
        this.kpiDisplayName = kpiDisplayName;
    }
    /**
     * @return the levelId
     */
    public long getLevelId() {
        return levelId;
    }
    /**
     * @param levelId the levelId to set
     */
    public void setLevelId(long levelId) {
        this.levelId = levelId;
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
     * @return the alarmLevelList
     */
    public List<AlarmLevelBean> getAlarmLevelList() {
        return alarmLevelList;
    }
    /**
     * @param alarmLevelList the alarmLevelList to set
     */
    public void setAlarmLevelList(List<AlarmLevelBean> alarmLevelList) {
        this.alarmLevelList = alarmLevelList;
    }
    /**
     * @return the updateflag
     */
    public String getUpdateflag() {
        return updateflag;
    }
    /**
     * @param updateflag the updateflag to set
     */
    public void setUpdateflag(String updateflag) {
        this.updateflag = updateflag;
    }
    /**
     * @return the pageSetIsGlobalId
     */
    public Integer getPageSetIsGlobalId() {
        return pageSetIsGlobalId;
    }
    /**
     * @param pageSetIsGlobalId the pageSetIsGlobalId to set
     */
    public void setPageSetIsGlobalId(Integer pageSetIsGlobalId) {
        this.pageSetIsGlobalId = pageSetIsGlobalId;
    }
    /**
     * @return the lowthresh
     */
    public Double getLowthresh() {
        return lowthresh;
    }
    /**
     * @param lowthresh the lowthresh to set
     */
    public void setLowthresh(Double lowthresh) {
        this.lowthresh = lowthresh;
    }
    /**
     * @return the highthresh
     */
    public Double getHighthresh() {
        return highthresh;
    }
    /**
     * @param highthresh the highthresh to set
     */
    public void setHighthresh(Double highthresh) {
        this.highthresh = highthresh;
    }
    /**
     * @return the highthreshList
     */
    public String getHighthreshList() {
        return highthreshList;
    }
    /**
     * @param highthreshList the highthreshList to set
     */
    public void setHighthreshList(String highthreshList) {
        this.highthreshList = highthreshList;
    }
    /**
     * @return the lowthreshList
     */
    public String getLowthreshList() {
        return lowthreshList;
    }
    /**
     * @param lowthreshList the lowthreshList to set
     */
    public void setLowthreshList(String lowthreshList) {
        this.lowthreshList = lowthreshList;
    }
    /**
     * @return the singleLowThreshUsedFlag
     */
    public int getSingleLowThreshUsedFlag() {
        return singleLowThreshUsedFlag;
    }
    /**
     * @param singleLowThreshUsedFlag the singleLowThreshUsedFlag to set
     */
    public void setSingleLowThreshUsedFlag(int singleLowThreshUsedFlag) {
        this.singleLowThreshUsedFlag = singleLowThreshUsedFlag;
    }
    /**
     * @return the singleHighThreshUsedFlag
     */
    public int getSingleHighThreshUsedFlag() {
        return singleHighThreshUsedFlag;
    }
    /**
     * @param singleHighThreshUsedFlag the singleHighThreshUsedFlag to set
     */
    public void setSingleHighThreshUsedFlag(int singleHighThreshUsedFlag) {
        this.singleHighThreshUsedFlag = singleHighThreshUsedFlag;
    }
    /**
     * @return the highLowBaselineFlag
     */
    public int getHighLowBaselineFlag() {
        return highLowBaselineFlag;
    }
    /**
     * @param highLowBaselineFlag the highLowBaselineFlag to set
     */
    public void setHighLowBaselineFlag(int highLowBaselineFlag) {
        this.highLowBaselineFlag = highLowBaselineFlag;
    }
    /**
     * @return the triggerflag
     */
    public int getTriggerflag() {
        return triggerflag;
    }
    /**
     * @param triggerflag the triggerflag to set
     */
    public void setTriggerflag(int triggerflag) {
        this.triggerflag = triggerflag;
    }
    /**
     * @return the triggerusestatus
     */
    public int getTriggerusestatus() {
        return triggerusestatus;
    }
    /**
     * @param triggerusestatus the triggerusestatus to set
     */
    public void setTriggerusestatus(int triggerusestatus) {
        this.triggerusestatus = triggerusestatus;
    }
    /**
     * @return the triggerunit
     */
    public String getTriggerunit() {
        return triggerunit;
    }
    /**
     * @param triggerunit the triggerunit to set
     */
    public void setTriggerunit(String triggerunit) {
        this.triggerunit = triggerunit;
    }
    /**
     * @return the triggertime
     */
    public long getTriggertime() {
        return triggertime;
    }
    /**
     * @param triggertime the triggertime to set
     */
    public void setTriggertime(long triggertime) {
        this.triggertime = triggertime;
    }
    /**
     * @return the triggerSearchSetIds
     */
    public String getTriggerSearchSetIds() {
        return triggerSearchSetIds;
    }
    /**
     * @param triggerSearchSetIds the triggerSearchSetIds to set
     */
    public void setTriggerSearchSetIds(String triggerSearchSetIds) {
        this.triggerSearchSetIds = triggerSearchSetIds;
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
    
    ///////////////////setter getter///////////////////

}
