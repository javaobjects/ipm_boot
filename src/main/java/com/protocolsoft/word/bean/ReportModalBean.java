/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

import java.util.List;

/**
 * @ClassName: ReportModalBean
 * @Description: 报表对象 用于与myBatis交互
 * @author 刘斌
 *
 */
public class ReportModalBean {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板创建者的Id
     */
    private Integer userId;
    /**
     * 模板创建者的名字
     */
    private String userName;
    /**
     * 模板描述
     */
    private String description;
    /**
     * 模板的创建时间
     */
    private Long createTime;
    /**
     * 模板kpi对象集合
     */
    private List<GroupBean> group;
    
    /**
     * 告警与列表集合
     */
    private List<ReportModalTableAndWranBean> tableAndWarning;
    
    /**
     * 模板id拼接的字符串
     */
    private String moduleIds;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param id
     * @param name
     * @param userId
     * @param userName
     * @param description
     * @param createTime
     * @param group
     * @param tableAndWarning
     */
    public ReportModalBean(Integer id, String name, Integer userId,
            String userName, String description, Long createTime,
            List<GroupBean> group,
            List<ReportModalTableAndWranBean> tableAndWarning) {
        super();
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.createTime = createTime;
        this.group = group;
        this.tableAndWarning = tableAndWarning;
    }

    /**
     * <p>Title: </p>
     * <p>Description:无参构造器 </p>
     */
    public ReportModalBean() {
        super();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public List<ReportModalTableAndWranBean> getTableAndWarning() {
        return tableAndWarning;
    }

    public void setTableAndWarning(List<ReportModalTableAndWranBean> tableAndWarning) {
        this.tableAndWarning = tableAndWarning;
    }

    @Override
    public String toString() {
        return "ReportModalBean [id=" + id + ", name=" + name + ", userId="
                + userId + ", userName=" + userName + ", description="
                + description + ", createTime=" + createTime + ", group="
                + group + ", tableAndWarning=" + tableAndWarning + "]";
    }

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }
    
}
