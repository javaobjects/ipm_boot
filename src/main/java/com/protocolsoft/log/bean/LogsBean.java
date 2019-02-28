/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:LogsBean
 *创建人:yan    创建时间:2017年9月4日
 */
package com.protocolsoft.log.bean;

import java.io.Serializable;

/**
 * 对应表ipm_logs
 * 2017年9月4日 下午2:59:27
 * @author yan
 * @version
 * @see
 */
@SuppressWarnings("serial")
public class LogsBean implements Serializable{
    
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 操作时间
     */
    private Integer time;
    
    /**
     * 模块id
     */
    private Integer moduleId;
    
    /**
     * 操作 内容
     */
    private String msg;

    /**
     * 
     */
    public LogsBean() {
        super();
    }

    /**
     * @param id
     * @param userId
     * @param time
     * @param moduleId
     * @param msg
     */
    public LogsBean(Integer id, Integer userId, Integer time, Integer moduleId,
            String msg) {
        super();
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.moduleId = moduleId;
        this.msg = msg;
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
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Integer time) {
        this.time = time;
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
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
