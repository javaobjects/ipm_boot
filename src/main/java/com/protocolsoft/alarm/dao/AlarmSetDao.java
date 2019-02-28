/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetDao
 *创建人:chensq    创建时间:2017年11月2日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmSetBean;

/**
 * @ClassName: AlarmSetDao
 * @Description: AlarmSet DAO
 * @author chensq
 *
 */
@Repository
public interface AlarmSetDao {
   
    /**
     * @Title: addAlarmSet
     * @Description: 添加告警阈值设置
     * @param alarmSetBean
     * @return int
     * @author chensq
     */
    @Insert("insert into ipm_alarm_set (watchpoint_id,business_id,kpitype,kpi_id,level_id,updateflag,lowthresh,highthresh) "
            + " values (#{watchpointId},#{businessId},#{kpitype},"
            + "#{kpiId},#{levelId},#{updateflag},#{lowthresh},#{highthresh})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addAlarmSet(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: updateAlarmSetById
     * @Description: 根据id修改告警阈值设置
     * @param alarmSetBean
     * @return int
     * @author chensq
     */
    @Update("update ipm_alarm_set set updateflag=#{updateflag},lowthresh=#{lowthresh},highthresh=#{highthresh} where id=#{id}")
    int updateAlarmSetById(AlarmSetBean alarmSetBean);

    /**
     * @Title: updateAlarmGlobalSet
     * @Description: 根据id修改告警阈值设置(全局用)
     * @param alarmSetBean
     * @return int
     * @author chensq
     */
    @Update("update "
            + "ipm_alarm_set "
            + "set "
            + "lowthresh=#{lowthresh}, "
            + "highthresh=#{highthresh}, "
            + "updateflag=#{updateflag} "
            + "WHERE "
            + "watchpoint_id=#{watchpointId} "
            + "and business_id =#{businessId} "
            + "and kpitype=#{kpitype} "
            + "and kpi_id = #{kpiId} "
            + "and level_id = #{levelId}")
    int updateAlarmGlobalSet(AlarmSetBean alarmSetBean);
            
    /**
     * @Title: getAlarmSetById
     * @Description: 根据id查询AlarmSet对象
     * @param id alarmSet id
     * @return AlarmSetBean
     * @author chensq
     */
    @Select("select "
            + "s.id as id, " 
            + "s.watchpoint_id as watchpointId, " 
            + "s.business_id as businessId, "
            + "s.kpitype as kpitype, "
            + "s.kpi_id as kpiId, "            
            + "s.level_id as levelId, "        
            + "s.updateflag as updateflag, "            
            + "s.lowthresh as lowthresh, "                  
            + "s.highthresh as highthresh, "    
            + "ak.module_id as moduleId "                        
            + "from " 
            + "(SELECT s1.* FROM ipm_alarm_set s1 WHERE s1.id=${id} ) s "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id AND s.kpitype=1 "
            + "where "
            + "s.id = ${id} ")
    AlarmSetBean getAlarmSetById(@Param("id") long id);
    
    /**
     * @Title: getAlarmSetIdByParam
     * @Description: 自定义组合告警insert使用
     * @param alarmSetBean
     * @return Long
     * @author chensq
     */
    @Select("select "
            +"s.id as id "
            +"FROM "
            +"ipm_alarm_set s "
            +"LEFT JOIN ipm_alarm_customkpi ac ON s.kpi_id=ac.id AND s.kpitype=2 "
            +"WHERE "
            +"ac.module_id=#{moduleId} AND "
            +"ac.business_id=#{businessId} AND "
            +"ac.business_id=s.business_id AND "
            +"level_id = #{levelId} ")
    Long getAlarmSetIdByParam(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmSetListType1
     * @Description: 原生使用, 查询watchpointId 与  businessId 下的AlarmSet集合
     * @param moduleId
     * @param watchpointId
     * @param businessId
     * @param kpitype kpi类型(原生)
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.id as id, " 
            + "s.watchpoint_id as watchpointId, " 
            + "s.business_id as businessId, "
            + "s.kpitype as kpitype, "
            + "s.kpi_id as kpiId, "            
            + "s.level_id as levelId, "        
            + "s.updateflag as updateflag, "            
            + "s.lowthresh as lowthresh, "         
            + "s.highthresh as highthresh "                        
            + "from " 
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_kpi ak ON s.kpi_id = ak.id  "
            + "where "
            + "ak.module_id = ${moduleId} and "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} and "
            + "s.kpitype = ${kpitype} ")
    List<AlarmSetBean> getAlarmSetListType1(
            @Param("moduleId") Integer  moduleId, 
            @Param("watchpointId") Integer  watchpointId, 
            @Param("businessId") Integer  businessId,
            @Param("kpitype") Integer  kpitype);
    
    /**
     * @Title: getAlarmSetListType2
     * @Description: 自定义kpi使用, 查询watchpointId 与  businessId 下的AlarmSet集合
     * @param moduleId
     * @param watchpointId
     * @param businessId
     * @param kpitype kpi类型(自定义)
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.id as id, " 
            + "s.watchpoint_id as watchpointId, " 
            + "s.business_id as businessId, "
            + "s.kpitype as kpitype, "
            + "s.kpi_id as kpiId, "            
            + "s.level_id as levelId, "        
            + "s.updateflag as updateflag, "            
            + "s.lowthresh as lowthresh, "     
            + "s.highthresh as highthresh "                        
            + "from " 
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_customkpi ac ON s.kpi_id = ac.id  "
            + "where "
            + "ac.module_id = ${moduleId} and "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} and "
            + "s.kpitype = ${kpitype} ")
    List<AlarmSetBean> getAlarmSetListType2(
            @Param("moduleId") Integer  moduleId, 
            @Param("watchpointId") Integer  watchpointId, 
            @Param("businessId") Integer  businessId,
            @Param("kpitype") Integer  kpitype);
       
    /**
     * @Title: delAlarmSetByBusWPid
     * @Description: 删除告警设置记录(告警设置ipm_alarm_set),原生kpi
     * @param moduleId
     * @param watchpointId
     * @param businessId
     * @return boolean
     * @author chensq
     */
    @Delete("delete s "
            + "from "
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_kpi ak ON s.kpi_id = ak.id and s.kpitype=1 "
            + "where "
            + "ak.module_id= ${moduleId} and "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} ")
    boolean delAlarmSetByBusWPid(
            @Param("moduleId") Integer  moduleId, 
            @Param("watchpointId") Integer  watchpointId, 
            @Param("businessId") Integer  businessId);
        
    /**
     * @Title: delCustomUnAlarmSetByBusWPid
     * @Description: 自定义kpi,删除告警设置记录(告警设置ipm_alarm_set)
     * @param moduleId
     * @param watchpointId
     * @param businessId
     * @return boolean
     * @author chensq
     */
    @Delete("delete s "
            + "from "
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_customkpi ck ON s.kpi_id = ck.id and s.kpitype=2 "
            + "where "
            + "ck.module_id= ${moduleId} and "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} ")
    boolean delCustomUnAlarmSetByBusWPid(
            @Param("moduleId") Integer  moduleId, 
            @Param("watchpointId") Integer  watchpointId, 
            @Param("businessId") Integer  businessId);
    
    /**
     * 
     * @Title: getAlarmSetListByKpiId(规则kpi)
     * @Description: 根据kpi_id查询告警阈值设置信息
     * @param watchpointId 观察点id
     * @param businessId 业务id
     * @param kpitype kpi类型
     * @param kpiId kpiid
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.id as id, " 
            + "s.watchpoint_id as watchpointId, " 
            + "s.business_id as businessId, "
            + "s.kpitype as kpitype, "
            + "s.kpi_id as kpiId, "            
            + "s.level_id as levelId, "        
            + "s.updateflag as updateflag, "            
            + "s.lowthresh as lowthresh, "     
            + "s.highthresh as highthresh, "     
            + "k.name as kpiName, "                        
            + "k.display_name as kpiDisplayName "                        
            + "from " 
            + "ipm_alarm_set s "
            + "left join ipm_alarm_kpi ak ON s.kpi_id = ak.id "
            + "left join ipm_kpis k ON ak.kpi_id= k.id "
            + "where "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} and "
            + "ak.module_id = ${moduleId} and "
            + "s.kpitype = ${kpitype} and "
            + "s.kpi_id = ${kpiId} ")
    List<AlarmSetBean> getAlarmSetListByKpiId(
            @Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId,
            @Param("moduleId") long  moduleId,
            @Param("kpitype")  int kpitype,
            @Param("kpiId")  long kpiId);    
    
    /**
     * 
     * @Title: getCustomAlarmSetListByKpiId(自定义kpi)
     * @Description: 根据kpi_id查询告警阈值设置信息
     * @param watchpointId 观察点id
     * @param businessId 业务id
     * @param kpitype kpi类型
     * @param kpiId kpiid
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.id as id, " 
            + "s.watchpoint_id as watchpointId, " 
            + "s.business_id as businessId, "
            + "s.kpitype as kpitype, "
            + "s.kpi_id as kpiId, "            
            + "s.level_id as levelId, "        
            + "s.updateflag as updateflag, "            
            + "s.lowthresh as lowthresh, "     
            + "s.highthresh as highthresh, "     
            + "ck.nameen as kpiName, "                        
            + "ck.namezh as kpiDisplayName "          
            + "from " 
            + "ipm_alarm_set s "
            + "left join ipm_alarm_customkpi ck ON s.kpi_id = ck.id "
            + "where "
            + "s.watchpoint_id = ${watchpointId} and "
            + "s.business_id = ${businessId} and "
            + "s.kpitype = ${kpitype} and "
            + "s.kpi_id = ${kpiId} ")
    List<AlarmSetBean> getCustomAlarmSetListByKpiId(
            @Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId,
            @Param("kpitype")  int kpitype,
            @Param("kpiId")  long kpiId);    
    
    /**
     * 
     * @Title: getAlarmSetListByAlarmKpiIds
     * @Description: 查询 watchpointId businessId alarmKpiIds 下的AlarmSet集合
     * @param watchpointId 观察点id
     * @param businessId 业务id
     * @param alarmKpiIds alarmkpi ids
     * @param levelIdsSql level ids sql
     * @param kpitypeSql kpitype sql
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "watchpoint_id as watchpointId, " 
            + "business_id as businessId, "
            + "kpitype as kpitype, "
            + "kpi_id as kpiId, "            
            + "level_id as levelId, "        
            + "updateflag as updateflag, "            
            + "lowthresh as lowthresh, " 
            + "highthresh as highthresh "                        
            + "from " 
            + "ipm_alarm_set "
            + "where "
            + "watchpoint_id = ${watchpointId} and "
            + "business_id = ${businessId} and "
            + "kpi_id in (${alarmKpiIds}) "
            + "${levelIdsSql} "
            + "${kpitypeSql} ")
    List<AlarmSetBean> getAlarmSetListByAlarmKpiIds(
            @Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId,
            @Param("alarmKpiIds") String  alarmKpiIds,
            @Param("levelIdsSql") String  levelIdsSql,
            @Param("kpitypeSql") String  kpitypeSql);
    
    /**
     * @Title: getAlarmGlobalSetByParam
     * @Description: 根据告警设置ids查询出模块id 与 kpi_id  与 业务id等,根据参数返回告警设置集合
     * @param alarmsetIds
     * @return AlarmSetBean
     * @author chensq
     */
    @Select("SELECT "
            +"s.id as id, "
            +"s.watchpoint_id as watchpointId, "
            +"s.business_id as businessId, "
            +"s.kpitype as kpitype, "
            +"s.kpi_id as kpiId, "
            +"s.level_id as levelId, "
            +"s.updateflag as updateflag, "
            +"s.lowthresh as lowthresh, "
            +"s.highthresh as highthresh, "
            +"ak.module_id  as moduleId "
            +"FROM "
            +"ipm_alarm_set s "
            +"LEFT JOIN ipm_alarm_kpi  ak ON s.kpi_id=ak.id "
            +"WHERE s.id IN (${alarmsetIds}) "
            +"GROUP BY kpitype,kpiId ")
    AlarmSetBean getAlarmGlobalSetByParam(@Param("alarmsetIds")  String alarmsetIds);
        
    /**
     * @Title: getAlarmGlobalStandardSetByParam
     * @Description: 根据告警设置ids查询出模块id 与 kpi_id  与 业务id等(基准使用),根据参数返回告警设置集合
     * @param alarmsetIds
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("SELECT "
            +"s.id as id, "
            +"s.watchpoint_id as watchpointId, "
            +"s.business_id as businessId, "
            +"s.kpitype as kpitype, "
            +"s.kpi_id as kpiId, "
            +"s.level_id as levelId, "
            +"s.updateflag as updateflag, "
            +"s.lowthresh as lowthresh, "
            +"s.highthresh as highthresh, "
            +"ak.module_id  as moduleId "
            +"FROM "
            +"ipm_alarm_set s "
            +"LEFT JOIN ipm_alarm_kpi  ak ON s.kpi_id=ak.id "
            +"WHERE s.id IN (${alarmsetIds}) "
            +"ORDER BY FIELD(s.id,${alarmsetIds}) ")
    List<AlarmSetBean> getAlarmGlobalStandardSetByParam(@Param("alarmsetIds")  String alarmsetIds);
        
    /**
     * @Title: getAlarmUsabilitySetByParam
     * @Description: 查询应用可用性的告警设置信息,根据参数返回告警设置对象
     * @param alarmSetBean
     * @return AlarmSetBean
     * @author chensq
     */
    @Select("SELECT "
            +"s.id as id, "
            +"s.watchpoint_id as watchpointId, "
            +"s.business_id as businessId, "
            +"s.kpitype as kpitype, "
            +"s.kpi_id as kpiId, "
            +"s.level_id as levelId, "
            +"s.updateflag as updateflag, "
            +"s.lowthresh as lowthresh, "
            +"s.highthresh as highthresh "
            +"FROM "
            +"ipm_alarm_set s "
            +"WHERE "
            +"s.watchpoint_id=#{watchpointId} and "
            +"s.business_id=#{businessId} and "
            +"s.kpi_id=#{kpiId} and "
            +"s.level_id=#{levelId} ")
    AlarmSetBean getAlarmUsabilitySetByParam(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmSetIdLevel
     * @Description: 查询告警设置id对应的级别,根据参数返回告警设置对象
     * @param alarmSetBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("SELECT "
            +"s.id as id, "
            +"s.level_id as levelId "
            +"FROM "
            +"ipm_alarm_set s "
            +"WHERE "
            +"s.id in (${idList}) "
            +"ORDER BY "
            +"FIELD(s.id,${idList}) ")
    List<AlarmSetBean> getAlarmSetIdLevel(AlarmSetBean alarmSetBean);
        
}

 