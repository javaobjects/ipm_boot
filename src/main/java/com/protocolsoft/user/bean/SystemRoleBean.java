/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:UserRoleBean
 *创建人:long    创建时间:2017年9月4日
 */
package com.protocolsoft.user.bean;


/**
 * user role bean
 * 2017年9月4日 上午9:36:49
 * @author long
 * @version
 * @see
 */
public class SystemRoleBean {
    /**
     * 主键id
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String descrption;
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
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the descrption
     */
    public String getDescrption() {
        return descrption;
    }
    /**
     * @param descrption the descrption to set
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
   
}
