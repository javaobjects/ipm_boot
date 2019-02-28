/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomkpiDao
 *创建人:chensq    创建时间:2017年11月2日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;

/**
 * @ClassName: AlarmCustomkpiDao
 * @Description: AlarmCustomkpi DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmCustomkpiDao {
    /**
     * 
     * @Title: getAlarmCustomKpiListByModuleId
     * @Description: 查询模块下的kpi
     * @param moduleId 业务模块id
     * @return List<AlarmCustomkpiBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "module_id as moduleId, " 
            + "business_id as businessId, " 
            + "namezh as namezh, "
            + "nameen as nameen "
            + "from " 
            + "ipm_alarm_customkpi "
            + "where "
            + "module_id = ${moduleId} ")
    List<AlarmCustomkpiBean> getAlarmCustomKpiListByModuleId(@Param("moduleId") Integer  moduleId);
    
    /**
     * 
     * @Title: getAlarmCustomKpiById
     * @Description: 查询by Id
     * @param id id
     * @return AlarmCustomkpiBean
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "module_id as moduleId, " 
            + "business_id as businessId, " 
            + "namezh as namezh, "
            + "nameen as nameen "
            + "from " 
            + "ipm_alarm_customkpi "
            + "where "
            + "id = ${id} ")
    AlarmCustomkpiBean getAlarmCustomKpiById(@Param("id") long  id);    
    
    /**
     * 
     * @Title: getAlarmCustomKpiByMoBus
     * @Description: 查询by moduleId businessId
     * @param moduleId  
     * @param businessId 
     * @return AlarmCustomkpiBean
     * @author chensq
     */
    @Select("select "
            + "id as id " 
            + "from " 
            + "ipm_alarm_customkpi "
            + "where "
            + "module_id= ${moduleId} and "
            + "business_id = ${businessId} ")
    AlarmCustomkpiBean getAlarmCustomKpiByMoBus(
            @Param("moduleId") long  moduleId, 
            @Param("businessId") long  businessId);    
    
    /**
     * 
     * @Title: delAlarmCustomkpiBusMoid
     * @Description: 删除组合告警设置记录(告警设置ipm_alarm_customkpi)
     * @param moduleId 模块id
     * @param businessId 业务id
     * @return boolean
     * @author chensq
     */
    @Delete("delete  "
            + "from "
            + "ipm_alarm_customkpi "
            + "where "
            + "module_id= ${moduleId} and "
            + "business_id = ${businessId} ")
    boolean delAlarmCustomkpiBusMoid(
            @Param("moduleId") long  moduleId, 
            @Param("businessId") long  businessId);
    
    
}
