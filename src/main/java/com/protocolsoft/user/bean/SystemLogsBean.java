/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SystemLogsBean.java
 *创建人: www    创建时间: 2017年12月20日
 */
package com.protocolsoft.user.bean;

/**
 * @ClassName: SystemLogsBean
 * @Description: 系统日志
 * @author www
 *
 */
public class SystemLogsBean {
    
    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;

    /**
     * 编号
     */
    private int id;
    
    /**
     * 时间
     */
    private long time;
    
    /**
     * 用户编号
     */
    private int userId;
    
    /**
     * 用户名称
     */
    private String userName;
    
    /**
     * 小模块编号
     */
    private int moduleId;
    
    /**
     * 小模块名称
     */
    private String moduleName;
    
    /**
     * 信息
     */
    private String msg;

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
     * <br />获取 <font color="red"><b>时间<b/></font>
     * @return time 时间
     */
    public long getTime() {
        return time;
    }

    /**  
     * <br />设置 <font color='#333399'><b>时间</b></font>
     * @param time 时间  
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * <br />获取 <font color="red"><b>用户编号<b/></font>
     * @return userId 用户编号
     */
    public int getUserId() {
        return userId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>用户编号</b></font>
     * @param userId 用户编号  
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * <br />获取 <font color="red"><b>用户名称<b/></font>
     * @return userName 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>用户名称</b></font>
     * @param userName 用户名称  
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * <br />获取 <font color="red"><b>模块名称<b/></font>
     * @return moduleName 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>模块名称</b></font>
     * @param moduleName 模块名称  
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * <br />获取 <font color="red"><b>信息<b/></font>
     * @return msg 信息
     */
    public String getMsg() {
        return msg;
    }

    /**  
     * <br />设置 <font color='#333399'><b>信息</b></font>
     * @param msg 信息  
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
