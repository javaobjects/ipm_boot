/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

import java.util.List;

/**
 * 
 * @ClassName: AddEmailBean
 * @Description: 
 * @author 刘斌
 *
 */
public class AddEmailBean {

    /**
     * IPid  代表远端id
     */
    private Integer ipmCenterId;
    
    /**
     * 需要添加的邮件对象的类表
     */
    private List<ReportEmailBean> list;
    
    public Integer getIpmCenterId() {
        return ipmCenterId;
    }
    public void setIpmCenterId(Integer ipmCenterId) {
        this.ipmCenterId = ipmCenterId;
    }
    public List<ReportEmailBean> getList() {
        return list;
    }
    public void setList(List<ReportEmailBean> list) {
        this.list = list;
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param ipmCenterId
     * @param list
     */
    public AddEmailBean(Integer ipmCenterId, List<ReportEmailBean> list) {
        super();
        this.ipmCenterId = ipmCenterId;
        this.list = list;
    }
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public AddEmailBean() {
        super();
    }
    @Override
    public String toString() {
        return "AddEmailBean [ipmCenterId=" + ipmCenterId + ", list=" + list
                + "]";
    }
}
