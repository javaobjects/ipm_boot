/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:ListColumn
 *创建人:long    创建时间:2017年9月14日
 */
package com.protocolsoft.user.bean;

/**
 * ListColumn
 * 2017年9月14日 下午6:10:01
 * @author long
 * @version
 * @see
 */
public class ListColumnBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 类型id
     */
    private int typeId;
    /**
     * 英文名称
     */
    private String columnen;
    /**
     * 中文名称
     */
    private String columnzh;
    /**
     * 计算
     */
    private String calcul;
    /**
     * 是否默认
     */
    private int isDefault;
    /**
     * 是否选中
     */
    private int checked;
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
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }
    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    /**
     * @return the columnen
     */
    public String getColumnen() {
        return columnen;
    }
    /**
     * @param columnen the columnen to set
     */
    public void setColumnen(String columnen) {
        this.columnen = columnen;
    }
    /**
     * @return the columnzh
     */
    public String getColumnzh() {
        return columnzh;
    }
    /**
     * @param columnzh the columnzh to set
     */
    public void setColumnzh(String columnzh) {
        this.columnzh = columnzh;
    }
    /**
     * @return the calcul
     */
    public String getCalcul() {
        return calcul;
    }
    /**
     * @param calcul the calcul to set
     */
    public void setCalcul(String calcul) {
        this.calcul = calcul;
    }
    /**
     * @return the isDefault
     */
    public int getIsDefault() {
        return isDefault;
    }
    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
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
