/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetDataBean
 *创建人:chensq    创建时间:2017年11月9日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmSetDataBean
 * @Description: 告警设置数据内容bean
 * @author chensq
 *
 */
public class AlarmSetDataBean {
    /**
     * @Fields id : id
     */
    private Long id;
   
    /**
     * @Fields kpiName : kpiName
     */
    private String kpiName;
    
    /**
     * @Fields nameValue : nameValue
     */
    private String nameValue;
     
    /**
     * @Fields kpiUnit : kpi的单位
     */
    private String kpiUnit;
    
    /**
     * @Fields deviateId : 偏离id
     */
    private Long deviateId;
   
    /**
     * @Fields deviateValue : 偏离value
     */
    private AlarmThreshBean deviateValue;
 
    /**
     * @Fields normalId : 普通id
     */
    private Long normalId;
   
    /**
     * @Fields normalValue : 普通value
     */
    private AlarmThreshBean normalValue;
     
    /**
     * @Fields importantId : 重要id
     */
    private Long importantId;
  
    /**
     * @Fields importantValue : 重要value
     */
    private AlarmThreshBean importantValue;
   
    /**
     * @Fields urgentId : 紧急id
     */
    private Long urgentId;
     
    /**
     * @Fields urgentValue : 紧急value
     */
    private AlarmThreshBean urgentValue;

    /**
     * @Fields singleLowThreshUsedFlag : 单独低阈值使用状态  0为启用，1为不启用
     */
    private int singleLowThreshUsedFlag;  
     
    /**
     * @Fields singleHighThreshUsedFlag : 单独高阈值使用状态 0为启用，1为不启用
     */
    private int singleHighThreshUsedFlag;  
    
    /**
     * @Fields globallowThreshUsedFlag : 全局低阈值使用状态  0为启用，1为不启用
     */
    private int globallowThreshUsedFlag;  
   
    /**
     * @Fields globalHighThreshUsedFlag : 全局低阈值使用状态  0为启用，1为不启用
     */
    private int globalHighThreshUsedFlag;  
    
    /**
     * @Fields alarmValueCompareFlag : 判断全局的值与设置的值是否相同     0为使用单独值，1为使用全局值
     * 情形：一：单独有，全局无     0
     *     二：单独有，全局有     值相同 1 值不同0
     *     三：单独无，全局有     1
     */
    private int alarmValueCompareFlag; 
    
    ///////////////setter getter///////////////
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the nameValue
     */
    public String getNameValue() {
        return nameValue;
    }

    /**
     * @param nameValue the nameValue to set
     */
    public void setNameValue(String nameValue) {
        this.nameValue = nameValue;
    }

    /**
     * @return the kpiUnit
     */
    public String getKpiUnit() {
        return kpiUnit;
    }

    /**
     * @param kpiUnit the kpiUnit to set
     */
    public void setKpiUnit(String kpiUnit) {
        this.kpiUnit = kpiUnit;
    }

    /**
     * @return the deviateId
     */
    public Long getDeviateId() {
        return deviateId;
    }

    /**
     * @param deviateId the deviateId to set
     */
    public void setDeviateId(Long deviateId) {
        this.deviateId = deviateId;
    }

    /**
     * @return the deviateValue
     */
    public AlarmThreshBean getDeviateValue() {
        return deviateValue;
    }

    /**
     * @param deviateValue the deviateValue to set
     */
    public void setDeviateValue(AlarmThreshBean deviateValue) {
        this.deviateValue = deviateValue;
    }

    /**
     * @return the normalId
     */
    public Long getNormalId() {
        return normalId;
    }

    /**
     * @param normalId the normalId to set
     */
    public void setNormalId(Long normalId) {
        this.normalId = normalId;
    }

    /**
     * @return the normalValue
     */
    public AlarmThreshBean getNormalValue() {
        return normalValue;
    }

    /**
     * @param normalValue the normalValue to set
     */
    public void setNormalValue(AlarmThreshBean normalValue) {
        this.normalValue = normalValue;
    }

    /**
     * @return the importantId
     */
    public Long getImportantId() {
        return importantId;
    }

    /**
     * @param importantId the importantId to set
     */
    public void setImportantId(Long importantId) {
        this.importantId = importantId;
    }

    /**
     * @return the importantValue
     */
    public AlarmThreshBean getImportantValue() {
        return importantValue;
    }

    /**
     * @param importantValue the importantValue to set
     */
    public void setImportantValue(AlarmThreshBean importantValue) {
        this.importantValue = importantValue;
    }

    /**
     * @return the urgentId
     */
    public Long getUrgentId() {
        return urgentId;
    }

    /**
     * @param urgentId the urgentId to set
     */
    public void setUrgentId(Long urgentId) {
        this.urgentId = urgentId;
    }

    /**
     * @return the urgentValue
     */
    public AlarmThreshBean getUrgentValue() {
        return urgentValue;
    }

    /**
     * @param urgentValue the urgentValue to set
     */
    public void setUrgentValue(AlarmThreshBean urgentValue) {
        this.urgentValue = urgentValue;
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
     * @return the globallowThreshUsedFlag
     */
    public int getGloballowThreshUsedFlag() {
        return globallowThreshUsedFlag;
    }

    /**
     * @param globallowThreshUsedFlag the globallowThreshUsedFlag to set
     */
    public void setGloballowThreshUsedFlag(int globallowThreshUsedFlag) {
        this.globallowThreshUsedFlag = globallowThreshUsedFlag;
    }

    /**
     * @return the globalHighThreshUsedFlag
     */
    public int getGlobalHighThreshUsedFlag() {
        return globalHighThreshUsedFlag;
    }

    /**
     * @param globalHighThreshUsedFlag the globalHighThreshUsedFlag to set
     */
    public void setGlobalHighThreshUsedFlag(int globalHighThreshUsedFlag) {
        this.globalHighThreshUsedFlag = globalHighThreshUsedFlag;
    }

    /**
     * @return the alarmValueCompareFlag
     */
    public int getAlarmValueCompareFlag() {
        return alarmValueCompareFlag;
    }

    /**
     * @param alarmValueCompareFlag the alarmValueCompareFlag to set
     */
    public void setAlarmValueCompareFlag(int alarmValueCompareFlag) {
        this.alarmValueCompareFlag = alarmValueCompareFlag;
    }
    ///////////////setter getter///////////////

}
