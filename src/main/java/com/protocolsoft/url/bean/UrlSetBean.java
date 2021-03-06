/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlBean.java
 *创建人: www    创建时间: 2018年3月1日
 */
package com.protocolsoft.url.bean;

import java.util.List;

/**
 * @ClassName: UrlSetBean
 * @Description: URL设置
 * @author www
 *
 */
public class UrlSetBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 备注
     */
    private String descrption;
    
    /**
     * 是否选中
     */
    private int checked;
    
    /**
     * 设置数据
     */
    private List<SimpleUrlBean> set;

    /**
     * <br />获取 <font color="red"><b>编号<b/></font>
     * @return id 编号
     */
    public int getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>编号</b></font>
     * @param id 编号  
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>名称<b/></font>
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>名称</b></font>
     * @param name 名称  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>备注<b/></font>
     * @return descrption 备注
     */
    public String getDescrption() {
        return descrption;
    }

    /**  
     * <br />设置 <font color='#333399'><b>备注</b></font>
     * @param descrption 备注  
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    /**
     * <br />获取 <font color="red"><b>设置数据<b/></font>
     * @return set 设置数据
     */
    public List<SimpleUrlBean> getSet() {
        return set;
    }

    /**  
     * <br />设置 <font color='#333399'><b>设置数据</b></font>
     * @param set 设置数据  
     */
    public void setSet(List<SimpleUrlBean> set) {
        this.set = set;
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
}
