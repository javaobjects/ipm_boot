/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

import com.protocolsoft.common.bean.CenterParamBean;

/**
 * @ClassName: AlarmLogBean
 * @Description: 告警记录实体类
 * @author chensq
 *
 */
public class AlarmLogBean extends CenterParamBean {  
    /**
     * @Fields id : id
     */
    private long id;
    /**
     * @Fields watchpointId : 观察点id
     */
    private long watchpointId;
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
    /**
     * @Fields handledflag : 是否响应
     */
    private String handledflag; //N:未响应 Y：响应
    /**
     * @Fields finishflag : 是否结束
     */
    private String finishflag; //N:未完成 Y：已完成
    /**
     * @Fields responseuser : 响应人
     */
    private long responseuser;
    /**
     * @Fields responsetime : 响应时间
     */
    private long responsetime;
    
    //-----------------alarmSet的字段-start------------------
    /**
     * @Fields watchpointName : 观察点显示名
     */
    private String watchpointName;
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
    /**
     * @Fields moduleName : 模块名称
     */
    private String moduleName;
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
    /**
     * @Fields businessName : 业务name
     */
    private String businessName;
    /**
     * @Fields kpitype : 告警类型
     */
    private int kpitype;
    /**
     * @Fields alarmKpiId : alarm_kpi_id
     */
    private long alarmKpiId;
    //-----------------alarmSet的字段-end------------------
    
    //-----------------kpis的字段-start------------------
    /**
     * @Fields kpisId : kpi的id
     */
    private long kpisId;
    /**
     * @Fields kId : ipm_kpis表的id
     */
    private long kId;
    /**
     * @Fields kpisName : kpi的name
     */
    private String kpisName;
    /**
     * @Fields kpisDisplayName : kpi的displayName
     */
    private String kpisDisplayName;    
    /**
     * @Fields kpisUnit : kpi的unit
     */
    private String kpisUnit; 
    /**
     * @Fields kpisCause : kpi的cause
     */
    private String cause;
    //-----------------kpis的字段-end------------------
    
    //-----------------alarm-type-level-start----------------
    /**
     * @Fields alarmTypeId : alarmTypeId
     */
    private long alarmTypeId;
     
    /**
     * @Fields alarmTypeEn : alarmTypeEn
     */
    private String alarmTypeEn;
    
    /**
     * @Fields alarmTypeZh : alarmTypeZh
     */
    private String alarmTypeZh;
   
    /**
     * @Fields alarmLevelId : alarmLevelId
     */
    private long alarmLevelId;
     
    /**
     * @Fields alarmLevelEn : alarmLevelEn
     */
    private String alarmLevelEn;
 
    /**
     * @Fields alarmLevelZh : alarmLevelZh
     */
    private String alarmLevelZh;
    
    /**
     * @Fields triggerflag : 上下阈值标识 0高阈值  1低阈值 2智能告警阈值  3应用可用性  4组合告警
     */
    private long triggerflag;
     
    /**
     * @Fields triggerflagStr : 告警阈值类型str
     */
    private String triggerflagStr;
    //-----------------alarm-type-level-end------------------
    

    //-----------------其他字段-start------------------
    
    /**
     * @Fields responseuserDisplayName : 响应人姓名
     */
    private String responseuserDisplayName;
  
    /**
     * @Fields alarmLevelIds : 告警级别ids
     */
    private String alarmLevelIds;
        
    /**
     * @Fields count : 告警数量
     */
    private long count;
    
    /**
     * @Fields ids : ids 批量修改时使用
     */
    private String ids;
     
    /**
     * @Fields starttimeStr : 显示时间
     */
    private String starttimeStr;
    
    /**
     * @Fields endtimeStr : 结束时间
     */
    private String endtimeStr;
    
    /**
     * @Fields sortStr : 排序标识
     */
    private String sortStr;
    
    /**
     * @Fields limitNum : 上线标识
     */
    private long limitNum;
     
    /**
     * @Fields healthColumn : 健康图段数
     */
    private int healthColumn;
    /**
     * @Fields columnNum : 柱图数量
     */
    private Integer columnNum;
    //-----------------其他字段-end------------------

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
    /**
     * @return the handledflag
     */
    public String getHandledflag() {
        return handledflag;
    }
    /**
     * @param handledflag the handledflag to set
     */
    public void setHandledflag(String handledflag) {
        this.handledflag = handledflag;
    }
    /**
     * @return the finishflag
     */
    public String getFinishflag() {
        return finishflag;
    }
    /**
     * @param finishflag the finishflag to set
     */
    public void setFinishflag(String finishflag) {
        this.finishflag = finishflag;
    }
    /**
     * @return the responseuser
     */
    public long getResponseuser() {
        return responseuser;
    }
    /**
     * @param responseuser the responseuser to set
     */
    public void setResponseuser(long responseuser) {
        this.responseuser = responseuser;
    }
    /**
     * @return the responsetime
     */
    public long getResponsetime() {
        return responsetime;
    }
    /**
     * @param responsetime the responsetime to set
     */
    public void setResponsetime(long responsetime) {
        this.responsetime = responsetime;
    }
    /**
     * @return the watchpointName
     */
    public String getWatchpointName() {
        return watchpointName;
    }
    /**
     * @param watchpointName the watchpointName to set
     */
    public void setWatchpointName(String watchpointName) {
        this.watchpointName = watchpointName;
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
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }
    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }
    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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
     * @return the alarmKpiId
     */
    public long getAlarmKpiId() {
        return alarmKpiId;
    }
    /**
     * @param alarmKpiId the alarmKpiId to set
     */
    public void setAlarmKpiId(long alarmKpiId) {
        this.alarmKpiId = alarmKpiId;
    }
    /**
     * @return the kpisId
     */
    public long getKpisId() {
        return kpisId;
    }
    /**
     * @param kpisId the kpisId to set
     */
    public void setKpisId(long kpisId) {
        this.kpisId = kpisId;
    }
    /**
     * <br />获取 <font color="red"><b>kId<b/></font>
     * @return kId kId
     */
    public long getkId() {
        return kId;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kId</b></font>
     * @param kId kId  
     */
    public void setkId(long kId) {
        this.kId = kId;
    }
    /**
     * @return the kpisName
     */
    public String getKpisName() {
        return kpisName;
    }
    /**
     * @param kpisName the kpisName to set
     */
    public void setKpisName(String kpisName) {
        this.kpisName = kpisName;
    }
    /**
     * @return the kpisDisplayName
     */
    public String getKpisDisplayName() {
        return kpisDisplayName;
    }
    /**
     * @param kpisDisplayName the kpisDisplayName to set
     */
    public void setKpisDisplayName(String kpisDisplayName) {
        this.kpisDisplayName = kpisDisplayName;
    }
    /**
     * @return the kpisUnit
     */
    public String getKpisUnit() {
        return kpisUnit;
    }
    /**
     * @param kpisUnit the kpisUnit to set
     */
    public void setKpisUnit(String kpisUnit) {
        this.kpisUnit = kpisUnit;
    }
    /**
     * <br />获取 <font color="red"><b>cause<b/></font>
     * @return cause cause
     */
    public String getCause() {
        return cause;
    }
    /**  
     * <br />设置 <font color='#333399'><b>cause</b></font>
     * @param cause cause  
     */
    public void setCause(String cause) {
        this.cause = cause;
    }
    /**
     * @return the alarmTypeId
     */
    public long getAlarmTypeId() {
        return alarmTypeId;
    }
    /**
     * @param alarmTypeId the alarmTypeId to set
     */
    public void setAlarmTypeId(long alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }
    /**
     * @return the alarmTypeEn
     */
    public String getAlarmTypeEn() {
        return alarmTypeEn;
    }
    /**
     * @param alarmTypeEn the alarmTypeEn to set
     */
    public void setAlarmTypeEn(String alarmTypeEn) {
        this.alarmTypeEn = alarmTypeEn;
    }
    /**
     * @return the alarmTypeZh
     */
    public String getAlarmTypeZh() {
        return alarmTypeZh;
    }
    /**
     * @param alarmTypeZh the alarmTypeZh to set
     */
    public void setAlarmTypeZh(String alarmTypeZh) {
        this.alarmTypeZh = alarmTypeZh;
    }
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
     * @return the alarmLevelEn
     */
    public String getAlarmLevelEn() {
        return alarmLevelEn;
    }
    /**
     * @param alarmLevelEn the alarmLevelEn to set
     */
    public void setAlarmLevelEn(String alarmLevelEn) {
        this.alarmLevelEn = alarmLevelEn;
    }
    /**
     * @return the alarmLevelZh
     */
    public String getAlarmLevelZh() {
        return alarmLevelZh;
    }
    /**
     * @param alarmLevelZh the alarmLevelZh to set
     */
    public void setAlarmLevelZh(String alarmLevelZh) {
        this.alarmLevelZh = alarmLevelZh;
    }
    /**
     * @return the triggerflag
     */
    public long getTriggerflag() {
        return triggerflag;
    }
    /**
     * @param triggerflag the triggerflag to set
     */
    public void setTriggerflag(long triggerflag) {
        this.triggerflag = triggerflag;
    }
    /**
     * @return the triggerflagStr
     */
    public String getTriggerflagStr() {
        return triggerflagStr;
    }
    /**
     * @param triggerflagStr the triggerflagStr to set
     */
    public void setTriggerflagStr(String triggerflagStr) {
        this.triggerflagStr = triggerflagStr;
    }
    /**
     * @return the responseuserDisplayName
     */
    public String getResponseuserDisplayName() {
        return responseuserDisplayName;
    }
    /**
     * @param responseuserDisplayName the responseuserDisplayName to set
     */
    public void setResponseuserDisplayName(String responseuserDisplayName) {
        this.responseuserDisplayName = responseuserDisplayName;
    }
    /**
     * @return the alarmLevelIds
     */
    public String getAlarmLevelIds() {
        return alarmLevelIds;
    }
    /**
     * @param alarmLevelIds the alarmLevelIds to set
     */
    public void setAlarmLevelIds(String alarmLevelIds) {
        this.alarmLevelIds = alarmLevelIds;
    }
    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }
    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count = count;
    }
    /**
     * @return the ids
     */
    public String getIds() {
        return ids;
    }
    /**
     * @param ids the ids to set
     */
    public void setIds(String ids) {
        this.ids = ids;
    }
    /**
     * @return the starttimeStr
     */
    public String getStarttimeStr() {
        return starttimeStr;
    }
    /**
     * @param starttimeStr the starttimeStr to set
     */
    public void setStarttimeStr(String starttimeStr) {
        this.starttimeStr = starttimeStr;
    }
    /**
     * @return the endtimeStr
     */
    public String getEndtimeStr() {
        return endtimeStr;
    }
    /**
     * @param endtimeStr the endtimeStr to set
     */
    public void setEndtimeStr(String endtimeStr) {
        this.endtimeStr = endtimeStr;
    }
    /**
     * @return the sortStr
     */
    public String getSortStr() {
        return sortStr;
    }
    /**
     * @param sortStr the sortStr to set
     */
    public void setSortStr(String sortStr) {
        this.sortStr = sortStr;
    }
    /**
     * @return the limitNum
     */
    public long getLimitNum() {
        return limitNum;
    }
    /**
     * @param limitNum the limitNum to set
     */
    public void setLimitNum(long limitNum) {
        this.limitNum = limitNum;
    }
    /**
     * @return the healthColumn
     */
    public int getHealthColumn() {
        return healthColumn;
    }
    /**
     * @param healthColumn the healthColumn to set
     */
    public void setHealthColumn(int healthColumn) {
        this.healthColumn = healthColumn;
    }
    /**
     * <br />获取 <font color="red"><b>columnNum<b/></font>
     * @return columnNum columnNum
     */
    public Integer getColumnNum() {
        return columnNum;
    }
    /**  
     * <br />设置 <font color='#333399'><b>columnNum</b></font>
     * @param columnNum columnNum  
     */
    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }
    
}
