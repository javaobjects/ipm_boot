/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: DBSessionBean.java
 *创建人: www    创建时间: 2018年1月12日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: DBSessionBean
 * @Description: SQL会话列表
 * @author www
 *
 */
public class DBSessionBean extends AppSessionBean {
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 服务端IP
     */
    private String server;
    
    /**
     * 数据库名
     */
    private String dbname;
    
    /**
     * 用户名
     */
    private String user;
    
    /**
     * SQL语句
     */
    private String sqlquery;
    
    /**
     * 返回码
     */
    private String responsecode;
    
    /**
     * 返回消息
     */
    private String responsemsg;
    
    /**
     * SQL处理时延
     */
    private float queryduration;

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
     * <br />获取 <font color="red"><b>服务端IP<b/></font>
     * @return server 服务端IP
     */
    public String getServer() {
        return server;
    }

    /**  
     * <br />设置 <font color='#333399'><b>服务端IP</b></font>
     * @param server 服务端IP  
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * <br />获取 <font color="red"><b>数据库名<b/></font>
     * @return dbname 数据库名
     */
    public String getDbname() {
        return dbname;
    }

    /**  
     * <br />设置 <font color='#333399'><b>数据库名</b></font>
     * @param dbname 数据库名  
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /**
     * <br />获取 <font color="red"><b>用户名<b/></font>
     * @return user 用户名
     */
    public String getUser() {
        return user;
    }

    /**  
     * <br />设置 <font color='#333399'><b>用户名</b></font>
     * @param user 用户名  
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * <br />获取 <font color="red"><b>SQL语句<b/></font>
     * @return sqlquery SQL语句
     */
    public String getSqlquery() {
        return sqlquery;
    }

    /**  
     * <br />设置 <font color='#333399'><b>SQL语句</b></font>
     * @param sqlquery SQL语句  
     */
    public void setSqlquery(String sqlquery) {
        this.sqlquery = sqlquery;
    }

    /**
     * <br />获取 <font color="red"><b>返回码<b/></font>
     * @return responsecode 返回码
     */
    public String getResponsecode() {
        return responsecode;
    }

    /**  
     * <br />设置 <font color='#333399'><b>返回码</b></font>
     * @param responsecode 返回码  
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * <br />获取 <font color="red"><b>返回消息<b/></font>
     * @return responsemsg 返回消息
     */
    public String getResponsemsg() {
        return responsemsg;
    }

    /**  
     * <br />设置 <font color='#333399'><b>返回消息</b></font>
     * @param responsemsg 返回消息  
     */
    public void setResponsemsg(String responsemsg) {
        this.responsemsg = responsemsg;
    }

    /**
     * <br />获取 <font color="red"><b>SQL处理时延<b/></font>
     * @return queryduration SQL处理时延
     */
    public float getQueryduration() {
        return queryduration;
    }

    /**  
     * <br />设置 <font color='#333399'><b>SQL处理时延</b></font>
     * @param queryduration SQL处理时延  
     */
    public void setQueryduration(float queryduration) {
        this.queryduration = queryduration;
    }
}
