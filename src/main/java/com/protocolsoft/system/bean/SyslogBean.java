/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: SyslogBean.java
 *创建人: www    创建时间: 2018年3月31日
 */
package com.protocolsoft.system.bean;

/**
 * @ClassName: SyslogBean
 * @Description: SYSLOG
 * @author www
 *
 */
public class SyslogBean {

    /**
     * 编号
     */
    private int id;
    
    /**
     * IP地址
     */
    private String ip;
    
    /**
     * IP端口
     */
    private int port;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 备注
     */
    private String descrption;

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
     * <br />获取 <font color="red"><b>IP地址<b/></font>
     * @return ip IP地址
     */
    public String getIp() {
        return ip;
    }

    /**  
     * <br />设置 <font color='#333399'><b>IP地址</b></font>
     * @param ip IP地址  
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * <br />获取 <font color="red"><b>IP端口<b/></font>
     * @return port IP端口
     */
    public int getPort() {
        return port;
    }

    /**  
     * <br />设置 <font color='#333399'><b>IP端口</b></font>
     * @param port IP端口  
     */
    public void setPort(int port) {
        this.port = port;
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
     * <br />获取 <font color="red"><b>备注<b/></font>
     * @return descrption 备注
     */
    public String getDescrption() {
        return descrption;
    }

    /**  
     * <br />设置 <font color='#333399'><b>备注</b></font>
     * @param descrption 备注  
     */
    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
}
