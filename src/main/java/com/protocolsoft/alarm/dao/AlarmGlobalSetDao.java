/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmGlobalSetDao
 *创建人:chensq    创建时间:2018年3月5日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmGlobalSetBean;

/**
 * @ClassName: AlarmGlobalSetDao
 * @Description: AlarmGlobalSet DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmGlobalSetDao {
   
    /**
     * @Title: getAlarmGlobalSetByParam
     * @Description: 查询set, 根据参数返回告警全局设置集合
     * @param alarmGlobalSetBean
     * @return List<AlarmGlobalSetBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            +" module_id as moduleId, " 
            +" watchpoint_id as watchpointId, " 
            +" kpitype as kpitype, " 
            +" kpi_id as kpiId " 
            +" from " 
            +" ipm_alarm_globalset " 
            +" where " 
            +" module_id = #{moduleId} and "
            +" watchpoint_id = #{watchpointId} and "
            +" kpitype = #{kpitype} and "
            +" kpi_id = #{kpiId}")
    List<AlarmGlobalSetBean> getAlarmGlobalSetByParam(AlarmGlobalSetBean alarmGlobalSetBean);
 
    /**
     * @Title: insertAlarmGlobalSet
     * @Description: 新增 
     * @param alarmGlobalSetBean
     * @return long
     * @author chensq
     */
    @Insert("insert into ipm_alarm_globalset(module_id,watchpoint_id,kpitype,kpi_id) "
            + "values(#{moduleId},#{watchpointId},#{kpitype},#{kpiId})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    long insertAlarmGlobalSet(AlarmGlobalSetBean alarmGlobalSetBean);
    
    /**
     * @Title: deleteAlarmGlobalSet
     * @Description: 删除
     * @param alarmGlobalSetBean void
     * @author chensq
     */
    @Delete("delete from ipm_alarm_globalset where module_id = #{moduleId} and watchpoint_id = #{watchpointId} ")  
    void deleteAlarmGlobalSet(AlarmGlobalSetBean alarmGlobalSetBean);
    
}
