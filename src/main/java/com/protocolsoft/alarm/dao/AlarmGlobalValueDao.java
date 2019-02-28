/**
 *<p>Copyright © 北京协软科技有限公司版权所有。</p>
 *类名:AlarmGlobalValueDao
 *创建人:chensq    创建时间:2018年3月5日
 */
package com.protocolsoft.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.protocolsoft.alarm.bean.AlarmGlobalValueBean;

/**
 * @ClassName: AlarmGlobalValueDao
 * @Description: 全局阈值设置dao
 * @author chensq
 *
 */
public interface AlarmGlobalValueDao {
    
    /**
     * @Title: getAlarmGlobalValueByParam
     * @Description: 查询set，根据参数返回告警全局阈值设置集合
     * @param globalsetId
     * @return List<AlarmGlobalValueBean>
     * @author chensq
     */
    @Select("select "
            + "id as id, " 
            +" globalset_id as globalsetId, " 
            +" level_id as levelId, " 
            +" triggerflag as triggerflag, " 
            +" triggerusestatus as triggerusestatus, "             
            +" triggervalue as triggerValue " 
            +" from " 
            +" ipm_alarm_globalvalue " 
            +" where " 
            +" globalset_id = #{globalsetId}")    
    List<AlarmGlobalValueBean> getAlarmGlobalValueByParam(long globalsetId);
    
    /**
     * @Title: deleteAlarmGlobalValue
     * @Description: 删除
     * @param globalsetId void
     * @author chensq
     */
    @Delete("delete from ipm_alarm_globalvalue where globalset_id =#{globalsetId} ")  
    void deleteAlarmGlobalValue(long globalsetId);
    
    /**
     * @Title: insertAlarmGlobalValue
     * @Description: 新增 
     * @param alarmGlobalValueBean void
     * @author chensq
     */
    @Insert("insert into ipm_alarm_globalvalue(globalset_id,level_id,triggerflag,triggerusestatus,triggervalue) "
            + "values(#{globalsetId},#{levelId},#{triggerflag},#{triggerusestatus},#{triggerValue})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    void insertAlarmGlobalValue(AlarmGlobalValueBean alarmGlobalValueBean);
   
    /**
     * @Title: updateAlarmGlobalValue
     * @Description: 修改
     * @param alarmGlobalValueBean void
     * @author chensq
     */
    @Update("update ipm_alarm_globalvalue "
            + "set "
            + "triggerflag = #{triggerflag}, "
            + "triggerusestatus = #{triggerusestatus}, "
            + "triggervalue = #{triggerValue} "
            + "where "
            + "globalset_id = #{globalsetId} and  "
            + "level_id = #{levelId} ")
    void updateAlarmGlobalValue(AlarmGlobalValueBean alarmGlobalValueBean);
    
}
