/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmSetThreadDao
 *创建人:chensq    创建时间:2017年11月13日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmCustomkpigroupBean;
import com.protocolsoft.alarm.bean.AlarmSetBean;
/**
 * @ClassName: AlarmSetThreadDao
 * @Description: AlarmSetThreadDao Dao
 * @author chensq
 *
 */
@Repository
public interface AlarmSetThreadDao {

    /**
     * 
     * @Title: getAsetGroupByWpId
     * @Description: 查询告警设置group by watchpoint_id
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.watchpoint_id as watchpointId " 
            + "from " 
            + "ipm_alarm_set s "
            + "where "
            + "s.updateflag = 'Y'  "
            + "group by s.watchpoint_id ")
    List<AlarmSetBean> getAsetGroupByWpId();   
    
    /**
     * 
     * @Title: getAsetGroupByBusId
     * @Description: 查询告警设置group by business_id
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select DISTINCT "
            + "s.business_id as businessId, " 
            + "ak.module_id as moduleId " 
            + "from " 
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id AND s.kpitype=1 "
            + "where "
            + "s.watchpoint_id = ${watchpointId} AND  "
            + "s.updateflag = 'Y' "
            + "GROUP BY s.business_id,ak.module_id ")
    List<AlarmSetBean> getAsetGroupByBusId(@Param("watchpointId") long  watchpointId);
    
    /**
     * 
     * @Title: getAsetGroupBykpitype
     * @Description: 查询告警设置group by kpitype
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select "
            + "s.kpitype as kpitype " 
            + "from " 
            + "ipm_alarm_set s "
            + "where "
            + "s.watchpoint_id = ${watchpointId} and  "
            + "s.business_id = ${businessId} and  "
            + "s.updateflag = 'Y'  "
            + "group by s.kpitype ")
    List<AlarmSetBean> getAsetGroupBykpitype(@Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId);
    
    /**
     * 
     * @Title: getAsetGroupBykpiId
     * @Description: 查询告警设置group by kpi_id
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
            + "IF(ak.module_id IS NULL,ac.module_id,ak.module_id) AS moduleId "
            + "from " 
            + "ipm_alarm_set s "
            + "LEFT JOIN ipm_alarm_kpi     ak     ON   ak.id = s.kpi_id AND s.kpitype=1 "
            + "LEFT JOIN ipm_alarm_customkpi ac   ON   ac.id = s.kpi_id AND s.kpitype=2 "
            + "where "
            + "(ak.module_id=${moduleId} OR ac.module_id=${moduleId}) AND "
            + "s.watchpoint_id = ${watchpointId} and  "
            + "s.business_id = ${businessId} and  "
            + "s.updateflag = 'Y'  "
            + "group by s.kpi_id,s.kpitype ")
    List<AlarmSetBean> getAsetGroupBykpiId(
            @Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId,
            @Param("moduleId") long  moduleId);
            
    /**
     * 
     * @Title: getAsetGroupByLevelId
     * @Description: 查询告警设置group by kpitype
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
            + "where "
            + "s.watchpoint_id = ${watchpointId} and  "
            + "s.business_id = ${businessId} and  "
            + "s.kpitype = ${kpitype} and  "
            + "s.kpi_id = ${kpiId} and  "
            + "s.updateflag = 'Y'  "
            + "group by s.level_id "
            + "order by s.level_id ")
    List<AlarmSetBean> getAsetGroupByLevelId(
            @Param("watchpointId") long  watchpointId, 
            @Param("businessId") long  businessId,
            @Param("kpitype") int  kpitype,
            @Param("kpiId")  long kpiId);
    
    /**
     * @Title: getAsetGroupByBaseHighLowTrigger
     * @Description: 基本的阈值告警使用,evel_id!=1(除去基线),triggerflag 0高 1低,根据告警条件查询alarmset与AlarmTrigger
     * @param alarmSetBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select " 
            + "s.id AS id,  " 
            + "s.watchpoint_id AS watchpointId,  " 
            + "s.business_id AS businessId,  " 
            + "s.kpitype AS kpitype,  " 
            + "s.kpi_id AS kpiId, " 
            + "s.level_id AS levelId,   "  
            + "s.updateflag AS updateflag,   "  
            + "s.lowthresh AS lowthresh, " 
            + "s.highthresh AS highthresh, " 
            + "a.triggerflag AS triggerflag, " 
            + "a.triggerusestatus AS triggerusestatus, " 
            + "a.triggerunit AS triggerunit, " 
            + "a.triggertime AS  triggertime, "
            + "ak.module_id AS moduleId "
            + "FROM ipm_alarm_set s  " 
            + "LEFT JOIN ( " 
            + "SELECT a.*  " 
            + "FROM " 
            + "ipm_alarm_trigger a  " 
            + "LEFT JOIN ipm_alarm_trigger b ON a.alarmset_id=b.alarmset_id  AND a.triggertime<=b.triggertime " 
            + "WHERE a.alarmset_id IN (${triggerSearchSetIds}) AND  b.alarmset_id IN (${triggerSearchSetIds}) "
            + "GROUP BY a.id,a.alarmset_id,a.alarmset_id,a.triggerflag,a.triggertime " 
            + "HAVING COUNT(b.id)<=2 " 
            + "ORDER BY a.alarmset_id,a.triggertime DESC " 
            + ") a ON s.id=a.alarmset_id " 
            + "left join ipm_alarm_kpi ak on ak.id=s.kpi_id and s.kpitype=1 " 
            + "WHERE " 
            + "s.watchpoint_id = #{watchpointId} AND   " 
            + "s.business_id = #{businessId} AND   " 
            + "s.kpitype =#{kpitype} AND   "
            + "s.kpi_id = #{kpiId} AND  " 
            + "s.updateflag = 'Y' AND "
            + "ak.module_id = #{moduleId} AND "
            + "triggerusestatus = 0 AND "
            + "triggerflag = #{triggerflag} AND "
            + "s.level_id != #{levelId} ")
    List<AlarmSetBean> getAsetGroupByBaseHighLowTrigger(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmSetStrNative
     * @Description: 查找对应的alarmset ids(native情况)
     * @param alarmSetBean
     * @return String
     * @author chensq
     */
    @Select("select "  
             +"GROUP_CONCAT(s.id) " 
             +"FROM ipm_alarm_set s "
             +"LEFT JOIN ipm_alarm_kpi k ON s.kpi_id=k.id AND s.kpitype=#{kpitype} "
             +"WHERE "
             +"s.watchpoint_id =#{watchpointId} AND  "
             +"k.module_id=#{moduleId} AND  "
             +"s.business_id =#{businessId} AND  "
             +"s.kpitype =#{kpitype} AND  "
             +"s.kpi_id =#{kpiId} AND  "
             +"s.updateflag = 'Y' AND  "
             +"s.level_id !=1 ")
    String getAlarmSetStrNative(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmSetStrCustom
     * @Description: 查找对应的alarmset ids(custom情况)
     * @param alarmSetBean
     * @return String
     * @author chensq
     */
    @Select("select "  
             +"GROUP_CONCAT(s.id) " 
             +"FROM ipm_alarm_set s "
             +"LEFT JOIN ipm_alarm_customkpi k ON s.kpi_id=k.id AND s.kpitype=#{kpitype} "
             +"WHERE "
             +"s.watchpoint_id =#{watchpointId} AND  "
             +"k.module_id=#{moduleId} AND  "
             +"s.business_id =#{businessId} AND  "
             +"s.kpitype =#{kpitype} AND  "
             +"s.kpi_id =#{kpiId} AND  "
             +"s.updateflag = 'Y' AND  "
             +"s.level_id !=1 ")
    String getAlarmSetStrCustom(AlarmSetBean alarmSetBean);
    
    /**
     * @Title: getAlarmSetBaseLineStr
     * @Description: 查找对应的alarmset ids 基线用
     * @param alarmSetBean
     * @return String
     * @author chensq
     */
    @Select("select "  
             +"GROUP_CONCAT(s.id) " 
             +"FROM ipm_alarm_set s "
             +"LEFT JOIN ipm_alarm_kpi k ON s.kpi_id=k.id AND s.kpitype=#{kpitype} "
             +"WHERE "
             +"s.watchpoint_id =#{watchpointId} AND  "
             +"k.module_id=#{moduleId} AND  "
             +"s.business_id =#{businessId} AND  "
             +"s.kpitype =#{kpitype} AND  "
             +"s.kpi_id =#{kpiId} AND  "
             +"s.updateflag = 'Y' AND  "
             +"s.level_id =1 ")
    String getAlarmSetBaseLineStr(AlarmSetBean alarmSetBean);
          
    /**
     * @Title: getAsetGroupByBaseLineHighLowTrigger
     * @Description: 基线的阈值告警使用,level_id=1(基线),根据告警条件查询alarmset与AlarmTrigger
     * @param alarmSetBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("select " 
            + "s.id AS id,  " 
            + "s.watchpoint_id AS watchpointId,  " 
            + "s.business_id AS businessId,  " 
            + "s.kpitype AS kpitype,  " 
            + "s.kpi_id AS kpiId, " 
            + "s.level_id AS levelId,   "  
            + "s.updateflag AS updateflag,   "  
            + "s.lowthresh AS lowthresh, " 
            + "s.highthresh AS highthresh, " 
            + "a.triggerflag AS triggerflag, " 
            + "a.triggerusestatus AS triggerusestatus, " 
            + "a.triggerunit AS triggerunit, " 
            + "a.triggertime AS triggertime, " 
            + "ak.module_id AS moduleId "
            + "FROM ipm_alarm_set s  " 
            + "LEFT JOIN ( " 
            + "SELECT a.*  " 
            + "FROM " 
            + "ipm_alarm_trigger a  " 
            + "LEFT JOIN ipm_alarm_trigger b ON a.alarmset_id=b.alarmset_id  AND a.triggertime<=b.triggertime " 
            + "WHERE a.alarmset_id IN (${triggerSearchSetIds}) AND  b.alarmset_id IN (${triggerSearchSetIds}) "
            + "GROUP BY a.id,a.alarmset_id,a.alarmset_id,a.triggerflag,a.triggertime " 
            + "HAVING COUNT(b.id)<=2 " 
            + "ORDER BY a.alarmset_id,a.triggertime DESC " 
            + ") a ON s.id=a.alarmset_id " 
            + "left join ipm_alarm_kpi ak on ak.id=s.kpi_id and s.kpitype=1 " 
            + "WHERE " 
            + "s.watchpoint_id = #{watchpointId} AND   " 
            + "s.business_id = #{businessId} AND   " 
            + "s.kpitype =#{kpitype} AND   "
            + "s.kpi_id = #{kpiId} AND  " 
            + "s.updateflag = 'Y' AND "
            + "ak.module_id = #{moduleId} AND "
            + "triggerusestatus = 0 AND "
            + "s.level_id = #{levelId} ")
    List<AlarmSetBean> getAsetGroupByBaseLineHighLowTrigger(AlarmSetBean alarmSetBean);

    /**
     * @Title: getAsetGroupByCustomUnionKpi
     * @Description: 组合kpi告警使用
     * @param alarmSetBean
     * @return List<AlarmCustomkpigroupBean>
     * @author chensq
     */
    @Select("SELECT " 
            +"acg.kpi_id as kpiId  "
            +"FROM  "
            +"ipm_alarm_customkpigroup acg "
            +"LEFT JOIN ipm_alarm_customkpi ac ON acg.customkpi_id=ac.id "
            +"WHERE  "
            +"ac.module_id =#{moduleId} AND ac.business_id =#{businessId} ")
    List<AlarmCustomkpigroupBean> getAsetGroupByCustomUnionKpi(AlarmSetBean alarmSetBean);
 
    /**
     * @Title: getAsetGroupByCustomUnionAlarmSetId
     * @Description: 组合kpi告警使用,获取alarmSet id
     * @param alarmSetBean
     * @return List<AlarmSetBean>
     * @author chensq
     */
    @Select("SELECT "
            +"s.id as id, "
            +"s.level_id as levelId, "
            +"s.kpi_id AS kpiId "
            +"FROM "
            +"ipm_alarm_set s "
            +"LEFT JOIN ipm_alarm_kpi ak ON ak.id=s.kpi_id  AND s.kpitype=1 "
            +"LEFT JOIN ipm_kpis k ON k.id= ak.kpi_id "
            +"WHERE "
            +"k.id=#{kpiId}  "
            +"AND s.watchpoint_id=0 "
            +"AND s.business_id=#{businessId} "
            +"AND ak.module_id=#{moduleId} "
            +"AND s.level_id!=1 "
            +"ORDER BY s.id ASC ")
    List<AlarmSetBean> getAsetGroupByCustomUnionAlarmSetId(AlarmSetBean alarmSetBean);

}  
