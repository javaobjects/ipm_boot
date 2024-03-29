/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: HeatmapDataBean.java
 *创建人: www    创建时间: 2018年5月18日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: HeatmapDataBean
 * @Description: 健康图
 * @author WWW
 *
 */
public class HeatmapDataBean extends PlotDataBean {
    
    /**
     * Y轴数据 (健康图)
     */
    private int[] yLabel;
    
    /**
     * X轴数据 (健康图)
     */
    private long[] xLabel;

    /**
     * <br />获取 <font color="red"><b>Y轴数据(健康图)<b/></font>
     * @return yLabel Y轴数据(健康图)
     */
    public int[] getyLabel() {
        return yLabel;
    }
    
    /**  
     * <br />设置 <font color='#333399'><b>Y轴数据(健康图)</b></font>
     * @param yLabel Y轴数据(健康图)  
     */
    public void setyLabel(int[] yLabel) {
        this.yLabel = yLabel;
    }
    
    /**
     * <br />获取 <font color="red"><b>X轴数据(健康图)<b/></font>
     * @return xLabel X轴数据(健康图)
     */
    public long[] getxLabel() {
        return xLabel;
    }

    /**  
     * <br />设置 <font color='#333399'><b>X轴数据(健康图)</b></font>
     * @param xLabel X轴数据(健康图)  
     */
    public void setxLabel(long[] xLabel) {
        this.xLabel = xLabel;
    }
}
