/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmKpiAlgorithmBean
 *创建人:chensq    创建时间:2017年10月30日
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmKpiAlgorithmBean
 * @Description: 告警算法与kpi关系实体类
 * @author chensq
 *
 */
public class AlarmKpiAlgorithmBean {
    /**
     * @Fields id : id
     */
    private long id;
    
    /**
     * @Fields kpitype : kpitype
     */
    private int kpitype; //1:kpi 2：custom
    
    /**
     * @Fields kpiId : kpiId
     */
    private long kpiId;
    
    /**
     * @Fields algorithmId : algorithmId
     */
    private long algorithmId;
    
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
     * @return the algorithmId
     */
    public long getAlgorithmId() {
        return algorithmId;
    }
    /**
     * @param algorithmId the algorithmId to set
     */
    public void setAlgorithmId(long algorithmId) {
        this.algorithmId = algorithmId;
    }
      
   
}
  