/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserListColumnBean
 *创建人:long    创建时间:2017年9月15日
 */
package com.protocolsoft.user.bean;

/**
 * UserListColumnBean
 * 2017年9月15日 上午9:42:17
 * @author long
 * @version
 * @see
 */
public class UserListColumnBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 列字段id
     */
    private int columnId;
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
     * @return the columnId
     */
    public int getColumnId() {
        return columnId;
    }
    /**
     * @param columnId the columnId to set
     */
    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

}
