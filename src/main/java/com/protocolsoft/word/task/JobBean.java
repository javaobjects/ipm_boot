/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: JobBean.java
 *创建人: wangjianmin    创建时间: 2018年7月10日
 */
package com.protocolsoft.word.task;

/**
 * @ClassName: JobBean
 * @Description: 定时任务Bean
 * @author wangjianmin
 *
 */
public class JobBean {
    /**
     * 任务名 
     */
    String jobName;
    /**
     * 任务组名 
     */
    String jobGroupName;
    /**
     *  触发器名
     */
    String triggerName;
    /**
     * 触发器组名 
     */
    String triggerGroupName;
    /**
     * <br />获取 <font color="red"><b>jobName<b/></font>
     * @return jobName jobName
     */
    public String getJobName() {
        return jobName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>jobName</b></font>
     * @param jobName jobName  
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    /**
     * <br />获取 <font color="red"><b>jobGroupName<b/></font>
     * @return jobGroupName jobGroupName
     */
    public String getJobGroupName() {
        return jobGroupName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>jobGroupName</b></font>
     * @param jobGroupName jobGroupName  
     */
    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }
    /**
     * <br />获取 <font color="red"><b>triggerName<b/></font>
     * @return triggerName triggerName
     */
    public String getTriggerName() {
        return triggerName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>triggerName</b></font>
     * @param triggerName triggerName  
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
    /**
     * <br />获取 <font color="red"><b>triggerGroupName<b/></font>
     * @return triggerGroupName triggerGroupName
     */
    public String getTriggerGroupName() {
        return triggerGroupName;
    }
    /**  
     * <br />设置 <font color='#333399'><b>triggerGroupName</b></font>
     * @param triggerGroupName triggerGroupName  
     */
    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public JobBean(String jobName, String jobGroupName, String triggerName,
            String triggerGroupName) {
        super();
        this.jobName = jobName;
        this.jobGroupName = jobGroupName;
        this.triggerName = triggerName;
        this.triggerGroupName = triggerGroupName;
    }

    
}
