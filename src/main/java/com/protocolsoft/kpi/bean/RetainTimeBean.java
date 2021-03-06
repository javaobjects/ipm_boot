/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: RetainTimeBean.java
 *创建人: www    创建时间: 2018年5月18日
 */
package com.protocolsoft.kpi.bean;

/**
 * @ClassName: RetainTimeBean
 * @Description: RRD保存时间
 * @author WWW
 *
 */
public class RetainTimeBean {

    /**
     * 粒度 (秒级)
     */
    private int step;
    
    /**
     * 保存时间 (秒级)
     */
    private long time;

    /**
     * <br />获取 <font color="red"><b>粒度(秒级)<b/></font>
     * @return step 粒度(秒级)
     */
    public int getStep() {
        return step;
    }

    /**  
     * <br />设置 <font color='#333399'><b>粒度(秒级)</b></font>
     * @param step 粒度(秒级)  
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * <br />获取 <font color="red"><b>time<b/></font>
     * @return time time
     */
    public long getTime() {
        return time;
    }

    /**  
     * <br />设置 <font color='#333399'><b>time</b></font>
     * @param time time  
     */
    public void setTime(long time) {
        this.time = time;
    }
    
}
