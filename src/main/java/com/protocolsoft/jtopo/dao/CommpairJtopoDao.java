/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:CommpairDao
 *创建人:yan    创建时间:2017-10-16
 */
package com.protocolsoft.jtopo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.protocolsoft.jtopo.bean.CommpairQoBean;
import com.protocolsoft.jtopo.bean.CommpairTablesBean;
import com.protocolsoft.jtopo.bean.CommpairVoBean;
import com.protocolsoft.jtopo.provider.CommpairDaoProvider;

/**
 * 对应通信对表
 * 2017-10-16 下午2:41:57
 * @author yan
 * @version
 * @see
 */
public interface CommpairJtopoDao {

    /**
     * 查询表是否存在
     * 2017-10-18 下午2:44:32
     * @param tableName
     * @return String
     * @exception 
     * @see
     */
    @Select("select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_NAME`= #{tableName}")
    String selectTableIsExist(String tableName);
    
    /**
     * 查询
     * 2017-10-16 下午2:43:22
     * @param commpairQoBean
     * @return List<CommpairTablesBean>
     * @exception 
     * @see
     */
    @Select("select table_id tableId,wpid,#{granularity} granularity,start,end from commpair_#{granularity}_log_tables "
            + "where "
            + "((end >= #{startTime} and end <= #{endTime}) or"
            + "(start <= #{startTime} and end >= #{endTime}) or"
            + "(start >= #{startTime} and start <= #{endTime}))")
    List<CommpairTablesBean> selectCommpairTableId(CommpairQoBean commpairQoBean);
    
    /**
     * 查询
     * 2017-10-16 下午2:43:22
     * @param commpairQoBean
     * @return List<CommpairTablesBean>
     * @exception 
     * @see
     */
    @Select("select table_id tableId,wpid,#{granularity} granularity,start,end from commpair_#{granularity}_log_tables "
            + "where wpid = #{watchpointId} and "
            + "((end >= #{startTime} and end <= #{endTime}) or"
            + "(start <= #{startTime} and end >= #{endTime}) or"
            + "(start >= #{startTime} and start <= #{endTime}))")
    List<CommpairTablesBean> selectCommpairTableIdByWId(CommpairQoBean commpairQoBean);
    
    /**
     * 查询
     * 2017-10-18 下午2:30:47
     * @param commpairTablesBeans
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    @SelectProvider(type = CommpairDaoProvider.class, method = "selectCommpairData") 
    List<CommpairVoBean> selectCommpairData(@Param("beans") List<CommpairTablesBean> commpairTablesBeans,
            @Param("commpairQoBean") CommpairQoBean commpairQoBean);
    
    /**
     * 查询
     * 2017-10-18 下午2:30:47
     * @param commpairTablesBeans
     * @param commpairQoBean
     * @return List<CommpairVoBean>
     * @exception 
     * @see
     */
    @SelectProvider(type = CommpairDaoProvider.class, method = "selectCommpairDataByIpPort") 
    List<CommpairVoBean> selectCommpairDataByIpPort(@Param("beans") List<CommpairTablesBean> commpairTablesBeans,
            @Param("commpairQoBean") CommpairQoBean commpairQoBean);
}
