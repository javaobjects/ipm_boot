/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名: AlarmBaseLineDao.java
 *创建人: chensq    创建时间: 2018年5月24日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmBaseLineBean;

/**
 * @ClassName: AlarmBaseLineDao
 * @Description: 智能告警dao控制类
 * @author chensq
 *
 */
@Repository
public interface AlarmBaseLineDao {

    /**
     * 
     * @Title: getAlarmBaseLineByParam
     * @Description: 查询BaseLine信息,根据条件
     * @return AlarmBaseLineBean
     * @author chensq
     */
    @Select("select "
            + "a.id as id, "
            + "a.watchpoint_id as watchpointId, "
            + "a.module_id as moduleId, "
            + "a.business_id as businessId, "
            + "a.kpi_id as kpiId, "
            + "a.temp_flag as tempFlag, "
            + "a.start_time as startTime, "
            + "a.end_time as endTime "
            + "from "
            + "ipm_alarm_baseline a "
            + "where "
            + "a.watchpoint_id=#{watchpointId} and "
            + "a.module_id=#{moduleId} and "
            + "a.business_id=#{businessId} and "
            + "a.kpi_id=#{kpiId} ")
    AlarmBaseLineBean getAlarmBaseLineByParam(AlarmBaseLineBean alarmBaseLineBean);
    
    /**
     * 
     * @Title: getAlarmBaseLineOutKpiId
     * @Description: 查询BaseLine信息,根据不区分kpi_id的条件
     * @return List<AlarmBaseLineBean>
     * @author chensq
     */
    @Select("select "
            + "a.id as id, "
            + "a.watchpoint_id as watchpointId, "
            + "a.module_id as moduleId, "
            + "a.business_id as businessId, "
            + "a.kpi_id as kpiId, "
            + "a.temp_flag as tempFlag, "
            + "k.name as kpiName, "         
            + "a.start_time as startTime, "
            + "a.end_time as endTime "
            + "from "
            + "ipm_alarm_baseline a "
            + "left join ipm_kpis k ON k.id=a.kpi_id "
            + "where "
            + "a.watchpoint_id=#{watchpointId} and "
            + "a.module_id=#{moduleId} and "
            + "a.business_id=#{businessId} ")
    List<AlarmBaseLineBean> getAlarmBaseLineOutKpiId(AlarmBaseLineBean alarmBaseLineBean);
    
    /**
     * @Title: addAlarmBaseLine
     * @Description: 添加智能告警设置
     * @param alarmBaseLineBean
     * @return int
     * @author chensq
     */
    @Insert("insert into ipm_alarm_baseline "
            + "(watchpoint_id,module_id,business_id,kpi_id,temp_flag,start_time,end_time) "
            + " values "
            + "(#{watchpointId},#{moduleId},#{businessId},#{kpiId},#{tempFlag},#{startTime},#{endTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addAlarmBaseLine(AlarmBaseLineBean alarmBaseLineBean);

    /**
     * @Title: delAlarmBaseLine
     * @Description: 删除基线设置
     * @param alarmBaseLineBean
     * @return boolean
     * @author chensq
     */
    @Delete("delete "
            + "from "
            + "ipm_alarm_baseline  "
            + "where "
            + "watchpoint_id=#{watchpointId} and "
            + "module_id=#{moduleId} and "
            + "business_id=#{businessId} ")
    boolean delAlarmBaseLine(AlarmBaseLineBean alarmBaseLineBean);
    
    /**
     * @Title: delAlarmBaseLineByKpiId
     * @Description: 删除基线设置，根据业务与业务的kpi
     * @param alarmBaseLineBean
     * @return boolean
     * @author chensq
     */
    @Delete("delete "
            + "from "
            + "ipm_alarm_baseline  "
            + "where "
            + "watchpoint_id=#{watchpointId} and "
            + "module_id=#{moduleId} and "
            + "business_id=#{businessId} and "
            + "kpi_id=#{kpiId} ")
    boolean delAlarmBaseLineByKpiId(AlarmBaseLineBean alarmBaseLineBean);
    
    /**
     * @Title: updateAlarmBaseLineEndTime
     * @Description: 修改智能告警设置的结束时间
     * @param alarmBaseLineBean
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_baseline a set "
            + "a.end_time=#{endTime} "
            + "where "
            + "a.watchpoint_id=#{watchpointId} and "
            + "a.module_id=#{moduleId} and "
            + "a.business_id=#{businessId} and "
            + "a.kpi_id=#{kpiId} ")
    int updateAlarmBaseLineEndTime(AlarmBaseLineBean alarmBaseLineBean);
    
    /**
     * @Title: updateAlarmBaseLineStartTimeByParam
     * @Description: 根据对象条件修改智能告警设置的开始时间,结束时间
     * @param alarmBaseLineBean
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_baseline a set "
            + "a.start_time=a.start_time+#{stepTime}, "
            + "a.end_time=a.end_time+#{stepTime} "
            + "where "
            + "a.watchpoint_id=#{watchpointId} and "
            + "a.module_id=#{moduleId} and "
            + "a.business_id=#{businessId} and "
            + "a.kpi_id=#{kpiId} ")
    int updateAlarmBaseLineStartEndTimeByParam(AlarmBaseLineBean alarmBaseLineBean);
    
    
    /**
     * 
     * @Title: getAlarmBaseLineMinEndTimeByParam
     * @Description: 查询业务下的最小开始时间
     * @return AlarmBaseLineBean
     * @author chensq
     */
    @Select("select "
            + "min(a.end_time) as endTime "
            + "from "
            + "ipm_alarm_baseline a "
            + "where "
            + "a.watchpoint_id=#{watchpointId} and "
            + "a.module_id=#{moduleId} and "
            + "a.business_id=#{businessId} ")
    long getAlarmBaseLineMinEndTimeByParam(AlarmBaseLineBean alarmBaseLineBean);
    
}
