/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomkpiBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

 
/**
 * @ClassName: AlarmCustomkpiBean
 * @Description: custom union 告警自定义kpi实体类
 * @author chensq
 *
 */
public class AlarmCustomkpiBean {    
    
    /**
     * @Fields id : id
     */
    private long id;
  
    /**
     * @Fields moduleId : 模块id
     */
    private long moduleId;
   
    /**
     * @Fields businessId : 业务id
     */
    private long businessId;
    
    /**
     * @Fields namezh : 自定义kpi中文名
     */
    private String namezh;
     
    /**
     * @Fields nameen : 自定义kpi英文名
     */
    private String nameen;    
    
    /**
     * @Fields groupKpis : 用户选择的kpis
     */
    private String groupKpis;
    
    /**
     * @Fields groupKpiName : 组合的kpi name
     */
    private String groupKpiName;
    
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
     * @return the namezh
     */
    public String getNamezh() {
        return namezh;
    }
    /**
     * @param namezh the namezh to set
     */
    public void setNamezh(String namezh) {
        this.namezh = namezh;
    }
    /**
     * @return the nameen
     */
    public String getNameen() {
        return nameen;
    }
    /**
     * @param nameen the nameen to set
     */
    public void setNameen(String nameen) {
        this.nameen = nameen;
    }
    /**
     * @return the groupKpis
     */
    public String getGroupKpis() {
        return groupKpis;
    }
    /**
     * @param groupKpis the groupKpis to set
     */
    public void setGroupKpis(String groupKpis) {
        this.groupKpis = groupKpis;
    }
    /**
     * @return the groupKpiName
     */
    public String getGroupKpiName() {
        return groupKpiName;
    }
    /**
     * @param groupKpiName the groupKpiName to set
     */
    public void setGroupKpiName(String groupKpiName) {
        this.groupKpiName = groupKpiName;
    }
        
}
