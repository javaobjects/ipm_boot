/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlStautsParamBean.java
 *创建人: www    创建时间: 2018年3月20日
 */
package com.protocolsoft.url.bean;

/**
 * @ClassName: UrlStautsParamBean
 * @Description: URL状态列表参数
 * @author www
 *
 */
public class UrlStateParamBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 观察点编号
     */
    private int watchpointId;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 列表类型编号
     */
    private int typeId;
    
    /**
     * 业务编号
     */
    private int busiId;

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
     * <br />获取 <font color="red"><b>列表类型编号<b/></font>
     * @return typeId 列表类型编号
     */
    public int getTypeId() {
        return typeId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>列表类型编号</b></font>
     * @param typeId 列表类型编号  
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
}
