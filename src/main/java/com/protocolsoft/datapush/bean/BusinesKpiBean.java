/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusinesKpiBean.java
 *创建人: wangjianmin    创建时间: 2018年6月20日
 */
package com.protocolsoft.datapush.bean;

/**
 * @ClassName: BusinesKpiBean
 * @Description: kpi参数实体类
 * @author wangjianmin
 *
 */
public class BusinesKpiBean {
    /**
     * kpi id
     */
    private String kpiId;
     
     /**
      * kpi 名称
      */
    private String name;

    /**
     * <br />获取 <font color="red"><b>kpiId<b/></font>
     * @return kpiId kpiId
     */
    public String getKpiId() {
        return kpiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>kpiId</b></font>
     * @param kpiId kpiId  
     */
    public void setKpiId(String kpiId) {
        this.kpiId = kpiId;
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
     

}
