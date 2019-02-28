/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SimpleParamBean.java
 *创建人: www    创建时间: 2018年1月11日
 */
package com.protocolsoft.app.bean;

/**
 * @ClassName: SimpleParamBean
 * @Description: 下列表参数
 * @author www
 *
 */
public class SimpleParamBean extends ParamBean {

    /**
     * 绘图编号(多个逗号隔开)
     */
    private String plotIds;

    /**
     * <br />获取 <font color="red"><b>绘图编号(多个逗号隔开)<b/></font>
     * @return plotIds 绘图编号(多个逗号隔开)
     */
    public String getPlotIds() {
        return plotIds;
    }

    /**  
     * <br />设置 <font color='#333399'><b>绘图编号(多个逗号隔开)</b></font>
     * @param plotIds 绘图编号(多个逗号隔开)  
     */
    public void setPlotIds(String plotIds) {
        this.plotIds = plotIds;
    }
}
