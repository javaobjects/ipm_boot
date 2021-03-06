/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotBaseBea.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.bean;

import java.util.List;

/**
 * @ClassName: PlotBaseBea
 * @Description: 
 * @author www
 *
 */
public class PlotBean {
    
    /**
     * 编号
     */
    private int id;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * KPI编号
     */
    private int kpiId;
    
    /**
     * 分组编号
     */
    private int groupId;
    
    /**
     * 分组名称
     */
    private String groupName;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 是否支持绘图
     */
    private boolean graph;
    
    /**
     * 是否支持展示数字
     */
    private boolean number;
    
    /**
     * 数据算法
     */
    private String calcul;
    
    /**
     * 绘图类型
     */
    private List<PlotTypeBean> types;

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
     * <br />获取 <font color="red"><b>KPI编号<b/></font>
     * @return kpiId KPI编号
     */
    public int getKpiId() {
        return kpiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>KPI编号</b></font>
     * @param kpiId KPI编号  
     */
    public void setKpiId(int kpiId) {
        this.kpiId = kpiId;
    }

    /**
     * <br />获取 <font color="red"><b>分组编号<b/></font>
     * @return groupId 分组编号
     */
    public int getGroupId() {
        return groupId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>分组编号</b></font>
     * @param groupId 分组编号  
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * <br />获取 <font color="red"><b>分组名称<b/></font>
     * @return groupName 分组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>分组名称</b></font>
     * @param groupName 分组名称  
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * <br />获取 <font color="red"><b>名称<b/></font>
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>名称</b></font>
     * @param name 名称  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>是否支持绘图<b/></font>
     * @return graph 是否支持绘图
     */
    public boolean isGraph() {
        return graph;
    }

    /**  
     * <br />设置 <font color='#333399'><b>是否支持绘图</b></font>
     * @param graph 是否支持绘图  
     */
    public void setGraph(boolean graph) {
        this.graph = graph;
    }

    /**
     * <br />获取 <font color="red"><b>是否支持展示数字<b/></font>
     * @return number 是否支持展示数字
     */
    public boolean isNumber() {
        return number;
    }

    /**  
     * <br />设置 <font color='#333399'><b>是否支持展示数字</b></font>
     * @param number 是否支持展示数字  
     */
    public void setNumber(boolean number) {
        this.number = number;
    }

    /**
     * <br />获取 <font color="red"><b>数据算法<b/></font>
     * @return calcul 数据算法
     */
    public String getCalcul() {
        return calcul;
    }

    /**  
     * <br />设置 <font color='#333399'><b>数据算法</b></font>
     * @param calcul 数据算法  
     */
    public void setCalcul(String calcul) {
        this.calcul = calcul;
    }

    /**
     * <br />获取 <font color="red"><b>绘图类型<b/></font>
     * @return types 绘图类型
     */
    public List<PlotTypeBean> getTypes() {
        return types;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图类型</b></font>
     * @param types 绘图类型  
     */
    public void setTypes(List<PlotTypeBean> types) {
        this.types = types;
    }
}
