/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AppBusinessBean
 *创建人:yan    创建时间:2017年9月1日
 */
package com.protocolsoft.common.bean;

import java.io.Serializable;

/**
 * 对应表ipm_app_business
 * 2017年9月1日 上午11:13:10
 * @author yan
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class AppBusinessBean implements Serializable{
    
    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 模块id
     */
    private Integer moduleId;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * ip
     */
    private String displayIp;
    
    /**
     * 带宽
     */
    private int bandwidth;
    
    /**
     * 描述
     */
    private String descrption;
    
    /**
     * 是否选中
     */
    private int checked;

    /**
     * 探针告警类型
     */
    private int probeAlarmType;
    
    /**
     * 
     */
    public AppBusinessBean() {
        super();
    }

    /**
     * @param id
     * @param moduleId
     * @param name
     * @param displayIp
     * @param descrption
     */
    public AppBusinessBean(Integer id, Integer moduleId, String name,
            String displayIp, String descrption) {
        super();
        this.id = id;
        this.moduleId = moduleId;
        this.name = name;
        this.displayIp = displayIp;
        this.descrption = descrption;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the moduleId
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId the moduleId to set
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
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
     * @return the displayIp
     */
    public String getDisplayIp() {
        return displayIp;
    }

    /**
     * @param displayIp the displayIp to set
     */
    public void setDisplayIp(String displayIp) {
        this.displayIp = displayIp;
    }

    /**
     * <br />获取 <font color="red"><b>带宽<b/></font>
     * @return bandwidth 带宽
     */
    public int getBandwidth() {
        return bandwidth;
    }

    /**  
     * <br />设置 <font color='#333399'><b>带宽</b></font>
     * @param bandwidth 带宽  
     */
    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
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

    /**
     * <br />获取 <font color="red"><b>probeAlarmType<b/></font>
     * @return probeAlarmType probeAlarmType
     */
    public int getProbeAlarmType() {
        return probeAlarmType;
    }

    /**  
     * <br />设置 <font color='#333399'><b>probeAlarmType</b></font>
     * @param probeAlarmType probeAlarmType  
     */
    public void setProbeAlarmType(int probeAlarmType) {
        this.probeAlarmType = probeAlarmType;
    }
    
    
}
