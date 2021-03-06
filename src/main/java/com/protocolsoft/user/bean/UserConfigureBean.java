/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserConfigureBean
 *创建人:long    创建时间:2017年9月1日
 */
package com.protocolsoft.user.bean;

import java.io.Serializable;

/**
 * 配置bean
 * 2017年9月1日 下午4:50:49
 * @author long
 * @version
 * @see
 */
public class UserConfigureBean implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private int id;
    /**
     * userid
     */
    private int userId;
    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;
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
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
