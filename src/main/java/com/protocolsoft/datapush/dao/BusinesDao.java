/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: BusinessDao.java
 *创建人: wangjianmin    创建时间: 2018年6月19日
 */
package com.protocolsoft.datapush.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.datapush.bean.BusinesBean;
import com.protocolsoft.datapush.bean.BusinesKpiBean;

/**
 * @ClassName: BusinessDao
 * @Description: 数据推送dao
 * @author wangjianmin
 *
 */
@Repository
public interface BusinesDao {
    
    /**
     * 
     * @Title: addDataPush
     * @Description: 添加数据推送设置
     * @param bean 接收添加参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @Insert("insert into ipm_data_push(granularity,moduleType,watchpointId,busiId,kpiIds,brokerIp,port,topic,type,"
            + "serverIp,clientIp,pushType)"
            + " values(#{granularity},#{moduleType},#{watchpointId},#{busiId},#{kpiIds},#{brokerIp},#{port}"
            + ",#{topic},#{type},#{serverIp},#{clientIp},#{pushType})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    boolean  addDataPush(BusinesBean bean);
    
    /**
     * 
     * @Title: delDataPush
     * @Description: 删除推送设置
     * @param id   业务id 
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @Delete(" delete from ipm_data_push where id =#{id} ")
    boolean  delDataPush(@Param("id") int id);
    
    /**
     * 
     * @Title: update
     * @Description: 修改推送设置
     * @param bean 接收修改推送设置参数
     * @return boolean 返回ture/false
     * @author wangjianmin
     */
    @Update("update  ipm_data_push set granularity=#{granularity},moduleType=#{moduleType},"
            + "watchpointId =#{watchpointId},busiId =#{busiId},kpiIds =#{kpiIds},brokerIp=#{brokerIp},"
            + "port=#{port},topic=#{topic},type=#{type},serverIp=#{serverIp},"
            + "clientIp=#{clientIp},pushType=#{pushType} where id=#{id}")
    boolean update(BusinesBean bean);
    
    /**
     * 
     * @Title: getAll
     * @Description: 查询所有推送数据设置
     * @return List<BusinesBean>
     * @author wangjianmin
     */
    @Select("SELECT id,granularity,moduleType,watchpointId,busiId,kpiIds,brokerIp,port,topic,"
            + "type,serverIp,clientIp,pushType FROM ipm_data_push ")
    List<BusinesBean>  getAll();
    
    /**
     * 
     * @Title: getKpis
     * @Description: 查询某模块的kpi信息
     * @return List<BusinesKpiBean>
     * @author wangjianmin
     */
    @Select("SELECT p.kpi_id AS kpiId,k.name from ipm_plot_option p"
            + " LEFT JOIN  ipm_kpis  k ON p.kpi_id=k.id "
            + "WHERE p.module_id =#{moduleId} and  p.kpi_id !=0  AND p.number = 1")
    List<BusinesKpiBean> getKpis(Integer moduleId);
    
    /**
     * 
     * @Title: getSelectById
     * @Description: 查询一条记录
     * @param id  业务id
     * @return BusinesKpiBean
     * @author wangjianmin
     */
    @Select("SELECT id,granularity,moduleType,watchpointId,busiId,kpiIds,brokerIp,port,topic,type "
            + ",serverIp,clientIp,pushType FROM ipm_data_push where id =#{id}")
    BusinesBean   getSelectById(Integer id);
}
