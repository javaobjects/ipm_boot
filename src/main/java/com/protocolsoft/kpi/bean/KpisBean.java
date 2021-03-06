/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: KpisBean.java
 *创建人: www    创建时间: 2017年9月20日
 */
package com.protocolsoft.kpi.bean;

/**
 * @ClassName: KpisBean
 * @Description: KPI
 * @author www
 *
 */
public class KpisBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 名称EN
     */
    private String name;
    
    /**
     * 名称ZH
     */
    private String displayName;
    
    /**
     * 单位
     */
    private String unit;
    /**
     * 查询使用的字段，该kpi属于哪个模块(智能告警)
     */
    private long moduleId;

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
     * <br />获取 <font color="red"><b>名称EN<b/></font>
     * @return name 名称EN
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>名称EN</b></font>
     * @param name 名称EN  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>名称ZH<b/></font>
     * @return displayName 名称ZH
     */
    public String getDisplayName() {
        return displayName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>名称ZH</b></font>
     * @param displayName 名称ZH  
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * <br />获取 <font color="red"><b>单位<b/></font>
     * @return unit 单位
     */
    public String getUnit() {
        return unit;
    }

    /**  
     * <br />设置 <font color='#333399'><b>单位</b></font>
     * @param unit 单位  
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * <br />获取 <font color="red"><b>moduleId<b/></font>
     * @return moduleId moduleId
     */
    public long getModuleId() {
        return moduleId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>moduleId</b></font>
     * @param moduleId moduleId  
     */
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }
    
}
