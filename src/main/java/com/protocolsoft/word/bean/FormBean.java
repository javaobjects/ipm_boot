/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

import java.util.List;



/**
 * @ClassName: FormBean
 * @Description: 报表对象 用于与myBatis交互
 * @author 刘斌
 *
 */
public class FormBean {
    
    /**
     * @Fields id : id
     */
    private Integer id;
    /**
     * @Fields typeId : 报表形式
     */
    private Integer typeId;
    /**
     * @Fields name : 报表名称
     */
    private String name;
    /**
     * @Fields starttime : 开始时间
     */
    private Long starttime;
    /**
     * @Fields endtime : 结束时间
     */
    private Long endtime;
    /**
     * @Fields group : 图片信息(每项KPI)
     */
    private List<GroupBean> group;
    /**
     * @Fields state : 状态（开启可生成，关闭不能）
     */
    private Integer state;
    
    /**
     * @Fields watchpointId : 观察点id
     */
    private Integer watchpointId;
    
    /**
     * @Fields watchpointId : 模板Id
     */
    private Integer modalId;
    

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }
    /**
     * <p>Title: </p>
     * <p>Description:无参构造器 </p>
     */ 
    public FormBean() {
        super();
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }
    
    /**
     * <p>Title: </p>
     * <p>Description:有参构造器 </p>
     * @param typeId
     * @param name
     * @param starttime
     * @param endtime
     * @param group
     */ 
    public FormBean(Integer typeId, String name, Long starttime, Long endtime,
            List<GroupBean> group) {
        super();
        this.typeId = typeId;
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.group = group;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getWatchpointId() {
        return watchpointId;
    }

    public void setWatchpointId(Integer watchpointId) {
        this.watchpointId = watchpointId;
    }

    public Integer getModalId() {
        return modalId;
    }

    public void setModalId(Integer modalId) {
        this.modalId = modalId;
    }

    @Override
    public String toString() {
        return "FormBean [id=" + id + ", typeId=" + typeId + ", name=" + name
                + ", starttime=" + starttime + ", endtime=" + endtime
                + ", group=" + group + ", state=" + state + ", watchpointId="
                + watchpointId + ", modalId=" + modalId + "]";
    }

    
}
