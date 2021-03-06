/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairTablesBean
 *创建人:yan    创建时间:2017-10-16
 */
package com.protocolsoft.map.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: CommpairTablesBean
 * @Description: 对应commpair_观察点_粒度_log_tables
 * @author wangjianmin
 *
 */
public class CommpairTablesBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 表名
     */
    private String tableId;
    
    /**
     * 观察点id
     */
    private Integer wpid;
    
    /**
     * 开始时间
     */
    private Long start;
    
    /**
     * 结束时间
     */
    private Long end;
    
    /**
     * 粒度 数据库无此字段
     */
    private Long granularity;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Integer getWpid() {
        return wpid;
    }

    public void setWpid(Integer wpid) {
        this.wpid = wpid;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getGranularity() {
        return granularity;
    }

    public void setGranularity(Long granularity) {
        this.granularity = granularity;
    }
}
