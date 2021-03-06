/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SimpleDataBean.java
 *创建人: www    创建时间: 2018年1月11日
 */
package com.protocolsoft.app.bean;

import java.util.List;

/**
 * @ClassName: SimpleDataBean
 * @Description: 十字格数据
 * @author www
 *
 */
public class SimpleDataBean extends ParamBean {
    
   /**
    * 数据
    */
    private List<PointDataBean> data;
    
    /**
     * <br />获取 <font color="red"><b>数据<b/></font>
     * @return data 数据
     */
    public List<PointDataBean> getData() {
        return data;
    }
    
    /**  
     * <br />设置 <font color='#333399'><b>数据</b></font>
     * @param data 数据  
     */
    public void setData(List<PointDataBean> data) {
        this.data = data;
    }
}
