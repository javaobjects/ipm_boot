/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: TableParamBean.java
 *创建人: www    创建时间: 2018年2月27日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: TableParamBean
 * @Description: 获取表名参数
 * @author www
 *
 */
public class TableParamBean {
    
    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 表格名称
     */
    private String tableName;
    
    /**
     * 表前缀名称
     */
    private String name;

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
     * <br />获取 <font color="red"><b>表格名称<b/></font>
     * @return tableName 表格名称
     */
    public String getTableName() {
        return tableName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>表格名称</b></font>
     * @param tableName 表格名称  
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * <br />获取 <font color="red"><b>表前缀名称<b/></font>
     * @return name 表前缀名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>表前缀名称</b></font>
     * @param name 表前缀名称  
     */
    public void setName(String name) {
        this.name = name;
    }
}
