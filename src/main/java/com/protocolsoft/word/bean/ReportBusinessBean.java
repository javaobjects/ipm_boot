/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 */
package com.protocolsoft.word.bean;

/**
 * @ClassName: ReportBusinessBean
 * @Description: 报表对象 用于与myBatis交互
 * @author 刘斌
 *
 */
public class ReportBusinessBean {

    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 对应的计划Id
     */
    private Integer planId;
    /**
     * 模块Id
     */
    private Integer modulId;
    /**
     * 模板Id
     */
    private Integer bussinessId;
    
    /**
     * 观察点I
     */
    private Integer watchPointId;
    
    
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
    public Integer getPlanId() {
        return planId;
    }
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
    public Integer getModulId() {
        return modulId;
    }
    public void setModulId(Integer modulId) {
        this.modulId = modulId;
    }
    public Integer getBussinessId() {
        return bussinessId;
    }
    public void setBussinessId(Integer bussinessId) {
        this.bussinessId = bussinessId;
    }
    
    /**
     * <p>Title: </p>
     * <p>Description:无参构造器 </p>
     */
    public ReportBusinessBean() {
        super();
    }
    public Integer getWatchPointId() {
        return watchPointId;
    }
    public void setWatchPointId(Integer watchPointId) {
        this.watchPointId = watchPointId;
    }
    @Override
    public String toString() {
        return "ReportBusinessBean [id=" + id + ", name=" + name + ", planId="
                + planId + ", modulId=" + modulId + ", bussinessId="
                + bussinessId + ", watchPointId=" + watchPointId + "]";
    }
    
}
