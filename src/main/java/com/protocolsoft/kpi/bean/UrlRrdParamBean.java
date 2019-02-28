/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlRrdParamBean.java
 *创建人: www    创建时间: 2018年3月15日
 */
package com.protocolsoft.kpi.bean;

import com.protocolsoft.kpi.enumeration.RrdAlgorithm;

/**
 * @ClassName: UrlRrdParamBean
 * @Description: URl参数
 * @author www
 *
 */
public class UrlRrdParamBean {

    /**
     * 开始时间
     */
    private long starttime;
    
    /**
     * 结束时间
     */
    private long endtime;
    
    /**
     * 间隔
     */
    private int step;
    
    /**
     * 观察点
     */
    private int watchpointId;
    
    /**
     * 业务编号
     */
    private int busiId;
    
    /**
     * URL编号
     */
    private int subId;
    
    /**
     * 算法
     */
    private RrdAlgorithm alg;

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
     * <br />获取 <font color="red"><b>间隔<b/></font>
     * @return step 间隔
     */
    public int getStep() {
        return step;
    }

    /**  
     * <br />设置 <font color='#333399'><b>间隔</b></font>
     * @param step 间隔  
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * <br />获取 <font color="red"><b>观察点<b/></font>
     * @return watchpointId 观察点
     */
    public int getWatchpointId() {
        return watchpointId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>观察点</b></font>
     * @param watchpointId 观察点  
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
     * <br />获取 <font color="red"><b>URL编号<b/></font>
     * @return subId URL编号
     */
    public int getSubId() {
        return subId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URL编号</b></font>
     * @param subId URL编号  
     */
    public void setSubId(int subId) {
        this.subId = subId;
    }

    /**
     * <br />获取 <font color="red"><b>算法<b/></font>
     * @return alg 算法
     */
    public RrdAlgorithm getAlg() {
        return alg;
    }

    /**  
     * <br />设置 <font color='#333399'><b>算法</b></font>
     * @param alg 算法  
     */
    public void setAlg(RrdAlgorithm alg) {
        this.alg = alg;
    }
}
