/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:JurisModuleBean
 *创建人:long    创建时间:2017年9月5日
 */
package com.protocolsoft.user.bean;

import java.util.List;

/**
 * 
 * @ClassName: JurisModuleBean
 * @Description: 原模块信息
 * @author wangjianmin
 *
 */
public class JurisModuleBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 英文名称
     */
    private String nameen;
    /**
     * 中文名称
     */
    private String namezh;
    /**
     * 是否选中
     */
    private int checked;
    /**
     * 驾驶舱id
     */
    private int monitorId;
    
    /**
     * 用户名称
     */
    private String userName;
    /**
     * AuthorizeJurisBean
     */
    private AuthorizeJurisBean authorizeJurisBean;
    /**
     * List<MonitorViewBean>
     */
    private List<?> monitorViewBeanList;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the nameen
     */
    public String getNameen() {
        return nameen;
    }
    /**
     * @param nameen the nameen to set
     */
    public void setNameen(String nameen) {
        this.nameen = nameen;
    }
    /**
     * @return the namezh
     */
    public String getNamezh() {
        return namezh;
    }
    /**
     * @param namezh the namezh to set
     */
    public void setNamezh(String namezh) {
        this.namezh = namezh;
    }
    /**
     * @return the authorizeJurisBean
     */
    public AuthorizeJurisBean getAuthorizeJurisBean() {
        return authorizeJurisBean;
    }
    /**
     * @param authorizeJurisBean the authorizeJurisBean to set
     */
    public void setAuthorizeJurisBean(AuthorizeJurisBean authorizeJurisBean) {
        this.authorizeJurisBean = authorizeJurisBean;
    }
 
    /**
     * @return the checked
     */
    public int getChecked() {
        return checked;
    }
    /**
     * @param checked the checked to set
     */
    public void setChecked(int checked) {
        this.checked = checked;
    }
    /**
     * @return the monitorId
     */
    public int getMonitorId() {
        return monitorId;
    }
    /**
     * @param monitorId the monitorId to set
     */
    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }
    /**
     * @return the monitorViewBeanList
     */
    public List<?> getMonitorViewBeanList() {
        return monitorViewBeanList;
    }
    /**
     * @param monitorViewBeanList the monitorViewBeanList to set
     */
    public void setMonitorViewBeanList(List<?> monitorViewBeanList) {
        this.monitorViewBeanList = monitorViewBeanList;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
