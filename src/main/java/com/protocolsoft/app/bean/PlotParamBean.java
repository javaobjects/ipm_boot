/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotParamsBean.java
 *创建人: www    创建时间: 2018年1月10日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: PlotParamsBean
 * @Description: 绘图选项
 * @author www
 *
 */
public class PlotParamBean extends ParamBean {
    
    /**
     * 绘图编号
     */
    private int plotId;
    
    /**
     * 绘图类型编号
     */
    private int plotTypeId;
    
    /**
     * 聚合
     */
    private String calcul;
    
    /**
     * 表名
     */
    private String table;
    
    /**
     * 粒度(目前为0)
     */
    private int step;

    /**
     * <br />获取 <font color="red"><b>绘图编号<b/></font>
     * @return plotId 绘图编号
     */
    public int getPlotId() {
        return plotId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图编号</b></font>
     * @param plotId 绘图编号  
     */
    public void setPlotId(int plotId) {
        this.plotId = plotId;
    }

    /**
     * <br />获取 <font color="red"><b>绘图类型编号<b/></font>
     * @return plotTypeId 绘图类型编号
     */
    public int getPlotTypeId() {
        return plotTypeId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图类型编号</b></font>
     * @param plotTypeId 绘图类型编号  
     */
    public void setPlotTypeId(int plotTypeId) {
        this.plotTypeId = plotTypeId;
    }

    /**
     * <br />获取 <font color="red"><b>聚合<b/></font>
     * @return calcul 聚合
     */
    public String getCalcul() {
        return calcul;
    }

    /**  
     * <br />设置 <font color='#333399'><b>聚合</b></font>
     * @param calcul 聚合  
     */
    public void setCalcul(String calcul) {
        this.calcul = calcul;
    }

    /**
     * <br />获取 <font color="red"><b>表名<b/></font>
     * @return table 表名
     */
    public String getTable() {
        return table;
    }

    /**  
     * <br />设置 <font color='#333399'><b>表名</b></font>
     * @param table 表名  
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * <br />获取 <font color="red"><b>粒度(目前为0)<b/></font>
     * @return step 粒度(目前为0)
     */
    public int getStep() {
        return step;
    }

    /**  
     * <br />设置 <font color='#333399'><b>粒度(目前为0)</b></font>
     * @param step 粒度(目前为0)  
     */
    public void setStep(int step) {
        this.step = step;
    }
}
