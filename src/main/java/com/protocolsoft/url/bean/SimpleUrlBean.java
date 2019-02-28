/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: UrlBean.java
 *创建人: www    创建时间: 2018年3月1日
 */
package com.protocolsoft.url.bean;

/**
 * @ClassName: SimpleUrlBean
 * @Description: 单个URL设置
 * @author www
 *
 */
public class SimpleUrlBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 业务编号
     */
    private int appId;
    
    /**
     * 显示编号
     */
    private int num;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * URL
     */
    private String url;
    
    /**
     * 是否存储
     */
    private boolean isStored;
    
    /**
     * 当前状态(修改时使用)   <br>
     * 1 -> 添加  <br>
     * 2 -> 修改  <br>
     * 3 -> 删除  <br>
     */
    private int stauts;

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
     * <br />获取 <font color="red"><b>业务编号<b/></font>
     * @return appId 业务编号
     */
    public int getAppId() {
        return appId;
    }

    /**  
     * <br />设置 <font color='#333399'><b>业务编号</b></font>
     * @param appId 业务编号  
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }

    /**
     * <br />获取 <font color="red"><b>显示编号<b/></font>
     * @return num 显示编号
     */
    public int getNum() {
        return num;
    }

    /**  
     * <br />设置 <font color='#333399'><b>显示编号</b></font>
     * @param num 显示编号  
     */
    public void setNum(int num) {
        this.num = num;
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
     * <br />获取 <font color="red"><b>URL<b/></font>
     * @return url URL
     */
    public String getUrl() {
        return url;
    }

    /**  
     * <br />设置 <font color='#333399'><b>URL</b></font>
     * @param url URL  
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <br />获取 <font color="red"><b>是否存储<b/></font>
     * @return isStored 是否存储
     */
    public boolean isStored() {
        return isStored;
    }

    /**  
     * <br />设置 <font color='#333399'><b>是否存储</b></font>
     * @param isStored 是否存储  
     */
    public void setStored(boolean isStored) {
        this.isStored = isStored;
    }

    /**
     * <br />获取 <font color="red"><b>当前状态(修改时使用)<br>1->添加<br>2->修改<br>3->删除<br><b/></font>
     * @return stauts 当前状态(修改时使用)<br>1->添加<br>2->修改<br>3->删除<br>
     */
    public int getStauts() {
        return stauts;
    }

    /**  
     * <br />设置 <font color='#333399'><b>当前状态(修改时使用)<br>1->添加<br>2->修改<br>3->删除<br></b></font>
     * @param stauts 当前状态(修改时使用)<br>1->添加<br>2->修改<br>3->删除<br>  
     */
    public void setStauts(int stauts) {
        this.stauts = stauts;
    }
}
