/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: TimeDefaultBean.java
 *创建人: www    创建时间: 2017年12月4日
 */
package com.protocolsoft.utils.bean;

/**
 * @ClassName: TimeDefaultBean
 * @Description: 默认时间
 * @author www
 *
 */
public class TimeDefaultBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;

    /**
     * <br />获取 <font color="red"><b>开始时间<b/></font>
     * @return starttime 开始时间
     */
    public long getStarttime() {
        return starttime;
    }

    /**
     * <br />获取 <font color="red"><b>结束时间<b/></font>
     * @return endtime 结束时间
     */
    public long getEndtime() {
        return endtime;
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param starttime 开始时间
     * @param endtime 结束时间
     */ 
    public TimeDefaultBean(long starttime, long endtime) {
        super();
        this.starttime = starttime;
        this.endtime = endtime;
    }
}
    
