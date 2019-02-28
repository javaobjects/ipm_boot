/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SamllModuleBean.java
 *创建人: WWW    创建时间: 2018年9月26日
 */
package com.protocolsoft.user.bean;

/**
 * @ClassName: SamllModuleBean
 * @Description: 小模块信息
 * @author WWW
 *
 */
public class SamllModuleBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 模块编号
     */
    private int moduleId;
    
    /**
     * 名称
     */
    private String name;

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
     * <br />获取 <font color="red"><b>模块编号<b/></font>
     * @return moduleId 模块编号
     */
    public int getModuleId() {
        return moduleId;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>模块编号</b></font>
     * @param moduleId 模块编号  
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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
}
