/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmHeatmap.java
 *创建人: www    创建时间: 2018年5月11日
 */
package com.protocolsoft.common.bean;

/**
 * @ClassName: AlarmHeatmap
 * @Description: 告警健康度
 * @author www
 *
 */
public class AlarmHeatmapBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 总个数
     */
    private int num;
    
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
     * <br />获取 <font color="red"><b>总个数<b/></font>
     * @return num 总个数
     */
    public int getNum() {
        return num;
    }

    /**  
     * <br />设置 <font color='#333399'><b>总个数</b></font>
     * @param num 总个数  
     */
    public void setNum(int num) {
        this.num = num;
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
