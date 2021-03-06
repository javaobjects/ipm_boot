/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLogFindParamBean
 *创建人:chensq    创建时间:2017年11月21日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmLogFindParamBean
 * @Description: 告警日志查找bean
 * @author chensq
 *
 */
public class AlarmLogFindParamBean {
    /**
     * @Fields wheresql : where
     */
    private String wheresql;
    /**
     * @Fields watchpointsql : watchpoint
     */
    private String watchpointsql;
    /**
     * @Fields businesssql : business
     */
    private String businesssql;
    /**
     * @Fields modulesql : module
     */
    private String modulesql;
    /**
     * @Fields alarmLevelsql : 告警级别ids
     */
    private String alarmLevelsql;
    /**
     * @Fields handledflagsql : handledflag
     */
    private String handledflagsql;
    /**
     * @Fields starttimesql : starttime
     */
    private String starttimesql;
    /**
     * @Fields endtimesql : endtime
     */
    private String endtimesql;
    /**
     * @Fields kpitypesql : kpitype
     */
    private String kpitypesql;        
    /**
     * @Fields kpisql : kpis
     */
    private String kpisql;
    /**
     * @Fields kIdSql : kIdSql
     */
    private String kIdSql;
    /**
     * @Fields kpiDisplayNamesql : kpiDisplayName
     */
    private String kpiDisplayNamesql;    
    /**
     * @Fields levelDisplayNamesql : 告警级别显示名
     */
    private String levelDisplayNamesql;
    /**
     * @Fields typeIdsql : 告警类型idsql
     */
    private String typeIdsql;
    /**
     * @Fields typeDisplayNamesql : 告警类型显示名sql
     */
    private String typeDisplayNamesql;
    /**
     * @Fields responseuserIdsql : 响应人id sql
     */
    private String responseuserIdsql;
    /**
     * @Fields responseuserRealnamesql : 响应人真实姓名 sql
     */
    private String responseuserRealnamesql;
    /**
     * @Fields moduleNamezhsql : moduleNamezhsql
     */
    private String moduleNamezhsql;
    /**
     * @Fields watchpointDisplayNamesql : watchpointDisplayNamesql
     */
    private String watchpointDisplayNamesql;
    /**
     * @Fields businessNamesql : businessNamesql
     */
    private String businessNamesql;    
    /**
     * @Fields paramsql1 : paramsql1
     */
    private String paramsql1;
    /**
     * @Fields paramsql2 : paramsql2
     */
    private String paramsql2;
    /**
     * @Fields paramsql3 : paramsql3
     */
    private String paramsql3;
    /**
     * @Fields sortStrsql : sortStr
     */
    private String sortStrsql;
    /**
     * @Fields limitsql : limitsql
     */
    private String limitsql;
    
    /**
     * @return the wheresql
     */
    public String getWheresql() {
        return wheresql;
    }
    /**
     * @param wheresql the wheresql to set
     */
    public void setWheresql(String wheresql) {
        this.wheresql = wheresql;
    }
    /**
     * @return the watchpointsql
     */
    public String getWatchpointsql() {
        return watchpointsql;
    }
    /**
     * @param watchpointsql the watchpointsql to set
     */
    public void setWatchpointsql(String watchpointsql) {
        this.watchpointsql = watchpointsql;
    }
    /**
     * @return the businesssql
     */
    public String getBusinesssql() {
        return businesssql;
    }
    /**
     * @param businesssql the businesssql to set
     */
    public void setBusinesssql(String businesssql) {
        this.businesssql = businesssql;
    }
    /**
     * @return the modulesql
     */
    public String getModulesql() {
        return modulesql;
    }
    /**
     * @param modulesql the modulesql to set
     */
    public void setModulesql(String modulesql) {
        this.modulesql = modulesql;
    }
    /**
     * @return the alarmLevelsql
     */
    public String getAlarmLevelsql() {
        return alarmLevelsql;
    }
    /**
     * @param alarmLevelsql the alarmLevelsql to set
     */
    public void setAlarmLevelsql(String alarmLevelsql) {
        this.alarmLevelsql = alarmLevelsql;
    }
    /**
     * @return the handledflagsql
     */
    public String getHandledflagsql() {
        return handledflagsql;
    }
    /**
     * @param handledflagsql the handledflagsql to set
     */
    public void setHandledflagsql(String handledflagsql) {
        this.handledflagsql = handledflagsql;
    }
    /**
     * @return the starttimesql
     */
    public String getStarttimesql() {
        return starttimesql;
    }
    /**
     * @param starttimesql the starttimesql to set
     */
    public void setStarttimesql(String starttimesql) {
        this.starttimesql = starttimesql;
    }
    /**
     * @return the endtimesql
     */
    public String getEndtimesql() {
        return endtimesql;
    }
    /**
     * @param endtimesql the endtimesql to set
     */
    public void setEndtimesql(String endtimesql) {
        this.endtimesql = endtimesql;
    }
    /**
     * @return the kpitypesql
     */
    public String getKpitypesql() {
        return kpitypesql;
    }
    /**
     * @param kpitypesql the kpitypesql to set
     */
    public void setKpitypesql(String kpitypesql) {
        this.kpitypesql = kpitypesql;
    }
    /**
     * @return the kpisql
     */
    public String getKpisql() {
        return kpisql;
    }
    /**
     * @param kpisql the kpisql to set
     */
    public void setKpisql(String kpisql) {
        this.kpisql = kpisql;
    }    
    /**
     * <br />获取 <font color="red"><b>kIdSql<b/></font>
     * @return kIdSql kIdSql
     */
    public String getkIdSql() {
        return kIdSql;
    }
    /**  
     * <br />设置 <font color='#333399'><b>kIdSql</b></font>
     * @param kIdSql kIdSql  
     */
    public void setkIdSql(String kIdSql) {
        this.kIdSql = kIdSql;
    }
    /**
     * @return the kpiDisplayNamesql
     */
    public String getKpiDisplayNamesql() {
        return kpiDisplayNamesql;
    }
    /**
     * @param kpiDisplayNamesql the kpiDisplayNamesql to set
     */
    public void setKpiDisplayNamesql(String kpiDisplayNamesql) {
        this.kpiDisplayNamesql = kpiDisplayNamesql;
    }
    /**
     * @return the levelDisplayNamesql
     */
    public String getLevelDisplayNamesql() {
        return levelDisplayNamesql;
    }
    /**
     * @param levelDisplayNamesql the levelDisplayNamesql to set
     */
    public void setLevelDisplayNamesql(String levelDisplayNamesql) {
        this.levelDisplayNamesql = levelDisplayNamesql;
    }
    /**
     * @return the typeIdsql
     */
    public String getTypeIdsql() {
        return typeIdsql;
    }
    /**
     * @param typeIdsql the typeIdsql to set
     */
    public void setTypeIdsql(String typeIdsql) {
        this.typeIdsql = typeIdsql;
    }
    /**
     * @return the typeDisplayNamesql
     */
    public String getTypeDisplayNamesql() {
        return typeDisplayNamesql;
    }
    /**
     * @param typeDisplayNamesql the typeDisplayNamesql to set
     */
    public void setTypeDisplayNamesql(String typeDisplayNamesql) {
        this.typeDisplayNamesql = typeDisplayNamesql;
    }
    /**
     * @return the responseuserIdsql
     */
    public String getResponseuserIdsql() {
        return responseuserIdsql;
    }
    /**
     * @param responseuserIdsql the responseuserIdsql to set
     */
    public void setResponseuserIdsql(String responseuserIdsql) {
        this.responseuserIdsql = responseuserIdsql;
    }
    /**
     * @return the responseuserRealnamesql
     */
    public String getResponseuserRealnamesql() {
        return responseuserRealnamesql;
    }
    /**
     * @param responseuserRealnamesql the responseuserRealnamesql to set
     */
    public void setResponseuserRealnamesql(String responseuserRealnamesql) {
        this.responseuserRealnamesql = responseuserRealnamesql;
    }
    /**
     * @return the moduleNamezhsql
     */
    public String getModuleNamezhsql() {
        return moduleNamezhsql;
    }
    /**
     * @param moduleNamezhsql the moduleNamezhsql to set
     */
    public void setModuleNamezhsql(String moduleNamezhsql) {
        this.moduleNamezhsql = moduleNamezhsql;
    }
    /**
     * @return the watchpointDisplayNamesql
     */
    public String getWatchpointDisplayNamesql() {
        return watchpointDisplayNamesql;
    }
    /**
     * @param watchpointDisplayNamesql the watchpointDisplayNamesql to set
     */
    public void setWatchpointDisplayNamesql(String watchpointDisplayNamesql) {
        this.watchpointDisplayNamesql = watchpointDisplayNamesql;
    }
    /**
     * @return the businessNamesql
     */
    public String getBusinessNamesql() {
        return businessNamesql;
    }
    /**
     * @param businessNamesql the businessNamesql to set
     */
    public void setBusinessNamesql(String businessNamesql) {
        this.businessNamesql = businessNamesql;
    }
    /**
     * @return the paramsql1
     */
    public String getParamsql1() {
        return paramsql1;
    }
    /**
     * @param paramsql1 the paramsql1 to set
     */
    public void setParamsql1(String paramsql1) {
        this.paramsql1 = paramsql1;
    }
    /**
     * @return the paramsql2
     */
    public String getParamsql2() {
        return paramsql2;
    }
    /**
     * @param paramsql2 the paramsql2 to set
     */
    public void setParamsql2(String paramsql2) {
        this.paramsql2 = paramsql2;
    }
    /**
     * @return the paramsql3
     */
    public String getParamsql3() {
        return paramsql3;
    }
    /**
     * @param paramsql3 the paramsql3 to set
     */
    public void setParamsql3(String paramsql3) {
        this.paramsql3 = paramsql3;
    }
    /**
     * @return the sortStrsql
     */
    public String getSortStrsql() {
        return sortStrsql;
    }
    /**
     * @param sortStrsql the sortStrsql to set
     */
    public void setSortStrsql(String sortStrsql) {
        this.sortStrsql = sortStrsql;
    }
    /**
     * @return the limitsql
     */
    public String getLimitsql() {
        return limitsql;
    }
    /**
     * @param limitsql the limitsql to set
     */
    public void setLimitsql(String limitsql) {
        this.limitsql = limitsql;
    }
    
    
}
