/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:MapDao
 *创建人:yan    创建时间:2017年11月7日
 */
package com.protocolsoft.map.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.protocolsoft.map.bean.CommpairTablesBean;
import com.protocolsoft.map.bean.MapQoBean;
import com.protocolsoft.map.bean.MapVoBean;
import com.protocolsoft.map.provider.MapDaoProvider;

/**
 * 地图Dao
 * 2017年11月7日 下午3:14:25
 * @author yan
 * @version
 * @see
 */
@Repository
public interface MapDao {

    /**
     * 
     * @Title: selectTableIsExist
     * @Description: 查询表是否存在
     * @param tableName 表名
     * @return String
     * @author wangjianmin
     */
    @Select("select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_NAME`= #{tableName}")
    String selectTableIsExist(String tableName);

    /**
     * 
     * @Title: selectCommpairTableId
     * @Description: 查询
     * @param mapQoBean 接收参数
     * @return List<CommpairTablesBean>
     * @author wangjianmin
     */
    @Select("select table_id tableId,wpid,#{granularity} granularity,start,end from commpair_#{granularity}_log_tables "
            + "where " 
            + "((end >= #{startTime} and end <= #{endTime}) or" 
            + "(start <= #{startTime} and end >= #{endTime}) or" 
            +"(start >= #{startTime} and start <= #{endTime}))")
    List<CommpairTablesBean> selectCommpairTableId(MapQoBean mapQoBean);
    
    /**
     * 
     * @Title: selectCommpairDataToMapData
     * @Description: 查询地图所需要的通信对数据
     * @param commpairTablesBeans 接收表数据参数
     * @param mapQoBean  接收Qo参数
     * @return List<MapVoBean>
     * @author wangjianmin
     */
    @SelectProvider(type = MapDaoProvider.class, method = "selectCommpairDataToSql") 
    List<MapVoBean> selectCommpairDataToMapData(@Param("beans") List<CommpairTablesBean> commpairTablesBeans, 
            @Param("mapQoBean") MapQoBean mapQoBean) ;
}
