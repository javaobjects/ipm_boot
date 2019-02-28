/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotSimpleLineBean.java
 *创建人: www    创建时间: 2018年1月13日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: PlotSimpleLineBean
 * @Description: 单条线数据
 * @author www
 *
 */
public class PlotSimpleBean extends PlotBean {
    
    /**
     * 绘图类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;
    
    /**
     * 值
     */
    private Object value;
    

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
     * <br />获取 <font color="red"><b>值<b/></font>
     * @return value 值
     */
    public Object getValue() {
        return value;
    }

    /**  
     * <br />设置 <font color='#333399'><b>值</b></font>
     * @param value 值  
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
