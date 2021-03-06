/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: HeatSimpleBean.java
 *创建人: www    创建时间: 2018年5月15日
 */
package com.protocolsoft.common.bean;

import com.protocolsoft.app.bean.PlotSimpleBean;

/**
 * @ClassName: HeatSimpleBean
 * @Description: 健康图单条数据
 * @author 小王
 *
 */
public class HeatSimpleBean extends PlotSimpleBean {

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
     * X轴坐标
     */
    private int x;
    
    /**
     * Y轴坐标
     */
    private int y;
    
    /**
     * 最严重等级
     */
    private int level;

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
     * <br />获取 <font color="red"><b>X轴坐标<b/></font>
     * @return x X轴坐标
     */
    public int getX() {
        return x;
    }

    /**  
     * <br />设置 <font color='#333399'><b>X轴坐标</b></font>
     * @param x X轴坐标  
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * <br />获取 <font color="red"><b>Y轴坐标<b/></font>
     * @return y Y轴坐标
     */
    public int getY() {
        return y;
    }
    
    /**  
     * <br />设置 <font color='#333399'><b>Y轴坐标</b></font>
     * @param y Y轴坐标  
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * <br />获取 <font color="red"><b>最严重等级<b/></font>
     * @return level 最严重等级
     */
    public int getLevel() {
        return level;
    }
    
    /**  
     * <br />设置 <font color='#333399'><b>最严重等级</b></font>
     * @param level 最严重等级  
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
