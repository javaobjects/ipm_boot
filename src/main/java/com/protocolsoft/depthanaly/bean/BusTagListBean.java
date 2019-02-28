/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:TestBean
 *创建人:wjm    创建时间:2018年4月9日
 */
package com.protocolsoft.depthanaly.bean;

import java.util.List;


/**
 * 
 * @ClassName: BusTagListBean
 * @Description: 数据接收
 * @author wangjianmin
 *
 */
public class BusTagListBean {
    
    /**
     * 获取数据
     */
    private List<IpmBusTagBean> set;

    /**
     * @return the set
     */
    public List<IpmBusTagBean> getSet() {
        return set;
    }

    /**
     * @param set the set to set
     */
    public void setSet(List<IpmBusTagBean> set) {
        this.set = set;
    }

}
