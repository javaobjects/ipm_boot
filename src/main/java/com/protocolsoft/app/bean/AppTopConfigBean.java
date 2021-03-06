/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTopConfigBean.java
 *创建人: www    创建时间: 2018年1月26日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: AppTopConfigBean
 * @Description: TOP配置
 * @author www
 *
 */
public class AppTopConfigBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 绘图编号
     */
    private int plotId;
    
    /**
     * 汇总字段
     */
    private String summary;
    
    /**
     * 取值字段
     */
    private String column;
    
    /**
     * 条件字段
     */
    private String where;
    
    /**
     * 分组字段
     */
    private String group;
    
    /**
     * 排序字段
     */
    private String order;
    
    /**
     * 条数
     */
    private int limit;

    /**
     * <br />获取 <font color="red"><b>编号<b/></font>
     * @return id 编号
     */
    public int getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>编号</b></font>
     * @param id 编号  
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>模块编号<b/></font>
     * @return moduleId 模块编号
     */
    public int getModuleId() {
        return moduleId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>模块编号</b></font>
     * @param moduleId 模块编号  
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

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
     * <br />获取 <font color="red"><b>汇总字段<b/></font>
     * @return summary 汇总字段
     */
    public String getSummary() {
        return summary;
    }

    /**  
     * <br />设置 <font color='#333399'><b>汇总字段</b></font>
     * @param summary 汇总字段  
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * <br />获取 <font color="red"><b>取值字段<b/></font>
     * @return column 取值字段
     */
    public String getColumn() {
        return column;
    }

    /**  
     * <br />设置 <font color='#333399'><b>取值字段</b></font>
     * @param column 取值字段  
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * <br />获取 <font color="red"><b>条件字段<b/></font>
     * @return where 条件字段
     */
    public String getWhere() {
        return where;
    }

    /**  
     * <br />设置 <font color='#333399'><b>条件字段</b></font>
     * @param where 条件字段  
     */
    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * <br />获取 <font color="red"><b>分组字段<b/></font>
     * @return group 分组字段
     */
    public String getGroup() {
        return group;
    }

    /**  
     * <br />设置 <font color='#333399'><b>分组字段</b></font>
     * @param group 分组字段  
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * <br />获取 <font color="red"><b>排序字段<b/></font>
     * @return order 排序字段
     */
    public String getOrder() {
        return order;
    }

    /**  
     * <br />设置 <font color='#333399'><b>排序字段</b></font>
     * @param order 排序字段  
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * <br />获取 <font color="red"><b>条数<b/></font>
     * @return limit 条数
     */
    public int getLimit() {
        return limit;
    }

    /**  
     * <br />设置 <font color='#333399'><b>条数</b></font>
     * @param limit 条数  
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
