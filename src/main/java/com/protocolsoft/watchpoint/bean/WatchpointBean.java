/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:WatchpointBean
 *创建人:wjm    创建时间:2017年9月1日
 */
package com.protocolsoft.watchpoint.bean;

/**
 * 
 * @ClassName: WatchpointBean
 * @Description: 观察点信息类
 * @author wangjianmin
 *
 */
public class WatchpointBean {
    /**
     * 业务编号
     */
    private int id;
    
    /**
     * 业务名称
     */
    private String name;
    
    /**
     * 网卡名称
     */
    private String did;
    
    /**
     * VLAN 
     * vid
     */
    private String vid;
    
    /**
     * vx VLAN
     */
    private String vxid;
    
    /**
     * MPLS LABLE 
     * lid1
     */
    private String lid1;
    
    /**
     * MPLS LABLE 
     * lid2
     */
    private String lid2;
    
    /**
     * MPLS LABLE 
     * lid3
     */
    private String lid3;
    
    /**
     * MPLS LABLE 
     * lid4
     */
    private String lid4;
    
    /**
     * MPLS LABLE 
     * lid5
     */
    private String lid5;
    
    /**
     * 带宽
     */
    private String bandwidth;
    
    /**
     * 是否选中
     */
    private int checked;
    
    /**
     * 租户IP
     */
    private String ip;
    
    /**
     * 租户端口
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
     * 邮箱
     */
    private String email;
    
    /**
     * 服务时限（就是有效限期）
     */
    private Integer validterm;
    
    /**
     * 是否为本地观察点
     */
    private boolean isLocal;

    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getValidterm() {
		return validterm;
	}

	public void setValidterm(Integer validterm) {
		this.validterm = validterm;
	}

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}

	/**
     * <br />获取 <font color="red"><b>id<b/></font>
     * @return id id
     */
    public int getId() {
        return id;
    }

    /**  
     * <br />设置 <font color='#333399'><b>id</b></font>
     * @param id id  
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * <br />获取 <font color="red"><b>name<b/></font>
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**  
     * <br />设置 <font color='#333399'><b>name</b></font>
     * @param name name  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <br />获取 <font color="red"><b>did<b/></font>
     * @return did did
     */
    public String getDid() {
        return did;
    }

    /**  
     * <br />设置 <font color='#333399'><b>did</b></font>
     * @param did did  
     */
    public void setDid(String did) {
        this.did = did;
    }

    /**
     * <br />获取 <font color="red"><b>vid<b/></font>
     * @return vid vid
     */
    public String getVid() {
        return vid;
    }

    /**  
     * <br />设置 <font color='#333399'><b>vid</b></font>
     * @param vid vid  
     */
    public void setVid(String vid) {
        this.vid = vid;
    }

    /**
     * <br />获取 <font color="red"><b>vxid<b/></font>
     * @return vxid vxid
     */
    public String getVxid() {
        return vxid;
    }

    /**  
     * <br />设置 <font color='#333399'><b>vxid</b></font>
     * @param vxid vxid  
     */
    public void setVxid(String vxid) {
        this.vxid = vxid;
    }

    /**
     * <br />获取 <font color="red"><b>lid1<b/></font>
     * @return lid1 lid1
     */
    public String getLid1() {
        return lid1;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lid1</b></font>
     * @param lid1 lid1  
     */
    public void setLid1(String lid1) {
        this.lid1 = lid1;
    }

    /**
     * <br />获取 <font color="red"><b>lid2<b/></font>
     * @return lid2 lid2
     */
    public String getLid2() {
        return lid2;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lid2</b></font>
     * @param lid2 lid2  
     */
    public void setLid2(String lid2) {
        this.lid2 = lid2;
    }

    /**
     * <br />获取 <font color="red"><b>lid3<b/></font>
     * @return lid3 lid3
     */
    public String getLid3() {
        return lid3;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lid3</b></font>
     * @param lid3 lid3  
     */
    public void setLid3(String lid3) {
        this.lid3 = lid3;
    }

    /**
     * <br />获取 <font color="red"><b>lid4<b/></font>
     * @return lid4 lid4
     */
    public String getLid4() {
        return lid4;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lid4</b></font>
     * @param lid4 lid4  
     */
    public void setLid4(String lid4) {
        this.lid4 = lid4;
    }

    /**
     * <br />获取 <font color="red"><b>lid5<b/></font>
     * @return lid5 lid5
     */
    public String getLid5() {
        return lid5;
    }

    /**  
     * <br />设置 <font color='#333399'><b>lid5</b></font>
     * @param lid5 lid5  
     */
    public void setLid5(String lid5) {
        this.lid5 = lid5;
    }

    /**
     * <br />获取 <font color="red"><b>bandwidth<b/></font>
     * @return bandwidth bandwidth
     */
    public String getBandwidth() {
        return bandwidth;
    }

    /**  
     * <br />设置 <font color='#333399'><b>bandwidth</b></font>
     * @param bandwidth bandwidth  
     */
    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    /**
     * <br />获取 <font color="red"><b>checked<b/></font>
     * @return checked checked
     */
    public int getChecked() {
        return checked;
    }

    /**  
     * <br />设置 <font color='#333399'><b>checked</b></font>
     * @param checked checked  
     */
    public void setChecked(int checked) {
        this.checked = checked;
    }
}
