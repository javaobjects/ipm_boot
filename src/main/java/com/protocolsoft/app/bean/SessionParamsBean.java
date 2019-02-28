/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SessionParamsBean.java
 *创建人: www    创建时间: 2018年1月24日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: SessionParamsBean
 * @Description: 拼接会话列表参数
 * @author www
 *
 */
public class SessionParamsBean extends ParamBean {

    /**
     * 表名
     */
    private String table;
    
    /**
     * 查询字段
     */
    private String column;
    
    /**
     * 获取条数
     */
    private int num;
    
    /**
     * 绘图编号
     */
    private int plotId;
    
    /**
     * 负载段
     */
    private String message;
    
    /**
     * 服务器分页
     */
    private Integer serverPageNumber;
    
    /**
     * 分页过滤数量
     */
    private int startNum;

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
     * <br />获取 <font color="red"><b>查询字段<b/></font>
     * @return column 查询字段
     */
    public String getColumn() {
        return column;
    }

    /**  
     * <br />设置 <font color='#333399'><b>查询字段</b></font>
     * @param column 查询字段  
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * <br />获取 <font color="red"><b>获取条数<b/></font>
     * @return num 获取条数
     */
    public int getNum() {
        return num;
    }

    /**  
     * <br />设置 <font color='#333399'><b>获取条数</b></font>
     * @param num 获取条数  
     */
    public void setNum(int num) {
        this.num = num;
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
     * <br />获取 <font color="red"><b>负载段<b/></font>
     * @return message 负载段
     */
    public String getMessage() {
        return message;
    }

    /**  
     * <br />设置 <font color='#333399'><b>负载段</b></font>
     * @param message 负载段  
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <br />获取 <font color="red"><b>服务器分页<b/></font>
     * @return serverPageNumber 服务器分页
     */
    public Integer getServerPageNumber() {
        return serverPageNumber;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务器分页</b></font>
     * @param serverPageNumber 服务器分页  
     */
    public void setServerPageNumber(Integer serverPageNumber) {
        this.serverPageNumber = serverPageNumber;
    }

    /**
     * <br />获取 <font color="red"><b>分页过滤数量<b/></font>
     * @return startNum 分页过滤数量
     */
    public int getStartNum() {
        return startNum;
    }

    /**  
     * <br />设置 <font color='#333399'><b>分页过滤数量</b></font>
     * @param startNum 分页过滤数量  
     */
    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }
}
