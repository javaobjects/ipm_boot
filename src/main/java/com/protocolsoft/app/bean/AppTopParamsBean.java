/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AppTopParamsBean.java
 *创建人: www    创建时间: 2018年1月26日
 */
package com.protocolsoft.app.bean;

import java.util.List;

/**
 * @ClassName: AppTopParamsBean
 * @Description: TOP参数
 * @author www
 *
 */
public class AppTopParamsBean implements Cloneable {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 观察点编号
     */
    private int watchpointId;
    
    /**
     * 业务编号
     */
    private int busiId;
    
    /**
     * 表名
     */
    private List<String> tables;
    
    /**
     * TOP配置
     */
    private AppTopConfigBean bean;

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
     * <br />获取 <font color="red"><b>观察点编号<b/></font>
     * @return watchpointId 观察点编号
     */
    public int getWatchpointId() {
        return watchpointId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>观察点编号</b></font>
     * @param watchpointId 观察点编号  
     */
    public void setWatchpointId(int watchpointId) {
        this.watchpointId = watchpointId;
    }

    /**
     * <br />获取 <font color="red"><b>业务编号<b/></font>
     * @return busiId 业务编号
     */
    public int getBusiId() {
        return busiId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>业务编号</b></font>
     * @param busiId 业务编号  
     */
    public void setBusiId(int busiId) {
        this.busiId = busiId;
    }

    /**
     * <br />获取 <font color="red"><b>表名<b/></font>
     * @return tables 表名
     */
    public List<String> getTables() {
        return tables;
    }

    /**  
     * <br />设置 <font color='#333399'><b>表名</b></font>
     * @param tables 表名  
     */
    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    /**
     * <br />获取 <font color="red"><b>TOP配置<b/></font>
     * @return bean TOP配置
     */
    public AppTopConfigBean getBean() {
        return bean;
    }

    /**  
     * <br />设置 <font color='#333399'><b>TOP配置</b></font>
     * @param bean TOP配置  
     */
    public void setBean(AppTopConfigBean bean) {
        this.bean = bean;
    }
    
    /**
     * (非 Javadoc)
     * <p>Title: clone</p>
     * <p>Description: </p>
     * @return AppTopParamsBean
     * @see java.lang.Object#clone()
     */
    @Override
    public AppTopParamsBean clone() {
        AppTopParamsBean o  = null;
        try {
            o = (AppTopParamsBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return o;
    }
}
