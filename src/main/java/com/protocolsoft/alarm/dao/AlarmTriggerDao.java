/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmTriggerDao
 *创建人:chensq    创建时间:2017年10月31日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.protocolsoft.alarm.bean.AlarmTriggerBean;

/**
 * @ClassName: AlarmTriggerDao
 * @Description: AlarmTrigger Dao
 * @author chensq
 *
 */
@Repository
public interface AlarmTriggerDao {
    /**
     * 
     * @Title: getTriggerSetIdList
     * @Description: 返回符合时间段范围and alarmsetId的数据集合
     * @param alarmsetId 告警设置id
     * @param starttime 开始时间
     * @param endtime 结束时间
     * @return List<AlarmTriggerBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            + "alarmset_id as alarmsetId, " 
            + "triggerflag as triggerflag, " 
            + "triggerusestatus as triggerusestatus, "             
            + "triggervalue as triggervalue, " 
            + "triggerunit as triggerunit, " 
            + "triggertime as triggertime, " 
            + "DATE_FORMAT(FROM_UNIXTIME(triggertime), '%Y-%m-%d %H:%i:%s') as triggertimeStr " 
            + "from " 
            + "ipm_alarm_trigger " 
            + "where " 
            + "alarmset_id = ${alarmsetId} "
            + "and triggertime >= ${starttime} "
            + "and triggertime <= ${endtime} ")
    List<AlarmTriggerBean> getTriggerSetIdList(
            @Param("alarmsetId") String  alarmsetId,
            @Param("starttime") long  starttime,
            @Param("endtime") long  endtime);
    
    /**
     * 
     * @Title: addAlarmTrigger
     * @Description: 添加告警普通阈值历史
     * @param alarmTriggerBean 告警历史阈值对象
     * @return int
     * @author chensq
     */    
    @Insert("insert into ipm_alarm_trigger (alarmset_id,triggerflag,triggerusestatus,triggervalue,triggerunit,triggertime) "
            + " values (#{alarmsetId},#{triggerflag},#{triggerusestatus},#{triggervalue},#{triggerunit},#{triggertime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addAlarmTrigger(AlarmTriggerBean alarmTriggerBean);
    
    /**
     * 
     * @Title: delAlarmTriggerByAlarmsetId
     * @Description: 删除告警阈值历史记录(自定义告警表ipm_alarm_trigger)
     * @param alarmsetId
     * @return boolean
     * @author chensq
     */   
    @Delete("delete from ipm_alarm_trigger where alarmset_id=${alarmsetId}")
    boolean delAlarmTriggerByAlarmsetId(@Param("alarmsetId") long  alarmsetId);
    
    /**
     * @Title: getAsetGroupByAlarmSetIdAlarmTrigger
     * @Description: 根据alarmset_id查询ipm_alarm_trigger信息
     * @param alarmsetId
     * @return List<AlarmTriggerBean>
     * @author chensq
     */
    @Select("select "
            + "a.*  " 
            + "from " 
            + "ipm_alarm_trigger a "
            + "LEFT JOIN ipm_alarm_trigger b ON a.alarmset_id=b.alarmset_id AND a.triggertime<=b.triggertime "
            + "where a.alarmset_id=${alarmsetId} "
            + "GROUP BY a.id,a.alarmset_id,a.alarmset_id,a.triggerflag,a.triggertime "
            + "HAVING COUNT(b.id)<=2 "
            + "ORDER BY a.alarmset_id,a.triggertime DESC ")
    List<AlarmTriggerBean> getAsetGroupByAlarmSetIdAlarmTrigger(@Param("alarmsetId") long  alarmsetId);
    
    /**
     * @Title: getMinStartTime
     * @Description: 为ipm_alarm_trigger表历史数据删除使用
     * @param endTime  结束时间
     * @return Long
     * @author chensq
     */
    @Select("select min(triggertime) as triggertime from ipm_alarm_trigger where triggertime <= #{endTime}")
    Long getMinStartTime (@Param("endTime") long endTime);    
    
    /**
     * @Title: loopDelAlertTrigger
     * @Description: 为ipm_alarm_trigger表历史数据删除使用,定时删除告警记录使用
     * @param startTime
     * @param endTime void
     * @author chensq
     */
    @Delete("delete from ipm_alarm_trigger where triggertime>=#{startTime} AND triggertime <= #{endTime}")
    void  loopDelAlertTrigger (@Param("startTime") long startTime, @Param("endTime") long endTime);
    
    /**
     * @Title: loopDelAlertTriggerByCount
     * @Description: 为ipm_alarm_trigger表历史数据删除使用,定时删除告警记录以外数量使用
     * @param startTime void
     * @author chensq
     */
    @Delete("delete from ipm_alarm_trigger where triggertime<#{startTime} ")
    void  loopDelAlertTriggerByCount (@Param("startTime") long startTime);
    
}
