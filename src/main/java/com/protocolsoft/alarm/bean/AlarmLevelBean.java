/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmLevelBean
 *创建人:chensq    创建时间:2017年10月30日 上午11:45:21
 */
package com.protocolsoft.alarm.bean;

/**
 * @ClassName: AlarmLevelBean
 * @Description: 告警级别实体类
 * @author chensq
 *
 */
public class AlarmLevelBean {
    /**
     * @Fields id : id
     */
    private long id;
    /**
     * @Fields typeId : typeId
     */
    private long typeId;
    /**
     * @Fields nameen : nameen
     */
    private String nameen;
    /**
     * @Fields namezh : namezh
     */
    private String namezh;
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @return the typeId
     */
    public long getTypeId() {
        return typeId;
    }
    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(long typeId) {
        this.typeId = typeId;
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
    
}
