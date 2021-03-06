/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmCustomUnionKpiDao
 *创建人:chensq    创建时间:2018年4月4日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.protocolsoft.alarm.bean.AlarmCustomkpiBean;
import com.protocolsoft.alarm.bean.AlarmCustomkpigroupBean;
import com.protocolsoft.alarm.bean.AlarmLogBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;


/**
 * @ClassName: AlarmCustomUnionKpiDao
 * @Description: 组合告警kpi使用的dao
 * @author chensq
 *
 */
public interface AlarmCustomUnionKpiDao {
         
    /**
     * @Title: getAlarmCanUsedSetIdsList
     * @Description: 查询set ids-Str 根据参数返回告警ids str设置集合
     * @param alarmSetBean
     * @return String
     * @author chensq
     */
    @Select("SELECT "
             +"GROUP_CONCAT(s.id) as idList "
             +"FROM "
             +"ipm_alarm_set s "
             +"LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id "
             +"WHERE "
             +"s.watchpoint_id = #{watchpointId} AND "
             +"ak.module_id=#{moduleId} AND "
             +"s.business_id=#{businessId} AND "
             +"s.kpitype=1 AND "
             +"s.updateflag ='Y' AND "
             +"s.level_id!=1  "
             +"ORDER BY s.kpi_id ")
    String getAlarmCanUsedSetIdsList(AlarmSetBean alarmSetBean);
   
    /**
     * @Title: getAlarmCanUsedKpisList
     * @Description: 查询set 根据参数返回告警kpis设置集合
     * @param alarmSetBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("SELECT "
            + "k.id AS kpiId, "
            + "k.name AS kpiName, "
            + "k.display_name AS kpiDisplayName, "
            + "k.unit AS unit "
            + "FROM  "
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id "
            + "LEFT JOIN ipm_kpis k ON k.id=ak.kpi_id "
            + "LEFT JOIN ( "
                        + "SELECT "
                        + "a.*  "
                        + "FROM "
                        + "ipm_alarm_trigger a  "
                        + "LEFT JOIN ipm_alarm_trigger b ON a.alarmset_id=b.alarmset_id  AND a.triggertime<=b.triggertime "
                        + "${idsInSql} "
                        + "GROUP BY a.id,a.alarmset_id,a.triggerflag,a.triggertime "
                        + "HAVING COUNT(b.id)<=2 "
                        + "ORDER BY a.alarmset_id,a.triggertime DESC "
             + ") a ON s.id=a.alarmset_id "
             + "WHERE "
             + "s.watchpoint_id = #{watchpointId} AND "
             + "ak.module_id=#{moduleId} AND "
             + "s.business_id=#{businessId} AND "
             + "s.kpitype=1 AND "
             + "s.updateflag ='Y' AND "
             + "s.level_id!=1 AND "
             + "a.triggerusestatus =0  "
             + "GROUP BY kpiId ")
    List<AlarmSetBean> getAlarmCanUsedKpisList(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmUsedKpisList
     * @Description: 查询模块下的业务已经设置过的kpi id,根据参数返回已设置告警kpis设置集合
     * @param alarmCustomkpiBean
     * @return List<AlarmCustomkpigroupBean>
     * @author chensq
     */
    @Select("SELECT "
    + "k.id as kpiId "
    + "FROM "
    + "ipm_alarm_customkpi ck "
    + "LEFT JOIN   ipm_alarm_customkpigroup ag ON ag.customkpi_id=ck.id "
    + "LEFT JOIN   ipm_kpis k ON k.id = ag.kpi_id "
    + "WHERE "
    + "ck.module_id= #{moduleId} AND ck.business_id =#{businessId} "
    + "GROUP BY kpiId ")
    List<AlarmCustomkpigroupBean> getAlarmUsedKpisList(AlarmCustomkpiBean alarmCustomkpiBean);
    
    /**
     * @Title: getAlarmUnKpisByParam
     * @Description: 多kpi告警设置查询 
     * @param alarmCustomkpiBean
     * @return List<AlarmCustomkpiBean>
     * @author chensq
     */
    @Select("SELECT "
            + "ck.id as id, "
            + "ck.namezh as namezh, "
            + "ck.namezh as groupKpiName "            
            + "FROM "
            + "ipm_alarm_customkpi ck "
            + "WHERE "
            + "ck.module_id= #{moduleId} AND ck.business_id =#{businessId} ")
    List<AlarmCustomkpiBean> getAlarmUnKpisByParam(AlarmCustomkpiBean alarmCustomkpiBean);
    
    /**
     * @Title: updateAlarmSetByIds
     * @Description: 修改组合kpi告警
     * @param alarmCustomkpiBean
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_customkpi ck set "
            + "namezh=#{groupKpiName} "
            + "WHERE "
            + "ck.id= #{id} ")
    int updateAlarmSetByIds(AlarmCustomkpiBean alarmCustomkpiBean);
    
    /**
     * 
     * @Title: delAlarmCustomKpsByCusId
     * @Description: 根据多阈值表
     * @param customkpiId
     * @return boolean
     * @author chensq
     */
    @Delete("delete from ipm_alarm_customkpigroup where customkpi_id=${customkpiId}")
    boolean delAlarmCustomKpsByCusId(@Param("customkpiId") long  customkpiId);
    
    /**
     * @Title: addCustomkpigroup
     * @Description: 添加组合告警设置
     * @param alarmCustomkpigroupBean
     * @return int
     * @author chensq
     */
    @Insert("insert into ipm_alarm_customkpigroup "
            + "(customkpi_id,kpi_id) "
            + " values "
            + "(#{customkpiId},#{kpiId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCustomkpigroup(AlarmCustomkpigroupBean alarmCustomkpigroupBean);
     
    /**
     * @Title: addCustomkpi
     * @Description: 添加组合告警设置
     * @param alarmCustomkpiBean
     * @return int
     * @author chensq
     */
    @Insert("insert into ipm_alarm_customkpi (module_id,business_id,namezh,nameen) "
            + " values (#{moduleId},#{businessId},#{groupKpiName},#{nameen})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCustomkpi(AlarmCustomkpiBean alarmCustomkpiBean);
        
    /**
     * @Title: getAlarmUnKpiByLogId
     * @Description: 查询组合告警对应设置的信息
     * @param alarmLogBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("SELECT "
            +"l.watchpoint_id as watchpointId, "
            +"ac.module_id as moduleId, "
            +"ac.business_id as businessId, "
            +"ag.kpi_id as kpiId "
            +"FROM ipm_alarm_log l "
            +"LEFT JOIN ipm_alarm_set s ON l.alarmset_id=s.id "
            +"LEFT JOIN ipm_alarm_customkpi ac ON ac.id =s.kpi_id AND s.kpitype=2 "
            +"LEFT JOIN ipm_alarm_customkpigroup ag ON ac.id=ag.customkpi_id "
            +"WHERE l.id =#{id} ")
    List<AlarmSetBean> getAlarmUnKpiByLogId(AlarmLogBean alarmLogBean);
    
    /**
     * @Title: getAlarmUnionEdSid
     * @Description: 组合kpi告警使用,获取alarmSet id
     * @param alarmSetBean
     * @return Long
     * @author chensq
     */
    @Select("SELECT "
            +"s.id AS id "
            +"FROM ipm_alarm_log l "
            +"LEFT JOIN ipm_alarm_set s ON l.alarmset_id = s.id "
            +"LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id  AND s.kpitype=1 "
            +"LEFT JOIN ipm_kpis k ON k.id= ak.kpi_id "
            +"WHERE "
            +"k.id=#{kpiId} "
            +"AND l.watchpoint_id=#{watchpointId} "
            +"AND ak.module_id=#{moduleId} "
            +"AND s.business_id=#{businessId} "
            +"AND s.level_id!=1 "
            +"AND l.starttime>=#{starttime} "
            +"AND l.endtime<=#{endtime} "
            +"GROUP BY id "
            +"ORDER BY s.id ASC ")
    Long getAlarmUnionEdSid(AlarmSetBean alarmSetBean);    
    
}
