/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SaasUserBean.java
 *创建人: WWW    创建时间: 2018年8月6日
 */
package com.protocolsoft.common.bean;

/**
 * @ClassName: SaasUserBean
 * @Description: 用户信息
 * @author WWW
 *
 */
public class SaasUserBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * IP
     */
    private String ip;
    
    /**
     * 端口
     */
    private int port;
    
    /**
     * 联系人
     */
    private String contacts;
    
    /**
     * 电话
     */
    private String telephone;
    
    /**
     * 邮件
     */
    private String email;
    
    /**
     * 上线时间
     */
    private long uptime;
    
    /**
     * 服务时限
     */
    private long limitdate;
    
    /**
     * 状态 (测试or正式)
     */
    private int state;
    
    /**
     * 硬件版本(B、U、S)
     */
    private int version;
    
    /**
     * 通信时延
     */
    private int delay;

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
     * <br />获取 <font color="red"><b>用户名<b/></font>
     * @return name 用户名
     */
    public String getName() {
        return name;
    }
    


    /**  
     * <br />设置 <font color='#333399'><b>用户名</b></font>
     * @param name 用户名  
     */
    public void setName(String name) {
        this.name = name;
    }
    


    /**
     * <br />获取 <font color="red"><b>IP<b/></font>
     * @return ip IP
     */
    public String getIp() {
        return ip;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>IP</b></font>
     * @param ip IP  
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    

    /**
     * <br />获取 <font color="red"><b>端口<b/></font>
     * @return port 端口
     */
    public int getPort() {
        return port;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>端口</b></font>
     * @param port 端口  
     */
    public void setPort(int port) {
        this.port = port;
    }
    

    /**
     * <br />获取 <font color="red"><b>联系人<b/></font>
     * @return contacts 联系人
     */
    public String getContacts() {
        return contacts;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>联系人</b></font>
     * @param contacts 联系人  
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    

    /**
     * <br />获取 <font color="red"><b>电话<b/></font>
     * @return telephone 电话
     */
    public String getTelephone() {
        return telephone;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>电话</b></font>
     * @param telephone 电话  
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    

    /**
     * <br />获取 <font color="red"><b>邮件<b/></font>
     * @return email 邮件
     */
    public String getEmail() {
        return email;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>邮件</b></font>
     * @param email 邮件  
     */
    public void setEmail(String email) {
        this.email = email;
    }
    

    /**
     * <br />获取 <font color="red"><b>上线时间<b/></font>
     * @return uptime 上线时间
     */
    public long getUptime() {
        return uptime;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>上线时间</b></font>
     * @param uptime 上线时间  
     */
    public void setUptime(long uptime) {
        this.uptime = uptime;
    }
    

    /**
     * <br />获取 <font color="red"><b>服务时限<b/></font>
     * @return limitdate 服务时限
     */
    public long getLimitdate() {
        return limitdate;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>服务时限</b></font>
     * @param limitdate 服务时限  
     */
    public void setLimitdate(long limitdate) {
        this.limitdate = limitdate;
    }
    

    /**
     * <br />获取 <font color="red"><b>状态(测试or正式)<b/></font>
     * @return state 状态(测试or正式)
     */
    public int getState() {
        return state;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>状态(测试or正式)</b></font>
     * @param state 状态(测试or正式)  
     */
    public void setState(int state) {
        this.state = state;
    }
    

    /**
     * <br />获取 <font color="red"><b>硬件版本(B、U、S)<b/></font>
     * @return version 硬件版本(B、U、S)
     */
    public int getVersion() {
        return version;
    }
    

    /**  
     * <br />设置 <font color='#333399'><b>硬件版本(B、U、S)</b></font>
     * @param version 硬件版本(B、U、S)  
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * <br />获取 <font color="red"><b>通信时延<b/></font>
     * @return delay 通信时延
     */
    public int getDelay() {
        return delay;
    }


    /**  
     * <br />设置 <font color='#333399'><b>通信时延</b></font>
     * @param delay 通信时延  
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
