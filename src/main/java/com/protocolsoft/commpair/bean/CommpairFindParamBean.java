/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairFindParamBean
 *创建人:chensq    创建时间:2018年1月12日
 */
package com.protocolsoft.commpair.bean;
 
/**
 * @ClassName: CommpairFindParamBean
 * @Description: 通信对查询参数对象
 * @author chensq
 *
 */
public class CommpairFindParamBean {
   
    /**
     * @Fields watchpointSql : 查询参数sql
     */
    private String watchpointSql;
     
    /**
     * @Fields unionAllSql : 查询参数sql
     */
    private String unionAllSql;
   
    /**
     * @Fields groupBySql : 分组
     */
    private String groupBySql;
    
    /**
     * @Fields orderBySql : 排序
     */
    private String orderBySql;
    
    /**
     * @Fields limitNum : 前n条
     */
    private long limitNum;
    
    /**
     * @Fields startNum : limit开始
     */
    private Integer startNum;
    
    /**
     * @Fields stepNum : limit步长
     */
    private Integer stepNum;
    
    /**
     * @Fields pageSql : 分页sql
     */
    private String pageSql;
    
    /**
     * @Fields openIpLocationFlagLeftJoinSql : 地址库开关-left join
     */
    private String openIpLocationFlagLeftJoinSql;
     
    /**
     * @Fields openIpLocationFlagColumnSql : 地址库开关-列
     */
    private String openIpLocationFlagColumnSql;
    
    /**
     * @Fields mapMatchCommpair : 地图匹配通信对
     */
    private String mapMatchCommpair;
    
    /**
     * @return the watchpointSql
     */
    public String getWatchpointSql() {
        return watchpointSql;
    }
    /**
     * @param watchpointSql the watchpointSql to set
     */
    public void setWatchpointSql(String watchpointSql) {
        this.watchpointSql = watchpointSql;
    }
    /**
     * @return the unionAllSql
     */
    public String getUnionAllSql() {
        return unionAllSql;
    }
    /**
     * @param unionAllSql the unionAllSql to set
     */
    public void setUnionAllSql(String unionAllSql) {
        this.unionAllSql = unionAllSql;
    }
    /**
     * @return the groupBySql
     */
    public String getGroupBySql() {
        return groupBySql;
    }
    /**
     * @param groupBySql the groupBySql to set
     */
    public void setGroupBySql(String groupBySql) {
        this.groupBySql = groupBySql;
    }
    /**
     * @return the orderBySql
     */
    public String getOrderBySql() {
        return orderBySql;
    }
    /**
     * @param orderBySql the orderBySql to set
     */
    public void setOrderBySql(String orderBySql) {
        this.orderBySql = orderBySql;
    }
    /**
     * @return the limitNum
     */
    public long getLimitNum() {
        return limitNum;
    }
    /**
     * @param limitNum the limitNum to set
     */
    public void setLimitNum(long limitNum) {
        this.limitNum = limitNum;
    }
    /**
     * @return the openIpLocationFlagLeftJoinSql
     */
    public String getOpenIpLocationFlagLeftJoinSql() {
        return openIpLocationFlagLeftJoinSql;
    }
    /**
     * @param openIpLocationFlagLeftJoinSql the openIpLocationFlagLeftJoinSql to set
     */
    public void setOpenIpLocationFlagLeftJoinSql(
            String openIpLocationFlagLeftJoinSql) {
        this.openIpLocationFlagLeftJoinSql = openIpLocationFlagLeftJoinSql;
    }
    /**
     * @return the openIpLocationFlagColumnSql
     */
    public String getOpenIpLocationFlagColumnSql() {
        return openIpLocationFlagColumnSql;
    }
    /**
     * @param openIpLocationFlagColumnSql the openIpLocationFlagColumnSql to set
     */
    public void setOpenIpLocationFlagColumnSql(String openIpLocationFlagColumnSql) {
        this.openIpLocationFlagColumnSql = openIpLocationFlagColumnSql;
    }
    /**
     * @return the mapMatchCommpair
     */
    public String getMapMatchCommpair() {
        return mapMatchCommpair;
    }
    /**
     * @param mapMatchCommpair the mapMatchCommpair to set
     */
    public void setMapMatchCommpair(String mapMatchCommpair) {
        this.mapMatchCommpair = mapMatchCommpair;
    }
    /**
     * @return the startNum
     */
    public Integer getStartNum() {
        return startNum;
    }
    /**
     * @param startNum the startNum to set
     */
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }
    /**
     * @return the stepNum
     */
    public Integer getStepNum() {
        return stepNum;
    }
    /**
     * @param stepNum the stepNum to set
     */
    public void setStepNum(Integer stepNum) {
        this.stepNum = stepNum;
    }
    /**
     * <br />获取 <font color="red"><b>pageSql<b/></font>
     * @return pageSql pageSql
     */
    public String getPageSql() {
        return pageSql;
    }
    /**  
     * <br />设置 <font color='#333399'><b>pageSql</b></font>
     * @param pageSql pageSql  
     */
    public void setPageSql(String pageSql) {
        this.pageSql = pageSql;
    }
    
}
