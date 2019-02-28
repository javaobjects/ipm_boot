/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: PlotTypeBean.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.bean;

/**
 * @ClassName: PlotTypeBean
 * @Description: 绘图类型
 * @author www
 *
 */
public class PlotTypeBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 类型
     */
    private String type;

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
     * <br />获取 <font color="red"><b>类型<b/></font>
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**  
     * <br />设置 <font color='#333399'><b>类型</b></font>
     * @param type 类型  
     */
    public void setType(String type) {
        this.type = type;
    }
}
