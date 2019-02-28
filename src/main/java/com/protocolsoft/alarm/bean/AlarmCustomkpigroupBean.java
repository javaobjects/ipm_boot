/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomkpigroupBean
 *创建人:chensq    创建时间:2018年4月7日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmCustomkpigroupBean
 * @Description:  自定义组合kpibean
 * @author chensq
 *
 */
public class AlarmCustomkpigroupBean {
    
    /**
     * @Fields id : id
     */
    private long id;
    
    /**
     * @Fields customkpiId : customkpiId
     */
    private long customkpiId;
    
    /**
     * @Fields kpiId : kpiId
     */
    private long kpiId;
    
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
     * @return the customkpiId
     */
    public long getCustomkpiId() {
        return customkpiId;
    }
    /**
     * @param customkpiId the customkpiId to set
     */
    public void setCustomkpiId(long customkpiId) {
        this.customkpiId = customkpiId;
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

}
