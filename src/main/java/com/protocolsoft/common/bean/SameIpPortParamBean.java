/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SameIpPortParamBean.java
 *创建人: WWW    创建时间: 2018年9月29日
 */
package com.protocolsoft.common.bean;

import java.util.List;

import com.protocolsoft.servers.bean.AppIpPortBean;

/**
 * @ClassName: SameIpPortParamBean
 * @Description: 参数BEAN
 * @author WWW
 *
 */
public class SameIpPortParamBean {
    
    /**
     * 业务编号
     */
    private int busiId;

    /**
     * IP端口
     */
    private List<AppIpPortBean> list;
    
    /**
     * 是否为修改
     */
    private boolean isUpd;

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
     * <br />获取 <font color="red"><b>IP端口<b/></font>
     * @return list IP端口
     */
    public List<AppIpPortBean> getList() {
        return list;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>IP端口</b></font>
     * @param list IP端口  
     */
    public void setList(List<AppIpPortBean> list) {
        this.list = list;
    }
    

    /**
     * <br />获取 <font color="red"><b>是否为修改<b/></font>
     * @return isUpd 是否为修改
     */
    public boolean isUpd() {
        return isUpd;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>是否为修改</b></font>
     * @param isUpd 是否为修改  
     */
    public void setUpd(boolean isUpd) {
        this.isUpd = isUpd;
    }
    
}
