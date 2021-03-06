/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppSimpleDataBean.java
 *创建人: www    创建时间: 2018年1月10日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: AppSimpleDataBean
 * @Description: 单点数据
 * @author www
 *
 */
public class PointDataBean {
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 值
     */
    private double value;
    
    /**
     * 绘图名称
     */
    private String plotName;

    /**
     * <br />获取 <font color="red"><b>单位<b/></font>
     * @return unit 单位
     */
    public String getUnit() {
        return unit;
    }

    /**  
     * <br />设置 <font color='#333399'><b>单位</b></font>
     * @param unit 单位  
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * <br />获取 <font color="red"><b>值<b/></font>
     * @return value 值
     */
    public double getValue() {
        return value;
    }

    /**  
     * <br />设置 <font color='#333399'><b>值</b></font>
     * @param value 值  
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * <br />获取 <font color="red"><b>绘图名称<b/></font>
     * @return plotName 绘图名称
     */
    public String getPlotName() {
        return plotName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图名称</b></font>
     * @param plotName 绘图名称  
     */
    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }
}
