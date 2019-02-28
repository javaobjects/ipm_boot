/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppPlotDataBean.java
 *创建人: www    创建时间: 2018年1月10日
 */
package com.protocolsoft.app.bean;

import java.util.List;


/**
 * @ClassName: AppPlotDataBean
 * @Description: 画图
 * @author www
 *
 */
public class PlotDataBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 绘图名称
     */
    private String plotName;
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 绘图类型
     */
    private String type;
    
    /**
     * 数据
     */
    private List<? extends PlotBean> data;

    /**
     * <br />获取 <font color="red"><b>开始时间<b/></font>
     * @return starttime 开始时间
     */
    public long getStarttime() {
        return starttime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>开始时间</b></font>
     * @param starttime 开始时间  
     */
    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    /**
     * <br />获取 <font color="red"><b>结束时间<b/></font>
     * @return endtime 结束时间
     */
    public long getEndtime() {
        return endtime;
    }

    /**  
     * <br />设置 <font color='#333399'><b>结束时间</b></font>
     * @param endtime 结束时间  
     */
    public void setEndtime(long endtime) {
        this.endtime = endtime;
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
     * <br />获取 <font color="red"><b>绘图类型<b/></font>
     * @return type 绘图类型
     */
    public String getType() {
        return type;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图类型</b></font>
     * @param type 绘图类型  
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <br />获取 <font color="red"><b>数据<b/></font>
     * @return data 数据
     */
    public List<? extends PlotBean> getData() {
        return data;
    }
    
    /**  
     * <br />设置 <font color='#333399'><b>数据</b></font>
     * @param data 数据  
     */
    public void setData(List<? extends PlotBean> data) {
        this.data = data;
    }
}
