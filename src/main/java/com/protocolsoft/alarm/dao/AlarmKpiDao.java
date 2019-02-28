/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmKpiDao
 *创建人:chensq    创建时间:2017年10月31日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmKpiBean;

/**
 * @ClassName: AlarmKpiDao
 * @Description: AlarmKpi DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmKpiDao {
   
    /**
     * @Title: getAlarmKpiListByModuleId
     * @Description: 增加排序功能(与绘图选项表进行关联)， 查询模块下的kpi
     * @param moduleId
     * @param kpiIdSql
     * @return List<AlarmKpiBean>
     * @author chensq
     */
    @Select("SELECT  "
           + "k.id AS id, " 
           + "k.module_id AS moduleId, " 
           + "k.kpi_id AS kpiId, "
           + "o.name AS displayName "
           + "FROM " 
           + "ipm_alarm_kpi k "
           + "JOIN ipm_plot_option o ON k.kpi_id = o.kpi_id "
           + "WHERE "
           +" k.module_id = ${moduleId} AND o.module_id = ${moduleId} AND (o.number = 1 "
           + "OR (o.number = 0 AND k.kpi_id=2)) "
           + "AND o.id NOT IN(323,324,325) "
           + "${kpiIdSql} "
           +" ORDER BY group_id, `order` ")
    List<AlarmKpiBean> getAlarmKpiListByModuleId(
            @Param("moduleId") long  moduleId,
            @Param("kpiIdSql") String  kpiIdSql);
    
    /**
     * @Title: getAlarmKpiListByModuleId1
     * @Description: 应用可用性专用,查询模块下的kpi
     * @param moduleId
     * @return List<AlarmKpiBean>
     * @author chensq
     */
    @Select("SELECT  "
           + "k.id AS id, " 
           + "k.module_id AS moduleId, " 
           + "k.kpi_id AS kpiId " 
           + "FROM " 
           + "ipm_alarm_kpi k "
           + "WHERE "
           +" k.module_id = ${moduleId} ")
    List<AlarmKpiBean> getAlarmKpiListByModuleId1(
            @Param("moduleId") long  moduleId);
    
    /**
     * @Title: getAlarmKpiById
     * @Description: 查询by Id
     * @param id id
     * @return AlarmKpiBean
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "module_id as moduleId, " 
            + "kpi_id as kpiId "
            + "from " 
            + "ipm_alarm_kpi "
            + "where "
            + "id = ${id} ")
    AlarmKpiBean getAlarmKpiById(@Param("id") long  id);
    
}
