/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: CommpairAreaDict.java
 *创建人: chensq    创建时间: 2018年6月29日
 */
package com.protocolsoft.commpair.bean;

/**
 * @ClassName: CommpairAreaDictBean
 * @Description: 通信对地址信息
 * @author chensq
 *
 */
public class CommpairAreaDictBean {

    /**
     * @Fields id : id
     */
    private long id;

    /**
     * @Fields levelId : 级别id
     */
    private int levelId;
    
    /**
     * @Fields levelName : 级别名称
     */
    private String levelName;
    
    /**
     * @Fields parentId : 父级id
     */
    private long parentId;
    
    /**
     * @Fields nameCn : 名称
     */
    private String nameCn;

    /**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public long getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>levelId<b/></font>
     * @return levelId levelId
     */
    public int getLevelId() {
        return levelId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>levelId</b></font>
     * @param levelId levelId  
     */
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * <br />获取 <font color="red"><b>levelName<b/></font>
     * @return levelName levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**  
     * <br />设置 <font color='#333399'><b>levelName</b></font>
     * @param levelName levelName  
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * <br />获取 <font color="red"><b>parentId<b/></font>
     * @return parentId parentId
     */
    public long getParentId() {
        return parentId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>parentId</b></font>
     * @param parentId parentId  
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    /**
     * <br />获取 <font color="red"><b>nameCn<b/></font>
     * @return nameCn nameCn
     */
    public String getNameCn() {
        return nameCn;
    }

    /**  
     * <br />设置 <font color='#333399'><b>nameCn</b></font>
     * @param nameCn nameCn  
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }
     
}
