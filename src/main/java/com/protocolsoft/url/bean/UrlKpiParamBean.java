/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlKpiBean.java
 *创建人: www    创建时间: 2018年3月7日
 */
package com.protocolsoft.url.bean;

import java.util.List;

import com.protocolsoft.app.bean.ParamBean;

/**
 * @ClassName: UrlKpiBean
 * @Description: URL
 * @author www
 *
 */
public class UrlKpiParamBean extends ParamBean {

    /**
     * HTTP数据表
     */
    private List<String> tables;

    /**
     * <br />获取 <font color="red"><b>HTTP数据表<b/></font>
     * @return tables HTTP数据表
     */
    public List<String> getTables() {
        return tables;
    }

    /**  
     * <br />设置 <font color='#333399'><b>HTTP数据表</b></font>
     * @param tables HTTP数据表  
     */
    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
